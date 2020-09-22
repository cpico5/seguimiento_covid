package mx.gob.cdmx.seguimientocovid;

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

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.seguimientocovid.model.DatoContent;
import mx.gob.cdmx.seguimientocovid.model.Usuario;
import mx.gob.cdmx.seguimientocovid.service.AppLog;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.seguimientocovid.Nombre.USUARIO;
import static mx.gob.cdmx.seguimientocovid.Nombre.customURL;
import static mx.gob.cdmx.seguimientocovid.Nombre.encuesta;
import static mx.gob.cdmx.seguimientocovid.Nombre.contactos;
import static mx.gob.cdmx.seguimientocovid.Nombre.adicionales;

public class MainActivityPantalla1 extends Activity {

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

    Random random = new java.util.Random();
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
    private Spinner spinner13e;
    private Spinner spinner19e;
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


    public String opA="sin datos";	public RadioGroup rdPreguntaA;	public EditText editPreguntaA;	public String capturaA;	LinearLayout layA;
    public String opB="sin datos";	public RadioGroup rdPreguntaB;	public EditText editPreguntaB;	public String capturaB;	LinearLayout layB;
    public String op1="sin datos";	public RadioGroup rdPregunta1;	public EditText editPregunta1;	public String captura1;	LinearLayout lay1;
    public String op2="sin datos";	public RadioGroup rdPregunta2;	public EditText editPregunta2;	public String captura2;	LinearLayout lay2;
    public String op3="sin datos";	public RadioGroup rdPregunta3;	public EditText editPregunta3;	public String captura3;	LinearLayout lay3;
    public String op4="sin datos";	public RadioGroup rdPregunta4;	public EditText editPregunta4;	public String captura4;	LinearLayout lay4;
    public String op5="sin datos";	public RadioGroup rdPregunta5;	public EditText editPregunta5;	public String captura5;	LinearLayout lay5;
    public String op6="sin datos";	public RadioGroup rdPregunta6;	public EditText editPregunta6;	public String captura6;	LinearLayout lay6;
    public String op6a="sin datos";	public RadioGroup rdPregunta6a;	public EditText editPregunta6a;	public String captura6a;	LinearLayout lay6a;
    public String op6b="sin datos";	public RadioGroup rdPregunta6b;	public EditText editPregunta6b;	public String captura6b;	LinearLayout lay6b;
    public String op6c="sin datos";	public RadioGroup rdPregunta6c;	public EditText editPregunta6c;	public String captura6c;	LinearLayout lay6c;
    public String op6d="sin datos";	public RadioGroup rdPregunta6d;	public EditText editPregunta6d;	public String captura6d;	LinearLayout lay6d;
    public String op6e="sin datos";	public RadioGroup rdPregunta6e;	public EditText editPregunta6e;	public String captura6e;	LinearLayout lay6e;
    public String op6f="sin datos";	public RadioGroup rdPregunta6f;	public EditText editPregunta6f;	public String captura6f;	LinearLayout lay6f;
    public String op6g="sin datos";	public RadioGroup rdPregunta6g;	public EditText editPregunta6g;	public String captura6g;	LinearLayout lay6g;
    public String op6h="sin datos";	public RadioGroup rdPregunta6h;	public EditText editPregunta6h;	public String captura6h;	LinearLayout lay6h;
    public String op6i="sin datos";	public RadioGroup rdPregunta6i;	public EditText editPregunta6i;	public String captura6i;	LinearLayout lay6i;
    public String op6j="sin datos";	public RadioGroup rdPregunta6j;	public EditText editPregunta6j;	public String captura6j;	LinearLayout lay6j;
    public String op6k="sin datos";	public RadioGroup rdPregunta6k;	public EditText editPregunta6k;	public String captura6k;	LinearLayout lay6k;
    public String op7="sin datos";	public RadioGroup rdPregunta7;	public EditText editPregunta7;	public String captura7;	LinearLayout lay7;
    public String op8="sin datos";	public RadioGroup rdPregunta8;	public EditText editPregunta8;	public String captura8;	LinearLayout lay8;
    public String op8a="sin datos";	public RadioGroup rdPregunta8a;	public EditText editPregunta8a;	public String captura8a;	LinearLayout lay8a;
    public String op8b="sin datos";	public RadioGroup rdPregunta8b;	public EditText editPregunta8b;	public String captura8b;	LinearLayout lay8b;
    public String op8c="sin datos";	public RadioGroup rdPregunta8c;	public EditText editPregunta8c;	public String captura8c;	LinearLayout lay8c;
    public String op8d="sin datos";	public RadioGroup rdPregunta8d;	public EditText editPregunta8d;	public String captura8d;	LinearLayout lay8d;
    public String op8e="sin datos";	public RadioGroup rdPregunta8e;	public EditText editPregunta8e;	public String captura8e;	LinearLayout lay8e;
    public String op8f="sin datos";	public RadioGroup rdPregunta8f;	public EditText editPregunta8f;	public String captura8f;	LinearLayout lay8f;
    public String op9="sin datos";	public RadioGroup rdPregunta9;	public EditText editPregunta9;	public String captura9;	LinearLayout lay9;
    public String op10="sin datos";	public RadioGroup rdPregunta10;	public EditText editPregunta10;	public String captura10;	LinearLayout lay10;
    public String op11="sin datos";	public RadioGroup rdPregunta11;	public EditText editPregunta11;	public String captura11;	LinearLayout lay11;
    public String op12="sin datos";	public RadioGroup rdPregunta12;	public EditText editPregunta12;	public String captura12;	LinearLayout lay12;
    public String op13="sin datos";	public RadioGroup rdPregunta13;	public EditText editPregunta13;	public String captura13;	LinearLayout lay13;
    public String op13a="sin datos";	public RadioGroup rdPregunta13a;	public EditText editPregunta13a;	public String captura13a;	LinearLayout lay13a;
    public String op13b="sin datos";	public RadioGroup rdPregunta13b;	public EditText editPregunta13b;	public String captura13b;	LinearLayout lay13b;
    public String op13c="sin datos";	public RadioGroup rdPregunta13c;	public EditText editPregunta13c;	public String captura13c;	LinearLayout lay13c;
    public String op13d="sin datos";	public RadioGroup rdPregunta13d;	public EditText editPregunta13d;	public String captura13d;	LinearLayout lay13d;
    public String op13e="sin datos";	public RadioGroup rdPregunta13e;	public EditText editPregunta13e;	public String captura13e;	LinearLayout lay13e;
    public String op14="sin datos";	public RadioGroup rdPregunta14;	public EditText editPregunta14;	public String captura14;	LinearLayout lay14;
    public String op15="sin datos";	public RadioGroup rdPregunta15;	public EditText editPregunta15;	public String captura15;	LinearLayout lay15;
    public String op16="sin datos";	public RadioGroup rdPregunta16;	public EditText editPregunta16;	public String captura16;	LinearLayout lay16;
    public String op16a="sin datos";	public RadioGroup rdPregunta16a;	public EditText editPregunta16a;	public String captura16a;	LinearLayout lay16a;
    public String op16b="sin datos";	public RadioGroup rdPregunta16b;	public EditText editPregunta16b;	public String captura16b;	LinearLayout lay16b;
    public String op16c="sin datos";	public RadioGroup rdPregunta16c;	public EditText editPregunta16c;	public String captura16c;	LinearLayout lay16c;
    public String op16d="sin datos";	public RadioGroup rdPregunta16d;	public EditText editPregunta16d;	public String captura16d;	LinearLayout lay16d;
    public String op16e="sin datos";	public RadioGroup rdPregunta16e;	public EditText editPregunta16e;	public String captura16e;	LinearLayout lay16e;
    public String op16f="sin datos";	public RadioGroup rdPregunta16f;	public EditText editPregunta16f;	public String captura16f;	LinearLayout lay16f;
    public String op16g="sin datos";	public RadioGroup rdPregunta16g;	public EditText editPregunta16g;	public String captura16g;	LinearLayout lay16g;
    public String op16h="sin datos";	public RadioGroup rdPregunta16h;	public EditText editPregunta16h;	public String captura16h;	LinearLayout lay16h;
    public String op16i="sin datos";	public RadioGroup rdPregunta16i;	public EditText editPregunta16i;	public String captura16i;	LinearLayout lay16i;
    public String op16j="sin datos";	public RadioGroup rdPregunta16j;	public EditText editPregunta16j;	public String captura16j;	LinearLayout lay16j;
    public String op16k="sin datos";	public RadioGroup rdPregunta16k;	public EditText editPregunta16k;	public String captura16k;	LinearLayout lay16k;
    public String op17="sin datos";	public RadioGroup rdPregunta17;	public
    EditText editPregunta17a;
    EditText editPregunta17b;
    EditText editPregunta17c;
    EditText editPregunta17d;
    public String captura17;	LinearLayout lay17;
    public String op18="sin datos";	public RadioGroup rdPregunta18;	public EditText editPregunta18;	public String captura18;	LinearLayout lay18;
    public String op18a="sin datos";	public RadioGroup rdPregunta18a;	public EditText editPregunta18a;	public String captura18a;	LinearLayout lay18a;
    public String op18b="sin datos";	public RadioGroup rdPregunta18b;	public EditText editPregunta18b;	public String captura18b;	LinearLayout lay18b;
    public String op18c="sin datos";	public RadioGroup rdPregunta18c;	public EditText editPregunta18c;	public String captura18c;	LinearLayout lay18c;
    public String op18d="sin datos";	public RadioGroup rdPregunta18d;	public EditText editPregunta18d;	public String captura18d;	LinearLayout lay18d;
    public String op18e="sin datos";	public RadioGroup rdPregunta18e;	public EditText editPregunta18e;	public String captura18e;	LinearLayout lay18e;
    public String op18f="sin datos";	public RadioGroup rdPregunta18f;	public EditText editPregunta18f;	public String captura18f;	LinearLayout lay18f;
    public String op19="sin datos";	public RadioGroup rdPregunta19;	public EditText editPregunta19;	public String captura19;	LinearLayout lay19;
    public String op19a="sin datos";	public RadioGroup rdPregunta19a;	public EditText editPregunta19a;	public String captura19a;	LinearLayout lay19a;
    public String op19b="sin datos";	public RadioGroup rdPregunta19b;	public EditText editPregunta19b;	public String captura19b;	LinearLayout lay19b;
    public String op19c="sin datos";	public RadioGroup rdPregunta19c;	public EditText editPregunta19c;	public String captura19c;	LinearLayout lay19c;
    public String op19d="sin datos";	public RadioGroup rdPregunta19d;	public EditText editPregunta19d;	public String captura19d;	LinearLayout lay19d;
    public String op19e="sin datos";	public RadioGroup rdPregunta19e;	public EditText editPregunta19e;	public String captura19e;	LinearLayout lay19e;
    public String op20="sin datos";	public RadioGroup rdPregunta20;	public EditText editPregunta20;	public String captura20;	LinearLayout lay20;
    public String op21="sin datos";	public RadioGroup rdPregunta21;	public EditText editPregunta21;	public String captura21;	LinearLayout lay21;
    public String op22="sin datos";	public RadioGroup rdPregunta22;	public EditText editPregunta22;	public String captura22;	LinearLayout lay22;
    public String op23="sin datos";	public RadioGroup rdPregunta23;	public EditText editPregunta23;	public String captura23;	LinearLayout lay23;
    public String op24="sin datos";	public RadioGroup rdPregunta24;	public EditText editPregunta24;	public String captura24;	LinearLayout lay24;

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

    public String cachaTelefono() {
        Bundle datos = this.getIntent().getExtras();
        String telefono = datos.getString("telefono");
        return telefono;
    }

    public String cachaSeccion() {
        Bundle datos = this.getIntent().getExtras();
        String Seccion = datos.getString("Seccion");
        return Seccion;
    }

    public String cachaAlcaldia() {
        Bundle datos = this.getIntent().getExtras();
        String alcaldia = datos.getString("alcaldia");
        return alcaldia;
    }
    public String cachaDelegacion() {
        Bundle datos = this.getIntent().getExtras();
        String delegacion = datos.getString("delegacion");
        return delegacion;
    }

