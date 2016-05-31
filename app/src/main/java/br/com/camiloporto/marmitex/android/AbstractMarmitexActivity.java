package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import br.com.camiloporto.marmitex.android.event.SellerUpdatedEvent;
import br.com.camiloporto.marmitex.android.model.Seller;
import br.com.camiloporto.marmitex.android.service.AuthenticationRequiredException;

/**
 * Created by camiloporto on 17/12/15.
 */
public class AbstractMarmitexActivity extends Activity implements MarmitexApplication.OnMarmitariaUpdatedCallback {

    protected Seller getActiveMarmitaria() {
        MarmitexApplication app = (MarmitexApplication) getApplication();
        Seller activeMarmitaria = app.getActiveMarmitaria();
        return activeMarmitaria;
    }

    protected MarmitexApplication getMarmitexApplication() {
        return (MarmitexApplication) getApplication();
    }

    @Override
    public void onSellerUpdated(SellerUpdatedEvent evt) {
        String msg = null;
        if(evt.isSuccess()) {
            msg = "Marmitaria Atualizada";
        } else {
            msg = "Ocorreu Erro!";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
                .show();
    }

    protected void catchAuthenticationRequiredException(AuthenticationRequiredException e) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
