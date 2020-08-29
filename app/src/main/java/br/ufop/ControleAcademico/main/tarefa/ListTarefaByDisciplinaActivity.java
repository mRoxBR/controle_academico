package br.ufop.ControleAcademico.main.tarefa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.ufop.ControleAcademico.R;

public class ListTarefaByDisciplinaActivity extends AppCompatActivity {

    private ListView lvTarefas;
    private TextView tvEmptyList;
    private String identificadorDisciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tarefa_by_disciplina);

        lvTarefas = findViewById(R.id.listTarefas);
        tvEmptyList = findViewById(R.id.tvEmptyList);

        Intent it = getIntent();
        identificadorDisciplina = it.getStringExtra("identificador");
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedResourcesTarefa.getInstance().loadTarefasByDisciplina(identificadorDisciplina,this);
        if(SharedResourcesTarefa.getInstance().getTarefas().isEmpty()){
            tvEmptyList.setVisibility(View.VISIBLE);
        }else{
            tvEmptyList.setVisibility(View.INVISIBLE);
        }
        lvTarefas.setAdapter(new TarefaAdapter(SharedResourcesTarefa.getInstance().getTarefas(), this));
        lvTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ListTarefaByDisciplinaActivity.this, EditTarefaByDisciplinaActivity.class);
                it.putExtra("position", position);
                it.putExtra("identificadorDisciplina", identificadorDisciplina);
                startActivity(it);
            }
        });
    }
}
