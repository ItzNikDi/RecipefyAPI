package nd.nikdi

import com.openai.client.okhttp.OpenAIOkHttpClient
import com.openai.models.ChatModel
import com.openai.models.chat.completions.ChatCompletionCreateParams
import kotlin.jvm.optionals.getOrNull

private val apiKey = System.getenv("OPENAI_THINGY") ?: error("OpenAI API not set.")
private val client = OpenAIOkHttpClient.builder()
    .apiKey(apiKey = apiKey).build()

fun recipeFromName(fromNameRequest: NameRequest): String {
    val userPrompt = "Върни само рецепта за ${fromNameRequest.name} за ${fromNameRequest.servings} порции, всяка с тегло ${fromNameRequest.portion_size} грама. Придържай се към ${fromNameRequest.cuisine} кухня"
    val devPrompt = "Ти предлагаш рецепти. Използвай емоджита и markdown форматиране, за да изглежда по-добре текста на рецептата и съставките"
    return callOpenAI(userPrompt, devPrompt)
}

fun recipeFromIngredients(fromIngredientsRequest: IngredientsRequest): String {
    val userPrompt = "Върни само рецепта за ${fromIngredientsRequest.servings} порции, където всяка тежи ${fromIngredientsRequest.portion_size} грама с тези съставки ${fromIngredientsRequest.ingredients.joinToString(", ")}. Можеш да допълниш съставките и се придържай към ${fromIngredientsRequest.cuisine} кухня"
    val devPrompt = "Ти предлагаш рецепти. Позволено ти е да допълваш рецептата там, където не достига нещо. Използвай емоджита и markdown форматиране, за да изглежда по-добре текста на рецептата и съставките"
    return callOpenAI(userPrompt, devPrompt)
}

private fun callOpenAI(userPrompt: String, devPrompt: String): String {
    val params = ChatCompletionCreateParams.builder()
        .addDeveloperMessage(devPrompt)
        .addUserMessage(userPrompt)
        .model(ChatModel.GPT_4O_MINI_2024_07_18)
        .temperature(0.8)
        .build()

    val completion = client.chat().completions().create(params)

    tokenLogger.info("Input tokens: ${completion.usage().get().promptTokens()}, Output tokens: ${completion.usage().get().completionTokens()}")

    return completion.choices()[0].message().content().getOrNull() ?: "Моля, опитайте по-късно."
}