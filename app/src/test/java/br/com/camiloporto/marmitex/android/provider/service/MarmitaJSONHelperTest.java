package br.com.camiloporto.marmitex.android.provider.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

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
        GrupoAlimentar carnes = cardapio.adicioneGrupoDeOpcoes("Carnes");
        carnes.adicioneOpcao("Patinho");
        carnes.adicioneOpcao("Frango Grelhado");


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

        String idMarmitaria = m.getId();
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

        String idMarmitaria = m.getId();
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