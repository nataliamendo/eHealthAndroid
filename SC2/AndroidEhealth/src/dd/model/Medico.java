package dd.model;


public class Medico {

	private int idmedico;

	private String nombreM;

	private String dnim;

	private String telefm;

	private String pass;

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

	public int getIdmedico() {
		return idmedico;
	}

	public void setIdmedico(int idmedico) {
		this.idmedico = idmedico;
	}

	public String getNombreM() {
		return nombreM;
	}

	public void setNombreM(String nombreM) {
		this.nombreM = nombreM;
	}

	public String getDnim() {
		return dnim;
	}

	public void setDnim(String dnim) {
		this.dnim = dnim;
	}

	public String getTelefm() {
		return telefm;
	}

	public void setTelefm(String telefm) {
		this.telefm = telefm;
	}

}
