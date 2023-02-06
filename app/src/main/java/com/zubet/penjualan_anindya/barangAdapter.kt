package com.zubet.penjualan_anindya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_barang_adapter.view.*
import com.zubet.penjualan_anindya.barangAdapter.*
import com.zubet.penjualan_anindya.room.tb_barang

class barangAdapter (private val barang: ArrayList<tb_barang>,private val listener: onAdapterListener)
    :RecyclerView.Adapter<barangAdapter.barangViewHolder>(){
    class barangViewHolder (val view:View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): barangViewHolder {
       return barangViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.activity_barang_adapter,parent,false)
       )
    }

    override fun onBindViewHolder(holder: barangViewHolder, position: Int) {
        val thing = barang [position]
        holder.view.CVnama.text = thing.nm_brg
        holder.view.CVnama.setOnClickListener{
            listener.onClick(thing)
        }
        holder.view.edit.setOnClickListener{
            listener.onUpdate(thing)}
        holder.view.delete.setOnClickListener{
            listener.onDelete(thing)}
    }

    override fun getItemCount() = barang.size
    fun setData(list: List<tb_barang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }
    interface onAdapterListener{
        fun onClick (tbsis : tb_barang)
        fun onUpdate (tbsis: tb_barang)
        fun onDelete(tbsis: tb_barang)
    }
}
