package br.com.camiloporto.marmitex.android.service;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.MarmitexApplication;
import br.com.camiloporto.marmitex.android.R;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.MarmitaService;
import br.com.camiloporto.marmitex.android.repository.InMemoryMarmitariaRepository;

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

    //    @Test
    public void deveCriarNovaMarmitaria() {
        MarmitaService marmitaService = new MarmitaService(new InMemoryMarmitariaRepository());
        Marmitaria m = marmitaService.create("Espaco Sabor", "3245-6778", "Jaguarai, 4546");

        Assert.assertNotNull(m.getId());

        Marmitaria queried = marmitaService.find(m.getId());
        Assert.assertNotNull(queried);
        Assert.assertEquals(m.getId(), queried.getId());

    }

//    @Test
    public void devePersistirMarmitaria() {
        MarmitaService marmitaService = new MarmitaService(new InMemoryMarmitariaRepository());
        Marmitaria m = marmitaService.create("Espaco Sabor", "3245-6778", "Jaguarai, 4546");

        m.setEndereco("Novo Endereco");
        m.setNome("Novo Nome");

        marmitaService.save(m);

        Marmitaria updated = marmitaService.find(m.getId());
        Assert.assertEquals("Novo Endereco", updated.getEndereco());
        Assert.assertEquals("Novo Nome", updated.getNome());
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
        Marmitaria marmitaria = marmitaService.find(username);
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
}