    public String cachaEquipo() {
        Bundle datos = this.getIntent().getExtras();
        String equipo = datos.getString("equipo");
        return equipo;
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
        builder.setMessage("Desea continuar Encuestando..?").setTitle("IMPORTANTE").setCancelable(false)
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        detenerGrabacion();

                        Intent i = new Intent(MainActivityPantalla1.this, Entrada.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        System.exit(0); // metodo que se debe implementar
                    }
                }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                detenerGrabacion();

                Intent i = new Intent(MainActivityPantalla1.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Nombre", cachaNombre());
                i.putExtra(USUARIO, usuario);
                startActivity(i);
                System.exit(0); // metodo que se debe implementar
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void dialogoAdicionales() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Registrar Personas adicionales").setTitle("Aviso").setCancelable(false)
            .setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                detenerGrabacion();

                // NORMAL
                Nombre nom = new Nombre();
                String nombreE = nom.nombreEncuesta();

                GPSTracker gps = new GPSTracker(MainActivityPantalla1.this);
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

                Intent i = new Intent(MainActivityPantalla1.this, MainActivityPantalla2.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("Nombre", cachaNombre());
                i.putExtra("consecutivo_diario", elMaximo);
                i.putExtra("equipo", cachaEquipo().toUpperCase());
                i.putExtra("usuario", cachaNombre().toUpperCase());
                i.putExtra("nombre_encuesta", nombreE.toUpperCase());
                i.putExtra("fecha", formattedDate1);
                i.putExtra("hora", formattedDate5);
                i.putExtra("imei", sacaImei());
                i.putExtra("seccion", strSecc);
                i.putExtra("latitud", strLatitud);
                i.putExtra("longitud", strLongitud);
                i.putExtra("alcaldia", cachaAlcaldia());
                i.putExtra(USUARIO,usuario);
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
    MainActivityPantalla1.this.runOnUiThread(new Runnable() {
        public void run() {
            builder.setMessage("¿Se detendrá la grabación y \n se reiniciará la encuesta..?")
            .setTitle("AVISO...!!!").setCancelable(false)
            .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    detenerGrabacion();

                    Intent i = new Intent(MainActivityPantalla1.this, Entrada.class);
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
    MainActivityPantalla1.this.runOnUiThread(new Runnable() {
        public void run() {
            builder.setMessage("Excediste el tiempo máximo para realizar la encuesta \n"
                + "¡¡¡ Se detendrá la grabación y se reiniciará la Aplicación..!!!").setTitle("AVISO...!!!")
            .setCancelable(false).setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    detenerGrabacion();

                    Intent i = new Intent(MainActivityPantalla1.this, Entrada.class);
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
        final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo +"_contacto"+ ".mp3";
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
setContentView(R.layout.activity_pantalla1); // COMENTAR ESTA CUANDO ES ALEATORIO

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
random = new java.util.Random();
//


// Crea Log cuando falla la app
Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(MainActivityPantalla1.this,this));


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

    rdPreguntaA = (RadioGroup)findViewById(R.id.rdPreguntaA);	capturaA =res.getString(R.string.PREGUNTAA);	layA = (LinearLayout) findViewById(R.id.layA);
    rdPreguntaB = (RadioGroup)findViewById(R.id.rdPreguntaB);	capturaB =res.getString(R.string.PREGUNTAB);	layB = (LinearLayout) findViewById(R.id.layB);
    rdPregunta1 = (RadioGroup)findViewById(R.id.rdPregunta1);	captura1 =res.getString(R.string.PREGUNTA1);	lay1 = (LinearLayout) findViewById(R.id.lay1);
    rdPregunta2 = (RadioGroup)findViewById(R.id.rdPregunta2);	captura2 =res.getString(R.string.PREGUNTA2);	lay2 = (LinearLayout) findViewById(R.id.lay2);
    rdPregunta3 = (RadioGroup)findViewById(R.id.rdPregunta3);	captura3 =res.getString(R.string.PREGUNTA3);	lay3 = (LinearLayout) findViewById(R.id.lay3);
    rdPregunta4 = (RadioGroup)findViewById(R.id.rdPregunta4);	captura4 =res.getString(R.string.PREGUNTA4);	lay4 = (LinearLayout) findViewById(R.id.lay4);
    rdPregunta5 = (RadioGroup)findViewById(R.id.rdPregunta5);	captura5 =res.getString(R.string.PREGUNTA5);	lay5 = (LinearLayout) findViewById(R.id.lay5);
    rdPregunta6 = (RadioGroup)findViewById(R.id.rdPregunta6);	captura6 =res.getString(R.string.PREGUNTA6);	lay6 = (LinearLayout) findViewById(R.id.lay6);
    rdPregunta6a = (RadioGroup)findViewById(R.id.rdPregunta6a);	captura6a =res.getString(R.string.PREGUNTA6a);	lay6a = (LinearLayout) findViewById(R.id.lay6a);
    rdPregunta6b = (RadioGroup)findViewById(R.id.rdPregunta6b);	captura6b =res.getString(R.string.PREGUNTA6b);	lay6b = (LinearLayout) findViewById(R.id.lay6b);
    rdPregunta6c = (RadioGroup)findViewById(R.id.rdPregunta6c);	captura6c =res.getString(R.string.PREGUNTA6c);	lay6c = (LinearLayout) findViewById(R.id.lay6c);
    rdPregunta6d = (RadioGroup)findViewById(R.id.rdPregunta6d);	captura6d =res.getString(R.string.PREGUNTA6d);	lay6d = (LinearLayout) findViewById(R.id.lay6d);
    rdPregunta6e = (RadioGroup)findViewById(R.id.rdPregunta6e);	captura6e =res.getString(R.string.PREGUNTA6e);	lay6e = (LinearLayout) findViewById(R.id.lay6e);
    rdPregunta6f = (RadioGroup)findViewById(R.id.rdPregunta6f);	captura6f =res.getString(R.string.PREGUNTA6f);	lay6f = (LinearLayout) findViewById(R.id.lay6f);
    rdPregunta6g = (RadioGroup)findViewById(R.id.rdPregunta6g);	captura6g =res.getString(R.string.PREGUNTA6g);	lay6g = (LinearLayout) findViewById(R.id.lay6g);
    rdPregunta6h = (RadioGroup)findViewById(R.id.rdPregunta6h);	captura6h =res.getString(R.string.PREGUNTA6h);	lay6h = (LinearLayout) findViewById(R.id.lay6h);
    rdPregunta6i = (RadioGroup)findViewById(R.id.rdPregunta6i);	captura6i =res.getString(R.string.PREGUNTA6i);	lay6i = (LinearLayout) findViewById(R.id.lay6i);
    rdPregunta6j = (RadioGroup)findViewById(R.id.rdPregunta6j);	captura6j =res.getString(R.string.PREGUNTA6j);	lay6j = (LinearLayout) findViewById(R.id.lay6j);
    rdPregunta6k = (RadioGroup)findViewById(R.id.rdPregunta6k);	captura6k =res.getString(R.string.PREGUNTA6k);	lay6k = (LinearLayout) findViewById(R.id.lay6k);
    rdPregunta7 = (RadioGroup)findViewById(R.id.rdPregunta7);	captura7 =res.getString(R.string.PREGUNTA7);	lay7 = (LinearLayout) findViewById(R.id.lay7);
    rdPregunta8 = (RadioGroup)findViewById(R.id.rdPregunta8);	captura8 =res.getString(R.string.PREGUNTA8);	lay8 = (LinearLayout) findViewById(R.id.lay8);
    rdPregunta8a = (RadioGroup)findViewById(R.id.rdPregunta8a);	captura8a =res.getString(R.string.PREGUNTA8a);	lay8a = (LinearLayout) findViewById(R.id.lay8a);
    rdPregunta8b = (RadioGroup)findViewById(R.id.rdPregunta8b);	captura8b =res.getString(R.string.PREGUNTA8b);	lay8b = (LinearLayout) findViewById(R.id.lay8b);
    rdPregunta8c = (RadioGroup)findViewById(R.id.rdPregunta8c);	captura8c =res.getString(R.string.PREGUNTA8c);	lay8c = (LinearLayout) findViewById(R.id.lay8c);
    rdPregunta8d = (RadioGroup)findViewById(R.id.rdPregunta8d);	captura8d =res.getString(R.string.PREGUNTA8d);	lay8d = (LinearLayout) findViewById(R.id.lay8d);
    rdPregunta8e = (RadioGroup)findViewById(R.id.rdPregunta8e);	captura8e =res.getString(R.string.PREGUNTA8e);	lay8e = (LinearLayout) findViewById(R.id.lay8e);
    rdPregunta8f = (RadioGroup)findViewById(R.id.rdPregunta8f);	captura8f =res.getString(R.string.PREGUNTA8f);	lay8f = (LinearLayout) findViewById(R.id.lay8f);
    rdPregunta9 = (RadioGroup)findViewById(R.id.rdPregunta9);	captura9 =res.getString(R.string.PREGUNTA9);	lay9 = (LinearLayout) findViewById(R.id.lay9);
    rdPregunta10 = (RadioGroup)findViewById(R.id.rdPregunta10);	captura10 =res.getString(R.string.PREGUNTA10);	lay10 = (LinearLayout) findViewById(R.id.lay10);
    rdPregunta11 = (RadioGroup)findViewById(R.id.rdPregunta11);	captura11 =res.getString(R.string.PREGUNTA11);	lay11 = (LinearLayout) findViewById(R.id.lay11);
    rdPregunta12 = (RadioGroup)findViewById(R.id.rdPregunta12);	captura12 =res.getString(R.string.PREGUNTA12);	lay12 = (LinearLayout) findViewById(R.id.lay12);
    rdPregunta13 = (RadioGroup)findViewById(R.id.rdPregunta13);	captura13 =res.getString(R.string.PREGUNTA13);	lay13 = (LinearLayout) findViewById(R.id.lay13);
    rdPregunta13a = (RadioGroup)findViewById(R.id.rdPregunta13a);	captura13a =res.getString(R.string.PREGUNTA13a);	lay13a = (LinearLayout) findViewById(R.id.lay13a);
    rdPregunta13b = (RadioGroup)findViewById(R.id.rdPregunta13b);	captura13b =res.getString(R.string.PREGUNTA13b);	lay13b = (LinearLayout) findViewById(R.id.lay13b);
    rdPregunta13c = (RadioGroup)findViewById(R.id.rdPregunta13c);	captura13c =res.getString(R.string.PREGUNTA13c);	lay13c = (LinearLayout) findViewById(R.id.lay13c);
    rdPregunta13d = (RadioGroup)findViewById(R.id.rdPregunta13d);	captura13d =res.getString(R.string.PREGUNTA13d);	lay13d = (LinearLayout) findViewById(R.id.lay13d);
    rdPregunta13e = (RadioGroup)findViewById(R.id.rdPregunta13e);	captura13e =res.getString(R.string.PREGUNTA13e);	lay13e = (LinearLayout) findViewById(R.id.lay13e);
    rdPregunta14 = (RadioGroup)findViewById(R.id.rdPregunta14);	captura14 =res.getString(R.string.PREGUNTA14);	lay14 = (LinearLayout) findViewById(R.id.lay14);
    rdPregunta15 = (RadioGroup)findViewById(R.id.rdPregunta15);	captura15 =res.getString(R.string.PREGUNTA15);	lay15 = (LinearLayout) findViewById(R.id.lay15);
    rdPregunta16 = (RadioGroup)findViewById(R.id.rdPregunta16);	captura16 =res.getString(R.string.PREGUNTA16);	lay16 = (LinearLayout) findViewById(R.id.lay16);
    rdPregunta16a = (RadioGroup)findViewById(R.id.rdPregunta16a);	captura16a =res.getString(R.string.PREGUNTA16a);	lay16a = (LinearLayout) findViewById(R.id.lay16a);
    rdPregunta16b = (RadioGroup)findViewById(R.id.rdPregunta16b);	captura16b =res.getString(R.string.PREGUNTA16b);	lay16b = (LinearLayout) findViewById(R.id.lay16b);
    rdPregunta16c = (RadioGroup)findViewById(R.id.rdPregunta16c);	captura16c =res.getString(R.string.PREGUNTA16c);	lay16c = (LinearLayout) findViewById(R.id.lay16c);
    rdPregunta16d = (RadioGroup)findViewById(R.id.rdPregunta16d);	captura16d =res.getString(R.string.PREGUNTA16d);	lay16d = (LinearLayout) findViewById(R.id.lay16d);
    rdPregunta16e = (RadioGroup)findViewById(R.id.rdPregunta16e);	captura16e =res.getString(R.string.PREGUNTA16e);	lay16e = (LinearLayout) findViewById(R.id.lay16e);
    rdPregunta16f = (RadioGroup)findViewById(R.id.rdPregunta16f);	captura16f =res.getString(R.string.PREGUNTA16f);	lay16f = (LinearLayout) findViewById(R.id.lay16f);
    rdPregunta16g = (RadioGroup)findViewById(R.id.rdPregunta16g);	captura16g =res.getString(R.string.PREGUNTA16g);	lay16g = (LinearLayout) findViewById(R.id.lay16g);
    rdPregunta16h = (RadioGroup)findViewById(R.id.rdPregunta16h);	captura16h =res.getString(R.string.PREGUNTA16h);	lay16h = (LinearLayout) findViewById(R.id.lay16h);
    rdPregunta16i = (RadioGroup)findViewById(R.id.rdPregunta16i);	captura16i =res.getString(R.string.PREGUNTA16i);	lay16i = (LinearLayout) findViewById(R.id.lay16i);
    rdPregunta16j = (RadioGroup)findViewById(R.id.rdPregunta16j);	captura16j =res.getString(R.string.PREGUNTA16j);	lay16j = (LinearLayout) findViewById(R.id.lay16j);
    rdPregunta16k = (RadioGroup)findViewById(R.id.rdPregunta16k);	captura16k =res.getString(R.string.PREGUNTA16k);	lay16k = (LinearLayout) findViewById(R.id.lay16k);
    rdPregunta17 = (RadioGroup)findViewById(R.id.rdPregunta17);	captura17 =res.getString(R.string.PREGUNTA17);	lay17 = (LinearLayout) findViewById(R.id.lay17);
    rdPregunta18 = (RadioGroup)findViewById(R.id.rdPregunta18);	captura18 =res.getString(R.string.PREGUNTA18);	lay18 = (LinearLayout) findViewById(R.id.lay18);
    rdPregunta18a = (RadioGroup)findViewById(R.id.rdPregunta18a);	captura18a =res.getString(R.string.PREGUNTA18a);	lay18a = (LinearLayout) findViewById(R.id.lay18a);
    rdPregunta18b = (RadioGroup)findViewById(R.id.rdPregunta18b);	captura18b =res.getString(R.string.PREGUNTA18b);	lay18b = (LinearLayout) findViewById(R.id.lay18b);
    rdPregunta18c = (RadioGroup)findViewById(R.id.rdPregunta18c);	captura18c =res.getString(R.string.PREGUNTA18c);	lay18c = (LinearLayout) findViewById(R.id.lay18c);
    rdPregunta18d = (RadioGroup)findViewById(R.id.rdPregunta18d);	captura18d =res.getString(R.string.PREGUNTA18d);	lay18d = (LinearLayout) findViewById(R.id.lay18d);
    rdPregunta18e = (RadioGroup)findViewById(R.id.rdPregunta18e);	captura18e =res.getString(R.string.PREGUNTA18e);	lay18e = (LinearLayout) findViewById(R.id.lay18e);
    rdPregunta18f = (RadioGroup)findViewById(R.id.rdPregunta18f);	captura18f =res.getString(R.string.PREGUNTA18f);	lay18f = (LinearLayout) findViewById(R.id.lay18f);
    rdPregunta19 = (RadioGroup)findViewById(R.id.rdPregunta19);	captura19 =res.getString(R.string.PREGUNTA19);	lay19 = (LinearLayout) findViewById(R.id.lay19);
    rdPregunta19a = (RadioGroup)findViewById(R.id.rdPregunta19a);	captura19a =res.getString(R.string.PREGUNTA19a);	lay19a = (LinearLayout) findViewById(R.id.lay19a);
    rdPregunta19b = (RadioGroup)findViewById(R.id.rdPregunta19b);	captura19b =res.getString(R.string.PREGUNTA19b);	lay19b = (LinearLayout) findViewById(R.id.lay19b);
    rdPregunta19c = (RadioGroup)findViewById(R.id.rdPregunta19c);	captura19c =res.getString(R.string.PREGUNTA19c);	lay19c = (LinearLayout) findViewById(R.id.lay19c);
    rdPregunta19d = (RadioGroup)findViewById(R.id.rdPregunta19d);	captura19d =res.getString(R.string.PREGUNTA19d);	lay19d = (LinearLayout) findViewById(R.id.lay19d);
    rdPregunta19e = (RadioGroup)findViewById(R.id.rdPregunta19e);	captura19e =res.getString(R.string.PREGUNTA19e);	lay19e = (LinearLayout) findViewById(R.id.lay19e);
    rdPregunta20 = (RadioGroup)findViewById(R.id.rdPregunta20);	captura20 =res.getString(R.string.PREGUNTA20);	lay20 = (LinearLayout) findViewById(R.id.lay20);
    rdPregunta21 = (RadioGroup)findViewById(R.id.rdPregunta21);	captura21 =res.getString(R.string.PREGUNTA21);	lay21 = (LinearLayout) findViewById(R.id.lay21);
    rdPregunta22 = (RadioGroup)findViewById(R.id.rdPregunta22);	captura22 =res.getString(R.string.PREGUNTA22);	lay22 = (LinearLayout) findViewById(R.id.lay22);
    rdPregunta23 = (RadioGroup)findViewById(R.id.rdPregunta23);	captura23 =res.getString(R.string.PREGUNTA23);	lay23 = (LinearLayout) findViewById(R.id.lay23);
    rdPregunta24 = (RadioGroup)findViewById(R.id.rdPregunta24);	captura24 =res.getString(R.string.PREGUNTA24);	lay24 = (LinearLayout) findViewById(R.id.lay24);

    editPregunta1= (EditText)findViewById(R.id.editPregunta1);
    editPregunta2= (EditText)findViewById(R.id.editPregunta2);
    editPregunta3= (EditText)findViewById(R.id.editPregunta3);
    editPregunta4= (EditText)findViewById(R.id.editPregunta4);
    editPregunta7= (EditText)findViewById(R.id.editPregunta7);
    editPregunta8a= (EditText)findViewById(R.id.editPregunta8a);
    editPregunta8b= (EditText)findViewById(R.id.editPregunta8b);
    editPregunta8c= (EditText)findViewById(R.id.editPregunta8c);
    editPregunta8d= (EditText)findViewById(R.id.editPregunta8d);
    editPregunta8e= (EditText)findViewById(R.id.editPregunta8e);
    editPregunta8f= (EditText)findViewById(R.id.editPregunta8f);
    editPregunta10= (EditText)findViewById(R.id.editPregunta10);
    editPregunta12= (EditText)findViewById(R.id.editPregunta12);
    editPregunta13a= (EditText)findViewById(R.id.editPregunta13a);
    editPregunta13b= (EditText)findViewById(R.id.editPregunta13b);
    editPregunta13c= (EditText)findViewById(R.id.editPregunta13c);
    editPregunta13d= (EditText)findViewById(R.id.editPregunta13d);
    editPregunta17a= (EditText)findViewById(R.id.editPregunta17a);
    editPregunta17b= (EditText)findViewById(R.id.editPregunta17b);
    editPregunta17c= (EditText)findViewById(R.id.editPregunta17c);
    editPregunta17d= (EditText)findViewById(R.id.editPregunta17d);
    editPregunta19a= (EditText)findViewById(R.id.editPregunta19a);
    editPregunta19b= (EditText)findViewById(R.id.editPregunta19b);
    editPregunta19c= (EditText)findViewById(R.id.editPregunta19c);
    editPregunta19d= (EditText)findViewById(R.id.editPregunta19d);
    editPregunta22= (EditText)findViewById(R.id.editPregunta22);
    editPregunta23= (EditText)findViewById(R.id.editPregunta23);
    editPregunta24= (EditText)findViewById(R.id.editPregunta24);

    spinner13e= (Spinner)findViewById(R.id.spinner13e);
    spinner19e= (Spinner)findViewById(R.id.spinner19e);

    CargaSpinnerAlcaldia13e();
    CargaSpinnerAlcaldia19e();



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
    rdPreguntaA.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opA = "0";
            }
            else if (checkedId == R.id.radio2) {
                opA = "A";
            }
            else if (checkedId == R.id.radio3) {
                opA = "No abren/ no hay nadie";
                layB .setVisibility(View.GONE);	rdPreguntaB.clearCheck();	opB="No aplica";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";


            }
            else if (checkedId == R.id.radio4) {
                opA = "SI";
                layB .setVisibility(View.VISIBLE);	rdPreguntaB.clearCheck();	opB="sin datos";
                lay1 .setVisibility(View.VISIBLE);	rdPregunta1.clearCheck();	op1="sin datos";
                lay2 .setVisibility(View.VISIBLE);	rdPregunta2.clearCheck();	op2="sin datos";
                lay3 .setVisibility(View.VISIBLE);	rdPregunta3.clearCheck();	op3="sin datos";
                lay4 .setVisibility(View.VISIBLE);	rdPregunta4.clearCheck();	op4="sin datos";
                lay5 .setVisibility(View.VISIBLE);	rdPregunta5.clearCheck();	op5="sin datos";
                lay6 .setVisibility(View.VISIBLE);	rdPregunta6.clearCheck();	op6="sin datos";
                lay6a .setVisibility(View.VISIBLE);	rdPregunta6a.clearCheck();	op6a="sin datos";
                lay6b .setVisibility(View.VISIBLE);	rdPregunta6b.clearCheck();	op6b="sin datos";
                lay6c .setVisibility(View.VISIBLE);	rdPregunta6c.clearCheck();	op6c="sin datos";
                lay6d .setVisibility(View.VISIBLE);	rdPregunta6d.clearCheck();	op6d="sin datos";
                lay6e .setVisibility(View.VISIBLE);	rdPregunta6e.clearCheck();	op6e="sin datos";
                lay6f .setVisibility(View.VISIBLE);	rdPregunta6f.clearCheck();	op6f="sin datos";
                lay6g .setVisibility(View.VISIBLE);	rdPregunta6g.clearCheck();	op6g="sin datos";
                lay6h .setVisibility(View.VISIBLE);	rdPregunta6h.clearCheck();	op6h="sin datos";
                lay6i .setVisibility(View.VISIBLE);	rdPregunta6i.clearCheck();	op6i="sin datos";
                lay6j .setVisibility(View.VISIBLE);	rdPregunta6j.clearCheck();	op6j="sin datos";
                lay6k .setVisibility(View.VISIBLE);	rdPregunta6k.clearCheck();	op6k="sin datos";
                lay7 .setVisibility(View.VISIBLE);	rdPregunta7.clearCheck();	op7="sin datos";
                lay8 .setVisibility(View.VISIBLE);	rdPregunta8.clearCheck();	op8="sin datos";
                lay8a .setVisibility(View.VISIBLE);	rdPregunta8a.clearCheck();	op8a="sin datos";
                lay8b .setVisibility(View.VISIBLE);	rdPregunta8b.clearCheck();	op8b="sin datos";
                lay8c .setVisibility(View.VISIBLE);	rdPregunta8c.clearCheck();	op8c="sin datos";
                lay8d .setVisibility(View.VISIBLE);	rdPregunta8d.clearCheck();	op8d="sin datos";
                lay8e .setVisibility(View.VISIBLE);	rdPregunta8e.clearCheck();	op8e="sin datos";
                lay8f .setVisibility(View.VISIBLE);	rdPregunta8f.clearCheck();	op8f="sin datos";
                lay9 .setVisibility(View.VISIBLE);	rdPregunta9.clearCheck();	op9="sin datos";
                lay10 .setVisibility(View.VISIBLE);	rdPregunta10.clearCheck();	op10="sin datos";
                lay11 .setVisibility(View.VISIBLE);	rdPregunta11.clearCheck();	op11="sin datos";
                lay12 .setVisibility(View.VISIBLE);	rdPregunta12.clearCheck();	op12="sin datos";
                lay13 .setVisibility(View.VISIBLE);	rdPregunta13.clearCheck();	op13="sin datos";
                lay13a .setVisibility(View.VISIBLE);	rdPregunta13a.clearCheck();	op13a="sin datos";
                lay13b .setVisibility(View.VISIBLE);	rdPregunta13b.clearCheck();	op13b="sin datos";
                lay13c .setVisibility(View.VISIBLE);	rdPregunta13c.clearCheck();	op13c="sin datos";
                lay13d .setVisibility(View.VISIBLE);	rdPregunta13d.clearCheck();	op13d="sin datos";
                lay13e .setVisibility(View.VISIBLE);	rdPregunta13e.clearCheck();	op13e="sin datos";
                lay14 .setVisibility(View.VISIBLE);	rdPregunta14.clearCheck();	op14="sin datos";
                lay15 .setVisibility(View.VISIBLE);	rdPregunta15.clearCheck();	op15="sin datos";
                lay16 .setVisibility(View.VISIBLE);	rdPregunta16.clearCheck();	op16="sin datos";
                lay16a .setVisibility(View.VISIBLE);	rdPregunta16a.clearCheck();	op16a="sin datos";
                lay16b .setVisibility(View.VISIBLE);	rdPregunta16b.clearCheck();	op16b="sin datos";
                lay16c .setVisibility(View.VISIBLE);	rdPregunta16c.clearCheck();	op16c="sin datos";
                lay16d .setVisibility(View.VISIBLE);	rdPregunta16d.clearCheck();	op16d="sin datos";
                lay16e .setVisibility(View.VISIBLE);	rdPregunta16e.clearCheck();	op16e="sin datos";
                lay16f .setVisibility(View.VISIBLE);	rdPregunta16f.clearCheck();	op16f="sin datos";
                lay16g .setVisibility(View.VISIBLE);	rdPregunta16g.clearCheck();	op16g="sin datos";
                lay16h .setVisibility(View.VISIBLE);	rdPregunta16h.clearCheck();	op16h="sin datos";
                lay16i .setVisibility(View.VISIBLE);	rdPregunta16i.clearCheck();	op16i="sin datos";
                lay16j .setVisibility(View.VISIBLE);	rdPregunta16j.clearCheck();	op16j="sin datos";
                lay16k .setVisibility(View.VISIBLE);	rdPregunta16k.clearCheck();	op16k="sin datos";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="sin datos";
                lay18 .setVisibility(View.VISIBLE);	rdPregunta18.clearCheck();	op18="sin datos";
                lay18a .setVisibility(View.VISIBLE);	rdPregunta18a.clearCheck();	op18a="sin datos";
                lay18b .setVisibility(View.VISIBLE);	rdPregunta18b.clearCheck();	op18b="sin datos";
                lay18c .setVisibility(View.VISIBLE);	rdPregunta18c.clearCheck();	op18c="sin datos";
                lay18d .setVisibility(View.VISIBLE);	rdPregunta18d.clearCheck();	op18d="sin datos";
                lay18e .setVisibility(View.VISIBLE);	rdPregunta18e.clearCheck();	op18e="sin datos";
                lay18f .setVisibility(View.VISIBLE);	rdPregunta18f.clearCheck();	op18f="sin datos";
                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
                lay20 .setVisibility(View.VISIBLE);	rdPregunta20.clearCheck();	op20="sin datos";
                lay21 .setVisibility(View.VISIBLE);	rdPregunta21.clearCheck();	op21="sin datos";
                lay22 .setVisibility(View.VISIBLE);	rdPregunta22.clearCheck();	op22="sin datos";
                lay23 .setVisibility(View.VISIBLE);	rdPregunta23.clearCheck();	op23="sin datos";
                lay24 .setVisibility(View.VISIBLE);	rdPregunta24.clearCheck();	op24="sin datos";

            }
            else if (checkedId == R.id.radio5) {
                opA = "SI vive ahí y no esta";
                layB .setVisibility(View.GONE);	rdPreguntaB.clearCheck();	opB="No aplica";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";
            }
            else if (checkedId == R.id.radio6) {
                opA = "No vive ahí";
                layB .setVisibility(View.GONE);	rdPreguntaB.clearCheck();	opB="No aplica";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opA = "No sabe / No contestó";
                layB .setVisibility(View.GONE);	rdPreguntaB.clearCheck();	opB="No aplica";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPreguntaB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                opB = "0";
            }
            else if (checkedId == R.id.radio2) {
                opB = "B";
            }
            else if (checkedId == R.id.radio3) {
                opB = "SI";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";

            }
            else if (checkedId == R.id.radio4) {
                opB = "NO";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                opB = "No sabe / No contestó";
                lay1 .setVisibility(View.GONE);	rdPregunta1.clearCheck();	op1="No aplica";
                lay2 .setVisibility(View.GONE);	rdPregunta2.clearCheck();	op2="No aplica";
                lay3 .setVisibility(View.GONE);	rdPregunta3.clearCheck();	op3="No aplica";
                lay4 .setVisibility(View.GONE);	rdPregunta4.clearCheck();	op4="No aplica";
                lay5 .setVisibility(View.GONE);	rdPregunta5.clearCheck();	op5="No aplica";
                lay6 .setVisibility(View.GONE);	rdPregunta6.clearCheck();	op6="No aplica";
                lay6a .setVisibility(View.GONE);	rdPregunta6a.clearCheck();	op6a="No aplica";
                lay6b .setVisibility(View.GONE);	rdPregunta6b.clearCheck();	op6b="No aplica";
                lay6c .setVisibility(View.GONE);	rdPregunta6c.clearCheck();	op6c="No aplica";
                lay6d .setVisibility(View.GONE);	rdPregunta6d.clearCheck();	op6d="No aplica";
                lay6e .setVisibility(View.GONE);	rdPregunta6e.clearCheck();	op6e="No aplica";
                lay6f .setVisibility(View.GONE);	rdPregunta6f.clearCheck();	op6f="No aplica";
                lay6g .setVisibility(View.GONE);	rdPregunta6g.clearCheck();	op6g="No aplica";
                lay6h .setVisibility(View.GONE);	rdPregunta6h.clearCheck();	op6h="No aplica";
                lay6i .setVisibility(View.GONE);	rdPregunta6i.clearCheck();	op6i="No aplica";
                lay6j .setVisibility(View.GONE);	rdPregunta6j.clearCheck();	op6j="No aplica";
                lay6k .setVisibility(View.GONE);	rdPregunta6k.clearCheck();	op6k="No aplica";
                lay7 .setVisibility(View.GONE);	rdPregunta7.clearCheck();	op7="No aplica";
                lay8 .setVisibility(View.GONE);	rdPregunta8.clearCheck();	op8="No aplica";
                lay8a .setVisibility(View.GONE);	rdPregunta8a.clearCheck();	op8a="No aplica";
                lay8b .setVisibility(View.GONE);	rdPregunta8b.clearCheck();	op8b="No aplica";
                lay8c .setVisibility(View.GONE);	rdPregunta8c.clearCheck();	op8c="No aplica";
                lay8d .setVisibility(View.GONE);	rdPregunta8d.clearCheck();	op8d="No aplica";
                lay8e .setVisibility(View.GONE);	rdPregunta8e.clearCheck();	op8e="No aplica";
                lay8f .setVisibility(View.GONE);	rdPregunta8f.clearCheck();	op8f="No aplica";
                lay9 .setVisibility(View.GONE);	rdPregunta9.clearCheck();	op9="No aplica";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";
                lay11 .setVisibility(View.GONE);	rdPregunta11.clearCheck();	op11="No aplica";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
                lay18 .setVisibility(View.GONE);	rdPregunta18.clearCheck();	op18="No aplica";
                lay18a .setVisibility(View.GONE);	rdPregunta18a.clearCheck();	op18a="No aplica";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="No aplica";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="No aplica";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="No aplica";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="No aplica";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="No aplica";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="No aplica";
                lay20 .setVisibility(View.GONE);	rdPregunta20.clearCheck();	op20="No aplica";
                lay21 .setVisibility(View.GONE);	rdPregunta21.clearCheck();	op21="No aplica";
                lay22 .setVisibility(View.GONE);	rdPregunta22.clearCheck();	op22="No aplica";
                lay23 .setVisibility(View.GONE);	rdPregunta23.clearCheck();	op23="No aplica";
                lay24 .setVisibility(View.GONE);	rdPregunta24.clearCheck();	op24="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op1 = "1";
                editPregunta1.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op1 = "1";
                editPregunta1.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op1 = "Si es";
                editPregunta1.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op1 = "No sabe / No contestó";
                editPregunta1.setText("");
            }
        }
    });
    editPregunta1.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta1.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta1.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op2 = "1";
                editPregunta2.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op2 = "2";
                editPregunta2.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op2 = "Si es";
                editPregunta2.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op2 = "No sabe / No contestó";
                editPregunta2.setText("");
            }
        }
    });
    editPregunta2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta2.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta2.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op3 = "1";
                editPregunta3.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op3 = "3";
                editPregunta3.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op3 = "Si es";
                editPregunta3.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op3 = "No sabe / No contestó";
                editPregunta3.setText("");
            }
        }
    });
    editPregunta3.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta3.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta3.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op4 = "1";
                editPregunta4.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op4 = "4";
                editPregunta4.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op4 = "Si es";
                editPregunta4.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op4 = "No sabe / No contestó";
                editPregunta4.setText("");
            }
        }
    });
    editPregunta4.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta4.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta4.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op5 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op5 = "5";
            }
            else if (checkedId == R.id.radio3) {
                op5 = "Hombre";
            }
            else if (checkedId == R.id.radio4) {
                op5 = "Mujer";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6 = "6";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6a = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6a = "6a";
            }
            else if (checkedId == R.id.radio3) {
                op6a = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6a = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6a = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6b = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6b = "6b";
            }
            else if (checkedId == R.id.radio3) {
                op6b = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6b = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6b = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6c = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6c = "6c";
            }
            else if (checkedId == R.id.radio3) {
                op6c = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6c = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6c = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6d = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6d = "6d";
            }
            else if (checkedId == R.id.radio3) {
                op6d = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6d = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6d = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6e = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6e = "6e";
            }
            else if (checkedId == R.id.radio3) {
                op6e = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6e = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6e = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6f = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6f = "6f";
            }
            else if (checkedId == R.id.radio3) {
                op6f = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6f = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6f = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6g = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6g = "6g";
            }
            else if (checkedId == R.id.radio3) {
                op6g = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6g = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6g = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6h = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6h = "6h";
            }
            else if (checkedId == R.id.radio3) {
                op6h = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6h = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6h = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6i.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6i = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6i = "6i";
            }
            else if (checkedId == R.id.radio3) {
                op6i = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6i = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6i = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6j.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6j = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6j = "6j";
            }
            else if (checkedId == R.id.radio3) {
                op6j = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6j = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6j = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta6k.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op6k = "0";
            }
            else if (checkedId == R.id.radio2) {
                op6k = "6k";
            }
            else if (checkedId == R.id.radio3) {
                op6k = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op6k = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op6k = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta7.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op7 = "1";
                editPregunta7.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op7 = "7";
                editPregunta7.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op7 = "NO TIENE";
                editPregunta7.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op7 = "No sabe / No contestó";
                editPregunta7.setText("");
            }
        }
    });
    editPregunta7.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta7.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta7.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op8 = "8";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8a = "1";
                editPregunta8a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8a = "8a";
                editPregunta8a.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8a = "Si es";
                editPregunta8a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8a = "No sabe / No contestó";
                editPregunta8a.setText("");
            }
        }
    });
    editPregunta8a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8b = "1";
                editPregunta8b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8b = "8b";
                editPregunta8b.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8b = "Si es";
                editPregunta8b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8b = "No sabe / No contestó";
                editPregunta8b.setText("");
            }
        }
    });
    editPregunta8b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8c = "1";
                editPregunta8c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8c = "8c";
                editPregunta8c.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8c = "Si es";
                editPregunta8c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8c = "No sabe / No contestó";
                editPregunta8c.setText("");
            }
        }
    });
    editPregunta8c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8d = "1";
                editPregunta8d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8d = "8d";
                editPregunta8d.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8d = "Si es";
                editPregunta8d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8d = "No sabe / No contestó";
                editPregunta8d.setText("");
            }
        }
    });
    editPregunta8d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8e = "1";
                editPregunta8e.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8e = "8e";
                editPregunta8e.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8e = "Si es";
                editPregunta8e.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8e = "No sabe / No contestó";
                editPregunta8e.setText("");
            }
        }
    });
    editPregunta8e.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8e.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8e.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta8f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op8f = "1";
                editPregunta8f.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op8f = "8f";
                editPregunta8f.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op8f = "Si es";
                editPregunta8f.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op8f = "No sabe / No contestó";
                editPregunta8f.setText("");
            }
        }
    });
    editPregunta8f.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta8f.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta8f.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta9.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op9 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op9 = "9";
            }
            else if (checkedId == R.id.radio3) {
                op9 = "SI";
                lay10 .setVisibility(View.VISIBLE);	rdPregunta10.clearCheck();	op10="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                op9 = "NO";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";

            }
            else if (checkedId == R.id.radio0) {
                op9 = "No sabe / No contestó";
                lay10 .setVisibility(View.GONE);	rdPregunta10.clearCheck();	op10="No aplica";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta10.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op10 = "1";
                editPregunta10.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op10 = "10";
                editPregunta10.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op10 = "No sabe / No contestó";
                editPregunta10.setText("");
            }
        }
    });
    editPregunta10.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta10.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta10.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta11.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op11 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op11 = "11";
            }
            else if (checkedId == R.id.radio3) {
                op11 = "SI";
                lay12 .setVisibility(View.VISIBLE);	rdPregunta12.clearCheck();	op12="sin datos";
                lay13 .setVisibility(View.VISIBLE);	rdPregunta13.clearCheck();	op13="sin datos";
                lay13a .setVisibility(View.VISIBLE);	rdPregunta13a.clearCheck();	op13a="sin datos";
                lay13b .setVisibility(View.VISIBLE);	rdPregunta13b.clearCheck();	op13b="sin datos";
                lay13c .setVisibility(View.VISIBLE);	rdPregunta13c.clearCheck();	op13c="sin datos";
                lay13d .setVisibility(View.VISIBLE);	rdPregunta13d.clearCheck();	op13d="sin datos";
                lay13e .setVisibility(View.VISIBLE);	rdPregunta13e.clearCheck();	op13e="sin datos";
                lay14 .setVisibility(View.VISIBLE);	rdPregunta14.clearCheck();	op14="sin datos";
                lay15 .setVisibility(View.VISIBLE);	rdPregunta15.clearCheck();	op15="sin datos";
                lay16 .setVisibility(View.VISIBLE);	rdPregunta16.clearCheck();	op16="sin datos";
                lay16a .setVisibility(View.VISIBLE);	rdPregunta16a.clearCheck();	op16a="sin datos";
                lay16b .setVisibility(View.VISIBLE);	rdPregunta16b.clearCheck();	op16b="sin datos";
                lay16c .setVisibility(View.VISIBLE);	rdPregunta16c.clearCheck();	op16c="sin datos";
                lay16d .setVisibility(View.VISIBLE);	rdPregunta16d.clearCheck();	op16d="sin datos";
                lay16e .setVisibility(View.VISIBLE);	rdPregunta16e.clearCheck();	op16e="sin datos";
                lay16f .setVisibility(View.VISIBLE);	rdPregunta16f.clearCheck();	op16f="sin datos";
                lay16g .setVisibility(View.VISIBLE);	rdPregunta16g.clearCheck();	op16g="sin datos";
                lay16h .setVisibility(View.VISIBLE);	rdPregunta16h.clearCheck();	op16h="sin datos";
                lay16i .setVisibility(View.VISIBLE);	rdPregunta16i.clearCheck();	op16i="sin datos";
                lay16j .setVisibility(View.VISIBLE);	rdPregunta16j.clearCheck();	op16j="sin datos";
                lay16k .setVisibility(View.VISIBLE);	rdPregunta16k.clearCheck();	op16k="sin datos";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                op11 = "NO";
                lay12 .setVisibility(View.GONE);	rdPregunta12.clearCheck();	op12="No aplica";
                lay13 .setVisibility(View.GONE);	rdPregunta13.clearCheck();	op13="No aplica";
                lay13a .setVisibility(View.GONE);	rdPregunta13a.clearCheck();	op13a="No aplica";
                lay13b .setVisibility(View.GONE);	rdPregunta13b.clearCheck();	op13b="No aplica";
                lay13c .setVisibility(View.GONE);	rdPregunta13c.clearCheck();	op13c="No aplica";
                lay13d .setVisibility(View.GONE);	rdPregunta13d.clearCheck();	op13d="No aplica";
                lay13e .setVisibility(View.GONE);	rdPregunta13e.clearCheck();	op13e="No aplica";
                lay14 .setVisibility(View.GONE);	rdPregunta14.clearCheck();	op14="No aplica";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="No aplica";
                lay16 .setVisibility(View.GONE);	rdPregunta16.clearCheck();	op16="No aplica";
                lay16a .setVisibility(View.GONE);	rdPregunta16a.clearCheck();	op16a="No aplica";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";

            }
            else if (checkedId == R.id.radio0) {
                op11 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta12.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op12 = "1";
                editPregunta12.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op12 = "12";
                editPregunta12.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op12 = "No sabe / No contestó";
                editPregunta12.setText("");
            }
        }
    });
    editPregunta12.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta12.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta12.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op13 = "13";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13a = "1";
                editPregunta13a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op13a = "13a";
                editPregunta13a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op13a = "No sabe / No contestó";
                editPregunta13a.setText("");
            }
        }
    });
    editPregunta13a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta13a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta13a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13b = "1";
                editPregunta13b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op13b = "13b";
                editPregunta13b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op13b = "No sabe / No contestó";
                editPregunta13b.setText("");
            }
        }
    });
    editPregunta13b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta13b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta13b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13c = "1";
                editPregunta13c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op13c = "13c";
                editPregunta13c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op13c = "No sabe / No contestó";
                editPregunta13c.setText("");
            }
        }
    });
    editPregunta13c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta13c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta13c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13d = "1";
                editPregunta13d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op13d = "13d";
                editPregunta13d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op13d = "No sabe / No contestó";
                editPregunta13d.setText("");
            }
        }
    });
    editPregunta13d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta13d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta13d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta13e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op13e = "2";
                spinner13e.setSelection(0);
            }
            else if (checkedId == R.id.radio2) {
                op13e = "13e";
                spinner13e.setSelection(0);
            }
            else if (checkedId == R.id.radio0) {
                op13e = "No sabe / No contestó";
                spinner13e.setSelection(0);
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta14.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op14 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op14 = "14";
            }
            else if (checkedId == R.id.radio3) {
                op14 = "SI";
                lay15 .setVisibility(View.VISIBLE);	rdPregunta15.clearCheck();	op15="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                op14 = "NO";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op14 = "No sabe / No contestó";
                lay15 .setVisibility(View.GONE);	rdPregunta15.clearCheck();	op15="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta15.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op15 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op15 = "15";
            }
            else if (checkedId == R.id.radio3) {
                op15 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op15 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op15 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16 = "16";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16a = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16a = "16a";
            }
            else if (checkedId == R.id.radio3) {
                op16a = "SI";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";

            }
            else if (checkedId == R.id.radio4) {
                op16a = "NO";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";


            }
            else if (checkedId == R.id.radio0) {
                op16a = "No sabe / No contestó";
                lay16b .setVisibility(View.GONE);	rdPregunta16b.clearCheck();	op16b="No aplica";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16b = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16b = "16b";

            }
            else if (checkedId == R.id.radio3) {
                op16b = "SI";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16b = "NO";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16b = "No sabe / No contestó";
                lay16c .setVisibility(View.GONE);	rdPregunta16c.clearCheck();	op16c="No aplica";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16c = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16c = "16c";
            }
            else if (checkedId == R.id.radio3) {
                op16c = "SI";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16c = "NO";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16c = "No sabe / No contestó";
                lay16d .setVisibility(View.GONE);	rdPregunta16d.clearCheck();	op16d="No aplica";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16d = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16d = "16d";
            }
            else if (checkedId == R.id.radio3) {
                op16d = "SI";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16d = "NO";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16d = "No sabe / No contestó";
                lay16e .setVisibility(View.GONE);	rdPregunta16e.clearCheck();	op16e="No aplica";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16e = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16e = "16e";
            }
            else if (checkedId == R.id.radio3) {
                op16e = "SI";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16e = "NO";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16e = "No sabe / No contestó";
                lay16f .setVisibility(View.GONE);	rdPregunta16f.clearCheck();	op16f="No aplica";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16f = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16f = "16f";
            }
            else if (checkedId == R.id.radio3) {
                op16f = "SI";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16f = "NO";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16f = "No sabe / No contestó";
                lay16g .setVisibility(View.GONE);	rdPregunta16g.clearCheck();	op16g="No aplica";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16g = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16g = "16g";
            }
            else if (checkedId == R.id.radio3) {
                op16g = "SI";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.VISIBLE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16g = "NO";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16g = "No sabe / No contestó";
                lay16h .setVisibility(View.GONE);	rdPregunta16h.clearCheck();	op16h="No aplica";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16h = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16h = "16h";
            }
            else if (checkedId == R.id.radio3) {
                op16h = "SI";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16h = "NO";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16h = "No sabe / No contestó";
                lay16i .setVisibility(View.GONE);	rdPregunta16i.clearCheck();	op16i="No aplica";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16i.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16i = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16i = "16i";
            }
            else if (checkedId == R.id.radio3) {
                op16i = "SI";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16i = "NO";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16i = "No sabe / No contestó";
                lay16j .setVisibility(View.GONE);	rdPregunta16j.clearCheck();	op16j="No aplica";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16j.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16j = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16j = "16j";
            }
            else if (checkedId == R.id.radio3) {
                op16j = "SI";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16j = "NO";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16j = "No sabe / No contestó";
                lay16k .setVisibility(View.GONE);	rdPregunta16k.clearCheck();	op16k="No aplica";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta16k.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op16k = "0";
            }
            else if (checkedId == R.id.radio2) {
                op16k = "16k";
            }
            else if (checkedId == R.id.radio3) {
                op16k = "SI";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio4) {
                op16k = "NO";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
            else if (checkedId == R.id.radio0) {
                op16k = "No sabe / No contestó";
                lay17 .setVisibility(View.GONE);	rdPregunta17.clearCheck();	op17="No aplica";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta17.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op17 = "1";
                editPregunta17a.setText("");
                editPregunta17b.setText("");
                editPregunta17c.setText("");
                editPregunta17d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op17 = "17";
                editPregunta17a.setText("");
                editPregunta17b.setText("");
                editPregunta17c.setText("");
                editPregunta17d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op17 = "No sabe / No contestó";
                editPregunta17a.setText("");
                editPregunta17b.setText("");
                editPregunta17c.setText("");
                editPregunta17d.setText("");
            }
        }
    });
    editPregunta17a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta17a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta17.clearCheck();
            }
        }
    });


    editPregunta17b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta17b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta17.clearCheck();
            }
        }
    });


    editPregunta17c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta17c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta17.clearCheck();
            }
        }
    });


    editPregunta17d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta17d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta17.clearCheck();
            }
        }
    });



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18 = "18";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18a = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18a = "18a";
            }
            else if (checkedId == R.id.radio3) {
                op18a = "SI";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";

                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";

            }
            else if (checkedId == R.id.radio4) {
                op18a = "NO";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";

            }
            else if (checkedId == R.id.radio0) {
                op18a = "No sabe / No contestó";
                lay18b .setVisibility(View.GONE);	rdPregunta18b.clearCheck();	op18b="No aplica";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";

            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18b = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18b = "18b";
            }
            else if (checkedId == R.id.radio3) {
                op18b = "SI";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";

                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                op18b = "NO";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op18b = "No sabe / No contestó";
                lay18c .setVisibility(View.GONE);	rdPregunta18c.clearCheck();	op18c="No aplica";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18c = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18c = "18c";
            }
            else if (checkedId == R.id.radio3) {
                op18c = "SI";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";

                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                op18c = "NO";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op18c = "No sabe / No contestó";
                lay18d .setVisibility(View.GONE);	rdPregunta18d.clearCheck();	op18d="No aplica";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18d = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18d = "18d";
            }
            else if (checkedId == R.id.radio3) {
                op18d = "SI";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";

                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                op18d = "NO";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op18d = "No sabe / No contestó";
                lay18e .setVisibility(View.GONE);	rdPregunta18e.clearCheck();	op18e="No aplica";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18e = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18e = "18e";
            }
            else if (checkedId == R.id.radio3) {
                op18e = "SI";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";

                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                op18e = "NO";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op18e = "No sabe / No contestó";
                lay18f .setVisibility(View.GONE);	rdPregunta18f.clearCheck();	op18f="No aplica";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta18f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op18f = "0";
            }
            else if (checkedId == R.id.radio2) {
                op18f = "18f";
            }
            else if (checkedId == R.id.radio3) {
                op18f = "SI";
                lay19 .setVisibility(View.VISIBLE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.VISIBLE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.VISIBLE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.VISIBLE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.VISIBLE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.VISIBLE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio4) {
                op18f = "NO";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
            else if (checkedId == R.id.radio0) {
                op18f = "No sabe / No contestó";
                lay19 .setVisibility(View.GONE);	rdPregunta19.clearCheck();	op19="sin datos";
                lay19a .setVisibility(View.GONE);	rdPregunta19a.clearCheck();	op19a="sin datos";
                lay19b .setVisibility(View.GONE);	rdPregunta19b.clearCheck();	op19b="sin datos";
                lay19c .setVisibility(View.GONE);	rdPregunta19c.clearCheck();	op19c="sin datos";
                lay19d .setVisibility(View.GONE);	rdPregunta19d.clearCheck();	op19d="sin datos";
                lay19e .setVisibility(View.GONE);	rdPregunta19e.clearCheck();	op19e="sin datos";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op19 = "19";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19a = "1";
                editPregunta19a.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op19a = "19a";
                editPregunta19a.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op19a = "No sabe / No contestó";
                editPregunta19a.setText("");
            }
        }
    });
    editPregunta19a.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta19a.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta19a.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19b = "1";
                editPregunta19b.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op19b = "19b";
                editPregunta19b.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op19b = "No sabe / No contestó";
                editPregunta19b.setText("");
            }
        }
    });
    editPregunta19b.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta19b.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta19b.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19c = "1";
                editPregunta19c.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op19c = "19c";
                editPregunta19c.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op19c = "No sabe / No contestó";
                editPregunta19c.setText("");
            }
        }
    });
    editPregunta19c.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta19c.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta19c.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19d = "1";
                editPregunta19d.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op19d = "19d";
                editPregunta19d.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op19d = "No sabe / No contestó";
                editPregunta19d.setText("");
            }
        }
    });
    editPregunta19d.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta19d.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta19d.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta19e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op19e = "2";
                spinner19e.setSelection(0);
            }
            else if (checkedId == R.id.radio2) {
                op19e = "19e";
                spinner19e.setSelection(0);
            }
            else if (checkedId == R.id.radio0) {
                op19e = "No sabe / No contestó";
                spinner19e.setSelection(0);
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta20.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op20 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op20 = "20";
            }
            else if (checkedId == R.id.radio3) {
                op20 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op20 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op20 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta21.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op21 = "0";
            }
            else if (checkedId == R.id.radio2) {
                op21 = "21";
            }
            else if (checkedId == R.id.radio3) {
                op21 = "SI";
            }
            else if (checkedId == R.id.radio4) {
                op21 = "NO";
            }
            else if (checkedId == R.id.radio0) {
                op21 = "No sabe / No contestó";
            }
        }
    });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta22.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op22 = "1";
                editPregunta22.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op22 = "22";
                editPregunta22.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op22 = "No sabe / No contestó";
                editPregunta22.setText("");
            }
        }
    });
    editPregunta22.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta22.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta22.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta23.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op23 = "1";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op23 = "23";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio3) {
                op23 = "Esposo (a)/ Pareja";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio4) {
                op23 = "Hijo (a)";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio5) {
                op23 = "Padre/ Madre";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio6) {
                op23 = "Abuelo/ abuela";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio7) {
                op23 = "Tío/tía";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio8) {
                op23 = "Otro (registrar)";
                editPregunta23.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op23 = "No sabe / No contestó";
                editPregunta23.setText("");
            }
        }
    });
    editPregunta23.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta23.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta23.clearCheck();
            }
        }
    });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    rdPregunta24.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio1) {
                op24 = "1";
                editPregunta24.setText("");
            }
            else if (checkedId == R.id.radio2) {
                op24 = "24";
                editPregunta24.setText("");
            }
            else if (checkedId == R.id.radio0) {
                op24 = "No sabe / No contestó";
                editPregunta24.setText("");
            }
        }
    });
    editPregunta24.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPregunta24.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable s) {}
        @Override
        public void beforeTextChanged(CharSequence s, int start,int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start,int before, int count) {
            if(s.length() != 0){
                rdPregunta24.clearCheck();
            }
        }
    });



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

    String strText1;
    if(editPregunta1.getText().toString().trim().length()==0){
        strText1=op1;
    }else{
        strText1=editPregunta1.getText().toString().trim();
        rdPregunta1.clearCheck();
    }

    String strText2;
    if(editPregunta2.getText().toString().trim().length()==0){
        strText2=op2;
    }else{
        strText2=editPregunta2.getText().toString().trim();
        rdPregunta2.clearCheck();
    }

    String strText3;
    if(editPregunta3.getText().toString().trim().length()==0){
        strText3=op3;
    }else{
        strText3=editPregunta3.getText().toString().trim();
        rdPregunta3.clearCheck();
    }

    String strText4;
    if(editPregunta4.getText().toString().trim().length()==0){
        strText4=op4;
    }else{
        strText4=editPregunta4.getText().toString().trim();
        rdPregunta4.clearCheck();
    }


    String strText7;
    if(editPregunta7.getText().toString().trim().length()==0){
        strText7=op7;
    }else{
        strText7=editPregunta7.getText().toString().trim();
        rdPregunta7.clearCheck();
    }

    String strText8a;
    if(editPregunta8a.getText().toString().trim().length()==0){
        strText8a=op8a;
    }else{
        strText8a=editPregunta8a.getText().toString().trim();
        rdPregunta8a.clearCheck();
    }

    String strText8b;
    if(editPregunta8b.getText().toString().trim().length()==0){
        strText8b=op8b;
    }else{
        strText8b=editPregunta8b.getText().toString().trim();
        rdPregunta8b.clearCheck();
    }


    String strText8c;
    if(editPregunta8c.getText().toString().trim().length()==0){
        strText8c=op8c;
    }else{
        strText8c=editPregunta8c.getText().toString().trim();
        rdPregunta8c.clearCheck();
    }

    String strText8d;
    if(editPregunta8d.getText().toString().trim().length()==0){
        strText8d=op8d;
    }else{
        strText8d=editPregunta8d.getText().toString().trim();
        rdPregunta8d.clearCheck();
    }

    String strText8e;
    if(editPregunta8e.getText().toString().trim().length()==0){
        strText8e=op8e;
    }else{
        strText8e=editPregunta8e.getText().toString().trim();
        rdPregunta8e.clearCheck();
    }

    String strText8f;
    if(editPregunta8f.getText().toString().trim().length()==0){
        strText8f=op8f;
    }else{
        strText8f=editPregunta8f.getText().toString().trim();
        rdPregunta8f.clearCheck();
    }

    String strText10;
    if(editPregunta10.getText().toString().trim().length()==0){
        strText10=op10;
    }else{
        strText10=editPregunta10.getText().toString().trim();
        rdPregunta10.clearCheck();
    }

    String strText12;
    if(editPregunta12.getText().toString().trim().length()==0){
        strText12=op12;
    }else{
        strText12=editPregunta12.getText().toString().trim();
        rdPregunta12.clearCheck();
    }

    String strText13a;
    if(editPregunta13a.getText().toString().trim().length()==0){
        strText13a=op13a;
    }else{
        strText13a=editPregunta13a.getText().toString().trim();
        rdPregunta13a.clearCheck();
    }

    String strText13b;
    if(editPregunta13b.getText().toString().trim().length()==0){
        strText13b=op13b;
    }else{
        strText13b=editPregunta13b.getText().toString().trim();
        rdPregunta13b.clearCheck();
    }

    String strText13c;
    if(editPregunta13c.getText().toString().trim().length()==0){
        strText13c=op13c;
    }else{
        strText13c=editPregunta13c.getText().toString().trim();
        rdPregunta13c.clearCheck();
    }

    String strText13d;
    if(editPregunta13d.getText().toString().trim().length()==0){
        strText13d=op13d;
    }else{
        strText13d=editPregunta13d.getText().toString().trim();
        rdPregunta13d.clearCheck();
    }

    String strText13e;
    if(spinner13e.getSelectedItem().toString().equals("Selecciona")){
        strText13e=op13e;
    }else{
        strText13e=spinner13e.getSelectedItem().toString();
        rdPregunta13e.clearCheck();
    }

    String strText17a;
    if(editPregunta17a.getText().toString().trim().length()==0){
        strText17a=op17;
    }else{
        strText17a=editPregunta17a.getText().toString().trim();
        rdPregunta17.clearCheck();
    }

    String strText17b;
    if(editPregunta17b.getText().toString().trim().length()==0){
        strText17b=op17;
    }else{
        strText17b=editPregunta17b.getText().toString().trim();
        rdPregunta17.clearCheck();
    }

    String strText17c;
    if(editPregunta17c.getText().toString().trim().length()==0){
        strText17c=op17;
    }else{
        strText17c=editPregunta17c.getText().toString().trim();
        rdPregunta17.clearCheck();
    }

    String strText17d;
    if(editPregunta17d.getText().toString().trim().length()==0){
        strText17d=op17;
    }else{
        strText17d=editPregunta17d.getText().toString().trim();
        rdPregunta17.clearCheck();
    }

    String strText19a;
    if(editPregunta19a.getText().toString().trim().length()==0){
        strText19a=op19a;
    }else{
        strText19a=editPregunta19a.getText().toString().trim();
        rdPregunta19a.clearCheck();
    }

    String strText19b;
    if(editPregunta19b.getText().toString().trim().length()==0){
        strText19b=op19b;
    }else{
        strText19b=editPregunta19b.getText().toString().trim();
        rdPregunta19b.clearCheck();
    }

    String strText19c;
    if(editPregunta19c.getText().toString().trim().length()==0){
        strText19c=op19c;
    }else{
        strText19c=editPregunta19c.getText().toString().trim();
        rdPregunta19c.clearCheck();
    }

    String strText19d;
    if(editPregunta19d.getText().toString().trim().length()==0){
        strText19d=op19d;
    }else{
        strText19d=editPregunta19d.getText().toString().trim();
        rdPregunta19d.clearCheck();
    }

    String strText19e;
    if(spinner19e.getSelectedItem().toString().equals("Selecciona")){
        strText19e=op19e;
    }else{
        strText19e=spinner19e.getSelectedItem().toString();
        rdPregunta19e.clearCheck();
    }

    String strText22;
    if(editPregunta22.getText().toString().trim().length()==0){
        strText22=op22;
    }else{
        strText22=editPregunta22.getText().toString().trim();
        rdPregunta22.clearCheck();
    }

    String strText23;
    if(editPregunta23.getText().toString().trim().length()==0){
        strText23=op23;
    }else{
        strText23=editPregunta23.getText().toString().trim();
        rdPregunta23.clearCheck();
    }


    String strText24;
    if(editPregunta24.getText().toString().trim().length()==0){
        strText24=op24;
    }else{
        strText24=editPregunta24.getText().toString().trim();
        rdPregunta24.clearCheck();
    }


    String strA = opA;
    String strB = opB;
    String str1 = strText1;
    String str2 = strText2;
    String str3 = strText3;
    String str4 = strText4;
    String str5 = op5;
    String str6 = "Tiene alguna de las siguientes condiciones";
    String str6a = op6a;
    String str6b = op6b;
    String str6c = op6c;
    String str6d = op6d;
    String str6e = op6e;
    String str6f = op6f;
    String str6g = op6g;
    String str6h = op6h;
    String str6i = op6i;
    String str6j = op6j;
    String str6k = op6k;
    String str7 = strText7;
    String str8 = op8;
    String str8a = strText8a;
    String str8b = strText8b;
    String str8c = strText8c;
    String str8d = strText8d;
    String str8e = strText8e;
    String str8f = strText8f;
    String str9 = op9;
    String str10 = strText10;
    String str11 = op11;
    String str12 = strText12;
    String str13 = "En ...";
    String str13a = strText13a;
    String str13b = strText13b;
    String str13c = strText13c;
    String str13d = strText13d;
    String str13e = strText13e;
    String str14 = op14;
    String str15 = op15;
    String str16 = op16;
    String str16a = op16a;
    String str16b = op16b;
    String str16c = op16c;
    String str16d = op16d;
    String str16e = op16e;
    String str16f = op16f;
    String str16g = op16g;
    String str16h = op16h;
    String str16i = op16i;
    String str16j = op16j;
    String str16k = op16k;
    String str17a = strText17a;
    String str17b = strText17b;
    String str17c = strText17c;
    String str17d = strText17d;
    String str18 = op18;
    String str18a = op18a;
    String str18b = op18b;
    String str18c = op18c;
    String str18d = op18d;
    String str18e = op18e;
    String str18f = op18f;
    String str19 = op19;
    String str19a = strText19a;
    String str19b = strText19b;
    String str19c = strText19c;
    String str19d = strText19d;
    String str19e = strText19e;
    String str20 = op20;
    String str21 = op21;
    String str22 = strText22;
    String str23 = strText23;
    String str24 = strText24;


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

            values.put("pregunta_a",strA);
            values.put("pregunta_b",strB);
            values.put("pregunta_1",str1);
            values.put("pregunta_2",str2);
            values.put("pregunta_3",str3);
            values.put("pregunta_4",str4);
            values.put("pregunta_5",str5);
            values.put("pregunta_6",str6);
            values.put("pregunta_6a",str6a);
            values.put("pregunta_6b",str6b);
            values.put("pregunta_6c",str6c);
            values.put("pregunta_6d",str6d);
            values.put("pregunta_6e",str6e);
            values.put("pregunta_6f",str6f);
            values.put("pregunta_6g",str6g);
            values.put("pregunta_6h",str6h);
            values.put("pregunta_6i",str6i);
            values.put("pregunta_6j",str6j);
            values.put("pregunta_6k",str6k);
            values.put("pregunta_7",str7);
            values.put("pregunta_8",str8);
            values.put("pregunta_8a",str8a);
            values.put("pregunta_8b",str8b);
            values.put("pregunta_8c",str8c);
            values.put("pregunta_8d",str8d);
            values.put("pregunta_8e",str8e);
            values.put("pregunta_8f",str8f);
            values.put("pregunta_9",str9);
            values.put("pregunta_10",str10);
            values.put("pregunta_11",str11);
            values.put("pregunta_12",str12);
            values.put("pregunta_13",str13);
            values.put("pregunta_13a",str13a);
            values.put("pregunta_13b",str13b);
            values.put("pregunta_13c",str13c);
            values.put("pregunta_13d",str13d);
            values.put("pregunta_13e",str13e);
            values.put("pregunta_14",str14);
            values.put("pregunta_15",str15);
            values.put("pregunta_16",str16);
            values.put("pregunta_16a",str16a);
            values.put("pregunta_16b",str16b);
            values.put("pregunta_16c",str16c);
            values.put("pregunta_16d",str16d);
            values.put("pregunta_16e",str16e);
            values.put("pregunta_16f",str16f);
            values.put("pregunta_16g",str16g);
            values.put("pregunta_16h",str16h);
            values.put("pregunta_16i",str16i);
            values.put("pregunta_16j",str16j);
            values.put("pregunta_16k",str16k);
            values.put("pregunta_17a",str17a);
            values.put("pregunta_17b",str17b);
            values.put("pregunta_17c",str17c);
            values.put("pregunta_17d",str17d);
            values.put("pregunta_18",str18);
            values.put("pregunta_18a",str18a);
            values.put("pregunta_18b",str18b);
            values.put("pregunta_18c",str18c);
            values.put("pregunta_18d",str18d);
            values.put("pregunta_18e",str18e);
            values.put("pregunta_18f",str18f);
            values.put("pregunta_19",str19);
            values.put("pregunta_19a",str19a);
            values.put("pregunta_19b",str19b);
            values.put("pregunta_19c",str19c);
            values.put("pregunta_19d",str19d);
            values.put("pregunta_19e",str19e);
            values.put("pregunta_20",str20);
            values.put("pregunta_21",str21);
            values.put("pregunta_22",str22);
            values.put("pregunta_23",str23);
            values.put("pregunta_24",str24);

            values.put("abandono", strAbandono.toUpperCase());
            values.put("tiempo", elTiempo());
            values.put("tipo_captura", tipoEncuesta);


            if (!verificaConexion(this)) {
                Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                values.put("enviado", "0");
                db.insert("contactos", null, values);
            }else{
                values.put("enviado", "1");
                consecutivoObtenido = db.insert("contactos", null, values);
            }
        }
