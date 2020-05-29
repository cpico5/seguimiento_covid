package mx.gob.cdmx.telefonica20200530;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import mx.gob.cdmx.telefonica20200530.db.DaoManager;
import mx.gob.cdmx.telefonica20200530.model.DatoContent;
import mx.gob.cdmx.telefonica20200530.model.TelefonoAsignado;
import mx.gob.cdmx.telefonica20200530.model.Usuario;

import static mx.gob.cdmx.telefonica20200530.Nombre.USUARIO;
import static mx.gob.cdmx.telefonica20200530.Nombre.customURL;
import static mx.gob.cdmx.telefonica20200530.Nombre.encuesta;

public class MainActivity extends Activity implements OnItemSelectedListener {

    private DaoManager daoManager;
    private mx.gob.cdmx.telefonica20200530.model.Usuario usuario;
    private View mProgressView;
    private TelefonoAsignado telefonoAsignado;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();

    Random random = new java.util.Random();
    public int rand;

    TelephonyManager t_manager;
    PhoneStateListener p_listener;
    boolean llamada = false;

    private EditText txtCodigo;
    private EditText txtNombre;
    private EditText editTelefono;
    private TextView txtResultado;
    private TextView txtEncuesta;
    private TextView txtEquipo;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;
    private Button btnMarcar;
    private Button btnSolicitar;
    private Button btnSiguiente;
    private Button btnEnviar;
    private Button btnFalse;
    private Button btnExito;
    private Button btnRechazo;
    private Button btnRechazo2;
    private Button btnInformacion;
    public ProgressBar progressBar;

    private SQLiteDatabase db;

    public String encuestador;
    public String credencial;
    public String seccion;
    public String equipo;

    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;

    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;

    public EditText Usuario;
    public String tablet;
    public String encuestaQuien;
    public String pasoUsuario;

    public String Secc;
    public String cuantasSecciones;
    public String quienEncuesta;
    final String path = "/mnt/sdcard/Mis_archivos/";
    final String pathZip = "/mnt/sdcard/";
    // final String pathAudio="/mnt/sdcard/Audio/";

    double latitude;
    double longitude;

    public String maximo = "";
    int elMaximo;

    private static final String LOG_TAG = "Grabadora";
    private static final String TAG = "MainActivity";
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private String audio;
    private Handler handler;

    private Button btnCargar;

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

    //	static String prefix = ID;

    public EditText editUsuario;

    public String str;

    ProgressDialog dialog = null;

    private static final int READ_BLOCK_SIZE = 2000000;

    UsuariosSQLiteHelper usdbh;
    private Spinner spinner;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinnerTipoCierre;

    UsuariosSQLiteHelper Udb;
    List<String> list;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
    String formattedDate1 = df1.format(c.getTime());

    SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
    String formattedDate2 = df2.format(c.getTime());

    SimpleDateFormat df3 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate3 = df3.format(c.getTime());

    SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
    String formattedDate4 = df4.format(c.getTime());

    SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
    String formattedDate5 = df5.format(c.getTime());

    // Para calcular la diferencia entre horas se toma el tiempo en milisegundos
    Calendar t1 = Calendar.getInstance();
    Calendar t2 = Calendar.getInstance();
    Calendar t3 = Calendar.getInstance();
    // esta variable la paso hasta la última página
    long milis1 = t1.getTimeInMillis();

    public String nombreArchivo() {
//		String date = formattedDate3.toString();
//		String var2 = ".txt";
//		String var3 = date + var2;

//		final String nombre = date + "-" + tablet + "-" + nombreEncuesta + "-" + var2;
        final String nombre = nombreEncuesta + "_" + prefix;

        // String nombre="encuestas20140219.txt";
        return nombre;
    }

    public String nombreAudio() {

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String date = formattedDate3.toString();
        String var2 = ".mp3";

        final String nombreAudio = "R" + elMaximo + "_T" + sacaImei() + "_E" + cachaNombre() + var2;
        // String nombre="encuestas20140219.txt";
        return nombreAudio;
    }

    public String nombreArchivoCSV() {
        String date = formattedDate3.toString();
        String var2 = ".csv";
        String var3 = date + var2;

        final String nombre = date + "-" + nombreEncuesta + "-" + prefix + var2;
        // String nombre="encuestas20140219.txt";
        return nombre;
    }

    public String cachaNombre2() {
        Bundle datos = this.getIntent().getExtras();
        encuestador = datos.getString("Nombre2");
        return encuestador;

    }

