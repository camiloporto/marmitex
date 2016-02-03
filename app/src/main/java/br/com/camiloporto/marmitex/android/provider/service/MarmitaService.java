package br.com.camiloporto.marmitex.android.provider.service;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.repository.MarmitariaRepository;

public class MarmitaService {
	
	private static final String TAG = "MarmitaService";


	private MarmitariaRepository marmitariaRepository;


	public MarmitaService(MarmitariaRepository marmitariaRepository) {

		this.marmitariaRepository = marmitariaRepository;
	}


	public void save(Marmitaria m) {
		marmitariaRepository.save(m);
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
