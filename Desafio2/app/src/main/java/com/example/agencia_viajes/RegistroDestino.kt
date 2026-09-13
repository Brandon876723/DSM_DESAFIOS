package com.example.agencia_viajes

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class RegistroDestino : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var spPais: Spinner
    private lateinit var etPrecio: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var btnSeleccionarImagen: Button
    private lateinit var btnGuardarDestino: Button

    private var imagenUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_destino)

        etNombre = findViewById(R.id.etNombre)
        spPais = findViewById(R.id.spPais)
        etPrecio = findViewById(R.id.etPrecio)
        etDescripcion = findViewById(R.id.etDescripcion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        btnGuardarDestino = findViewById(R.id.btnGuardarDestino)

        // Llenar Spinner con países
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.paises_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPais.adapter = adapter

        // Seleccionar imagen
        btnSeleccionarImagen.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        // Guardar destino
        btnGuardarDestino.setOnClickListener {
            val nombre = etNombre.text.toString()
            val pais = spPais.selectedItem.toString()
            val precioStr = etPrecio.text.toString()
            val descripcion = etDescripcion.text.toString()

            if (nombre.isEmpty() || precioStr.isEmpty() || descripcion.isEmpty() || imagenUri == null) {
                Toast.makeText(this, getString(R.string.error_imagen_faltante), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val precio = precioStr.toDoubleOrNull()
            if (precio == null || precio <= 0) {
                Toast.makeText(this, getString(R.string.error_precio_invalido), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (descripcion.length < 20) {
                Toast.makeText(this, getString(R.string.error_descripcion_corta), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Subir imagen a Storage
            val fileName = "destinos/${System.currentTimeMillis()}.jpg"
            val ref = storage.reference.child(fileName)

            ref.putFile(imagenUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        val destino = hashMapOf(
                            "nombre" to nombre,
                            "pais" to pais,
                            "precio" to precio,
                            "descripcion" to descripcion,
                            "imagenUrl" to uri.toString()
                        )

                        db.collection("destinos").add(destino)
                            .addOnSuccessListener {
                                Toast.makeText(this, getString(R.string.destino_guardado), Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al subir imagen: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imagenUri = data?.data
            Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show()
        }
    }
}