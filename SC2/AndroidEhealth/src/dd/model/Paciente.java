package dd.model;

public class Paciente {
	private int idp;

	private String nombrep;

	private String dni;

	private String telef;

	private String pass;

	HistorialC historialc;

	private String salt;

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public HistorialC getHistorialc() {
		return historialc;
	}

	public void setHistorialc(HistorialC historialc) {
		this.historialc = historialc;
	}

	// coordenadas donde se encuentra el paciente
	// Geocoding ObjGeocod=new Geocoding();
	/*
	 * private int xp; private int yp;
	 */

	public int getIdp() {
		return idp;
	}

	public void setIdp(int idp) {
		this.idp = idp;
	}

	public String getNombrep() {
		return nombrep;
	}

	public void setNombrep(String nombrep) {
		this.nombrep = nombrep;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelef() {
		return telef;
	}

	public void setTelef(String telef) {
		this.telef = telef;
	}

}
