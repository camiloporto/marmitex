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

import java.util.ArrayList;

import br.com.camiloporto.marmitex.android.model.MenuCategory;
import br.com.camiloporto.marmitex.android.model.MenuOption;

public class OpcaoCardapioListFragment extends ListFragment {

	private static final String TAG = OpcaoCardapioListFragment.class.getName();

	public interface OpcaoCardapioListFragmentCallbacks {

		void onNewItemCreated(String descricao);

		void onOpcaoCardapioDeleted(MenuOption opcaoCardapio);
	}
	
	private MenuCategory grupoOpcao;
	private OpcaoCardapioListFragmentCallbacks mCallback;
	
	private EditText inputItem;
	private EditText inputCategoryName;
	private EditText inputCategoryComments;
	private Button addButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallback = (OpcaoCardapioListFragmentCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Class" + activity.getClass().getName() + " must implement " +
					OpcaoCardapioListFragmentCallbacks.class.getName());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_cardapio_opcao, null);
		
		inputItem = (EditText) v.findViewById(R.id.cardapio_opcoes_novo_item_input);
		inputCategoryComments = (EditText) v.findViewById(R.id.categoria_comments_input);
		inputCategoryName = (EditText) v.findViewById(R.id.categoria_nome_input);
		addButton = (Button) v.findViewById(R.id.cardapio_opcoes_add);
		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				newItemAdded(inputItem.getText().toString());
				inputItem.setText(null);
			}

		});

		updateUI();
		
		return v;
	}

	public void updateUI() {
		if(grupoOpcao != null) {
			getActivity().setTitle(grupoOpcao.getName());
			inputCategoryName.setText(grupoOpcao.getName());
			inputCategoryName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						grupoOpcao.setName(newValue.toString());
					}
				}
			});

			inputCategoryComments.setText(grupoOpcao.getComments());
			inputCategoryComments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						grupoOpcao.setComments(newValue.toString());
					}
				}
			});


			setListAdapter(new OpcaoListAdapter(grupoOpcao));
		}
	}
	
	private void newItemAdded(String itemDescricao) {
		mCallback.onNewItemCreated(itemDescricao);
	}
	
	private class OpcaoListAdapter extends ArrayAdapter<MenuOption> {


		public OpcaoListAdapter(MenuCategory grupo) {
			super(getActivity(), 0, new ArrayList<MenuOption>(grupo.getOptions()));
		}
		
		
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_cardapio_opcao, null);
			}

			MenuOption itemClicked = getItem(position);
			
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_opcao_item_list_descricao_input);
			inputDescricao.setText(itemClicked.getName());

			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_opcao_item_list_removeButton);
			
			deleteButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					MenuOption itemClicked = getItem(position);
					mCallback.onOpcaoCardapioDeleted(itemClicked);
				}

			});

			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						MenuOption item = getItem(position);
						item.setName(newValue.toString());
					}
				}
			});
			

			return convertView;
		}

	}

	public void setGrupoOpcoes(MenuCategory grupoOpcao) {
		this.grupoOpcao = grupoOpcao;
	}

}
