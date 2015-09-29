package br.com.camiloporto.marmitex.android;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;

public class GrupoOpcaoListFragment extends ListFragment {
	
	public static final String ARG_NAME_CARDAPIO = "br.com.camiloporto.marmitex.android.NovoCardapioFragment.CARDAPIO";
	private Cardapio cardapio;
	private LinearLayout containerGrupoOpcoes;
	
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
			setListAdapter(new OpcaoListAdapter(cardapio.getGruposItems()));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		GrupoItems clicked = (GrupoItems) getListAdapter().getItem(position);
		Intent i = new Intent(getActivity(), OpcaoCardapioActivity.class);
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, clicked);
		startActivity(i);
	}
	
	private class OpcaoListAdapter extends ArrayAdapter<GrupoItems> {
		
		public OpcaoListAdapter(List<GrupoItems> items) {
			super(getActivity(), 0, items);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			if(convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_cardapio_grupo_opcao, null);
			}
			
			GrupoItems item = getItem(position);
			TextView text = (TextView) convertView.findViewById(R.id.cardapio_opcao_item_list_descricao);
			text.setText(item.getDescricao());
			
			Button delete = (Button) convertView.findViewById(R.id.cardapio_opcao_item_list_removeButton);
			delete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
//					Intent i = new Intent(getActivity(), OpcaoCardapioActivity.class);
					GrupoItems item = getItem(position);
					((GrupoOpcaoListFragmentListener) getActivity()).onEditGroupItemsRequested(item);
					((OpcaoListAdapter)getListAdapter()).notifyDataSetChanged();
//					i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, item);
//					getActivity().startActivityForResult(i, 1);
//					startActivityForResult(i, 1);
//					startActivity(i);
				}
			});
			//TODO adiciona comportamento para editar grupo e remover grupo
			//editar grupo vai para a List de opcoes do grupo (nova ListView)
			
			return convertView;
		}
		
	}
	
	public interface GrupoOpcaoListFragmentListener {
		public void onEditGroupItemsRequested(GrupoItems grupo);
//		public void onItemUpdated(ItemCardapio item);
//		public void onItemDeleted(ItemCardapio item);
//		public void onItemGroupUpdated(GrupoItems groupItems);
	}
	

}
