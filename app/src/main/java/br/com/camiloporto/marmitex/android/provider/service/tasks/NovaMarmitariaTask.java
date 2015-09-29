package br.com.camiloporto.marmitex.android.provider.service.tasks;

import android.content.Context;
import android.os.AsyncTask;
import br.com.camiloporto.marmitex.android.NovaMarmitariaActivity;
import br.com.camiloporto.marmitex.android.model.Marmitaria;
import br.com.camiloporto.marmitex.android.provider.service.RestService;
import br.com.camiloporto.marmitex.android.provider.service.UserService;

public class NovaMarmitariaTask extends AsyncTask<Context, Void, String> {

	private NovaMarmitariaActivity ctx;

	@Override
	protected String doInBackground(Context... contexto) {
		UserService userService = new UserService(RestService.getInstance());
		ctx = (NovaMarmitariaActivity) contexto[0];
		Marmitaria novaMarmitaria = ctx.getNovaMarmitaria();
		return userService.novaMarmitaria(novaMarmitaria);
	}
	
	@Override
	protected void onPostExecute(String result) {
		ctx.setNovaMarmitariaActionResponse(result);
	}

}
