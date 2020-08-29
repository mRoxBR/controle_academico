package br.ufop.ControleAcademico.main.tarefa;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufop.ControleAcademico.R;
import br.ufop.ControleAcademico.models.bean.Tarefa;
import br.ufop.ControleAcademico.models.dao.DisciplinaDAO;

public class TarefaAdapter extends BaseAdapter {

    private ArrayList<Tarefa> tarefas;
    Context context;

    public TarefaAdapter(ArrayList<Tarefa> tarefas, Context context) {
        this.tarefas = tarefas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return tarefas.size();
    }

    @Override
    public Object getItem(int position) {
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tarefa tarefa = tarefas.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.tarefa_adapter,null);

        TextView tvNomeDisciplina = v.findViewById(R.id.tvNomeDisciplina);
        TextView tvDescricao = v.findViewById(R.id.tvDescricao);
        TextView tvData = v.findViewById(R.id.tvData);

        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(context);
        Cursor cursor = disciplinaDAO.findNomeByIdentificador(tarefa.getDisciplina().getIdentificador());
        String nome = cursor.getString(cursor.getColumnIndex("nome"));
        tvNomeDisciplina.setText(nome);

        tvDescricao.setText(tarefa.getDescricao());

        String dataVetor[] = tarefa.getDataEntrega().split("-");
        String dataFormatada = dataVetor[2] + "/" + dataVetor[1] + "/" + dataVetor[0];
        tvData.setText(dataFormatada);

        return v;
    }
}
