package newsapp.eudoxia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dorian on 26/04/2017.
 */

public class ListFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView list;
    NewsAdapter adapter;
    NewsResponse response;
    Url url;
    //public static final NewsAPI api = new NewsAPI();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.listfragment,container,false);
        list = (ListView)v.findViewById(R.id.listView);
        Bundle args = getArguments();
        String param = args.getString("tab");

        url = new Url();
        SharedPreferences p = getContext().getSharedPreferences("Preferences",(MODE_PRIVATE));

        switch(param) {
            case "Trends":
                url.setToShared(true);
                break;
            case "Economy":
                url.addCategory("Business");
                break;
            case "Politics":
                url.addCategory("Society/Politics");
                break;
            case "Society":
                url.addCategory("Society");
                url.addIgcategory("Society/Politics");
                break;
            case "Sports":
                url.addCategory("Sports");
                break;
            case "Science":
                url.addCategory("Science");
                url.addIgcategory("Science/Technology");
                break;
            case "Ecology":
                url.addCategory("Science/Biology/Ecology");
                break;
            case "Technology":
                url.addCategory("Science/Technology");
                url.addCategory("Computer");
                break;
            case "Culture":
                url.addCategory("Arts");
                break;
            default:
                break;
        }

        RunAPI runapi = new RunAPI();
        runapi.execute();

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String title = response.articles.results.get(i).title;
        String imgurl = response.articles.results.get(i).image;
        String desc = response.articles.results.get(i).body;
        String url = response.articles.results.get(i).url;

        Intent intent  = new Intent(getActivity(),ArticleActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("url",url);
        intent.putExtra("desc",desc);
        intent.putExtra("imgurl",imgurl);

        startActivity(intent);
    }

    public class RunAPI extends AsyncTask<String, Object, NewsResponse> {

        @Override
        protected NewsResponse doInBackground(String... strings) {

            try {
                response = NewsAPI.run(url);
            } catch (IOException e) {
            }
            return response;
        }


        @Override
        protected void onPostExecute(NewsResponse newsResponse) {
            super.onPostExecute(newsResponse);

            adapter = new NewsAdapter();
            list.setAdapter(adapter);
            list.setOnItemClickListener(ListFragment.this);
            if(getView()!=null) {
                getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }
    }

    public class NewsAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public NewsAdapter(){
            inflater = (LayoutInflater)getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            if(response == null){
                return 0;
            }
            return response.articles.count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v = view;
            if(v == null){
                v = inflater.inflate(R.layout.listitem, viewGroup, false);
            }

            TextView titre = (TextView)v.findViewById(R.id.title);
            ImageView image = (ImageView)v.findViewById(R.id.image);
            String title = response.articles.results.get(i).title;
            String imgurl = response.articles.results.get(i).image;

            titre.setText(title);
            if(imgurl!=null) {
                Picasso.with(getActivity().getApplicationContext()).load(imgurl).into(image);
            }
            else{
                image.setImageResource(R.mipmap.ic_sad);
            }
            return v;
        }
    }
}
