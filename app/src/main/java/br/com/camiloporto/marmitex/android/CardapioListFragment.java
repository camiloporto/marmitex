package br.com.camiloporto.marmitex.android;

import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class CardapioListFragment extends ListFragment {

	public interface CardapioListFragmentCallbacks {

	}
	
	private static final String TAG = "CardapioListFragment";

	private CardapioListFragmentCallbacks mCallbacks;


	private Marmitaria marmitaria;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (CardapioListFragmentCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Class" + activity.getClass().getName() + " must implement " +
					CardapioListFragmentCallbacks.class.getName());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		updateUI();

		return view;
	}

	public void updateUI() {
		if(marmitaria != null) {
			getActivity().setTitle(marmitaria.getNome());
			setListAdapter(new CardapioListAdapter(marmitaria));
		}
	}

	public void setMarmitaria(Marmitaria marmitaria) {
		this.marmitaria = marmitaria;
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
			inputDescricao.setText(item.getNome());
			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						getItem(position).setNome(newValue.toString());
					}
				}
			});

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
					CardapioListFragment.this.updateUI();
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
