package edu.calpoly.idulkin.podcrust.rest;

import android.util.Log;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Max on 11/29/2016.
 */

public class QueryExecutor {
    public static Trending getTrending() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());;
        Trending t = restTemplate.getForObject("https://www.audiosear.ch/api/trending", Trending.class);
        Log.d("QueryExecutor", t.toString());
        return t;
    }
}
