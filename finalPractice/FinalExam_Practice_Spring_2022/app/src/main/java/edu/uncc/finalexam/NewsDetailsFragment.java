package edu.uncc.finalexam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.uncc.finalexam.databinding.FragmentNewsDetailsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_NEWS = "ARG_PARAM_NEWS";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private News mNews;
    private String mParam2;

    public NewsDetailsFragment() {
        // Required empty public constructor
    }


    public static NewsDetailsFragment newInstance(News news) {
        NewsDetailsFragment fragment = new NewsDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_NEWS, news);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNews = (News) getArguments().getSerializable(ARG_PARAM_NEWS);

        }
    }
     FragmentNewsDetailsBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentNewsDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("News Details");


        binding.textViewNewsTitle.setText(mNews.getTitle());
        binding.textViewNewsAuthor.setText(mNews.getAuthor());
        Picasso.get().load(mNews.getUrlToImage()).into(binding.imageViewNewsIcon);
        binding.textViewSourceName.setText(mNews.getName());


        binding.imageViewAddToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<NewsList> newsList = new ArrayList<>();
                NewsList news = new NewsList(mNews.getTitle(),mNews.getAuthor(),mNews.getUrlToImage(),mNews.getUrl(),mNews.getName());
                mListener.addNewsToList(news);

            }
        });
        binding.imageViewBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewsUrl(mNews.getUrl());
            }
        });
    }

    //this is the code required to open the news page news url
    public void openNewsUrl(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getActivity(), Uri.parse(url));
    }
    NewsDetailsFragmentInterface mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewsDetailsFragmentInterface) context;
    }

    interface NewsDetailsFragmentInterface{
        void addNewsToList(NewsList news);
    }
}