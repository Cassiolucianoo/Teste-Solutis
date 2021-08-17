package com.cassiolucianodasilva.teste_solutios.view
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cassiolucianodasilva.teste_solutios.R
import com.cassiolucianodasilva.teste_solutios.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        editPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        progress.visibility = View.GONE
        buttonLogin.setOnClickListener(this)

        editUser.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(content: Editable) {

                val message = getString(R.string.invalid_user)

                editUser.error =
                    if (content.isNotEmpty()
                        && Patterns.EMAIL_ADDRESS.matcher(content).matches()
                    )
                        null
                    else
                        message
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })

        /*
        * Colocando configuração de validação de campo de senha
        * para enquanto o usuário informa o conteúdo deste campo.
        * */
        editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(content: Editable) {

                val message = getString(R.string.invalid_password)

                editPassword.error =
                    if (content.length > 5)
                        null
                    else
                        message
            }

            override fun beforeTextChanged(
                content: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                content: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })


        cacheLogin()

        observe()

        showAuthentication()

    }
    /**
     * Mostra autenticação biométrica
     */
    private fun showAuthentication() {

        // Executor - Funciona como um thread para a autenticação
        val executor: Executor = ContextCompat.getMainExecutor(this)

        // BiometricPrompt
        val biometricPrompt = BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(applicationContext, StartmentActivity::class.java))
                    finish()
                }
            })

        // Informações apresentadas no momento da autenticação
        val info: BiometricPrompt.PromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.fp_titulo))
            .setSubtitle(getString(R.string.fp_subtitulo))
            .setDescription(getString(R.string.fp_descricao))
            .setNegativeButtonText(getString(R.string.fp_cancelar))
            .build()

        // Exibe para o usuário
        biometricPrompt.authenticate(info)
    }

    private fun cacheLogin() {
        mViewModel.cacheLogin()
    }
    /**
     * TODO
     *Observa ViewModel
     */
    private fun observe() {

        mViewModel.login.observe(this, Observer {
            if (it.success()) {
                startActivity(Intent(this, StartmentActivity::class.java))
                finish()
                progress.visibility = View.VISIBLE
            } else {

                editUser.setTextColor(Color.parseColor("#AAC30000"))
                Toast.makeText(applicationContext, it.failure(), Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }
        })
        mViewModel.lastLogin.observe(this, Observer {
            if (it != "") {
                editUser.setText(it)
            }
        })
    }
    override fun onClick(v: View) {
        if (v.id == R.id.buttonLogin) {
            handleLogin()
            progress.visibility = View.VISIBLE
        }
    }
    /**
     * Autentica usuário
     */

    private fun handleLogin() {
        val user = editUser.text.toString()
        val password = editPassword.text.toString()

        mViewModel.doLogin(user, password)

        progress.visibility = View.GONE

    }
}


