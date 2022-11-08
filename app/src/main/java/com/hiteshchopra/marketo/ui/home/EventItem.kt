package com.hiteshchopra.marketo.ui.home

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.hiteshchopra.marketo.R
import java.time.ZonedDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalUnitApi::class)
@Composable
fun EventItem(eventDetails: EventDetailInfo) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        shape = CardDefaults.shape,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.White)
    ) {

        val context = LocalContext.current

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .background(color = Color.White)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .weight(4.5f)
                        .size(height = 80.dp, width = 80.dp),
                    painter = painterResource(
                        id = eventDetails.coverPhoto
                    ),
                    contentDescription = "Cover Photo"
                )

                Column(
                    modifier = Modifier
                        .padding(start = 2.dp, top = 0.dp)
                        .weight(12.0f)
                        .width(IntrinsicSize.Max)
                ) {
                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(top = 0.dp, start = 8.dp, bottom = 4.dp),
                        fontSize = TextUnit(16.0f, TextUnitType.Sp),
                        text = eventDetails.title,
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        fontSize = TextUnit(12.0f, TextUnitType.Sp),
                        modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
                        text = eventDetails.startDate,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                    )

                    Row(
                        modifier = Modifier.width(IntrinsicSize.Max)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location_grey),
                            contentDescription = "Location",
                            modifier = Modifier
                                .padding(start = 4.dp, bottom = 4.dp)
                                .size(16.dp)
                                .align(Alignment.CenterVertically)
                        )

                        Text(
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            fontSize = TextUnit(12.0f, TextUnitType.Sp),
                            modifier = Modifier
                                .padding(start = 2.dp, bottom = 4.dp)
                                .align(Alignment.CenterVertically),
                            text = getCityNameWithLocation(
                                context = context,
                                lat = eventDetails.location.latitude,
                                long = eventDetails.location.longitude
                            ),
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Image(
                    painter = painterResource(id = R.drawable.ic_directions),
                    contentDescription = "Directions",
                    modifier = Modifier
                        .weight(2.0f)
                        .padding(start = 2.dp, bottom = 4.dp)
                        .size(24.dp)
                        .align(Alignment.Top)
                )
            }

            Divider()

            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(12.0f, TextUnitType.Sp),
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    text = eventDetails.attendance.toString(),
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    fontSize = TextUnit(12.0f, TextUnitType.Sp),
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 4.dp)
                        .weight(8.0f),
                    text = "people are expected to attend this event!",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.weight(1.0f))

                var isBookMarked by remember { mutableStateOf(false) }

                Crossfade(
                    targetState = isBookMarked, animationSpec = tween(
                        durationMillis = 1000
                    )
                ) { bookMarked ->
                    when (bookMarked) {
                        true -> {
                            Image(painter = painterResource(id = R.drawable.ic_bookmark_added),
                                contentDescription = "Bookmark added",
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .size(32.dp)
                                    .align(Alignment.Bottom)
                                    .weight(2.0f)
                                    .clickable {
                                        isBookMarked = false
                                    })
                        }
                        false -> {
                            Image(
                                painter = painterResource(id = R.drawable.ic_bookmark_add),
                                contentDescription = "Add to Bookmark",
                                modifier = Modifier
                                    .padding(start = 2.dp)
                                    .size(32.dp)
                                    .weight(2.0f)
                                    .align(Alignment.Bottom)
                                    .clickable {
                                        isBookMarked = true
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}