    public String sacaImei() {
        String szImei;
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            if (szImei == null) {
                szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
            }
            return szImei;
        }
        szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        if (szImei == null) {
            szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
        }
        return szImei;
    }

    public String sacaChip() {
        String szImei;
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);//Telefono
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            if (szImei == null) {
                szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
            }
            return szImei;
        }
        szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        if (szImei == null) {
            szImei = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);// Tableta
        }
        return szImei;
    }

    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        encuestador = datos.getString("Nombre");
        return encuestador;
    }


    public Integer random(int min, int max) {
//	      int min = 50;
//	      int max = 100;
        //Generate random double value from 50 to 100
        System.out.println("Random value in double from " + min + " to " + max + ":");
        double random_double = Math.random() * (max - min + 1) + min;
        System.out.println(random_double);

        //Generate random int value from 50 to 100
        System.out.println("Random value in int from " + min + " to " + max + ":");
        int random_int = (int) (Math.random() * (max - min + 1) + min);
        System.out.println(random_int);

        return random_int;
    }

    AudioManager audioManager;

    private void turnOnSpeaker(boolean on) {
        audioManager = (AudioManager) getApplicationContext().getSystemService(this.AUDIO_SERVICE);
        if (audioManager.isSpeakerphoneOn() != on) {
            Log.i(TAG, "turning speaker phone on: " + on);
            audioManager.setSpeakerphoneOn(on);
        }
    }

    private String[] listaTelefonosPrueba = {
            "5611457553",
            "5565281646",
            "5550071806",
            "5518094179",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Log.i("GPS", "Latitud sin GPS: " + latitude);
        Log.i("GPS", "Longitud sin GPS: " + longitude);

        sacaChip();

        File sdCard, directory, file;

        sdCard = Environment.getExternalStorageDirectory();
        // directory = new File(sdCard.getAbsolutePath() +
        // "/Audio"+formattedDate3+"");

        // directory.mkdirs();//CREA EL DIRECTORIO SI NO EXISTE

        // Obtenemos las referencias a los controles
        // txtCodigo = (EditText)findViewById(R.id.txtReg);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
        txtEncuesta = (TextView) findViewById(R.id.txtEncuesta);
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);
        mProgressView = findViewById(R.id.login_progressMain);

//		spinner2 = (Spinner) findViewById(R.id.spinner2);
//		spinner3 = (Spinner) findViewById(R.id.spinner3);
        spinnerTipoCierre = (Spinner) findViewById(R.id.spinnerTipoCierre);

        // btnInsertar = (Button)findViewById(R.id.btnInsertar);
        // btnActualizar = (Button)findViewById(R.id.btnActualizar);
        // btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setEnabled(false);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);

//		btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnRechazo = (Button) findViewById(R.id.btnRechazo);
        btnMarcar = (Button) findViewById(R.id.btnMarcar);
        btnSolicitar = (Button) findViewById(R.id.btnSolicitar);
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnExito = (Button) findViewById(R.id.btnExito);
        btnInformacion = (Button) findViewById(R.id.btnInformacion);
        btnRechazo.setEnabled(false);
//		btnRechazo2.setEnabled(false);

        btnFalse.setEnabled(false);
        btnExito.setEnabled(false);

        editTelefono.setEnabled(false);

        if (cachaNombre() == null) {

            encuestaQuien = cachaNombre2();

        } else {

            cachaNombre();

            encuestaQuien = cachaNombre();

        }

        t_manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        p_listener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                boolean on = true;
                boolean off = false;
                switch (state) {
                    case (TelephonyManager.CALL_STATE_IDLE):
                        turnOnSpeaker(off);

                        btnExito.setEnabled(false);

                        break;

                    case (TelephonyManager.CALL_STATE_OFFHOOK):
                        llamada = true;
                        Log.i(TAG, "Haciendo Llamada True");

                        btnExito.setEnabled(true);

                        turnOnSpeaker(on);

                        break;
                    case (TelephonyManager.CALL_STATE_RINGING):
                        turnOnSpeaker(on);

                        break;

                }
            }
        };
        t_manager.listen(p_listener, PhoneStateListener.LISTEN_CALL_STATE);

        final String F = "File dbfile";

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);
        db = usdbh.getWritableDatabase();
        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getWritableDatabase();
        daoManager = new DaoManager(db2);

        btnExito.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainActivityPantalla1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("Nombre", encuestaQuien);
                intent.putExtra("telefono", editTelefono.getText().toString());
                intent.putExtra(USUARIO, usuario);
                startActivity(intent);
                finish();

            }

        });


        btnSolicitar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Integer marca=random(10000000, 99999999);
                //editTelefono.setText("55"+String.valueOf(marca));
                obtieneRegistroWS();
            }

        });

        btnMarcar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {


                String aMarcar = editTelefono.getText().toString();

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + aMarcar));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    startActivity(intent);
                }else{
                    startActivity(intent);
                }


                btnFalse.setEnabled(true);
