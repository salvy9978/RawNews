package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.smartproductions.rawnews.databinding.VerifyEmailFragmentBinding


class VerifyEmailFragment : Fragment() {
    private var _binding: VerifyEmailFragmentBinding? = null

    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = VerifyEmailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if(user!=null){
            binding.emailToVerify.text = user.email
        }

        binding.btnLoginOtherAccount.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                FirebaseAuth.getInstance().signOut()
                val loginActivity = activity as LoginActivity
                loginActivity.mostrarFragmentLogin()
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
            checkVerified(currentUser, 500)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUI(user : FirebaseUser){
       user.reload()
        if(user.isEmailVerified){
            val loginActivity = activity as LoginActivity
            loginActivity.mostrarFragmentCheckPasswordForDeleteAccount()


        }
    }

    private fun checkVerified(user : FirebaseUser, delay :Long){
        val handler : Handler = object : Handler(){}

        var runnable: Runnable = object : Runnable {
            override fun run() {
                user.reload()
                if (!user.isEmailVerified){
                    handler.postDelayed(this, delay)
                }else{
                    updateUI(user)
                }
            }
        }
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }


}