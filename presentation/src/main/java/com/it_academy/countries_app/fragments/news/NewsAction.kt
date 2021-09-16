package com.it_academy.countries_app.fragments.news

import com.it_academy.countries_app.base.mvi.ViewAction

sealed class NewsAction : ViewAction {
    object AllNews: NewsAction()
}