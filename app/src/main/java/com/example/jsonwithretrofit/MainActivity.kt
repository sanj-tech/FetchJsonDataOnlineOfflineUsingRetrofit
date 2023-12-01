package com.example.jsonwithretrofit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.jsonwithretrofit.Util.ConnectivityUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val subCategoryDetailsList = mutableListOf<SubCategory>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ConnectivityUtil.isNetworkConnected(this)){
            dataFromServer()
        }else{
            dataFromRoom()
        }
    }

    private fun dataFromRoom() {
        val db = AppDatabase.getInstance(this)
        val subCategoriesOffline = db.subCategoryDao().getAllSubCategories()
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = SubCategoryAdapter(subCategoriesOffline as MutableList<SubCategory>)

        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
//
//        subCategoryDetailsList.clear()
//        subCategoryDetailsList.addAll(subCategoriesOffline)
//        adapter.updateData(subCategoriesOffline)

    }

    private fun dataFromServer() {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://vasundharaapps.com/artwork_apps/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getApplications().enqueue(object : Callback<ApiModel?> {
            override fun onResponse(call: Call<ApiModel?>, response: Response<ApiModel?>) {
                val apiModel = response.body()
                Log.d("API_RESPONSE", "Response: $apiModel")
                if (response.isSuccessful) {
                    response.body()
                    val subCategories = response.body()!!.app_center
                        ?.flatMap { it.sub_category }
                        ?.filter { it.banner_image?.isNotEmpty() == true && it.banner_image != "null" }

                    subCategories?.forEach { subCategory ->
                        // val subCategoryInfoList = subCategories?.map {
//                            SubCategory(
//                                it.id,
//                                it.name,
//                                it.banner_image ?: ""
//                            )


                        // }
                        // Process each subcategory here
                        val id = subCategory.id
                        val subName = subCategory.name
                        val bannerImage = subCategory.banner_image

                        val subCategoryDetails =
                            SubCategory(id, subName, bannerImage)

                          Log.d("MyList","$subCategoryDetails")

                        val db =
                            AppDatabase.getInstance(applicationContext) // Get instance of your Room database
                        GlobalScope.launch(Dispatchers.IO) {
                            db.subCategoryDao()
                                .insertAll(listOf(subCategoryDetails))
                        }

                        subCategoryDetailsList.add(subCategoryDetails)
                        updateRecyclerView()
//                        val recyclerView = findViewById<RecyclerView>(R.id.rv)
//                        val adapter = SubCategoryAdapter(subCategoryDetailsList)
//
//                        recyclerView.adapter = adapter
//                        Log.d(
//                            "SUBCATEGORY_DETAILS",
//                            "ID: $subCategoryId, Name: $subCategoryName, Banner Image: $bannerImage"
//                        )

                        // Perform other operations as needed for each subcategory

//                        val subCategory = SubCategory(subCategoryId,subCategoryName,bannerImage/* Your SubCategory details */)
//
//                        // Call the function to save the SubCategory
//                        subCategoryRepository.saveSubCategoryDetails(subCategory)
//
//
//                        GlobalScope.launch {
//                            subCategories?.let {
//
//
//                            }
//                        }
                    }
                } else {
                    Log.e("API_ERROR", "Error fetching data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiModel?>, t: Throwable) {
                Log.e("API_ERROR", "Error fetching data: ${t.message}")
            }
        })

    }

    private fun updateRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
                        val adapter = SubCategoryAdapter(subCategoryDetailsList)

                        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}