package com.cassiolucianodasilva.teste_solutios.model

import com.google.gson.annotations.SerializedName

class LoginModel {

    @SerializedName("nome")
    private var name: String? = null

    @SerializedName("cpf")
    private var cpf: Int? = null

    @SerializedName("saldo")
    private var balance: Float? = 0.0F

    @SerializedName("token")
    private var token: String? = null

}
