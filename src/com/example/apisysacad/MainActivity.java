package com.example.apisysacad;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);

	if (savedInstanceState == null) {
	    getSupportFragmentManager().beginTransaction()
		    .add(R.id.container, new PlaceholderFragment()).commit();
	}

	Spinner spinnerConsultas = (Spinner) findViewById(R.id.spinner1);

	@SuppressWarnings("serial")
	List<String> tiposDeConsultas = new ArrayList<String>() {
	    {
		add("Estado");
		add(GetSysacadAsyncTask.CALENDARIO);
		add(GetSysacadAsyncTask.CURSADO);
		add(GetSysacadAsyncTask.ESTADOACADEMICO);
		add(GetSysacadAsyncTask.EXAMENES);
		add(GetSysacadAsyncTask.FECHASEXAMENES);
		add(GetSysacadAsyncTask.INGRESO);
		add(GetSysacadAsyncTask.MATERIASINSCRIPTAS);
		add(GetSysacadAsyncTask.MATERIASPARAINSCRIPCION);
	    }
	};

	tiposDeConsultas.add(GetSysacadAsyncTask.CALENDARIO);

	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		android.R.layout.simple_spinner_item, tiposDeConsultas);

	dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	spinnerConsultas.setAdapter(dataAdapter);

	spinnerConsultas
		.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> parent,
			    View view, int position, long id) {
			
			String consulta = parent.getItemAtPosition(position)
				.toString();

			if (consulta.equalsIgnoreCase("Estado")) {
			    ProbarServicio();
			} else {
			    Consultar(consulta);
			}
			
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		    }
		});

    }

    private void Consultar(String tipoDeConsulta) {
	final EditText legajo = (EditText) findViewById(R.id.editText1);
	final EditText contrasenia = (EditText) findViewById(R.id.editText2);
	final TextView resultado = (TextView) findViewById(R.id.textView1);

	new GetSysacadAsyncTask() {
	    protected void onPostExecute(String result) {
		super.onPostExecute(result);

		try {

		    resultado.setText(result);

		} catch (Exception e) {
		    Log.e("GetLoginTask", "Error:" + e.getMessage());
		}
	    }
	}.execute(tipoDeConsulta, legajo.getText().toString(), contrasenia
		.getText().toString());
    }

    private void ProbarServicio() {
	final TextView resultado = (TextView) findViewById(R.id.textView1);

	new GetSysacadAsyncTask() {
	    protected void onPostExecute(String result) {
		super.onPostExecute(result);

		try {

		    resultado.setText(result);

		} catch (Exception e) {
		    Log.e("GetLoginTask", "Error:" + e.getMessage());
		}
	    }
	}.execute(GetSysacadAsyncTask.INGRESO);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_main, container,
		    false);
	    return rootView;
	}
    }

}
