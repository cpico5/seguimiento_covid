package mx.gob.cdmx.seguimientocovid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.seguimientocovid.model.DatoContent;
import mx.gob.cdmx.seguimientocovid.model.Usuario;
import mx.gob.cdmx.seguimientocovid.service.AppLog;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.seguimientocovid.Nombre.USUARIO;
import static mx.gob.cdmx.seguimientocovid.Nombre.adicionales;
import static mx.gob.cdmx.seguimientocovid.Nombre.customURL;
import static mx.gob.cdmx.seguimientocovid.Nombre.encuesta;

public class MainActivityPantalla2 extends Activity {

    private static final String LOG_TAG = "Grabadora";
    private static final String TAG = "Pantalla1";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    TelephonyManager t_manager;
    PhoneStateListener p_listener;
    boolean llamada = false;



    private View mProgressView;
    private Usuario usuario;

    final Context context = this;

    private ArrayList<CheckBox> mChecks;
    private ArrayList<CheckBox> mSelectedChecks;

    private ArrayList<CheckBox> mChecks2;
    private ArrayList<CheckBox> mSelectedChecks2;

    public MediaRecorder recorder = new MediaRecorder();
    private String audio;
    private Handler handler;
    public String honestidad;

    public StringBuilder builder0;


    private Button btnGuardar;
    private Button btnAbandono;
    private Button btnRechazo;
    private Button btnAbrir;
    private Button btnSalir;
    public Button uploadButton, emailButton;

    private Button btnPaterno,btnMaterno,btnNombres,btnTelefonoSospecha,btnCalle,btnExterior,btnInterior,btnManzana,btnLote,btnTelefonoContacto;

    private Button btnPaterno2,btnMaterno2,btnNombres2,btnTelefonoSospecha2,btnCalle2,btnExterior2,btnInterior2,btnManzana2,btnLote2,btnTelefonoContacto2;

    private TextView textSpeetch,textSpeetch2,textPreguntaEntrada,textPreguntaSalida;

    double latitude;
    double longitude;

    Random random = new Random();
    public int rand;

    public RadioGroup rdPreguntaOcupacion, rdPreguntaFocos,   rdPreguntaCoche,rdPreguntaCuantosCoches,rdPreguntaCuartos, rdPreguntaCuartosDormir,
    rdPreguntaBanos,rdPreguntaTrabajaron,rdPreguntaInternet,rdPreguntaRegadera,
    rdPreguntaEstufa, rdPreguntaEdad, rdPreguntaGenero, rdPreguntaTipoVivienda, rdPreguntaTipoPiso;

    public RadioGroup rdPreguntaUrgente;



    public RadioGroup  rdPreguntaAporta, rdPreguntaHijos, rdPreguntaAbandono;


    private static final int READ_BLOCK_SIZE = 100000;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();

    UsuariosSQLiteHelper usdbh;
    UsuariosSQLiteHelper Udb;
    List<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    private SQLiteDatabase db;

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;

    private Spinner spinnerDelegaciones;
    private Spinner spinnerc_12e;
    private Spinner spinnerc_18e;
    private Spinner spinnerMeses;
    private Spinner spinnerSemana;
    private Spinner spinnerCalifica;

    private Spinner spinner0;

    private Spinner spinner9;
    Timer timer;

    public EditText txtSeccion;
    public EditText editObservaciones;


    public String opEstadoCivil="sin datos";
    public String opHijos="sin datos";
    public String opJefe="sin datos";
    public String opAporta="sin datos";
    public String opEstudio="sin datos";
    public String opAbandono="sin datos";
    public String opOcupacion="sin datos";
    public String opCoche="sin datos";
    public String opFocos="sin datos";
    public String opCuantosCoches="sin datos";

    public String opCuartos="sin datos";
    public String opCuartosDormir="sin datos";
    public String opBanos="sin datos";
    public String opRegadera="sin datos";
    public String opInternet="sin datos";
    public String opTrabajaron="sin datos";
    public String opEstufa="sin datos";
    public String opEdad="sin datos";
    public String opGenero="sin datos";
    public String opUrgente="sin datos";
    public String opTipoVivienda="sin datos";
    public String opTipoPiso="sin datos";

    public String opMedio = "sin datos";
    public String opSemana = "sin datos";
    public String opFinSemana = "sin datos";


    public String opc="sin datos";	public RadioGroup rdPreguntac;	public EditText editPreguntac;	public String capturac;	LinearLayout layc;
    public String opc_1="sin datos";	public RadioGroup rdPreguntac_1;	public EditText editPreguntac_1;	public String capturac_1;	LinearLayout layc_1;
    public String opc_2="sin datos";	public RadioGroup rdPreguntac_2;	public EditText editPreguntac_2;	public String capturac_2;	LinearLayout layc_2;
    public String opc_3="sin datos";	public RadioGroup rdPreguntac_3;	public EditText editPreguntac_3;	public String capturac_3;	LinearLayout layc_3;
    public String opc_4="sin datos";	public RadioGroup rdPreguntac_4;	public EditText editPreguntac_4;	public String capturac_4;	LinearLayout layc_4;
    public String opc_5="sin datos";	public RadioGroup rdPreguntac_5;	public EditText editPreguntac_5;	public String capturac_5;	LinearLayout layc_5;
    public String opc_6="sin datos";	public RadioGroup rdPreguntac_6;	public EditText editPreguntac_6;	public String capturac_6;	LinearLayout layc_6;
    public String opc_6a="sin datos";	public RadioGroup rdPreguntac_6a;	public EditText editPreguntac_6a;	public String capturac_6a;	LinearLayout layc_6a;
    public String opc_6b="sin datos";	public RadioGroup rdPreguntac_6b;	public EditText editPreguntac_6b;	public String capturac_6b;	LinearLayout layc_6b;
    public String opc_6c="sin datos";	public RadioGroup rdPreguntac_6c;	public EditText editPreguntac_6c;	public String capturac_6c;	LinearLayout layc_6c;
    public String opc_6d="sin datos";	public RadioGroup rdPreguntac_6d;	public EditText editPreguntac_6d;	public String capturac_6d;	LinearLayout layc_6d;
    public String opc_6e="sin datos";	public RadioGroup rdPreguntac_6e;	public EditText editPreguntac_6e;	public String capturac_6e;	LinearLayout layc_6e;
    public String opc_6f="sin datos";	public RadioGroup rdPreguntac_6f;	public EditText editPreguntac_6f;	public String capturac_6f;	LinearLayout layc_6f;
    public String opc_6g="sin datos";	public RadioGroup rdPreguntac_6g;	public EditText editPreguntac_6g;	public String capturac_6g;	LinearLayout layc_6g;
    public String opc_6h="sin datos";	public RadioGroup rdPreguntac_6h;	public EditText editPreguntac_6h;	public String capturac_6h;	LinearLayout layc_6h;
    public String opc_6i="sin datos";	public RadioGroup rdPreguntac_6i;	public EditText editPreguntac_6i;	public String capturac_6i;	LinearLayout layc_6i;
    public String opc_6j="sin datos";	public RadioGroup rdPreguntac_6j;	public EditText editPreguntac_6j;	public String capturac_6j;	LinearLayout layc_6j;
    public String opc_6k="sin datos";	public RadioGroup rdPreguntac_6k;	public EditText editPreguntac_6k;	public String capturac_6k;	LinearLayout layc_6k;
    public String opc_7="sin datos";	public RadioGroup rdPreguntac_7;	public EditText editPreguntac_7;	public String capturac_7;	LinearLayout layc_7;
    public String opc_8="sin datos";	public RadioGroup rdPreguntac_8;	public EditText editPreguntac_8;	public String capturac_8;	LinearLayout layc_8;
    public String opc_8a="sin datos";	public RadioGroup rdPreguntac_8a;	public EditText editPreguntac_8a;	public String capturac_8a;	LinearLayout layc_8a;
    public String opc_8b="sin datos";	public RadioGroup rdPreguntac_8b;	public EditText editPreguntac_8b;	public String capturac_8b;	LinearLayout layc_8b;
    public String opc_8c="sin datos";	public RadioGroup rdPreguntac_8c;	public EditText editPreguntac_8c;	public String capturac_8c;	LinearLayout layc_8c;
    public String opc_8d="sin datos";	public RadioGroup rdPreguntac_8d;	public EditText editPreguntac_8d;	public String capturac_8d;	LinearLayout layc_8d;
    public String opc_8e="sin datos";	public RadioGroup rdPreguntac_8e;	public EditText editPreguntac_8e;	public String capturac_8e;	LinearLayout layc_8e;
    public String opc_8f="sin datos";	public RadioGroup rdPreguntac_8f;	public EditText editPreguntac_8f;	public String capturac_8f;	LinearLayout layc_8f;
    public String opc_9="sin datos";	public RadioGroup rdPreguntac_9;	public EditText editPreguntac_9;	public String capturac_9;	LinearLayout layc_9;
    public String opc_10="sin datos";	public RadioGroup rdPreguntac_10;	public EditText editPreguntac_10;	public String capturac_10;	LinearLayout layc_10;
    public String opc_11="sin datos";	public RadioGroup rdPreguntac_11;	public EditText editPreguntac_11;	public String capturac_11;	LinearLayout layc_11;
    public String opc_12="sin datos";	public RadioGroup rdPreguntac_12;	public EditText editPreguntac_12;	public String capturac_12;	LinearLayout layc_12;
    public String opc_12a="sin datos";	public RadioGroup rdPreguntac_12a;	public EditText editPreguntac_12a;	public String capturac_12a;	LinearLayout layc_12a;
    public String opc_12b="sin datos";	public RadioGroup rdPreguntac_12b;	public EditText editPreguntac_12b;	public String capturac_12b;	LinearLayout layc_12b;
    public String opc_12c="sin datos";	public RadioGroup rdPreguntac_12c;	public EditText editPreguntac_12c;	public String capturac_12c;	LinearLayout layc_12c;
    public String opc_12d="sin datos";	public RadioGroup rdPreguntac_12d;	public EditText editPreguntac_12d;	public String capturac_12d;	LinearLayout layc_12d;
    public String opc_12e="sin datos";	public RadioGroup rdPreguntac_12e;	public EditText editPreguntac_12e;	public String capturac_12e;	LinearLayout layc_12e;
    public String opc_13="sin datos";	public RadioGroup rdPreguntac_13;	public EditText editPreguntac_13;	public String capturac_13;	LinearLayout layc_13;
    public String opc_14="sin datos";	public RadioGroup rdPreguntac_14;	public EditText editPreguntac_14;	public String capturac_14;	LinearLayout layc_14;
    public String opc_15="sin datos";	public RadioGroup rdPreguntac_15;	public EditText editPreguntac_15;	public String capturac_15;	LinearLayout layc_15;
    public String opc_15a="sin datos";	public RadioGroup rdPreguntac_15a;	public EditText editPreguntac_15a;	public String capturac_15a;	LinearLayout layc_15a;
    public String opc_15b="sin datos";	public RadioGroup rdPreguntac_15b;	public EditText editPreguntac_15b;	public String capturac_15b;	LinearLayout layc_15b;
    public String opc_15c="sin datos";	public RadioGroup rdPreguntac_15c;	public EditText editPreguntac_15c;	public String capturac_15c;	LinearLayout layc_15c;
    public String opc_15d="sin datos";	public RadioGroup rdPreguntac_15d;	public EditText editPreguntac_15d;	public String capturac_15d;	LinearLayout layc_15d;
    public String opc_15e="sin datos";	public RadioGroup rdPreguntac_15e;	public EditText editPreguntac_15e;	public String capturac_15e;	LinearLayout layc_15e;
    public String opc_15f="sin datos";	public RadioGroup rdPreguntac_15f;	public EditText editPreguntac_15f;	public String capturac_15f;	LinearLayout layc_15f;
    public String opc_15g="sin datos";	public RadioGroup rdPreguntac_15g;	public EditText editPreguntac_15g;	public String capturac_15g;	LinearLayout layc_15g;
    public String opc_15h="sin datos";	public RadioGroup rdPreguntac_15h;	public EditText editPreguntac_15h;	public String capturac_15h;	LinearLayout layc_15h;
    public String opc_15i="sin datos";	public RadioGroup rdPreguntac_15i;	public EditText editPreguntac_15i;	public String capturac_15i;	LinearLayout layc_15i;
    public String opc_15j="sin datos";	public RadioGroup rdPreguntac_15j;	public EditText editPreguntac_15j;	public String capturac_15j;	LinearLayout layc_15j;
    public String opc_15k="sin datos";	public RadioGroup rdPreguntac_15k;	public EditText editPreguntac_15k;	public String capturac_15k;	LinearLayout layc_15k;
    public String opc_16="sin datos";	public RadioGroup rdPreguntac_16;
    public EditText editPreguntac_16a;
    public EditText editPreguntac_16b;
    public EditText editPreguntac_16c;
    public EditText editPreguntac_16d;
    public String capturac_16;	LinearLayout layc_16;
    public String opc_17="sin datos";	public RadioGroup rdPreguntac_17;	public EditText editPreguntac_17;	public String capturac_17;	LinearLayout layc_17;
    public String opc_17a="sin datos";	public RadioGroup rdPreguntac_17a;	public EditText editPreguntac_17a;	public String capturac_17a;	LinearLayout layc_17a;
    public String opc_17b="sin datos";	public RadioGroup rdPreguntac_17b;	public EditText editPreguntac_17b;	public String capturac_17b;	LinearLayout layc_17b;
    public String opc_17c="sin datos";	public RadioGroup rdPreguntac_17c;	public EditText editPreguntac_17c;	public String capturac_17c;	LinearLayout layc_17c;
    public String opc_17d="sin datos";	public RadioGroup rdPreguntac_17d;	public EditText editPreguntac_17d;	public String capturac_17d;	LinearLayout layc_17d;
    public String opc_17e="sin datos";	public RadioGroup rdPreguntac_17e;	public EditText editPreguntac_17e;	public String capturac_17e;	LinearLayout layc_17e;
    public String opc_17f="sin datos";	public RadioGroup rdPreguntac_17f;	public EditText editPreguntac_17f;	public String capturac_17f;	LinearLayout layc_17f;
    public String opc_18="sin datos";	public RadioGroup rdPreguntac_18;	public EditText editPreguntac_18;	public String capturac_18;	LinearLayout layc_18;
    public String opc_18a="sin datos";	public RadioGroup rdPreguntac_18a;	public EditText editPreguntac_18a;	public String capturac_18a;	LinearLayout layc_18a;
    public String opc_18b="sin datos";	public RadioGroup rdPreguntac_18b;	public EditText editPreguntac_18b;	public String capturac_18b;	LinearLayout layc_18b;
    public String opc_18c="sin datos";	public RadioGroup rdPreguntac_18c;	public EditText editPreguntac_18c;	public String capturac_18c;	LinearLayout layc_18c;
    public String opc_18d="sin datos";	public RadioGroup rdPreguntac_18d;	public EditText editPreguntac_18d;	public String capturac_18d;	LinearLayout layc_18d;
    public String opc_18e="sin datos";	public RadioGroup rdPreguntac_18e;	public EditText editPreguntac_18e;	public String capturac_18e;	LinearLayout layc_18e;
    public String opc_19="sin datos";	public RadioGroup rdPreguntac_19;	public EditText editPreguntac_19;	public String capturac_19;	LinearLayout layc_19;
    public String opc_20="sin datos";	public RadioGroup rdPreguntac_20;	public EditText editPreguntac_20;	public String capturac_20;	LinearLayout layc_20;
    public String opc_21="sin datos";	public RadioGroup rdPreguntac_21;	public EditText editPreguntac_21;	public String capturac_21;	LinearLayout layc_21;
    public String opc_22="sin datos";	public RadioGroup rdPreguntac_22;	public EditText editPreguntac_22;	public String capturac_22;	LinearLayout layc_22;
    public String opc_23="sin datos";	public RadioGroup rdPreguntac_23;	public EditText editPreguntac_23;	public String capturac_23;	LinearLayout layc_23;




    LinearLayout laySocioE;
    LinearLayout layEst;
    LinearLayout layAporta;
    LinearLayout layOcupacion;
    LinearLayout layCuartos;
    LinearLayout layCuartosDormir;
    LinearLayout layFocos;
    LinearLayout layBanos;
    LinearLayout layRegadera;
    LinearLayout layEstufa;
    LinearLayout layEdad;
    LinearLayout layTipoPiso;
    LinearLayout layTipoVivienda;
    LinearLayout layGenero;


    public Resources res;

    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;


    LinearLayout layCuantosCoches;


    public RadioButton radio1_07;
    public RadioButton radio_abandono1;
    public RadioButton radio_abandono2;
    public RadioButton radio_abandono3;
    public RadioButton radio_abandono4;

    public RadioButton radio_urgente1;
    public RadioButton radio_urgente2;


    public String capturaOcupacion, capturaCoche, capturaE3, capturaE4, capturaCuantosCoches, capturaTrabajo, capturaE7,
    capturaFocos, capturaCuartos, capturaCuartosDormir, capturaBanos, capturaInternet, capturaTrabajaron;
    public String capturaRegadera, capturaEstufa, capturaEdad, capturaGenero, capturaTipoVivienda, capturaTipoPiso,capturaObservaciones,
    capturaCalle, capturaExterior, capturaInterior;
    public String capturaTelefono, capturaAporta;
    public String capturaPaterno, capturaMaterno,capturaNombres,getCapturaTelefonoSospechoso;


    public String maximo = "";
    int elMaximo;
    String tipoEncuesta;

    public String pasoUsuario;

    public String Secc;

    public EditText editUsuario;

    public String str;
    public String variablePrueba;
    public String encuestador;
    public String tablet;
    public String hora;

    public String quien;

    String upLoadServerUri = null;
    ProgressDialog dialog = null;
    final String path = "/mnt/sdcard/Mis_archivos/";

    int serverResponseCode = 0;

    public String tiempo;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df6 = new SimpleDateFormat("MM");
    String formattedDate6 = df6.format(c.getTime());

    SimpleDateFormat df7 = new SimpleDateFormat("dd");
    String formattedDate7 = df7.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDate5 = df5.format(c.getTime());

    Calendar t1 = Calendar.getInstance();
    long milis1 = t1.getTimeInMillis();

