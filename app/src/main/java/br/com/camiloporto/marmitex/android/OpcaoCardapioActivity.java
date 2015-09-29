package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import br.com.camiloporto.marmitex.android.OpcaoCardapioListFragment.OpcaoCardapioListFragmentListener;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;

public class OpcaoCardapioActivity extends Activity implements OpcaoCardapioListFragmentListener {
	
	private static final String TAG = OpcaoCardapioActivity.class.getName();
	
	private GrupoItems grupoOpcao;
	private OpcaoCardapioListFragment opcoesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_opcoes);
		FragmentManager fm = getFragmentManager();
		opcoesFragment = (OpcaoCardapioListFragment) fm.findFragmentById(R.id.cardapio_opcoes_fragmentContainer);
		grupoOpcao = (GrupoItems) getIntent().getSerializableExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO);

		if (opcoesFragment == null) {
			opcoesFragment = OpcaoCardapioListFragment.newInstance(grupoOpcao);
			fm.beginTransaction().add(R.id.cardapio_opcoes_fragmentContainer, opcoesFragment)
					.commit();
		} else {
			opcoesFragment.setGrupoOpcoes(grupoOpcao);
		}
		
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent();
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, this.grupoOpcao);
		setResult(Activity.RESULT_OK, i);
		super.onBackPressed();
	}
	
	@Override
	public void onNewItemAdded(String descricaoItem) {
		ItemCardapio i = new ItemCardapio(grupoOpcao);
		i.setDescricao(descricaoItem);
		Log.i(TAG, "adicionando novo item: " + i);
		//TODO ver como salvar esse item. Salvar individualmente ou salvar o cardapio todo?
		//FIXME item nao esta sendo persistido quando saimos e entramos na atividade de lista de opcoes. VERIFICAR
		i.setId((long)Math.random() * 10000L);
		grupoOpcao.adicionaItem(i);
	}

	@Override
	public void onItemUpdated(ItemCardapio item) {
		Log.i(TAG, "atualizando item: " + item);
		//TODO ver como salvar esse item. Salvar individualmente ou salvar o cardapio todo?
	}

	@Override
	public void onItemDeleted(ItemCardapio item) {
		Log.i(TAG, "removendo item: " + item);
		grupoOpcao.removeItem(item);
	}

	@Override
	public void onItemGroupUpdated(GrupoItems groupItems) {
		// TODO Auto-generated method stub
		
	}

}
