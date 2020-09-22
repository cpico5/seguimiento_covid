package mx.gob.cdmx.seguimientocovid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import mx.gob.cdmx.seguimientocovid.R;
import mx.gob.cdmx.seguimientocovid.model.DatoContent;

import static mx.gob.cdmx.seguimientocovid.Nombre.encuesta;

import static mx.gob.cdmx.seguimientocovid.Nombre.USUARIO;
import static mx.gob.cdmx.seguimientocovid.Nombre.customURL;

public class MainActivity extends Activity implements OnItemSelectedListener, AdapterView.OnItemClickListener {


    private static final String TAG = "MainActivity";
    Random random = new java.util.Random();
    public int rand;

    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();
    private View mProgressView;
    AutoCompleteTextView autoCompleteTextView;


    private EditText txtCodigo;
    private EditText txtNombre;
    private TextView txtResultado;
    private TextView txtEncuesta;
    private TextView txtEquipo;

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnConsultar;
    private Button btnSiguiente;
    private Button btnEnviar;
    private Button btnRechazo;
    private Button btnRechazo2;
    private Button btnInformacion;
    private Button btnNoCasa;
    private Button btnNoAbre;
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
    private Spinner spinner_seccion;
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
        TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
            System.out.println("Mi Número: " + szImei);

            return szImei;
        }
        String szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
        System.out.println("Mi Número: " + szImei);

        return szImei;
    }

    public String sacaChip(){
        String deviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
        tablet=deviceId;
        return tablet;
    }

    public String cachaNombre() {
        Bundle datos = this.getIntent().getExtras();
        encuestador = datos.getString("Nombre");
        return encuestador;
    }


    private ArrayList<String> SeccionesArray(String alc) {

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        ArrayList<String> set = new ArrayList<String>();
        db2 = usdbh2.getWritableDatabase();

        String selectQuery1;

        if(Integer.parseInt(cachaNombre())==1){
            Log.i(TAG,"cqs --------->> Usuario secciones todos "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM secciones order by seccion asc";
        }else if(Integer.parseInt(cachaNombre())>=3000 && Integer.parseInt(cachaNombre())<=4035){
            Log.i(TAG,"cqs --------->> Usuario secciones unico "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM secciones WHERE delegacion='" + alc + "' order by seccion asc";
            Log.i(TAG,"cqs --------->> Usuario secciones unico "+ selectQuery1);

        }else if(Integer.parseInt(cachaNombre())>=4036 && Integer.parseInt(cachaNombre())<=4047 ){
            Log.i(TAG,"cqs --------->> Usuario secciones todos "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM secciones order by seccion asc";
        }else{
            Log.i(TAG,"cqs --------->> Usuario secciones "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM datos WHERE equipo='" + equipo + "' order by seccion asc";
        }


//        String selectQuery1 = "SELECT DISTINCT seccion FROM datos";
        Cursor c = db2.rawQuery(selectQuery1, null);
        if (c.moveToFirst()) {
            do {
                set.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
//		db.close();
        return set;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        Log.i("GPS", "Latitud sin GPS: "+latitude);
        Log.i("GPS", "Longitud sin GPS: "+longitude);

//		if(gps.canGetLocation()){
//		    double latitude = gps.getLatitude();
//		    double longitude = gps.getLongitude();
//		}else{
//		// can't get location
//		// GPS or Network is not enabled
//		// Ask user to enable GPS/network in settings
//		    gps.showSettingsAlert();
//			Log.i("GPS", "Latitud sin GPS: "+latitude);
//			Log.i("GPS", "Longitud sin GPS: "+longitude);
//		}
//


        sacaChip();

        File sdCard, directory, file;

        sdCard = Environment.getExternalStorageDirectory();
        // directory = new File(sdCard.getAbsolutePath() +
        // "/Audio"+formattedDate3+"");

        // directory.mkdirs();//CREA EL DIRECTORIO SI NO EXISTE

        // Obtenemos las referencias a los controles
        // txtCodigo = (EditText)findViewById(R.id.txtReg);
        // txtNombre = (EditText)findViewById(R.id.txtVal);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
        txtEncuesta = (TextView) findViewById(R.id.txtEncuesta);
        txtEquipo = (TextView) findViewById(R.id.txtEquipo);

        spinner2 = (Spinner) findViewById(R.id.spinner2);
//        spinner_seccion = (Spinner) findViewById(R.id.spinner_seccion);

        // btnInsertar = (Button)findViewById(R.id.btnInsertar);
        // btnActualizar = (Button)findViewById(R.id.btnActualizar);
        // btnEliminar = (Button)findViewById(R.id.btnEliminar);
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnSiguiente.setEnabled(false);


        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mProgressView = findViewById(R.id.login_progressMain);

//		btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnRechazo = (Button) findViewById(R.id.btnRechazo);
        btnRechazo2 = (Button) findViewById(R.id.btnRechazo2);
        btnInformacion = (Button) findViewById(R.id.btnInformacion);
        btnRechazo.setEnabled(false);
        btnRechazo2.setEnabled(false);
        btnNoCasa = (Button) findViewById(R.id.btnNoCasa);
        btnNoAbre = (Button) findViewById(R.id.btnNoAbre);
        btnNoAbre.setEnabled(false);
        btnNoCasa.setEnabled(false);

        if (cachaNombre() == null) {

            encuestaQuien = cachaNombre2();

        } else {

            cachaNombre();

            encuestaQuien = cachaNombre();

        }

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.actv);

//        String coord = "algo";
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, SeccionesArray(coord));
//
//        autoCompleteTextView.setAdapter(adapter);
//        autoCompleteTextView.setOnItemClickListener(this);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length() != 0){
                    btnSiguiente.setEnabled(false);
                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);
                }
            }
        });



        cargaUsuarioBrigadas(cachaNombre());


