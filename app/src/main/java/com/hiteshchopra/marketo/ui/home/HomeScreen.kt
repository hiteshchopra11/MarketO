package com.hiteshchopra.marketo.ui.home

import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hiteshchopra.marketo.R
import com.hiteshchopra.marketo.domain.model.EventDetailsEntity
import com.hiteshchopra.marketo.ui.LottieAnimation
import java.util.*
import kotlinx.coroutines.flow.Flow


@Composable
fun HomeScreen(
    homeScreenVM: HomeScreenVM, closeApp: () -> Unit
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val location = remember {
        homeScreenVM.getLocationDetails()
    }

    val events = remember {
        mutableStateOf(
            homeScreenVM.getEvents(
                category = null,
                state = "active",
                countryCode = context.resources.configuration.locale.country
            )
        )
    }

    val upcomingEvents = events.value.collectAsLazyPagingItems()

    EventsScreenUI(
        pagingEvents = upcomingEvents, fetchEventDetailsInfo = {
            homeScreenVM.fetchEventDetailsInfo(it)
        }, homeScreenVM = homeScreenVM, context = context, events = events, location = location
    )

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


    BackHandler {
        closeApp()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun EventsScreenUI(
    pagingEvents: LazyPagingItems<EventDetailsEntity.Result>,
    fetchEventDetailsInfo: (EventDetailsEntity.Result) -> EventDetailInfo,
    homeScreenVM: HomeScreenVM,
    context: Context,
    events: MutableState<Flow<PagingData<EventDetailsEntity.Result>>>,
    location: LocationDetails?
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Surface(
            shadowElevation = 4.dp,
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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
                        text = location?.city ?: "Default",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.weight(1.0F))

                }

                val category = remember { CategoryHelper() }

                Row(modifier = Modifier) {
                    category.categoryItemLists.filter { it.id <= 4 }.forEach { item ->
                        CategoryItem(categoryItem = item, onCheckChanged = { categoryItem ->
                            events.value = homeScreenVM.getEvents(
                                category = if (categoryItem.id == 1) {
                                    null
                                } else {
                                    categoryItem.categoryName.lowercase()
                                },
                                state = "active",
                                countryCode = context.resources.configuration.locale.country,
                                debounce = 2000
                            )
                            category.onItemSelected(categoryItem)
                        })
                    }
                }

                Row {
                    category.categoryItemLists.filterNot { it.id <= 4 }.forEach { item ->
                        CategoryItem(categoryItem = item, onCheckChanged = { categoryItem ->
                            events.value = homeScreenVM.getEvents(
                                category = categoryItem.categoryName.lowercase(),
                                state = "active",
                                countryCode = context.resources.configuration.locale.country,
                                debounce = 200
                            )
                            category.onItemSelected(categoryItem)
                        })
                    }
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
                text = "Upcoming Events",
                color = Color.Black,
                style = MaterialTheme.typography.titleSmall
            )

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (pagingEvents.loadState.prepend) {
                    is LoadState.NotLoading -> Unit
                    is LoadState.Loading -> {
                        item {
                            LottieAnimation(
                                resId = R.raw.loading
                            )
                        }
                    }
                    is LoadState.Error -> {

                    }
                }
                when (val state = pagingEvents.loadState.refresh) {
                    is LoadState.NotLoading -> Unit
                    is LoadState.Loading -> {
                        item {
                            LottieAnimation(
                                resId = R.raw.loading
                            )
                        }
                    }
                    is LoadState.Error -> {

                    }
                }

                items(items = pagingEvents, key = {
                    it.id.toString()
                }) { event ->
                    event?.let { eventDetails ->
                        EventItem(
                            eventDetails = fetchEventDetailsInfo(
                                eventDetails
                            )
                        )
                    }
                }

                when (pagingEvents.loadState.append) {
                    is LoadState.NotLoading -> Unit
                    is LoadState.Loading -> {
                        item {
                            LottieAnimation(
                                resId = R.raw.loading
                            )
                        }
                    }
                    is LoadState.Error -> {

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