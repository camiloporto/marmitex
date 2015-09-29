package br.com.camiloporto.marmitex.android;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.provider.service.CardapioService;

public class CardapioListFragment extends ListFragment {
	
	private static final String TAG = "CardapioListFragment";
	private List<Cardapio> cardapios;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cardapios = CardapioService.getInstance().list();
		setListAdapter(new CardapioListAdapter(cardapios));
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cardapio itemClicked = (Cardapio) getListAdapter().getItem(position);
		Intent i = new Intent(getActivity(), GrupoOpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, itemClicked);
		startActivity(i);
	}
	
	private class CardapioListAdapter extends ArrayAdapter<Cardapio> {
		
		public CardapioListAdapter(List<Cardapio> cardapios) {
			super(getActivity(), android.R.layout.simple_list_item_1, cardapios);
		}
		
	}

}
