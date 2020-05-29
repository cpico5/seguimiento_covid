package mx.gob.cdmx.telefonica20200530;

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
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
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
import mx.gob.cdmx.telefonica20200530.model.DatoContent;
import mx.gob.cdmx.telefonica20200530.model.Usuario;

import static mx.gob.cdmx.telefonica20200530.Nombre.USUARIO;
import static mx.gob.cdmx.telefonica20200530.Nombre.customURL;
import static mx.gob.cdmx.telefonica20200530.Nombre.encuesta;

public class MainActivityPantalla1 extends Activity {

    private static final String LOG_TAG = "Grabadora";
    private static final String TAG = "Pantalla1";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

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

    double latitude;
    double longitude;

    Random random = new java.util.Random();
    public int rand;

    public RadioGroup rdPreguntaOcupacion, rdPreguntaFocos,   rdPreguntaCoche,rdPreguntaCuantosCoches,rdPreguntaCuartos, rdPreguntaCuartosDormir,
            rdPreguntaBanos,rdPreguntaTrabajaron,rdPreguntaInternet,rdPreguntaRegadera,
            rdPreguntaEstufa, rdPreguntaEdad, rdPreguntaGenero, rdPreguntaTipoVivienda, rdPreguntaTipoPiso;


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

    Timer timer;

    public String opEstadoCivil = "sin datos";
    public String opHijos = "sin datos";
    public String opJefe = "sin datos";
    public String opAporta = "sin datos";
    public String opEstudio = "sin datos";
    public String opAbandono = "sin datos";
    public String opOcupacion = "sin datos";
    public String opE1a = "sin datos";
    public String opE1b = "sin datos";
    public String opCoche = "sin datos";
    public String opE3 = "sin datos";
    public String opFocos = "sin datos";
    public String opCuantosCoches = "sin datos";
    public String opTrabajo = "sin datos";
    public String opE7 = "sin datos";

    public String opCuartos = "sin datos";
    public String opCuartosDormir = "sin datos";
    public String opE4 = "sin datos";
    public String opE91a = "sin datos";
    public String opE92 = "sin datos";
    public String opBanos = "sin datos";
    public String opE101 = "sin datos";
    public String opE102a = "sin datos";
    public String opE103 = "sin datos";
    public String opRegadera = "sin datos";
    public String opInternet = "sin datos";
    public String opTrabajaron = "sin datos";
    public String opEstufa = "sin datos";
    public String opEdad = "sin datos";
    public String opGenero = "sin datos";
    public String opTipoVivienda = "sin datos";
    public String opTipoPiso = "sin datos";

    public String opMedio = "sin datos";
    public String opSemana = "sin datos";
    public String opFinSemana = "sin datos";

    public String opvive = "sin datos";
    public String op0 = "sin datos";
    public String op1 = "sin datos";
    public String op2 = "sin datos";
    public String op3 = "sin datos";
    public String op4 = "sin datos";
    public String op5 = "sin datos";
    public String opc1 = "sin datos";
    public String opc1a = "sin datos";
    public String opc1b = "sin datos";
    public String opc1c = "sin datos";
    public String opc1d = "sin datos";
    public String opc1e = "sin datos";
    public String opc1f = "sin datos";
    public String opc1g = "sin datos";
    public String opc1h = "sin datos";
    public String opc2 = "sin datos";
    public String opc3 = "sin datos";
    public String opc3a = "sin datos";
    public String opc3b = "sin datos";
    public String opc3c = "sin datos";
    public String opc3d = "sin datos";
    public String opc3e = "sin datos";
    public String opc4 = "sin datos";
    public String opc4a = "sin datos";
    public String opc4b = "sin datos";
    public String opc4c = "sin datos";
    public String opc5 = "sin datos";
    public String opc5a = "sin datos";
    public String opc5b = "sin datos";
    public String opc5c = "sin datos";
    public String opc5d = "sin datos";
    public String opc5e = "sin datos";
    public String opc6 = "sin datos";

    public RadioGroup rdPreguntavive;
    public RadioGroup rdPregunta0;
    public RadioGroup rdPregunta1;
    public RadioGroup rdPregunta2;
    public RadioGroup rdPregunta3;
    public RadioGroup rdPregunta4;
    public RadioGroup rdPregunta5;
    public RadioGroup rdPreguntac1;
    public RadioGroup rdPreguntac1a;
    public RadioGroup rdPreguntac1b;
    public RadioGroup rdPreguntac1c;
    public RadioGroup rdPreguntac1d;
    public RadioGroup rdPreguntac1e;
    public RadioGroup rdPreguntac1f;
    public RadioGroup rdPreguntac1g;
    public RadioGroup rdPreguntac1h;
    public RadioGroup rdPreguntac2;
    public RadioGroup rdPreguntac3;
    public RadioGroup rdPreguntac3a;
    public RadioGroup rdPreguntac3b;
    public RadioGroup rdPreguntac3c;
    public RadioGroup rdPreguntac3d;
    public RadioGroup rdPreguntac3e;
    public RadioGroup rdPreguntac4;
    public RadioGroup rdPreguntac4a;
    public RadioGroup rdPreguntac4b;
    public RadioGroup rdPreguntac4c;
    public RadioGroup rdPreguntac5;
    public RadioGroup rdPreguntac5a;
    public RadioGroup rdPreguntac5b;
    public RadioGroup rdPreguntac5c;
    public RadioGroup rdPreguntac5d;
    public RadioGroup rdPreguntac5e;
    public RadioGroup rdPreguntac6;
    public EditText editPreguntavive;
    public EditText editPregunta0;
    public EditText editPregunta1;
    public EditText editPregunta2;
    public EditText editPregunta3;
    public EditText editPregunta4;
    public EditText editPregunta5;
    public EditText editPreguntac1;
    public EditText editPreguntac1a;
    public EditText editPreguntac1b;
    public EditText editPreguntac1c;
    public EditText editPreguntac1d;
    public EditText editPreguntac1e;
    public EditText editPreguntac1f;
    public EditText editPreguntac1g;
    public EditText editPreguntac1h;
    public EditText editPreguntac2;
    public EditText editPreguntac3;
    public EditText editPreguntac3a;
    public EditText editPreguntac3b;
    public EditText editPreguntac3c;
    public EditText editPreguntac3d;
    public EditText editPreguntac3e;
    public EditText editPreguntac4;
    public EditText editPreguntac4a;
    public EditText editPreguntac4b;
    public EditText editPreguntac4c;
    public EditText editPreguntac5;
    public EditText editPreguntac5a;
    public EditText editPreguntac5b;
    public EditText editPreguntac5c;
    public EditText editPreguntac5d;
    public EditText editPreguntac5e;
    public EditText editPreguntac6;

    public String capturavive;
    public String captura0;
    public String captura1;
    public String captura2;
    public String captura3;
    public String captura4;
    public String captura5;
    public String capturac1;
    public String capturac1a;
    public String capturac1b;
    public String capturac1c;
    public String capturac1d;
    public String capturac1e;
    public String capturac1f;
    public String capturac1g;
    public String capturac1h;
    public String capturac2;
    public String capturac3;
    public String capturac3a;
    public String capturac3b;
    public String capturac3c;
    public String capturac3d;
    public String capturac3e;
    public String capturac4;
    public String capturac4a;
    public String capturac4b;
    public String capturac4c;
    public String capturac5;
    public String capturac5a;
    public String capturac5b;
    public String capturac5c;
    public String capturac5d;
    public String capturac5e;
    public String capturac6;

    LinearLayout layvive;
    LinearLayout lay0;
    LinearLayout lay1;
    LinearLayout lay2;
    LinearLayout lay3;
    LinearLayout lay4;
    LinearLayout lay5;
    LinearLayout layc1;
    LinearLayout layc1a;
    LinearLayout layc1b;
    LinearLayout layc1c;
    LinearLayout layc1d;
    LinearLayout layc1e;
    LinearLayout layc1f;
    LinearLayout layc1g;
    LinearLayout layc1h;
    LinearLayout layc2;
    LinearLayout layc3;
    LinearLayout layc3a;
    LinearLayout layc3b;
    LinearLayout layc3c;
    LinearLayout layc3d;
    LinearLayout layc3e;
    LinearLayout layc4;
    LinearLayout layc4a;
    LinearLayout layc4b;
    LinearLayout layc4c;
    LinearLayout layc5;
    LinearLayout layc5a;
    LinearLayout layc5b;
    LinearLayout layc5c;
    LinearLayout layc5d;
    LinearLayout layc5e;
    LinearLayout layc6;


    public EditText editTelefono;


    LinearLayout laySocioE;
    LinearLayout layEst;
    LinearLayout layHijos;
    LinearLayout layAporta;
    LinearLayout layOcupacion;
    LinearLayout laycoche;
    LinearLayout layCuartos;
    LinearLayout layCuartosDormir;
    LinearLayout layFocos;
    LinearLayout layBanos;
    LinearLayout layRegadera;
    LinearLayout layInternet;
    LinearLayout layTrabajaron;
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

    public String captura10a, captura11a, captura13a, captura14a, captura14b, captura14c;
    public String captura18a;
    public String capturaMedio;
    public String capturaSemana;
    public String capturaFinSemana, capturaHijos;

    String ochoa_1;
    String ochoa_2;
    String ochoa_3;
    String ochoa_4;
    String ochoa_5;
    String ochoa_6;
    String ochoa_7;
    String ochoa_8;
    String ochoa_9;
    String ochoa_10;
    String ochoa_11;

//    CheckBox checkochoa_1;
//    CheckBox checkochoa_2;
//    CheckBox checkochoa_3;
//    CheckBox checkochoa_4;
//    CheckBox checkochoa_5;
//    CheckBox checkochoa_6;
//    CheckBox checkochoa_7;
//    CheckBox checkochoa_8;
//    CheckBox checkochoa_9;
//    CheckBox checkochoa_10;
//    CheckBox checkochoa_11;

    public String capturaOcupacion, capturaCoche, capturaE3, capturaE4, capturaCuantosCoches, capturaTrabajo, capturaE7,
            capturaFocos, capturaCuartos, capturaCuartosDormir, capturaBanos, capturaInternet, capturaTrabajaron;
    public String capturaRegadera, capturaEstufa, capturaEdad, capturaGenero, capturaTipoVivienda, capturaTipoPiso,
            capturaE17, capturaE18, capturaE19, capturaE20;
    public String capturaJefe, capturaAporta;


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

//	public long t1() {
//		Bundle datos = this.getIntent().getExtras();
//		long t1 = datos.getLong("t1");
//		return t1;
//	}


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

