package ru.ochirov.gg.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ru.ochirov.gg.service.Calculate
import ru.ochirov.gg.service.GiphyFeign
import ru.ochirov.gg.service.RatesFeign

@Controller
class HtmlController {

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    lateinit var calc: Calculate


    @GetMapping("/")
    fun main(model: Model): String {
        model["rates"] = calc.getTodayRates()
        return "index"
    }

    @GetMapping("/{code}")
    fun historical(@PathVariable(value = "code") code: String, model: Model): String {
        model["rates"] = calc.getTodayRates()
        try {
            model["gif"] = calc.getGif(code)
        } catch (e: KotlinNullPointerException) {
            model["gifinfo"] = "Проблема с валютой"
        }
        return "index"
    }
}

