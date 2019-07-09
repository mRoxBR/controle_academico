package br.ufop.controle_academico.main.tarefa;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import br.ufop.controle_academico.R;
import br.ufop.controle_academico.main.NotificationPublisher;
import br.ufop.controle_academico.main.disciplina.SharedResourcesDisciplina;
import br.ufop.controle_academico.models.bean.Disciplina;
import br.ufop.controle_academico.models.bean.Tarefa;
import br.ufop.controle_academico.models.dao.DisciplinaDAO;
import br.ufop.controle_academico.models.dao.TarefaDAO;

public class EditTarefaByDisciplinaActivity extends AppCompatActivity {
    private int position;
    private int id;

    private EditText etDescricao;
    private EditText etValor;
    private EditText etNota;

    private String identificadorDisciplina;

    private String dataEntrega = "";
    private Button btDataEntrega;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;

    private Spinner spTipoTarefa;
    private String[] tipoTarefas = {"Atividade", "Lista", "Prova", "Seminário", "Trabalho"};

    private EditText etPrioridade;

    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tarefa_by_disciplina);

        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        etNota = findViewById(R.id.etNota);
        btDataEntrega = findViewById(R.id.btDataEntrega);

        spTipoTarefa = findViewById(R.id.spTipoTarefa);
        ArrayAdapter<String> adapterSpinnerTarefas =
                new ArrayAdapter<>(this, R.layout.customized_spinner_item, tipoTarefas);
        spTipoTarefa.setAdapter(adapterSpinnerTarefas);

        etPrioridade = findViewById(R.id.etPrioridade);

        Intent it = getIntent();
        position = it.getIntExtra("position",0);
        identificadorDisciplina = it.getStringExtra("identificadorDisciplina");

        id = SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getId();

        etDescricao.setText(SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getDescricao());

        etValor.setText(""+SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getValor());

        double nota = SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getNota();
        String notaString = nota == - 1 ? "" : "" + nota;
        etNota.setText(notaString);

        String tipoTarefa = SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getTipo();
        int indexTarefa = 0;
        for(int i = 0; i < tipoTarefas.length; i++){
            if(tipoTarefa.equals(tipoTarefas[i])){
                indexTarefa = i;
                break;
            }
        }
        spTipoTarefa.setSelection(indexTarefa);

        etPrioridade.setText(""+SharedResourcesTarefa.getInstance()
                .getTarefas()
                .get(position)
                .getPrioridade());
    }

    public void clickDataEntrega(View view) {
        int day;
        int month;
        int year;
        calendar = Calendar.getInstance();

        if(dataEntrega.equals("")) {
            dataEntrega = SharedResourcesTarefa
                    .getInstance()
                    .getTarefas()
                    .get(position)
                    .getDataEntrega();
        }

        String dataArray[] = dataEntrega.split("-");
        day = Integer.parseInt(dataArray[2]);
        year = Integer.parseInt(dataArray[0]);

        if(flag == 0){
            month = Integer.parseInt(dataArray[1]) - 1;
            dataEntrega = year + "-" + month + "-" + day;
        }else{
            month = Integer.parseInt(dataArray[1]);
        }

        flag = 1;

        datePickerDialog = new DatePickerDialog(EditTarefaByDisciplinaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int aYear, int aMonth, int aDayOfMonth) {
                dataEntrega = aYear + "-" + aMonth + "-" + aDayOfMonth;
                calendar.set(Calendar.YEAR, aYear);
                calendar.set(Calendar.MONTH, aMonth);
                calendar.set(Calendar.DAY_OF_MONTH, aDayOfMonth);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void editar(View view) {
        int day, month, year;
        Disciplina disciplina = new Disciplina();
        Tarefa tarefa = new Tarefa();
        tarefa.setDisciplina(disciplina);
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        try{
            String descricao = etDescricao.getText().toString();
            double valor = Double.parseDouble(etValor.getText().toString());
            double nota = etNota.getText().toString().equals("")?
                    -1 : Double.parseDouble(etNota.getText().toString());

            String auxDay = "", auxMonth = "";

            if(dataEntrega.equals("")) {
                dataEntrega = SharedResourcesTarefa
                        .getInstance()
                        .getTarefas()
                        .get(position)
                        .getDataEntrega();
                String dataArray[] = dataEntrega.split("-");
                day = Integer.parseInt(dataArray[2]);
                month = Integer.parseInt(dataArray[1]);
                year = Integer.parseInt(dataArray[0]);
                if(day < 10){
                    auxDay = "0";
                }
                if(month < 9){
                    auxMonth = "0";
                }
                dataEntrega = year + "-" + auxMonth + month + "-" + auxDay + day;
            }else{
                String dataArray[] = dataEntrega.split("-");
                day = Integer.parseInt(dataArray[2]);
                month = Integer.parseInt(dataArray[1]);
                year = Integer.parseInt(dataArray[0]);
                if(day < 10){
                    auxDay = "0";
                }
                if(month < 9){
                    auxMonth = "0";
                }
                dataEntrega = year + "-" + auxMonth + (month + 1) + "-" + auxDay + day;
            }

            String tipoTarefa = spTipoTarefa.getSelectedItem().toString();
            double prioridade = Double.parseDouble(etPrioridade.getText().toString());

            tarefa.setId(id);
            tarefa.getDisciplina().setIdentificador(identificadorDisciplina);
            tarefa.setDescricao(descricao);
            tarefa.setValor(valor);
            tarefa.setNota(nota);
            tarefa.setDataEntrega(dataEntrega);
            tarefa.setTipo(tipoTarefa);
            tarefa.setPrioridade(prioridade);
        }catch (Exception e){
            Toast.makeText(this,"Verifique se os campos estão preenchidos corretamente.",
                    Toast.LENGTH_SHORT).show();
        }
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        tarefaDAO.edit(tarefa);
        SharedResourcesTarefa.getInstance().getTarefas().set(position, tarefa);
        Toast.makeText(this, "Tarefa editada com suceso!",Toast.LENGTH_SHORT).show();

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));

        long time = calendar.getTimeInMillis();
        long timeDifference = time - System.currentTimeMillis();

        if (timeDifference <= 0) {
            cancelNotification();
            Toast.makeText(this, "Você não será notificado, pois a data da tarefa já passou!",
                    Toast.LENGTH_SHORT).show();
        }else{
            Cursor cursor = disciplinaDAO.findNomeByIdentificador(identificadorDisciplina);
            String nomeDisciplina = cursor.getString(cursor.getColumnIndex("nome"));

            cancelNotification();

            scheduleNotification(this, (time + 30000) - (3600*24*1000), id,
                    etDescricao.getText().toString(), nomeDisciplina);
            Toast.makeText(this, "Você será notificado um dia antes da tarefa.",
                    Toast.LENGTH_SHORT).show();
        }

        finish();

    }

    public void deletar(View view) {
        TarefaDAO tarefaDAO = new TarefaDAO(this);
        tarefaDAO.delete(id);
        SharedResourcesTarefa.getInstance().getTarefas().remove(position);
        Toast.makeText(this, "Tarefa removida com sucesso!", Toast.LENGTH_SHORT).show();

        cancelNotification();

        finish();
    }

    public void scheduleNotification(Context context, long moment, int notificationId, String descricaoTarefa, String nomeDisciplina) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "id_unique")
                .setContentTitle("Lembrete de Tarefa")
                .setContentText("Você tem " + descricaoTarefa + " de " + nomeDisciplina + " amanhã.")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round);

        Intent intent = new Intent(context, ListTarefaActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, 0);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, moment, pendingIntent);
    }

    public void cancelNotification(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notificationIntent, PendingIntent.FLAG_NO_CREATE);

        if (pendingIntent != null){
            alarmManager.cancel(pendingIntent);
        }
    }
}
