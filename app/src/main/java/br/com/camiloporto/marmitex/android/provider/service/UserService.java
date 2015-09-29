package br.com.camiloporto.marmitex.android.provider.service;

import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

import android.util.Log;

public class UserService {
	private final static String TAG = "UserService";
	private RestService restService;
	private URL serverEndPoint;
	
	public UserService(RestService restService) {
		this.restService = restService;
	}
	
	public String novaMarmitaria(Marmitaria m) {
		try {
			JSONObject json = m.json();
			Log.i(TAG, json.toString());
			String response = restService.sendJSONPost(serverEndPoint, json);
			return response;
			//TODO enviar http POST para servidor via JSON
			//TODO obter resposta.
			//TODO notificar GUI
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
