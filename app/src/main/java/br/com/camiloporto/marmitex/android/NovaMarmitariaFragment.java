package br.com.camiloporto.marmitex.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

public class NovaMarmitariaFragment extends Fragment {
	
	interface NovaMarmitariaFragmentCallbacks {
		void saveButtonClicked();
	}
	
	private Marmitaria marmitaria;
	
	private EditText nome;
	private EditText endereco;
	private EditText telefone;
	private EditText login;
	private EditText senha;
	private EditText cpfOuCnpj;
	private EditText horarioFuncionamento;
	private Button saveButton;

	private NovaMarmitariaFragmentCallbacks mCallbacks;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NovaMarmitariaFragmentCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("Class" + activity.getClass().getName() + " must implement " +
			NovaMarmitariaFragmentCallbacks.class.getName());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_novamarmitaria, container, false);
		nome = (EditText) v.findViewById(R.id.novamarmitaria_nome);
		endereco = (EditText) v.findViewById(R.id.novamarmitaria_endereco);
		telefone = (EditText) v.findViewById(R.id.novamarmitaria_telefone);
		login = (EditText) v.findViewById(R.id.novamarmitaria_login);
		senha = (EditText) v.findViewById(R.id.novamarmitaria_senha);
		cpfOuCnpj = (EditText) v.findViewById(R.id.novamarmitaria_id);
		horarioFuncionamento = (EditText) v.findViewById(R.id.novamarmitaria_funcionamento);

		atualizaFormularioComMarmitariaAtivaSeExistir();

		saveButton = (Button) v.findViewById(R.id.novamarmitaria_newButton);
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCallbacks.saveButtonClicked();
			}
		});
		return v;
	}

	private void atualizaFormularioComMarmitariaAtivaSeExistir() {
		if(marmitaria != null) {
			nome.setText(marmitaria.getNome());
			endereco.setText(marmitaria.getEndereco());
			telefone.setText(marmitaria.getTelefone());
			horarioFuncionamento.setText(marmitaria.getFuncionamento());
			//FIXME preencher demais dados.
			// mudar nome de de campos de cpf/cnpj do modelo Marmitaria. Era id antes..
		}
	}


	public void setMarmitaria(Marmitaria mNovaMarmitaria) {
		this.marmitaria = mNovaMarmitaria;
	}



	public EditText getNome() {
		return nome;
	}

	public EditText getEndereco() {
		return endereco;
	}

	public EditText getTelefone() {
		return telefone;
	}

	public EditText getLogin() {
		return login;
	}

	public EditText getSenha() {
		return senha;
	}

	public EditText getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public EditText getHorarioFuncionamento() {
		return horarioFuncionamento;
	}

	public Button getSaveButton() {
		return saveButton;
	}
}
