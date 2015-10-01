package br.com.camiloporto.marmitex.android;

import br.com.camiloporto.marmitex.android.provider.service.CardapioService;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CardapioActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio);
		Button novoCardapio = (Button) findViewById(R.id.cardapio_novo);
		Button swap = (Button) findViewById(R.id.cardapio_swapFragment);
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
		
		swap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fm = getFragmentManager();
				Fragment f = fm.findFragmentById(R.id.cardapio_fragmentContainer);
				Fragment f2 = null;
				if(f != null) {
					f2 = GrupoOpcaoListFragment.newInstance(CardapioService.getInstance(CardapioActivity.this).list().get(0));
					fm.beginTransaction()
						.replace(R.id.cardapio_fragmentContainer, f2)
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
						.commit();
				}
				
				if(f == null) {
					f = new CardapioListFragment();
					fm.beginTransaction()
						.add(R.id.cardapio_fragmentContainer, f)
						.commit();
				}
			}
		});
		
		FragmentManager fm = getFragmentManager();
		Fragment f = fm.findFragmentById(R.id.cardapio_fragmentContainer);
		if(f == null) {
			f = new CardapioListFragment();
			fm.beginTransaction()
				.add(R.id.cardapio_fragmentContainer, f)
				.commit();
		}
	}

}