//        db.close();

        values.put("consecutivo", consecutivoObtenido);

        guardaEncuestaWS(values);


        System.out.println("consecutivo_diario " + elMaximo);
        System.out.println("usuario " + cachaNombre().toUpperCase());
        System.out.println("nombre_encuesta " + nombreE.toUpperCase());
        System.out.println("fecha " + formattedDate1);
        System.out.println("hora " + formattedDate5);
        System.out.println("imei " + sacaImei());
        System.out.println("Seccion " + str);
        System.out.println("Latitud  " + strLatitud);
        System.out.println("Longitud  " + strLongitud);

        System.out.println("pregunta_a  " +   strA);
        System.out.println("pregunta_b " +   strB);
        System.out.println("pregunta_1  " +   str1);
        System.out.println("pregunta_2  " +   str2);
        System.out.println("pregunta_3  " +   str3);
        System.out.println("pregunta_4  " +   str4);
        System.out.println("pregunta_5  " +   str5);
        System.out.println("pregunta_6  " +   str6);
        System.out.println("pregunta_6a  " +   str6a);
        System.out.println("pregunta_6b  " +   str6b);
        System.out.println("pregunta_6c  " +   str6c);
        System.out.println("pregunta_6d  " +   str6d);
        System.out.println("pregunta_6e  " +   str6e);
        System.out.println("pregunta_6f  " +   str6f);
        System.out.println("pregunta_6g  " +   str6g);
        System.out.println("pregunta_6h  " +   str6h);
        System.out.println("pregunta_6i  " +   str6i);
        System.out.println("pregunta_6j  " +   str6j);
        System.out.println("pregunta_6k  " +   str6k);
        System.out.println("pregunta_7  " +   str7);
        System.out.println("pregunta_8  " +   str8);
        System.out.println("pregunta_8a  " +   str8a);
        System.out.println("pregunta_8b  " +   str8b);
        System.out.println("pregunta_8c  " +   str8c);
        System.out.println("pregunta_8d  " +   str8d);
        System.out.println("pregunta_8e  " +   str8e);
        System.out.println("pregunta_8f  " +   str8f);
        System.out.println("pregunta_9  " +   str9);
        System.out.println("pregunta_10  " +   str10);
        System.out.println("pregunta_11  " +   str11);
        System.out.println("pregunta_12  " +   str12);
        System.out.println("pregunta_13  " +   str13);
        System.out.println("pregunta_13a  " +   str13a);
        System.out.println("pregunta_13b  " +   str13b);
        System.out.println("pregunta_13c  " +   str13c);
        System.out.println("pregunta_13d  " +   str13d);
        System.out.println("pregunta_13e  " +   str13e);
        System.out.println("pregunta_14  " +   str14);
        System.out.println("pregunta_15  " +   str15);
        System.out.println("pregunta_16  " +   str16);
        System.out.println("pregunta_16a  " +   str16a);
        System.out.println("pregunta_16b  " +   str16b);
        System.out.println("pregunta_16c  " +   str16c);
        System.out.println("pregunta_16d  " +   str16d);
        System.out.println("pregunta_16e  " +   str16e);
        System.out.println("pregunta_16f  " +   str16f);
        System.out.println("pregunta_16g  " +   str16g);
        System.out.println("pregunta_16h  " +   str16h);
        System.out.println("pregunta_16i  " +   str16i);
        System.out.println("pregunta_16j  " +   str16j);
        System.out.println("pregunta_16k  " +   str16k);
        System.out.println("pregunta_17a  " +   str17a);
        System.out.println("pregunta_17b  " +   str17b);
        System.out.println("pregunta_17c  " +   str17c);
        System.out.println("pregunta_17d  " +   str17d);
        System.out.println("pregunta_18  " +   str18);
        System.out.println("pregunta_18a  " +   str18a);
        System.out.println("pregunta_18b  " +   str18b);
        System.out.println("pregunta_18c  " +   str18c);
        System.out.println("pregunta_18d  " +   str18d);
        System.out.println("pregunta_18e  " +   str18e);
        System.out.println("pregunta_18f  " +   str18f);
        System.out.println("pregunta_19  " +   str19);
        System.out.println("pregunta_19a  " +   str19a);
        System.out.println("pregunta_19b  " +   str19b);
        System.out.println("pregunta_19c  " +   str19c);
        System.out.println("pregunta_19d  " +   str19d);
        System.out.println("pregunta_19e  " +   str19e);
        System.out.println("pregunta_20  " +   str20);
        System.out.println("pregunta_21  " +   str21);
        System.out.println("pregunta_22  " +   str22);
        System.out.println("pregunta_23  " +   str23);
        System.out.println("pregunta_24  " +   str24);


        System.out.println("abandono  " + strAbandono);


