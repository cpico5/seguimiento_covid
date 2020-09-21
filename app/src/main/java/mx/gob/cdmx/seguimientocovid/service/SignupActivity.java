package mx.gob.cdmx.seguimientocovid.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import mx.gob.cdmx.seguimientocovid.R;
import mx.gob.cdmx.seguimientocovid.UsuariosSQLiteHelper3;


public class SignupActivity extends AsyncTask<String, Void, String> {

	final static String TAG = "SignupActivity";
	private Context context;
	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";
	String imei_num;
	UsuariosSQLiteHelper3 usdbh3;
	private SQLiteDatabase db3;

    public static final int PERMISSION_REQUEST_CODE = 1;
    private WifiState wifiState;
    private Imei imei;

    private String latitud;
    private String longitud;
	SimpleDateFormat sdFecha = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat sdHora = new SimpleDateFormat("HH:mm:ss");
	String fechaStr = "";
	String horaStr = "";

	private Usuario usuario;

    private Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	Calendar c = Calendar.getInstance();
	SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
	String formattedDateFecha = df3.format(c.getTime());
	SimpleDateFormat df5 = new SimpleDateFormat("HH:mm");
	String formattedDateHora = df5.format(c.getTime());
	SimpleDateFormat ayer = new SimpleDateFormat("yyyyMMdd");
	String formattedDateAyer = ayer.format(yesterday());

	int serverResponseCode = 0;

	static InputStream is2 = null;


	public SignupActivity(Context context) {
		this.context = context;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
    protected void onPreExecute() {

        Intent serviceIntent = new Intent(context, GPSWidgetProvider.GPSWidgetService.class);
//        ContextCompat.startForegroundService(context, serviceIntent );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, serviceIntent );
        } else {
            context.startService(serviceIntent);
        }

        wifiState = new WifiState(context);

        try{
            imei = new Imei(this.context);
            imei_num = imei.getImai().toString();
            Log.i(null, "El chip: " + imei_num);

			usdbh3 = new UsuariosSQLiteHelper3(this.context);
			db3 = usdbh3.getWritableDatabase();
			latitud = "";
			longitud = "";


        }catch(Exception ex){
            ex.printStackTrace();
        }

	}


	@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
	protected String doInBackground(String... arg0) {

		latitud = arg0[0];
		longitud = arg0[1];
		String direccion = arg0[2];

		String newDireccion = direccion.replaceAll("\\s", "_");
		final String laDireccion = newDireccion.trim();

		final String hora = formattedDateHora;
		final String elImei = imei_num;
		final String token = "ZJvI7PooUhZogIarOp8v";
		final String project = this.context.getResources().getString((R.string.app_project));
		final String url;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			url=this.context.getResources().getString(R.string.urls_gps);
		} else {
			url=this.context.getResources().getString(R.string.url_gps);
		}

		if (wifiState.haveNetworkConnection()) {


			fechaStr = sdFecha.format(new Date());
			horaStr = sdHora.format(new Date());



			Calendar calendar = Calendar.getInstance();
			Date horaActual= null;
			Date FechaActual= null;

			Log.i("log_tag","---------->> Hora sacada de la Base: "+sacaHora());
			Log.i("log_tag","---------->> Fecha sacada de la Base: "+sacaFecha());

			if(sacaHora()==null||sacaFecha()==null){

				Log.i("log_tag","---------->> Fecha Base entra en  null: "+ sacaFecha());
			Log.i("log_tag","---------->> Hora Base entra en null: "+ sacaHora());


			try {

				Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

				RequestParams params = new RequestParams();
				params.put("latitude", latitud);
				params.put("longitude", longitud);
				params.put("data", laDireccion);
				params.put("imei", imei_num);
				params.put("token", token);
				params.put("project", project);

				AsyncHttpClient client = new AsyncHttpClient();
				RequestHandle requestHandle = client.post(url + "/api/location/set", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

					@Override
					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

						Log.d(TAG, "pimc -----------> " + new String(responseBody));
						String json = new String(responseBody);
						try {

							Log.i("log_tag", "Borrando Ubicacion anterior");

							Log.i("log_tag", "Insertando ubicacion actual");

							//ejecuta el background

							if(usuario !=  null){
								String[] enviado = {"0"};

								//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
								//      enviado,null,null,null,null);

							}

							if(json != null && !json.equals("")){

								//cacha el msj del alerta

							}


						} catch (Exception ex) {
							Log.e(TAG, ex.getMessage());
						}


					}

					@Override
					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
						if (statusCode != 200) {
							Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
							if(responseBody != null){
								Log.d(TAG, "e2lira -----------> " + new String(responseBody));
								String json = new String(responseBody);
							}

						}
					}
				});




				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

