import java.util.Scanner;
import java.util.Random;

public class AVL {
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
        // Para calcular isso chamamos o método calcularAltura que calcula a altura do lado esquerdo e direito
        // Mostrando como árvore está(-1,0,1)
        int estado = calcularAltura(raiz);

        // O estado diz como que o balanceamento da árvore está(-1,0,1)
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
        if(raiz != null) {
            Nodes novaRaiz = raiz.getEsquerda();
            Nodes temp = novaRaiz.getDireita();
            novaRaiz.setDireita(raiz);
            raiz.setEsquerda(temp);
            return novaRaiz;
        }
        return raiz;
    }

    public static Nodes rotacaoEsquerda(Nodes raiz){
        if(raiz != null) {
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

    public static Nodes removerElemento(Nodes raiz, int elemento){
        /*O procedimento de balanceamento deve ser
        executado cada vez que um elemento é inserido
        ou removido na árvore.*/
        if(raiz != null){
            if(raiz.getInfo() == elemento){
                System.out.println("Encontramos o número!");
                // Encontramos o número certo

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

                // Agora realizamos o balanceamento da árvore
                int estado = calcularAltura(raiz);

                if(estado != 0){
                    System.out.println("O estado atual da arvore é: " + estado);
                }

                // Para ver como o balanceamento está agora como um elemento removido
                int balanceamentoEsquerdo = calcularAltura(raiz.getEsquerda());
                int balanceamentoDireito = calcularAltura(raiz.getDireita());

                if(estado < -1){
                    System.out.println("A subárvore da direita está maior do que a da esquerda!");
                    if(balanceamentoDireito <= 0){
                        return rotacaoEsquerda(raiz);
                    } else{
                        raiz.setDireita(rotacaoDireita(raiz.getDireita()));
                        return rotacaoEsquerda(raiz);
                    }
                } else if (estado > 1) {
                    System.out.println("A subárvore da esquerda está maior do que a da direita!");
                    if(balanceamentoEsquerdo >= 0){
                        return rotacaoDireita(raiz);
                    } else{
                        raiz.setEsquerda(rotacaoEsquerda(raiz.getEsquerda()));
                        return rotacaoDireita(raiz);
                    }
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

    public static void main(String[] args) {
        // O teclado
        Scanner teclado = new Scanner(System.in);
        Random random = new Random();

        Arvore arvore = new Arvore();

        System.out.println("Digite quantos elementos deseja ter na arvore: ");
        int quantidade = teclado.nextInt();

        long tempoCriacao = System.currentTimeMillis();

        for(int i = 0; i < quantidade; i++){
            int numero = random.nextInt(quantidade); // Gere números aleatórios no intervalo de 0 a 99 (você pode ajustar o intervalo conforme necessário)
            arvore.setRaiz(inserir(arvore.getRaiz(), numero));
        }

        // Registra o tempo de término
        long tempoCriacaoFim = System.currentTimeMillis();
        // Calcula o tempo de execução em milissegundos
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore criada em " + tempoCriacaoFinal + " milissegundos.");

        imprimirPre(arvore.getRaiz());

        // Mostramos o quão balenceado a árvore está
        System.out.println("\n" + calcularAltura(arvore.getRaiz()));

        System.out.println("\nDigite um número para adicionar na árvore: ");
        int adicionarx = teclado.nextInt();

        // Antes da inserção
        long tempoInsercao = System.currentTimeMillis();

        arvore.setRaiz(inserir(arvore.getRaiz(), adicionarx));

        // Após a inserção
        long tempoInsercaoFim = System.currentTimeMillis();
        // Tempo de execução da inserção em milissegundos
        long tempoInsercaoFinal = tempoInsercaoFim - tempoInsercao;

        System.out.println("Tempo de inserção: " + tempoInsercaoFinal + " milissegundos");

        System.out.println("\nDigite um número para remover na árvore: ");
        int removerx = teclado.nextInt();

        // Antes da remoção
        long tempoRemocao = System.currentTimeMillis();

        arvore.setRaiz(removerElemento(arvore.getRaiz(), removerx));

        // Após a remoção
        long tempoRemocaoFim = System.currentTimeMillis();
        // Tempo de execução da remoção em milissegundos
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocao;

        System.out.println("Tempo de remoção: " + tempoRemocaoFinal + " milissegundos");

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
