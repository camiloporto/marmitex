package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

import static org.junit.Assert.*;

/**
 * Created by camiloporto on 08/11/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MarmitaJSONHelperTest {

    @Test
    public void devePersistirNovaMarmitaria() throws MalformedURLException {

        Marmitaria m = new Marmitaria("camilo", "12345", "tereza campos");
        Cardapio cardapio = m.createCardapio("Segunda-Feira");
        GrupoItems carnes = cardapio.addGrupo("Carnes");
        carnes.newItem("Patinho");
        carnes.newItem("Frango Grelhado");


        CouldantResponse result = new MarmitariaJSONHelper(null)
                .persistRemote(m);

        Assert.assertEquals(result.getStatus(), "ok");

    }

    @Test
    public void deveRecuperarMarmitariaPreviamenteCadastrada() throws MalformedURLException {
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

        CouldantResponse result = new MarmitariaJSONHelper(null)
                .persistRemote(m);

        String idMarmitaria = m.getUuid().toString();
        Marmitaria retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        Assert.assertEquals(retrieved.getNome(), "camilo");
        Assert.assertNotNull(retrieved.getRevision());
    }

    @Test
    public void deveAtualizarMarmitariaPreviamenteCadastrada() throws MalformedURLException {
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

        new MarmitariaJSONHelper(null)
                .persistRemote(m);

        String idMarmitaria = m.getUuid().toString();
        Marmitaria retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        retrieved.setEndereco("Novo Endereco");
        CouldantResponse result = new MarmitariaJSONHelper(null)
                .persistRemote(retrieved);

        retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        Assert.assertEquals("Novo Endereco", retrieved.getEndereco());

    }

}