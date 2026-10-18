package br.com.kruskalfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class KruskalFX extends KruskalFXApplication {

    // Tratador de evento clássico, sem lambdas
    public class AcaoBotaoKruskal implements EventHandler<ActionEvent> {
        private TextArea saida;

        public AcaoBotaoKruskal(TextArea saida) {
            this.saida = saida;
        }

        @Override
        public void handle(ActionEvent evento) {
            executarKruskal(saida);
        }
    }

    @Override
    public void start(Stage palcoPrincipal) {
        VBox layout = new VBox();
        layout.getStyleClass().add("kruskal-root");
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setSpacing(10);

        Button botaoExecutar = new Button("Executar Kruskal");
        botaoExecutar.getStyleClass().add("primary-button");

        Label labelSaida = new Label("Teste de Mesa:");
        labelSaida.getStyleClass().add("section-label");
        TextArea areaSaida = new TextArea();
        areaSaida.getStyleClass().add("result-area");
        areaSaida.setEditable(false);
        areaSaida.setPrefHeight(450);

        AcaoBotaoKruskal acao = new AcaoBotaoKruskal(areaSaida);
        botaoExecutar.setOnAction(acao);

        layout.getChildren().add(botaoExecutar);
        layout.getChildren().add(labelSaida);
        layout.getChildren().add(areaSaida);

        Scene cena = new Scene(layout, 650, 550);
        cena.getStylesheets().add(
                KruskalFX.class.getResource("styles.css").toExternalForm()
        );
        palcoPrincipal.setTitle("Trabalho de Teoria dos Gráfos (TG I) - Algoritmo de Kruskal");
        palcoPrincipal.setScene(cena);
        palcoPrincipal.show();
    }

    public int buscarIndice(String[] array, String alvo) {
        int indiceEncontrado = -1;
        int i = 0;

        while (i < array.length && indiceEncontrado == -1) {
            if (array[i].equals(alvo)) {
                indiceEncontrado = i;
            }
            i = i + 1;
        }

        return indiceEncontrado;
    }

    public void ordenarArestasManualmente(ArrayList<Aresta> lista) {
        int tamanho = lista.size();
        int i = 0;
        boolean trocou = true;

        while (i < tamanho - 1 && trocou) {
            trocou = false;
            int j = 0;

            while (j < tamanho - 1 - i) {
                Aresta atual = lista.get(j);
                Aresta proxima = lista.get(j + 1);

                if (atual.peso > proxima.peso) {
                    lista.set(j, proxima);
                    lista.set(j + 1, atual);
                    trocou = true;
                }

                j = j + 1;
            }
            i = i + 1;
        }
    }

    public void executarKruskal(TextArea areaSaida) {
        String[] vertices = null;
        ArrayList<Aresta> listaArestas = new ArrayList<>();
        int indiceVerticeOrigem = 0;

        // 1. LEITURA DIRETA DO ARQUIVO ma.txt
        try {
            FileReader leitorArquivo = new FileReader("ma3.txt");
            BufferedReader leitor = new BufferedReader(leitorArquivo);

            String conteudoLinha = leitor.readLine();

            while (conteudoLinha != null) {
                conteudoLinha = conteudoLinha.trim();

                if (!conteudoLinha.isEmpty()) {
                    if (!conteudoLinha.startsWith("[")) {

                        if (vertices == null) {
                            vertices = conteudoLinha.split("\\s+");
                        } else {
                            String[] pesosStr = conteudoLinha.split("\\s+");
                            int colAtual = 0;

                            while (colAtual < pesosStr.length) {
                                int peso = Integer.parseInt(pesosStr[colAtual]);

                                if (colAtual > indiceVerticeOrigem) {
                                    if (peso > 0) {
                                        listaArestas.add(new Aresta(vertices[indiceVerticeOrigem], vertices[colAtual], peso));
                                    }
                                }
                                colAtual = colAtual + 1;
                            }
                            indiceVerticeOrigem = indiceVerticeOrigem + 1;
                        }
                    }
                }
                // Lê a próxima linha do arquivo
                conteudoLinha = leitor.readLine();
            }

            leitor.close();

        } catch (Exception erro) {
            areaSaida.setText("Erro ao tentar ler o arquivo 'ma.txt'.\nCertifique-se de que ele está na pasta correta do projeto.\nDetalhe do erro: " + erro.getMessage());
            return; // Retorno estritamente necessário para interromper a execução caso o arquivo não exista
        }

        int v = 0;
        String textoSaida = "VÉRTICES NA ORDEM DO ARQUIVO: ";

        while (v < vertices.length) {
            textoSaida = textoSaida + vertices[v];
            if (v < vertices.length - 1) {
                textoSaida = textoSaida + ", ";
            }
            v = v + 1;
        }

        textoSaida = textoSaida + "\n\nArestas em ordem original:\n";
        int i = 0;

        while (i < listaArestas.size()) {
            Aresta aresta = listaArestas.get(i);
            textoSaida = textoSaida + aresta.origem + " - " + aresta.destino
                    + " (peso " + aresta.peso + ")\n";
            i = i + 1;
        }

        // Ordenação manual por peso.
        ordenarArestasManualmente(listaArestas);

        textoSaida = textoSaida + "\nArestas em ordem crescente (por peso):\n";
        i = 0;

        while (i < listaArestas.size()) {
            Aresta aresta = listaArestas.get(i);
            textoSaida = textoSaida + aresta.origem + " - " + aresta.destino
                    + " (peso " + aresta.peso + ")\n";
            i = i + 1;
        }

        textoSaida = textoSaida + "\nTeste de mesa:\n";

        // Preparação do teste de mesa.
        String[] representantes = new String[vertices.length];
        v = 0;
        String cabecalho = "ARESTA | ";
        String linhaInicio = "(Início) | ";

        while (v < vertices.length) {
            representantes[v] = vertices[v];
            cabecalho = cabecalho + vertices[v] + " | ";
            linhaInicio = linhaInicio + representantes[v] + " | ";
            v = v + 1;
        }

        textoSaida = textoSaida + cabecalho + "\n" + linhaInicio + "\n";
        int custoTotal = 0;
        i = 0;

        // 4. ALGORITMO KRUSKAL
        while (i < listaArestas.size()) {
            Aresta arestaAtual = listaArestas.get(i);

            int indOrigem = buscarIndice(vertices, arestaAtual.origem);
            int indDestino = buscarIndice(vertices, arestaAtual.destino);

            String repOrigem = representantes[indOrigem];
            String repDestino = representantes[indDestino];

            textoSaida = textoSaida + "(" + arestaAtual.origem + "," + arestaAtual.destino + ") [" + arestaAtual.peso + "] | ";

            if (!repOrigem.equals(repDestino)) {
                custoTotal = custoTotal + arestaAtual.peso;

                int j = 0;
                while (j < representantes.length) {
                    if (representantes[j].equals(repDestino)) {
                        representantes[j] = repOrigem;
                    }
                    j = j + 1;
                }

                int k = 0;
                while (k < representantes.length) {
                    textoSaida = textoSaida + representantes[k] + " | ";
                    k = k + 1;
                }
                textoSaida = textoSaida + "\n";

            } else {
                textoSaida = textoSaida + "Houve ciclo (Então descarta)\n";
            }

            i = i + 1;
        }

        textoSaida = textoSaida + "\nCusto final da AGM: " + custoTotal;

        areaSaida.setText(textoSaida);
    }

    public static void main(String[] args) {
        launch(args);
    }
}