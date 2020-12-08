package com.tcs.service.model

data class SearchParameters(
        val storeNumber: Long?=0                   ,
        val streamNumber: Int? =0                  ,
        val schemaName: String? =""                ,
        val deliveryDateTime: String?="2020-11-24" ,
        val orderDateTime: String? ="2020-11-20"   ,
        val fillDateTime: String?="2020-11-24"     ,
        val mainDeliveryFlag: String?="J"          ,
        val startFillTime: String?="2020-11-24"    ,
        val deliveryDateFrom:String? = "abc"       ,
        val deliveryDateTo:String? = "xyz"         ,
        val orderDateFrom:String? = "abc"          ,
        val orderDateTo:String? = "xyz"            ,
        val fillDateFrom:String? = "abc"           ,
        val fillDateTo:String? = "xyz"             ,
        val startFillTimeFrom:String? = "abc"      ,
        val startFillTimeTo:String? = "xyz"        ,
        val logisticGroupNumber:Int? = 0


)