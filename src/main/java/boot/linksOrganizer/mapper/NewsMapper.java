package boot.linksOrganizer.mapper;

import boot.linksOrganizer.dto.NewsSumaryDTO;
import lombok.experimental.UtilityClass;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@UtilityClass
public class NewsMapper {
  public List<NewsSumaryDTO> newsSumaryDTO(String response) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response);
      String content = root
              .path("candidates").get(0)
              .path("content")
              .path("parts").get(0)
              .path("text")
              .asText();

      return mapper.readValue(content, new TypeReference<List<NewsSumaryDTO>>() {});
    } catch (Exception e) {
      throw new RuntimeException("Erro ao parsear resposta do Gemini", e);
    }
  }
}
