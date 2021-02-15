package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private final val RC_SIGN_IN = 200



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        //Animaciones de nubes y texto logo
        val animationNubeArriba = AnimationUtils.loadAnimation(this, R.anim.nube_arriba)
        binding.nubearriba.animation = animationNubeArriba
        val animationNubeAbajo = AnimationUtils.loadAnimation(this, R.anim.nube_abajo)
        binding.nubeabajo.animation = animationNubeAbajo
        setAnimatedTextLogo(binding.tvLogo,"RAW\nNEWS", 150)

        // Set the dimensions of the sign-in button.

        val signInButton = binding.btnGoogle
        signInButton.setSize(SignInButton.SIZE_WIDE)

        signInButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                signIn()
            }

        })



    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
         // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)

        //TODO: pasar cuenta
        if(account!=null){
            val intentIrMenu = Intent(this, MainActivity::class.java)
            startActivity(intentIrMenu)
        }


    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }


    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            //TODO: pasar cuenta
            val intentIrMenu = Intent(this, MainActivity::class.java)
            startActivity(intentIrMenu)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            //TODO: iniciar activity main updateUI(null)
            Log.w("Google SignIn", "signInResult:failed code=" + e.statusCode)

        }
    }


    fun setAnimatedTextLogo(tvLogo:TextView, texto:CharSequence, delay:Long = 200){
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
        handler.postDelayed(runnable,delay)
    }







}