package edu.calpoly.idulkin.podcrust.rest;

import android.util.Log;

//import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter ;
//import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
//import org.springframework.security.oauth2.client.OAuth2RestTemplate;
//import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import audiosearch.Audiosearch;
import audiosearch.exception.CredentialsNotFoundException;
import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import edu.calpoly.idulkin.podcrust.rest.Show.Show;

/*import java.util.ArrayList;
import java.util.List;

import static android.R.attr.port;
import static java.lang.String.format;
import static java.util.Arrays.asList;*/

/**
 * Created by Max on 11/29/2016.
 */

public class QueryExecutor {
    public static Trending getTrending() {

        /*ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setUsername("roy");
        resourceDetails.setPassword("spring");
        resourceDetails.setAccessTokenUri(format("http://localhost:%d/oauth/token", port));
        resourceDetails.setClientId("clientapp");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(asList("read", "write"));

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate restTemplate2 = new OAuth2RestTemplate(resourceDetails, clientContext);
        //restTemplate2.setMessageConverters(asList(new MappingJackson2HttpMessageConverter()));

        final List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        restTemplate2.setMessageConverters(converters);
        return restTemplate2.getForObject("https://www.audiosear.ch/api/trending", Trending.class);*/


        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());;
        Trending t = restTemplate.getForObject("https://www.audiosear.ch/api/trending", Trending.class);
        Log.d("QueryExecutor", t.toString());
        return t;
    }

    final String callbackUrl = "urn:ietf:wg:oauth:2.0:oob";
    final String applicationId = "c2b235f2620e362157a40aec609e737fe5a2547784933e00201ff90358e092c5";
    final String secret = "bee75fbb20ce6b45b64113b44208d12aeca02121fee8ea40f1bd9f44b491ba1c";
    final String authorizationCode = "ad2311b2860d224f89c32b7dfd4cb99550ba358aef412fae9ad11b52957a8930";

    Audiosearch createClient() throws CredentialsNotFoundException, UnsupportedEncodingException {

        return new Audiosearch()
                .setApplicationId(applicationId)
                .setSecret(secret)
                .build();

        /*try {
            Audiosearch result = new Audiosearch()
                    .setApplicationId(applicationId)
                    .setSecret(secret)
                    .build();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) { }
            finally {
                return createClient();
            }
        }*/
    }

    SearchShowResult getSearchShowResult(Audiosearch client, String query) throws IOException {
        return client.searchShows(query).execute().body();

        /*try {
            return client.searchShows(query).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) { }
            finally {
                return getSearchShowResult(client, query);
            }
        }*/
    }

    Show getShow(Audiosearch client, long showId) throws IOException {
        return client.getShow(showId).execute().body();

        /*try {
            return client.getShow(showId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) { }
            finally {
                return getShow(client, showId);
            }
        }*/
    }

    Episode getEpisode(Audiosearch client, long episodeId) throws IOException {
        return client.getEpisode(episodeId).execute().body();

        /*try {
            return client.getEpisode(episodeId).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) { }
            finally {
                return getEpisode(client, episodeId);
            }
        }*/
    }


}