//                btnExito.setEnabled(true);

            }
        });

        btnFalse.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialogoCerrar();
            }
        });

        //consulta el webServices para obtener telefono a marcar
        obtieneRegistroWS();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void obtieneRegistroWS() {

        editTelefono.setText("Sin datos");
        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("api", "consulta");
        params.put("encuesta", encuesta);
        params.put("id_usuario", usuario.getId());
        params.put("latitud", latitude);
        params.put("longitud", longitude);
        params.put("imei", sacaImei());

        AsyncHttpClient client = new AsyncHttpClient();
        client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
        //client.addHeader("Authorization", "Bearer " + usuario.getToken());
        client.setTimeout(60000);

        RequestHandle requestHandle = client.post(customURL, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String nombreStr = "";
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

                            JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("asignado");
                            Type collectionType = new TypeToken<TelefonoAsignado>() {
                            }.getType();
                            telefonoAsignado = gson.fromJson(jsonUser.toString(), collectionType);

                            //daoManager.delete(PadronSms.class);

                            if (telefonoAsignado != null) {

//                                editTelefono.setText(telefonoAsignado.getTelefono());
                                editTelefono.setText(listaTelefonosPrueba[rand]);  //====================>> aleatorio de los números de prueba
                                daoManager.insert(telefonoAsignado);
                                showProgress(false);
                            }
							/*
							//bitacora
							jsonUser = jsonObject.getJSONObject("data").getJSONObject("padron");
							collectionType = new TypeToken<PadronSms>() {}.getType();
							padronSms = gson.fromJson(jsonUser.toString(), collectionType);

							daoManager.delete(PadronSms.class);

							if (padronSms != null) {
								editNombre.setText(padronSms.getNombre_completo() + " " + "(" + padronSms.getNombres() + ")");
								editTelefono.setText(padronSms.getTelefono());
								editFecha.setText(padronSms.getFecha_inicio());

								daoManager.insert(padronSms);


								showProgress(false);

							}*/

                        } else {
                            Toast.makeText(MainActivity.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Usuario y/o Contase?a Incorrectos", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(MainActivity.this, "Error de conexion, intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void Info(View view) {

        Intent intent = new Intent(getApplicationContext(), Menu_Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Nombre", encuestaQuien);
        intent.putExtra("t1", milis1);
        startActivity(intent);
        finish();

    }

    public void valoresMarcado(String telefono, String tipo_cierre) {

//		String strSecc = spinner3.getSelectedItem().toString();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String strTel = telefono;
        String strTipoCierre = tipo_cierre;

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

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
        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

// Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
// INSERTA EN LA BASE DE DATOS //
            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaChip() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);
            db = usdbh.getWritableDatabase();

// NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();

            long consecutivoObtenido = 0;
            ContentValues values = new ContentValues();

            if (db != null) {
                values.put("consecutivo_diario", elMaximo);
//values.put("consecutivo", elMaximo);
                values.put("diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaChip());
                values.put("telefono", strTel);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);
                values.put("tipocaptura", strTipoCierre);
                values.put("status", "0");
                values.put("estatus", "0");
                values.put("tiempo", "00:00");
                consecutivoObtenido = db.insert("encuestas", null, values);
            }
//			db.close();
            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);

// FIN INSERTA BASE DE DATOS //
/*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
intent.putExtra("Nombre", encuestaQuien);
startActivity(intent);
finish();*/

        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i("TAG", "Error Inserta Telefono marcado" + stackTrace);
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

                          Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          intent.putExtra("Nombre", encuestaQuien);
                          intent.putExtra(USUARIO,usuario);
                          startActivity(intent);
                          finish();


                        } else {
                            Toast.makeText(MainActivity.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(MainActivity.this, "Error al subir los datos", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(MainActivity.this, "Error de conexion, intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        });


    }


    class UpdateProgress extends AsyncTask<Void, Integer, Void> {
        int progress;

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onPreExecute() {
            progress = 0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            while (progress < 100) {
                progress++;
                publishProgress(progress);
                SystemClock.sleep(100);
            }
            return null;
        }
    }

    public void Siguiente(View view) {

        if (cachaNombre() == null) {

            encuestaQuien = cachaNombre2().toUpperCase();

        } else {

            cachaNombre();

            encuestaQuien = cachaNombre().toUpperCase();

        }

//		String equipo = spinner2.getSelectedItem().toString();

        System.out.println("El ID: " + ID);
        System.out.println("El prefix: " + prefix);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID: " + prefix);

        System.out.println("El equipo:  " + equipo);
//		System.out.println("delegacion:  " + delegacion().toString().toUpperCase());
//		System.out.println("equipo:  " + equipo().toString().toUpperCase());

        dialogo();

    }

    public void Salir(View v) {

        finish();
    }


//	public void sinExito(View view) {
//
//		dialogoCerrar();
//
//	}

    public void Rechazo2(View view) {

        dialogoRechazo2();

    }

//	public void Info(View view) {
//
//		Intent intent = new Intent(getApplicationContext(), Menu_Principal.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtra("Seccion", spinner3.getSelectedItem().toString());
//		intent.putExtra("Nombre", encuestaQuien);
//		intent.putExtra("equipo", equipo());// para pasar la variable a la otra
//											// actividad
//		intent.putExtra("t1", milis1);
//		startActivity(intent);
//		finish();
//
//	}

    public void dialogoCerrar() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.activity_cierre, null);

        Button btnCierraModal = (Button) mView.findViewById(R.id.btnCierraModal);
        //txtOtroCierre = mView.findViewById(R.id.txtOtroCierre);
        spinnerTipoCierre = (Spinner) mView.findViewById(R.id.spinnerTipoCierre);

        String var = "Selecciona";
        if (var == null) {
            var = "";
        }
        final String[] datos = new String[]{"" + var + "",
                "No contestaron / envia a buzon",
                "Numero inexistente / dado de baja"
        };
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.multiline_spinner_dropdown_item, datos);
        adaptador.setDropDownViewResource(R.layout.multiline_spinner_dropdown_item);
        spinnerTipoCierre.setAdapter(adaptador);
        spinnerTipoCierre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        btnCierraModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (spinnerTipoCierre.getSelectedItem().toString().equals("Selecciona")) {
                    Toast.makeText(MainActivity.this,
                            "Por favor, indique el motivo de cierre", Toast.LENGTH_SHORT).show();
                    return;
                }

                String cierre = spinnerTipoCierre.getSelectedItem().toString();
                String telefono = editTelefono.getText().toString();

                valoresMarcado(telefono, cierre);

                dialog.dismiss();

            }
        });
    }


    public void dialogo() {

        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setMessage(cuentaArchivos())
        builder.setMessage("Seccion " + seccion + ": \n\n" + "Encuestas: \n" + "Normal:   " + " " + dameSecciones()
                + "\n" + "Abandono: " + " " + dameAbandonos() + "\n" + "Rechazos: " + dameRechazos() + "\n" + "Filtros: " + dameFiltros() + "\n\n"
//				+ "Sexo: \n" + "Masculino: " + dameHombres() + "\n" + "Femenino:  " + dameMujeres() + "\n\n"
                + "Rechazos Totales: " + dameRechazosTodos() + "\n\n")

                .setTitle("AVISO...!!!").setCancelable(false)
                .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//						Intent i = new Intent(MainActivity.this, Entrada.class);
//						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						startActivity(i);
//						finish();
//						; // metodo que se debe implementar
                    }
                }).setPositiveButton("CONTINUAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                System.out.println(cachaNombre());


                pasaDatos();


            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    // RECUPERAR UN DATO DE LA BASE DE DATOS

//	public String delegacion() {
//
//		seccion = spinner3.getSelectedItem().toString().toUpperCase();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//
//		db2 = usdbh2.getWritableDatabase();
//		String selectQuery1 = " SELECT delegacion FROM datos  WHERE seccion='" + seccion + "'";
//		Cursor c = db2.rawQuery(selectQuery1, null);
//
//		c.moveToFirst();
//		// Recorremos el cursor hasta que no haya más registros
//		String delegacion = c.getString(0);
//		c.close();
//		db2.close();
//		return delegacion;
//	}


//	public String equipo() {
//
//		seccion = spinner3.getSelectedItem().toString().toUpperCase();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//
//		// db = usdbh.getWritableDatabase();
//		// // String selectQuery1 = " SELECT a.equipo FROM datos as a, delegados
//		// as
//		// // b WHERE a.seccion=b.seccion and a.seccion='"+seccion+"'";
//		// String selectQuery1 = " SELECT equipo FROM datos WHERE seccion='" +
//		// seccion + "'";
//		// Cursor c = db.rawQuery(selectQuery1, null);
//		//
//		// c.moveToFirst();
//		// // Recorremos el cursor hasta que no haya más registros
//		// String equipo = c.getString(0);
//		// c.close();
//		// db.close();
//
//		return equipo;
//	}

    public void cargaEncuestador() {

        db = usdbh.getWritableDatabase();
        String selectQuery1 = " SELECT usuario, count(*) as total from encuestas where fecha='" + formattedDate3
                + "' group by usuario";
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.moveToFirst())
            do {

                String enc = c.getString(0);
                String tot = c.getString(1);
                // Recorremos el cursor hasta que no haya más registros
                txtEncuesta.append("- Encuestador: " + enc + "  " + "\tTotal de encuestas: " + tot + "\n");

            } while (c.moveToNext());

        c.close();
        db.close();
    }

    public String dameSecciones() {

        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;

        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and tipocaptura='NORMAL' group by seccion";
        // String selectQuery1 = "select seccion, count(*) as total from
        // locatario where seccion='"+seccion+"' group by seccion"; //total de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {

            cuantasSecciones_base = "0";

        }

        c.close();
        db.close();
        return cuantasSecciones_base;

    }

    public String dameAbandonos() {
        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and (tipocaptura='ABANDONO' or tipocaptura='ABANDONO TEMOR A CONTAGIO')  group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public String dameHombres() {

        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String masculino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='MASCULINO' and tipocaptura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            masculino = c.getString(1);
        } else {

            masculino = "0";

        }

        c.close();
        db.close();
        return masculino;

    }

    public String dameMujeres() {

        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String femenino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='FEMENINO' and tipocaptura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            femenino = c.getString(1);
        } else {

            femenino = "0";

        }

        c.close();
        db.close();
        return femenino;

    }

    public String dameRechazosTodos() {
        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where fecha='" + formattedDate3
                + "' and (tipocaptura='RECHAZO' or tipocaptura='RECHAZO TEMOR A CONTAGIO')  group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public String dameRechazos() {
        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and (tipocaptura='RECHAZO' or tipocaptura='RECHAZO TEMOR A CONTAGIO') group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public String dameFiltros() {
        seccion = spinner3.getSelectedItem().toString().toUpperCase();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from encuestas where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and (tipocaptura='FILTRO') group by seccion";
        Cursor c = db.rawQuery(selectQuery1, null);
        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        } else {
            cuantasSecciones_base = "0";
        }
        c.close();
        db.close();
        return cuantasSecciones_base;
    }

    public void pasaDatos() {

        String seg = formattedDate5.substring(7);
        // Intent intent = new Intent(getApplicationContext(), MainActivityPantalla1.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // intent.putExtra("Seccion", txtNombre.getText().toString());//para
        // pasar la variable a la otra actividad
        // intent.putExtra("Nombre", encuestaQuien);//para pasar la variable a
        // la otra actividad
        // intent.putExtra("delegacion", delegacion);
        // intent.putExtra("equipo", equipo());//para pasar la variable a la
        // otra actividad
        // intent.putExtra("t1", milis1);
        // startActivity(intent);
        // finish();

        Intent intent = new Intent(getApplicationContext(), MainActivityPantalla1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Nombre", encuestaQuien);
        startActivity(intent);
        finish();

    }


    //Usuarios

//	private void cargaUsuario() {
//
//		final String[] datos = new String[] {
//				"ALAN",
//				"ARREDONDO",
//				"BERNACHI",
//				"DANIEL",
//				"EDUARDO S",
//				"ENRIQUE",
//				"HUGO",
//				"IRIS",
//				"LUJANO",
//				"OSCAR",
//				"POLANCO",
//				"SAUL",
//				"TABLA"
//};
//
//		// Alternativa 1: Array java
//		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
//
//		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//		spinner2.setAdapter(adaptador);
//
//		// ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
//		spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//				SeccionesSpinner();
//				txtEquipo.setText("Seleccionado: " + datos[position]);
//				btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//				txtResultado.setText("");// limpia el cuadro de texto
//			}
//
//			public void onNothingSelected(AdapterView<?> parent) {
//				txtEquipo.setText("");
//			}
//		});
//
//	}

//	private void SeccionesSpinner() {
//
//		usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
//
//		Set<String> set = new HashSet<String>();
//
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//		System.out.println(equipo);
//
//		db2 = usdbh2.getWritableDatabase();
//
//		// Para llenar el spinner solo con los datos del equipo
//		// PARA QUITAR LOS REGISTROS SELECT t1.seccion FROM (SELECT seccion FROM
//		// datos where equipo='"+equipo+"' union ALL SELECT seccion FROM
//		// encuestas )t1 GROUP BY t1.seccion HAVING count(*) <=3 ORDER BY
//		// t1.seccion asc
//		// String selectQuery1 = "SELECT * FROM datos WHERE equipo='"+equipo+"'
//		// and seccion NOT IN (SELECT seccion FROM encuestas GROUP BY seccion
//		// HAVING COUNT(*) >= 4 ORDER BY seccion)";
//		String selectQuery1 = "SELECT * FROM datos WHERE equipo='" + equipo + "'";
//
//		// Otra consulta Select seccion from datos where equipo ='eddy' and not
//		// exists (select seccion from encuestas where encuestas.seccion =
//		// datos.seccion) GROUP BY datos.seccion HAVING count(*) <=4;
//		Cursor c = db2.rawQuery(selectQuery1, null);
//
//		if (c.moveToFirst()) {
//			do {
//
//				set.add(c.getString(0));
//
//				String secc = c.getString(0);
//
//			} while (c.moveToNext());
//		}
//
//		else {
//			Toast toast2 = Toast.makeText(getApplicationContext(), "NO CORRESPONDE ESTE NÚMERO DE SECCIÓN...!",
//					Toast.LENGTH_LONG);
//
//			toast2.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
//
//			toast2.show();
//
//		}
//
//		c.close();
////		db.close();
//		// here i used Set Because Set doesn't allow duplicates.
//		Set<String> set1 = set;
//		List<String> list = new ArrayList<String>(set1);
//
//		adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);
//
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//		Collections.sort(list);
//		spinner3.setAdapter(adapter);
//
//		// ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
//		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//			public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//				btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//				txtResultado.setText("");// limpia el cuadro de texto
//			}
//
//			public void onNothingSelected(AdapterView<?> parent) {
//				// txtEquipo.setText("");
//			}
//		});
//		spinner3.setWillNotDraw(false);
//
//	}

    private String sacaMaximo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDate3 + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                maximo = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
//		db.close();

        return maximo;
    }

    private String sacaMaximo2() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM usuario";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                maximo = cursor.getString(0);

            } while (cursor.moveToNext());
        }

        cursor.close();
