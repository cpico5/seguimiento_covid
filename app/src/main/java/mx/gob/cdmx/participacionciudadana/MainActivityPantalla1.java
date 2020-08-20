package mx.gob.cdmx.participacionciudadana;

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
import java.util.TimerTask;

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
import android.util.LayoutDirection;
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
import mx.gob.cdmx.participacionciudadana.model.DatoContent;
import mx.gob.cdmx.participacionciudadana.model.Usuario;
import mx.gob.cdmx.participacionciudadana.service.AppLog;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static mx.gob.cdmx.participacionciudadana.Nombre.USUARIO;
import static mx.gob.cdmx.participacionciudadana.Nombre.customURL;
import static mx.gob.cdmx.participacionciudadana.Nombre.encuesta;

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
    private Spinner spinner63;
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


    public String op1="sin datos";
    public String op2="sin datos";
    public String op3="sin datos";

    public RadioGroup rdPregunta1;
    public RadioGroup rdPregunta2;
    public RadioGroup rdPregunta3;


    public EditText editPregunta1;
    public EditText editPregunta2;
    public EditText editPregunta3;

    public EditText editPreguntaCalle;
    public EditText editPreguntaExterior;
    public EditText editPreguntaInterior;
    public EditText editPreguntaManzana;
    public EditText editPreguntaLote;
    public EditText editPreguntaCalle2;
    public EditText editPreguntaExterior2;
    public EditText editPreguntaInterior2;
    public EditText editPreguntaManzana2;
    public EditText editPreguntaLote2;
    public EditText editPreguntaTelefono;

    public EditText editPreguntaPaterno;
    public EditText editPreguntaMaterno;
    public EditText editPreguntaNombres;
    public EditText editPreguntaTelefonoSospecha;
    public EditText editPreguntaPaterno2;
    public EditText editPreguntaMaterno2;
    public EditText editPreguntaNombres2;
    public EditText editPreguntaTelefonoSospecha2;

    public String captura1;
    public String captura2;
    public String captura3;
    public String capturaUrgente;

    LinearLayout lay1;
    LinearLayout lay2;
    LinearLayout lay3;
    LinearLayout layDireccion;
    LinearLayout layDatosSospechoso;
    LinearLayout layDireccion2;
    LinearLayout layDatosSospechoso2;
    LinearLayout layObservaciones;
    LinearLayout layTelefono;
    LinearLayout layUrgente;

    public EditText editTelefono;


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
        final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo + ".mp3";
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

    rdPregunta1 = (RadioGroup)findViewById(R.id.rdPregunta1);
    rdPregunta2 = (RadioGroup)findViewById(R.id.rdPregunta2);
    rdPregunta3 = (RadioGroup)findViewById(R.id.rdPregunta3);
    rdPreguntaUrgente = (RadioGroup) findViewById(R.id.rdPreguntaUrgente);

    editPreguntaPaterno = (EditText) findViewById(R.id.editPreguntapaterno);
    editPreguntaMaterno = (EditText) findViewById(R.id.editPreguntaMaterno);
    editPreguntaNombres = (EditText) findViewById(R.id.editPreguntaNombres);
    editPreguntaTelefonoSospecha = (EditText) findViewById(R.id.editPreguntaTelefonoSospechoso);
    editPreguntaPaterno2 = (EditText) findViewById(R.id.editPreguntapaterno2);
    editPreguntaMaterno2 = (EditText) findViewById(R.id.editPreguntaMaterno2);
    editPreguntaNombres2 = (EditText) findViewById(R.id.editPreguntaNombres2);
    editPreguntaTelefonoSospecha2 = (EditText) findViewById(R.id.editPreguntaTelefonoSospechoso2);

    editPreguntaCalle = (EditText) findViewById(R.id.editPreguntacalle);
    editPreguntaExterior = (EditText) findViewById(R.id.editPreguntaExterior);
    editPreguntaInterior = (EditText) findViewById(R.id.editPreguntaInterior);
    editPreguntaManzana = (EditText) findViewById(R.id.editPreguntaManzana);
    editPreguntaLote = (EditText) findViewById(R.id.editPreguntaLote);
    editPreguntaCalle2 = (EditText) findViewById(R.id.editPreguntacalle2);
    editPreguntaExterior2 = (EditText) findViewById(R.id.editPreguntaExterior2);
    editPreguntaInterior2 = (EditText) findViewById(R.id.editPreguntaInterior2);
    editPreguntaManzana2 = (EditText) findViewById(R.id.editPreguntaManzana2);
    editPreguntaLote2 = (EditText) findViewById(R.id.editPreguntaLote2);
    editObservaciones = (EditText) findViewById(R.id.editPreguntaObservaciones);
    editPreguntaTelefono = (EditText) findViewById(R.id.editPreguntaTelefono);

    captura1 =res.getString(R.string.PREGUNTA1);
    captura2 =res.getString(R.string.PREGUNTA2);
    captura3 =res.getString(R.string.PREGUNTA3);
    capturaUrgente =res.getString(R.string.PREGUNTA_URGENCIA);

    lay1 = (LinearLayout) findViewById(R.id.lay1);
    lay2 = (LinearLayout) findViewById(R.id.lay2);
    lay3 = (LinearLayout) findViewById(R.id.lay3);
    layDireccion = (LinearLayout) findViewById(R.id.layDireccion);
    layDatosSospechoso = (LinearLayout) findViewById(R.id.layDatosSospechoso);
    layDireccion2 = (LinearLayout) findViewById(R.id.layDireccion2);
    layDatosSospechoso2 = (LinearLayout) findViewById(R.id.layDatosSospechoso2);
    layObservaciones= (LinearLayout) findViewById(R.id.layObservaciones);
    layTelefono= (LinearLayout) findViewById(R.id.layTelefono);
    layUrgente= (LinearLayout) findViewById(R.id.layUrgente);

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

    layDireccion.setVisibility(View.GONE);
    layDatosSospechoso.setVisibility(View.GONE);
    layDireccion2.setVisibility(View.GONE);
    layDatosSospechoso2.setVisibility(View.GONE);

    radio_abandono1 = (RadioButton) findViewById(R.id.radio_abandono1);
    radio_abandono2 = (RadioButton) findViewById(R.id.radio_abandono2);
    radio_abandono3 = (RadioButton) findViewById(R.id.radio_abandono3);
    radio_abandono4 = (RadioButton) findViewById(R.id.radio_abandono4);


    editTelefono = (EditText) findViewById(R.id.editTelefono);

    rdPreguntaAporta = (RadioGroup) findViewById(R.id.rdPreguntaAporta);
    rdPreguntaAbandono = (RadioGroup) findViewById(R.id.rdPreguntaAbandono);
    rdPreguntaOcupacion = (RadioGroup) findViewById(R.id.rdPreguntaOcupacion);
    rdPreguntaCuantosCoches = (RadioGroup) findViewById(R.id.rdPreguntaCuantosCoches);
    rdPreguntaCuartos = (RadioGroup) findViewById(R.id.rdPreguntaCuartos);
    rdPreguntaCuartosDormir = (RadioGroup) findViewById(R.id.rdPreguntaCuartosDormir);
    rdPreguntaFocos = (RadioGroup) findViewById(R.id.rdPreguntaFocos);
    rdPreguntaBanos = (RadioGroup) findViewById(R.id.rdPreguntaBanos);
    rdPreguntaRegadera = (RadioGroup) findViewById(R.id.rdPreguntaRegadera);
    rdPreguntaEstufa = (RadioGroup) findViewById(R.id.rdPreguntaEstufa);
    rdPreguntaEdad = (RadioGroup) findViewById(R.id.rdPreguntaEdad);
    rdPreguntaGenero = (RadioGroup) findViewById(R.id.rdPreguntaGenero);

    rdPreguntaTipoVivienda = (RadioGroup) findViewById(R.id.rdPreguntaTipoVivienda);
    rdPreguntaTipoPiso = (RadioGroup) findViewById(R.id.rdPreguntaTipoPiso);


    capturaAporta = res.getString(R.string.PREGUNTAAPORTA);
    capturaOcupacion = res.getString(R.string.PREGUNTAOCUPACION);
    capturaCuantosCoches = res.getString(R.string.PREGUNTACUANTOSCOCHES);
    capturaFocos = res.getString(R.string.PREGUNTAFOCOS);
    capturaCuartos = res.getString(R.string.PREGUNTACUARTOS);
    capturaCuartosDormir = res.getString(R.string.PREGUNTACUARTOSDORMIR);
    capturaBanos = res.getString(R.string.PREGUNTABANOS);
    capturaEstufa = res.getString(R.string.PREGUNTAESTUFA);
    capturaEdad = res.getString(R.string.PREGUNTAEDAD);
    capturaGenero = res.getString(R.string.PREGUNTAGENERO);
    capturaTipoVivienda = res.getString(R.string.PREGUNTA_TIPO_VIVIENDA);
    capturaTipoPiso = res.getString(R.string.PREGUNTA_TIPO_PISO);
    capturaObservaciones = res.getString(R.string.PREGUNTA_OBSERVACIONES);
    capturaCalle = res.getString(R.string.PREGUNTA_CALLE);
    capturaExterior = res.getString(R.string.PREGUNTA_EXTERIOR);
    capturaInterior = res.getString(R.string.PREGUNTA_INTERIOR);
    capturaTelefono = res.getString(R.string.PREGUNTA_TELEFONO);
    capturaPaterno = res.getString(R.string.PREGUNTA_PATERNO);
    capturaMaterno = res.getString(R.string.PREGUNTA_MATERNO);
    capturaNombres = res.getString(R.string.PREGUNTA_NOMBRES);
    getCapturaTelefonoSospechoso = res.getString(R.string.PREGUNTA_TELEFONO_SOSPECHA);


    btnGuardar = (Button) findViewById(R.id.btnGuardar);
    btnSalir = (Button) findViewById(R.id.btnSalir);
    btnSalir.setEnabled(false);
    btnSalir.setVisibility(View.GONE);

    btnPaterno = (Button) findViewById(R.id.btnPaterno);
    btnMaterno = (Button) findViewById(R.id.btnMaterno);
    btnNombres = (Button) findViewById(R.id.btnNombres);
    btnTelefonoSospecha = (Button) findViewById(R.id.btnTelefonoSospecha);
    btnCalle = (Button) findViewById(R.id.btnCalle);
    btnExterior = (Button) findViewById(R.id.btnExterior);
    btnInterior = (Button) findViewById(R.id.btnInterior);
    btnManzana = (Button) findViewById(R.id.btnManzana);
    btnLote = (Button) findViewById(R.id.btnLote);

    btnPaterno2 = (Button) findViewById(R.id.btnPaterno2);
    btnMaterno2 = (Button) findViewById(R.id.btnMaterno2);
    btnNombres2 = (Button) findViewById(R.id.btnNombres2);
    btnTelefonoSospecha2 = (Button) findViewById(R.id.btnTelefonoSospecha2);
    btnCalle2 = (Button) findViewById(R.id.btnCalle2);
    btnExterior2 = (Button) findViewById(R.id.btnExterior2);
    btnInterior2 = (Button) findViewById(R.id.btnInterior2);
    btnManzana2 = (Button) findViewById(R.id.btnManzana2);
    btnLote2 = (Button) findViewById(R.id.btnLote2);

    btnTelefonoContacto = (Button) findViewById(R.id.btnTelefonoContacto);

    textSpeetch = (TextView) findViewById(R.id.textSpeetch);
    textSpeetch2 = (TextView) findViewById(R.id.textSpeetch2);
    textPreguntaEntrada = (TextView) findViewById(R.id.textPreguntaEntrada);
    textPreguntaSalida = (TextView) findViewById(R.id.textPreguntaSalida);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        textSpeetch.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        textSpeetch2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        textPreguntaEntrada.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        textPreguntaSalida.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
    }

