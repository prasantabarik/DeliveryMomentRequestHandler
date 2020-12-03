package com.tcs.service.component


import com.nhaarman.mockito_kotlin.whenever
import com.tcs.service.constant.URLPath
import com.tcs.service.constant.URLPath.BASE_URI
import com.tcs.service.constant.URLPath.ENTITY_RESPONSE_JSON_PATH
import com.tcs.service.constant.URLPath.GET_ALL_URI
import com.tcs.service.constant.URLPath.GET_BY_ID_URI
import com.tcs.service.constant.URLPath.GET_RESPONSE_JSON_PATH
import com.tcs.service.constant.URLPath.POST_RESPONSE_JSON_PATH
import com.tcs.service.constant.URLPath.SAMPLE_RESPONSE_JSON_PATH
import com.tcs.service.proxy.DeliverymomentClientService
import com.tcs.service.service.Service
import com.tcs.service.utility.RestTemplateClient
import com.tcs.service.utility.getModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.*
import java.io.File

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
class ControllerTest: BaseTest() {


    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var service: Service

    @MockBean
    lateinit var momentservice: DeliverymomentClientService

    @MockBean
    lateinit var postService: RestTemplateClient



    /**
     * Preparing Mock Stub For service class
     **/
    @BeforeEach
    fun setup() {
        whenever(service.getById(id = dataId)).thenAnswer { getModel() }
        whenever(momentservice.getdeliverymomentall(storeNumber, streamNumber,
                schemaName,deliveryDateTime, orderDateTime,
                fillDateTime, startFillTime, deliveryDateFrom,
                deliveryDateTo, orderDateFrom, orderDateTo,
                fillDateFrom, fillDateTo,
                startFillTimeFrom, startFillTimeTo,logisticGroupNumber, mainDeliveryFlag)).thenAnswer { getModel() }
        whenever(postService.postForm(model)).thenAnswer { getModel() }
//        whenever(service.getById(id = dataId)).thenAnswer { getModel() }
//        whenever(service.getById(id = dataId)).thenAnswer { getModel() }

    }

    /**
     * Test Method  for Controller Get Endpoint
     * Service call is mocked
     **/
    @Test
    fun `should respond data using id`() {
        var expected = File(SAMPLE_RESPONSE_JSON_PATH).readText(Charsets.UTF_8)
        var result: MvcResult =
                mockMvc.get(BASE_URI + GET_BY_ID_URI, dataId)
                {
                    contentType = MediaType.APPLICATION_JSON
                }.andExpect { status { isOk } }.andReturn()
        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
    }

    @Test
    fun `should respond with parameters`(){
        var expected = File(GET_RESPONSE_JSON_PATH).readText(Charsets.UTF_8)
        var result: MvcResult =
                mockMvc.get(BASE_URI + GET_ALL_URI, storeNumber, streamNumber,
                        schemaName,deliveryDateTime, orderDateTime,
                        fillDateTime, startFillTime, deliveryDateFrom,
                        deliveryDateTo, orderDateFrom, orderDateTo,
                        fillDateFrom, fillDateTo,
                        startFillTimeFrom, startFillTimeTo,logisticGroupNumber, mainDeliveryFlag)
                {
                    contentType = MediaType.APPLICATION_JSON
                }.andExpect { status { isOk } }.andReturn()
//        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
        JSONAssert.assertEquals(expected, result.response.contentAsString, false )
    }

//    @Test
//    fun `should respond with post msg`(){
//        var expected = File(POST_RESPONSE_JSON_PATH).readText(Charsets.UTF_8)
//        var result: MvcResult =
//                mockMvc.post(BASE_URI + GET_ALL_URI, File(ENTITY_RESPONSE_JSON_PATH))
//                {
//                    contentType = MediaType.APPLICATION_JSON
//                }.andExpect { status { isOk } }.andReturn()

    var expected = "{\n" +
            "    \"responseCode\": \"200\",\n" +
            "    \"responseDescription\": \"SUCCESS\",\n" +
            "    \"response\": \"Data Successfully Updated\"\n" +
            "}"
    var result: MvcResult =
            mockMvc.put(BASE_URI + GET_ALL_URI )
            {
                contentType = MediaType.APPLICATION_JSON
                content = getBodyJson(URLPath.PUT_REQUEST_BODY)
            }.andExpect { status { isOk } }.andReturn()
////        JSONAssert.assertEquals(expected, result.response.contentAsString, false)
////        JSONAssert.assertEquals(expected, result.response.contentAsString, false )
//        assert(true)
//    }

}