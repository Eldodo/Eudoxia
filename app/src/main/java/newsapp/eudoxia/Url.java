package newsapp.eudoxia;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Url {
    private String url;
    //private String country = "default";
    private String lang = "default";
    private List<String> category = null;
    private List<String> igcategory = null;
    private int count = 200;
    private String date ;
    private Boolean byShared = false; //pour l'onglet des "trends"

    public Url(){
        url = "http://eventregistry.org/json/article?apiKey=29308057-876e-4825-982c-826dc6bba203&isDuplicateFilter=skipDuplicates&action=getArticles&resultType=articles&articlesIncludeArticleImage=True";
        Calendar today = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(today.getTime());
    }

   // public void setCountry(String country){
   //     this.country = country;
   // }

    public void setLang(String lang){
        this.lang = lang;
    }

    public void setCount(int c){
        count = c;
    }

    public void addCategory(String category){
        if(this.category == null){
            this.category = new ArrayList<String>();
        }
        this.category.add(category);
    }

    public void addIgcategory(String igcategory){
        if(this.igcategory == null){
            this.igcategory = new ArrayList<String>();
        }
        this.igcategory.add(igcategory);
    }

    public void setToShared(boolean toShared){
        this.byShared = toShared;
    }

    public String getUrl(){
        if (lang.equals("default")){
            lang = "eng"; //REMPLACER AVEC VALEUR DANS LE XML PREFERENCE
        }
        url += "&lang="+lang+"&articlesCount="+count;
        if (byShared == true){
            return url+"&articlesSortBy=socialScore&dateStart="+date+"&dateEnd="+date;
        }

        /*if (country.equals("default")){
            country = "Canada"; //REMPLACER AVEC VALEUR DANS LE XML PREFERENCE
        }
        if (!country.equals("none")){
            url+="&locationUri=http://en.wikipedia.org/wiki/"+country;
        }*/

        url += "&articlesSortBy=date";
        //TODO PARTCOURIR LISTES CATEGORY
        if(category != null) {
            for (String cat : category) {
                url += "&categoriUri=dmoz/" + cat;
            }
        }
        if(igcategory != null) {
            for (String igcat : igcategory) {
                url += "&ignoreCategoryUri=dmoz/" + igcat;
            }
        }
        return url;
    }






}
