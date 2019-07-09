package br.ufop.controle_academico.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufop.controle_academico.connection.Database;
import br.ufop.controle_academico.models.bean.Disciplina;

public class DisciplinaDAO {

    private SQLiteDatabase db;
    private Database database;

    public DisciplinaDAO(Context context) {
        this.database = new Database(context);
    }

    public long insert(Disciplina disciplina){
        ContentValues values = new ContentValues();
        long result;

        db = database.getWritableDatabase();
        values.put("identificador", disciplina.getIdentificador());
        values.put("nome", disciplina.getNome());
        values.put("semestre",disciplina.getSemestre());
        values.put("faltas", disciplina.getFaltas());
        values.put("limite_faltas",disciplina.getLimiteFaltas());
        values.put("meta",disciplina.getMeta());
        values.put("situacao",disciplina.getSituacao());

        result = db.insert("disciplina", null, values);
        db.close();

        return result;
    }

    public Cursor findAll(){
        String[] campos = {"identificador","nome", "semestre", "faltas", "limite_faltas",
        "meta", "situacao"};
        Cursor cursor;

        db = database.getReadableDatabase();
        cursor = db.query("disciplina", campos, null, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findByIdentificador(String identificador){
        String[] campos = {"nome", "semestre", "faltas", "limite_faltas",
                "meta", "situacao"};
        Cursor cursor;
        String where = "identificador = " + "'" + identificador + "'";

        db = database.getReadableDatabase();
        cursor = db.query("disciplina", campos, where, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findNome(){
        String[] campos = {"nome"};
        Cursor cursor;

        db = database.getReadableDatabase();
        cursor = db.query("disciplina", campos, null, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findNomeByIdentificador(String identificador){
        String[] campos = {"nome"};
        Cursor cursor;
        String where = "identificador = " + "'" + identificador + "'";

        db = database.getReadableDatabase();
        cursor = db.query("disciplina", campos, where, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findIdentificadorByNome(String nome){
        String[] campos = {"identificador"};
        Cursor cursor;
        String where = "nome = " + "'" + nome + "'";

        db = database.getReadableDatabase();
        cursor = db.query("disciplina", campos, where, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void edit(Disciplina disciplina, String identificadorAntigo){
        ContentValues values = new ContentValues();
        String where = "identificador = " + "'" + identificadorAntigo + "'";

        db = database.getWritableDatabase();
        values.put("identificador", disciplina.getIdentificador());
        values.put("nome", disciplina.getNome());
        values.put("semestre",disciplina.getSemestre());
        values.put("faltas", disciplina.getFaltas());
        values.put("limite_faltas",disciplina.getLimiteFaltas());
        values.put("meta",disciplina.getMeta());
        values.put("situacao",disciplina.getSituacao());

        db.update("disciplina", values, where, null);
        db.close();
    }

    public void delete(String identificador){
        String where = "identificador = " + "'" + identificador + "'";
        db = database.getReadableDatabase();
        db.delete("disciplina", where, null);
        db.close();
    }
}
