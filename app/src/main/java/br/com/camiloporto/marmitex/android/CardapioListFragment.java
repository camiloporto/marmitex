package br.com.camiloporto.marmitex.android;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class CardapioListFragment extends ListFragment {
	
	private static final String TAG = "CardapioListFragment";
	public static final String ARG_NAME_MARMITARIA = "br.com.camiloporto.marmitex.android.CardapioListFragment.MARMITARIA";
	private Marmitaria marmitaria;

	@Deprecated
	private List<Cardapio> cardapios;

	public static CardapioListFragment newFragment(Marmitaria marmitaria) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_NAME_MARMITARIA, marmitaria);
		CardapioListFragment cardapioListFragment = new CardapioListFragment();
		cardapioListFragment.setArguments(args);
		return cardapioListFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		marmitaria = (Marmitaria) getArguments().getSerializable(ARG_NAME_MARMITARIA);
		if(marmitaria != null) {
			getActivity().setTitle(marmitaria.getNome());
			setListAdapter(new CardapioListAdapter(marmitaria));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Cardapio itemClicked = (Cardapio) getListAdapter().getItem(position);
		Intent i = new Intent(getActivity(), GrupoOpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, itemClicked);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 0) {
			Cardapio cardapio = (Cardapio) data.getExtras().get(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			//FIXME colocar metodos de interacao entre Activities nas Activities (tira esse daqui e colocar am CardapioActivity)
			marmitaria.saveCardapio(cardapio);
			CardapioListAdapter listAdapter = (CardapioListAdapter) getListAdapter();
			listAdapter.clear();
			listAdapter.addAll(marmitaria.getCardapios());
		}
	}

	private class CardapioListAdapter extends ArrayAdapter<Cardapio> {
		
		public CardapioListAdapter(Marmitaria marmitaria) {
			super(getActivity(), android.R.layout.simple_list_item_1, marmitaria.getCardapios());
		}
		
	}

}