    public static String getHostName(String defValue) {
        try {
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);
            return getString.invoke(null, "net.hostname").toString();
        } catch (Exception ex) {
            return defValue;
        }
    }

    static String ID = getHostName(null);
    static String prefix = ID;


    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        String Nombre = datos.getString("Nombre");
        return Nombre;
    }

    public String cachaConsecutivoDiario() {
        Bundle datos = this.getIntent().getExtras();
        String consecutivo_diario = datos.getString("consecutivo_diario");
        return consecutivo_diario;
    }

    public String cachaEquipo() {
        Bundle datos = this.getIntent().getExtras();
        String equipo = datos.getString("equipo");
        return equipo;
    }

    public String cachaLatitud() {
        Bundle datos = this.getIntent().getExtras();
        String latitud = datos.getString("latitud");
        return latitud;
    }

    public String cachaLongitud() {
        Bundle datos = this.getIntent().getExtras();
        String longitud = datos.getString("longitud");
        return longitud;
    }


    public String cachaUsuario() {
        Bundle datos = this.getIntent().getExtras();
        String usuario = datos.getString("usuario");
        return usuario;
    }

    public String cachaSeccion() {
        Bundle datos = this.getIntent().getExtras();
        String seccion = datos.getString("seccion");
        return seccion;
    }

    public String cachaAlcaldia() {
        Bundle datos = this.getIntent().getExtras();
        String alcaldia = datos.getString("alcaldia");
        return alcaldia;
    }


    public String cachaTelefono() {
        Bundle datos = this.getIntent().getExtras();
        String telefono = datos.getString("telefono");
        return telefono;
    }

    public String cachaDelegacion() {
        Bundle datos = this.getIntent().getExtras();
        String delegacion = datos.getString("delegacion");
        return delegacion;
    }

    @SuppressLint("MissingPermission")
    public String sacaChip() {
        String szImei;
TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
if (szImei == null) {
szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
}
return szImei;
}

@SuppressLint("MissingPermission")
public String sacaImei() {
    String szImei;
TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
if (szImei == null) {
szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
}
return szImei;
}

public String hora() {

    if (formattedDate5.matches("")) {
        formattedDate5 = df5.format(c.getTime());
    }
    return formattedDate5;
}

    public void dialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Agregar persona adicional").setTitle("IMPORTANTE").setCancelable(false)
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        detenerGrabacion();

                        Intent i = new Intent(MainActivityPantalla2.this, Entrada.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        System.exit(0); // metodo que se debe implementar
                    }
                }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                detenerGrabacion();


                // NORMAL
                Nombre nom = new Nombre();
                String nombreE = nom.nombreEncuesta();

                GPSTracker gps = new GPSTracker(MainActivityPantalla2.this);
//        latitude = gps.getLatitude();
//        longitude = gps.getLongitude();

                latitude = Double.valueOf(sacaLatitud());
                longitude = Double.valueOf(sacaLongitud());

                if (latitude == 0.0) {
                    if (sacaLatitud() != null) {
                        latitude = Double.valueOf(sacaLatitud());
                    } else {
                        latitude = 0.0;
                    }
                }

                if (longitude == 0.0) {
                    if (sacaLongitud() != null) {
                        longitude = Double.valueOf(sacaLongitud());
                    } else {
                        longitude = 0.0;
                    }
                }

                String strSecc = txtSeccion.getText().toString();
                String strLatitud = String.valueOf(latitude);
                String strLongitud = String.valueOf(longitude);

                Intent i = new Intent(MainActivityPantalla2.this, MainActivityPantalla2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Nombre", cachaNombre());
                i.putExtra("consecutivo_diario", elMaximo);
                i.putExtra("equipo", cachaEquipo().toUpperCase());
                i.putExtra("usuario", cachaNombre().toUpperCase());
                i.putExtra("nombre_encuesta", nombreE.toUpperCase());
                i.putExtra("fecha", formattedDate1);
                i.putExtra("hora", formattedDate5);
                i.putExtra("imei", sacaImei());
                i.putExtra("seccion", cachaSeccion());
                i.putExtra("latitud",cachaLatitud() );
                i.putExtra("longitud", cachaLongitud());
                i.putExtra("alcaldia", cachaAlcaldia());
                i.putExtra(USUARIO, usuario);
                startActivity(i);
                System.exit(0); // metodo que se debe implementar
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

public void dialogoParoAudio() {
    timer.cancel();
    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    MainActivityPantalla2.this.runOnUiThread(new Runnable() {
        public void run() {
            builder.setMessage("¿Se detendrá la grabación y \n se reiniciará la encuesta..?")
            .setTitle("AVISO...!!!").setCancelable(false)
            .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    detenerGrabacion();

                    Intent i = new Intent(MainActivityPantalla2.this, Entrada.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
System.exit(0); // metodo que se debe
// implementar
}
});
            AlertDialog alert = builder.create();
            alert.show();

        }
    });

}

public void dialogoCierraEncuesta() {
    timer.cancel();

    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
    MainActivityPantalla2.this.runOnUiThread(new Runnable() {
        public void run() {
            builder.setMessage("Excediste el tiempo máximo para realizar la encuesta \n"
                + "¡¡¡ Se detendrá la grabación y se reiniciará la Aplicación..!!!").setTitle("AVISO...!!!")
            .setCancelable(false).setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    detenerGrabacion();

                    Intent i = new Intent(MainActivityPantalla2.this, Entrada.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
System.exit(0); // metodo que se debe
// implementar
}
});

            AlertDialog alert = builder.create();

            alert.show();
        }
    });

}


// EVENTO AL PULSAR EL BOTON ATRAS

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás
            Toast.makeText(getApplicationContext(), "No puedo ir hacia atrás!!\nEstoy grabando...", Toast.LENGTH_SHORT)
                    .show();

// dialogoAbandono();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public String nombreAudio() {

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String date = formattedDate3.toString();
        String var2 = ".mp3";

        int consecutivo = Integer.parseInt(sacaConsecutivo().toString()) + 1;
        String elConsecutivo = String.valueOf(consecutivo);
        int Cons = elConsecutivo.length();

        if (Cons == 1) {
            elConsecutivo = "00" + elConsecutivo;
        } else if (Cons == 2) {
            elConsecutivo = "0" + elConsecutivo;
        } else {
            elConsecutivo = elConsecutivo;
        }

        String usuario;

        int tamanoUsuario = cachaNombre().length();

        if (tamanoUsuario == 1) {
            usuario = "00" + cachaNombre();
        } else if (tamanoUsuario == 2) {
            usuario = "0" + cachaNombre();
        } else {
            usuario = cachaNombre();
        }

// nombreEncuesta_fecha_chip_usuario_consecutivo
//    final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo + "_" + cachaTelefono() + ".mp3";
        final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo +"_adicional"+ ".mp3";
// final String nombreAudio =
// nombreEncuesta+"_"+date+"_"+prefix+"_"+elConsecutivo+".mp3";
        return nombreAudio;
    }

    public String elTiempo() {
// Para la diferenci entre tiempos
        Calendar t2 = Calendar.getInstance();
        long milis2 = t2.getTimeInMillis();
//		long diff = milis2 - t1();
        long diff = milis2 - milis1;

        long diffSegundos = diff / 1000;

        long diffMinutos = diffSegundos / 60;

        long residuo = diffSegundos % 60;

        System.out.println(diffSegundos);
        System.out.println(diffMinutos);
        System.out.println(residuo);

        tiempo = diffMinutos + ":" + residuo;

        return tiempo;

    }



@Override
protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
    super.onCreate(savedInstanceState);
setContentView(R.layout.activity_pantalla2); // COMENTAR ESTA CUANDO ES ALEATORIO

Intent startingIntent = getIntent();
if (startingIntent == null) {
    Log.e(TAG, "No Intent?  We're not supposed to be here...");
    finish();
    return;
}

if (savedInstanceState != null) {
    usuario = (Usuario) savedInstanceState.getSerializable(USUARIO);
} else {
    usuario = (Usuario) startingIntent.getSerializableExtra(USUARIO);
}

// Carga las pantallas aleatoriamente
random = new Random();
//


// Crea Log cuando falla la app
Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivityPantalla2.this,this));


cachaNombre(); // llamado al metodo para obtener el numero del
// encuestador

try {

    handler = new Handler();

    new Thread(new Runnable() {
        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "Iniciando Grabación");
                    grabar();
                }

            });

        }
    }).start();

} catch (Exception e) {

}

///////////// EL TIMER PARA PARAR LA ENCUESTA /////////////////

timer = new Timer();
//		timer.schedule(new CierraEncuesta(), 1800000); // 8 Minutos 480000

