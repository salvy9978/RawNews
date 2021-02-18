package com.smartproductions.rawnews.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.smartproductions.rawnews.viewModels.CategoriesViewModel
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.CategoriesFragmentBinding
import com.smartproductions.rawnews.models.ElementoCategoria

class CategoriesFragment : Fragment() {
    private var _binding: CategoriesFragmentBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        //ANIMACIONES INICIALES------------------------------INICIO-----------------------------
        val animacionScaleFragment = AnimationUtils.loadAnimation(requireContext(),R.anim.resize_elements)
        _binding!!.rvListaCategorias.animation = animacionScaleFragment

        //ANIMACIONES INICIALES------------------------------FIN-----------------------------



        // RECYCLER VIEW ---------------------INICIO--------------------------------
        //Categories general | science | sports | business | health | entertainment | tech | politics | food | travel
        val categoryGeneral = ElementoCategoria("GENERAL", R.color.primary_Light)
        val categoryScience = ElementoCategoria("SCIENCE", R.drawable.category_science)
        val categorySports = ElementoCategoria("SPORTS", R.drawable.category_sports)
        val categoryBusiness = ElementoCategoria("BUSINESS", R.drawable.category_business)
        val categoryHealth = ElementoCategoria("HEALTH", R.drawable.category_health)
        val categoryEntertainment = ElementoCategoria("ENTERTAINMENT", R.drawable.category_entertainment)
        val categoryTech = ElementoCategoria("TECH", R.drawable.category_tech)
        val categoryPolitics = ElementoCategoria("POLITICS", R.drawable.category_politics)
        val categoryFood = ElementoCategoria("FOOD", R.drawable.category_food)
        val categoryTravel = ElementoCategoria("TRAVEL", R.drawable.category_travel)

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


        val listaCategoriasAdapter = ListaCategoriasAdapter(listaCategorias)

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

}