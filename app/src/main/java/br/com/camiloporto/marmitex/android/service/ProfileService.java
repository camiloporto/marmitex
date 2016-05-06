package br.com.camiloporto.marmitex.android.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.camiloporto.marmitex.android.R;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by ur42 on 22/04/2016.
 */
public class ProfileService {

    private Context context;
    private RestTemplate restTemplate;
    private String clientId;
    private String clientSecret;

    public ProfileService(Context context) {
        this.context = context;
        clientId = context.getString(R.string.oauth_client_id);
        clientSecret = context.getString(R.string.oauth_client_secret);

    }

    public void create(Profile profile) {

        restTemplate = criaRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Profile> entity = new HttpEntity<Profile>(profile, httpHeaders);

        String url = context.getString(R.string.marmitex_profile_endpoint);
        url += "/uaa";
        try {
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, String>>() {
                    }
            );
        } catch (HttpClientErrorException e) {
            String error = e.getResponseBodyAsString();
            //FIXME could fail deserialization. create fallback
            Map map = new Gson().fromJson(error, Map.class);
            List<String> errorMsg = (List<String>) map.get("errors");
            throw new MarmitexException(errorMsg);
        }
        catch (HttpServerErrorException e) {
            String error = e.getResponseBodyAsString();
            throw new MarmitexException(error);
        }

    }

    private RestTemplate criaRestTemplate() {
        if(restTemplate == null)
            restTemplate = new RestTemplate();
        return restTemplate;
    }

    public String login(String username, String password) {
        restTemplate = criaRestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(clientId, clientSecret);
        httpHeaders.setAuthorization(httpBasicAuthentication);

        MultiValueMap<String, String> requestContent = new LinkedMultiValueMap<String, String>();
        requestContent.add("grant_type", "password");
        requestContent.add("scope", "write");
        requestContent.add("username", username);
        requestContent.add("password", password);
        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<MultiValueMap<String, String>>(requestContent,httpHeaders);

        String url = context.getString(R.string.marmitex_profile_endpoint);
        url += "/oauth/token";
        try {
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<Map<String, String>>() {
                    },requestContent
            );

            Map<String, String> body = responseEntity.getBody();
            String accessToken = body.get("access_token");
            saveAccessToken(username, accessToken);
            return accessToken;
        } catch (HttpClientErrorException e) {
            String error = e.getResponseBodyAsString();
            //FIXME could fail deserialization. create fallback
            Map map = new Gson().fromJson(error, Map.class);
            List<String> errorMsg = (List<String>) map.get("errors");
            throw new MarmitexException(errorMsg);
        }
        catch (HttpServerErrorException e) {
            String error = e.getResponseBodyAsString();
            throw new MarmitexException(error);
        }
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void saveAccessToken(String username, String accessToken) {
        SecurityContext.setAcessToken(accessToken);
        SharedPreferences prefs =
                context.getSharedPreferences("access_tokens", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(username, accessToken);
        editor.commit();
    }
}
