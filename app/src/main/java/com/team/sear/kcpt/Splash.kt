package com.team.sear.kcpt

import android.content.Intent
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class Splash : AppCompatActivity() {

    private lateinit var splashBackgroundFirst: TextView
    private lateinit var splashBackgroundSecond: TextView
    private lateinit var splashTcpLogo: ImageView
    private lateinit var splashKcptText: TextView
    private lateinit var splashStartWorking: Button
    private lateinit var slide: Animation
    private lateinit var alpha: Animation
    private lateinit var slideSecond: Animation
    private lateinit var authAlpha: Animation
    private var navigateIntent: Intent? = null
    private var registrationIntent: Intent? = null
    internal var user: FirebaseUser? = null
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        navigateIntent = Intent(this, Navigate::class.java)
        registrationIntent = Intent(this, SelectReg::class.java)
        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        } catch (e: Exception) {
        }
        splashBackgroundFirst = findViewById(R.id.splash_background_first)
        splashBackgroundSecond = findViewById(R.id.splash_background_second)
        splashTcpLogo = findViewById(R.id.splash_tcp_logo)
        splashKcptText = findViewById(R.id.splash_kcpt_text)
        splashStartWorking = findViewById(R.id.splash_start_working)
        splashBackgroundFirst.visibility = View.GONE
        splashBackgroundSecond.visibility = View.GONE
        splashTcpLogo.visibility = View.GONE
        splashKcptText.visibility = View.GONE
        splashStartWorking.visibility = View.GONE
        splashStartWorking.text = "Загрузка..."
        slide = AnimationUtils.loadAnimation(this, R.anim.slide)
        alpha = AnimationUtils.loadAnimation(this, R.anim.splash_alpha)
        slideSecond = AnimationUtils.loadAnimation(this, R.anim.slide_second)
        authAlpha = AnimationUtils.loadAnimation(this, R.anim.aplha_0_1)
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            try {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    startAnimInAuth()
                } else {
                    startAnimInNotAuth()
                }
            } catch (e: Exception) {
                Toast.makeText(this@Splash, "Undefinded error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startAnimInAuth() {
        splashBackgroundFirst.startAnimation(authAlpha)
        splashBackgroundSecond.startAnimation(authAlpha)
        splashStartWorking.startAnimation(authAlpha)
        splashKcptText.startAnimation(authAlpha)
        splashTcpLogo.startAnimation(authAlpha)
        Handler().postDelayed({
            splashBackgroundFirst.visibility = View.VISIBLE
            splashBackgroundFirst.visibility = View.VISIBLE
            splashBackgroundSecond.visibility = View.VISIBLE
            splashTcpLogo.visibility = View.VISIBLE

            splashKcptText.visibility = View.VISIBLE
            splashStartWorking.visibility = View.VISIBLE
            startActivity(navigateIntent)
            finish()
        }, 800)
    }

    private fun startAnimInNotAuth() {
        splashBackgroundFirst.startAnimation(slide)
        splashBackgroundFirst.visibility = View.VISIBLE
        Handler().postDelayed({
            splashBackgroundSecond.startAnimation(slideSecond)
            splashBackgroundSecond.visibility = View.VISIBLE
        }, 500)
        Handler().postDelayed({
            splashTcpLogo.startAnimation(alpha)
            splashKcptText.startAnimation(alpha)
            splashStartWorking.startAnimation(alpha)
            splashTcpLogo.visibility = View.VISIBLE
            splashKcptText.visibility = View.VISIBLE
            splashStartWorking.visibility = View.VISIBLE
            Handler().postDelayed({
                Handler().postDelayed({
                    startActivity(registrationIntent)
                    finish()
                }, 100)
            }, 800)
        }, 500)
    }

    public override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener!!)
    }

    public override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener!!)
        }
    }
}