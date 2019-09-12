package com.example.autenticacionfirebaseyandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*23: importamos*/
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

/*importantes*/
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class MainActivity extends AppCompatActivity {


    private Button registarse;
    /*24: se hace unas asociaciones*/
    private EditText email;
    private EditText contrasena;
    private Button ingresar;
    private ProgressDialog progressDialog;

    /*25: se usa el objeto de firebase*/
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*26: instancia de la clase firebase*/
        firebaseAuth = FirebaseAuth.getInstance();

        /*27: se asigna los campos con las variables*/
        email = findViewById(R.id.txtusuario);
        contrasena = findViewById(R.id.txtcontrasena);
        ingresar = findViewById(R.id.btningresar);

        /*28: instancia dentro de la misma clase*/
        progressDialog = new ProgressDialog(this);

        registarse=findViewById(R.id.btnregistrarse);

        /*30: se crea el boton para ingresar*/
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*31: logica de logueo*/
                final String correo = email.getText().toString().trim();
                String pass = contrasena.getText().toString().trim();

                /*32: se llama el objecto de progress*/
                progressDialog.setMessage("Espere por favor, validando...");
                progressDialog.show();

                /*33: consumir el recurso o servicio*/
                Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(correo, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                /*34: validacion si todoo esta bien*/
                                if (task.isSuccessful()) {
                                    int pos = correo.indexOf("@");
                                    /*esto separa el @ y lo hace como un vector y deja el usuario*/
                                    String user = correo.substring(0, pos);
                                    Toast.makeText(MainActivity.this, "Bienvenido: " + user, Toast.LENGTH_LONG).show();
                                    /*se crea la otra actividad o en este ejemplo se hizo asi*/
                                    /*35: que cargue la otra ventana*/
                                    Intent ventanaBienvenido = new Intent(getApplication(), Bienvenido.class);

                                    /*36: mandar el nombre desde actividades*/
                                    ventanaBienvenido.putExtra("usuario", user);
                                } else {
                                    /*37: si hay un error de tipo Firebase*/
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(MainActivity.this, "Error en la autenticación.", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "No es posible autenticarse", Toast.LENGTH_LONG).show();
                                    }
                                }
                                /*38: cerra el objeto de progressdialog*/
                                progressDialog.dismiss();
                            }
                        });


            }
        });



        registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaRegister = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(ventanaRegister);
            }
        });
    }
}
