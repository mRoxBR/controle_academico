package br.ufop.ControleAcademico.main.tarefa;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.ufop.ControleAcademico.models.bean.Disciplina;
import br.ufop.ControleAcademico.models.bean.Tarefa;
import br.ufop.ControleAcademico.models.dao.TarefaDAO;

public class SharedResourcesTarefa {
    public static SharedResourcesTarefa sharedResourcesTarefa = null;
    public static ArrayList<Tarefa> tarefas;

    private SharedResourcesTarefa(){
        tarefas = new ArrayList<>();
    }

    public static SharedResourcesTarefa getInstance(){
        if(sharedResourcesTarefa == null){
            sharedResourcesTarefa = new SharedResourcesTarefa();
        }
        return sharedResourcesTarefa;
    }

    public ArrayList<Tarefa> getTarefas() {
        return tarefas;
    }

    public void loadTarefas(Context context){
        tarefas.clear();
        TarefaDAO tarefaDAO = new TarefaDAO(context);
        Cursor cursor = tarefaDAO.findAll();

        while(!cursor.isAfterLast()){
            Tarefa tarefa = new Tarefa();
            Disciplina disciplina = new Disciplina();

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String identificador_disciplina = cursor.getString(cursor.getColumnIndex("identificador_disciplina"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
            double nota = cursor.getDouble(cursor.getColumnIndex("nota"));
            String data_entrega = cursor.getString(cursor.getColumnIndex("data_entrega"));
            String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            double prioridade = cursor.getDouble(cursor.getColumnIndex("prioridade"));

            disciplina.setIdentificador(identificador_disciplina);
            tarefa.setId(id);
            tarefa.setDisciplina(disciplina);
            tarefa.setDescricao(descricao);
            tarefa.setValor(valor);
            tarefa.setNota(nota);
            tarefa.setDataEntrega(data_entrega);
            tarefa.setTipo(tipo);
            tarefa.setPrioridade(prioridade);

            tarefas.add(tarefa);
            cursor.moveToNext();
        }


    }

    public void loadTarefasByDisciplina(String identificadorDisciplina, Context context){
        tarefas.clear();
        TarefaDAO tarefaDAO = new TarefaDAO(context);
        Cursor cursor = tarefaDAO.findByIdentificadorDisciplina(identificadorDisciplina);

        while(!cursor.isAfterLast()){
            Tarefa tarefa = new Tarefa();
            Disciplina disciplina = new Disciplina();

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
            double nota = cursor.getDouble(cursor.getColumnIndex("nota"));
            String data_entrega = cursor.getString(cursor.getColumnIndex("data_entrega"));
            String tipo = cursor.getString(cursor.getColumnIndex("tipo"));
            double prioridade = cursor.getDouble(cursor.getColumnIndex("prioridade"));

            disciplina.setIdentificador(identificadorDisciplina);
            tarefa.setId(id);
            tarefa.setDisciplina(disciplina);
            tarefa.setDescricao(descricao);
            tarefa.setValor(valor);
            tarefa.setNota(nota);
            tarefa.setDataEntrega(data_entrega);
            tarefa.setTipo(tipo);
            tarefa.setPrioridade(prioridade);

            tarefas.add(tarefa);
            cursor.moveToNext();
        }
    }

    public void loadTarefasByTipo(String tipo, Context context){
        tarefas.clear();
        TarefaDAO tarefaDAO = new TarefaDAO(context);
        Cursor cursor = tarefaDAO.findByTipo(tipo);

        while(!cursor.isAfterLast()){
            Tarefa tarefa = new Tarefa();
            Disciplina disciplina = new Disciplina();

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String identificadorDisciplina =  cursor.getString(cursor.getColumnIndex("identificador_disciplina"));
            String descricao = cursor.getString(cursor.getColumnIndex("descricao"));
            double valor = cursor.getDouble(cursor.getColumnIndex("valor"));
            double nota = cursor.getDouble(cursor.getColumnIndex("nota"));
            String data_entrega = cursor.getString(cursor.getColumnIndex("data_entrega"));
            double prioridade = cursor.getDouble(cursor.getColumnIndex("prioridade"));

            disciplina.setIdentificador(identificadorDisciplina);
            tarefa.setId(id);
            tarefa.setDisciplina(disciplina);
            tarefa.setDescricao(descricao);
            tarefa.setValor(valor);
            tarefa.setNota(nota);
            tarefa.setDataEntrega(data_entrega);
            tarefa.setTipo(tipo);
            tarefa.setPrioridade(prioridade);

            tarefas.add(tarefa);
            cursor.moveToNext();
        }
    }
}
