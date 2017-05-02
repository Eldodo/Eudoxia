package newsapp.eudoxia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        final ArrayList<String> mThemeList = new ArrayList<String>();
        for (int i = 0; i < Themes.sThemeStrings.length; ++i) {
            mThemeList.add(Themes.sThemeStrings[i]);
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