    public void dialogoAbandono() {
        timer.cancel();

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivityPantalla1.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Deseas abandonar la encuesta?").setTitle("AVISO...!!!").setCancelable(false)
                        .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                detenerGrabacion();
                            }
                        }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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

    public String nombreArchivo() {
        String date = formattedDate3.toString();
        String var2 = ".txt";
        String var3 = date + var2;

        final String nombre = date + "-" + tablet + "-" + nombreEncuesta + var2;
        return nombre;
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
        final String nombreAudio = nombreEncuesta + "_" + date + "_" + sacaImei() + "_" + cachaNombre() + "_" + elConsecutivo + "_" + cachaTelefono() + ".mp3";
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

    private Integer[] mLinearLayoutIds = {
            R.layout.activity_pantalla1,
            R.layout.activity_pantalla2,
            R.layout.activity_pantalla3,
            R.layout.activity_pantalla4,
            R.layout.activity_pantalla5,
            R.layout.activity_pantalla6,
            R.layout.activity_pantalla7,
            R.layout.activity_pantalla8,
            R.layout.activity_pantalla9,
            R.layout.activity_pantalla10,
            //// R.layout.activity_pantalla11,
            //// R.layout.activity_pantalla12,
            //// R.layout.activity_pantalla13,
            //// R.layout.activity_pantalla14,
            //// R.layout.activity_pantalla15,
            //// R.layout.activity_pantalla16,
            //// R.layout.activity_pantalla17,
            //// R.layout.activity_pantalla18,
            //// R.layout.activity_pantalla19,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pantalla1); // COMENTAR ESTA CUANDO ES ALEATORIO

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
        /*DESCOMENTAR ESTAS 3 LINEAS CUANDO YA ESTA EL NUMERO DE HOJAS ALEATORIO */
        rand = random.nextInt(9);
        setContentView(mLinearLayoutIds[rand]);
        Log.i(null, "El aleatorio: " + rand); // si rand= 11 en el layoud corresponde a uno mas


        /*activity_pantalla12*/

        // Crea Log cuando falla la app
        Thread.setDefaultUncaughtExceptionHandler(new Crash(this));

        cachaNombre(); // llamado al mÃ©todo para obtener el numero del
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

        res = getResources();

        spinner0 = (Spinner) findViewById(R.id.spinner0);

        rdPreguntavive = (RadioGroup)findViewById(R.id.rdPreguntavive);
        rdPregunta0 = (RadioGroup)findViewById(R.id.rdPregunta0);
        rdPregunta1 = (RadioGroup)findViewById(R.id.rdPregunta1);
        rdPregunta2 = (RadioGroup)findViewById(R.id.rdPregunta2);
        rdPregunta3 = (RadioGroup)findViewById(R.id.rdPregunta3);
        rdPregunta4 = (RadioGroup)findViewById(R.id.rdPregunta4);
        rdPregunta5 = (RadioGroup)findViewById(R.id.rdPregunta5);
        rdPreguntac1 = (RadioGroup)findViewById(R.id.rdPreguntac1);
        rdPreguntac1a = (RadioGroup)findViewById(R.id.rdPreguntac1a);
        rdPreguntac1b = (RadioGroup)findViewById(R.id.rdPreguntac1b);
        rdPreguntac1c = (RadioGroup)findViewById(R.id.rdPreguntac1c);
        rdPreguntac1d = (RadioGroup)findViewById(R.id.rdPreguntac1d);
        rdPreguntac1e = (RadioGroup)findViewById(R.id.rdPreguntac1e);
        rdPreguntac1f = (RadioGroup)findViewById(R.id.rdPreguntac1f);
        rdPreguntac1g = (RadioGroup)findViewById(R.id.rdPreguntac1g);
        rdPreguntac1h = (RadioGroup)findViewById(R.id.rdPreguntac1h);
        rdPreguntac2 = (RadioGroup)findViewById(R.id.rdPreguntac2);
        rdPreguntac3 = (RadioGroup)findViewById(R.id.rdPreguntac3);
        rdPreguntac3a = (RadioGroup)findViewById(R.id.rdPreguntac3a);
        rdPreguntac3b = (RadioGroup)findViewById(R.id.rdPreguntac3b);
        rdPreguntac3c = (RadioGroup)findViewById(R.id.rdPreguntac3c);
        rdPreguntac3d = (RadioGroup)findViewById(R.id.rdPreguntac3d);
        rdPreguntac3e = (RadioGroup)findViewById(R.id.rdPreguntac3e);
        rdPreguntac4 = (RadioGroup)findViewById(R.id.rdPreguntac4);
        rdPreguntac4a = (RadioGroup)findViewById(R.id.rdPreguntac4a);
        rdPreguntac4b = (RadioGroup)findViewById(R.id.rdPreguntac4b);
        rdPreguntac4c = (RadioGroup)findViewById(R.id.rdPreguntac4c);
        rdPreguntac5 = (RadioGroup)findViewById(R.id.rdPreguntac5);
        rdPreguntac5a = (RadioGroup)findViewById(R.id.rdPreguntac5a);
        rdPreguntac5b = (RadioGroup)findViewById(R.id.rdPreguntac5b);
        rdPreguntac5c = (RadioGroup)findViewById(R.id.rdPreguntac5c);
        rdPreguntac5d = (RadioGroup)findViewById(R.id.rdPreguntac5d);
        rdPreguntac5e = (RadioGroup)findViewById(R.id.rdPreguntac5e);
        rdPreguntac6 = (RadioGroup)findViewById(R.id.rdPreguntac6);

        capturavive = res.getString(R.string.PREGUNTAvive);
        captura0 = res.getString(R.string.PREGUNTA0);
        captura1 = res.getString(R.string.PREGUNTA1);
        captura2 = res.getString(R.string.PREGUNTA2);
        captura3 = res.getString(R.string.PREGUNTA3);
        captura4 = res.getString(R.string.PREGUNTA4);
        captura5 = res.getString(R.string.PREGUNTA5);
        capturac1 = res.getString(R.string.PREGUNTAc1);
        capturac1a = res.getString(R.string.PREGUNTAc1a);
        capturac1b = res.getString(R.string.PREGUNTAc1b);
        capturac1c = res.getString(R.string.PREGUNTAc1c);
        capturac1d = res.getString(R.string.PREGUNTAc1d);
        capturac1e = res.getString(R.string.PREGUNTAc1e);
        capturac1f = res.getString(R.string.PREGUNTAc1f);
        capturac1g = res.getString(R.string.PREGUNTAc1g);
        capturac1h = res.getString(R.string.PREGUNTAc1h);
        capturac2 = res.getString(R.string.PREGUNTAc2);
        capturac3 = res.getString(R.string.PREGUNTAc3);
        capturac3a = res.getString(R.string.PREGUNTAc3a);
        capturac3b = res.getString(R.string.PREGUNTAc3b);
        capturac3c = res.getString(R.string.PREGUNTAc3c);
        capturac3d = res.getString(R.string.PREGUNTAc3d);
        capturac3e = res.getString(R.string.PREGUNTAc3e);
        capturac4 = res.getString(R.string.PREGUNTAc4);
        capturac4a = res.getString(R.string.PREGUNTAc4a);
        capturac4b = res.getString(R.string.PREGUNTAc4b);
        capturac4c = res.getString(R.string.PREGUNTAc4c);
        capturac5 = res.getString(R.string.PREGUNTAc5);
        capturac5a = res.getString(R.string.PREGUNTAc5a);
        capturac5b = res.getString(R.string.PREGUNTAc5b);
        capturac5c = res.getString(R.string.PREGUNTAc5c);
        capturac5d = res.getString(R.string.PREGUNTAc5d);
        capturac5e = res.getString(R.string.PREGUNTAc5e);
        capturac6 = res.getString(R.string.PREGUNTAc6);

        layvive = (LinearLayout) findViewById(R.id.layvive);
        lay0 = (LinearLayout) findViewById(R.id.lay0);
        lay1 = (LinearLayout) findViewById(R.id.lay1);
        lay2 = (LinearLayout) findViewById(R.id.lay2);
        lay3 = (LinearLayout) findViewById(R.id.lay3);
        lay4 = (LinearLayout) findViewById(R.id.lay4);
        lay5 = (LinearLayout) findViewById(R.id.lay5);
        layc1 = (LinearLayout) findViewById(R.id.layc1);
        layc1a = (LinearLayout) findViewById(R.id.layc1a);
        layc1b = (LinearLayout) findViewById(R.id.layc1b);
        layc1c = (LinearLayout) findViewById(R.id.layc1c);
        layc1d = (LinearLayout) findViewById(R.id.layc1d);
        layc1e = (LinearLayout) findViewById(R.id.layc1e);
        layc1f = (LinearLayout) findViewById(R.id.layc1f);
        layc1g = (LinearLayout) findViewById(R.id.layc1g);
        layc1h = (LinearLayout) findViewById(R.id.layc1h);
        layc2 = (LinearLayout) findViewById(R.id.layc2);
        layc3 = (LinearLayout) findViewById(R.id.layc3);
        layc3a = (LinearLayout) findViewById(R.id.layc3a);
        layc3b = (LinearLayout) findViewById(R.id.layc3b);
        layc3c = (LinearLayout) findViewById(R.id.layc3c);
        layc3d = (LinearLayout) findViewById(R.id.layc3d);
        layc3e = (LinearLayout) findViewById(R.id.layc3e);
        layc4 = (LinearLayout) findViewById(R.id.layc4);
        layc4a = (LinearLayout) findViewById(R.id.layc4a);
        layc4b = (LinearLayout) findViewById(R.id.layc4b);
        layc4c = (LinearLayout) findViewById(R.id.layc4c);
        layc5 = (LinearLayout) findViewById(R.id.layc5);
        layc5a = (LinearLayout) findViewById(R.id.layc5a);
        layc5b = (LinearLayout) findViewById(R.id.layc5b);
        layc5c = (LinearLayout) findViewById(R.id.layc5c);
        layc5d = (LinearLayout) findViewById(R.id.layc5d);
        layc5e = (LinearLayout) findViewById(R.id.layc5e);
        layc6 = (LinearLayout) findViewById(R.id.layc6);

//        editPreguntavive = (EditText)findViewById(R.id.editPreguntavive);
//        editPregunta0 = (EditText)findViewById(R.id.editPregunta0);
//        editPregunta1 = (EditText)findViewById(R.id.editPregunta1);
//        editPregunta2 = (EditText)findViewById(R.id.editPregunta2);
//        editPregunta3 = (EditText)findViewById(R.id.editPregunta3);
//        editPregunta4 = (EditText)findViewById(R.id.editPregunta4);
//        editPregunta5 = (EditText)findViewById(R.id.editPregunta5);
//        editPreguntac1 = (EditText)findViewById(R.id.editPreguntac1);
//        editPreguntac1a = (EditText)findViewById(R.id.editPreguntac1a);
//        editPreguntac1b = (EditText)findViewById(R.id.editPreguntac1b);
//        editPreguntac1c = (EditText)findViewById(R.id.editPreguntac1c);
//        editPreguntac1d = (EditText)findViewById(R.id.editPreguntac1d);
//        editPreguntac1e = (EditText)findViewById(R.id.editPreguntac1e);
//        editPreguntac1f = (EditText)findViewById(R.id.editPreguntac1f);
//        editPreguntac1g = (EditText)findViewById(R.id.editPreguntac1g);
//        editPreguntac1h = (EditText)findViewById(R.id.editPreguntac1h);
//        editPreguntac2 = (EditText)findViewById(R.id.editPreguntac2);
//        editPreguntac3 = (EditText)findViewById(R.id.editPreguntac3);
//        editPreguntac3a = (EditText)findViewById(R.id.editPreguntac3a);
//        editPreguntac3b = (EditText)findViewById(R.id.editPreguntac3b);
//        editPreguntac3c = (EditText)findViewById(R.id.editPreguntac3c);
//        editPreguntac3d = (EditText)findViewById(R.id.editPreguntac3d);
//        editPreguntac3e = (EditText)findViewById(R.id.editPreguntac3e);
//        editPreguntac4 = (EditText)findViewById(R.id.editPreguntac4);
//        editPreguntac4a = (EditText)findViewById(R.id.editPreguntac4a);
//        editPreguntac4b = (EditText)findViewById(R.id.editPreguntac4b);
//        editPreguntac4c = (EditText)findViewById(R.id.editPreguntac4c);
//        editPreguntac5 = (EditText)findViewById(R.id.editPreguntac5);
//        editPreguntac5a = (EditText)findViewById(R.id.editPreguntac5a);
//        editPreguntac5b = (EditText)findViewById(R.id.editPreguntac5b);
//        editPreguntac5c = (EditText)findViewById(R.id.editPreguntac5c);
//        editPreguntac5d = (EditText)findViewById(R.id.editPreguntac5d);
//        editPreguntac5e = (EditText)findViewById(R.id.editPreguntac5e);
//        editPreguntac6 = (EditText)findViewById(R.id.editPreguntac6);


//        editPreguntac3b= (EditText)findViewById(R.id.editPreguntac3b);

        laySocioE = (LinearLayout) findViewById(R.id.laySocioE);
        layEst = (LinearLayout) findViewById(R.id.layEst);
        layHijos = (LinearLayout) findViewById(R.id.layHijos);
        layAporta = (LinearLayout) findViewById(R.id.layAporta);
        layOcupacion = (LinearLayout) findViewById(R.id.layOcupacion);
        laycoche = (LinearLayout) findViewById(R.id.laycoche);
        layCuartos = (LinearLayout) findViewById(R.id.layCuartos);
        layCuartosDormir = (LinearLayout) findViewById(R.id.layCuartosDormir);
        layFocos = (LinearLayout) findViewById(R.id.layFocos);
        layBanos = (LinearLayout) findViewById(R.id.layBanos);
        layRegadera = (LinearLayout) findViewById(R.id.layRegadera);
        layInternet = (LinearLayout) findViewById(R.id.layInternet);
        layTrabajaron = (LinearLayout) findViewById(R.id.layTrabajaron);
        layEstufa = (LinearLayout) findViewById(R.id.layEstufa);
        layEdad = (LinearLayout) findViewById(R.id.layEdad);
        layTipoPiso = (LinearLayout) findViewById(R.id.layTipoPiso);
        layTipoVivienda = (LinearLayout) findViewById(R.id.layTipoVivienda);
        layGenero = (LinearLayout) findViewById(R.id.layGenero);

        radio_abandono1 = (RadioButton) findViewById(R.id.radio_abandono1);
        radio_abandono2 = (RadioButton) findViewById(R.id.radio_abandono2);
        radio_abandono3 = (RadioButton) findViewById(R.id.radio_abandono3);
        radio_abandono4 = (RadioButton) findViewById(R.id.radio_abandono4);


//        checkochoa_1 = (CheckBox) findViewById(R.id.checkochoa_1);
//        checkochoa_2 = (CheckBox) findViewById(R.id.checkochoa_2);
//        checkochoa_3 = (CheckBox) findViewById(R.id.checkochoa_3);
//        checkochoa_4 = (CheckBox) findViewById(R.id.checkochoa_4);
//        checkochoa_5 = (CheckBox) findViewById(R.id.checkochoa_5);
//        checkochoa_6 = (CheckBox) findViewById(R.id.checkochoa_6);
//        checkochoa_7 = (CheckBox) findViewById(R.id.checkochoa_7);
//        checkochoa_8 = (CheckBox) findViewById(R.id.checkochoa_8);
//        checkochoa_9 = (CheckBox) findViewById(R.id.checkochoa_9);
//        checkochoa_10 = (CheckBox) findViewById(R.id.checkochoa_10);
//        checkochoa_11 = (CheckBox) findViewById(R.id.checkochoa_11);


        editTelefono = (EditText) findViewById(R.id.editTelefono);

        rdPreguntaHijos = (RadioGroup) findViewById(R.id.rdPreguntaHijos);
        rdPreguntaAporta = (RadioGroup) findViewById(R.id.rdPreguntaAporta);
        rdPreguntaAbandono = (RadioGroup) findViewById(R.id.rdPreguntaAbandono);
        rdPreguntaOcupacion = (RadioGroup) findViewById(R.id.rdPreguntaOcupacion);
        rdPreguntaCoche = (RadioGroup) findViewById(R.id.rdPreguntaCoche);
        rdPreguntaCuantosCoches = (RadioGroup) findViewById(R.id.rdPreguntaCuantosCoches);
        rdPreguntaCuartos = (RadioGroup) findViewById(R.id.rdPreguntaCuartos);
        rdPreguntaCuartosDormir = (RadioGroup) findViewById(R.id.rdPreguntaCuartosDormir);
        rdPreguntaFocos = (RadioGroup) findViewById(R.id.rdPreguntaFocos);
        rdPreguntaBanos = (RadioGroup) findViewById(R.id.rdPreguntaBanos);
        rdPreguntaRegadera = (RadioGroup) findViewById(R.id.rdPreguntaRegadera);
        rdPreguntaInternet = (RadioGroup) findViewById(R.id.rdPreguntaInternet);
        rdPreguntaTrabajaron = (RadioGroup) findViewById(R.id.rdPreguntaTrabajaron);
        rdPreguntaEstufa = (RadioGroup) findViewById(R.id.rdPreguntaEstufa);
        rdPreguntaEdad = (RadioGroup) findViewById(R.id.rdPreguntaEdad);
        rdPreguntaGenero = (RadioGroup) findViewById(R.id.rdPreguntaGenero);
        rdPreguntaTipoVivienda = (RadioGroup) findViewById(R.id.rdPreguntaTipoVivienda);
        rdPreguntaTipoPiso = (RadioGroup) findViewById(R.id.rdPreguntaTipoPiso);


        capturaHijos = res.getString(R.string.PREGUNTAHIJOS);
        capturaAporta = res.getString(R.string.PREGUNTAAPORTA);
        capturaOcupacion = res.getString(R.string.PREGUNTAOCUPACION);
        capturaCoche = res.getString(R.string.PREGUNTACOCHE);
        capturaCuantosCoches = res.getString(R.string.PREGUNTACUANTOSCOCHES);
        capturaFocos = res.getString(R.string.PREGUNTAFOCOS);
        capturaCuartos = res.getString(R.string.PREGUNTACUARTOS);
        capturaCuartosDormir = res.getString(R.string.PREGUNTACUARTOSDORMIR);
        capturaBanos = res.getString(R.string.PREGUNTABANOS);
//		capturaRegadera=res.getString(R.string.PREGUNTAREGADERA);
        capturaInternet = res.getString(R.string.PREGUNTAINTERNET);
        capturaTrabajaron = res.getString(R.string.PREGUNTATRABAJARON);
        capturaEstufa = res.getString(R.string.PREGUNTAESTUFA);
        capturaEdad = res.getString(R.string.PREGUNTAEDAD);
        capturaGenero = res.getString(R.string.PREGUNTAGENERO);
        capturaTipoVivienda = res.getString(R.string.PREGUNTA_TIPO_VIVIENDA);
        capturaTipoPiso = res.getString(R.string.PREGUNTA_TIPO_PISO);


        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSalir.setEnabled(false);
        btnSalir.setVisibility(View.GONE);


        //
        //

        layCuantosCoches = (LinearLayout) findViewById(R.id.layCuantosCoches);
        layCuantosCoches.setVisibility(View.GONE);


//        mChecks = new ArrayList();
//        mSelectedChecks = new ArrayList();
//
//        mChecks.add(checkochoa_1);
//        mChecks.add(checkochoa_2);
//        mChecks.add(checkochoa_3);
//        mChecks.add(checkochoa_4);
//        mChecks.add(checkochoa_5);
//        mChecks.add(checkochoa_6);
//        mChecks.add(checkochoa_7);
//        mChecks.add(checkochoa_8);
//        mChecks.add(checkochoa_9);
//        mChecks.add(checkochoa_10);
//		mChecks.add(checkochoa_11);// este no porque es el que debe limpiar los otros

        //Add Click listener
//        for (CheckBox c : mChecks) {
//            c.setOnClickListener((new OnClickListener() {
//                @Override
//                public void onClick(View arg0) {
//                    CheckBox c = (CheckBox) arg0;
//
//
//                    if (mSelectedChecks.contains(c)) {
//                        mSelectedChecks.remove(c);
//                    } else {
//                        if (mSelectedChecks.size() < 3) {
//                            mSelectedChecks.add(c);
//                        } else {
//                            mSelectedChecks.remove(0);
//                            mSelectedChecks.add(c);
//                        }
//                    }
//
//                    drawResults();
//                }
//            }));
//
//
//        }


        CargaSpinner0();



        rdPreguntavive.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opvive = "Si";

                    rdPregunta0.clearCheck();
                    op0 = "sin datos";

                    radio_abandono1.setVisibility(View.VISIBLE);
                    radio_abandono2.setVisibility(View.VISIBLE);
                    radio_abandono3.setVisibility(View.VISIBLE);
                    radio_abandono4.setVisibility(View.GONE);
                    radio_abandono4.setChecked(false);
                    radio_abandono1.setChecked(true);


                    lay0 .setVisibility(View.VISIBLE);
                    lay1 .setVisibility(View.VISIBLE);
                    lay2 .setVisibility(View.VISIBLE);
                    lay3 .setVisibility(View.VISIBLE);
                    lay4 .setVisibility(View.VISIBLE);
                    lay5 .setVisibility(View.VISIBLE);
                    layc1 .setVisibility(View.VISIBLE);
                    layc1a .setVisibility(View.VISIBLE);
                    layc1b .setVisibility(View.VISIBLE);
                    layc1c .setVisibility(View.VISIBLE);
                    layc1d .setVisibility(View.VISIBLE);
                    layc1e .setVisibility(View.VISIBLE);
                    layc1f .setVisibility(View.VISIBLE);
                    layc1g .setVisibility(View.VISIBLE);
                    layc1h .setVisibility(View.VISIBLE);
                    layc2 .setVisibility(View.VISIBLE);
                    layc3 .setVisibility(View.VISIBLE);
                    layc3a .setVisibility(View.VISIBLE);
                    layc3b .setVisibility(View.VISIBLE);
                    layc3c .setVisibility(View.VISIBLE);
                    layc3d .setVisibility(View.VISIBLE);
                    layc3e .setVisibility(View.VISIBLE);
                    layc4 .setVisibility(View.VISIBLE);
                    layc4a .setVisibility(View.VISIBLE);
                    layc4b .setVisibility(View.VISIBLE);
                    layc4c .setVisibility(View.VISIBLE);
                    layc5 .setVisibility(View.VISIBLE);
                    layc5a .setVisibility(View.VISIBLE);
                    layc5b .setVisibility(View.VISIBLE);
                    layc5c .setVisibility(View.VISIBLE);
                    layc5d .setVisibility(View.VISIBLE);
                    layc5e .setVisibility(View.VISIBLE);
                    layc6 .setVisibility(View.VISIBLE);

                    rdPregunta0.clearCheck(); op0="sin datos";
                    rdPregunta1.clearCheck(); op1="sin datos";
                    rdPregunta2.clearCheck(); op2="sin datos";
                    rdPregunta3.clearCheck(); op3="sin datos";
                    rdPregunta4.clearCheck(); op4="sin datos";
                    rdPregunta5.clearCheck(); op5="sin datos";
//                    rdPreguntac1.clearCheck(); opc1="sin datos";
                    rdPreguntac1a.clearCheck(); opc1a="sin datos";
                    rdPreguntac1b.clearCheck(); opc1b="sin datos";
                    rdPreguntac1c.clearCheck(); opc1c="sin datos";
                    rdPreguntac1d.clearCheck(); opc1d="sin datos";
                    rdPreguntac1e.clearCheck(); opc1e="sin datos";
                    rdPreguntac1f.clearCheck(); opc1f="sin datos";
                    rdPreguntac1g.clearCheck(); opc1g="sin datos";
                    rdPreguntac1h.clearCheck(); opc1h="sin datos";
                    rdPreguntac2.clearCheck(); opc2="sin datos";
//                    rdPreguntac3.clearCheck(); opc3="sin datos";
                    rdPreguntac3a.clearCheck(); opc3a="sin datos";
                    rdPreguntac3b.clearCheck(); opc3b="sin datos";
                    rdPreguntac3c.clearCheck(); opc3c="sin datos";
                    rdPreguntac3d.clearCheck(); opc3d="sin datos";
                    rdPreguntac3e.clearCheck(); opc3e="sin datos";
//                    rdPreguntac4.clearCheck(); opc4="sin datos";
                    rdPreguntac4a.clearCheck(); opc4a="sin datos";
                    rdPreguntac4b.clearCheck(); opc4b="sin datos";
                    rdPreguntac4c.clearCheck(); opc4c="sin datos";
//                    rdPreguntac5.clearCheck(); opc5="sin datos";
                    rdPreguntac5a.clearCheck(); opc5a="sin datos";
                    rdPreguntac5b.clearCheck(); opc5b="sin datos";
                    rdPreguntac5c.clearCheck(); opc5c="sin datos";
                    rdPreguntac5d.clearCheck(); opc5d="sin datos";
                    rdPreguntac5e.clearCheck(); opc5e="sin datos";
                    rdPreguntac6.clearCheck(); opc6="sin datos";


                    laySocioE.setVisibility(View.VISIBLE);
                    layEst.setVisibility(View.VISIBLE);
                    layHijos.setVisibility(View.VISIBLE);
                    layAporta.setVisibility(View.VISIBLE);
                    layOcupacion.setVisibility(View.VISIBLE);
                    laycoche.setVisibility(View.VISIBLE);
                    layCuantosCoches.setVisibility(View.VISIBLE);
                    layCuartos.setVisibility(View.VISIBLE);
                    layCuartosDormir.setVisibility(View.VISIBLE);
                    layFocos.setVisibility(View.VISIBLE);
                    layBanos.setVisibility(View.VISIBLE);
                    layRegadera.setVisibility(View.VISIBLE);
                    layInternet.setVisibility(View.VISIBLE);
                    layTrabajaron.setVisibility(View.VISIBLE);
                    layEstufa.setVisibility(View.VISIBLE);
                    layEdad.setVisibility(View.VISIBLE);
                    layTipoPiso.setVisibility(View.VISIBLE);
                    layTipoVivienda.setVisibility(View.VISIBLE);
                    layGenero.setVisibility(View.VISIBLE);

                    rdPreguntaHijos.clearCheck();
                    rdPreguntaAporta.clearCheck();
                    rdPreguntaOcupacion.clearCheck();
                    rdPreguntaCoche.clearCheck();
                    rdPreguntaCuantosCoches.clearCheck();
                    rdPreguntaCuartos.clearCheck();
                    rdPreguntaCuartosDormir.clearCheck();
                    rdPreguntaFocos.clearCheck();
                    rdPreguntaBanos.clearCheck();
                    rdPreguntaRegadera.clearCheck();
                    rdPreguntaInternet.clearCheck();
                    rdPreguntaTrabajaron.clearCheck();
                    rdPreguntaEstufa.clearCheck();
                    rdPreguntaTipoPiso.clearCheck();
                    rdPreguntaTipoVivienda.clearCheck();
                    rdPreguntaGenero.clearCheck();

                    opHijos = "sin datos";
                    opAporta = "sin datos";
                    opOcupacion = "sin datos";
                    opCoche = "sin datos";
                    opCuantosCoches = "sin datos";
                    opCuartos = "sin datos";
                    opCuartosDormir = "sin datos";
                    opFocos = "sin datos";
                    opBanos = "sin datos";
                    opRegadera = "sin datos";
                    opInternet = "sin datos";
                    opTrabajaron = "sin datos";
                    opEstufa = "sin datos";
                    opTipoPiso = "sin datos";
                    opTipoVivienda = "sin datos";
                    opGenero = "sin datos";
                }

                else if (checkedId == R.id.radio2) {
                    opvive = "No";

                    opAbandono = "4";
                    tipoEncuesta = "FILTRO";
                    btnGuardar.setText("Guardar Filtro");
                    radio_abandono1.setChecked(false);
                    radio_abandono4.setChecked(true);

                    radio_abandono1.setVisibility(View.GONE);
                    radio_abandono2.setVisibility(View.GONE);
                    radio_abandono3.setVisibility(View.GONE);
                    radio_abandono4.setVisibility(View.VISIBLE);

                    spinner0.setSelection(0);

                    lay0 .setVisibility(View.GONE);
                    lay1 .setVisibility(View.GONE);
                    lay2 .setVisibility(View.GONE);
                    lay3 .setVisibility(View.GONE);
                    lay4 .setVisibility(View.GONE);
                    lay5 .setVisibility(View.GONE);
                    layc1 .setVisibility(View.GONE);
                    layc1a .setVisibility(View.GONE);
                    layc1b .setVisibility(View.GONE);
                    layc1c .setVisibility(View.GONE);
                    layc1d .setVisibility(View.GONE);
                    layc1e .setVisibility(View.GONE);
                    layc1f .setVisibility(View.GONE);
                    layc1g .setVisibility(View.GONE);
                    layc1h .setVisibility(View.GONE);
                    layc2 .setVisibility(View.GONE);
                    layc3 .setVisibility(View.GONE);
                    layc3a .setVisibility(View.GONE);
                    layc3b .setVisibility(View.GONE);
                    layc3c .setVisibility(View.GONE);
                    layc3d .setVisibility(View.GONE);
                    layc3e .setVisibility(View.GONE);
                    layc4 .setVisibility(View.GONE);
                    layc4a .setVisibility(View.GONE);
                    layc4b .setVisibility(View.GONE);
                    layc4c .setVisibility(View.GONE);
                    layc5 .setVisibility(View.GONE);
                    layc5a .setVisibility(View.GONE);
                    layc5b .setVisibility(View.GONE);
                    layc5c .setVisibility(View.GONE);
                    layc5d .setVisibility(View.GONE);
                    layc5e .setVisibility(View.GONE);
                    layc6 .setVisibility(View.GONE);

                    rdPregunta0.clearCheck(); op0="No aplica";
                    rdPregunta1.clearCheck(); op1="No aplica";
                    rdPregunta2.clearCheck(); op2="No aplica";
                    rdPregunta3.clearCheck(); op3="No aplica";
                    rdPregunta4.clearCheck(); op4="No aplica";
                    rdPregunta5.clearCheck(); op5="No aplica";
//                    rdPreguntac1.clearCheck(); opc1="No aplica";
                    rdPreguntac1a.clearCheck(); opc1a="No aplica";
                    rdPreguntac1b.clearCheck(); opc1b="No aplica";
                    rdPreguntac1c.clearCheck(); opc1c="No aplica";
                    rdPreguntac1d.clearCheck(); opc1d="No aplica";
                    rdPreguntac1e.clearCheck(); opc1e="No aplica";
                    rdPreguntac1f.clearCheck(); opc1f="No aplica";
                    rdPreguntac1g.clearCheck(); opc1g="No aplica";
                    rdPreguntac1h.clearCheck(); opc1h="No aplica";
                    rdPreguntac2.clearCheck(); opc2="No aplica";
//                    rdPreguntac3.clearCheck(); opc3="No aplica";
                    rdPreguntac3a.clearCheck(); opc3a="No aplica";
                    rdPreguntac3b.clearCheck(); opc3b="No aplica";
                    rdPreguntac3c.clearCheck(); opc3c="No aplica";
                    rdPreguntac3d.clearCheck(); opc3d="No aplica";
                    rdPreguntac3e.clearCheck(); opc3e="No aplica";
//                    rdPreguntac4.clearCheck(); opc4="No aplica";
                    rdPreguntac4a.clearCheck(); opc4a="No aplica";
                    rdPreguntac4b.clearCheck(); opc4b="No aplica";
                    rdPreguntac4c.clearCheck(); opc4c="No aplica";
//                    rdPreguntac5.clearCheck(); opc5="No aplica";
                    rdPreguntac5a.clearCheck(); opc5a="No aplica";
                    rdPreguntac5b.clearCheck(); opc5b="No aplica";
                    rdPreguntac5c.clearCheck(); opc5c="No aplica";
                    rdPreguntac5d.clearCheck(); opc5d="No aplica";
                    rdPreguntac5e.clearCheck(); opc5e="No aplica";
                    rdPreguntac6.clearCheck(); opc6="No aplica";

                    laySocioE.setVisibility(View.GONE);
                    layEst.setVisibility(View.GONE);
                    layHijos.setVisibility(View.GONE);
                    layAporta.setVisibility(View.GONE);
                    layOcupacion.setVisibility(View.GONE);
                    laycoche.setVisibility(View.GONE);
                    layCuantosCoches.setVisibility(View.GONE);
                    layCuartos.setVisibility(View.GONE);
                    layCuartosDormir.setVisibility(View.GONE);
                    layFocos.setVisibility(View.GONE);
                    layBanos.setVisibility(View.GONE);
                    layRegadera.setVisibility(View.GONE);
                    layInternet.setVisibility(View.GONE);
                    layTrabajaron.setVisibility(View.GONE);
                    layEstufa.setVisibility(View.GONE);
                    layEdad.setVisibility(View.GONE);
                    layTipoPiso.setVisibility(View.GONE);
                    layTipoVivienda.setVisibility(View.GONE);
                    layGenero.setVisibility(View.GONE);

                    rdPreguntaHijos.clearCheck();
                    rdPreguntaAporta.clearCheck();
                    rdPreguntaOcupacion.clearCheck();
                    rdPreguntaCoche.clearCheck();
                    rdPreguntaCuantosCoches.clearCheck();
                    rdPreguntaCuartos.clearCheck();
                    rdPreguntaCuartosDormir.clearCheck();
                    rdPreguntaFocos.clearCheck();
                    rdPreguntaBanos.clearCheck();
                    rdPreguntaRegadera.clearCheck();
                    rdPreguntaInternet.clearCheck();
                    rdPreguntaTrabajaron.clearCheck();
                    rdPreguntaEstufa.clearCheck();
                    rdPreguntaTipoPiso.clearCheck();
                    rdPreguntaTipoVivienda.clearCheck();
                    rdPreguntaGenero.clearCheck();

                    opHijos = "No aplica";
                    opAporta = "No aplica";
                    opOcupacion = "No aplica";
                    opCoche = "No aplica";
                    opCuantosCoches = "No aplica";
                    opCuartos = "No aplica";
                    opCuartosDormir = "No aplica";
                    opFocos = "No aplica";
                    opBanos = "No aplica";
                    opRegadera = "No aplica";
                    opInternet = "No aplica";
                    opTrabajaron = "No aplica";
                    opEstufa = "No aplica";
                    opTipoPiso = "No aplica";
                    opTipoVivienda = "No aplica";
                    opGenero = "No aplica";
                }

                else if (checkedId == R.id.radio0) {
                    opvive = "No sabe / No contestó";

                    opAbandono = "4";
                    tipoEncuesta = "FILTRO";
                    btnGuardar.setText("Guardar Filtro");
                    radio_abandono1.setChecked(false);
                    radio_abandono4.setChecked(true);

                    radio_abandono1.setVisibility(View.GONE);
                    radio_abandono2.setVisibility(View.GONE);
                    radio_abandono3.setVisibility(View.GONE);
                    radio_abandono4.setVisibility(View.VISIBLE);

                    spinner0.setSelection(0);

                    lay0 .setVisibility(View.GONE);
                    lay1 .setVisibility(View.GONE);
                    lay2 .setVisibility(View.GONE);
                    lay3 .setVisibility(View.GONE);
                    lay4 .setVisibility(View.GONE);
                    lay5 .setVisibility(View.GONE);
                    layc1 .setVisibility(View.GONE);
                    layc1a .setVisibility(View.GONE);
                    layc1b .setVisibility(View.GONE);
                    layc1c .setVisibility(View.GONE);
                    layc1d .setVisibility(View.GONE);
                    layc1e .setVisibility(View.GONE);
                    layc1f .setVisibility(View.GONE);
                    layc1g .setVisibility(View.GONE);
                    layc1h .setVisibility(View.GONE);
                    layc2 .setVisibility(View.GONE);
                    layc3 .setVisibility(View.GONE);
                    layc3a .setVisibility(View.GONE);
                    layc3b .setVisibility(View.GONE);
                    layc3c .setVisibility(View.GONE);
                    layc3d .setVisibility(View.GONE);
                    layc3e .setVisibility(View.GONE);
                    layc4 .setVisibility(View.GONE);
                    layc4a .setVisibility(View.GONE);
                    layc4b .setVisibility(View.GONE);
                    layc4c .setVisibility(View.GONE);
                    layc5 .setVisibility(View.GONE);
                    layc5a .setVisibility(View.GONE);
                    layc5b .setVisibility(View.GONE);
                    layc5c .setVisibility(View.GONE);
                    layc5d .setVisibility(View.GONE);
                    layc5e .setVisibility(View.GONE);
                    layc6 .setVisibility(View.GONE);

                    rdPregunta0.clearCheck(); op0="No aplica";
                    rdPregunta1.clearCheck(); op1="No aplica";
                    rdPregunta2.clearCheck(); op2="No aplica";
                    rdPregunta3.clearCheck(); op3="No aplica";
                    rdPregunta4.clearCheck(); op4="No aplica";
                    rdPregunta5.clearCheck(); op5="No aplica";
//                    rdPreguntac1.clearCheck(); opc1="No aplica";
                    rdPreguntac1a.clearCheck(); opc1a="No aplica";
                    rdPreguntac1b.clearCheck(); opc1b="No aplica";
                    rdPreguntac1c.clearCheck(); opc1c="No aplica";
                    rdPreguntac1d.clearCheck(); opc1d="No aplica";
                    rdPreguntac1e.clearCheck(); opc1e="No aplica";
                    rdPreguntac1f.clearCheck(); opc1f="No aplica";
                    rdPreguntac1g.clearCheck(); opc1g="No aplica";
                    rdPreguntac1h.clearCheck(); opc1h="No aplica";
                    rdPreguntac2.clearCheck(); opc2="No aplica";
//                    rdPreguntac3.clearCheck(); opc3="No aplica";
                    rdPreguntac3a.clearCheck(); opc3a="No aplica";
                    rdPreguntac3b.clearCheck(); opc3b="No aplica";
                    rdPreguntac3c.clearCheck(); opc3c="No aplica";
                    rdPreguntac3d.clearCheck(); opc3d="No aplica";
                    rdPreguntac3e.clearCheck(); opc3e="No aplica";
//                    rdPreguntac4.clearCheck(); opc4="No aplica";
                    rdPreguntac4a.clearCheck(); opc4a="No aplica";
                    rdPreguntac4b.clearCheck(); opc4b="No aplica";
                    rdPreguntac4c.clearCheck(); opc4c="No aplica";
//                    rdPreguntac5.clearCheck(); opc5="No aplica";
                    rdPreguntac5a.clearCheck(); opc5a="No aplica";
                    rdPreguntac5b.clearCheck(); opc5b="No aplica";
                    rdPreguntac5c.clearCheck(); opc5c="No aplica";
                    rdPreguntac5d.clearCheck(); opc5d="No aplica";
                    rdPreguntac5e.clearCheck(); opc5e="No aplica";
                    rdPreguntac6.clearCheck(); opc6="No aplica";

                    laySocioE.setVisibility(View.GONE);
                    layEst.setVisibility(View.GONE);
                    layHijos.setVisibility(View.GONE);
                    layAporta.setVisibility(View.GONE);
                    layOcupacion.setVisibility(View.GONE);
                    laycoche.setVisibility(View.GONE);
                    layCuantosCoches.setVisibility(View.GONE);
                    layCuartos.setVisibility(View.GONE);
                    layCuartosDormir.setVisibility(View.GONE);
                    layFocos.setVisibility(View.GONE);
                    layBanos.setVisibility(View.GONE);
                    layRegadera.setVisibility(View.GONE);
                    layInternet.setVisibility(View.GONE);
                    layTrabajaron.setVisibility(View.GONE);
                    layEstufa.setVisibility(View.GONE);
                    layEdad.setVisibility(View.GONE);
                    layTipoPiso.setVisibility(View.GONE);
                    layTipoVivienda.setVisibility(View.GONE);
                    layGenero.setVisibility(View.GONE);

                    rdPreguntaHijos.clearCheck();
                    rdPreguntaAporta.clearCheck();
                    rdPreguntaOcupacion.clearCheck();
                    rdPreguntaCoche.clearCheck();
                    rdPreguntaCuantosCoches.clearCheck();
                    rdPreguntaCuartos.clearCheck();
                    rdPreguntaCuartosDormir.clearCheck();
                    rdPreguntaFocos.clearCheck();
                    rdPreguntaBanos.clearCheck();
                    rdPreguntaRegadera.clearCheck();
                    rdPreguntaInternet.clearCheck();
                    rdPreguntaTrabajaron.clearCheck();
                    rdPreguntaEstufa.clearCheck();
                    rdPreguntaTipoPiso.clearCheck();
                    rdPreguntaTipoVivienda.clearCheck();
                    rdPreguntaGenero.clearCheck();

                    opHijos = "No aplica";
                    opAporta = "No aplica";
                    opOcupacion = "No aplica";
                    opCoche = "No aplica";
                    opCuantosCoches = "No aplica";
                    opCuartos = "No aplica";
                    opCuartosDormir = "No aplica";
                    opFocos = "No aplica";
                    opBanos = "No aplica";
                    opRegadera = "No aplica";
                    opInternet = "No aplica";
                    opTrabajaron = "No aplica";
                    opEstufa = "No aplica";
                    opTipoPiso = "No aplica";
                    opTipoVivienda = "No aplica";
                    opGenero = "No aplica";
                }

            }
        });



        rdPregunta0.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op0 = "Otro estado";


                    opAbandono = "4";
                    tipoEncuesta = "FILTRO";
                    btnGuardar.setText("Guardar Filtro");
                    radio_abandono1.setChecked(false);
                    radio_abandono4.setChecked(true);

                    radio_abandono1.setVisibility(View.GONE);
                    radio_abandono2.setVisibility(View.GONE);
                    radio_abandono3.setVisibility(View.GONE);
                    radio_abandono4.setVisibility(View.VISIBLE);

                    spinner0.setSelection(0);

                    lay1 .setVisibility(View.GONE);
                    lay2 .setVisibility(View.GONE);
                    lay3 .setVisibility(View.GONE);
                    lay4 .setVisibility(View.GONE);
                    lay5 .setVisibility(View.GONE);
                    layc1 .setVisibility(View.GONE);
                    layc1a .setVisibility(View.GONE);
                    layc1b .setVisibility(View.GONE);
                    layc1c .setVisibility(View.GONE);
                    layc1d .setVisibility(View.GONE);
                    layc1e .setVisibility(View.GONE);
                    layc1f .setVisibility(View.GONE);
                    layc1g .setVisibility(View.GONE);
                    layc1h .setVisibility(View.GONE);
                    layc2 .setVisibility(View.GONE);
                    layc3 .setVisibility(View.GONE);
                    layc3a .setVisibility(View.GONE);
                    layc3b .setVisibility(View.GONE);
                    layc3c .setVisibility(View.GONE);
                    layc3d .setVisibility(View.GONE);
                    layc3e .setVisibility(View.GONE);
                    layc4 .setVisibility(View.GONE);
                    layc4a .setVisibility(View.GONE);
                    layc4b .setVisibility(View.GONE);
                    layc4c .setVisibility(View.GONE);
                    layc5 .setVisibility(View.GONE);
                    layc5a .setVisibility(View.GONE);
                    layc5b .setVisibility(View.GONE);
                    layc5c .setVisibility(View.GONE);
                    layc5d .setVisibility(View.GONE);
                    layc5e .setVisibility(View.GONE);
                    layc6 .setVisibility(View.GONE);

                    rdPregunta1.clearCheck(); op1="No aplica";
                    rdPregunta2.clearCheck(); op2="No aplica";
                    rdPregunta3.clearCheck(); op3="No aplica";
                    rdPregunta4.clearCheck(); op4="No aplica";
                    rdPregunta5.clearCheck(); op5="No aplica";
