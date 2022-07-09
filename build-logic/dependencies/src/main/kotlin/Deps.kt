@file:Suppress("FunctionName", "unused")

object Deps {
    const val compileSdkVersion = 30
    const val minSdkVersion = 21

    private const val kotlinVersion = "1.7.0"
    private const val coroutinesVersion = "1.5.2"
    private const val serializationVersion = "1.3.0"
    private const val nodejsExternalsVersion = "0.0.7"
    private const val ktorVersion = "1.6.3"
    private const val ktorOpenapiVersion = "0.2-beta.20"
    private const val exposedVersion = "0.35.1"
    private const val datetimeVersion = "0.3.0"

    private const val postgresqlVersion = "42.2.24"
    private const val slf4jJVersion = "1.7.32"
    private const val logbackVersion = "1.2.6"
    private const val sshVersion = "2.10.1"

    private const val materialVersion = "1.5.0-alpha03"
    private const val recyclerViewVersion = "1.3.0-alpha01"
    private const val swipeRefreshLayoutVersion = "1.2.0"
    private const val constraintLayoutVersion = "2.1.0"
    private const val lifecycleVersion = "2.3.1"
    private const val glideVersion = "4.12.0"
    private const val androidAppCompatVersion = "1.3.1"
    private const val androidComposeVersion = "1.1.0-alpha04"
    private const val androidGradlePluginVersion = "4.2.2"

    private const val kvisionVersion = "5.1.1"
    private const val shadowVer = "7.0.0"
    private const val kspVersion = "1.6.0-1.0.1"
    private const val implierVer = "1.0.2"

    object Libs {
        object Implier {
            const val Processor = "com.y9vad9.implier:ksp:$implierVer"
            const val Annotations = "com.y9vad9.implier:implier:$implierVer"
        }

        object Kotlin {
            object Test {
                const val JUnit = "org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion"
            }

            object Script {
                const val Common = "org.jetbrains.kotlin:kotlin-scripting-common:$kotlinVersion"
                const val Jvm = "org.jetbrains.kotlin:kotlin-scripting-jvm:$kotlinVersion"
                const val Js = "org.jetbrains.kotlin:kotlin-scripting-js:$kotlinVersion"
                const val JvmHost = "org.jetbrains.kotlin:kotlin-scripting-jvm-host:$kotlinVersion"
                const val CompilerEmbeddable =
                    "org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable:$kotlinVersion"
                const val Compiler = "org.jetbrains.kotlin:kotlin-compiler:$kotlinVersion"
                const val Dependencies = "org.jetbrains.kotlin:kotlin-scripting-dependencies:$kotlinVersion"
                const val DependenciesMaven = "org.jetbrains.kotlin:kotlin-scripting-dependencies-maven:$kotlinVersion"
            }

            object Compiler {
                const val Embeddable = "org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion"
            }
        }

        object KVision {
            const val Core = "io.kvision:kvision:$kvisionVersion"

            object Bootstrap {
                const val Core = "io.kvision:kvision-bootstrap:$kvisionVersion"
                const val Css = "io.kvision:kvision-bootstrap-css:$kvisionVersion"
                const val DateTime = "io.kvision:kvision-bootstrap-datetime:$kvisionVersion"
                const val Select = "io.kvision:kvision-bootstrap-select:$kvisionVersion"
                const val Spinner = "io.kvision:kvision-bootstrap-spinner:$kvisionVersion"
                const val Upload = "io.kvision:kvision-bootstrap-upload:$kvisionVersion"
                const val Dialog = "io.kvision:kvision-bootstrap-dialog:$kvisionVersion"
                const val Typeahead = "io.kvision:kvision-bootstrap-typeahead:$kvisionVersion"
            }

            const val FontAwesome = "io.kvision:kvision-fontawesome:$kvisionVersion"
            const val i18n = "io.kvision:kvision-i18n:$kvisionVersion"
            const val RichText = "io.kvision:kvision-richtext:$kvisionVersion"
            const val Handlebars = "io.kvision:kvision-handlebars:$kvisionVersion"
            const val DataContainer = "io.kvision:kvision-datacontainer:$kvisionVersion"
            const val Chart = "io.kvision:kvision-chart:$kvisionVersion"
            const val Tabulator = "io.kvision:kvision-tabulator:$kvisionVersion"
            const val Pace = "io.kvision:kvision-pace:$kvisionVersion"
            const val Toast = "io.kvision:kvision-toast:$kvisionVersion"
            const val React = "io.kvision:kvision-react:$kvisionVersion"
            const val Navigo = "io.kvision:kvision-routing-navigo:$kvisionVersion"
            const val State = "io.kvision:kvision-state:$kvisionVersion"
            const val Rest = "io.kvision:kvision-rest:$kvisionVersion"
        }

