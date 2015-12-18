package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class MarmitexActivity extends Activity implements MarmitexApplication.OnMarmitariaLoaded {

	private static final String TAG = "MarmitexActivity";

	private Button cardapios;
	private Button novaMarmitariaButton;

	private ProgressDialog carregando;

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



		super.onCreate(savedInstanceState);
		//Passar contexto do usuario logado??
		carregaMarmitariaExistente();

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

	private void carregaMarmitariaExistente() {
		MarmitexApplication application = (MarmitexApplication) getApplication();
		application.loadMarmitariaOfLoggedUser(this);
		carregando = ProgressDialog.show(this, "Carregando", "Carregando Marmitaria....");
	}

	private void updateUI() {
		MarmitexApplication application = (MarmitexApplication) getApplication();
		Marmitaria active = application.getActiveMarmitaria();

		if(active != null) {
			this.novaMarmitariaButton.setText(active.getNome());
		} else {
			this.novaMarmitariaButton.setText(R.string.marmitex_nova);
		}

		cardapios.setEnabled(application.getActiveMarmitaria() != null);
	}

	@Override
	public void onMarmitariaLoaded() {
		carregando.dismiss();
		updateUI();
	}

}
