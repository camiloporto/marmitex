package br.com.camiloporto.marmitex.android;

import android.app.Activity;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 17/12/15.
 */
public class AbstractMarmitexActivity extends Activity {

    protected Marmitaria getActiveMarmitaria() {
        MarmitexApplication app = (MarmitexApplication) getApplication();
        Marmitaria activeMarmitaria = app.getActiveMarmitaria();
        return activeMarmitaria;
    }
}