//        if(cachaNombre().equals("1")){
//            cargaUsuarioBrigadas(cachaNombre());
//        }else if(Integer.parseInt(cachaNombre())>=3000){
//
//            cargaUsuarioBrigadas(cachaNombre());
//        } else {
//
//            cargaUsuario(cachaNombre());
//        }
        // SeccionesSpinner();

        final String F = "File dbfile";

        String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

        db = usdbh.getWritableDatabase();

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);

        db2 = usdbh2.getWritableDatabase();

        btnConsultar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                txtResultado.setText("");

                try {

                    Log.i(TAG,"cqs ---------->> delegacion: "+delegacion());

                }catch (Exception e){
                    String stackTrace = Log.getStackTraceString(e);
                    Log.i(TAG, "Error al sacar la delegación" + stackTrace);
                    Toast toast2 = Toast.makeText(getApplicationContext(), "No existe esta sección en la muestra", Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast2.show();

                    autoCompleteTextView.setText("");
                    btnSiguiente.setEnabled(false);

                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);
                    btnNoAbre.setEnabled(false);
                    btnNoCasa.setEnabled(false);

                }

                if(autoCompleteTextView.getText().toString().trim().length()==0){
                    btnSiguiente.setEnabled(false);
                    btnRechazo.setEnabled(false);
                    btnRechazo2.setEnabled(false);
                    btnNoAbre.setEnabled(false);
                    btnNoCasa.setEnabled(false);

                    Toast toast2 = Toast.makeText(getApplicationContext(), "No existe esta sección en la muestra",Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast2.show();

                }else {
                    btnSiguiente.setEnabled(true);
                    btnRechazo.setEnabled(true);
                    btnRechazo2.setEnabled(true);
                    btnNoAbre.setEnabled(true);
                    btnNoCasa.setEnabled(true);
                }

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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

        String equipo = spinner2.getSelectedItem().toString();

        System.out.println("El ID: " + ID);
        System.out.println("El prefix: " + prefix);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID: " + prefix);

//        System.out.println("El equipo:  " + equipo);
//        System.out.println("delegacion:  " + delegacion().toString().toUpperCase());
//        System.out.println("equipo:  " + equipo().toString().toUpperCase());

        // System.out.println("el peso "+elPeso());

        // if (elPeso()==0) {

        // if(area.equals("ORIENTE-edgar")||area.equals("ORIENTE-EDDY")||area.equals("ORIENTE-MAGGIE")||area.equals("ORIENTE-MAYRA")){
        //
        // String delegacion=text;
        // Intent intent = new Intent(getApplicationContext(),
        // MainActivityPantalla2.class);
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
        //
        // } else {

        // pasaDatos();
        // }

        // } else {

        // System.out.println(cuentaArchivos());
        // new UpdateProgress().execute();
        dialogo();
        // }
    }

    public void Salir(View v) {

        finish();
    }


    public void Rechazo(View view) {

        dialogoRechazo();

    }

    public void Rechazo2(View view) {

        dialogoRechazo2();

    }

    public void NoAbre(View view) {

        dialogoNoAbre();

    }

    public void NoCasa(View view) {

        dialogoNoCasa();

    }

    public void Info(View view) {

        Intent intent = new Intent(getApplicationContext(), Menu_Principal.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Seccion", autoCompleteTextView.getText().toString());
        intent.putExtra("Nombre", encuestaQuien);
        intent.putExtra("equipo", equipo());// para pasar la variable a la otra
        // actividad
        intent.putExtra("t1", milis1);
        startActivity(intent);
        finish();

    }

    public void dialogo() {

        seccion = autoCompleteTextView.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // builder.setMessage(cuentaArchivos())
        builder.setMessage("Punto " + seccion + ": \n\n" + "Encuestas: \n" + "Normal:   " + " " + dameSecciones()
                + "\n" + "Abandono: " + " " + dameAbandonos() + "\n"
                + "\n" + "Rechazos: " + " " + dameRechazos() + "\n\n")
//                + "Rechazos por contagio: " + dameRechazosContagio() + "\n\n")
//				+ "Sexo: \n" + "Masculino: " + dameHombres() + "\n" + "Femenino:  " + dameMujeres() + "\n\n"
//                + "Rechazos Totales: " + dameRechazosTodos() + "\n\n")

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

    public String delegacion() {

        seccion = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        db2 = usdbh2.getWritableDatabase();
        String selectQuery1 = " SELECT delegacion FROM secciones  WHERE seccion='" + seccion + "'";
        Cursor c = db2.rawQuery(selectQuery1, null);

        c.moveToFirst();
        // Recorremos el cursor hasta que no haya más registros
        String delegacion = c.getString(0);
        c.close();
        db2.close();
        return delegacion;
    }



    public String equipo() {

        seccion = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        // db = usdbh.getWritableDatabase();
        // // String selectQuery1 = " SELECT a.equipo FROM datos as a, delegados
        // as
        // // b WHERE a.seccion=b.seccion and a.seccion='"+seccion+"'";
        // String selectQuery1 = " SELECT equipo FROM datos WHERE seccion='" +
        // seccion + "'";
        // Cursor c = db.rawQuery(selectQuery1, null);
        //
        // c.moveToFirst();
        // // Recorremos el cursor hasta que no haya más registros
        // String equipo = c.getString(0);
        // c.close();
        // db.close();

        return equipo;
    }

    public void cargaEncuestador() {

        db = usdbh.getWritableDatabase();
        String selectQuery1 = " SELECT usuario, count(*) as total from contactos where fecha='" + formattedDate3
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

        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;

        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from contactos where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and tipo_captura='NORMAL' group by seccion";
        // String selectQuery1 = "select seccion, count(*) as total from
        // locatario where seccion='"+seccion+"' group by seccion"; //total de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            cuantasSecciones_base = c.getString(1);
        }

        else {

            cuantasSecciones_base = "0";

        }

        c.close();
        db.close();
        return cuantasSecciones_base;

    }

    public String dameAbandonos() {
        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from contactos where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "' and (tipo_captura='ABANDONO' or tipo_captura='ABANDONO TEMOR A CONTAGIO')  group by seccion";
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

        seccion = autoCompleteTextView.getText().toString();
        String masculino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from contactos where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='MASCULINO' and tipo_captura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            masculino = c.getString(1);
        }

        else {

            masculino = "0";

        }

        c.close();
        db.close();
        return masculino;

    }

    public String dameMujeres() {

        seccion = autoCompleteTextView.getText().toString();
        String femenino;

        db = usdbh.getWritableDatabase();
        // String selectQuery1 = "select seccion, count(*) as total from
        // encuestas where seccion='"+seccion+"' and fecha='"+formattedDate1+"'
        // group by seccion";
        String selectQuery1 = "select socioeconomico_12, count(*) as total from contactos where seccion='" + seccion
                + "' and fecha='" + formattedDate3 + "'  and socioeconomico_12='FEMENINO' and tipo_captura='NORMAL' group by socioeconomico_12"; // total
        // de
        // encuestas
        Cursor c = db.rawQuery(selectQuery1, null);

        if (c.getCount() > 0) {
            // Recorremos el cursor hasta que no haya más registros
            c.moveToFirst();
            femenino = c.getString(1);
        }

        else {

            femenino = "0";

        }

        c.close();
        db.close();
        return femenino;

    }

    public String dameRechazosTodos() {
        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from contactos where fecha='" + formattedDate3 + "' and (tipo_captura='RECHAZO' or tipo_captura='RECHAZO TEMOR A CONTAGIO')  group by seccion";
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
        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from contactos where seccion='" + seccion + "' and fecha='" + formattedDate3 + "' and (tipo_captura='RECHAZO' or tipo_captura='RECHAZO TEMOR A CONTAGIO') group by seccion";
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

    public String dameRechazosContagio() {
        seccion = autoCompleteTextView.getText().toString();
        String cuantasSecciones_base;
        db = usdbh.getWritableDatabase();
        String selectQuery1 = "select seccion, count(*) as total from contactos where seccion='" + seccion + "' and fecha='" + formattedDate3 + "' and (tipo_captura='RECHAZO TEMOR A CONTAGIO') group by seccion";
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
        String laAlcaldia;
        laAlcaldia=sacaAlcaldiaBinomio();
//        if(sacaAlcaldia(spinner_seccion.getSelectedItem().toString())==null){
//
//        }else{
//            laAlcaldia=sacaAlcaldia(spinner_seccion.getSelectedItem().toString());
//        }
        Intent intent = new Intent(getApplicationContext(), MainActivityPantalla1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Seccion", autoCompleteTextView.getText().toString());
        intent.putExtra("Nombre", encuestaQuien);
        intent.putExtra("alcaldia", laAlcaldia);
        intent.putExtra("equipo", equipo());// para pasar la variable a la otra
        // actividad
        intent.putExtra("t1", milis1);
        startActivity(intent);
        finish();

    }




    //Usuarios

    private void cargaUsuarioTodos() {

        final String[] datos = new String[] {
                "FRANCISCO LUCAS LUIS JAVIER",
                "TREJO DURAN ABRAHAM",
                "TUNION FIGUEROA DIANA LAURA",
                "MARTINEZ ARIAS ISMAEL",
                "BERTHA REYES",
                "ANGELES LOPEZ",
                "NORA SUCHETT",
                "ADELAIDA DE JESUS AVILA PEREZ",
                "FERNANDA KUMASI CORIA PLANCARTE",
                "CLAUDIA ACEVEDO MARTINEZ",
                "CRISTINA GONZALEZ NAVA",
                "IVONE MEDINA AGUILAR",
                "LUIS ESTRADA RAMIREZ",
                "LIGIA LOPEZ ROJAS",
                "CARLOS ROSAS TREJO",
                "MA LUISA CRUZ DE JESUS",
                "MARISOL PACHECO DIAZ",
                "BENJAMIN ARIAS ALONSO",
                "CAROLINA ESTRADA GOMEZ",
                "ALAN YAIR MEJIA ALCAZAR",
                "EDGAR POZAS GRANADOS",
                "KELVIN URIEL ROQUE GONZALEZ",
                "MELENDEZ HERNANDEZ TRINIDAD ALMENDRA",
                "SANCHÉZ WINDFIELD GUSTAVO",
                "LUNA GARCÍA PATRICIA",
                "LARA BÉNITEZ VERONICA",
                "GARCÍA AGUILAR NORMA ANGELICA",
                "RÚIZ GUTIERREZ AXEL",
                "VARGAS SOLIS ADRIANA",
                "VERA GONZALEZ GERARDO DANIEL",
                "GARCIA RIVERA GERARDO MISHAEL",
                "RUVALCABA MOTA RAQUEL ADRIANA",
                "JUANA ESPINOZA HERNANDEZ",
                "JESUS MENDOZA",
                "JESSICA PAOLA RODRIGUEZ",
                "JULIA MARCOS HERNANDEZ",
                "OJEDA SALAZAR SANDRA LUZ",
                "VALDEZ NOGUERON PATRICIA",
                "HERRERA RANGEL JOSE DANIEL",
                "MANZANO ALTAMIRANO MARICELA",
                "ANA MARIA CASAS MIRANDA",
                "YENDI LUCERO ROCHA CASTRO",
                "ANA LILIA BEIMONT TAPIA",
                "ROSA MILLAN HUERTA",
                "ARMANDO SOUZA ERREGUÍN",
                "LEON ESPINOSA NANCY VIRIDIANA",
                "MENDOZA SANCHEZ FRANCISCO ARTURO",
                "MEDINA NIETO RICARDO",
                "VELAZQUEZ BRAVO MARCOS FERNANDO",
                "NATALY CHORA HERNANDEZ",
                "LOURDES ALVARADO GALLARDO",
                "EDUARDO SOTELO MARTINEZ",
                "ANA LAURA SANDOVAL BAUTISTA",
                "LUIS RAMIREZ GARCIA",
                "ANTONIO BENJAMIN CAMPOS PEREZ",
                "CLAUDIA NAVARRO HERNÁNDEZ",
                "ALEJANDRA YAZMIN PACHECO",
                "LUIS FRANCISCO MIGUEL",
                "PAOLA RAMIREZ SOSA",
                "MARTINEZ ROMERO VICTOR HUGO",
                "JIMENEZ CAPIZ ELEANE",
                "LUNA HERNANDEZ JOSE MIGUEL",
                "GUTIERREZ GARCIA JORGE",
                "ALVARADO MEDINA JOSE ARTURO",
                "VANEGAS CONTRERAS DAMARIS",
                "LOPEZ PELCASTRE MARIBEL",
                "LOPEZ MALDONADO EDMUNDA"
        };

        // Alternativa 1: Array java
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adaptador);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

                String laAlcaldia=sacaAlcaldiaBinomio();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, SeccionesArray(laAlcaldia));

                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener(MainActivity.this);

//                SeccionesSpinner();
                txtEquipo.setText("Seleccionado: " + datos[position]);
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                txtEquipo.setText("");
            }
        });

    }


    private void cargaUsuario(String usuario) {

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);

        Set<String> set = new HashSet<String>();

        db2 = usdbh2.getWritableDatabase();

        String selectQuery1 = "SELECT * FROM datos WHERE usuario='" + usuario + "'";

        // Otra consulta Select seccion from datos where equipo ='eddy' and not
        // exists (select seccion from encuestas where encuestas.seccion =
        // datos.seccion) GROUP BY datos.seccion HAVING count(*) <=4;
        Cursor c = db2.rawQuery(selectQuery1, null);

        if (c.moveToFirst()) {
            do {

                set.add(c.getString(4));

                String usu = c.getString(4);

            } while (c.moveToNext());
        }
        c.close();
