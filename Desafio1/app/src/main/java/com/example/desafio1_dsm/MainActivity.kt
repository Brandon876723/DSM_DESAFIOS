package com.example.desafio1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle
import android.widget.Button
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    /*Este seria el primer metodo que se ejecutara cuando iniciemos la app*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) //llama al metodo onCreate
        setContentView(R.layout.activity_main) //con esto conectamos con el archivo xml

        /*Guardamos los botones en una variable para que los podamos ocupar aca
    * en el codigo*/
        val btnEjercicio1 = findViewById<Button>(R.id.btnEjercicio1) /*el find busca en el xml
    los id de cada boton el cual le pusimos seria buscar el @+id/Ejercicio"N" n=1,2,3*/
        val btnEjercicio2 = findViewById<Button>(R.id.btnEjercicio2)
        val btnEjercicio3 = findViewById<Button>(R.id.btnEjercicio3)

        //Los botones
        /*el evento que acompaña a la variable hara que cambie al darle click*/
        btnEjercicio1.setOnClickListener {
            //Esta variable hara que abra la clase que seria la del ejercicio
            //ese llevara lo que corresponde a el ejercicio
            val intent = Intent(this, PromedioActivity::class.java)
            //aqui le decimos que ejecute ese intent que se crea y
            //abra la nueva pantalla
            startActivity(intent)
            /*Lo mismo sera con los otros botones pero con su clase correspondiente*/
        }

        btnEjercicio2.setOnClickListener {
            val intent = Intent(this, SalarioActivity::class.java)
            startActivity(intent)
        }

        btnEjercicio3.setOnClickListener {
            val intent = Intent(this, CalculadoraActivity::class.java)
            startActivity(intent)
        }
    }
}