package www.udb.edu.sv.desafio3_dsm.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import www.udb.edu.sv.desafio3_dsm.R
import www.udb.edu.sv.desafio3_dsm.model.Recurso
import www.udb.edu.sv.desafio3_dsm.network.RetrofitClient
import www.udb.edu.sv.desafio3_dsm.ui.adapter.ResourceAdapter

class SearchResourceActivity : AppCompatActivity() {

    private lateinit var editTextId: EditText
    private lateinit var buttonBuscar: Button
    private lateinit var recyclerViewResultado: RecyclerView
    private lateinit var recursoAdapter: ResourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_resource)

        editTextId = findViewById(R.id.editTextId)
        buttonBuscar = findViewById(R.id.buttonBuscar)
        recyclerViewResultado = findViewById(R.id.recyclerViewSearchResult)

        recyclerViewResultado.layoutManager = LinearLayoutManager(this)
        recursoAdapter = ResourceAdapter(mutableListOf(), { recurso, position ->
        }, { recurso, position ->
        })
        recyclerViewResultado.adapter = recursoAdapter

        buttonBuscar.setOnClickListener {
            val id = editTextId.text.toString()
            if (id.isNotEmpty()) {
                buscarRecurso(id)
            } else {
                Toast.makeText(this, "Por favor, ingresa un ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun buscarRecurso(id: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.retrofitService.getResource(id)
                if (response.isSuccessful && response.body() != null) {
                    val recurso: Recurso? = response.body()
                    mostrarResultado(recurso)
                } else {
                    Toast.makeText(this@SearchResourceActivity, "Recurso no encontrado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@SearchResourceActivity, "Error al buscar el recurso: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mostrarResultado(recurso: Recurso?) {
        recurso?.let {
            recursoAdapter.updateList(listOf(it))
        }
    }
}
