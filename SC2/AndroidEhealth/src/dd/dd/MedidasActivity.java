package dd.dd;

import java.math.BigInteger;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
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
import dd.dd.HistorialCActivity.FetchHCTask;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.HistorialC;

@SuppressLint("NewApi")
public class MedidasActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medidas);

		ActionBar bar = getActionBar();
		// bar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();

		Button update = (Button) findViewById(R.id.medidasU);
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				(new FetchUpdateMTask()).execute();
			}
		});

		Button cancelar = (Button) findViewById(R.id.cancelar);
		cancelar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MedidasActivity.this,
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
		getMenuInflater().inflate(R.menu.medidas, menu);
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

	
	public class FetchUpdateMTask extends AsyncTask<String, Void, Void> {

		private ProgressDialog pd;
		public String idpiso;

		@Override
		protected Void doInBackground(String... params) {
			//Cogemos las medidas:
			Bundle bundle = getIntent().getExtras();
			String idhc = bundle.getString("idhc");
			Log.d("UpdateMedidas idHC:", idhc);
			
			EditText eTpulso = (EditText) findViewById(R.id.pulsoU);
			EditText eTtemp = (EditText) findViewById(R.id.temperaturaU);
			EditText eTpresion= (EditText) findViewById(R.id.presionAU);
			
			HistorialC hc = new HistorialC();
			hc.setPresiona(BigInteger.valueOf(Integer.parseInt(eTpresion.getText().toString())));
			hc.setPulso(BigInteger.valueOf(Integer.parseInt(eTpulso.getText().toString())));
			hc.setTemp(BigInteger.valueOf(Integer.parseInt(eTtemp.getText().toString())));
			
			try {
				EHealthAPI.getInstance(MedidasActivity.this).putMedidas(hc,
						Integer.parseInt(bundle.getString("idp")),
						Integer.parseInt((bundle.getString("idp"))));
			} catch (NumberFormatException | EHealthAndroidException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// Obtenemos todos los textViews del layout:
			Intent i = new Intent(MedidasActivity.this,
					HistorialCActivity.class);
			Bundle bundle = getIntent().getExtras();
			String idp = bundle.getString("idp");
			i.putExtra("idp", idp);

			startActivity(i);
		}

	}
	
}
