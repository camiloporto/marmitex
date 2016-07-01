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
import br.com.camiloporto.marmitex.android.model.MenuCategory;

public class GrupoOpcaoListFragment extends ListFragment {

	public interface GrupoOpcaoListFragmentCallbacks {

		void onGrupoAlimentarDeleted(MenuCategory grupoAlimentar);

		void onGrupoAlimentarCreated(String descricao);

		void onGrupoAlimentarRequestForEdition(MenuCategory item);
	}

	private static final String TAG = GrupoOpcaoListFragment.class.getName();

	private GrupoOpcaoListFragmentCallbacks mCallbacks;

	private Menu cardapio;
	private EditText inputItem;
	private EditText menuNameInput;
	private EditText menuCommentsInput;
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
		menuNameInput = (EditText) v.findViewById(R.id.cardapio_nome_input);
		menuCommentsInput = (EditText) v.findViewById(R.id.cardapio_comments_input);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
				public void onClick (View v){
					newGroupAdded(inputItem.getText().toString());
				inputItem.setText(null);
			}

		});

		updateUI();

		return v;
	}

	public void updateUI() {
		if(cardapio != null) {
			getActivity().setTitle(cardapio.getName());
			menuNameInput.setText(cardapio.getName());
			menuNameInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						cardapio.setName(newValue.toString());
					}
				}
			});

			menuCommentsInput.setText(cardapio.getComments());
			menuCommentsInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						cardapio.setComments(newValue.toString());
					}
				}
			});

			setListAdapter(new GrupoOpcaoListAdapter(cardapio));
		}
	}

	private void newGroupAdded(String descricao) {
		mCallbacks.onGrupoAlimentarCreated(descricao);
	}

	public void setCardapio(Menu cardapio) {
		this.cardapio = cardapio;
	}

	public Menu getCardapio() {
		return cardapio;
	}

	private class GrupoOpcaoListAdapter extends ArrayAdapter<MenuCategory> {

		public GrupoOpcaoListAdapter(Menu cardapio) {

			super(getActivity(), 0, cardapio.getCategories());

		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio_grupo_opcao, null);
			}
			
			MenuCategory item = getItem(position);
			TextView inputDescricao = (TextView) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_descricao_input);
			inputDescricao.setText(item.getName());
			//FIXME passar a atualizacao deste campo para a tela de edicao de Categorias
//			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//				@Override
//				public void onFocusChange(View view, boolean hasFocus) {
//					if(!hasFocus) {
//						Editable newValue = ((EditText) view).getText();
//						MenuCategory gItem = getItem(position);
//						gItem.setName(newValue.toString());
//					}
//				}
//			});


			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_removeButton);

			final Button editButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_editButton);

			deleteButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					MenuCategory item = getItem(position);
					mCallbacks.onGrupoAlimentarDeleted(item);
				}

			});

			editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					MenuCategory item = getItem(position);
					mCallbacks.onGrupoAlimentarRequestForEdition(item);

				}
			});
			
			return convertView;
		}
		
	}
	
	public interface GrupoOpcaoListFragmentListener {
		public void onEditGroupItemsRequested(MenuCategory grupo);

		void onNewGroupAdded(String descricao);
//		public void onItemUpdated(ItemCardapio item);
		public void onItemDeleted(MenuCategory grupo);
//		public void onItemGroupUpdated(GrupoItems groupItems);
	}
	

}
