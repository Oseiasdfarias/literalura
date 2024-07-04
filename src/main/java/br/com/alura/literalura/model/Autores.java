package br.com.alura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAutor;
    private Integer anoNascimento;
    private Integer anoMorte;

    @ManyToOne
    private Livro livro;

    public Autores () {}

    public Autores( DadosAutores dadosAutores, Livro livro){
        String[] author = dadosAutores.nameAutor().split(",");
        if (author.length > 1) {
            this.nameAutor = author[1] + " " + author[0];
        } else {
            this.nameAutor = author[0];
        }
        this.anoNascimento = dadosAutores.anoNascimento();
        this.anoMorte = dadosAutores.anoMorte();
        this.livro = livro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameAutor() {
        return nameAutor;
    }

    public void setNameAutor(String nameAutor) {
        this.nameAutor = nameAutor;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoMorte() {
        return anoMorte;
    }

    public void setAnoMorte(Integer anoMorte) {
        this.anoMorte = anoMorte;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    @Override
    public String toString() {
        return "++++++++++++   +++++++++++++++\n" +
                "Autor: " + nameAutor +
                "\nAno de Nascimento: " + anoNascimento +
                "\nAno da Morte: " + anoMorte +
                "\n++++++++++++   +++++++++++++++\n";
    }
}
