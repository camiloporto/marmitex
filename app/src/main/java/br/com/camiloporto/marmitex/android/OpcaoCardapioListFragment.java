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

import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.OpcaoCardapio;

public class OpcaoCardapioListFragment extends ListFragment {

	private static final String TAG = OpcaoCardapioListFragment.class.getName();

	public interface OpcaoCardapioListFragmentCallbacks {

		void onNewItemCreated(String descricao);

		void onOpcaoCardapioDeleted(OpcaoCardapio opcaoCardapio);
	}
	
	private GrupoAlimentar grupoOpcao;
	private OpcaoCardapioListFragmentCallbacks mCallback;
	
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
			getActivity().setTitle(grupoOpcao.getNome());
			setListAdapter(new OpcaoListAdapter(grupoOpcao));
		}
	}
	
	private void newItemAdded(String itemDescricao) {
		mCallback.onNewItemCreated(itemDescricao);
	}
	
	private class OpcaoListAdapter extends ArrayAdapter<OpcaoCardapio> {


		public OpcaoListAdapter(GrupoAlimentar grupo) {
			super(getActivity(), 0, new ArrayList<OpcaoCardapio>(grupo.getOpcoes()));
		}
		
		
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_cardapio_opcao, null);
			}

			OpcaoCardapio itemClicked = getItem(position);
			
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_opcao_item_list_descricao_input);
			inputDescricao.setText(itemClicked.getNome());

			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_opcao_item_list_removeButton);
			
			deleteButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					OpcaoCardapio itemClicked = getItem(position);
					mCallback.onOpcaoCardapioDeleted(itemClicked);
				}

			});

			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						OpcaoCardapio item = getItem(position);
						item.setNome(newValue.toString());
					}
				}
			});
			

			return convertView;
		}

	}

	public void setGrupoOpcoes(GrupoAlimentar grupoOpcao) {
		this.grupoOpcao = grupoOpcao;
	}

}
