package br.ufop.controle_academico.main.grafico;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;

import br.ufop.controle_academico.R;
import br.ufop.controle_academico.main.disciplina.SharedResourcesDisciplina;
import br.ufop.controle_academico.main.tarefa.SharedResourcesTarefa;
import br.ufop.controle_academico.models.bean.Tarefa;
import br.ufop.controle_academico.models.dao.DisciplinaDAO;
import br.ufop.controle_academico.models.dao.TarefaDAO;

public class MainGraficoActivity extends AppCompatActivity {

    private PieChart chart;

    Spinner spinner1;
    String opcoes1[] = {"Disciplinas", "Tarefas"};
    String opcao1 = "";

    Spinner spinner2;
    ArrayList<String>opcoes2 = new ArrayList<>();
    String opcao2 = "";

    TextView tvEmptyGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grafico);

        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        chart = findViewById(R.id.chart);
        tvEmptyGraph = findViewById(R.id.tvEmptyGraph);

        ArrayAdapter<String> adapterSpinner1 =
                new ArrayAdapter<>(this, R.layout.customized_spinner_item, opcoes1);
        spinner1.setAdapter(adapterSpinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    opcao1 = opcoes1[0];

                    opcoes2.clear();
                    opcoes2 = SharedResourcesDisciplina.getInstance().getNomeDisciplinas(MainGraficoActivity.this);
                    ArrayAdapter<String> adapterSpinner2 =
                            new ArrayAdapter<>(MainGraficoActivity.this, R.layout.customized_spinner_item, opcoes2);
                    spinner2.setAdapter(adapterSpinner2);
                    trataInformacoes();
                }else{
                    opcao1 = opcoes1[1];

                    opcoes2.clear();
                    opcoes2.add("Atividade");
                    opcoes2.add("Lista");
                    opcoes2.add("Prova");
                    opcoes2.add("Seminário");
                    opcoes2.add("Trabalho");
                    ArrayAdapter<String> adapterSpinner2 =
                            new ArrayAdapter<>(MainGraficoActivity.this, R.layout.customized_spinner_item, opcoes2);
                    spinner2.setAdapter(adapterSpinner2);
                    trataInformacoes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> adapterSpinner2 =
                new ArrayAdapter<>(MainGraficoActivity.this, R.layout.customized_spinner_item, opcoes2);
        spinner2.setAdapter(adapterSpinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trataInformacoes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        menu.getItem(0).setTitle("Exibir no modo padrão");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() ==  R.id.actionTogglePercent){
            chart.setUsePercentValues(!chart.isUsePercentValuesEnabled());

            if(chart.isUsePercentValuesEnabled()){
                item.setTitle("Exibir no modo padrão");
            }else{
                item.setTitle("Exibir em porcentagem");
            }

            chart.invalidate();
        }
        return true;
    }

    public void trataInformacoes(){
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        ArrayList<PieEntry> entries = new ArrayList<>();

        //Selecionou disciplinas
        if(opcao1.equals(opcoes1[0])){
            String nomeDisciplina, identificadorDisciplina;
            Cursor cursor1, cursor2;

            nomeDisciplina = spinner2.getSelectedItem().toString();
            cursor1 = disciplinaDAO.findIdentificadorByNome(nomeDisciplina);
            identificadorDisciplina = cursor1.getString(cursor1.getColumnIndex("identificador"));
            cursor2 = tarefaDAO.findNotasDescricaoByIdentificadorDisciplina(identificadorDisciplina);

            if(cursor2.getCount() == 0){
                chart.setVisibility(View.GONE);
                tvEmptyGraph.setVisibility(View.VISIBLE);
            }else{
                chart.setVisibility(View.VISIBLE);
                tvEmptyGraph.setVisibility(View.GONE);

                for(cursor2.moveToFirst(); !cursor2.isAfterLast(); cursor2.moveToNext()) {

                    double nota = cursor2.getDouble(cursor2.getColumnIndex("nota"));
                    String descricao = cursor2.getString(cursor2.getColumnIndex("descricao"));

                    entries.add(new PieEntry((float)nota, descricao));
                }

                exibeGrafico(entries);
            }

        //Selecionou tarefas
        }else{
            ArrayList<String> descricoes = new ArrayList<>();
            ArrayList<Double> notas = new ArrayList<>();
            String nomeTipo, identificadorDisciplina;
            Cursor cursor;

            nomeTipo = spinner2.getSelectedItem().toString();
            cursor = tarefaDAO.findByTipo(nomeTipo);

            if(cursor.getCount() == 0) {
                chart.setVisibility(View.GONE);
                tvEmptyGraph.setVisibility(View.VISIBLE);
            }else {
                chart.setVisibility(View.VISIBLE);
                tvEmptyGraph.setVisibility(View.GONE);

                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                    double nota = cursor.getDouble(cursor.getColumnIndex("nota"));
                    String descricao = cursor.getString(cursor.getColumnIndex("descricao"));

                    if (descricoes.contains(descricao)) {
                        int index = descricoes.indexOf(descricao);
                        double novaNota = notas.get(index) + nota;
                        notas.set(index, novaNota);
                    } else {
                        descricoes.add(descricao);
                        notas.add(nota);
                    }
                }

                //can also be "i < notas.size()"
                for (int i = 0; i < descricoes.size(); i++) {
                    entries.add(new PieEntry(notas.get(i).floatValue(), descricoes.get(i)));
                }

                exibeGrafico(entries);
            }
        }
    }

    public void exibeGrafico(ArrayList<PieEntry> entries){
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(false);

        chart.setRotationAngle(0);

        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // make an effect when the activity starts
        chart.animateY(1400, Easing.EaseInOutQuad);

        // make an legend for the chart
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // disable the label at the chart
        chart.setDrawEntryLabels(false);

        //setting the data;
        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(2f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(Typeface.DEFAULT);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();
    }
}