        object Kotlinx {
            const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            const val Serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion"
            const val Datetime = "org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion"
            const val Nodejs = "org.jetbrains.kotlinx:kotlinx-nodejs:$nodejsExternalsVersion"
        }

        object Ktor {
            object Client {
                const val Core = "io.ktor:ktor-client-core:$ktorVersion"
                const val Cio = "io.ktor:ktor-client-cio:$ktorVersion"
                const val Serialization = "io.ktor:ktor-client-serialization:$ktorVersion"
            }

            object Server {
                const val Core = "io.ktor:ktor-server-core:$ktorVersion"
                const val Cio = "io.ktor:ktor-server-cio:$ktorVersion"
                const val Serialization = "io.ktor:ktor-serialization:$ktorVersion"
                const val Openapi = "com.github.papsign:Ktor-OpenAPI-Generator:$ktorOpenapiVersion"
            }
        }

        object Exposed {
            const val Core = "org.jetbrains.exposed:exposed-core:$exposedVersion"
            const val Jdbc = "org.jetbrains.exposed:exposed-jdbc:$exposedVersion"
            const val Time = "org.jetbrains.exposed:exposed-java-time:$exposedVersion"
        }

        object Postgres {
            const val Jdbc = "org.postgresql:postgresql:$postgresqlVersion"
        }

        object Logback {
            const val Classic = "ch.qos.logback:logback-classic:$logbackVersion"
        }

        object Slf4j {
            const val Simple = "org.slf4j:slf4j-simple:$slf4jJVersion"
        }

        object Androidx {
            const val AppCompat =
                "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val Material =
                "com.google.android.material:material:$materialVersion"
            const val RecyclerView =
                "androidx.recyclerview:recyclerview:$recyclerViewVersion"
            const val SwipeRefreshLayout =
                "androidx.swiperefreshlayout:swiperefreshlayout:$swipeRefreshLayoutVersion"
            const val ConstraintLayout =
                "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
            const val Lifecycle =
                "androidx.lifecycle:lifecycle-extensions:$lifecycleVersion"
            const val LifecycleKtx =
                "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"

            object Compose {
                const val UI = "androidx.compose.ui:ui:$androidComposeVersion"
                const val UITooling = "androidx.compose.ui:ui-tooling:$androidComposeVersion"
                const val Foundation = "androidx.compose.foundation:foundation:$androidComposeVersion"
                const val Material = "androidx.compose.material:material:$androidComposeVersion"
                const val Icons = "androidx.compose.material:material-icons-core:$androidComposeVersion"
                const val ExtendedIcons = "androidx.compose.material:material-icons-extended:$androidComposeVersion"
                const val JUnitTests = "androidx.compose.ui:ui-test-junit4:$androidComposeVersion"
                const val Activity = "androidx.activity:activity-compose:1.4.0-alpha02"
            }
        }

        object Bumtech {
            const val Glide =
                "com.github.bumptech.glide:glide:$glideVersion"
        }
    }

    object Kapt {
        object Bumtech {
            const val Glide = "com.github.bumptech.glide:compiler:$glideVersion"
        }
    }

    object Plugins {
        object Deploy {
            const val Id = "deploy"
        }

        object KSP {
            const val Id = "com.google.devtools.ksp"
            const val Classpath = "com.google.devtools.ksp:symbol-processing-gradle-plugin:$kspVersion"
        }

        object Configuration {
            object Kotlin {
                const val Mpp = "k-mpp"
                const val Jvm = "k-jvm"
                const val Js = "k-js"

                object Android {
                    const val App = "k-android-app"
                    const val Library = "k-android-library"
                }
            }
        }

        object Dependencies {
            const val Id = "dependencies"
            const val Classpath = "dependencies:dependencies:SNAPSHOT"
        }

        object Kotlin {
            const val Multiplatform = "org.jetbrains.kotlin.multiplatform"
            const val Jvm = "org.jetbrains.kotlin.jvm"
            const val Js = "org.jetbrains.kotlin.js"
            const val Android = "org.jetbrains.kotlin.android"
            const val Classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        }

        object Android {
            const val Application = "com.android.application"
            const val Library = "com.android.library"
            const val Classpath = "com.android.tools.build:gradle:$androidGradlePluginVersion"
        }

        object Serialization {
            const val Id = "org.jetbrains.kotlin.plugin.serialization"
            const val Classpath = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
        }

        object Ssh {
            const val Id = "org.hidetake.ssh"
            const val Classpath = "org.hidetake:gradle-ssh-plugin:$sshVersion"
        }

        object Publish {
            const val Id = "publish"
            const val Classpath = "publish:publish:SNAPSHOT"
        }

        object MavenPublish {
            const val Id = "maven-publish"
        }

        object Application {
            const val Id = "application"
        }

        object Shadow {
            const val Classpath = "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:$shadowVer"
            const val Id = "com.github.johnrengelman.shadow"
        }
    }
}
