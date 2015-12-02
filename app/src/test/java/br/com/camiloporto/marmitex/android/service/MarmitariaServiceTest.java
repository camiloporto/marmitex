package br.com.camiloporto.marmitex.android.service;

import junit.framework.Assert;

import org.junit.Test;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;
import br.com.camiloporto.marmitex.android.provider.service.MarmitariaBuilder;
import br.com.camiloporto.marmitex.android.repository.InMemoryMarmitariaRepository;
import br.com.camiloporto.marmitex.android.repository.MarmitariaRepository;

/**
 * Created by camiloporto on 01/12/15.
 */
public class MarmitariaServiceTest {

    @Test
    public void deveCriarNovaMarmitaria() {
        MarmitaService marmitaService = new MarmitaService(new InMemoryMarmitariaRepository());
        Marmitaria m = marmitaService.create("Espaco Sabor", "3245-6778", "Jaguarai, 4546");

        Assert.assertNotNull(m.getId());

        Marmitaria queried = marmitaService.find(m.getId());
        Assert.assertNotNull(queried);
        Assert.assertEquals(m.getId(), queried.getId());

    }

    @Test
    public void devePersistirMarmitaria() {
        MarmitaService marmitaService = new MarmitaService(new InMemoryMarmitariaRepository());
        Marmitaria m = marmitaService.create("Espaco Sabor", "3245-6778", "Jaguarai, 4546");

        m.setEndereco("Novo Endereco");
        m.setNome("Novo Nome");

        marmitaService.save(m);

        Marmitaria updated = marmitaService.find(m.getId());
        Assert.assertEquals("Novo Endereco", updated.getEndereco());
        Assert.assertEquals("Novo Nome", updated.getNome());
    }
}
