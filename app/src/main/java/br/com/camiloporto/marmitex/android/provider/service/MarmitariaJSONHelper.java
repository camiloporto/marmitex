package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;

import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import br.com.camiloporto.marmitex.android.model.Seller;

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

    @Deprecated
    public CouldantResponse persistRemote(Seller m) throws MalformedURLException {
        throw new UnsupportedOperationException("@Deprecated Operation");
//        URL endPOint = new URL("https://camiloporto.cloudant.com/marmitex-dev/" + m.getId());
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(key, pass);
//        httpHeaders.setAuthorization(httpBasicAuthentication);
//        HttpEntity<Seller> entity = new HttpEntity<Seller>(m, httpHeaders);
//
//        ResponseEntity<CouldantResponse> responseEntity = restTemplate.exchange(
//                "https://camiloporto.cloudant.com/marmitex-dev/" + m.getId(),
//                HttpMethod.PUT,
//                entity,
//                CouldantResponse.class
//        );
//
//        return responseEntity.getBody();

    }


    public Seller getMarmitaria(String idMarmitaria) {
        String url = "https://camiloporto.cloudant.com/marmitex-dev/" + idMarmitaria;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpBasicAuthentication httpBasicAuthentication = new HttpBasicAuthentication(key, pass);
        httpHeaders.setAuthorization(httpBasicAuthentication);
        HttpEntity<Seller> entity = new HttpEntity<Seller>(httpHeaders);

        ResponseEntity<Seller> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Seller.class
        );

        return responseEntity.getBody();
    }
}
