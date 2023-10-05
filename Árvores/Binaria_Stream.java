import java.util.Scanner;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Binaria_Stream {
    public static Nodes inserir(Nodes raiz, int numero) {
        if (raiz == null) {
            return new Nodes(numero);
        }
        if (numero >= raiz.getInfo()) {
            raiz.setDireita(inserir(raiz.getDireita(), numero));
        } else {
            raiz.setEsquerda(inserir(raiz.getEsquerda(), numero));
        }
        return raiz;
    }

    public static void imprimirIn(Nodes raiz){
        // Forma inordem
        if (raiz != null) {
            imprimirIn(raiz.getEsquerda());
            System.out.print(raiz.getInfo() + " ");
            imprimirIn(raiz.getDireita());
        }
    }

    public static void imprimirPre(Nodes raiz){
        // Forma préordem
        if (raiz != null) {
            System.out.print(raiz.getInfo() + " ");
            imprimirPre(raiz.getEsquerda());
            imprimirPre(raiz.getDireita());
        }
    }

    public static void imprimirPos(Nodes raiz){
        // Forma préordem
        if (raiz != null) {
            imprimirPos(raiz.getEsquerda());
            imprimirPos(raiz.getDireita());
            System.out.print(raiz.getInfo() + " ");
        }
    }


    public static Nodes encontrarMaior(Nodes raiz) {
        if (raiz == null) {
            return null;
        }

        while (raiz.getDireita() != null) {
            raiz = raiz.getDireita();
        }

        return raiz;
    }

    public static Nodes encontrarMenor(Nodes raiz) {
        if (raiz == null) {
            return null;
        }

        while (raiz.getEsquerda() != null) {
            raiz = raiz.getEsquerda();
        }

        return raiz;
    }

    public static int encontrarPosicao(Nodes raiz) {
        if (raiz == null) {
            return 0;
        } else {
            // Para saber onde estamos numa árvore podemos calcular a altura do lado esquerdo e direito
            // Se a esquerda for maior estamos no lado esquerdo e vice-versa.
            int alturaEsquerda = encontrarPosicao(raiz.getEsquerda());
            int alturaDireita = encontrarPosicao(raiz.getDireita());

            if (alturaEsquerda > alturaDireita) {
                return 1 + alturaEsquerda;
            } else {
                return 1 + alturaDireita;
            }
        }
    }

    public static Nodes removerElemento(Nodes raiz, int elemento){
        if(raiz != null){
            if(raiz.getInfo() == elemento){
                System.out.println("Encontramos o número!");

                // Verificamos se o node não possui filhos
                if(raiz.getEsquerda() == null && raiz.getDireita() == null){
                    return null;
                }

                // Aqui verificamos se ele tem um filho em alguma de suas raizes
                if (raiz.getEsquerda() == null) {
                    return raiz.getDireita();
                } else if (raiz.getDireita() == null) {
                    return raiz.getEsquerda();
                }

                // Calcule as alturas das subárvores esquerda e direita para ver qual lado da árvore estamos
                int alturaEsquerda = encontrarPosicao(raiz.getEsquerda());
                int alturaDireita = encontrarPosicao(raiz.getDireita());

                // Escolha o elemento substituto com base na altura das subárvores
                if (alturaEsquerda > alturaDireita) {
                    // Substituir pelo maior elemento da subárvore esquerda
                    Nodes maiorEsquerda = encontrarMaior(raiz.getEsquerda());
                    raiz.setInfo(maiorEsquerda.getInfo());
                    raiz.setEsquerda(removerElemento(raiz.getEsquerda(), maiorEsquerda.getInfo()));
                } else {
                    // Substituir pelo menor elemento da subárvore direita
                    Nodes menorDireita = encontrarMenor(raiz.getDireita());
                    raiz.setInfo(menorDireita.getInfo());
                    raiz.setDireita(removerElemento(raiz.getDireita(), menorDireita.getInfo()));
                }
            }
            else if (elemento < raiz.getInfo()) {
                // O elemento a ser removido está na subárvore esquerda já que e menor
                raiz.setEsquerda(removerElemento(raiz.getEsquerda(), elemento));
            } else {
                // O elemento a ser removido está na subárvore direita já que e maior
                raiz.setDireita(removerElemento(raiz.getDireita(), elemento));
            }
        }
        return raiz;
    }

    public static int[] gerarVetorAleatorioSemRepeticao(int tamanho) {
        Random random = new Random();
        int[] vetor = new int[tamanho];
        boolean[] numerosUtilizados = new boolean[tamanho];

        for (int i = 0; i < tamanho; i++) {
            int numeroAleatorio;
            do {
                numeroAleatorio = random.nextInt(tamanho); // Ajuste o limite superior para o tamanho
            } while (numerosUtilizados[numeroAleatorio]);

            vetor[i] = numeroAleatorio;
            numerosUtilizados[numeroAleatorio] = true;
        }

        return vetor;
    }

    public static boolean encontrarVetor(int numero, int quantidade, int[] vetor){
        for(int i = 0; i < quantidade; i++){
            if(numero == vetor[i]){
                return true;
            }
        }

        return false;
    }


    public static void adicionarNosEGrafos(Nodes raiz, Graph graph) {
        if (raiz != null) {
            Node node = graph.addNode(Integer.toString(raiz.getInfo()));
            node.setAttribute("ui.label", Integer.toString(raiz.getInfo())); // Adicione o rótulo com o número
            adicionarNosEGrafos(raiz.getEsquerda(), graph);
            adicionarNosEGrafos(raiz.getDireita(), graph);
            if (raiz.getEsquerda() != null) {
                graph.addEdge(Integer.toString(raiz.getInfo()) + "-" + Integer.toString(raiz.getEsquerda().getInfo()),
                        Integer.toString(raiz.getInfo()), Integer.toString(raiz.getEsquerda().getInfo()));
            }
            if (raiz.getDireita() != null) {
                graph.addEdge(Integer.toString(raiz.getInfo()) + "-" + Integer.toString(raiz.getDireita().getInfo()),
                        Integer.toString(raiz.getInfo()), Integer.toString(raiz.getDireita().getInfo()));
            }
        }
    }

    public static boolean busca(Nodes raiz, int elemento){
        if(raiz != null){
            if(raiz.getInfo() == elemento){
                // Encontramos o elemento que estavamos buscando
                System.out.println("Elemento encontrado!");
                return true;
            }

            busca(raiz.getEsquerda(), elemento);
            busca(raiz.getDireita(), elemento);
        }

        return false;
    }

    public static void main(String[] args) {
        // O teclado
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.setProperty("org.graphstream.ui", "swing");

        Arvore arvore = new Arvore();

        System.out.println("Digite quantos elementos deseja ter na arvore: ");
        int quantidade = teclado.nextInt();

        // Colocamos todos os números aleatorios num vetor para não ter números repetidos
        int[] vetorAleatorio = gerarVetorAleatorioSemRepeticao(quantidade);

        // Registra o tempo de início
        long tempoCriacao = System.currentTimeMillis();

        // Coloca todos os números na árvore
        for(int numero : vetorAleatorio){
            arvore.setRaiz(inserir(arvore.getRaiz(), numero));
        }

        // Registra o tempo de término
        long tempoCriacaoFim = System.currentTimeMillis();
        // Calcula o tempo de execução em milissegundos
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore criada em " + tempoCriacaoFinal + " milissegundos.");

        // Mostramos a árvore
        imprimirPre(arvore.getRaiz());

        // Para adicionar um elemento na árvore
        int adicionarx = 0;
        // Verificamos se esse número está na árvore, se não estiver continua o programa
        // Mas se ele estiver ele pede pra colocar um novo
        do {
            System.out.println("\nDigite um número para adicionar na árvore: ");
            adicionarx = teclado.nextInt();
        } while (encontrarVetor(adicionarx, quantidade, vetorAleatorio));

        // Antes da inserção
        long tempoInsercao = System.currentTimeMillis();

        arvore.setRaiz(inserir(arvore.getRaiz(), adicionarx));

        // Após a inserção
        long tempoInsercaoFim = System.currentTimeMillis();
        // Tempo de execução da inserção em milissegundos
        long tempoInsercaoFinal = tempoInsercaoFim - tempoInsercao;

        System.out.println("Tempo de inserção: " + tempoInsercaoFinal + " milissegundos");

        // Para remover um elemento na árvore
        int removerx = 0;
        // Se o elemento igualar o elemento inserido ou se ele estiver na árvore o programa continua
        // Senão pede para inserir um número que está na árvore
        do{
            System.out.println("\nDigite um número para remover na árvore: ");
            removerx = teclado.nextInt();
            if(removerx == adicionarx){
                break;
            }
        }while (!encontrarVetor(removerx, quantidade, vetorAleatorio));

        // Antes da remoção
        long tempoRemocao = System.currentTimeMillis();

        arvore.setRaiz(removerElemento(arvore.getRaiz(), removerx));

        // Após a remoção
        long tempoRemocaoFim = System.currentTimeMillis();
        // Tempo de execução da remoção em milissegundos
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocao;

        System.out.println("Tempo de remoção: " + tempoRemocaoFinal + " milissegundos");

        // Aqui pedimos para o usuario o número do node que deseja buscar
        System.out.println("\nDigite um número para buscar na árvore: ");
        int buscarx = teclado.nextInt();

        // Antes da busca
        long tempoBusca = System.currentTimeMillis();

        busca(arvore.getRaiz(), buscarx);

        // Após a busca
        long tempoBuscaFim = System.currentTimeMillis();
        // Tempo de execução da busca em milissegundos
        long tempoBuscaFinal = tempoBuscaFim - tempoBusca;

        // Uma simples checkagem para ver se o elemento foi encontrado ou não
        // Fazemos a busca de novo só para garantir que não havera impacto na performance
        if(!busca(arvore.getRaiz(),buscarx)){
            System.out.println("O elemento não foi encontrado");
        }

        System.out.println("Tempo de busca: " + tempoBuscaFinal + " milissegundos.");

        // Como podemos colocar muitos elementos na árvore, isso pode fazer o GraphStream não aguentar a quantidade de nodes
        // Por isso só vamos mostrar ele se o número for menor do que 1000
        if(quantidade <= 1000){
            // A parte visual do código
            Graph graph = new SingleGraph("Árvore Binaria");

            // Adicione os nós e arestas ao grafo
            adicionarNosEGrafos(arvore.getRaiz(), graph);

            // Configure o layout
            graph.setAttribute("ui.layout", "org.graphstream.algorithm.layout.TreeLayout");

            // Defina o estilo dos nós
            graph.setAttribute("ui.stylesheet", "node { size: 30px; fill-color: blue; text-mode: normal; }");

            // Exiba o rótulo dos nós
            graph.setAttribute("ui.nodeLabelsVisible", true);

            // Exiba o grafo
            graph.display();
        }
    }
}
