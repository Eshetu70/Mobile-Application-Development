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
import com.example.assessment8.databinding.FragmentUpdateDrinkBinding;
import com.example.assessment8.db.Drink;

public class UpdateDrinkFragment extends Fragment {
    private static final String ARG_PARAM_DRINK = "ARG_PARAM_DRINK";
    private Drink mDrink;

    public UpdateDrinkFragment() {
        // Required empty public constructor
    }

    public static UpdateDrinkFragment newInstance(Drink drink) {
        UpdateDrinkFragment fragment = new UpdateDrinkFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_DRINK, drink);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDrink = (Drink) getArguments().getSerializable(ARG_PARAM_DRINK);
        }
    }

    FragmentUpdateDrinkBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpdateDrinkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Update Drink");

        binding.seekBarAlcohol.setProgress((int) mDrink.getAlcohol());
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

        if(mDrink.getSize() == 1.0){
            binding.radioGroupDrinkSize.check(R.id.radioButton1oz);
        } else if(mDrink.getSize() == 5.0){
            binding.radioGroupDrinkSize.check(R.id.radioButton5oz);
        } else if(mDrink.getSize() == 12.0){
            binding.radioGroupDrinkSize.check(R.id.radioButton12oz);
        }

        if(mDrink.getType().equals("BEER")){
            binding.radioGroupType.check(R.id.radioButtonBeer);
        } else if(mDrink.getType().equals("WINE")){
            binding.radioGroupType.check(R.id.radioButtonWine);
        } else if(mDrink.getType().equals("SHOT")){
            binding.radioGroupType.check(R.id.radioButtonShot);
        }

        binding.buttonUpdateDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add code to update the current drink.
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
                mDrink.setAlcohol(alcohol);
                mDrink.setSize(size);
                mDrink.setType(type);

                //TODO: Update the drink in the database.
                AppDatabase db = Room.databaseBuilder(getActivity(), AppDatabase.class, "drinks-db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
                Drink drink = new Drink(alcohol, size, type);
                db.drinkDao().update(mDrink);

                mListener.doneUpdateDrink();
            }
        });

        binding.buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelUpdateDrink();
            }
        });

    }

    UpdateDrinkListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UpdateDrinkListener) context;
    }

    public interface UpdateDrinkListener{
        void doneUpdateDrink();
        void cancelUpdateDrink();
    }
}