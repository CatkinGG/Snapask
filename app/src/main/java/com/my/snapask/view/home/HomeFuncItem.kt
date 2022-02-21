package com.my.snapask.view.home

class HomeFuncItem(
    val onUserSelect: ((String) -> Unit) = { _-> },
    val onRepoSelect: ((String) -> Unit) = { _-> }
)