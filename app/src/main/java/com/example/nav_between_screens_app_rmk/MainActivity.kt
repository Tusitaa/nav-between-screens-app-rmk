package com.example.nav_between_screens_app_rmk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nav_between_screens_app_rmk.screens.LoginScreen
import com.example.nav_between_screens_app_rmk.screens.MenuScreen
import com.example.nav_between_screens_app_rmk.screens.PedidosScreen
import com.example.nav_between_screens_app_rmk.screens.PerfilScreen
import com.example.nav_between_screens_app_rmk.ui.theme.NavbetweenscreensapprmkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavbetweenscreensapprmkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login",
                    ) {
                        composable(route = "login") {
                            LoginScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(modifier = Modifier.padding(innerPadding), navController)
                        }
                        composable(
                            route = "pedidos?cliente={cliente}",
                            arguments = listOf(navArgument("cliente") {
                                defaultValue = "Cliente Genérico"
                            })
                        ) {
                            PedidosScreen(modifier = Modifier.padding(innerPadding), navController, it.arguments?.getString("cliente"))
                        }
                        composable(route = "perfil/{nome}") {
                            val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
                            PerfilScreen(
                                modifier = Modifier.padding(innerPadding),
                                navController,
                                nome!!
                            )
                        }
                    }
                }
            }
        }
    }
}