// FIN INSERTA BASE DE DATOS //

    } catch (Exception e) {
        String stackTrace = Log.getStackTraceString(e);
        Log.i(TAG,"Error Inserta Base"+ stackTrace);
    }

}

private void guardaEncuestaWS(ContentValues values){

    showProgress(true);

    final String strText10;
    if(editPregunta10.getText().toString().trim().length()==0){
        strText10=op10;
    }else{
        strText10=editPregunta10.getText().toString().trim();
        rdPregunta10.clearCheck();
    }

//RECORRE CONTENT VALUES
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
    params.put("encuesta", contactos);
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

                        Log.i(TAG, "cqs -----------> con cuantos vive " + strText10);
                        if(strText10.equals("0")||strText10.equals("No sabe / No contestó")||strText10.equals("sin datos")||strText10.equals("")||strText10.equals("No abren/ no hay nadie")){
                            dialogo();
                        }else{
                            dialogoAdicionales();
                        }


} else {
    Toast.makeText(MainActivityPantalla1.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
}
}

} catch (Exception e) {
    showProgress(false);
    Log.e(TAG, e.getMessage());
    Toast.makeText(MainActivityPantalla1.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
}
}

@Override
public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
    showProgress(false);
    try {
        Log.e(TAG, "PIMC-----------------> existe un error en la conexión -----> " + error.getMessage());
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

    Toast.makeText(MainActivityPantalla1.this, "Error de conexion, Se guarda en la base interna", Toast.LENGTH_SHORT).show();
    btnGuardar.setEnabled(true);

    Log.i(TAG, "cqs -----------> con cuantos vive: " + strText10);
    if(strText10.equals("0")||strText10.equals("No sabe / No contestó")||strText10.equals("sin datos")||strText10.equals("")||strText10.equals("No abren/ no hay nadie")){
        dialogo();
    }else{
        dialogoAdicionales();
    }


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

            if (layA.getVisibility() == View.VISIBLE && opA.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturaA,Toast.LENGTH_LONG).show();}
            else if (layB.getVisibility() == View.VISIBLE && opB.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturaB,Toast.LENGTH_LONG).show();}
            else if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos") && editPregunta1.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura1,Toast.LENGTH_LONG).show();}
            else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos") && editPregunta2.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura2,Toast.LENGTH_LONG).show();}
            else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos") && editPregunta3.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura3,Toast.LENGTH_LONG).show();}
            else if (lay4.getVisibility() == View.VISIBLE && op4.matches("sin datos") && editPregunta4.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura4,Toast.LENGTH_LONG).show();}
            else if (lay5.getVisibility() == View.VISIBLE && op5.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura5,Toast.LENGTH_LONG).show();}
