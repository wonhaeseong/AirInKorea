package com.phil.airinkorea.manager.di

import com.phil.airinkorea.manager.PermissionManager
import com.phil.airinkorea.manager.PermissionManagerImpl
import com.phil.airinkorea.manager.SettingManager
import com.phil.airinkorea.manager.SettingManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindPermissionManager(permissionManagerImpl: PermissionManagerImpl): PermissionManager

    @Binds
    abstract fun bindSettingManager(settingManagerImpl: SettingManagerImpl): SettingManager
}