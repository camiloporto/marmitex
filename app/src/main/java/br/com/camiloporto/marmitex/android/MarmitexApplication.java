package br.com.camiloporto.marmitex.android;

import android.app.Application;
import android.os.AsyncTask;

import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;
import br.com.camiloporto.marmitex.android.repository.InMemoryMarmitariaRepository;

/**
 * Created by camiloporto on 02/12/15.
 */
public class MarmitexApplication extends Application {

    private MarmitaService marmitaService;
    private Marmitaria activeMarmitaria;

    @Override
    public void onCreate() {
        marmitaService = new MarmitaService(
                new InMemoryMarmitariaRepository()
        );
        super.onCreate();
    }

    public void updateActiveMarmitaria(final OnMarmitariaUpdatedCallback callback) {
        AsyncTask<Marmitaria, Void, Marmitaria> task = new AsyncTask<Marmitaria, Void, Marmitaria>() {

            @Override
            protected Marmitaria doInBackground(Marmitaria... params) {
                Marmitaria marmitaria = params[0];
                marmitaService.save(marmitaria);
                return marmitaria;
            }

            @Override
            protected void onPostExecute(Marmitaria marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaUpdated();
            }
        };
        task.execute(activeMarmitaria);
    }

    public void createMarmitaria(String nome, String fone, String endereco, final OnMarmitariaCreatedCallback callback) {
        AsyncTask<String, Void, Marmitaria> task = new AsyncTask<String, Void, Marmitaria>() {

            @Override
            protected Marmitaria doInBackground(String... params) {

                return marmitaService.create(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(Marmitaria marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaCreated();
            }
        };
        task.execute(nome, fone, endereco);
    }

    private void setActiveMarmitaria(Marmitaria marmitaria) {
        activeMarmitaria = marmitaria;
    }

    public Marmitaria getActiveMarmitaria() {
        return activeMarmitaria;
    }

    public void loadMarmitariaOfLoggedUser(final OnMarmitariaLoaded callback) {
        AsyncTask<String, Void, Marmitaria> task = new AsyncTask<String, Void, Marmitaria>() {

            @Override
            protected Marmitaria doInBackground(String... params) {
                String id = params[0];
                return marmitaService.find(id);
            }

            @Override
            protected void onPostExecute(Marmitaria marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaLoaded();
            }
        };
        //// FIXME: 08/12/15 Recuperar contexto do usuario e pegar id da marmitaria persistida. Ou procurar marmitaria por usuarioId
        String id = "123";
        task.execute(id);
    }

    public static interface OnMarmitariaCreatedCallback {
        void onMarmitariaCreated();
    }

    public static interface OnMarmitariaLoaded {
        void onMarmitariaLoaded();
    }

    public static interface OnMarmitariaUpdatedCallback {
        void onMarmitariaUpdated();
    }
}