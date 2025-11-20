package zinnia.britgeth.httyd.model

import com.google.gson.annotations.SerializedName

data class Dragon(
    // El ID es necesario para la navegación
    @SerializedName("id")
    val id: String,

    // Título del dragón
    @SerializedName("name")
    val name: String,

    // Especie (usaremos esto como subtítulo)
    @SerializedName("species")
    val species: String,

    // Imagen del dragón
    @SerializedName("image")
    val imageUrl: String,

    // Descripción extendida (puede venir vacía en la lista)
    @SerializedName("description")
    val description: String? = null,

    // Clase del dragón (ej. "Strike Class"), dato extra para el detalle
    @SerializedName("class")
    val dragonClass: String? = null
)