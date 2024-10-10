package www.udb.edu.sv.desafio3_dsm.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://66fdbb486993693089560e6f.mockapi.io/"
    val retrofitService: ResourceService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ResourceService::class.java)
    }
}
