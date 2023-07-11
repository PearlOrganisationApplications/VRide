package com.pearl.v_ride

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.pearl.adapter.AttendanceAdapter
import com.pearl.adapter.NotificationAdapter
import com.pearl.common.retrofit.data_model_class.*
import com.pearl.common.retrofit.rest_api_interface.LocationApi
import com.pearl.common.retrofit.rest_api_interface.ProfileApi
import com.pearl.ui.DocumentStatus
import com.pearl.v_ride_lib.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.*
import kotlin.collections.ArrayList


class HomeScreen : BaseClass(), OnMapReadyCallback {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var navView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var appbar: MaterialToolbar

    //    lateinit var notificationI: ImageView
    lateinit var notificationLL: LinearLayout
    lateinit var ivback: AppCompatImageView
    lateinit var apptitle: AppCompatTextView
    lateinit var notificationRV: RecyclerView
    lateinit var nBell: ImageView
    private lateinit var mMap: GoogleMap
    lateinit var mapLL: LinearLayout
    lateinit var dImage: CircleImageView
    lateinit var drawerName: TextView
    private var hasGps = false
    private var hasNetwork = false
    var to_lat: String? = ""
    var from_lat: String? = ""
    var to_lng: String? = ""
    val apiKey = R.string.google_api_key
    var from_lng: String? = ""
    private var locationByGps: Location? = null
    private var locationByNetwork: Location? = null
    lateinit var locationManager: LocationManager
    private lateinit var location: LatLng

    //    lateinit var mapFragment: Fragment
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    lateinit var mAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var cityTextView: TextView
    lateinit var pieChart: PieChart
    lateinit var prefManager: PrefManager
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var calendarRV: RecyclerView
    lateinit var monthlyCal: TextView
    lateinit var hideCal: TextView
    lateinit var headerLayout: View
    val attendanceCard = ArrayList<AttendanceList>()
    lateinit var toggle_off: LinearLayout
    lateinit var toggle_on: LinearLayout
    lateinit var on_duty: TextView
    lateinit var earnTxt: TextView
    lateinit var off_duty: TextView
    lateinit var hideCalendar: TextView
    lateinit var rate_list: TextView
    lateinit var monthly_pay: TextView
    private lateinit var resourcess: Resources
    private lateinit var homeMenuItem: MenuItem
    private lateinit var profileMenuItem: MenuItem
    private lateinit var walletMenuItem: MenuItem
    private lateinit var earning: MenuItem
    private lateinit var history: MenuItem
    private lateinit var nearest_service: MenuItem
    private lateinit var issue: MenuItem
    private lateinit var document: MenuItem
    private lateinit var language1: MenuItem
    private lateinit var englishLL: LinearLayout
    private lateinit var hindiLL: LinearLayout
    private lateinit var cancelLang: TextView
    lateinit var menu: Menu
    lateinit var context: Context
    private lateinit var dialog: Dialog
    private val senderID = "YOUR_SENDER_ID"

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        internetChangeBroadCast()

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        /*val mapFragment = supportFragmentManager
            .findFragmentById(R.id.homeScreenmap) as SupportMapFragment
        fetchLocation()*/


        prefManager = PrefManager(this)

        prefManager.setLogin(true)

        registerReceiver(gpsBroadcastReceiver, filter)
//        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)

        setLayoutXml()
        initializeViews()
        initializeClickListners()
        initializeInputs()
        initializeLabels()
//        getDocStatus()
        getToken()
//        startService(Intent(this@HomeScreen,MyService::class.java))
        val workRequest = OneTimeWorkRequest.Builder(LocationWorker::class.java).build()
        WorkManager.getInstance(this).enqueue(workRequest)


        resourcess = Global.language(this, resources)
        homeMenuItem.title = resourcess.getString(R.string.home)
        profileMenuItem.title = resourcess.getString(R.string.profile)
        walletMenuItem.title = resourcess.getString(R.string.wallet)
//        earning.title = resourcess.getString(R.string.my_earning)
        history.title = resourcess.getString(R.string.history)
        nearest_service.title = resourcess.getString(R.string.my_nearest_service)
        issue.title = resourcess.getString(R.string.service_request)
        document.title = resourcess.getString(R.string.document)
        language1.title = resourcess.getString(R.string.language)



