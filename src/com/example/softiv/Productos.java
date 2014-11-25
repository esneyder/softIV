package com.example.softiv;

import java.util.LinkedList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Productos extends Activity implements OnClickListener {
	Spinner desplegable;
	EditText id,nombre,descripcion,costo,cantidad;
	
	Button insertar,actualizar,mostrar;
	int alimentoId;
	Variables_publicas variables= new Variables_publicas();
	float valor;
	public String msje="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productos);
		
		desplegable=(Spinner) this.findViewById(R.id.listcategoria);
		id=(EditText)this.findViewById(R.id.txtid);
		nombre=(EditText)this.findViewById(R.id.txtnombre);
		descripcion=(EditText)this.findViewById(R.id.txtdescripcion);
		costo=(EditText)this.findViewById(R.id.txtvalor);
		cantidad=(EditText)this.findViewById(R.id.txtcantidad);
		insertar=(Button)this.findViewById(R.id.btnguardar);
		actualizar=(Button)this.findViewById(R.id.btnactualizar);
		mostrar=(Button)this.findViewById(R.id.btnmostrar);
		insertar.setOnClickListener(this);
		actualizar.setOnClickListener(this);
		mostrar.setOnClickListener(this);
		
	 
		
		TareaWSConsultar tarea1=new TareaWSConsultar();
		tarea1.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.productos, menu);
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
	
	@Override
	public void onClick(View v) {
		if(v==insertar)
		{
			//TareaWSInsertarProducto tarea=new TareaWSInsertarProducto();
			//tarea.execute();
			
			insertar();
		}
		
	}
	
public class MyOnItemSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			if (parent.getId() == R.id.listcategoria) {
				alimentoId = ((ObjetosClase) parent.getItemAtPosition(pos)).getId();
			}
			//Podemos hacer varios ifs o un switchs por si tenemos varios spinners.
		}
		
		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// Do nothing.
		}
		
		
		
}

private class TareaWSConsultar extends AsyncTask<String,Integer,Boolean>{
	private ObjetosClase[] listaCategorias;
	@Override
	protected Boolean doInBackground(String... arg0) {
		boolean result=true;
		
		//Se define interfaz entre android y el webservice
		final String NAMESPACE = "http://esneyder.org/";
		final String URL=variables.direccionIp + "/Servicio.asmx"; 
		final String METHOD_NAME = "listarCategorias";
		final String SOAP_ACTION = "http://esneyder.org/listarCategorias";
		
		//Se crea nuestro objeto de comunicación
		SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
		
		//Se pasa los parámetros al request
		//Se crea un envolvente para pasar los datos
		SoapSerializationEnvelope envelope= new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet=true;
		
		//Se envía los datos
		envelope.setOutputSoapObject(request);
		
		//Se crea la capa de transporte
		HttpTransportSE transporte= new HttpTransportSE(URL);
		try{
			//Se llama el servicio
			transporte.call(SOAP_ACTION, envelope);
			SoapObject resSoap=(SoapObject)envelope.getResponse();
			//Se crea el areglo como tantos elementos traiga la lista
			listaCategorias = new ObjetosClase[resSoap.getPropertyCount()];
			for(int i=0;i<listaCategorias.length;i++){
				//Se toma el primir elemento de la lista
				SoapObject ic=(SoapObject)resSoap.getProperty(i);
				ObjetosClase cli = new ObjetosClase(Integer.parseInt(ic.getProperty(0).toString()),ic.getProperty(1).toString());
				listaCategorias[i]=cli;
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		    result=false;
		}		
		return result;
	}
	
	protected void onPostExecute(Boolean result){
		if(result){
		//Creamos la lista
		LinkedList<ObjetosClase> comidas = new LinkedList<ObjetosClase>();
		//La poblamos con los ejemplos
		for(int i=0;i<listaCategorias.length;i++){
		comidas.add(new ObjetosClase(listaCategorias[i].getId(),listaCategorias[i].toString()));
		}
		//Creamos el adaptador
		ArrayAdapter<ObjetosClase> spinner_adapter =new ArrayAdapter<ObjetosClase>(Productos.this,android.R.layout.simple_spinner_item,comidas);
				
		//Añadimos el layout para el menú y se lo damos al spinner
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		desplegable.setAdapter(spinner_adapter);
		
		}
		else
			Toast.makeText(Productos.this,"Error", Toast.LENGTH_SHORT).show();
		
	}
	
}

private class TareaWSInsertarProducto extends AsyncTask<String,Integer,Boolean>{
	public String msje="";
	@Override
	protected Boolean doInBackground(String... arg0) {
		boolean result=true;
		
		final String NAMESPACE = "http://esneyder.org/";
		final String URL=variables.direccionIp + "/Servicio.asmx"; 
		final String METHOD_NAME = "crearproducto";
		final String SOAP_ACTION = "http://esneyder.org/crearproducto";								
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		request.addProperty("nombre", nombre.getText().toString()); 
		request.addProperty("descripcion", descripcion.getText().toString()); 
		request.addProperty("avatar", "");
		request.addProperty("idcategoria", 1); 
		request.addProperty("valor", valor); 
		request.addProperty("cantidad", Integer.parseInt(cantidad.getText().toString())); 
		
		
		 
		SoapSerializationEnvelope envelope = 
				new SoapSerializationEnvelope(SoapEnvelope.VER11);
		
		envelope.dotNet = true; 

		envelope.setOutputSoapObject(request);

		HttpTransportSE transporte = new HttpTransportSE(URL);
		try{
			transporte.call(SOAP_ACTION, envelope);

			SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
			String res = resultado_xml.toString();
			msje=res;
			// mostramos la respuesta en un toast
			Toast.makeText(getBaseContext(), res+"",Toast.LENGTH_SHORT).show();					
	 										
			if(res.equals("Registro creado correctamente..")){
				
				nombre.setText("");
				descripcion.setText("");
				//valor.setText("");
				cantidad.setText("");
 
					//envia al otro activity
				Intent intent=new Intent("android.intent.action.Menu_principal");				 
				startActivity(intent);
				finish();
			}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		    result=false;
		}		
		return result;
	}
	
