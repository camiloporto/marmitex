package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.NovaMarmitariaFragment.NovaMarmitariaFragmentCallbacks;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class NovaMarmitariaActivity extends Activity implements NovaMarmitariaFragmentCallbacks, MarmitexApplication.OnMarmitariaCreatedCallback, MarmitexApplication.OnMarmitariaUpdatedCallback {
	
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
		MarmitexApplication application = (MarmitexApplication) getApplication();
		Marmitaria active = application.getActiveMarmitaria();
		novaMarmitariaFragment.setMarmitaria(active);

	}

	@Override
	public void saveButtonClicked() {
		String nome = novaMarmitariaFragment.getNome().getText().toString();
		String fone = novaMarmitariaFragment.getTelefone().getText().toString();
		String endereco = novaMarmitariaFragment.getEndereco().getText().toString();

		MarmitexApplication application = (MarmitexApplication) getApplication();
		Marmitaria active = application.getActiveMarmitaria();
		if(active == null) {
			application.createMarmitaria(nome, fone, endereco, this);
		} else {
			active.setNome(nome);
			active.setTelefone(fone);
			active.setEndereco(endereco);
			//FIXME atualizar demais dados
			application.updateActiveMarmitaria(this);
		}


	}

	public void setNovaMarmitariaActionResponse(String result) {
		Log.i(TAG, "resposta da acao NovaMarmitaria: " + result);
	}

	@Override
	public void onMarmitariaCreated() {
		Toast.makeText(this, "Marmitaria Criada", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onMarmitariaUpdated() {
		Toast.makeText(this, "Marmitaria Atualizada", Toast.LENGTH_LONG)
				.show();
	}
}
