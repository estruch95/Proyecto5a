package com.example.estruch18.proyecto5a;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

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
