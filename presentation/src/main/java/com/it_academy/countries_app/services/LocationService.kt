package com.it_academy.countries_app.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.content.ContextCompat
import com.it_academy.countries_app.R
import com.it_academy.domain.*

class LocationService : Service(), LocationListener {

    private var locationManager: LocationManager? = null
    private var userLocation: Location? = null
    private var checkGPSStatus = false
    private var checkNetworkStatus = false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if (!it.hasExtra("kill_service")) {
                initLocationScan()
                initNotifications()
            } else {
                killService()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun initNotifications() {
        val intent = Intent()
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.notification_foreground_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, CHANNEL_ID)

        val textStyle = NotificationCompat.BigTextStyle()
            .setBigContentTitle(getString(R.string.notification_foreground_name))

        notificationBuilder.apply {
            this
                .setWhen(System.currentTimeMillis())
                .setStyle(textStyle)
                .setContentIntent(pendingIntent)
                .setContentTitle(getString(R.string.countries_app))
                .setContentText(getString(R.string.notification_content_text))
                .setSmallIcon(R.drawable.ic_launcher_foreground)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                this.priority = NotificationManager.IMPORTANCE_DEFAULT
            } else {
                this.priority = PRIORITY_DEFAULT
            }
        }
        startForeground(SERVICE_ID, notificationBuilder.build())
    }

    @SuppressLint("MissingPermission")
    private fun initLocationScan(): Location? {
        try {
            locationManager =
                applicationContext?.getSystemService(LOCATION_SERVICE) as LocationManager
            checkGPSStatus =
                locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true
            checkNetworkStatus =
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
            if (!checkGPSStatus) {
                Log.e("GPS", "GPS turned off")
            } else {
                applicationContext?.let {
                    if (ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        &&
                        ContextCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager?.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            TIME_INTERVAL_UPDATES_MILLIS,
                            DISTANCE_CHANGE_FOR_UPDATES,
                            this
                        )
                        if (locationManager != null) {
                            userLocation =
                                locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return userLocation
    }

    override fun onLocationChanged(location: Location) {
        val intent = Intent().apply {
            action = LOCATION_ACTION
            putExtra("location", location)
        }
        sendBroadcast(intent)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    private fun stopListening() {
        if (locationManager != null) {
            locationManager?.let {
                applicationContext?.let {
                    locationManager?.removeUpdates(this@LocationService)
                }
            }
        }
    }

    private fun killService() {
        stopListening()
        stopForeground(true)
        stopSelf()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        killService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}