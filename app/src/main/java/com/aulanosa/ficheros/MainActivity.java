package com.aulanosa.ficheros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {



    Button btnEscExt,btnLeeExt,btnEscInt, btnLeeInt;
    RadioButton interno, externo;
    boolean sdDisponible = false;
    boolean sdAccesoEscritura = false;
    TextView mensaje;
    EditText contenidoLect, contenidoEscr;
    File sd;
    File archivo1,archivo2;
    boolean TipoAlmacenamiento;
    Resources recurso = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hacer scroll en la caja de texto
       // final EditText txtMensaje =  (EditText) findViewById(R.id.edTxtMensaje);

        //txtMensaje.setVerticalScrollBarEnabled(true);

        //    sd = Environment.getExternalStorageDirectory();
        //   archivo1 = new File("fichero.txt");
         //   archivo2 = new File(sd, "fichero2.txt");
            mensaje = (TextView) findViewById(R.id.txtMensaje);
            contenidoLect = (EditText)
                    findViewById(R.id.edTxtMensajeLect);
            contenidoEscr = (EditText)
                findViewById(R.id.edTxtMensajeEscr);

            btnEscExt = (Button) findViewById(R.id.btnEscribirExt);
            btnLeeExt = (Button) findViewById(R.id.btnLeerExt);
            btnEscInt = (Button) findViewById(R.id.btnEscribirInt);
            btnLeeInt = (Button) findViewById(R.id.btnLeerInt);
      //      interno = (RadioButton) findViewById(R.id.chkInterno);
       //     externo = (RadioButton) findViewById(R.id.chkExterno);

            //CAMBIO EN EL CHECK_PRIMERA FORMA Y CHEQUEO DE LA PROPIEDAD:
//            if (interno.isChecked()) {
//                TipoAlmacenamiento = true;
 //           } else {
 //               TipoAlmacenamiento = false;
 //           }
            //CANMBIO EN EL CHECK SEGUNDA FORMA:
// interno.setOnCheckedChangeListener(new RadioClass());
// externo.setOnCheckedChangeListener(new RadioClass());//
// class RadioClass implements
     //       CompoundButton.OnCheckedChangeListener
// {
// @Override
// public void onCheckedChanged(CompoundButton buttonView,
       //     boolean isChecked) {
// if(interno.isChecked())
// {
// TipoAlmacenamiento=true;
// }
// else if(externo.isChecked())
// {
// TipoAlmacenamiento=false;
// }
// }
// }
                //CAMBIO EN EL CHECK TERCERA FORMA:
      /*          interno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TipoAlmacenamiento = true;
                        mensaje.setText("Elegido almacenamiento interno privado");
                    }
                });
                externo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TipoAlmacenamiento = false;
                        mensaje.setText("Elegido almacenamiento interno compartido");
                    }
                });
             */

                btnEscExt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        escribirMemExt();
                    }
                });
                btnLeeExt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        leerMemExt();
                    }
                });
                btnEscInt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       escribirMemInt();
                    }
                });
                btnLeeInt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       leerMemInt();
                    }
                });
         }


        //Escribir en la memoria interna con acceso privado
        public void escribirMemInt() {
            try {
                OutputStreamWriter output =
                        new OutputStreamWriter(openFileOutput("fichero.txt", MODE_PRIVATE));
                output.write(contenidoEscr.getText().toString());
                // aqui envio un mensaje por pantalla
                mensaje.setText("Archivo acceso privado.. escritura realizada");
                output.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Leer en la memoria interna con acceso privado
        public void leerMemInt(){
            try{
               // contenidoLect.setText("");
                BufferedReader in = new BufferedReader(new InputStreamReader(openFileInput("fichero.txt")));

                // Lectura del fichero
                String linea;
                String totalLineas ="";
                while((linea=in.readLine())!=null){
                    totalLineas = totalLineas + linea +"\n";
                }
                contenidoLect.setText(totalLineas);
                in.close();
                mensaje.setText("Archivo de acceso privado... lectura realizada");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
  // escritura y lectura en un directorio en memoria extera sd
        public void EstadoSD(){
        String cadena= "";

         try{
             // se comprueba el estado de la memoria externa (tarjeta sd)
             String estado = Environment.getExternalStorageState();
             if (estado.equals(Environment.MEDIA_MOUNTED)){
                 sdDisponible = true;
                 sdAccesoEscritura = true;

             }else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
                 sdDisponible = true;
                 sdAccesoEscritura = false;

             }else {
                 sdDisponible = false;
                 sdAccesoEscritura = false;
             }
             cadena = "Disponibilidad SD " + sdDisponible + "Acceso Escritura " + sdAccesoEscritura;
             mensaje.setText(cadena);
         }catch(Exception e){
                e.printStackTrace();
         }
        }

   /* public void escribirMemExt() {
       recurso = getResources();
        mensaje.setText("Ruta : "+recurso);


    }
*/
        public void escribirMemExt(){
            EstadoSD();
            File[] Memoria = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
            File ruta = Memoria[1];
            mensaje.setText("Ruta : "+ruta.toString());
            File file = new File (ruta.getAbsolutePath(), "fichero2.txt");
            try{
               OutputStreamWriter outputStreamWriter = new OutputStreamWriter((new FileOutputStream(file)));
               outputStreamWriter.write(contenidoEscr.getText().toString());
               outputStreamWriter.close();
            }catch (Exception e){
                Log.e("FILE I/O", "Error en la escritura del fichero "+ e.getMessage());
            }
       }

        public void leerMemExt(){
            EstadoSD();
            File[] Memoria = ContextCompat.getExternalFilesDirs(getApplicationContext(), null);
            File ruta = Memoria[1];
            mensaje.setText("Ruta : "+ruta.toString());
            File file = new File (ruta.getAbsolutePath(), "fichero2.txt");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String linea;
                String totalLineas ="";
                while((linea=bufferedReader.readLine())!=null){
                    totalLineas = totalLineas + linea +"\n";
                }

                contenidoLect.setText(totalLineas);
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}