package mx.gob.cdmx.seguimientocovid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import mx.gob.cdmx.seguimientocovid.db.DaoManager;
import mx.gob.cdmx.seguimientocovid.model.Alcaldia;
import mx.gob.cdmx.seguimientocovid.model.Bitacora;
import mx.gob.cdmx.seguimientocovid.model.Colonia;
import mx.gob.cdmx.seguimientocovid.model.Status;
import mx.gob.cdmx.seguimientocovid.model.TelefonoAsignado;
import mx.gob.cdmx.seguimientocovid.model.Usuario;

public class UsuariosSQLiteHelper2 extends SQLiteOpenHelper {

    private static final String ENCODING = "ISO-8859-1";


    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    public String tablet;
    InputStream datos, usuarios, secciones, brigadas, prd, pri, pan, morena, independiente = null;

    static Nombre nom = new Nombre();
    static String nombreD = nom.nombreDatos();

    static String ID = getHostName(null);
    static String prefix = "listado";

    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreD + "_" + prefix + "";
    private static final int DATABASE_VERSION = 17;

    public UsuariosSQLiteHelper2(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
        try {
            datos = context.getAssets().open("datos.sql");
            usuarios = context.getAssets().open("usuarios.sql");
            secciones = context.getAssets().open("secciones.sql");
            brigadas = context.getAssets().open("brigadas.sql");

        } catch (Exception ex) {
            Log.i(null, "HORROR-1: " + ex.fillInStackTrace());
        }

    }

//////////////////////   TABLA DATOS  //////////////////////////////////////////////////////////////


//////////////////////TABLA DATOS  //////////////////////////////////////////////////////////////


    public static class TablaDatos {
        public static String TABLA_DATOS = "datos";
        public static String COLUMNA_SECCION = "seccion";
        public static String COLUMNA_DISTRITO = "distrito";
        public static String COLUMNA_MUNICIPIO = "municipio";
        public static String COLUMNA_DELEGACION = "delegacion";
        public static String COLUMNA_EQUIPOS = "equipo";
        public static String COLUMNA_COORDINADOR = "coordinador";
        public static String COLUMNA_Usuario = "usuario";

    }

    private static final String DATABASE_DATOS = "create table "
    + TablaDatos.TABLA_DATOS + "("
    + TablaDatos.COLUMNA_SECCION + " INTEGER not null, "
    + TablaDatos.COLUMNA_DISTRITO + " text not null, "
    + TablaDatos.COLUMNA_MUNICIPIO + " text not null, "
    + TablaDatos.COLUMNA_DELEGACION + " integer not null, "
    + TablaDatos.COLUMNA_EQUIPOS + " text not null, "
    + TablaDatos.COLUMNA_COORDINADOR + " text not null, "
    + TablaDatos.COLUMNA_Usuario + " text not null); ";

    //////////////////////TABLA BRIGADAS  //////////////////////////////////////////////////////////////


    public static class TablaBrigadas {
        public static String TABLA_BRIGADAS = "brigadas";
        public static String COLUMNA_numero_brigada = "numero_brigada";
        public static String COLUMNA_responsable = "responsable";
        public static String COLUMNA_telefono = "telefono";
        public static String COLUMNA_imei = "imei";
        public static String COLUMNA_usuario = "usuario";
        public static String COLUMNA_alcaldia = "alcaldia";


    }

    private static final String DATABASE_BRIGADAS = "create table "
            + TablaBrigadas.TABLA_BRIGADAS + "("
            + TablaBrigadas.COLUMNA_numero_brigada + " INTEGER not null, "
            + TablaBrigadas.COLUMNA_responsable + " text not null, "
            + TablaBrigadas.COLUMNA_telefono + " text not null, "
            + TablaBrigadas.COLUMNA_imei + " integer not null, "
            + TablaBrigadas.COLUMNA_usuario + " text not null, "
            + TablaBrigadas.COLUMNA_alcaldia + " text not null); ";

    //////////////////////TABLA SECCIONES  //////////////////////////////////////////////////////////////


