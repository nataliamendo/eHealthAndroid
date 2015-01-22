package dd.model;

import java.util.ArrayList;
import java.util.List;


public class PacienteCollection {

	
	private List<Paciente> Paciente = new ArrayList<Paciente>();

	public boolean add(Paciente object) {
		return Paciente.add(object);
	}

	public List<Paciente> getPaciente() {
		return Paciente;
	}

	public void setPaciente(List<Paciente> paciente) {
		Paciente = paciente;
	}

}
