package com.android.developer.prof.reda.voyagex.home.homeFragment

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.android.developer.prof.reda.voyagex.models.SliderModel
import com.android.developer.prof.reda.voyagex.mvvm.HomeViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.argumentCaptor
import javax.inject.Inject
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    // Set up TestCoroutineDispatcher for testing coroutine-based code
    @get:Rule
    val dispatcher = StandardTestDispatcher()

    @Inject
    private lateinit var database: FirebaseDatabase

    private lateinit var ref: DatabaseReference
    private lateinit var viewModel: HomeViewModel
    private var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        Dispatchers.setMain(dispatcher)

        // Inject mock Firebase Database
        hiltRule.inject()
        database = Mockito.mock(FirebaseDatabase::class.java)

        ref = Mockito.mock(DatabaseReference::class.java)

        viewModel = HomeViewModel(database)

        // Mock the database reference behavior
        Mockito.`when`(database.getReference("Banner")).thenReturn(ref)
    }

    @Test
    suspend fun loadBanner(){
        //Mock snapshot
        val mockSnapshot = Mockito.mock(DataSnapshot::class.java)
        val mockBanner1 = SliderModel("https://firebasestorage.googleapis.com/v0/b/project189-ede2a.appspot.com/o/banner1.png?alt=media&token=f525f102-1a82-4bc3-b697-b9d3b2e85e26")
        val mockBanner2 = SliderModel("https://firebasestorage.googleapis.com/v0/b/project189-ede2a.appspot.com/o/banner2.png?alt=media&token=898ca000-1f21-42df-84e1-e073ec464ae7")

        val mockChildren = listOf(
            Mockito.mock(DataSnapshot::class.java)
                .apply { Mockito.`when`(getValue(SliderModel::class.java)).thenReturn(mockBanner1) },
            Mockito.mock(DataSnapshot::class.java)
                .apply { Mockito.`when`(getValue(SliderModel::class.java)).thenReturn(mockBanner2) }
        )
        Mockito.`when`(mockSnapshot.children).thenReturn(mockChildren.asIterable())

        // Capture the ValueEventListener
        val eventListenerCaptor = argumentCaptor<ValueEventListener>()
        Mockito.doNothing().`when`(ref).addListenerForSingleValueEvent(eventListenerCaptor.capture())

        // Call ViewModel function
        viewModel.loadBanner()

        // Simulate onCancelled
        eventListenerCaptor.firstValue.onDataChange(mockSnapshot)

        // Verify StateFlow emits the correct list of users
        viewModel.banners.test {
            val banners = awaitItem()
            assert(banners.data!!.size == 2)
            assert(banners.data!![0] == mockBanner1)
            assert(banners.data!![1] == mockBanner2)
        }
    }

    @Test
    fun `fetchBanner onCancelled sets null to state flow`() = runTest {
        // Capture the ValueEventListener
        val eventListenerCaptor = argumentCaptor<ValueEventListener>()
        Mockito.doNothing().`when`(ref).addListenerForSingleValueEvent(eventListenerCaptor.capture())

        viewModel.loadBanner()

        // Simulate onCancelled
        eventListenerCaptor.firstValue.onCancelled(Mockito.mock(DatabaseError::class.java))

        // Test emitted flow values using Turbine
        viewModel.banners.test {
            val banners = awaitItem()
            banners.data?.let { assert(it.isEmpty()) }
        }
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }
}