//            else if (lay6.getVisibility() == View.VISIBLE && op6.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6,Toast.LENGTH_LONG).show();}
            else if (lay6a.getVisibility() == View.VISIBLE && op6a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6a,Toast.LENGTH_LONG).show();}
            else if (lay6b.getVisibility() == View.VISIBLE && op6b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6b,Toast.LENGTH_LONG).show();}
            else if (lay6c.getVisibility() == View.VISIBLE && op6c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6c,Toast.LENGTH_LONG).show();}
            else if (lay6d.getVisibility() == View.VISIBLE && op6d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6d,Toast.LENGTH_LONG).show();}
            else if (lay6e.getVisibility() == View.VISIBLE && op6e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6e,Toast.LENGTH_LONG).show();}
            else if (lay6f.getVisibility() == View.VISIBLE && op6f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6f,Toast.LENGTH_LONG).show();}
            else if (lay6g.getVisibility() == View.VISIBLE && op6g.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6g,Toast.LENGTH_LONG).show();}
            else if (lay6h.getVisibility() == View.VISIBLE && op6h.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6h,Toast.LENGTH_LONG).show();}
            else if (lay6i.getVisibility() == View.VISIBLE && op6i.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6i,Toast.LENGTH_LONG).show();}
            else if (lay6j.getVisibility() == View.VISIBLE && op6j.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6j,Toast.LENGTH_LONG).show();}
            else if (lay6k.getVisibility() == View.VISIBLE && op6k.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura6k,Toast.LENGTH_LONG).show();}
            else if (lay7.getVisibility() == View.VISIBLE && op7.matches("sin datos") && editPregunta7.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura7,Toast.LENGTH_LONG).show();}
