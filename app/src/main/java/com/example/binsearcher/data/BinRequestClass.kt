package com.example.binsearcher.data

import android.util.Log
import com.example.binsearcher.data.requestModels.requestModels.BinInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import io.ktor.client.call.body as body1

class BinRequestClass {

    suspend fun BinGetRequest(bin:String):BinInfo?{
        val client = HttpClient(CIO){
            install(ContentNegotiation){
                json(Json{
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        val response = client.get("https://lookup.binlist.net/$bin")

        return if(response.status.value in 200..299){
            val test = Json.decodeFromString<BinInfo>(response.body1())
            Log.e("test", test.toString())
            test
        } else{
            null
        }
    }

    fun parseResponse(responseString :String){

    }
}