				HttpResponse response2 = httpclient.execute(httppost);
				HttpEntity entity2 = response2.getEntity();
				is2 = entity2.getContent();

				Log.i("log_tag", "connection success ");
				Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);


				if (db3 != null) {

					db3.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
					Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
					db3.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
					Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");


					if(usuario !=  null){
                        String[] enviado = {"0"};

					    //encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
                          //      enviado,null,null,null,null);

					}

				} else {
					Log.i("log_tag", "mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
				}


			} catch (Exception e) {
				String stackTrace = Log.getStackTraceString(e);
				Log.i("log_tag", "connection error" + stackTrace);
				Log.i("log_tag", "Error in http connection " + e.toString());

			}

			}else {


				Calendar c = Calendar.getInstance();

				SimpleDateFormat df1 = new SimpleDateFormat("yyyMMdd");
				String formattedDate1 = df1.format(c.getTime());

				SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
				String formattedDate2 = df2.format(c.getTime());

				SimpleDateFormat df3 = new SimpleDateFormat("yyyMMdd");
				String formattedDate3 = df3.format(c.getTime());

				SimpleDateFormat df4 = new SimpleDateFormat("HH:mm:ss a");
				String formattedDate4 = df4.format(c.getTime());

				SimpleDateFormat df5 = new SimpleDateFormat("HH:mm:ss");
				String formattedDate5 = df5.format(c.getTime());





				//Obtiene la Fecha
				try {
					FechaActual = new SimpleDateFormat("yyyy/MM/dd").parse(fechaStr);
					Log.i("log_tag","---------->> La Fecha actual: "+formattedDate3);
					Log.i("log_tag","---------->> La Fecha base: "+sacaFecha());
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Date horaBase = null;
				SimpleDateFormat format = new SimpleDateFormat("HH:mm");
				try {
					horaBase = format.parse(sacaHora());
					Log.i("log_tag","---------->> Hora sacada de la Base: "+horaBase);
				} catch (ParseException e) {
					e.printStackTrace();
					Log.i("log_tag","---------->> Error Hora sacada de la Base: "+horaBase);
				}

				try {
					horaActual = new SimpleDateFormat("HH:mm").parse(horaStr);
					Log.i("log_tag","---------->> Hora actual: "+horaActual);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				calendar.setTime(horaBase);
				calendar.add(Calendar.MINUTE, 2);
				Date horaActualizacion = calendar.getTime();
				Log.i("log_tag","---------->> Hora de la actualización: "+horaActualizacion);



				if(formattedDate3.equals(sacaFecha())){

					Log.i("log_tag","---------->> SON IGUALES ");



					//valida el tiempo
					if(horaActual.after(horaActualizacion)){
						Log.i("log_tag","---------->> Hora actualización: "+ "esta despues  Si mando");

						try {

							Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

							RequestParams params = new RequestParams();
							params.put("latitude", latitud);
							params.put("longitude", longitud);
							params.put("data", laDireccion);
							params.put("imei", imei_num);
							params.put("token", token);
							params.put("project", project);

							AsyncHttpClient client = new AsyncHttpClient();
							RequestHandle requestHandle = client.post(url + "/api/location/set", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

								@Override
								public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

									Log.d(TAG, "pimc ---------->> " + new String(responseBody));
									String json = new String(responseBody);
									try {

										Log.i("log_tag", "---------->> Borrando Ubicacion anterior");

										Log.i("log_tag", "---------->> Insertando ubicacion actual");

										//ejecuta el background

										if(usuario !=  null){
											String[] enviado = {"0"};

											//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
											//      enviado,null,null,null,null);

										}

										if(json != null && !json.equals("")){

											//cacha el msj del alerta

										}


									} catch (Exception ex) {
										Log.e(TAG, ex.getMessage());
									}


								}

								@Override
								public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
									if (statusCode != 200) {
										Log.e(TAG, "existe un error en la conexión ---------->>  " + error.getMessage());
										if(responseBody != null){
											Log.d(TAG, "e2lira ---------->>  " + new String(responseBody));
											String json = new String(responseBody);
										}

									}
								}
							});




							HttpClient httpclient = new DefaultHttpClient();
							HttpPost httppost = new HttpPost(url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

							HttpResponse response2 = httpclient.execute(httppost);
							HttpEntity entity2 = response2.getEntity();
							is2 = entity2.getContent();

							Log.i("log_tag", "---------->> connection success ");
							Log.i("log_tag", "---------->> connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);


							if (db3 != null) {

								db3.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
								Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
								db3.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
								Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");


								if(usuario !=  null){
									String[] enviado = {"0"};

									//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
									//      enviado,null,null,null,null);

								}

							} else {
								Log.i("log_tag", " ---------->> mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
							}


						} catch (Exception e) {
							String stackTrace = Log.getStackTraceString(e);
							Log.i("log_tag", "---------->> connection error" + stackTrace);
							Log.i("log_tag", "---------->> Error in http connection " + e.toString());

						}

					}else{
						Log.i("log_tag","---------->> Hora actualización: "+ "esta antes NO mando");
					}




				}else{
					Log.i("log_tag","---------->> SON DIFERENTES ");


					try {

						Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

						RequestParams params = new RequestParams();
						params.put("latitude", latitud);
						params.put("longitude", longitud);
						params.put("data", laDireccion);
						params.put("imei", imei_num);
						params.put("token", token);
						params.put("project", project);

						AsyncHttpClient client = new AsyncHttpClient();
						RequestHandle requestHandle = client.post(url + "/api/location/set", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

							@Override
							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

								Log.d(TAG, "pimc -----------> " + new String(responseBody));
								String json = new String(responseBody);
								try {

									Log.i("log_tag", "Borrando Ubicacion anterior");

									Log.i("log_tag", "Insertando ubicacion actual");

									//ejecuta el background

									if(usuario !=  null){
										String[] enviado = {"0"};

										//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
										//      enviado,null,null,null,null);

									}

									if(json != null && !json.equals("")){

										//cacha el msj del alerta

									}


								} catch (Exception ex) {
									Log.e(TAG, ex.getMessage());
								}


							}

							@Override
							public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
								if (statusCode != 200) {
									Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
									if(responseBody != null){
										Log.d(TAG, "e2lira -----------> " + new String(responseBody));
										String json = new String(responseBody);
									}

								}
							}
						});




						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);

						HttpResponse response2 = httpclient.execute(httppost);
						HttpEntity entity2 = response2.getEntity();
						is2 = entity2.getContent();

						Log.i("log_tag", "connection success ");
						Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);


						if (db3 != null) {

							db3.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
							Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
							db3.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
							Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");


							if(usuario !=  null){
								String[] enviado = {"0"};

								//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
								//      enviado,null,null,null,null);

							}

						} else {
							Log.i("log_tag", "mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
						}


					} catch (Exception e) {
						String stackTrace = Log.getStackTraceString(e);
						Log.i("log_tag", "connection error" + stackTrace);
						Log.i("log_tag", "Error in http connection " + e.toString());

					}



				}


