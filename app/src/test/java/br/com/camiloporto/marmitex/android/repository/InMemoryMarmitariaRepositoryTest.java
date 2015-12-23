package br.com.camiloporto.marmitex.android.repository;

import junit.framework.Assert;

import org.junit.Test;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitariaBuilder;

/**
 * Created by camiloporto on 01/12/15.
 */

public class InMemoryMarmitariaRepositoryTest {

    @Test
    public void deveInserirNovaMarmitaria() {
        MarmitariaRepository repository = new InMemoryMarmitariaRepository();
        Marmitaria marmitaria = new MarmitariaBuilder()
                .newMarmitaria("Espaco Sabor", "2324-33434", "Jaguarari")
                .getMarmitaria();

        Marmitaria saved = repository.save(marmitaria);
        Assert.assertNotNull(saved.getId());

        Marmitaria queried = repository.find(saved.getId());
        Assert.assertNotNull(queried);
    }

}
