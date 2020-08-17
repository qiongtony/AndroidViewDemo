package com.example.kotlindemo.domain

import com.example.kotlindemo.date.Forecast

public interface Command <T>{
    fun execute() : T
}

data class ForecastList(val city : String, val  country : String, val dailyForecast : List<Forecast>)

data class Foreast(val date : String, val description : String, val high : Int, val low : Int)
