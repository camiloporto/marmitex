package br.com.camiloporto.marmitex.android.provider.service;

import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class RestService {
	
	private static final String TAG = "RestService";
	private static RestService restService;

	private final String key = "hengledungsheriallestopa";
	private final String pass = "4771147dce8dae2abf30787367d38c4197a39af7";
	
	private RestService() {
	}
	
	public static RestService getInstance() {
		if(restService == null) {
			restService = new RestService();
		}
		return restService;
	}

	public String get(String url) {
		//https://camiloporto.cloudant.com/marmitex-dev/_all_docs
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpPut = new HttpGet(url);

		try {
			// Add your data

			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-Type", "application/json");
			String credentials = encodeCredentials();
			httpPut.setHeader("Authorization", "Basic " + credentials);
			HttpResponse response = httpclient.execute(httpPut);
			InputStream content = response.getEntity().getContent();
			String result = convertInputStreamToString(content);
			return result;

		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	public String sendJSONPost(URL serverEndPoint, String json) {
		// TODO enviar JSON via Post para Endpoit
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPut httpPut = new HttpPut(serverEndPoint.toString());
//		HttpPost httppost = new HttpPost(serverEndPoint.toString());

		try {
			// Add your data
			StringEntity se = new StringEntity(json);
			httpPut.setEntity(se);
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-Type", "application/json");
			String credentials = encodeCredentials();
			httpPut.setHeader("Authorization", "Basic " + credentials);
			HttpResponse response = httpclient.execute(httpPut);
			InputStream content = response.getEntity().getContent();
			String result = convertInputStreamToString(content);
			return result;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	private String encodeCredentials() {
		return Base64.encodeToString((key + ":" + pass).getBytes(), Base64.NO_WRAP);
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

}
