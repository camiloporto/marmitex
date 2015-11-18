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
	private Button cardapios;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		/*
		 * FIXME
		 * Iniciar essa atividade fazendo os seguinte:
		 * 1. Autenticar usuario (tentar ver se credenciais estao persistidas ou solicitar login)
		 * 2. Verificar se usuario autenticado ja criou uma Marmitaria
		 * 3. Caso seja 1o acesso, enviar para uma Tela que o auxile a criar a marmitaria,
		 * incluindo a criacao de cardapios
		 * 4. Caso já tenha uma marmitaria criada, apresentar opção de Editar cardapios/marmitaria
		 */

		//FIXME tornar essa leitura assincrona. Mostar na UI uma Spin enquanto a leitura eh feita.
		marmitariaService = MarmitaService.getInstance(MarmitexActivity.this);
		marmitaria = marmitariaService.readMarmitaria();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marmitex);
		Button novaMarmitaria = (Button) findViewById(R.id.btnNovaMarmitaria);
		cardapios = (Button) findViewById(R.id.btnCardapios);
		cardapios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, CardapioActivity.class);
				//FIXME colocar essa string numa constante
				i.putExtra(CardapioListFragment.ARG_NAME_MARMITARIA, marmitaria);
				startActivityForResult(i, 0);
			}
		});
		updateCardapiosButtonStatus();
		
		novaMarmitaria.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, NovaMarmitariaActivity.class);
				startActivity(i);
			}
		});
	}

	private void updateCardapiosButtonStatus() {
		cardapios.setEnabled(marmitaria != null);
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
