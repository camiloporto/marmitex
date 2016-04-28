package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
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

    public ProfileService(Context context) {
        this.context = context;
    }

    public void create(Profile profile) {

        RestTemplate restTemplate = criaRestTemplate();
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
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        //FIXME create proxy only on local environment.
        //Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
//        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }
}
