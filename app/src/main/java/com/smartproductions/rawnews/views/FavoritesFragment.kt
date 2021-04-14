package com.smartproductions.rawnews.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.FavoritesFragmentBinding
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.viewModels.FavoritesViewModel


class FavoritesFragment : Fragment() {

    private var _binding: FavoritesFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavoritesViewModel

    private lateinit var listaNoticiasFirebaseAdapter: ListaNoticiasFirebaseAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val activityMain = activity as MainActivity
            activityMain.finish()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val firebaseRepository = FirebaseRepository()
        val mAuth = FirebaseAuth.getInstance()

        val options = firebaseRepository.getOptionFavoriteQuery(mAuth.currentUser.uid)
        val rvNoticiasLiked = binding.rvListaNoticiasFavoritas
        rvNoticiasLiked.layoutManager = LinearLayoutManager(requireContext())
        listaNoticiasFirebaseAdapter = ListaNoticiasFirebaseAdapter(
            options = options,
            firebaseRepository = firebaseRepository,
            userUID = mAuth.currentUser.uid
        )
        rvNoticiasLiked.adapter = listaNoticiasFirebaseAdapter



        return view
    }

    override fun onStart() {
        super.onStart()
        listaNoticiasFirebaseAdapter.startListening()

        listaNoticiasFirebaseAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                listaNoticiasFirebaseAdapter.notifyDataSetChanged()
                //ANIMACIONES INICIALES------------------------------INICIO--------------------------
                val animacionScaleFragment = AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.resize_elements
                )
                binding.rvListaNoticiasFavoritas.animation = animacionScaleFragment
                //ANIMACIONES INICIALES------------------------------FIN-----------------------------
                listaNoticiasFirebaseAdapter.unregisterAdapterDataObserver(this)
            }
        })




    }

    override fun onStop() {
        super.onStop()
        listaNoticiasFirebaseAdapter.stopListening()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
    }

}