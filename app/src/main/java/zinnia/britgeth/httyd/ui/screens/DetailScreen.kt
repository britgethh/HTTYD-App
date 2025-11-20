package zinnia.britgeth.httyd.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import zinnia.britgeth.httyd.viewmodel.DragonViewModel

@OptIn(ExperimentalMaterial3Api::class) // Necesario para usar TopAppBar
@Composable
fun DetailScreen(
    viewModel: DragonViewModel,
    dragonId: String,
    onBack: () -> Unit
) {
    // 1. Al entrar, pedimos el detalle del dragón específico
    LaunchedEffect(dragonId) {
        viewModel.fetchDragonDetail(dragonId)
    }

    val dragon = viewModel.selectedDragon

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Dragón") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.isDetailLoading) {
                CircularProgressIndicator()
            } else if (dragon != null) {
                // CONTENIDO DEL DETALLE
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()) // Permite scroll si el texto es largo
                ) {
                    // IMAGEN SUPERIOR
                    AsyncImage(
                        model = dragon.imageUrl,
                        contentDescription = dragon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp), // Altura fija para la imagen de cabecera
                        contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(16.dp)) {
                        // TÍTULO JERARQUIZADO
                        Text(
                            text = dragon.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // CAMPOS EXTRA (Especie y Clase)
                        Text(
                            text = "Especie: ${dragon.species}",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        // Si existe el dato extra 'dragonClass', lo mostramos
                        dragon.dragonClass?.let {
                            Text(
                                text = "Clase: $it",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // DESCRIPCIÓN (CUERPO)
                        Text(
                            text = "Descripción:",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = dragon.description ?: "Sin descripción disponible.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                Text("No se encontró información.")
            }
        }
    }
}