package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.InstrumentationTestRunner;
import android.test.mock.MockApplication;
import android.test.mock.MockContext;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;

import br.com.camiloporto.marmitex.android.BuildConfig;
import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoItems;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

import static org.junit.Assert.*;

/**
 * Created by camiloporto on 08/11/15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MarmitaServiceTest {


    @Test
    public void devePersistirNovaMarmitaria() throws MalformedURLException {

        Marmitaria m = new Marmitaria("camilo", "12345", "tereza campos");
        Cardapio cardapio = m.createCardapio("Segunda-Feira");
        GrupoItems carnes = cardapio.addGrupo("Carnes");
        carnes.newItem("Patinho");
        carnes.newItem("Frango Grelhado");


        String result = new MarmitariaJSONHelper(null)
                .persistRemote(m);

        System.out.println(result);

    }

    @Test
    public void testFinaAall() {
        String result = RestService.getInstance().get("https://camiloporto.cloudant.com/marmitex-dev/_all_docs");
        System.out.println(result);
    }

}