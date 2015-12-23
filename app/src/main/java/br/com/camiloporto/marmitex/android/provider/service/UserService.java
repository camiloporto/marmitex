package br.com.camiloporto.marmitex.android.provider.service;

import java.net.URL;

public class UserService {
	private final static String TAG = "UserService";
	private RestService restService;
	private URL serverEndPoint;
	
	public UserService(RestService restService) {
		this.restService = restService;
	}

}
