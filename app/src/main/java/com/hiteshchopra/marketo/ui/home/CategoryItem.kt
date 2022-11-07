package com.hiteshchopra.marketo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(
    categoryItem: CategoryItem, onCheckChanged: (CategoryItem) -> Unit
) {
    val brush = Brush.horizontalGradient(
        listOf(
            Color(0xFF667db6), Color(0xFF0082c8), Color(0xFF0082c8), Color(0xFF667db6)
        )
    )

    val textColor = if (categoryItem.isSelected) {
        Color.White
    } else {
        Color.Gray
    }

    val modifier = if (categoryItem.isSelected) {
        Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(brush = brush)
    } else {
        Modifier
    }

    Category(
        textColor = textColor, modifier = modifier, categoryName = categoryItem.categoryName
    ) {
        onCheckChanged(categoryItem.copy(isSelected = !categoryItem.isSelected))
    }
}


data class CategoryItem(
    val id: Int,
    val categoryName: String,
    val isSelected: Boolean = false
)