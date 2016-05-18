package br.com.camiloporto.marmitex.android.repository;

import br.com.camiloporto.marmitex.android.model.Seller;

/**
 * Created by camiloporto on 01/12/15.
 */
public interface MarmitariaRepository {
    Seller save(Seller marmitaria);

    Seller find(String id);
}
