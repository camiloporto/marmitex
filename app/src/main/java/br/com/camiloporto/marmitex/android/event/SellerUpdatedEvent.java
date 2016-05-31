package br.com.camiloporto.marmitex.android.event;

import br.com.camiloporto.marmitex.android.model.Seller;
import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by ur42 on 30/05/2016.
 */
public class SellerUpdatedEvent extends AbstractMarmitexEvent {

    private Seller seller;

    public SellerUpdatedEvent(boolean success) {
        super(success);
    }

    public SellerUpdatedEvent(MarmitexException e) {
        super(e);
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Seller getSeller() {
        return seller;
    }
}
