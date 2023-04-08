package com.pearl.v_ride


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent

import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.pearl.Global
import com.pearl.test5.R
import de.hdodenhof.circleimageview.CircleImageView


class HomeScreen : AppCompatActivity(), OnMapReadyCallback {
    lateinit var toggle: ActionBarDrawerToggle

    lateinit var navView: NavigationView
    lateinit var drawerLayout:DrawerLayout
    lateinit var  appbar: MaterialToolbar
//    lateinit var notificationI: ImageView
    lateinit var notificationLL: LinearLayout
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var notificationRV: RecyclerView
    lateinit var nBell: ImageView
    private lateinit var mMap: GoogleMap
    lateinit var mapLL: LinearLayout
    lateinit var  dImage: CircleImageView

    //    lateinit var mapFragment: Fragment
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.homeScreenmap) as SupportMapFragment
        fetchLocation()





/*        val ai: ApplicationInfo = applicationContext.packageManager
            ?.getApplicationInfo(applicationContext.applicationContext!!.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["com.google.android.geo.${R.string.google_map_api_key}"]
        val apiKey = value.toString()
        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext.applicationContext,apiKey)
        }*/
        mapFragment.getMapAsync(this)

      /*  mapFragment.getMapAsync {
            mMap = it

            val originLocation = LatLng( 30.2891496, 78.0437616)

            mMap.addMarker(MarkerOptions().position(originLocation).title("hey"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(originLocation))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 15F))
        }*/
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        appbar = findViewById<MaterialToolbar>(R.id.appBar)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_view)
//        notificationI = findViewById(R.id.notificationI)
        notificationLL = findViewById(R.id.notificationLL)
        notificationRV = findViewById(R.id.notificationRV)
        nBell = findViewById(R.id.nBell)
        mapLL = findViewById(R.id.mapLL)

        val headerLayout: View = navView.inflateHeaderView(R.layout.nav_header)
        dImage = headerLayout.findViewById(R.id.drawerImage)



            //set Image
//                dImage.setImageURI(uri)




        val notificationCard = ArrayList<NotificationList>()


        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        setUpViews()

        nBell.setOnClickListener {
//            onBackPressed()
            notificationLL.visibility=View.VISIBLE
            appbar.visibility = View.GONE
            mapLL.setVisibility(View.GONE)

        }
        apptitle.text ="Notification"
        ivback.setOnClickListener {


            mapLL.setVisibility(View.VISIBLE)
            onBackPressed()
/*            notificationLL.visibility = View.GONE
            notificationI.visibility =View.VISIBLE
            appbar.visibility = View.VISIBLE*/
//            mapFragment.view?.setVisibility(View.VISIBLE)
        }


/*        notificationI.setOnClickListener {
                   notificationLL.visibility = View.VISIBLE
                   notificationI.visibility =View.GONE
                   appbar.visibility = View.GONE
            onBackPressed()

        }*/
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


    override fun onResume() {
        super.onResume()
        if(Global.imageString != "") {
            val uri = Uri.parse(Global.imageString)
            dImage.setImageURI(uri)
            Log.d("abc", Global.imageString)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            var uri: Uri = data?.data!!

            // Use Uri object instead of File to avoid storage permissions

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onBackPressed() {
        //startActivity(Intent(this@HomeScreen,HomeScreen::class.java))
//        finish()
        if(notificationLL.visibility == View.GONE){
            //finish
/*            notificationLL.visibility = View.VISIBLE
            notificationI.visibility =View.GONE
            appbar.visibility = View.GONE   */
            finish()
        }else{
            appbar.visibility = View.VISIBLE
            notificationLL.visibility = View.GONE
            mapLL.visibility = View.VISIBLE
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

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {

/*        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    applicationContext,
                    "${currentLocation.latitude} ${currentLocation.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }*/
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

     /*   if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }*/
        val task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(
                    applicationContext,
                    "${currentLocation.latitude} ${currentLocation.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
//                fetchLocation()
                mMap = googleMap

                val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                val markerOptions = MarkerOptions().position(latLng).title("I am here!")

                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
//            mMap.addMarker(MarkerOptions().position(latLng).title("hey"))
                mMap.addMarker(markerOptions)
                /*val supportMapFragment =
                    supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
                supportMapFragment.getMapAsync(this)*/
            }
        }

        /*    val sydney = LatLng(-33.852, 151.211)
            googleMap.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )*/


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation()
                }
            }
        }
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