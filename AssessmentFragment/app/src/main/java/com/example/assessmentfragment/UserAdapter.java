package com.example.assessmentfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<Profile> {
    public UserAdapter(@NonNull Context context,  @NonNull List<Profile> objects) {
        super(context, R.layout.profile_list_item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.profile_list_item, parent, false);

        }
        Profile nprofile = getItem(position);

        TextView textViewWeight =convertView.findViewById(R.id.textViewWeight);
         TextView textViewGender = convertView.findViewById(R.id.textViewGender);

         textViewWeight.setText(String.valueOf(nprofile.getWeight()));
         textViewGender.setText(nprofile.getGender());
        return convertView;
    }
}
