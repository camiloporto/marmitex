package br.com.camiloporto.marmitex.android;

import android.app.FragmentManager;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.OpcaoCardapio;

public class OpcaoCardapioActivity extends AbstractMarmitexActivity implements OpcaoCardapioListFragment.OpcaoCardapioListFragmentCallbacks {
	
	private static final String TAG = OpcaoCardapioActivity.class.getName();

	public static final String ARG_NAME_CARDAPIO = "br.com.camiloporto.marmitex.android.NovoCardapioFragment.CARDAPIO";
	public static final String ARG_GRUPO_OPCAO = "br.com.camiloporto.marmitex.android.GRUPO_ITEM";
	
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

		String idCardapio = getIntent().getStringExtra(ARG_NAME_CARDAPIO);
		String idGrupoAlimentar = getIntent().getStringExtra(ARG_GRUPO_OPCAO);
		Cardapio cardapio = getActiveMarmitaria().findCardapioPeloId(idCardapio);
		grupoOpcao = cardapio.findGrupoDeOpcoes(idGrupoAlimentar);

		if (opcoesFragment == null) {
			opcoesFragment = new OpcaoCardapioListFragment();
			fm.beginTransaction().add(R.id.cardapio_opcoes_fragmentContainer, opcoesFragment)
					.commit();
			opcoesFragment.setGrupoOpcoes(grupoOpcao);
		} else {
			opcoesFragment.setGrupoOpcoes(grupoOpcao);
		}
		
	}

	@Override
	public void onNewItemCreated(String descricao) {
		grupoOpcao.adicioneOpcao(descricao);
		opcoesFragment.updateUI();
	}

	@Override
	public void onOpcaoCardapioDeleted(OpcaoCardapio opcaoCardapio) {
		grupoOpcao.removaOpcao(opcaoCardapio);
		opcoesFragment.updateUI();
	}
}
