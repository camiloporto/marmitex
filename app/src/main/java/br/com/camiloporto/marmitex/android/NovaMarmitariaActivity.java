package br.com.camiloporto.marmitex.android;

import android.app.FragmentManager;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.NovaMarmitariaFragment.NovaMarmitariaFragmentCallbacks;
import br.com.camiloporto.marmitex.android.model.Seller;

public class NovaMarmitariaActivity extends AbstractMarmitexActivity implements NovaMarmitariaFragmentCallbacks {
	
	private static final String TAG = "NovaMarmitariaActivity";
	private NovaMarmitariaFragment novaMarmitariaFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novamarmitaria);
		FragmentManager fm = getFragmentManager();
		novaMarmitariaFragment = (NovaMarmitariaFragment) fm.findFragmentById(R.id.fragmentContainer);
		if(novaMarmitariaFragment == null) {
			novaMarmitariaFragment = new NovaMarmitariaFragment();
			fm.beginTransaction()
				.add(R.id.fragmentContainer, novaMarmitariaFragment)
				.commit();
		}
		Seller active = getActiveMarmitaria();
		novaMarmitariaFragment.setMarmitaria(active);

	}

	@Override
	public void saveButtonClicked() {
		//FIXME ajustar logica ao novo backend (estrutura do objeto mudou..)
		String nome = novaMarmitariaFragment.getNome().getText().toString();
		String fone = novaMarmitariaFragment.getTelefone().getText().toString();
		String endereco = novaMarmitariaFragment.getEndereco().getText().toString();

		MarmitexApplication application = (MarmitexApplication) getApplication();
		Seller active = getActiveMarmitaria();
		if(active == null) {
			application.saveSeller(nome, fone, endereco, this);
		} else {
			active.setName(nome);
			active.setPhone(fone);
			active.setAddress(endereco);
			//FIXME atualizar demais dados
			application.saveActiveSeller(this);
		}


	}

}
