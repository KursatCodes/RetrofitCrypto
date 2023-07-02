package com.muhammedkursatgokgun.retrofitcrypto.adapter

import android.graphics.Color
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.muhammedkursatgokgun.retrofitcrypto.R
import com.muhammedkursatgokgun.retrofitcrypto.databinding.RowLayoutBinding
import com.muhammedkursatgokgun.retrofitcrypto.model.CryptoModel

class RecyclerviewAdapter(private val cryptoList : ArrayList<CryptoModel>,var listener : Listener) : RecyclerView.Adapter<RecyclerviewAdapter.RowHolder>() {
    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }
    class RowHolder(view: View) : RecyclerView.ViewHolder(view){
        var textName = itemView.findViewById<TextView>(R.id.text_name)
        var textPrice = itemView.findViewById<TextView>(R.id.text_price)
        var lineerlay = itemView.findViewById<LinearLayout>(R.id.lineerLayout)
        fun bind(cryptoModel: CryptoModel, listener : Listener){
            listener.onItemClick(cryptoModel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
    private val colorlar: Array<String> = arrayOf("#33FF36","#FF4233","#333CFF","#FF33E9","#FFF033"
        ,"#33FFF9","#FFC433","#C733FF")
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        var crypto = cryptoList[position]
        holder.textName.text = cryptoList.get(position).currency
        holder.textPrice.text = cryptoList.get(position).price
        holder.lineerlay.setBackgroundColor(Color.parseColor(colorlar[position % 8]))
//        holder.binding.lineerLayout.setBackgroundColor(Color.parseColor(colorlar[position % 8]))
//        holder.binding.textName.text = cryptoList.get(position).currency
//        holder.binding.textPrice.text = cryptoList.get(position).price
        holder.itemView.setOnClickListener {
            holder.bind(crypto,listener)
        }

    }
}