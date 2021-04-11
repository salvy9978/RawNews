package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.databinding.ActivityNoticiaDetailBinding
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.util.ImageLoadTask


class NoticiaDetailActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityNoticiaDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiaDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Recogemos los datos
        val bundle = intent.getBundleExtra("bundle")
        val noticia = bundle.getParcelable<Parcelable>("noticia") as Noticia


        //Boton ir hacia atras
        binding.btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })



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


        //Codigo prueba SING OUT Google
        val btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signOut()
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
}