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
        Cardapio c = new Cardapio();
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<GrupoAlimentar> gruposDeOpcoes = c.getGruposDeOpcoes();
        Assert.assertEquals(3, gruposDeOpcoes.size());
    }

    @Test
    public void deveEncontrarGrupoPeloId() {
        Cardapio c = new Cardapio();
        GrupoAlimentar proteina = c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        GrupoAlimentar queried = c.findGrupoDeOpcoes(proteina.getId());
        Assert.assertEquals(proteina.getDescricao(), queried.getDescricao());

    }

    @Test
    public void aoAdicionarGrupoDeOpcoesDeveGerarId() {
        Cardapio c = new Cardapio();
        GrupoAlimentar created = c.adicioneGrupoDeOpcoes("Proteina");

        Assert.assertNotNull(created.getId());
    }


    @Test
    public void deveRemoverGrupoDeOpcoes() {
        Cardapio c = new Cardapio();
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<GrupoAlimentar> gruposDeOpcoes = c.getGruposDeOpcoes();
        Iterator<GrupoAlimentar> iterator = gruposDeOpcoes.iterator();
        GrupoAlimentar acompanhamentos = iterator.next();

        c.removeGrupo(acompanhamentos);

        gruposDeOpcoes = c.getGruposDeOpcoes();
        Assert.assertEquals(2, gruposDeOpcoes.size());

        iterator = gruposDeOpcoes.iterator();
        Assert.assertEquals("Proteina", iterator.next().getDescricao());
        Assert.assertEquals("Saladas", iterator.next().getDescricao());
    }

    @Test
    public void gruposDevemSerOrdenadosPorNome() {
        Cardapio c = new Cardapio();
        c.adicioneGrupoDeOpcoes("Proteina");
        c.adicioneGrupoDeOpcoes("Acompanhamentos");
        c.adicioneGrupoDeOpcoes("Saladas");

        List<GrupoAlimentar> gruposDeOpcoes = c.getGruposDeOpcoes();
        Iterator<GrupoAlimentar> iterator = gruposDeOpcoes.iterator();
        Assert.assertEquals("Acompanhamentos", iterator.next().getDescricao());
        Assert.assertEquals("Proteina", iterator.next().getDescricao());
        Assert.assertEquals("Saladas", iterator.next().getDescricao());
    }

    @Test
    public void adoAdicionarGrupoExistenteOEfeitoDeveSerIgnorado() {
        Cardapio c = new Cardapio();
        c.adicioneGrupoDeOpcoes("Proteina");
        GrupoAlimentar acompanhamentos = c.adicioneGrupoDeOpcoes("Acompanhamentos");
        acompanhamentos.adicioneOpcao("Feijao");

        c.adicioneGrupoDeOpcoes("Saladas");

        c.adicioneGrupoDeOpcoes("Acompanhamentos");


        List<GrupoAlimentar> gruposDeOpcoes = c.getGruposDeOpcoes();
        Assert.assertEquals(3, gruposDeOpcoes.size());

        GrupoAlimentar grupo = gruposDeOpcoes.iterator().next();
        Assert.assertEquals("Acompanhamentos", grupo.getDescricao());
        Assert.assertEquals(1, grupo.getOpcoes().size());
    }
}
