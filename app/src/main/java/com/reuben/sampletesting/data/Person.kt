package com.reuben.sampletesting.data

data class Person(
    val firstName: String, val lastName: String, val age: Int
)

val samplePeople = listOf(
    Person("sdfdsf", "Doe", 15),
    Person("John", "sdfsdf", 45),
    Person("sdfdsf", "Doe", 23),
    Person("sdfdsf", "sdfsdf", 35),
    Person("sdfds", "Doe", 45),
    Person("Josdfdsfhn", "Doe", 56),
    Person("John", "Doe", 324),
    Person("sdfdsf", "sdfdsf", 45),
    Person("John", "Doe", 656),
    Person("Josdfdshn", "Doe", 34),
    Person("sdfdsf", "sdff", 435),
)

data class NoPersonsFoundException(
    val errorMessage: String
) : Exception()
