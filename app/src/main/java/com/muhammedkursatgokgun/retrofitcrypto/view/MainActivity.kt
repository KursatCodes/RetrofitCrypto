package com.muhammedkursatgokgun.retrofitcrypto.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.muhammedkursatgokgun.retrofitcrypto.R
import com.muhammedkursatgokgun.retrofitcrypto.adapter.RecyclerviewAdapter
import com.muhammedkursatgokgun.retrofitcrypto.model.CryptoModel
import com.muhammedkursatgokgun.retrofitcrypto.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
class MainActivity : AppCompatActivity(),RecyclerviewAdapter.Listener{
    private val BASE_URL= "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerviewAdapter : RecyclerviewAdapter? = null
    private var myDisposable = CompositeDisposable()
    private var job : Job? = null

    val exeptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Errorumuz ::::::::: "+ throwable.localizedMessage)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        val layoutmanager1 : RecyclerView.LayoutManager = LinearLayoutManager(this)
        var recyclerView : RecyclerView = findViewById(R.id.recyclerView1)
        recyclerView.layoutManager = layoutmanager1

         loadData()

     }
    private fun loadData() {
        println("selamünaleyküüüüüüm 1")
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // .addConverterFactory(GsonConverterFactory.create()) CALL ile bu şekilde
            .build()
            .create(CryptoAPI::class.java)
        //val service = retrofit.create(CryptoAPI::class.java)

        println("selamünaleyküüüüüüm 2")

        /* CoroutineScope(Dispatchers.IO+exeptionHandler).launch(){
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

        }*/

        job = CoroutineScope(Dispatchers.IO + exeptionHandler).launch() {
            println("selamünaleyküüüüüüm 99")
            val response = retrofit.getData()
            println("selam 1")
            withContext(Dispatchers.Main) {
                println("selam 2")
                if (response.isSuccessful) {
                    println("selam 3")
                    response.body()?.let {
                        println("selam 4")
                        cryptoModels = ArrayList(it)
                        cryptoModels?.let {
                            recyclerviewAdapter = RecyclerviewAdapter(it, this@MainActivity)
                            var recyclerView: RecyclerView = findViewById(R.id.recyclerView1)
                            recyclerView.adapter = recyclerviewAdapter
                        }
                    }
                }
            }

        }

        /*myDisposable.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )*/                                                 //RxJava, bu yöntem çok fazla getdata kullanılırsa daha verimli

        /*call.enqueue(object: Callback<List<CryptoModel>>{
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

        })*/ // çok fazla call olursa verimsiz olur
    }
        private fun handleResponse(cryptoList: List<CryptoModel>) {
            cryptoModels = cryptoList as ArrayList<CryptoModel>
            for (crypomodel: CryptoModel in cryptoModels!!) {
                println(crypomodel.currency)
            }
            cryptoModels?.let {
                recyclerviewAdapter = RecyclerviewAdapter(it, this@MainActivity)
                val recyclerView: RecyclerView = findViewById(R.id.recyclerView1)
                recyclerView.adapter = recyclerviewAdapter
            }
        }


        override fun onItemClick(cryptoModel: CryptoModel) {
            Toast.makeText(this, "Tıkladınız: " + cryptoModel.currency, Toast.LENGTH_LONG).show()
        }

        override fun onDestroy() {
            super.onDestroy()
            //myDisposable.clear()
            job?.cancel()
        }
    }
