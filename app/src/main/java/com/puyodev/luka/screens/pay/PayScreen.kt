package com.puyodev.luka.screens.pay

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.puyodev.luka.R
import com.puyodev.luka.common.composable.ActionToolbar
import com.puyodev.luka.common.ext.toolbarActions
import com.puyodev.luka.model.User
import com.puyodev.luka.screens.drawer.DrawerHeader
import com.puyodev.luka.screens.drawer.DrawerScreen
import kotlinx.coroutines.launch
import java.util.*
import kotlin.reflect.KFunction3

fun openGoogleMapsWithCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context
) {
    try {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                val geoUri = "geo:$latitude,$longitude?q=$latitude,$longitude"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri))
                intent.setPackage("com.google.android.apps.maps")
                context.startActivity(intent)
            } else {
                Log.e("Location", "Ubicación no disponible")
            }
        }.addOnFailureListener { exception ->
            Log.e("Location", "Error obteniendo la ubicación: ${exception.message}")
        }
    } catch (e: SecurityException) {
        Log.e("Location", "No se tienen los permisos necesarios para obtener la ubicación")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PayScreen(
    openScreen: (String) -> Unit,
    viewModel: PayViewModel = hiltViewModel(),
) {
    val user by viewModel.user.collectAsStateWithLifecycle(initialValue = User())
    val nfcStatus by viewModel.nfcStatus.collectAsState()
    val locationText = remember { mutableStateOf("Ubicación no disponible") }

    PayScreenContent(
        user = user,
        onProfileClick = viewModel::onProfileClick,
        onTicketClick = viewModel::onTicketClick,
        onProfilePaymentGatewayClick = viewModel::onProfilePaymentGatewayClick,
        openScreen = openScreen,
        nfcStatus = nfcStatus,
        locationText = locationText
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun PayScreenContent(
    user: User,
    onProfileClick: ((String) -> Unit) -> Unit,
    onProfilePaymentGatewayClick: ((String) -> Unit) -> Unit,
    onTicketClick: KFunction3<(String) -> Unit, Int, String, Unit>,
    openScreen: (String) -> Unit,
    nfcStatus: PayViewModel.NFCStatus,
    locationText: MutableState<String>
) {
    var valor by remember { mutableIntStateOf(1) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openGoogleMapsWithCurrentLocation(fusedLocationClient, context)
        }
    }

    fun checkLocationPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                DrawerHeader(user = user.username)
                Spacer(modifier = Modifier.height(16.dp))
                DrawerScreen(openScreen = openScreen)
            }
        },
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                ActionToolbar(
                    title = user.username,
                    modifier = Modifier.toolbarActions(),
                    endAction = { onProfileClick(openScreen) },
                    onMenuClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = { CustomBottomBar(locationText = locationText) },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    OutlinedButton(
                        modifier = Modifier.width(160.dp),
                        onClick = { onProfilePaymentGatewayClick(openScreen) }
                    ) {
                        Column {
                            Text(text = "Lukitas: ")
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lukita_coin),
                                    contentDescription = "Lukitas Icono",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Unspecified
                                )
                                Text(text = "${user.lukitas}")
                            }
                        }
                    }
                    OutlinedButton(
                        modifier = Modifier.width(160.dp),
                        onClick = { /*TODO*/ }
                    ) {
                        Column {
                            Text(text = "Tarifa: ")
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.lukita_coin),
                                    contentDescription = "Lukitas Icono",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Unspecified
                                )
                                Text(text = "1")
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (valor > 1) valor--
                        }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Minus")
                        }
                        Text(text = "$valor", fontSize = 100.sp)
                        IconButton(onClick = {
                            if (valor < 10) valor++
                        }) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Plus")
                        }
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 60.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(valor) {
                        Image(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.Gray)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    when (nfcStatus) {
                        is PayViewModel.NFCStatus.Idle -> {
                            ExtendedFloatingActionButton(
                                modifier = Modifier
                                    .width(200.dp)
                                    .padding(0.dp, 40.dp),
                                onClick = {
                                    onTicketClick(
                                        openScreen,
                                        valor,
                                        "Urb. Monterrey D-8, José Luis Bustamante y Rivero"
                                    )
                                },
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                                icon = {
                                    Icon(
                                        Icons.Default.KeyboardArrowUp,
                                        contentDescription = "Realizar pago"
                                    )
                                },
                                text = { Text(text = "Pagar", fontSize = 20.sp) }
                            )
                        }
                        is PayViewModel.NFCStatus.WaitingForNFC -> {
                            NFCWaitingIndicator()
                        }
                        is PayViewModel.NFCStatus.Success -> {
                            // No mostrar nada, la navegación ocurrirá automáticamente
                        }
                        is PayViewModel.NFCStatus.Error -> {
                            ErrorMessage(message = nfcStatus.message)
                        }
                    }
                }

                Button(
                    onClick = { openGoogleMapsWithCurrentLocation(fusedLocationClient, context) },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Saber mi ubicación actual")
                }
            }
        }
    }
}

@Composable
fun NFCWaitingIndicator() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Acerque su teléfono al lector...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ErrorMessage(message: String) {
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun CustomBottomBar(locationText: MutableState<String>) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val geocoder = Geocoder(context, Locale.getDefault())

    // Función para obtener la ubicación actual
    fun updateLocation() {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        val address = addresses[0]
                        locationText.value = address.getAddressLine(0)
                    } else {
                        locationText.value = "Ubicación no disponible"
                    }
                } else {
                    locationText.value = "Ubicación no disponible"
                }
            }.addOnFailureListener { exception ->
                locationText.value = "Error obteniendo la ubicación: ${exception.message}"
            }
        } catch (e: SecurityException) {
            locationText.value = "No se tienen los permisos necesarios para obtener la ubicación"
        }
    }

    BottomAppBar(
        modifier = Modifier
            .shadow(elevation = 10.dp)
            .height(150.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = "Ubicación Actual:", fontSize = 15.sp)
                Text(
                    text = locationText.value,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.45f)
                    .padding(start = 8.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = { updateLocation() }) {
                        Image(
                            painter = painterResource(id = R.drawable.located),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }
            }
        }
    }
}