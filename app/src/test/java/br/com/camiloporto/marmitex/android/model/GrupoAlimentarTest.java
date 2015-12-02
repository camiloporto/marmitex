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

        Collection<OpcaoCardapio> opcoes = grupo.getOpcoes();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void deveRemoverTodasAsOpcoes() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        grupo.limpa();
        Collection<OpcaoCardapio> opcoes = grupo.getOpcoes();
        Assert.assertEquals(0, opcoes.size());
    }

    @Test
    public void deveRemoverOpcaoCardapioExistente() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");


        Collection<OpcaoCardapio> opcoes = grupo.getOpcoes();
        OpcaoCardapio opcao1 = opcoes.iterator().next();
        grupo.removaOpcao(opcao1);

        opcoes = grupo.getOpcoes();

        Assert.assertEquals(1, opcoes.size());
        Assert.assertEquals("Opcao2", opcoes.iterator().next().getNome());
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

        Collection<OpcaoCardapio> opcoes = grupo.getOpcoes();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void opcoesDevemSerOrdenadasPorNome() {

        GrupoAlimentar grupo = new GrupoAlimentar();
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao3");

        Collection<OpcaoCardapio> opcoes = grupo.getOpcoes();
        Iterator<OpcaoCardapio> iterator = opcoes.iterator();

        Assert.assertEquals("Opcao1", iterator.next().getNome());
        Assert.assertEquals("Opcao2", iterator.next().getNome());
        Assert.assertEquals("Opcao3", iterator.next().getNome());
    }

}
