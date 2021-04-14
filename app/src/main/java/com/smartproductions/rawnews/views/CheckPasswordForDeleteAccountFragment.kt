package com.smartproductions.rawnews.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.CheckPasswordForDeleteAccountFragmentBinding
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.util.ValidateInputText


class CheckPasswordForDeleteAccountFragment : Fragment() {
    private var _binding: CheckPasswordForDeleteAccountFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseRepository: FirebaseRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CheckPasswordForDeleteAccountFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()
        firebaseRepository = FirebaseRepository()

        val database = Firebase.database.reference
        val dbRef: DatabaseReference = database.child("users").child(mAuth.currentUser.uid).child("passwordForDeleteAccount")


        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val intentIrMenu = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intentIrMenu)
                    activity?.finish()
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.llFormPassword.visibility = View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(), R.string.cant_access_for_info,
                    Toast.LENGTH_SHORT
                ).show()

                val loginActivity = activity as LoginActivity
                loginActivity.mostrarFragmentLogin()

            }
        })

        binding.btnSend.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                if (sendPassword(mAuth.currentUser.uid, binding.tilPassword, binding.etPassword.text.toString())){
                    val intentIrMenu = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intentIrMenu)
                    activity?.finish()
                }

            }

        })

        return view
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendPassword(uid:String, passwordField: TextInputLayout, password: String): Boolean{

        var valid = true
        val validPassword = ValidateInputText().validatePassword(password, passwordField.context)

        if(!validPassword.first){
            passwordField.error = validPassword.second
            valid = validPassword.first
        }else{
            firebaseRepository.setPasswordForDeleteAccount(uid,password)
        }


        return valid
    }


}