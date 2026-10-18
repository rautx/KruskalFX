# KruskalFX

Aplicacao desktop em JavaFX para, futuramente, visualizar o algoritmo de Kruskal.

## Requisitos

- JDK 21 ou superior
- Maven 3.9 ou superior

## Executar

Na raiz do projeto, execute:

```sh
mvn clean javafx:run
```

O projeto usa JavaFX 21 e Maven para baixar as dependencias e iniciar a aplicacao.
Ele nao usa `module-info.java`: a classe principal e executada pelo classpath.
No IntelliJ IDEA, configure `br.com.kruskalfx.KruskalFXLauncher` como classe principal
da configuracao **Application**. Tambem e possivel executar pelo Maven com `mvn javafx:run`.

## Estrutura

```text
src/
  main/
    java/
      br/com/kruskalfx/
        Aresta.java
        KruskalFX.java
        KruskalFXApplication.java
        KruskalFXLauncher.java
    resources/
      br/com/kruskalfx/
        styles.css
```
