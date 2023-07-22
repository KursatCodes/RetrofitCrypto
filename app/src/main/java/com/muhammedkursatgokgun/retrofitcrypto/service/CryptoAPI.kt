package com.muhammedkursatgokgun.retrofitcrypto.service

import com.muhammedkursatgokgun.retrofitcrypto.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {

    // https://raw.githubusercontent.com/
    // atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData(): Response<List<CryptoModel>> // coroutines kullanmak için respons tanımlıyoruz
    //suspend fun getData(): Call<List<CryptoModel>> //

    //fun getData(): Observable<List<CryptoModel>> //RxJava ile

    //fun getData(): Call<List<CryptoModel>> // RxJava olmadan call ile
}