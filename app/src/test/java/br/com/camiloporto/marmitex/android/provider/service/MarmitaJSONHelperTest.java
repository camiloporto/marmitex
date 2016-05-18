package br.com.camiloporto.marmitex.android.provider.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.model.Menu;
import br.com.camiloporto.marmitex.android.model.MenuCategory;
import br.com.camiloporto.marmitex.android.model.Seller;

/**
 * Created by camiloporto on 08/11/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MarmitaJSONHelperTest {

    @Test
    public void devePersistirNovaMarmitaria() throws MalformedURLException {

        Seller m = new Seller("camilo", "12345", "tereza campos");
        Menu cardapio = m.createCardapio("Segunda-Feira");
        MenuCategory carnes = cardapio.adicioneGrupoDeOpcoes("Carnes");
        carnes.adicioneOpcao("Patinho");
        carnes.adicioneOpcao("Frango Grelhado");


        CouldantResponse result = new MarmitariaJSONHelper(null)
                .persistRemote(m);

        Assert.assertEquals(result.getStatus(), "ok");

    }

//    @Test
    public void deveRecuperarMarmitariaPreviamenteCadastrada() throws MalformedURLException {
        Seller m = new MarmitariaBuilder()
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

        String idMarmitaria = m.getName();
        Seller retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        Assert.assertEquals(retrieved.getName(), "camilo");

    }

    @Test
    public void deveAtualizarMarmitariaPreviamenteCadastrada() throws MalformedURLException {
        Seller m = new MarmitariaBuilder()
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

        String idMarmitaria = m.getName();
        Seller retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        retrieved.setAddress("Novo Endereco");
        CouldantResponse result = new MarmitariaJSONHelper(null)
                .persistRemote(retrieved);

        retrieved = new MarmitariaJSONHelper(null)
                .getMarmitaria(idMarmitaria);

        Assert.assertEquals("Novo Endereco", retrieved.getAddress());

    }

}