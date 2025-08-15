package nd.nikdi

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        post("/name") {
            try {
                val req = call.receive<NameRequest>()
                val userIp = call.request.headers["X-Real-IP"] ?: "didn't parse"

                recipeLogger.info("Received data: $req from $userIp")

                val recipe = recipeFromName(req)
                call.respond(HttpStatusCode.OK, RecipeResponse(recipe))
            } catch (e: Exception) {
                recipeLogger.error("Error: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, RecipeResponse("Изпитваме затруднения, моля опитайте по-късно!"))
            }
        }

        post("/ingredients") {
            try {
                val req = call.receive<IngredientsRequest>()
                val userIp = call.request.headers["X-Real-IP"] ?: "didn't parse"

                recipeLogger.info("Received data: $req from $userIp")

                val recipe = recipeFromIngredients(req)
                call.respond(HttpStatusCode.OK, RecipeResponse(recipe))
            } catch (e: Exception) {
                recipeLogger.error("Error: ${e.message}", e)
                call.respond(HttpStatusCode.InternalServerError, RecipeResponse("Изпитваме затруднения, моля опитайте по-късно!"))
            }
        }
    }
}
