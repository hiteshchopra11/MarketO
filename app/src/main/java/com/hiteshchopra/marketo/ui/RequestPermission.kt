package com.hiteshchopra.marketo.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.hiteshchopra.marketo.ui.home.HomeScreenVM
import com.hiteshchopra.marketo.ui.home.LatitudeLongitude
import com.hiteshchopra.marketo.ui.home.LocationDetails
import com.hiteshchopra.marketo.ui.navigation.Screen
import java.util.*

@ExperimentalPermissionsApi
@Composable
fun RequestPermission(
    homeScreenVM: HomeScreenVM, navController: NavController
) {

    val context = LocalContext.current

    var permissionState by remember {
        mutableStateOf(PermissionState.Default)
    }

    val mGeocoder = remember {
        Geocoder(context, Locale.getDefault())
    }

    val startForResult = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        when (isPermissionGranted) {
            true -> {
                permissionState = PermissionState.Granted
            }
            false -> {
                permissionState = PermissionState.Denied
            }
        }
    }


    LaunchedEffect(key1 = Unit) {
        permissionState = when (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        )) {
            Log.d("TAG123", "Permission State is ${permissionState.name}"),
            PackageManager.PERMISSION_GRANTED -> {
                PermissionState.Granted
            }
            else -> {
                PermissionState.Requested
            }
        }
    }

    when (permissionState) {
        PermissionState.Requested -> {
            Log.d("TAG123", "Requested")
            CustomDialogLocation(title = "Turn On Location Service",
                desc = "We need your location access to find out upcoming events near you !!",
                onPositiveClicked = {
                    startForResult.launch(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                },
                onNegativeClicked = {
                    permissionState = PermissionState.Denied
                })
        }
        PermissionState.Granted -> {
            Log.d("TAG123", "Granted")
            LaunchedEffect(key1 = true) {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val addressList =
                            mGeocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (addressList != null && addressList.isNotEmpty()) {
                            val address = addressList[0]

                            val latitude = if (address.hasLatitude()) {
                                address.latitude
                            } else {
                                -1.0
                            }

                            val longitude = if (address.hasLongitude()) {
                                address.longitude
                            } else {
                                -1.0
                            }

                            homeScreenVM.setLocationDetails(
                                location = LocationDetails(
                                    completeAddress = address.getAddressLine(0),
                                    city = address.locality,
                                    latitudeLongitude = LatitudeLongitude(
                                        latitude = latitude, longitude = longitude
                                    ),
                                    postalCode = address.postalCode,
                                    country = address.countryName,
                                    countryCode = address.countryCode
                                )
                            )

                            navController.navigate(Screen.Home.route)

                            Log.d("TAG123", "addOnSuccessListener")
                        } else {
                            Log.d("TAG123", "addOnSuccessListener else")
                            Toast.makeText(
                                context,
                                "Could not locate exact position! Switching to default location now!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screen.Home.route)
                        }
                    } else {
                        Log.d("TAG123", "addOnSuccessListener else else")
                        Toast.makeText(
                            context,
                            "Could not locate exact position! Switching to default location now!",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Screen.Home.route)
                    }
                }.addOnFailureListener {
                    Toast.makeText(
                        context,
                        "Could not locate exact position! Switching to default location now!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG123", "addOnFailureListener")
                    navController.navigate(Screen.Home.route)
                }
            }
        }
        PermissionState.Denied -> {
            LaunchedEffect(key1 = true) {
                navController.navigate(Screen.Home.route)
            }
        }
        PermissionState.Default -> {
        }
        PermissionState.NotRequested -> {
            CustomDialogLocation(title = "Turn On Location Service",
                desc = "We need your location access to find out upcoming events near you !!",
                onPositiveClicked = {
                    startForResult.launch(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                },
                onNegativeClicked = {
                    permissionState = PermissionState.Denied
                })
        }
    }
}

enum class PermissionState {
    Requested, Default, Granted, Denied, NotRequested
}