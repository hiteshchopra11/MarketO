package com.hiteshchopra.marketo.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.hiteshchopra.marketo.R

@OptIn(ExperimentalUnitApi::class)
@Composable
fun EventItem() {
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
        Column(modifier = Modifier.height(2500.dp)) {
            Row(
                modifier = Modifier
                    .background(color = Color.White)
//                    .border(BorderStroke(2.dp, Color.Red))
                    .clip(RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(height = 80.dp, width = 80.dp),
                    painter = painterResource(
                        id = R.drawable.concert
                    ),
                    contentDescription = "COn cert"
                )

                Column(
                    modifier = Modifier
//                        .border(BorderStroke(2.dp, Color.Gray))
                        .padding(start = 4.dp, top = 0.dp)
                        .width(IntrinsicSize.Max)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 0.dp, start = 4.dp, bottom = 4.dp),
                        fontSize = TextUnit(16.0f, TextUnitType.Sp),
                        text = "Music Concert",
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall
                    )

                    Text(
                        textAlign = TextAlign.Center,
                        fontSize = TextUnit(12.0f, TextUnitType.Sp),
                        modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                        text = "25th November, 2022",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location_grey),
                            contentDescription = "Location",
                            modifier = Modifier
                                .padding(start = 2.dp, bottom = 4.dp)
                                .size(12.dp)
                                .align(Alignment.CenterVertically)
                        )

                        Text(
                            textAlign = TextAlign.Center,
                            fontSize = TextUnit(12.0f, TextUnitType.Sp),
                            modifier = Modifier
                                .padding(start = 2.dp, bottom = 4.dp)
                                .align(Alignment.CenterVertically),
                            text = "Chandigarh",
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1.0f))

                Image(
                    painter = painterResource(id = R.drawable.ic_directions),
                    contentDescription = "Location",
                    modifier = Modifier
                        .padding(start = 2.dp, bottom = 4.dp)
                        .size(24.dp)
                        .align(Alignment.Top)
                )
            }

            Divider()

            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(12.0f, TextUnitType.Sp),
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    text = "987",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )

                Text(
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(12.0f, TextUnitType.Sp),
                    modifier = Modifier.padding(start = 2.dp, bottom = 4.dp),
                    text = "people are expected to attend this concert!!",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.weight(1.0f))

                Image(
                    painter = painterResource(id = R.drawable.ic_bookmark_add),
                    contentDescription = "Location",
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .size(32.dp)
                        .align(Alignment.Bottom)
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewEventItem() {
    EventItem()
}