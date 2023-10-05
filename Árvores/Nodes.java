public class Nodes {
    Nodes esquerda;

    Nodes direita;

    private int info;

    public Nodes(int info){
        this.esquerda = null;
        this.direita = null;
        this.info = info;
    }

    public Nodes getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Nodes esquerda) {
        this.esquerda = esquerda;
    }

    public Nodes getDireita() {
        return direita;
    }

    public void setDireita(Nodes direita) {
        this.direita = direita;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }
}
