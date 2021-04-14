package com.smartproductions.rawnews.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.CategoriesFragmentBinding
import com.smartproductions.rawnews.databinding.RegisterFragmentBinding
import com.smartproductions.rawnews.models.ElementoCategoria
import com.smartproductions.rawnews.util.ValidateInputText
import com.smartproductions.rawnews.viewModels.CategoriesViewModel
import java.lang.Exception


class RegisterFragment : Fragment() {
    private var _binding: RegisterFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance()

        //Boton login here
        val btnLoginHere = binding.btnLoginHere

        btnLoginHere.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val loginActivity = activity as LoginActivity
                loginActivity.mostrarFragmentLogin()
            }

        })

        //Boton para efectuar registro
        val btnRegister = binding.btnRegister

        btnRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                registrar(binding.etUsername.text.toString(),
                        binding.etPassword.text.toString(),
                        binding.usernameTextInputLayout,
                        binding.passwordTextInputLayout)
            }

        })



        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun registrar(email:String, password:String, textInputLayoutEmail: TextInputLayout, textInputLayoutPassword: TextInputLayout){

        var valid = true

        val validEmail = ValidateInputText().validateEmail(email, textInputLayoutEmail.context)


        if(!validEmail.first){
            textInputLayoutEmail.error = validEmail.second
            valid = validEmail.first

        }

        val validPassword = ValidateInputText().validatePassword(password, textInputLayoutPassword.context)

        if(!validPassword.first){
            textInputLayoutPassword.error = validPassword.second
            valid = validPassword.first

        }

        if(valid){
            try {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success")
                                val user = mAuth.currentUser
                                Toast.makeText(requireContext(), R.string.register_correct,
                                        Toast.LENGTH_SHORT).show()
                                sendEmailVerification()
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.exception)
                                Toast.makeText(requireContext(), R.string.register_fail,
                                        Toast.LENGTH_SHORT).show()

                            }


                        }
            }catch (e: Exception){
                Toast.makeText(requireContext(),R.string.register_fail, Toast.LENGTH_SHORT).show()
            }
        }




    }


    private fun sendEmailVerification() {

        val user = mAuth.currentUser!!
        user.sendEmailVerification()
                .addOnCompleteListener(requireActivity()) { task ->
                    val loginActivity = activity as LoginActivity
                    loginActivity.mostrarFragmentVerifyEmail()
                }

    }






}