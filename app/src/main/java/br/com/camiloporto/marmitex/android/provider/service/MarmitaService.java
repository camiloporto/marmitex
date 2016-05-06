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
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.repository.MarmitariaRepository;
import br.com.camiloporto.marmitex.android.service.MarmitexException;
import br.com.camiloporto.marmitex.android.service.SecurityContext;

public class MarmitaService {
	
	private static final String TAG = "MarmitaService";


	private MarmitariaRepository marmitariaRepository;

	private Context context;
	private RestTemplate restTemplate;


	//FIXME this repository wont be necessary.. REST will be used instead
	@Deprecated
	public MarmitaService(MarmitariaRepository marmitariaRepository) {

		this.marmitariaRepository = marmitariaRepository;
	}

	public MarmitaService(Context context) {
		this.context = context;
	}

	public void save(Marmitaria m) {
		marmitariaRepository.save(m);
	}

	public Marmitaria create(String nome, String fone, String endereco) {
		Marmitaria m = new Marmitaria(nome, fone, endereco);
		Marmitaria saved = marmitariaRepository.save(m);
		return saved;
	}

	//FIXME load data useing ProfileSeller Service, throun REST API
	//FIXME get access_token for loggedUser
	public Marmitaria find(String username) {
		restTemplate = criaRestTemplate();
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		httpHeaders.add("Authorization", "Bearer " + SecurityContext.getAccessToken());
		HttpEntity<Marmitaria> entity = new HttpEntity<Marmitaria>(httpHeaders);

		String url = context.getString(R.string.marmitex_seller_endpoint);
		url += "/seller";
		try {
			ResponseEntity<Marmitaria> responseEntity = restTemplate.exchange(
					url,
					HttpMethod.GET,
					entity,
					Marmitaria.class
			);
			return responseEntity.getBody();
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

	private RestTemplate criaRestTemplate() {
		if(restTemplate == null)
			restTemplate = new RestTemplate();
		return restTemplate;
	}
}