//
//
//				//valida el tiempo
//				if(horaActual.after(horaActualizacion)){
//					Log.i("log_tag","---------->> Hora actualización: "+ "esta despues  Si mando");
//
//					try {
//
//						Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//						RequestParams params = new RequestParams();
//						params.put("latitude", latitud);
//						params.put("longitude", longitud);
//						params.put("data", laDireccion);
//						params.put("imei", imei_num);
//						params.put("token", token);
//						params.put("project", project);
//
//						AsyncHttpClient client = new AsyncHttpClient();
//						RequestHandle requestHandle = client.post(url + "/api/location/set", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
//
//							@Override
//							public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//								Log.d(TAG, "pimc ---------->> " + new String(responseBody));
//								String json = new String(responseBody);
//								try {
//
//									Log.i("log_tag", "---------->> Borrando Ubicacion anterior");
//
//									Log.i("log_tag", "---------->> Insertando ubicacion actual");
//
//									//ejecuta el background
//
//									if(usuario !=  null){
//										String[] enviado = {"0"};
//
//										//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
//										//      enviado,null,null,null,null);
//
//									}
//
//									if(json != null && !json.equals("")){
//
//										//cacha el msj del alerta
//
//									}
//
//
//								} catch (Exception ex) {
//									Log.e(TAG, ex.getMessage());
//								}
//
//
//							}
//
//							@Override
//							public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//								if (statusCode != 200) {
//									Log.e(TAG, "existe un error en la conexión ---------->>  " + error.getMessage());
//									if(responseBody != null){
//										Log.d(TAG, "e2lira ---------->>  " + new String(responseBody));
//										String json = new String(responseBody);
//									}
//
//								}
//							}
//						});
//
//
//
//
//						HttpClient httpclient = new DefaultHttpClient();
//						HttpPost httppost = new HttpPost(url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//						HttpResponse response2 = httpclient.execute(httppost);
//						HttpEntity entity2 = response2.getEntity();
//						is2 = entity2.getContent();
//
//						Log.i("log_tag", "---------->> connection success ");
//						Log.i("log_tag", "---------->> connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//
//						if (db != null) {
//
//							db.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
//							Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
//							db.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//							Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//
//
//							if(usuario !=  null){
//								String[] enviado = {"0"};
//
//								//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
//								//      enviado,null,null,null,null);
//
//							}
//
//						} else {
//							Log.i("log_tag", " ---------->> mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//						}
//
//
//					} catch (Exception e) {
//						String stackTrace = Log.getStackTraceString(e);
//						Log.i("log_tag", "---------->> connection error" + stackTrace);
//						Log.i("log_tag", "---------->> Error in http connection " + e.toString());
//
//					}
//
//				}else{
//					Log.i("log_tag","---------->> Hora actualización: "+ "esta antes NO mando");
//				}

			}



