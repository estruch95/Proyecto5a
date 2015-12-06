package com.example.estruch18.proyecto5a;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Atributos de la clase
    private MyDbAdapter dbAdapter;
    private Button btnAddProfe, btnAddEstudiante, btnEliminarBBDD, btnEliminarRegistro, btnConsultarRegistro;
    private TextView tvId;
    private EditText etID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ejecución de métodos
        declaracionViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void declaracionViews(){
        btnAddProfe = (Button)findViewById(R.id.btnAddProfesor);
        btnAddEstudiante = (Button)findViewById(R.id.btnAddEstudiante);
        btnEliminarBBDD = (Button)findViewById(R.id.btnEliminarBBDD);
        btnEliminarRegistro = (Button)findViewById(R.id.btnEliminarRegistro);
        btnConsultarRegistro = (Button)findViewById(R.id.btnConsultarRegistro);

        tvId = (TextView)findViewById(R.id.tvID);
        etID = (EditText)findViewById(R.id.etID);
    }

    //LISTENERS DE LOS BOTONES

    public void accionBtnAddProfesor(View v){
        Intent actProfe = new Intent(getApplicationContext(), Activity_profesor.class);
        startActivity(actProfe);
    }

    public void accionBtnAddEstudiante(View v){
        Intent actEstudiante = new Intent(getApplicationContext(), Activity_alumno.class);
        startActivity(actEstudiante);
    }

    public void accionBtnEliminarBBDD(View v){
        dbAdapter = new MyDbAdapter(this);
        dbAdapter.open();
        dbAdapter.dropBBDD();
        //INFO
        Toast.makeText(getApplicationContext(), "La BBDD ha sido eliminada", Toast.LENGTH_SHORT).show();
    }

    public void accionBtnEliminarRegistro(View v){
        dbAdapter = new MyDbAdapter(this);
        dbAdapter.open();
        dbAdapter.dropRegistro(Integer.parseInt(etID.getText().toString()));
        //INFO
        Toast.makeText(getApplicationContext(), "El registro ha sido eliminado", Toast.LENGTH_SHORT).show();
        etID.setText("");
    }

    public void accionBtnConsultas(View v){
        Intent actConsultas = new Intent(getApplicationContext(), ActivityConsultas.class);
        startActivity(actConsultas);
    }

}