    public static class TablaSecciones {
        public static String TABLA_SECCIONES = "secciones";
        public static String COLUMNA_SECCION = "seccion";
        public static String COLUMNA_DISTRITO = "distrito";
        public static String COLUMNA_MUNICIPIO = "municipio";
        public static String COLUMNA_DELEGACION = "delegacion";

    }

    private static final String DATABASE_SECCIONES = "create table "
            + TablaSecciones.TABLA_SECCIONES + "("
            + TablaSecciones.COLUMNA_SECCION + " INTEGER not null, "
            + TablaSecciones.COLUMNA_DISTRITO + " text not null, "
            + TablaSecciones.COLUMNA_MUNICIPIO + " text not null, "
            + TablaSecciones.COLUMNA_DELEGACION + " text not null); ";


///////////////////////////  TABLA USUARIOS	 /////////////////////////////////////////////////////////

    public static class TablaUsuarios {
        public static String TABLA_USUARIOS = "usuarios";
        public static String COLUMNA_USUARIO = "usuario";
        public static String COLUMNA_PASSWORD = "password";

    }

    private static final String DATABASE_USUARIOS = "create table "
    + TablaUsuarios.TABLA_USUARIOS + "("
    + TablaUsuarios.COLUMNA_USUARIO + " text not null, "
    + TablaUsuarios.COLUMNA_PASSWORD + " text not null); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        db.execSQL(DATABASE_DATOS);
        db.execSQL(DATABASE_USUARIOS);
        db.execSQL(DATABASE_SECCIONES);
        db.execSQL(DATABASE_BRIGADAS);

//        db.execSQL(DaoManager.generateCreateQueryString(Usuario.class));
//        db.execSQL(DaoManager.generateCreateQueryString(Status.class));
//        db.execSQL(DaoManager.generateCreateQueryString(Alcaldia.class));
//        db.execSQL(DaoManager.generateCreateQueryString(Colonia.class));
//        db.execSQL(DaoManager.generateCreateQueryString(Bitacora.class));
//        db.execSQL(DaoManager.generateCreateQueryString(TelefonoAsignado.class));

        cargaDatos(db);
        cargaUsuarios(db);
        cargaSecciones(db);
        cargaBrigadas(db);

    }


    public void cargaDatos(SQLiteDatabase db) {
        InputStream tabla = datos;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }

    public void cargaUsuarios(SQLiteDatabase db) {
        InputStream tabla = usuarios;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }


    public void cargaSecciones(SQLiteDatabase db) {
        InputStream tabla = secciones;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }

    public void cargaBrigadas(SQLiteDatabase db) {
        InputStream tabla = brigadas;
        try {

            if (tabla != null) {
                db.beginTransaction();
                BufferedReader reader = new BufferedReader(new InputStreamReader(tabla, ENCODING));
                String line = reader.readLine();
                while (!TextUtils.isEmpty(line)) {
                    db.execSQL(line);
                    line = reader.readLine();
                }
                db.setTransactionSuccessful();
            }
        } catch (Exception ex) {
            Log.i(null, "HORROR-2: " + ex.getMessage());
        } finally {
            db.endTransaction();
            if (tabla != null) {
                try {
                    tabla.close();
                } catch (IOException e) {
                    Log.i(null, "HORROR-3; " + e.getMessage());
                }
            }
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        db.execSQL("DROP table if exists " + TablaDatos.TABLA_DATOS);
        db.execSQL("DROP table if exists " + TablaUsuarios.TABLA_USUARIOS);
        db.execSQL("DROP table if exists " + TablaSecciones.TABLA_SECCIONES);
        db.execSQL("DROP table if exists " + TablaBrigadas.TABLA_BRIGADAS);

        db.execSQL(DaoManager.generateDropIfExistsQueryString(Usuario.class));
        db.execSQL(DaoManager.generateDropIfExistsQueryString(Status.class));
        db.execSQL(DaoManager.generateDropIfExistsQueryString(Alcaldia.class));
        db.execSQL(DaoManager.generateDropIfExistsQueryString(Colonia.class));
        db.execSQL(DaoManager.generateDropIfExistsQueryString(Bitacora.class));
        db.execSQL(DaoManager.generateDropIfExistsQueryString(TelefonoAsignado.class));

        onCreate(db);
    }
}
