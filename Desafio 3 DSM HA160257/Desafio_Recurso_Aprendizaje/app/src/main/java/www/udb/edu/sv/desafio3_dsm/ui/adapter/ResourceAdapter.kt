package www.udb.edu.sv.desafio3_dsm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import www.udb.edu.sv.desafio3_dsm.R
import www.udb.edu.sv.desafio3_dsm.model.Recurso

class ResourceAdapter(
    private val resources: MutableList<Recurso>,
    private val onDeleteClick: (Recurso, Int) -> Unit,
    private val onUpdateClick: (Recurso, Int) -> Unit
) : RecyclerView.Adapter<ResourceAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val tipoTextView: TextView = itemView.findViewById(R.id.tipoTextView)
        val enlaceTextView: TextView = itemView.findViewById(R.id.enlaceTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imagenRecurso)
        val deleteButton: Button = itemView.findViewById(R.id.btnDelete)
        val updateButton: Button = itemView.findViewById(R.id.buttonUpdateResource)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_resource, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resource = resources[position]

        holder.nameTextView.text = resource.titulo
        holder.descriptionTextView.text = resource.descripcion
        holder.tipoTextView.text = resource.tipo
        holder.enlaceTextView.text = resource.enlace

        Picasso.get()
            .load(resource.imagen)
            .placeholder(R.drawable.temporal_img)
            .error(R.drawable.img_error)
            .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
            .into(holder.imageView)

        holder.deleteButton.setOnClickListener {
            onDeleteClick(resource, position)
        }

        holder.updateButton.setOnClickListener {
            onUpdateClick(resource, position)
        }
    }

    override fun getItemCount(): Int {
        return resources.size
    }

    fun updateList(newResources: List<Recurso>) {
        resources.clear()
        resources.addAll(newResources)
        notifyDataSetChanged()
    }

}




