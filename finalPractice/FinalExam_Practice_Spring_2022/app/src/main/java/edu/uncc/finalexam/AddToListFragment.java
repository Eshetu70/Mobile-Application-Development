package edu.uncc.finalexam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import edu.uncc.finalexam.databinding.FragmentAddToListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddToListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NEWS = "ARG_PARAM_NEWS";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private NewsList mNews;
    private String mParam2;

    public AddToListFragment() {
        // Required empty public constructor
    }


    public static AddToListFragment newInstance(NewsList news) {
        AddToListFragment fragment = new AddToListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_NEWS, news);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNews = (NewsList) getArguments().getSerializable(ARG_PARAM_NEWS);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentAddToListBinding binding;
    ArrayAdapter<NewsList> adapter;
    ArrayList<NewsList> newsLists = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddToListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Add To List");
        newsLists.add(mNews);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, newsLists);
        binding.listViewMyLists.setAdapter(adapter);
        binding.listViewMyLists.setOnItemClickListener((parent, view1, position, id) -> {
            NewsList newsList = newsLists.get(position);
            newsLists.add(mNews);
            db.collection("lists").document(newsList.getListId()).set(newsList).addOnSuccessListener(aVoid -> {
                getActivity().getSupportFragmentManager().popBackStack();
            });
        });


        db.collection("lists").whereEqualTo("uid", mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                NewsList newsList = queryDocumentSnapshots.getDocuments().get(i).toObject(NewsList.class);
                newsLists.add(newsList);
            }
            adapter.notifyDataSetChanged();
        });
    }
}