package com.carlana.icademy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private Button button_registrar;
    private Button button_voltar2;
    private EditText editNome2,editTxtEmail2,editTxtPassword2,editTxtPassword3,editTxtTelefone;
    private ProgressBar progressBar;


    String[] mensagens = {"Preencha todos os campos","Cadastro realizado com sucesso, Por Favor Verifique seu E-mail","Confirmacao de senha incorreta"};
    String usuarioID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        getSupportActionBar().hide();
        IniciarCoponentes();
        getSupportActionBar().hide();

        button_voltar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaPrincipal();
            }
        });


        button_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editNome2.getText().toString();
                String email = editTxtEmail2.getText().toString();
                String senha = editTxtPassword2.getText().toString();
                String confirmsenha = editTxtPassword3.getText().toString();
                String telefone = editTxtTelefone.getText().toString();


                if (nome.trim().isEmpty() ||
                        email.trim().isEmpty() || senha.trim().isEmpty() || confirmsenha.trim().isEmpty() || telefone.trim().isEmpty() ){

                    Snackbar snackbar = Snackbar.make(v ,mensagens[0], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }else if(senha.equals(confirmsenha)) {

                    CadastrarUsuario(v);

                }
                else{
                    Snackbar snackbar = Snackbar.make(v ,mensagens[2], Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }


            }
        });

    }

    private void TelaPrincipal(){
        Intent intent = new Intent(FormCadastro.this,FormPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void TelaLogin(){
        Intent intent = new Intent(FormCadastro.this,FormLogin.class);
        startActivity(intent);
        finish();
    }

    private void CadastrarUsuario(View v){

        String email = editTxtEmail2.getText().toString();
        String senha = editTxtPassword2.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);

                    FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                SalvarDadosUsuario();

                                Snackbar snackbar = Snackbar.make(v ,mensagens[1], Snackbar.LENGTH_LONG);
                                snackbar.setBackgroundTint(Color.WHITE);
                                snackbar.setTextColor(Color.BLACK);
                                snackbar.show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        TelaLogin();

                                    }
                                },  2000);
                            }else{
                                String erro;
                                try {
                                    throw task.getException();

                                }catch (FirebaseAuthWeakPasswordException e) {
                                    erro = "Digite uma senha com no minimo 6 caracteres";

                                }catch (FirebaseAuthUserCollisionException e) {
                                    erro = "Email informado ja esta cadastrado";

                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    erro = "E-mail incorreto, exemplo: exemplo@gmail.com";
                                }catch(Exception e){
                                    erro = "Erro ao cadastrar usuario";
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

                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no minimo 6 caracteres";

                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email informado ja esta cadastrado";

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "E-mail incorreto, exemplo: exemplo@gmail.com";
                    }catch(Exception e){
                        erro = "Erro ao cadastrar usuario";
                    }

                    Snackbar snackbar = Snackbar.make(v ,erro, Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();


                }
            }
        });

    }

    private void SalvarDadosUsuario(){
        String nome = editNome2.getText().toString();
        String telefone = editTxtTelefone.getText().toString();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("telefone", telefone);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao salvar os dados");
            }
        })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("db_error","Erro ao salvar os dados: " + e.toString());
                }
            });


    }

    private void IniciarCoponentes(){
        button_registrar = findViewById(R.id.button_Registrar);
        button_voltar2 = findViewById(R.id.button_Voltar2);
        editNome2 = findViewById(R.id.editNome2);
        editTxtEmail2 = findViewById(R.id.editTxtEmail2);
        editTxtPassword2 = findViewById(R.id.editTxtPassword2);
        editTxtPassword3 = findViewById(R.id.editTxtPassword3);
        editTxtTelefone = findViewById(R.id.editTxtTelefone);
        progressBar = findViewById(R.id.progressBar2);
    }
}