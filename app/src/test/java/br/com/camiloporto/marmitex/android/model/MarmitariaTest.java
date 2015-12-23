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
        Marmitaria m = new Marmitaria(null, null, null);
        m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        Collection<Cardapio> cardapios = m.getCardapios();
        Assert.assertEquals(2, cardapios.size());
    }

    @Test
    public void deveEncontrarCardapioPeloId() {
        Marmitaria m = new Marmitaria(null, null, null);
        Cardapio cardapio = m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        Cardapio queried = m.findCardapioPeloId(cardapio.getId());

        Assert.assertEquals(cardapio.getId(), queried.getId());
    }

    @Test
    public void aoCriarNovoCardapioDeveGerarId() {
        Marmitaria m = new Marmitaria(null, null, null);
        Cardapio cardapio = m.createCardapio("Segunda-Feira");

        Assert.assertNotNull(cardapio.getId());
    }

    @Test
    public void deveRemoverCardapio() {
        Marmitaria m = new Marmitaria(null, null, null);
        Cardapio cardapio = m.createCardapio("Segunda-Feira");
        m.createCardapio("Terca-Feira");

        m.deleteCardapio(cardapio);
        Collection<Cardapio> cardapios = m.getCardapios();
        Assert.assertEquals(1, cardapios.size());
        Cardapio terca = cardapios.iterator().next();
        Assert.assertEquals("Terca-Feira", terca.getNome());
    }

    @Test
    public void cardapiosDeveSerOrdenadosPorNome() {
        Marmitaria m = new Marmitaria(null, null, null);
        m.createCardapio("Terca-Feira");
        m.createCardapio("Segunda-Feira");
        m.createCardapio("Quarta-Feira");


        Collection<Cardapio> cardapios = m.getCardapios();

        Iterator<Cardapio> iterator = cardapios.iterator();
        Assert.assertEquals("Quarta-Feira", iterator.next().getNome());
        Assert.assertEquals("Segunda-Feira", iterator.next().getNome());
        Assert.assertEquals("Terca-Feira", iterator.next().getNome());
    }


}
