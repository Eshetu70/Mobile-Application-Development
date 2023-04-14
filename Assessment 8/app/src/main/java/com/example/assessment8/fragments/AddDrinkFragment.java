package com.example.assessment8.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.assessment8.AppDatabase;
import com.example.assessment8.R;
import com.example.assessment8.databinding.FragmentAddDrinkBinding;
import com.example.assessment8.db.Drink;

public class AddDrinkFragment extends Fragment {
    public AddDrinkFragment() {
        // Required empty public constructor
    }

    FragmentAddDrinkBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddDrinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add Drink");


        binding.seekBarAlcohol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewAlcoholPercentage.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonAddDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double alcohol = binding.seekBarAlcohol.getProgress();
                double size = 1.0;
                if(binding.radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton5oz){
                    size = 5.0;
                } else if(binding.radioGroupDrinkSize.getCheckedRadioButtonId() == R.id.radioButton12oz){
                    size = 12.0;
                }

                String type = "BEER";
                if(binding.radioGroupType.getCheckedRadioButtonId() == R.id.radioButtonWine){
                    type = "WINE";
                } else if(binding.radioGroupType.getCheckedRadioButtonId() == R.id.radioButtonShot){
                    type = "SHOT";
                }


                //TODO: Store the new drink to the database
                AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "drinks-db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();

   //double alcohol, double size, String type
                Drink drink = new Drink(alcohol, size, type);
                db.drinkDao().insertAll(drink);
                mListener.doneAddDrink();
            }
        });

        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelAddDrink();
            }
        });

    }

    AddDrinkFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddDrinkFragmentListener) context;
    }

    public interface AddDrinkFragmentListener{
        void doneAddDrink();
        void cancelAddDrink();
    }

}