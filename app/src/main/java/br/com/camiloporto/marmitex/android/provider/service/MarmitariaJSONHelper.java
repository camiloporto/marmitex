package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 28/10/15.
 */
public class MarmitariaJSONHelper {
    private Context context;

    private final String key = "hengledungsheriallestopa";
    private final String pass = "4771147dce8dae2abf30787367d38c4197a39af7";

    MarmitariaJSONHelper(Context context) {

        this.context = context;
    }

    public CouldantResponse persistRemote(Marmitaria m) throws MalformedURLException {
        URL endPOint = new URL("https://camiloporto.cloudant.com/marmitex-dev/" + m.getId());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(key, pass);
        httpHeaders.setAuthorization(httpBasicAuthentication);
        HttpEntity<Marmitaria> entity = new HttpEntity<Marmitaria>(m, httpHeaders);

        ResponseEntity<CouldantResponse> responseEntity = restTemplate.exchange(
                "https://camiloporto.cloudant.com/marmitex-dev/" + m.getId(),
                HttpMethod.PUT,
                entity,
                CouldantResponse.class
        );

        return responseEntity.getBody();

    }


    public Marmitaria getMarmitaria(String idMarmitaria) {
        String url = "https://camiloporto.cloudant.com/marmitex-dev/" + idMarmitaria;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(key, pass);
        httpHeaders.setAuthorization(httpBasicAuthentication);
        HttpEntity<Marmitaria> entity = new HttpEntity<Marmitaria>(httpHeaders);

        ResponseEntity<Marmitaria> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Marmitaria.class
        );

        return responseEntity.getBody();
    }
}