//                    rdPreguntac1.clearCheck(); opc1="No aplica";
                    rdPreguntac1a.clearCheck(); opc1a="No aplica";
                    rdPreguntac1b.clearCheck(); opc1b="No aplica";
                    rdPreguntac1c.clearCheck(); opc1c="No aplica";
                    rdPreguntac1d.clearCheck(); opc1d="No aplica";
                    rdPreguntac1e.clearCheck(); opc1e="No aplica";
                    rdPreguntac1f.clearCheck(); opc1f="No aplica";
                    rdPreguntac1g.clearCheck(); opc1g="No aplica";
                    rdPreguntac1h.clearCheck(); opc1h="No aplica";
                    rdPreguntac2.clearCheck(); opc2="No aplica";
//                    rdPreguntac3.clearCheck(); opc3="No aplica";
                    rdPreguntac3a.clearCheck(); opc3a="No aplica";
                    rdPreguntac3b.clearCheck(); opc3b="No aplica";
                    rdPreguntac3c.clearCheck(); opc3c="No aplica";
                    rdPreguntac3d.clearCheck(); opc3d="No aplica";
                    rdPreguntac3e.clearCheck(); opc3e="No aplica";
//                    rdPreguntac4.clearCheck(); opc4="No aplica";
                    rdPreguntac4a.clearCheck(); opc4a="No aplica";
                    rdPreguntac4b.clearCheck(); opc4b="No aplica";
                    rdPreguntac4c.clearCheck(); opc4c="No aplica";
