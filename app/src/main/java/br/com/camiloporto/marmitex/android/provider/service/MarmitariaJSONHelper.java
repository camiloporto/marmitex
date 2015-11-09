package br.com.camiloporto.marmitex.android.provider.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 28/10/15.
 */
public class MarmitariaJSONHelper {
    private Context context;

    MarmitariaJSONHelper(Context context) {

        this.context = context;
    }

    //FIXME tornar essas Operacoes Asincronas
    public void persistMarmitaria(Marmitaria m) {
        final Gson gson = new Gson();
        String json = gson.toJson(m);
        Log.i("JSONHeper", "json gerado: " + json);
        final String fileName = m.getLogin() + ".json";
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
        } catch (IOException e) {
            Log.e("MarmitariaJSONHelper", e.toString());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                Log.e("MarmitariaJSONHelper", e.toString());
            }
        }


    }

    public Marmitaria readMarmitaria(String userId) {
        FileInputStream fis = null;
        Marmitaria m = null;
        try {
            fis = context.openFileInput(userId + ".json");
            Reader reader = new InputStreamReader(fis);
            Gson gson = new Gson();
            m = gson.fromJson(reader, Marmitaria.class);
            Log.i("JSONHeper", "marmitaria recuperada: " + m.getLogin());

        } catch (IOException e) {
            //do nothing
        } finally {
            if(fis != null) try {
                fis.close();
            } catch (IOException e) {
                //impossivel fechar stream
            }
        }
        return m;
    }

}
