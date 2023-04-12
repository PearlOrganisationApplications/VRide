package com.pearl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pearl.test5.R
import com.pearl.data.OnboardingItem

class OnboardingItemAdapter(private  val  onboardingItem: List<OnboardingItem>):
RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>()
{

    inner class OnboardingItemViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val imgOnboarding = view.findViewById<ImageView>(R.id.imgOnboarding)
        private val textTitle = view.findViewById<TextView>(R.id.textTitle)
        private val textDescription = view.findViewById<TextView>(R.id.textDescription)


        fun  bind(onboardingItem: OnboardingItem) {
            imgOnboarding.setImageResource(onboardingItem.onboardingImage)
            textTitle.text = onboardingItem.title
            textDescription.text = onboardingItem.discription
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {

        return  OnboardingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.onboarding_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {

        holder.bind((onboardingItem[position]))
    }

    override fun getItemCount(): Int {

        return onboardingItem.size
    }

}