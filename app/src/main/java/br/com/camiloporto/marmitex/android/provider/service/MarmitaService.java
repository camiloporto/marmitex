package br.com.camiloporto.marmitex.android.provider.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import android.content.Context;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;
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
	public List<Cardapio> list() {
		
		GrupoAlimentar opcoes = new GrupoAlimentar();
		opcoes.setDescricao("carnes");

		
		GrupoAlimentar opcoes2 = new GrupoAlimentar();
		opcoes2.setDescricao("acompanhamentos");

		
		GrupoAlimentar opcoes3 = new GrupoAlimentar();
		opcoes3.setDescricao("saladas");

		
		ItemCardapio i1 = new ItemCardapio();
		i1.setDescricao("Filet de Frango");

		
		ItemCardapio i2 = new ItemCardapio();
		i2.setDescricao("Feijao preto");

		
		ItemCardapio i3 = new ItemCardapio();
		i3.setDescricao("Hortalica");

		
		opcoes.adicioneOpcao(i1);
		opcoes2.adicioneOpcao(i2);
		opcoes3.adicioneOpcao(i3);
		
		
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
		Marmitaria m = new Marmitaria(nome, endereco, fone);
		Marmitaria saved = marmitariaRepository.save(m);
		return saved;
	}

	public Marmitaria find(String id) {
		return marmitariaRepository.find(id);
	}
}
