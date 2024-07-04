package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutores(@JsonAlias("name") String nameAutor,
                           @JsonAlias("birth_year") Integer anoNascimento,
                           @JsonAlias("death_year") Integer anoMorte) {
}