//		db.close();
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set1 = set;
        List<String> list = new ArrayList<String>(set1);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Collections.sort(list);
        spinner2.setAdapter(adapter);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {

                String laAlcaldia=sacaAlcaldiaBinomio();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, SeccionesArray(laAlcaldia));

                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener(MainActivity.this);

//                SeccionesSpinner();
                txtEquipo.setText("Seleccionado: " + spinner2.getSelectedItem());
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // txtEquipo.setText("");
            }
        });
        spinner2.setWillNotDraw(false);

    }


    private void cargaUsuarioBrigadas(String usuario) {

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);

        Set<String> set = new HashSet<String>();

        db2 = usdbh2.getWritableDatabase();

        String selectQuery1 = "SELECT * FROM brigadas WHERE usuario='" + usuario + "'";

        // Otra consulta Select seccion from datos where equipo ='eddy' and not
        // exists (select seccion from encuestas where encuestas.seccion =
        // datos.seccion) GROUP BY datos.seccion HAVING count(*) <=4;
        Cursor c = db2.rawQuery(selectQuery1, null);

        if (c.moveToFirst()) {
            do {

                set.add(c.getString(1));

                String usu = c.getString(1);

            } while (c.moveToNext());
        }
        c.close();
//		db.close();
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set1 = set;
        List<String> list = new ArrayList<String>(set1);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Collections.sort(list);
        spinner2.setAdapter(adapter);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
