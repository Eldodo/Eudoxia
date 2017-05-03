package newsapp.eudoxia;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsAPI {
    public static OkHttpClient client = null;
    public static Context context;


    public static NewsResponse run(Url query) throws IOException{

        if(client == null) {
            long cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(new File(context.getCacheDir(), "HttpResponseCache"), cacheSize);

            client = new OkHttpClient
                    .Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30,TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        }
        String url = query.getUrlPage(1);
        Log.i("Test",url);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response reponse = client.newCall(request).execute();
        String json = reponse.body().string();

        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<NewsResponse> jsonAdapter = moshi.adapter(NewsResponse.class);
        NewsResponse newsResponse =  jsonAdapter.fromJson(json);


        return newsResponse;
    }


}

