package br.com.alura.literalura.repositorio;

import br.com.alura.literalura.model.Autores;
import br.com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositorioLivros extends JpaRepository<Livro, Long> {

        @Query("SELECT a FROM Livro l JOIN l.autores a ORDER BY nameAutor")
        List<Autores> findAllAutores();

        @Query("SELECT a FROM Livro l JOIN l.autores a WHERE :ano BETWEEN a.anoNascimento AND a.anoMorte")
        List<Autores> findAutoresVivosDeterminadoAno(Integer ano);

        @Query("SELECT l FROM Livro l JOIN l.autores a WHERE l.languages = %:idioma%")
        List<Livro> findLivroPorIdiona(String[] idioma);

        @Query("SELECT l FROM Livro l order by l.downloadCount DESC LIMIT :numero")
        List <Livro> findTopXXLivros(Integer numero);

        @Query("SELECT a FROM Livro l JOIN l.autores a WHERE a.nameAutor ilike %:nome%")
        List<Autores> findAutoresPorNome(String nome);
}
