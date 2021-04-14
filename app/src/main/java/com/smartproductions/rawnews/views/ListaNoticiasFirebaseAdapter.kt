package com.smartproductions.rawnews.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.util.ImageLoadTask


class ListaNoticiasFirebaseAdapter(private val options: FirebaseRecyclerOptions<Noticia?>,
                                   private val firebaseRepository: FirebaseRepository,
                                   private val userUID: String): FirebaseRecyclerAdapter<Noticia, ListaNoticiasFirebaseAdapter.ViewHolder>(options) {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivElementoNoticia: ImageView
        val tvTitulo : TextView
        val tvDescripcion : TextView
        val btnLikeNoticia : ImageView
        val tvFecha : TextView
        val cvNoticia : CardView


        init {
            // Define click listener for the ViewHolder's View.

            tvTitulo = view.findViewById(R.id.tvTitulo)
            tvDescripcion = view.findViewById(R.id.tvDescripcion)
            btnLikeNoticia = view.findViewById(R.id.btnLikeNoticia)
            ivElementoNoticia = view.findViewById(R.id.ivElementoNoticia)
            tvFecha = view.findViewById(R.id.tvFecha)
            cvNoticia = view.findViewById(R.id.cvNoticia)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
                R.layout.elemento_noticia,
                parent,
                false
        )

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Noticia) {
        holder.tvTitulo.text = model.title
        holder.tvDescripcion.text = model.snippet
        holder.tvFecha.text = model.publishedAt.toString().substring(0, 20)
        holder.btnLikeNoticia.tag = "ic_heart_checked"
        holder.btnLikeNoticia.setImageResource(R.drawable.ic_heart_checked)
        holder.btnLikeNoticia.setColorFilter(
            ContextCompat.getColor(holder.btnLikeNoticia.context, R.color.secondary_Light),
            android.graphics.PorterDuff.Mode.SRC_IN
        )


        val imageLoadTask = ImageLoadTask(model.imageUrl, holder.ivElementoNoticia)
        imageLoadTask.execute()

        holder.btnLikeNoticia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                like(holder.btnLikeNoticia, holder.btnLikeNoticia.context, model)
            }
        })

        ViewCompat.setTransitionName(holder.ivElementoNoticia, "noticiaImagen$position")
        ViewCompat.setTransitionName(holder.tvTitulo, "noticiaTitulo$position")

        holder.cvNoticia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {


                //Ir a detailed view
                val intentDetailActivity = Intent(v?.context, NoticiaDetailActivity::class.java)
                //Ir a detailed view
                val bundleInfo = Bundle()
                bundleInfo.putParcelable("noticia", model)
                intentDetailActivity.putExtra("bundle", bundleInfo)
                v?.context?.startActivity(intentDetailActivity)

            }
        })
    }

    private fun like(btnLike: ImageView, context: Context, noticia: Noticia){
        when (btnLike.tag.toString()) {
            "ic_heart" -> {
                btnLike.setImageResource(R.drawable.ic_heart_checked)
                btnLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.secondary_Light),
                        android.graphics.PorterDuff.Mode.SRC_IN
                )
                btnLike.tag = "ic_heart_checked"
                val scaleAnimationAumento = ScaleAnimation(
                        0f,
                        1.2f,
                        0f,
                        1.2f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                )
                scaleAnimationAumento.interpolator = LinearInterpolator()
                scaleAnimationAumento.duration = 100
                scaleAnimationAumento.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val scaleAnimationDisminuir = ScaleAnimation(
                                1.2f,
                                1f,
                                1.2f,
                                1f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f
                        )
                        scaleAnimationDisminuir.interpolator = LinearInterpolator()
                        scaleAnimationDisminuir.duration = 30
                        btnLike.startAnimation(scaleAnimationDisminuir)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }
                })
                btnLike.startAnimation(scaleAnimationAumento)

                noticia.uuid?.let { firebaseRepository.likeNoticia(userUID, it, noticia, null) }

            }
            "ic_heart_checked" -> {
                btnLike.setImageResource(R.drawable.ic_heart);
                btnLike.setColorFilter(
                        ContextCompat.getColor(context, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN
                )
                btnLike.tag = "ic_heart"
                noticia.uuid?.let { firebaseRepository.unLikeNoticia(userUID, it, noticia) }
                val scaleAnimationDisminuir = ScaleAnimation(
                        1f,
                        1.2f,
                        1f,
                        1.2f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                )
                scaleAnimationDisminuir.interpolator = LinearInterpolator()
                scaleAnimationDisminuir.duration = 30
                scaleAnimationDisminuir.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val scaleAnimationAumento = ScaleAnimation(
                                1.2f,
                                1f,
                                1.2f,
                                1f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f,
                                Animation.RELATIVE_TO_SELF,
                                0.5f
                        )
                        scaleAnimationAumento.interpolator = LinearInterpolator()
                        scaleAnimationAumento.duration = 30
                        btnLike.startAnimation(scaleAnimationAumento)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {

                    }
                })
                btnLike.startAnimation(scaleAnimationDisminuir)


            }

        }
    }




}


