package com.smartproductions.rawnews.views

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.MostrarNoticiasByCategoriaFragmentBinding
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.repository.Repository
import com.smartproductions.rawnews.viewModels.MostrarNoticiasByCategoriaViewModel
import com.smartproductions.rawnews.viewModels.MostrarNoticiasByCategoriaViewModelFactory
import java.util.*


class MostrarNoticiasByCategoriaFragment : Fragment() {
    private var _binding : MostrarNoticiasByCategoriaFragmentBinding? = null

    private val binding get() = _binding!!

    private var page = 1

    private lateinit var language : String

    private lateinit var viewModel: MostrarNoticiasByCategoriaViewModel

    private lateinit var mAuth: FirebaseAuth

    private lateinit var activityMain : MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val activityMain = activity as MainActivity
            activityMain.mostrarFragmentCategorias()
        }

        // The callback can be enabled or disabled here or in the lambda
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MostrarNoticiasByCategoriaFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        //Example Google Account --> val acct = GoogleSignIn.getLastSignedInAccount(activity)

        language = Locale.getDefault().language.toLowerCase()

        mAuth = FirebaseAuth.getInstance()

        activityMain = activity as MainActivity

        binding.rvListaNoticiasByCategoria.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        viewModel.getNewsByCategory(page, language, activityMain.getKeyCategoria())
                        ++page
                    }

                }
            }
        })


        binding.tvNombreCategoria.text = activityMain.getNombreCategoria()

        binding.btnBack.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                activityMain.mostrarFragmentCategorias()
            }
        })



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = Repository()
        val viewModelFactory = MostrarNoticiasByCategoriaViewModelFactory(repository)
        var listaNoticiasAdapter : ListaNoticiasAdapter
        var rvListaNoticias : RecyclerView

        viewModel = ViewModelProvider(this, viewModelFactory).get(
            MostrarNoticiasByCategoriaViewModel::class.java
        )

        viewModel.getNewsByCategory(page, language, activityMain.getKeyCategoria())
        ++page

        val listaNoticias : MutableList<Noticia> = arrayListOf()
        val firebaseRepository = FirebaseRepository()
        listaNoticiasAdapter = ListaNoticiasAdapter( listaNoticias, firebaseRepository,mAuth.currentUser.uid)
        rvListaNoticias =  binding.rvListaNoticiasByCategoria
        rvListaNoticias.adapter = listaNoticiasAdapter
        rvListaNoticias.layoutManager = LinearLayoutManager(requireContext())


        viewModel.lvdNewsResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {

                if (response.body()?.data != null) {
                    val position = listaNoticias.size
                    listaNoticias.addAll(response.body()?.data!!)
                    listaNoticiasAdapter.notifyItemRangeInserted(
                        position,
                        listaNoticias.size - position
                    )

                    if (page == 2) {
                        //ANIMACIONES INICIALES------------------------------INICIO--------------------------
                        val animacionScaleFragment = AnimationUtils.loadAnimation(
                            requireContext(),
                            R.anim.resize_elements
                        )
                        binding.rvListaNoticiasByCategoria.animation = animacionScaleFragment
                        //ANIMACIONES INICIALES------------------------------FIN-----------------------------
                    }

                }

            }
        })



    }
}