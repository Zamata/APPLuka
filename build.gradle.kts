plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // Agregar el plugin de Google Services
    id("com.google.gms.google-services") version "4.4.2" apply false

}