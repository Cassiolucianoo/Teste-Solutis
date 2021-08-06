package com.cassiolucianodasilva.teste_solutios.service.repository.remote

import com.cassiolucianodasilva.teste_solutios.model.LoginModel
import com.cassiolucianodasilva.teste_solutios.model.StatmentModel
import retrofit2.Call
import retrofit2.http.*

interface StatmentService {

    @POST("login")
    fun login(@Body body: HashMap<String, String>): Call<LoginModel>

    @GET("extrato")
    fun statment(@Header("token") token: String): Call<List<StatmentModel>>

}