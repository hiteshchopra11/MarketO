package com.hiteshchopra.marketo.ui.home

import androidx.compose.runtime.mutableStateListOf

class CategoryHelper {

    val categoryItemLists = mutableStateListOf(
        CategoryItem(1, EventCategory.ALL.capitaliseFirstLetter(), true),
        CategoryItem(2, EventCategory.CONFERENCES.capitaliseFirstLetter()),
        CategoryItem(3, EventCategory.CONCERTS.capitaliseFirstLetter()),
        CategoryItem(4, EventCategory.SPORTS.capitaliseFirstLetter()),
        CategoryItem(5, EventCategory.PERFORMING_ARTS.capitaliseFirstLetter()),
        CategoryItem(6, EventCategory.COMMUNITY.capitaliseFirstLetter()),
    )

    fun onItemSelected(selectedCategoryItem: CategoryItem) {
        val iterator = categoryItemLists.listIterator()

        while (iterator.hasNext()) {
            val listItem = iterator.next()

            iterator.set(
                if (listItem.id == selectedCategoryItem.id) {
                    selectedCategoryItem
                } else {
                    listItem.copy(isSelected = false)
                }
            )
        }
    }
}
