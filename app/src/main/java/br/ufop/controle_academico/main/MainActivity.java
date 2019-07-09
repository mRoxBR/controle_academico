package br.ufop.controle_academico.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import br.ufop.controle_academico.R;
import br.ufop.controle_academico.main.disciplina.ListDisciplinaActivity;
import br.ufop.controle_academico.main.grafico.MainGraficoActivity;
import br.ufop.controle_academico.main.tarefa.ListTarefaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startDisciplinas(View view) {
        Intent it = new Intent(this, ListDisciplinaActivity.class);
        startActivity(it);
    }

    public void startGraficos(View view){
        Intent it = new Intent(this, MainGraficoActivity.class);
        startActivity(it);
    }

    public void startTarefas(View view) {
        Intent it = new Intent(this, ListTarefaActivity.class);
        startActivity(it);
    }
}
