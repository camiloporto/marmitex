package br.com.camiloporto.marmitex.android;

import android.app.Application;
import android.os.AsyncTask;

import br.com.camiloporto.marmitex.android.event.LoginFinishedEvent;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;
import br.com.camiloporto.marmitex.android.provider.service.ProfileService;
import br.com.camiloporto.marmitex.android.repository.InMemoryMarmitariaRepository;
import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by camiloporto on 02/12/15.
 */
public class MarmitexApplication extends Application {

    private MarmitaService marmitaService;
    private ProfileService profileService;
    private Marmitaria activeMarmitaria;

    @Override
    public void onCreate() {
        marmitaService = new MarmitaService(
                new InMemoryMarmitariaRepository()
        );
        profileService = new ProfileService(this);
        super.onCreate();
    }

    public void createNewProfile(Profile profile, final OnProfileCreatedCallback callback) {
        AsyncTask<Profile, Void, Profile> task = new AsyncTask<Profile, Void, Profile>() {

            @Override
            protected Profile doInBackground(Profile... params) {
                //FIXME return an operation status report. idicating errors or not..
                Profile profile = params[0];
                profileService.create(profile);
                return profile;
            }

            @Override
            protected void onPostExecute(Profile profile) {
                callback.onProfileCreated(profile);
            }
        };
        task.execute(profile);
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

    public void login(String email, String password, final OnLoginFinishedCallback callback) {
        AsyncTask<String, Void, LoginFinishedEvent> task = new AsyncTask<String, Void, LoginFinishedEvent>() {

            @Override
            protected LoginFinishedEvent doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                try {
                    String accessToken = profileService.login(username, password);
                    //FIXME Store AccessToken somewhere
                    boolean success = accessToken != null;
                    return new LoginFinishedEvent(success);
                } catch (MarmitexException e) {
                    return new LoginFinishedEvent(e);
                }
            }

            @Override
            protected void onPostExecute(LoginFinishedEvent event) {
                callback.onLoginFinished(event);
            }
        };
        task.execute(email, password);
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

    public static interface OnProfileCreatedCallback {
        void onProfileCreated(Profile profile);
    }

    public static interface OnLoginFinishedCallback {
        void onLoginFinished(LoginFinishedEvent event);
    }
}
