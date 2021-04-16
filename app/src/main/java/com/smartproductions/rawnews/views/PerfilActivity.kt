package com.smartproductions.rawnews.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityNoticiaDetailBinding
import com.smartproductions.rawnews.databinding.ActivityPerfilBinding
import com.smartproductions.rawnews.repository.FirebaseRepository

class PerfilActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityPerfilBinding
    private  lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseRepository: FirebaseRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mAuth = FirebaseAuth.getInstance()
        firebaseRepository = FirebaseRepository()

        //Seguridad por si no está logueado devolver a LoginActivity
        val currentUser = FirebaseAuth.getInstance().currentUser

        if(currentUser==null){
            val intentIrLoginActivity = Intent(this, LoginActivity::class.java)
            startActivity(intentIrLoginActivity)
            finish()
        }


        //Codigo prueba SING OUT Google
        val btnLogOut = binding.btnLogOut
        btnLogOut.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                signOut()
            }
        })


        //Boton ir hacia atras
        binding.btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        //Colocar datos de perfil
        binding.tvEmail.text = currentUser.email

        binding.btnDeleteAccount.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.btnDeleteAccount.visibility = View.GONE
                binding.clFormularioBorrarCuenta.visibility = View.VISIBLE

            }

        })


        binding.btnCancel.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(v: View?) {
                binding.btnDeleteAccount.visibility = View.VISIBLE
                binding.clFormularioBorrarCuenta.visibility = View.GONE

            }

        })

        binding.btnConfirmDeleteAccount.setOnClickListener(object  : View.OnClickListener{
            override fun onClick(v: View?) {
                firebaseRepository.deleteAccount(mAuth, binding.etPasswordDeleteAccount.text.toString(),binding.tilPasswordDeleteAccount)
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