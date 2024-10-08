pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "crypto_currency_info_app"
include(":app")
include(":common_ui")
include(":navigation")
include(":core_base")
include(":core_crypto_currency_data")
include(":core_crypto_currency_domain")
include(":feature_base")
include(":feature_crypto_currency_list")
include(":feature_crypto_currency_details")
include(":common_utils")
