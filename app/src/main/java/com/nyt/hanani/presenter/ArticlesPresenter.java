package com.nyt.hanani.presenter;

import android.os.AsyncTask;

import com.nyt.hanani.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.nyt.hanani.utils.Constant.API_KEY;
import static com.nyt.hanani.utils.Constant.SEARCH_URL;

public class ArticlesPresenter {

    private View view;

    public ArticlesPresenter(View view) {
        this.view = view;
    }

    /**
     * Get articles by using search api
     * @param search keyword input by user.
     */
    public void getBySearch(String search){
        new AsyncTask<String,Void,ArrayList<Article>>() {
            @Override
            protected ArrayList<Article> doInBackground(String... search) {
                ArrayList<Article> articleList = new ArrayList<>();
                try {
                    URL url = new URL(String.format(SEARCH_URL, search[0], API_KEY));
                    HttpsURLConnection connection =
                            (HttpsURLConnection)url.openConnection();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer();
                    String tmp="";
                    while((tmp=reader.readLine())!=null)
                        json.append(tmp).append("\n");
                    reader.close();

                    JSONObject data = new JSONObject(json.toString());
                    JSONObject response = data.getJSONObject("response");
                    JSONArray docs = response.getJSONArray("docs");

                    for (int i = 0; i < docs.length(); i++) {
                        try {
                            JSONObject articles = docs.getJSONObject(i);
                            Article article = new Article(articles);
                            articleList.add(article);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

                return articleList;
            }

            @Override
            protected void onPostExecute(ArrayList<Article> articles) {
                if (articles.size()>0) {
                    view.updateArticleList(articles);
                } else {
                    view.searchNotFound();
                }
            }
        }.execute(search);

    }

    /**
     * Get articles by using most popular api.
     * @param link selected by user.
     */
    public void getPopular(String link){
        new AsyncTask<String,Void,ArrayList<Article>>() {

            @Override
            protected ArrayList<Article> doInBackground(String... link) {
                ArrayList<Article> articleList = new ArrayList<>();
                try {
                    URL url = new URL(String.format(link[0], API_KEY));
                    HttpsURLConnection connection =
                            (HttpsURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    StringBuffer json = new StringBuffer();
                    String tmp = "";
                    while ((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();

                    JSONObject data = new JSONObject(json.toString());
                    JSONArray result = data.getJSONArray("results");

                    for (int i = 0; i < result.length(); i++) {
                        try {
                            JSONObject articles = result.getJSONObject(i);
                            Article article = new Article(articles);
                            articleList.add(article);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return articleList;
            }

            @Override
            protected void onPostExecute(ArrayList<Article> articles) {
                view.updateArticleList(articles);
            }
        }.execute(link);
    }

    public interface View{

        void updateArticleList(ArrayList<Article> articles);
        void searchNotFound();
    }
}
