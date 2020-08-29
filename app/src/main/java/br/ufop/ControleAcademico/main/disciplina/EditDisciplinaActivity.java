package br.ufop.ControleAcademico.main.disciplina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import br.ufop.ControleAcademico.enums.Situacao;
import br.ufop.ControleAcademico.R;
import br.ufop.ControleAcademico.main.tarefa.ListTarefaByDisciplinaActivity;
import br.ufop.ControleAcademico.models.bean.Disciplina;
import br.ufop.ControleAcademico.models.dao.DisciplinaDAO;

public class EditDisciplinaActivity extends AppCompatActivity {

    private int position;
    private String identificadorAntigo;
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

        setContentView(R.layout.activity_edit_disciplina);
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

        Intent it = getIntent();
        position = it.getIntExtra("position",0);

        identificadorAntigo  = SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getIdentificador();
        etIdentificador.setText(identificadorAntigo);

        etNome.setText(SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getNome());

        etSemestre.setText(""+SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getSemestre());
        etFaltas.setText(""+SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getFaltas());
        etLimiteFaltas.setText(""+SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getLimiteFaltas());
        etMeta.setText(""+SharedResourcesDisciplina.getInstance()
                .getDisciplinas()
                .get(position)
                .getMeta());

        String situacao = SharedResourcesDisciplina.getInstance().
                getDisciplinas()
                .get(position)
                .getSituacao();
        int index = situacao.equals(situacoes[0]) ? 0 : 1;
        spSituacao.setSelection(index);
    }

    public void editar(View view) {
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
            Toast.makeText(this,"Verifique se os campos estão preenchidos corretamente.",
                    Toast.LENGTH_SHORT).show();
        }

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        disciplinaDAO.edit(disciplina, identificadorAntigo);
        SharedResourcesDisciplina.getInstance().getDisciplinas().set(position, disciplina);

        Toast.makeText(this, "Disciplina editada com sucesso!",Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deletar(View view) {
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        disciplinaDAO.delete(identificadorAntigo);
        SharedResourcesDisciplina.getInstance().getDisciplinas().remove(position);
        Toast.makeText(this, "Disciplina removida com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void tarefas(View view) {
        Intent it = new Intent(this, ListTarefaByDisciplinaActivity.class);
        it.putExtra("identificador", identificadorAntigo);
        startActivity(it);
    }
}
