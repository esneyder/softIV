package com.example.softiv;
import com.example.softiv.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class Menu_principal extends Activity implements OnClickListener{
Button btncategoria;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_principal);
		btncategoria=(Button) findViewById(id.btncategoria);
		btncategoria.setOnClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
				//getMenuInflater().inflate(R.menu.activity_menu_principal, menu);
				//return true;
				
				//MEN� 1 Y SUBMEN� 1
				 
			       //SubMenu sub1 = menu.addSubMenu(id del grupo, id del item, orden, t�tulo del menu)
			       SubMenu sub1 = menu.addSubMenu(1,1,1,"Salir");
			 
			       //Icono del men� 1
			       sub1.setIcon(R.drawable.salir);
			       //Icono de las opciones del submen� del men� 1
			       sub1.setHeaderIcon(R.drawable.salir);
			 
			       //sub1.add(id del grupo, id del item, orden, t�tulo de la opci�n)
			       //sub1.add(1, 10, 1, "Menu salir opci�n 0");
			       //sub1.add(1, 11, 1, "Menu salir opci�n 1");
			 
			       //MEN� 2 Y SUBMEN� 2 PARECIDO AL ANTERIOR
			 
			       SubMenu sub2 = menu.addSubMenu(2,2,2,"Categoria");
			       sub2.setHeaderIcon(R.drawable.info);
			       sub2.setIcon(R.drawable.info);
			 
			       //sub2.add(1, 20, 0, "Men� informaci�n opci�n 0");
			       //sub2.add(1, 21, 1, "Men� informaci�n opci�n 1");
			       /*Como pod�is comprobar en las opciones del men� 1 de id del item le pongo 
			        * a la 1� opci�n 10 y a la 2� 11 debido a que pertenece al men� 1 la opci�n 0 y la opci�n 1 
			        * lo mismo hago con las id de las opciones del 2� men� 20 a la primera y 21 a la segunda.
			        * Esto cada persona lo puede poner como quiera, pero hay que tener cuidado, ya que
			        * no se puede repetir el id de ningun item (opci�n)
			        */
			       
			       
			       
			       SubMenu sub3 = menu.addSubMenu(3,3,3,"Productos");
			       sub3.setHeaderIcon(R.drawable.info);
			       sub3.setIcon(R.drawable.info);
			 
			       return super.onCreateOptionsMenu(menu);
	}
	
	//Gestionar los elementos del men�
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        /*El switch se encargar� de gestionar cada elemento del men� dependiendo de su id,
        por eso dijimos antes que ning�n id de los elementos del men� podia ser igual.
        */
 
        switch(item.getItemId()){
        case 1: //Id del men�, para combrobar que se ha pulsado       	
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setMessage("�Desea Salir?")
        	        .setTitle("Advertencia")
        	        .setCancelable(false)
        	        .setPositiveButton("Si",
        	                new DialogInterface.OnClickListener() {
        	                    public void onClick(DialogInterface dialog, int id) {
        	                        // metodo que se debe implementar
        	                    	//envia al otro activity login
        	                    	Intent intent = new Intent(Menu_principal.this, MainActivity.class);
        	                        startActivity(intent);
        	                        finish();
        	                    }
        	                })
        	 .setNegativeButton("No",
 	                new DialogInterface.OnClickListener() {
 	                    public void onClick(DialogInterface dialog, int id) {
 	                        dialog.cancel();
 	                    }
 	                });
        	AlertDialog alert = builder.create();
        	alert.show(); 
            break;
        case 2:
           // Toast.makeText(this,"Has pulsado la Opci�n Informacion",Toast.LENGTH_SHORT).show();
        	try{
        	Intent intent= new Intent(this,Categoria.class);
        	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(intent);
        	finish();}
        	catch(Exception e){
        		Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        		
        	}
            break; 
            
        case 3:
            // Toast.makeText(this,"Has pulsado la Opci�n Informacion",Toast.LENGTH_SHORT).show();
         	try{
         		Intent intent = new Intent();
    			intent.setClassName("com.example.softiv", "com.example.softiv.Productos");
    			startActivity(intent);
         	finish();}
         	catch(Exception e){
         		Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
         		
         	}
             break; 
                
        }
        return true;//Consumimos el item, no se propaga
    }
    
    

    
  //Definimos que para cuando se presione la tecla BACK no volvamos para atras  	 
  	 @Override
  	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
  	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
  	         // no hacemos nada.
  	         return true;
  	     }

  	   if(keyCode == KeyEvent.KEYCODE_HOME) {
	        Log.i("Home Button","Clicked");
	    }
  	     
  	     return super.onKeyDown(keyCode, event);
  	 }
  	 
  	@Override
  	public void onAttachedToWindow() {
  	    this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
  	    super.onAttachedToWindow();
  	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(btncategoria==v)
		{
			Intent intent = new Intent();
			intent.setClassName("com.example.softiv", "com.example.softiv.Productos");
			startActivity(intent);
					
		}
		
	}

}