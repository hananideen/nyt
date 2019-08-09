package com.nyt.hanani.utils;

public class Constant {

    public static final String BASE_URL = "https://api.nytimes.com/svc";
    public static final String SEARCH_URL = BASE_URL +"/search/v2/articlesearch.json?q=%s&api-key=%s";
    public static final String POPULAR_URL = BASE_URL +"/mostpopular/v2";
    public static final String POPULAR_EMAILED_URL = POPULAR_URL +"/emailed/7.json?api-key=%s";
    public static final String POPULAR_SHARED_URL = POPULAR_URL +"/shared/7/facebook.json?api-key=%s";
    public static final String POPULAR_VIEWED_URL = POPULAR_URL +"/viewed/7.json?api-key=%s";
    public static final String API_KEY = "fQSAAwTuPeXAoYEh58lOiXz0jkrF6e1E";
    public static final String ARTICLE_TYPE = "type";
    public static final String TYPE_VIEWED = "viewed";
    public static final String TYPE_SHARED = "shared";
    public static final String TYPE_EMAILED = "emailed";
}
