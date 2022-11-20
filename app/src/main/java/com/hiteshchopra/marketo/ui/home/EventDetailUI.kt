package com.hiteshchopra.marketo.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.hiteshchopra.marketo.R


@OptIn(ExperimentalUnitApi::class)
@Composable
fun EventDetailsUI(eventDetails: EventDetailInfo) {

    val context: Context = LocalContext.current
    val scrollState = rememberScrollState()

    val labels = remember {
        listOf("hitesh", "chopra", "row")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = Color.White)
            .padding(8.dp)
    ) {
        Image(
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .height(height = 300.dp)
                .fillMaxWidth(),
            painter = painterResource(
                id = eventDetails.coverPhoto
            ),
            contentDescription = "Cover Photo"
        )

        Text(
            text = "Title",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            text = eventDetails.title.ifBlank {
                "N/A"
            },
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Description",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            text = eventDetails.description.ifBlank {
                "N/A"
            },
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Labels",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Row {
            eventDetails.label.forEachIndexed { index, label ->
                Log.d("TAG123", "Size is ${eventDetails.label.size} and index is $index")
                Text(
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    text = if (index == eventDetails.label.size - 1) {
                        "${label}"
                    } else {
                        "${label} ,"
                    },
                    color = Color.Black,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Text(
            text = "Expected attendance",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            text = eventDetails.attendance.toString(),
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "Start Date",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            text = eventDetails.startDate,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = "End Date",
            fontSize = TextUnit(22.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Text(
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
            text = eventDetails.endDate,
            color = Color.Black,
            style = MaterialTheme.typography.bodySmall
        )


        FindNearBy(
            context = context,
            query = "hoardings",
            latitudeLongitude = eventDetails.location
        )
        FindNearBy(
            context = context,
            query = "restaurants",
            latitudeLongitude = eventDetails.location
        )
        FindNearBy(context = context, query = "hotels", latitudeLongitude = eventDetails.location)
        FindNearBy(
            context = context,
            query = "taxi stands",
            latitudeLongitude = eventDetails.location
        )
    }
}

@Composable
@OptIn(ExperimentalUnitApi::class)
private fun FindNearBy(context: Context, query: String, latitudeLongitude: LatitudeLongitude) {
    Row(modifier = Modifier.clickable {
        val gmmIntentUri: Uri =
            Uri.parse("geo:${latitudeLongitude.longitude},${latitudeLongitude.latitude}?q=${query}")

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        context.startActivity(mapIntent)
    }) {
        Text(
            fontStyle = FontStyle.Italic,
            text = "Find nearby $query",
            fontSize = TextUnit(18.0f, TextUnitType.Sp),
            modifier = Modifier.padding(start = 2.dp, bottom = 4.dp, top = 8.dp),
        )

        Image(
            painter = painterResource(id = R.drawable.external_link),
            contentDescription = "Location",
            modifier = Modifier
                .padding(start = 6.dp, bottom = 4.dp, top = 8.dp)
                .size(14.dp)
                .align(Alignment.CenterVertically)
        )
    }
}


fun String?.orNA(): String = this ?: "N/A"