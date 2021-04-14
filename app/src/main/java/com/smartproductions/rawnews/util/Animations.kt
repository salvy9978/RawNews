package com.smartproductions.rawnews.util

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.TextView
import com.smartproductions.rawnews.R

class Animations {

    fun likeCounterAnim(tvLikeCounter : TextView, likes:Int){

        if(likes == 1){
            tvLikeCounter.text = likes.toString() + " " + tvLikeCounter.context.getString(R.string.one_like)
        }else{
            tvLikeCounter.text = likes.toString() + " " + tvLikeCounter.context.getString(R.string.likes)
        }



        tvLikeCounter.visibility = View.VISIBLE

        //Animacion contador de LIKES
        val alphaAnimationAparecer = AlphaAnimation(0F, 1F)
        alphaAnimationAparecer.interpolator = LinearInterpolator()
        alphaAnimationAparecer.duration = 200
        alphaAnimationAparecer.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                val alphaAnimationDesaparecer = AlphaAnimation(1F, 0F)
                alphaAnimationDesaparecer.interpolator = LinearInterpolator()
                alphaAnimationDesaparecer.duration = 200
                alphaAnimationDesaparecer.startOffset = 2000

                alphaAnimationDesaparecer.setAnimationListener(object :  Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        tvLikeCounter.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })

                tvLikeCounter.startAnimation(alphaAnimationDesaparecer)
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
        tvLikeCounter.startAnimation(alphaAnimationAparecer)
    }

}