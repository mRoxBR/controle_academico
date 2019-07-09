package br.ufop.controle_academico.main.disciplina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import br.ufop.controle_academico.R;

public class ListDisciplinaActivity extends AppCompatActivity {

    private ListView lvDisciplinas;
    private TextView tvEmptyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_disciplina);

        lvDisciplinas = findViewById(R.id.listDisciplinas);
        tvEmptyList = findViewById(R.id.tvEmptyList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedResourcesDisciplina.getInstance().loadDisciplinas(this);
        if(SharedResourcesDisciplina.getInstance().getDisciplinas().isEmpty()){
            tvEmptyList.setVisibility(View.VISIBLE);
        }else{
            tvEmptyList.setVisibility(View.INVISIBLE);
        }
        lvDisciplinas.setAdapter(new DisciplinaAdapter(SharedResourcesDisciplina.getInstance().getDisciplinas(),this));
        lvDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(ListDisciplinaActivity.this, EditDisciplinaActivity.class);
                it.putExtra("position", position);
                startActivity(it);
            }
        });
    }

    public void adicionar(View view) {
        Intent it = new Intent(this,AddDisciplinaActivity.class);
        startActivity(it);
    }
}
