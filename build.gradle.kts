plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.hiltAndroid) apply false
    alias(libs.plugins.googleKsp) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.kotlinParcelize) apply false

/*
    // To format alphabetically and auto update version of libs version.
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.8.3"
    // ./gradlew versionCatalogUpdate → Automatically updates dependency versions, formats them, and sorts them alphabetically.
    // ./gradlew versionCatalogUpdate --interactive → Lists updatable dependencies but does not automatically update them.
    // ./gradlew versionCatalogApplyUpdates → Approves the updates listed for dependencies.
    // ./gradlew versionCatalogFormat → Formats and alphabetically sorts the version catalog.
    // **Close with comment block after using the plugin**
*/
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            if (project.findProperty("composeCompilerReports") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
            if (project.findProperty("composeCompilerMetrics") == "true") {
                freeCompilerArgs += listOf(
                    "-P",
                    "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_compiler"
                )
            }
        }
    }
    /* run with ./gradlew assembleRelease -PcomposeCompilerReports=true */
}

true // Needed to make the Suppress annotation work for the plugins block