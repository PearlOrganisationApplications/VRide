package com.pearl.v_ride

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pearl.adapter.HistoryAdapter
import com.pearl.common.retrofit.data_model_class.HistoryList
import com.pearl.v_ride_lib.Global

class UserHistoryActivity : AppCompatActivity() {

    private lateinit var historyRV: RecyclerView
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Global.language(this,resources)
        setContentView(R.layout.activity_user_history)
        historyRV = findViewById(R.id.historyRV)


        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        apptitle.setText(R.string.history)
        ivback.setOnClickListener {
            onBackPressed()
        }

        val histroyListCard = ArrayList<HistoryList>()

        histroyListCard.add(
            HistoryList(
            R.drawable.bicycle,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","31/03/2023","11.43PM"
        )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","22/03/2023","11.43AM"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.delivery_man,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","1/03/2023","11.43PM"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon","hello there hiw are you ","31/01/2023","11.43"
            )
        )

        histroyListCard.add(
            HistoryList(
                R.drawable.bicycle,"Dehradoon","hello there hiw are you hello there ","3/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon","hello there hiw are you hello there ","13/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.bicycle,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","31/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","22/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.delivery_man,"Dehradoon","hello there hiw are you hello there ","1/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","31/01/2023","11.43"
            )
        )

        histroyListCard.add(
            HistoryList(
                R.drawable.bicycle,"Dehradoon","hello there hiw are you hello there hiw are youhello there hiw are you","3/03/2023","11.43"
            )
        )
        histroyListCard.add(
            HistoryList(
                R.drawable.profile,"Dehradoon, Transport nagar","hello there hiw are you hello there hiw are youhello there hiw are you","13/03/2023","11.43"
            )
        )


        historyRV.layoutManager = LinearLayoutManager(this)

        val historyRVAdapter = HistoryAdapter(histroyListCard)

        historyRV.adapter = historyRVAdapter
    }
}


