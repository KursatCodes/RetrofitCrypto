package com.muhammedkursatgokgun.retrofitcrypto.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muhammedkursatgokgun.retrofitcrypto.R
import com.muhammedkursatgokgun.retrofitcrypto.databinding.RowLayoutBinding
import com.muhammedkursatgokgun.retrofitcrypto.model.CryptoModel

class RecyclerviewAdapter(private val cryptoList : ArrayList<CryptoModel>) : RecyclerView.Adapter<RecyclerviewAdapter.RowHolder>() {
    class RowHolder(var binding : RowLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
    private val colorlar: Array<String> = arrayOf("#33FF36","#FF4233","#333CFF","#FF33E9","#FFF033"
        ,"#33FFF9","#FFC433","#C733FF")
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.binding.lineerLayout.setBackgroundColor(Color.parseColor(colorlar[position % 8]))
        holder.binding.textName.text = cryptoList.get(position).currency
        holder.binding.textPrice.text = cryptoList.get(position).price
    }
}