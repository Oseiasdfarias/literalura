package br.com.alura.literalura.principal;

import br.com.alura.literalura.model.Autores;
import br.com.alura.literalura.model.DadosLivro;
import br.com.alura.literalura.model.Livro;
import br.com.alura.literalura.model.ResultadosDados;
import br.com.alura.literalura.repositorio.RepositorioLivros;
import br.com.alura.literalura.service.ConsumoApi;
import br.com.alura.literalura.service.ConverteDados;

import java.util.*;
import java.util.stream.Stream;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private final RepositorioLivros repositorio;

    public Principal(RepositorioLivros repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    \n###############    ###############
                    
                    1 - Buscar Livro
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Top 5 Livros mais baixados
                    7 - Top 10 Livros mais baixados
                    8 - Buscar autor por nome
                    
                    0 - Sair
                    ###############    ###############
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroWeb();
                    break;
                case 2:
                    listarLivroRigistrados();
                    break;
                case 3:
                    listarAutoresRigistrados();
                    break;
                case 4:
                    listarAutoresVivosDeterminadoAno();
                    break;
                case 5:
                    listarLivroPorIdioma();
                    break;
                case 6:
                    topXXLivrosMaisBaixados(5);
                    break;
                case 7:
                    topXXLivrosMaisBaixados(10);
                    break;
                case 8:
                    listarAutorPorNome();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarLivroWeb() {
        Livro livro = getDadosLivro();
        if (livro != null) {
            System.out.println("Livro: " +
                    livro.getTitle() +
                    " Registrado no banco de dados");
        }else {
            System.out.println("Livro não encontrado!");
        }
    }

    private Livro getDadosLivro() {
        System.out.println("Digite o nome do livro para busca");
        var nomeLivro = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO +
                nomeLivro.toLowerCase().replace(" ", "%20"));
        System.out.println(json);
        ResultadosDados dados = conversor.obterDados(json, ResultadosDados.class);
        List<DadosLivro> livros = dados.results().stream().toList();
        for (DadosLivro dadosLivro : livros) {
            if (dadosLivro.titulo().toLowerCase()
                    .contains(nomeLivro.toLowerCase())) {
                Livro livro = new Livro(dadosLivro);
                repositorio.save(livro);
                return livro;
            }
        }
        return null;
    }

    private void listarLivroRigistrados() {
        List<Livro> livros = repositorio.findAll();
        System.out.println("###############    ###############" +
                "\nlista de Livros Rigistrados\n");
        livros.stream()
                .sorted(Comparator.comparing(Livro::getDownloadCount))
                .forEach(livro -> System.out.println(
                        "*************  ************* \n" +
                        "Titulo: " + livro.getTitle() +
                        "\nAutor: " + livro.getPrimeiroAutor() +
                        "\n*************  ************* \n"));
    }

    private void listarAutoresRigistrados() {
        List<Autores> listaAutores = repositorio.findAllAutores();
        listaAutores.forEach(System.out::println);
    }

    private void listarAutoresVivosDeterminadoAno() {
        System.out.println("Digite o ano para verificar os autores vivos nessa data: ");
        var ano = leitura.nextInt();
        leitura.nextLine();
        List<Autores> autoresVivos = repositorio.findAutoresVivosDeterminadoAno(ano);
        System.out.println("###############    ###############" +
                "\nAutores Vivos no ano de " + ano + "\n");
        autoresVivos.forEach(System.out::println);
    }

    private void listarLivroPorIdioma() {
        System.out.println(
                "\n###############    ###############\n" +
                "Escolha uma das opções: \n" +
                "es - Espanhol\n" +
                "en - Inglês\n" +
                "fr - Francês\n" +
                "pt - Português\n");

        var idionaget = leitura.nextLine();
        String[] idioma = {idionaget};
        List<Livro> livros = repositorio.findLivroPorIdiona(idioma);
        livros.stream().forEach(livro -> System.out.println(
                "*************  ************* \n" +
                "Titulo: " + livro.getTitle() +
                "\nAutor: " + livro.getPrimeiroAutor() +
                "\nIdioma: " + livro.getLanguages()[0] +
                "\n*************  ************* \n"));
    }

    private void topXXLivrosMaisBaixados(Integer numero) {
        List<Livro> livros = repositorio.findTopXXLivros(numero);
        System.out.println("###############    ###############" +
                "\nTOP " + numero + " LIVROS MAIS BAIXADOS\n");
        livros.stream().forEach(livro -> System.out.println(
                "*************  ************* \n" +
                "Titulo: " + livro.getTitle() +
                "\nAutor: " + livro.getPrimeiroAutor() +
                "\nIdioma: " + livro.getLanguages()[0] +
                "\nNúmero Downloads: " + livro.getDownloadCount() +
                "\n*************  ************* \n"));
    }

    private void listarAutorPorNome() {
        System.out.println(
        "\n###############    ###############\n" +
        "Digite o nome do autor: \n");
        var nomeAutor = leitura.nextLine();
        List<Autores> listaAutores = repositorio.findAutoresPorNome(nomeAutor);
        listaAutores.forEach(System.out::println);
    }
}
