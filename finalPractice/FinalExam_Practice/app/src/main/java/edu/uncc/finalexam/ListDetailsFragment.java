package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.uncc.finalexam.databinding.FragmentListDetailsBinding;
import edu.uncc.finalexam.databinding.NewsRowItemWithDeleteBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NEWSLIST = "ARG_PARAM_NEWSLIST";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private NewsList mNewsList;
    private String mParam2;

    public ListDetailsFragment() {
        // Required empty public constructor
    }


    public static ListDetailsFragment newInstance(NewsList newsList) {
        ListDetailsFragment fragment = new ListDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_NEWSLIST, newsList);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsList = (NewsList) getArguments().getSerializable(ARG_PARAM_NEWSLIST);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
  FragmentListDetailsBinding binding;
   ListDetailsAdapter adapter;
   ArrayAdapter<NewsList> adapter1;
    ArrayList<NewsList> newsLists = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ListenerRegistration listenerRegistration;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("List Details");
        newsLists.add(mNewsList);
     adapter1 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, newsLists);
      binding.listViewNews.setAdapter(adapter1);


        listenerRegistration= db.collection("lists").addSnapshotListener((value, error) -> {
          newsLists.clear();
          for(QueryDocumentSnapshot doc : value)
          {
              NewsList newsList = doc.toObject(NewsList.class);
              newsLists.add(newsList);
          }

          adapter1.notifyDataSetChanged();
      });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration!=null)
            listenerRegistration.remove();
    }

    class ListDetailsAdapter extends ArrayAdapter<NewsList>{

        public ListDetailsAdapter(@NonNull Context context, int resource, @NonNull List<NewsList> objects) {
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            NewsRowItemWithDeleteBinding binding;
            if(convertView==null)
            {
                binding = NewsRowItemWithDeleteBinding.inflate(getLayoutInflater(),parent,false);
                convertView = binding.getRoot();
                convertView.setTag(binding);
            }
            else
            {
                binding = (NewsRowItemWithDeleteBinding) convertView.getTag();
            }
            NewsList newsList = getItem(position);
            binding.textViewNewsTitle.setText(newsList.getTitle());
            binding.textViewNewsAuthor.setText(newsList.getAuthor());
            binding.textViewSourceName.setText(newsList.getListName());

            Picasso.get().load(newsList.getUrlToImage()).into(binding.imageViewNewsIcon);

            return  convertView;
        }
    }
}