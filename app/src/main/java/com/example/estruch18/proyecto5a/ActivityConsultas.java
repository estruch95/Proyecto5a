package com.example.estruch18.proyecto5a;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityConsultas extends AppCompatActivity {
    //ATRIBUTOS DE LA CLASE
    private MyDbAdapter dbAdapter;

    private RadioGroup grupoRegistro;
    private TextView tvResultadoConsulta;
    private ListView lvResultado;
    private Button btnConsultar;
    private String registroSeleccionado;
    private EditText ciclo, curso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //EJECUCIÓN DE MÉTODOS
        declaracionViews();
    }

    public void declaracionViews(){
        grupoRegistro = (RadioGroup)findViewById(R.id.grupoRegistro);
        tvResultadoConsulta = (TextView)findViewById(R.id.tvResultadosConsulta);
        lvResultado = (ListView)findViewById(R.id.lvResultado);
        btnConsultar = (Button)findViewById(R.id.btnConsultar);
        ciclo = (EditText)findViewById(R.id.etCiclo);
        curso = (EditText)findViewById(R.id.etCurso);
    }

    //SELECCIÓN DE TIPO DE REGISTRO OBTENIDO EN EL RADIOGROUP
    public void getTipoRegistro(){
        int idRadioSelected = grupoRegistro.getCheckedRadioButtonId();
        RadioButton radioSeleccionado = (RadioButton)findViewById(idRadioSelected);

        if(radioSeleccionado.getText().toString().equals("Profesor")){
            //Toast.makeText(getApplicationContext(), "Has seleccionado profesor", Toast.LENGTH_SHORT).show();
            registroSeleccionado = "Profesor";
        }
        else if(radioSeleccionado.getText().toString().equals("Estudiante")){
            //Toast.makeText(getApplicationContext(), "Has seleccionado estudiante", Toast.LENGTH_SHORT).show();
            registroSeleccionado = "Estudiante";
        }
        else{
            //Toast.makeText(getApplicationContext(), "Has seleccionado todos", Toast.LENGTH_SHORT).show();
            registroSeleccionado = "Todos";
        }
    }

    //Método encargado de actualizar un TextView a partir de la selección del tipo de registro a consultar
    public void actualizarTextViewResultados(){
        if(registroSeleccionado.equals("Profesor")){
            tvResultadoConsulta.setVisibility(View.VISIBLE);
            tvResultadoConsulta.setText("PROFESORES OBTENIDOS:");
        }
        else if(registroSeleccionado.equals("Estudiante")){
            tvResultadoConsulta.setVisibility(View.VISIBLE);
            tvResultadoConsulta.setText("ESTUDIANTES OBTENIDOS:");
        }
        else{
            tvResultadoConsulta.setVisibility(View.VISIBLE);
            tvResultadoConsulta.setText("REGISTROS OBTENIDOS:");
        }
    }

    //LISTENER DEL BOTÓN CONSULTAR
    public void accionBtnConsultar(View v){
        getTipoRegistro();
        actualizarTextViewResultados();

        //Obtenemos la instancia
        dbAdapter = new MyDbAdapter(this);
        dbAdapter.open();

        ArrayAdapter adaptador;

        if(registroSeleccionado.equals("Profesor")){
            if(ciclo.getText().toString().equals("") && curso.getText().toString().equals("")){
                ArrayList<String> profesores = dbAdapter.loadProfesores();
                adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesores);
                adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                lvResultado.setAdapter(adaptador);
            }
            else if(ciclo.getText().toString().length() != 0 && curso.getText().toString().length() != 0){
                ArrayList<String> profesoresCiclo = dbAdapter.loadProfesorPorCicloYcurso(ciclo.getText().toString(), Integer.parseInt(curso.getText().toString()));
                adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesoresCiclo);
                adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                lvResultado.setAdapter(adaptador);
            }
            else{
                Toast.makeText(getApplicationContext(), "Debes rellenar curso y ciclo", Toast.LENGTH_SHORT).show();
            }
        }
        else if(registroSeleccionado.equals("Estudiante")){
            if(ciclo.getText().toString().equals("") && curso.getText().toString().equals("")){
                ArrayList<String> estudiantes = dbAdapter.loadEstudiantes();
                adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes);
                adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                lvResultado.setAdapter(adaptador);
            }
            else if(ciclo.getText().toString().length() != 0 && curso.getText().toString().length() != 0){
                ArrayList<String> estudiantesCiclo = dbAdapter.loadEstudiantePorCicloYcurso(ciclo.getText().toString(), Integer.parseInt(curso.getText().toString()));
                adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantesCiclo);
                adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                lvResultado.setAdapter(adaptador);
            }
            else{
                Toast.makeText(getApplicationContext(), "Debes rellenar curso y ciclo", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            ArrayList<String> todos = dbAdapter.loadTodos();
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, todos);
            adaptador.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            lvResultado.setAdapter(adaptador);
        }
    }
}
