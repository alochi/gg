package ru.ochirov.gg.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.ochirov.gg.model.Rates

@FeignClient(name = "RatesFeign", url = "\${openexchangerates.url}")
interface RatesFeign {
    @GetMapping(
        value = ["/latest.json?app_id=\${openexchangerates.id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getLatestRates(): Rates

    @GetMapping(
        value = ["/historical/{date}.json?app_id=\${openexchangerates.id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getHistoricalRates(@PathVariable date: String): Rates

}


