package com.example.luckyandroidapp.ui.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.luckyandroidapp.MainActivity
import com.example.luckyandroidapp.R
import com.example.luckyandroidapp.ui.theme.fadedOrange
import com.example.luckyandroidapp.ui.theme.primaryColor
import com.example.luckyandroidapp.ui.theme.redOrange
import com.example.luckyandroidapp.ui.theme.white
import com.example.luckyandroidapp.viewmodels.LoginViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen() {
    val context = LocalContext.current
    var isSigningIn by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val viewModel = viewModel<LoginViewModel>()

    val oneTapClient = remember { Identity.getSignInClient(context) }
    val authClient = remember { GoogleAuthUiClient(context, oneTapClient) }
    var userName by remember { mutableStateOf(authClient.getUser()) }

//    val signInLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult()
//    ) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val credential = Identity.getSignInClient(context).getSignInCredentialFromIntent(result.data)
//            val idToken = credential.googleIdToken
//            scope.launch {
//                val success = authClient.signInWithGoogle(idToken)
//                if (success) {
//                    userName = authClient.getUser()
//                } else {
//                    Log.e("GoogleSignIn", "Lỗi đăng nhập")
//                }
//            }
//        }
//    }

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        Log.d("GoogleSignIn", "Đăng nhập thành công: ${user?.email}")
                    } else {
                        Log.e("GoogleSignIn", "Lỗi xác thực Firebase: ${task.exception?.message}")
                    }
                }
        } catch (e: ApiException) {
            Log.e("GoogleSignIn", "Lỗi đăng nhập: ${e.message}")
        }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(primaryColor, redOrange, fadedOrange)
                    )
                ),
        ) {
            Text(
                text = "Sign in",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = 60.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 150.dp)
                .background(white, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .align(Alignment.BottomEnd)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Welcome Back!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "To keep connected with us please login with your personal info",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var rememberMe by remember { mutableStateOf(false) }

                // Email Input
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Password Input
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Remember Me & Forgot Password
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it }
                        )
                        Text(text = "Remember me")
                    }

                    Text(
                        text = "Forgot Password?",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sign In Button with Gradient
                Button(
                    onClick = {
//                        val auth: FirebaseAuth = FirebaseAuth.getInstance()
//                        auth.fetchSignInMethodsForEmail(email)
//                            .addOnCompleteListener { task ->
//                                if (task.isSuccessful) {
//                                    val result = task.result
//                                    if (result?.signInMethods?.isEmpty() == true) {
//                                        authClient.createUserWithEmailPassword(email, password)
//                                    } else {
//                                        authClient.signInWithEmailPassword(email, password)
//                                    }
//                                } else {
//                                    Toast.makeText(context, "Sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                                }
//                            }
                        if (email.isNotBlank() && password.isNotBlank()) {
                            authClient.signInWithEmailPassword(email, password)
                            email = ""
                            password = ""
                        } else {
                            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(redOrange, fadedOrange)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sign In",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                    Text(
                        text = "  OR CONTINUE WITH  ",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                    Divider(modifier = Modifier.weight(1f), color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(20.dp))

                SocialButton(
                    text = "Sign In with Facebook",
                    icon = painterResource(id = R.drawable.icon_google),
                    color = Color(
                        0xFFCACBCC
                    ),
                    onClick = {
                        scope.launch {
                            authClient.googleSignInClient.signInIntent.let { signInLauncher.launch(it) }
                        }
                    }
                )
                //            Spacer(modifier = Modifier.height(10.dp))
                //            SocialButton(text = "Sign In with Google", icon = painterResource(id = android.R.drawable.ic_menu_gallery), color = Color(0xFFDB4437))
            }
        }
    }
}

@Composable
fun SocialButton(text: String, icon: Painter, color: Color, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, color),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, color = Color.Black, fontSize = 14.sp)
        }
    }
}


class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val googleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("AIzaSyCUyG3iYtc-DnHjWBBiVhbE8-6m661kljo")  // Thay bằng Web Client ID từ Firebase
            .requestEmail()
            .build()
    )


    suspend fun signIn(): IntentSenderRequest? {
        return try {
            val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId("AIzaSyCUyG3iYtc-DnHjWBBiVhbE8-6m661kljo") // Thay thế bằng Web Client ID của bạn
                        .setFilterByAuthorizedAccounts(false)
                        .build()
                )
                .setAutoSelectEnabled(true)
                .build()

            val result = oneTapClient.beginSignIn(signInRequest).await()
            IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
        } catch (e: Exception) {
            Log.e("GoogleAuthUiClient", "Lỗi đăng nhập", e)
            null
        }
    }

    suspend fun signInWithGoogle(idToken: String?): Boolean {
        return try {
            val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
            true
        } catch (e: Exception) {
            Log.e("GoogleAuthUiClient", "Lỗi xác thực Firebase", e)
            false
        }
    }

    fun createUserWithEmailPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signInWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Sign in success", Toast.LENGTH_SHORT).show()
                    context.startActivity(Intent(context, MainActivity::class.java))
                } else {
                    Toast.makeText(context, "Sign in failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun signOut() {
        auth.signOut()
        oneTapClient.signOut()
    }

    fun getUser(): String? {
        return auth.currentUser?.displayName
    }
}
