package www.udb.edu.sv.desafio3_dsm.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import www.udb.edu.sv.desafio3_dsm.R
import www.udb.edu.sv.desafio3_dsm.model.Recurso
import www.udb.edu.sv.desafio3_dsm.network.RetrofitClient

class AddResourceActivity : AppCompatActivity() {

    private lateinit var editTextTitulo: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var editTextTipo: EditText
    private lateinit var editTextEnlace: EditText
    private lateinit var editTextImagen: EditText
    private lateinit var buttonAgregar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_resource)

        editTextTitulo = findViewById(R.id.editTextTitulo)
        editTextDescripcion = findViewById(R.id.editTextDescripcion)
        editTextTipo = findViewById(R.id.editTextTipo)
        editTextEnlace = findViewById(R.id.editTextEnlace)
        editTextImagen = findViewById(R.id.editTextImagen)
        buttonAgregar = findViewById(R.id.buttonAgregar)

        buttonAgregar.setOnClickListener {
            if (validarDatos()) {
                agregarRecurso()
            }
        }
    }

    private fun validarDatos(): Boolean {
        if (editTextTitulo.text.isEmpty() || editTextDescripcion.text.isEmpty() ||
            editTextTipo.text.isEmpty() || editTextEnlace.text.isEmpty() ||
            editTextImagen.text.isEmpty()
        ) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidUrl(editTextEnlace.text.toString())) {
            Toast.makeText(this, "Por favor, ingresa una URL válida.", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun isValidUrl(url: String): Boolean {
        val regex = "^(http|https)://.*$".toRegex()
        return regex.matches(url)
    }

    private fun agregarRecurso() {
        // Crea un nuevo recurso
        val nuevoRecurso = Recurso(
            id = "",
            titulo = editTextTitulo.text.toString(),
            descripcion = editTextDescripcion.text.toString(),
            tipo = editTextTipo.text.toString(),
            enlace = editTextEnlace.text.toString(),
            imagen = editTextImagen.text.toString()
        )

        lifecycleScope.launch {
            try {
                val recursoCreado = RetrofitClient.retrofitService.addResource(nuevoRecurso)
                Toast.makeText(
                    this@AddResourceActivity,
                    "Recurso agregado: ${recursoCreado.titulo}",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@AddResourceActivity,
                    "Error al agregar el recurso",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}