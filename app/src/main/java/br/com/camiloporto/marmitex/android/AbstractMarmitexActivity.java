package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.widget.Toast;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 17/12/15.
 */
public class AbstractMarmitexActivity extends Activity implements MarmitexApplication.OnMarmitariaUpdatedCallback, MarmitexApplication.OnMarmitariaCreatedCallback {

    protected Marmitaria getActiveMarmitaria() {
        MarmitexApplication app = (MarmitexApplication) getApplication();
        Marmitaria activeMarmitaria = app.getActiveMarmitaria();
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
}
