package com.tcs.service.proxy

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.tcs.service.constant.URLPath.DEL_MOMENT_CRUD
import com.tcs.service.model.DeliveryMomentModel
import com.tcs.service.utility.Utility
import org.apache.logging.log4j.kotlin.logger
import org.json.JSONObject
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.isEqualTo


@Service
class DeliverymomentClientService : DeliveryMoment<DeliveryMomentModel> {
//    val logger = logger()
//    private val basePath = "http://deliverymomentcrud-edppublic-deliverymomentcrud-dev.59ae6b648ca3437aae3a.westeurope.aksapp.io/api/v1/deliveryMoment-Crud-service"
//     private val basePath = "http://localhost:3500/v1.0/invoke/deliverymomentcrud.edppublic-deliverymomentcrud-dev/method/api/v1/deliveryMoment-Crud-service"

    override fun validateForParams(storeNumber: Long?, streamNumber: Int?, schemaName: String?, deliveryDateTime: String?, orderDateTime: String?, fillDateTime: String?, startFillTime: String?, deliveryDateFrom: String?, deliveryDateTo: String?, orderDateFrom: String?, orderDateTo: String?, fillDateFrom: String?, fillDateTo: String?, startFillTimeFrom: String?, startFillTimeTo: String?, logisticGroupNumber: Int?, mainDeliveryFlag: String?): MutableMap<String, String> {
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()


        if(storeNumber == null && streamNumber == null &&
                schemaName == null && deliveryDateTime == null && deliveryDateFrom == null && deliveryDateTo == null
                && startFillTime == null && startFillTimeFrom == null && startFillTimeTo == null && orderDateTime == null && orderDateFrom == null && orderDateTo == null
                && logisticGroupNumber== null && fillDateTime == null && fillDateFrom == null && fillDateTo == null
                && mainDeliveryFlag == null){
            println("Inside IF")
            return mapParams
        }
        if( storeNumber != null) {
            mapParams.put("storeNumber", storeNumber.toString());
        }

        if( streamNumber != null) {
            mapParams.put("streamNumber", streamNumber.toString());
        }

        if( schemaName != null) {
            mapParams.put("schemaName", schemaName);
        }
        if( deliveryDateTime != null) {
            mapParams.put("deliveryDateTime", deliveryDateTime);
        }
        if( deliveryDateFrom != null) {
            mapParams.put("deliveryDateFrom", deliveryDateFrom);
        }
        if( deliveryDateTo != null) {
            mapParams.put("deliveryDateTo", deliveryDateTo);
        }

        if( orderDateTime != null) {
            mapParams.put("orderDateTime", orderDateTime);
        }
        if( orderDateFrom != null) {
            mapParams.put("orderDateFrom", orderDateFrom);
        }
        if( orderDateTo != null) {
            mapParams.put("orderDateTo", orderDateTo);
        }

        if( fillDateTime != null) {
            mapParams.put("fillDateTime", fillDateTime);
        }
        if( fillDateFrom != null) {
            mapParams.put("fillDateFrom", fillDateFrom);
        }
        if( fillDateTo != null) {
            mapParams.put("fillDateTo", fillDateTo);
        }

        if( startFillTime != null) {
            mapParams.put("startFillTime", startFillTime);
        }
        if( startFillTimeFrom != null) {
            mapParams.put("startFillTimeFrom", startFillTimeFrom);
        }
        if( startFillTimeTo != null) {
            mapParams.put("startFillTimeTo", startFillTimeTo);
        }
        if(logisticGroupNumber != null){
            mapParams.put("logisticGroupNumber", logisticGroupNumber.toString());
        }
        if(mainDeliveryFlag != null){
            mapParams.put("mainDeliveryFlag", mainDeliveryFlag.toString());
        }
        return mapParams
    }


    override fun getDeliveryMomentAll(storeNumber: Long?, streamNumber: Int?,
                                      schemaName: String?,deliveryDateTime:String?,orderDateTime:String?,
                                      fillDateTime:String?,
                                      startFillTime:String?, deliveryDateFrom:String?, deliveryDateTo:String?,
                                      orderDateFrom:String?, orderDateTo:String?, fillDateFrom:String?,
                                      fillDateTo:String?, startFillTimeFrom:String?, startFillTimeTo:String?,
                                      logisticGroupNumber:Int?, mainDeliveryFlag: String?)
            : List<DeliveryMomentModel>? {
        var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()

//        if(storeNumber == null && StreamNumber == null &&
//                schemaName == null && deliveryDateTime == null && deliveryDateFrom == null && deliveryDateTo == null
//                && startFillTime == null && startFillTimeFrom == null && startFillTimeTo == null && orderDateTime == null && orderDateFrom == null && orderDateTo == null
//                && logisticGroupNumber== null && fillDateTime == null && fillDateFrom == null && fillDateTo == null
//                && mainDeliveryFlag == null){
//            println("Inside IF")
//            return Utility.convert("$DEL_MOMENT_CRUD/model", DeliveryMomentModel(), mapParams)
//        }
//
          mapParams = validateForParams(storeNumber, streamNumber,
                  schemaName,deliveryDateTime, orderDateTime,
                  fillDateTime, startFillTime, deliveryDateFrom,
                  deliveryDateTo, orderDateFrom, orderDateTo,
                  fillDateFrom, fillDateTo,
                  startFillTimeFrom, startFillTimeTo,logisticGroupNumber, mainDeliveryFlag)

        return Utility.convert("$DEL_MOMENT_CRUD/model", DeliveryMomentModel(), mapParams)
    }
//    companion object {
//
//        fun convertList(jsonObject: JSONObject): List<DeliveryMomentModel> {
//            return when {
//                jsonObject.has("response") -> {
//                    val mapper = ObjectMapper().registerKotlinModule()
//                    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
//                    mapper.readValue<List<DeliveryMomentModel>>(jsonObject["response"].toString(),
//                            object : TypeReference<List<DeliveryMomentModel>>() {})
//                }
//                else -> {
//                    listOf()
//                }
//            }
//        }
//    }

}
