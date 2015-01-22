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
import dd.model.Medico;
import dd.model.SHA1;

@SuppressLint("NewApi")
public class LoginMActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_m);

		ActionBar bar = getActionBar();
		// bar.setBackgroundDrawable(new
		// ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();

		Button login = (Button) findViewById(R.id.loginMedico);

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				(new FetchMTask()).execute();
			}
		});

	}

	public class FetchMTask extends AsyncTask<String, Void, Medico> {

		private ProgressDialog pd;

		@Override
		protected Medico doInBackground(String... params) {
			Medico p = new Medico();
			try {
				EditText nm = (EditText) findViewById(R.id.nombreUM);
				String nombrem = nm.getText().toString();
				p = EHealthAPI.getInstance(LoginMActivity.this).loginMedico(
						nombrem);
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			}

			return p;

		}

		@Override
		protected void onPostExecute(Medico result) {
			String passSha1 = null;
			if (result.getPass().equals("renueva")) {

				Toast.makeText(getApplicationContext(),
						"Renueva la contraseña", Toast.LENGTH_LONG).show();
				Intent i = new Intent(LoginMActivity.this,
						ActualizarPassPActivity.class);
				startActivity(i);

			} else {
				EditText pass = (EditText) findViewById(R.id.passwordUM);
				String pword = pass.getText().toString();
				SHA1 sha1 = new SHA1();

				passSha1 = sha1.get_SHA_1_SecurePassword(pword,
						result.getSalt());

				// Comparamos passwords:
				if (passSha1.equals(result.getPass())) {
					Intent i = new Intent(LoginMActivity.this,
							ListHCActivity.class);

					i.putExtra("dni", result.getDnim());
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
