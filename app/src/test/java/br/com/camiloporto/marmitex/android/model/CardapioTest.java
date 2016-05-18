package br.com.camiloporto.marmitex.android.model;

import junit.framework.Assert;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by camiloporto on 01/12/15.
 */
public class CardapioTest {

    @Test
    public void deveAdicionarNovoGrupoDeOpcoes() {
        Menu c = new Menu("Name");
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<MenuCategory> gruposDeOpcoes = c.getCategories();
        Assert.assertEquals(3, gruposDeOpcoes.size());
    }

    @Test
    public void deveEncontrarGrupoPeloId() {
        Menu c = new Menu("Name");
        MenuCategory proteina = c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        MenuCategory queried = c.findGrupoDeOpcoes(proteina.getName());
        Assert.assertEquals(proteina.getName(), queried.getName());

    }

    @Test
    public void aoAdicionarGrupoDeOpcoesDeveGerarId() {
        Menu c = new Menu("Name");
        MenuCategory created = c.adicioneGrupoDeOpcoes("Proteina");

        Assert.assertNotNull(created.getName());
    }


    @Test
    public void deveRemoverGrupoDeOpcoes() {
        Menu c = new Menu("Name");
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<MenuCategory> gruposDeOpcoes = c.getCategories();
        Iterator<MenuCategory> iterator = gruposDeOpcoes.iterator();
        MenuCategory acompanhamentos = iterator.next();

        c.removeGrupo(acompanhamentos);

        gruposDeOpcoes = c.getCategories();
        Assert.assertEquals(2, gruposDeOpcoes.size());

        iterator = gruposDeOpcoes.iterator();
        Assert.assertEquals("Proteina", iterator.next().getName());
        Assert.assertEquals("Saladas", iterator.next().getName());
    }

    @Test
    public void gruposDevemSerOrdenadosPorNome() {
        Menu c = new Menu("Name");
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<MenuCategory> gruposDeOpcoes = c.getCategories();
        Iterator<MenuCategory> iterator = gruposDeOpcoes.iterator();
        Assert.assertEquals("Acompanhamentos", iterator.next().getName());
        Assert.assertEquals("Proteina", iterator.next().getName());
        Assert.assertEquals("Saladas", iterator.next().getName());
    }

    @Test
    public void adoAdicionarGrupoExistenteOEfeitoDeveSerIgnorado() {
        Menu c = new Menu("Name");
        c.adicioneGrupoDeOpcoes("Proteina");
        MenuCategory acompanhamentos = c.adicioneGrupoDeOpcoes("Acompanhamentos");
        acompanhamentos.adicioneOpcao("Feijao");

        c.adicioneGrupoDeOpcoes("Saladas");

        c.adicioneGrupoDeOpcoes("Acompanhamentos");


        List<MenuCategory> gruposDeOpcoes = c.getCategories();
        Assert.assertEquals(3, gruposDeOpcoes.size());

        MenuCategory grupo = gruposDeOpcoes.iterator().next();
        Assert.assertEquals("Acompanhamentos", grupo.getName());
        Assert.assertEquals(1, grupo.getOptions().size());
    }
}
