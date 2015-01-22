package dd.dd;

import java.util.ArrayList;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import dd.model.EHealthAPI;
import dd.model.EHealthAndroidException;
import dd.model.HCCollection;
import dd.model.HistorialC;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ListHCActivity extends ListActivity {

	private class FetchHCsTask extends
			AsyncTask<String, Void, HCCollection> {

		private ProgressDialog pd;

		@Override
		protected HCCollection doInBackground(String... params) {

			HCCollection developers = null;
			Bundle bundle = getIntent().getExtras();
			Log.d("DNI Usuario:", bundle.getString("dni"));
			try {
				developers = EHealthAPI.getInstance(ListHCActivity.this).getListHCofMedico(bundle.getString("dni"));
			} catch (EHealthAndroidException e) {
				e.printStackTrace();
			}
			return developers;

		}

		@Override
		protected void onPostExecute(HCCollection result) {

			if (result == null) {

			} else {
				addDeveloper(result);
				if (pd != null) {
					pd.dismiss();
				}
			}

		}

		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(ListHCActivity.this);
			pd.setTitle("Buscando el servidor...");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
			if (pd != null) {
				pd.dismiss();
			}
		}

	}

	private void addDeveloper(HCCollection pisos) {
		hcList.addAll(pisos.getListHC());
		adapter.notifyDataSetChanged();
	}

	private ArrayList<HistorialC> hcList;
	private Adapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_hc);

		ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#64E8CE")));
		bar.hide();
		
		hcList = new ArrayList<HistorialC>();
		adapter = new Adapter(this, hcList);
		setListAdapter(adapter);

		(new FetchHCsTask()).execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_hc, menu);
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
	
	protected void onListItemClick(ListView l, View v, int position, long idhc) {
		HistorialC hc = hcList.get(position);
		
		Log.d("Position", ""+position);
		Log.d("pisoid", ""+hc.getIdhc());
		
		Bundle bundle = getIntent().getExtras();
		Log.d("DNI Usuario haciendo click:", bundle.getString("dni"));
		String dni = bundle.getString("dni");
		
		Intent i = new Intent(this, HistorialCMActivity.class);
		i.putExtra("idhc", ""+hc.getIdhc());
		i.putExtra("dni", dni);
		
		startActivity(i);
	}

}
