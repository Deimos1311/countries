package com.it_academy.countries_app.fragments.news

import com.it_academy.countries_app.base.mvi.ViewIntent

sealed class NewsIntent : ViewIntent {
    object LoadAllNews: NewsIntent()
}