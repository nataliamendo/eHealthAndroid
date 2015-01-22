package dd.dd;

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
public class LoginPActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_p);

		ActionBar bar = getActionBar();
		// bar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();

		Button login = (Button) findViewById(R.id.loginPaciente);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				(new FetchPTask()).execute();
			}
		});

	}

	public class FetchPTask extends AsyncTask<String, Void, Paciente> {

		private ProgressDialog pd;

		@Override
		protected Paciente doInBackground(String... params) {
			Paciente p = new Paciente();
			try {
				EditText np = (EditText) findViewById(R.id.nombreUP);
				String nombrep = np.getText().toString();
				p = EHealthAPI.getInstance(LoginPActivity.this).loginPaciente(
						nombrep);
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			}

			return p;

		}

		@Override
		protected void onPostExecute(Paciente result) {
			String passSha1 = null;
			if (result.getPass().equals("renueva")) {

				Toast.makeText(getApplicationContext(),
						"Renueva la contraseña", Toast.LENGTH_LONG).show();
				Intent i = new Intent(LoginPActivity.this,
						ActualizarPassPActivity.class);
				startActivity(i);

			} else {
				EditText pass = (EditText) findViewById(R.id.passwordUP);
				String pword = pass.getText().toString();
				SHA1 sha1 = new SHA1();

				passSha1 = sha1.get_SHA_1_SecurePassword(pword,
						result.getSalt());

				// Comparamos passwords:
				if (passSha1.equals(result.getPass())) {
					// Comprobamos si la relacion usuario contraseña:

					Intent i = new Intent(LoginPActivity.this,
							HistorialCActivity.class);
					String idp = "" + result.getIdp();
					Log.d("idp", idp);
					i.putExtra("idp", idp);
					startActivity(i);

				} else {

					Toast.makeText(getApplicationContext(),
							"usuario o contraseña incorrecto",
							Toast.LENGTH_LONG).show();

				}
			}
		}

	}

}