//            else if (lay8.getVisibility() == View.VISIBLE && op8.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8,Toast.LENGTH_LONG).show();}
            else if (lay8a.getVisibility() == View.VISIBLE && op8a.matches("sin datos") && editPregunta8a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8a,Toast.LENGTH_LONG).show();}
            else if (lay8b.getVisibility() == View.VISIBLE && op8b.matches("sin datos") && editPregunta8b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8b,Toast.LENGTH_LONG).show();}
            else if (lay8c.getVisibility() == View.VISIBLE && op8c.matches("sin datos") && editPregunta8c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8c,Toast.LENGTH_LONG).show();}
            else if (lay8d.getVisibility() == View.VISIBLE && op8d.matches("sin datos") && editPregunta8d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8d,Toast.LENGTH_LONG).show();}
            else if (lay8e.getVisibility() == View.VISIBLE && op8e.matches("sin datos") && editPregunta8e.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8e,Toast.LENGTH_LONG).show();}
            else if (lay8f.getVisibility() == View.VISIBLE && op8f.matches("sin datos") && editPregunta8f.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura8f,Toast.LENGTH_LONG).show();}
            else if (lay9.getVisibility() == View.VISIBLE && op9.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura9,Toast.LENGTH_LONG).show();}
            else if (lay10.getVisibility() == View.VISIBLE && op10.matches("sin datos") && editPregunta10.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura10,Toast.LENGTH_LONG).show();}
            else if (lay11.getVisibility() == View.VISIBLE && op11.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura11,Toast.LENGTH_LONG).show();}
            else if (lay12.getVisibility() == View.VISIBLE && op12.matches("sin datos") && editPregunta12.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura12,Toast.LENGTH_LONG).show();}
