package com.muhammedkursatgokgun.retrofitcrypto.model

data class CryptoModel(
    //@SerializedName("currecy") kotlinde birebir aynı isimle değişken oluşturunca bu kısma gerek kalmıyor.
    var currency: String,
    var price: String
    )