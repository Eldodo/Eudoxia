package newsapp.eudoxia;


import android.content.Context;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsAPI {
    public String url;
    public final OkHttpClient client;
    public static Context context;

    public static void setContext(Context c){
        NewsAPI.context = c;

    }


    public NewsAPI(Url query){
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(context.getCacheDir(), "HttpResponseCache"), cacheSize);
        client = new OkHttpClient.Builder().cache(cache).build();

        url = query.getUrl();
        Log.i("Test",url);
    }

    public NewsResponse run() throws IOException{

        Request request = new Request.Builder().url(url).build();
        Response reponse = client.newCall(request).execute();
        String json = reponse.body().string();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<NewsResponse> jsonAdapter = moshi.adapter(NewsResponse.class);
        NewsResponse newsResponse =  jsonAdapter.fromJson(json);


        return newsResponse;
    }

}
