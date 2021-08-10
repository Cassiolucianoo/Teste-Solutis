package com.cassiolucianodasilva.teste_solutios.viewModel
import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.cassiolucianodasilva.teste_solutios.R
import com.cassiolucianodasilva.teste_solutios.model.StatmentModel
import com.cassiolucianodasilva.teste_solutios.service.PersonRepository
import com.cassiolucianodasilva.teste_solutios.service.constants.StatmentsConstants
import com.cassiolucianodasilva.teste_solutios.service.listener.APIListener
import com.cassiolucianodasilva.teste_solutios.service.repository.local.SecurityPreferences
import com.cassiolucianodasilva.teste_solutios.utils.CpfCnpjMask
import com.cassiolucianodasilva.teste_solutios.view.LoginActivity

class StatmentViewModel(application: Application): AndroidViewModel(application) {

    private val mSecurityPreferences = SecurityPreferences(application)
    private val mStatmentRepository = PersonRepository(application)


    private val mLogout = MutableLiveData<Boolean>()





    private val mStatment = MutableLiveData<List<StatmentModel>>()
    var statment: LiveData<List<StatmentModel>> = mStatment

    fun loadData(): HashMap<String, String> {

        val name = mSecurityPreferences.get(StatmentsConstants.USER.USER_NAME)
        var cpf = mSecurityPreferences.get(StatmentsConstants.USER.USER_CPF)
        val balance = mSecurityPreferences.get(StatmentsConstants.USER.USER_BALANCE)

        val cpfcovert = CpfCnpjMask()
        cpf =  cpfcovert.cpfCnpj(cpf)


        val values = HashMap<String, String>()
        values[StatmentsConstants.USER.USER_NAME] = name
        values[StatmentsConstants.USER.USER_CPF] = cpf.format("")
        values[StatmentsConstants.USER.USER_BALANCE] = balance.replace('.', ',')

        return values
    }
//
    fun logout(){
       // mSecurityPreferences.remove(StatmentsConstants.SHARED.USER_LOGIN)
        mLogout.value = true
    }





    fun loadStatment(){
        mStatmentRepository.statment(mSecurityPreferences.get(StatmentsConstants.USER.USER_TOKEN), object : APIListener<List<StatmentModel>>{
            override fun onSuccess(model: List<StatmentModel>) {
                mStatment.value = model
            }

            override fun onFailure(str: String) {
                
                Toast.makeText(getApplication(),R.string.erroatualizarextrato, Toast.LENGTH_SHORT).show()
            }

        })
    }




}