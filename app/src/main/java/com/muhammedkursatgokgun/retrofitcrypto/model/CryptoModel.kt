package com.muhammedkursatgokgun.retrofitcrypto.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    //@SerializedName("currecy") kotlinde birebir aynı isimle değişken oluşturunca bu kısma gerek kalmıyor.
    var currency : String,
    var price : String
    )