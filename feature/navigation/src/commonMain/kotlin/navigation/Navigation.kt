package navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import product_catalog.route.CartScreen
import product_catalog.route.ProductDetailsScreen
import product_catalog.route.ProductScreen

enum class Route {
    ProductList, Cart

}

@Composable
fun MainNavGraph() {
    AppTheme{
        _MainNavGraph()
    }

}

@Composable
fun _MainNavGraph() {
    val navController = rememberNavController()
    var selected by remember { mutableStateOf(Destination.Products) }
    NavigationLayoutDecorator(
        selected = selected,
        onDestinationSelected = { destination ->
            selected = destination
            when (destination) {
                Destination.Products -> {
                    navigateAsTopDestination(navController, Route.ProductList)
                }

                Destination.Cart -> {
                    navigateAsTopDestination(navController, Route.Cart)
                }

                else -> {}
            }
        }
    ) {

        _NavGraph(navController)
    }


}

private fun navigateAsTopDestination(navController: NavHostController, destination: Route) {
    navController.currentBackStack.value.forEach {
        navController.popBackStack()
    }
    navController.navigate(destination.name)
}

@Composable
private fun _NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.ProductList.name
    ) {
        composable(route = Route.ProductList.name) {
            TopBarDecorator(
                title = "Product List",
            ) {
                ProductScreen(
                    onClick = { id ->
                        navController.navigate("ProductDetails/$id")
                    }
                )
            }

        }
        composable(
            route = "ProductDetails/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")

            if (id != null) {
                TopBarDecorator(
                    title = "Product List",
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                ) {

                    ProductDetailsScreen(id = id)
                }

            }

        }
        composable(route = Route.Cart.name) {
            TopBarDecorator(
                title = "Cart Items"
            ){
                CartScreen()
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarDecorator(
    title: String,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = navigationIcon,
                title = {
                    Text(title)
                }
            )
        }
    ) {
        Box(Modifier.padding(it)) {
            content()
        }

    }

}