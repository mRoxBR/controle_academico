package br.ufop.ControleAcademico.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.ufop.ControleAcademico.connection.Database;
import br.ufop.ControleAcademico.models.bean.Tarefa;

public class TarefaDAO {
    private SQLiteDatabase db;
    private Database database;

    public TarefaDAO(Context context){
        database = new Database(context);
    }

    public long insert(Tarefa tarefa){
        ContentValues values = new ContentValues();
        long result;

        db = database.getWritableDatabase();
        values.put("identificador_disciplina",tarefa.getDisciplina().getIdentificador());
        values.put("descricao", tarefa.getDescricao());
        values.put("valor",tarefa.getValor());
        values.put("nota",tarefa.getNota());
        values.put("data_entrega", tarefa.getDataEntrega());
        values.put("tipo", tarefa.getTipo());
        values.put("prioridade", tarefa.getPrioridade());

        result = db.insert("tarefa", "nota", values);
        db.close();

        return result;
    }

    public Cursor findAll(){
        String[] campos = {"id","identificador_disciplina", "descricao", "valor", "nota",
                "data_entrega", "tipo", "prioridade"};
        Cursor cursor;

        db = database.getReadableDatabase();
        cursor = db.query("tarefa", campos, null, null, null,
                null, "data_entrega ASC", null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findById(int id){
        String[] campos = {"id","identificador_disciplina", "descricao", "valor", "nota",
                "data_entrega", "tipo", "prioridade"};
        Cursor cursor;
        String where = "id = " + id;

        db = database.getReadableDatabase();
        cursor = db.query("tarefa", campos, where, null, null,
                null, "data_entrega ASC", null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findByIdentificadorDisciplina(String identificadorDisciplina){
        String[] campos = {"id","identificador_disciplina", "descricao", "valor", "nota",
                "data_entrega", "tipo", "prioridade"};
        Cursor cursor;
        String where = "identificador_disciplina = " + "'" + identificadorDisciplina + "'";

        db = database.getReadableDatabase();
        cursor = db.query("tarefa", campos, where, null, null,
                null, "data_entrega ASC", null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findByTipo(String tipo){
        String[] campos = {"id","identificador_disciplina", "descricao", "valor", "nota",
                "data_entrega", "tipo", "prioridade"};
        Cursor cursor;
        String where = "tipo = " + "'" + tipo + "' AND nota != -1";

        db = database.getReadableDatabase();
        cursor = db.query("tarefa", campos, where, null, null,
                null, "data_entrega ASC", null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor findNotasDescricaoByIdentificadorDisciplina(String identificadorDisciplina){
        String[] campos = {"identificador_disciplina", "nota", "descricao"};
        Cursor cursor;
        String where = "identificador_disciplina = " + "'" + identificadorDisciplina + "'" +
                " AND nota != -1";

        db = database.getReadableDatabase();
        cursor = db.query("tarefa", campos, where, null, null,
                null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }


    public Cursor findSomaNotasByIdentificadorDisciplina(String identificadorDisciplina){
        Cursor cursor;
        db = database.getReadableDatabase();
        cursor = db.rawQuery("SELECT SUM(nota) AS soma_notas FROM tarefa " +
                "WHERE identificador_disciplina = ? AND nota != ?",
                new String[]{identificadorDisciplina, "-1"});

        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void edit(Tarefa tarefa){
        ContentValues values = new ContentValues();
        String where = "id = " + tarefa.getId();

        db = database.getWritableDatabase();
        values.put("identificador_disciplina",tarefa.getDisciplina().getIdentificador());
        values.put("descricao", tarefa.getDescricao());
        values.put("valor",tarefa.getValor());
        values.put("nota",tarefa.getNota());
        values.put("data_entrega", tarefa.getDataEntrega());
        values.put("tipo", tarefa.getTipo());
        values.put("prioridade", tarefa.getPrioridade());

        db.update("tarefa", values, where, null);
        db.close();
    }

    public void delete(int id){
        String where = "id = " + id;
        db = database.getReadableDatabase();
        db.delete("tarefa", where, null);
        db.close();
    }
}
