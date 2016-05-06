package br.com.camiloporto.marmitex.android.service;

/**
 * Created by ur42 on 03/05/2016.
 */
public class SecurityContext {

    private static String acessToken;

    public static String getAccessToken() {
        return acessToken;
    }

    public static void setAcessToken(String acessToken) {
        SecurityContext.acessToken = acessToken;
    }
}
