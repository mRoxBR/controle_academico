package br.ufop.controle_academico.main.tarefa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.ufop.controle_academico.R;
import br.ufop.controle_academico.main.disciplina.EditDisciplinaActivity;
import br.ufop.controle_academico.main.disciplina.SharedResourcesDisciplina;

public class ListTarefaActivity extends AppCompatActivity {

    private ListView lvTarefas;
    private TextView tvEmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tarefa);

        lvTarefas = findViewById(R.id.listTarefas);
        tvEmptyList = findViewById(R.id.tvEmptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedResourcesTarefa.getInstance().loadTarefas(this);
        if(SharedResourcesTarefa.getInstance().getTarefas().isEmpty()){
            tvEmptyList.setVisibility(View.VISIBLE);
        }else{
            tvEmptyList.setVisibility(View.INVISIBLE);
            lvTarefas.setAdapter(new TarefaAdapter(SharedResourcesTarefa.getInstance().getTarefas(), this));
            lvTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent it = new Intent(ListTarefaActivity.this, EditTarefaActivity.class);
                    it.putExtra("position", position);
                    startActivity(it);
                }
            });
        }
    }

    public void adicionar(View view) {
        Intent it = new Intent(this,AddTarefaActivity.class);
        startActivity(it);
    }
}
