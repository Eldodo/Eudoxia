package newsapp.eudoxia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.woxthebox.draglistview.DragListView;

import java.util.ArrayList;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        Intent intent = getIntent();
        String settings = intent.getStringExtra("settings");
        final ArrayList<String> mThemeList = new ArrayList<String>();
        if(settings.equals("true")){
            int i = 1;
            SharedPreferences p = getSharedPreferences("Preferences",(MODE_PRIVATE));
            String str = p.getString(""+i,"Test");
            while(!str.equals("Test")){
                mThemeList.add(str);
                i++;
                str = p.getString(""+i, "Test");
            }
            TextView txt = (TextView) findViewById(R.id.text_view_pref);
            txt.setText("Here you can reorder the topics. When finished, press OK.");
        }
        else{

            for (int i = 0; i < Themes.sThemeStrings.length; ++i) {
                mThemeList.add(Themes.sThemeStrings[i]);
        }
        }
        StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, mThemeList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.Themelist);

        listView.setThemeList(mThemeList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        Button button= (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String indice;
                editor.putString("0", "Valide");
                editor.commit();
                for (int n=0; n<mThemeList.size(); n++) {
                    indice = "" + (n+1);
                    System.out.println(n +'\0');
                    System.out.println(mThemeList.get(n));
                    editor.putString(indice, mThemeList.get(n));
                    editor.commit();
                    System.out.println(preferences.getString(indice,"Raté"));
                }
                Intent i= new Intent(Preferences.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
};

