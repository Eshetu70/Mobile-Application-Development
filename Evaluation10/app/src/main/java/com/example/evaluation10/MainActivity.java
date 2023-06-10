package com.example.evaluation10;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView textViewCity;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ArrayList<Location>locations = new ArrayList<>();

        textViewCity = findViewById(R.id.textViewCity);

        textViewCity.setText("N/A");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        Places.initialize(getApplicationContext(),"AIzaSyC0HuyZ-9me2J6cYb4PdsYKFFznwWSOZVE");
        PlacesClient placesClient = Places.createClient(this);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);

        autocompleteFragment.setLocationBias(RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596)
        ));
        autocompleteFragment.setCountry("IN");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i("demo", "Place: " + place.getName() + ", " + place.getId());
            }


            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("demo", "An error occurred: " + status);
            }
        });


        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        // Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));

        // Use the builder to create a FindAutocompletePredictionsRequest.

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(new LatLng(-33.8749937, 151.2041382))
                .setCountries("AU", "NZ")
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Log.i(TAG, prediction.getPlaceId());
                Log.i(TAG, prediction.getPrimaryText(null).toString());
            }
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e("demo", "Place not found: " + apiException.getStatusCode());
            }
        });








        findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                intent.putExtra("key", "value");
//                someActivityResultLauncher.launch(intent);


                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("text/plain");
                RequestBody body = RequestBody.create(mediaType, "");
                Request request = new Request.Builder()
                        .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522%2C151.1957362&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyC0HuyZ-9me2J6cYb4PdsYKFFznwWSOZVE")
                        .method("GET", null)
                        .build();


//                    Response response = client.newCall(request).execute();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        if (response.isSuccessful()) {

                            String body =response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(body);
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject resultsJson =jsonArray.getJSONObject(i);
                                    Location location = new Location(resultsJson);
                                    locations.add(location);
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("demo", "run: print the " + response);
                                    textViewCity.setText(" "+ locations);

                                }
                            });
                        }
                    }
                });

            }



        });
    }
    public void startAutocompleteActivity(View view){
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN,
                Arrays.asList(Place.Field.ID, Place.Field.NAME)
        ).build(this);
    }

//  public void startAutocompleteActivity(View view){
//        Intent intent = new Autocomplete.IntentBuilder(
//                AutocompleteActivityMode.FULLSCREEN,
//                Arrays.asList((Place.Field.ID, Place.Field.NAME)).build(this);
//        )
//  }
  
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {


            }
        });
    }
}
