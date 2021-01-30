package com.nikhil.kextend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nikhil.library.*
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val context = this

    data class Destination(val name:String, val weight:Int = 0)
    data class Seller(val destination: Destination? = null)
    private var seller:Seller? = null

    @ExperimentalStdlibApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        concepts()
        conceptExample1()
        conceptExample2()
        conceptExample3()
        contextExtensionExamples()
        dateExtensionExamples()
        genericExtensionExamples()
        nullExtensionExamples()
        parseExtensionExamples()
        priceExtensionExamples()
        stringExtensionExamples()
        anyExtensionExamples()
        uriExtensionExamples()
        listExtensionExamples()
        liveDataExtensionExamples()
    }

    private fun concepts() {
        /**
         * How are extensions resolved?
         * Statically.
         *
         * Extensions do not actually modify classes they extend.
         * By defining an extension, you do not insert new members into a class, but merely make new functions
         * callable with the dot-notation on variables of this type.
         */
    }

    private fun conceptExample1() {
        /**
         * Now if we try to call the [someMethod] with [BaseClass] object but the actual reference we pass
         * to it will be [DerivedClass] reference,
         */
        //actual call
        printMessage(DerivedClass())  //This will print BaseClass.someMethod

        /**
         * Even though we passed [DerivedClass] reference, the method is being called from [BaseClass]
         * Because the extension function being called depends only on the declared type of the parameter [base] in [printMessage] method,
         * which is the [BaseClass] class.
         *
         * This is different from runtime polymorphism as here it is resolved statically but not at the runtime.
         */
    }

    /**
     * What if my class has the same method with the same signature that we are adding as an extension function?
     *
     * The compiler will call the method declared in the class but not the extension function.
     */
    private fun conceptExample2(): Unit {
        /**
         * <code>
         *     class BaseClass{
         *          fun someMethod(){
         *              print("I am the actual someMethod")
         *          }
         *     }
         *
         *     fun BaseClass.someMethod(){
         *          print("I am the extension function")
         *     }
         *
         *     //to call this method
         *     BaseClass().someMethod()
         *
         *     This will call the someMethod which s defined in BaseClass and will print "I am the actual someMethod"
         */
    }

    /**
     * Nullable Receiver
     *
     * The extensions can be defined with a nullable receiver type also.
     * These extension function can be called on a nullable object variable.
     */
    private fun conceptExample3(): Unit {
        /**
         * fun Any?.toString(): String {
         *      if (this == null) return "null"
         *      // after the null check,
         *      // 'this' is autocast to a non-null type,
         *      // so the toString() below
         *      // resolves to the member function of the Any class
         *      return toString()
         * }
         */
    }

    private fun contextExtensionExamples() {
        //1
        val vn = versionName ?: "Unknown"
        val vc = versionCode?.toString() ?: "Unknown"
        val appVersion = "App Version: $vn ($vc)"

        //2
        Log.d(TAG, "User's screen size: ${screenSize.x}x${screenSize.y}")

        //3
        vibrate(500) // 500 ms      // Should be called from Activity or other Context
        context.vibrate(500) // 500 ms      // Can be called from any place having "context" variable

        //4
        showToast("Hello")
    }

    private fun dateExtensionExamples() {
        //1
        val currentTime = System.currentTimeMillis()
        println(currentTime)
        println(currentTime.getTimeStamp())
        println(currentTime.getYearMonthDay())
        println("2020-09-20".getDateUnixTime())

        //2
        val json = JSONObject();
        json.put("date", 1598435781)
        val date = json.getIntOrNull("date")?.asDate
        println("date is $date")

        //3
        val format = "yyyy-MM-dd HH:mm:ss"
        val dateObject = Date()
        val str = dateObject.toString(format)
        val date2 = str.toDate(format)
        println("date2 is $date2")
    }

    private fun genericExtensionExamples(): Unit {
        val listOfUsers = mutableListOf<Student>()
        listOfUsers.add(Student(1, "Kotlin"))
        listOfUsers.add(Student(2, "Alex"))
        listOfUsers.add(Student(3, "Mohammed"))
        print(listOfUsers.getUserNameList())

        /*Old way*/
        //1
//        val destination : Destination = seller?.destination ?: Destination(name = "Not found")

        //2
       /* val destination : Destination = if(seller?.destination != null){
            seller?.destination
        }else {
            Destination(name = "Not found")
        }*/

        /*Add default value using extention*/
        val destination : Destination = seller?.destination.orDefault(Destination(name = "Not found"))
        val minWeight : Int = seller?.destination?.weight.orDefault(20)
    }

    private fun nullExtensionExamples(): Unit {
        val name = "John"
        if (name.isNotNull()) {
            println("$name is a programmer!")
        } else {
            println("Null field")
        }

        /**
         * Notice 2 usages:
         * 1. [isNull]
         * 2. [isNull().not()]
         */
        if(name.isNull() || name.length.isNull().not()){
            println("$name is not null and $name length is not null")
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
        //1
        println("11.0".toPriceAmount())
        println("11".toPriceAmount())
        println("05".toPriceAmount())
        println(11.0.toPriceAmount())

        //2
        val amount = 4999
        val doubleAmount = amount.centsToDollars()
        val priceTag = amount.centsToDollarsFormat("\$")

        //3
        val price = 123456789.5.toPrice()
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

        //11
        val apiLoc = "41.6168, 41.6367".toLocation("API")
        println("location is $apiLoc")

        //12
        val digitsOnly = "12345".containsDigit
        val notDigitsOnly = "abc12345".containsDigit
        val alphaNumeric = "abc123".isAlphanumeric
        val notAlphanumeric = "ab.2a#1".isAlphanumeric

        //13
        val uri = "invalid_uri".asUri
        val uri2 = "https://medium.com/@alex_nekrasov".asUri
    }

    @ExperimentalStdlibApi
    private fun anyExtensionExamples() {
        //1
        Log.d(TAG, "User's device: $deviceName")
    }

    private fun uriExtensionExamples() {
        //1
        val uri2 = "https://medium.com/@alex_nekrasov".asUri
        uri2?.open(this)
    }

    private fun listExtensionExamples() {
        //1
        var list = listOf<Int>(1, 2, 3, 4, 5)
        var mid1 = list.midElement()

        //2
        var arrayList = arrayListOf(5, 4, 3, 2, 1)
        var mid2 = arrayList.midElement()
    }

    private fun liveDataExtensionExamples(){
        /*Old way to observe live data*/
//        viewModel.newProfilePicture.observe(this, Observer { string ->
            //Do something here
//        })

        //1
        /*Observe live data with extension*/
//        observe(viewModel.newProfilePicture){ string ->
            //Do something here
//        }

        //2
        /*Observe live data with extention + dedicated function
        It will make code cleaner when you have long code to handle variable of live data
        */
//        observe(viewModel.newProfilePicture, ::showProfilePicture)

        //...

//        private fun showProfilePicture(url: String){
            //Do something here
//        }
    }
}