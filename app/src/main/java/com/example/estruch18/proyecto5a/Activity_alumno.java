package com.example.estruch18.proyecto5a;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_alumno extends AppCompatActivity {
    //Atributos de la clase
    private MyDbAdapter dbAdapter;
    private EditText nombre, edad, ciclo, curso, notaMedia;
    private Button btnInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_alumno);

        //Ejecución de métodos
        declaracionViews();
    }

    public void declaracionViews(){
        nombre = (EditText)findViewById(R.id.etNombre);
        edad = (EditText)findViewById(R.id.etEdad);
        ciclo = (EditText)findViewById(R.id.etCiclo);
        curso = (EditText)findViewById(R.id.etCurso);
        notaMedia = (EditText)findViewById(R.id.etNotaMedia);
        btnInsertar = (Button)findViewById(R.id.btnInsertarEst);
    }

    //Listener del botón
    public void accionBtnInsertar(View v){
        dbAdapter = new MyDbAdapter(this);
        dbAdapter.open();
        //INSERCIÓN DE DATOS
        dbAdapter.insertarEstudiante(nombre.getText().toString(), Integer.parseInt(edad.getText().toString()), ciclo.getText().toString(), Integer.parseInt(curso.getText().toString()), notaMedia.getText().toString());
        //INFO
        Toast.makeText(getApplicationContext(), "Estudiante insertado correctamente", Toast.LENGTH_SHORT).show();
        //RESETEADO DE CAMPOS
        resetearCampos();
    }

    public void resetearCampos(){
        nombre.setText("");
        edad.setText("");
        ciclo.setText("");
        curso.setText("");
        notaMedia.setText("");
    }

}
