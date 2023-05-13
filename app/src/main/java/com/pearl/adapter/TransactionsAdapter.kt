package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import com.pearl.v_ride_lib.Global
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.data.TransactionList
import com.pearl.v_ride.R

class TransactionsAdapter(private val transactionList: ArrayList<TransactionList>):
RecyclerView.Adapter<TransactionsAdapter.ItemViewHolder>(){
    class ItemViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView) {

        val tTitle = itemView.findViewById<TextView>(R.id.transaction_title)
        val tAmount = itemView.findViewById<TextView>(R.id.transaction_amount)
        val tDate = itemView.findViewById<TextView>(R.id.transaction_Date)
        val tTime = itemView.findViewById<TextView>(R.id.transaction_Time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.transaction_list,parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = transactionList[position]
        holder.tTitle.text = currentItem.tTitle
        holder.tAmount.text = currentItem.tAmount
        holder.tDate.text = currentItem.tDate
        holder.tTime.text = currentItem.tTime
    }

    override fun getItemCount(): Int {
       return transactionList.size
    }

}