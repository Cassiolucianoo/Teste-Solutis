package com.cassiolucianodasilva.teste_solutios.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cassiolucianodasilva.teste_solutios.R
import com.cassiolucianodasilva.teste_solutios.service.constants.StatmentsConstants
import com.cassiolucianodasilva.teste_solutios.view.adapter.StatmentAdapter
import com.cassiolucianodasilva.teste_solutios.viewModel.StatmentViewModel
import kotlinx.android.synthetic.main.header_list_card.*
import android.content.DialogInterface




class StartmentActivity : AppCompatActivity(), View.OnClickListener {
    private val mAdapter = StatmentAdapter()
    private lateinit var mViewModel: StatmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startment)
        mViewModel = ViewModelProvider(this).get(StatmentViewModel::class.java)

        loadData()
        loadStatment()

        val recycler = findViewById<RecyclerView>(R.id.recyler_statment)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = mAdapter


        logout.setOnClickListener(this)

        observe()

    }

    private fun loadData() {
        val values = mViewModel.loadData()
        nomeUsuario.text = values[StatmentsConstants.USER.USER_NAME]
        numeroConta.text = values[StatmentsConstants.USER.USER_CPF]
        valorConta.text = values[StatmentsConstants.USER.USER_BALANCE]
    }

    override fun onClick(v: View) {
        if (v.id == R.id.logout) {
            mViewModel.logout()

            exit()

        }
    }

     fun onBackPresseds() {
        Toast.makeText(applicationContext, "Alterar botão retornar", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
       exit()
    }


    private fun loadStatment() {
        mViewModel.loadStatment()
    }


    private fun observe() {
        mViewModel.statment.observe(this, Observer {
            if (it.count() > 0) {
                mAdapter.updateStatment(it)
            }
        })

    }

    private fun exit(){

        AlertDialog.Builder(this)
            //.setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Sair da Conta")
            .setMessage("Deseja realmente sair ?")
            .setPositiveButton("Sim",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("Não", null)
            .show()

    }

}