////////////////////////
mProgressView = findViewById(R.id.login_progressMain);

    txtSeccion = (EditText) findViewById(R.id.txtSeccion);

    txtSeccion.setText(cachaSeccion());
    txtSeccion.setEnabled(false);

    res = getResources();

    rdPreguntaAbandono = (RadioGroup)findViewById(R.id.rdPreguntaAbandono);

    rdPreguntac = (RadioGroup)findViewById(R.id.rdPreguntaC);	capturac =res.getString(R.string.PREGUNTAC);	layc = (LinearLayout) findViewById(R.id.layC);
    rdPreguntac_1 = (RadioGroup)findViewById(R.id.rdPreguntac_1);	capturac_1 =res.getString(R.string.PREGUNTAc_1);	layc_1 = (LinearLayout) findViewById(R.id.layc_1);
    rdPreguntac_2 = (RadioGroup)findViewById(R.id.rdPreguntac_2);	capturac_2 =res.getString(R.string.PREGUNTAc_2);	layc_2 = (LinearLayout) findViewById(R.id.layc_2);
    rdPreguntac_3 = (RadioGroup)findViewById(R.id.rdPreguntac_3);	capturac_3 =res.getString(R.string.PREGUNTAc_3);	layc_3 = (LinearLayout) findViewById(R.id.layc_3);
    rdPreguntac_4 = (RadioGroup)findViewById(R.id.rdPreguntac_4);	capturac_4 =res.getString(R.string.PREGUNTAc_4);	layc_4 = (LinearLayout) findViewById(R.id.layc_4);
    rdPreguntac_5 = (RadioGroup)findViewById(R.id.rdPreguntac_5);	capturac_5 =res.getString(R.string.PREGUNTAc_5);	layc_5 = (LinearLayout) findViewById(R.id.layc_5);
    rdPreguntac_6 = (RadioGroup)findViewById(R.id.rdPreguntac_6);	capturac_6 =res.getString(R.string.PREGUNTAc_6);	layc_6 = (LinearLayout) findViewById(R.id.layc_6);
    rdPreguntac_6a = (RadioGroup)findViewById(R.id.rdPreguntac_6a);	capturac_6a =res.getString(R.string.PREGUNTAc_6a);	layc_6a = (LinearLayout) findViewById(R.id.layc_6a);
    rdPreguntac_6b = (RadioGroup)findViewById(R.id.rdPreguntac_6b);	capturac_6b =res.getString(R.string.PREGUNTAc_6b);	layc_6b = (LinearLayout) findViewById(R.id.layc_6b);
    rdPreguntac_6c = (RadioGroup)findViewById(R.id.rdPreguntac_6c);	capturac_6c =res.getString(R.string.PREGUNTAc_6c);	layc_6c = (LinearLayout) findViewById(R.id.layc_6c);
    rdPreguntac_6d = (RadioGroup)findViewById(R.id.rdPreguntac_6d);	capturac_6d =res.getString(R.string.PREGUNTAc_6d);	layc_6d = (LinearLayout) findViewById(R.id.layc_6d);
    rdPreguntac_6e = (RadioGroup)findViewById(R.id.rdPreguntac_6e);	capturac_6e =res.getString(R.string.PREGUNTAc_6e);	layc_6e = (LinearLayout) findViewById(R.id.layc_6e);
    rdPreguntac_6f = (RadioGroup)findViewById(R.id.rdPreguntac_6f);	capturac_6f =res.getString(R.string.PREGUNTAc_6f);	layc_6f = (LinearLayout) findViewById(R.id.layc_6f);
    rdPreguntac_6g = (RadioGroup)findViewById(R.id.rdPreguntac_6g);	capturac_6g =res.getString(R.string.PREGUNTAc_6g);	layc_6g = (LinearLayout) findViewById(R.id.layc_6g);
    rdPreguntac_6h = (RadioGroup)findViewById(R.id.rdPreguntac_6h);	capturac_6h =res.getString(R.string.PREGUNTAc_6h);	layc_6h = (LinearLayout) findViewById(R.id.layc_6h);
    rdPreguntac_6i = (RadioGroup)findViewById(R.id.rdPreguntac_6i);	capturac_6i =res.getString(R.string.PREGUNTAc_6i);	layc_6i = (LinearLayout) findViewById(R.id.layc_6i);
    rdPreguntac_6j = (RadioGroup)findViewById(R.id.rdPreguntac_6j);	capturac_6j =res.getString(R.string.PREGUNTAc_6j);	layc_6j = (LinearLayout) findViewById(R.id.layc_6j);
    rdPreguntac_6k = (RadioGroup)findViewById(R.id.rdPreguntac_6k);	capturac_6k =res.getString(R.string.PREGUNTAc_6k);	layc_6k = (LinearLayout) findViewById(R.id.layc_6k);
    rdPreguntac_7 = (RadioGroup)findViewById(R.id.rdPreguntac_7);	capturac_7 =res.getString(R.string.PREGUNTAc_7);	layc_7 = (LinearLayout) findViewById(R.id.layc_7);
    rdPreguntac_8 = (RadioGroup)findViewById(R.id.rdPreguntac_8);	capturac_8 =res.getString(R.string.PREGUNTAc_8);	layc_8 = (LinearLayout) findViewById(R.id.layc_8);
    rdPreguntac_8a = (RadioGroup)findViewById(R.id.rdPreguntac_8a);	capturac_8a =res.getString(R.string.PREGUNTAc_8a);	layc_8a = (LinearLayout) findViewById(R.id.layc_8a);
    rdPreguntac_8b = (RadioGroup)findViewById(R.id.rdPreguntac_8b);	capturac_8b =res.getString(R.string.PREGUNTAc_8b);	layc_8b = (LinearLayout) findViewById(R.id.layc_8b);
    rdPreguntac_8c = (RadioGroup)findViewById(R.id.rdPreguntac_8c);	capturac_8c =res.getString(R.string.PREGUNTAc_8c);	layc_8c = (LinearLayout) findViewById(R.id.layc_8c);
    rdPreguntac_8d = (RadioGroup)findViewById(R.id.rdPreguntac_8d);	capturac_8d =res.getString(R.string.PREGUNTAc_8d);	layc_8d = (LinearLayout) findViewById(R.id.layc_8d);
    rdPreguntac_8e = (RadioGroup)findViewById(R.id.rdPreguntac_8e);	capturac_8e =res.getString(R.string.PREGUNTAc_8e);	layc_8e = (LinearLayout) findViewById(R.id.layc_8e);
    rdPreguntac_8f = (RadioGroup)findViewById(R.id.rdPreguntac_8f);	capturac_8f =res.getString(R.string.PREGUNTAc_8f);	layc_8f = (LinearLayout) findViewById(R.id.layc_8f);
    rdPreguntac_9 = (RadioGroup)findViewById(R.id.rdPreguntac_9);	capturac_9 =res.getString(R.string.PREGUNTAc_9);	layc_9 = (LinearLayout) findViewById(R.id.layc_9);
    rdPreguntac_10 = (RadioGroup)findViewById(R.id.rdPreguntac_10);	capturac_10 =res.getString(R.string.PREGUNTAc_10);	layc_10 = (LinearLayout) findViewById(R.id.layc_10);
    rdPreguntac_11 = (RadioGroup)findViewById(R.id.rdPreguntac_11);	capturac_11 =res.getString(R.string.PREGUNTAc_11);	layc_11 = (LinearLayout) findViewById(R.id.layc_11);
    rdPreguntac_12 = (RadioGroup)findViewById(R.id.rdPreguntac_12);	capturac_12 =res.getString(R.string.PREGUNTAc_12);	layc_12 = (LinearLayout) findViewById(R.id.layc_12);
    rdPreguntac_12a = (RadioGroup)findViewById(R.id.rdPreguntac_12a);	capturac_12a =res.getString(R.string.PREGUNTAc_12a);	layc_12a = (LinearLayout) findViewById(R.id.layc_12a);
    rdPreguntac_12b = (RadioGroup)findViewById(R.id.rdPreguntac_12b);	capturac_12b =res.getString(R.string.PREGUNTAc_12b);	layc_12b = (LinearLayout) findViewById(R.id.layc_12b);
    rdPreguntac_12c = (RadioGroup)findViewById(R.id.rdPreguntac_12c);	capturac_12c =res.getString(R.string.PREGUNTAc_12c);	layc_12c = (LinearLayout) findViewById(R.id.layc_12c);
    rdPreguntac_12d = (RadioGroup)findViewById(R.id.rdPreguntac_12d);	capturac_12d =res.getString(R.string.PREGUNTAc_12d);	layc_12d = (LinearLayout) findViewById(R.id.layc_12d);
    rdPreguntac_12e = (RadioGroup)findViewById(R.id.rdPreguntac_12e);	capturac_12e =res.getString(R.string.PREGUNTAc_12e);	layc_12e = (LinearLayout) findViewById(R.id.layc_12e);
    rdPreguntac_13 = (RadioGroup)findViewById(R.id.rdPreguntac_13);	capturac_13 =res.getString(R.string.PREGUNTAc_13);	layc_13 = (LinearLayout) findViewById(R.id.layc_13);
    rdPreguntac_14 = (RadioGroup)findViewById(R.id.rdPreguntac_14);	capturac_14 =res.getString(R.string.PREGUNTAc_14);	layc_14 = (LinearLayout) findViewById(R.id.layc_14);
    rdPreguntac_15 = (RadioGroup)findViewById(R.id.rdPreguntac_15);	capturac_15 =res.getString(R.string.PREGUNTAc_15);	layc_15 = (LinearLayout) findViewById(R.id.layc_15);
    rdPreguntac_15a = (RadioGroup)findViewById(R.id.rdPreguntac_15a);	capturac_15a =res.getString(R.string.PREGUNTAc_15a);	layc_15a = (LinearLayout) findViewById(R.id.layc_15a);
    rdPreguntac_15b = (RadioGroup)findViewById(R.id.rdPreguntac_15b);	capturac_15b =res.getString(R.string.PREGUNTAc_15b);	layc_15b = (LinearLayout) findViewById(R.id.layc_15b);
    rdPreguntac_15c = (RadioGroup)findViewById(R.id.rdPreguntac_15c);	capturac_15c =res.getString(R.string.PREGUNTAc_15c);	layc_15c = (LinearLayout) findViewById(R.id.layc_15c);
    rdPreguntac_15d = (RadioGroup)findViewById(R.id.rdPreguntac_15d);	capturac_15d =res.getString(R.string.PREGUNTAc_15d);	layc_15d = (LinearLayout) findViewById(R.id.layc_15d);
    rdPreguntac_15e = (RadioGroup)findViewById(R.id.rdPreguntac_15e);	capturac_15e =res.getString(R.string.PREGUNTAc_15e);	layc_15e = (LinearLayout) findViewById(R.id.layc_15e);
    rdPreguntac_15f = (RadioGroup)findViewById(R.id.rdPreguntac_15f);	capturac_15f =res.getString(R.string.PREGUNTAc_15f);	layc_15f = (LinearLayout) findViewById(R.id.layc_15f);
    rdPreguntac_15g = (RadioGroup)findViewById(R.id.rdPreguntac_15g);	capturac_15g =res.getString(R.string.PREGUNTAc_15g);	layc_15g = (LinearLayout) findViewById(R.id.layc_15g);
    rdPreguntac_15h = (RadioGroup)findViewById(R.id.rdPreguntac_15h);	capturac_15h =res.getString(R.string.PREGUNTAc_15h);	layc_15h = (LinearLayout) findViewById(R.id.layc_15h);
    rdPreguntac_15i = (RadioGroup)findViewById(R.id.rdPreguntac_15i);	capturac_15i =res.getString(R.string.PREGUNTAc_15i);	layc_15i = (LinearLayout) findViewById(R.id.layc_15i);
    rdPreguntac_15j = (RadioGroup)findViewById(R.id.rdPreguntac_15j);	capturac_15j =res.getString(R.string.PREGUNTAc_15j);	layc_15j = (LinearLayout) findViewById(R.id.layc_15j);
    rdPreguntac_15k = (RadioGroup)findViewById(R.id.rdPreguntac_15k);	capturac_15k =res.getString(R.string.PREGUNTAc_15k);	layc_15k = (LinearLayout) findViewById(R.id.layc_15k);
    rdPreguntac_16 = (RadioGroup)findViewById(R.id.rdPreguntac_16);	capturac_16 =res.getString(R.string.PREGUNTAc_16);	layc_16 = (LinearLayout) findViewById(R.id.layc_16);
    rdPreguntac_17 = (RadioGroup)findViewById(R.id.rdPreguntac_17);	capturac_17 =res.getString(R.string.PREGUNTAc_17);	layc_17 = (LinearLayout) findViewById(R.id.layc_17);
    rdPreguntac_17a = (RadioGroup)findViewById(R.id.rdPreguntac_17a);	capturac_17a =res.getString(R.string.PREGUNTAc_17a);	layc_17a = (LinearLayout) findViewById(R.id.layc_17a);
    rdPreguntac_17b = (RadioGroup)findViewById(R.id.rdPreguntac_17b);	capturac_17b =res.getString(R.string.PREGUNTAc_17b);	layc_17b = (LinearLayout) findViewById(R.id.layc_17b);
    rdPreguntac_17c = (RadioGroup)findViewById(R.id.rdPreguntac_17c);	capturac_17c =res.getString(R.string.PREGUNTAc_17c);	layc_17c = (LinearLayout) findViewById(R.id.layc_17c);
    rdPreguntac_17d = (RadioGroup)findViewById(R.id.rdPreguntac_17d);	capturac_17d =res.getString(R.string.PREGUNTAc_17d);	layc_17d = (LinearLayout) findViewById(R.id.layc_17d);
    rdPreguntac_17e = (RadioGroup)findViewById(R.id.rdPreguntac_17e);	capturac_17e =res.getString(R.string.PREGUNTAc_17e);	layc_17e = (LinearLayout) findViewById(R.id.layc_17e);
    rdPreguntac_17f = (RadioGroup)findViewById(R.id.rdPreguntac_17f);	capturac_17f =res.getString(R.string.PREGUNTAc_17f);	layc_17f = (LinearLayout) findViewById(R.id.layc_17f);
    rdPreguntac_18 = (RadioGroup)findViewById(R.id.rdPreguntac_18);	capturac_18 =res.getString(R.string.PREGUNTAc_18);	layc_18 = (LinearLayout) findViewById(R.id.layc_18);
    rdPreguntac_18a = (RadioGroup)findViewById(R.id.rdPreguntac_18a);	capturac_18a =res.getString(R.string.PREGUNTAc_18a);	layc_18a = (LinearLayout) findViewById(R.id.layc_18a);
    rdPreguntac_18b = (RadioGroup)findViewById(R.id.rdPreguntac_18b);	capturac_18b =res.getString(R.string.PREGUNTAc_18b);	layc_18b = (LinearLayout) findViewById(R.id.layc_18b);
    rdPreguntac_18c = (RadioGroup)findViewById(R.id.rdPreguntac_18c);	capturac_18c =res.getString(R.string.PREGUNTAc_18c);	layc_18c = (LinearLayout) findViewById(R.id.layc_18c);
    rdPreguntac_18d = (RadioGroup)findViewById(R.id.rdPreguntac_18d);	capturac_18d =res.getString(R.string.PREGUNTAc_18d);	layc_18d = (LinearLayout) findViewById(R.id.layc_18d);
    rdPreguntac_18e = (RadioGroup)findViewById(R.id.rdPreguntac_18e);	capturac_18e =res.getString(R.string.PREGUNTAc_18e);	layc_18e = (LinearLayout) findViewById(R.id.layc_18e);
    rdPreguntac_19 = (RadioGroup)findViewById(R.id.rdPreguntac_19);	capturac_19 =res.getString(R.string.PREGUNTAc_19);	layc_19 = (LinearLayout) findViewById(R.id.layc_19);
    rdPreguntac_20 = (RadioGroup)findViewById(R.id.rdPreguntac_20);	capturac_20 =res.getString(R.string.PREGUNTAc_20);	layc_20 = (LinearLayout) findViewById(R.id.layc_20);
    rdPreguntac_21 = (RadioGroup)findViewById(R.id.rdPreguntac_21);	capturac_21 =res.getString(R.string.PREGUNTAc_21);	layc_21 = (LinearLayout) findViewById(R.id.layc_21);
    rdPreguntac_22 = (RadioGroup)findViewById(R.id.rdPreguntac_22);	capturac_22 =res.getString(R.string.PREGUNTAc_22);	layc_22 = (LinearLayout) findViewById(R.id.layc_22);
    rdPreguntac_23 = (RadioGroup)findViewById(R.id.rdPreguntac_23);	capturac_23 =res.getString(R.string.PREGUNTAc_23);	layc_23 = (LinearLayout) findViewById(R.id.layc_23);

    editPreguntac_1= (EditText)findViewById(R.id.editPreguntac_1);
    editPreguntac_2= (EditText)findViewById(R.id.editPreguntac_2);
    editPreguntac_3= (EditText)findViewById(R.id.editPreguntac_3);
    editPreguntac_4= (EditText)findViewById(R.id.editPreguntac_4);
    editPreguntac_7= (EditText)findViewById(R.id.editPreguntac_7);
    editPreguntac_8a= (EditText)findViewById(R.id.editPreguntac_8a);
    editPreguntac_8b= (EditText)findViewById(R.id.editPreguntac_8b);
    editPreguntac_8c= (EditText)findViewById(R.id.editPreguntac_8c);
    editPreguntac_8d= (EditText)findViewById(R.id.editPreguntac_8d);
    editPreguntac_8e= (EditText)findViewById(R.id.editPreguntac_8e);
    editPreguntac_8f= (EditText)findViewById(R.id.editPreguntac_8f);
    editPreguntac_9= (EditText)findViewById(R.id.editPreguntac_9);
    editPreguntac_11= (EditText)findViewById(R.id.editPreguntac_11);
    editPreguntac_12a= (EditText)findViewById(R.id.editPreguntac_12a);
    editPreguntac_12b= (EditText)findViewById(R.id.editPreguntac_12b);
    editPreguntac_12c= (EditText)findViewById(R.id.editPreguntac_12c);
    editPreguntac_12d= (EditText)findViewById(R.id.editPreguntac_12d);
    editPreguntac_16a= (EditText)findViewById(R.id.editPreguntac_16a);
    editPreguntac_16b= (EditText)findViewById(R.id.editPreguntac_16b);
    editPreguntac_16c= (EditText)findViewById(R.id.editPreguntac_16c);
    editPreguntac_16d= (EditText)findViewById(R.id.editPreguntac_16d);
    editPreguntac_18a= (EditText)findViewById(R.id.editPreguntac_18a);
    editPreguntac_18b= (EditText)findViewById(R.id.editPreguntac_18b);
    editPreguntac_18c= (EditText)findViewById(R.id.editPreguntac_18c);
    editPreguntac_18d= (EditText)findViewById(R.id.editPreguntac_18d);
    editPreguntac_21= (EditText)findViewById(R.id.editPreguntac_21);
    editPreguntac_22= (EditText)findViewById(R.id.editPreguntac_22);
    editPreguntac_23= (EditText)findViewById(R.id.editPreguntac_23);


    spinnerc_12e= (Spinner)findViewById(R.id.spinnerc_12e);
    spinnerc_18e= (Spinner)findViewById(R.id.spinnerc_18e);

    CargaSpinnerAlcaldiac_12e();
    CargaSpinnerAlcaldiac_18e();



    laySocioE = (LinearLayout) findViewById(R.id.laySocioE);
    layEst = (LinearLayout) findViewById(R.id.layEst);
    layAporta = (LinearLayout) findViewById(R.id.layAporta);
    layOcupacion = (LinearLayout) findViewById(R.id.layOcupacion);
    layCuartos = (LinearLayout) findViewById(R.id.layCuartos);
    layCuartosDormir = (LinearLayout) findViewById(R.id.layCuartosDormir);
    layFocos = (LinearLayout) findViewById(R.id.layFocos);
    layBanos = (LinearLayout) findViewById(R.id.layBanos);
    layRegadera = (LinearLayout) findViewById(R.id.layRegadera);
    layEstufa = (LinearLayout) findViewById(R.id.layEstufa);
    layEdad = (LinearLayout) findViewById(R.id.layEdad);
    layTipoPiso = (LinearLayout) findViewById(R.id.layTipoPiso);
    layTipoVivienda = (LinearLayout) findViewById(R.id.layTipoVivienda);
    layGenero = (LinearLayout) findViewById(R.id.layGenero);

    radio_abandono1 = (RadioButton) findViewById(R.id.radio_abandono1);
    radio_abandono2 = (RadioButton) findViewById(R.id.radio_abandono2);
    radio_abandono3 = (RadioButton) findViewById(R.id.radio_abandono3);
    radio_abandono4 = (RadioButton) findViewById(R.id.radio_abandono4);


    rdPreguntaTipoVivienda = (RadioGroup) findViewById(R.id.rdPreguntaTipoVivienda);
    rdPreguntaTipoPiso = (RadioGroup) findViewById(R.id.rdPreguntaTipoPiso);

    btnGuardar = (Button) findViewById(R.id.btnGuardar);
    btnSalir = (Button) findViewById(R.id.btnSalir);
    btnSalir.setEnabled(false);
    btnSalir.setVisibility(View.GONE);


    textPreguntaEntrada = (TextView) findViewById(R.id.textPreguntaEntrada);
    textPreguntaSalida = (TextView) findViewById(R.id.textPreguntaSalida);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        textPreguntaEntrada.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        textPreguntaSalida.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }

