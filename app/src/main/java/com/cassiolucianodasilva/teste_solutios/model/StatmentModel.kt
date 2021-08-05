package com.cassiolucianodasilva.teste_solutios.model

import com.google.gson.annotations.SerializedName
import java.util.*

class StatmentModel {

    @SerializedName("descricao")
    private var description: String? = null

    @SerializedName("date")
    private lateinit var  date: Date

    @SerializedName("value")
    private var value: Float? = 0.0F

    }
