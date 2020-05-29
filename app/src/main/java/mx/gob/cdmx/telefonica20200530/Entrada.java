package mx.gob.cdmx.telefonica20200530;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import mx.gob.cdmx.telefonica20200530.R;
import mx.gob.cdmx.telefonica20200530.db.DaoManager;
import mx.gob.cdmx.telefonica20200530.model.Status;
import mx.gob.cdmx.telefonica20200530.model.Usuario;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import static mx.gob.cdmx.telefonica20200530.Nombre.USUARIO;
import static mx.gob.cdmx.telefonica20200530.Nombre.customURL;
import static mx.gob.cdmx.telefonica20200530.Nombre.encuesta;

public class Entrada extends Activity {


    private final String TAG = "Entrada";

    public EditText editUsuario;
    public EditText editPaterno;
    public EditText editLat;
    public EditText editLon;
    Geocoder geocoder;
    public EditText editDireccion;
    public Button Salir;
    public String tablet;
    double latitude;
    double longitude;

    private Utils utils = new Utils();
    private DaoManager daoManager;
    private View mProgressView;
    private View mLoginFormView;
    private Usuario usuario;


    Nombre nom = new Nombre();
    String nombreEncuesta = nom.nombreEncuesta();

    String upLoadServerUriBase = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeBases" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    String upLoadServerUriAudio = "https://opinion.cdmx.gob.mx/cgi-bin/php/recibeAudios" + nombreEncuesta + ".php?encuesta=" + nombreEncuesta + "";
    int serverResponseCode = 0;

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


    ArrayAdapter<String> adapter;
    // public static Spinner spinnerTecnico;
    public static EditText editTecnico;

    ProgressDialog dialog = null;

    int elMaximo;
    public String maximo = "";
    UsuariosSQLiteHelper usdbh;
    private SQLiteDatabase db;
    UsuariosSQLiteHelper2 usdbh2;
    private SQLiteDatabase db2;
    UsuariosSQLiteHelper3 usdbh3;
    private SQLiteDatabase db3;
    public Button buscar;
    public Button ubicar;
    public Button continuar;

    List<Address> addresses;

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df1 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDate1 = df1.format(c.getTime());
    SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
    String formattedDate3 = df3.format(c.getTime());
    SimpleDateFormat df4 = new SimpleDateFormat("yyy-MM-dd");
    String formattedDateFecha = df4.format(c.getTime());
    SimpleDateFormat df5 = new SimpleDateFormat("yyyyMMdd");
    String formattedDateAyer = df5.format(yesterday());

    Date ayer = yesterday();


//	Envio bases = new Envio();

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

//	public String sacaChip(){
//		String deviceId = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
//		tablet=deviceId;	
//		return tablet;
//	}

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


