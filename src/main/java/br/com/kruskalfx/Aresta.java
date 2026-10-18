package br.com.kruskalfx;

// Classe simples para representar uma aresta
public class Aresta {
    public String origem;
    public String destino;
    public int peso;

    public Aresta(String origem, String destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }
}