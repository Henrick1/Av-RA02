import java.util.Scanner;
import java.util.Random;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class AVL_Stream {
    public static Nodes inserir(Nodes raiz, int numero) {
        /*O procedimento de balanceamento deve ser
        executado cada vez que um elemento é inserido
        ou removido na árvore.*/
        if (raiz == null) {
            return new Nodes(numero);
        }
        if (numero >= raiz.getInfo()) {
            raiz.setDireita(inserir(raiz.getDireita(), numero));
        } else {
            raiz.setEsquerda(inserir(raiz.getEsquerda(), numero));
        }

        // Agora realizamos o balanceamento da árvore
        int estado = calcularAltura(raiz);

        if(estado != 0){
            System.out.println("O estado atual da arvore é: " + estado);
        }

        if(estado < -1){
            System.out.println("A subárvore da direita está maior do que a da esquerda!");
            if (numero > raiz.getDireita().getInfo()) {
                // Rotação à esquerda
                return rotacaoEsquerda(raiz);
            } else {
                // Rotação dupla à esquerda
                raiz.setDireita(rotacaoDireita(raiz.getDireita()));
                return rotacaoEsquerda(raiz);
            }
        } else if (estado > 1) {
            System.out.println("A subárvore da esquerda está maior do que a da direita!");
            if (numero < raiz.getEsquerda().getInfo()) {
                // Rotação á direita
                return rotacaoDireita(raiz);
            } else {
                // Rotação dupla à direita
                raiz.setEsquerda(rotacaoEsquerda(raiz.getEsquerda()));
                return rotacaoDireita(raiz);
            }
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

    public static int calcularAltura(Nodes raiz){
        if(raiz == null){
            return 0;
        }
        int alturaEsquerda = encontrarPosicao(raiz.getEsquerda());
        int alturaDireita = encontrarPosicao(raiz.getDireita());

        return alturaEsquerda - alturaDireita;
    }

    public static Nodes rotacaoDireita(Nodes raiz){
        if(raiz != null && raiz.getEsquerda() != null) {
            Nodes novaRaiz = raiz.getEsquerda();
            Nodes temp = novaRaiz.getDireita();
            novaRaiz.setDireita(raiz);
            raiz.setEsquerda(temp);
            return novaRaiz;
        }
        return raiz;
    }

    public static Nodes rotacaoEsquerda(Nodes raiz){
        if(raiz != null && raiz.getDireita() != null) {
            Nodes novaRaiz = raiz.getDireita();
            Nodes temp = novaRaiz.getEsquerda();
            novaRaiz.setEsquerda(raiz);
            raiz.setDireita(temp);
            return novaRaiz;
        }
        return raiz;
    }

    public static int encontrarPosicao(Nodes raiz) {
        if (raiz == null) {
            return 0;
        } else {
            int alturaEsquerda = encontrarPosicao(raiz.getEsquerda());
            int alturaDireita = encontrarPosicao(raiz.getDireita());

            if (alturaEsquerda > alturaDireita) {
                return 1 + alturaEsquerda;
            } else {
                return 1 + alturaDireita;
            }
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

    public static Nodes removerElemento(Nodes raiz, int elemento) {
        if (raiz == null) {
            return raiz;
        }

        if (elemento < raiz.getInfo()) {
            raiz.setEsquerda(removerElemento(raiz.getEsquerda(), elemento));
        } else if (elemento > raiz.getInfo()) {
            raiz.setDireita(removerElemento(raiz.getDireita(), elemento));
        } else {
            // Encontrou o elemento a ser removido

            // Verificar se o nó possui um ou nenhum filho
            if (raiz.getEsquerda() == null || raiz.getDireita() == null) {
                Nodes temp = null;
                if (temp == raiz.getEsquerda()) {
                    temp = raiz.getDireita();
                } else {
                    temp = raiz.getEsquerda();
                }

                // Se não tem filhos, apenas retorna o filho (pode ser nulo)
                if (temp == null) {
                    raiz = null;
                } else {
                    // Caso com um filho, copia o conteúdo do filho
                    raiz = temp;
                }
            } else {
                // Caso com dois filhos, encontrar o sucessor em ordem
                Nodes temp = encontrarMenor(raiz.getDireita());

                // Copiar o conteúdo do sucessor em ordem para este nó
                raiz.setInfo(temp.getInfo());

                // Remover o sucessor em ordem
                raiz.setDireita(removerElemento(raiz.getDireita(), temp.getInfo()));
            }
        }

        // Se a árvore tinha apenas um nó, retorne
        if (raiz == null) {
            return raiz;
        }

        // Verificar o fator de equilíbrio da raiz
        int estado = calcularAltura(raiz);

        // Realizar as rotações necessárias com base no fator de equilíbrio
        if (estado > 1) {
            if (raiz.getInfo() > raiz.getEsquerda().getInfo()) {
                // Rotação à direita simples
                return rotacaoDireita(raiz);
            } else {
                // Rotação à esquerda seguida por rotação à direita
                raiz.setEsquerda(rotacaoEsquerda(raiz.getEsquerda()));
                return rotacaoDireita(raiz);
            }
        }

        if (estado < -1) {
            if (raiz.getInfo() < raiz.getDireita().getInfo()) {
                // Rotação à esquerda simples
                return rotacaoEsquerda(raiz);
            } else {
                // Rotação à direita seguida por rotação à esquerda
                raiz.setDireita(rotacaoDireita(raiz.getDireita()));
                return rotacaoEsquerda(raiz);
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

    public static boolean encontrarVetor(int numero, int quantidade, int[] vetor) {
        for (int i = 0; i < quantidade; i++) {
            if (numero == vetor[i]) {
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

    public static void main(String[] args) {
        // O teclado
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        System.setProperty("org.graphstream.ui", "swing");

        // Criando um vetor de inteiros com os elementos fornecidos
        //int[] vetor = {10, 5, 15, 3, 7, 12, 18, 2, 4, 6, 8, 11, 13, 17, 19, 1, 9, 14, 16, 20};
        //int[] vetor = {50,40,70,60,80};
        //int[] vetor = {50,40,97};
        //int[] vetor = {50,40,70,35,45};
        int[] vetor = {20,4,26,3,9,2,7,11,21,30};
        //int[] vetor = {2,1,4,3,5};

        Arvore arvore = new Arvore();

        System.out.println("Digite quantos elementos deseja ter na arvore: ");
        int quantidade = teclado.nextInt();

        // Colocamos todos os números aleatorios num vetor para não ter números repetidos
        int[] vetorAleatorio = gerarVetorAleatorioSemRepeticao(quantidade);

        // Registra o tempo de início
        long tempoCriacao = System.currentTimeMillis();

        for(int numero : vetor){
            arvore.setRaiz(inserir(arvore.getRaiz(), numero));
        }

        // Registra o tempo de término
        long tempoCriacaoFim = System.currentTimeMillis();
        // Calcula o tempo de execução em milissegundos
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore criada em " + tempoCriacaoFinal + " milissegundos.");

        // Imprimimos a árvore na pre ordem para mostrar como ficou
        imprimirPre(arvore.getRaiz());

        // Mostramos o quão balenceado a árvore está
        System.out.println("O balanceamento atual ficou: \n" + calcularAltura(arvore.getRaiz()));


        // Para adicionar um elemento na árvore
        int adicionarx = 0;
        // Verificamos se esse número está na árvore, se não estiver continua o programa
        // Mas se ele estiver ele pede para colocar um novo
        do {
            System.out.println("\nDigite um número para adicionar na árvore: ");
            adicionarx = teclado.nextInt();
        } while (encontrarVetor(adicionarx, quantidade, vetor));

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
        }while (!encontrarVetor(removerx, quantidade, vetor));

        // Antes da remoção
        long tempoRemocao = System.currentTimeMillis();
        arvore.setRaiz(removerElemento(arvore.getRaiz(), removerx));

        // Após a remoção
        long tempoRemocaoFim = System.currentTimeMillis();
        // Tempo de execução da remoção em milissegundos
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocao;

        System.out.println("Tempo de remoção: " + tempoRemocaoFinal + " milissegundos");

        // Mostramos o quão balenceado a árvore está
        System.out.println(calcularAltura(arvore.getRaiz()));

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

        imprimirPre(arvore.getRaiz());

    }
}
