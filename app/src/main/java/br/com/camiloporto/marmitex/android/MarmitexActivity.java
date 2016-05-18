package br.com.camiloporto.marmitex.android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.camiloporto.marmitex.android.model.Seller;

public class MarmitexActivity extends AbstractMarmitexActivity implements MarmitexApplication.OnMarmitariaLoaded {

	private static final String TAG = "MarmitexActivity";

	private Button cardapios;
	private Button novaMarmitariaButton;

	private ProgressDialog carregando;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		/*
		 * FIXME
		 * Iniciar essa atividade fazendo os seguinte:
		 * 2. Verificar se usuario autenticado ja criou uma Marmitaria
		 * 3. Caso seja 1o acesso, enviar para uma Tela que o auxile a criar a marmitaria,
		 * incluindo a criacao de cardapios
		 * 4. Caso já tenha uma marmitaria criada, apresentar opção de Editar cardapios/marmitaria
		 */



		super.onCreate(savedInstanceState);
		loadLoggedUserLunchBoxSeller();

		setContentView(R.layout.activity_marmitex);
		novaMarmitariaButton = (Button) findViewById(R.id.btnNovaMarmitaria);
		cardapios = (Button) findViewById(R.id.btnCardapios);
		cardapios.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, CardapioListActivity.class);
				startActivity(i);
			}
		});
		
		novaMarmitariaButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, NovaMarmitariaActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateUI();
	}

	private void loadLoggedUserLunchBoxSeller() {
		carregando = ProgressDialog.show(this, "Carregando", "Carregando Marmitaria....");
		MarmitexApplication application = (MarmitexApplication) getApplication();
		application.loadMarmitariaOfLoggedUser(this);
	}

	private void updateUI() {
		Seller active = getActiveMarmitaria();

		if(active != null) {
			this.novaMarmitariaButton.setText(active.getName());
		} else {
			this.novaMarmitariaButton.setText(R.string.marmitex_nova);
		}

		cardapios.setEnabled(active != null);
	}

	@Override
	public void onMarmitariaLoaded() {
		carregando.dismiss();
		updateUI();
	}

}
