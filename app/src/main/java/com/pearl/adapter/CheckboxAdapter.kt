package com.pearl.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.pearl.common.retrofit.data_model_class.Merchant
import com.pearl.v_ride.R
import com.pearl.v_ride_lib.PrefManager

class CheckboxAdapter(private  val context: Context,private val merchantList: ArrayList<Merchant>) :
    RecyclerView.Adapter<CheckboxAdapter.ItemViewHolder>() {

    private val selectedMerchants = HashMap<Int, Merchant>()
    lateinit var pref: PrefManager


    /*val list = pref.getIds()
    */
    lateinit var list: List<String>


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBoxName = itemView.findViewById<CheckBox>(R.id.doc_zomatoCB)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        pref = PrefManager(context)

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.merchant_checkbox, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = merchantList[position]
        holder.checkBoxName.text = currentItem.name

        holder.checkBoxName.isChecked = selectedMerchants.containsKey(currentItem.id)
       /* list = pref.get
        list.contains(currentItem.id)*/

//        mList = list.split(",")
//        mList = listOf(list)
//        val  mList = pref.getIds()
//        list = mList.split(",")
//        list = listOf(mList)

        val mList = pref.getIds()
        val items = mList.split(",")
        val intList = mutableListOf<Int>()

        for (item in items) {
            if (item.isNotEmpty()) {
                intList.add(item.toInt())
            }
        }

        if (intList.contains(currentItem.id)){
            Log.d("Ifcase","jkahsdlk")
            holder.checkBoxName.isChecked = true

        }
        else{
            Log.d("Elsecase","${currentItem.id}  ${intList}")

        }
        holder.checkBoxName.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedMerchants[currentItem.id] = currentItem
            } else {
                selectedMerchants.remove(currentItem.id)
            }
        }

//        holder.checkBoxName.isChecked = true

    }

    override fun getItemCount(): Int {
        return merchantList.size
    }

    fun getSelectedMerchants(): List<Merchant> {
        return selectedMerchants.values.toMutableList()
    }

}


