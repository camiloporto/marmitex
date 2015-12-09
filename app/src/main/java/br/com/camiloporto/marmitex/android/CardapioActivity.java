package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;

public class CardapioActivity extends Activity implements CardapioListFragment.CardapioListFragmentCallbacks {

	private CardapioListFragment cardapioListFragment;
//	private Marmitaria marmitaria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio);

		//// FIXME: 08/12/15 Colocar esse Button como parte do Fragment?
		Button novoCardapio = (Button) findViewById(R.id.cardapio_novo);
		novoCardapio.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(CardapioActivity.this, GrupoOpcaoCardapioActivity.class);
				startActivity(i);
			}
		});

		FragmentManager fm = getFragmentManager();
		cardapioListFragment = (CardapioListFragment) fm.findFragmentById(R.id.cardapio_fragmentContainer);
		if(cardapioListFragment == null) {

			cardapioListFragment = new CardapioListFragment();
			fm.beginTransaction()
				.add(R.id.cardapio_fragmentContainer, cardapioListFragment)
				.commit();

			MarmitexApplication application = (MarmitexApplication) getApplication();
			Marmitaria active = application.getActiveMarmitaria();
			cardapioListFragment.setMarmitaria(active);
		}
	}

//	@Override
	public void onCardapioAdded(Cardapio c) {
		//FIXME implementar metodo
	}

//	@Override
	public void onCardapioRequestForEdition(Cardapio c) {
		Intent i = new Intent(this, GrupoOpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, c);
		startActivityForResult(i, 0);
	}

	@Override
	public void onCardapioDeleted(Cardapio c) {
		MarmitexApplication application = (MarmitexApplication) getApplication();
		Marmitaria active = application.getActiveMarmitaria();
		active.deleteCardapio(c);
		cardapioListFragment.updateUI();
	}
}
