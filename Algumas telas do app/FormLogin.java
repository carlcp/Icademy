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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;


public class FormLogin extends AppCompatActivity {
    private TextView esquecisenha;
    private Button button_entrar;
    private Button button_voltar;
    private ProgressBar progressBar;
    private EditText editTxtUser,editTxtPassword;
    String[] mensagens = {"Preencha todos os campos", "Usuario ou senha invalidos", "Por Favor Verifique seu E-mail"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_login);

        IniciarCoponentes();
        getSupportActionBar().hide();

        esquecisenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaEsqueciSenha();
            }
        });

        button_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaPrincipal();
            }
        });


        button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = editTxtUser.getText().toString();
                String senha = editTxtPassword.getText().toString();


                if (user.trim().isEmpty() ||
                        senha.trim().isEmpty()){

                    Snackbar snackbar = Snackbar.make(v ,mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else{
                    AutenticarUsuario(v);

                }
            }
        });

    }
    private void AutenticarUsuario(View v){
        String user = editTxtUser.getText().toString();
        String senha = editTxtPassword.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(user,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()){
                        progressBar.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                TelaMenu();
                            }
                        },  1000);
                    }else{
                        Snackbar snackbar = Snackbar.make(v ,mensagens[2], Snackbar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.WHITE);
                        snackbar.setTextColor(Color.BLACK);
                        snackbar.show();
                    }
                }else{

                    Snackbar snackbar = Snackbar.make(v ,mensagens[1], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }



    private void TelaPrincipal(){
        Intent intent = new Intent(FormLogin.this,FormPrincipal.class);
        startActivity(intent);

    }
    private void TelaMenu(){
        Intent intent = new Intent(FormLogin.this,FormMenu.class);
        startActivity(intent);
        finish();
    }
    private void TelaEsqueciSenha(){
        Intent intent = new Intent(FormLogin.this,FormEsqueciSenha.class);
        startActivity(intent);

    }

    private void IniciarCoponentes(){
        button_entrar = findViewById(R.id.button_entrar);
        editTxtUser = findViewById(R.id.editTxtUser);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        esquecisenha = findViewById(R.id.txtEsquecisenha);
        button_voltar = findViewById(R.id.button_voltar);
        progressBar = findViewById(R.id.progressBar);
    }
}