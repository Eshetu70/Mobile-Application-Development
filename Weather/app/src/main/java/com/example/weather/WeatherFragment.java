package com.example.weather;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weather.databinding.FragmentWeatherBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DataService.City mCity;


    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(DataService.City city) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CITY, city);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);

        }
    }


    FragmentWeatherBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    Weather mWeather;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding =FragmentWeatherBinding.inflate(inflater, container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Weather");

        binding.textViewCityName.setText(mCity.toString());
        getWeather();


        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.gotoCheckedForcast(mCity);

            }
        });

    }




    //https://api.openweathermap.org/data/2.5/weather?
    // lat=51.5285578
    // &lon=-0.2420223&
    // appid=5e06cb54ad2e6448e96671eaadc3f7df
    // &units=imperial

 void getWeather(){
        HttpUrl url = HttpUrl.parse("https://api.openweathermap.org/data/2.5/weather").newBuilder()
                .addQueryParameter("appid","5e06cb54ad2e6448e96671eaadc3f7df")
                .addQueryParameter("units","imperial")
                .addQueryParameter("lon",String.valueOf(mCity.getLon()) )
                .addQueryParameter("lat",String.valueOf(mCity.getLat()))
                .build();
     Request request = new Request.Builder()
             .url(url)
             .build();

     client.newCall(request).enqueue(new Callback() {
         @Override
         public void onFailure(@NonNull Call call, @NonNull IOException e) {

         }

         @Override
         public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
             if(response.isSuccessful()){

                 String body = response.body().string();
                 try {
                     JSONObject jsonObject = new JSONObject(body);
                    mWeather = new Weather(jsonObject);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.textViewTemp.setText(String.valueOf(mWeather.getTemp()));
                            binding.textViewTempMax.setText(String.valueOf(mWeather.getTemp_max()));
                            binding.textViewTempMin.setText(String.valueOf(mWeather.getTemp_min()));
                            binding.textViewDesc.setText(String.valueOf(mWeather.getDescription()));
                            binding.textViewWindSpeed.setText(String.valueOf(mWeather.getSpeed()));
                            binding.textViewWindDegree.setText(String.valueOf(mWeather.getDeg()));
                            binding.textViewHumidity.setText(String.valueOf(mWeather.getHumidity()));
                            binding.textViewCloudiness.setText(String.valueOf(mWeather.getCloudiness()));
                            Picasso.get().load(mWeather.getIconUrl()).into(binding.imageViewWeatherIcon);
                        }
                    });


                 } catch (JSONException e) {
                     throw new RuntimeException(e);
                 }

             }else {

             }

         }
     });


 }
    WeatherFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(WeatherFragmentListener)context;
    }

    interface WeatherFragmentListener{
        void gotoCheckedForcast(DataService.City mCity);
 }
}