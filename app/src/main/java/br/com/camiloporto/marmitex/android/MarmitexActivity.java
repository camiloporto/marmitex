package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;

public class MarmitexActivity extends Activity {

	private static final String TAG = "MarmitexActivity";

	private Marmitaria marmitaria;
	private MarmitaService marmitariaService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		marmitariaService = MarmitaService.getInstance(MarmitexActivity.this);
		marmitaria = marmitariaService.readMarmitaria();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marmitex);
		Button novaMarmitaria = (Button) findViewById(R.id.btnNovaMarmitaria);
		Button cardapios = (Button) findViewById(R.id.btnCardapios);
		cardapios.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, CardapioActivity.class);
				//FIXME colocar essa string numa constante
				i.putExtra(CardapioListFragment.ARG_NAME_MARMITARIA, marmitaria);
				//FIXME iniicar essa Activity for Result. NO retorno, persistir toda a Marmitaria com seus Cardapios (serializar Marmitaria e mandar para servidor/disco local/etc.
				startActivityForResult(i, 0);
			}
		});
		
		novaMarmitaria.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, NovaMarmitariaActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 0) {
			if(resultCode == Activity.RESULT_OK) {
				Marmitaria m = (Marmitaria) data.getSerializableExtra(CardapioListFragment.ARG_NAME_MARMITARIA);
				marmitariaService.persist(m);
			}
		}
	}
}
