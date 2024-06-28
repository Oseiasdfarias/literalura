package br.com.alura.literalura.principal;

import br.com.alura.literalura.service.ConsumoApi;

import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private final String ENDERECO = "https://gutendex.com/books/2/";

    public void print() {
        var json = consumo.obterDados(ENDERECO);
        System.out.println(json);
    }
}
