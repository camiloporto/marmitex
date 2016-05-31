package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;

import com.google.gson.Gson;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import br.com.camiloporto.marmitex.android.R;
import br.com.camiloporto.marmitex.android.model.Seller;
import br.com.camiloporto.marmitex.android.repository.MarmitariaRepository;
import br.com.camiloporto.marmitex.android.service.MarmitexException;
import br.com.camiloporto.marmitex.android.service.SecurityContext;

public class MarmitaService {
	
	private static final String TAG = "MarmitaService";

	private Context context;
	private RestTemplate restTemplate;


	public MarmitaService(Context context) {
		this.context = context;
	}

	public void save(Seller m) {

		restTemplate = criaRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.add("Authorization", "Bearer " + SecurityContext.getAccessToken());
		HttpEntity<Seller> entity = new HttpEntity<Seller>(m, httpHeaders);

		String url = context.getString(R.string.marmitex_seller_endpoint);
		url += "/seller";
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

	public Seller find(String username) {
		restTemplate = criaRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.add("Authorization", "Bearer " + SecurityContext.getAccessToken());
		HttpEntity<Seller> entity = new HttpEntity<Seller>(httpHeaders);

		String url = context.getString(R.string.marmitex_seller_endpoint);
		url += "/seller";
		try {
			ResponseEntity<Seller> responseEntity = restTemplate.exchange(
					url,
					HttpMethod.GET,
					entity,
					Seller.class
			);
			return responseEntity.getBody();
		} catch (HttpClientErrorException e) {
			String error = e.getResponseBodyAsString();
			//FIXME could fail deserialization. create fallback
			Map map = new Gson().fromJson(error, Map.class);
			//FIXME Better Serialize this result. Error may not be from Application. but from OAuth..
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

	private RestTemplate criaRestTemplate() {
		if(restTemplate == null)
			restTemplate = new RestTemplate();
		return restTemplate;
	}
}
