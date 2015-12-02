package br.com.camiloporto.marmitex.android.repository;

import java.util.HashMap;
import java.util.Map;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 01/12/15.
 */
public class InMemoryMarmitariaRepository implements MarmitariaRepository {

    private Map<String, Marmitaria> database = new HashMap<String, Marmitaria>();

    @Override
    public Marmitaria save(Marmitaria marmitaria) {
        database.put(marmitaria.getId(), marmitaria);
        return marmitaria;
    }

    @Override
    public Marmitaria find(String id) {
        return database.get(id);
    }
}
