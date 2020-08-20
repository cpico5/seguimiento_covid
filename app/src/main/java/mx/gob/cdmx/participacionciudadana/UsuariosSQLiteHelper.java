package mx.gob.cdmx.participacionciudadana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.UUID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

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


    UUID uuid = UUID.randomUUID();

    public String tablet;
    InputStream datos, usuarios, nofue, acambio, prd, pri, pan, morena, independiente = null;

    static Nombre nom = new Nombre();
    static String nombreE = nom.nombreEncuesta();


    static String ID = getHostName(null);
    static String prefix = ID;

    // private static final String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreE+"_"+prefix+"";
    private static final int DATABASE_VERSION = 17;


    public UsuariosSQLiteHelper(Context context, String name, CursorFactory factory, int version, String DATABASE_NAME) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
// TODO Auto-generated constructor stub
        try {
            datos = context.getAssets().open("datos.sql");
            usuarios = context.getAssets().open("usuarios.sql");

        } catch (Exception ex) {
            Log.i(null, "HORROR-1: " + ex.fillInStackTrace());
        }

    }


/////////////////////  TABLA ENCUESTAS  ///////////////////////////////////////////////

    public static class TablaEncuestas {
        public static String TABLA_ENCUESTAS = "encuestas";
        public static String COLUMNA_CONSECUTIVO_DIARIO = "consecutivo_diario";
        public static String COLUMNA_EQUIPO = "equipo";
        public static String COLUMNA_USUARIO = "usuario";
        public static String COLUMNA_NOMBRE_ENCUESTA = "nombre_encuesta";
        public static String COLUMNA_FECHA = "fecha";
        public static String COLUMNA_HORA = "hora";
        public static String COLUMNA_imei = "imei";
        public static String COLUMNA_SECCION = "seccion";
        public static String COLUMNA_latitud = "latitud";
        public static String COLUMNA_longitud = "longitud";
        public static String COLUMNA_alcaldia = "alcaldia";
        //INICIAN PREGUNTAS
        public static String COLUMNA_pregunta_1 = "pregunta_1";
        public static String COLUMNA_pregunta_2 = "pregunta_2";
        public static String COLUMNA_pregunta_3 = "pregunta_3";
        public static String COLUMNA_genero = "genero";

        public static String COLUMNA_abandono = "abandono";
        // FINALIZAN PREGUNTAS
        public static String COLUMNA_TIEMPO = "tiempo";
        public static String COLUMNA_TIPO_CAPTURA = "tipo_captura";
        public static String COLUMNA_enviado = "enviado";
        public static String COLUMNA_observaciones = "observaciones";
        public static String COLUMNA_direccion_gps = "direccion_gps";
        public static String COLUMNA_calle = "calle";
        public static String COLUMNA_num_exterior = "num_exterior";
        public static String COLUMNA_num_interior = "num_interior";
        public static String COLUMNA_manzana = "manzana";
        public static String COLUMNA_lote = "lote";
        public static String COLUMNA_telefono_contacto = "telefono_contacto";
        public static String COLUMNA_paterno = "paterno";
        public static String COLUMNA_nombres = "nombres";
        public static String COLUMNA_materno = "materno";
        public static String COLUMNA_telefono_sospecha = "telefono_sospecha";
    }


    private static final String DATABASE_ENCUESTA = "create table "
            + TablaEncuestas.TABLA_ENCUESTAS + "("
            + "id integer primary key autoincrement,"
            + TablaEncuestas.COLUMNA_CONSECUTIVO_DIARIO + " text not null, "
            + TablaEncuestas.COLUMNA_EQUIPO + " text not null, "
            + TablaEncuestas.COLUMNA_USUARIO + " text not null, "
            + TablaEncuestas.COLUMNA_NOMBRE_ENCUESTA + " text not null, "
            + TablaEncuestas.COLUMNA_FECHA + " date not null, "
            + TablaEncuestas.COLUMNA_HORA + " text not null, "
            + TablaEncuestas.COLUMNA_imei + " text not null, "
            + TablaEncuestas.COLUMNA_SECCION + " INTEGER not null, "
            + TablaEncuestas.COLUMNA_latitud + " text, "
            + TablaEncuestas.COLUMNA_longitud + " text, "
            + TablaEncuestas.COLUMNA_alcaldia + " text, "

            + TablaEncuestas.COLUMNA_pregunta_1 + " text, "
            + TablaEncuestas.COLUMNA_pregunta_2 + " text, "
            + TablaEncuestas.COLUMNA_pregunta_3 + " text, "
            + TablaEncuestas.COLUMNA_genero + " text, "

            + TablaEncuestas.COLUMNA_abandono + " text, "
            + TablaEncuestas.COLUMNA_TIEMPO + " text, "
            + TablaEncuestas.COLUMNA_TIPO_CAPTURA + " text, "
            + TablaEncuestas.COLUMNA_enviado + " text, "

            + TablaEncuestas.COLUMNA_observaciones + " text, "
            + TablaEncuestas.COLUMNA_lote + " text, "
            + TablaEncuestas.COLUMNA_num_interior + " text, "
            + TablaEncuestas.COLUMNA_manzana + " text, "
            + TablaEncuestas.COLUMNA_direccion_gps + " text, "
            + TablaEncuestas.COLUMNA_calle + " text, "
            + TablaEncuestas.COLUMNA_num_exterior
            + TablaEncuestas.COLUMNA_telefono_contacto + " text, "
            + TablaEncuestas.COLUMNA_paterno + " text, "
            + TablaEncuestas.COLUMNA_nombres + " text, "
            + TablaEncuestas.COLUMNA_materno + " text, "
            + TablaEncuestas.COLUMNA_telefono_sospecha + " text); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        db.execSQL(DATABASE_ENCUESTA);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
//        db.execSQL("drop table if exists " + TablaEncuestas.TABLA_ENCUESTAS);
//        onCreate(db);

        Log.i(null, "cqs --->> Versiones:  1->" + oldVersion + "2->" + newVersion);
        if (newVersion > oldVersion) {
            try {
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN observaciones");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN lote");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN num_interior");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN manzana");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN direccion_gps");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN calle");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN num_exterior");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN telefono_contacto");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN paterno");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN nombres");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN materno");
                db.execSQL("ALTER TABLE  encuestas ADD COLUMN telefono_sospecha");

            } catch (Exception e) {
                Log.i(null, "cqs --->> Columna Observaciones; " + e.getMessage());
            }

        } else {
            db.execSQL("drop table if exists " + TablaEncuestas.TABLA_ENCUESTAS);
            onCreate(db);
        }
    }
}
