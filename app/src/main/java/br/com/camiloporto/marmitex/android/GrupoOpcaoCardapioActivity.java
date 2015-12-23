package br.com.camiloporto.marmitex.android;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;

public class GrupoOpcaoCardapioActivity extends AbstractMarmitexActivity implements GrupoOpcaoListFragment.GrupoOpcaoListFragmentCallbacks {

	private Cardapio cardapio;
	private GrupoOpcaoListFragment grupoOpcaoListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_grupo_opcao);
		FragmentManager fm = getFragmentManager();
		grupoOpcaoListFragment = (GrupoOpcaoListFragment) fm.findFragmentById(R.id.cardapio_novo_fragmentContainer);

		if (grupoOpcaoListFragment == null) {
			//// FIXME: 22/12/15 Refatorar onde fica essas constatnes. Unificar local
			String idCardapio = getIntent().getStringExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			cardapio = getActiveMarmitaria().findCardapioPeloId(idCardapio);
			grupoOpcaoListFragment = new GrupoOpcaoListFragment();
			fm.beginTransaction().add(R.id.cardapio_novo_fragmentContainer, grupoOpcaoListFragment)
					.commit();
			grupoOpcaoListFragment.setCardapio(cardapio);
		}

	}

	@Override
	public void onGrupoAlimentarDeleted(GrupoAlimentar grupoAlimentar) {
		cardapio.removeGrupo(grupoAlimentar);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarCreated(String descricao) {
		cardapio.adicioneGrupoDeOpcoes(descricao);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarRequestForEdition(GrupoAlimentar item) {
		Intent i = new Intent(this, OpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, cardapio.getId());
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, item.getId());
		startActivity(i);
	}
}
