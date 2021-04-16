 package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemReselectedListener
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityMainBinding
    private lateinit var  nombreCategoria: String
    private var  keyCategoria: String = "general"
    private lateinit var navContoller : NavController
    private lateinit var navOptions: NavOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        nombreCategoria = getString(R.string.GENERAL)

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

        //Boton ir al perfil
        binding.btnProfile.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intentIrPerfil = Intent(applicationContext, PerfilActivity::class.java)
                startActivity(intentIrPerfil)
            }

        })






        //BOTTOM NAVIGATION VIEW--------------INICIO------------------
        val btmNavView = binding.btmNavView

        btmNavView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
        navContoller = findNavController(R.id.contenedor_main)

        navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.enter_anim)
                .setExitAnim(R.anim.exit_anim)
                .setPopEnterAnim(R.anim.enter_anim)
                .setPopExitAnim(R.anim.exit_anim)
                .setPopUpTo(navContoller.graph.startDestination, false)
                .build()


        btmNavView.setOnNavigationItemSelectedListener {

            item -> when(item.itemId){
            R.id.itBreakingNews -> {

                navContoller.navigate(R.id.breakingNewsFragment, null, navOptions)
                true
            }
            R.id.itCategories -> {
                navContoller.navigate(R.id.categoriesFragment, null, navOptions)
                true
            }
            R.id.itFavorites -> {
                navContoller.navigate(R.id.favoritesFragment, null, navOptions)
                true
            } else -> false
            }
        }


        //Evitar recrear el fragment al pulsar en el mismo boton del btmNavView

        btmNavView.setOnNavigationItemReselectedListener(OnNavigationItemReselectedListener {
            // do nothing here
        })

        //BOTTOM NAVIGATION VIEW--------------FIN------------------


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


    fun mostrarFragmentNoticiasByCategorias(nombreCategoria : String, keyCategoria : String){
        this.nombreCategoria = nombreCategoria
        this.keyCategoria = keyCategoria
        navContoller.navigate(R.id.mostrarNoticiasByCategoriaFragment, null, navOptions)
    }

    fun mostrarFragmentCategorias(){
        navContoller.navigate(R.id.categoriesFragment, null, navOptions)
    }

    fun getNombreCategoria() : String{
        return this.nombreCategoria
    }

    fun getKeyCategoria() : String{
        return this.keyCategoria
    }




}