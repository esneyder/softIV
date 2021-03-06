package com.example.softiv;

 
 
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
private Button btnIngresar;
private Button btnRegistrar;
private EditText txtUsuario;
private EditText txtPassword;

public final int dialogo_alert=0;
public String msje="";

// referencia a la clase
Variables_publicas variables= new Variables_publicas();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   
    btnIngresar=(Button) findViewById(R.id.btnIngresar);
    
    
    
    txtUsuario = (EditText)findViewById(R.id.txtUsuario);
	txtPassword = (EditText)findViewById(R.id.txtPassword);
	 
	btnIngresar.setOnClickListener(this);
	
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



	@Override
	public void onClick(View v) {	
		
		 
		
		if(btnIngresar==v)
		{
			final String NAMESPACE = "http://esneyder.org/";
			final String URL=variables.direccionIp + "/Servicio.asmx"; 
			final String METHOD_NAME = "LoginUsuario";
			final String SOAP_ACTION = "http://esneyder.org/LoginUsuario";								
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			request.addProperty("user", txtUsuario.getText().toString()); 
			request.addProperty("password", txtPassword.getText().toString()); 

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
		 										
				if(res.equals("Bienvenido")){
					variables.usuario=txtUsuario.getText().toString();
					txtUsuario.setText("");
					txtPassword.setText("");
						//envia al otro activity
					Intent intent=new Intent("android.intent.action.Barra_cargado");				 
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
}
