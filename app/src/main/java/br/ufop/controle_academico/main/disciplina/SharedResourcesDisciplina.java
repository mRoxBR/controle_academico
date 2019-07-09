package br.ufop.controle_academico.main.disciplina;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import br.ufop.controle_academico.models.bean.Disciplina;
import br.ufop.controle_academico.models.dao.DisciplinaDAO;

public class SharedResourcesDisciplina {
    public static SharedResourcesDisciplina sharedResourcesDisciplina = null;
    public static ArrayList<Disciplina> disciplinas;

    private SharedResourcesDisciplina(){
        disciplinas = new ArrayList<>();
    }

    public static SharedResourcesDisciplina getInstance(){
        if(sharedResourcesDisciplina == null){
            sharedResourcesDisciplina = new SharedResourcesDisciplina();
        }
        return sharedResourcesDisciplina;
    }

    public ArrayList<Disciplina> getDisciplinas(){
        return disciplinas;
    }

    public void loadDisciplinas(Context context){
        disciplinas.clear();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        Cursor cursor = disciplinaDAO.findAll();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Disciplina disciplina = new Disciplina();

            String identificador = cursor.getString(cursor.getColumnIndex("identificador"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            int semestre = cursor.getInt(cursor.getColumnIndex("semestre"));
            int faltas = cursor.getInt(cursor.getColumnIndex("faltas"));
            int limiteFaltas = cursor.getInt(cursor.getColumnIndex("limite_faltas"));
            double meta = cursor.getDouble(cursor.getColumnIndex("meta"));
            String situacao = cursor.getString(cursor.getColumnIndex("situacao"));

            disciplina.setIdentificador(identificador);
            disciplina.setNome(nome);
            disciplina.setSemestre(semestre);
            disciplina.setFaltas(faltas);
            disciplina.setLimiteFaltas(limiteFaltas);
            disciplina.setMeta(meta);
            disciplina.setSituacao(situacao);

            disciplinas.add(disciplina);
        }
    }

    public ArrayList<String> getNomeDisciplinas(Context context){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        Cursor cursor = disciplinaDAO.findNome();
        ArrayList<String> nomeDisciplinas = new ArrayList<>();

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            nomeDisciplinas.add(nome);
        }
        return nomeDisciplinas;
    }
}
