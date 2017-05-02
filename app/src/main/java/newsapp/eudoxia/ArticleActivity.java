package newsapp.eudoxia;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ArticleActivity extends AppCompatActivity {
    ImageView image;
    TextView title,desc,url;
    String surl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        image = (ImageView)findViewById(R.id.imageArticle);
        title = (TextView)findViewById(R.id.TitleArticle);
        desc = (TextView)findViewById(R.id.DescArticle);
        url = (TextView)findViewById(R.id.urlArticle);

        Intent intent = getIntent();

        String stitle = intent.getStringExtra("title");
        surl = intent.getStringExtra("url");
        String sdesc = intent.getStringExtra("desc");
        String imgurl = intent.getStringExtra("imgurl");

        title.setText(stitle);
        Picasso.with(this).load(imgurl).into(image);
        desc.setText(sdesc);

    }
    public void onClick(View v){
        Intent web = new Intent(this, WebActivity.class);
        web.putExtra("url",surl);
        startActivity(web);
    }
}
