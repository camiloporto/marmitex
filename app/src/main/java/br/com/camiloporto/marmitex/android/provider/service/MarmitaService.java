package br.com.camiloporto.marmitex.android.provider.service;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class MarmitaService {
	
	private static final String TAG = "MarmitaService";
	
	private static MarmitaService marmitaService;

	private Context context;
	private RestService restService;
	private URL serverEndPoint;

	private MarmitaService(Context context, RestService restService) {
		super();
		this.context = context;
		this.restService = restService;
	}
	
	public static MarmitaService getInstance(Context context) {
		if(marmitaService == null) {
			marmitaService = new MarmitaService(
					context.getApplicationContext(),
					RestService.getInstance());
		}
		return marmitaService;
	}

	public String salvaCardapio(Cardapio cardapio) {
		try {
			JSONObject json = cardapio.json();
			Log.i(TAG, json.toString());
			String response = restService.sendJSONPost(serverEndPoint, json);
			return response;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	//FIXME ver como recuperar marmitaria cadastrada no dispositivo. recuperar via REST ou Localmente
	public Marmitaria readMarmitaria() {
		Marmitaria m = new Marmitaria("Marmita 1", "32410-9343", "Jaguarari 123, Lagoa Nova");
		List<Cardapio> cardapio  = list();
		for (Cardapio c: cardapio) {
			m.saveCardapio(c);
		}
		return m;
	}
	
	public List<Cardapio> list() {
		
		GrupoItems opcoes = new GrupoItems();
		opcoes.setDescricao("carnes");
		opcoes.setId(1L);
		
		GrupoItems opcoes2 = new GrupoItems();
		opcoes2.setDescricao("acompanhamentos");
		opcoes2.setId(2L);
		
		GrupoItems opcoes3 = new GrupoItems();
		opcoes3.setDescricao("saladas");
		opcoes3.setId(3L);
		
		ItemCardapio i1 = new ItemCardapio(opcoes);
		i1.setDescricao("Filet de Frango");
		i1.setId(1L);
		
		ItemCardapio i2 = new ItemCardapio(opcoes2);
		i2.setDescricao("Feijao preto");
		i2.setId(2L);
		
		ItemCardapio i3 = new ItemCardapio(opcoes3);
		i3.setDescricao("Hortalica");
		i3.setId(3L);
		
		opcoes.newItem(i1);
		opcoes2.newItem(i2);
		opcoes3.newItem(i3);
		
		
		//FIXME invocar via rest o servidor e construir cardapios
		Cardapio c1 = new Cardapio();
		c1.setId(1L);
		c1.setDescricao("Segunda-Feira");
		c1.adicionaGrupo(opcoes);
		c1.adicionaGrupo(opcoes2);
		c1.adicionaGrupo(opcoes3);
		
		Cardapio c2 = new Cardapio();
		c2.setId(2L);
		c2.setDescricao("Terça-Feira");
		c2.adicionaGrupo(opcoes);
		c2.adicionaGrupo(opcoes2);
		c2.adicionaGrupo(opcoes3);
		
		Cardapio c3 = new Cardapio();
		c3.setId(3L);
		c3.setDescricao("Quarta-Feira");
		c3.adicionaGrupo(opcoes);
		c3.adicionaGrupo(opcoes2);
		c3.adicionaGrupo(opcoes3);
		
		return Arrays.asList(c1, c2, c3);
		
	}

	public void persist(Marmitaria m) {
		Log.i(TAG, "persistingo marmitaria " + m.getId());
	}
}
