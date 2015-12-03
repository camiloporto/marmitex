package br.com.camiloporto.marmitex.android.provider.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.OpcaoCardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.repository.MarmitariaRepository;

public class MarmitaService {
	
	private static final String TAG = "MarmitaService";

	@Deprecated
	private static MarmitaService marmitaService;

	@Deprecated
	private Context context;

	@Deprecated
	private RestService restService;
	@Deprecated
	private URL serverEndPoint;

	private MarmitariaRepository marmitariaRepository;

	@Deprecated
	public MarmitaService(Context context, RestService restService) {
		super();
		this.context = context;
		this.restService = restService;
	}

	public MarmitaService(MarmitariaRepository marmitariaRepository) {

		this.marmitariaRepository = marmitariaRepository;
	}

	@Deprecated
	public static MarmitaService getInstance(Context context) {
		if(marmitaService == null) {
			marmitaService = new MarmitaService(
					context.getApplicationContext(),
					RestService.getInstance());
		}
		return marmitaService;
	}

	public void save(Marmitaria m) {
		marmitariaRepository.save(m);
	}

	@Deprecated
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

	@Deprecated
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

	public Marmitaria create(String nome, String fone, String endereco) {
		Marmitaria m = new Marmitaria(nome, fone, endereco);
		Marmitaria saved = marmitariaRepository.save(m);
		return saved;
	}

	public Marmitaria find(String id) {
		return marmitariaRepository.find(id);
	}
}
