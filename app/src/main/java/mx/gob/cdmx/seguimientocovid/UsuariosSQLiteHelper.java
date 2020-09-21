package mx.gob.cdmx.seguimientocovid;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
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


/////////////////////  TABLA CONTACTOS  ///////////////////////////////////////////////

    public static class TablaContactos {
        public static String TABLA_Contactos = "contactos";
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
        public static String COLUMNA_pregunta_A="pregunta_A";
        public static String COLUMNA_pregunta_B="pregunta_B";
        public static String COLUMNA_pregunta_1="pregunta_1";
        public static String COLUMNA_pregunta_2="pregunta_2";
        public static String COLUMNA_pregunta_3="pregunta_3";
        public static String COLUMNA_pregunta_4="pregunta_4";
        public static String COLUMNA_pregunta_5="pregunta_5";
        public static String COLUMNA_pregunta_6="pregunta_6";
        public static String COLUMNA_pregunta_6a="pregunta_6a";
        public static String COLUMNA_pregunta_6b="pregunta_6b";
        public static String COLUMNA_pregunta_6c="pregunta_6c";
        public static String COLUMNA_pregunta_6d="pregunta_6d";
        public static String COLUMNA_pregunta_6e="pregunta_6e";
        public static String COLUMNA_pregunta_6f="pregunta_6f";
        public static String COLUMNA_pregunta_6g="pregunta_6g";
        public static String COLUMNA_pregunta_6h="pregunta_6h";
        public static String COLUMNA_pregunta_6i="pregunta_6i";
        public static String COLUMNA_pregunta_6j="pregunta_6j";
        public static String COLUMNA_pregunta_6k="pregunta_6k";
        public static String COLUMNA_pregunta_7="pregunta_7";
        public static String COLUMNA_pregunta_8="pregunta_8";
        public static String COLUMNA_pregunta_8a="pregunta_8a";
        public static String COLUMNA_pregunta_8b="pregunta_8b";
        public static String COLUMNA_pregunta_8c="pregunta_8c";
        public static String COLUMNA_pregunta_8d="pregunta_8d";
        public static String COLUMNA_pregunta_8e="pregunta_8e";
        public static String COLUMNA_pregunta_8f="pregunta_8f";
        public static String COLUMNA_pregunta_9="pregunta_9";
        public static String COLUMNA_pregunta_10="pregunta_10";
        public static String COLUMNA_pregunta_11="pregunta_11";
        public static String COLUMNA_pregunta_12="pregunta_12";
        public static String COLUMNA_pregunta_13="pregunta_13";
        public static String COLUMNA_pregunta_13a="pregunta_13a";
        public static String COLUMNA_pregunta_13b="pregunta_13b";
        public static String COLUMNA_pregunta_13c="pregunta_13c";
        public static String COLUMNA_pregunta_13d="pregunta_13d";
        public static String COLUMNA_pregunta_13e="pregunta_13e";
        public static String COLUMNA_pregunta_14="pregunta_14";
        public static String COLUMNA_pregunta_15="pregunta_15";
        public static String COLUMNA_pregunta_16="pregunta_16";
        public static String COLUMNA_pregunta_16a="pregunta_16a";
        public static String COLUMNA_pregunta_16b="pregunta_16b";
        public static String COLUMNA_pregunta_16c="pregunta_16c";
        public static String COLUMNA_pregunta_16d="pregunta_16d";
        public static String COLUMNA_pregunta_16e="pregunta_16e";
        public static String COLUMNA_pregunta_16f="pregunta_16f";
        public static String COLUMNA_pregunta_16g="pregunta_16g";
        public static String COLUMNA_pregunta_16h="pregunta_16h";
        public static String COLUMNA_pregunta_16i="pregunta_16i";
        public static String COLUMNA_pregunta_16j="pregunta_16j";
        public static String COLUMNA_pregunta_16k="pregunta_16k";
        public static String COLUMNA_pregunta_17a="pregunta_17a";
        public static String COLUMNA_pregunta_17b="pregunta_17b";
        public static String COLUMNA_pregunta_17c="pregunta_17c";
        public static String COLUMNA_pregunta_17d="pregunta_17d";
        public static String COLUMNA_pregunta_18="pregunta_18";
        public static String COLUMNA_pregunta_18a="pregunta_18a";
        public static String COLUMNA_pregunta_18b="pregunta_18b";
        public static String COLUMNA_pregunta_18c="pregunta_18c";
        public static String COLUMNA_pregunta_18d="pregunta_18d";
        public static String COLUMNA_pregunta_18e="pregunta_18e";
        public static String COLUMNA_pregunta_18f="pregunta_18f";
        public static String COLUMNA_pregunta_19="pregunta_19";
        public static String COLUMNA_pregunta_19a="pregunta_19a";
        public static String COLUMNA_pregunta_19b="pregunta_19b";
        public static String COLUMNA_pregunta_19c="pregunta_19c";
        public static String COLUMNA_pregunta_19d="pregunta_19d";
        public static String COLUMNA_pregunta_19e="pregunta_19e";
        public static String COLUMNA_pregunta_20="pregunta_20";
        public static String COLUMNA_pregunta_21="pregunta_21";
        public static String COLUMNA_pregunta_22="pregunta_22";
        public static String COLUMNA_pregunta_23="pregunta_23";
        public static String COLUMNA_pregunta_24="pregunta_24";

        public static String COLUMNA_abandono="abandono";
        // FINALIZAN PREGUNTAS
        public static String COLUMNA_TIEMPO = "tiempo";
        public static String COLUMNA_TIPO_CAPTURA = "tipo_captura";
        public static String COLUMNA_enviado = "enviado";

    }


    private static final String DATABASE_Contactos = "create table "
            + TablaContactos.TABLA_Contactos + "("
            + "id integer primary key autoincrement,"
            + TablaContactos.COLUMNA_CONSECUTIVO_DIARIO + " text not null, "
            + TablaContactos.COLUMNA_EQUIPO + " text not null, "
            + TablaContactos.COLUMNA_USUARIO + " text not null, "
            + TablaContactos.COLUMNA_NOMBRE_ENCUESTA + " text not null, "
            + TablaContactos.COLUMNA_FECHA + " date not null, "
            + TablaContactos.COLUMNA_HORA + " text not null, "
            + TablaContactos.COLUMNA_imei + " text not null, "
            + TablaContactos.COLUMNA_SECCION + " INTEGER not null, "
            + TablaContactos.COLUMNA_latitud + " text, "
            + TablaContactos.COLUMNA_longitud + " text, "
            + TablaContactos.COLUMNA_alcaldia + " text, "

            + TablaContactos.COLUMNA_pregunta_A +  " text, "
            + TablaContactos.COLUMNA_pregunta_B +  " text, "
            + TablaContactos.COLUMNA_pregunta_1 +  " text, "
            + TablaContactos.COLUMNA_pregunta_2 +  " text, "
            + TablaContactos.COLUMNA_pregunta_3 +  " text, "
            + TablaContactos.COLUMNA_pregunta_4 +  " text, "
            + TablaContactos.COLUMNA_pregunta_5 +  " text, "
            + TablaContactos.COLUMNA_pregunta_6 +  " text, "
            + TablaContactos.COLUMNA_pregunta_6a +  " text, "
            + TablaContactos.COLUMNA_pregunta_6b +  " text, "
            + TablaContactos.COLUMNA_pregunta_6c +  " text, "
            + TablaContactos.COLUMNA_pregunta_6d +  " text, "
            + TablaContactos.COLUMNA_pregunta_6e +  " text, "
            + TablaContactos.COLUMNA_pregunta_6f +  " text, "
            + TablaContactos.COLUMNA_pregunta_6g +  " text, "
            + TablaContactos.COLUMNA_pregunta_6h +  " text, "
            + TablaContactos.COLUMNA_pregunta_6i +  " text, "
            + TablaContactos.COLUMNA_pregunta_6j +  " text, "
            + TablaContactos.COLUMNA_pregunta_6k +  " text, "
            + TablaContactos.COLUMNA_pregunta_7 +  " text, "
            + TablaContactos.COLUMNA_pregunta_8 +  " text, "
            + TablaContactos.COLUMNA_pregunta_8a +  " text, "
            + TablaContactos.COLUMNA_pregunta_8b +  " text, "
            + TablaContactos.COLUMNA_pregunta_8c +  " text, "
            + TablaContactos.COLUMNA_pregunta_8d +  " text, "
            + TablaContactos.COLUMNA_pregunta_8e +  " text, "
            + TablaContactos.COLUMNA_pregunta_8f +  " text, "
            + TablaContactos.COLUMNA_pregunta_9 +  " text, "
            + TablaContactos.COLUMNA_pregunta_10 +  " text, "
            + TablaContactos.COLUMNA_pregunta_11 +  " text, "
            + TablaContactos.COLUMNA_pregunta_12 +  " text, "
            + TablaContactos.COLUMNA_pregunta_13 +  " text, "
            + TablaContactos.COLUMNA_pregunta_13a +  " text, "
            + TablaContactos.COLUMNA_pregunta_13b +  " text, "
            + TablaContactos.COLUMNA_pregunta_13c +  " text, "
            + TablaContactos.COLUMNA_pregunta_13d +  " text, "
            + TablaContactos.COLUMNA_pregunta_13e +  " text, "
            + TablaContactos.COLUMNA_pregunta_14 +  " text, "
            + TablaContactos.COLUMNA_pregunta_15 +  " text, "
            + TablaContactos.COLUMNA_pregunta_16 +  " text, "
            + TablaContactos.COLUMNA_pregunta_16a +  " text, "
            + TablaContactos.COLUMNA_pregunta_16b +  " text, "
            + TablaContactos.COLUMNA_pregunta_16c +  " text, "
            + TablaContactos.COLUMNA_pregunta_16d +  " text, "
            + TablaContactos.COLUMNA_pregunta_16e +  " text, "
            + TablaContactos.COLUMNA_pregunta_16f +  " text, "
            + TablaContactos.COLUMNA_pregunta_16g +  " text, "
            + TablaContactos.COLUMNA_pregunta_16h +  " text, "
            + TablaContactos.COLUMNA_pregunta_16i +  " text, "
            + TablaContactos.COLUMNA_pregunta_16j +  " text, "
            + TablaContactos.COLUMNA_pregunta_16k +  " text, "
            + TablaContactos.COLUMNA_pregunta_17a +  " text, "
            + TablaContactos.COLUMNA_pregunta_17b +  " text, "
            + TablaContactos.COLUMNA_pregunta_17c +  " text, "
            + TablaContactos.COLUMNA_pregunta_17d +  " text, "
            + TablaContactos.COLUMNA_pregunta_18 +  " text, "
            + TablaContactos.COLUMNA_pregunta_18a +  " text, "
            + TablaContactos.COLUMNA_pregunta_18b +  " text, "
            + TablaContactos.COLUMNA_pregunta_18c +  " text, "
            + TablaContactos.COLUMNA_pregunta_18d +  " text, "
            + TablaContactos.COLUMNA_pregunta_18e +  " text, "
            + TablaContactos.COLUMNA_pregunta_18f +  " text, "
            + TablaContactos.COLUMNA_pregunta_19 +  " text, "
            + TablaContactos.COLUMNA_pregunta_19a +  " text, "
            + TablaContactos.COLUMNA_pregunta_19b +  " text, "
            + TablaContactos.COLUMNA_pregunta_19c +  " text, "
            + TablaContactos.COLUMNA_pregunta_19d +  " text, "
            + TablaContactos.COLUMNA_pregunta_19e +  " text, "
            + TablaContactos.COLUMNA_pregunta_20 +  " text, "
            + TablaContactos.COLUMNA_pregunta_21 +  " text, "
            + TablaContactos.COLUMNA_pregunta_22 +  " text, "
            + TablaContactos.COLUMNA_pregunta_23 +  " text, "
            + TablaContactos.COLUMNA_pregunta_24 +  " text, "

            + TablaContactos.COLUMNA_abandono +  " text, "
            + TablaContactos.COLUMNA_TIEMPO + " text, "
            + TablaContactos.COLUMNA_TIPO_CAPTURA + " text, "
            + TablaContactos.COLUMNA_enviado + " text not null); ";

    /////////////////////  TABLA Adicionales  ///////////////////////////////////////////////

    public static class TablaAdicionales {
        public static String TABLA_Adicionales = "adicionales";
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

        public static String COLUMNA_abandono="abandono";
        // FINALIZAN PREGUNTAS
        public static String COLUMNA_TIEMPO = "tiempo";
        public static String COLUMNA_TIPO_CAPTURA = "tipo_captura";
        public static String COLUMNA_enviado = "enviado";
    }


    private static final String DATABASE_Adicionales = "create table "
            + TablaAdicionales.TABLA_Adicionales + "("
            + "id integer primary key autoincrement,"
            + TablaAdicionales.COLUMNA_CONSECUTIVO_DIARIO + " text not null, "
            + TablaAdicionales.COLUMNA_EQUIPO + " text not null, "
            + TablaAdicionales.COLUMNA_USUARIO + " text not null, "
            + TablaAdicionales.COLUMNA_NOMBRE_ENCUESTA + " text not null, "
            + TablaAdicionales.COLUMNA_FECHA + " date not null, "
            + TablaAdicionales.COLUMNA_HORA + " text not null, "
            + TablaAdicionales.COLUMNA_imei + " text not null, "
            + TablaAdicionales.COLUMNA_SECCION + " INTEGER not null, "
            + TablaAdicionales.COLUMNA_latitud + " text, "
            + TablaAdicionales.COLUMNA_longitud + " text, "
            + TablaAdicionales.COLUMNA_alcaldia + " text, "

            + TablaAdicionales.COLUMNA_pregunta_1 + " text, "
            + TablaAdicionales.COLUMNA_pregunta_2 + " text, "
            + TablaAdicionales.COLUMNA_pregunta_3 + " text, "
            + TablaAdicionales.COLUMNA_genero + " text, "

            + TablaAdicionales.COLUMNA_abandono + " text, "
            + TablaAdicionales.COLUMNA_TIEMPO + " text, "
            + TablaAdicionales.COLUMNA_TIPO_CAPTURA + " text, "
            + TablaAdicionales.COLUMNA_enviado + " text, "

            + TablaAdicionales.COLUMNA_abandono +  " text, "
            + TablaAdicionales.COLUMNA_TIEMPO + " text, "
            + TablaAdicionales.COLUMNA_TIPO_CAPTURA + " text, "
            + TablaAdicionales.COLUMNA_enviado + " text not null); ";


    @Override
    public void onCreate(SQLiteDatabase db) {
// TODO Auto-generated method stub
        db.execSQL(DATABASE_Contactos);
        db.execSQL(DATABASE_Adicionales);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// TODO Auto-generated method stub
        db.execSQL("drop table if exists " + TablaContactos.TABLA_Contactos);
        db.execSQL("drop table if exists " + TablaAdicionales.TABLA_Adicionales);
        onCreate(db);

//        Log.i(null, "cqs --->> Versiones:  1->" + oldVersion + "2->" + newVersion);
//        if (newVersion > oldVersion) {
//            try {
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN observaciones");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN lote");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN num_interior");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN manzana");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN direccion_gps");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN calle");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN num_exterior");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN telefono_contacto");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN paterno");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN nombres");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN materno");
//                db.execSQL("ALTER TABLE  encuestas ADD COLUMN telefono_sospecha");
//
//            } catch (Exception e) {
//                Log.i(null, "cqs --->> Columna Observaciones; " + e.getMessage());
//            }
//
//        } else {
//            db.execSQL("drop table if exists " + TablaEncuestas.TABLA_ENCUESTAS);
//            onCreate(db);
//        }
    }
}
