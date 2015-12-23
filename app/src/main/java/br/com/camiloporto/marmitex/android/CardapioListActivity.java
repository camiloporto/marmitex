package br.com.camiloporto.marmitex.android;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class CardapioListActivity extends AbstractMarmitexActivity implements CardapioListFragment.CardapioListFragmentCallbacks {

	private CardapioListFragment cardapioListFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_list);

		FragmentManager fm = getFragmentManager();
		cardapioListFragment = (CardapioListFragment) fm.findFragmentById(R.id.cardapio_list_fragmentContainer);
		if(cardapioListFragment == null) {

			cardapioListFragment = new CardapioListFragment();
			fm.beginTransaction()
				.add(R.id.cardapio_list_fragmentContainer, cardapioListFragment)
				.commit();

			Marmitaria active = getActiveMarmitaria();
			cardapioListFragment.setMarmitaria(active);
		}
	}

	@Override
	public void onBackPressed() {
		getMarmitexApplication().updateActiveMarmitaria(this);
		super.onBackPressed();
	}

	@Override
	public void onCardapioCreated(String nomeCardapio) {
		getActiveMarmitaria().createCardapio(nomeCardapio);
		cardapioListFragment.updateUI();
	}

	@Override
	public void onCardapioRequestForEdition(Cardapio c) {
		Intent i = new Intent(this, GrupoOpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, c.getId());
		startActivity(i);
	}

	@Override
	public void onCardapioDeleted(Cardapio c) {
		Marmitaria active = getActiveMarmitaria();
		active.deleteCardapio(c);
		cardapioListFragment.updateUI();
	}


}
