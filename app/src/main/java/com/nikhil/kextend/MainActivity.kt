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
        stringExtensionExamples()
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
        //1
        val kotlinExtention = "Kotlin Extensions ObjectSerializer"
        val serializedString = kotlinExtention.serialize()
        println(serializedString)
        println(serializedString.deserialize() as String)

        //2
        val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
        val firstName = json?.getStringOrNull("first_name")
        val lastName = json?.getStringOrNull("last_name")
        println("firstName is $firstName")
        println("lastName is $lastName")
    }

    private fun priceExtensionExamples(): Unit {
        println("11.0".toPriceAmount())
        println("11".toPriceAmount())
        println("05".toPriceAmount())
        println(11.0.toPriceAmount())
    }

    private fun stringExtensionExamples() {
        //1
        val md5Hash = "test".md5 // 098f6bcd4621d373cade4e832627b4f6
        val sha1Hash = "test".sha1 // a94a8fe5ccb19ba61c4c0873d391e987982fbbd3
        println("md5Hash is $md5Hash")
        println("sha1Hash is $sha1Hash")

        //2
        val email = "test@email.com"
        if (email.isEmailValid()) {
            print("Email is valid. Continue registration")
        } else {
            print("Email is not validate. Show error")
        }

        //3
        val phone = "(202)555-0156" // Phone number is fake, but has valid format
        val formattedPhone = phone.formatPhoneNumber(this, "US")
        if (formattedPhone == null) {
            println("Phone number is not valid")
        } else {
            println("Sending $formattedPhone to API")
        }

        //4
        val cl = "Contains letters".containsLatinLetter // true
        val cnl = "12345".containsLatinLetter // false
        val cd = "Contains digits 123".containsDigit // true
        val istr = "123".isIntegerNumber // true
        val dstr = "12.9".toDecimalNumber // true

        //5
        val password = "yt6Hbb2.s(ma**213"
        val password2 = "yt6Hbb2sma213"
        val isPasswordValid =
            !password.isAlphanumeric && password.containsDigit &&
                    password.containsLatinLetter && password.length > 6
                    && password.length < 20 // true
        val isPassword2Valid =
            !password2.isAlphanumeric && password2.containsDigit &&
                    password2.containsLatinLetter && password2.length > 6 &&
                    password2.length < 20 // false, doesn't contain non-alphanumeric characters

        //6
        "com.app.options".save(
            applicationContext,
            mapOf(
                "volume" to 0.8f,
                "fullscreen" to true
            )
        )
        val volume = "com.app.options".load(applicationContext)["volume"] as? Float // 0.8
        println("volume is $volume")

        //7
        val json = "{\"key\": \"value\"}".jsonObject  // {"key": "value"}
        val jsonAgain = json?.toString() // "{"key": "value"}"
        val stringFromJson = json?.getString("key") // "value"

        //8
        val lpc1 = "https://google.com/chrome/".lastPathComponent // chrome
        val lpc2 = "C:\\Windows\\Fonts\\font.ttf".lastPathComponent // font.ttf
        val lpc3 = "/dev/null".lastPathComponent // null

        //9
        val colorHex = "#010203"
        val color = colorHex.asColor // -16711165
        val nonColorHex = "abcdef"
        val nonColor = nonColorHex.asColor // null

        //10
        val ccFormatted = "1234567890123456".creditCardFormatted // "1234 5678 9012 3456"
        println("ccFormatted is $ccFormatted")
    }
}