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
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.BreakingNewsFragmentBinding
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.repository.FirebaseRepository
import com.smartproductions.rawnews.repository.Repository
import com.smartproductions.rawnews.viewModels.BreakingNewsViewModel
import com.smartproductions.rawnews.viewModels.BreakingNewsViewModelFactory
import java.util.*


class BreakingNewsFragment : Fragment() {

    private var _binding : BreakingNewsFragmentBinding? = null

    private val binding get() = _binding!!

    private var page = 1

    private lateinit var locale : String

    private lateinit var viewModel: BreakingNewsViewModel

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This callback will only be called when MyFragment is at least Started.
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            val activityMain = activity as MainActivity
            activityMain.finish()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        //Example Google Account --> val acct = GoogleSignIn.getLastSignedInAccount(activity)

        locale = Locale.getDefault().country.toLowerCase()

        mAuth = FirebaseAuth.getInstance()

        binding.rvListaNoticias.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        viewModel.getTopNews(page, locale)
                        ++page
                    }

                }
            }
        })




        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = Repository()
        val viewModelFactory = BreakingNewsViewModelFactory(repository)
        var listaNoticiasAdapter : ListaNoticiasAdapter
        var rvListaNoticias : RecyclerView

        viewModel = ViewModelProvider(this, viewModelFactory).get(BreakingNewsViewModel::class.java)

        //setNews(binding!!.tvBreakingNews)
        viewModel.getTopNews(page, locale)
        ++page

        val listaNoticias : MutableList<Noticia> = arrayListOf()
        val firebaseRepository = FirebaseRepository()
        listaNoticiasAdapter = ListaNoticiasAdapter(listaNoticias, firebaseRepository, mAuth.currentUser.uid)
        rvListaNoticias =  binding.rvListaNoticias
        rvListaNoticias.adapter = listaNoticiasAdapter
        rvListaNoticias.layoutManager = LinearLayoutManager(requireContext())


        viewModel.lvdNewsResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {

                if (response.body()?.data != null) {
                    val position = listaNoticias.size
                    listaNoticias.addAll(response.body()?.data!!)
                    listaNoticiasAdapter.notifyItemRangeInserted(position, listaNoticias.size - position)

                    if (page == 2) {
                        //ANIMACIONES INICIALES------------------------------INICIO--------------------------
                        val animacionScaleFragment = AnimationUtils.loadAnimation(requireContext(), R.anim.resize_elements)
                        binding.rvListaNoticias.animation = animacionScaleFragment
                        //ANIMACIONES INICIALES------------------------------FIN-----------------------------
                    }

                }

            }
        })



    }








}