package br.com.camiloporto.marmitex.android.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Random;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.MarmitexApplication;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.provider.service.ProfileService;

/**
 * Created by ur42 on 25/04/2016.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = MarmitexApplication.class)
public class ProfileServiceTest {

    private RestTemplate restTemplate;


    public ProfileServiceTest() {
        restTemplate = criaRestTemplate();
    }

    @Test
    public void shouldCreateNewProfile() {

        ProfileService profileService = new ProfileService(RuntimeEnvironment.application);
        profileService.setRestTemplate(restTemplate);
        Profile profile = new Profile();
        profile.setLogin(randomString() + "@email.com.uk");
        profile.setPass("secret");
        profileService.create(profile);
    }

    @Test
    public void shouldLoginExistentuser() {

        ProfileService profileService = new ProfileService(RuntimeEnvironment.application);
        profileService.setRestTemplate(restTemplate);
        String accessToken = profileService.login("camiloporto@email.com", "1234567");
        Assert.assertNotNull(accessToken);
    }

    @Test
    public void shouldHandleBadRequestErrors() {

        ProfileService profileService = new ProfileService(RuntimeEnvironment.application);
        Profile profile = new Profile();
        profile.setLogin("");
        profile.setPass("secret");
        try {
            profileService.create(profile);
            Assert.fail("it should raised exception");
        }catch (MarmitexException e) {
            List<String> errors = e.getErrors();
            Assert.assertEquals(1, errors.size());
        }
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

    private RestTemplate criaRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        //FIXME create proxy only on local environment.
        Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
        requestFactory.setProxy(proxy);
        RestTemplate rt = new RestTemplate(requestFactory);

        return rt;
    }
}
