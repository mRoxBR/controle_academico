package br.ufop.controle_academico.connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String SCRIPT_CREATE_TABLE_DISCIPLINA = "CREATE TABLE disciplina(" +
            "identificador TEXT PRIMARY KEY NOT NULL," +
            "nome TEXT NOT NULL UNIQUE," +
            "semestre INTEGER NOT NULL," +
            "faltas INTEGER NOT NULL," +
            "limite_faltas INTEGER NOT NULL," +
            "meta REAL NOT NULL," +
            "situacao TEXT NOT NULL" +
            ");";
    private static final String SCRIPT_DELETE_TABLE_DISCIPLINA = "DROP TABLE IF EXISTS disciplina";

    private static final String SCRIPT_CREATE_TABLE_TAREFA = "CREATE TABLE tarefa(" +
            "id INTEGER PRIMARY KEY NOT NULL," +
            "identificador_disciplina TEXT NOT NULL," +
            "descricao TEXT NOT NULL," +
            "valor REAL NOT NULL," +
            "nota REAL," +
            "data_entrega TEXT NOT NULL," +
            "tipo TEXT NOT NULL," +
            "prioridade REAL NOT NULL, " +
            "FOREIGN KEY (identificador_disciplina) " +
            "REFERENCES disciplina(identificador) " +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE" +
            ");";
    private static final String SCRIPT_DELETE_TABLE_TAREFA = "DROP TABLE IF EXISTS tarefa";

    private static final String DB_NAME = "controle_academico.db";
    private static final int DB_VERSION = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_TABLE_DISCIPLINA);
        db.execSQL(SCRIPT_CREATE_TABLE_TAREFA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SCRIPT_DELETE_TABLE_TAREFA);
        db.execSQL(SCRIPT_DELETE_TABLE_DISCIPLINA);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = ON");
    }
}
