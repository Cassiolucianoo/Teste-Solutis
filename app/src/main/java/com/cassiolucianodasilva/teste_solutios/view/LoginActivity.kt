package com.cassiolucianodasilva.teste_solutios.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cassiolucianodasilva.teste_solutios.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener{

            verconta()
        }

    }
private fun verconta(){

    val  verconta = Intent(this,StartmentActivity::class.java)
    startActivity(verconta)
}
}