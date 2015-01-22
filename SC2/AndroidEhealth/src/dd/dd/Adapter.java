package dd.dd;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import dd.model.HistorialC;

public class Adapter extends BaseAdapter {
	private List<HistorialC> data;
	private LayoutInflater inflater;

	private static class ViewHolder {

		//TextView NombreU;
		TextView TextoC;
	}

	public Adapter(Context context, ArrayList<HistorialC> data) {
		super();
		inflater = LayoutInflater.from(context);
		this.data = data;
	}

	@Override
	public long getItemId(int position) {
		
		return (long) ((HistorialC) getItem(position)).getIdhc().intValue();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_adapter,
					null);
			viewHolder = new ViewHolder();
//			viewHolder.NombreU = (TextView) convertView
//					.findViewById(R.id.NombreUsuarioC);
			viewHolder.TextoC = (TextView) convertView
					.findViewById(R.id.TextoComentario);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
//		String nombreU = "" + data.get(position).getUsu().getNombreE();
		String texto = data.get(position).getIdhc().toString();

		viewHolder.TextoC.setText(texto);
		viewHolder.TextoC.setTextSize(17);
		viewHolder.TextoC.setTextColor(Color.BLACK);
		viewHolder.TextoC.setTypeface(null, Typeface.BOLD);
		return convertView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

}
