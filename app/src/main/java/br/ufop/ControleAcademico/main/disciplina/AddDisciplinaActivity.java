package br.ufop.ControleAcademico.main.disciplina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufop.ControleAcademico.enums.Situacao;
import br.ufop.ControleAcademico.R;
import br.ufop.ControleAcademico.models.bean.Disciplina;
import br.ufop.ControleAcademico.models.dao.DisciplinaDAO;

public class AddDisciplinaActivity extends AppCompatActivity {

    private EditText etIdentificador;
    private EditText etNome;
    private EditText etSemestre;
    private EditText etFaltas;
    private EditText etLimiteFaltas;
    private EditText etMeta;
    private Spinner spSituacao;
    private String situacoes[] = {Situacao.EM_ANDAMENTO.getDescricao(), Situacao.ENCERRADA.getDescricao()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_disciplina);
        etIdentificador = findViewById(R.id.etIdentificador);
        etNome = findViewById(R.id.etNome);
        etSemestre = findViewById(R.id.etSemestre);
        etFaltas = findViewById(R.id.etFaltas);
        etLimiteFaltas = findViewById(R.id.etLimiteFaltas);
        etMeta = findViewById(R.id.etMeta);

        spSituacao = findViewById(R.id.spSituacao);
        ArrayAdapter<String> adapterSpinner =
                new ArrayAdapter<>(this, R.layout.customized_spinner_item, situacoes);
        spSituacao.setAdapter(adapterSpinner);
    }


    public void adicionar(View view) {
        Disciplina disciplina = new Disciplina();
        try {
            String identificador = etIdentificador.getText().toString();
            String nome = etNome.getText().toString();
            int semestre = Integer.parseInt(etSemestre.getText().toString());
            int faltas = Integer.parseInt(etFaltas.getText().toString());
            int limiteFaltas = Integer.parseInt(etLimiteFaltas.getText().toString());
            double meta = Double.parseDouble(etMeta.getText().toString());
            String situacao = spSituacao.getSelectedItem().toString();

            disciplina.setIdentificador(identificador);
            disciplina.setNome(nome);
            disciplina.setSemestre(semestre);
            disciplina.setFaltas(faltas);
            disciplina.setLimiteFaltas(limiteFaltas);
            disciplina.setMeta(meta);
            disciplina.setSituacao(situacao);
        }catch (Exception e){
            Toast.makeText(this,"Verifique se os campos preenchidos correspondem ao tipo correto.",
                    Toast.LENGTH_SHORT).show();
        }

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        long resultado = disciplinaDAO.insert(disciplina);
        if(resultado != -1){
            SharedResourcesDisciplina.getInstance().getDisciplinas().add(disciplina);
            Toast.makeText(this, "Disciplina cadastrada com sucesso!",Toast.LENGTH_SHORT).show();
            etIdentificador.setText("");
            etNome.setText("");
            etSemestre.setText("");
            etFaltas.setText("");
            etLimiteFaltas.setText("");
            etMeta.setText("");
            etIdentificador.requestFocus();
        }else{
            Toast.makeText(this,"Ocorreu um erro ao inserir a disciplina.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
