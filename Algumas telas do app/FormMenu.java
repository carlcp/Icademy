package com.carlana.icademy2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FormMenu extends AppCompatActivity {
    private ImageButton  buttonconfig, buttonnoti;
    private ImageButton  buttonperfil,DesempenhoTre;
    private ImageButton Buttonheart;
    private TextView     txtqnt;
    private ImageButton Buttonheart2;
    private TextView     txtqnt2;
    private ImageButton Buttonheart3;
    private TextView     txtqnt3;
    private TextView txtqnt4, txtqnt5, txtqnt6;
    private ImageButton Treino1,Treino2,Treino3;
    String usuarioID;
    Boolean status = true;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private int counterLikes = 0;
    private int counterPosts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_menu);

        IniciarCoponentes();
        getSupportActionBar().hide();




        buttonconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Telaconfig();
            }
        });



        buttonnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaNotificacao();
            }
        });

        buttonperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaPerfil();
            }
        });

        Buttonheart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                referencia.child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            counterLikes = (int) snapshot.getChildrenCount();
                            txtqnt.setText(Integer.toString(counterLikes));

                            referencia.child("Likes").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        referencia.child("Likes").child(usuarioID).removeValue();
                                        Buttonheart.setBackgroundResource(R.drawable.empty_heart);
                                    }else{
                                        Buttonheart.setBackgroundResource(R.drawable.full_heart);
                                        referencia.child("Likes").child(usuarioID).setValue(status);
                                        addTohisNotifications("Voce deu like no Treino Iniciante");
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }else{
                            referencia.child("Likes").child(usuarioID).setValue(status);
                            Buttonheart.setBackgroundResource(R.drawable.full_heart);
                            addTohisNotifications("Voce deu like no Treino Iniciante");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        Buttonheart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                referencia.child("Likes2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        if(datasnapshot.exists()){

                            referencia.child("Likes2").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    if (datasnapshot.exists()){
                                        referencia.child("Likes2").child(usuarioID).removeValue();
                                        Buttonheart2.setBackgroundResource(R.drawable.empty_heart);
                                    }else{
                                        Buttonheart2.setBackgroundResource(R.drawable.full_heart);
                                        referencia.child("Likes2").child(usuarioID).setValue(status);
                                        addTohisNotifications("Voce deu like no Treino Intermediario");
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            counterLikes = (int) datasnapshot.getChildrenCount();
                            txtqnt2.setText(Integer.toString(counterLikes));



                        }else{
                            referencia.child("Likes2").child(usuarioID).setValue(status);
                            Buttonheart2.setBackgroundResource(R.drawable.full_heart);
                            addTohisNotifications("Voce deu like no Treino Intermediario");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Buttonheart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                referencia.child("Likes3").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            referencia.child("Likes3").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        referencia.child("Likes3").child(usuarioID).removeValue();
                                        Buttonheart3.setBackgroundResource(R.drawable.empty_heart);
                                    }else{
                                        Buttonheart3.setBackgroundResource(R.drawable.full_heart);
                                        referencia.child("Likes3").child(usuarioID).setValue(status);
                                        addTohisNotifications("Voce deu like no Treino Avancado");
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            counterLikes = (int) snapshot.getChildrenCount();
                            txtqnt3.setText(Integer.toString(counterLikes));



                        }else{
                            referencia.child("Likes3").child(usuarioID).setValue(status);
                            Buttonheart3.setBackgroundResource(R.drawable.full_heart);
                            addTohisNotifications("Voce deu like no Treino Avancado");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        Treino1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                referencia.child("Posts").child(usuarioID).setValue(status);

                referencia.child("Posts").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            counterPosts = (int) snapshot.getChildrenCount();
                            txtqnt4.setText(Integer.toString(counterPosts));

                        }else{
                            txtqnt4.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                TelaTreinoIniciante();

            }
        });

        Treino2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                referencia.child("Posts2").child(usuarioID).setValue(status);

                referencia.child("Posts2").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            counterPosts = (int) snapshot.getChildrenCount();
                            txtqnt5.setText(Integer.toString(counterPosts));

                        }else{
                            txtqnt5.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                TelaTreinoIntermediario();
            }
        });

        Treino3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                referencia.child("Posts3").child(usuarioID).setValue(status);

                referencia.child("Posts3").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            counterPosts = (int) snapshot.getChildrenCount();
                            txtqnt6.setText(Integer.toString(counterPosts));

                        }else{
                            txtqnt6.setText("0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                TelaTreinoAvancado();
            }
        });

        DesempenhoTre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelaDesempenho();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        referencia.child("Likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    counterLikes = (int) snapshot.getChildrenCount();
                    txtqnt.setText(Integer.toString(counterLikes));
                    referencia.child("Likes").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                            if (dsnapshot.exists()){
                                Buttonheart.setBackgroundResource(R.drawable.full_heart);
                            }else{
                                Buttonheart.setBackgroundResource(R.drawable.empty_heart);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }else{
                    txtqnt.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        referencia.child("Likes2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    counterLikes = (int) snapshot.getChildrenCount();
                    txtqnt2.setText(Integer.toString(counterLikes));
                    referencia.child("Likes2").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                            if (dsnapshot.exists()){
                                Buttonheart2.setBackgroundResource(R.drawable.full_heart);
                            }else{
                                Buttonheart2.setBackgroundResource(R.drawable.empty_heart);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    txtqnt2.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        referencia.child("Likes3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    counterLikes = (int) snapshot.getChildrenCount();
                    txtqnt3.setText(Integer.toString(counterLikes));
                    referencia.child("Likes3").child(usuarioID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dsnapshot) {
                            if (dsnapshot.exists()){
                                Buttonheart3.setBackgroundResource(R.drawable.full_heart);
                            }else{
                                Buttonheart3.setBackgroundResource(R.drawable.empty_heart);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    txtqnt3.setText("0");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        referencia.child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    counterPosts = (int) snapshot.getChildrenCount();
                    txtqnt4.setText(Integer.toString(counterPosts));

                }else{
                    txtqnt4.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referencia.child("Posts2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    counterPosts = (int) snapshot.getChildrenCount();
                    txtqnt5.setText(Integer.toString(counterPosts));

                }else{
                    txtqnt5.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        referencia.child("Posts3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    counterPosts = (int) snapshot.getChildrenCount();
                    txtqnt6.setText(Integer.toString(counterPosts));

                }else{
                    txtqnt6.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void TelaTreinoIniciante(){
        Intent intent = new Intent(FormMenu.this,Form_Treino_Iniciante.class);
        startActivity(intent);

    }

    private void TelaTreinoIntermediario(){
        Intent intent = new Intent(FormMenu.this,Form_Treino_Intermediario.class);
        startActivity(intent);

    }

    private void TelaTreinoAvancado(){
        Intent intent = new Intent(FormMenu.this,Form_Treino_Avancado.class);
        startActivity(intent);

    }


    private void Telaconfig(){
        Intent intent = new Intent(FormMenu.this,formconfig.class);
        startActivity(intent);

    }

    private void TelaPerfil(){
        Intent intent = new Intent(FormMenu.this,FormPerfil.class);
        startActivity(intent);

    }

    private void TelaDesempenho(){
        Intent intent = new Intent(FormMenu.this,FormIMC.class);
        startActivity(intent);

    }

    private void TelaNotificacao(){
        Intent intent = new Intent(FormMenu.this,FormNotification.class);
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



    private void IniciarCoponentes(){
        buttonconfig = findViewById(R.id.imagestatic2);
        buttonperfil = findViewById(R.id.imageButton4);
        Buttonheart = findViewById(R.id.imageButtonheart);
        txtqnt = findViewById(R.id.txtqnt);
        Buttonheart2 = findViewById(R.id.imageButtonheart2);
        txtqnt2 = findViewById(R.id.txtqnt4);
        Buttonheart3 = findViewById(R.id.imageButtonheart3);
        txtqnt3 = findViewById(R.id.txtqnt6);
        Treino1 = findViewById(R.id.Treino1);
        Treino2 = findViewById(R.id.Treino2);
        Treino3 = findViewById(R.id.Treino3);
        txtqnt4 = findViewById(R.id.txtqnt2);
        txtqnt5 = findViewById(R.id.txtqnt3);
        txtqnt6 = findViewById(R.id.txtqnt5);
        DesempenhoTre = findViewById(R.id.imageButton5);
        buttonnoti = findViewById(R.id.imageButton3);
    }


}