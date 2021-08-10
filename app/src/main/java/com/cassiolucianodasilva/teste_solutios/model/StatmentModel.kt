package com.cassiolucianodasilva.teste_solutios.model

import com.google.gson.annotations.SerializedName
import java.util.*

class StatmentModel {


    @SerializedName("descricao")
      var description: String = ""

    @SerializedName("data")
    lateinit var  date: Date

    @SerializedName("valor")
    var value: Float = 0.0F



    }


