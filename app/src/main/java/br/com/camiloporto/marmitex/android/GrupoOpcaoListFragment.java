package br.com.camiloporto.marmitex.android;

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
import android.widget.LinearLayout;

import java.util.List;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;

public class GrupoOpcaoListFragment extends ListFragment {

	public interface GrupoOpcaoListFragmentCallbacks {

		void onGrupoAlimentarDeleted(GrupoAlimentar grupoAlimentar);

		void onGrupoAlimentarCreated(String descricao);

		void onGrupoAlimentarRequestForEdition(GrupoAlimentar item);
	}
	
	public static final String ARG_NAME_CARDAPIO = "br.com.camiloporto.marmitex.android.NovoCardapioFragment.CARDAPIO";
	private static final String TAG = GrupoOpcaoListFragment.class.getName();

	private GrupoOpcaoListFragmentCallbacks mCallbacks;

	private Cardapio cardapio;
	private EditText inputItem;
	private Button addButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (GrupoOpcaoListFragmentCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Class" + activity.getClass().getName() + " must implement " +
					GrupoOpcaoListFragmentCallbacks.class.getName());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_cardapio_grupo_opcao, null);

		inputItem = (EditText) v.findViewById(R.id.cardapio_grupo_opcoes_novo_item_input);
		addButton = (Button) v.findViewById(R.id.cardapio_grupo_opcoes_add);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newGroupAdded(inputItem.getText().toString());
				inputItem.setText(null);
			}

		});

		updateUI();

		return v;
	}

	public void updateUI() {
		if(cardapio != null) {
			getActivity().setTitle(cardapio.getNome());
			setListAdapter(new GrupoOpcaoListAdapter(cardapio));
		}
	}

	private void newGroupAdded(String descricao) {
		mCallbacks.onGrupoAlimentarCreated(descricao);
	}

	public void setCardapio(Cardapio cardapio) {
		this.cardapio = cardapio;
	}

	public Cardapio getCardapio() {
		return cardapio;
	}

	private class GrupoOpcaoListAdapter extends ArrayAdapter<GrupoAlimentar> {

		public GrupoOpcaoListAdapter(Cardapio cardapio) {

			super(getActivity(), 0, cardapio.getGruposDeOpcoes());

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio_grupo_opcao, null);
			}
			
			GrupoAlimentar item = getItem(position);
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_descricao_input);
			inputDescricao.setText(item.getNome());
			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if(!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						GrupoAlimentar gItem = getItem(position);
						gItem.setNome(newValue.toString());
					}
				}
			});


			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_removeButton);

			final Button editButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_editButton);

			deleteButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					GrupoAlimentar item = getItem(position);
					mCallbacks.onGrupoAlimentarDeleted(item);
				}

			});

			editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					GrupoAlimentar item = getItem(position);
					mCallbacks.onGrupoAlimentarRequestForEdition(item);

				}
			});
			
			return convertView;
		}
		
	}
	
	public interface GrupoOpcaoListFragmentListener {
		public void onEditGroupItemsRequested(GrupoAlimentar grupo);

		void onNewGroupAdded(String descricao);
//		public void onItemUpdated(ItemCardapio item);
		public void onItemDeleted(GrupoAlimentar grupo);
//		public void onItemGroupUpdated(GrupoItems groupItems);
	}
	

}
