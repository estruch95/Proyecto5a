package com.example.estruch18.proyecto5a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by estruch18 on 2/12/15.
 */
public class MyDbAdapter {

    //Constantes definidas
    //Nombre de la base de datos
    private static final String BBDD_NAME = "Instituto.db";
    //Tablas de la base de datos
    private static final String BBDD_TABLA_PROFESORES = "Profesor";
    private static final String BBDD_TABLA_ESTUDIANTES = "Estudiante";
    //Versión de la base de datos
    private static final int BBDD_VERSION = 1;

    //Columnas
    private static final String NOMBRE = "nombre";
    private static final String EDAD = "edad";
    private static final String CICLO = "ciclo";
    private static final String CURSO = "curso";
    private static final String NOTAMEDIA = "notaMedia";
    private static final String DESPACHO = "despacho";

    //Creación de las distintas tablas de la bbdd
    private static final String CREAR_TABLA_PROFESORES = "CREATE TABLE "+BBDD_TABLA_PROFESORES
            +" ( id         integer     primary key autoincrement, " +
            "    nombre     text        not null," +
            "    edad       integer     not null," +
            "    ciclo      text        not null," +
            "    curso      integer     not null," +
            "    despacho   text        not null);";

    private static final String CREAR_TABLA_ESTUDIANTES = "CREATE TABLE "+BBDD_TABLA_ESTUDIANTES
            +" ( id         integer     primary key autoincrement, " +
            "    nombre     text        not null," +
            "    edad       integer     not null," +
            "    ciclo      text        not null," +
            "    curso      integer     not null," +
            "    notaMedia  float      not null);";

    //Eliminación de las distintas tablas
    private static final String ELIMINAR_TABLA_PROFESORES = "DROP TABLE IF EXISTS "+BBDD_TABLA_PROFESORES+";";
    private static final String ELIMINAR_TABLA_ESTUDIANTES = "DROP TABLE IF EXISTS "+BBDD_TABLA_ESTUDIANTES+";";

    //CONTEXTO
    private final Context context;
    //SQLITEOPENHELPER (para crear y actualizar la bbdd)
    private MyDbHelper dbHelper;
    //INSTANCIA
    private SQLiteDatabase db;

    public MyDbAdapter(Context c){
        context = c;
        dbHelper = new MyDbHelper(context, BBDD_NAME, null, BBDD_VERSION);
        //OJO open();
    }

    public void open(){

        try{
            db = dbHelper.getWritableDatabase();
        }
        catch(SQLiteException e){
            db = dbHelper.getReadableDatabase();
        }

    }

    public void insertarProfesor(String n, int e, String ci, int cu, String d){
        //NUEVO REGISTRO
        ContentValues nuevosValores = new ContentValues();
        //ASIGNACIÓN DE VALORES A SUS RESPECTIVOS CAMPOS
        nuevosValores.put(NOMBRE,n);
        nuevosValores.put(EDAD, e);
        nuevosValores.put(CICLO, ci);
        nuevosValores.put(CURSO, cu);
        nuevosValores.put(DESPACHO, d);

        //REALIZAMOS LA INSERCIÓN DE DATOS
        db.insert(BBDD_TABLA_PROFESORES, null, nuevosValores);
    }

    public void insertarEstudiante(String n, int e, String ci, int cu, String nMedia){
        //NUEVO REGISTRO
        ContentValues nuevosValores = new ContentValues();
        //ASIGNACIÓN DE VALORES A SUS RESPECTIVOS CAMPOS
        nuevosValores.put(NOMBRE,n);
        nuevosValores.put(EDAD, e);
        nuevosValores.put(CICLO, ci);
        nuevosValores.put(CURSO, cu);
        nuevosValores.put(NOTAMEDIA, nMedia);

        //REALIZAMOS LA INSERCIÓN DE DATOS
        db.insert(BBDD_TABLA_ESTUDIANTES, null, nuevosValores);
    }

    public void dropBBDD(){
        context.deleteDatabase(BBDD_NAME);
    }

    public ArrayList<String> loadProfesores(){
        //ARRAYLIST DONDE SE ALMACENARAN LOS PROFESORES OBTENIDOS
        ArrayList<String> profesores = new ArrayList<String>();

        //MEDIANTE UN CURSOR OBTENEMOS LOS DATOS DE LA CONSULTA
        Cursor cursorProfesores = db.query(BBDD_TABLA_PROFESORES,null,null,null,null,null,null);

        //REALIZAMOS UN RECORRIDO DE EL CURSOR OBTENIDO
        if(cursorProfesores != null && cursorProfesores.moveToFirst()){

            do{
                profesores.add(cursorProfesores.getString(1)+" ID: "+cursorProfesores.getString(0));
            }
            while (cursorProfesores.moveToNext());

        }

        return profesores;
    }

