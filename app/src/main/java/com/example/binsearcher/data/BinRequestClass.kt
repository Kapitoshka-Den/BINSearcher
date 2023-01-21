package com.example.binsearcher.data

import com.example.binsearcher.data.models.requestModels.BinInfo
import com.example.binsearcher.data.models.responseModels.ResponseModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.serialization.json.Json
import java.lang.reflect.Type

class BinRequestClass {

    suspend fun BinGetRequest(bin:String):ResponseModel?{
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


            ResponseModel(response.body())
        } else{
            null
        }
    }

    fun parseResponse(responseString :String){

    }
}