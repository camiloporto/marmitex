package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MarmitexActivity extends Activity {

	private static final String TAG = "MarmitexActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marmitex);
		Button novaMarmitaria = (Button) findViewById(R.id.btnNovaMarmitaria);
		Button cardapios = (Button) findViewById(R.id.btnCardapios);
		cardapios.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, CardapioActivity.class);
				startActivity(i);
			}
		});
		
		novaMarmitaria.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MarmitexActivity.this, NovaMarmitariaActivity.class);
				startActivity(i);
			}
		});
	}
	
}