//                    rdPreguntac5.clearCheck(); opc5="No aplica";
                    rdPreguntac5a.clearCheck(); opc5a="No aplica";
                    rdPreguntac5b.clearCheck(); opc5b="No aplica";
                    rdPreguntac5c.clearCheck(); opc5c="No aplica";
                    rdPreguntac5d.clearCheck(); opc5d="No aplica";
                    rdPreguntac5e.clearCheck(); opc5e="No aplica";
                    rdPreguntac6.clearCheck(); opc6="No aplica";

                    laySocioE.setVisibility(View.GONE);
                    layEst.setVisibility(View.GONE);
                    layHijos.setVisibility(View.GONE);
                    layAporta.setVisibility(View.GONE);
                    layOcupacion.setVisibility(View.GONE);
                    laycoche.setVisibility(View.GONE);
                    layCuantosCoches.setVisibility(View.GONE);
                    layCuartos.setVisibility(View.GONE);
                    layCuartosDormir.setVisibility(View.GONE);
                    layFocos.setVisibility(View.GONE);
                    layBanos.setVisibility(View.GONE);
                    layRegadera.setVisibility(View.GONE);
                    layInternet.setVisibility(View.GONE);
                    layTrabajaron.setVisibility(View.GONE);
                    layEstufa.setVisibility(View.GONE);
                    layEdad.setVisibility(View.GONE);
                    layTipoPiso.setVisibility(View.GONE);
                    layTipoVivienda.setVisibility(View.GONE);
                    layGenero.setVisibility(View.GONE);

                    rdPreguntaHijos.clearCheck();
                    rdPreguntaAporta.clearCheck();
                    rdPreguntaOcupacion.clearCheck();
                    rdPreguntaCoche.clearCheck();
                    rdPreguntaCuantosCoches.clearCheck();
                    rdPreguntaCuartos.clearCheck();
                    rdPreguntaCuartosDormir.clearCheck();
                    rdPreguntaFocos.clearCheck();
                    rdPreguntaBanos.clearCheck();
                    rdPreguntaRegadera.clearCheck();
                    rdPreguntaInternet.clearCheck();
                    rdPreguntaTrabajaron.clearCheck();
                    rdPreguntaEstufa.clearCheck();
                    rdPreguntaTipoPiso.clearCheck();
                    rdPreguntaTipoVivienda.clearCheck();
                    rdPreguntaGenero.clearCheck();

                    opHijos = "No aplica";
                    opAporta = "No aplica";
                    opOcupacion = "No aplica";
                    opCoche = "No aplica";
                    opCuantosCoches = "No aplica";
                    opCuartos = "No aplica";
                    opCuartosDormir = "No aplica";
                    opFocos = "No aplica";
                    opBanos = "No aplica";
                    opRegadera = "No aplica";
                    opInternet = "No aplica";
                    opTrabajaron = "No aplica";
                    opEstufa = "No aplica";
                    opTipoPiso = "No aplica";
                    opTipoVivienda = "No aplica";
                    opGenero = "No aplica";

                } else if (checkedId == R.id.radio0) {
                    op0 = "No sabe / No contestó";

                    radio_abandono1.setVisibility(View.GONE);
                    radio_abandono2.setVisibility(View.GONE);
                    radio_abandono3.setVisibility(View.GONE);
                    radio_abandono4.setVisibility(View.VISIBLE);

                    opAbandono = "4";
                    tipoEncuesta = "FILTRO";
                    btnGuardar.setText("Guardar Filtro");
                    radio_abandono1.setChecked(false);
                    radio_abandono4.setChecked(true);
                    spinner0.setSelection(0);

                    lay1 .setVisibility(View.GONE);
                    lay2 .setVisibility(View.GONE);
                    lay3 .setVisibility(View.GONE);
                    lay4 .setVisibility(View.GONE);
                    lay5 .setVisibility(View.GONE);
                    layc1 .setVisibility(View.GONE);
                    layc1a .setVisibility(View.GONE);
                    layc1b .setVisibility(View.GONE);
                    layc1c .setVisibility(View.GONE);
                    layc1d .setVisibility(View.GONE);
                    layc1e .setVisibility(View.GONE);
                    layc1f .setVisibility(View.GONE);
                    layc1g .setVisibility(View.GONE);
                    layc1h .setVisibility(View.GONE);
                    layc2 .setVisibility(View.GONE);
                    layc3 .setVisibility(View.GONE);
                    layc3a .setVisibility(View.GONE);
                    layc3b .setVisibility(View.GONE);
                    layc3c .setVisibility(View.GONE);
                    layc3d .setVisibility(View.GONE);
                    layc3e .setVisibility(View.GONE);
                    layc4 .setVisibility(View.GONE);
                    layc4a .setVisibility(View.GONE);
                    layc4b .setVisibility(View.GONE);
                    layc4c .setVisibility(View.GONE);
                    layc5 .setVisibility(View.GONE);
                    layc5a .setVisibility(View.GONE);
                    layc5b .setVisibility(View.GONE);
                    layc5c .setVisibility(View.GONE);
                    layc5d .setVisibility(View.GONE);
                    layc5e .setVisibility(View.GONE);
                    layc6 .setVisibility(View.GONE);

                    rdPregunta1.clearCheck(); op1="No aplica";
                    rdPregunta2.clearCheck(); op2="No aplica";
                    rdPregunta3.clearCheck(); op3="No aplica";
                    rdPregunta4.clearCheck(); op4="No aplica";
                    rdPregunta5.clearCheck(); op5="No aplica";
//                    rdPreguntac1.clearCheck(); opc1="No aplica";
                    rdPreguntac1a.clearCheck(); opc1a="No aplica";
                    rdPreguntac1b.clearCheck(); opc1b="No aplica";
                    rdPreguntac1c.clearCheck(); opc1c="No aplica";
                    rdPreguntac1d.clearCheck(); opc1d="No aplica";
                    rdPreguntac1e.clearCheck(); opc1e="No aplica";
                    rdPreguntac1f.clearCheck(); opc1f="No aplica";
                    rdPreguntac1g.clearCheck(); opc1g="No aplica";
                    rdPreguntac1h.clearCheck(); opc1h="No aplica";
                    rdPreguntac2.clearCheck(); opc2="No aplica";
//                    rdPreguntac3.clearCheck(); opc3="No aplica";
                    rdPreguntac3a.clearCheck(); opc3a="No aplica";
                    rdPreguntac3b.clearCheck(); opc3b="No aplica";
                    rdPreguntac3c.clearCheck(); opc3c="No aplica";
                    rdPreguntac3d.clearCheck(); opc3d="No aplica";
                    rdPreguntac3e.clearCheck(); opc3e="No aplica";
//                    rdPreguntac4.clearCheck(); opc4="No aplica";
                    rdPreguntac4a.clearCheck(); opc4a="No aplica";
                    rdPreguntac4b.clearCheck(); opc4b="No aplica";
                    rdPreguntac4c.clearCheck(); opc4c="No aplica";
//                    rdPreguntac5.clearCheck(); opc5="No aplica";
                    rdPreguntac5a.clearCheck(); opc5a="No aplica";
                    rdPreguntac5b.clearCheck(); opc5b="No aplica";
                    rdPreguntac5c.clearCheck(); opc5c="No aplica";
                    rdPreguntac5d.clearCheck(); opc5d="No aplica";
                    rdPreguntac5e.clearCheck(); opc5e="No aplica";
                    rdPreguntac6.clearCheck(); opc6="No aplica";


                    laySocioE.setVisibility(View.GONE);
                    layEst.setVisibility(View.GONE);
                    layHijos.setVisibility(View.GONE);
                    layAporta.setVisibility(View.GONE);
                    layOcupacion.setVisibility(View.GONE);
                    laycoche.setVisibility(View.GONE);
                    layCuantosCoches.setVisibility(View.GONE);
                    layCuartos.setVisibility(View.GONE);
                    layCuartosDormir.setVisibility(View.GONE);
                    layFocos.setVisibility(View.GONE);
                    layBanos.setVisibility(View.GONE);
                    layRegadera.setVisibility(View.GONE);
                    layInternet.setVisibility(View.GONE);
                    layTrabajaron.setVisibility(View.GONE);
                    layEstufa.setVisibility(View.GONE);
                    layEdad.setVisibility(View.GONE);
                    layTipoPiso.setVisibility(View.GONE);
                    layTipoVivienda.setVisibility(View.GONE);
                    layGenero.setVisibility(View.GONE);

                    rdPreguntaHijos.clearCheck();
                    rdPreguntaAporta.clearCheck();
                    rdPreguntaOcupacion.clearCheck();
                    rdPreguntaCoche.clearCheck();
                    rdPreguntaCuantosCoches.clearCheck();
                    rdPreguntaCuartos.clearCheck();
                    rdPreguntaCuartosDormir.clearCheck();
                    rdPreguntaFocos.clearCheck();
                    rdPreguntaBanos.clearCheck();
                    rdPreguntaRegadera.clearCheck();
                    rdPreguntaInternet.clearCheck();
                    rdPreguntaTrabajaron.clearCheck();
                    rdPreguntaEstufa.clearCheck();
                    rdPreguntaTipoPiso.clearCheck();
                    rdPreguntaTipoVivienda.clearCheck();
                    rdPreguntaGenero.clearCheck();

                    opHijos = "No aplica";
                    opAporta = "No aplica";
                    opOcupacion = "No aplica";
                    opCoche = "No aplica";
                    opCuantosCoches = "No aplica";
                    opCuartos = "No aplica";
                    opCuartosDormir = "No aplica";
                    opFocos = "No aplica";
                    opBanos = "No aplica";
                    opRegadera = "No aplica";
                    opInternet = "No aplica";
                    opTrabajaron = "No aplica";
                    opEstufa = "No aplica";
                    opTipoPiso = "No aplica";
                    opTipoVivienda = "No aplica";
                    opGenero = "No aplica";
                }
            }

        });


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        rdPregunta1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op1 = "Televisión";
                } else if (checkedId == R.id.radio2) {
                    op1 = "Radio";
                } else if (checkedId == R.id.radio3) {
                    op1 = "Periódico";
                } else if (checkedId == R.id.radio4) {
                    op1 = "Redes Sociales";
                } else if (checkedId == R.id.radio5) {
                    op1 = "Internet";
                } else if (checkedId == R.id.radio6) {
                    op1 = "Otra";
                } else if (checkedId == R.id.radio0) {
                    op1 = "No sabe / No contestó";
                } else {
                    op1 = "";
                }

            }
        });

        rdPregunta2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op2 = "Si";
                } else if (checkedId == R.id.radio2) {
                    op2 = "No";
                } else if (checkedId == R.id.radio0) {
                    op2 = "No sabe / No contestó";
                    ;
                } else {
                    op2 = "";

                }

            }
        });
        rdPregunta3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op3 = "Si";
                } else if (checkedId == R.id.radio2) {
                    op3 = "No";
                } else if (checkedId == R.id.radio0) {
                    op3 = "No sabe / No contestó";
                    ;
                } else {
                    op3 = "";

                }

            }
        });



        rdPregunta4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op4 = "De acuerdo";
                }

                else if (checkedId == R.id.radio2) {
                    op4 = "Ni de acuerdo, ni en desacuerdo";
                }

                else if (checkedId == R.id.radio3) {
                    op4 = "En desacuerdo";
                }

                else if (checkedId == R.id.radio0) {
                    op4 = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPregunta5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    op5 = "Si";
                }

                else if (checkedId == R.id.radio2) {
                    op5 = "No";
                }

                else if (checkedId == R.id.radio0) {
                    op5 = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        rdPreguntac1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        rdPreguntac1a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1a = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1a = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1a = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1a = "Hasta el próximo ciclo escolar/ septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1a = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1a = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1a = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1b = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1b = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1b = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1b = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1b = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1b = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1b = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1c = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1c = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1c = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1c = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1c = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1c = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1c = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1d = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1d = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1d = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1d = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1d = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1d = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1d = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1e = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1e = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1e = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1e = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1e = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1e = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1e = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1f.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1f = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1f = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1f = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1f = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1f = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1f = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1f = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1g.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1g = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1g = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1g = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1g = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1g = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1g = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1g = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac1h.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc1h = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc1h = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc1h = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc1h = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc1h = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc1h = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc1h = "No sabe / No Contestó";
                }

            }
        });


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc2 = "Saldrá usando tapabocas, respetando la sana distancia y usando varias veces al día gel antibacterial y teniendo mucho cuidado al regresar a casa";
                }

                else if (checkedId == R.id.radio2) {
                    opc2 = "Saldrá normal, solo respetando las medidas que tenga cada lugar al que vaya";
                }

                else if (checkedId == R.id.radio3) {
                    opc2 = "Saldrá sin ninguna medida adicional, pues no es necesaria";
                }

                else if (checkedId == R.id.radio4) {
                    opc2 = "Hasta ahora ha salido normalmente";
                }

                else if (checkedId == R.id.radio0) {
                    opc2 = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3a = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc3a = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc3a = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc3a = "Hasta el próximo ciclo escolar/ septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc3a = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc3a = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc3a = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3b = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc3b = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc3b = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc3b = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc3b = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc3b = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc3b = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3c = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc3c = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc3c = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc3c = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc3c = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc3c = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc3c = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3d = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc3d = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc3d = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc3d = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc3d = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc3d = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc3d = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac3e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc3e = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc3e = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc3e = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc3e = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc3e = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc3e = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc3e = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac4a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc4a = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc4a = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc4a = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc4a = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc4a = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc4a = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc4a = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac4b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc4b = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc4b = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc4b = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc4b = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc4b = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc4b = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc4b = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac4c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc4c = "Inmediatamente/ en junio";
                }

                else if (checkedId == R.id.radio2) {
                    opc4c = "En julio";
                }

                else if (checkedId == R.id.radio3) {
                    opc4c = "Agosto";
                }

                else if (checkedId == R.id.radio4) {
                    opc4c = "Septiembre";
                }

                else if (checkedId == R.id.radio5) {
                    opc4c = "Entre octubre y diciembre";
                }

                else if (checkedId == R.id.radio6) {
                    opc4c = "Hasta el próximo año";
                }

                else if (checkedId == R.id.radio0) {
                    opc4c = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {



            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac5a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5a = "Ansiedad";
                }

                else if (checkedId == R.id.radio2) {
                    opc5a = "Tranquilidad";
                }

                else if (checkedId == R.id.radio0) {
                    opc5a = "No sabe / No Contestó";
                }

            }
        });



        rdPreguntac5b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5b = "Felicidad";
                }

                else if (checkedId == R.id.radio2) {
                    opc5b = "Tristeza";
                }

                else if (checkedId == R.id.radio0) {
                    opc5b = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac5c.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5c = "Alegría";
                }

                else if (checkedId == R.id.radio2) {
                    opc5c = "Enojo";
                }

                else if (checkedId == R.id.radio0) {
                    opc5c = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac5d.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5d = "Frustración";
                }

                else if (checkedId == R.id.radio2) {
                    opc5d = "Esperanza";
                }

                else if (checkedId == R.id.radio0) {
                    opc5d = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac5e.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc5e = "Confianza";
                }

                else if (checkedId == R.id.radio2) {
                    opc5e = "Miedo";
                }

                else if (checkedId == R.id.radio0) {
                    opc5e = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        rdPreguntac6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opc6 = "Muy probable";
                }

                else if (checkedId == R.id.radio2) {
                    opc6 = "Probable";
                }

                else if (checkedId == R.id.radio3) {
                    opc6 = "Poco probable";
                }

                else if (checkedId == R.id.radio4) {
                    opc6 = "Nada probable";
                }

                else if (checkedId == R.id.radio0) {
                    opc6 = "No sabe / No Contestó";
                }

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////





        ///////////////////////////////////// SOCIOECONOMICOS  ///////////////////////////////////// //////////////////////////////////////////////////////////////////////

        rdPreguntaHijos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opHijos = "Si";
                } else if (checkedId == R.id.radio2) {
                    opHijos = "No";
                } else if (checkedId == R.id.radio0) {
                    opHijos = "No sabe / No contestó";
                } else {
                    opHijos = "";

                }

            }
        });

        rdPreguntaAporta.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opAporta = "No completó primaria";
                } else if (checkedId == R.id.radio2) {
                    opAporta = "Primaria o secundaria";
                } else if (checkedId == R.id.radio3) {
                    opAporta = "Preparatoria o carrera técnica";
                } else if (checkedId == R.id.radio4) {
                    opAporta = "Licenciatura";
                } else if (checkedId == R.id.radio5) {
                    opAporta = "Posgrado";
                } else if (checkedId == R.id.radio0) {
                    opAporta = "No contestó";
                } else {
                    opAporta = "";

                }

            }
        });

        rdPreguntaOcupacion.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opOcupacion = "Hogar";
                } else if (checkedId == R.id.radio2) {
                    opOcupacion = "Estudiante";
                } else if (checkedId == R.id.radio3) {
                    opOcupacion = "Profesionista";
                } else if (checkedId == R.id.radio4) {
                    opOcupacion = "Empleado";
                } else if (checkedId == R.id.radio5) {
                    opOcupacion = "Obrero / oficio";
                } else if (checkedId == R.id.radio6) {
                    opOcupacion = "Comerciante";
                } else if (checkedId == R.id.radio7) {
                    opOcupacion = "Jubilado";
                } else if (checkedId == R.id.radio8) {
                    opOcupacion = "Otro";
                } else if (checkedId == R.id.radio9) {
                    opOcupacion = "Desempleado";
                } else if (checkedId == R.id.radio0) {
                    opOcupacion = "No contestó";
                } else {
                    opOcupacion = "";

                }

            }
        });

        rdPreguntaCoche.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCoche = "Si";
                    layCuantosCoches.setVisibility(View.VISIBLE);
                    opCuantosCoches = "sin datos";
                } else if (checkedId == R.id.radio2) {
                    opCoche = "No";
                    layCuantosCoches.setVisibility(View.GONE);
                    rdPreguntaCuantosCoches.clearCheck();
                    opCuantosCoches = "Ninguno";

                } else if (checkedId == R.id.radio0) {
                    opCoche = "No contestó";
                    layCuantosCoches.setVisibility(View.GONE);
                    rdPreguntaCuantosCoches.clearCheck();
                    opCuantosCoches = "Ninguno";

                } else {
                    opCoche = "";

                }

            }
        });

        rdPreguntaCuantosCoches.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuantosCoches = "Ninguno";
                } else if (checkedId == R.id.radio2) {
                    opCuantosCoches = "Uno";
                } else if (checkedId == R.id.radio3) {
                    opCuantosCoches = "Dos";
                } else if (checkedId == R.id.radio4) {
                    opCuantosCoches = "Tres";
                } else if (checkedId == R.id.radio5) {
                    opCuantosCoches = "Cuatro o más";
                } else if (checkedId == R.id.radio0) {
                    opCuantosCoches = "No contestó";
                } else {
                    opCuantosCoches = "";

                }

            }
        });

        rdPreguntaCuartos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuartos = "1";
                } else if (checkedId == R.id.radio2) {
                    opCuartos = "2";
                } else if (checkedId == R.id.radio3) {
                    opCuartos = "3";
                } else if (checkedId == R.id.radio4) {
                    opCuartos = "4";
                } else if (checkedId == R.id.radio5) {
                    opCuartos = "5";
                } else if (checkedId == R.id.radio6) {
                    opCuartos = "6";
                } else if (checkedId == R.id.radio7) {
                    opCuartos = "7";
                } else if (checkedId == R.id.radio8) {
                    opCuartos = "8";
                } else if (checkedId == R.id.radio9) {
                    opCuartos = "9";
                } else if (checkedId == R.id.radio10) {
                    opCuartos = "10";
                } else if (checkedId == R.id.radio0) {
                    opCuartos = "No contestó";
                } else {
                    opCuartos = "";

                }

            }
        });

        rdPreguntaCuartosDormir.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opCuartosDormir = "1";
                } else if (checkedId == R.id.radio2) {
                    opCuartosDormir = "2";
                } else if (checkedId == R.id.radio3) {
                    opCuartosDormir = "3";
                } else if (checkedId == R.id.radio4) {
                    opCuartosDormir = "4";
                } else if (checkedId == R.id.radio5) {
                    opCuartosDormir = "5";
                } else if (checkedId == R.id.radio6) {
                    opCuartosDormir = "6";
                } else if (checkedId == R.id.radio7) {
                    opCuartosDormir = "7";
                } else if (checkedId == R.id.radio8) {
                    opCuartosDormir = "8";
                } else if (checkedId == R.id.radio9) {
                    opCuartosDormir = "9";
                } else if (checkedId == R.id.radio10) {
                    opCuartosDormir = "10";
                } else if (checkedId == R.id.radio0) {
                    opCuartosDormir = "No contestó";
                } else {
                    opCuartosDormir = "";

                }

            }
        });

        rdPreguntaFocos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opFocos = "0 a 5";
                } else if (checkedId == R.id.radio2) {
                    opFocos = "6 a 10";
                } else if (checkedId == R.id.radio3) {
                    opFocos = "11 a 15";
                } else if (checkedId == R.id.radio4) {
                    opFocos = "16 a 20";
                } else if (checkedId == R.id.radio5) {
                    opFocos = "21 o más";
                } else if (checkedId == R.id.radio0) {
                    opFocos = "No contestó";
                } else {
                    opFocos = "";

                }

            }
        });

        rdPreguntaBanos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opBanos = "Ninguno";
                } else if (checkedId == R.id.radio2) {
                    opBanos = "Uno";
                } else if (checkedId == R.id.radio3) {
                    opBanos = "Dos o Tres";
                } else if (checkedId == R.id.radio4) {
                    opBanos = "Cuatro o más";
                } else if (checkedId == R.id.radio0) {
                    opBanos = "No contestó";
                } else {
                    opBanos = "";

                }

            }
        });

        rdPreguntaRegadera.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opRegadera = "Si";

                } else if (checkedId == R.id.radio2) {
                    opRegadera = "No";
                } else if (checkedId == R.id.radio0) {
                    opRegadera = "No contestó";
                } else {
                    opRegadera = "";

                }

            }
        });

        rdPreguntaInternet.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radio1) {
                    opInternet = "Si";

                } else if (checkedId == R.id.radio2) {
                    opInternet = "No";
                } else if (checkedId == R.id.radio0) {
                    opInternet = "No sabe / no contestó";
                } else {
                    opInternet = "";

                }


            }
        });


        rdPreguntaTrabajaron.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                if (checkedId == R.id.radio1) {
                    opTrabajaron = "Ninguno";

                } else if (checkedId == R.id.radio2) {
                    opTrabajaron = "Uno";
                } else if (checkedId == R.id.radio3) {
                    opTrabajaron = "Dos";
                } else if (checkedId == R.id.radio4) {
                    opTrabajaron = "Tres";
                } else if (checkedId == R.id.radio5) {
                    opTrabajaron = "Cuatro o más";
                } else if (checkedId == R.id.radio0) {
                    opTrabajaron = "No sabe / no contestó";
                } else {
                    opTrabajaron = "";

                }


            }
        });

        rdPreguntaEstufa.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opEstufa = "Si";

                } else if (checkedId == R.id.radio2) {
                    opEstufa = "No";
                } else if (checkedId == R.id.radio0) {
                    opEstufa = "No contestó";
                } else {
                    opEstufa = "";

                }

            }
        });

        rdPreguntaEdad.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opEdad = "Menor de 18 años";
                } else if (checkedId == R.id.radio2) {
                    opEdad = "18 a 29";
                } else if (checkedId == R.id.radio3) {
                    opEdad = "30 a 39";
                } else if (checkedId == R.id.radio4) {
                    opEdad = "40 a 49";
                } else if (checkedId == R.id.radio5) {
                    opEdad = "50 a 59";
                } else if (checkedId == R.id.radio6) {
                    opEdad = "60 a 69";
                } else if (checkedId == R.id.radio7) {
                    opEdad = "70 o más";
                } else if (checkedId == R.id.radio0) {
                    opEdad = "No contestó";
                } else {
                    opEdad = "";

                }

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

        rdPreguntaTipoVivienda.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opTipoVivienda = "Casa";
                } else if (checkedId == R.id.radio2) {
                    opTipoVivienda = "Condominio";
                } else if (checkedId == R.id.radio0) {
                    opTipoVivienda = "No contestó";
                } else {
                    opTipoVivienda = "";

                }

            }
        });

        rdPreguntaTipoPiso.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radio1) {
                    opTipoPiso = "Tierra o cemento";
                } else if (checkedId == R.id.radio2) {
                    opTipoPiso = "Cualquier otro";
                } else if (checkedId == R.id.radio0) {
                    opTipoPiso = "No contestó";
                } else {
                    opTipoPiso = "";

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

    class CierraEncuesta extends TimerTask {

        @Override
        public void run() {

            dialogoCierraEncuesta();

        }

    }

    public void drawResults() {
        for (CheckBox c : mChecks) {
            c.setChecked(mSelectedChecks.contains(c));
        }
    }

    public void drawResults2() {
        for (CheckBox d : mChecks2) {
            d.setChecked(mSelectedChecks2.contains(d));
        }
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


        String strId = String.valueOf(rand + 1);

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        String strText0;
        if (spinner0.getSelectedItem().toString().equals("Selecciona")) {
            strText0 = op0;
        } else {
            strText0 = spinner0.getSelectedItem().toString();
            rdPregunta0.clearCheck();
        }


        String strvive = opvive;
        String str0 = strText0;
        String str1 = op1;
        String str2 = op2;
        String str3 = op3;
        String str4 = op4;
        String str5 = op5;
        String strc1 = "Pregunta";
        String strc1a = opc1a;
        String strc1b = opc1b;
        String strc1c = opc1c;
        String strc1d = opc1d;
        String strc1e = opc1e;
        String strc1f = opc1f;
        String strc1g = opc1g;
        String strc1h = opc1h;
        String strc2 = opc2;
        String strc3 = "Pregunta";
        String strc3a = opc3a;
        String strc3b = opc3b;
        String strc3c = opc3c;
        String strc3d = opc3d;
        String strc3e = opc3e;
        String strc4 = "Pregunta";
        String strc4a = opc4a;
        String strc4b = opc4b;
        String strc4c = opc4c;
        String strc5 = "Pregunta";
        String strc5a = opc5a;
        String strc5b = opc5b;
        String strc5c = opc5c;
        String strc5d = opc5d;
        String strc5e = opc5e;
        String strc6 = opc6;


        String strJefe = opJefe;
        String strHijos = opHijos;
        String strAporta = opAporta;
        String strOcupacion = opOcupacion;
        String strCoches = opCoche;// coche
        String strCuantosCoches = opCuantosCoches;
        String strCuartos = opCuartos;
        String strCuartosDormir = opCuartosDormir;
        String strFocos = opFocos;
        String strBanos = opBanos;
        String strRegadera = opRegadera;
        String strInternet = opInternet;
        String strTrabajaron = opTrabajaron;
        String strEstufa = opEstufa;
        String strEdad = opEdad;
        String strGenero = opGenero;
        String strTipoVivienda = opTipoVivienda;
        String strTipoPiso = opTipoPiso;
        String strAbandono = opAbandono;

        if (strAbandono.matches("1")) {
            tipoEncuesta = "NORMAL";
        }

        String strEstudios = opAporta;
        String strCocheCuantos = opCuantosCoches;
        String strFoco = opFocos;
        String strCuarto = opCuartos;
        String strBano = opBanos;
        String strRega = opRegadera;
        String strEstu = opEstufa;
        String strPiso = opTipoPiso;

        // estudios
        if (strEstudios.matches("sin datos")) {
            strEstudios = "0";
        } else if (strEstudios.matches("No completó primaria")) {
            strEstudios = "0";
        } else if (strEstudios.matches("Primaria o secundaria")) {
            strEstudios = "22";
        } else if (strEstudios.matches("Preparatoria o carrera técnica")) {
            strEstudios = "38";
        } else if (strEstudios.matches("Licenciatura")) {
            strEstudios = "52";
        } else if (strEstudios.matches("Posgrado")) {
            strEstudios = "72";
        } else if (strEstudios.matches("No contestó")) {
            strEstudios = "0";
        } else if (strEstudios.matches("No aplica")) {
            strEstudios = "0";
        }
        // coches
        if (strCocheCuantos.matches("sin datos")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("Ninguno")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("Uno")) {
            strCocheCuantos = "32";
        } else if (strCocheCuantos.matches("Dos")) {
            strCocheCuantos = "41";
        } else if (strCocheCuantos.matches("Tres")) {
            strCocheCuantos = "58";
        } else if (strCocheCuantos.matches("Cuatro o más")) {
            strCocheCuantos = "58";
        } else if (strCocheCuantos.matches("No aplica")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("No contestó")) {
            strCocheCuantos = "0";
        } else if (strCocheCuantos.matches("No aplica")) {
            strCocheCuantos = "0";
        }
        // Focos
        if (strFoco.matches("sin datos")) {
            strFoco = "0";
        } else if (strFoco.matches("0 a 5")) {
            strFoco = "0";
        } else if (strFoco.matches("6 a 10")) {
            strFoco = "15";
        } else if (strFoco.matches("11 a 15")) {
            strFoco = "27";
        } else if (strFoco.matches("16 a 20")) {
            strFoco = "32";
        } else if (strFoco.matches("21 o más")) {
            strFoco = "46";
        } else if (strFoco.matches("No contestó")) {
            strFoco = "0";
        } else if (strFoco.matches("No aplica")) {
            strFoco = "0";
        }
        // Cuartos
        if (strCuarto.matches("sin datos")) {
            strCuarto = "0";
        } else if (strCuarto.matches("1")) {
            strCuarto = "0";
        } else if (strCuarto.matches("2")) {
            strCuarto = "0";
        } else if (strCuarto.matches("3")) {
            strCuarto = "0";
        } else if (strCuarto.matches("4")) {
            strCuarto = "0";
        } else if (strCuarto.matches("5")) {
            strCuarto = "8";
        } else if (strCuarto.matches("6")) {
            strCuarto = "8";
        } else if (strCuarto.matches("7")) {
            strCuarto = "14";
        } else if (strCuarto.matches("8")) {
            strCuarto = "14";
        } else if (strCuarto.matches("9")) {
            strCuarto = "14";
        } else if (strCuarto.matches("10")) {
            strCuarto = "14";
        } else if (strCuarto.matches("No contestó")) {
            strCuarto = "0";
        } else if (strCuarto.matches("No aplica")) {
            strCuarto = "0";
        }
        // Banos
        if (strBano.matches("sin datos")) {
            strBano = "0";
        } else if (strBano.matches("Ninguno")) {
            strBano = "0";
        } else if (strBano.matches("Uno")) {
            strBano = "13";
        } else if (strBano.matches("Dos o Tres")) {
            strBano = "31";
        } else if (strBano.matches("Cuatro o más")) {
            strBano = "48";
        } else if (strBano.matches("No contestó")) {
            strBano = "0";
        } else if (strBano.matches("No aplica")) {
            strBano = "0";
        }
        // Regadera
        if (strRega.matches("sin datos")) {
            strRega = "0";
        } else if (strRega.matches("Si")) {
            strRega = "10";
        } else if (strRega.matches("No")) {
            strRega = "0";
        } else if (strRega.matches("No contestó")) {
            strRega = "0";
        } else if (strRega.matches("No aplica")) {
            strRega = "0";
        }
        // Estufa
        if (strEstu.matches("sin datos")) {
            strEstu = "0";
        } else if (strEstu.matches("Si")) {
            strEstu = "20";
        } else if (strEstu.matches("No")) {
            strEstu = "0";
        } else if (strEstu.matches("No contestó")) {
            strEstu = "0";
        } else if (strEstu.matches("No aplica")) {
            strEstu = "0";
        }
        // Piso
        if (strPiso.matches("sin datos")) {
            strPiso = "0";
        } else if (strPiso.matches("Tierra o cemento")) {
            strPiso = "0";
        } else if (strPiso.matches("Cualquier otro")) {
            strPiso = "11";
        } else if (strPiso.matches("No contestó")) {
            strPiso = "0";
        } else if (strPiso.matches("No aplica")) {
            strPiso = "0";
        }

        Integer estudios = Integer.valueOf(strEstudios);
        Integer coches = Integer.valueOf(strCocheCuantos);
        Integer focos = Integer.valueOf(strFoco);
        Integer cuartos = Integer.valueOf(strCuarto);
        Integer banos = Integer.valueOf(strBano);
        Integer regadera = Integer.valueOf(strRega);
        Integer estufa = Integer.valueOf(strEstu);
        Integer piso = Integer.valueOf(strPiso);

        Integer suma = (estudios + coches + focos + cuartos + banos + regadera + estufa + piso);

        String status = null;

        if (suma >= 0 && suma <= 32) {
            status = "E";
        } else if (suma >= 33 && suma <= 79) {
            status = "D";
        } else if (suma >= 80 && suma <= 104) {
            status = "D+";
        } else if (suma >= 105 && suma <= 127) {
            status = "C-";
        } else if (suma >= 128 && suma <= 154) {
            status = "C";
        } else if (suma >= 155 && suma <= 192) {
            status = "C+";
        } else if (suma >= 193) {
            status = "AB";
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

            String strLatitud = String.valueOf(latitude);
            String strLongitud = String.valueOf(longitude);
            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();
            if (db != null) {

                values.put("consecutivo_diario", elMaximo);
                values.put("diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate1);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("telefono", cachaTelefono());
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("pregunta_vive",strvive);
                values.put("pregunta_0",str0);
                values.put("pregunta_1",str1);
                values.put("pregunta_2",str2);
                values.put("pregunta_3",str3);
                values.put("pregunta_4",str4);
                values.put("pregunta_5",str5);
                values.put("pregunta_c1",strc1);
                values.put("pregunta_c1a",strc1a);
                values.put("pregunta_c1b",strc1b);
                values.put("pregunta_c1c",strc1c);
                values.put("pregunta_c1d",strc1d);
                values.put("pregunta_c1e",strc1e);
                values.put("pregunta_c1f",strc1f);
                values.put("pregunta_c1g",strc1g);
                values.put("pregunta_c1h",strc1h);
                values.put("pregunta_c2",strc2);
                values.put("pregunta_c3",strc3);
                values.put("pregunta_c3a",strc3a);
                values.put("pregunta_c3b",strc3b);
                values.put("pregunta_c3c",strc3c);
                values.put("pregunta_c3d",strc3d);
                values.put("pregunta_c3e",strc3e);
                values.put("pregunta_c4",strc4);
                values.put("pregunta_c4a",strc4a);
                values.put("pregunta_c4b",strc4b);
                values.put("pregunta_c4c",strc4c);
                values.put("pregunta_c5",strc5);
                values.put("pregunta_c5a",strc5a);
                values.put("pregunta_c5b",strc5b);
                values.put("pregunta_c5c",strc5c);
                values.put("pregunta_c5d",strc5d);
                values.put("pregunta_c5e",strc5e);
                values.put("pregunta_c6",strc6);

                values.put("hijos", strHijos);
                values.put("aporta", strAporta);
                values.put("ocupacion", strOcupacion);
                values.put("coche", strCoches);
                values.put("cuantos_coches", strCuantosCoches);
                values.put("cuartos", strCuartos);
                values.put("cuartos_dormir", strCuartosDormir);
                values.put("focos", strFocos);
                values.put("banos", strBanos);
                values.put("regadera", strRegadera);
                values.put("internet", strInternet);
                values.put("trabajaron", strTrabajaron);
                values.put("estufa", strEstufa);
                values.put("edad", strEdad);
                values.put("genero", strGenero);
                values.put("tipo_vivienda", strTipoVivienda);
                values.put("tipo_piso", strTipoPiso);

                values.put("abandono", strAbandono.toUpperCase());

                values.put("suma", suma);
                values.put("status", status);
                values.put("estatus", status);

                values.put("tiempo", elTiempo());
                values.put("tipocaptura", tipoEncuesta);

                consecutivoObtenido = db.insert("encuestas", null, values);
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
            System.out.println("telefono " + cachaTelefono());
            System.out.println("Latitud  " + strLatitud);
            System.out.println("Longitud  " + strLongitud);

            System.out.println("pregunta_vive " + strvive);
            System.out.println("pregunta_0 " + str0);
            System.out.println("pregunta_1 " + str1);
            System.out.println("pregunta_2 " + str2);
            System.out.println("pregunta_3 " + str3);
            System.out.println("pregunta_4 " + str4);
            System.out.println("pregunta_5 " + str5);
            System.out.println("pregunta_c1 " + strc1);
            System.out.println("pregunta_c1a " + strc1a);
            System.out.println("pregunta_c1b " + strc1b);
            System.out.println("pregunta_c1c " + strc1c);
            System.out.println("pregunta_c1d " + strc1d);
            System.out.println("pregunta_c1e " + strc1e);
            System.out.println("pregunta_c1f " + strc1f);
            System.out.println("pregunta_c1g " + strc1g);
            System.out.println("pregunta_c1h " + strc1h);
            System.out.println("pregunta_c2 " + strc2);
            System.out.println("pregunta_c3 " + strc3);
            System.out.println("pregunta_c3a " + strc3a);
            System.out.println("pregunta_c3b " + strc3b);
            System.out.println("pregunta_c3c " + strc3c);
            System.out.println("pregunta_c3d " + strc3d);
            System.out.println("pregunta_c3e " + strc3e);
            System.out.println("pregunta_c4 " + strc4);
            System.out.println("pregunta_c4a " + strc4a);
            System.out.println("pregunta_c4b " + strc4b);
            System.out.println("pregunta_c4c " + strc4c);
            System.out.println("pregunta_c5 " + strc5);
            System.out.println("pregunta_c5a " + strc5a);
            System.out.println("pregunta_c5b " + strc5b);
            System.out.println("pregunta_c5c " + strc5c);
            System.out.println("pregunta_c5d " + strc5d);
            System.out.println("pregunta_c5e " + strc5e);
            System.out.println("pregunta_c6 " + strc6);

            System.out.println(" hijos   " + strHijos);
            System.out.println(" aporta   " + strAporta);
            System.out.println(" ocupacion   " + strOcupacion);
            System.out.println(" coche   " + strCoches);
            System.out.println(" cuantos_coches   " + strCuantosCoches);
            System.out.println(" cuartos   " + strCuartos);
            System.out.println(" cuartos_dormir   " + strCuartosDormir);
            System.out.println(" focos   " + strFocos);

            System.out.println(" baños   " + strBanos);
            System.out.println(" regadera   " + strRegadera);
            System.out.println(" internet   " + strInternet);
            System.out.println(" trabajaron   " + strTrabajaron);
            System.out.println(" estufa   " + strEstufa);
            System.out.println(" edad   " + strEdad);
            System.out.println(" genero   " + strGenero);
            System.out.println(" tipo_vivienda   " + strTipoVivienda);
            System.out.println(" tipo_piso   " + strTipoPiso);

            System.out.println("abandono  " + strAbandono);

            System.out.println("suma  " + suma);
            System.out.println("status  " + status);

            // FIN INSERTA BASE DE DATOS //

        } catch (Exception e) {
            System.out.println("algo pasó...1");
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

                            dialogo();

                            /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("Nombre", encuestaQuien);
                            startActivity(intent);
                            finish();*/


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

                Toast.makeText(MainActivityPantalla1.this, "Error de conexion, intente de nuevo", Toast.LENGTH_SHORT).show();
                btnGuardar.setEnabled(true);

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

                if (layvive.getVisibility() == View.VISIBLE && opvive.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura0, Toast.LENGTH_LONG).show();
                } else if (lay0.getVisibility() == View.VISIBLE && op0.matches("sin datos") && spinner0.getSelectedItem().toString().equals("Selecciona")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura0, Toast.LENGTH_LONG).show();
                } else if (lay1.getVisibility() == View.VISIBLE && op1.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura1, Toast.LENGTH_LONG).show();
                } else if (lay2.getVisibility() == View.VISIBLE && op2.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura2, Toast.LENGTH_LONG).show();
                } else if (lay3.getVisibility() == View.VISIBLE && op3.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura3, Toast.LENGTH_LONG).show();
                } else if (lay4.getVisibility() == View.VISIBLE && op4.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura4, Toast.LENGTH_LONG).show();
                } else if (lay5.getVisibility() == View.VISIBLE && op5.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura5, Toast.LENGTH_LONG).show();
                }
