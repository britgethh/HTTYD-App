package zinnia.britgeth.httyd.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zinnia.britgeth.httyd.ui.components.DragonItem
import zinnia.britgeth.httyd.viewmodel.DragonViewModel

@Composable
fun HomeScreen(
    viewModel: DragonViewModel,
    onDragonClick: (String) -> Unit // Callback para navegar al detalle
) {
    // 1. Disparar la carga de datos al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchDragons()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // CASO 1: CARGANDO
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        }
        // CASO 2: ERROR
        else if (viewModel.errorMessage != null) {
            Text(text = viewModel.errorMessage ?: "Error desconocido")
        }
        // CASO 3: LISTA DE DATOS
        else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewModel.dragonList) { dragon ->
                    DragonItem(
                        dragon = dragon,
                        onClick = { onDragonClick(dragon.id) }
                    )
                }
            }
        }
    }
}