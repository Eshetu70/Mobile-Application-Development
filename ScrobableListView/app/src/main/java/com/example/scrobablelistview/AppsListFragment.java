package com.example.scrobablelistview;

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

import com.example.scrobablelistview.databinding.FragmentAppsListBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_APP = "ARG_PARAM_APP";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ArrayList<DataServices.App> appArrayList;
    //private String mParam2;
     private String mCategory;
    public AppsListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_APP, category);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory= getArguments().getString(ARG_PARAM_APP);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentAppsListBinding binding;
    AppsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAppsListBinding.inflate(inflater,container, false);
        return  binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(mCategory);
        appArrayList =DataServices.getAppsByCategory(mCategory);
        adapter =new AppsAdapter(getActivity(),appArrayList);
        binding.listView.setAdapter(adapter);


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataServices.App app =appArrayList.get(i);
                mlistener.sendSelectedApp(app);
            }
        });

    }
    class AppsAdapter extends ArrayAdapter<DataServices.App>{

        public AppsAdapter(@NonNull Context context, @NonNull List<DataServices.App> objects) {
            super(context,R.layout.app_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView =getLayoutInflater().inflate(R.layout.app_list_item, parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.textViewAppName =convertView.findViewById(R.id.textViewAppName);
                viewHolder.textViewArtistName =convertView.findViewById(R.id.textViewArtistName);
                viewHolder.textViewReleaseDate =convertView.findViewById(R.id.textViewReleaseDate);
                convertView.setTag(viewHolder);
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            DataServices.App app = getItem(position);
            viewHolder.textViewAppName.setText(app.getName());
            viewHolder.textViewArtistName.setText(app.getArtistId());
            viewHolder.textViewReleaseDate.setText(app.getReleaseDate());
            return convertView;
        }

    }

   private static class ViewHolder{
        TextView textViewAppName;
        TextView textViewArtistName;
        TextView textViewReleaseDate;
   }
    AppsListFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(AppsListFragmentListener)context;
    }

    interface AppsListFragmentListener{
        void sendSelectedApp(DataServices.App app);
   }
}