package nd.nikdi

import io.ktor.server.application.*
import io.ktor.server.netty.*
import java.io.File

fun main(args: Array<String>) {
    File("logs").mkdirs()
    EngineMain.main(args)
}

fun Application.module() {
    configureLogging()
    configureSerialization()
    configureRouting()
}
