package www.udb.edu.sv.desafio3_dsm.ui

import android.app.Activity
import android.os.Bundle
import www.udb.edu.sv.desafio3_dsm.R
import www.udb.edu.sv.desafio3_dsm.model.Recurso
import www.udb.edu.sv.desafio3_dsm.network.RetrofitClient
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class EditResourceActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextType: EditText
    private lateinit var editTextLink: EditText
    private lateinit var editTextImage: EditText
    private lateinit var buttonUpdateResource: Button

    private var resourceId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_resource)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        editTextType = findViewById(R.id.editTextType)
        editTextLink = findViewById(R.id.editTextLink)
        editTextImage = findViewById(R.id.editTextImage)
        buttonUpdateResource = findViewById(R.id.buttonUpdateResource)

        resourceId = intent.getStringExtra("id")
        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")
        val tipo = intent.getStringExtra("tipo")
        val enlace = intent.getStringExtra("enlace")
        val imagen = intent.getStringExtra("imagen")

        editTextTitle.setText(titulo)
        editTextDescription.setText(descripcion)
        editTextType.setText(tipo)
        editTextLink.setText(enlace)
        editTextImage.setText(imagen)

        buttonUpdateResource.setOnClickListener {
            val updatedTitulo = editTextTitle.text.toString()
            val updatedDescripcion = editTextDescription.text.toString()
            val updatedTipo = editTextType.text.toString()
            val updatedEnlace = editTextLink.text.toString()
            val updatedImagen = editTextImage.text.toString()

            if (updatedTitulo.isNotEmpty() && updatedDescripcion.isNotEmpty() && updatedTipo.isNotEmpty() && updatedEnlace.isNotEmpty() && updatedImagen.isNotEmpty()) {
                val recursoActualizado = Recurso(
                    id = resourceId ?: "",
                    titulo = updatedTitulo,
                    descripcion = updatedDescripcion,
                    tipo = updatedTipo,
                    enlace = updatedEnlace,
                    imagen = updatedImagen
                )

                lifecycleScope.launch {
                    try {
                        val updatedResource = RetrofitClient.retrofitService.updateResource(resourceId!!, recursoActualizado)
                        Toast.makeText(this@EditResourceActivity, "Recurso actualizado: ${updatedResource.titulo}", Toast.LENGTH_SHORT).show()
                        setResult(Activity.RESULT_OK)
                        finish()
                    } catch (e: Exception) {
                        Toast.makeText(this@EditResourceActivity, "Error al actualizar recurso", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@EditResourceActivity, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
