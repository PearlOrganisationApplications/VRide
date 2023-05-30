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

    private val selectedMerchants = HashMap<Int, Merchant>()

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

        holder.checkBoxName.isChecked = selectedMerchants.containsKey(currentItem.id)

        holder.checkBoxName.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedMerchants[currentItem.id] = currentItem
            } else {
                selectedMerchants.remove(currentItem.id)
            }
        }
    }

    override fun getItemCount(): Int {
        return merchantList.size
    }

    fun getSelectedMerchants(): List<Merchant> {
        return selectedMerchants.values.toMutableList()
    }

}