//            else if (lay13.getVisibility() == View.VISIBLE && op13.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13,Toast.LENGTH_LONG).show();}
            else if (lay13a.getVisibility() == View.VISIBLE && op13a.matches("sin datos") && editPregunta13a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13a,Toast.LENGTH_LONG).show();}
            else if (lay13b.getVisibility() == View.VISIBLE && op13b.matches("sin datos") && editPregunta13b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13b,Toast.LENGTH_LONG).show();}
            else if (lay13c.getVisibility() == View.VISIBLE && op13c.matches("sin datos") && editPregunta13c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13c,Toast.LENGTH_LONG).show();}
            else if (lay13d.getVisibility() == View.VISIBLE && op13d.matches("sin datos") && editPregunta13d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13d,Toast.LENGTH_LONG).show();}
            else if (lay13e.getVisibility() == View.VISIBLE && op13e.matches("sin datos") && spinner13e.getSelectedItem().toString().equals("Selecciona") ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura13e,Toast.LENGTH_LONG).show();}
            else if (lay14.getVisibility() == View.VISIBLE && op14.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura14,Toast.LENGTH_LONG).show();}
            else if (lay15.getVisibility() == View.VISIBLE && op15.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura15,Toast.LENGTH_LONG).show();}
//            else if (lay16.getVisibility() == View.VISIBLE && op16.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16,Toast.LENGTH_LONG).show();}
            else if (lay16a.getVisibility() == View.VISIBLE && op16a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16a,Toast.LENGTH_LONG).show();}
            else if (lay16b.getVisibility() == View.VISIBLE && op16b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16b,Toast.LENGTH_LONG).show();}
            else if (lay16c.getVisibility() == View.VISIBLE && op16c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16c,Toast.LENGTH_LONG).show();}
            else if (lay16d.getVisibility() == View.VISIBLE && op16d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16d,Toast.LENGTH_LONG).show();}
            else if (lay16e.getVisibility() == View.VISIBLE && op16e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16e,Toast.LENGTH_LONG).show();}
            else if (lay16f.getVisibility() == View.VISIBLE && op16f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16f,Toast.LENGTH_LONG).show();}
            else if (lay16g.getVisibility() == View.VISIBLE && op16g.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16g,Toast.LENGTH_LONG).show();}
            else if (lay16h.getVisibility() == View.VISIBLE && op16h.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16h,Toast.LENGTH_LONG).show();}
            else if (lay16i.getVisibility() == View.VISIBLE && op16i.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16i,Toast.LENGTH_LONG).show();}
            else if (lay16j.getVisibility() == View.VISIBLE && op16j.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16j,Toast.LENGTH_LONG).show();}
            else if (lay16k.getVisibility() == View.VISIBLE && op16k.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura16k,Toast.LENGTH_LONG).show();}
            else if (lay17.getVisibility() == View.VISIBLE && op17.matches("sin datos")
                    && editPregunta17a.getText().toString().trim().length() == 0
                    && editPregunta17b.getText().toString().trim().length() == 0
                    && editPregunta17c.getText().toString().trim().length() == 0
                    && editPregunta17d.getText().toString().trim().length() == 0
            ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura17,Toast.LENGTH_LONG).show();}
