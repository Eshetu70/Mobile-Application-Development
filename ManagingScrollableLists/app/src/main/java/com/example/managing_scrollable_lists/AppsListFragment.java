package com.example.managing_scrollable_lists;

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

import com.example.managing_scrollable_lists.databinding.FragmentAppsListBinding;

import java.util.ArrayList;
import java.util.List;


public class AppsListFragment extends Fragment {


    private static final String ARG_PARAM_CATEGORY = "ARG_PARAM_CATEGORY";
   // private static final String ARG_PARAM2 = "param2";

    private String mCategory;
    private ArrayList<DataServices.App> appArrayList;
   // private String mParam2;

    public AppsListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_CATEGORY, category);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARAM_CATEGORY);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
   FragmentAppsListBinding binding;
    AppsAdater adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAppsListBinding.inflate(inflater,container, false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Top Paid Apps");
        appArrayList =DataServices.getAppsByCategory(mCategory);
        adapter =new AppsAdater(getActivity(),appArrayList);
        binding.listView.setAdapter(adapter);

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataServices.App app = appArrayList.get(i);
                mlistener.sendSelectedApp(app);

            }
        });

    }

    class AppsAdater extends ArrayAdapter<DataServices.App> {
        public AppsAdater(@NonNull Context context, @NonNull List<DataServices.App> objects) {
            super(context, R.layout.app_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView ==null){
                convertView =getLayoutInflater().inflate(R.layout.app_list_item,parent, false);

                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textViewAppName =convertView.findViewById(R.id.textViewAppName);
                viewHolder.textViewArtistName =convertView.findViewById(R.id.textViewAppName);
                viewHolder.textViewReleaseDate =convertView.findViewById(R.id.textViewReleaseDate);
                convertView.setTag(viewHolder);

            }
//            TextView textViewAppName =convertView.findViewById(R.id.textViewAppName);
//            TextView textViewArtistName =convertView.findViewById(R.id.textViewArtistName);
//            TextView textViewReleaseDate =convertView.findViewById(R.id.textViewReleaseDate);
               DataServices.App app = getItem(position);
              ViewHolder viewHolder = (ViewHolder) convertView.getTag();

            viewHolder.textViewAppName.setText(app.getName());
            viewHolder.textViewArtistName.setText(app.artistName);
            viewHolder.textViewReleaseDate.setText(app.getReleaseDate());
            return  convertView;
        }
    }

    private static class ViewHolder{
        TextView textViewAppName;
        TextView textViewArtistName;
        TextView textViewReleaseDate;
    }
    AppsListListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(AppsListListener)context;
    }

    interface AppsListListener{
        void sendSelectedApp(DataServices.App app);
}

}