//                SeccionesSpinner();
                String laAlcaldia=sacaAlcaldiaBinomio();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1, SeccionesArray(laAlcaldia));

                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener(MainActivity.this);
                txtEquipo.setText("Seleccionado: " + spinner2.getSelectedItem());
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // txtEquipo.setText("");
            }
        });
        spinner2.setWillNotDraw(false);

    }




    private void SeccionesSpinner() {

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);

        Set<String> set = new HashSet<String>();

        equipo = spinner2.getSelectedItem().toString();
        System.out.println(equipo);

        db2 = usdbh2.getWritableDatabase();

        String laAlcaldia=sacaAlcaldiaBinomio();

        String selectQuery1 = null;
        if(Integer.parseInt(cachaNombre())>=3000 && Integer.parseInt(cachaNombre())<=4035){
            Log.i(TAG,"cqs --------->> Usuario secciones unico "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM secciones WHERE delegacion='" + laAlcaldia + "' order by seccion asc";

        }else if(Integer.parseInt(cachaNombre())>=4036 && Integer.parseInt(cachaNombre())<=4047 ){
            Log.i(TAG,"cqs --------->> Usuario secciones todos "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM secciones order by seccion asc";
        }else{
            Log.i(TAG,"cqs --------->> Usuario secciones "+ Integer.parseInt(cachaNombre()) );
            selectQuery1 = "SELECT * FROM datos WHERE equipo='" + equipo + "' order by seccion asc";
        }

        Log.i(TAG,"cqs --------->> Usuario secciones "+ Integer.parseInt(cachaNombre()) );
        Log.i(TAG,"cqs --------->> Query secciones "+ selectQuery1 );

        Cursor c = db2.rawQuery(selectQuery1, null);

        if (c.moveToFirst()) {
            do {

                set.add(c.getString(0));

                String secc = c.getString(0);

            } while (c.moveToNext());
        }

        else {
            Toast toast2 = Toast.makeText(getApplicationContext(), "NO CORRESPONDE ESTE NÚMERO DE SECCIÓN...!",
                    Toast.LENGTH_LONG);

            toast2.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);

            toast2.show();

        }

        c.close();
//		db.close();
        // here i used Set Because Set doesn't allow duplicates.
        Set<String> set1 = set;
        List<String> list = new ArrayList<String>(set1);

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, list);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Collections.sort(list);
        spinner_seccion.setAdapter(adapter);

        // ACCIÓN QUE SE REALIZA CUANDO ES SELECCIOANDO UN ELEMENTO DEL SPINNER
        spinner_seccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, android.view.View v, int position, long id) {
                btnSiguiente.setEnabled(false);// desactiva el botón siguiente
                txtResultado.setText("");// limpia el cuadro de texto
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // txtEquipo.setText("");
            }
        });
        spinner_seccion.setWillNotDraw(false);

    }

    private String sacaMaximo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura

        String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

        db = usdbh.getReadableDatabase();

        String selectQuery = "SELECT count(*) FROM contactos where fecha='" + formattedDate3 + "'";

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

        String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

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



    public void valoresRechazoMasculino() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();

