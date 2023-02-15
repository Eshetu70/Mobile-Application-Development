package com.example.hw04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hw04.databinding.FragmentViewDrinksBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewDrinksFragment extends Fragment {
    private static final String ARG_PARAM_DRINKS = "ARG_PARAM_DRINKS";
    private ArrayList<Drink> mDrinks;
    private int currentIndex = 0;

    public ViewDrinksFragment() {
        // Required empty public constructor
    }

    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinks) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DRINKS, drinks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDrinks = (ArrayList<Drink>) getArguments().getSerializable(ARG_PARAM_DRINKS);
        }
    }

    FragmentViewDrinksBinding binding;
    UserAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewDrinksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("View Drinks");
        currentIndex = 0;
        displayDrink();

        adapter= new UserAdapter(getActivity(),mDrinks);
        binding.listView.setAdapter(adapter);

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.closeViewDrinks();
            }
        });
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Drink drink = mDrinks.get(i);
                adapter.remove(drink);
            }
        });
    }

    void displayPrevious(){
        if(currentIndex == 0){
            currentIndex = mDrinks.size() - 1;
        } else {
            currentIndex--;
        }
        displayDrink();
    }

    private void displayDrink(){
        Drink drink = mDrinks.get(currentIndex);
//        binding.textViewDrinksCount.setText("Drink " + (currentIndex + 1) + " out of " + mDrinks.size());
//        binding.textViewDrinkSize.setText(String.valueOf(drink.getSize()) + "oz");
//        binding.textViewDrinkAlcoholPercent.setText(String.valueOf(drink.getAlcohol()) + "% Alcohol");
//        binding.textViewDrinkAddedOn.setText("Added " + drink.getAddedOn().toString());
    }

    class UserAdapter extends ArrayAdapter<Drink>

     {
        public UserAdapter(@NonNull Context context,  @NonNull List<Drink> objects) {
            super(context, R.layout.item_view_row, objects);
        }

         @NonNull
         @Override
         public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView ==null){
                convertView =getLayoutInflater().inflate(R.layout.item_view_row, parent,false);
            }
            Drink drink = mDrinks.get(position);
             TextView textViewAlcohol = convertView.findViewById(R.id.textViewAlcohol);
              TextView textViewdate= convertView.findViewById(R.id.textViewdate);
              TextView textView11 =convertView.findViewById(R.id.textView11);
              textViewAlcohol.setText(String.valueOf(drink.getAlcohol()));
              textViewdate.setText(drink.getAddedOn().toString());
              textView11.setText(String.valueOf(drink.getSize()));

             return convertView;
         }
     }


    ViewDrinksFragmentListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ViewDrinksFragmentListener) context;
    }

    interface ViewDrinksFragmentListener{
        void closeViewDrinks();
    }

}