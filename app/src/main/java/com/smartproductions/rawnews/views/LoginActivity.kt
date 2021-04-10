package com.smartproductions.rawnews.views

import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var navContoller: NavController
    private lateinit var navOptions: NavOptions


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //Animaciones de nubes y texto logo
        val animationNubeArriba = AnimationUtils.loadAnimation(this, R.anim.nube_arriba)
        binding.nubearriba.animation = animationNubeArriba
        val animationNubeAbajo = AnimationUtils.loadAnimation(this, R.anim.nube_abajo)
        binding.nubeabajo.animation = animationNubeAbajo
        setAnimatedTextLogo(binding.tvLogo, "RAW\nNEWS", 150)
        navContoller = findNavController(R.id.contenedor_login)
        navOptions = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.enter_anim_1)
            .setExitAnim(R.anim.exit_anim_1)
            .setPopEnterAnim(R.anim.enter_anim_1)
            .setPopExitAnim(R.anim.exit_anim_1)
            .setPopUpTo(navContoller.graph.startDestination, false)
            .build()

    }





    private fun setAnimatedTextLogo(tvLogo: TextView, texto: CharSequence, delay: Long = 200){
        var index = 0
        val handler : Handler = object : Handler(){

        }
        tvLogo.text = ""
        var runnable: Runnable = object : Runnable {
            override fun run() {
                tvLogo.text = texto.subSequence(0, index++)
                if (index<=texto.length){
                    handler.postDelayed(this, delay)
                }
            }
        }

        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }


    fun mostrarFragmentRegistro() {
        navContoller.navigate(R.id.registerFragment, null, navOptions)
    }

    fun mostrarFragmentLogin() {
        navContoller.navigate(R.id.loginFragment, null, navOptions)
    }

    fun mostrarFragmentVerifyEmail() {
        navContoller.navigate(R.id.verifyEmailFragment, null, navOptions)
    }

    fun mostrarFragmentCheckPasswordForDeleteAccount() {
        navContoller.navigate(R.id.checkPasswordForDeleteAccountFragment, null, navOptions)
    }



}