	protected void onPostExecute(Boolean result){
		if(!result){
			Toast.makeText(Productos.this,"Error", Toast.LENGTH_SHORT).show();

		}
			
		
	}
	
}


//insertar nuevo producto



public void insertar(){
	final String NAMESPACE = "http://esneyder.org/";
	final String URL=variables.direccionIp + "/Servicio.asmx"; 
	final String METHOD_NAME = "crearproducto";
	final String SOAP_ACTION = "http://esneyder.org/crearproducto";								
	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

	request.addProperty("nombre", nombre.getText().toString()); 
	request.addProperty("descripcion", descripcion.getText().toString()); 
	request.addProperty("avatar", "");
	request.addProperty("idcategoria", 1); 
	request.addProperty("valor", valor = Float.valueOf(costo.getText().toString())); 
	request.addProperty("cantidad", Integer.parseInt(cantidad.getText().toString())); 
	 
	 
	SoapSerializationEnvelope envelope = 
			new SoapSerializationEnvelope(SoapEnvelope.VER11);
	
	envelope.dotNet = true; 

	envelope.setOutputSoapObject(request);

	HttpTransportSE transporte = new HttpTransportSE(URL);
	try 
	{
		transporte.call(SOAP_ACTION, envelope);

		SoapPrimitive resultado_xml =(SoapPrimitive)envelope.getResponse();
		String res = resultado_xml.toString();
		msje=res;
		// mostramos la respuesta en un toast
		Toast.makeText(getBaseContext(), res+"",Toast.LENGTH_SHORT).show();					
 										
		if(res.equals("Registro creado correctamente..")){
			variables.usuario=nombre.getText().toString();
			nombre.setText("");
			 
				//envia al otro activity
			Intent intent=new Intent("android.intent.action.Menu_principal");				 
			startActivity(intent);
			finish();
		}
		
	} 
	catch (Exception e) 
	{
		Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
	} 
	
	
}



}


