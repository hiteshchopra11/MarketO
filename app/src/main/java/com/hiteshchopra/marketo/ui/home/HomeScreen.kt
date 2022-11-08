package com.hiteshchopra.marketo.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hiteshchopra.marketo.R
import com.hiteshchopra.marketo.data.DateUtil
import com.hiteshchopra.marketo.ui.LottieAnimation

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeScreenVM: HomeScreenVM) {
    val systemUiController = rememberSystemUiController()

    val state = homeScreenVM.viewState.collectAsState()

    when (val data = state.value) {
        is ViewState.Failure -> {

        }
        ViewState.Loading -> {
            LottieAnimation(
                resId = R.raw.loading
            )
        }

        ViewState.NetworkError -> {

        }
        is ViewState.SuccessWithData -> {
            if (data.data.isNotEmpty()) {
                EventsScreenUI(data.data)
            }
        }
    }

    DisposableEffect(systemUiController) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = Color.White
        )

        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {
            systemUiController.setSystemBarsColor(
                color = Color.Black
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun EventsScreenUI(events: List<EventDetailInfo?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            shadowElevation = 4.dp, // play with the elevation values
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .border(BorderStroke(2.dp, Color.Red))
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = "Location"
                    )

                    Text(
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(alignment = Alignment.CenterVertically)
                            .padding(start = 6.dp),
                        text = "Chandigarh",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.weight(1.0F))

                    Image(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )

                }

//                Text(
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .align(alignment = Alignment.Start)
//                        .padding(horizontal = 8.dp, vertical = 8.dp),
//                    text = "Good morning Hitesh!",
//                    color = Color.Black,
//                    fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.displayMedium
//                )

                val category = remember { CategoryHelper() }

                Row {
                    LazyRow(modifier = Modifier.weight(0.9f)) {
                        items(category.categoryItemLists, key = { categoryItem ->
                            categoryItem.id
                        }) { item ->
                            CategoryItem(
                                categoryItem = item, onCheckChanged = category::onItemSelected
                            )
                        }
                    }
                    Image(
                        modifier = Modifier
                            .weight(0.1f)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.ic_bookmarks),
                        contentDescription = "Search"
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(1.0f)
        ) {

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 4.dp),
                text = DateUtil.getAwsDate(),
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall
            )

            LazyColumn {
                items(events) {
                    events.forEach { event ->
                        event?.let { eventDetails -> EventItem(eventDetails = eventDetails) }
                    }
                }
            }
        }
    }
}

@Composable
fun Category(
    textColor: Color, modifier: Modifier, categoryName: String, onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(0.dp),
        onClick = {
            onClick()
        },
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(50.dp))
                .padding(12.dp)
                .border(
                    100.dp,
                    Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            Text(
                color = textColor,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall,
                text = categoryName,
            )
        }
    }
}