    public void dialogoFecha() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Entrada.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("La fecha del dispositivo es \n" + formattedDateFecha + "\n" + "Es correcto?")
                        .setTitle("IMPORTANTE...!!!").setCancelable(false)
                        .setNegativeButton("CONFIGURAR", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                                System.exit(0);

                            }
                        }).setPositiveButton("CORRECTO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // entrada();

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public void dialogoSeguimiento() {
        // timer.cancel();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Entrada.this.runOnUiThread(new Runnable() {
            public void run() {
                builder.setMessage("Instala la aplicacion de Seguimiento")
                        .setTitle("Aviso").setCancelable(false)
                        .setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                finish();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    private String sacaMaximo() {

        Set<String> set = new HashSet<String>();

        final String F = "File dbfile";


        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);
        db = usdbh.getReadableDatabase();


        String selectQuery = "SELECT count(*) FROM encuestas where fecha='" + formattedDateFecha + "'";

        Log.i(TAG, "Query maximo" + selectQuery);

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

    void deleteDir(File file) {
        if (file.isDirectory())
            for (String child : file.list())
                deleteDir(new File(file, child));
        file.delete(); // delete child file or empty directory
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(Entrada.this,
                    new String[]{android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.INTERNET,
                            android.Manifest.permission.ACCESS_NETWORK_STATE,
                            android.Manifest.permission.READ_PHONE_STATE,
                            android.Manifest.permission.RECORD_AUDIO,
                            android.Manifest.permission.LOCATION_HARDWARE,
                            android.Manifest.permission.MODIFY_AUDIO_SETTINGS,
                            android.Manifest.permission.MODIFY_PHONE_STATE,
                            android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                            Manifest.permission.PROCESS_OUTGOING_CALLS,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.ACCESS_WIFI_STATE},
                    1);
        }
        setContentView(R.layout.entrada);
        Thread.setDefaultUncaughtExceptionHandler(new Crash(this));

        /* Abre la app de seguimiento*/
        try {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("mx.gob.cdmx.seguimiento");
            startActivity(launchIntent);
        } catch (Exception e) {
            String stackTrace = Log.getStackTraceString(e);
            Log.i(TAG, "Lanza Seguimiento" + stackTrace);

            dialogoSeguimiento();

        }

		mLoginFormView = findViewById(R.id.editUsuario);
		mProgressView = findViewById(R.id.login_progress);

        Log.i(TAG, " =====> la latitud: " + latitude);


        elMaximo = Integer.parseInt(sacaMaximo().toString()) + 1;
        String elMaximoFecha = String.valueOf(elMaximo);

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

        Log.i(TAG, " =====> la latitud: " + latitude);
        Log.i(TAG, " =====> la longitud: " + longitude);

        Log.i(TAG, " =====> la fecha: " + formattedDateFecha);
        Log.i(TAG, " =====> ayer: " + formattedDateAyer);

//			dialog = ProgressDialog.show(Entrada.this, "", "Actualizando...", true);

        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(), "Sin conexión",
                    Toast.LENGTH_LONG).show();
            //this.finish();
        } else {


            new UpdateBases().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new UpdateAudios().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//				new UpdateBases().execute();


            // retrazo de 3 segundos para ejecutar la subida de audios
//		            new Handler().postDelayed(new Runnable() {
//		                @Override
//		                public void run() {
//		                	
//		                new UpdateAudios().execute();
//		                }
//		            }, 3000);


        }
        /////////////////////////////////////////////////////////////////////// 7

        if (elMaximoFecha.matches("1")) {

            Log.i(TAG, " =====> El numero inicial: " + elMaximoFecha);

            dialogoFecha();

        }


        continuar = (Button) findViewById(R.id.btnE1);

        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new UsuariosSQLiteHelper(this, "F", null, 1, DATABASE_NAME);
        db = usdbh.getReadableDatabase();

        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getReadableDatabase();

        continuar.setOnClickListener(new OnClickListener() {
            @SuppressWarnings("null")
            @Override
            public void onClick(View v) {
//					// TODO Auto-generated method stub Aqui para generar el archivo vacio// hasta la 112
                final EditText usuario = (EditText) findViewById(R.id.editUsuario);
                final EditText pass = (EditText) findViewById(R.id.editPass);
                File directory;
                File file;
                File sdCard;
                sdCard = Environment.getExternalStorageDirectory();
                FileOutputStream fout = null;
                try {
                    directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");
                    directory.mkdirs();
                    Nombre nom = new Nombre();
                    String nombreE = nom.nombreEncuesta();
                    directory = new File(sdCard.getAbsolutePath() + "/" + nombreE + "-Audio" + formattedDate3);
                    directory.mkdirs();


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                String user = usuario.getText().toString();
                String pssw = pass.getText().toString();

                //valida usuario
                daoManager = new DaoManager(db2);

                if (!utils.verificaConexion(Entrada.this)) {
                    loginLocal();
                } else {
                    loginWS(user, pssw);
                }



					/*sacaUsuario(user, pssw);
					String paso = sacaUsuario(user, pssw).toString();
					
					
					if (paso.matches("1")) {

						Intent intent = new Intent(Entrada.this, MainActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("Nombre", usuario.getText().toString());
						intent.putExtras(bundle);
						startActivity(intent);
						Toast.makeText(v.getContext(), "Acceso OK", Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(v.getContext(), "Usuario y/o Contaseña Incorrectos", Toast.LENGTH_SHORT).show();
					}*/

            }
        });
    }

    public void loginWS(final String user, final String password) {

        showProgress(true);

        RequestParams params = new RequestParams();
        params.put("api", "login");
        params.put("encuesta", encuesta);
        params.put("usuario", user);
        params.put("pass", password);
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

                            JSONObject jsonUser = jsonObject.getJSONObject("data").getJSONObject("user");
                            Type collectionType = new TypeToken<Usuario>() {
                            }.getType();
                            usuario = gson.fromJson(jsonUser.toString(), collectionType);

                            daoManager.deleteClausule(Usuario.class, "id='" + user + "'", null);

                            if (usuario != null) {
                                usuario.setPassword(Integer.valueOf(password));
                                long idUsuario = daoManager.insert(usuario);
                                if (idUsuario > 0) {
                                    //descarga los catalogos
                                    descargaCatalogosWS(user);
                                }
                            }

                        } else {
                            Toast.makeText(Entrada.this, "Usuario y/o Contase?a Incorrectos", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    showProgress(false);
                    Log.e(TAG, e.getMessage());
                    Toast.makeText(Entrada.this, "Usuario y/o Contase?a Incorrectos", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(Entrada.this, "Error de conexion, intente de nuevo", Toast.LENGTH_SHORT).show();

            }
        });
    }

	public void descargaCatalogosWS(final String user){

		RequestParams params = new RequestParams();
		params.put("api", "catalogos");
		params.put("encuesta", encuesta);

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

							//obtiene status
							String jsonStatus = jsonObject.getJSONObject("data").getJSONArray("status").toString();
							Type collectionType = new TypeToken<List<Status>>() {}.getType();
							List<Status> listaStatus = gson.fromJson(jsonStatus, collectionType);

							if(listaStatus != null && !listaStatus.isEmpty()){
								daoManager.delete(Status.class);
								for(Status status :listaStatus ){
									daoManager.insert(status);
								}
							}

							/*
							//obtiene alcaldias
							jsonStatus = jsonObject.getJSONObject("data").getJSONArray("alcaldias").toString();
							collectionType = new TypeToken<List<Alcaldia>>() {}.getType();
							List<Alcaldia> listaAlcaldias = gson.fromJson(jsonStatus, collectionType);

							if(listaAlcaldias != null && !listaAlcaldias.isEmpty()){
								daoManager.delete(Alcaldia.class);
								for(Alcaldia alcaldia : listaAlcaldias ){
									daoManager.insert(alcaldia);
								}
							}

							//obtiene colonia
							jsonStatus = jsonObject.getJSONObject("data").getJSONArray("colonias").toString();
							collectionType = new TypeToken<List<Colonia>>() {}.getType();
							List<Colonia> listaColonias = gson.fromJson(jsonStatus, collectionType);

							if(listaColonias != null && !listaColonias.isEmpty()){
								daoManager.delete(Colonia.class);
								for(Colonia colonia : listaColonias){
									daoManager.insert(colonia);
								}
							}
                            */
							showProgress(false);

							Intent intent = new Intent(Entrada.this, MainActivity.class);
							Bundle bundle = new Bundle();
							bundle.putString("Nombre", String.valueOf(usuario.getId()));
							intent.putExtras(bundle);
							intent.putExtra(USUARIO,usuario);
							startActivity(intent);

							Toast.makeText(Entrada.this, "Acceso OK", Toast.LENGTH_SHORT).show();
							showProgress(false);


						} else {
							Toast.makeText(Entrada.this, "Usuario y/o Contase?a Incorrectos", Toast.LENGTH_SHORT).show();
						}
					}

				} catch (Exception e) {
					showProgress(false);
					Log.e(TAG, e.getMessage());
					Toast.makeText(Entrada.this, "Usuario y/o Contase?a Incorrectos", Toast.LENGTH_SHORT).show();
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

				Toast.makeText(Entrada.this, "Error de conexion, intente de nuevo", Toast.LENGTH_SHORT).show();

			}
		});


	}

    public void loginLocal() {

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

    public void salir() {
        Salir = (Button) findViewById(R.id.btnSalir);
    }

    public void salir(View view) {
        finish();
    }

    public void Sigue(View view) {
        // TODO Auto-generated method stub Aqui para generar el archivo vacio// hasta la 112

        final EditText usuario = (EditText) findViewById(R.id.editUsuario);
        final EditText pass = (EditText) findViewById(R.id.editPass);
        File directory;
        File file;
        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        FileOutputStream fout = null;
        try {
            directory = new File(sdCard.getAbsolutePath() + "/Mis_archivos");
            directory.mkdirs();
            Nombre nom = new Nombre();
            String nombreE = nom.nombreEncuesta();
            directory = new File(sdCard.getAbsolutePath() + "/" + nombreE + "-Audio" + formattedDate3);
            directory.mkdirs();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String user = usuario.getText().toString();
        String pssw = pass.getText().toString();
        sacaUsuario(user, pssw);
        String paso = sacaUsuario(user, pssw).toString();


        if (paso.matches("1")) {

            Intent intent = new Intent(Entrada.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("Nombre", usuario.getText().toString());
            intent.putExtras(bundle);
            startActivity(intent);
            Toast.makeText(this, "Acceso OK", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Usuario y/o Contaseña Incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    private String sacaUsuario(String user, String pass) {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        String DATABASE_NAME = Environment.getExternalStorageDirectory() + "/Mis_archivos/" + nombreEncuesta + "_" + sacaImei() + "";

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh2 = new UsuariosSQLiteHelper2(this, "F", null, 1);
        db2 = usdbh2.getReadableDatabase();
        String selectQuery = "select count(*) from usuarios where usuario='" + user + "' and password='" + pass + "';";
        Cursor cursor = db2.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db2.close();

        return acceso;
    }

    private String sacaLatitud() {
        Set<String> set = new HashSet<String>();
        String acceso = null;
        final String F = "File dbfile";
        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh3 = new UsuariosSQLiteHelper3(this);
        db3 = usdbh3.getReadableDatabase();
        String selectQuery = "select latitud from ubicacion limit 1";
        Cursor cursor = db3.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                acceso = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return acceso;
    }

    public void continua(View view) {

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Nombre", editUsuario.getText().toString());

        startActivity(i);

        editUsuario.setText("");

    }

    //Enviar Base
    public int uploadBase(String sourceFileUri) {

        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

//				             dialog.dismiss(); 
            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathBase + "" + "/" + "20161124_002_359083065132816_1.jpg");
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUriBase);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
                        + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("TAG", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + " http://www.androidexample.com/media/uploads/"
                                    + "20161124_002_359083065132816_1.jpg";

//				                              Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//				                dialog.dismiss();  
                ex.printStackTrace();


                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server " + "error: " + ex.getMessage());

//				                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {

//				                dialog.dismiss();  
                e.printStackTrace();

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception " + "Exception : " + e.getMessage());

//				                Log.e("Upload file to server Exception", "Exception : "
//				                                                 + e.getMessage(), e);  
            }
            return serverResponseCode;

        } // End else block
    }

    class UpdateBases extends AsyncTask<String, Float, String> {

        protected void onPreExecute() {
            super.onPreExecute();

//					dialog = new ProgressDialog(Entrada.this);
//		            dialog.setMessage("Enviando Base de Datos...");
//		            dialog.setTitle("Progreso");
//		            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//		            dialog.setCancelable(false);
//		            dialog.setProgress(0);
//		            dialog.setMax(100);
//		            dialog.show(); //Mostramos el diálogo antes de comenzar
        }


        @Override
        protected String doInBackground(String... params) {


            File sdCard;
            sdCard = Environment.getExternalStorageDirectory();
            final String pathBase = sdCard.getAbsolutePath() + "/Mis_archivos";

            String sDirectorio = pathBase;
            final File f = new File(sDirectorio);
            Log.i(TAG, "lista" + pathBase);

//						final String customURL = "https://encuestas.sies2018.org/coordinacion/audios/";
            final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/bases/";


            Log.i(TAG, " =====> lista 1: " + pathBase);

            File F = new File(pathBase);

            try {

                if (F.exists()) {

                    File[] ficheros = F.listFiles();

                    for (int i = 0; i < ficheros.length; i++) {
                        //Simulamos cierto retraso
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }

                        publishProgress(i / (float) (ficheros.length)); //Actualizamos los valores
                    }


                    String[] s = new String[ficheros.length];
                    String[] t = new String[ficheros.length];
                    for (int x = 0; x < ficheros.length; x++) {
                        Log.i(TAG, " =====> lista: " + ficheros[x].getName());
                        s[x] = pathBase + "/" + nombreEncuesta + "_" + sacaImei();
//								t[x] = ficheros[x].getName();

                        Log.i(TAG, " =====> Nombre del Archivo: " + s[x]);

                        uploadBase(s[x]);
//								 URL u = new URL (customURL+t[x]);
//	     						HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection (); 
//	     						huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD"); 
//	     						huc.connect () ; 
//	     						huc.getResponseCode();
//	     						Log.i(TAG, " =====> Archivo:  lista ==>" + huc.getResponseCode());
//	     						if(huc.getResponseCode()==200 || huc.getResponseCode()==500){
////	     							moveFile(pathFotosN, t[x], pathFotosF);
//	     							Log.i(TAG, " =====> Archivo:  Movido ==>" + t[x] );
//	     						}else{
//	     							Log.i(TAG, " =====> Archivo:  Sin Moverse ==>" + t[x] );
//	     						}
                    }
                    // first parameter is d files second parameter is zip file name

                } else {
                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
                }
                // first parameter is d files second parameter is zip file name

            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.i(TAG, " =====> error zip: " + "_" + e.getMessage());
            }


            return null;
        }


//				protected void onProgressUpdate (Float... valores) {
//		              int p = Math.round(100*valores[0]);
//		              dialog.setProgress(p);
//		          }


        //tomo
        protected void onPostExecute(String date2) {
            super.onPostExecute(date2);
//					dialog.dismiss();

            Toast.makeText(getApplicationContext(), "Archivo Enviado", Toast.LENGTH_LONG).show();

//					dialogo(fecha_envio);

//					correo(date2, sacaChip());

        }

    }

    //Enviar Fotos
    class UpdateAudios extends AsyncTask<String, Float, String> {

        protected void onPreExecute() {
            super.onPreExecute();

//					dialog = new ProgressDialog(CalendarViewFotos.this);
//			        dialog.setMessage("Enviando Fotografías...");
//			        dialog.setTitle("Progreso");
//			        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			        dialog.setCancelable(false);
//			        dialog.setProgress(0);
//			        dialog.setMax(100);
//			        dialog.show(); //Mostramos el diálogo antes de comenzar
        }


        @Override
        protected String doInBackground(String... params) {


            File sdCard;
            sdCard = Environment.getExternalStorageDirectory();
//						final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta+"-Audio"+date2;
            final String pathAudios = sdCard.getAbsolutePath() + "/" + nombreEncuesta + "-Audio" + formattedDate3 + "/";

            String sDirectorio = pathAudios;
            final File f = new File(sDirectorio);
            Log.i(TAG, "lista" + pathAudios);

//						final String customURL = "https://opinion.cdmx.gob.mx/cgi-bin/fotos/programas_sociales/";
            final String customURL = "https://opinion.cdmx.gob.mx/audios/" + nombreEncuesta + "/";

            Log.i(TAG, " =====> URL audios: " + customURL);
            Log.i(TAG, " =====> lista audios 1: " + pathAudios);

            File F = new File(pathAudios);

            try {

                if (F.exists()) {

                    File[] ficheros = F.listFiles();

                    for (int i = 0; i < ficheros.length; i++) {
                        //Simulamos cierto retraso
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                        }

                        publishProgress(i / (float) (ficheros.length)); //Actualizamos los valores
                    }


                    String[] s = new String[ficheros.length];
                    String[] t = new String[ficheros.length];
                    for (int x = 0; x < ficheros.length; x++) {
                        Log.i(TAG, " =====> lista audios: " + ficheros[x].getName());
                        s[x] = pathAudios + "/" + ficheros[x].getName();
                        t[x] = ficheros[x].getName();

//								 uploadFotos(s[x],date2);


                        URL u = new URL(customURL + t[x]);
                        Log.i(TAG, " =====> Archivo Audios custom: " + customURL + t[x]);
                        HttpURLConnection huc = (HttpURLConnection) u.openConnection();
                        huc.setRequestMethod("GET");  //OR  huc.setRequestMethod ("HEAD");
                        huc.connect();
                        huc.getResponseCode();
                        Log.i(TAG, " =====> Archivo:  lista De Audios ==>" + huc.getResponseCode());
                        if (huc.getResponseCode() == 200) {

                            //moveFile(pathFotosN, t[x], pathFotosF);
                            Log.i(TAG, " =====> Archivo:  En el servidor custom no hace nada==>" + t[x]);

                        } else if (huc.getResponseCode() == 404) {

                            uploadAudios(s[x]);
                            Log.i(TAG, " =====> Archivo:  Enviado al servidor custom==>" + t[x]);


                        }

                    }
                    // first parameter is d files second parameter is zip file name

                } else {
                    Log.i(TAG, " =====> lista 2: " + "No existe el directorio");
                }
                // first parameter is d files second parameter is zip file name

            } catch (Exception e) {
                String stackTrace = Log.getStackTraceString(e);
                Log.i("Manda Audios", "Error Manda Audios" + stackTrace);
            }


            return null;
        }


//				protected void onProgressUpdate (Float... valores) {
//			          int p = Math.round(100*valores[0]);
//			          dialog.setProgress(p);
//			      }


        //tomo
        protected void onPostExecute(String date2) {
            super.onPostExecute(date2);
//					dialog.dismiss();

            //	Toast.makeText(CalendarViewFotos.this, "Envío de Fotografias completo ",Toast.LENGTH_LONG).show();

//					correo(date2, prefix);
//					correo(date2, sacaChip());

        }

    }

    public int uploadAudios(String sourceFileUri) {

        File sdCard;
        sdCard = Environment.getExternalStorageDirectory();
        //final String pathFotos = sdCard.getAbsolutePath() + "/"+ nombreEncuesta+"-Audio"+fech;
        final String pathAudios = sdCard.getAbsolutePath() + nombreEncuesta + "-Audio" + formattedDate3 + "/";

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

//			     dialog.dismiss(); 
            Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + pathAudios + "" + "/" + "20161124_002_359083065132816_1.jpg");
            runOnUiThread(new Runnable() {
                public void run() {

                }
            });

            return 0;

        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUriAudio);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\""
                        + lineEnd);

                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    + " http://www.androidexample.com/media/uploads/"
                                    + "20161124_002_359083065132816_1.jpg";

//			                      Toast.makeText(Entrada.this, "File Upload Complete."+msg,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

//			        dialog.dismiss();  
                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//			                messageText.setText("MalformedURLException Exception : check script url.");
//			                Toast.makeText(CalendarViewFotos.this, "MalformedURLException", 
//			                                                    Toast.LENGTH_SHORT).show();
                    }
                });

                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server " + "error: " + ex.getMessage());

//			        Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {

//			        dialog.dismiss();  
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
//			                messageText.setText("Error de Internet");
//			                Toast.makeText(CalendarViewFotos.this, "Error de Internet", 
//			                        Toast.LENGTH_SHORT).show();
                    }
                });
                Log.i(TAG, " =====> archivo:  El Archivo no existe... :" + "Upload file to server Exception " + "Exception : " + e.getMessage());

//			        Log.e("Upload file to server Exception", "Exception : "
//			                                         + e.getMessage(), e);  
            }
            return serverResponseCode;

        } // End else block
    }

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(
					show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});

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
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

}
