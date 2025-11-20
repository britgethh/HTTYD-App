package zinnia.britgeth.httyd.network

import zinnia.britgeth.httyd.model.Dragon
import retrofit2.http.GET
import retrofit2.http.Path

interface DragonApiService {

    // Obtener la lista completa de dragones
    @GET("dragons")
    suspend fun getDragons(): List<Dragon>

    // Obtener detalle de un dragón por ID
    @GET("dragons/{id}")
    suspend fun getDragonDetail(@Path("id") id: String): Dragon
}