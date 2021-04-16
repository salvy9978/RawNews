package com.smartproductions.rawnews.repository



import android.content.Intent
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.smartproductions.rawnews.R
import com.smartproductions.rawnews.models.Noticia
import com.smartproductions.rawnews.util.Animations
import com.smartproductions.rawnews.util.Hash
import com.smartproductions.rawnews.views.LoginActivity
import com.smartproductions.rawnews.views.MainActivity
import java.util.*
import kotlin.collections.HashMap


private lateinit var database: DatabaseReference


class FirebaseRepository (){
    init {
        database = Firebase.database.reference
    }


    fun setPasswordForDeleteAccount(uid: String, password: String){
        database.child("users").child(uid).child("passwordForDeleteAccount").setValue(Hash().sha256(password))

    }

    fun likeNoticia(uidUsuario: String, uidNoticia:String, noticia: Noticia, tvLikesContador : TextView?){
        val calendar = Calendar.getInstance()
        calendar.time = noticia.publishedAt

        database.child("users").child(uidUsuario).child("noticiasLiked").child(uidNoticia).setValue(noticia)
        database.child("users")
            .child(uidUsuario)
            .child("noticiasLikedIndice")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())
            .child(uidNoticia )
            .setValue("liked")


        val dbRef = database.child("noticiasLiked")
                .child((if (noticia.locale!=null) noticia.locale else "global").toString())
                .child(calendar.get(Calendar.YEAR).toString())
                .child(calendar.get(Calendar.MONTH).toString())
                .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
                .child(calendar.get(Calendar.HOUR_OF_DAY).toString())
                .child(uidNoticia)
                .child("likes")

        val dbRef2 = database.child("noticiasLiked")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())


        dbRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nuevosLikes: Map<String, Int>
                    nuevosLikes = HashMap<String, Int>()
                    nuevosLikes.put("likes", (snapshot.value.toString().toInt()) + 1)
                    dbRef2.child(uidNoticia).updateChildren(nuevosLikes)

                    if (tvLikesContador != null) {
                        Animations().likeCounterAnim(tvLikesContador, (snapshot.value.toString().toInt()) + 1)

                    }
                } else {
                    dbRef.setValue(1)
                    if (tvLikesContador != null) {
                        Animations().likeCounterAnim(tvLikesContador, 1)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    }





    fun unLikeNoticia(uidUsuario: String, uidNoticia: String, noticia: Noticia){
        database.child("users").child(uidUsuario).child("noticiasLiked").child(uidNoticia).removeValue()

        val calendar = Calendar.getInstance()
        calendar.time = noticia.publishedAt

        database.child("users")
            .child(uidUsuario)
            .child("noticiasLikedIndice")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())
            .child(uidNoticia).removeValue()

        val dbRef = database.child("noticiasLiked")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())
            .child(uidNoticia)
            .child("likes")

        val dbRef2 = database.child("noticiasLiked")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())


        dbRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nuevosLikes : Map<String, Int>
                    nuevosLikes = HashMap<String, Int>()
                    val nuevosLikesInt = (snapshot.value.toString().toInt())-1

                    if(nuevosLikesInt>0){
                        nuevosLikes.put("likes", nuevosLikesInt)
                    }else{
                        nuevosLikes.put("likes", 0)
                    }
                    dbRef2.child(uidNoticia).updateChildren(nuevosLikes)

                } else {
                    dbRef.setValue(0)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



    }

    fun getOptionFavoriteQuery(uidUsuario : String): FirebaseRecyclerOptions<Noticia?> {
        val query = database.child("users").child(uidUsuario).child("noticiasLiked")
        return FirebaseRecyclerOptions.Builder<Noticia>().setQuery(query, Noticia::class.java).build()
    }


    fun checkIfNoticiaIsLiked(btnLike : ImageView, uidUsuario: String, uidNoticia: String, noticia: Noticia){

        val calendar = Calendar.getInstance()
        calendar.time = noticia.publishedAt


        val dbRef = database.child("users")
            .child(uidUsuario)
            .child("noticiasLikedIndice")
            .child((if (noticia.locale!=null) noticia.locale else "global").toString())
            .child(calendar.get(Calendar.YEAR).toString())
            .child(calendar.get(Calendar.MONTH).toString())
            .child(calendar.get(Calendar.DAY_OF_MONTH).toString())
            .child(calendar.get(Calendar.HOUR_OF_DAY).toString())
            .child(uidNoticia)

        dbRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    noticia.isLiked = true
                    btnLike.setImageResource(R.drawable.ic_heart_checked)
                    btnLike.setColorFilter(
                        ContextCompat.getColor(btnLike.context, R.color.secondary_Light),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }else{
                    btnLike.setImageResource(R.drawable.ic_heart);
                    btnLike.setColorFilter(
                        ContextCompat.getColor(btnLike.context, R.color.white),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }

    fun deleteAccount(mAuth : FirebaseAuth, passwordDeleteAccount: String, textInputLayoutPassword : TextInputLayout){
        val dbRef = database.child("users").child(mAuth.currentUser.uid).child("passwordForDeleteAccount")
        dbRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    if(Hash().sha256(passwordDeleteAccount).equals(snapshot.value.toString())){
                        mAuth.currentUser.delete()
                        database.child("users").child(mAuth.currentUser.uid).removeValue()
                        Toast.makeText(textInputLayoutPassword.context,textInputLayoutPassword.context.getString(R.string.account_deleted), Toast.LENGTH_SHORT).show()

                        mAuth.signOut()
                        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build()
                        val mGoogleSignInClient = GoogleSignIn.getClient(textInputLayoutPassword.context, gso)
                        mGoogleSignInClient.signOut()

                        val intentIrLoginActivity = Intent(textInputLayoutPassword.context, LoginActivity :: class.java)
                        textInputLayoutPassword.context.startActivity(intentIrLoginActivity)
                    }else{
                        textInputLayoutPassword.error = textInputLayoutPassword.context.getString(R.string.incorrect_password)
                    }

                }else{
                    Toast.makeText(textInputLayoutPassword.context,textInputLayoutPassword.context.getString(R.string.error_deleting_account), Toast.LENGTH_SHORT).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }




}


