package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(@JsonAlias("title") String titulo,
                         @JsonAlias("authors") List<DadosAutores> autores,
                         @JsonAlias("download_count") Integer numeroDownload,
                         @JsonAlias("languages") String[] languages,
                         @JsonAlias("formats") Map<String, String> formats) {
}
