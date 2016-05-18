package br.com.camiloporto.marmitex.android;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.model.Menu;
import br.com.camiloporto.marmitex.android.model.MenuCategory;

public class GrupoOpcaoCardapioActivity extends AbstractMarmitexActivity implements GrupoOpcaoListFragment.GrupoOpcaoListFragmentCallbacks {

	public static final String ARG_NAME_CARDAPIO = "br.com.camiloporto.marmitex.android.NovoCardapioFragment.CARDAPIO";

	private Menu cardapio;
	private GrupoOpcaoListFragment grupoOpcaoListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_grupo_opcao);
		FragmentManager fm = getFragmentManager();
		grupoOpcaoListFragment = (GrupoOpcaoListFragment) fm.findFragmentById(R.id.cardapio_novo_fragmentContainer);

		if (grupoOpcaoListFragment == null) {
			String idCardapio = getIntent().getStringExtra(ARG_NAME_CARDAPIO);
			cardapio = getActiveMarmitaria().findCardapioPeloId(idCardapio);
			grupoOpcaoListFragment = new GrupoOpcaoListFragment();
			fm.beginTransaction().add(R.id.cardapio_novo_fragmentContainer, grupoOpcaoListFragment)
					.commit();
			grupoOpcaoListFragment.setCardapio(cardapio);
		}

	}

	@Override
	public void onGrupoAlimentarDeleted(MenuCategory grupoAlimentar) {
		cardapio.removeGrupo(grupoAlimentar);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarCreated(String descricao) {
		cardapio.adicioneGrupoDeOpcoes(descricao);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarRequestForEdition(MenuCategory item) {
		Intent i = new Intent(this, OpcaoCardapioActivity.class);
		i.putExtra(OpcaoCardapioActivity.ARG_NAME_CARDAPIO, cardapio.getName());
		i.putExtra(OpcaoCardapioActivity.ARG_GRUPO_OPCAO, item.getName());
		startActivity(i);
	}
}
