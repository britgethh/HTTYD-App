package zinnia.britgeth.httyd.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import zinnia.britgeth.httyd.model.Dragon
import zinnia.britgeth.httyd.network.RetrofitClient
import kotlinx.coroutines.launch

class DragonViewModel : ViewModel() {

    // ESTADO DE LA LISTA (HOME)
    // Usamos 'var' con mutableStateOf para que la UI sepa cuándo redibujarse
    var dragonList by mutableStateOf<List<Dragon>>(emptyList())
        private set // Solo modificable desde dentro de esta clase

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // ESTADO DEL DETALLE
    var selectedDragon by mutableStateOf<Dragon?>(null)
        private set

    var isDetailLoading by mutableStateOf(false)
        private set

    // ---------------------------------------------------------
    // FUNCIONES
    // ---------------------------------------------------------

    // 1. Obtener la lista de dragones (Se llama al iniciar la app)
    fun fetchDragons() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                // Llamamos a la API (Paso 3)
                val dragons = RetrofitClient.instance.getDragons()
                dragonList = dragons
            } catch (e: Exception) {
                errorMessage = "Error al cargar: ${e.localizedMessage}"
                e.printStackTrace() // Para ver el error en Logcat
            } finally {
                isLoading = false
            }
        }
    }

    // 2. Obtener el detalle de un dragón por ID
    fun fetchDragonDetail(id: String) {
        viewModelScope.launch {
            isDetailLoading = true
            selectedDragon = null // Limpiamos el anterior para que no se vea información vieja
            try {
                val dragon = RetrofitClient.instance.getDragonDetail(id)
                selectedDragon = dragon
            } catch (e: Exception) {
                errorMessage = "Error al cargar detalle: ${e.localizedMessage}"
            } finally {
                isDetailLoading = false
            }
        }
    }
}