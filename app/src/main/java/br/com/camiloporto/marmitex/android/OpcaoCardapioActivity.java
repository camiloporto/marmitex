package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import br.com.camiloporto.marmitex.android.OpcaoCardapioListFragment.OpcaoCardapioListFragmentListener;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.OpcaoCardapio;

public class OpcaoCardapioActivity extends Activity implements OpcaoCardapioListFragmentListener {
	
	private static final String TAG = OpcaoCardapioActivity.class.getName();
	
	private GrupoAlimentar grupoOpcao;
	private OpcaoCardapioListFragment opcoesFragment;

	public OpcaoCardapioListFragment getOpcoesFragment() {
		return opcoesFragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_opcoes);
		FragmentManager fm = getFragmentManager();
		opcoesFragment = (OpcaoCardapioListFragment) fm.findFragmentById(R.id.cardapio_opcoes_fragmentContainer);
		grupoOpcao = (GrupoAlimentar) getIntent().getSerializableExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO);

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
		OpcaoCardapio opcaoCardapio = grupoOpcao.adicioneOpcao(descricaoItem);
	}

	@Override
	public void onItemUpdated(OpcaoCardapio item) {
		Log.i(TAG, "atualizando item: " + item);
		//TODO ver como salvar esse item. Salvar individualmente ou salvar o cardapio todo?
	}

	@Override
	public void onItemDeleted(OpcaoCardapio item) {
		Log.i(TAG, "removendo item: " + item);
		grupoOpcao.removaOpcao(item);
	}

	@Override
	public void onItemGroupUpdated(GrupoAlimentar groupItems) {
		// TODO Auto-generated method stub
		
	}

}
