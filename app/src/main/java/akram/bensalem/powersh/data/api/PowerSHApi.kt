package akram.bensalem.powersh.data.api

import com.github.theapache64.retrosheet.core.Read
import retrofit2.http.GET
import akram.bensalem.powersh.data.responses.PowerSHShoesResponse

interface PowerSHApi {
    @Read("SELECT *")
    @GET("all_shoes")
    suspend fun getPowerSHSHoes(): List<PowerSHShoesResponse>
}