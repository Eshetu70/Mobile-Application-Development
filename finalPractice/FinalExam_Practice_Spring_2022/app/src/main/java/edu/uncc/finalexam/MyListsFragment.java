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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.uncc.finalexam.databinding.FragmentMyListsBinding;
import edu.uncc.finalexam.databinding.MylistRowItemBinding;

public class MyListsFragment extends Fragment {
    public MyListsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentMyListsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    MyListsAdapter adapter;
    ArrayList<NewsList> newsLists = new ArrayList<>();
    NewsList newsList = new NewsList();
    ListenerRegistration listenerRegistration;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       binding = FragmentMyListsBinding.inflate(inflater, container, false);
       return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Lists");
        adapter = new MyListsAdapter(getActivity(),R.layout.mylist_row_item, newsLists);
        binding.listViewMyLists.setAdapter(adapter);
        binding.editTextListName.setText(newsList.getName());


     listenerRegistration = db.collection("lists").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    return;
                }
                for (QueryDocumentSnapshot doc: value){
                    NewsList newsList = doc.toObject(NewsList.class);
                    newsLists.add(newsList);
                    }

                adapter.notifyDataSetChanged();

                }



        });

    binding.buttonAddList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String listName = binding.editTextListName.getText().toString();
            if(listName.isEmpty()){
                binding.editTextListName.setError("Please enter a list name");

            }else{
                DocumentReference docRef = db.collection("lists").document();
                HashMap<String,Object> data = new HashMap<>();
                data.put("listName",listName);
                data.put("listId",docRef.getId());
                data.put("name",mAuth.getCurrentUser().getDisplayName());
                data.put("uid",mAuth.getCurrentUser().getUid());
                data.put("quantity",newsLists.size());
                docRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            binding.editTextListName.setText("");
                        }

                    }
                });


            }

        }
    });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(listenerRegistration!=null)
            listenerRegistration.remove();
    }

    class MyListsAdapter extends ArrayAdapter<NewsList> {
        public MyListsAdapter(@NonNull Context context, int resource, @NonNull List<NewsList> objects) {
            super(context, resource, objects);
        }

        @Override
        public int getCount() {
            return newsLists.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            MylistRowItemBinding binding = MylistRowItemBinding.inflate(getLayoutInflater());
            NewsList newsList = newsLists.get(position);
            binding.textViewSourceName.setText(newsList.getListName());
            binding.textViewItemsCount.setText(" "+ newsLists.size());
            return binding.getRoot();
        }
    }
}