package boot.linksOrganizer.enums;

import java.util.Arrays;
import java.util.List;

public enum AnnotationSlug {
  TRABALHO("trabalho", "Trabalho"),
  ESTUDOS("escola", "estudo", "Escola", "Estudo"),
  TECNOLOGIA("tec", "Tec", "Tecnologia", "tecnologia"),
  RECEITAS("receita", "Receitas", "receitas", "Receita"),
  RENDA_EXTRA("renda extra", "Renda extra"),
  RECEITA_SAUDAVEL("Receita Saudável", "Receita saudável", "saudavel", "Saudável", "Saudavel"),;

  private final List<String> slugs;

  AnnotationSlug(String... slugs) {
    this.slugs = List.of(slugs);
  }

  public List<String> getSlugs() {
    return slugs;
  }

  public static AnnotationSlug fromSlug(String slug) {
    return Arrays.stream(values())
            .filter(e -> e.slugs.contains(slug))
            .findFirst()
            .orElse(null);
  }
}