//                else if (layc1.getVisibility() == View.VISIBLE && opc1.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1,Toast.LENGTH_LONG).show();}
                else if (layc1a.getVisibility() == View.VISIBLE && opc1a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1a,Toast.LENGTH_LONG).show();}
                else if (layc1b.getVisibility() == View.VISIBLE && opc1b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1b,Toast.LENGTH_LONG).show();}
                else if (layc1c.getVisibility() == View.VISIBLE && opc1c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1c,Toast.LENGTH_LONG).show();}
                else if (layc1d.getVisibility() == View.VISIBLE && opc1d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1d,Toast.LENGTH_LONG).show();}
                else if (layc1e.getVisibility() == View.VISIBLE && opc1e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1e,Toast.LENGTH_LONG).show();}
                else if (layc1f.getVisibility() == View.VISIBLE && opc1f.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1f,Toast.LENGTH_LONG).show();}
                else if (layc1g.getVisibility() == View.VISIBLE && opc1g.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1g,Toast.LENGTH_LONG).show();}
                else if (layc1h.getVisibility() == View.VISIBLE && opc1h.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac1h,Toast.LENGTH_LONG).show();}
                else if (layc2.getVisibility() == View.VISIBLE && opc2.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac2,Toast.LENGTH_LONG).show();}
