package com.muhammedkursatgokgun.retrofitcrypto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muhammedkursatgokgun.retrofitcrypto.R
import com.muhammedkursatgokgun.retrofitcrypto.adapter.RecyclerviewAdapter
import com.muhammedkursatgokgun.retrofitcrypto.model.CryptoModel
import com.muhammedkursatgokgun.retrofitcrypto.service.CryptoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity(){
    private val BASE_URL= "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerviewAdapter : RecyclerviewAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
        println("selam 1")
        val layoutmanager1 : RecyclerView.LayoutManager = LinearLayoutManager(this)
        println("selam 2")
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = layoutmanager1
        println("selam 3")
        loadData()


    }
    private fun loadData(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object: Callback<List<CryptoModel>>{
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                println("selam 4")
                if (response.isSuccessful){
                    println("selam 5")
                    response.body()?.let {
                        println("selam 6")
                        cryptoModels = ArrayList(it)
                        println("selam 7")
                        cryptoModels?.let {
                            println("selam 8")
                            recyclerviewAdapter= RecyclerviewAdapter(it)
                            println("selam 9")
                            val recyclerView : RecyclerView = findViewById(R.id.recyclerView1)
                            recyclerView.adapter = recyclerviewAdapter
                        }

//                        for (crypomodel : CryptoModel in cryptoModels!!){
//                            println(crypomodel.currency)
//                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}