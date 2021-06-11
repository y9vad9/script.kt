import `fun`.kotlingang.deploy.Deploy
import `fun`.kotlingang.deploy.DeployEntity
import `fun`.kotlingang.deploy.DeployProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.util.GUtil.loadProperties
import java.io.File

fun Project.applyPublication() {
    val deployPropertiesFile: File = rootProject.file("deploy.properties")

    if (deployPropertiesFile.exists()) {
        val properties = loadProperties(deployPropertiesFile)

        project.apply<Deploy>()
        project.configure<DeployProperties> {
            username = properties.getProperty("user")
            host = properties.getProperty("host")
            password = properties.getProperty("password")
            deployPath = properties.getProperty("destination")
        }

        project.configure<DeployEntity> {
            artifactId = project.name
            group = Library.GROUP
            version = Library.VERSION
            name = "KScript"
            description = "KScript running engine"
        }
    }
}