package com.smartproductions.rawnews.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.CategoriesFragmentBinding
import com.smartproductions.rawnews.models.ElementoCategoria
import com.smartproductions.rawnews.viewModels.CategoriesViewModel

class CategoriesFragment : Fragment() {
    private var _binding: CategoriesFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

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

        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        //ANIMACIONES INICIALES------------------------------INICIO-----------------------------
        val animacionScaleFragment = AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.resize_elements
        )
        _binding!!.rvListaCategorias.animation = animacionScaleFragment

        //ANIMACIONES INICIALES------------------------------FIN-----------------------------



        // RECYCLER VIEW ---------------------INICIO--------------------------------
        //Categories general | science | sports | business | health | entertainment | tech | politics | food | travel
        val categoryGeneral = ElementoCategoria(
            getString(R.string.GENERAL),
            "general",
            R.color.primary_Light
        )
        val categoryScience = ElementoCategoria(
            getString(R.string.SCIENCE),
            "science",
            R.drawable.category_science
        )
        val categorySports = ElementoCategoria(
            getString(R.string.SPORTS),
            "sports",
            R.drawable.category_sports
        )
        val categoryBusiness = ElementoCategoria(
            getString(R.string.BUSINESS),
            "business",
            R.drawable.category_business
        )
        val categoryHealth = ElementoCategoria(
            getString(R.string.HEALTH),
            "health",
            R.drawable.category_health
        )
        val categoryEntertainment = ElementoCategoria(
            getString(R.string.ENTERTAINMENT),
            "entertainment",
            R.drawable.category_entertainment
        )
        val categoryTech = ElementoCategoria(
            getString(R.string.TECH),
            "tech",
            R.drawable.category_tech
        )
        val categoryPolitics = ElementoCategoria(
            getString(R.string.POLITICS),
            "politics",
            R.drawable.category_politics
        )
        val categoryFood = ElementoCategoria(
            getString(R.string.FOOD),
            "food",
            R.drawable.category_food
        )
        val categoryTravel = ElementoCategoria(
            getString(R.string.TRAVEL),
            "travel",
            R.drawable.category_travel
        )

        //Añadimos una lista de categorias que pasar al adaptador
        val listaCategorias = mutableListOf<ElementoCategoria>()
        listaCategorias.add(categoryGeneral)
        listaCategorias.add(categoryScience)
        listaCategorias.add(categorySports)
        listaCategorias.add(categoryBusiness)
        listaCategorias.add(categoryHealth)
        listaCategorias.add(categoryEntertainment)
        listaCategorias.add(categoryTech)
        listaCategorias.add(categoryPolitics)
        listaCategorias.add(categoryFood)
        listaCategorias.add(categoryTravel)


        val listaCategoriasAdapter = ListaCategoriasAdapter(
            listaCategorias,
            activityMain = activity as MainActivity
        )

        val rvListaCategorias =  _binding!!.rvListaCategorias
        rvListaCategorias.adapter = listaCategoriasAdapter
        rvListaCategorias.layoutManager = GridLayoutManager(requireContext(), 2)

        // RECYCLER VIEW ---------------------FIN--------------------------------



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}