//		db.close();

        return maximo;
    }


//	public void valoresRechazoMasculino() {
//
//		String strSecc = spinner3.getSelectedItem().toString();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//
//		String seg = formattedDate5.substring(7);
//		System.out.println("El segundo: " + seg);
//		System.out.println("El ID" + prefix);
//
//		cachaNombre();
//
//		String strId = String.valueOf(rand + 1);
//		
//		GPSTracker gps = new GPSTracker(this);
//		latitude = gps.getLatitude();
//		longitude = gps.getLongitude();
//		
//		if(latitude==0.0){
//			if(sacaLatitud()!=null){
//				latitude=Double.valueOf(sacaLatitud());
//			}else{
//				latitude=0.0;
//			}
//		}
//		
//		if(longitude==0.0){
//			if(sacaLongitud()!=null){
//				longitude=Double.valueOf(sacaLongitud());
//			}else{
//				longitude=0.0;
//			}
//		}
//		
//		String strLatitud=String.valueOf(latitude);
//		String strLongitud=String.valueOf(longitude);
//
//
//		elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
//
//		// Clase que permite grabar texto en un archivo
//		FileOutputStream fout = null;
//		try {
//			// INSERTA EN LA BASE DE DATOS //
//
//			String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
//			usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);
//
//			db = usdbh.getWritableDatabase();
//
//			// NORMAL
//			Nombre nom = new Nombre();
//			String nombreE = nom.nombreEncuesta();
//
//			if (db != null) {
//				ContentValues values = new ContentValues();
//				values.put("consecutivo_diario", elMaximo);
//				values.put("usuario", cachaNombre().toUpperCase());
//				values.put("equipo", equipo.toUpperCase());
//				values.put("nombre_encuesta", nombreE.toUpperCase());
//				values.put("fecha", formattedDate3);
//				values.put("hora", formattedDate5);
//				values.put("imei", sacaImei());
//				values.put("seccion", strSecc);
//				values.put("latitud", strLatitud);
//				values.put("longitud", strLongitud);
//
//				values.put("genero", "Masculino");
//				values.put("suma", "0");
//				values.put("status", "0");
//
//				values.put("tiempo", "00:00");
//				values.put("tipocaptura", "RECHAZO");
//
//				db.insert("encuestas", null, values);
//			}
////			db.close();
//
//			// FIN INSERTA BASE DE DATOS //
//			
//			txtEquipo.setText("");
//			btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//			btnRechazo.setEnabled(false);
////			btnRechazo2.setEnabled(false);
//			txtResultado.setText("");// limpia el cuadro de texto
//
//		} catch (Exception e) {
//			System.out.println("algo pasó...1");
//			Log.i("Guardar", e.getMessage());
//		}
//
//	}