////////////////////////////////////////////////////////////////////////////  INICIAN LAS PREGUNTAS //////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc = "C";
            }
            else if (checkedId == R.id.radio3) {
                opc = "Acepta contestar";
                layc_1 .setVisibility(View.VISIBLE);	rdPreguntac_1.clearCheck();	opc_1="sin datos";
                layc_2 .setVisibility(View.VISIBLE);	rdPreguntac_2.clearCheck();	opc_2="sin datos";
                layc_3 .setVisibility(View.VISIBLE);	rdPreguntac_3.clearCheck();	opc_3="sin datos";
                layc_4 .setVisibility(View.VISIBLE);	rdPreguntac_4.clearCheck();	opc_4="sin datos";
                layc_5 .setVisibility(View.VISIBLE);	rdPreguntac_5.clearCheck();	opc_5="sin datos";
                layc_6 .setVisibility(View.VISIBLE);	rdPreguntac_6.clearCheck();	opc_6="sin datos";
                layc_6a .setVisibility(View.VISIBLE);	rdPreguntac_6a.clearCheck();	opc_6a="sin datos";
                layc_6b .setVisibility(View.VISIBLE);	rdPreguntac_6b.clearCheck();	opc_6b="sin datos";
                layc_6c .setVisibility(View.VISIBLE);	rdPreguntac_6c.clearCheck();	opc_6c="sin datos";
                layc_6d .setVisibility(View.VISIBLE);	rdPreguntac_6d.clearCheck();	opc_6d="sin datos";
                layc_6e .setVisibility(View.VISIBLE);	rdPreguntac_6e.clearCheck();	opc_6e="sin datos";
                layc_6f .setVisibility(View.VISIBLE);	rdPreguntac_6f.clearCheck();	opc_6f="sin datos";
                layc_6g .setVisibility(View.VISIBLE);	rdPreguntac_6g.clearCheck();	opc_6g="sin datos";
                layc_6h .setVisibility(View.VISIBLE);	rdPreguntac_6h.clearCheck();	opc_6h="sin datos";
                layc_6i .setVisibility(View.VISIBLE);	rdPreguntac_6i.clearCheck();	opc_6i="sin datos";
                layc_6j .setVisibility(View.VISIBLE);	rdPreguntac_6j.clearCheck();	opc_6j="sin datos";
                layc_6k .setVisibility(View.VISIBLE);	rdPreguntac_6k.clearCheck();	opc_6k="sin datos";
                layc_7 .setVisibility(View.VISIBLE);	rdPreguntac_7.clearCheck();	opc_7="sin datos";
                layc_8 .setVisibility(View.VISIBLE);	rdPreguntac_8.clearCheck();	opc_8="sin datos";
                layc_8a .setVisibility(View.VISIBLE);	rdPreguntac_8a.clearCheck();	opc_8a="sin datos";
                layc_8b .setVisibility(View.VISIBLE);	rdPreguntac_8b.clearCheck();	opc_8b="sin datos";
                layc_8c .setVisibility(View.VISIBLE);	rdPreguntac_8c.clearCheck();	opc_8c="sin datos";
                layc_8d .setVisibility(View.VISIBLE);	rdPreguntac_8d.clearCheck();	opc_8d="sin datos";
                layc_8e .setVisibility(View.VISIBLE);	rdPreguntac_8e.clearCheck();	opc_8e="sin datos";
                layc_8f .setVisibility(View.VISIBLE);	rdPreguntac_8f.clearCheck();	opc_8f="sin datos";
                layc_9 .setVisibility(View.VISIBLE);	rdPreguntac_9.clearCheck();	opc_9="sin datos";
                layc_10 .setVisibility(View.VISIBLE);	rdPreguntac_10.clearCheck();	opc_10="sin datos";
                layc_11 .setVisibility(View.VISIBLE);	rdPreguntac_11.clearCheck();	opc_11="sin datos";
                layc_12 .setVisibility(View.VISIBLE);	rdPreguntac_12.clearCheck();	opc_12="sin datos";
                layc_12a .setVisibility(View.VISIBLE);	rdPreguntac_12a.clearCheck();	opc_12a="sin datos";
                layc_12b .setVisibility(View.VISIBLE);	rdPreguntac_12b.clearCheck();	opc_12b="sin datos";
                layc_12c .setVisibility(View.VISIBLE);	rdPreguntac_12c.clearCheck();	opc_12c="sin datos";
                layc_12d .setVisibility(View.VISIBLE);	rdPreguntac_12d.clearCheck();	opc_12d="sin datos";
                layc_12e .setVisibility(View.VISIBLE);	rdPreguntac_12e.clearCheck();	opc_12e="sin datos";
                layc_13 .setVisibility(View.VISIBLE);	rdPreguntac_13.clearCheck();	opc_13="sin datos";
                layc_14 .setVisibility(View.VISIBLE);	rdPreguntac_14.clearCheck();	opc_14="sin datos";
                layc_15 .setVisibility(View.VISIBLE);	rdPreguntac_15.clearCheck();	opc_15="sin datos";
                layc_15a .setVisibility(View.VISIBLE);	rdPreguntac_15a.clearCheck();	opc_15a="sin datos";
                layc_15b .setVisibility(View.VISIBLE);	rdPreguntac_15b.clearCheck();	opc_15b="sin datos";
                layc_15c .setVisibility(View.VISIBLE);	rdPreguntac_15c.clearCheck();	opc_15c="sin datos";
                layc_15d .setVisibility(View.VISIBLE);	rdPreguntac_15d.clearCheck();	opc_15d="sin datos";
                layc_15e .setVisibility(View.VISIBLE);	rdPreguntac_15e.clearCheck();	opc_15e="sin datos";
                layc_15f .setVisibility(View.VISIBLE);	rdPreguntac_15f.clearCheck();	opc_15f="sin datos";
                layc_15g .setVisibility(View.VISIBLE);	rdPreguntac_15g.clearCheck();	opc_15g="sin datos";
                layc_15h .setVisibility(View.VISIBLE);	rdPreguntac_15h.clearCheck();	opc_15h="sin datos";
                layc_15i .setVisibility(View.VISIBLE);	rdPreguntac_15i.clearCheck();	opc_15i="sin datos";
                layc_15j .setVisibility(View.VISIBLE);	rdPreguntac_15j.clearCheck();	opc_15j="sin datos";
                layc_15k .setVisibility(View.VISIBLE);	rdPreguntac_15k.clearCheck();	opc_15k="sin datos";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="sin datos";
                layc_17 .setVisibility(View.VISIBLE);	rdPreguntac_17.clearCheck();	opc_17="sin datos";
                layc_17a .setVisibility(View.VISIBLE);	rdPreguntac_17a.clearCheck();	opc_17a="sin datos";
                layc_17b .setVisibility(View.VISIBLE);	rdPreguntac_17b.clearCheck();	opc_17b="sin datos";
                layc_17c .setVisibility(View.VISIBLE);	rdPreguntac_17c.clearCheck();	opc_17c="sin datos";
                layc_17d .setVisibility(View.VISIBLE);	rdPreguntac_17d.clearCheck();	opc_17d="sin datos";
                layc_17e .setVisibility(View.VISIBLE);	rdPreguntac_17e.clearCheck();	opc_17e="sin datos";
                layc_17f .setVisibility(View.VISIBLE);	rdPreguntac_17f.clearCheck();	opc_17f="sin datos";
                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
                layc_19 .setVisibility(View.VISIBLE);	rdPreguntac_19.clearCheck();	opc_19="sin datos";
                layc_20 .setVisibility(View.VISIBLE);	rdPreguntac_20.clearCheck();	opc_20="sin datos";
                layc_21 .setVisibility(View.VISIBLE);	rdPreguntac_21.clearCheck();	opc_21="sin datos";
                layc_22 .setVisibility(View.VISIBLE);	rdPreguntac_22.clearCheck();	opc_22="sin datos";
                layc_23 .setVisibility(View.VISIBLE);	rdPreguntac_23.clearCheck();	opc_23="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                opc = "Menor de edad";
                layc_1 .setVisibility(View.VISIBLE);	rdPreguntac_1.clearCheck();	opc_1="sin datos";
                layc_2 .setVisibility(View.VISIBLE);	rdPreguntac_2.clearCheck();	opc_2="sin datos";
                layc_3 .setVisibility(View.VISIBLE);	rdPreguntac_3.clearCheck();	opc_3="sin datos";
                layc_4 .setVisibility(View.VISIBLE);	rdPreguntac_4.clearCheck();	opc_4="sin datos";
                layc_5 .setVisibility(View.VISIBLE);	rdPreguntac_5.clearCheck();	opc_5="sin datos";
                layc_6 .setVisibility(View.VISIBLE);	rdPreguntac_6.clearCheck();	opc_6="sin datos";
                layc_6a .setVisibility(View.VISIBLE);	rdPreguntac_6a.clearCheck();	opc_6a="sin datos";
                layc_6b .setVisibility(View.VISIBLE);	rdPreguntac_6b.clearCheck();	opc_6b="sin datos";
                layc_6c .setVisibility(View.VISIBLE);	rdPreguntac_6c.clearCheck();	opc_6c="sin datos";
                layc_6d .setVisibility(View.VISIBLE);	rdPreguntac_6d.clearCheck();	opc_6d="sin datos";
                layc_6e .setVisibility(View.VISIBLE);	rdPreguntac_6e.clearCheck();	opc_6e="sin datos";
                layc_6f .setVisibility(View.VISIBLE);	rdPreguntac_6f.clearCheck();	opc_6f="sin datos";
                layc_6g .setVisibility(View.VISIBLE);	rdPreguntac_6g.clearCheck();	opc_6g="sin datos";
                layc_6h .setVisibility(View.VISIBLE);	rdPreguntac_6h.clearCheck();	opc_6h="sin datos";
                layc_6i .setVisibility(View.VISIBLE);	rdPreguntac_6i.clearCheck();	opc_6i="sin datos";
                layc_6j .setVisibility(View.VISIBLE);	rdPreguntac_6j.clearCheck();	opc_6j="sin datos";
                layc_6k .setVisibility(View.VISIBLE);	rdPreguntac_6k.clearCheck();	opc_6k="sin datos";
                layc_7 .setVisibility(View.VISIBLE);	rdPreguntac_7.clearCheck();	opc_7="sin datos";
                layc_8 .setVisibility(View.VISIBLE);	rdPreguntac_8.clearCheck();	opc_8="sin datos";
                layc_8a .setVisibility(View.VISIBLE);	rdPreguntac_8a.clearCheck();	opc_8a="sin datos";
                layc_8b .setVisibility(View.VISIBLE);	rdPreguntac_8b.clearCheck();	opc_8b="sin datos";
                layc_8c .setVisibility(View.VISIBLE);	rdPreguntac_8c.clearCheck();	opc_8c="sin datos";
                layc_8d .setVisibility(View.VISIBLE);	rdPreguntac_8d.clearCheck();	opc_8d="sin datos";
                layc_8e .setVisibility(View.VISIBLE);	rdPreguntac_8e.clearCheck();	opc_8e="sin datos";
                layc_8f .setVisibility(View.VISIBLE);	rdPreguntac_8f.clearCheck();	opc_8f="sin datos";
                layc_9 .setVisibility(View.VISIBLE);	rdPreguntac_9.clearCheck();	opc_9="sin datos";
                layc_10 .setVisibility(View.VISIBLE);	rdPreguntac_10.clearCheck();	opc_10="sin datos";
                layc_11 .setVisibility(View.VISIBLE);	rdPreguntac_11.clearCheck();	opc_11="sin datos";
                layc_12 .setVisibility(View.VISIBLE);	rdPreguntac_12.clearCheck();	opc_12="sin datos";
                layc_12a .setVisibility(View.VISIBLE);	rdPreguntac_12a.clearCheck();	opc_12a="sin datos";
                layc_12b .setVisibility(View.VISIBLE);	rdPreguntac_12b.clearCheck();	opc_12b="sin datos";
                layc_12c .setVisibility(View.VISIBLE);	rdPreguntac_12c.clearCheck();	opc_12c="sin datos";
                layc_12d .setVisibility(View.VISIBLE);	rdPreguntac_12d.clearCheck();	opc_12d="sin datos";
                layc_12e .setVisibility(View.VISIBLE);	rdPreguntac_12e.clearCheck();	opc_12e="sin datos";
                layc_13 .setVisibility(View.VISIBLE);	rdPreguntac_13.clearCheck();	opc_13="sin datos";
                layc_14 .setVisibility(View.VISIBLE);	rdPreguntac_14.clearCheck();	opc_14="sin datos";
                layc_15 .setVisibility(View.VISIBLE);	rdPreguntac_15.clearCheck();	opc_15="sin datos";
                layc_15a .setVisibility(View.VISIBLE);	rdPreguntac_15a.clearCheck();	opc_15a="sin datos";
                layc_15b .setVisibility(View.VISIBLE);	rdPreguntac_15b.clearCheck();	opc_15b="sin datos";
                layc_15c .setVisibility(View.VISIBLE);	rdPreguntac_15c.clearCheck();	opc_15c="sin datos";
                layc_15d .setVisibility(View.VISIBLE);	rdPreguntac_15d.clearCheck();	opc_15d="sin datos";
                layc_15e .setVisibility(View.VISIBLE);	rdPreguntac_15e.clearCheck();	opc_15e="sin datos";
                layc_15f .setVisibility(View.VISIBLE);	rdPreguntac_15f.clearCheck();	opc_15f="sin datos";
                layc_15g .setVisibility(View.VISIBLE);	rdPreguntac_15g.clearCheck();	opc_15g="sin datos";
                layc_15h .setVisibility(View.VISIBLE);	rdPreguntac_15h.clearCheck();	opc_15h="sin datos";
                layc_15i .setVisibility(View.VISIBLE);	rdPreguntac_15i.clearCheck();	opc_15i="sin datos";
                layc_15j .setVisibility(View.VISIBLE);	rdPreguntac_15j.clearCheck();	opc_15j="sin datos";
                layc_15k .setVisibility(View.VISIBLE);	rdPreguntac_15k.clearCheck();	opc_15k="sin datos";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="sin datos";
                layc_17 .setVisibility(View.VISIBLE);	rdPreguntac_17.clearCheck();	opc_17="sin datos";
                layc_17a .setVisibility(View.VISIBLE);	rdPreguntac_17a.clearCheck();	opc_17a="sin datos";
                layc_17b .setVisibility(View.VISIBLE);	rdPreguntac_17b.clearCheck();	opc_17b="sin datos";
                layc_17c .setVisibility(View.VISIBLE);	rdPreguntac_17c.clearCheck();	opc_17c="sin datos";
                layc_17d .setVisibility(View.VISIBLE);	rdPreguntac_17d.clearCheck();	opc_17d="sin datos";
                layc_17e .setVisibility(View.VISIBLE);	rdPreguntac_17e.clearCheck();	opc_17e="sin datos";
                layc_17f .setVisibility(View.VISIBLE);	rdPreguntac_17f.clearCheck();	opc_17f="sin datos";
                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
                layc_19 .setVisibility(View.VISIBLE);	rdPreguntac_19.clearCheck();	opc_19="sin datos";
                layc_20 .setVisibility(View.VISIBLE);	rdPreguntac_20.clearCheck();	opc_20="sin datos";
                layc_21 .setVisibility(View.VISIBLE);	rdPreguntac_21.clearCheck();	opc_21="sin datos";
                layc_22 .setVisibility(View.VISIBLE);	rdPreguntac_22.clearCheck();	opc_22="sin datos";
                layc_23 .setVisibility(View.VISIBLE);	rdPreguntac_23.clearCheck();	opc_23="sin datos";
            }
            else if (checkedId == R.id.radio5) {
                opc = "Rechazó contestar";
                layc_1 .setVisibility(View.GONE);	rdPreguntac_1.clearCheck();	opc_1="No aplica";
                layc_2 .setVisibility(View.GONE);	rdPreguntac_2.clearCheck();	opc_2="No aplica";
                layc_3 .setVisibility(View.GONE);	rdPreguntac_3.clearCheck();	opc_3="No aplica";
                layc_4 .setVisibility(View.GONE);	rdPreguntac_4.clearCheck();	opc_4="No aplica";
                layc_5 .setVisibility(View.GONE);	rdPreguntac_5.clearCheck();	opc_5="No aplica";
                layc_6 .setVisibility(View.GONE);	rdPreguntac_6.clearCheck();	opc_6="No aplica";
                layc_6a .setVisibility(View.GONE);	rdPreguntac_6a.clearCheck();	opc_6a="No aplica";
                layc_6b .setVisibility(View.GONE);	rdPreguntac_6b.clearCheck();	opc_6b="No aplica";
                layc_6c .setVisibility(View.GONE);	rdPreguntac_6c.clearCheck();	opc_6c="No aplica";
                layc_6d .setVisibility(View.GONE);	rdPreguntac_6d.clearCheck();	opc_6d="No aplica";
                layc_6e .setVisibility(View.GONE);	rdPreguntac_6e.clearCheck();	opc_6e="No aplica";
                layc_6f .setVisibility(View.GONE);	rdPreguntac_6f.clearCheck();	opc_6f="No aplica";
                layc_6g .setVisibility(View.GONE);	rdPreguntac_6g.clearCheck();	opc_6g="No aplica";
                layc_6h .setVisibility(View.GONE);	rdPreguntac_6h.clearCheck();	opc_6h="No aplica";
                layc_6i .setVisibility(View.GONE);	rdPreguntac_6i.clearCheck();	opc_6i="No aplica";
                layc_6j .setVisibility(View.GONE);	rdPreguntac_6j.clearCheck();	opc_6j="No aplica";
                layc_6k .setVisibility(View.GONE);	rdPreguntac_6k.clearCheck();	opc_6k="No aplica";
                layc_7 .setVisibility(View.GONE);	rdPreguntac_7.clearCheck();	opc_7="No aplica";
                layc_8 .setVisibility(View.GONE);	rdPreguntac_8.clearCheck();	opc_8="No aplica";
                layc_8a .setVisibility(View.GONE);	rdPreguntac_8a.clearCheck();	opc_8a="No aplica";
                layc_8b .setVisibility(View.GONE);	rdPreguntac_8b.clearCheck();	opc_8b="No aplica";
                layc_8c .setVisibility(View.GONE);	rdPreguntac_8c.clearCheck();	opc_8c="No aplica";
                layc_8d .setVisibility(View.GONE);	rdPreguntac_8d.clearCheck();	opc_8d="No aplica";
                layc_8e .setVisibility(View.GONE);	rdPreguntac_8e.clearCheck();	opc_8e="No aplica";
                layc_8f .setVisibility(View.GONE);	rdPreguntac_8f.clearCheck();	opc_8f="No aplica";
                layc_9 .setVisibility(View.GONE);	rdPreguntac_9.clearCheck();	opc_9="No aplica";
                layc_10 .setVisibility(View.GONE);	rdPreguntac_10.clearCheck();	opc_10="No aplica";
                layc_11 .setVisibility(View.GONE);	rdPreguntac_11.clearCheck();	opc_11="No aplica";
                layc_12 .setVisibility(View.GONE);	rdPreguntac_12.clearCheck();	opc_12="No aplica";
                layc_12a .setVisibility(View.GONE);	rdPreguntac_12a.clearCheck();	opc_12a="No aplica";
                layc_12b .setVisibility(View.GONE);	rdPreguntac_12b.clearCheck();	opc_12b="No aplica";
                layc_12c .setVisibility(View.GONE);	rdPreguntac_12c.clearCheck();	opc_12c="No aplica";
                layc_12d .setVisibility(View.GONE);	rdPreguntac_12d.clearCheck();	opc_12d="No aplica";
                layc_12e .setVisibility(View.GONE);	rdPreguntac_12e.clearCheck();	opc_12e="No aplica";
                layc_13 .setVisibility(View.GONE);	rdPreguntac_13.clearCheck();	opc_13="No aplica";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
                layc_15 .setVisibility(View.GONE);	rdPreguntac_15.clearCheck();	opc_15="No aplica";
                layc_15a .setVisibility(View.GONE);	rdPreguntac_15a.clearCheck();	opc_15a="No aplica";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
                layc_17 .setVisibility(View.GONE);	rdPreguntac_17.clearCheck();	opc_17="No aplica";
                layc_17a .setVisibility(View.GONE);	rdPreguntac_17a.clearCheck();	opc_17a="No aplica";
                layc_17b .setVisibility(View.GONE);	rdPreguntac_17b.clearCheck();	opc_17b="No aplica";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="No aplica";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="No aplica";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="No aplica";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="No aplica";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="No aplica";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="No aplica";
                layc_19 .setVisibility(View.GONE);	rdPreguntac_19.clearCheck();	opc_19="No aplica";
                layc_20 .setVisibility(View.GONE);	rdPreguntac_20.clearCheck();	opc_20="No aplica";
                layc_21 .setVisibility(View.GONE);	rdPreguntac_21.clearCheck();	opc_21="No aplica";
                layc_22 .setVisibility(View.GONE);	rdPreguntac_22.clearCheck();	opc_22="No aplica";
                layc_23 .setVisibility(View.GONE);	rdPreguntac_23.clearCheck();	opc_23="No aplica";

            }
            else if (checkedId == R.id.radio6) {
                opc = "No está";
                layc_1 .setVisibility(View.GONE);	rdPreguntac_1.clearCheck();	opc_1="No aplica";
                layc_2 .setVisibility(View.GONE);	rdPreguntac_2.clearCheck();	opc_2="No aplica";
                layc_3 .setVisibility(View.GONE);	rdPreguntac_3.clearCheck();	opc_3="No aplica";
                layc_4 .setVisibility(View.GONE);	rdPreguntac_4.clearCheck();	opc_4="No aplica";
                layc_5 .setVisibility(View.GONE);	rdPreguntac_5.clearCheck();	opc_5="No aplica";
                layc_6 .setVisibility(View.GONE);	rdPreguntac_6.clearCheck();	opc_6="No aplica";
                layc_6a .setVisibility(View.GONE);	rdPreguntac_6a.clearCheck();	opc_6a="No aplica";
                layc_6b .setVisibility(View.GONE);	rdPreguntac_6b.clearCheck();	opc_6b="No aplica";
                layc_6c .setVisibility(View.GONE);	rdPreguntac_6c.clearCheck();	opc_6c="No aplica";
                layc_6d .setVisibility(View.GONE);	rdPreguntac_6d.clearCheck();	opc_6d="No aplica";
                layc_6e .setVisibility(View.GONE);	rdPreguntac_6e.clearCheck();	opc_6e="No aplica";
                layc_6f .setVisibility(View.GONE);	rdPreguntac_6f.clearCheck();	opc_6f="No aplica";
                layc_6g .setVisibility(View.GONE);	rdPreguntac_6g.clearCheck();	opc_6g="No aplica";
                layc_6h .setVisibility(View.GONE);	rdPreguntac_6h.clearCheck();	opc_6h="No aplica";
                layc_6i .setVisibility(View.GONE);	rdPreguntac_6i.clearCheck();	opc_6i="No aplica";
                layc_6j .setVisibility(View.GONE);	rdPreguntac_6j.clearCheck();	opc_6j="No aplica";
                layc_6k .setVisibility(View.GONE);	rdPreguntac_6k.clearCheck();	opc_6k="No aplica";
                layc_7 .setVisibility(View.GONE);	rdPreguntac_7.clearCheck();	opc_7="No aplica";
                layc_8 .setVisibility(View.GONE);	rdPreguntac_8.clearCheck();	opc_8="No aplica";
                layc_8a .setVisibility(View.GONE);	rdPreguntac_8a.clearCheck();	opc_8a="No aplica";
                layc_8b .setVisibility(View.GONE);	rdPreguntac_8b.clearCheck();	opc_8b="No aplica";
                layc_8c .setVisibility(View.GONE);	rdPreguntac_8c.clearCheck();	opc_8c="No aplica";
                layc_8d .setVisibility(View.GONE);	rdPreguntac_8d.clearCheck();	opc_8d="No aplica";
                layc_8e .setVisibility(View.GONE);	rdPreguntac_8e.clearCheck();	opc_8e="No aplica";
                layc_8f .setVisibility(View.GONE);	rdPreguntac_8f.clearCheck();	opc_8f="No aplica";
                layc_9 .setVisibility(View.GONE);	rdPreguntac_9.clearCheck();	opc_9="No aplica";
                layc_10 .setVisibility(View.GONE);	rdPreguntac_10.clearCheck();	opc_10="No aplica";
                layc_11 .setVisibility(View.GONE);	rdPreguntac_11.clearCheck();	opc_11="No aplica";
                layc_12 .setVisibility(View.GONE);	rdPreguntac_12.clearCheck();	opc_12="No aplica";
                layc_12a .setVisibility(View.GONE);	rdPreguntac_12a.clearCheck();	opc_12a="No aplica";
                layc_12b .setVisibility(View.GONE);	rdPreguntac_12b.clearCheck();	opc_12b="No aplica";
                layc_12c .setVisibility(View.GONE);	rdPreguntac_12c.clearCheck();	opc_12c="No aplica";
                layc_12d .setVisibility(View.GONE);	rdPreguntac_12d.clearCheck();	opc_12d="No aplica";
                layc_12e .setVisibility(View.GONE);	rdPreguntac_12e.clearCheck();	opc_12e="No aplica";
                layc_13 .setVisibility(View.GONE);	rdPreguntac_13.clearCheck();	opc_13="No aplica";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
                layc_15 .setVisibility(View.GONE);	rdPreguntac_15.clearCheck();	opc_15="No aplica";
                layc_15a .setVisibility(View.GONE);	rdPreguntac_15a.clearCheck();	opc_15a="No aplica";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
                layc_17 .setVisibility(View.GONE);	rdPreguntac_17.clearCheck();	opc_17="No aplica";
                layc_17a .setVisibility(View.GONE);	rdPreguntac_17a.clearCheck();	opc_17a="No aplica";
                layc_17b .setVisibility(View.GONE);	rdPreguntac_17b.clearCheck();	opc_17b="No aplica";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="No aplica";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="No aplica";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="No aplica";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="No aplica";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="No aplica";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="No aplica";
                layc_19 .setVisibility(View.GONE);	rdPreguntac_19.clearCheck();	opc_19="No aplica";
                layc_20 .setVisibility(View.GONE);	rdPreguntac_20.clearCheck();	opc_20="No aplica";
                layc_21 .setVisibility(View.GONE);	rdPreguntac_21.clearCheck();	opc_21="No aplica";
                layc_22 .setVisibility(View.GONE);	rdPreguntac_22.clearCheck();	opc_22="No aplica";
                layc_23 .setVisibility(View.GONE);	rdPreguntac_23.clearCheck();	opc_23="No aplica";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_1 = "1";
                editPreguntac_1.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_1 = "c_1";
                editPreguntac_1.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_1 = "No sabe / No contestó";
                editPreguntac_1.setText("");
            }
        }
    });
    editPreguntac_1.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_1.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_1.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_2 = "1";
                editPreguntac_2.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_2 = "c_2";
                editPreguntac_2.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_2 = "No sabe / No contestó";
                editPreguntac_2.setText("");
            }
        }
    });
    editPreguntac_2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_2.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_2.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_3 = "1";
                editPreguntac_3.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_3 = "c_3";
                editPreguntac_3.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_3 = "No sabe / No contestó";
                editPreguntac_3.setText("");
            }
        }
    });
    editPreguntac_3.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_3.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_3.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_4 = "1";
                editPreguntac_4.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_4 = "c_4";
                editPreguntac_4.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_4 = "No sabe / No contestó";
                editPreguntac_4.setText("");
            }
        }
    });
    editPreguntac_4.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_4.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_4.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_5 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_5 = "c_5";
            }
            else if (checkedId == R.id.radio3) {
                opc_5 = "Hombre";
            }
            else if (checkedId == R.id.radio4) {
                opc_5 = "Mujer";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6 = "c_6";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6a = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6a = "c_6a";
            }
            else if (checkedId == R.id.radio3) {
                opc_6a = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6a = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6a = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6b = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6b = "c_6b";
            }
            else if (checkedId == R.id.radio3) {
                opc_6b = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6b = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6b = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6c = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6c = "c_6c";
            }
            else if (checkedId == R.id.radio3) {
                opc_6c = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6c = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6c = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6d = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6d = "c_6d";
            }
            else if (checkedId == R.id.radio3) {
                opc_6d = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6d = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6d = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6e = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6e = "c_6e";
            }
            else if (checkedId == R.id.radio3) {
                opc_6e = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6e = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6e = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6f = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6f = "c_6f";
            }
            else if (checkedId == R.id.radio3) {
                opc_6f = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6f = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6f = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6g = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6g = "c_6g";
            }
            else if (checkedId == R.id.radio3) {
                opc_6g = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6g = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6g = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6h = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6h = "c_6h";
            }
            else if (checkedId == R.id.radio3) {
                opc_6h = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6h = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6h = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6i.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6i = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6i = "c_6i";
            }
            else if (checkedId == R.id.radio3) {
                opc_6i = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6i = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6i = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6j.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6j = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6j = "c_6j";
            }
            else if (checkedId == R.id.radio3) {
                opc_6j = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6j = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6j = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_6k.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_6k = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_6k = "c_6k";
            }
            else if (checkedId == R.id.radio3) {
                opc_6k = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_6k = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_6k = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_7 = "1";
                editPreguntac_7.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_7 = "c_7";
                editPreguntac_7.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_7 = "NO TIENE";
                editPreguntac_7.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_7 = "No sabe / No contestó";
                editPreguntac_7.setText("");
            }
        }
    });
    editPreguntac_7.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_7.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_7.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_8 = "c_8";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8a = "1";
                editPreguntac_8a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8a = "c_8a";
                editPreguntac_8a.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8a = "Si es";
                editPreguntac_8a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8a = "No sabe / No contestó";
                editPreguntac_8a.setText("");
            }
        }
    });
    editPreguntac_8a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8b = "1";
                editPreguntac_8b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8b = "c_8b";
                editPreguntac_8b.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8b = "Si es";
                editPreguntac_8b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8b = "No sabe / No contestó";
                editPreguntac_8b.setText("");
            }
        }
    });
    editPreguntac_8b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8c = "1";
                editPreguntac_8c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8c = "c_8c";
                editPreguntac_8c.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8c = "Si es";
                editPreguntac_8c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8c = "No sabe / No contestó";
                editPreguntac_8c.setText("");
            }
        }
    });
    editPreguntac_8c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8d = "1";
                editPreguntac_8d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8d = "c_8d";
                editPreguntac_8d.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8d = "Si es";
                editPreguntac_8d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8d = "No sabe / No contestó";
                editPreguntac_8d.setText("");
            }
        }
    });
    editPreguntac_8d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8e = "1";
                editPreguntac_8e.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8e = "c_8e";
                editPreguntac_8e.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8e = "Si es";
                editPreguntac_8e.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8e = "No sabe / No contestó";
                editPreguntac_8e.setText("");
            }
        }
    });
    editPreguntac_8e.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8e.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8e.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_8f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_8f = "1";
                editPreguntac_8f.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_8f = "c_8f";
                editPreguntac_8f.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_8f = "Si es";
                editPreguntac_8f.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_8f = "No sabe / No contestó";
                editPreguntac_8f.setText("");
            }
        }
    });
    editPreguntac_8f.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_8f.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_8f.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_9 = "1";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_9 = "c_9";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_9 = "Esposo (a)/ Pareja";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio4) {
                opc_9 = "Hijo (a)";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio5) {
                opc_9 = "Padre/ Madre";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio6) {
                opc_9 = "Abuelo/ abuela";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio7) {
                opc_9 = "Tío/tía";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio8) {
                opc_9 = "Otro (registrar)";
                editPreguntac_9.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_9 = "No sabe / No contestó";
                editPreguntac_9.setText("");
            }
        }
    });
    editPreguntac_9.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_9.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_9.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_10.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_10 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_10 = "c_10";
            }
            else if (checkedId == R.id.radio3) {
                opc_10 = "SI";
                layc_11 .setVisibility(View.VISIBLE);	rdPreguntac_11.clearCheck();	opc_11="No aplica";
                layc_12 .setVisibility(View.VISIBLE);	rdPreguntac_12.clearCheck();	opc_12="No aplica";
                layc_12a .setVisibility(View.VISIBLE);	rdPreguntac_12a.clearCheck();	opc_12a="No aplica";
                layc_12b .setVisibility(View.VISIBLE);	rdPreguntac_12b.clearCheck();	opc_12b="No aplica";
                layc_12c .setVisibility(View.VISIBLE);	rdPreguntac_12c.clearCheck();	opc_12c="No aplica";
                layc_12d .setVisibility(View.VISIBLE);	rdPreguntac_12d.clearCheck();	opc_12d="No aplica";
                layc_12e .setVisibility(View.VISIBLE);	rdPreguntac_12e.clearCheck();	opc_12e="No aplica";
                layc_13 .setVisibility(View.VISIBLE);	rdPreguntac_13.clearCheck();	opc_13="No aplica";
                layc_14 .setVisibility(View.VISIBLE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
                layc_15 .setVisibility(View.VISIBLE);	rdPreguntac_15.clearCheck();	opc_15="No aplica";
                layc_15a .setVisibility(View.VISIBLE);	rdPreguntac_15a.clearCheck();	opc_15a="No aplica";
                layc_15b .setVisibility(View.VISIBLE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.VISIBLE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.VISIBLE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.VISIBLE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.VISIBLE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.VISIBLE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.VISIBLE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.VISIBLE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.VISIBLE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.VISIBLE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_10 = "NO";
                layc_11 .setVisibility(View.GONE);	rdPreguntac_11.clearCheck();	opc_11="No aplica";
                layc_12 .setVisibility(View.GONE);	rdPreguntac_12.clearCheck();	opc_12="No aplica";
                layc_12a .setVisibility(View.GONE);	rdPreguntac_12a.clearCheck();	opc_12a="No aplica";
                layc_12b .setVisibility(View.GONE);	rdPreguntac_12b.clearCheck();	opc_12b="No aplica";
                layc_12c .setVisibility(View.GONE);	rdPreguntac_12c.clearCheck();	opc_12c="No aplica";
                layc_12d .setVisibility(View.GONE);	rdPreguntac_12d.clearCheck();	opc_12d="No aplica";
                layc_12e .setVisibility(View.GONE);	rdPreguntac_12e.clearCheck();	opc_12e="No aplica";
                layc_13 .setVisibility(View.GONE);	rdPreguntac_13.clearCheck();	opc_13="No aplica";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
                layc_15 .setVisibility(View.GONE);	rdPreguntac_15.clearCheck();	opc_15="No aplica";
                layc_15a .setVisibility(View.GONE);	rdPreguntac_15a.clearCheck();	opc_15a="No aplica";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";

            }
            else if (checkedId == R.id.radio0) {
                opc_10 = "No sabe / No contestó";
                layc_11 .setVisibility(View.GONE);	rdPreguntac_11.clearCheck();	opc_11="No aplica";
                layc_12 .setVisibility(View.GONE);	rdPreguntac_12.clearCheck();	opc_12="No aplica";
                layc_12a .setVisibility(View.GONE);	rdPreguntac_12a.clearCheck();	opc_12a="No aplica";
                layc_12b .setVisibility(View.GONE);	rdPreguntac_12b.clearCheck();	opc_12b="No aplica";
                layc_12c .setVisibility(View.GONE);	rdPreguntac_12c.clearCheck();	opc_12c="No aplica";
                layc_12d .setVisibility(View.GONE);	rdPreguntac_12d.clearCheck();	opc_12d="No aplica";
                layc_12e .setVisibility(View.GONE);	rdPreguntac_12e.clearCheck();	opc_12e="No aplica";
                layc_13 .setVisibility(View.GONE);	rdPreguntac_13.clearCheck();	opc_13="No aplica";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
                layc_15 .setVisibility(View.GONE);	rdPreguntac_15.clearCheck();	opc_15="No aplica";
                layc_15a .setVisibility(View.GONE);	rdPreguntac_15a.clearCheck();	opc_15a="No aplica";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_11.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_11 = "1";
                editPreguntac_11.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_11 = "c_11";
                editPreguntac_11.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_11 = "No sabe / No contestó";
                editPreguntac_11.setText("");
            }
        }
    });
    editPreguntac_11.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_11.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_11.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_12 = "c_12";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12a = "1";
                editPreguntac_12a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_12a = "c_12a";
                editPreguntac_12a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_12a = "No sabe / No contestó";
                editPreguntac_12a.setText("");
            }
        }
    });
    editPreguntac_12a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_12a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_12a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12b = "1";
                editPreguntac_12b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_12b = "c_12b";
                editPreguntac_12b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_12b = "No sabe / No contestó";
                editPreguntac_12b.setText("");
            }
        }
    });
    editPreguntac_12b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_12b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_12b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12c = "1";
                editPreguntac_12c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_12c = "c_12c";
                editPreguntac_12c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_12c = "No sabe / No contestó";
                editPreguntac_12c.setText("");
            }
        }
    });
    editPreguntac_12c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_12c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_12c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12d = "1";
                editPreguntac_12d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_12d = "c_12d";
                editPreguntac_12d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_12d = "No sabe / No contestó";
                editPreguntac_12d.setText("");
            }
        }
    });
    editPreguntac_12d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_12d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_12d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_12e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_12e = "2";
                spinnerc_12e.setSelection(0);
            }
            else if (checkedId == R.id.radio2) {
                opc_12e = "c_12e";
                spinnerc_12e.setSelection(0);
            }
            else if (checkedId == R.id.radio0) {
                opc_12e = "No sabe / No contestó";
                spinnerc_12e.setSelection(0);
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_13.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_13 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_13 = "c_13";
            }
            else if (checkedId == R.id.radio3) {
                opc_13 = "SI";
                layc_14 .setVisibility(View.VISIBLE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";

            }
            else if (checkedId == R.id.radio4) {
                opc_13 = "NO";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_13 = "No sabe / No contestó";
                layc_14 .setVisibility(View.GONE);	rdPreguntac_14.clearCheck();	opc_14="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_14.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_14 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_14 = "c_14";
            }
            else if (checkedId == R.id.radio3) {
                opc_14 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_14 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_14 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15 = "c_15";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15a = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15a = "15a";
            }
            else if (checkedId == R.id.radio3) {
                opc_15a = "SI";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";

            }
            else if (checkedId == R.id.radio4) {
                opc_15a = "NO";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";


            }
            else if (checkedId == R.id.radio0) {
                opc_15a = "No sabe / No contestó";
                layc_15b .setVisibility(View.GONE);	rdPreguntac_15b.clearCheck();	opc_15b="No aplica";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15b = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15b = "15b";

            }
            else if (checkedId == R.id.radio3) {
                opc_15b = "SI";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15b = "NO";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15b = "No sabe / No contestó";
                layc_15c .setVisibility(View.GONE);	rdPreguntac_15c.clearCheck();	opc_15c="No aplica";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15c = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15c = "15c";
            }
            else if (checkedId == R.id.radio3) {
                opc_15c = "SI";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15c = "NO";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15c = "No sabe / No contestó";
                layc_15d .setVisibility(View.GONE);	rdPreguntac_15d.clearCheck();	opc_15d="No aplica";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15d = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15d = "15d";
            }
            else if (checkedId == R.id.radio3) {
                opc_15d = "SI";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15d = "NO";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15d = "No sabe / No contestó";
                layc_15e .setVisibility(View.GONE);	rdPreguntac_15e.clearCheck();	opc_15e="No aplica";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15e = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15e = "15e";
            }
            else if (checkedId == R.id.radio3) {
                opc_15e = "SI";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15e = "NO";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15e = "No sabe / No contestó";
                layc_15f .setVisibility(View.GONE);	rdPreguntac_15f.clearCheck();	opc_15f="No aplica";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15f = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15f = "15f";
            }
            else if (checkedId == R.id.radio3) {
                opc_15f = "SI";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15f = "NO";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15f = "No sabe / No contestó";
                layc_15g .setVisibility(View.GONE);	rdPreguntac_15g.clearCheck();	opc_15g="No aplica";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15g = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15g = "15g";
            }
            else if (checkedId == R.id.radio3) {
                opc_15g = "SI";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.VISIBLE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15g = "NO";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15g = "No sabe / No contestó";
                layc_15h .setVisibility(View.GONE);	rdPreguntac_15h.clearCheck();	opc_15h="No aplica";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15h = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15h = "15h";
            }
            else if (checkedId == R.id.radio3) {
                opc_15h = "SI";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15h = "NO";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15h = "No sabe / No contestó";
                layc_15i .setVisibility(View.GONE);	rdPreguntac_15i.clearCheck();	opc_15i="No aplica";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15i.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15i = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15i = "15i";
            }
            else if (checkedId == R.id.radio3) {
                opc_15i = "SI";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15i = "NO";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15i = "No sabe / No contestó";
                layc_15j .setVisibility(View.GONE);	rdPreguntac_15j.clearCheck();	opc_15j="No aplica";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15j.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15j = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15j = "15j";
            }
            else if (checkedId == R.id.radio3) {
                opc_15j = "SI";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15j = "NO";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15j = "No sabe / No contestó";
                layc_15k .setVisibility(View.GONE);	rdPreguntac_15k.clearCheck();	opc_15k="No aplica";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_15k.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_15k = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_15k = "15k";
            }
            else if (checkedId == R.id.radio3) {
                opc_15k = "SI";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                opc_15k = "NO";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opc_15k = "No sabe / No contestó";
                layc_16 .setVisibility(View.GONE);	rdPreguntac_16.clearCheck();	opc_16="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_16.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_16 = "1";
                editPreguntac_16a.setText("");
                editPreguntac_16b.setText("");
                editPreguntac_16c.setText("");
                editPreguntac_16d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_16 = "16";
                editPreguntac_16a.setText("");
                editPreguntac_16b.setText("");
                editPreguntac_16c.setText("");
                editPreguntac_16d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_16 = "No sabe / No contestó";
                editPreguntac_16a.setText("");
                editPreguntac_16b.setText("");
                editPreguntac_16c.setText("");
                editPreguntac_16d.setText("");
            }
        }
    });
    editPreguntac_16a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_16a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_16.clearCheck();
            }
        }
    });


    editPreguntac_16b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_16b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_16.clearCheck();
            }
        }
    });


    editPreguntac_16c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_16c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_16.clearCheck();
            }
        }
    });


    editPreguntac_16d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_16d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_16.clearCheck();
            }
        }
    });



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17 = "17";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17a = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17a = "17a";
            }
            else if (checkedId == R.id.radio3) {
                opc_17a = "SI";
                layc_17b .setVisibility(View.GONE);	rdPreguntac_17b.clearCheck();	opc_17b="No aplica";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";

                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                opc_17a = "NO";
                layc_17b .setVisibility(View.GONE);	rdPreguntac_17b.clearCheck();	opc_17b="No aplica";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";

            }
            else if (checkedId == R.id.radio0) {
                opc_17a = "No sabe / No contestó";
                layc_17b .setVisibility(View.GONE);	rdPreguntac_17b.clearCheck();	opc_17b="No aplica";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17b = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17b = "17b";
            }
            else if (checkedId == R.id.radio3) {
                opc_17b = "SI";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";

                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                opc_17b = "NO";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                opc_17b = "No sabe / No contestó";
                layc_17c .setVisibility(View.GONE);	rdPreguntac_17c.clearCheck();	opc_17c="No aplica";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17c = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17c = "17c";
            }
            else if (checkedId == R.id.radio3) {
                opc_17c = "SI";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";

                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                opc_17c = "NO";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                opc_17c = "No sabe / No contestó";
                layc_17d .setVisibility(View.GONE);	rdPreguntac_17d.clearCheck();	opc_17d="No aplica";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17d = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17d = "17d";
            }
            else if (checkedId == R.id.radio3) {
                opc_17d = "SI";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";

                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                opc_17d = "NO";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                opc_17d = "No sabe / No contestó";
                layc_17e .setVisibility(View.GONE);	rdPreguntac_17e.clearCheck();	opc_17e="No aplica";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17e = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17e = "17e";
            }
            else if (checkedId == R.id.radio3) {
                opc_17e = "SI";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";

                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                opc_17e = "NO";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                opc_17e = "No sabe / No contestó";
                layc_17f .setVisibility(View.GONE);	rdPreguntac_17f.clearCheck();	opc_17f="No aplica";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_17f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_17f = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_17f = "17f";
            }
            else if (checkedId == R.id.radio3) {
                opc_17f = "SI";
                layc_18 .setVisibility(View.VISIBLE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.VISIBLE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.VISIBLE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.VISIBLE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.VISIBLE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.VISIBLE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                opc_17f = "NO";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                opc_17f = "No sabe / No contestó";
                layc_18 .setVisibility(View.GONE);	rdPreguntac_18.clearCheck();	opc_18="sin datos";
                layc_18a .setVisibility(View.GONE);	rdPreguntac_18a.clearCheck();	opc_18a="sin datos";
                layc_18b .setVisibility(View.GONE);	rdPreguntac_18b.clearCheck();	opc_18b="sin datos";
                layc_18c .setVisibility(View.GONE);	rdPreguntac_18c.clearCheck();	opc_18c="sin datos";
                layc_18d .setVisibility(View.GONE);	rdPreguntac_18d.clearCheck();	opc_18d="sin datos";
                layc_18e .setVisibility(View.GONE);	rdPreguntac_18e.clearCheck();	opc_18e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_18 = "18";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18a = "1";
                editPreguntac_18a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_18a = "18a";
                editPreguntac_18a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_18a = "No sabe / No contestó";
                editPreguntac_18a.setText("");
            }
        }
    });
    editPreguntac_18a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_18a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_18a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18b = "1";
                editPreguntac_18b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_18b = "18b";
                editPreguntac_18b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_18b = "No sabe / No contestó";
                editPreguntac_18b.setText("");
            }
        }
    });
    editPreguntac_18b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_18b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_18b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18c = "1";
                editPreguntac_18c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_18c = "18c";
                editPreguntac_18c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_18c = "No sabe / No contestó";
                editPreguntac_18c.setText("");
            }
        }
    });
    editPreguntac_18c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_18c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_18c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18d = "1";
                editPreguntac_18d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_18d = "18d";
                editPreguntac_18d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_18d = "No sabe / No contestó";
                editPreguntac_18d.setText("");
            }
        }
    });
    editPreguntac_18d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_18d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_18d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_18e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_18e = "2";
                spinnerc_18e.setSelection(0);
            }
            else if (checkedId == R.id.radio2) {
                opc_18e = "18e";
                spinnerc_18e.setSelection(0);
            }
            else if (checkedId == R.id.radio0) {
                opc_18e = "No sabe / No contestó";
                spinnerc_18e.setSelection(0);
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_19.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_19 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_19 = "19";
            }
            else if (checkedId == R.id.radio3) {
                opc_19 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_19 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_19 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_20.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_20 = "0";
            }
            else if (checkedId == R.id.radio2) {
                opc_20 = "20";
            }
            else if (checkedId == R.id.radio3) {
                opc_20 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                opc_20 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                opc_20 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_21.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_21 = "1";
                editPreguntac_21.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_21 = "21";
                editPreguntac_21.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_21 = "No sabe / No contestó";
                editPreguntac_21.setText("");
            }
        }
    });
    editPreguntac_21.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_21.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_21.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_22.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_22 = "1";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_22 = "22";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio3) {
                opc_22 = "Esposo (a)/ Pareja";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio4) {
                opc_22 = "Hijo (a)";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio5) {
                opc_22 = "Padre/ Madre";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio6) {
                opc_22 = "Abuelo/ abuela";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio7) {
                opc_22 = "Tío/tía";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio8) {
                opc_22 = "Otro (registrar)";
                editPreguntac_22.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_22 = "No sabe / No contestó";
                editPreguntac_22.setText("");
            }
        }
    });
    editPreguntac_22.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_22.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_22.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntac_23.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opc_23 = "1";
                editPreguntac_23.setText("");
            }
            else if (checkedId == R.id.radio2) {
                opc_23 = "23";
                editPreguntac_23.setText("");
            }
            else if (checkedId == R.id.radio0) {
                opc_23 = "No sabe / No contestó";
                editPreguntac_23.setText("");
            }
        }
    });
    editPreguntac_23.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntac_23.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPreguntac_23.clearCheck();
            }
        }
    });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    rdPreguntaAbandono.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.radio_abandono1) {
                opAbandono = "1";
                tipoEncuesta = "NORMAL";
                btnGuardar.setText("Guardar Normal");
            } else if (checkedId == R.id.radio_abandono2) {
                opAbandono = "2";
                tipoEncuesta = "ABANDONO";
                btnGuardar.setText("Guardar Abandono");
            } else if (checkedId == R.id.radio_abandono3) {
                opAbandono = "3";
                tipoEncuesta = "RECHAZO";
                btnGuardar.setText("Guardar Rechazo");
            } else if (checkedId == R.id.radio_abandono4) {
                opAbandono = "4";
                tipoEncuesta = "FILTRO";
                btnGuardar.setText("Guardar Filtro");
            }


        }
    });


}

