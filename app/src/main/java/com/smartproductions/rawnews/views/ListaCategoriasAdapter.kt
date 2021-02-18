package com.smartproductions.rawnews.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.models.ElementoCategoria

class ListaCategoriasAdapter(private val dataSet: MutableList<ElementoCategoria>): RecyclerView.Adapter<ListaCategoriasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvElementoCategoria: TextView
        val ivElementoCategoria : ImageView
        init {
            // Define click listener for the ViewHolder's View.
            tvElementoCategoria = view.findViewById(R.id.tvElementoCategoria)
            ivElementoCategoria = view.findViewById(R.id.ivElementoCategoria)

            view.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    Toast.makeText(view.context,tvElementoCategoria.text,Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_categoria, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvElementoCategoria.text = dataSet[position].nombreCategoria
        holder.ivElementoCategoria.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, dataSet[position].idFoto))



    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}