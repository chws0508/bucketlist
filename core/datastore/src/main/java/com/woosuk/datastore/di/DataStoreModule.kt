package com.woosuk.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.woosuk.datastore.SettingPreferences
import com.woosuk.datastore.SettingPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideSettingPreferences(
        @ApplicationContext context: Context,
        settingPreferencesSerializer: SettingPreferencesSerializer,
    ): DataStore<SettingPreferences> = DataStoreFactory.create(
        serializer = settingPreferencesSerializer,
    ) {
        context.dataStoreFile("user_preferences.pb")
    }
}