////// FIN ONCREATE/////////////////////////////

@Override
protected void onPause() {
    super.onPause();

}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
}

public void valores() {

    String str = "";

    String seg = formattedDate5.substring(7);
    System.out.println("El segundo: " + seg);
    System.out.println("El IMEI" + sacaImei());

    String mes = formattedDate6.toString();
    System.out.println("El mes" + mes);

    String dia = formattedDate7.toString();
    System.out.println("El dia" + dia);

    sacaChip();

    cachaNombre();

    txtSeccion.setText(cachaSeccion());

    String strSecc = txtSeccion.getText().toString();
    String strId = String.valueOf(rand + 1);

    elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

    String strTextc_1;
    if(editPreguntac_1.getText().toString().trim().length()==0){
        strTextc_1=opc_1;
    }else{
        strTextc_1=editPreguntac_1.getText().toString().trim();
        rdPreguntac_1.clearCheck();
    }

    String strTextc_2;
    if(editPreguntac_2.getText().toString().trim().length()==0){
        strTextc_2=opc_2;
    }else{
        strTextc_2=editPreguntac_2.getText().toString().trim();
        rdPreguntac_2.clearCheck();
    }


    String strTextc_3;
    if(editPreguntac_3.getText().toString().trim().length()==0){
        strTextc_3=opc_3;
    }else{
        strTextc_3=editPreguntac_3.getText().toString().trim();
        rdPreguntac_3.clearCheck();
    }

    String strTextc_4;
    if(editPreguntac_4.getText().toString().trim().length()==0){
        strTextc_4=opc_4;
    }else{
        strTextc_4=editPreguntac_4.getText().toString().trim();
        rdPreguntac_4.clearCheck();
    }

    String strTextc_7;
    if(editPreguntac_7.getText().toString().trim().length()==0){
        strTextc_7=opc_7;
    }else{
        strTextc_7=editPreguntac_7.getText().toString().trim();
        rdPreguntac_7.clearCheck();
    }

    String strTextc_8a;
    if(editPreguntac_8a.getText().toString().trim().length()==0){
        strTextc_8a=opc_8a;
    }else{
        strTextc_8a=editPreguntac_8a.getText().toString().trim();
        rdPreguntac_8a.clearCheck();
    }

    String strTextc_8b;
    if(editPreguntac_8b.getText().toString().trim().length()==0){
        strTextc_8b=opc_8b;
    }else{
        strTextc_8b=editPreguntac_8b.getText().toString().trim();
        rdPreguntac_8b.clearCheck();
    }

    String strTextc_8c;
    if(editPreguntac_8c.getText().toString().trim().length()==0){
        strTextc_8c=opc_8c;
    }else{
        strTextc_8c=editPreguntac_8c.getText().toString().trim();
        rdPreguntac_8c.clearCheck();
    }

    String strTextc_8d;
    if(editPreguntac_8d.getText().toString().trim().length()==0){
        strTextc_8d=opc_8d;
    }else{
        strTextc_8d=editPreguntac_8d.getText().toString().trim();
        rdPreguntac_8d.clearCheck();
    }

    String strTextc_8e;
    if(editPreguntac_8e.getText().toString().trim().length()==0){
        strTextc_8e=opc_8e;
    }else{
        strTextc_8e=editPreguntac_8e.getText().toString().trim();
        rdPreguntac_8e.clearCheck();
    }

    String strTextc_8f;
    if(editPreguntac_8f.getText().toString().trim().length()==0){
        strTextc_8f=opc_8f;
    }else{
        strTextc_8f=editPreguntac_8f.getText().toString().trim();
        rdPreguntac_8f.clearCheck();
    }

    String strTextc_9;
    if(editPreguntac_9.getText().toString().trim().length()==0){
        strTextc_9=opc_9;
    }else{
        strTextc_9=editPreguntac_9.getText().toString().trim();
        rdPreguntac_9.clearCheck();
    }

    String strTextc_11;
    if(editPreguntac_11.getText().toString().trim().length()==0){
        strTextc_11=opc_11;
    }else{
        strTextc_11=editPreguntac_11.getText().toString().trim();
        rdPreguntac_11.clearCheck();
    }

    String strTextc_12a;
    if(editPreguntac_12a.getText().toString().trim().length()==0){
        strTextc_12a=opc_12a;
    }else{
        strTextc_12a=editPreguntac_12a.getText().toString().trim();
        rdPreguntac_12a.clearCheck();
    }

    String strTextc_12b;
    if(editPreguntac_12b.getText().toString().trim().length()==0){
        strTextc_12b=opc_12b;
    }else{
        strTextc_12b=editPreguntac_12b.getText().toString().trim();
        rdPreguntac_12b.clearCheck();
    }

    String strTextc_12c;
    if(editPreguntac_12c.getText().toString().trim().length()==0){
        strTextc_12c=opc_12c;
    }else{
        strTextc_12c=editPreguntac_12c.getText().toString().trim();
        rdPreguntac_12c.clearCheck();
    }

    String strTextc_12d;
    if(editPreguntac_12d.getText().toString().trim().length()==0){
        strTextc_12d=opc_12d;
    }else{
        strTextc_12d=editPreguntac_12d.getText().toString().trim();
        rdPreguntac_12d.clearCheck();
    }

    String strTextc_12e;
    if(spinnerc_12e.getSelectedItem().toString().equals("Selecciona")){
        strTextc_12e=opc_12e;
    }else{
        strTextc_12e=spinnerc_12e.getSelectedItem().toString();
        rdPreguntac_12e.clearCheck();
    }

    String strTextc_16a;
    if(editPreguntac_16a.getText().toString().trim().length()==0){
        strTextc_16a=opc_16;
    }else{
        strTextc_16a=editPreguntac_16a.getText().toString().trim();
        rdPreguntac_16.clearCheck();
    }

    String strTextc_16b;
    if(editPreguntac_16b.getText().toString().trim().length()==0){
        strTextc_16b=opc_16;
    }else{
        strTextc_16b=editPreguntac_16b.getText().toString().trim();
        rdPreguntac_16.clearCheck();
    }

    String strTextc_16c;
    if(editPreguntac_16c.getText().toString().trim().length()==0){
        strTextc_16c=opc_16;
    }else{
        strTextc_16c=editPreguntac_16c.getText().toString().trim();
        rdPreguntac_16.clearCheck();
    }

    String strTextc_16d;
    if(editPreguntac_16d.getText().toString().trim().length()==0){
        strTextc_16d=opc_16;
    }else{
        strTextc_16d=editPreguntac_16d.getText().toString().trim();
        rdPreguntac_16.clearCheck();
    }

    String strTextc_18a;
    if(editPreguntac_18a.getText().toString().trim().length()==0){
        strTextc_18a=opc_18a;
    }else{
        strTextc_18a=editPreguntac_18a.getText().toString().trim();
        rdPreguntac_18a.clearCheck();
    }

    String strTextc_18b;
    if(editPreguntac_18b.getText().toString().trim().length()==0){
        strTextc_18b=opc_18b;
    }else{
        strTextc_18b=editPreguntac_18b.getText().toString().trim();
        rdPreguntac_18b.clearCheck();
    }

    String strTextc_18c;
    if(editPreguntac_18c.getText().toString().trim().length()==0){
        strTextc_18c=opc_18c;
    }else{
        strTextc_18c=editPreguntac_18c.getText().toString().trim();
        rdPreguntac_18c.clearCheck();
    }

    String strTextc_18d;
    if(editPreguntac_18d.getText().toString().trim().length()==0){
        strTextc_18d=opc_18d;
    }else{
        strTextc_18d=editPreguntac_18d.getText().toString().trim();
        rdPreguntac_18d.clearCheck();
    }

    String strTextc_18e;
    if(spinnerc_18e.getSelectedItem().toString().equals("Selecciona")){
        strTextc_18e=opc_18e;
    }else{
        strTextc_18e=spinnerc_18e.getSelectedItem().toString();
        rdPreguntac_18e.clearCheck();
    }

    String strTextc_21;
    if(editPreguntac_21.getText().toString().trim().length()==0){
        strTextc_21=opc_21;
    }else{
        strTextc_21=editPreguntac_21.getText().toString().trim();
        rdPreguntac_21.clearCheck();
    }

    String strTextc_22;
    if(editPreguntac_22.getText().toString().trim().length()==0){
        strTextc_22=opc_22;
    }else{
        strTextc_22=editPreguntac_22.getText().toString().trim();
        rdPreguntac_22.clearCheck();
    }

    String strTextc_23;
    if(editPreguntac_23.getText().toString().trim().length()==0){
        strTextc_23=opc_23;
    }else{
        strTextc_23=editPreguntac_23.getText().toString().trim();
        rdPreguntac_23.clearCheck();
    }

    String strc = opc;
    String strc_1 = strTextc_1;
    String strc_2 = strTextc_2;
    String strc_3 = strTextc_3;
    String strc_4 = strTextc_4;
    String strc_5 = opc_5;
    String strc_6 = opc_6;
    String strc_6a = opc_6a;
    String strc_6b = opc_6b;
    String strc_6c = opc_6c;
    String strc_6d = opc_6d;
    String strc_6e = opc_6e;
    String strc_6f = opc_6f;
    String strc_6g = opc_6g;
    String strc_6h = opc_6h;
    String strc_6i = opc_6i;
    String strc_6j = opc_6j;
    String strc_6k = opc_6k;
    String strc_7 = strTextc_7;
    String strc_8 = opc_8;
    String strc_8a = strTextc_8a;
    String strc_8b = strTextc_8b;
    String strc_8c = strTextc_8c;
    String strc_8d = strTextc_8d;
    String strc_8e = strTextc_8e;
    String strc_8f = strTextc_8f;
    String strc_9 = strTextc_9;
    String strc_10 = opc_10;
    String strc_11 = strTextc_11;
    String strc_12 = opc_12;
    String strc_12a = strTextc_12a;
    String strc_12b = strTextc_12b;
    String strc_12c = strTextc_12c;
    String strc_12d = strTextc_12d;
    String strc_12e = strTextc_12e;
    String strc_13 = opc_13;
    String strc_14 = opc_14;
    String strc_15 = opc_15;
    String strc_15a = opc_15a;
    String strc_15b = opc_15b;
    String strc_15c = opc_15c;
    String strc_15d = opc_15d;
    String strc_15e = opc_15e;
    String strc_15f = opc_15f;
    String strc_15g = opc_15g;
    String strc_15h = opc_15h;
    String strc_15i = opc_15i;
    String strc_15j = opc_15j;
    String strc_15k = opc_15k;
    String strc_16a = strTextc_16a;
    String strc_16b = strTextc_16b;
    String strc_16c = strTextc_16c;
    String strc_16d = strTextc_16d;
    String strc_17 = opc_17;
    String strc_17a = opc_17a;
    String strc_17b = opc_17b;
    String strc_17c = opc_17c;
    String strc_17d = opc_17d;
    String strc_17e = opc_17e;
    String strc_17f = opc_17f;
    String strc_18 = opc_18;
    String strc_18a = strTextc_18a;
    String strc_18b = strTextc_18b;
    String strc_18c = strTextc_18c;
    String strc_18d = strTextc_18d;
    String strc_18e = strTextc_18e;
    String strc_19 = opc_19;
    String strc_20 = opc_20;
    String strc_21 = strTextc_21;
    String strc_22 = strTextc_22;
    String strc_23 = strTextc_23;



    String strAbandono = opAbandono;


    if (strAbandono.matches("1")) {
        tipoEncuesta = "NORMAL";
    }



    String strFinal = "\n";

// Clase que permite grabar texto en un archivo
    FileOutputStream fout = null;
    try {
// INSERTA EN LA BASE DE DATOS //

        final String F = "File dbfile";

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
        + sacaImei() + "";

// Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getWritableDatabase();

// NORMAL
        Nombre nom = new Nombre();
        String nombreE = nom.nombreEncuesta();

        GPSTracker gps = new GPSTracker(this);
//        latitude = gps.getLatitude();
//        longitude = gps.getLongitude();

        latitude = Double.valueOf(sacaLatitud());
        longitude = Double.valueOf(sacaLongitud());

        if (latitude == 0.0) {
            if (sacaLatitud() != null) {
                latitude = Double.valueOf(sacaLatitud());
            } else {
                latitude = 0.0;
            }
        }

        if (longitude == 0.0) {
            if (sacaLongitud() != null) {
                longitude = Double.valueOf(sacaLongitud());
            } else {
                longitude = 0.0;
            }
        }

        // para sacar la dirección de reversa //

        String direccionGPS = "";

        Geocoder coder = new Geocoder(this);
        List<Address> addresses = null;
        String info = "";

        AppLog.logString("Service.updateCoordinates()");
        AppLog.logString(info);

        try {
            addresses = coder.getFromLocation(latitude, longitude, 2);

            if (null != addresses && addresses.size() > 0) {
                int addressCount = addresses.get(0).getMaxAddressLineIndex();

                if (-1 != addressCount) {
                    for (int index = 0; index <= addressCount; ++index) {
                        info += addresses.get(0).getAddressLine(index);
                        direccionGPS += addresses.get(0).getAddressLine(index);

                        if (index < addressCount)
                            direccionGPS += ", ";
                    }
                } else {
                    direccionGPS += addresses.get(0).getFeatureName() + ", " + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getAdminArea();
                }
            }

            AppLog.logString(addresses.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // finaliza para sacar la dirección de reversa //


        Log.i(TAG,"cqs ---------->> DireccionGPS: "+direccionGPS);
        String strDireccionGPS = direccionGPS;

        String strLatitud = String.valueOf(latitude);
        String strLongitud = String.valueOf(longitude);
        long consecutivoObtenido = 0;
        ContentValues values = new ContentValues();


        if (db != null) {

            values.put("id_contacto", Integer.valueOf(sacaId_contacto()));
            values.put("consecutivo_diario", elMaximo);
            values.put("equipo", cachaEquipo().toUpperCase());
            values.put("usuario", cachaNombre().toUpperCase());
            values.put("nombre_encuesta", nombreE.toUpperCase());
            values.put("fecha", formattedDate1);
            values.put("hora", formattedDate5);
            values.put("imei", sacaImei());
            values.put("seccion", strSecc);
            values.put("latitud", strLatitud);
            values.put("longitud", strLongitud);

            values.put("pregunta_c",strc);
            values.put("pregunta_c_1",strc_1);
            values.put("pregunta_c_2",strc_2);
            values.put("pregunta_c_3",strc_3);
            values.put("pregunta_c_4",strc_4);
            values.put("pregunta_c_5",strc_5);
            values.put("pregunta_c_6",strc_6);
            values.put("pregunta_c_6a",strc_6a);
            values.put("pregunta_c_6b",strc_6b);
            values.put("pregunta_c_6c",strc_6c);
            values.put("pregunta_c_6d",strc_6d);
            values.put("pregunta_c_6e",strc_6e);
            values.put("pregunta_c_6f",strc_6f);
            values.put("pregunta_c_6g",strc_6g);
            values.put("pregunta_c_6h",strc_6h);
            values.put("pregunta_c_6i",strc_6i);
            values.put("pregunta_c_6j",strc_6j);
            values.put("pregunta_c_6k",strc_6k);
            values.put("pregunta_c_7",strc_7);
            values.put("pregunta_c_8",strc_8);
            values.put("pregunta_c_8a",strc_8a);
            values.put("pregunta_c_8b",strc_8b);
            values.put("pregunta_c_8c",strc_8c);
            values.put("pregunta_c_8d",strc_8d);
            values.put("pregunta_c_8e",strc_8e);
            values.put("pregunta_c_8f",strc_8f);
            values.put("pregunta_c_9",strc_9);
            values.put("pregunta_c_10",strc_10);
            values.put("pregunta_c_11",strc_11);
            values.put("pregunta_c_12",strc_12);
            values.put("pregunta_c_12a",strc_12a);
            values.put("pregunta_c_12b",strc_12b);
            values.put("pregunta_c_12c",strc_12c);
            values.put("pregunta_c_12d",strc_12d);
            values.put("pregunta_c_12e",strc_12e);
            values.put("pregunta_c_13",strc_13);
            values.put("pregunta_c_14",strc_14);
            values.put("pregunta_c_15",strc_15);
            values.put("pregunta_c_15a",strc_15a);
            values.put("pregunta_c_15b",strc_15b);
            values.put("pregunta_c_15c",strc_15c);
            values.put("pregunta_c_15d",strc_15d);
            values.put("pregunta_c_15e",strc_15e);
            values.put("pregunta_c_15f",strc_15f);
            values.put("pregunta_c_15g",strc_15g);
            values.put("pregunta_c_15h",strc_15h);
            values.put("pregunta_c_15i",strc_15i);
            values.put("pregunta_c_15j",strc_15j);
            values.put("pregunta_c_15k",strc_15k);
            values.put("pregunta_c_16a",strc_16a);
            values.put("pregunta_c_16b",strc_16b);
            values.put("pregunta_c_16c",strc_16c);
            values.put("pregunta_c_16d",strc_16d);
            values.put("pregunta_c_17",strc_17);
            values.put("pregunta_c_17a",strc_17a);
            values.put("pregunta_c_17b",strc_17b);
            values.put("pregunta_c_17c",strc_17c);
            values.put("pregunta_c_17d",strc_17d);
            values.put("pregunta_c_17e",strc_17e);
            values.put("pregunta_c_17f",strc_17f);
            values.put("pregunta_c_18",strc_18);
            values.put("pregunta_c_18a",strc_18a);
            values.put("pregunta_c_18b",strc_18b);
            values.put("pregunta_c_18c",strc_18c);
            values.put("pregunta_c_18d",strc_18d);
            values.put("pregunta_c_18e",strc_18e);
            values.put("pregunta_c_19",strc_19);
            values.put("pregunta_c_20",strc_20);
            values.put("pregunta_c_21",strc_21);
            values.put("pregunta_c_22",strc_22);
            values.put("pregunta_c_23",strc_23);


            values.put("abandono", strAbandono.toUpperCase());
            values.put("tiempo", elTiempo());
            values.put("tipo_captura", tipoEncuesta);


            if (!verificaConexion(this)) {
                Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                values.put("enviado", "0");
                db.insert("adicionales", null, values);
            }else{
                values.put("enviado", "1");
                consecutivoObtenido = db.insert("adicionales", null, values);
            }
        }
        db.close();

        values.put("consecutivo", consecutivoObtenido);

        guardaEncuestaWS(values);

        System.out.println("id_contacto:  " + sacaChip());
        System.out.println("consecutivo_diario " + elMaximo);
        System.out.println("usuario " + cachaNombre().toUpperCase());
        System.out.println("nombre_encuesta " + nombreE.toUpperCase());
        System.out.println("fecha " + formattedDate1);
        System.out.println("hora " + formattedDate5);
        System.out.println("imei " + sacaImei());
        System.out.println("Seccion " + str);
        System.out.println("Latitud  " + strLatitud);
        System.out.println("Longitud  " + strLongitud);

        System.out.println("pregunta_c  " +   strc);
        System.out.println("pregunta_c_1  " +   strc_1);
        System.out.println("pregunta_c_2  " +   strc_2);
        System.out.println("pregunta_c_3  " +   strc_3);
        System.out.println("pregunta_c_4  " +   strc_4);
        System.out.println("pregunta_c_5  " +   strc_5);
        System.out.println("pregunta_c_6  " +   strc_6);
        System.out.println("pregunta_c_6a  " +   strc_6a);
        System.out.println("pregunta_c_6b  " +   strc_6b);
        System.out.println("pregunta_c_6c  " +   strc_6c);
        System.out.println("pregunta_c_6d  " +   strc_6d);
        System.out.println("pregunta_c_6e  " +   strc_6e);
        System.out.println("pregunta_c_6f  " +   strc_6f);
        System.out.println("pregunta_c_6g  " +   strc_6g);
        System.out.println("pregunta_c_6h  " +   strc_6h);
        System.out.println("pregunta_c_6i  " +   strc_6i);
        System.out.println("pregunta_c_6j  " +   strc_6j);
        System.out.println("pregunta_c_6k  " +   strc_6k);
        System.out.println("pregunta_c_7  " +   strc_7);
        System.out.println("pregunta_c_8  " +   strc_8);
        System.out.println("pregunta_c_8a  " +   strc_8a);
        System.out.println("pregunta_c_8b  " +   strc_8b);
        System.out.println("pregunta_c_8c  " +   strc_8c);
        System.out.println("pregunta_c_8d  " +   strc_8d);
        System.out.println("pregunta_c_8e  " +   strc_8e);
        System.out.println("pregunta_c_8f  " +   strc_8f);
        System.out.println("pregunta_c_9  " +   strc_9);
        System.out.println("pregunta_c_10  " +   strc_10);
        System.out.println("pregunta_c_11  " +   strc_11);
        System.out.println("pregunta_c_12  " +   strc_12);
        System.out.println("pregunta_c_12a  " +   strc_12a);
        System.out.println("pregunta_c_12b  " +   strc_12b);
        System.out.println("pregunta_c_12c  " +   strc_12c);
        System.out.println("pregunta_c_12d  " +   strc_12d);
        System.out.println("pregunta_c_12e  " +   strc_12e);
        System.out.println("pregunta_c_13  " +   strc_13);
        System.out.println("pregunta_c_14  " +   strc_14);
        System.out.println("pregunta_c_15  " +   strc_15);
        System.out.println("pregunta_c_15a  " +   strc_15a);
        System.out.println("pregunta_c_15b  " +   strc_15b);
        System.out.println("pregunta_c_15c  " +   strc_15c);
        System.out.println("pregunta_c_15d  " +   strc_15d);
        System.out.println("pregunta_c_15e  " +   strc_15e);
        System.out.println("pregunta_c_15f  " +   strc_15f);
        System.out.println("pregunta_c_15g  " +   strc_15g);
        System.out.println("pregunta_c_15h  " +   strc_15h);
        System.out.println("pregunta_c_15i  " +   strc_15i);
        System.out.println("pregunta_c_15j  " +   strc_15j);
        System.out.println("pregunta_c_15k  " +   strc_15k);
        System.out.println("pregunta_c_16a  " +   strc_16a);
        System.out.println("pregunta_c_16b  " +   strc_16b);
        System.out.println("pregunta_c_16c  " +   strc_16c);
        System.out.println("pregunta_c_16d  " +   strc_16d);
        System.out.println("pregunta_c_17  " +   strc_17);
        System.out.println("pregunta_c_17a  " +   strc_17a);
        System.out.println("pregunta_c_17b  " +   strc_17b);
        System.out.println("pregunta_c_17c  " +   strc_17c);
        System.out.println("pregunta_c_17d  " +   strc_17d);
        System.out.println("pregunta_c_17e  " +   strc_17e);
        System.out.println("pregunta_c_17f  " +   strc_17f);
        System.out.println("pregunta_c_18  " +   strc_18);
        System.out.println("pregunta_c_18a  " +   strc_18a);
        System.out.println("pregunta_c_18b  " +   strc_18b);
        System.out.println("pregunta_c_18c  " +   strc_18c);
        System.out.println("pregunta_c_18d  " +   strc_18d);
        System.out.println("pregunta_c_18e  " +   strc_18e);
        System.out.println("pregunta_c_19  " +   strc_19);
        System.out.println("pregunta_c_20  " +   strc_20);
        System.out.println("pregunta_c_21  " +   strc_21);
        System.out.println("pregunta_c_22  " +   strc_22);
        System.out.println("pregunta_c_23  " +   strc_23);



        System.out.println("abandono  " + strAbandono);


// FIN INSERTA BASE DE DATOS //

    } catch (Exception e) {
        String stackTrace = Log.getStackTraceString(e);
        Log.i(TAG,"Error Inserta Base"+ stackTrace);
    }

}

private void guardaEncuestaWS(ContentValues values){

    showProgress(true);

//RECORRE CONTENTVALUES
    DatoContent datoContent = new DatoContent();
    List<DatoContent> listaContenido = new ArrayList();
    Set<Map.Entry<String, Object>> s=values.valueSet();
    Iterator itr = s.iterator();
    while(itr.hasNext()) {
        Map.Entry me = (Map.Entry)itr.next();
        String key = me.getKey().toString();
        Object value =  me.getValue();

        datoContent = new DatoContent();
        datoContent.setKey(key);
        datoContent.setValue(String.valueOf(value));

        listaContenido.add(datoContent);
    }

    Gson gson  = new Gson();
    Type collectionType = new TypeToken<List<DatoContent>>() { }.getType();
    String json = gson.toJson(listaContenido,collectionType);

    RequestParams params = new RequestParams();
    params.put("api", "guarda_encuesta");
    params.put("encuesta", adicionales);
    params.put("data", json);

    Log.d(TAG, "pimc -----------> " + json);

    AsyncHttpClient client = new AsyncHttpClient();
    client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
//client.addHeader("Authorization", "Bearer " + usuario.getToken());
    client.setTimeout(60000);

    RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            showProgress(false);
            Log.d(TAG, "pimc -----------> Respuesta OK ");
            Log.d(TAG, "pimc -----------> " + new String(responseBody));
            try {


                String json = new String(responseBody);

                if (json != null && !json.isEmpty()) {

                    Gson gson = new Gson();
                    JSONObject jsonObject = new JSONObject(json);
                    Log.d(TAG, "pimc -----------> Data: " + jsonObject.get("data"));

                    String login = jsonObject.getJSONObject("response").get("code").toString();
                    if (Integer.valueOf(login) == 1) {

/*JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("respuesta");
Type collectionType = new TypeToken<Usuario>() {}.getType();
usuario = gson.fromJson(jsonUser.toString(), collectionType);*/
//
//if(!opAbandono.equals("5")){
//    dialogo();
//}
/*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
intent.putExtra("Nombre", encuestaQuien);
startActivity(intent);
finish();*/

                        dialogo();


} else {
    Toast.makeText(MainActivityPantalla2.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
}
}

} catch (Exception e) {
    showProgress(false);
    Log.e(TAG, e.getMessage());
    Toast.makeText(MainActivityPantalla2.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
}
}

@Override
public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    showProgress(false);
    try {
        Log.e(TAG, "PIMC-----------------> existe un error en la conexi?n -----> " + error.getMessage());
        if (responseBody != null)
            Log.d(TAG, "pimc -----------> " + new String(responseBody));

    } catch (Exception ex) {
        ex.printStackTrace();
    }

    if (statusCode != 200) {
        Log.e(TAG, "Existe un error en la conexi?n -----> " + error.getMessage());
        if (responseBody != null)
            Log.d(TAG, "pimc -----------> " + new String(responseBody));

    }

    Toast.makeText(MainActivityPantalla2.this, "Error de conexion, Se guarda en la base interna", Toast.LENGTH_SHORT).show();
    btnGuardar.setEnabled(true);

    dialogo();

}
});


}

