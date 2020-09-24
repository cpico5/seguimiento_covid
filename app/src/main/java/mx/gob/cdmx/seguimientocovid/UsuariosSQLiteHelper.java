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
    private static final int DATABASE_VERSION = 21;


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
        //INICIAN PREGUNTAS
        public static String COLUMNA_pregunta_A="pregunta_a";
        public static String COLUMNA_pregunta_B="pregunta_b";
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
        public static String COLUMNA_pregunta_8cm="pregunta_8cm";
        public static String COLUMNA_pregunta_8cl="pregunta_8cl";
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
        public static String COLUMNA_pregunta_19s="pregunta_19s";
        public static String COLUMNA_pregunta_19as="pregunta_19as";
        public static String COLUMNA_pregunta_19bs="pregunta_19bs";
        public static String COLUMNA_pregunta_19cs="pregunta_19cs";
        public static String COLUMNA_pregunta_19ds="pregunta_19ds";
        public static String COLUMNA_pregunta_19es="pregunta_19es";
        public static String COLUMNA_pregunta_18b="pregunta_18b";
        public static String COLUMNA_pregunta_19m="pregunta_19m";
        public static String COLUMNA_pregunta_19am="pregunta_19am";
        public static String COLUMNA_pregunta_19bm="pregunta_19bm";
        public static String COLUMNA_pregunta_19cm="pregunta_19cm";
        public static String COLUMNA_pregunta_19dm="pregunta_19dm";
        public static String COLUMNA_pregunta_19em="pregunta_19em";
        public static String COLUMNA_pregunta_18c="pregunta_18c";
        public static String COLUMNA_pregunta_19t="pregunta_19t";
        public static String COLUMNA_pregunta_19at="pregunta_19at";
        public static String COLUMNA_pregunta_19bt="pregunta_19bt";
        public static String COLUMNA_pregunta_19ct="pregunta_19ct";
        public static String COLUMNA_pregunta_19dt="pregunta_19dt";
        public static String COLUMNA_pregunta_19et="pregunta_19et";
        public static String COLUMNA_pregunta_18d="pregunta_18d";
        public static String COLUMNA_pregunta_19ca="pregunta_19ca";
        public static String COLUMNA_pregunta_19aca="pregunta_19aca";
        public static String COLUMNA_pregunta_19bca="pregunta_19bca";
        public static String COLUMNA_pregunta_19cca="pregunta_19cca";
        public static String COLUMNA_pregunta_19dca="pregunta_19dca";
        public static String COLUMNA_pregunta_19eca="pregunta_19eca";
        public static String COLUMNA_pregunta_18e="pregunta_18e";
        public static String COLUMNA_pregunta_19cc="pregunta_19cc";
        public static String COLUMNA_pregunta_19acc="pregunta_19acc";
        public static String COLUMNA_pregunta_19bcc="pregunta_19bcc";
        public static String COLUMNA_pregunta_19ccc="pregunta_19ccc";
        public static String COLUMNA_pregunta_19dcc="pregunta_19dcc";
        public static String COLUMNA_pregunta_19ecc="pregunta_19ecc";
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
            + TablaContactos.COLUMNA_pregunta_8cm +  " text, "
            + TablaContactos.COLUMNA_pregunta_8cl +  " text, "
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
            + TablaContactos.COLUMNA_pregunta_19s +  " text, "
            + TablaContactos.COLUMNA_pregunta_19as +  " text, "
            + TablaContactos.COLUMNA_pregunta_19bs +  " text, "
            + TablaContactos.COLUMNA_pregunta_19cs +  " text, "
            + TablaContactos.COLUMNA_pregunta_19ds +  " text, "
            + TablaContactos.COLUMNA_pregunta_19es +  " text, "
            + TablaContactos.COLUMNA_pregunta_18b +  " text, "
            + TablaContactos.COLUMNA_pregunta_19m +  " text, "
            + TablaContactos.COLUMNA_pregunta_19am +  " text, "
            + TablaContactos.COLUMNA_pregunta_19bm +  " text, "
            + TablaContactos.COLUMNA_pregunta_19cm +  " text, "
            + TablaContactos.COLUMNA_pregunta_19dm +  " text, "
            + TablaContactos.COLUMNA_pregunta_19em +  " text, "
            + TablaContactos.COLUMNA_pregunta_18c +  " text, "
            + TablaContactos.COLUMNA_pregunta_19t +  " text, "
            + TablaContactos.COLUMNA_pregunta_19at +  " text, "
            + TablaContactos.COLUMNA_pregunta_19bt +  " text, "
            + TablaContactos.COLUMNA_pregunta_19ct +  " text, "
            + TablaContactos.COLUMNA_pregunta_19dt +  " text, "
            + TablaContactos.COLUMNA_pregunta_19et +  " text, "
            + TablaContactos.COLUMNA_pregunta_18d +  " text, "
            + TablaContactos.COLUMNA_pregunta_19ca +  " text, "
            + TablaContactos.COLUMNA_pregunta_19aca +  " text, "
            + TablaContactos.COLUMNA_pregunta_19bca +  " text, "
            + TablaContactos.COLUMNA_pregunta_19cca +  " text, "
            + TablaContactos.COLUMNA_pregunta_19dca +  " text, "
            + TablaContactos.COLUMNA_pregunta_19eca +  " text, "
            + TablaContactos.COLUMNA_pregunta_18e +  " text, "
            + TablaContactos.COLUMNA_pregunta_19cc +  " text, "
            + TablaContactos.COLUMNA_pregunta_19acc +  " text, "
            + TablaContactos.COLUMNA_pregunta_19bcc +  " text, "
            + TablaContactos.COLUMNA_pregunta_19ccc +  " text, "
            + TablaContactos.COLUMNA_pregunta_19dcc +  " text, "
            + TablaContactos.COLUMNA_pregunta_19ecc +  " text, "
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
        public static String COLUMNA_id_consecutivo_contacto = "id_consecutivo_contacto";
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
        //INICIAN PREGUNTAS
        public static String COLUMNA_pregunta_c="pregunta_c";
        public static String COLUMNA_pregunta_c_1="pregunta_c_1";
        public static String COLUMNA_pregunta_c_2="pregunta_c_2";
        public static String COLUMNA_pregunta_c_3="pregunta_c_3";
        public static String COLUMNA_pregunta_c_4="pregunta_c_4";
        public static String COLUMNA_pregunta_c_5="pregunta_c_5";
        public static String COLUMNA_pregunta_c_6="pregunta_c_6";
        public static String COLUMNA_pregunta_c_6a="pregunta_c_6a";
        public static String COLUMNA_pregunta_c_6b="pregunta_c_6b";
        public static String COLUMNA_pregunta_c_6c="pregunta_c_6c";
        public static String COLUMNA_pregunta_c_6d="pregunta_c_6d";
        public static String COLUMNA_pregunta_c_6e="pregunta_c_6e";
        public static String COLUMNA_pregunta_c_6f="pregunta_c_6f";
        public static String COLUMNA_pregunta_c_6g="pregunta_c_6g";
        public static String COLUMNA_pregunta_c_6h="pregunta_c_6h";
        public static String COLUMNA_pregunta_c_6i="pregunta_c_6i";
        public static String COLUMNA_pregunta_c_6j="pregunta_c_6j";
        public static String COLUMNA_pregunta_c_6k="pregunta_c_6k";
        public static String COLUMNA_pregunta_c_7="pregunta_c_7";
        public static String COLUMNA_pregunta_c_8="pregunta_c_8";
        public static String COLUMNA_pregunta_c_8a="pregunta_c_8a";
        public static String COLUMNA_pregunta_c_8b="pregunta_c_8b";
        public static String COLUMNA_pregunta_c_8c="pregunta_c_8c";
        public static String COLUMNA_pregunta_c_8cm="pregunta_c_8cm";
        public static String COLUMNA_pregunta_c_8cl="pregunta_c_8cl";
        public static String COLUMNA_pregunta_c_8d="pregunta_c_8d";
        public static String COLUMNA_pregunta_c_8e="pregunta_c_8e";
        public static String COLUMNA_pregunta_c_8f="pregunta_c_8f";
        public static String COLUMNA_pregunta_c_9="pregunta_c_9";
        public static String COLUMNA_pregunta_c_10="pregunta_c_10";
        public static String COLUMNA_pregunta_c_11="pregunta_c_11";
        public static String COLUMNA_pregunta_c_12="pregunta_c_12";
        public static String COLUMNA_pregunta_c_12a="pregunta_c_12a";
        public static String COLUMNA_pregunta_c_12b="pregunta_c_12b";
        public static String COLUMNA_pregunta_c_12c="pregunta_c_12c";
        public static String COLUMNA_pregunta_c_12d="pregunta_c_12d";
        public static String COLUMNA_pregunta_c_12e="pregunta_c_12e";
        public static String COLUMNA_pregunta_c_13="pregunta_c_13";
        public static String COLUMNA_pregunta_c_14="pregunta_c_14";
        public static String COLUMNA_pregunta_c_15="pregunta_c_15";
        public static String COLUMNA_pregunta_c_15a="pregunta_c_15a";
        public static String COLUMNA_pregunta_c_15b="pregunta_c_15b";
        public static String COLUMNA_pregunta_c_15c="pregunta_c_15c";
        public static String COLUMNA_pregunta_c_15d="pregunta_c_15d";
        public static String COLUMNA_pregunta_c_15e="pregunta_c_15e";
        public static String COLUMNA_pregunta_c_15f="pregunta_c_15f";
        public static String COLUMNA_pregunta_c_15g="pregunta_c_15g";
        public static String COLUMNA_pregunta_c_15h="pregunta_c_15h";
        public static String COLUMNA_pregunta_c_15i="pregunta_c_15i";
        public static String COLUMNA_pregunta_c_15j="pregunta_c_15j";
        public static String COLUMNA_pregunta_c_15k="pregunta_c_15k";
        public static String COLUMNA_pregunta_c_16a="pregunta_c_16a";
        public static String COLUMNA_pregunta_c_16b="pregunta_c_16b";
        public static String COLUMNA_pregunta_c_16c="pregunta_c_16c";
        public static String COLUMNA_pregunta_c_16d="pregunta_c_16d";
        public static String COLUMNA_pregunta_c_17="pregunta_c_17";
        public static String COLUMNA_pregunta_c_17a="pregunta_c_17a";
        public static String COLUMNA_pregunta_c_18s="pregunta_c_18s";
        public static String COLUMNA_pregunta_c_18as="pregunta_c_18as";
        public static String COLUMNA_pregunta_c_18bs="pregunta_c_18bs";
        public static String COLUMNA_pregunta_c_18cs="pregunta_c_18cs";
        public static String COLUMNA_pregunta_c_18ds="pregunta_c_18ds";
        public static String COLUMNA_pregunta_c_18es="pregunta_c_18es";
        public static String COLUMNA_pregunta_c_17b="pregunta_c_17b";
        public static String COLUMNA_pregunta_c_18m="pregunta_c_18m";
        public static String COLUMNA_pregunta_c_18am="pregunta_c_18am";
        public static String COLUMNA_pregunta_c_18bm="pregunta_c_18bm";
        public static String COLUMNA_pregunta_c_18cm="pregunta_c_18cm";
        public static String COLUMNA_pregunta_c_18dm="pregunta_c_18dm";
        public static String COLUMNA_pregunta_c_18em="pregunta_c_18em";
        public static String COLUMNA_pregunta_c_17c="pregunta_c_17c";
        public static String COLUMNA_pregunta_c_18t="pregunta_c_18t";
        public static String COLUMNA_pregunta_c_18at="pregunta_c_18at";
        public static String COLUMNA_pregunta_c_18bt="pregunta_c_18bt";
        public static String COLUMNA_pregunta_c_18ct="pregunta_c_18ct";
        public static String COLUMNA_pregunta_c_18dt="pregunta_c_18dt";
        public static String COLUMNA_pregunta_c_18et="pregunta_c_18et";
        public static String COLUMNA_pregunta_c_17d="pregunta_c_17d";
        public static String COLUMNA_pregunta_c_18ca="pregunta_c_18ca";
        public static String COLUMNA_pregunta_c_18aca="pregunta_c_18aca";
        public static String COLUMNA_pregunta_c_18bca="pregunta_c_18bca";
        public static String COLUMNA_pregunta_c_18cca="pregunta_c_18cca";
        public static String COLUMNA_pregunta_c_18dca="pregunta_c_18dca";
        public static String COLUMNA_pregunta_c_18eca="pregunta_c_18eca";
        public static String COLUMNA_pregunta_c_17e="pregunta_c_17e";
        public static String COLUMNA_pregunta_c_18cc="pregunta_c_18cc";
        public static String COLUMNA_pregunta_c_18acc="pregunta_c_18acc";
        public static String COLUMNA_pregunta_c_18bcc="pregunta_c_18bcc";
        public static String COLUMNA_pregunta_c_18ccc="pregunta_c_18ccc";
        public static String COLUMNA_pregunta_c_18dcc="pregunta_c_18dcc";
        public static String COLUMNA_pregunta_c_18ecc="pregunta_c_18ecc";
        public static String COLUMNA_pregunta_c_17f="pregunta_c_17f";

        public static String COLUMNA_pregunta_c_18="pregunta_c_18";
        public static String COLUMNA_pregunta_c_18a="pregunta_c_18a";
        public static String COLUMNA_pregunta_c_18b="pregunta_c_18b";
        public static String COLUMNA_pregunta_c_18c="pregunta_c_18c";
        public static String COLUMNA_pregunta_c_18d="pregunta_c_18d";
        public static String COLUMNA_pregunta_c_18e="pregunta_c_18e";
        public static String COLUMNA_pregunta_c_19="pregunta_c_19";
        public static String COLUMNA_pregunta_c_20="pregunta_c_20";
        public static String COLUMNA_pregunta_c_21="pregunta_c_21";
        public static String COLUMNA_pregunta_c_22="pregunta_c_22";
        public static String COLUMNA_pregunta_c_23="pregunta_c_23";


        public static String COLUMNA_abandono="abandono";
        // FINALIZAN PREGUNTAS
        public static String COLUMNA_TIEMPO = "tiempo";
        public static String COLUMNA_TIPO_CAPTURA = "tipo_captura";
        public static String COLUMNA_enviado = "enviado";
    }


    private static final String DATABASE_Adicionales = "create table "
            + TablaAdicionales.TABLA_Adicionales + "("
            + "id integer primary key autoincrement,"
            + TablaAdicionales.COLUMNA_id_consecutivo_contacto + " text, "
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

            + TablaAdicionales.COLUMNA_pregunta_c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_1 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_2 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_3 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_4 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_5 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6f +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6g +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6h +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6i +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6j +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_6k +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_7 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8cm +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8cl +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_8f +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_9 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_10 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_11 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_12e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_13 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_14 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15f +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15g +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15h +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15i +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15j +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_15k +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_16a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_16b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_16c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_16d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18s +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18as +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18bs +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18cs +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18ds +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18es +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18m +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18am +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18bm +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18cm +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18dm +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18em +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18t +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18at +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18bt +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18ct +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18dt +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18et +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18ca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18aca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18bca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18cca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18dca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18eca +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18cc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18acc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18bcc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18ccc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18dcc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18ecc +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_17f +  " text, "

            + TablaAdicionales.COLUMNA_pregunta_c_18 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18a +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18b +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18c +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18d +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_18e +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_19 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_20 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_21 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_22 +  " text, "
            + TablaAdicionales.COLUMNA_pregunta_c_23 +  " text, "


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
