package com.tcs.service.proxy

interface DeliveryMoment<T> {
    fun getdeliverymomentall(storeNumber: Long?, StreamNumber: Int?,
                             schemaName: String?,deliveryDateTime:String?,orderDateTime:String?,
                             fillDateTime:String?, startFillTime:String?, deliveryDateFrom:String?, deliveryDateTo:String?,
                             orderDateFrom:String?, orderDateTo:String?, fillDateFrom:String?,
                             fillDateTo:String?, startFillTimeFrom:String?, startFillTimeTo:String?,
                             logisticGroupNumber:Int?, mainDeliveryFlag: String?):Any?
}