//	public void valoresRechazoFemenino() {
//
//		String strSecc = spinner3.getSelectedItem().toString();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//		
//		GPSTracker gps = new GPSTracker(this);
//		latitude = gps.getLatitude();
//		longitude = gps.getLongitude();
//		
//		if(latitude==0.0){
//			if(sacaLatitud()!=null){
//				latitude=Double.valueOf(sacaLatitud());
//			}else{
//				latitude=0.0;
//			}
//		}
//		
//		if(longitude==0.0){
//			if(sacaLongitud()!=null){
//				longitude=Double.valueOf(sacaLongitud());
//			}else{
//				longitude=0.0;
//			}
//		}
//		
//		String strLatitud=String.valueOf(latitude);
//		String strLongitud=String.valueOf(longitude);
//
//		String seg = formattedDate5.substring(7);
//		System.out.println("El segundo: " + seg);
//		System.out.println("El Imei" + sacaImei());
//
//		cachaNombre();
//
//		String strId = String.valueOf(rand + 1);
//
//		elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
//
//		// Clase que permite grabar texto en un archivo
//		FileOutputStream fout = null;
//		try {
//			// INSERTA EN LA BASE DE DATOS //
//
//			final String F = "File dbfile";
//
//			// Abrimos la base de datos 'DBUsuarios' en modo escritura
//			String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
//			usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);
//
//			db = usdbh.getWritableDatabase();
//
//			// NORMAL
//			Nombre nom = new Nombre();
//			String nombreE = nom.nombreEncuesta();
//
//			if (db != null) {
//				ContentValues values = new ContentValues();
//				values.put("consecutivo_diario", elMaximo);
//				values.put("usuario", cachaNombre().toUpperCase());
//				values.put("equipo", equipo.toUpperCase());
//				values.put("nombre_encuesta", nombreE.toUpperCase());
//				values.put("fecha", formattedDate3);
//				values.put("hora", formattedDate5);
//				values.put("imei", sacaImei());
//				values.put("seccion", strSecc);
//				values.put("latitud", strLatitud);
//				values.put("longitud", strLongitud);
//
//				values.put("genero", "Femenino");
//				values.put("suma", "0");
//				values.put("status", "0");
//
//				values.put("tiempo", "00:00");
//				values.put("tipocaptura", "RECHAZO");
//
//				db.insert("encuestas", null, values);
//			}
////			db.close();
//
//			// FIN INSERTA BASE DE DATOS //
//			
//			txtEquipo.setText("");
//			btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//			btnRechazo.setEnabled(false);
////			btnRechazo2.setEnabled(false);
//			txtResultado.setText("");// limpia el cuadro de texto
//
//		} catch (Exception e) {
//			System.out.println("algo pasó...1");
//			Log.i("Guardar", e.getMessage());
//		}
//
//	}

    //
