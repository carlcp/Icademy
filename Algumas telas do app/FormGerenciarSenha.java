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
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class FormGerenciarSenha extends AppCompatActivity {
    private ImageButton buttonMenu;
    private ImageButton buttonperfil,buttonnot;
    private Button btnVoltar;
    private Button btnSalvar;
    private EditText editSenha;
    private EditText senhaAtual;
    private EditText confirmacaoSenha;
    private ProgressBar progressBar;

    String[] mensagens = {"Preencha todos os campos","Senha Alterada com sucesso","Confirmacao de senha incorreta"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_gerenciar_senha);
        IniciarCoponentes();
        getSupportActionBar().hide();

        buttonperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaPerfil();
            }
        });

        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaMenu();
            }
        });

        buttonnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Telanoti();
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaConfig();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String senha = senhaAtual.getText().toString();
                String editSenh = editSenha.getText().toString();
                String confirmacaoSenh = confirmacaoSenha.getText().toString();

                if(senha.trim().isEmpty() || editSenh.trim().isEmpty() || confirmacaoSenh.trim().isEmpty()){
                    Snackbar snackbar = Snackbar.make(v ,mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else if(editSenh.equals(confirmacaoSenh)){

                    UpdateSenha(v);

                }else{
                    Snackbar snackbar = Snackbar.make(v ,mensagens[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }



            }
        });



    }


    private void UpdateSenha(View v){
        String senha = editSenha.getText().toString();
        String senhaold = senhaAtual.getText().toString();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(),senhaold);
        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(senha).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                progressBar.setVisibility(View.VISIBLE);
                                Snackbar snackbar = Snackbar.make(v, mensagens[1], Snackbar.LENGTH_LONG);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        TelaLogin();

                                    }
                                }, 2000);
                            }else{
                                String erro;
                                try {
                                    throw task.getException();

                                }catch (FirebaseAuthWeakPasswordException e) {
                                    erro = "Digite uma senha com no minimo 6 caracteres";

                                } catch(Exception e){
                                    erro = "Erro ao atualizar senha";
                                }

                                Snackbar snackbar = Snackbar.make(v ,erro, Snackbar.LENGTH_SHORT);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();


                            }
                        }
                    });
                }else{

                    String erro;

                    try {
                        throw task.getException();


                    } catch(Exception e){
                        erro = "Senha atual incorreta";
                    }

                    Snackbar snackbar = Snackbar.make(v ,erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();


                }
            }
        });

    }


    private void Telanoti(){
        Intent intent = new Intent(FormGerenciarSenha.this,FormNotification.class);
        startActivity(intent);

    }

    private void TelaLogin(){
        Intent intent = new Intent(FormGerenciarSenha.this,FormLogin.class);
        startActivity(intent);
        finish();
    }
    private void TelaMenu(){
        Intent intent = new Intent(FormGerenciarSenha.this,FormMenu.class);
        startActivity(intent);
        finish();
    }

    private void TelaPerfil(){
        Intent intent = new Intent(FormGerenciarSenha.this,FormPerfil.class);
        startActivity(intent);
        finish();
    }

    private  void  TelaConfig(){
        Intent intent = new Intent(FormGerenciarSenha.this,formconfig.class);
        startActivity(intent);
        finish();
    }

    private void IniciarCoponentes(){
        buttonMenu = findViewById(R.id.menubutton);
        buttonperfil = findViewById(R.id.perfilbutton);
        btnVoltar = findViewById(R.id.button_Voltar5);
        btnSalvar = findViewById(R.id.button_salvar2);
        editSenha = findViewById(R.id.editSenha2);
        senhaAtual = findViewById(R.id.editsenha4);
        confirmacaoSenha = findViewById(R.id.editsenha3);
        progressBar = findViewById(R.id.progressBar4);
        buttonnot = findViewById(R.id.notbutton);
    }
}