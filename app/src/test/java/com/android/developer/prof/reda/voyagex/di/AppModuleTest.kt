package com.android.developer.prof.reda.voyagex.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.Mockito
import javax.inject.Singleton
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]// Replace the real module with this mock module
)
object AppModuleTest {
    @Provides
    @Singleton
    fun provideMockFirebaseDatabase(): FirebaseDatabase {
        return Mockito.mock(FirebaseDatabase::class.java)
    }
}