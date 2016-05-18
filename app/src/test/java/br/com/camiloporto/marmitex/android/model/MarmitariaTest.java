package br.com.camiloporto.marmitex.android.model;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by camiloporto on 01/12/15.
 */
public class MarmitariaTest {

    @Test
    public void deveAdicionarNovoCardapio() {
        Seller m = new Seller(null, null, null);
        m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        Collection<Menu> cardapios = m.getMenus();
        Assert.assertEquals(2, cardapios.size());
    }

    @Test
    public void deveEncontrarCardapioPeloId() {
        Seller m = new Seller(null, null, null);
        Menu cardapio = m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        Menu queried = m.findCardapioPeloId(cardapio.getName());

        Assert.assertEquals(cardapio.getName(), queried.getName());
    }

    @Test
    public void aoCriarNovoCardapioDeveGerarId() {
        Seller m = new Seller(null, null, null);
        Menu cardapio = m.createCardapio("Segunda-Feira");

        Assert.assertNotNull(cardapio.getName());
    }

    @Test
    public void deveRemoverCardapio() {
        Seller m = new Seller(null, null, null);
        Menu cardapio = m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        m.deleteCardapio(cardapio);
        Collection<Menu> cardapios = m.getMenus();
        Assert.assertEquals(1, cardapios.size());
        Menu terca = cardapios.iterator().next();
        Assert.assertEquals("Terca-Feira", terca.getName());
    }

    @Test
    public void cardapiosDeveSerOrdenadosPorNome() {
        Seller m = new Seller(null, null, null);
        m.createCardapio("Terca-Feira");
        m.createCardapio("Segunda-Feira");
        m.createCardapio("Quarta-Feira");


        Collection<Menu> cardapios = m.getMenus();

        Iterator<Menu> iterator = cardapios.iterator();
        Assert.assertEquals("Quarta-Feira", iterator.next().getName());
        Assert.assertEquals("Segunda-Feira", iterator.next().getName());
        Assert.assertEquals("Terca-Feira", iterator.next().getName());
    }


}
