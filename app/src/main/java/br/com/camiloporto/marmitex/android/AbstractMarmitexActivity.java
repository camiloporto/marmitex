package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import br.com.camiloporto.marmitex.android.model.Seller;

/**
 * Created by camiloporto on 17/12/15.
 */
public class AbstractMarmitexActivity extends Activity implements MarmitexApplication.OnMarmitariaUpdatedCallback, MarmitexApplication.OnMarmitariaCreatedCallback {

    protected Seller getActiveMarmitaria() {
        MarmitexApplication app = (MarmitexApplication) getApplication();
        Seller activeMarmitaria = app.getActiveMarmitaria();
        return activeMarmitaria;
    }

    protected MarmitexApplication getMarmitexApplication() {
        return (MarmitexApplication) getApplication();
    }

    @Override
    public void onMarmitariaUpdated() {
        Toast.makeText(this, "Marmitaria Atualizada", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onMarmitariaCreated() {
        Toast.makeText(this, "Marmitaria Criada", Toast.LENGTH_LONG)
                .show();
    }

    protected void catchAuthenticationRequiredException(AuthenticationRequiredException e) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}
