package ru.nihongo.study.service.ai.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.client.OpenAiApi;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class AiCaller {
    public String chatModel;
    public OpenAiService openAi;

    public AiCaller(String apiKey, String baseUrl, String chatModel) {
        this.chatModel = chatModel;
        this.openAi = new OpenAiService(apiKey);

        customizeBaseUrl(baseUrl, apiKey);
    }

    private void customizeBaseUrl(String baseUrl, String apiKey) {
        try {
            Field apiField = OpenAiService.class.getDeclaredField("api");
            apiField.setAccessible(true);

            ObjectMapper mapper = OpenAiService.defaultObjectMapper();

            // Создаем логирующий интерцептор
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(log::debug);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Создаем интерцептор для добавления API ключа в заголовок Authorization
            Interceptor apiKeyInterceptor = new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request requestWithApiKey = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + apiKey)
                        .build();
                    return chain.proceed(requestWithApiKey);
                }
            };

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Duration.ofSeconds(30))
                .readTimeout(Duration.ofSeconds(30))
                .build();

            Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

            OpenAiApi customApi = retrofit.create(OpenAiApi.class);
            apiField.set(this.openAi, customApi);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось настроить базовый URL", e);
        }
    }

    public String sendOpenAiMessage(String systemInstruction, String request) {
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemInstruction);
        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), request);
        List<ChatMessage> messages = new ArrayList<>(List.of(systemMessage, userMessage));

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
            .builder()
            .model(chatModel)
            .messages(messages)
            .build();

        ChatCompletionResult result = openAi.createChatCompletion(chatCompletionRequest);
        return result.getChoices().get(0).getMessage().getContent();
    }
}