package com.example.softcare.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.softcare.ui.theme.Purple
import com.google.firebase.auth.FirebaseAuth
import com.softcare.R


@Composable
fun SignInScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Bem-vindo ao SoftCare",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                loading = true
                auth.signInAnonymously().addOnCompleteListener { task ->
                    loading = false
                    if (task.isSuccessful) {
                        navController.navigate("checkin_intro") {
                            popUpTo("signin") { inclusive = true }
                        }
                    } else {
                        println("Erro ao fazer login anônimo: ${task.exception?.localizedMessage}")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = if (loading) "Entrando..." else "Entrar")
        }
    }
}