package com.tcs.service.proxy

import com.ahold.commerce.config.getBodyJson
import com.ahold.commerce.config.getSearchParameter
import com.mongodb.internal.connection.tlschannel.util.Util.assertTrue
import com.nhaarman.mockito_kotlin.whenever
import com.tcs.service.constant.URLPath
import com.tcs.service.model.DeliveryMomentModel
import com.tcs.service.service.Service
//import com.tcs.service.utility.Utility.convert
import com.tcs.service.utility.getModel
import org.bouncycastle.asn1.cms.CMSAttributes.contentType
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.put
import com.tcs.service.utility.Utility
import java.io.File
import java.time.LocalDate
import java.time.ZoneId

class ProxyClientTest {

    @Autowired
    val proxyClient = DeliverymomentClientService()

//    @MockBean
//    lateinit var utility: com.tcs.service.utility.Utility

    @MockBean
    var mapParams: MutableMap<String, String> = mutableMapOf<String, String>()

    @BeforeEach
    fun setup() {
//        whenever(Utility.convert("${URLPath.DEL_MOMENT_CRUD}/model", DeliveryMomentModel(), mapParams)).thenAnswer { getModel() }

    }

    @Test
    fun validateSearchParameter() {
        val param = getSearchParameter()

        val params = proxyClient.validateForParams(param.storeNumber, param.streamNumber, param.schemaName, param.deliveryDateTime, param.orderDateTime,
                                                    param.fillDateTime, param.startFillTime, param.deliveryDateFrom, param.deliveryDateTo,
                                                    param.orderDateFrom, param.orderDateTo, param.fillDateFrom, param.fillDateTo, param.startFillTimeFrom,
                                                    param.startFillTimeTo, param.logisticGroupNumber, param.mainDeliveryFlag)

        Assert.assertEquals(params["storeNumber"], param.storeNumber.toString())
        Assert.assertEquals(params["streamNumber"], param.streamNumber.toString())
        Assert.assertEquals(params["schemaName"], param.schemaName)
        Assert.assertEquals(params["deliveryDateTime"], param.deliveryDateTime )
        Assert.assertEquals(params["orderDateTime"], param.orderDateTime )
        Assert.assertEquals(params["fillDateTime"], param.fillDateTime )
        Assert.assertEquals(params["startFillTime"], param.startFillTime )
        Assert.assertEquals(params["deliveryDateFrom"], param.deliveryDateFrom )
        Assert.assertEquals(params["deliveryDateTo"], param.deliveryDateTo )
        Assert.assertEquals(params["orderDateFrom"], param.orderDateFrom )
        Assert.assertEquals(params["orderDateTo"], param.orderDateTo )
        Assert.assertEquals(params["fillDateFrom"], param.fillDateFrom )
        Assert.assertEquals(params["fillDateTo"], param.fillDateTo )
        Assert.assertEquals(params["startFillTimeFrom"], param.startFillTimeFrom )
        Assert.assertEquals(params["startFillTimeTo"], param.startFillTimeTo )
        Assert.assertEquals(params["logisticGroupNumber"], param.logisticGroupNumber.toString() )
        Assert.assertEquals(params["mainDeliveryFlag"], param.mainDeliveryFlag )
    }

    @Test
    fun getDeliveryMomentAllTest(){

        var expected  = "[DeliveryMomentModel(id=70052020-11-211, storeNumber=7005, streamNumber=1, deliveryStreamName=HOUDBAAR, schemaName=BASISSCHEMA, deliveryDateTime=2020-12-02 18:30:00, orderDateTime=2020-12-01 21:00:00, initialPromotionFlag=N, orderStatus=null, totalInitialOrderQuantity=null, totalOrderQuantity=null, boxSize=90010, fillDateTime=2020-12-02 18:30:00, mainDeliveryFlag=J, storeAdviseFlag=N, deliverySchemaType=null, delivererNumber=2, startFillTime=2020-12-02 18:00:00, storeOrder=[{orderNumber=5236352, warehouseNumber=1}], logisticGroupExclusion=[LogisticGroupExclusion(logisticGroupNumber=123)], createdBy=pnl0060s, creationDateTime=2020-11-04 13:05:27, updatedBy=pnl0060s, updateDateTime=2020-11-04 13:05:27, isdeleted=false)]"
        var result = proxyClient.getDeliveryMomentAll(7005, 1, null, "2020-12-02 18:30:00",
        null, null,null,null,null,null,null,null,null,null,null,null,null)

        Assert.assertEquals(expected.toString(), result.toString() )
    }
}