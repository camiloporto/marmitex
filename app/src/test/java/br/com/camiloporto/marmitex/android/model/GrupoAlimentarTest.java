package br.com.camiloporto.marmitex.android.model;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by camiloporto on 01/12/15.
 */
public class GrupoAlimentarTest {

    @Test
    public void deveAdicionarNovaOpcaoCardapio() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        Collection<ItemCardapio> opcoes = grupo.getOpcoes();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void aoInserirOpcaoJaExistenteOEfeitoDeveSerIgnorado() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        Collection<ItemCardapio> opcoes = grupo.getOpcoes();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void opcoesDevemSerOrdenadasPorNome() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao3");

        Collection<ItemCardapio> opcoes = grupo.getOpcoes();
        Iterator<ItemCardapio> iterator = opcoes.iterator();

        Assert.assertEquals("Opcao1", iterator.next().getDescricao());
        Assert.assertEquals("Opcao2", iterator.next().getDescricao());
        Assert.assertEquals("Opcao3", iterator.next().getDescricao());
    }

}
