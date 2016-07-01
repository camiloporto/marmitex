package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.camiloporto.marmitex.android.model.Menu;
import br.com.camiloporto.marmitex.android.model.Seller;

public class CardapioListFragment extends ListFragment {

	public interface CardapioListFragmentCallbacks {
		void onCardapioCreated(String nomeCardapio);
		void onCardapioDeleted(Menu cardapio);
		void onCardapioRequestForEdition(Menu c);
	}
	
	private static final String TAG = "CardapioListFragment";

	private CardapioListFragmentCallbacks mCallbacks;

	private EditText inputItem;
	private Button addButton;


	private Seller marmitaria;

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
		View v = inflater.inflate(R.layout.fragment_cardapiolist, null);

		inputItem = (EditText) v.findViewById(R.id.cardapio_list_novo_item_input);
		addButton = (Button) v.findViewById(R.id.cardapio_list_add);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newCardapioCreated(inputItem.getText().toString());
				inputItem.setText(null);
			}

		});

		updateUI();

		return v;
	}

	private void newCardapioCreated(String nomeCardapio) {
		mCallbacks.onCardapioCreated(nomeCardapio);
	}

	public void updateUI() {
		if(marmitaria != null) {
			getActivity().setTitle(marmitaria.getName());
			setListAdapter(new CardapioListAdapter(marmitaria));
		}
	}

	public void setMarmitaria(Seller marmitaria) {
		this.marmitaria = marmitaria;
	}


	private class CardapioListAdapter extends ArrayAdapter<Menu> {
		
		public CardapioListAdapter(Seller marmitaria) {
			super(getActivity(), R.layout.list_item_cardapio, marmitaria.getMenus());
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio, null);
			}

			Menu item = getItem(position);
			final TextView inputDescricao = (TextView) convertView
					.findViewById(R.id.cardapio_item_list_descricao_input);
			inputDescricao.setText(item.getName());

			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_item_list_removeButton);

			final Button editButton = (Button) convertView
					.findViewById(R.id.cardapio_item_list_editButton);

			deleteButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Menu item = getItem(position);
					mCallbacks.onCardapioDeleted(item);
				}

			});


			editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Menu item = getItem(position);
					mCallbacks.onCardapioRequestForEdition(item);
				}
			});

			return convertView;
		}
	}

}
