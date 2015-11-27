package br.com.camiloporto.marmitex.android.provider.service;

import java.net.MalformedURLException;
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

	public void save(Marmitaria m) {
		new MarmitariaJSONHelper(context)
				.persistMarmitaria(m);
	}

	//FIXME ver como recuperar marmitaria cadastrada no dispositivo. recuperar via REST ou Localmente
	public Marmitaria readMarmitaria() {

		//FIXME recuperar usuario logado
//		String userId = getAuthenticatedUserId();
//		Marmitaria m = new MarmitariaJSONHelper(context)
//				.readMarmitaria(userId);
		String idMarmitaria = getAuthenticatedUserId();
		Marmitaria m = new MarmitariaJSONHelper(context)
				.getMarmitaria(idMarmitaria);

		return m;
	}
	
	public List<Cardapio> list() {
		
		GrupoItems opcoes = new GrupoItems();
		opcoes.setDescricao("carnes");

		
		GrupoItems opcoes2 = new GrupoItems();
		opcoes2.setDescricao("acompanhamentos");

		
		GrupoItems opcoes3 = new GrupoItems();
		opcoes3.setDescricao("saladas");

		
		ItemCardapio i1 = new ItemCardapio();
		i1.setDescricao("Filet de Frango");

		
		ItemCardapio i2 = new ItemCardapio();
		i2.setDescricao("Feijao preto");

		
		ItemCardapio i3 = new ItemCardapio();
		i3.setDescricao("Hortalica");

		
		opcoes.newItem(i1);
		opcoes2.newItem(i2);
		opcoes3.newItem(i3);
		
		
		//FIXME invocar via rest o servidor e construir cardapios
		Cardapio c1 = new Cardapio();

		c1.setDescricao("Segunda-Feira");
		c1.adicionaGrupo(opcoes);
		c1.adicionaGrupo(opcoes2);
		c1.adicionaGrupo(opcoes3);
		
		Cardapio c2 = new Cardapio();

		c2.setDescricao("Terça-Feira");
		c2.adicionaGrupo(opcoes);
		c2.adicionaGrupo(opcoes2);
		c2.adicionaGrupo(opcoes3);
		
		Cardapio c3 = new Cardapio();

		c3.setDescricao("Quarta-Feira");
		c3.adicionaGrupo(opcoes);
		c3.adicionaGrupo(opcoes2);
		c3.adicionaGrupo(opcoes3);
		
		return Arrays.asList(c1, c2, c3);
		
	}

	public Marmitaria persist(Marmitaria m) {
		String idMarmitaria = getAuthenticatedUserId();
		try {

			CouldantResponse couldantResponse = new MarmitariaJSONHelper(context)
					.persistRemote(m);
			m.setRevision(couldantResponse.getRevId());
			return m;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}

	}

	public String getAuthenticatedUserId() {
		return "61f9fdea-0690-4b9c-a541-985a2a50be69";
	}
}