//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);

                values.put("genero", "Masculino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "3");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexion",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }
            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }

    public void valoresRechazoFemenino() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El Imei" + sacaImei());

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            // Abrimos la base de datos 'DBUsuarios' en modo escritura
            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();
//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);
                values.put("alcaldia",laAlcaldia);



                values.put("genero", "Femenino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "3");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexion",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }
            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }


    public void valoresRechazoMasculino2() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();
//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);


                values.put("genero", "Masculino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "5");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO TEMOR A CONTAGIO");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }
            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }

    public void valoresRechazoFemenino2() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El Imei" + sacaImei());

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            final String F = "File dbfile";

            // Abrimos la base de datos 'DBUsuarios' en modo escritura
            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();

//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }
                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);


                values.put("genero", "Femenino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "5");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "RECHAZO TEMOR A CONTAGIO");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }


            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);

            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG,"Error inserta rechazo contagio femenino"+ stackTrace);
        }

    }


    public void valoresNoAbre() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();
//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);


                values.put("genero", "Masculino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "6");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "NO ABRE");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }
            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
        }

    }


    public void valoresNoCasa() {

        String strSecc = autoCompleteTextView.getText().toString();
        equipo = spinner2.getSelectedItem().toString().toLowerCase();

        String seg = formattedDate5.substring(7);
        System.out.println("El segundo: " + seg);
        System.out.println("El ID" + prefix);

        cachaNombre();

        String strId = String.valueOf(rand + 1);

        GPSTracker gps = new GPSTracker(this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();

        if(latitude==0.0){
            latitude=Double.valueOf(sacaLatitud());
        }

        if(longitude==0.0){
            longitude=Double.valueOf(sacaLongitud());
        }

        String strLatitud=String.valueOf(latitude);
        String strLongitud=String.valueOf(longitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;

        // Clase que permite grabar texto en un archivo
        FileOutputStream fout = null;
        try {
            // INSERTA EN LA BASE DE DATOS //

            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

            db = usdbh.getWritableDatabase();

            // NORMAL
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            long consecutivoObtenido = 0;
            String laAlcaldia;
            ContentValues values = new ContentValues();
            if (db != null) {

                laAlcaldia=sacaAlcaldiaBinomio();
//                if(Integer.parseInt(cachaNombre())>=3000){
//
//                    laAlcaldia=sacaAlcaldiaBinomio();
//                }else{
//                    laAlcaldia=sacaAlcaldia(strSecc);
//                }

                values.put("consecutivo_diario", elMaximo);
                values.put("usuario", cachaNombre().toUpperCase());
                values.put("equipo", equipo.toUpperCase());
                values.put("nombre_encuesta", nombreE.toUpperCase());
                values.put("fecha", formattedDate3);
                values.put("hora", formattedDate5);
                values.put("imei", sacaImei());
                values.put("seccion", strSecc);
                values.put("latitud", strLatitud);
                values.put("longitud", strLongitud);


                values.put("genero", "Masculino");
                values.put("observaciones", "sin datos");
                values.put("abandono", "7");
//                values.put("suma", "0");
//                values.put("status", "0");

                values.put("tiempo", "00:00");
                values.put("tipo_captura", "NO ES CASA");

                if (!verificaConexion(this)) {
                    Toast.makeText(getBaseContext(),"Sin conexión",Toast.LENGTH_LONG).show();
                    values.put("enviado", "0");
                    db.insert("contactos", null, values);
                }else{
                    values.put("enviado", "1");
                    consecutivoObtenido = db.insert("contactos", null, values);
                }
            }
//			db.close();

            values.put("consecutivo", consecutivoObtenido);
//
            guardaEncuestaWS(values);
            // FIN INSERTA BASE DE DATOS //

            txtEquipo.setText("");
            btnSiguiente.setEnabled(false);// desactiva el botón siguiente
            btnRechazo.setEnabled(false);
            btnRechazo2.setEnabled(false);
            btnNoAbre.setEnabled(false);
            btnNoCasa.setEnabled(false);
            txtResultado.setText("");// limpia el cuadro de texto

        } catch (Exception e) {
            System.out.println("algo pasó...1");
            Log.i("Guardar", e.getMessage());
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
                            intent.putExtra(USUARIO,cachaNombre());
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
                    Log.e(TAG, "PIMC-----------------> existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "pimc -----------> " + new String(responseBody));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                if (statusCode != 200) {
                    Log.e(TAG, "Existe un error en la conexion -----> " + error.getMessage());
                    if (responseBody != null)
                        Log.d(TAG, "pimc -----------> " + new String(responseBody));

                }

                Toast.makeText(MainActivity.this, "Error de conexion, se guarda en la base interna", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void dialogoRechazo() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Sexo de quien rechaza")
                        .setTitle("Rechazo de Encuesta").setCancelable(false)
                        .setNegativeButton("Masculino", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                valoresRechazoMasculino();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        valoresRechazoFemenino();
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

                                valoresRechazoMasculino2();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("Nombre", encuestaQuien);
                                // actividad
                                startActivity(intent);
                                finish();

                            }
                        }).setPositiveButton("Femenino", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        valoresRechazoFemenino2();
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

    public void dialogoNoAbre() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Cierre de levantamiento")
                        .setTitle("No abre").setCancelable(false)
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                valoresNoAbre();
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

    public void dialogoNoCasa() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        MainActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Cierre de levantamiento")
                        .setTitle("No es casa").setCancelable(false)
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                valoresNoCasa();
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

            String DATABASE_NAME = Environment.getExternalStorageDirectory() +"/Mis_archivos/" +nombreEncuesta+"_"+sacaImei()+"";
            usdbh = new UsuariosSQLiteHelper(this, "F", null, 1,DATABASE_NAME);

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
        }

        finally {

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

    private String sacaAlcaldia(String secc) {
        Set<String> set = new HashSet<String>();
        String alcaldia = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "select delegacion from secciones where seccion='"+secc+"' limit 1";
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                alcaldia = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
//		db.close();

        return alcaldia;
    }

    private String sacaAlcaldiaBinomio() {
        Set<String> set = new HashSet<String>();
        String alcaldia = null;
        String responsable=spinner2.getSelectedItem().toString();
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "select alcaldia from brigadas where responsable='"+responsable+"' limit 1";
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                alcaldia = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
//		db.close();

        return alcaldia;
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
