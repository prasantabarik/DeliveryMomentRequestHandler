package com.tcs.service.proxy

interface DeliveryMoment<T> {
    fun getDeliveryMomentAll(storeNumber: Long?, streamNumber: Int?,
                             schemaName: String?,deliveryDateTime:String?,orderDateTime:String?,
                             fillDateTime:String?, startFillTime:String?, deliveryDateFrom:String?, deliveryDateTo:String?,
                             orderDateFrom:String?, orderDateTo:String?, fillDateFrom:String?,
                             fillDateTo:String?, startFillTimeFrom:String?, startFillTimeTo:String?,
                             logisticGroupNumber:Int?, mainDeliveryFlag: String?):Any?
    fun validateForParams(storeNumber: Long?, streamNumber: Int?,
                          schemaName: String?,deliveryDateTime:String?,orderDateTime:String?,
                          fillDateTime:String?, startFillTime:String?, deliveryDateFrom:String?, deliveryDateTo:String?,
                          orderDateFrom:String?, orderDateTo:String?, fillDateFrom:String?,
                          fillDateTo:String?, startFillTimeFrom:String?, startFillTimeTo:String?,
                          logisticGroupNumber:Int?, mainDeliveryFlag: String?): MutableMap<String, String>
}