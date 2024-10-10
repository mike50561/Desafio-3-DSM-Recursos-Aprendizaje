package www.udb.edu.sv.desafio3_dsm.network

import retrofit2.Response
import retrofit2.http.*
import www.udb.edu.sv.desafio3_dsm.model.Recurso

interface ResourceService {
    @GET("Desafio-DSM")
    suspend fun getAllResources(): Response<List<Recurso>>

    @GET("Desafio-DSM/{id}")
    suspend fun getResource(@Path("id") id: String): Response<Recurso>


    @POST("Desafio-DSM")
    suspend fun addResource(@Body recurso: Recurso): Recurso

    @PUT("Desafio-DSM/{id}")
    suspend fun updateResource(@Path("id") id: String, @Body recurso: Recurso): Recurso

    @DELETE("Desafio-DSM/{id}")
    suspend fun deleteResource(@Path("id") id: String): Response<Unit>
}
