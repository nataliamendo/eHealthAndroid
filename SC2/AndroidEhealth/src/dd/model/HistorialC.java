package dd.model;

import java.math.BigInteger;

public class HistorialC {
	
	private BigInteger idhc;

	private BigInteger temp;
	
	private BigInteger presiona;
	
	private BigInteger pulso;
	
	private String enfermedad;
	
	private BigInteger idpaciente;
	
	
	public BigInteger getIdpaciente() {
		return idpaciente;
	}
	public void setIdpaciente(BigInteger idpaciente) {
		this.idpaciente = idpaciente;
	}
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	public BigInteger getIdhc() {
		return idhc;
	}
	public void setIdhc(BigInteger idhc) {
		this.idhc = idhc;
	}
	public BigInteger getTemp() {
		return temp;
	}
	public void setTemp(BigInteger temp) {
		this.temp = temp;
	}
	public BigInteger getPresiona() {
		return presiona;
	}
	public void setPresiona(BigInteger presiona) {
		this.presiona = presiona;
	}
	public BigInteger getPulso() {
		return pulso;
	}
	public void setPulso(BigInteger pulso) {
		this.pulso = pulso;
	}

}
