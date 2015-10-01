package br.com.camiloporto.marmitex.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;

import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;

public class OpcaoCardapioListFragment extends ListFragment {

	public static final String ARG_GRUPO_OPCAO = "br.com.camiloporto.marmitex.android.GRUPO_ITEM";
	private static final String TAG = OpcaoCardapioListFragment.class.getName();
	
	private GrupoItems grupoOpcao;
	
	private EditText inputItem;
	private Button addButton;

	@SuppressLint("ValidFragment")
	private OpcaoCardapioListFragment() {
	}

	public static OpcaoCardapioListFragment newInstance(GrupoItems grupoOpcao) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_GRUPO_OPCAO, grupoOpcao);
		OpcaoCardapioListFragment fragment = new OpcaoCardapioListFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		grupoOpcao = (GrupoItems) getArguments().getSerializable(
				ARG_GRUPO_OPCAO);
		if (grupoOpcao != null) {
			getActivity().setTitle(grupoOpcao.getDescricao());
			setListAdapter(new OpcaoListAdapter(grupoOpcao.getItems()));
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
		
		return v;
	}
	
	private void newItemAdded(String itemDescricao) {
		((OpcaoCardapioListFragmentListener) getActivity()).onNewItemAdded(itemDescricao);
		ArrayAdapter listAdapter = (OpcaoListAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(grupoOpcao.getItems());
		listAdapter.notifyDataSetChanged();
	}
	
	public interface OpcaoCardapioListFragmentListener {
		public void onNewItemAdded(String itemDescricao);
		public void onItemUpdated(ItemCardapio item);
		public void onItemDeleted(ItemCardapio item);
		public void onItemGroupUpdated(GrupoItems groupItems);
	}
	
	private class OpcaoListAdapter extends ArrayAdapter<ItemCardapio> {


		public OpcaoListAdapter(Collection<ItemCardapio> items) {
			super(getActivity(), 0, new ArrayList<ItemCardapio>(items));
		}
		
		
		@Override
		public View getView(final int position, View convertView, final ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_cardapio_opcao, null);
			}

			ItemCardapio itemClicked = getItem(position);
			
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_opcao_item_list_descricao_input);
			inputDescricao.setText(itemClicked.getDescricao());
			
//			final Button editButton = (Button) convertView
//					.findViewById(R.id.cardapio_opcao_item_list_editButton);
			
			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_opcao_item_list_removeButton);
			
//			editButton.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					if(editButton.getText().equals("Edit")) {
//						inputDescricao.setEnabled(true);
//						editButton.setText("Save");
//					} else {
//						inputDescricao.setEnabled(false);
//						editButton.setText("Edit");
//						Log.i(TAG, "Salvando nova descricao: " + inputDescricao.getText().toString());
//					}
//				}
//			});
			
			deleteButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ItemCardapio itemClicked = getItem(position);
					remove(itemClicked);
					notifyDataSetChanged();
					Log.i(TAG, "removendo item " + itemClicked);
				}

			});
			

			return convertView;
		}

	}

	public void setGrupoOpcoes(GrupoItems grupoOpcao) {
		this.grupoOpcao = grupoOpcao;
		
	}

}
