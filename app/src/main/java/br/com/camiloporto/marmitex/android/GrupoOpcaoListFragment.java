package br.com.camiloporto.marmitex.android;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;

public class GrupoOpcaoListFragment extends ListFragment {
	
	public static final String ARG_NAME_CARDAPIO = "br.com.camiloporto.marmitex.android.NovoCardapioFragment.CARDAPIO";
	private static final String TAG = GrupoOpcaoListFragment.class.getName();
	private Cardapio cardapio;
	private LinearLayout containerGrupoOpcoes;
	private EditText inputItem;
	private Button addButton;

	private GrupoOpcaoListFragment() {
	}
	
	public static GrupoOpcaoListFragment newInstance(Cardapio c) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_NAME_CARDAPIO, c);
		GrupoOpcaoListFragment cardapioFragment = new GrupoOpcaoListFragment();
		cardapioFragment.setArguments(args);
		return cardapioFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cardapio = (Cardapio) getArguments().getSerializable(ARG_NAME_CARDAPIO);
		if(cardapio != null) {
			getActivity().setTitle(cardapio.getDescricao());
			setListAdapter(new GrupoOpcaoListAdapter(cardapio));
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

		return v;
	}

	private void newGroupAdded(String descricao) {
		((GrupoOpcaoListFragmentListener) getActivity()).onNewGroupAdded(descricao);
		ArrayAdapter listAdapter = (GrupoOpcaoListAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(cardapio.getGruposItems());
		listAdapter.notifyDataSetChanged();
	}

//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		GrupoItems clicked = (GrupoItems) getListAdapter().getItem(position);
//		Intent i = new Intent(getActivity(), OpcaoCardapioActivity.class);
//		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, clicked);
//		startActivity(i);
//	}

	public void notifyDataSetChanged() {
		GrupoOpcaoListAdapter listAdapter = (GrupoOpcaoListAdapter) getListAdapter();
		listAdapter.clear();
		listAdapter.addAll(cardapio.getGruposItems());
	}

	private class GrupoOpcaoListAdapter extends ArrayAdapter<GrupoItems> {
		
		public GrupoOpcaoListAdapter(Cardapio cardapio) {

			super(getActivity(), 0, cardapio.getGruposItems());
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio_grupo_opcao, null);
			}
			
			GrupoItems item = getItem(position);
			final EditText inputDescricao = (EditText) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_descricao_input);
			inputDescricao.setText(item.getDescricao());

			final Button deleteButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_removeButton);

			final Button editButton = (Button) convertView
					.findViewById(R.id.cardapio_grupo_opcao_item_list_editButton);

			deleteButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					GrupoItems item = getItem(position);
					remove(item);
					notifyDataSetChanged();
					Log.i(TAG, "removendo grupo " + item);
				}

			});

			editButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					GrupoItems item = getItem(position);
					((GrupoOpcaoListFragmentListener) getActivity()).onEditGroupItemsRequested(item);

				}
			});
			
			return convertView;
		}
		
	}
	
	public interface GrupoOpcaoListFragmentListener {
		public void onEditGroupItemsRequested(GrupoItems grupo);

		void onNewGroupAdded(String descricao);
//		public void onItemUpdated(ItemCardapio item);
//		public void onItemDeleted(ItemCardapio item);
//		public void onItemGroupUpdated(GrupoItems groupItems);
	}
	

}
