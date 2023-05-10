package edu.uncc.finalexam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import edu.uncc.finalexam.databinding.FragmentNewsBinding;
import edu.uncc.finalexam.databinding.NewsRowItemBinding;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {
    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 FragmentNewsBinding binding;
    private final OkHttpClient client = new OkHttpClient();
    NewsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("News");

          binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
           adapter= new NewsAdapter(new ArrayList<>());
            binding.recyclerView.setAdapter(adapter);


             getNews();
        //https://newsapi.org/v2/top-headlines?country=us&apiKey=your-api-key&category=technology&pageSize=50
        //this is the api .. need to set apiKey to your API Key!!




       binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.logout();
            }
        });

       binding.buttonMyLists.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               mListener.goToMyLists();
           }
       });
    }


    void getNews(){
        Request request = new Request.Builder()
                .url("https://www.theappsdr.com/news_api.json")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if(response.isSuccessful()){
                    String body = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        JSONArray articles = jsonObject.getJSONArray("articles");
                        for(int i = 0; i < articles.length(); i++){
                            JSONObject article = articles.getJSONObject(i);
                            String name = article.getJSONObject("source").getString("name");
                            String title = article.getString("title");
                            String author = article.getString("author");
                            String url = article.getString("url");
                            String urlToImage = article.getString("urlToImage");
                            String publishedAt = article.getString("publishedAt");
                            String content = article.getString("content");
                            String description = article.getString("description");

                            News news = new News(name,title, author, url, urlToImage, publishedAt, content, description);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    binding.textViewNews.setText(news.toString());
                                    adapter.newsList.add(news);
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }

            }
        });

    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
        ArrayList<News> newsList;
        public NewsAdapter(ArrayList<News> newsList){
            this.newsList = newsList;
        }

        @NonNull
        @Override
        public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            NewsRowItemBinding vhBinding = NewsRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

            return new NewsViewHolder(vhBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            News news = newsList.get(position);
            holder.bind(news);
        }

        @Override
        public int getItemCount() {
            return newsList.size();
        }

        class NewsViewHolder extends RecyclerView.ViewHolder{
            NewsRowItemBinding mBinding;
            News mNews;

            public NewsViewHolder( NewsRowItemBinding vhBinding) {
                super(vhBinding.getRoot());
                this.mBinding = vhBinding;


                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.goToNewsDetails(mNews);
                    }
                });

            }
            void bind(News news){
                this.mNews = news;
                mBinding.textViewNewsTitle.setText(mNews.getTitle());
                mBinding.textViewNewsAuthor.setText(mNews.getAuthor());
                mBinding.textViewPublishedAt.setText(mNews.getPublishedAt());
                mBinding.textViewSourceName.setText(mNews.getName());
                Picasso.get().load(mNews.getUrlToImage()).into(mBinding.imageViewNewsIcon);



            }
        }
    }
    NewsFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (NewsFragmentListener) context;
    }

    public interface NewsFragmentListener{
        void goToNewsDetails(News news);
        void goToMyLists();
        void logout();
    }
}