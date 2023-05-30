package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.pearl.common.retrofit.data_model_class.Merchant
import com.pearl.v_ride.R

class CheckboxAdapter(private val merchantList: ArrayList<Merchant>) :
    RecyclerView.Adapter<CheckboxAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxName = itemView.findViewById<CheckBox>(R.id.doc_zomatoCB)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.merchant_checkbox, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = merchantList[position]
        holder.checkBoxName.text = currentItem.name
        /*holder.checkBoxName.isChecked = currentItem.isSelected

        holder.checkBoxName.setOnCheckedChangeListener { _, isChecked ->
            currentItem.isSelected = isChecked
        }*/
    }

    override fun getItemCount(): Int {
        return merchantList.size
    }

    /*fun getSelectedMerchants(): List<Merchant> {
        val selectedMerchants = mutableListOf<Merchant>()
        for (merchant in merchantList) {
            if (merchant.isSelected) {
                selectedMerchants.add(merchant)
            }
        }
        return selectedMerchants
    }*/
}