public void guardar(View v) {
    System.out.println(cachaDelegacion());

    timer.cancel();

    String str = "";

    if (opAbandono.matches("sin datos")) {
        opAbandono = "1";
    }

    int tipo = Integer.parseInt(opAbandono);

    switch (tipo) {
        case 1:


            if (layc.getVisibility() == View.VISIBLE && opc.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac,Toast.LENGTH_LONG).show();}
            else if (layc_1.getVisibility() == View.VISIBLE && opc_1.matches("sin datos") && editPreguntac_1.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_1,Toast.LENGTH_LONG).show();}
            else if (layc_2.getVisibility() == View.VISIBLE && opc_2.matches("sin datos") && editPreguntac_2.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_2,Toast.LENGTH_LONG).show();}
            else if (layc_3.getVisibility() == View.VISIBLE && opc_3.matches("sin datos") && editPreguntac_3.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_3,Toast.LENGTH_LONG).show();}
            else if (layc_4.getVisibility() == View.VISIBLE && opc_4.matches("sin datos") && editPreguntac_4.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_4,Toast.LENGTH_LONG).show();}
            else if (layc_5.getVisibility() == View.VISIBLE && opc_5.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_5,Toast.LENGTH_LONG).show();}
//            else if (layc_6.getVisibility() == View.VISIBLE && opc_6.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6,Toast.LENGTH_LONG).show();}
            else if (layc_6a.getVisibility() == View.VISIBLE && opc_6a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6a,Toast.LENGTH_LONG).show();}
            else if (layc_6b.getVisibility() == View.VISIBLE && opc_6b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6b,Toast.LENGTH_LONG).show();}
            else if (layc_6c.getVisibility() == View.VISIBLE && opc_6c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6c,Toast.LENGTH_LONG).show();}
            else if (layc_6d.getVisibility() == View.VISIBLE && opc_6d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6d,Toast.LENGTH_LONG).show();}
            else if (layc_6e.getVisibility() == View.VISIBLE && opc_6e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6e,Toast.LENGTH_LONG).show();}
            else if (layc_6f.getVisibility() == View.VISIBLE && opc_6f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6f,Toast.LENGTH_LONG).show();}
            else if (layc_6g.getVisibility() == View.VISIBLE && opc_6g.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6g,Toast.LENGTH_LONG).show();}
            else if (layc_6h.getVisibility() == View.VISIBLE && opc_6h.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6h,Toast.LENGTH_LONG).show();}
            else if (layc_6i.getVisibility() == View.VISIBLE && opc_6i.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6i,Toast.LENGTH_LONG).show();}
            else if (layc_6j.getVisibility() == View.VISIBLE && opc_6j.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6j,Toast.LENGTH_LONG).show();}
            else if (layc_6k.getVisibility() == View.VISIBLE && opc_6k.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_6k,Toast.LENGTH_LONG).show();}
            else if (layc_7.getVisibility() == View.VISIBLE && opc_7.matches("sin datos") && editPreguntac_7.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_7,Toast.LENGTH_LONG).show();}
