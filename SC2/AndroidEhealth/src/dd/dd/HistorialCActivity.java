package dd.dd;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.HistorialC;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class HistorialCActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial_c);
		
		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();
		
		Button medir = (Button) findViewById(R.id.medidas);
		medir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Abre el layout de hacer las medidas:
				Intent i = new Intent(HistorialCActivity.this,
						MedidasActivity.class);
				
				Bundle bundle = getIntent().getExtras();
				Log.d("DNI Usuario:", bundle.getString("idp"));
				String idp=bundle.getString("idp").toString();
				

				i.putExtra("idp", idp);
				i.putExtra("idhc", idp);
				startActivity(i);
			}
		});
		
		Button alarma = (Button) findViewById(R.id.alarma);
		alarma.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Abre el layout de hacer las medidas:
				Intent i = new Intent(HistorialCActivity.this,
						MLocationActivity.class);
				Bundle bundle = getIntent().getExtras();
				Log.d("DNI Usuario:", bundle.getString("idp"));
				String idp=bundle.getString("idp").toString();
				

				i.putExtra("idp", idp);
				i.putExtra("idhc", idp);
				startActivity(i);
			}
		});

		(new FetchHCTask()).execute();
	}


	public class FetchHCTask extends AsyncTask<String, Void, HistorialC> {

		private ProgressDialog pd;
		public String idpiso;

		@Override
		protected HistorialC doInBackground(String... params) {
			HistorialC p = new HistorialC();
			try {
				Bundle bundle = getIntent().getExtras();
				Log.d("id Usuario:", bundle.getString("idp"));
				p = EHealthAPI.getInstance(HistorialCActivity.this).getHistorialCByPacienteDNI(bundle.getString("idp"));
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			}
			
			return p;

		}

		@Override
		protected void onPostExecute(HistorialC result) {
			//Obtenemos todos los textViews del layout:
			TextView tvPresion = (TextView) findViewById(R.id.tvPresionAHP);
			TextView tvPulso = (TextView) findViewById(R.id.tvPusloP);
			TextView tvEnfermedad = (TextView) findViewById(R.id.tvEnfermedadP);
			TextView tvTemperatura = (TextView) findViewById(R.id.tvTemperaturaP);
			
			//ToString+ printar
			String presion=result.getPresiona().toString();
			tvPresion.setText(""+presion);
			
			String pulso = ""+ result.getPulso().toString();
			tvPulso.setText(""+pulso);
			
			String enfermedad = ""+result.getEnfermedad();
			tvEnfermedad.setText(""+enfermedad);

			String temperatura = ""+result.getTemp().toString();
			tvTemperatura.setText(""+temperatura);
			
			

		}
		
		

	}

}
