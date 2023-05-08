package com.pearl.v_ride

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pearl.adapter.OnboardingItemAdapter
import com.pearl.data.OnboardingItem
import com.pearl.test5.R
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.Global

class WelcomeScreen : BaseClass() {
    private  lateinit var onboardingItemAdapter: OnboardingItemAdapter

    private lateinit var indicatorContainer: LinearLayout
    override fun setLayoutXml() {
        setContentView(R.layout.activity_welcome_screen)
    }

    override fun initializeViews() {

    }

    override fun initializeClickListners() {
    }

    override fun initializeInputs() {
    }

    override fun initializeLabels() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.language(this,resources)


        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()

        setOnboardingItem()
        setUpIndicator()
        setCurrenIndicator(0)
    }

    private fun setOnboardingItem(){

        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.animation_bike,
                    title = "Rent Bikes",
                    discription = "We are a highly acknowledged name for Bike Rental in Dehradun, Providing Bikes, Activa Scooty Bullets on Rent in Dehradun at an affordable price near ISBT"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.bicycle,
                    title = "Rent Bikes",
                    discription = "We are a highly acknowledged name for Bike Rental in Dehradun, Providing Bikes, Activa Scooty Bullets on Rent in Dehradun at an affordable price near ISBT"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.animation_bike,
                    title = "Rent Bikes",
                    discription = "We are a highly acknowledged name for Bike Rental in Dehradun, Providing Bikes, Activa Scooty Bullets on Rent in Dehradun at an affordable price near ISBT"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.delivery_man,
                    title = "Rent Bikes",
                    discription = "We are a highly acknowledged name for Bike Rental in Dehradun, Providing Bikes, Activa Scooty Bullets on Rent in Dehradun at an affordable price near ISBT"
                ),    OnboardingItem(
                    onboardingImage = R.drawable.delivery_man,
                    title = "Rent Bikes",
                    discription = "We are a highly acknowledged name for Bike Rental in Dehradun, Providing Bikes, Activa Scooty Bullets on Rent in Dehradun at an affordable price near ISBT"
                )
            )
        )

        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemAdapter

        onboardingViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrenIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER

        findViewById<ImageView>(R.id.image_next).setOnClickListener {
            if (onboardingViewPager.currentItem+1 < onboardingItemAdapter.itemCount){
                onboardingViewPager.currentItem +=1
            }else{
                navigateToDashboarde()
            }
        }

        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            navigateToDashboarde()
        }

        findViewById<TextView>(R.id.start).setOnClickListener {
            navigateToDashboarde()
        }
    }

    private fun navigateToDashboarde() {

            val i = Intent(this@WelcomeScreen, PermissionActivity::class.java)
            startActivity(i)
            finish()

    }


    private fun setUpIndicator(){
        indicatorContainer =findViewById(R.id.indicatorsContainer)

        val  indicator = arrayOfNulls<ImageView>(onboardingItemAdapter.itemCount)
        val layoutParam: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutParam.setMargins(8, 0, 8, 0)
        for (i in  indicator.indices){
            indicator[i] = ImageView(applicationContext)
            indicator[i]?.let {
                it.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.indicator_inactive_background
                )
                )

                it.layoutParams =layoutParam
                indicatorContainer.addView(it)
            }
        }
    }
    private fun setCurrenIndicator(position: Int){
        val chidCound = indicatorContainer.childCount
        for(i in 0 until chidCound){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }

}