//	public void valoresRechazoMasculino2() {
//
//		String strSecc = spinner3.getSelectedItem().toString();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//
//		String seg = formattedDate5.substring(7);
//		System.out.println("El segundo: " + seg);
//		System.out.println("El ID" + prefix);
//
//		cachaNombre();
//
//		String strId = String.valueOf(rand + 1);
//		
//		GPSTracker gps = new GPSTracker(this);
//		latitude = gps.getLatitude();
//		longitude = gps.getLongitude();
//		
//		if(latitude==0.0){
//			if(sacaLatitud()!=null){
//				latitude=Double.valueOf(sacaLatitud());
//			}else{
//				latitude=0.0;
//			}
//		}
//		
//		if(longitude==0.0){
//			if(sacaLongitud()!=null){
//				longitude=Double.valueOf(sacaLongitud());
//			}else{
//				longitude=0.0;
//			}
//		}
//		
//		String strLatitud=String.valueOf(latitude);
//		String strLongitud=String.valueOf(longitude);
//
//
//		elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
//
//		// Clase que permite grabar texto en un archivo
//		FileOutputStream fout = null;
//		try {
//			// INSERTA EN LA BASE DE DATOS //
//
//			String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
//			usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);
//
//			db = usdbh.getWritableDatabase();
//
//			// NORMAL
//			Nombre nom = new Nombre();
//			String nombreE = nom.nombreEncuesta();
//
//			if (db != null) {
//				ContentValues values = new ContentValues();
//				values.put("consecutivo_diario", elMaximo);
//				values.put("usuario", cachaNombre().toUpperCase());
//				values.put("equipo", equipo.toUpperCase());
//				values.put("nombre_encuesta", nombreE.toUpperCase());
//				values.put("fecha", formattedDate3);
//				values.put("hora", formattedDate5);
//				values.put("imei", sacaImei());
//				values.put("seccion", strSecc);
//				values.put("latitud", strLatitud);
//				values.put("longitud", strLongitud);
//
//				values.put("genero", "Masculino");
//				values.put("suma", "0");
//				values.put("status", "0");
//
//				values.put("tiempo", "00:00");
//				values.put("tipocaptura", "RECHAZO TEMOR A CONTAGIO");
//
//				db.insert("encuestas", null, values);
//			}
////			db.close();
//
//			// FIN INSERTA BASE DE DATOS //
//			
//			txtEquipo.setText("");
//			btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//			btnRechazo.setEnabled(false);
////			btnRechazo2.setEnabled(false);
//			txtResultado.setText("");// limpia el cuadro de texto
//
//		} catch (Exception e) {
//			System.out.println("algo pasó...1");
//			Log.i("Guardar", e.getMessage());
//		}
//
//	}
//
//	public void valoresRechazoFemenino2() {
//
//		String strSecc = spinner3.getSelectedItem().toString();
//		equipo = spinner2.getSelectedItem().toString().toLowerCase();
//		
//		GPSTracker gps = new GPSTracker(this);
//		latitude = gps.getLatitude();
//		longitude = gps.getLongitude();
//		
//		if(latitude==0.0){
//			if(sacaLatitud()!=null){
//				latitude=Double.valueOf(sacaLatitud());
//			}else{
//				latitude=0.0;
//			}
//		}
//		
//		if(longitude==0.0){
//			if(sacaLongitud()!=null){
//				longitude=Double.valueOf(sacaLongitud());
//			}else{
//				longitude=0.0;
//			}
//		}
//		
//		String strLatitud=String.valueOf(latitude);
//		String strLongitud=String.valueOf(longitude);
//
//		String seg = formattedDate5.substring(7);
//		System.out.println("El segundo: " + seg);
//		System.out.println("El Imei" + sacaImei());
//
//		cachaNombre();
//
//		String strId = String.valueOf(rand + 1);
//
//		elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
//
//		// Clase que permite grabar texto en un archivo
//		FileOutputStream fout = null;
//		try {
//			// INSERTA EN LA BASE DE DATOS //
//
//			final String F = "File dbfile";
//
//			// Abrimos la base de datos 'DBUsuarios' en modo escritura
//			String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
//			usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);
//
//			db = usdbh.getWritableDatabase();
//
//			// NORMAL
//			Nombre nom = new Nombre();
//			String nombreE = nom.nombreEncuesta();
//
//			if (db != null) {
//				ContentValues values = new ContentValues();
//				values.put("consecutivo_diario", elMaximo);
//				values.put("usuario", cachaNombre().toUpperCase());
//				values.put("equipo", equipo.toUpperCase());
//				values.put("nombre_encuesta", nombreE.toUpperCase());
//				values.put("fecha", formattedDate3);
//				values.put("hora", formattedDate5);
//				values.put("imei", sacaImei());
//				values.put("seccion", strSecc);
//				values.put("latitud", strLatitud);
//				values.put("longitud", strLongitud);
//
//				values.put("genero", "Femenino");
//				values.put("suma", "0");
//				values.put("status", "0");
//
//				values.put("tiempo", "00:00");
//				values.put("tipocaptura", "RECHAZO TEMOR A CONTAGIO");
//
//				db.insert("encuestas", null, values);
//			}
////			db.close();
//
//			// FIN INSERTA BASE DE DATOS //
//			
//			txtEquipo.setText("");
//			btnSiguiente.setEnabled(false);// desactiva el botón siguiente
//			btnRechazo.setEnabled(false);
////			btnRechazo2.setEnabled(false);
//			txtResultado.setText("");// limpia el cuadro de texto
//
//		} catch (Exception e) {
//			System.out.println("algo pasó...1");
//			Log.i("Guardar", e.getMessage());
//		}
//
//	}
//
//	
    public void dialogoRechazo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sexo de quien rechaza")
                        .setTitle("Rechazo de Encuesta").setCancelable(false)
                        .setNegativeButton("Masculino", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//								valoresRechazoMasculino();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//								valoresRechazoFemenino();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Nombre", encuestaQuien);
                        // actividad
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }

    public void dialogoRechazo2() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sexo de quien rechaza")
                        .setTitle("Rechazo por temor de Contagio").setCancelable(false)
                        .setNegativeButton("Masculino", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//								valoresRechazoMasculino2();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

//								valoresRechazoFemenino2();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("Nombre", encuestaQuien);
                        // actividad
                        startActivity(intent);
                        finish();

                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        });

    }

    public void dameTodo() throws IOException {

        File myFile;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String TimeStampDB = sdf.format(cal.getTime());

        try {

            File sdCard = null, directory, file = null;

            // validamos si se encuentra montada nuestra memoria externa

            if (Environment.getExternalStorageState().equals("mounted")) {

                // Obtenemos el directorio de la memoria externa
                sdCard = Environment.getExternalStorageDirectory();

            }

            // Obtenemos el direcorio donde se encuentra nuestro
            // archivo a leer
            directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");

            // Creamos un objeto File de nuestro archivo a leer
            file = new File(directory, nombreArchivoCSV());

            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append("diario,seccion,usuario,fecha,hora,tablet");

            myOutWriter.append("\n");

            String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            Cursor c = db.rawQuery("SELECT * FROM locatario", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String diario = c.getString(c.getColumnIndex("diario"));
                        String usuario = c.getString(c.getColumnIndex("usuario"));
                        String fecha = c.getString(c.getColumnIndex("fecha"));
                        String hora = c.getString(c.getColumnIndex("hora"));
                        String tablet = c.getString(c.getColumnIndex("tableta"));
                        String seccion = c.getString(c.getColumnIndex("seccion"));

                        myOutWriter.append(
                                diario + "," + seccion + "," + usuario + "," + fecha + "," + hora + "," + tablet);
                        myOutWriter.append("\n");

                    }

                    while (c.moveToNext());
                }

                c.close();
                myOutWriter.close();
                fOut.close();

            }
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {

//			db.close();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
//		db.close();

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
//		db.close();

        return acceso;
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

    // public void grabar() {
    // mediaRecorder = new MediaRecorder();
    // mediaRecorder.setOutputFile(fichero);
    // mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    // mediaRecorder.setOutputFormat(MediaRecorder.
    // OutputFormat.THREE_GPP);
    // mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.
    // AMR_NB);
    // try {
    // mediaRecorder.prepare();
    // } catch (IOException e) {
    // Log.e(LOG_TAG, "Fallo en grabación");
    // }
    // mediaRecorder.start();
    // }
    //
    //
    // public void detenerGrabacion() {
    // mediaRecorder.stop();
    // mediaRecorder.release();
    // finish();
    // }
    //

}
