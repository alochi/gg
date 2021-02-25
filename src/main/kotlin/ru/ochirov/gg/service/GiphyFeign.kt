package ru.ochirov.gg.service

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import ru.ochirov.gg.model.Giphy


@FeignClient(name = "GiphyFeign", url = "\${giphy.url}")
interface GiphyFeign {
    @GetMapping(value = ["?api_key=\${giphy.id}&tag={tag}&rating=g"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun giphyPic(@PathVariable tag: String): Giphy
}