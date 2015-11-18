package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    public String persistRemote(Marmitaria m) throws MalformedURLException {
        final Gson gson = new Gson();
        String json = gson.toJson(m);
        URL endPOint = new URL("https://camiloporto.cloudant.com/marmitex-dev/" + m.getUuid());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(key, pass);
        httpHeaders.setAuthorization(httpBasicAuthentication);
        HttpEntity<Marmitaria> entity = new HttpEntity<Marmitaria>(m, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://camiloporto.cloudant.com/marmitex-dev/" + m.getUuid(),
                HttpMethod.PUT,
                entity,
                String.class
        );

        return responseEntity.getBody();

    }


    //FIXME tornar essas Operacoes Asincronas
    public void persistMarmitaria(Marmitaria m) {
        final Gson gson = new Gson();
        String json = gson.toJson(m);
        Log.i("JSONHeper", "json gerado: " + json);
        final String fileName = m.getLogin() + ".json";
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
        } catch (IOException e) {
            Log.e("MarmitariaJSONHelper", e.toString());
        } finally {
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                Log.e("MarmitariaJSONHelper", e.toString());
            }
        }


    }

    public Marmitaria readMarmitaria(String userId) {
        FileInputStream fis = null;
        Marmitaria m = null;
        try {
            fis = context.openFileInput(userId + ".json");
            Reader reader = new InputStreamReader(fis);
            Gson gson = new Gson();
            m = gson.fromJson(reader, Marmitaria.class);
            Log.i("JSONHeper", "marmitaria recuperada: " + m.getLogin());

        } catch (IOException e) {
            //do nothing
        } finally {
            if(fis != null) try {
                fis.close();
            } catch (IOException e) {
                //impossivel fechar stream
            }
        }
        return m;
    }

    public Marmitaria getMarmitaria(String idMarmitaria) {
        String url = "https://camiloporto.cloudant.com/marmitex-dev/" + idMarmitaria;
        RestTemplate restTemplate = new RestTemplate();
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
