package com.carlana.icademy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class FormEsqueciSenha extends AppCompatActivity {


    private Button buttonenviar;
    private Button buttonvoltar;
    private EditText editTxtemail;
    private ProgressBar progressBar;
    String[] mensagens = {"Recuperação de acesso iniciada", "Erro, email não encontrado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_esqueci_senha);
        getSupportActionBar().hide();
        IniciarCoponentes();


        buttonenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTxtemail.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.VISIBLE);
                            Snackbar snackbar = Snackbar.make(v ,mensagens[0], Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    TelaLogin();
                                }
                            },  1000);
                        }
                        else{
                            Snackbar snackbar = Snackbar.make(v ,mensagens[1], Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.WHITE);
                            snackbar.setTextColor(Color.BLACK);
                            snackbar.show();
                        }
                    }
                });
            }
        });


        buttonvoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaLogin();
            }
        });


    }


    private void TelaLogin(){
        Intent intent = new Intent(FormEsqueciSenha.this,FormLogin.class);
        startActivity(intent);
        finish();
    }


    private void IniciarCoponentes() {
        buttonvoltar = findViewById(R.id.button_Voltar4);
        editTxtemail = findViewById(R.id.editemailview);
        buttonenviar = findViewById(R.id.button_Enviar);
        progressBar = findViewById(R.id.progressBar3);

    }


}