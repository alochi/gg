package ru.ochirov.gg.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Giphy(val data: Data) {
    data class Data(val images: Images) {
        data class Images(val original: HashMap<String, String>)
    }
}