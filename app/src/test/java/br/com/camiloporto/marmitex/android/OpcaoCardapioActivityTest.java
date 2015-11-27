package br.com.camiloporto.marmitex.android;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;

import br.com.camiloporto.marmitex.android.model.GrupoItems;

/**
 * Created by camiloporto on 26/11/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class OpcaoCardapioActivityTest {

    @Test
    public void deveListarOpcoesDoCarpdaio() {

        GrupoOpcaoCardapioActivity grupoOpcaoCardapioActivity = Robolectric.setupActivity(GrupoOpcaoCardapioActivity.class);

        GrupoItems grupoItems = new GrupoItems();
        grupoItems.newItem("Feijao");
        grupoItems.newItem("Arroz");
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


}
