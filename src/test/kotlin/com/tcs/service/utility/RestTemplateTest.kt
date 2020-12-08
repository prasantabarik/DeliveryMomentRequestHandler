package com.tcs.service.utility

import com.tcs.service.model.DeliveryMomentModel
import com.tcs.service.proxy.DeliverymomentClientService
import org.junit.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.client.RestTemplate

class RestTemplateTest {

    var restTemplateClient: RestTemplateClient = RestTemplateClient(DeliverymomentClientService(), RestTemplate())
    var params: DeliveryMomentModel = DeliveryMomentModel()

    @Test
    fun checkDateTimeTest() {


        val expected = true
        val result = restTemplateClient.checkDateTime("2020-12-31","2021-12-01")

        Assert.assertEquals(expected, result)

    }

    @Test
    fun validateUniqueMomentsTest(){

        params.storeNumber = 7005
        params.streamNumber = 1
        params.deliveryDateTime = "2020-12-02 18:30:00"
        params.orderDateTime = "2020-12-01 21:00:00"
        params.fillDateTime = "2020-12-02 18:30:00"

        val expected = false
        val result = restTemplateClient.validationForUniqueMoments(params)

        Assert.assertEquals(expected, result)

    }

    @Test
    fun mainDeliveryFlagChecksTest(){

        params.storeNumber = 7005
        params.streamNumber = 1
        params.deliveryDateTime = "2020-12-02 18:30:00"

        val expected = false
        val result = restTemplateClient.mainDeliveryFlagChecks(params)

        Assert.assertEquals(expected, result)
    }




}