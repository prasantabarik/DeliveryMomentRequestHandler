package com.ahold.commerce.config


import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.mongodb.BasicDBObjectBuilder
import com.mongodb.DBObject
import com.tcs.service.component.BaseTest
import com.tcs.service.model.SearchParameters
import org.bson.types.ObjectId
import java.io.File
import java.time.LocalDate

/**
 * Method for preparing stub  data from sample json
 **/

//fun getModel(): Store {
//    val mapper = jacksonObjectMapper()
//    mapper.registerModule(JavaTimeModule())
//    mapper.registerModule(ParameterNamesModule())
//    mapper.registerModule(Jdk8Module())
//    mapper.registerKotlinModule()
//    val jsonString: String = File(URLPath.DUMMY_DATA_COLLECTION).readText(Charsets.UTF_8)
//    return mapper.readValue(jsonString)
//}

fun getBodyJson(path: String): String {
    val mapper = jacksonObjectMapper()
    mapper.registerModule(JavaTimeModule())
    mapper.registerModule(ParameterNamesModule())
    mapper.registerModule(Jdk8Module())
    mapper.registerKotlinModule()
    return File(path).readText(Charsets.UTF_8)
}

fun getStoreMasterData(): DBObject
{

    val deliveryAddress = mapOf(
            "city" to "Zaandam",
    "country" to "NLD",
    "houseNumber" to 15,
    "postalCode" to "1506 MA",
    "street" to "Provincialeweg"
    )

    val visitingAddress = mapOf(
    "city" to "Zaandam",
    "country" to "NLD",
    "houseNumber" to 5,
    "postalCode" to "1508 AD",
    "street" to "Hermitage"
    )

    val poBoxAddress = mapOf(
    "city" to "Zaandam",
    "country" to "NLD",
    "poBoxNumber" to 18,
    "postalCode" to "1500 AA"
    )


    val storeMasterData = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "id" to 401920201,
            "name" to "Zaandam Hermitage test",
            "storeNumber" to 1006,
            "addressCodeEan" to 8735431765341,
            "deliveryAddress" to deliveryAddress,
            "openingDate" to "2020-11-10",
            "closingDate" to "2024-01-15",
            "franchiseFlag" to null,
            "createdAt" to "2020-11-05T18:24:23.205Z",
            "updatedAt" to "2020-11-12T12:27:12.584Z",
            "visitingAddress" to visitingAddress,
            "poBoxAddress" to poBoxAddress


    )


    return BasicDBObjectBuilder.start(storeMasterData).get()
}

fun getStoreParametersData(): DBObject {
    val parameter = mapOf(
            "name" to "eslEnabled"
    )
    val storeParameter = mapOf(
            "id" to ObjectId("5fbfa370ea68b422b67d2854"),
            "newValue" to "true",
            "newValueValidFrom" to "2003-01-16",
            "value" to "Hanshow",
            "parameter" to parameter

    )

    val storeParameters = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "createdAt" to "2020-11-05T18:24:23.205Z",
            "updatedAt" to "2020-11-12T12:27:12.584Z",
            "storeParameters" to listOf(storeParameter)
    )

    return BasicDBObjectBuilder.start(storeParameters).get()
}

fun getStoreTemporaryClosureData(): DBObject {

    val temporaryClosurePeriod = mapOf(
            "dateTimeClosedFrom" to "2020-11-29T11:00:00",
     "dateTimeClosedUntil" to "2020-11-30T11:00:00",
    "description" to "store moving from Hermitage to Westzijde",
     "reason" to "Relocation"
    )
    val storeTemporaryClosurePeriodDetails = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "id" to 401920201,
            "createdAt" to "2020-11-05T18:24:23.205Z",
            "updatedAt" to "2020-11-12T12:27:12.584Z",
            "temporaryClosurePeriods" to listOf(temporaryClosurePeriod)
    )

    return BasicDBObjectBuilder.start(storeTemporaryClosurePeriodDetails).get()
}

fun getStoreOpeningHoursData(): DBObject {

    val weekday = mapOf(
            "_id" to ObjectId(),
            "description" to "maandag",
    "name" to "ma"
    )
    val openingHours = mapOf(
            "closingTime" to "22:00:00.000",
    "openingTime" to "08:30:00.000",
    "weekday" to weekday
    )
    val storeOpeningHours = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "id" to 401920201,
            "createdAt" to "2020-11-05T18:24:23.205Z",
            "updatedAt" to "2020-11-12T12:27:12.584Z",
            "openingHours" to listOf(openingHours)
    )

    return BasicDBObjectBuilder.start(storeOpeningHours).get()
}

fun getSearchParameter(): SearchParameters = SearchParameters(
        storeNumber=0,
        streamNumber=0,
        schemaName="abc",
        deliveryDateTime="2020-11-24",
        orderDateTime="2020-11-20",
        fillDateTime="2020-11-24",
        mainDeliveryFlag="J",
        startFillTime="2020-11-24",
        deliveryDateFrom="abc",
        deliveryDateTo="xyz",
        orderDateFrom="abc",
        orderDateTo="xyz",
        fillDateFrom="abc",
        fillDateTo="xyz",
        startFillTimeFrom="abc",
        startFillTimeTo="xyz",
        logisticGroupNumber=0
)
//
//fun getStore() = Store(
//        _id = "5fbfa481bf1bb2213cfb50c8",
//        storeNumber = 1006,
//        id = 401920201
//
//)
//
//fun getDeliveryAddress() = DeliveryAddress(
//        city = "Zaandam",
//        country = "NLD",
//        houseNumber =  15,
//        postalCode = "1506 MA",
//        street = "Provincialeweg",
//        houseNumberSuffix = "AD"
//)

fun getStoreGroupingDetails() : DBObject {

    val storeGroupMembershipPeriod = mapOf(
            "_id" to ObjectId(),
            "storeGrouping" to "Format Struct",
    "storeGroup" to "AH XL",
    "startDate" to "2020-01-01",
    "endDate" to "2020-12-01"
    )

    val storeGroupingDetails = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "id" to 401920201,
    "createdAt" to "2020-11-05T18:24:23.205Z",
    "updatedAt" to "2020-11-12T12:27:12.584Z",
            "storeGroupMembershipPeriods" to listOf(storeGroupMembershipPeriod)
    )

    return BasicDBObjectBuilder.start(storeGroupingDetails).get()
}

fun getStoreOrganizationDetails() : DBObject {

    val operationalCluster = mapOf(
            "_id" to ObjectId(),
            "name" to "RKP 204 Noord/West"
    )

    val storeRegion = mapOf(
            "_id" to ObjectId(),
            "name" to "Regio Noord/West"
    )

    val storeOrgStructureMembership = mapOf(
            "operationalCluster" to operationalCluster,
            "storeRegion" to storeRegion
    )

    val storeOrganization = mapOf(
            "_id" to ObjectId("5fbfa481bf1bb2213cfb50c8"),
            "createdAt" to "2020-11-26T13:38:49.382",
    "updatedAt" to "2020-11-27T09:00:07.204",
    "id" to 401920201,
    "storeOrgStructureMembership" to storeOrgStructureMembership
    )
    return BasicDBObjectBuilder.start(storeOrganization).get()
}

