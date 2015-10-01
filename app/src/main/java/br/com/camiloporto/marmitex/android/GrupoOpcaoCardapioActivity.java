package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import br.com.camiloporto.marmitex.android.GrupoOpcaoListFragment.GrupoOpcaoListFragmentListener;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;

public class GrupoOpcaoCardapioActivity extends Activity implements GrupoOpcaoListFragmentListener {

	private Cardapio cardapio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_grupo_opcao);
		FragmentManager fm = getFragmentManager();
		Fragment f = fm.findFragmentById(R.id.cardapio_novo_fragmentContainer);

		if (f == null) {
			cardapio = (Cardapio) getIntent().getSerializableExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			f = GrupoOpcaoListFragment.newInstance(cardapio);
			fm.beginTransaction().add(R.id.cardapio_novo_fragmentContainer, f)
					.commit();
		}

		// TODO tornar a Activity Cardapio uma listagem de cardapios com
		// possibilidade de editar cardapio "clicado"
		// TODO a tela de novo cardapio deve poder receber um cardapio para
		// edicao ou criar um novo.
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 0) {
//			if(resultCode == Activity.RESULT_OK) {
				GrupoItems grupo = (GrupoItems) data.getExtras().get(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO);
				cardapio.adicionaGrupo(grupo);

				Log.i("GrupoOpcaoCardapioActiv", grupo.toString());
//			}
		}
	}

	@Override
	public void onEditGroupItemsRequested(GrupoItems grupo) {
		Intent i = new Intent(this, OpcaoCardapioActivity.class);
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, grupo);
		startActivityForResult(i, 0);
	}

}
