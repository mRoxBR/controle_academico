package br.ufop.controle_academico.main.disciplina;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufop.controle_academico.R;
import br.ufop.controle_academico.models.bean.Disciplina;
import br.ufop.controle_academico.models.dao.TarefaDAO;

public class DisciplinaAdapter extends BaseAdapter {

    private ArrayList<Disciplina> disciplinas;
    Context context;

    public DisciplinaAdapter(ArrayList<Disciplina> disciplinas, Context context) {
        this.disciplinas = disciplinas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return disciplinas.size();
    }

    @Override
    public Object getItem(int position) {
        return disciplinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Disciplina disciplina = disciplinas.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.disciplina_adapter, null);

        TarefaDAO tarefaDAO = new TarefaDAO(this.context);
        Cursor cursor = tarefaDAO.findSomaNotasByIdentificadorDisciplina(disciplina.getIdentificador());
        double nota = cursor.getDouble(cursor.getColumnIndex("soma_notas"));

        TextView tvNome = v.findViewById(R.id.nome);
        TextView tvNota = v.findViewById(R.id.nota);
        TextView tvFaltas = v.findViewById(R.id.faltas);
        TextView tvSemestre = v.findViewById(R.id.semestre);

        tvNome.setText(disciplina.getNome());
        tvNota.setText(""+nota);
        tvFaltas.setText(""+disciplina.getFaltas());
        tvSemestre.setText(""+disciplina.getSemestre());

        return v;
    }
}
