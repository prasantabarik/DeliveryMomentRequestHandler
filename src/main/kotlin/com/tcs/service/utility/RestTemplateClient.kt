package com.tcs.service.utility

import com.tcs.service.constant.URLPath.DEL_MOMENT_CRUD
import com.tcs.service.constant.URLPath.GET_ALL_URI
import com.tcs.service.constant.URLPath.GET_BY_ID_URI
import com.tcs.service.constant.URLPath.GET_REF_DATA
import com.tcs.service.model.*

import com.tcs.service.proxy.DeliverymomentClientService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random


@Component
class RestTemplateClient(private val delclient: DeliverymomentClientService, private var restTemplate: RestTemplate) {

//    @Autowired
//    lateinit var restTemplate: RestTemplate
//    private  val basePath = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service"
//    private val basePath = "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service"

    fun validationForUniqueMoments(params: DeliveryMomentModel): Boolean {

        println("result of unique thing")
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()

        mapParams.put("storeNumber", params.storeNumber.toString())
        mapParams.put("streamNumber", params.streamNumber.toString())
        mapParams.put("deliveryDateTime", params.deliveryDateTime.toString())
        mapParams.put("orderDateTime", params.orderDateTime.toString())
        mapParams.put("fillDateTime", params.fillDateTime.toString())
        var uniqueCheckList :List<DeliveryMomentModel>? =  Utility.convert("$DEL_MOMENT_CRUD/deliverymomentunique", DeliveryMomentModel(), mapParams)
         println(uniqueCheckList.isNullOrEmpty())

        // checking if isdeleted flag is true
        if(!uniqueCheckList.isNullOrEmpty() && uniqueCheckList?.get(0)?.isdeleted == true) {

            return true

        } else {
            return uniqueCheckList.isNullOrEmpty()
        }



    }
    fun checkDateTime(date1:String,date2:String) : Boolean {

        var dateList1 = date1.split(" ")
        var dateList2 = date2.split(" ")

        var dateComp1 = dateList1[0].format(DateTimeFormatter.ISO_DATE)
        var dateComp2 = dateList2[0].format(DateTimeFormatter.ISO_DATE)

        if(dateComp1 < dateComp2) {
            return true
        } else if(dateComp1 == dateComp2){
            var timeComp1 = dateList1[1].format(DateTimeFormatter.ISO_TIME)
            var timeComp2 = dateList2[1].format(DateTimeFormatter.ISO_TIME)

            return (timeComp1 <= timeComp2)
            println("result of date check validation")

        } else {
            return false
        }
    }
    fun mainDeliveryFlagChecks(params: DeliveryMomentModel): Boolean? {
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
        mapParams.put("storeNumber", params.storeNumber.toString());
        mapParams.put("streamNumber", params.streamNumber.toString());
        mapParams.put("deliveryDateFrom", params.deliveryDateTime.toString().split(" ")[0])
        mapParams.put("deliveryDateTo", LocalDate.parse(params.deliveryDateTime.toString().split(" ")[0], DateTimeFormatter.ISO_DATE).plusDays(1).toString())

        mapParams.put("mainDeliveryFlag", "J")
        var uniqueCheckList :List<DeliveryMomentModel>? =  Utility.convert("$DEL_MOMENT_CRUD/model", DeliveryMomentModel(), mapParams)
        println("result of main delivery validation")
        return   uniqueCheckList.isNullOrEmpty()
    }



fun postForm(params: DeliveryMomentModel) : ResponseEntity<ServiceResponse>? {
        println("inside postform")

    println("inside unique")
    println(validationForUniqueMoments(params))
    println("inside order")
    println(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString()))
    println("inside delivery")
    println(checkDateTime(params.fillDateTime.toString(),params.deliveryDateTime.toString()))
    println("inside startfill")
    println(checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString()))
    println("inside order")
    println(checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString()))
    println("inside flag check")
    println(mainDeliveryFlagChecks(params))



    if(validationForUniqueMoments(params) && checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString())
            && checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString())
            && checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString())
            && checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString())
            )  {
        println("VALIDATION 1")

        if(params.mainDeliveryFlag == "J" && mainDeliveryFlagChecks(params) == false) {
            println("VALIDATION 2")
            return null
        }

        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
        mapParams.put("storeNumber", params.storeNumber.toString());
        mapParams.put("deliveryStream", params.streamNumber.toString());
        mapParams.put("startDate", params.deliveryDateTime.toString().split(" ")[0]);
        var delivererList = Utility.convertOne(
                "$GET_REF_DATA/deliveryChannel", DeliveryChannel(), mapParams)

        var warehouseList: List<LogisticChannel>? = Utility.convertTwo(
                "$GET_REF_DATA/logisticChannel", LogisticChannel(), mapParams)

        var deliveryScheduleList = Utility.convertThree(
                "$GET_REF_DATA/deliveryscheduleformoment",DeliveryScheduleModel(),mapParams)
        println("warehouse")
        println(warehouseList)
        println("deliverer")
        println(delivererList)
        println("deliveryScheduleList")
        println(deliveryScheduleList)
        if(warehouseList!=null && delivererList != null
                && deliveryScheduleList != null
                 ) {
            println("nullchecklist")
            params.delivererNumber = delivererList[0].delivererNumber
            params.deliverySchemaType = deliveryScheduleList[0].deliverySchemaType

            params.id = params.storeNumber.toString() + params.deliveryDateTime + params.streamNumber

            ///need to check to generate sequence

            params.storeOrder?.add(StoreOrder(orderNumber = Random.nextLong(100000) ,wareHouseNumber = warehouseList?.get(0)?.warehouseNumber))
        } else {
            return null
        }
//        val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model"
//       val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model"
        val httpHeaders = HttpHeaders()
       httpHeaders.contentType = MediaType.APPLICATION_JSON
//       val requestParams = LinkedMultiValueMap<String, String>()

       val httpEntity = HttpEntity<DeliveryMomentModel>(params, httpHeaders)

        println("it comes to hit crud")
        val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(DEL_MOMENT_CRUD + GET_ALL_URI, HttpMethod.POST, httpEntity, ServiceResponse::class.java)

       return response

    } else {
        return null
    }

}



    fun putForm(params: DeliveryMomentModel) : ResponseEntity<ServiceResponse>? {
        println("inside putform")

        println("inside unique")
        println(validationForUniqueMoments(params))
        println("inside order")
        println(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString()))
        println("inside delivery")
        println(checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString()))
        println("inside startfill")
        println(checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString()))
        println("inside order")
        println(checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString()))
        println("inside flag check")
        println(mainDeliveryFlagChecks(params))



        if(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString())
                && checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString())
                && checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString())
                && checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString())
        ) {

             println("It is trying to update")
            var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
            mapParams.put("storeNumber", params.storeNumber.toString());
            mapParams.put("deliveryStream", params.streamNumber.toString());
            mapParams.put("startDate", params.deliveryDateTime.toString().split(" ")[0]);
            var delivererList = Utility.convertOne(
                    "$GET_REF_DATA/deliveryChannel", DeliveryChannel(), mapParams)

            var warehouseList: List<LogisticChannel>? = Utility.convertTwo(
                    "$GET_REF_DATA/logisticChannel", LogisticChannel(), mapParams)

            if(warehouseList!=null && delivererList != null
            ) {
                println("nullchecklist")
                params.delivererNumber = delivererList[0].delivererNumber

                params.id = params.storeNumber.toString() + params.deliveryDateTime + params.streamNumber

                ///need to check to generate sequence

               } else {
                return null
            }
//          val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model"
//          val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model"
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            val requestParams = LinkedMultiValueMap<String, String>()

            val httpEntity = HttpEntity<DeliveryMomentModel>(params, httpHeaders)

            val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(DEL_MOMENT_CRUD + GET_ALL_URI, HttpMethod.POST, httpEntity, ServiceResponse::class.java)

            return response

        } else {
            return null
        }

    }


    fun delForm(id:String) {
//        val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model/{id}"
//        val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model/{id}"

        var parametermap:MutableMap<String, String> = mutableMapOf<String, String>()

        parametermap.put("id" ,id)
        println("Call delete function")
        restTemplate.delete(DEL_MOMENT_CRUD + GET_BY_ID_URI, parametermap);
        println("Call complete")

    }

}

