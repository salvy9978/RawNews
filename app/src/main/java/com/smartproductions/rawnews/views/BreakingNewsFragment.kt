package com.smartproductions.rawnews.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.models.ApiNoticias
import com.smartproductions.rawnews.models.NoticiasResponse
import com.smartproductions.rawnews.viewModels.BreakingNewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class BreakingNewsFragment : Fragment() {

    companion object {
        fun newInstance() = BreakingNewsFragment()
    }

    private lateinit var viewModel: BreakingNewsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.breaking_news_fragment, container, false)

        //Example Google Account --> val acct = GoogleSignIn.getLastSignedInAccount(activity)
        val tv = view.findViewById<TextView>(R.id.tvBreakingNews)
        // Solo descomentar para pruebas (consume recursos API) --> getTopNewsExample(tv)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BreakingNewsViewModel::class.java)
        // TODO: Use the ViewModel


    }



    //Esta es una prueba de EJEMPLO
    fun getTopNewsExample(tv:TextView){
        var retrofit : Retrofit? = null

        val apiNoticias = ApiNoticias(retrofit).getClient()
        val callGetTopNews = apiNoticias?.getTopNews()

        callGetTopNews?.enqueue(object : Callback<NoticiasResponse>{
            override fun onResponse(call: Call<NoticiasResponse>?, response: Response<NoticiasResponse>?) {
                val noticiasResponse = response?.body()
                tv.text = noticiasResponse?.data?.get(0)?.snippet
            }

            override fun onFailure(call: Call<NoticiasResponse>?, t: Throwable?) {
                tv.text = "Fail"
            }

        })


    }




}