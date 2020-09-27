package com.nikhil.kextend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nikhil.library.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contextExtensionExamples()
        dateExtensionExamples()
        genericExtensionExamples()
        nullExtensionExamples()
        parseExtensionExamples()
        priceExtensionExamples()
    }

    private fun contextExtensionExamples() {
        //TODO: add examples
    }

    private fun dateExtensionExamples() {
        val currentTime = System.currentTimeMillis()
        println(currentTime)
        println(currentTime.getTimeStamp())
        println(currentTime.getYearMonthDay())
        println("2020-09-20".getDateUnixTime())
    }

    private fun genericExtensionExamples(): Unit {
        val listOfUsers = mutableListOf<Student>()
        listOfUsers.add(Student(1, "Kotlin"))
        listOfUsers.add(Student(2, "Alex"))
        listOfUsers.add(Student(3, "Mohammed"))
        print(listOfUsers.getUserNameList())
    }

    private fun nullExtensionExamples(): Unit {
        val name = "John"
        if (name.isNotNull()) {
            println("$name is a programmer!")
        } else {
            println("Null field")
        }
    }

    private fun parseExtensionExamples(): Unit {
        val kotlinExtention = "Kotlin Extensions ObjectSerializer"
        val serializedString = kotlinExtention.serialize()
        println(serializedString)
        println(serializedString.deserialize() as String)
    }

    private fun priceExtensionExamples(): Unit {
        println("11.0".toPriceAmount())
        println("11".toPriceAmount())
        println("05".toPriceAmount())
        println(11.0.toPriceAmount())
    }
}