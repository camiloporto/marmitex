package br.com.camiloporto.marmitex.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Random;

import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by ur42 on 10/05/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginActivityTest {

    @Test
    public void shouldCreateNewProfile() {
        LoginActivity loginActivity = Robolectric.setupActivity(LoginActivity.class);
        String user = randomString() + "@email.com";
        new LoginActivityTestObject(loginActivity)
                .fillForm(user, "secret")
                .clickNewProfile();

        Intent expectedIntent = new Intent(loginActivity, MarmitexActivity.class);
        checkAccessTokenIsOnSharedPreferences(user);
        Assert.assertEquals(shadowOf(loginActivity).getNextStartedActivity(), expectedIntent);

    }

    public String randomString() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void checkAccessTokenIsOnSharedPreferences(String username) {
        SharedPreferences prefs =
                RuntimeEnvironment.application
                        .getSharedPreferences("access_tokens", Context.MODE_PRIVATE);
        String actualAccessToken = prefs.getString(username, null);
        Assert.assertNotNull(actualAccessToken);
    }

    private static class LoginActivityTestObject {

        private LoginActivity loginActivity;

        public LoginActivityTestObject(LoginActivity loginActivity) {

            this.loginActivity = loginActivity;
        }

        public LoginActivityTestObject fillForm(String login, String pass) {
            EditText emailField = (EditText) loginActivity.findViewById(R.id.email);
            EditText passField = (EditText) loginActivity.findViewById(R.id.password);
            emailField.setText(login);
            passField.setText(pass);
            return this;
        }

        public LoginActivityTestObject clickNewProfile() {
            Button signInButton = (Button) loginActivity.findViewById(R.id.sign_up_button);
            signInButton.performClick();
            return this;
        }
    }
}
