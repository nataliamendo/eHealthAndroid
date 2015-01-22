package dd.model;

public class CalcDistancia {
	
	public double lat;
	public double lon;
	
	//Bellvitge
	private double lat_b = 41.25 ;
	private double lon_b = 2.10 ;
	
	//Quiron
	private double lat_c = 41.4150 ;
	private double lon_c = 2.1383 ;
	
	//Hospital del Mar
	private double lat_d = 41.3829 ;
	private double lon_d = 2.19 ;
	
	public double getDistanceb(double lat_a,double lng_a){
		  int Radius = 6371000; //Radio de la tierra
		  double lat1 = lat_a;
		  double lat2 = this.lat_b;
		  double lon1 = lng_a;
		  double lon2 = this.lon_b;
		  double dLat = Math.toRadians(lat2-lat1);
		  double dLon = Math.toRadians(lon2-lon1);
		  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
		  double cb = 2 * Math.asin(Math.sqrt(a));
		  return (double) (Radius * cb);  

		 }
	
	public double getDistancec(double lat_a,double lng_a){
		  int Radius = 6371000; //Radio de la tierra
		  double lat1 = lat_a ;
		  double lat2 = this.lat_c;
		  double lon1 = lng_a;
		  double lon2 = this.lon_c;
		  double dLat = Math.toRadians(lat2-lat1);
		  double dLon = Math.toRadians(lon2-lon1);
		  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
		  double cb = 2 * Math.asin(Math.sqrt(a));
		  return (double) (Radius * cb);  

		 }
	public double getDistanced(double lat_a,double lng_a){
		  int Radius = 6371000; //Radio de la tierra
		  double lat1 = lat_a;
		  double lat2 = this.lat_d;
		  double lon1 = lng_a;
		  double lon2 = this.lon_d;
		  double dLat = Math.toRadians(lat2-lat1);
		  double dLon = Math.toRadians(lon2-lon1);
		  double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon /2) * Math.sin(dLon/2);
		  double cb = 2 * Math.asin(Math.sqrt(a));
		  return (double) (Radius * cb);  

		 }
	
	
	public double getDistance(double ret, double ret2){
		  if (ret<ret2)
		  {
			  return ret;
		  }
		  else
			  return ret2;
		 }

}
