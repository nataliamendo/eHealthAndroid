package dd.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigInteger;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EHealthAPI {
	private final static String TAG = EHealthAPI.class.getName();
	private static EHealthAPI instance = null;
	private String url = "http://10.189.55.55:8080/ehealth/paciente/";

	public EHealthAPI(Context context) throws IOException,
			EHealthAndroidException {
		super();

	}

	public final static EHealthAPI getInstance(Context context)
			throws EHealthAndroidException {
		if (instance == null)
			try {
				instance = new EHealthAPI(context);// context es la actividad,
													// para recuperar valores
													// del fichero conf.
			} catch (IOException e) {
				throw new EHealthAndroidException(
						"Can't load configuration file");
			}
		return instance;
	}

	//Obtener certificado de la API
	public void autenticarAPI ()throws EHealthAndroidException {

		Log.d(TAG, "autenticarAPI");

		Gson gson = new Gson();
		String urlString = url + "/autenticaAPI";
		HttpClient httpClient = WebServiceUtils.getHttpClient();
	

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			Certificate cert = gson.fromJson(reader, Certificate.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}


	}

	
	// *Obtener historialC a partir de dni
	public HistorialC getHistorialCByPacienteDNI(String idp)
			throws EHealthAndroidException {

		Log.d(TAG, "getHistorialCByPacienteName");

		HistorialC hc = new HistorialC();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + idp + "/encrypt";
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			hc = gson.fromJson(reader, HistorialC.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		// DESCIFRAMOS EL CONTENIDO:
		HistorialC hcd = new HistorialC();
		hcd = this.desencryptedHistorialCByIdp(hc.getIdpaciente().intValue(),
				hc);

		return hcd;
	}

	// *Obtener historialC a partir de id
	public HistorialC getHistorialCByHCId(int idhc)
			throws EHealthAndroidException {

		Log.d(TAG, "getHistorialCByHCId");

		HistorialC hc = new HistorialC();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/idhc/" + idhc + "/encrypt";
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			hc = gson.fromJson(reader, HistorialC.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		// DESCIFRAMOS EL CONTENIDO:
		HistorialC hcd = new HistorialC();
		hcd = this.desencryptedHistorialCByIdp(hc.getIdpaciente().intValue(),
				hc);

		return hcd;
	}

	public HistorialC desencryptedHistorialC(String idp, HistorialC hce) {
		Log.d(TAG, "desencryptedHistorialC");

		SecurityDB s = new SecurityDB();

		HistorialC hcd = new HistorialC();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "securityP/" + idp;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			s = gson.fromJson(reader, SecurityDB.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		RSA rsa = new RSA(32);
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());

		hcd.setPresiona(rsa.decrypt(hce.getPresiona()));
		hcd.setEnfermedad(hce.getEnfermedad());
		hcd.setPulso(rsa.decrypt(hce.getPulso()));
		hcd.setTemp(rsa.decrypt(hce.getTemp()));

		return hcd;
	}

	public HistorialC desencryptedHistorialCByIdp(int idp, HistorialC hce) {
		Log.d(TAG, "desencryptedHistorialCByIdp");

		SecurityDB s = new SecurityDB();
		try {
			s = this.getSecurityPById(idp);
		} catch (EHealthAndroidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HistorialC hcd = new HistorialC();

		RSA rsa = new RSA(32);
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());

		hcd.setPresiona(rsa.decrypt(hce.getPresiona()));
		hcd.setEnfermedad(hce.getEnfermedad());
		hcd.setPulso(rsa.decrypt(hce.getPulso()));
		hcd.setTemp(rsa.decrypt(hce.getTemp()));

		return hcd;
	}

	public HistorialC encryptedHistorialCByIdp(int idp, HistorialC hce) {
		Log.d(TAG, "encryptedHistorialCByIdp");

		SecurityDB s = new SecurityDB();
		try {
			s = this.getSecurityPById(idp);
		} catch (EHealthAndroidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HistorialC hcd = new HistorialC();

		RSA rsa = new RSA(32);
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());

		hcd.setPresiona(rsa.encrypt(hce.getPresiona()));
		hcd.setEnfermedad(hce.getEnfermedad());
		hcd.setPulso(rsa.encrypt(hce.getPulso()));
		hcd.setTemp(rsa.encrypt(hce.getTemp()));

		return hcd;
	}
	
	public Localizacion encryptedLocal(int idp, Localizacion ld) {
		Log.d(TAG, "encryptedLocal");

		SecurityDB s = new SecurityDB();
		try {
			s = this.getSecurityPById(idp);
		} catch (EHealthAndroidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Localizacion le = new Localizacion();

		RSA rsa = new RSA(32);
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());

	//	le.setLatitud(rsa.encrypt(BigInteger.valueOf(Integer.parseInt(""+ld.getLatitud()*1E6))));
	

		return le;
	}
	public Paciente loginPaciente(String nombrep)
			throws EHealthAndroidException {

		Log.d(TAG, "loginPaciente");

		Paciente p = new Paciente();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/login/" + nombrep;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {

			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			p = gson.fromJson(reader, Paciente.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		return p;
	}

	public Medico loginMedico(String nombrem) throws EHealthAndroidException {

		Log.d(TAG, "loginMedico");

		Medico m = new Medico();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/loginm/" + nombrem;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {

			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			m = gson.fromJson(reader, Medico.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		return m;
	}

	public void putMedidas(HistorialC hcu, int idhc, int idp) {
		Log.d(TAG, "putMedidas");
		
		//Ciframos los datos con las claves del usuario:
		
		HistorialC hce = this.encryptedHistorialCByIdp(idp, hcu);
		// * * * * GoogleSON * * * *
		String urlString = url + "/actualizar/"+idhc+"/"+idp;

		HttpPut httpPost = new HttpPut(urlString);
		httpPost.setHeader("Content-Type",
				"application/vnd.ehealth.hc+json; charset=utf-8");

		Gson gson = new Gson();

		try {
			StringEntity stringE = new StringEntity(gson.toJson(hce));
			httpPost.setEntity(stringE);

			Log.d("GSON:", gson.toJson(hce));
		} catch (Exception e) {
			Log.i("json parse", "Error al parsear. ");
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// If successful.
		}

	}
	
	public void putPassP(Paciente p) {
		Log.d(TAG, "putPassP");
		
		
		
		// * * * * GoogleSON * * * *
		String urlString = url + "/actualizarPassP";

		HttpPut httpPost = new HttpPut(urlString);
		httpPost.setHeader("Content-Type",
				"application/vnd.ehealth.paciente+json; charset=utf-8");

		Gson gson = new Gson();

		try {
			StringEntity stringE = new StringEntity(gson.toJson(p));
			httpPost.setEntity(stringE);

			Log.d("GSON:", gson.toJson(p));
		} catch (Exception e) {
			Log.i("json parse", "Error al parsear. ");
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// If successful.
		}

	}
	
	public void putPassM(Medico m) {
		Log.d(TAG, "putPassM");
		
		
		
		// * * * * GoogleSON * * * *
		String urlString = url + "/actualizarPassM";

		HttpPut httpPost = new HttpPut(urlString);
		httpPost.setHeader("Content-Type",
				"application/vnd.ehealth.medico+json; charset=utf-8");

		Gson gson = new Gson();

		try {
			StringEntity stringE = new StringEntity(gson.toJson(m));
			httpPost.setEntity(stringE);

			Log.d("GSON:", gson.toJson(m));
		} catch (Exception e) {
			Log.i("json parse", "Error al parsear. ");
		}

		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// If successful.
		}

	}
	
	public Mensaje postLocal(Localizacion l, int idp) {
//		Log.d(TAG, "postLocal");
//		
//		//Ciframos los datos con las claves del usuario:
//		
//		//HistorialC hce = this.encryptLocal(idp, l);
//		// * * * * GoogleSON * * * *
//		String urlString = url + "/localiza/"+idp;
//		
//		Mensaje m = new Mensaje();
//
//		HttpPut httpPost = new HttpPut(urlString);
//		httpPost.setHeader("Content-Type",
//				"application/vnd.ehealth.local+json; charset=utf-8");
//
//		Gson gson = new Gson();
//
//		try {
//			StringEntity stringE = new StringEntity(gson.toJson(l));
//			httpPost.setEntity(stringE);
//
//			Log.d("GSON:", gson.toJson(l));
//		} catch (Exception e) {
//			Log.i("json parse", "Error al parsear. ");
//		}
//
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpResponse httpResponse = null;
//		try {
//			httpResponse = httpClient.execute(httpPost);
//			HttpEntity entity = httpResponse.getEntity();
//			Reader reader = new InputStreamReader(entity.getContent());
//			Log.i("Contenido_Mensjae_Hospi:", reader.toString());
//
//			m = gson.fromJson(reader, Mensaje.class);
//			Log.i("Contenido_Mensjae_Hospi:", m.getMensaje());
//		} catch (Exception e) {
//			Log.i("json array",
//					"While getting server response server generate error. ");
//		}
//
//		return m;
		CalcDistancia cd = new CalcDistancia();
		double lat = l.getLatitud();
		double lon = l.getLongitud();
		double ret;
		String nom;
		
		double retBellvitge = cd.getDistanceb(lat , lon);
		ret = retBellvitge;
		double retQuiron = cd.getDistancec(lat, lon);
		double ret2 = cd.getDistance(ret, retQuiron);
		if (ret == ret2){
			 nom = "Bellvitge";
			 ret2=ret;
		}
		else 
			nom="Hospital Quiron";
		ret= retQuiron;
		
		double retHMar = cd.getDistanced(lat, lon);
		double ret3 = cd.getDistance(ret, retHMar);
		if (ret3 == ret){
			 nom = nom;
		}
		else 
			nom="Hospital Del Mar";
		

		System.out.println("Dist bellvitge " + retBellvitge);
		System.out.println("Dist Quiron " + retQuiron);
		System.out.println("Dist Mar " + retHMar);
		
		String respuesta="Tu hospital más cercano es " + nom;
		
		Mensaje m = new Mensaje();
		m.setMensaje(respuesta);
		return m;

	}

	public HCCollection getListHCofMedico(String dni)
			throws EHealthAndroidException {

		Log.d(TAG, "getListHCofMedico");

		HCCollection devels = new HCCollection();

		// Este ArrayList de clase Developers para cargarlo de la respuesta del
		// server
		ArrayList<HistorialC> develsA = new ArrayList<HistorialC>();

		// ArrayList para parsear con el GSON
		java.lang.reflect.Type arrayListType = new TypeToken<ArrayList<HistorialC>>() {
		}.getType();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/listhc/" + dni;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			develsA = gson.fromJson(reader, arrayListType);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		int i = 0;
		List<HistorialC> listp = new ArrayList<HistorialC>();
		while (i < develsA.size()) {
			HistorialC p = develsA.get(i);
			// Desciframos:
			SecurityDB s = this.getSecurityPById(p.getIdpaciente().intValue());
			RSA rsa = new RSA(32);
			rsa.setD(s.getD());
			rsa.setE(s.getE());
			rsa.setN(s.getN());

			p.setIdhc(rsa.decrypt(p.getIdhc()));

			listp.add(p);
			i++;
		}

		devels.setListHC(listp);

		return devels;
	}

	public SecurityDB getSecurityPById(int idp) throws EHealthAndroidException {

		Log.d(TAG, "getSecurityPById");

		SecurityDB s = new SecurityDB();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/securityPid/" + idp;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			s = gson.fromJson(reader, SecurityDB.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		return s;
	}

	public HistorialC getHCByIdhcByMedico(String dnim, int idhc) {

		Log.d(TAG, "getHCByIdhcByMedico");

		HistorialC hc = new HistorialC();

		// * * * * GoogleSON * * * * *
		Gson gson = new Gson();
		String urlString = url + "/autenticar/" + dnim + "/" + idhc;
		HttpClient httpClient = WebServiceUtils.getHttpClient();

		try {
			HttpResponse response = httpClient.execute(new HttpGet(urlString));
			HttpEntity entity = response.getEntity();
			Reader reader = new InputStreamReader(entity.getContent());
			Log.i("Contenido_M:", reader.toString());

			hc = gson.fromJson(reader, HistorialC.class);

		} catch (Exception e) {
			Log.i("json array",
					"While getting server response server generate error. ");
		}

		HistorialC hcd = new HistorialC();
		// DESCIFRAMOS EL CONTENIDO:
		if (hc.getEnfermedad() != null) {

			hcd = this.desencryptedHistorialCByIdp(hc.getIdpaciente()
					.intValue(), hc);
		}

		else {
			hcd.setEnfermedad("NO TIENES PERMISO, NO HAY TICKET");
			int y = 0;
			hcd.setIdhc(BigInteger.valueOf(y));
			hcd.setPresiona(BigInteger.valueOf(y));
			hcd.setPulso(BigInteger.valueOf(y));
			hcd.setTemp(BigInteger.valueOf(y));
			
		}

		return hcd;

	}

}
