package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import br.com.camiloporto.marmitex.android.GrupoOpcaoListFragment.GrupoOpcaoListFragmentListener;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;

public class GrupoOpcaoCardapioActivity extends AbstractMarmitexActivity implements GrupoOpcaoListFragment.GrupoOpcaoListFragmentCallbacks {

	private Cardapio cardapio;
	private GrupoOpcaoListFragment grupoOpcaoListFragment;

	//// FIXME: 17/12/15 Refatorar essa classe. Fazer interacao com fragment semelhante ao CardapioListActivity
	/*
	* Fazer o fragment exigir que a classe implement o callbac no 'onAttach()'
	* Apos a execuaco de cada calback, solicitar ao fragment se atualizar (frag.updateUI())
	* Na criacao do fragment, criar com construtor default e depois da um setCardapio(caradpio) invocando updateUI()
	* remover metodo creator statico do fragment
	* Ajustar a requisicao para edico de grupo.
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cardapio_grupo_opcao);
		FragmentManager fm = getFragmentManager();
		grupoOpcaoListFragment = (GrupoOpcaoListFragment) fm.findFragmentById(R.id.cardapio_novo_fragmentContainer);

		if (grupoOpcaoListFragment == null) {
			String idCardapio = getIntent().getStringExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO);
			cardapio = getActiveMarmitaria().findCardapioPeloId(idCardapio);
			grupoOpcaoListFragment = new GrupoOpcaoListFragment();
			fm.beginTransaction().add(R.id.cardapio_novo_fragmentContainer, grupoOpcaoListFragment)
					.commit();
			grupoOpcaoListFragment.setCardapio(cardapio);
		}

	}

	@Override
	public void onGrupoAlimentarDeleted(GrupoAlimentar grupoAlimentar) {
		cardapio.removeGrupo(grupoAlimentar);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarCreated(String descricao) {
		cardapio.adicioneGrupoDeOpcoes(descricao);
		grupoOpcaoListFragment.updateUI();
	}

	@Override
	public void onGrupoAlimentarRequestForEdition(GrupoAlimentar item) {
		Intent i = new Intent(this, OpcaoCardapioActivity.class);
		i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, cardapio.getId());
		i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, item.getId());
		startActivity(i);
	}
}
