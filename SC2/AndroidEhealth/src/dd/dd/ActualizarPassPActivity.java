package dd.dd;

import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.Paciente;
import dd.model.SHA1;

@SuppressLint("NewApi")
public class ActualizarPassPActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_actualizar_pass_p);
		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();
		
		Button login = (Button) findViewById(R.id.actualizaPP);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText np = (EditText) findViewById(R.id.passwordUM1);
				EditText np2 = (EditText) findViewById(R.id.passwordUM2);
				String p1=np.getText().toString();
				String p2=np2.getText().toString();
				
				if(p1.equals(p2))
				{
					(new FetchPTask()).execute();
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Comprueba que las dos contras sean iguales",
							Toast.LENGTH_LONG).show();
				}
				
			}
		});
	}
	public class FetchPTask extends AsyncTask<String, Void, Paciente> {

		private ProgressDialog pd;

		@Override
		protected Paciente doInBackground(String... params) {
			Paciente p = new Paciente();
			try {
				EditText np = (EditText) findViewById(R.id.passwordUM1);
				EditText np2 = (EditText) findViewById(R.id.nombreUMUp);
				String p1=np.getText().toString();
				String p2=np2.getText().toString();
				
				SHA1 sha1 = new SHA1();
				String salt = sha1.getSalt();
				String passSha1 = null;
				passSha1 = sha1.get_SHA_1_SecurePassword(p1, salt);
				
				p.setNombrep(p2);
				p.setPass(passSha1);
				p.setSalt(salt);
				EHealthAPI.getInstance(ActualizarPassPActivity.this).putPassP(p);
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return p;

		}

		@Override
		protected void onPostExecute(Paciente result) {

			//vuelve al login
			Intent i = new Intent(ActualizarPassPActivity.this,
					LoginPActivity.class);
			Toast.makeText(getApplicationContext(), "Contraseña actualizada",
					Toast.LENGTH_LONG).show();

			startActivity(i);
		}

	}

}
