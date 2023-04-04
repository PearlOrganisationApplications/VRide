package com.pearl.v_ride

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.pearl.test5.R

class HomeScreen : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var navView: NavigationView
    lateinit var drawerLayout:DrawerLayout
    lateinit var  appbar: MaterialToolbar
    lateinit var notificationI: ImageView
    lateinit var notificationLL: LinearLayout
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var notificationRV: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
         appbar = findViewById<MaterialToolbar>(R.id.appBar)

        drawerLayout = findViewById(R.id.drawerLayout)
         navView = findViewById(R.id.nav_view)
        notificationI = findViewById(R.id.notificationI)
        notificationLL = findViewById(R.id.notificationLL)
        notificationRV = findViewById(R.id.notificationRV)

        val notificationCard = ArrayList<NotificationList>()


        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        setUpViews()

        apptitle.text ="Notification"
        ivback.setOnClickListener {
            onBackPressed()
/*            notificationLL.visibility = View.GONE
            notificationI.visibility =View.VISIBLE
            appbar.visibility = View.VISIBLE*/
        }


        notificationI.setOnClickListener {
     /*       notificationLL.visibility = View.VISIBLE
            notificationI.visibility =View.GONE
            appbar.visibility = View.GONE*/
            onBackPressed()

        }
        navView.setNavigationItemSelectedListener {

//           it.isChecked = true
            when(it.itemId){

                R.id.homemenu -> {
                    startActivity(Intent(this@HomeScreen, HomeScreen::class.java))
                    finish()
                }
                R.id.profile -> {
                    startActivity(Intent(this@HomeScreen, ProfileActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.wallet -> {
                    startActivity(Intent(this@HomeScreen, MyWalletActivity::class.java).putExtra("key", 1))
                    drawerLayout.closeDrawers()

                }
                R.id.earning-> {
                    startActivity(Intent(this@HomeScreen, MyWalletActivity::class.java).putExtra("key",0))
                    drawerLayout.closeDrawers()

                }
                R.id.history -> {

                    startActivity(Intent(this@HomeScreen, UserHistoryActivity::class.java))
                    drawerLayout.closeDrawers()

                }
                R.id.nearest_service -> {

                    startActivity(Intent(this@HomeScreen, NearestServiceActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.issue -> {
                    startActivity(Intent(this@HomeScreen, IssueRequestActivity::class.java))
                    drawerLayout.closeDrawers()
                    it.isChecked = false
                }
                R.id.document-> {

                    startActivity(Intent(this@HomeScreen, DocumentActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {
                    startActivity(Intent(this@HomeScreen, MainActivity::class.java))
                    Toast.makeText(applicationContext,"Logout", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            true
        }

        notificationCard.add(
            NotificationList(
                "Notification Title","this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title","this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title","this is my notification body"
            )
        )

        notificationRV.layoutManager = LinearLayoutManager(this)
        val nAdapter = NotificationAdapter(notificationCard)
        notificationRV.adapter = nAdapter



    }


    override fun onBackPressed() {
        //startActivity(Intent(this@HomeScreen,HomeScreen::class.java))
//        finish()
        if(notificationLL.visibility == View.GONE){
           //finish
            notificationLL.visibility = View.VISIBLE
            notificationI.visibility =View.GONE
            appbar.visibility = View.GONE
//            finish()
        }else{
            appbar.visibility = View.VISIBLE
            notificationLL.visibility = View.GONE
            notificationI.visibility =View.VISIBLE
        }
     /*   notificationLL.visibility = View.GONE
        notificationI.visibility =View.VISIBLE
        appbar.visibility = View.VISIBLE*/
//        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setUpViews(){
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {

        setSupportActionBar(appbar)
        toggle= ActionBarDrawerToggle( this,drawerLayout,R.string.open,R.string.close)
//        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }
/*    private fun replaceFragment(fragment: Fragment,title: String){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }*/

}