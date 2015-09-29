package br.com.camiloporto.marmitex.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class NovaMarmitariaFragment extends Fragment {
	
	interface OnNovaMarmitariaCreated {
		void novaMarmitariaCreated(Marmitaria m);
	}
	
	private Marmitaria mNovaMarmitaria;
	
	private EditText mNome;
	private EditText mEndereco;
	private EditText mTelefone;
	private EditText mLogin;
	private EditText mSenha;
	private EditText mCpfOuCnpj;
	private EditText mHorarioFuncionamento;
	private Button mSaveButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mNovaMarmitaria = new Marmitaria(null, null, null);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_novamarmitaria, container, false);
		mNome = (EditText) v.findViewById(R.id.novamarmitaria_nome);
		mEndereco = (EditText) v.findViewById(R.id.novamarmitaria_endereco);
		mTelefone = (EditText) v.findViewById(R.id.novamarmitaria_telefone);
		mLogin = (EditText) v.findViewById(R.id.novamarmitaria_login);
		mSenha = (EditText) v.findViewById(R.id.novamarmitaria_senha);
		mCpfOuCnpj = (EditText) v.findViewById(R.id.novamarmitaria_id);
		mHorarioFuncionamento = (EditText) v.findViewById(R.id.novamarmitaria_funcionamento);
		mSaveButton = (Button) v.findViewById(R.id.novamarmitaria_newButton);
		mSaveButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mNovaMarmitaria.setEndereco(mEndereco.getText().toString());
				mNovaMarmitaria.setNome(mNome.getText().toString());
				mNovaMarmitaria.setTelefone(mTelefone.getText().toString());
				mNovaMarmitaria.setId(mCpfOuCnpj.getText().toString());
				mNovaMarmitaria.setLogin(mLogin.getText().toString());
				mNovaMarmitaria.setSenha(mSenha.getText().toString());
				mNovaMarmitaria.setFuncionamento(mHorarioFuncionamento.getText().toString());
				
				((OnNovaMarmitariaCreated)getActivity()).novaMarmitariaCreated(mNovaMarmitaria);
			}
		});
		return v;
	}

}
