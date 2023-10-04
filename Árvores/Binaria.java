import java.util.Scanner;
import java.util.Random;

public class Binaria {
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

        // Para encontrar o maior node seguimos para a direita
        while (raiz.getDireita() != null) {
            raiz = raiz.getDireita();
        }

        return raiz;
    }

    public static Nodes encontrarMenor(Nodes raiz) {
        if (raiz == null) {
            return null;
        }

        // Para encontrar o menor node seguimos para a esquerda
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

        Arvore arvore = new Arvore();

        System.out.println("Digite quantos elementos deseja ter na arvore: ");
        int quantidade = teclado.nextInt();

        // Registra o tempo de início
        long tempoCriacao = System.currentTimeMillis();

        // Coloca todos os números na árvore
        for(int i = 0; i < quantidade; i++){
            int numero = random.nextInt(quantidade);
            arvore.setRaiz(inserir(arvore.getRaiz(), numero));
        }

        // Registra o tempo de término
        long tempoCriacaoFim = System.currentTimeMillis();
        // Calcula o tempo de execução em milissegundos
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore criada em " + tempoCriacaoFinal + " milissegundos.");
        
        // Mostramos a árvore na pre ordem
        imprimirPre(arvore.getRaiz());

        // Para adicionar um elemento na árvore
        System.out.println("\nDigite um número para adicionar na árvore: ");
        int adicionarx = teclado.nextInt();

        // Antes da inserção
        long tempoInsercao = System.currentTimeMillis();

        arvore.setRaiz(inserir(arvore.getRaiz(), adicionarx));

        // Após a inserção
        long tempoInsercaoFim = System.currentTimeMillis();
        // Tempo de execução da inserção em milissegundos
        long tempoInsercaoFinal = tempoInsercaoFim - tempoInsercao;

        System.out.println("Tempo de inserção: " + tempoInsercaoFinal + " milissegundos.");

        // Para remover um elemento na árvore
        System.out.println("\nDigite um número para remover na árvore: ");
        int removerx = teclado.nextInt();

        // Antes da remoção
        long tempoRemocao = System.currentTimeMillis();

        arvore.setRaiz(removerElemento(arvore.getRaiz(), removerx));

        // Após a remoção
        long tempoRemocaoFim = System.currentTimeMillis();
        // Tempo de execução da remoção em milissegundos
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocao;

        System.out.println("Tempo de remoção: " + tempoRemocaoFinal + " milissegundos.");

        // Mostramos a árvore depois da remoção e inserção de um elemento
        imprimirPre(arvore.getRaiz());

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
    }
}