//            else if (layc_8.getVisibility() == View.VISIBLE && opc_8.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8,Toast.LENGTH_LONG).show();}
            else if (layc_8a.getVisibility() == View.VISIBLE && opc_8a.matches("sin datos") && editPreguntac_8a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8a,Toast.LENGTH_LONG).show();}
            else if (layc_8b.getVisibility() == View.VISIBLE && opc_8b.matches("sin datos") && editPreguntac_8b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8b,Toast.LENGTH_LONG).show();}
            else if (layc_8c.getVisibility() == View.VISIBLE && opc_8c.matches("sin datos") && editPreguntac_8c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8c,Toast.LENGTH_LONG).show();}
            else if (layc_8d.getVisibility() == View.VISIBLE && opc_8d.matches("sin datos") && editPreguntac_8d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8d,Toast.LENGTH_LONG).show();}
            else if (layc_8e.getVisibility() == View.VISIBLE && opc_8e.matches("sin datos") && editPreguntac_8e.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8e,Toast.LENGTH_LONG).show();}
            else if (layc_8f.getVisibility() == View.VISIBLE && opc_8f.matches("sin datos") && editPreguntac_8f.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_8f,Toast.LENGTH_LONG).show();}
            else if (layc_9.getVisibility() == View.VISIBLE && opc_9.matches("sin datos") && editPreguntac_9.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_9,Toast.LENGTH_LONG).show();}
            else if (layc_10.getVisibility() == View.VISIBLE && opc_10.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_10,Toast.LENGTH_LONG).show();}
            else if (layc_11.getVisibility() == View.VISIBLE && opc_11.matches("sin datos") && editPreguntac_11.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_11,Toast.LENGTH_LONG).show();}
