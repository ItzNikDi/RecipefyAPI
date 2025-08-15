package nd.nikdi

import kotlinx.serialization.Serializable

@Serializable
data class NameRequest(
    val name: String = "",
    val servings: Int = 1,
    val portion_size: Float = 200.0f,
    val cuisine: String = ""
)

@Serializable
data class IngredientsRequest(
    val ingredients: List<String> = emptyList(),
    val servings: Int = 1,
    val portion_size: Float = 200.0f,
    val cuisine: String = ""
)

@Serializable
data class RecipeResponse(val markdown: String)