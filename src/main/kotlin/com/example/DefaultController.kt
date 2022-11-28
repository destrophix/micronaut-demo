package com.example

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import org.json.*;


@Controller("/") //
class HelloController {

    @Get(value = "/hello", produces = [MediaType.TEXT_PLAIN]) //
    fun index(): String {
        return "Hello World" //
    }

    @Post(value = "/text/analyze")
        fun analyze(@Body payload:String): String {
        val jsonObject = JSONTokener(payload).nextValue() as JSONObject
        val content = jsonObject.getString("content")
        val character_count_with_spaces = content.length
        var character_count_without_spaces = 0
        var lines = content.count { it == '.'}
        val uniqueWords = hashSetOf<String>()

        // count words
        val strArray = content.split("[\\s.,]".toRegex()).toTypedArray() // Spilt String by Space
        val sb = StringBuilder()
        var count = 0

        //iterate String array
        for (s in strArray) {
            if (s != "") {
                count++
                character_count_without_spaces += s.length
                uniqueWords.add(s.toLowerCase())

            }
        }

        val response = """{
            |"word_count": ${count},
            |"character_count_with_spaces": ${character_count_with_spaces},
            |"character_count_without_spaces": ${character_count_without_spaces},
            |"lines": ${lines},
            |"unique_words": ${uniqueWords.size}
            |}
        """.trimMargin()

            return response
    }
}