//                else if (layc3.getVisibility() == View.VISIBLE && opc3.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3,Toast.LENGTH_LONG).show();}
                else if (layc3a.getVisibility() == View.VISIBLE && opc3a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3a,Toast.LENGTH_LONG).show();}
                else if (layc3b.getVisibility() == View.VISIBLE && opc3b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3b,Toast.LENGTH_LONG).show();}
                else if (layc3c.getVisibility() == View.VISIBLE && opc3c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3c,Toast.LENGTH_LONG).show();}
                else if (layc3d.getVisibility() == View.VISIBLE && opc3d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3d,Toast.LENGTH_LONG).show();}
                else if (layc3e.getVisibility() == View.VISIBLE && opc3e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac3e,Toast.LENGTH_LONG).show();}
//                else if (layc4.getVisibility() == View.VISIBLE && opc4.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac4,Toast.LENGTH_LONG).show();}
                else if (layc4a.getVisibility() == View.VISIBLE && opc4a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac4a,Toast.LENGTH_LONG).show();}
                else if (layc4b.getVisibility() == View.VISIBLE && opc4b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac4b,Toast.LENGTH_LONG).show();}
                else if (layc4c.getVisibility() == View.VISIBLE && opc4c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac4c,Toast.LENGTH_LONG).show();}
