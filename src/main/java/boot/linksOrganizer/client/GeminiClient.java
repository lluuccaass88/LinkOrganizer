package boot.linksOrganizer.client;

import boot.linksOrganizer.dto.MessageDTO;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GeminiClient {

  private final RestClient restClient;

  private final static String PROMPT_SUMMARIZE_NEWS = """
          Abaixo estão mensagens de um canal de notícias do Discord.
          Para cada mensagem, retorne um JSON array com objetos contendo "title", "summary" e "link".
          Responda APENAS com o JSON puro, sem markdown, sem backticks, sem texto adicional.
          O summary pode ser o suficiente para entender o ponto principal da noticia, o tamanho é relativamente grande
          
          Mensagens:
          %s
          """;

  private final static String PROMPT_SUMMARIZE_REELS = """
          Abaixo estão mensagens de um canal de reels do Discord.
          Para cada mensagem, retorne um JSON array com objetos contendo "title", "description", "tag" e "link".
          Eu quero que você me retorne todas as informações que você conseguir a respeito deste reels
          
          Mensagens:
          %s
          """;

  @Value("${gemini.token}")
  private String token;

  public GeminiClient() {
    this.restClient = RestClient.builder()
            .baseUrl("https://generativelanguage.googleapis.com")
            .build();
  }

  public String getNewsSummarize(List<MessageDTO> messages) {
    String messagesText = messages.stream()
            .map(MessageDTO::getContent)
            .collect(Collectors.joining("\n---\n"));

    String prompt = PROMPT_SUMMARIZE_NEWS.formatted(messagesText);

    Map<String, Object> body = Map.of(
            "contents", List.of(
                    Map.of("parts", List.of(
                            Map.of("text", prompt)
                    ))
            )
    );

    return getGeminiResponse(body);
  }

  private String getGeminiResponse(Map<String, Object> body) {
    String response = restClient.post()
            .uri("/v1beta/models/gemini-2.5-flash:generateContent?key={key}", token).header("Content-Type", "application/json")
            .body(body)
            .retrieve()
            .body(String.class);
    return response;
  }


}