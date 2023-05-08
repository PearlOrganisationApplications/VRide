package com.pearl.v_ride

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.NearestListAapter
import com.pearl.data.NearestList
import com.pearl.test5.R
import com.pearl.v_ride_lib.Global

class NearestServiceActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
//    private lateinit var newArrayList: ArrayList<NearestList>
/*    lateinit var placeName: Array<String>
    lateinit var placeAddress: Array<String*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    Global.language(this,resources)
        setContentView(R.layout.activity_nearest_service)

    ivback=findViewById(R.id.ivBack)
    apptitle = findViewById(R.id.titleTVAppbar)

    apptitle.text =title
    ivback.setOnClickListener {
        onBackPressed()
    }

        val listCard = ArrayList<NearestList>()

        listCard.add(
            NearestList(
                "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001")
        )
        listCard.add(
            NearestList(
                "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001")
        )
        listCard.add(
            NearestList(
                "Dehradoon", "Arahat Bazar Dehradun Dehradun UTTARAKHAND 248001")
        )

        recyclerView = findViewById(R.id.nearestRV)


        recyclerView.layoutManager = LinearLayoutManager(this)

        val recyclerViewAapter = NearestListAapter(listCard)
    recyclerView.adapter =recyclerViewAapter


    }
}

/*
lateinit var ivback: AppCompatImageView
lateinit var apptitle: AppCompatTextView

ivback=findViewById(R.id.ivBack)
apptitle = findViewById(R.id.titleTVAppbar)

apptitle.text =title
ivback.setOnClickListener {
    onBackPressed()
}

*/