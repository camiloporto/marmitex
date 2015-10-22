package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardapioActivity extends Activity {

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
				// TODO Trocar forma como os grupos de opcoes sao editados.
				// nao seria interessante criar um fragment para listar os grupos
				// e permitir alterar/isnerir nome do cardaipio?
				// essa tela seria o "modo edicao" de um cardapio
				// renomera grupoOpcaoCardapioActiviti para:
				//1 ou um CardapioEditFragment
				//2. ou um CardapioEditActivity (primeira opcao mais adequada) 
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

}
