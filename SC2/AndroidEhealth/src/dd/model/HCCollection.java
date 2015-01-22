package dd.model;

import java.util.ArrayList;
import java.util.List;

public class HCCollection {
	
	private List<HistorialC> listHC = new ArrayList<HistorialC>();

	public List<HistorialC> getListHC() {
		return listHC;
	}

	public void setListHC(List<HistorialC> listHC) {
		this.listHC = listHC;
	}

	public boolean add(HistorialC arg0) {
		return listHC.add(arg0);
	}

	public boolean remove(Object arg0) {
		return listHC.remove(arg0);
	}
	

}
