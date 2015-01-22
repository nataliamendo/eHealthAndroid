package dd.dd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.Localizacion;
import dd.model.Mensaje;

public class MLocationActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mlocation);
		
		Button local = (Button) findViewById(R.id.localizacion);
		local.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				(new FetchLocalizacionTask()).execute();
			}
		});

		Button cancelar = (Button) findViewById(R.id.cancelarLocal);
		cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MLocationActivity.this,
						HistorialCActivity.class);

				Bundle bundle = getIntent().getExtras();
				Log.d("DNI Usuario:", bundle.getString("idp"));
				String dni = bundle.getString("idp").toString();

				i.putExtra("idp", dni);

				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mlocation, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public class FetchLocalizacionTask extends AsyncTask<String, Void, Mensaje> {


		@Override
		protected Mensaje doInBackground(String... params) {
			//Cogemos las medidas:
			Mensaje m = new Mensaje();
			
			Bundle bundle = getIntent().getExtras();
			String idhc = bundle.getString("idhc");
			Log.d("UpdateMedidas idHC:", idhc);
			
			EditText longitud = (EditText) findViewById(R.id.longitud);
			EditText latitud = (EditText) findViewById(R.id.latitud);
			
			Localizacion l = new Localizacion();
			l.setIdp(Integer.parseInt(bundle.getString("idp")));
			l.setLatitud(Double.parseDouble(latitud.getText().toString()));
			l.setLongitud(Double.parseDouble(longitud.getText().toString()));
			
			
			try {
				m=EHealthAPI.getInstance(MLocationActivity.this).postLocal(l,
						Integer.parseInt((bundle.getString("idp"))));
			} catch (NumberFormatException | EHealthAndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return m;
		}

		@Override
		protected void onPostExecute(Mensaje result) {
			// Obtenemos todos los textViews del layout:
			
			Intent i = new Intent(MLocationActivity.this,
					HistorialCActivity.class);
			Toast.makeText(getApplicationContext(), result.getMensaje(),
					Toast.LENGTH_LONG).show();

			Bundle bundle = getIntent().getExtras();
			String idp = bundle.getString("idp");
			i.putExtra("idp", idp);

			startActivity(i);
		}

	}
}
