package com.muhammedkursatgokgun.retrofitcrypto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
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
class MainActivity : AppCompatActivity(),RecyclerviewAdapter.Listener{
    private val BASE_URL= "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerviewAdapter : RecyclerviewAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        val layoutmanager1 : RecyclerView.LayoutManager = LinearLayoutManager(this)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = layoutmanager1
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
                if (response.isSuccessful){
                    response.body()?.let {
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            recyclerviewAdapter= RecyclerviewAdapter(it,this@MainActivity)
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
    private fun tiklama(){

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this,"Tıkladınız: "+cryptoModel.currency,Toast.LENGTH_LONG).show()
    }
}