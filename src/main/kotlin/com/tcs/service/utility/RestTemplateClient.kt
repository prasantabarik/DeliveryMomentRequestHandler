package com.tcs.service.utility

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
    private val basePath = "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service"

    fun validationforUniqueMoments(params: DeliveryMomentModel): Boolean {

        println("result of unique thing")
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()

        mapParams.put("storeNumber", params.storeNumber.toString())
        mapParams.put("streamNumber", params.streamNumber.toString())
        mapParams.put("deliveryDateTime", params.deliveryDateTime.toString())
        mapParams.put("orderDateTime", params.orderDateTime.toString())
        mapParams.put("fillDateTime", params.fillDateTime.toString())
        var uniqueCheckList :List<DeliveryMomentModel>? =  Utility.convert("$basePath/deliverymomentunique", DeliveryMomentModel(), mapParams)
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
    fun maindeliveryflagcheck(params: DeliveryMomentModel): Boolean? {
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
        mapParams.put("storeNumber", params.storeNumber.toString());
        mapParams.put("streamNumber", params.streamNumber.toString());
        mapParams.put("deliveryDateFrom", params.deliveryDateTime.toString().split(" ")[0])
        mapParams.put("deliveryDateTo", LocalDate.parse(params.deliveryDateTime.toString().split(" ")[0], DateTimeFormatter.ISO_DATE).plusDays(1).toString())

        mapParams.put("mainDeliveryFlag", "J")
        var uniqueCheckList :List<DeliveryMomentModel>? =  Utility.convert("$basePath/model", DeliveryMomentModel(), mapParams)
        println("result of main delivery validation")
         return   uniqueCheckList.isNullOrEmpty()
    }



fun postForm(params: DeliveryMomentModel) : ResponseEntity<ServiceResponse>? {
        println("inside postform")

    println("inside unique")
    println(validationforUniqueMoments(params))
    println("inside order")
    println(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString()))
    println("inside delivery")
    println(checkDateTime(params.fillDateTime.toString(),params.deliveryDateTime.toString()))
    println("inside startfill")
    println(checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString()))
    println("inside order")
    println(checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString()))
    println("inside flag check")
    println(maindeliveryflagcheck(params))



    if(validationforUniqueMoments(params) && checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString())
            && checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString())
            && checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString())
            && checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString())
            )  {
        println("VALIDATION 1")

        if(params.mainDeliveryFlag == "J" && maindeliveryflagcheck(params) == false) {
            println("VALIDATION 2")
            return null
        }

        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
        mapParams.put("storeNumber", params.storeNumber.toString());
        mapParams.put("deliveryStream", params.streamNumber.toString());
        mapParams.put("startDate", params.deliveryDateTime.toString().split(" ")[0]);
        var delivererlist = Utility.convertOne(
                "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/deliveryChannel", DeliveryChannel(), mapParams)

        var warehouselist: List<LogisticChannel>? = Utility.convertwo(
                "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/logisticChannel", LogisticChannel(), mapParams)

        var deliveryScheduleList = Utility.converthree(
                "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/deliveryscheduleformoment",DeliveryScheduleModel(),mapParams)
        println("warehouse")
        println(warehouselist)
        println("deliverer")
        println(delivererlist)
        println("deliveryScheduleList")
        println(deliveryScheduleList)
        if(warehouselist!=null && delivererlist != null
                && deliveryScheduleList != null
                 ) {
            println("nullchecklist")
            params.delivererNumber = delivererlist[0].delivererNumber
            params.deliverySchemaType = deliveryScheduleList[0].deliverySchemaType

            params.id = params.storeNumber.toString() + params.deliveryDateTime + params.streamNumber

            ///need to check to generate sequence

            params.storeOrder?.add(StoreOrder(orderNumber = Random.nextLong(100000) ,wareHouseNumber = warehouselist?.get(0)?.warehouseNumber))
        } else {
            return null
        }
//        val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model"
       val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model"
        val httpHeaders = HttpHeaders()
       httpHeaders.contentType = MediaType.APPLICATION_JSON
       val requestParams = LinkedMultiValueMap<String, String>()
      //params.forEach(requestParams::add)

       val httpEntity = HttpEntity<DeliveryMomentModel>(params, httpHeaders)
//      // return restTemplate.postForObject(url, httpEntity, ResponseEntity<T>::class.java)
//       // var serv: ResponseEntity<ServiceResponse>? = null
        println("it comes to hit crud")
        val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ServiceResponse::class.java)

