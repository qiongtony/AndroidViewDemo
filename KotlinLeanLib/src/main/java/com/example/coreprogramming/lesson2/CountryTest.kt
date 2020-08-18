package com.example.coreprogramming.lesson2

class CountryTest {
    fun isBigEuropeanCountry(country: Country) : Boolean{
        return country.continient == "EU" && country.population > 10000
    }
}