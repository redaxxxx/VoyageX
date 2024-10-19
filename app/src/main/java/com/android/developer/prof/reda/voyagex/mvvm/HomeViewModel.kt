package com.android.developer.prof.reda.voyagex.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.developer.prof.reda.voyagex.models.CategoryModel
import com.android.developer.prof.reda.voyagex.models.ItemModel
import com.android.developer.prof.reda.voyagex.models.LocationModel
import com.android.developer.prof.reda.voyagex.models.SliderModel
import com.android.developer.prof.reda.voyagex.util.Resources
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val db: FirebaseDatabase
) : ViewModel(){

    private val _banners = MutableStateFlow<Resources<List<SliderModel>>>(Resources.Unspecified())
    val banners: StateFlow<Resources<List<SliderModel>>>
        get() = _banners

    private val _categories = MutableStateFlow<Resources<List<CategoryModel>>>(Resources.Unspecified())
    val categories: StateFlow<Resources<List<CategoryModel>>>
        get() = _categories

    private val _populars = MutableStateFlow<Resources<List<ItemModel>>>(Resources.Unspecified())
    val populars: StateFlow<Resources<List<ItemModel>>>
        get() = _populars

    private val _recommendPlaces = MutableStateFlow<Resources<List<ItemModel>>>(Resources.Unspecified())
    val recommendPlaces: StateFlow<Resources<List<ItemModel>>>
        get() = _recommendPlaces

    private val _locations = MutableStateFlow<Resources<List<LocationModel>>>(Resources.Unspecified())
    val locations: StateFlow<Resources<List<LocationModel>>>
        get() = _locations
    init {
        loadBanner()
        loadCategories()
        loadPopulars()
        loadRecommendItems()
        loadLocations()
    }

    private fun loadLocations(){
        runBlocking {
            _locations.emit(Resources.Loading())
        }

        val ref = db.getReference("Location")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val locationModelList = mutableListOf<LocationModel>()
                for (childSnapshot in snapshot.children){
                    val locationModel = childSnapshot.getValue(LocationModel::class.java)

                    if (locationModel != null) locationModelList.add(locationModel)
                }
                viewModelScope.launch {
                    _locations.emit(Resources.Success(locationModelList))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                viewModelScope.launch {
                    _locations.emit(Resources.Failed(error.message))
                }
            }
        })
    }
    private fun loadRecommendItems(){
        runBlocking {
            _recommendPlaces.emit(Resources.Loading())
        }

        val ref = db.getReference("Item")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children){
                    val item = childSnapshot.getValue(ItemModel::class.java)

                    if (item != null) items.add(item)
                }

                viewModelScope.launch {
                    _recommendPlaces.emit(Resources.Success(items))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                viewModelScope.launch {
                    _recommendPlaces.emit(Resources.Failed(error.message))
                }
            }
        })
    }
    private fun loadPopulars(){
        runBlocking {
            _populars.emit(Resources.Loading())
        }

        val ref = db.getReference("Popular")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val popularList = mutableListOf<ItemModel>()
                for (snapshotChild in snapshot.children){
                    val popular = snapshotChild.getValue(ItemModel::class.java)

                    if (popular != null) popularList.add(popular)
                }

                viewModelScope.launch {
                    _populars.emit(Resources.Success(popularList))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                viewModelScope.launch {
                    _populars.emit(Resources.Failed(error.message))
                }
            }
        })
    }
    private fun loadCategories(){
        runBlocking {
            _categories.emit(Resources.Loading())
        }

        val ref = db.getReference("Category")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val categoryList = mutableListOf<CategoryModel>()

                for (snapshotChild in snapshot.children){
                    val category = snapshotChild.getValue(CategoryModel::class.java)

                    if (category != null) categoryList.add(category)
                }

                viewModelScope.launch {
                    _categories.emit(Resources.Success(categoryList))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                viewModelScope.launch {
                    _categories.emit(Resources.Failed(error.message))
                }
            }
        })
    }
     fun loadBanner(){
        runBlocking {
            _banners.emit(Resources.Loading())
        }

        val ref = db.getReference("Banner")

        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bannersList = mutableListOf<SliderModel>()

                for (childSnapshot in snapshot.children){
                    val banner = childSnapshot.getValue(SliderModel::class.java)

                    if (banner != null) bannersList.add(banner)
                }

                viewModelScope.launch {
                    _banners.emit(Resources.Success(bannersList))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                viewModelScope.launch {
                    _banners.emit(Resources.Failed(error.message))
                }
            }
        })
    }
}