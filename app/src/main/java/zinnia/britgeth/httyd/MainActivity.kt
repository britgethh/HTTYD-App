package zinnia.britgeth.httyd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import zinnia.britgeth.httyd.ui.screens.DetailScreen
import zinnia.britgeth.httyd.ui.screens.HomeScreen
import zinnia.britgeth.httyd.ui.theme.HTTYDAppTheme
import zinnia.britgeth.httyd.viewmodel.DragonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usamos el tema por defecto que creó Android Studio
            // Si tu tema tiene otro nombre (ej. DragonAppTheme), cámbialo aquí.
            zinnia.britgeth.httyd.ui.theme.HTTYDAppTheme {

                // 1. Creamos el controlador de navegación
                val navController = rememberNavController()

                // 2. Instanciamos el ViewModel (el cerebro que guarda los datos)
                // La función viewModel() lo guarda para que sobreviva giros de pantalla
                val viewModel: DragonViewModel = viewModel()

                // 3. Definimos el mapa de navegación
                NavHost(navController = navController, startDestination = "home") {

                    // RUTA 1: LISTA (HOME)
                    composable("home") {
                        HomeScreen(
                            viewModel = viewModel,
                            onDragonClick = { dragonId ->
                                // Al hacer click, navegamos a la ruta de detalle pasando el ID
                                navController.navigate("detail/$dragonId")
                            }
                        )
                    }

                    // RUTA 2: DETALLE
                    // Definimos que esta ruta espera un argumento llamado "dragonId"
                    composable(
                        route = "detail/{dragonId}",
                        arguments = listOf(navArgument("dragonId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        // Extraemos el ID de la ruta
                        val id = backStackEntry.arguments?.getString("dragonId") ?: ""

                        DetailScreen(
                            viewModel = viewModel,
                            dragonId = id,
                            onBack = {
                                // Al volver, sacamos la pantalla actual de la pila
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}