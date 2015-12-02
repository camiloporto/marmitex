package br.com.camiloporto.marmitex.android.repository;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 01/12/15.
 */
public interface MarmitariaRepository {
    Marmitaria save(Marmitaria marmitaria);

    Marmitaria find(String id);
}
