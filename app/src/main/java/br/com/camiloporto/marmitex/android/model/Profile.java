package br.com.camiloporto.marmitex.android.model;

/**
 * Created by ur42 on 22/04/2016.
 */
public class Profile {

    private String id;
    private String login;
    private String pass;

    public Profile() {
    }

    public Profile(String login, String pass) {

        this.login = login;
        this.pass = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
