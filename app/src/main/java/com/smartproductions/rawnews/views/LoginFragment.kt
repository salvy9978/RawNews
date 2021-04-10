package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.LoginFragmentBinding
import com.smartproductions.rawnews.repository.FirebaseRepository


class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private final val RC_SIGN_IN = 200
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root



        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) .requestIdToken(
            getString(
                R.string.default_web_client_id
            )
        ).requestEmail().build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // Set the dimensions of the sign-in button.

        val signInButton = binding.btnGoogle
        signInButton.setSize(SignInButton.SIZE_WIDE)

        signInButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                signIn()
            }

        })



        mAuth = FirebaseAuth.getInstance()

        //Boton ir a registrar
        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val loginActivity = activity as LoginActivity
                loginActivity.mostrarFragmentRegistro()
            }

        })

        //Boton efectuar login
        val btnLogin = binding.btnLogin
        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                signIn(binding.etUsername.text.toString(), binding.etPassword.text.toString())
            }

        })


        return view
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val currentUser = mAuth.currentUser

        if(currentUser!=null){

            updateUI(currentUser)
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
            val account = completedTask.getResult(ApiException::class.java)!!

            // Signed in successfully, show authenticated UI.
            firebaseAuthWithGoogle(account.idToken!!)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.


            //TO-DO: iniciar activity main updateUI(null)
            Log.w("Google SignIn", "signInResult:failed code=" + e.statusCode)

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)


        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                requireActivity(),
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        updateUI(mAuth.currentUser)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Firebase Auth", "signInWithCredential:failure", task.exception)

                    }

                })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signIn(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    //Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), R.string.login_fail,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

    }

    private fun updateUI(currentUser: FirebaseUser){
        if(currentUser.isEmailVerified){

            val loginActivity = activity as LoginActivity
            loginActivity.mostrarFragmentCheckPasswordForDeleteAccount()


        }else{
            val loginActivity = activity as LoginActivity
            loginActivity.mostrarFragmentVerifyEmail()
        }

    }

}


