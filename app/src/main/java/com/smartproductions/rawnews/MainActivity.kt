package com.smartproductions.rawnews

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.smartproductions.rawnews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signOut()
            }
        })



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