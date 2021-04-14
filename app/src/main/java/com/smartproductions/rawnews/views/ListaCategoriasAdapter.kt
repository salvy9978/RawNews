package com.smartproductions.rawnews.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.models.ElementoCategoria
import com.smartproductions.rawnews.models.Noticia

class ListaCategoriasAdapter(private val dataSet: List<ElementoCategoria>, private val activityMain: MainActivity): RecyclerView.Adapter<ListaCategoriasAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvElementoCategoria: TextView
        val ivElementoCategoria : ImageView
        val cvCategoria : CardView

        init {
            // Define click listener for the ViewHolder's View.
            tvElementoCategoria = view.findViewById(R.id.tvElementoCategoria)
            ivElementoCategoria = view.findViewById(R.id.ivElementoCategoria)
            cvCategoria = view.findViewById(R.id.cvCategoria)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.elemento_categoria, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvElementoCategoria.text = dataSet[position].nombreCategoria
        holder.ivElementoCategoria.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, dataSet[position].idFoto))


        holder.cvCategoria.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                activityMain.mostrarFragmentNoticiasByCategorias(dataSet[position].nombreCategoria, dataSet[position].key)
            }

        })


    }

    override fun getItemCount(): Int {
        return dataSet.size
    }




}