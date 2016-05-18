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

        MenuCategory grupo = new MenuCategory("Name");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        Collection<MenuOption> opcoes = grupo.getOptions();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void deveEncontrarOpcaoPeloId() {

        MenuCategory grupo = new MenuCategory("Name");
        MenuOption opcao1 = grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        MenuOption queried = grupo.findOpcaoCardapioPeloId(opcao1.getName());


        Assert.assertEquals(opcao1.getName(), queried.getName());
    }

    @Test
    public void deveAtualizarInformacoesOpcaoCardapio() {

        MenuCategory grupo = new MenuCategory("Name");
        MenuOption opcao1 = grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        MenuOption queried = grupo.findOpcaoCardapioPeloId(opcao1.getName());
        queried.setName("NovoNome");

        MenuOption updated = grupo.findOpcaoCardapioPeloId(opcao1.getName());

        Assert.assertEquals("NovoNome", updated.getName());

        Collection<MenuOption> opcoes = grupo.getOptions();
        Iterator<MenuOption> iterator = opcoes.iterator();

        Assert.assertEquals("NovoNome", iterator.next().getName());
        Assert.assertEquals("Opcao2", iterator.next().getName());
    }

    @Test
    public void aoCriarOpcaoCardapioDeveGerarUmId() {

        MenuCategory grupo = new MenuCategory("Name");
        MenuOption created = grupo.adicioneOpcao("Opcao1");
        Assert.assertNotNull(created.getName());

    }

    @Test
    public void deveRemoverTodasAsOpcoes() {

        MenuCategory grupo = new MenuCategory("Name");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        grupo.limpa();
        Collection<MenuOption> opcoes = grupo.getOptions();
        Assert.assertEquals(0, opcoes.size());
    }

    @Test
    public void deveRemoverOpcaoCardapioExistente() {

        MenuCategory grupo = new MenuCategory("Name");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");


        Collection<MenuOption> opcoes = grupo.getOptions();
        MenuOption opcao1 = opcoes.iterator().next();
        grupo.removaOpcao(opcao1);

        opcoes = grupo.getOptions();

        Assert.assertEquals(1, opcoes.size());
        Assert.assertEquals("Opcao2", opcoes.iterator().next().getName());
    }

    @Test
    public void aoInserirOpcaoJaExistenteOEfeitoDeveSerIgnorado() {

        MenuCategory grupo = new MenuCategory("Name");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao2");

        Collection<MenuOption> opcoes = grupo.getOptions();
        Assert.assertEquals(2, opcoes.size());
    }

    @Test
    public void opcoesDevemSerOrdenadasPorNome() {

        MenuCategory grupo = new MenuCategory("Name");
        grupo.adicioneOpcao("Opcao2");
        grupo.adicioneOpcao("Opcao1");
        grupo.adicioneOpcao("Opcao3");

        Collection<MenuOption> opcoes = grupo.getOptions();
        Iterator<MenuOption> iterator = opcoes.iterator();

        Assert.assertEquals("Opcao1", iterator.next().getName());
        Assert.assertEquals("Opcao2", iterator.next().getName());
        Assert.assertEquals("Opcao3", iterator.next().getName());
    }

}
