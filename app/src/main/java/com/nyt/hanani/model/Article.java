package com.nyt.hanani.model;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

    private String webUrl;
    private String title;
    private String pubDate;

    public Article(){ }

    /**
     * Parse JSON object
     * @param json
     */
    public Article(JSONObject json){
        try {
            JSONObject headline = json.getJSONObject("headline");
            title = headline.optString("main");
        } catch (JSONException e) {
            if (TextUtils.isEmpty(title))
                title = json.optString("title");
        }

        try {
            pubDate = convertDate(json.optString("pub_date"), "yyyy-MM-dd'T'HH:mm:ss+0000");
        } catch (Exception e) {
            if (TextUtils.isEmpty(pubDate))
                pubDate = convertDate(json.optString("published_date"), "yyyy-MM-dd");
        }

        webUrl = json.optString("web_url");
        if (TextUtils.isEmpty(webUrl))
            webUrl = json.optString("url");
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    private String convertDate(String date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date newDate = null;
        try {
            newDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateFormat = new SimpleDateFormat("d MMM yyyy");
        date = dateFormat.format(newDate);
        return date;
    }
}
