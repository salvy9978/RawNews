package com.smartproductions.rawnews.repository



import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.util.Hash
import java.security.MessageDigest


private lateinit var database: DatabaseReference


class FirebaseRepository (){
    init {
        database = Firebase.database.reference
    }



    fun setPasswordForDeleteAccount(uid: String, password: String){
        database.child("users").child(uid).child("passwordForDeleteAccount").setValue(Hash().sha256(password))

    }

    fun likeNoticia(uidUsuario: String, uidNoticia:String, noticia: Noticia){
        database.child("users").child(uidUsuario).child("noticiasLiked").child(uidNoticia).setValue(noticia)

        //database.child("noticias").child("noticiasLiked").child(uidNoticia).setValue(noticia)
    }

    fun unLikeNoticia(uidUsuario: String, uidNoticia: String){
        database.child("users").child(uidUsuario).child("noticiasLiked").child(uidNoticia).removeValue()
    }



}