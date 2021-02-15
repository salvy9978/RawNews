package com.smartproductions.rawnews.views

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Codigo prueba SING OUT Google
        val btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signOut()
            }
        })
        //BOTTOM NAVIGATION VIEW--------------INICIO------------------
        val btmNavView = binding.btmNavView

        btmNavView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED


        btmNavView.setOnNavigationItemSelectedListener {
            item -> when(item.itemId){
                R.id.recientes ->{

                    Toast.makeText(this,"Recientes",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.categorias ->{
                    Toast.makeText(this,"Categories",Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.favoritos ->{
                    Toast.makeText(this,"Favoritos",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
                }
        }
        //BOTTOM NAVIGATION VIEW--------------FIN------------------


    }


    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mGoogleSignInClient.signOut().addOnCompleteListener(this, OnCompleteListener<Void?> {
                Toast.makeText(this,"Cerrar Sesión Correcto", Toast.LENGTH_SHORT).show()
            })
    }
}