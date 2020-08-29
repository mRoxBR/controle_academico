package br.ufop.ControleAcademico.main.tarefa;

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

import br.ufop.ControleAcademico.main.disciplina.SharedResourcesDisciplina;
import br.ufop.ControleAcademico.R;
import br.ufop.ControleAcademico.main.NotificationPublisher;
import br.ufop.ControleAcademico.models.bean.Disciplina;
import br.ufop.ControleAcademico.models.bean.Tarefa;
import br.ufop.ControleAcademico.models.dao.DisciplinaDAO;
import br.ufop.ControleAcademico.models.dao.TarefaDAO;

public class AddTarefaActivity extends AppCompatActivity {

    private Spinner spNomeDisciplina;
    private ArrayList<String> nomeDisciplinas;

    private EditText etDescricao;
    private EditText etValor;
    private EditText etNota;

    private String dataEntrega = "";
    private Button btDataEntrega;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    private Spinner spTipoTarefa;
    private String[] tipoTarefas = {"Atividade", "Lista", "Prova", "Seminário", "Trabalho"};

    private EditText etPrioridade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tarefa);

        nomeDisciplinas = SharedResourcesDisciplina
                .getInstance()
                .getNomeDisciplinas(this);
        spNomeDisciplina = findViewById(R.id.spNomeDisciplina);
        ArrayAdapter<String> adapterSpinnerDisciplinas =
                new ArrayAdapter<>(this, R.layout.customized_spinner_item, nomeDisciplinas);
        spNomeDisciplina.setAdapter(adapterSpinnerDisciplinas);

        etDescricao = findViewById(R.id.etDescricao);
        etValor = findViewById(R.id.etValor);
        etNota = findViewById(R.id.etNota);
        btDataEntrega = findViewById(R.id.btDataEntrega);

        spTipoTarefa = findViewById(R.id.spTipoTarefa);
        ArrayAdapter<String> adapterSpinnerTarefas =
                new ArrayAdapter<>(this, R.layout.customized_spinner_item, tipoTarefas);
        spTipoTarefa.setAdapter(adapterSpinnerTarefas);

        etPrioridade = findViewById(R.id.etPrioridade);
    }


    public void clickDataEntrega(View view) {
        int day, month, year;
        calendar = Calendar.getInstance();
        if(dataEntrega.equals("")){
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
            dataEntrega = year + "-" + month + "-" + day;
        }else{
            String dataArray[] = dataEntrega.split("-");
            day = Integer.parseInt(dataArray[2]);
            month = Integer.parseInt(dataArray[1]);
            year = Integer.parseInt(dataArray[0]);
        }

        datePickerDialog = new DatePickerDialog(AddTarefaActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    public void adicionar(View view) {
        int day = 0, month = 0, year = 0;
        String auxDay = "", auxMonth = "";
        Disciplina disciplina = new Disciplina();
        Tarefa tarefa = new Tarefa();
        DisciplinaDAO disciplinaDAO = new DisciplinaDAO(this);
        tarefa.setDisciplina(disciplina);

        try{
            String nomeDisciplina = spNomeDisciplina.getSelectedItem().toString();
            Cursor cursor = disciplinaDAO.findIdentificadorByNome(nomeDisciplina);
            String identificadorDisciplina = cursor.getString(cursor.getColumnIndex("identificador"));
            String descricao = etDescricao.getText().toString();
            double valor = Double.parseDouble(etValor.getText().toString());
            double nota = etNota.getText().toString().equals("")?
                    -1 : Double.parseDouble(etNota.getText().toString());

            if(dataEntrega.equals("")){
                calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);
                if(day < 10){
                    auxDay = "0";
                }
                if(month < 9){
                    auxMonth = "0";
                }
                dataEntrega = year + "-" + auxMonth + (month + 1) + "-" + auxDay + day;
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
        long resultado = tarefaDAO.insert(tarefa);

        if(resultado != -1){
            Toast.makeText(this, "Tarefa cadastrada com suceso!",Toast.LENGTH_SHORT).show();

            dataEntrega = year + "-" + auxMonth + month + "-" + auxDay + day;

            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND));

            long time = calendar.getTimeInMillis();
            long timeDifference = time - System.currentTimeMillis();

            if (timeDifference <= 0) {
                Toast.makeText(this, "Você não será notificado, pois a data da tarefa já passou!",
                        Toast.LENGTH_SHORT).show();
            }else{
                scheduleNotification(this, (time + 30000) - (3600*24*1000), (int) resultado,
                        etDescricao.getText().toString(), spNomeDisciplina.getSelectedItem().toString());
                Toast.makeText(this, "Você será notificado um dia antes da tarefa.",
                        Toast.LENGTH_SHORT).show();
            }

            etDescricao.setText("");
            etValor.setText("");
            etNota.setText("");
            etPrioridade.setText("");
            etDescricao.requestFocus();
        }else{
            Toast.makeText(this,"Ocorreu um erro ao cadastrar a tarefa.",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void scheduleNotification(Context context, long moment, int notificationId, String descricaoTarefa, String nomeDisciplina) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "id_unique")
                .setContentTitle("Lembrete de Tarefa")
                .setContentText("Você tem " + descricaoTarefa + " de " + nomeDisciplina + " amanhã.")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round);

        Intent intent = new Intent(context, ListTarefaActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent,0);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, moment, pendingIntent);
    }
}