//            else if (layc_12.getVisibility() == View.VISIBLE && opc_12.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12,Toast.LENGTH_LONG).show();}
            else if (layc_12a.getVisibility() == View.VISIBLE && opc_12a.matches("sin datos") && editPreguntac_12a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12a,Toast.LENGTH_LONG).show();}
            else if (layc_12b.getVisibility() == View.VISIBLE && opc_12b.matches("sin datos") && editPreguntac_12b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12b,Toast.LENGTH_LONG).show();}
            else if (layc_12c.getVisibility() == View.VISIBLE && opc_12c.matches("sin datos") && editPreguntac_12c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12c,Toast.LENGTH_LONG).show();}
            else if (layc_12d.getVisibility() == View.VISIBLE && opc_12d.matches("sin datos") && editPreguntac_12d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12d,Toast.LENGTH_LONG).show();}
            else if (layc_12e.getVisibility() == View.VISIBLE && opc_12e.matches("sin datos") && spinnerc_12e.getSelectedItem().toString().equals("Selecciona") ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_12e,Toast.LENGTH_LONG).show();}
            else if (layc_13.getVisibility() == View.VISIBLE && opc_13.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_13,Toast.LENGTH_LONG).show();}
            else if (layc_14.getVisibility() == View.VISIBLE && opc_14.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_14,Toast.LENGTH_LONG).show();}
//            else if (layc_15.getVisibility() == View.VISIBLE && opc_15.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15,Toast.LENGTH_LONG).show();}
            else if (layc_15a.getVisibility() == View.VISIBLE && opc_15a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15a,Toast.LENGTH_LONG).show();}
            else if (layc_15b.getVisibility() == View.VISIBLE && opc_15b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15b,Toast.LENGTH_LONG).show();}
            else if (layc_15c.getVisibility() == View.VISIBLE && opc_15c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15c,Toast.LENGTH_LONG).show();}
            else if (layc_15d.getVisibility() == View.VISIBLE && opc_15d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15d,Toast.LENGTH_LONG).show();}
            else if (layc_15e.getVisibility() == View.VISIBLE && opc_15e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15e,Toast.LENGTH_LONG).show();}
            else if (layc_15f.getVisibility() == View.VISIBLE && opc_15f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15f,Toast.LENGTH_LONG).show();}
            else if (layc_15g.getVisibility() == View.VISIBLE && opc_15g.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15g,Toast.LENGTH_LONG).show();}
            else if (layc_15h.getVisibility() == View.VISIBLE && opc_15h.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15h,Toast.LENGTH_LONG).show();}
            else if (layc_15i.getVisibility() == View.VISIBLE && opc_15i.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15i,Toast.LENGTH_LONG).show();}
            else if (layc_15j.getVisibility() == View.VISIBLE && opc_15j.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15j,Toast.LENGTH_LONG).show();}
            else if (layc_15k.getVisibility() == View.VISIBLE && opc_15k.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_15k,Toast.LENGTH_LONG).show();}
            else if (layc_16.getVisibility() == View.VISIBLE && opc_16.matches("sin datos")
                    && editPreguntac_16a.getText().toString().trim().length() == 0
                    && editPreguntac_16b.getText().toString().trim().length() == 0
                    && editPreguntac_16c.getText().toString().trim().length() == 0
                    && editPreguntac_16d.getText().toString().trim().length() == 0
            ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_16,Toast.LENGTH_LONG).show();}
//            else if (layc_17.getVisibility() == View.VISIBLE && opc_17.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17,Toast.LENGTH_LONG).show();}
            else if (layc_17a.getVisibility() == View.VISIBLE && opc_17a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17a,Toast.LENGTH_LONG).show();}
            else if (layc_17b.getVisibility() == View.VISIBLE && opc_17b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17b,Toast.LENGTH_LONG).show();}
            else if (layc_17c.getVisibility() == View.VISIBLE && opc_17c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17c,Toast.LENGTH_LONG).show();}
            else if (layc_17d.getVisibility() == View.VISIBLE && opc_17d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17d,Toast.LENGTH_LONG).show();}
            else if (layc_17e.getVisibility() == View.VISIBLE && opc_17e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17e,Toast.LENGTH_LONG).show();}
            else if (layc_17f.getVisibility() == View.VISIBLE && opc_17f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_17f,Toast.LENGTH_LONG).show();}
//            else if (layc_18.getVisibility() == View.VISIBLE && opc_18.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18,Toast.LENGTH_LONG).show();}
            else if (layc_18a.getVisibility() == View.VISIBLE && opc_18a.matches("sin datos") && editPreguntac_18a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18a,Toast.LENGTH_LONG).show();}
            else if (layc_18b.getVisibility() == View.VISIBLE && opc_18b.matches("sin datos") && editPreguntac_18b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18b,Toast.LENGTH_LONG).show();}
            else if (layc_18c.getVisibility() == View.VISIBLE && opc_18c.matches("sin datos") && editPreguntac_18c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18c,Toast.LENGTH_LONG).show();}
            else if (layc_18d.getVisibility() == View.VISIBLE && opc_18d.matches("sin datos") && editPreguntac_18d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18d,Toast.LENGTH_LONG).show();}
            else if (layc_18e.getVisibility() == View.VISIBLE && opc_18e.matches("sin datos") && spinnerc_18e.getSelectedItem().toString().equals("Selecciona") ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_18e,Toast.LENGTH_LONG).show();}
            else if (layc_19.getVisibility() == View.VISIBLE && opc_19.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_19,Toast.LENGTH_LONG).show();}
            else if (layc_20.getVisibility() == View.VISIBLE && opc_20.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_20,Toast.LENGTH_LONG).show();}
            else if (layc_21.getVisibility() == View.VISIBLE && opc_21.matches("sin datos") && editPreguntac_21.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_21,Toast.LENGTH_LONG).show();}
            else if (layc_22.getVisibility() == View.VISIBLE && opc_22.matches("sin datos") && editPreguntac_22.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_22,Toast.LENGTH_LONG).show();}
            else if (layc_23.getVisibility() == View.VISIBLE && opc_23.matches("sin datos") && editPreguntac_23.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac_23,Toast.LENGTH_LONG).show();}

 else {

// para valor por default
            if (opAbandono.matches("sin datos")) {
                opAbandono = "1";
            }

            valores();
            btnGuardar.setEnabled(false);
//            dialogo();

            } // Finaliza else
            break;

            case 2:

                if (opc_5.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac_5, Toast.LENGTH_LONG).show();
            }
            else {
                valores();
                btnGuardar.setEnabled(false);
//                dialogo();
            }

            break;

            case 3:

            if (opc_5.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturac_5, Toast.LENGTH_LONG).show();
            }
            else {
                valores();
                btnGuardar.setEnabled(false);
//                dialogo();
            }
            break;
            }

}

public void Salir(View view) {
    finish();
}

private String sacaMaximo() {

    Set<String> set = new HashSet<String>();

    final String F = "File dbfile";

// Abrimos la base de datos 'DBUsuarios' en modo escritura
    String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"+ sacaImei() + "";
    usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

    db = usdbh.getReadableDatabase();

    String selectQuery = "SELECT count(*) FROM adicionales where fecha='" + formattedDate1 + "'";

    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
        do {

            maximo = cursor.getString(0);

        } while (cursor.moveToNext());
    }

    cursor.close();
//    db.close();

    return maximo;
}


    private String sacaId_contacto() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";
        String id_contacto = null;

// Abrimos la base de datos 'DBUsuarios' en modo escritura
        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"+ sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);
        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM contactos";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                id_contacto = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
//        db.close();

        return id_contacto;
    }

private String sacaConsecutivo() {

    String consecutivo = null;

    Set<String> set = new HashSet<String>();

    final String F = "File dbfile";

// Abrimos la base de datos 'DBUsuarios' en modo escritura

    String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
    + sacaImei() + "";
    usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

    db = usdbh.getReadableDatabase();

    String selectQuery = "SELECT count(*) FROM adicionales order by id desc limit 1";

    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
        do {

            consecutivo = cursor.getString(0);

        } while (cursor.moveToNext());
    }

    cursor.close();
//    db.close();

    return consecutivo;
}

//	public void CargaSpinnerMeses() {
//		String var = "Selecciona";
//		if (var == null) {
//			var = "";
//		}
//		final String[] datos = new String[] { "" + var + "",
//				"Enero 2019",
//				"Febrero 2019",
//				"Marzo 2019",
//				"Abril 2019",
//				"Mayo 2019",
//				"Junio 2019",
//				"Julio 2019",
//				"Agosto 2019",
//				"Septiembre 2019",
//				"Octubre 2019",
//				"Noviembre 2019",
//				"Diciembre 2019",
//				"Enero 2020",
//				"Febrero 20"
//		};
//		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinnerMeses.setAdapter(adaptador);
//		spinnerMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//				rdPregunta42.clearCheck();
//
//			}
//
//			public void onNothingSelected(AdapterView<?> parent) {
//			}
//		});
//	}

public void CargaSpinnerEscala() {
    String var = "Selecciona";
    if (var == null) {
        var = "";
    }
    final String[] datos = new String[]{"" + var + "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
    "No sabe / No contestó"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
        datos);
    adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
    spinnerCalifica.setAdapter(adaptador);
    spinnerCalifica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}





public void CargaSpinnerSemana() {
    String var = "Selecciona";
    if (var == null) {
        var = "";
    }
    final String[] datos = new String[]{"" + var + "", "Dictan sentencia de cadena perpetua al Chapo en EU",
    "Se presenta plan de acción para rescatar PEMEX", "Se harán subastas de joyas incautadas",
    "Derrame de ácido en el Mar de Cortés",
    "Detienen a presuntos culpables del asesinato de Norberto Ronquillo",
    "Asesinatos/ muertos/ secuestros sin especificar", "Robos/ asaltos/ inseguridad sin especificar"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item,
        datos);
    adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
    spinnerSemana.setAdapter(adaptador);
    spinnerSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

//				rdPregunta10.clearCheck();
//				editPregunta10.setText("");

        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}

/////////////// SPINNER /////////////////
public void CargaSpinnerAlcaldiac_12e() {
    String var = "Selecciona";
    if (var == null) {
        var = "";
    }
    final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
    "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
    "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
    "XOCHIMILCO", "No sabe / No contestó"};
    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinnerc_12e.setAdapter(adaptador);
    spinnerc_12e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}

    public void CargaSpinnerAlcaldiac_18e() {
        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
                "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
                "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
                "XOCHIMILCO", "No sabe / No contestó"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerc_18e.setAdapter(adaptador);
        spinnerc_18e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


//public void CargaSpinner9() {
//    String var = "Selecciona";
//    if (var == null) {
//        var = "";
//    }
//    final String[] datos = new String[]{"" + var + "",
//    "1", "2", "3",
//    "4", "5", "6", "7", "8", "9",
//    "10"
//};
//ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//spinner9.setAdapter(adaptador);
//spinner9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//    public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//        rdPregunta9.clearCheck();
//        op9 = "sin datos";
//    }
//
//    public void onNothingSelected(AdapterView<?> parent) {
//    }
//});
//}

//
//public void CargaSpinner0() {
//    String var = "Selecciona";
//    if (var == null) {
//        var = "";
//    }
//    final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
//    "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
//    "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
//    "XOCHIMILCO"};
//    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//    spinner0.setAdapter(adaptador);
//    spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//
//            rdPregunta0.clearCheck();
//            op0 = "sin datos";
//
//            radio_abandono1.setVisibility(View.VISIBLE);
//            radio_abandono2.setVisibility(View.VISIBLE);
//            radio_abandono3.setVisibility(View.VISIBLE);
//            radio_abandono4.setVisibility(View.GONE);
//            radio_abandono4.setChecked(false);
//            radio_abandono1.setChecked(true);
//
//            lay1 .setVisibility(View.VISIBLE);
//            lay2 .setVisibility(View.VISIBLE);
//            lay3 .setVisibility(View.VISIBLE);
//            lay4 .setVisibility(View.VISIBLE);
//            lay5 .setVisibility(View.VISIBLE);
//            lay6 .setVisibility(View.VISIBLE);
//            lay7 .setVisibility(View.VISIBLE);
//            layc1 .setVisibility(View.VISIBLE);
//            layc2 .setVisibility(View.VISIBLE);
//            layc3 .setVisibility(View.VISIBLE);
//            layc4 .setVisibility(View.VISIBLE);
//            layc5 .setVisibility(View.VISIBLE);
//            layc6 .setVisibility(View.VISIBLE);
//            layc6a .setVisibility(View.VISIBLE);
//            layc6b .setVisibility(View.VISIBLE);
//            layc7 .setVisibility(View.VISIBLE);
//            layc7a .setVisibility(View.VISIBLE);
//            layc8 .setVisibility(View.VISIBLE);
//            layc8a .setVisibility(View.VISIBLE);
//            layc9 .setVisibility(View.VISIBLE);
//            layc9a .setVisibility(View.VISIBLE);
//            layc10 .setVisibility(View.VISIBLE);
//            layc10a .setVisibility(View.VISIBLE);
//            layc11 .setVisibility(View.VISIBLE);
//            layc11a .setVisibility(View.VISIBLE);
//            layc12 .setVisibility(View.VISIBLE);
//            layc12a .setVisibility(View.VISIBLE);
//            layc13 .setVisibility(View.VISIBLE);
//            layc13a .setVisibility(View.VISIBLE);
//
//            laySocioE.setVisibility(View.VISIBLE);
//            layEst.setVisibility(View.VISIBLE);
//            layAporta.setVisibility(View.VISIBLE);
//            layOcupacion.setVisibility(View.VISIBLE);
//            layCuantosCoches.setVisibility(View.VISIBLE);
//            layCuartos.setVisibility(View.VISIBLE);
//            layCuartosDormir.setVisibility(View.VISIBLE);
//            layFocos.setVisibility(View.VISIBLE);
//            layBanos.setVisibility(View.VISIBLE);
//            layRegadera.setVisibility(View.VISIBLE);
//            layEstufa.setVisibility(View.VISIBLE);
//            layEdad.setVisibility(View.VISIBLE);
//            layTipoPiso.setVisibility(View.VISIBLE);
//            layTipoVivienda.setVisibility(View.VISIBLE);
//            layGenero.setVisibility(View.VISIBLE);
//
//        }
//
//        public void onNothingSelected(AdapterView<?> parent) {
//        }
//    });
//}

//	public void CargaSpinner63() {
//		String var = "Selecciona";
//		if (var == null) {
//			var = "";
//		}
//		final String[] datos = new String[] { "" + var + "", "ÁLVARO OBREGÓN", "AZCAPOTZALCO", "BENITO JUÁREZ",
//				"COYOACÁN", "CUAJIMALPA DE MORELOS", "CUAUHTÉMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
//				"MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLÁHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
//				"XOCHIMILCO" };
//		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner63.setAdapter(adaptador);
//		spinner63.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//
//				rdPregunta63.clearCheck();
//
//			}
//
//			public void onNothingSelected(AdapterView<?> parent) {
//			}
//		});
//	}

///////////// FIN SPINNER /////////////

private String sacaDelegacion(String seccion) {
    Set<String> set = new HashSet<String>();
    final String F = "File dbfile";
    String Dele = "";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
    usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
    db2 = usdbh2.getReadableDatabase();
    String selectQuery = "SELECT delegacion FROM datos where seccion='" + seccion + "'";
    Cursor cursor = db2.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            Dele = cursor.getString(0);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db2.close();
    return Dele;
}

private String sacaLatitud() {
    Set<String> set = new HashSet<String>();
    String acceso = null;
    final String F = "File dbfile";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
    usdbh3 = new UsuariosSQLiteHelper3(this);
    db3 = usdbh3.getReadableDatabase();
    String selectQuery = "select latitud from ubicacion order by id desc limit 1";
    Cursor cursor = db3.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            acceso = cursor.getString(0);
        } while (cursor.moveToNext());
    }
    cursor.close();
//    db3.close();

    return acceso;
}

private String sacaLongitud() {
    Set<String> set = new HashSet<String>();
    String acceso = null;
    final String F = "File dbfile";
// Abrimos la base de datos 'DBUsuarios' en modo escritura
    usdbh3 = new UsuariosSQLiteHelper3(this);
    db3 = usdbh3.getReadableDatabase();
    String selectQuery = "select longitud from ubicacion order by id desc limit 1";
    Cursor cursor = db3.rawQuery(selectQuery, null);
    if (cursor.moveToFirst()) {
        do {
            acceso = cursor.getString(0);
        } while (cursor.moveToNext());
    }
    cursor.close();
//    db3.close();

    return acceso;
}

public void grabar() {
    try {
// sacaMaximo();
        String pathAudio = "/mnt/sdcard/Audio1" + formattedDate3 + "";

        Nombre nom = new Nombre();
        String nombreEncuesta = nom.nombreEncuesta();

        File sdCard = null, directory, file = null;
        if (Environment.getExternalStorageState().equals("mounted")) {
// Obtenemos el directorio de la memoria externa
            sdCard = Environment.getExternalStorageDirectory();
        }
        directory = new File(sdCard.getAbsolutePath() + "/" + nombreEncuesta + "-Audio" + formattedDate3 + "");
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(
            "/mnt/sdcard/" + nombreEncuesta + "-Audio" + formattedDate3 + "/" + nombreAudio() + "");

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG, String.valueOf("Fallo en grabacion: " + e.getMessage()));
        }
    } catch (Exception e) {
        String stackTrace = Log.getStackTraceString(e);
        Log.i(TAG, "Fallo en grabando" + stackTrace);
    }

}

public void detenerGrabacion() {
    Thread thread = new Thread() {
        public void run() {
            if (recorder != null) {

                try {
                    Log.i(TAG, String.valueOf("Grabadora detenida correctamente "));
                    recorder.stop();
recorder.reset(); // You can reuse the object by going back to
// setAudioSource() step
recorder.release();

} catch (Exception e) {
    String stackTrace = Log.getStackTraceString(e);
    Log.i("Manda Audios", "Al detener grabacion" + stackTrace);
}

}
}
};
thread.start();
}

    /////// METODO PARA VERIFICAR LA CONEXIÓN A INTERNET
    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
private void showProgress(final boolean show) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
            show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
