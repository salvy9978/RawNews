package com.smartproductions.rawnews.views

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityNoticiaDetailBinding
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.util.ImageLoadTask


class NoticiaDetailActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityNoticiaDetailBinding

    private  lateinit var firebaseRepository : FirebaseRepository

    private  lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiaDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseRepository = FirebaseRepository()
        mAuth = FirebaseAuth.getInstance()

        //Seguridad por si no está logueado devolver a LoginActivity
        val currentUser = mAuth.currentUser

        if(currentUser==null){
            val intentIrLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentIrLoginActivity)
            finish()
        }
        /*
        --------------------------------------------------------------------------------------------
        --------------------------------------------------------------------------------------------
        NOTA: este fragmento de código no se usa debido a que la API no envía una descripción
        de la noticia completa y estéticamente y funcionalmente no queda bien
        --------------------------------------------------------------------------------------------
        --------------------------------------------------------------------------------------------

        //Boton ir hacia atras
        binding.btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        //Recogemos los datos
        val bundle = intent.getBundleExtra("bundle")
        val noticia = bundle.getParcelable<Parcelable>("noticia") as Noticia




        //Animacion
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName: String? = bundle.getString("NOMBRE_TRANSICION_IMAGEN_NOTICIA")
            val tituloTransitionName: String? = bundle.getString("NOMBRE_TRANSICION_TITULO_NOTICIA")


            binding.ivFotoNoticia.transitionName = imageTransitionName
            binding.tvTituloNoticia.transitionName = tituloTransitionName
        }


        //Y los colocamos en su sitio
        binding.tvTituloNoticia.text = noticia.title
        val imageLoadTask = ImageLoadTask(noticia.imageUrl, binding.ivFotoNoticia)
        imageLoadTask.execute()
        binding.tvFechaNoticia.text = noticia.publishedAt.toString().subSequence(0, 20)
        binding.tvDescripcionNoticia.text = noticia.description
        */

        //Codigo prueba SING OUT Google
        val btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signOut()
            }
        })

        //Boton ir al perfil
        binding.btnProfile.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intentIrPerfil = Intent(applicationContext, PerfilActivity::class.java)
                startActivity(intentIrPerfil)
            }

        })

        //Boton ir hacia atras
        binding.btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        //Recogemos los datos
        val bundle = intent.getBundleExtra("bundle")
        val noticia = bundle.getParcelable<Parcelable>("noticia") as Noticia
        binding.wvNoticia.loadUrl(noticia.url)
        binding.tvFechaNoticia.text = noticia.publishedAt.toString().subSequence(0, 20)

        if(noticia.isLiked){
            binding.btnLikeNoticia.setImageResource(R.drawable.ic_heart_checked)
            binding.btnLikeNoticia.setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.secondary_Light),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }

        //Like button
        binding.btnLikeNoticia.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                like(binding.btnLikeNoticia, applicationContext, noticia, null)
            }

        })


    }






    private fun signOut() {

        FirebaseAuth.getInstance().signOut()


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mGoogleSignInClient.signOut().addOnCompleteListener(this, OnCompleteListener<Void?> {
            val intentRedirectToLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentRedirectToLogin)
            finish()
        })

    }

    private fun like(btnLike: ImageView, context: Context, noticia: Noticia, tvLikesContador : TextView?){

        if(!noticia.isLiked){
            btnLike.setImageResource(R.drawable.ic_heart_checked)
            btnLike.setColorFilter(
                ContextCompat.getColor(context, R.color.secondary_Light),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
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

            noticia.uuid?.let { firebaseRepository.likeNoticia(mAuth.currentUser.uid, it, noticia, tvLikesContador) }

            noticia.isLiked = true
        }else{
            btnLike.setImageResource(R.drawable.ic_heart);
            btnLike.setColorFilter(
                ContextCompat.getColor(context, R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
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
            noticia.uuid?.let { firebaseRepository.unLikeNoticia(mAuth.currentUser.uid, it, noticia) }
            noticia.isLiked = false
        }


    }


}