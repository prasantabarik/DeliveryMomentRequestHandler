package com.tcs.service.utility

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.tcs.service.model.DeliveryChannel
import com.tcs.service.model.DeliveryMomentModel
import com.tcs.service.model.DeliveryScheduleModel
import com.tcs.service.model.LogisticChannel
import khttp.get

object Utility {

    fun convert(url: String, objectType: Any, params: MutableMap<String, String>): List<DeliveryMomentModel>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)

        return when (true) {
             true -> {
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {
                    is DeliveryMomentModel -> {
                        print(mapper)

                        mapper.readValue(jsonObject["response"].toString(), Array<DeliveryMomentModel>::class.java).toMutableList()
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }

    fun convertOne(url: String, objectType: Any, params: MutableMap<String, String>): List<DeliveryChannel>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)
        return when (true){
            true -> {
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {
                    is DeliveryChannel -> {
                        mapper.readValue(jsonObject["response"].toString(), Array<DeliveryChannel>::class.java).toMutableList()
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }
    fun convertTwo(url: String, objectType: Any, params: MutableMap<String, String>): List<LogisticChannel>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)
        return when (true) {
            true -> {
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {

                    is LogisticChannel -> {
                        mapper.readValue(jsonObject["response"].toString(), Array<LogisticChannel>::class.java).toMutableList()
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }

    // for del schedule
    fun convertThree(url: String, objectType: Any, params: MutableMap<String, String>): List<DeliveryScheduleModel>? {

        val jsonObject = get(url = url, params = params).jsonObject
        println(jsonObject)
        return when (true){
           true -> {
                val mapper = ObjectMapper().registerKotlinModule()
                mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                println(objectType)
                when (objectType) {
                    is DeliveryScheduleModel -> {
                        mapper.readValue(jsonObject["response"].toString(), Array<DeliveryScheduleModel>::class.java).toMutableList()
                    }
                    else -> {
                        null
                    }
                }
            }
            false -> {
                null
            }
        }
    }


}
