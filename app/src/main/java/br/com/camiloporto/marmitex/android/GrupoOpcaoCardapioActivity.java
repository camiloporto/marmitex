package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.GrupoOpcaoListFragment.GrupoOpcaoListFragmentListener;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;

public class GrupoOpcaoCardapioActivity extends AbstractMarmitexActivity implements GrupoOpcaoListFragmentListener {

	private Cardapio cardapio;
	private GrupoOpcaoListFragment grupoOpcaoListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_grupo_opcao);
		FragmentManager fm = getFragmentManager();
		grupoOpcaoListFragment = (GrupoOpcaoListFragment) fm.findFragmentById(R.id.cardapio_novo_fragmentContainer);

		if (grupoOpcaoListFragment == null) {
			String idCardapio = getIntent().getStringExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			cardapio = getActiveMarmitaria().findCardapioPeloId(idCardapio);
			grupoOpcaoListFragment = GrupoOpcaoListFragment.newInstance(cardapio);
			fm.beginTransaction().add(R.id.cardapio_novo_fragmentContainer, grupoOpcaoListFragment)
					.commit();
		}
	}

//	@Override
//	public void onBackPressed() {
//		Intent i = new Intent();
//		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, this.cardapio);
//		setResult(Activity.RESULT_OK, i);
//		super.onBackPressed();
//	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if(requestCode == 0) {
//			GrupoAlimentar grupo = (GrupoAlimentar) data.getExtras().get(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO);
//			cardapio.adicionaGrupo(grupo);
//			grupoOpcaoListFragment.notifyDataSetChanged();
//		}
//	}

	@Override
	public void onEditGroupItemsRequested(GrupoAlimentar grupo) {
		Intent i = new Intent(this, OpcaoCardapioActivity.class);
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, grupo);
		startActivityForResult(i, 0);
	}

	@Override
	public void onNewGroupAdded(String descricao) {
		cardapio.adicioneGrupoDeOpcoes(descricao);
	}

	@Override
	public void onItemDeleted(GrupoAlimentar grupo) {
		cardapio.removeGrupo(grupo);
	}

}
