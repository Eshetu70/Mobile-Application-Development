package com.example.scrollable;

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

import com.example.scrollable.databinding.FragmentAppsListBinding;

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
    private static final String ARG_PARAM_CATEGORY = "ARG_PARAM_CATEGORY ";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mCategory;
   // private String mParam2;

    public AppsListFragment() {
        // Required empty public constructor
    }


    public static AppsListFragment newInstance(String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_CATEGORY , category);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCategory = getArguments().getString(ARG_PARAM_CATEGORY );
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
   FragmentAppsListBinding binding;
    UserAdapter adapter;
    ArrayList<DataServices.App> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding =  FragmentAppsListBinding.inflate(inflater,container,false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(mCategory);
        arrayList = DataServices.getAppsByCategory(mCategory);
        adapter =new UserAdapter(getContext(), arrayList);
        binding.listView.setAdapter(adapter);


        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DataServices.App app =arrayList.get(i);
                mlistener.sendAppsListSelected(app);

            }
        });
    }

    class UserAdapter extends ArrayAdapter<DataServices.App> {
        public UserAdapter(@NonNull Context context, @NonNull List<DataServices.App> objects) {
            super(context, R.layout.app_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView =getLayoutInflater().inflate(R.layout.app_list_item,parent, false);
            }
            DataServices.App app =getItem(position);
            TextView textViewAppName =convertView.findViewById(R.id.textViewAppName);
            TextView textViewArtistName = convertView.findViewById(R.id.textViewArtistName);
            TextView textViewReleaseDate =convertView.findViewById(R.id.textViewReleaseDate);
            textViewAppName.setText(app.getName());
            textViewArtistName.setText(app.getArtistName());
            textViewReleaseDate.setText(app.getReleaseDate());
            return convertView;
        }
    }
    AppsListFragmentListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mlistener =(AppsListFragmentListener)context;
    }

    interface AppsListFragmentListener{
        void sendAppsListSelected(DataServices.App app);
    }
}