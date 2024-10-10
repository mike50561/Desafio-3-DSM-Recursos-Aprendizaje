package www.udb.edu.sv.desafio3_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import www.udb.edu.sv.desafio3_dsm.R
import www.udb.edu.sv.desafio3_dsm.model.Recurso
import www.udb.edu.sv.desafio3_dsm.network.RetrofitClient
import www.udb.edu.sv.desafio3_dsm.ui.adapter.ResourceAdapter

class ResourceActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var resourceAdapter: ResourceAdapter
    private val resources = mutableListOf<Recurso>()

    private val EDIT_RESOURCE_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)

        // Inicializar RecyclerView y el Adaptador
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        resourceAdapter = ResourceAdapter(resources, { resource, position ->
            eliminarRecurso(resource, position)
        }, { resource, position ->
            abrirEditarRecurso(resource)
        })

        recyclerView.adapter = resourceAdapter
        lifecycleScope.launch {
            cargarRecursos()
        }
    }
    private suspend fun cargarRecursos() {
        try {
            val response = RetrofitClient.retrofitService.getAllResources()
            if (response.isSuccessful) {
                val resourceList = response.body()
                Log.d("ResourceActivity", "Recursos cargados: $resourceList")

                if (resourceList != null) {
                    resources.clear()
                    resources.addAll(resourceList)
                    resourceAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "La lista de recursos está vacía", Toast.LENGTH_SHORT).show()
                }
            } else {
                val errorMessage = "Error al cargar los recursos: Código ${response.code()}, Mensaje: ${response.message()}"
                Log.e("ResourceActivity", errorMessage)
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            val errorMessage = "Excepción al cargar los recursos: ${e.message}"
            Log.e("ResourceActivity", errorMessage, e)
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    // Función para eliminar recurso
    private fun eliminarRecurso(resource: Recurso, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar Eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar este recurso?")

        builder.setPositiveButton("Sí") { dialog, _ ->
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.retrofitService.deleteResource(resource.id)
                    if (response.isSuccessful) {
                        resources.removeAt(position)
                        resourceAdapter.notifyItemRemoved(position)
                        Toast.makeText(this@ResourceActivity, "Recurso eliminado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ResourceActivity, "Error al eliminar el recurso: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ResourceActivity, "Error al eliminar el recurso: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    // Método para abrir la actividad
    private fun abrirEditarRecurso(resource: Recurso) {
        val intent = Intent(this, EditResourceActivity::class.java).apply {
            putExtra("id", resource.id)
            putExtra("titulo", resource.titulo)
            putExtra("descripcion", resource.descripcion)
            putExtra("tipo", resource.tipo)
            putExtra("enlace", resource.enlace)
            putExtra("imagen", resource.imagen)
        }
        startActivityForResult(intent, EDIT_RESOURCE_REQUEST_CODE)
    }

    // Manejar el resultado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_RESOURCE_REQUEST_CODE && resultCode == RESULT_OK) {
            lifecycleScope.launch {
                cargarRecursos()
            }
        }
    }
}