//                else if (layc5.getVisibility() == View.VISIBLE && opc5.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5,Toast.LENGTH_LONG).show();}
                else if (layc5a.getVisibility() == View.VISIBLE && opc5a.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5a,Toast.LENGTH_LONG).show();}
                else if (layc5b.getVisibility() == View.VISIBLE && opc5b.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5b,Toast.LENGTH_LONG).show();}
                else if (layc5c.getVisibility() == View.VISIBLE && opc5c.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5c,Toast.LENGTH_LONG).show();}
                else if (layc5d.getVisibility() == View.VISIBLE && opc5d.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5d,Toast.LENGTH_LONG).show();}
                else if (layc5e.getVisibility() == View.VISIBLE && opc5e.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac5e,Toast.LENGTH_LONG).show();}
                else if (layc6.getVisibility() == View.VISIBLE && opc6.matches("sin datos")){Toast.makeText(getBaseContext(),"CAPTURA:  " +  capturac6,Toast.LENGTH_LONG).show();}



                else if (opHijos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaHijos, Toast.LENGTH_LONG).show();
                } else if (opAporta.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaAporta, Toast.LENGTH_LONG).show();
                } else if (opOcupacion.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaOcupacion, Toast.LENGTH_LONG).show();
                } else if (opCoche.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCoche, Toast.LENGTH_LONG).show();
                } else if (layCuantosCoches.getVisibility() == View.VISIBLE && opCuantosCoches.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuantosCoches, Toast.LENGTH_LONG).show();
                } else if (opCuartos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuartos, Toast.LENGTH_LONG).show();
                } else if (opCuartosDormir.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaCuartosDormir, Toast.LENGTH_LONG).show();
                } else if (opFocos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaFocos, Toast.LENGTH_LONG).show();
                } else if (opBanos.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaBanos, Toast.LENGTH_LONG).show();
                } else if (opInternet.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaInternet, Toast.LENGTH_LONG).show();
                } else if (opTrabajaron.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaTrabajaron, Toast.LENGTH_LONG).show();
                } else if (opEstufa.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaEstufa, Toast.LENGTH_LONG).show();
                } else if (opEdad.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaEdad, Toast.LENGTH_LONG).show();
                } else if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                } else if (opTipoVivienda.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaTipoVivienda, Toast.LENGTH_LONG).show();
                } else if (opTipoPiso.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaTipoPiso, Toast.LENGTH_LONG).show();
                } else {

                    // para valor por default
                    if (opAbandono.matches("sin datos")) {
                        opAbandono = "1";
                    }

                    valores();
                    btnGuardar.setEnabled(false);
                    //dialogo();

                } // Finaliza else
                break;

            case 2:

                if (layvive.getVisibility() == View.VISIBLE && opvive.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura0, Toast.LENGTH_LONG).show();
                }else if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                }
                else {
                    valores();
                    btnGuardar.setEnabled(false);
                    //dialogo();
                }

                break;

            case 3:

                if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                }
                else {
                    valores();
                    btnGuardar.setEnabled(false);
                    //dialogo();
                }
                break;

            case 4:

                if (layvive.getVisibility() == View.VISIBLE && opvive.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura0, Toast.LENGTH_LONG).show();
                } else {
                    valores();
                    btnGuardar.setEnabled(false);
                    //dialogo();
                }

                break;

            case 5:
                if (layvive.getVisibility() == View.VISIBLE && opvive.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + captura0, Toast.LENGTH_LONG).show();
                } else if (opGenero.matches("sin datos")) {
                    Toast.makeText(getBaseContext(), "CAPTURA:  " + capturaGenero, Toast.LENGTH_LONG).show();
                } else {
                    valores();
                    btnGuardar.setEnabled(false);
                    //dialogo();
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


    public void CargaSpinner0() {
        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "", "ALVARO OBREGON", "AZCAPOTZALCO", "BENITO JUAREZ",
                "COYOACAN", "CUAJIMALPA DE MORELOS", "CUAUHTEMOC", "GUSTAVO A. MADERO", "IZTACALCO", "IZTAPALAPA",
                "MAGDALENA CONTRERAS", "MIGUEL HIDALGO", "MILPA ALTA", "TLAHUAC", "TLALPAN", "VENUSTIANO CARRANZA",
                "XOCHIMILCO"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner0.setAdapter(adaptador);
        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {


                rdPregunta0.clearCheck();
                op0 = "sin datos";

                radio_abandono1.setVisibility(View.VISIBLE);
                radio_abandono2.setVisibility(View.VISIBLE);
                radio_abandono3.setVisibility(View.VISIBLE);
                radio_abandono4.setVisibility(View.GONE);
                radio_abandono4.setChecked(false);
                radio_abandono1.setChecked(true);


                lay1 .setVisibility(View.VISIBLE);
                lay2 .setVisibility(View.VISIBLE);
                lay3 .setVisibility(View.VISIBLE);
                lay4 .setVisibility(View.VISIBLE);
                lay5 .setVisibility(View.VISIBLE);
                layc1 .setVisibility(View.VISIBLE);
                layc1a .setVisibility(View.VISIBLE);
                layc1b .setVisibility(View.VISIBLE);
                layc1c .setVisibility(View.VISIBLE);
                layc1d .setVisibility(View.VISIBLE);
                layc1e .setVisibility(View.VISIBLE);
                layc1f .setVisibility(View.VISIBLE);
                layc1g .setVisibility(View.VISIBLE);
                layc1h .setVisibility(View.VISIBLE);
                layc2 .setVisibility(View.VISIBLE);
                layc3 .setVisibility(View.VISIBLE);
                layc3a .setVisibility(View.VISIBLE);
                layc3b .setVisibility(View.VISIBLE);
                layc3c .setVisibility(View.VISIBLE);
                layc3d .setVisibility(View.VISIBLE);
                layc3e .setVisibility(View.VISIBLE);
                layc4 .setVisibility(View.VISIBLE);
                layc4a .setVisibility(View.VISIBLE);
                layc4b .setVisibility(View.VISIBLE);
                layc4c .setVisibility(View.VISIBLE);
                layc5 .setVisibility(View.VISIBLE);
                layc5a .setVisibility(View.VISIBLE);
                layc5b .setVisibility(View.VISIBLE);
                layc5c .setVisibility(View.VISIBLE);
                layc5d .setVisibility(View.VISIBLE);
                layc5e .setVisibility(View.VISIBLE);
                layc6 .setVisibility(View.VISIBLE);


//                rdPregunta1.clearCheck();
//                rdPregunta2.clearCheck();
//                rdPregunta3.clearCheck();
//                rdPregunta4.clearCheck();
//                rdPreguntac1.clearCheck();
//                rdPreguntac2.clearCheck();
//                rdPreguntac3.clearCheck();
//                rdPreguntac4.clearCheck();
//                rdPreguntac5.clearCheck();
//                rdPreguntac6.clearCheck();
//                rdPreguntac7.clearCheck();
//                rdPreguntac8.clearCheck();
//                rdPreguntac9.clearCheck();
//                rdPreguntac10.clearCheck();
//                rdPreguntac10a.clearCheck();
//                rdPreguntac11.clearCheck();
//                rdPreguntac11a.clearCheck();
//                rdPreguntac12.clearCheck();
//                rdPreguntac12a.clearCheck();
//                rdPreguntac13.clearCheck();
//                rdPreguntac14.clearCheck();
//                rdPreguntac14a.clearCheck();
//                rdPreguntac15.clearCheck();
//                rdPreguntac16.clearCheck();
//                rdPreguntac17.clearCheck();
//
//
//                op1 = "sin datos";
//                op2 = "sin datos";
//                op3 = "sin datos";
//                op4 = "sin datos";
//                opc1 = "sin datos";
//                opc2 = "sin datos";
//                opc3 = "sin datos";
//                opc4 = "sin datos";
//                opc5 = "sin datos";
//                opc6 = "sin datos";
//                opc7 = "sin datos";
//                opc8 = "sin datos";
//                opc9 = "sin datos";
//                opc10 = "sin datos";
//                opc10a = "sin datos";
//                opc11 = "sin datos";
//                opc11a = "sin datos";
//                opc12 = "sin datos";
//                opc12a = "sin datos";
//                opc13 = "sin datos";
//                opc14 = "sin datos";
//                opc14a = "sin datos";
//                opc15 = "sin datos";
//                opc16 = "sin datos";
//                opc17 = "sin datos";




                laySocioE.setVisibility(View.VISIBLE);
                layEst.setVisibility(View.VISIBLE);
                layHijos.setVisibility(View.VISIBLE);
                layAporta.setVisibility(View.VISIBLE);
                layOcupacion.setVisibility(View.VISIBLE);
                laycoche.setVisibility(View.VISIBLE);
                layCuantosCoches.setVisibility(View.VISIBLE);
                layCuartos.setVisibility(View.VISIBLE);
                layCuartosDormir.setVisibility(View.VISIBLE);
                layFocos.setVisibility(View.VISIBLE);
                layBanos.setVisibility(View.VISIBLE);
                layRegadera.setVisibility(View.VISIBLE);
                layInternet.setVisibility(View.VISIBLE);
                layTrabajaron.setVisibility(View.VISIBLE);
                layEstufa.setVisibility(View.VISIBLE);
                layEdad.setVisibility(View.VISIBLE);
                layTipoPiso.setVisibility(View.VISIBLE);
                layTipoVivienda.setVisibility(View.VISIBLE);
                layGenero.setVisibility(View.VISIBLE);

//                rdPreguntaHijos.clearCheck();
//                rdPreguntaAporta.clearCheck();
//                rdPreguntaOcupacion.clearCheck();
//                rdPreguntaCoche.clearCheck();
//                rdPreguntaCuantosCoches.clearCheck();
//                rdPreguntaCuartos.clearCheck();
//                rdPreguntaCuartosDormir.clearCheck();
//                rdPreguntaFocos.clearCheck();
//                rdPreguntaBanos.clearCheck();
//                rdPreguntaRegadera.clearCheck();
//                rdPreguntaInternet.clearCheck();
//                rdPreguntaTrabajaron.clearCheck();
//                rdPreguntaEstufa.clearCheck();
//                rdPreguntaTipoPiso.clearCheck();
//                rdPreguntaTipoVivienda.clearCheck();
//                rdPreguntaGenero.clearCheck();
//
//                opHijos = "sin datos";
//                opAporta = "sin datos";
//                opOcupacion = "sin datos";
//                opCoche = "sin datos";
//                opCuantosCoches = "sin datos";
//                opCuartos = "sin datos";
//                opCuartosDormir = "sin datos";
//                opFocos = "sin datos";
//                opBanos = "sin datos";
//                opRegadera = "sin datos";
//                opInternet = "sin datos";
//                opTrabajaron = "sin datos";
//                opEstufa = "sin datos";
//                opTipoPiso = "sin datos";
//                opTipoVivienda = "sin datos";
//                opGenero = "sin datos";

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

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
