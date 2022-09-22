package com.reuben.sampletesting.data

data class Person(
    val firstName: String, val lastName: String, val age: Int
)

val samplePeople = listOf(
    Person("sdfdsf", "Doe", 15),
    Person("John", "sdfsdf", 45),
    Person("sddfdsf", "qwqdwq", 23),
    Person("sdfsddsf", "sdfsdf", 35),
    Person("sdfffds", "Doe", 45),
    Person("Josdfdsfhn", "Doe", 56),
    Person("xc,m xc", "lkna", 324),
    Person("sdfdsf", "sdfdsf", 45),
    Person("John", "s,bc,z", 656),
    Person("Josdfdshn", "asbcakj", 34),
    Person("sdfdsf", "iowhoinqw", 435),
    Person("werewr", "akslbcl", 3434),
    Person("asdbk", "asd", 246),
    Person("sdfdsf", "lkb sacihoh", 853),
    Person("sefklwbe", "sudakgiugw", 3434),
    Person("wefobl", "w.kn. scs", 864),
)

data class NoPersonsFoundException(
    val errorMessage: String
) : Exception()
