package src;

class No {
    int chave;
    int dados;
    No esquerda, direita, pai;
    int altura;
    int cor; // 0 = preto, 1 = vermelho

    public No(int chave, int dados) {
        this.chave = chave;
        this.dados = dados;
        this.esquerda = this.direita = this.pai = null;
        this.altura = 1;
        this.cor = 1; // Novo nó sempre vermelho
    }
}
