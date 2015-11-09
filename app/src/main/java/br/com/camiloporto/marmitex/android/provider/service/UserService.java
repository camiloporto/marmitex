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

}
