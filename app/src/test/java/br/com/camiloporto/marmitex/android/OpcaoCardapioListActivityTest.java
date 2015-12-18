package br.com.camiloporto.marmitex.android;

import android.content.Intent;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitariaBuilder;

/**
 * Created by camiloporto on 26/11/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class OpcaoCardapioListActivityTest {

    @Test
    public void deveListarOpcoesDoCarpdaio() {

        GrupoOpcaoCardapioActivity grupoOpcaoCardapioActivity = Robolectric.setupActivity(GrupoOpcaoCardapioActivity.class);

        GrupoAlimentar grupoItems = new GrupoAlimentar();
        grupoItems.adicioneOpcao("Feijao");
        grupoItems.adicioneOpcao("Arroz");
        Intent i = new Intent(grupoOpcaoCardapioActivity, OpcaoCardapioActivity.class);
        i.putExtra(OpcaoCardapioListFragment.ARG_GRUPO_OPCAO, grupoItems);

        OpcaoCardapioActivity opcaoCardapioActivity = Robolectric.buildActivity(OpcaoCardapioActivity.class)
                .withIntent(i)
                .create().visible().get();

        OpcaoCardapioListFragment opcoesFragment = opcaoCardapioActivity.getOpcoesFragment();
        ListView opcoesListView = opcoesFragment.getListView();
        ListAdapter adapter = opcoesListView.getAdapter();
        EditText arroz = (EditText) adapter.getView(0, null, null).findViewById(R.id.cardapio_opcao_item_list_descricao_input);
        EditText feijao = (EditText) adapter.getView(1, null, null).findViewById(R.id.cardapio_opcao_item_list_descricao_input);

        Assert.assertEquals("Arroz", arroz.getText().toString());
        Assert.assertEquals("Feijao", feijao.getText().toString());

    }

    @Test
    public void deveAtualizarEstadoListaItemsCardapio() {

        Marmitaria m = new MarmitariaBuilder()
                .newMarmitaria("camilo", "12345", "tereza campos")
                .criaCardapio("Segunda-Feira")
                .adicioneGrupo("Carnes")
                .comOpcao("Patinho")
                .comOpcao("Frango Grelhado")
                .ok()
                .adicioneGrupo("Acompanhamentos")
                .comOpcao("Feijao")
                .comOpcao("Arroz")
                .ok()
                .adicioneGrupo("Salada")
                .comOpcao("Hortaliças")
                .comOpcao("Legumes no Vapor")
                .ok()
                .okCardapio()
                .getMarmitaria();

        Intent i = new Intent(
                RuntimeEnvironment.application, GrupoOpcaoCardapioActivity.class);
        i.putExtra(GrupoOpcaoListFragment.ARG_NAME_CARDAPIO, m.getCardapios().get(0));

        GrupoOpcaoCardapioActivity grupoOpcaoCardapioActivity = Robolectric.buildActivity(GrupoOpcaoCardapioActivity.class)
                .withIntent(i)
                .visible().get();



        //FIXME terminar este teste

    }


}
