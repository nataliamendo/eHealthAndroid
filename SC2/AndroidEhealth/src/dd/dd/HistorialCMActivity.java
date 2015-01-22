package dd.dd;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.HistorialC;

@SuppressLint("NewApi")
public class HistorialCMActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historial_cm);
		
		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();
		
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
				Log.d("ID HC:", bundle.getString("idhc"));
				int idhc = Integer.parseInt(bundle.getString("idhc"));
				String dnim = bundle.getString("dni");
				p = EHealthAPI.getInstance(HistorialCMActivity.this).getHCByIdhcByMedico(dnim, idhc);
				//p = EHealthAPI.getInstance(HistorialCMActivity.this).getHistorialCByHCId(idhc);
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			}

			return p;

		}

		@Override
		protected void onPostExecute(HistorialC result) {
			// Obtenemos todos los textViews del layout:
			TextView tvPresion = (TextView) findViewById(R.id.tvPresionAHPM);
			TextView tvPulso = (TextView) findViewById(R.id.tvPusloPM);
			TextView tvEnfermedad = (TextView) findViewById(R.id.tvEnfermedadPM);
			TextView tvTemperatura = (TextView) findViewById(R.id.tvTemperaturaPM);

			// ToString+ printar
			String localidad = result.getPresiona().toString();
			tvPresion.setText("" + localidad);

			String dimension = "" + result.getPulso().toString();
			tvPulso.setText("" + dimension);

			String numeroH = "" + result.getEnfermedad();
			tvEnfermedad.setText("" + numeroH);

			String calle = "" + result.getTemp().toString();
			tvTemperatura.setText("" + calle);

		}

	}
}
