import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties by lazy {
    Properties().apply {
        val file = rootProject.file("local.properties")
        if (file.exists()) {
            file.inputStream().use { load(it) }
        }
    }
}

fun getConfigProperty(key: String): String? =
    providers.gradleProperty(key).orNull ?: localProperties.getProperty(key)

val stripeDefaultCustomerId = getConfigProperty("STRIPE_DEFAULT_CUSTOMER_ID")
    ?: error("Define STRIPE_DEFAULT_CUSTOMER_ID via gradle.properties or local.properties")
val stripeSecretKey = getConfigProperty("STRIPE_SECRET_KEY")
    ?: error("Define STRIPE_SECRET_KEY via gradle.properties or local.properties")

android {
    namespace = "com.eliascoelho911.paymentsdk"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        buildConfigField(
            "String",
            "STRIPE_DEFAULT_CUSTOMER_ID",
            "\"$stripeDefaultCustomerId\""
        )
        buildConfigField(
            "String",
            "STRIPE_SECRET_KEY",
            "\"$stripeSecretKey\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
}
