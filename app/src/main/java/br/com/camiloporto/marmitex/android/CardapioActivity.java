package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardapioActivity extends Activity implements CardapioListFragment.CardapioListFragmentListener {

	private CardapioListFragment cardapioListFragment;
	private Marmitaria marmitaria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio);
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
			marmitaria = (Marmitaria) getIntent().getSerializableExtra(CardapioListFragment.ARG_NAME_MARMITARIA);
			cardapioListFragment = CardapioListFragment.newFragment(marmitaria);
			fm.beginTransaction()
				.add(R.id.cardapio_fragmentContainer, cardapioListFragment)
				.commit();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 0) {
			Cardapio cardapio = (Cardapio) data.getExtras().get(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			marmitaria.saveCardapio(cardapio);
			cardapioListFragment.notifyDataSetChanged();
		}
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putExtra(CardapioListFragment.ARG_NAME_MARMITARIA, this.marmitaria);
		setResult(Activity.RESULT_OK, i);
		super.onBackPressed();
	}

	@Override
	public void onCardapioAdded(Cardapio c) {
		//FIXME implementar metodo
	}

	@Override
	public void onCardapioRequestForEdition(Cardapio c) {
		Intent i = new Intent(this, GrupoOpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, c);
		startActivityForResult(i, 0);
	}

	@Override
	public void onCardapioDeleted(Cardapio c) {
		marmitaria.deleteCardapio(c);
	}
}
