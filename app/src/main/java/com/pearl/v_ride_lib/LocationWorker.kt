package com.pearl.v_ride_lib

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pearl.common.retrofit.rest_api_interface.LocationApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

     suspend fun doActualWork(): Result {
        val prefManager = PrefManager(applicationContext)
        val retrofit = Retrofit.Builder()
            .baseUrl(Global.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val locationApi = retrofit.create(LocationApi::class.java)

        return withContext(Dispatchers.IO) {
            try {
                val bearerToken = prefManager.getToken()
                val requestData = com.pearl.common.retrofit.data_model_class.LocationRequest(
                    lat = prefManager.getlatitude().toString(),
                    lon = prefManager.getlongitude().toString(),
                    device_token = prefManager.getNotificationToken()
                )
                val response = locationApi.updateLocation("Bearer $bearerToken", requestData)

                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        val msg = responseData.msg
                        val status = responseData.status
                        Log.d("Response", response.body().toString())
                        Log.d("LATLONRES", "$msg $status")

                        Log.d("device_token1", prefManager.getNotificationToken())
//                        Log.d("devicxeL", "$latitude  ---   $longitude")

                        Log.d("latlong","${prefManager.getlatitude()} ${prefManager.getlongitude()}")
                    } else {
                        response.body()?.let { Log.d("else", it.msg) }
                    }
                } else {
                    val errorBody = response.errorBody()
                    val errorMessage = errorBody?.string()
                    Log.e("API Error", "body: $errorBody, errorMessage: $errorMessage  isSuccessful: ${response.isSuccessful}")
                }

                Result.success()
            } catch (e: Exception) {
                Log.e("API Exception", "${e.message} ${e.printStackTrace()} ${e.localizedMessage}")
                Result.failure()
            }
        }
    }

    override fun doWork(): Result {
        return runBlocking {
            doActualWork()
        }
    }
}