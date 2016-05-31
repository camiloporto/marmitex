package br.com.camiloporto.marmitex.android.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Random;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.MarmitexApplication;
import br.com.camiloporto.marmitex.android.model.Menu;
import br.com.camiloporto.marmitex.android.model.MenuCategory;
import br.com.camiloporto.marmitex.android.model.MenuOption;
import br.com.camiloporto.marmitex.android.model.Profile;
import br.com.camiloporto.marmitex.android.model.Seller;

/**
 * Created by camiloporto on 01/12/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = MarmitexApplication.class)
public class MarmitariaServiceTest {

    private RestTemplate restTemplate;

    public MarmitariaServiceTest() {
        restTemplate = criaRestTemplate();
    }

    @Test
    public void deveCriarNovaMarmitaria() {

        final String username = randomString() + "@email.com.uk";
        final String pass = "secret";
        ProfileService profileService = new ProfileService(RuntimeEnvironment.application);
        profileService.setRestTemplate(restTemplate);
        Profile profile = new Profile();
        profile.setLogin(username);
        profile.setPass(pass);
        profileService.create(profile);
        String access_token = profileService.login(username, pass);

        MarmitaService marmitaService = new MarmitaService(RuntimeEnvironment.application);
        marmitaService.setRestTemplate(restTemplate);

        Seller s = new Seller();
        s.setName("Espaco e Sabor");
        s.setAddress("Jaguarari, 1056, lagoa nova");
        s.setProfileId(username);

        MenuOption rice = new MenuOption("Rice");
        MenuOption bean = new MenuOption("Bean");
        MenuOption meat = new MenuOption("Meat");
        MenuOption chicken = new MenuOption("Chicken");
        MenuOption salad = new MenuOption("Green Salad");

        MenuCategory carbo = new MenuCategory("Carbo");
        carbo.setOptions(Arrays.asList(rice, bean));

        MenuCategory protein = new MenuCategory("Protein");
        protein.setOptions(Arrays.asList(meat, chicken));

        MenuCategory veggs = new MenuCategory("Veggs");
        veggs.setOptions(Arrays.asList(salad));


        Menu mon = new Menu("Monday");
        mon.setCategories(Arrays.asList(carbo, protein, veggs));

        Menu tue = new Menu("Tuesday");
        tue.setCategories(Arrays.asList(carbo, protein, veggs));

        s.setMenus(Arrays.asList(mon, tue));

        marmitaService.save(s);

        Seller savedSeller = marmitaService.find(username);
        Assert.assertNotNull(savedSeller);

    }

    //FIXME change this test. use REST Enpoint instead
//    @Test
    public void devePersistirMarmitaria() {
        MarmitaService marmitaService = new MarmitaService(RuntimeEnvironment.application);
        Seller s = new Seller();
        s.setName("Espaco e Sabor");
        s.setAddress("Jaguarari, 1056, lagoa nova");
        String profileId = "camiloporto@email.com";
        s.setProfileId(profileId);

        MenuOption rice = new MenuOption("Rice");
        MenuOption bean = new MenuOption("Bean");
        MenuOption meat = new MenuOption("Meat");
        MenuOption chicken = new MenuOption("Chicken");
        MenuOption salad = new MenuOption("Green Salad");

        MenuCategory carbo = new MenuCategory("Carbo");
        carbo.setOptions(Arrays.asList(rice, bean));

        MenuCategory protein = new MenuCategory("Protein");
        protein.setOptions(Arrays.asList(meat, chicken));

        MenuCategory veggs = new MenuCategory("Veggs");
        veggs.setOptions(Arrays.asList(salad));


        Menu mon = new Menu("Monday");
        mon.setCategories(Arrays.asList(carbo, protein, veggs));

        Menu tue = new Menu("Tuesday");
        tue.setCategories(Arrays.asList(carbo, protein, veggs));

        s.setMenus(Arrays.asList(mon, tue));

        //save
        marmitaService.save(s);

        s.setAddress("Novo Endereco");
        s.setName("Novo Nome");

        //update
        marmitaService.save(s);

    }

    //FIXME pass this test
    @Test
    public void shouldLoadLunchBoxSeller() {
        ProfileService profileService = new ProfileService(RuntimeEnvironment.application);
        profileService.setRestTemplate(restTemplate);
        String username = "camiloporto@email.com";
        String access_token = profileService.login(username, "1234567");

        MarmitaService marmitaService = new MarmitaService(RuntimeEnvironment.application);
        marmitaService.setRestTemplate(restTemplate);
        Seller marmitaria = marmitaService.find(username);
        Assert.assertNotNull(marmitaria);

    }

    private RestTemplate criaRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        //FIXME create proxy only on local environment.
        Proxy proxy= new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 3128));
        requestFactory.setProxy(proxy);
        RestTemplate rt = new RestTemplate(requestFactory);

        return rt;
    }

    public String randomString() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(20) + 97);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