//			Log.i("log_tag","---------->> Fecha Base: "+ sacaFecha());
//			Log.i("log_tag","---------->> Hora Base: "+ sacaHora());


//			try {
//
//				Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//				RequestParams params = new RequestParams();
//				params.put("latitude", latitud);
//				params.put("longitude", longitud);
//				params.put("data", laDireccion);
//				params.put("imei", imei_num);
//				params.put("token", token);
//				params.put("project", project);
//
//				AsyncHttpClient client = new AsyncHttpClient();
//				RequestHandle requestHandle = client.post(url + "/api/location/set", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {
//
//					@Override
//					public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//
//						Log.d(TAG, "pimc -----------> " + new String(responseBody));
//						String json = new String(responseBody);
//						try {
//
//							Log.i("log_tag", "Borrando Ubicacion anterior");
//
//							Log.i("log_tag", "Insertando ubicacion actual");
//
//							//ejecuta el background
//
//							if(usuario !=  null){
//								String[] enviado = {"0"};
//
//								//encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
//								//      enviado,null,null,null,null);
//
//							}
//
//							if(json != null && !json.equals("")){
//
//								//cacha el msj del alerta
//
//							}
//
//
//						} catch (Exception ex) {
//							Log.e(TAG, ex.getMessage());
//						}
//
//
//					}
//
//					@Override
//					public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//						if (statusCode != 200) {
//							Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
//							if(responseBody != null){
//								Log.d(TAG, "e2lira -----------> " + new String(responseBody));
//								String json = new String(responseBody);
//							}
//
//						}
//					}
//				});
//
//
//
//
//				HttpClient httpclient = new DefaultHttpClient();
//				HttpPost httppost = new HttpPost(url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//				HttpResponse response2 = httpclient.execute(httppost);
//				HttpEntity entity2 = response2.getEntity();
//				is2 = entity2.getContent();
//
//				Log.i("log_tag", "connection success ");
//				Log.i("log_tag", "connection: " + url + "/api/location/set?latitude=" + latitud + "&longitude=" + longitud + "&data=" + laDireccion + "&imei=" + elImei + "&token=" + token + "&project=" + project);
//
//
//				if (db != null) {
//
//					db.execSQL("DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
//					Log.i("log_tag", "connection: " + "DELETE FROM ubicacion where fecha='" + formattedDateAyer + "'");
//					db.execSQL("INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//					Log.i("log_tag", "connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//
//
//					if(usuario !=  null){
//                        String[] enviado = {"0"};
//
//					    //encuesta = (Encuesta) objectRepository.findOne(Encuesta.class,"enviado=?",
//                          //      enviado,null,null,null,null);
//
//					}
//
//				} else {
//					Log.i("log_tag", "mal connection: " + "INSERT INTO ubicacion (fecha,hora,latitud,longitud,direccion) values (" + "'" + formattedDateFecha + "'" + "," + "'" + hora + "'" + "," + "'" + latitud + "'" + "," + "'" + longitud + "'" + "," + "'" + laDireccion + "');");
//				}
//
//
//			} catch (Exception e) {
//				String stackTrace = Log.getStackTraceString(e);
//				Log.i("log_tag", "connection error" + stackTrace);
//				Log.i("log_tag", "Error in http connection " + e.toString());
//
//			}
		}

		return null;

	}

	@TargetApi(Build.VERSION_CODES.O)
	@RequiresApi(api = Build.VERSION_CODES.O)
	private void cargaEncuestaWS(){

		String json = "";

		try{

			Gson gson  = new Gson();
			//Type collectionType = new TypeToken<Encuesta>() { }.getType();
			//json = gson.toJson(encuesta,collectionType);

			imei = new Imei(context);

		} catch (Exception ex){
			Log.d(TAG, "pimc -----------> " + ex.getMessage());
		}

		Log.d(TAG, "Token JWT: " + usuario.getToken());

		RequestParams params = new RequestParams();
		params.put("data", json);

		params.put("latitude", latitud);
		params.put("longitude", longitud);
		params.put("project", "SituacionCalle");
		params.put("imei", imei.getImai().toString());

		AsyncHttpClient client = new AsyncHttpClient();
		client.setSSLSocketFactory(MySSLSocketFactory.getFixedSocketFactory());
		client.addHeader("Authorization", "Bearer " + usuario.getToken());

		RequestHandle requestHandle = client.post(context.getResources().getString(R.string.url_aplicacion) + "/api/situacion_calle/people", params, new AsyncHttpResponseHandler(Looper.getMainLooper()) {

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

				Log.d(TAG, "pimc -----------> Realizo la conexión para subir los datos");
				Log.d(TAG, "pimc -----------> " + new String(responseBody));

			/*	try {

					String json = new String(responseBody);
					JSONObject jsonObject = new JSONObject(json);
					Log.d(TAG, "pimc -----------> Data: " + jsonObject.get("data"));
					String idReporte = jsonObject.getJSONObject("data").getString("id");

					encuesta.setIdEncuesta(Integer.valueOf(idReporte));
					encuesta.setEnviado(1);

					String[] idEnc = {String.valueOf(encuesta.getId())};
					objectRepository.update(encuesta,idEnc);

				} catch (JSONException e){
					Log.e(TAG, e.getMessage());
				}  */
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				if (statusCode != 200) {
					Log.e(TAG, "existe un error en la conexión -----> " + error.getMessage());
					if(responseBody != null)
						Log.d(TAG, "e2lira -----------> " + new String(responseBody));

				}

			}
		});
	}


	@Override
	protected void onPostExecute(String result) {
		//db.close();
		System.gc();
    }


	private String sacaFecha() {
		Set<String> set = new HashSet<String>();
		String fecha = null;
		final String F = "File dbfile";
//// Abrimos la base de datos 'DBUsuarios' en modo escritura
//		usdbh = new UsuariosSQLiteHelper(this);
//		db = usdbh.getReadableDatabase();
		String selectQuery = "select fecha from ubicacion order by id desc limit 1";
		Cursor cursor = db3.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				fecha = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		cursor.close();
// db.close();

		return fecha;
	}

	private String sacaHora() {
		Set<String> set = new HashSet<String>();
		String hora = null;
		final String F = "File dbfile";
//// Abrimos la base de datos 'DBUsuarios' en modo escritura
//		usdbh = new UsuariosSQLiteHelper(this);
//		db = usdbh.getReadableDatabase();
		String selectQuery = "select hora from ubicacion order by id desc limit 1";
		Cursor cursor = db3.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				hora = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		cursor.close();
// db.close();

		return hora;
	}


}
