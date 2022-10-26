package com.carlana.icademy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FormGerenciarDespertador extends AppCompatActivity {

    private TimePicker timePicker;
    private Button definirAlarm;
    private int hours,minutes;
    private Calendar calendario;
    private int horaAtual = 0;
    private int minutosAtual = 0;
    private ImageButton buttonmenu, buttonnoti;
    private ImageButton buttonperfil;
    private ImageButton buttonConfig;
    private EditText AlarmName;
    private String Alarm;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_gerenciar_despertador);
        iniciarComponentes();
        getSupportActionBar().hide();

        definirAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Alarm = AlarmName.getText().toString();
                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        hours = hourOfDay;
                        minutes = minute;
                        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
                        intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
                        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
                        intent.putExtra(AlarmClock.EXTRA_MESSAGE, Alarm);
                        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
                        startActivity(intent);
                        addTohisNotifications("Alarme Definido Para " + hours + ":" + minutes);
                    }
                });


            }
        });

        buttonnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaNotifi();
            }
        });

        buttonmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaMenu();
            }
        });

        buttonperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaPerfil();
            }
        });

        buttonConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaConfig();
            }
        });

    }

    private void TelaMenu(){
        Intent intent = new Intent(FormGerenciarDespertador.this,FormMenu.class);
        startActivity(intent);

    }

    private void TelaPerfil(){
        Intent intent = new Intent(FormGerenciarDespertador.this,FormPerfil.class);
        startActivity(intent);

    }
    private void TelaConfig(){
        Intent intent = new Intent(FormGerenciarDespertador.this,formconfig.class);
        startActivity(intent);

    }

    private void TelaNotifi(){

            Intent intent = new Intent(FormGerenciarDespertador.this,FormNotification.class);
            startActivity(intent);

    }




    private void addTohisNotifications(String notification){

        String timestamp = ""+System.currentTimeMillis();

        HashMap<Object, String > hashMap = new HashMap<>();
        hashMap.put("notification", notification);
        hashMap.put("timestamp",timestamp);
        hashMap.put("sUid", usuarioID);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("User");
        ref.child(usuarioID).child("Notifications").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //added successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


    }



    private  void iniciarComponentes(){
        timePicker = findViewById ( R . id . TimePicker ); // inicia um selecionador de tempo
        timePicker . setIs24HourView ( true ); // define o modo 24 horas para o seletor de horário
        definirAlarm = findViewById(R.id.button_setdesp);
        calendario = Calendar.getInstance();
        horaAtual = calendario.get(Calendar.HOUR_OF_DAY);
        minutosAtual = calendario.get(Calendar.MINUTE);
        timePicker.setCurrentHour(horaAtual);
        timePicker.setCurrentMinute(minutosAtual);
        buttonmenu = findViewById(R.id.imageButton6);
        buttonperfil = findViewById(R.id.imageButton8);
        buttonConfig = findViewById(R.id.imageButtonConfig);
        AlarmName = findViewById(R.id.editnoemal);
        buttonnoti = findViewById(R.id.imageButton7);
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    }