//        serv = restTemplate.postForObject(url, httpEntity, ServiceResponse::class.java)
       return response

    } else {
        return null
    }

}



    fun putForm(params: DeliveryMomentModel) : ResponseEntity<ServiceResponse>? {
        println("inside putform")

        println("inside unique")
        println(validationforUniqueMoments(params))
        println("inside order")
        println(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString()))
        println("inside delivery")
        println(checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString()))
        println("inside startfill")
        println(checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString()))
        println("inside order")
        println(checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString()))
        println("inside flag check")
        println(maindeliveryflagcheck(params))



        if(checkDateTime(params.orderDateTime.toString(), params.deliveryDateTime.toString())
                && checkDateTime(params.deliveryDateTime.toString(),params.fillDateTime.toString())
                && checkDateTime(params.startFillTime.toString(),params.deliveryDateTime.toString())
                && checkDateTime(params.orderDateTime.toString(),params.startFillTime.toString())
        )
        {

//            if(params.mainDeliveryFlag == "J" && maindeliveryflagcheck(params) == false) {
//                return null
//            }
             println("It is trying to update")
            var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()
            mapParams.put("storeNumber", params.storeNumber.toString());
            mapParams.put("deliveryStream", params.streamNumber.toString());
            mapParams.put("startDate", params.deliveryDateTime.toString().split(" ")[0]);
            var delivererlist = Utility.convertOne(
                    "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/deliveryChannel", DeliveryChannel(), mapParams)

            var warehouselist: List<LogisticChannel>? = Utility.convertwo(
                    "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/logisticChannel", LogisticChannel(), mapParams)

            var deliveryScheduleList = Utility.converthree(
                    "http://localhost:3500/v1.0/invoke/getrefdata.edppublic-getrefdata-dev/method/api/v1/getReferenceData/deliveryscheduleformoment",DeliveryScheduleModel(),mapParams)

            if(warehouselist!=null && delivererlist != null
            //&& deliveryScheduleList != null
            ) {
                println("nullchecklist")
                params.delivererNumber = delivererlist[0].delivererNumber
                // params.deliverySchemaType = deliveryScheduleList[0].deliverySchemaType

                params.id = params.storeNumber.toString() + params.deliveryDateTime + params.streamNumber

                ///need to check to generate sequence
               // params.storeOrder?.add(StoreOrder(orderNumber = 12355,wareHouseNumber = warehouselist?.get(0)?.warehouseNumber))
            } else {
                return null
            }
//            val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model"
            val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model"
            val httpHeaders = HttpHeaders()
            httpHeaders.contentType = MediaType.APPLICATION_JSON
            val requestParams = LinkedMultiValueMap<String, String>()
            //params.forEach(requestParams::add)

            val httpEntity = HttpEntity<DeliveryMomentModel>(params, httpHeaders)
//      // return restTemplate.postForObject(url, httpEntity, ResponseEntity<T>::class.java)
//       // var serv: ResponseEntity<ServiceResponse>? = null

            val response: ResponseEntity<ServiceResponse> = restTemplate.exchange(url, HttpMethod.POST, httpEntity, ServiceResponse::class.java)

//     //    serv = restTemplate.postForObject(url, httpEntity, ServiceResponse::class.java)
            return response

        } else {
            return null
        }

    }


    fun delForm(id:String) {
//        val url = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service/model/{id}"
        val url =  "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service/model/{id}"

        var parametermap:MutableMap<String, String> = mutableMapOf<String, String>()

        parametermap.put("id" ,id)
        println("Call delete function")
        restTemplate.delete(url, parametermap);
        println("Call complete")

    }

}

