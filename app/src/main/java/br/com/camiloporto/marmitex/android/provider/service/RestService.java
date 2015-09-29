package br.com.camiloporto.marmitex.android.provider.service;

import java.net.URL;

import org.json.JSONObject;

import android.util.Log;

public class RestService {
	
	private static final String TAG = "RestService";
	private static RestService restService;
	
	private RestService() {
	}
	
	public static RestService getInstance() {
		if(restService == null) {
			restService = new RestService();
		}
		return restService;
	}

	public String sendJSONPost(URL serverEndPoint, JSONObject json) {
		// TODO enviar JSON via Post para Endpoit
		Log.i(TAG, "Salvando " + json);
		return "OK";
	}

}
