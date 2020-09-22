package mx.gob.cdmx.seguimientocovid;

public class Nombre  {

    public static final String APLICACION = "aplicacion";
    public static final String USUARIO = "usuario";
    public static final String FOLIO = "folio";
    public static final String EMAIL = "email";
    public static final String USER_ID = "userId";
    public static final String TOKEN = "token";
    public static final String NOMBRE = "nombre";
    public static final String TIPO_USUARIO = "tipoUsuario";

    public static final String customURL = "https://opinion.cdmx.gob.mx/encuestas/";
    public static final String encuesta = "seguimiento_covid";
    public static final String contactos = "seguimientocovid_contactos";
    public static final String adicionales = "seguimientocovid_adicionales";
//    public static final String USUARIO = "usuario";
    public static final String PADRON = "padron";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String IMEI = "imei";

    public String nombreEncuesta(){

        final String nombreEncuesta = "seguimiento_covid";
        return nombreEncuesta;
    }

    public String nombreDatos(){

        final String nombreEncuesta = "datos_seguimiento_covid";
        return nombreEncuesta;
    }

}	 