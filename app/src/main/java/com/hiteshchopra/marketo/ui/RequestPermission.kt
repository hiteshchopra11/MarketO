package com.hiteshchopra.marketo.ui

import android.util.Log
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.hiteshchopra.marketo.R

@ExperimentalPermissionsApi
@Composable
fun RequestPermission(
    permission: String,
    rationaleMessage: String = stringResource(R.string.give_permisison_to_use_app),
) {
    val permissionState = rememberPermissionState(permission)

    HandleRequest(permissionState = permissionState, deniedContent = { shouldShowRationale ->
        PermissionDeniedContent(
            rationaleMessage = rationaleMessage, shouldShowRationale = shouldShowRationale
        ) {
            permissionState.launchPermissionRequest()
        }
    }, content = {
        /*   Content(
               text = "PERMISSION GRANTED!",
               showButton = false
           ) {}*/
    })
}

@ExperimentalPermissionsApi
@Composable
fun HandleRequest(
    permissionState: PermissionState,
    deniedContent: @Composable (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    when (val permissionStatus = permissionState.status) {
        is PermissionStatus.Granted -> {
            Log.d("TAG123", "Granted")
            content()
        }
        is PermissionStatus.Denied -> {
            Log.d("TAG123", "Denied")
            deniedContent(permissionStatus.shouldShowRationale)
        }
    }
}

@Composable
fun Content(showButton: Boolean = true, onClick: () -> Unit) {
    if (showButton) {
        val enableLocation = remember { mutableStateOf(true) }
        if (enableLocation.value) {
            CustomDialogLocation(
                title = "Turn On Location Service",
                desc = "Explore the world without getting lost and keep the track of your location.\n\nGive this app a permission to proceed. If it doesn't work, then you'll have to do it manually from the settings.",
                enableLocation,
                onClick
            )
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun PermissionDeniedContent(
    rationaleMessage: String, shouldShowRationale: Boolean, onRequestPermission: () -> Unit
) {

    if (shouldShowRationale) {

        AlertDialog(onDismissRequest = {}, title = {
            Text(
                text = "Permission Request", style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            )
        }, text = {
            Text(rationaleMessage)
        }, confirmButton = {
            Button(onClick = onRequestPermission) {
                Text("Give Permission")
            }
        })

    } else {
        Content(onClick = onRequestPermission)
    }

}