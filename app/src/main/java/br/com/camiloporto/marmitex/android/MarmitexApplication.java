package br.com.camiloporto.marmitex.android;

import android.app.Application;
import android.os.AsyncTask;

import br.com.camiloporto.marmitex.android.event.LoginFinishedEvent;
import br.com.camiloporto.marmitex.android.model.Seller;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;
import br.com.camiloporto.marmitex.android.service.ProfileService;
import br.com.camiloporto.marmitex.android.repository.InMemoryMarmitariaRepository;
import br.com.camiloporto.marmitex.android.service.MarmitexException;

/**
 * Created by camiloporto on 02/12/15.
 */
public class MarmitexApplication extends Application {

    private MarmitaService marmitaService;
    private ProfileService profileService;
    private Seller activeMarmitaria;
    private String loggedUser;

    @Override
    public void onCreate() {
        marmitaService = new MarmitaService(this);
        profileService = new ProfileService(this);
        super.onCreate();
    }

    public void createNewProfile(Profile profile, final OnProfileCreatedCallback callback) {
        AsyncTask<Profile, Void, ProfileCreatedEvent> task = new AsyncTask<Profile, Void, ProfileCreatedEvent>() {

            @Override
            protected ProfileCreatedEvent doInBackground(Profile... params) {
                try {
                    Profile profile = params[0];
                    profileService.create(profile);
                    ProfileCreatedEvent event = new ProfileCreatedEvent();
                    event.setCreatedProfile(profile);
                    return event;
                } catch (MarmitexException e) {
                    return new ProfileCreatedEvent(e);
                }
            }

            @Override
            protected void onPostExecute(ProfileCreatedEvent event) {
                callback.onProfileCreated(event);
            }
        };
        task.execute(profile);
    }

    public void updateActiveMarmitaria(final OnMarmitariaUpdatedCallback callback) {
        AsyncTask<Seller, Void, Seller> task = new AsyncTask<Seller, Void, Seller>() {

            @Override
            protected Seller doInBackground(Seller... params) {
                Seller marmitaria = params[0];
                marmitaService.save(marmitaria);
                return marmitaria;
            }

            @Override
            protected void onPostExecute(Seller marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaUpdated();
            }
        };
        task.execute(activeMarmitaria);
    }

    //FIXME retornar um objeto com status da operacao de criacao, sinalizando OK ou erro.
    public void createMarmitaria(String nome, String fone, String endereco, final OnMarmitariaCreatedCallback callback) {
        AsyncTask<String, Void, Seller> task = new AsyncTask<String, Void, Seller>() {

            @Override
            protected Seller doInBackground(String... params) {

                Seller seller = new Seller(params[0], params[1], params[2]);
                seller.setProfileId(getPrincipal());
                marmitaService.save(seller);
                return seller;
            }

            @Override
            protected void onPostExecute(Seller marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaCreated();
            }
        };
        task.execute(nome, fone, endereco);
    }

    private void setActiveMarmitaria(Seller marmitaria) {
        activeMarmitaria = marmitaria;
    }

    public Seller getActiveMarmitaria() {
        return activeMarmitaria;
    }

    public void loadMarmitariaOfLoggedUser(final OnMarmitariaLoaded callback) {
        AsyncTask<String, Void, Seller> task = new AsyncTask<String, Void, Seller>() {

            @Override
            protected Seller doInBackground(String... params) {
                String id = params[0];
                return marmitaService.find(id);
            }

            @Override
            protected void onPostExecute(Seller marmitaria) {
                setActiveMarmitaria(marmitaria);
                callback.onMarmitariaLoaded();
            }
        };

        String principal = getPrincipal();
        task.execute(principal);
    }

    private String getPrincipal() {
        if(hasLoggedUser()) {
            return this.loggedUser;
        } else {
            throw new AuthenticationRequiredException();
        }
    }

    private boolean hasLoggedUser() {
        return loggedUser != null;
    }

    public void login(String email, String password, final OnLoginFinishedCallback callback) {
        AsyncTask<String, Void, LoginFinishedEvent> task = new AsyncTask<String, Void, LoginFinishedEvent>() {

            @Override
            protected LoginFinishedEvent doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                try {
                    String accessToken = profileService.login(username, password);
                    boolean success = accessToken != null;
                    if(success) {
                        MarmitexApplication.this.loggedUser = new String(username);
                    }
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
        void onProfileCreated(ProfileCreatedEvent event);
    }

    public static interface OnLoginFinishedCallback {
        void onLoginFinished(LoginFinishedEvent event);
    }
}
