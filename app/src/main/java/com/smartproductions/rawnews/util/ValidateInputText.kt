package com.smartproductions.rawnews.util

import android.content.Context
import android.content.res.Resources
import com.smartproductions.rawnews.R
import java.util.regex.Pattern

class ValidateInputText {

    fun validateEmail(email: String?, context: Context): Pair<Boolean, String>{
        var respuesta = Pair<Boolean, String>(true, "")

        if(email==null || email==""){
            respuesta = Pair<Boolean, String>(false, context.getString(R.string.introduce_email))
            return respuesta
        }
        val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$"

        val pat: Pattern = Pattern.compile(emailRegex)


        if(!pat.matcher(email).matches()){
            respuesta = Pair<Boolean, String>(false, context.getString(R.string.not_valid_email))
            return respuesta
        }


        return respuesta
    }


    fun validatePassword(password: String?, context: Context): Pair<Boolean, String>{
        var respuesta = Pair<Boolean, String>(true, "")

        if(password==null || password==""){
            respuesta = Pair<Boolean, String>(false, context.getString(R.string.introduce_password))
            return respuesta
        }



        if(password.length<8){
            respuesta = Pair<Boolean, String>(false, context.getString(R.string.minium_lenght_password))
            return respuesta
        }


        return respuesta
    }


}