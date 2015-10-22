package br.com.camiloporto.marmitex.android;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
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


	public void notifyDataSetChanged() {
		CardapioListAdapter listAdapter = (CardapioListAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(marmitaria.getCardapios());
	}


	private class CardapioListAdapter extends ArrayAdapter<Cardapio> {
		
		public CardapioListAdapter(Marmitaria marmitaria) {
			super(getActivity(), R.layout.list_item_cardapio, marmitaria.getCardapios());
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio, null);
			}

			Cardapio item = getItem(position);
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_item_list_descricao_input);
			inputDescricao.setText(item.getDescricao());

			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_item_list_removeButton);

			final Button editButton = (Button) convertView
					.findViewById(R.id.cardapio_item_list_editButton);

			deleteButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Cardapio item = getItem(position);
					((CardapioListFragmentListener)getActivity()).onCardapioDeleted(item);
					remove(item);
					CardapioListFragment.this.notifyDataSetChanged();
					Log.i(TAG, "removendo cardapio " + item);
				}

			});

			editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Cardapio item = getItem(position);
					((CardapioListFragmentListener) getActivity()).onCardapioRequestForEdition(item);

				}
			});

			return convertView;
		}
	}

	interface CardapioListFragmentListener {
		public void onCardapioAdded(Cardapio c);
		public void onCardapioRequestForEdition(Cardapio c);
		public void onCardapioDeleted(Cardapio c);
	}

}
