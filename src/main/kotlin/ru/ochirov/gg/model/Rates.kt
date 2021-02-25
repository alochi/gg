package ru.ochirov.gg.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Rates(@JsonProperty("rates") val getRates: HashMap<String, Double>)