        pieChart()
        showAttendance()


        getLocation()
//        sendLocation()


        /*    {       val mapFragment = supportFragmentManager
               .findFragmentById(R.id.homeScreenmap) as SupportMapFragment?
           mapFragment!!.getMapAsync(this)*/

/*        val ai: ApplicationInfo = applicationContext.packageManager
            ?.getApplicationInfo(applicationContext.applicationContext!!.packageName, PackageManager.GET_META_DATA)!!
        val value = ai.metaData["com.google.android.geo.${R.string.google_map_api_key}"]
        val apiKey = value.toString()
        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext.applicationContext,apiKey)
        }

            /*  mapFragment.getMapAsync {
                  mMap = it

                  val originLocation = LatLng( 30.2891496, 78.0437616)

                  mMap.addMarker(MarkerOptions().position(originLocation).title("hey"))
                  mMap.moveCamera(CameraUpdateFactory.newLatLng(originLocation))

                  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(originLocation, 15F))
              }*/
            //  fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        }*/

        val notificationCard = ArrayList<NotificationList>()

        setUpViews()


        apptitle.text = "Notification"


/*        notificationI.setOnClickListener {
                   notificationLL.visibility = View.VISIBLE
                   notificationI.visibility =View.GONE
                   appbar.visibility = View.GONE
            onBackPressed()
        }*/

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )
        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )
        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )
        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )
        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )
        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationCard.add(
            NotificationList(
                "Notification Title", "this is my notification body"
            )
        )

        notificationRV.layoutManager = LinearLayoutManager(this)
        val nAdapter = NotificationAdapter(notificationCard)
        notificationRV.adapter = nAdapter
        
