package com.devin.project4_v5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject

class ThirdViewModel : ViewModel() {

    private val apiId: String  = ""                                     //ADD YOUR API ID HERE
    private val apiKey: String = ""                                     //ADD YOUR API KEY HERE
    private var vmFood: MutableLiveData<String> = MutableLiveData()
    private var vmCal: MutableLiveData<String> = MutableLiveData()
    private var vmFat: MutableLiveData<String> = MutableLiveData()
    private var vmFiber: MutableLiveData<String> = MutableLiveData()
    private var vmCarbs: MutableLiveData<String> = MutableLiveData()
    private var vmIcon: MutableLiveData<String> = MutableLiveData()

    //getters for food values
    fun getFoodName(): MutableLiveData<String> {
        return vmFood
    }
    fun getCal(): MutableLiveData<String> {
        return vmCal
    }
    fun getFat(): MutableLiveData<String> { //function = my favorite of eating XD
        return vmFat
    }
    fun getFiber(): MutableLiveData<String> {
        return vmFiber
    }
    fun getCarbs(): MutableLiveData<String> {
        return vmCarbs
    }
    fun getIcon(): MutableLiveData<String> {
        return vmIcon
    }

    //set food value obtained from api
    fun setFood(food: String, queue:RequestQueue) {
        val url="https://api.edamam.com/api/food-database/v2/parser?app_id=${apiId}&app_key=${apiKey}&ingr=${food}&nutrition-type=logging&category=generic-foods"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->

            // create JSONObject
            val obj = JSONObject(response)

            //	get food data
            if(obj.getJSONArray("parsed").length() > 0) {
                var parsedFood = obj.getJSONArray("parsed").getJSONObject(0)
                parsedFood = parsedFood.getJSONObject("food")

                var foodLabel = "no value"
                if(parsedFood.has("label")) {
                    foodLabel = parsedFood.getString("label")
                }
                vmFood.setValue(foodLabel)

                var kCal = "no value"
                if(parsedFood.getJSONObject("nutrients").has("ENERC_KCAL")) {
                    kCal = parsedFood.getJSONObject("nutrients").getString("ENERC_KCAL")
                }
                vmCal.setValue(kCal)

                var fat = "no value"
                if(parsedFood.getJSONObject("nutrients").has("FAT")) {
                    fat = parsedFood.getJSONObject("nutrients").getString("FAT")
                    fat += " g"
                }
                vmFat.setValue(fat)

                var fiber = "no value"
                if(parsedFood.getJSONObject("nutrients").has("FIBTG")) {
                    fiber = parsedFood.getJSONObject("nutrients").getString("FIBTG")
                    fiber += " g"
                }
                vmFiber.setValue(fiber)

                var carbs = "no value"
                if(parsedFood.getJSONObject("nutrients").has("CHOCDF")) {
                    carbs = parsedFood.getJSONObject("nutrients").getString("CHOCDF")
                    carbs += " g"
                }
                vmCarbs.setValue(carbs)

                var icon = "no value"
                if(parsedFood.has("image")) {
                    icon = parsedFood.getString("image")
                }
                vmIcon.setValue(icon)
            }
            else {
                vmFood.setValue("Error getting food data")
            }

            },
        Response.ErrorListener { vmFood.value = "Error getting food data" }) 

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}