    public ArrayList<String> loadEstudiantes(){
        //ARRAYLIST DONDE SE ALMACENARAN LOS ESTUDIANTES OBTENIDOS
        ArrayList<String> estudiantes = new ArrayList<String>();

        //MEDIANTE UN CURSOR OBTENEMOS LOS DATOS DE LA CONSULTA
        Cursor cursorEstudiantes = db.query(BBDD_TABLA_ESTUDIANTES,null,null,null,null,null,null);

        //REALIZAMOS UN RECORRIDO DE EL CURSOR OBTENIDO
        if(cursorEstudiantes != null && cursorEstudiantes.moveToFirst()){

            do{
                estudiantes.add(cursorEstudiantes.getString(1)+" ID: "+cursorEstudiantes.getString(0));
            }
            while (cursorEstudiantes.moveToNext());

        }
        return estudiantes;
    }

    public ArrayList<String> loadTodos(){
        //ARRAYLIST DONDE SE ALMACENARAN TODOS LOS REGISTROS OBTENIDOS
        ArrayList<String> todos = new ArrayList<String>();

        //MEDIANTE DOS CURSORES OBTENEMOS TANTO ESTUDIANTES COMO PROFESORES
        Cursor cursorEstudiantes = db.query(BBDD_TABLA_ESTUDIANTES,null,null,null,null,null,null);
        Cursor cursorProfesores = db.query(BBDD_TABLA_PROFESORES,null,null,null,null,null,null);

        //REALIZAMOS UN RECORRIDO DE AMBOS CURSORES Y VAMOS AÑADIENDO A NUESTRO ARRAYLIST
        if(cursorEstudiantes != null && cursorEstudiantes.moveToFirst()){

            do{
                todos.add(cursorEstudiantes.getString(1)+" ID: "+cursorEstudiantes.getString(0));
            }
            while (cursorEstudiantes.moveToNext());

        }

        if(cursorProfesores != null && cursorProfesores.moveToFirst()){

            do{
                todos.add(cursorProfesores.getString(1)+" ID: "+cursorProfesores.getString(0));
            }
            while (cursorProfesores.moveToNext());

        }
        return todos;
    }

    public ArrayList<String> loadEstudiantePorCicloYcurso(String ciclo, int curso){
        //ARRAYLIST DONDE SE ALMACENARAN LOS ESTUDIANTES OBTENIDOS
        ArrayList<String> estudiantes = new ArrayList<String>();

        //MEDIANTE UN CURSOR OBTENEMOS LOS DATOS
        Cursor cursorEstCiclo = db.rawQuery("SELECT * FROM Estudiante WHERE ciclo = '"+ciclo+"' AND curso = "+curso+";", null);

        //REALIZAMOS UN RECORRIDO DE EL CURSOR OBTENIDO
        if(cursorEstCiclo != null && cursorEstCiclo.moveToFirst()){

            do{
                estudiantes.add(cursorEstCiclo.getString(1)+" ID: "+cursorEstCiclo.getString(0));
            }
            while (cursorEstCiclo.moveToNext());

        }
        return estudiantes;
    }

    public ArrayList<String> loadProfesorPorCicloYcurso(String ciclo, int curso){
        //ARRAYLIST DONDE SE ALMACENARAN LOS ESTUDIANTES OBTENIDOS
        ArrayList<String> profesores = new ArrayList<String>();

        //MEDIANTE UN CURSOR OBTENEMOS LOS DATOS
        Cursor cursorProCiclo = db.rawQuery("SELECT * FROM Profesor WHERE ciclo = '"+ciclo+"' AND curso = "+curso+";", null);

        //REALIZAMOS UN RECORRIDO DE EL CURSOR OBTENIDO
        if(cursorProCiclo != null && cursorProCiclo.moveToFirst()){

            do{
                profesores.add(cursorProCiclo.getString(1)+" ID: "+cursorProCiclo.getString(0));
            }
            while (cursorProCiclo.moveToNext());

        }
        return profesores;
    }

    public void dropRegistro(int idRegistro){
        db.execSQL("DELETE FROM "+BBDD_TABLA_PROFESORES+" WHERE id = "+idRegistro+";");
        db.execSQL("DELETE FROM "+BBDD_TABLA_ESTUDIANTES+" WHERE id = "+idRegistro+";");
    }

    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context,name,factory,version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREAR_TABLA_PROFESORES);
            db.execSQL(CREAR_TABLA_ESTUDIANTES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(ELIMINAR_TABLA_PROFESORES);
            db.execSQL(ELIMINAR_TABLA_ESTUDIANTES);
            onCreate(db);
        }
    }
}
