package br.com.camiloporto.marmitex.android.repository;

import java.util.HashMap;
import java.util.Map;

import br.com.camiloporto.marmitex.android.model.Seller;

/**
 * Created by camiloporto on 01/12/15.
 */
public class InMemoryMarmitariaRepository implements MarmitariaRepository {

    private Map<String, Seller> database = new HashMap<String, Seller>();

    @Deprecated
    public Seller save(Seller marmitaria) {
        throw new UnsupportedOperationException("@Deprecated");
//        database.put(marmitaria.getId(), marmitaria);
//        return marmitaria;
    }

    @Override
    public Seller find(String id) {
        if(id.equals("123")) {
            Seller gerada = database.get(id);
            if(gerada == null) {
                gerada = new Seller("gerada", "555", "jagua");
                gerada.createCardapio("cardapio1");
                gerada.createCardapio("cardapio 22");
                save(gerada);

            }
            return gerada;
        }
        return database.get(id);
    }
}
