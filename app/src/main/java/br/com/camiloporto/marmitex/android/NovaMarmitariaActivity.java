package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.tasks.NovaMarmitariaTask;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class NovaMarmitariaActivity extends Activity implements NovaMarmitariaFragment.OnNovaMarmitariaCreated {
	
	private static final String TAG = "NovaMarmitariaActivity";
	private Marmitaria mNovaMarmitaria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novamarmitaria);
		FragmentManager fm = getFragmentManager();
		Fragment f = fm.findFragmentById(R.id.fragmentContainer);
		if(f == null) {
			f = new NovaMarmitariaFragment();
			fm.beginTransaction()
				.add(R.id.fragmentContainer, f)
				.commit();
		}
	}

	@Override
	public void novaMarmitariaCreated(Marmitaria m) {
		this.mNovaMarmitaria = m;
		new NovaMarmitariaTask().execute(this);
	}
	
	public Marmitaria getNovaMarmitaria() {
		return mNovaMarmitaria;
	}
	
	public void setNovaMarmitariaActionResponse(String result) {
		Log.i(TAG, "resposta da acao NovaMarmitaria: " + result);
	}

}
