package com.smartproductions.rawnews.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.databinding.BreakingNewsFragmentBinding
import com.smartproductions.rawnews.databinding.CategoriesFragmentBinding
import com.smartproductions.rawnews.repository.Repository
import com.smartproductions.rawnews.viewModels.BreakingNewsViewModel
import com.smartproductions.rawnews.viewModels.BreakingnewsViewModelFactory


class BreakingNewsFragment : Fragment() {

    private var _binding : BreakingNewsFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = BreakingNewsFragment()
    }

    private lateinit var viewModel: BreakingNewsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = BreakingNewsFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        //Example Google Account --> val acct = GoogleSignIn.getLastSignedInAccount(activity)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = Repository()
        val viewModelFactory = BreakingnewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BreakingNewsViewModel::class.java)

        setNews(binding!!.tvBreakingNews)

    }

    fun setNews(tv : TextView){
        viewModel.getTopNews()
        viewModel.lvdNewsResponse.observe(viewLifecycleOwner, { response ->
            if(response.isSuccessful){
               tv.text = response.body()?.data?.get(0)?.imageUrl
            }
        })
    }






}