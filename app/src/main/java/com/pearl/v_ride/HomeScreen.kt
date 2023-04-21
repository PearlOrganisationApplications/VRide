package com.pearl.v_ride


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.pearl.v_ride_lib.Global
import com.pearl.adapter.NotificationAdapter
import com.pearl.data.NotificationList
import com.pearl.test5.R
import com.pearl.ui.DocumentActivity
import com.pearl.ui.FormActivity
import com.pearl.v_ride_lib.BaseClass
import com.pearl.v_ride_lib.PrefManager
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class HomeScreen : BaseClass(), OnMapReadyCallback {
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
    private var hasGps = false
    private var hasNetwork = false
    var to_lat :String ?= ""
    var from_lat :String ?= ""
    var to_lng :String ?= ""
    var from_lng :String ?= ""
    private var locationByGps: Location? = null
    private var locationByNetwork: Location? = null
    lateinit var locationManager: LocationManager
    private lateinit var  location:LatLng
    //    lateinit var mapFragment: Fragment
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    lateinit var mAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var city: TextView
    lateinit var pieChart: PieChart
    lateinit var prefManager: PrefManager


    override fun setLayoutXml() {
        setContentView(R.layout.activity_home_screen)
    }

    override fun initializeViews() {
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
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ivback=findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        city = findViewById(R.id.stateTV)
        pieChart = findViewById(R.id.pieChart)
    }

    override fun initializeClickListners() {
        nBell.setOnClickListener {
//            onBackPressed()
            notificationLL.visibility=View.VISIBLE
            appbar.visibility = View.GONE
            mapLL.setVisibility(View.GONE)
        }
        ivback.setOnClickListener {
            mapLL.setVisibility(View.VISIBLE)
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
                R.id.form -> {
                    startActivity(Intent(this,FormActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.logout -> {


                    prefManager.setLogin(false)

                    mAuth = FirebaseAuth.getInstance()
                    /* if (::mAuth.isInitialized) {
                         mAuth.signOut()
 //                        GoogleSignIn.
                         Toast.makeText(applicationContext,"Logout", Toast.LENGTH_SHORT).show()
                         finish()
                     }*/

                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
                    mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
//                    mGoogleSignInClient= GoogleSignInClient

                    mGoogleSignInClient.signOut().addOnCompleteListener {
                        startActivity(Intent(this@HomeScreen, MainActivity::class.java))
                        finish()
                    }
                    mAuth.signOut()
                    Toast.makeText(applicationContext,"Logout", Toast.LENGTH_SHORT).show()


                }
            }
            true
        }
    }

    override fun initializeInputs() {
    }
    override fun initializeLabels() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.homeScreenmap) as SupportMapFragment
        fetchLocation()*/

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()

        getLocation()
        pieChart()

        prefManager = PrefManager(this)
        prefManager.setLogin(true)

     /*   val mapFragment = supportFragmentManager
            .findFragmentById(R.id.homeScreenmap) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)*/



/*        val ai: ApplicationInfo = applicationContext.packageManager
            ?.getApplicationInfo(applicationContext.applicationContext!!.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["com.google.android.geo.${R.string.google_map_api_key}"]
        val apiKey = value.toString()
        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext.applicationContext,apiKey)
        }*/

      /*  mapFragment.getMapAsync {
            mMap = it

            val originLocation = LatLng( 30.2891496, 78.0437616)

            mMap.addMarker(MarkerOptions().position(originLocation).title("hey"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(originLocation))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 15F))
        }*/
      //  fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        val notificationCard = ArrayList<NotificationList>()
        setUpViews()


        apptitle.text ="Notification"

/*        notificationI.setOnClickListener {
                   notificationLL.visibility = View.VISIBLE
                   notificationI.visibility =View.GONE
                   appbar.visibility = View.GONE
            onBackPressed()

        }*/
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


//        startService(Intent(this,MyService::class.java))
        unregisterBroadcast()

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
        drawerLayout.addDrawerListener(toggle)
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

        mMap = googleMap

        getLocation()
/*
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
                mMap.addMarker(markerOptions)
                *//*val supportMapFragment =
                    supportFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
                supportMapFragment.getMapAsync(this)*//*
            }
        }*/



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions , grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation()
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


    @SuppressLint("MissingPermission")
    private fun getLocation() {


        if (hasGps) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }
//------------------------------------------------------//
        if (hasNetwork) {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                5000,
                0F,
                networkLocationListener
            )
        }


        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        // locationByGps = getLastKnownLocation()
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }
        //------------------------------------------------------//


        val lastKnownLocationByNetwork =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        lastKnownLocationByNetwork?.let {
            locationByNetwork = lastKnownLocationByNetwork
        }
     //------------------------------------------------------//
        if (locationByGps != null && locationByNetwork != null) {
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {


                to_lat = locationByGps?.latitude.toString()
                to_lng = locationByGps?.longitude.toString()

            }else{

                to_lat = locationByNetwork?.latitude.toString()
                to_lng = locationByNetwork?.longitude.toString()

            }
        }else {
            if (locationByNetwork == null) {
                // Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show()

                to_lat = locationByGps?.latitude.toString()
                to_lng = locationByGps?.longitude.toString()

            } else {

                to_lat = locationByNetwork?.latitude.toString()
                to_lng = locationByNetwork?.longitude.toString()


            }


        }
        var geocoder: Geocoder
        //  val addresses: List<Address>
        geocoder = Geocoder(this, Locale.getDefault())


        var strAdd : String? = null
        try {
            val addresses = geocoder.getFromLocation(to_lat!!.toDouble(), to_lng!!.toDouble(), 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = java.lang.StringBuilder("")

                val cityState = returnedAddress.locality +","+returnedAddress.adminArea;
                strAdd = cityState
            } else {
              //  Log.w(" Current loction address", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Log.w(" Current loction address",  e.printStackTrace().toString())
        }

       city.text = strAdd.toString()
/*

        location = LatLng(to_lat.toString().toDouble(), to_lng.toString().toDouble())

//        location = LatLng(Global.curr_lat.toString().toDouble(), Global.curr_long.toString().toDouble())
        val bitmapdraw = resources.getDrawable(R.drawable.logo_round) as BitmapDrawable
        val b = bitmapdraw.bitmap
        val smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false)
        mMap?.addMarker(
            MarkerOptions().position(location!!)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                .title("Driver Location")
        )
        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(location!!, 15F))
*/

    }
    val gpsLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByGps = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}

    }
    val networkLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            locationByNetwork= location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun pieChart(){

        // on below line we are setting user percent value,
        // setting description as enabled and offset for pie chart
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        // on below line we are setting drag for our pie chart
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        pieChart.setHoleRadius(0f)
        pieChart.transparentCircleRadius = 0f

        // on below line we are setting center text
        pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        pieChart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(60f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(10f))
        entries.add(PieEntry(10f))

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.purple_200))
        colors.add(resources.getColor(R.color.yellow))
        colors.add(resources.getColor(R.color.red))
        colors.add(resources.getColor(R.color.lite_blue))

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.setData(data)

        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()


    }

}