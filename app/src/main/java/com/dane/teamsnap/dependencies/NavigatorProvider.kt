package com.dane.teamsnap.dependencies

import com.dane.teamsnap.navigation.Navigator
import com.dane.teamsnap.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigatorModule {
    @Binds
    abstract fun provide(impl: NavigatorImpl): Navigator
}
