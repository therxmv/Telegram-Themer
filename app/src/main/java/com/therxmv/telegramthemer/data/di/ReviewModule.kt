package com.therxmv.telegramthemer.data.di

import android.content.Context
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReviewModule {

    @Singleton
    @Provides
    fun providesReviewManager(context: Context): ReviewManager =
        ReviewManagerFactory.create(context)
}
