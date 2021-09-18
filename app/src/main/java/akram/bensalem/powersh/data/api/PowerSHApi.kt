package akram.bensalem.powersh.data.api

import akram.bensalem.powersh.data.responses.PowerSHShoesResponse
import com.github.theapache64.retrosheet.core.Read
import retrofit2.http.GET

interface PowerSHApi {
    @Read("SELECT *")
    @GET("all_shoes")
    suspend fun getPowerSHSHoes(): List<PowerSHShoesResponse>
}