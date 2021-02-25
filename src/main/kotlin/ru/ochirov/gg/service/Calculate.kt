package ru.ochirov.gg.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class Calculate {
    @Value("\${currency.val}")
    private val currency: String = "USD"

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var ratesFeign: RatesFeign

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var giphyFeign: GiphyFeign

    fun calculateCourse(rates: HashMap<String, Double>, currentCurrency: String): Double {
        val ratesCurrency = rates[currency]?: throw KotlinNullPointerException()
        val ratesCurrentCurrency = rates[currentCurrency]?: throw KotlinNullPointerException()
        return ratesCurrency.div(ratesCurrentCurrency)
    }

    fun isRateMoreThanYesterday(currencyCode: String): Boolean {
        val todayCourse = calculateCourse(getTodayRates(), currencyCode.toUpperCase())
        val yesterdayCourse = calculateCourse(getYesterdayRates(), currencyCode.toUpperCase())
        return todayCourse > yesterdayCourse
    }

    fun getGif(code: String): String =
        giphyFeign.giphyPic(if (isRateMoreThanYesterday(code)) "rich" else "broke")
            .data
            .images
            .original["url"] ?: ""

    fun getTodayRates(): HashMap<String, Double> =
        ratesFeign.getLatestRates().getRates

    private fun getYesterdayRates(): HashMap<String, Double> {
        val yesterdayCalendar = Calendar.getInstance()
        yesterdayCalendar.add(Calendar.DATE, -1)
        val yesterday = SimpleDateFormat("yyyy-MM-dd").format(yesterdayCalendar.time)
        return ratesFeign.getHistoricalRates(yesterday).getRates
    }

}