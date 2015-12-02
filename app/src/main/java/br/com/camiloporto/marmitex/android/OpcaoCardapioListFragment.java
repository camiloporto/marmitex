package br.com.camiloporto.marmitex.android;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Collection;

import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.OpcaoCardapio;

public class OpcaoCardapioListFragment extends ListFragment {

	public static final String ARG_GRUPO_OPCAO = "br.com.camiloporto.marmitex.android.GRUPO_ITEM";
	private static final String TAG = OpcaoCardapioListFragment.class.getName();
	
	private GrupoAlimentar grupoOpcao;
	
	private EditText inputItem;
	private Button addButton;

	@SuppressLint("ValidFragment")
	private OpcaoCardapioListFragment() {
	}

	public static OpcaoCardapioListFragment newInstance(GrupoAlimentar grupoOpcao) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_GRUPO_OPCAO, grupoOpcao);
		OpcaoCardapioListFragment fragment = new OpcaoCardapioListFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		grupoOpcao = (GrupoAlimentar) getArguments().getSerializable(
				ARG_GRUPO_OPCAO);
		if (grupoOpcao != null) {
			getActivity().setTitle(grupoOpcao.getDescricao());
			setListAdapter(new OpcaoListAdapter(grupoOpcao.getOpcoes()));
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
		listAdapter.addAll(grupoOpcao.getOpcoes());
		listAdapter.notifyDataSetChanged();
	}
	
	public interface OpcaoCardapioListFragmentListener {
		public void onNewItemAdded(String itemDescricao);
		public void onItemUpdated(OpcaoCardapio item);
		public void onItemDeleted(OpcaoCardapio item);
		public void onItemGroupUpdated(GrupoAlimentar groupItems);
	}
	
	private class OpcaoListAdapter extends ArrayAdapter<OpcaoCardapio> {


		public OpcaoListAdapter(Collection<OpcaoCardapio> items) {
			super(getActivity(), 0, new ArrayList<OpcaoCardapio>(items));
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
					((OpcaoCardapioListFragmentListener) getActivity()).onItemDeleted(itemClicked);
					remove(itemClicked);
					notifyDataSetChanged();
					Log.i(TAG, "removendo item " + itemClicked);
				}

			});
			//FIXME melhorar/simplificar essa implementacao de Edicao de itens. Ver como funciona Adapter e verificar pq a erros na remocao de items
			// 1. a remocao esta desfazendo a edicao de items
			// 2. Eh preciso fazer clear(); addAll() no GrupoOpcaoAcitivti apos encerrar essa atividade? A quantidade de grupos nao eh atlerada
			// apenas seus items (que nao eh listado na GrupoActivity
			inputDescricao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean hasFocus) {
					if (!hasFocus) {
						Editable newValue = ((EditText) view).getText();
						if (getCount() > 0) {
							OpcaoCardapio item = getItem(position);
							if (item != null) {
								item.setNome(newValue.toString());
							}
						}
					}
				}
			});
			

			return convertView;
		}

	}

	public void setGrupoOpcoes(GrupoAlimentar grupoOpcao) {
		this.grupoOpcao = grupoOpcao;
		((OpcaoListAdapter) getListAdapter()).clear();
		((OpcaoListAdapter) getListAdapter()).addAll(grupoOpcao.getOpcoes());
	}

}