////////////////////////////////////////////////////////////////////////////  INICIAN LAS PREGUNTAS //////////////////////////////////////////////////////////////////////////
    rdPregunta1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio1) {
            op1 = "Si";
            layDireccion.setVisibility(View.VISIBLE);
            layDatosSospechoso.setVisibility(View.VISIBLE);
            layDireccion2.setVisibility(View.GONE);
            layDatosSospechoso2.setVisibility(View.GONE);
            layTelefono.setVisibility(View.VISIBLE);

        }
        else if (checkedId == R.id.radio2) {
            op1 = "No";
            layDireccion.setVisibility(View.GONE);
            layDatosSospechoso.setVisibility(View.GONE);
            layDireccion2.setVisibility(View.GONE);
            layDatosSospechoso2.setVisibility(View.GONE);
            editPreguntaPaterno.setText("");
            editPreguntaMaterno.setText("");
            editPreguntaNombres.setText("");
            editPreguntaTelefonoSospecha.setText("");
            editPreguntaCalle.setText("");
            editPreguntaExterior.setText("");
            editPreguntaInterior.setText("");
            editPreguntaManzana.setText("");
            editPreguntaLote.setText("");
            editPreguntaPaterno2.setText("");
            editPreguntaMaterno2.setText("");
            editPreguntaNombres2.setText("");
            editPreguntaTelefonoSospecha2.setText("");
            editPreguntaCalle2.setText("");
            editPreguntaExterior2.setText("");
            editPreguntaInterior2.setText("");
            editPreguntaManzana2.setText("");
            editPreguntaLote2.setText("");
            layTelefono.setVisibility(View.GONE);
        }
        else if (checkedId == R.id.radio0) {
            op1 = "No sabe / No Contestó";
            layDireccion.setVisibility(View.GONE);
            layDatosSospechoso.setVisibility(View.GONE);
            layDireccion2.setVisibility(View.GONE);
            layDatosSospechoso2.setVisibility(View.GONE);
            editPreguntaPaterno.setText("");
            editPreguntaMaterno.setText("");
            editPreguntaNombres.setText("");
            editPreguntaTelefonoSospecha.setText("");
            editPreguntaCalle.setText("");
            editPreguntaExterior.setText("");
            editPreguntaInterior.setText("");
            editPreguntaManzana.setText("");
            editPreguntaLote.setText("");
            editPreguntaPaterno2.setText("");
            editPreguntaMaterno2.setText("");
            editPreguntaNombres2.setText("");
            editPreguntaTelefonoSospecha2.setText("");
            editPreguntaCalle2.setText("");
            editPreguntaExterior2.setText("");
            editPreguntaInterior2.setText("");
            editPreguntaManzana2.setText("");
            editPreguntaLote2.setText("");
           layTelefono.setVisibility(View.GONE);
        }
    }
});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio1) {
            op2 = "Si";

            if(op1.equals("Si")){
                layDireccion.setVisibility(View.VISIBLE);
                layDatosSospechoso.setVisibility(View.VISIBLE);
                layDireccion2.setVisibility(View.GONE);
                layDatosSospechoso2.setVisibility(View.GONE);
                editPreguntaPaterno2.setText("");
                editPreguntaMaterno2.setText("");
                editPreguntaNombres2.setText("");
                editPreguntaTelefonoSospecha2.setText("");
                editPreguntaCalle2.setText("");
                editPreguntaExterior2.setText("");
                editPreguntaInterior2.setText("");
                editPreguntaManzana2.setText("");
                editPreguntaLote2.setText("");
            }else{
                layDireccion.setVisibility(View.GONE);
                layDatosSospechoso.setVisibility(View.GONE);
                editPreguntaPaterno.setText("");
                editPreguntaMaterno.setText("");
                editPreguntaNombres.setText("");
                editPreguntaTelefonoSospecha.setText("");
                editPreguntaCalle.setText("");
                editPreguntaExterior.setText("");
                editPreguntaInterior.setText("");
                editPreguntaManzana.setText("");
                editPreguntaLote.setText("");
                layDireccion2.setVisibility(View.VISIBLE);
                layDatosSospechoso2.setVisibility(View.VISIBLE);
            }

            layTelefono.setVisibility(View.VISIBLE);
        }
        else if (checkedId == R.id.radio2) {
            op2 = "No";
            if(op1.equals("Si")){
                layDireccion.setVisibility(View.VISIBLE);
                layDatosSospechoso.setVisibility(View.VISIBLE);
                layDireccion2.setVisibility(View.GONE);
                layDatosSospechoso2.setVisibility(View.GONE);
                editPreguntaPaterno2.setText("");
                editPreguntaMaterno2.setText("");
                editPreguntaNombres2.setText("");
                editPreguntaTelefonoSospecha2.setText("");
                editPreguntaCalle2.setText("");
                editPreguntaExterior2.setText("");
                editPreguntaInterior2.setText("");
                editPreguntaManzana2.setText("");
                editPreguntaLote2.setText("");
                layTelefono.setVisibility(View.VISIBLE);
            }else{
                layDireccion.setVisibility(View.GONE);
                layDatosSospechoso.setVisibility(View.GONE);
                layDireccion2.setVisibility(View.GONE);
                layDatosSospechoso2.setVisibility(View.GONE);
                editPreguntaPaterno.setText("");
                editPreguntaMaterno.setText("");
                editPreguntaNombres.setText("");
                editPreguntaTelefonoSospecha.setText("");
                editPreguntaCalle.setText("");
                editPreguntaExterior.setText("");
                editPreguntaInterior.setText("");
                editPreguntaManzana.setText("");
                editPreguntaLote.setText("");
                editPreguntaPaterno2.setText("");
                editPreguntaMaterno2.setText("");
                editPreguntaNombres2.setText("");
                editPreguntaTelefonoSospecha2.setText("");
                editPreguntaCalle2.setText("");
                editPreguntaExterior2.setText("");
                editPreguntaInterior2.setText("");
                editPreguntaManzana2.setText("");
                editPreguntaLote2.setText("");
                layTelefono.setVisibility(View.GONE);
            }

        }
        else if (checkedId == R.id.radio0) {
            op2 = "No sabe / No Contestó";
            if(op1.equals("Si")){
                layDireccion.setVisibility(View.VISIBLE);
                layDatosSospechoso.setVisibility(View.VISIBLE);
                layDireccion2.setVisibility(View.GONE);
                layDatosSospechoso2.setVisibility(View.GONE);
                editPreguntaPaterno2.setText("");
                editPreguntaMaterno2.setText("");
                editPreguntaNombres2.setText("");
                editPreguntaTelefonoSospecha2.setText("");
                editPreguntaCalle2.setText("");
                editPreguntaExterior2.setText("");
                editPreguntaInterior2.setText("");
                editPreguntaManzana2.setText("");
                editPreguntaLote2.setText("");
                layTelefono.setVisibility(View.VISIBLE);
            }else{
                layDireccion.setVisibility(View.GONE);
                layDatosSospechoso.setVisibility(View.GONE);
                layDireccion2.setVisibility(View.GONE);
                layDatosSospechoso2.setVisibility(View.GONE);
                editPreguntaPaterno.setText("");
                editPreguntaMaterno.setText("");
                editPreguntaNombres.setText("");
                editPreguntaTelefonoSospecha.setText("");
                editPreguntaCalle.setText("");
                editPreguntaExterior.setText("");
                editPreguntaInterior.setText("");
                editPreguntaManzana.setText("");
                editPreguntaLote.setText("");
                editPreguntaPaterno2.setText("");
                editPreguntaMaterno2.setText("");
                editPreguntaNombres2.setText("");
                editPreguntaTelefonoSospecha2.setText("");
                editPreguntaCalle2.setText("");
                editPreguntaExterior2.setText("");
                editPreguntaInterior2.setText("");
                editPreguntaManzana2.setText("");
                editPreguntaLote2.setText("");
                layTelefono.setVisibility(View.GONE);
            }
        }
    }
});
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio1) {
            op3 = "Si";
            layTelefono.setVisibility(View.VISIBLE);
        }
        else if (checkedId == R.id.radio2) {
            op3 = "No";
            if(op1.equals("Si")||op2.equals("Si")){
                layTelefono.setVisibility(View.VISIBLE);
            }else{
                layTelefono.setVisibility(View.GONE);
            }
        }
        else if (checkedId == R.id.radio0) {
            op3 = "No sabe / No Contestó";
            if(op1.equals("Si")||op2.equals("Si")){
                layTelefono.setVisibility(View.VISIBLE);
            }else{
                layTelefono.setVisibility(View.GONE);
            }
        }
    }
});


    editPreguntaPaterno.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaPaterno2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaMaterno.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaMaterno2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaNombres.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaNombres2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaCalle.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaCalle2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaExterior.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaExterior2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaInterior.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaInterior2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaManzana.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaManzana2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaLote.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editPreguntaLote2.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });
    editObservaciones.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });

    editPreguntaTelefono.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    });

    rdPreguntaGenero.setOnCheckedChangeListener(new OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId == R.id.radio1) {
                opGenero = "Masculino";


            } else if (checkedId == R.id.radio2) {
                opGenero = "Femenino";


            }

        }
    });

    rdPreguntaUrgente.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.radio_urgente1) {
                opUrgente = "Si";
            }
            else if (checkedId == R.id.radio_urgente2) {
                opUrgente = "No";
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

    btnCalle.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaCalle.setText("No contestó");
        }
    });

    btnExterior.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaExterior.setText("No contestó");
        }
    });
    btnInterior.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaInterior.setText("No contestó");
        }
    });
    btnManzana.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaManzana.setText("No contestó");
        }
    });
    btnLote.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaLote.setText("No contestó");
        }
    });
    btnPaterno.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaPaterno.setText("No contestó");
        }
    });
    btnMaterno.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaMaterno.setText("No contestó");
        }
    });
    btnNombres.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaNombres.setText("No contestó");
        }
    });
    btnTelefonoSospecha.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaTelefonoSospecha.setText("No contestó");
        }
    });

    btnCalle2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaCalle2.setText("No contestó");
            Log.i(TAG,"cqs ------>>"+"calle pregunta2");
        }
    });

    btnExterior2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaExterior2.setText("No contestó");
        }
    });
    btnInterior2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaInterior2.setText("No contestó");
        }
    });
    btnManzana2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaManzana2.setText("No contestó");
        }
    });
    btnLote2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaLote2.setText("No contestó");
        }
    });
    btnPaterno2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaPaterno2.setText("No contestó");
        }
    });
    btnMaterno2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaMaterno2.setText("No contestó");
        }
    });
    btnNombres2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaNombres2.setText("No contestó");
        }
    });
    btnTelefonoSospecha2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaTelefonoSospecha2.setText("No contestó");
        }
    });
    btnTelefonoContacto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editPreguntaTelefono.setText("No contestó");
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



    String str1 = op1;
    String str2 = op2;
    String str3 = op3;
    String strCalle = editPreguntaCalle.getText().toString().toUpperCase()+editPreguntaCalle2.getText().toString().toUpperCase();
    String strExterior = editPreguntaExterior.getText().toString().toUpperCase()+editPreguntaExterior2.getText().toString().toUpperCase();
    String strInterior = editPreguntaInterior.getText().toString().toUpperCase()+editPreguntaInterior2.getText().toString().toUpperCase();
    String strManzana = editPreguntaManzana.getText().toString().toUpperCase()+editPreguntaManzana2.getText().toString().toUpperCase();
    String strLote = editPreguntaLote.getText().toString().toUpperCase()+editPreguntaLote2.getText().toString().toUpperCase();
    String strTelefono = editPreguntaTelefono.getText().toString().toUpperCase();
    String strUrgente = opUrgente;
    String strObservaciones = editObservaciones.getText().toString().toUpperCase();

    String strGenero = opGenero;
    String strAbandono = opAbandono;

    String strPaterno = editPreguntaPaterno.getText().toString()+editPreguntaPaterno2.getText().toString();
    String strMaterno = editPreguntaMaterno.getText().toString()+editPreguntaMaterno2.getText().toString();
    String strNombres = editPreguntaNombres.getText().toString()+editPreguntaNombres2.getText().toString();
    String strTelefonoSospecha = editPreguntaTelefonoSospecha.getText().toString()+editPreguntaTelefonoSospecha2.getText().toString();

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
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

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
            values.put("alcaldia", cachaAlcaldia());

            values.put("pregunta_1",str1);
            values.put("pregunta_2",str2);
            values.put("pregunta_3",str3);
            values.put("genero",strGenero);
            values.put("abandono", strAbandono.toUpperCase());
            values.put("tiempo", elTiempo());
            values.put("tipo_captura", tipoEncuesta);
            values.put("observaciones",strObservaciones);
            values.put("direccion_gps",strDireccionGPS);
            values.put("calle",strCalle.trim());
            values.put("num_exterior",strExterior.trim());
            values.put("num_interior",strInterior.trim());
            values.put("manzana",strManzana.trim());
            values.put("lote",strLote.trim());
            values.put("telefono_contacto",strTelefono);
            values.put("atencion_urgente",strUrgente);
            values.put("paterno",strPaterno.trim());
            values.put("materno",strMaterno.trim());
            values.put("nombres",strNombres.trim());
            values.put("telefono_sospecha",strTelefonoSospecha.trim());

            if (!verificaConexion(this)) {
                Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                values.put("enviado", "0");
                db.insert("encuestas", null, values);
            }else{
                values.put("enviado", "1");
                consecutivoObtenido = db.insert("encuestas", null, values);
            }
        }
        db.close();

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
        System.out.println("alcaldia  " + cachaAlcaldia());

        System.out.println("pregunta_1  " +   str1);
        System.out.println("pregunta_2  " +   str2);
        System.out.println("pregunta_3  " +   str3);
        System.out.println("observaciones  " +   strObservaciones);
        System.out.println("direccion_gps  "+strDireccionGPS);
        System.out.println("calle  "+strCalle);
        System.out.println("num_exterior  "+strExterior);
        System.out.println("num_interior  "+strInterior);
        System.out.println("manzana  "+strManzana);
        System.out.println("lote  "+strLote);
        System.out.println("telefono_contacto  "+strTelefono);
        System.out.println("atencion_urgente  "+strUrgente);

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
    params.put("encuesta", encuesta);
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

    Toast.makeText(MainActivityPantalla1.this, "Error de conexion, Se guarda en la base interna", Toast.LENGTH_SHORT).show();
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


            if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + captura1, Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso.getVisibility() == View.VISIBLE && editPreguntaPaterno.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "Captura Apellido Paterno del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso.getVisibility() == View.VISIBLE && editPreguntaMaterno.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "Captura Apellido Materno del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso.getVisibility() == View.VISIBLE && editPreguntaNombres.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "CAPTURA los Nombres del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso.getVisibility() == View.VISIBLE && editPreguntaTelefonoSospecha.getText().toString().trim().length() < 10) {
                Toast.makeText(getBaseContext(), "Deben ser 10 dígitos del Teléfono", Toast.LENGTH_LONG).show();
            } else if (layDireccion.getVisibility() == View.VISIBLE && editPreguntaCalle.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCalle, Toast.LENGTH_LONG).show();
            } else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + captura2, Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso2.getVisibility() == View.VISIBLE && editPreguntaPaterno2.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "Captura Apellido Paterno del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso2.getVisibility() == View.VISIBLE && editPreguntaMaterno2.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "Captura Apellido Materno del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso2.getVisibility() == View.VISIBLE && editPreguntaNombres2.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "Captura los Nombres del probable sospechoso", Toast.LENGTH_LONG).show();
            } else if (layDatosSospechoso2.getVisibility() == View.VISIBLE && editPreguntaTelefonoSospecha2.getText().toString().trim().length() < 10) {
                Toast.makeText(getBaseContext(), "Deben ser 10 dígitos del Teléfono", Toast.LENGTH_LONG).show();
            } else if (layDireccion2.getVisibility() == View.VISIBLE && editPreguntaCalle2.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCalle, Toast.LENGTH_LONG).show();
            } else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + captura3, Toast.LENGTH_LONG).show();
            } else if (layTelefono.getVisibility() == View.VISIBLE && editPreguntaTelefono.getText().toString().trim().length() < 10) {
                Toast.makeText(getBaseContext(), "Deben ser 10 dígitos del  " + capturaTelefono, Toast.LENGTH_LONG).show();
            } else if (layUrgente.getVisibility() == View.VISIBLE && opUrgente.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaUrgente, Toast.LENGTH_LONG).show();
            } else if (layObservaciones.getVisibility() == View.VISIBLE && editObservaciones.getText().toString().trim().length() == 0) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaObservaciones, Toast.LENGTH_LONG).show();
            } else if (layGenero.getVisibility() == View.VISIBLE && opGenero.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
            }

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

                if (opGenero.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
            }
            else {
                valores();
                btnGuardar.setEnabled(false);
//                dialogo();
            }

            break;

            case 3:

            if (opGenero.matches("sin datos")) {
                Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
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

    String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDate1 + "'";

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

    String selectQuery = "SELECT count(*) FROM encuestas order by id desc limit 1";

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
public void CargaSpinnerAlcaldia() {
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
    spinnerDelegaciones.setAdapter(adaptador);
    spinnerDelegaciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
// db.close();

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
// db.close();

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
