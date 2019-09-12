package com.example.autenticacionfirebaseyandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*7:despues de los metodos en usuario se importan estos*/
import com.example.autenticacionfirebaseyandroid.modelo.Usuarios;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class RegistroActivity extends AppCompatActivity {

    /*1: Se crean los atributos*/
    private EditText usuario;
    private EditText clave;
    private EditText confirmarClave;
    private EditText numeroDocumento;
    private EditText nombreCompleto;
    private EditText edad;
    private Button guardar;
    private Button volver;
    /*se agregan despues de la importacion de arriba*/
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        /*2: Asignar los campos con los atributos que creamos recien*/
        numeroDocumento = findViewById(R.id.txtdocumento);
        nombreCompleto = findViewById(R.id.txtnombre);
        edad = findViewById(R.id.txtedad);
        usuario = findViewById(R.id.txtusuario);
        clave = findViewById(R.id.txtcontrasena);
        confirmarClave = findViewById(R.id.txtconfirmarclave);
        guardar = findViewById(R.id.btnguardar);
        volver = findViewById(R.id.btnvolver);
        /*3: creamos un uevo package modelo y llamamos una clase usuarios*/

        /* despues de agregar  los atributos de las importaciones de arriba*/
        firebaseAuth = FirebaseAuth.getInstance();

        /*metodo creado abajo*/
        inicializarFirebase();
        /*21: que este oyendo cuando haya un cambio*/
        numeroDocumento.addTextChangedListener(validarCampo);
        nombreCompleto.addTextChangedListener(validarCampo);
        edad.addTextChangedListener(validarCampo);
        usuario.addTextChangedListener(validarCampo);
        clave.addTextChangedListener(validarCampo);
        confirmarClave.addTextChangedListener(validarCampo);

        /*10: botones*/
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*11: capturar lo que el usuario escribio*/
                String valorDocumento = numeroDocumento.getText().toString().trim();
                String valorNombre = nombreCompleto.getText().toString().trim();
                String valorEdad = edad.getText().toString().trim();
                String user = usuario.getText().toString().trim();
                String pass = clave.getText().toString().trim();
                String pass2 = confirmarClave.getText().toString().trim();

                /*11: confirmacion de contraseñas*/
                if(pass.equals(pass2)){
                    /*12: se instancia el objeto usuario*/
                    Usuarios usuarios = new Usuarios();
                    /*13: Se traen los datos ya ingresados, se hace un setteo*/
                    usuarios.setNumeroDocumento(valorDocumento);
                    usuarios.setNombreCompleto(valorNombre);
                    usuarios.setEdad(valorEdad);
                    usuarios.setEmail(user);


                    /*14: Logica para insertar, como se va a llamar esa entidad y cual sera la clave primeria en este caso el documento*/
                    databaseReference.child("Usuarios").child(usuarios.getNumeroDocumento()).setValue(usuarios);


                    /*15: Para la autenticacion*/
                    firebaseAuth.createUserWithEmailAndPassword(usuarios.getEmail(), pass).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            /*16: si la tarea se completó*/
                            if(!task.isSuccessful()){
                                /*17: obtenga el resultado*/
                                task.getResult();
                                /*18: ue muestre una alerta*/
                                Toast.makeText(RegistroActivity.this, "Error en el registro", Toast.LENGTH_LONG).show();
                            }
                            else{
                                /*19: Donde abrira*/
                                Intent ventanaLogin = new Intent(RegistroActivity.this, MainActivity.class );
                                startActivity(ventanaLogin);
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(RegistroActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*8: se crea un metodo privado que se encargara de iniciar la conexion*/
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        /*9: fuera de este metodo vamos a inicializarlo en la parte de arriba, en el metodo Oncreate*/
    }

    /*20: se crea un metodo*/
    private TextWatcher validarCampo = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            /*no is need*/
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            /*Validacion para el campo de correo, si el usuario no pone el @ se bloquea el boton*/
            final String regex = "(?:[^<>()\\[\\].,;:\\s@\"]+(?:\\.[^<>()\\[\\].,;:\\s@\"]+)*|\"[^\\n\"]+\")@(?:[^<>()\\[\\].,;:\\s@\"]+\\.)+[^<>()\\[\\]\\.,;:\\s@\"]{2,63}";

            /*21: capturando los valores*/
            String valorDocumento = numeroDocumento.getText().toString().trim();
            String valorNombre = nombreCompleto.getText().toString().trim();
            String valorEdad = edad.getText().toString().trim();
            String user = usuario.getText().toString().trim();
            String pass = clave.getText().toString().trim();
            String pass2 = confirmarClave.getText().toString().trim();

            /*22: validar que los campos esten llenos al terminar nos vamos al main*/
            guardar.setEnabled(
                            !valorDocumento.isEmpty() &&
                                    !valorNombre.isEmpty() &&
                                    !valorEdad.isEmpty() &&
                                    !user.isEmpty() &&
                                    !pass.isEmpty() &&
                                    !pass2.isEmpty() &&
                                    /*Validacion del correo*/
                                     user.matches(regex)&&
                                     /*contraseña mayor a 5 digitos*/
                                     pass.length() > 5 &&
                                     pass2.length() > 5 &&
                                    /*confirmacion de la contraseña*/
                                    (pass.equals(pass2)) &&
                                    /*documento mayor a 5 digitos*/
                                     valorDocumento.length() > 5
                                    /*vamos al boton en el diseño y se pone enable: false*/
                    /*volvemos al metodo de arriba inicializar firebase*/
            );

        }

        @Override
        public void afterTextChanged(Editable editable) {
            /*no is need*/
        }
    };
}
