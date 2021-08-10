package com.cassiolucianodasilva.teste_solutios.viewModel

import android.app.Application
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cassiolucianodasilva.teste_solutios.R
import com.cassiolucianodasilva.teste_solutios.model.LoginModel
import com.cassiolucianodasilva.teste_solutios.service.PersonRepository
import com.cassiolucianodasilva.teste_solutios.service.constants.StatmentsConstants
import com.cassiolucianodasilva.teste_solutios.service.listener.APIListener
import com.cassiolucianodasilva.teste_solutios.service.listener.ValidationListener
import com.cassiolucianodasilva.teste_solutios.service.repository.local.SecurityPreferences


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    // Acesso a dados
    private val mPersonRepository = PersonRepository(application)
    private val mSecurityPreferences = SecurityPreferences(application)


    // Login usando API
    private val mLogin = MutableLiveData<ValidationListener>()
    val login: LiveData<ValidationListener> = mLogin


    private val mLastLogin = MutableLiveData<String>()
    var lastLogin: LiveData<String> = mLastLogin

    /**
     * TODO
     * Faz login usando API
     * @param user
     * @param password
     */

    fun doLogin(user: String, password: String) {
        if (mPersonRepository.isConnectionAvailable(getApplication())) {
            val emailLogin = Patterns.EMAIL_ADDRESS.matcher(user).matches()
            val specialCharacter = password.contains(Regex("[@#$%^/&+=]"))
            val alphaNumeric = password.contains(Regex("[a-zA-Z0-9]"))
            if (!emailLogin or !specialCharacter or !alphaNumeric) {


            }

            mPersonRepository.login(user, password, object : APIListener<LoginModel> {
                override fun onSuccess(model: LoginModel) {
                    mSecurityPreferences.store(StatmentsConstants.USER.USER_NAME, model.name)
                    mSecurityPreferences.store(StatmentsConstants.USER.USER_CPF, model.cpf)
                    mSecurityPreferences.store(
                        StatmentsConstants.USER.USER_BALANCE,
                        model.balance.toString()
                    )
                    mSecurityPreferences.store(StatmentsConstants.USER.USER_TOKEN, model.token)
                    mSecurityPreferences.store(StatmentsConstants.SHARED.USER_LOGIN, user)

                    // Informa sucesso
                    mLogin.value = ValidationListener()

                }

                override fun onFailure(message: String) {
                    mLogin.value = ValidationListener(message)
                }

            })
        } else {
            Toast.makeText(getApplication(), R.string.errointernet, Toast.LENGTH_SHORT).show()
        }


    }

    /**
     * TODO
     * Verifica se usuário está logado
     */
    fun verifyLoggedUser() {

    }


    fun cacheLogin() {
        mLastLogin.value = mSecurityPreferences.get(StatmentsConstants.SHARED.USER_LOGIN)
    }


}