//        startService(Intent(this,MyService::class.java))
//        f9P3VrgZQdK2qvvs1_6SqM:APA91bHBC-CEYGwoLU5o8KAf4OVYAgqb0enzB10G3U3gYnEHWdpGKPglzLfXUrmvfefWLGGFerciuM3_5RyZC4QvqX0FshjT1MCpkwvH7RW5IvlBAwwA9xlz0K-HT6TN5jZrY333eYo2
//I/OSG-null__BroadcastReceiver: func1com.pearl.v_ride_lib.BaseClass$IChangeReceiver$1@9df8e07

    }

    private fun getToken() {
        Thread(Runnable {
            try {
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        Log.d("Token -->", token)
                        prefManager.setNotificationToken(token)
                    } else {
                        Log.d("Failed_FCM_token:" ,"${task.exception}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }


    override fun setLayoutXml() {

        setContentView(R.layout.activity_home_screen)
    }

    @SuppressLint("CutPasteId")
    override fun initializeViews() {



        context = SessionManager.setLocale(this@HomeScreen, prefManager.getLanID().toString())
        appbar = findViewById<MaterialToolbar>(R.id.appBar)

        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.nav_view)
        //        notificationI = findViewById(R.id.notificationI)
        notificationLL = findViewById(R.id.notificationLL)
        notificationRV = findViewById(R.id.notificationRV)
        nBell = findViewById(R.id.nBell)
        mapLL = findViewById(R.id.mapLL)
        rate_list = findViewById(R.id.rate_list)

        headerLayout = navView.inflateHeaderView(R.layout.nav_header)
//        navView.inflateMenu(R.menu.nav_menu)
        dImage = headerLayout.findViewById(R.id.drawerImage)
        drawerName = headerLayout.findViewById(R.id.drawerName)
        val dName = headerLayout.findViewById<TextView>(R.id.drawerName)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ivback = findViewById(R.id.ivBack)
        apptitle = findViewById(R.id.titleTVAppbar)

        cityTextView = findViewById(R.id.stateTV)
        pieChart = findViewById(R.id.pieChart)
        swipeRefreshLayout = findViewById(R.id.container)
        calendarRV = findViewById(R.id.calenderRV)
        monthlyCal = findViewById(R.id.monthlyCalender)
        hideCal = findViewById(R.id.hideCalendar)
        hideCalendar = findViewById(R.id.hideCalendar)

        toggle_off = findViewById(R.id.toggle_off)
        toggle_on = findViewById(R.id.toggle_on)
        on_duty = findViewById(R.id.dutyON)
        earnTxt = findViewById(R.id.txt_earn)
        off_duty = findViewById(R.id.dutyOFF)
        monthly_pay = findViewById(R.id.monthly_pay)
        menu = navView.menu
        homeMenuItem = menu.findItem(R.id.homemenu)
        profileMenuItem = menu.findItem(R.id.profile)
        walletMenuItem = menu.findItem(R.id.wallet)
//        earning = menu.findItem(R.id.earning)
        history = menu.findItem(R.id.history)
        nearest_service = menu.findItem(R.id.nearest_service)
        document = menu.findItem(R.id.document)
        issue = menu.findItem(R.id.issue)
        language1 = menu.findItem(R.id.language)
//        hindiLang = menu.findItem(R.id.btnHindiLang)
//        engLang = menu.findItem(R.id.btnEngLang)

//        hindiLang.isVisible=false
//        engLang.isVisible=false

        resourcess = context.resources
//        groupLang = menu.findItem(R.id.group_language)
        dialog = Dialog(this)


    }

    override fun initializeClickListners() {


        toggle_on.setOnClickListener {
            on_duty.visibility = View.GONE
            off_duty.visibility = View.VISIBLE
            toggle_off.visibility = View.VISIBLE
            toggle_on.visibility = View.GONE
        }
        toggle_off.setOnClickListener {
            on_duty.visibility = View.VISIBLE
            off_duty.visibility = View.GONE
            toggle_off.visibility = View.GONE
            toggle_on.visibility = View.VISIBLE
        }
        nBell.setOnClickListener {
//            onBackPressed()
            notificationLL.visibility = View.VISIBLE
            appbar.visibility = View.GONE
            mapLL.setVisibility(View.GONE)
        }
        ivback.setOnClickListener {
            mapLL.setVisibility(View.VISIBLE)
            onBackPressed()
        }
        navView.setNavigationItemSelectedListener {

//           it.isChecked = true
            when (it.itemId) {

                R.id.homemenu -> {
                    startActivity(Intent(this@HomeScreen, HomeScreen::class.java))
                    finish()
                }
                R.id.profile -> {
                    startActivity(Intent(this@HomeScreen, ProfileActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.wallet -> {
                    startActivity(
                            Intent(
                                    this@HomeScreen,
                                    MyWalletActivity::class.java
                            ).putExtra("key", 1)
                    )
                    drawerLayout.closeDrawers()

                }
                /*     R.id.earning-> {
                         startActivity(Intent(this@HomeScreen, MyWalletActivity::class.java).putExtra("key",0))
                         drawerLayout.closeDrawers()

                     }*/

                R.id.history -> {

                    startActivity(Intent(this@HomeScreen, UserHistoryActivity::class.java))
                    drawerLayout.closeDrawers()

                }
                R.id.nearest_service -> {

                    startActivity(Intent(this@HomeScreen, NearestServiceActivity::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.nearest_swap ->{
                    startActivity(Intent(this@HomeScreen, SwapCenter::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.issue -> {
                    startActivity(Intent(this@HomeScreen, IssueRequestActivity::class.java))
                    drawerLayout.closeDrawers()
                    it.isChecked = false
                }
                R.id.document -> {

                    startActivity(Intent(this@HomeScreen, DocumentStatus::class.java))
                    drawerLayout.closeDrawers()
                }
                R.id.language -> {

                    /*  startActivity(Intent(this@HomeScreen, LanguageActivity::class.java))
                      drawerLayout.closeDrawers()*/



                    drawerLayout.closeDrawers()
                    dialog.setContentView(R.layout.language_dialog)
                    dialog.window?.setLayout(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    dialog.setCancelable(false)
                    hindiLL = dialog.findViewById(R.id.hindiLL)!!
                    englishLL = dialog.findViewById(R.id.englishLL)!!
                    cancelLang = dialog.findViewById(R.id.cancelLang)!!
                    dialog.window?.attributes?.windowAnimations = R.style.animation

                    dialog.show()
                    cancelLang.setOnClickListener {
                        dialog.dismiss()
                    }
                    englishLL.setOnClickListener {
                        prefManager.setLangauge("en")
                        dialog.dismiss()
                        finish()
//                        overridePendingTransition(0, 0);
                        startActivity(intent)
//                        overridePendingTransition(0, 0);

                    }
                    hindiLL.setOnClickListener {
                        prefManager.setLangauge("hi")
                        dialog.dismiss()
                        finish()
                        startActivity(intent)
                        /*recreate()
                        dialog.dismiss()*/
                    }
//                    hindiLang.isVisible = !hindiLang.isVisible
                    //navView.menu.clear()


                    // Inflate the new menu
                    //navView.inflateMenu(R.menu.nav_menu)
                }

                R.id.logout -> {


                    mAuth = FirebaseAuth.getInstance()
                    if (::mAuth.isInitialized) {
                        mAuth.signOut()
                        //                        GoogleSignIn.
                        Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
                        finish()
                    }


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
                    Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()

                    prefManager.setLogin(false)
                }

            }
            true
        }

        swipeRefreshLayout.setOnRefreshListener {

            // on below line we are setting is refreshing to false.
            swipeRefreshLayout.isRefreshing = false

        }

        monthlyCal.setOnClickListener {
            /*  calendarRV.visibility = View.VISIBLE
              hideCalendar.visibility = View.VISIBLE
              monthlyCal.visibility = View.GONE*/
        }
        hideCalendar.setOnClickListener {
            calendarRV.visibility = View.GONE
            monthlyCal.visibility = View.VISIBLE
            hideCalendar.visibility = View.GONE
        }
    }

    override fun initializeInputs() {
    }

    override fun initializeLabels() {
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(gpsBroadcastReceiver)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val x = connectivityManager.activeNetworkInfo

        if (x != null) {
            if (!x.isConnected && !x.isConnectedOrConnecting && !x.isAvailable && x.isFailover)
                return false
        } else {
            return false
        }
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)


            if (capabilities != null) {

                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                    return true
                }
            }
        }

        return false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        context = SessionManager.setLocale(this@HomeScreen, prefManager.getLanID().toString())

        resourcess = Global.language(this, resources)
        earnTxt.text = resourcess.getString(R.string.net_earn)
//        cityTextView.text = resourcess.getString(R.string.state)
//        R.id.earning = resourcess.getString(R.string.my_earning)
        on_duty.text = resourcess.getString(R.string.on_duty)
        off_duty.text = resourcess.getString(R.string.off_duty)
        rate_list.text = resourcess.getString(R.string.rate_list)
        monthlyCal.text = resourcess.getString(R.string.monthly_report)
        hideCalendar.text = resourcess.getString(R.string.hide_report)
        rate_list.text = resourcess.getString(R.string.rate_list)
        monthly_pay.text = resourcess.getString(R.string.monthly_pay)
        if (::cancelLang.isInitialized) {
            cancelLang.text = resourcess.getString(R.string.cancel)
        }
        appbar.title = resourcess.getString(R.string.app_name)

        homeMenuItem.title = resourcess.getString(R.string.home)
        profileMenuItem.title = resourcess.getString(R.string.profile)
        walletMenuItem.title = resourcess.getString(R.string.wallet)
//        earning.title = resourcess.getString(R.string.my_earning)
        history.title = resourcess.getString(R.string.history)
        nearest_service.title = resourcess.getString(R.string.my_nearest_service)
        issue.title = resourcess.getString(R.string.service_request)
        document.title = resourcess.getString(R.string.document)
        language1.title = resourcess.getString(R.string.language)


        val isConnected = isNetworkConnected(this.applicationContext)

        registerReceiver(gpsBroadcastReceiver, filter)


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
        if (notificationLL.visibility == View.GONE) {
            //finish
/*            notificationLL.visibility = View.VISIBLE
            notificationI.visibility =View.GONE
            appbar.visibility = View.GONE   */
            finish()
        } else {
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
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun setUpViews() {
        setUpDrawerLayout()
    }

    private fun setUpDrawerLayout() {

        setSupportActionBar(appbar)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        getLocation()
//        sendLocation()
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
            Log.d("locaitonGPS3", locationByGps.toString() + " " + locationByNetwork)
            if (locationByGps!!.accuracy > locationByNetwork!!.accuracy) {


                to_lat = locationByGps?.latitude.toString()
                to_lng = locationByGps?.longitude.toString()

            } else {

                to_lat = locationByNetwork?.latitude.toString()
                to_lng = locationByNetwork?.longitude.toString()

            }
        } else {

            if (locationByNetwork == null) {
                // Toast.makeText(this, "No Network", Toast.LENGTH_LONG).show()
                Log.d("locaitonGPS1", " " + locationByNetwork)
                to_lat = locationByGps?.latitude.toString()
                to_lng = locationByGps?.longitude.toString()

            } else {
                Log.d("locaitonGPS2", locationByGps.toString() + " ")
                to_lat = locationByNetwork?.latitude.toString()
                to_lng = locationByNetwork?.longitude.toString()


            }


        }

        var geocoder: Geocoder
        //  val addresses: List<Address>


        geocoder = Geocoder(this, Locale.getDefault())

        var strAdd: String? = null
        try {
            Log.d("addressX", to_lat + " " + to_lng)
            val url =
                "https://maps.googleapis.com/maps/api/geocode/json?latlng=$to_lat,$to_lng&language=hi&key=$apiKey"

            val request = Request.Builder()
                .url(url)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string()
                    // Parse the response and extract the city name in Hindi
                    // Update the UI with the localized city name
                    Log.d("responseBody", responseBody.toString() + "")
                }
            })

            val addresses = geocoder.getFromLocation(to_lat!!.toDouble(), to_lng!!.toDouble(), 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = java.lang.StringBuilder("")

                val cityState = returnedAddress.locality + "," + returnedAddress.adminArea;
                /* if (prefManager.getLanID() == "hi") {
                     strAdd =

                 }else{
                     strAdd = cityState
                 }*/
                strAdd = cityState

                cityTextView.text = returnedAddress.locality + "," + returnedAddress.adminArea

//                cityTextView.text = cityState
                Log.d("strAdd",strAdd.toString())
                Log.d("cityState",cityState.toString())
                Log.d("cityTextView",cityTextView.toString())

                Log.d("cityX", returnedAddress.toString() + "")
            } else {
//                Log.w(" Current loction address", "No Address returned!")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            //Log.w(" Current loction address",  e.printStackTrace().toString())
        }



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
            locationByNetwork = location
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun pieChart() {

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
        val dataSet = PieDataSet(entries, "Merchant")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.teal_700))
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

    private fun showAttendance() {
        attendanceCard.add(
            AttendanceList(
                "10:00 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.red_dot
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.red_dot
            )
        )
        attendanceCard.add(
            AttendanceList(
                "10 AM", "7.00 PM", "20/12/2022", R.drawable.online
            )
        )
        calendarRV.layoutManager = LinearLayoutManager(this)
        val calAdapter = AttendanceAdapter(attendanceCard)
        calendarRV.adapter = calAdapter
    }

    fun sendLocation() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Global.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val locationApi = retrofit.create(LocationApi::class.java)


        /*    val lat = to_lat.toString()
            val lon = to_lng.toString()*/

/*
        val params = mapOf("lat" to lat, "lon" to lon)
         Log.d("MAP",params.toString())*/

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bearerToken = prefManager.getToken()
                val requestData = LocationRequest(
                    lat = to_lat.toString(),
                    lon = to_lng.toString(),
                    device_token = ""

                )
                Log.d("latlon", "$to_lat $to_lng")

                val response = locationApi.updateLocation("Bearer $bearerToken", requestData)


                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val msg = responseData.msg
                        val status = responseData.status
                        // Process the response data as needed
                        Log.d("Response", response.body().toString())
                        Log.d("LATLONRES", msg+" "+status)
                    } else {
                        // Handle case when responseData is null

                        response.body()?.let { Log.d("else", it.msg) }
                    }
                } else {
                    // Handle unsuccessful response
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string()
//                    showErrorDialog(errorMessage.toString(),"ok")
                    // Handle the case when the API call is unsuccessful
                    // Log or display the error message
                    Log.e("API Error", "body: $errorBody, errorMessage: $errorMessage")
                }

            } catch (e: Exception) {
                // Handle network or API error
                Log.e(
                    "API Exception",
                    "${e.message}  ${e.printStackTrace()}  ${e.localizedMessage}"
                )
            }

        }

    }

    fun getDocStatus() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Global.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(createOkHttpClient())
            .build()


        val profileService = retrofit.create(ProfileApi::class.java)


//                val response = profileApi.getProfileData()

        val token = prefManager.getToken()
        val call = profileService.getProfileData("Bearer $token")
        Log.d("Bearer token",token)
        call.enqueue(object : retrofit2.Callback<ProfileData> {
            override fun onResponse(call: retrofit2.Call<ProfileData>, response: retrofit2.Response<ProfileData>) {
                if (response.isSuccessful) {
                    val profileData = response.body()
                    if (profileData != null) {
                        val profile = profileData.profileData
                        val message = profileData.message
                        val profilePicUrl = profile?.profilePic
                        val profileName = profile?.name


                        drawerName.text = profileName

                        Picasso.get().load(profilePicUrl).placeholder(R.drawable.profile).into(dImage)

                        Log.d("profile", ""+profilePicUrl)
                        Log.d("msg", "$message")
//                        showErrorDialog("$message","ok")

                        // Use the profile data as needed

                    }  else {
                        Log.d("ElseSignup ","t.toString()")
                        val errorResponseCode = response.code()
                        val errorResponseBody = response.errorBody()?.string()
                        // Handle the error response code and body
                        Log.e("API Error3", "Response Code: $errorResponseCode, Body: $errorResponseBody")
                        Log.d("responserror","${response.body()?.message} ${response.body()?.status}")
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<ProfileData>, t: Throwable) {
                when (t) {
                    is SocketTimeoutException -> {
                        // Handle SocketTimeoutException
                        Log.d("fail1", "Socket Timeout: ${t.message}")
                        val errorMessage = "Socket Timeout: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    is IOException -> {
                        // Handle IOException
                        Log.d("fail", "IO Exception: ${t.message}")
                        val errorMessage = "IO Exception: ${t.message}"
                        showErrorToast(errorMessage)
                    }
                    else -> {
                        // Handle other types of exceptions or generic error
                        val errorMessage = "Error: ${t.message}"
                        Log.d("fail3", "Error: ${t.message}")
                        showErrorToast(errorMessage)
                    }
                }
            }

        })



    }

}




/*
private fun requestCityName(latitude: Double, longitude: Double) {

    val language = "hi" // Specify the desired language code, e.g., "fr" for French

    val client = OkHttpClient()
    val url =
        "https://maps.googleapis.com/maps/api/geocode/json?latlng=$to_lat,$to_lng&language=hi&key=$apiKey&language=$language"
    val request = Request.Builder()
        .url(url)
        .build()
    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            val responseData = response.body?.string()
            if (response.isSuccessful && responseData != null) {
                try {
                    val jsonObject = JSONObject(responseData)
                    val results = jsonObject.getJSONArray("results")
                    if (results.length() > 0) {
                        val addressComponents =
                            results.getJSONObject(0).getJSONArray("address_components")
                        for (i in 0 until addressComponents.length()) {
                            val component = addressComponents.getJSONObject(i)
                            val types = component.getJSONArray("types")
                            if (types.toString().contains("locality")) {
                                val city = component.getString("long_name")
                                runOnUiThread {
                                    cityTextView.text = city
                                }
                                break
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    })
}

companion object {
    private const val PERMISSION_REQUEST_LOCATION = 100
}*/
