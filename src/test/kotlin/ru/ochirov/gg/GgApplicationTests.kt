package ru.ochirov.gg

import org.junit.Assert
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.ochirov.gg.model.Giphy
import ru.ochirov.gg.model.Rates
import ru.ochirov.gg.service.Calculate
import ru.ochirov.gg.service.GiphyFeign
import ru.ochirov.gg.service.RatesFeign
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


@SpringBootTest
class GgApplicationTests {

    @MockBean
    private lateinit var ratesFeign: RatesFeign

    @MockBean
    private lateinit var giphyFeign: GiphyFeign

    @Autowired
    private lateinit var calc: Calculate


    private var today: String = ""
    private var yesterday: String = ""
    private lateinit var todayRates: HashMap<String, Double>
    private lateinit var yesterdayRates: HashMap<String, Double>
    private lateinit var original: HashMap<String, String>


    @BeforeEach
    fun init() {
        val calendar = Calendar.getInstance()
        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        today = dateFormat.format(calendar.time)
        calendar.add(Calendar.DATE, -1)
        yesterday = dateFormat.format(calendar.time)
        todayRates = hashMapOf("EUR" to 2.0, "USD" to 1.0, "RUB" to 3.0)
        yesterdayRates = hashMapOf("EUR" to 1.0, "USD" to 2.0, "RUB" to 3.0)
        original = hashMapOf("url" to "giphy.gif")
    }

    @Test
    fun `is rate more than yesterday test`() {
        Mockito.`when`(ratesFeign.getLatestRates())
            .thenReturn(Rates(todayRates))
        Mockito.`when`(ratesFeign.getHistoricalRates(yesterday))
            .thenReturn(Rates(yesterdayRates))
        val falseIsBigger: Boolean = calc.isRateMoreThanYesterday("EUR")
        val trueIsBigger: Boolean = calc.isRateMoreThanYesterday("USD")
        assertFalse(falseIsBigger)
        assertTrue(trueIsBigger)
    }

    @Test
    fun `get gif test`(){
        Mockito.`when`(ratesFeign.getLatestRates())
            .thenReturn(Rates(todayRates))
        Mockito.`when`(ratesFeign.getHistoricalRates(yesterday))
            .thenReturn(Rates(yesterdayRates))
        Mockito.`when`(giphyFeign.giphyPic("rich"))
            .thenReturn(Giphy(Giphy.Data(Giphy.Data.Images(original))))
        val giphy = calc.getGif("USD")
        Assert.assertNotNull(giphy)
    }

}