//            else if (lay18.getVisibility() == View.VISIBLE && op18.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18,Toast.LENGTH_LONG).show();}
            else if (lay18a.getVisibility() == View.VISIBLE && op18a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18a,Toast.LENGTH_LONG).show();}
            else if (lay18b.getVisibility() == View.VISIBLE && op18b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18b,Toast.LENGTH_LONG).show();}
            else if (lay18c.getVisibility() == View.VISIBLE && op18c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18c,Toast.LENGTH_LONG).show();}
            else if (lay18d.getVisibility() == View.VISIBLE && op18d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18d,Toast.LENGTH_LONG).show();}
            else if (lay18e.getVisibility() == View.VISIBLE && op18e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18e,Toast.LENGTH_LONG).show();}
            else if (lay18f.getVisibility() == View.VISIBLE && op18f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura18f,Toast.LENGTH_LONG).show();}
//            else if (lay19.getVisibility() == View.VISIBLE && op19.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19,Toast.LENGTH_LONG).show();}
            else if (lay19a.getVisibility() == View.VISIBLE && op19a.matches("sin datos") && editPregunta19a.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19a,Toast.LENGTH_LONG).show();}
            else if (lay19b.getVisibility() == View.VISIBLE && op19b.matches("sin datos") && editPregunta19b.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19b,Toast.LENGTH_LONG).show();}
            else if (lay19c.getVisibility() == View.VISIBLE && op19c.matches("sin datos") && editPregunta19c.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19c,Toast.LENGTH_LONG).show();}
            else if (lay19d.getVisibility() == View.VISIBLE && op19d.matches("sin datos") && editPregunta19d.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19d,Toast.LENGTH_LONG).show();}
            else if (lay19e.getVisibility() == View.VISIBLE && op19e.matches("sin datos") && spinner19e.getSelectedItem().toString().equals("Selecciona") ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura19e,Toast.LENGTH_LONG).show();}
            else if (lay20.getVisibility() == View.VISIBLE && op20.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura20,Toast.LENGTH_LONG).show();}
            else if (lay21.getVisibility() == View.VISIBLE && op21.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura21,Toast.LENGTH_LONG).show();}
            else if (lay22.getVisibility() == View.VISIBLE && op22.matches("sin datos") && editPregunta22.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura22,Toast.LENGTH_LONG).show();}
            else if (lay23.getVisibility() == View.VISIBLE && op23.matches("sin datos") && editPregunta23.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura23,Toast.LENGTH_LONG).show();}
            else if (lay24.getVisibility() == View.VISIBLE && op24.matches("sin datos") && editPregunta24.getText().toString().trim().length() == 0 ){Toast.makeText(getBaseContext(),"CAPTURA:  " +  captura24,Toast.LENGTH_LONG).show();}

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

                if (op5.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + captura5, Toast.LENGTH_LONG).show();
            }
            else {
                valores();
                btnGuardar.setEnabled(false);
//                dialogo();
            }

            break;

            case 3:

            if (op5.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + captura5, Toast.LENGTH_LONG).show();
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
    String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_"
    + sacaImei() + "";
    usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

    db = usdbh.getReadableDatabase();

    String selectQuery = "SELECT count(*) FROM contactos where fecha='" + formattedDate1 + "'";

    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
        do {

            maximo = cursor.getString(0);

        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

    return maximo;
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

    String selectQuery = "SELECT count(*) FROM contactos order by id desc limit 1";

    Cursor cursor = db.rawQuery(selectQuery, null);

    if (cursor.moveToFirst()) {
        do {

            consecutivo = cursor.getString(0);

        } while (cursor.moveToNext());
    }

    cursor.close();
    db.close();

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
        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
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
        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

//				rdPregunta10.clearCheck();
//				editPregunta10.setText("");

        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}

/////////////// SPINNER /////////////////
public void CargaSpinnerAlcaldia13e() {
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
    spinner13e.setAdapter(adaptador);
    spinner13e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
        }

        public void onNothingSelected(AdapterView<?> parent) {
        }
    });
}

    public void CargaSpinnerAlcaldia19e() {
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
        spinner19e.setAdapter(adaptador);
        spinner19e.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
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
    db3.close();

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
    db3.close();

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
