package www.udb.edu.sv.desafio3_dsm.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import www.udb.edu.sv.desafio3_dsm.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón para ver recursos
        val buttonViewResources: Button = findViewById(R.id.btnViewResources)
        buttonViewResources.setOnClickListener {
            Toast.makeText(this, "Navegando a la lista de recursos", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ResourceActivity::class.java)
            startActivity(intent)
        }

        // agregar un recurso
        val buttonAddResource: FloatingActionButton = findViewById(R.id.btnAddResource)
        buttonAddResource.setOnClickListener {
            Toast.makeText(this, "Navegando a agregar recurso", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddResourceActivity::class.java)
            startActivity(intent)
        }

        // Botón para buscar recursos
        val buttonSeachResources: Button = findViewById(R.id.btnseachResources)
        buttonSeachResources.setOnClickListener {
            Toast.makeText(this, "Navegando a la búsqueda de recursos", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SearchResourceActivity::class.java)
            startActivity(intent)
        }
    }
}
