package com.hiteshchopra.marketo.ui.home

import androidx.compose.runtime.mutableStateListOf

class CategoryHelper {

    val categoryItemLists = mutableStateListOf(
        CategoryItem(1, "All"),
        CategoryItem(2, "Concerts"),
        CategoryItem(3, "Conferences"),
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
