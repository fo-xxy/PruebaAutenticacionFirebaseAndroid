package com.example.autenticacionfirebaseyandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button registarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registarse=findViewById(R.id.btnregistrarse);

        registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventanaRegister = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(ventanaRegister);
            }
        });
    }
}
