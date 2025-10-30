package src;

class ArvoreAVL implements Arvore {
    private No raiz;
    private int contadorComparacoes;
    
    public ArvoreAVL() {
        this.raiz = null;
        this.contadorComparacoes = 0;
    }
    
    @Override
    public void inserir(int chave, int dados) {
        raiz = inserirRecursivo(raiz, chave, dados);
    }
    
    private No inserirRecursivo(No no, int chave, int dados) {
        contadorComparacoes++;
        if (no == null) {
            return new No(chave, dados);
        }
        
        contadorComparacoes++;
        if (chave < no.chave) {
            no.esquerda = inserirRecursivo(no.esquerda, chave, dados);
        } else if (chave > no.chave) {
            no.direita = inserirRecursivo(no.direita, chave, dados);
        } else {
            no.dados = dados;
            return no;
        }
        
        // Atualiza altura
        no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        
        // Verifica balanceamento
        int balanceamento = getBalanceamento(no);
        
        // Casos de rotação
        if (balanceamento > 1 && chave < no.esquerda.chave) {
            return rotacaoDireita(no);
        }
        if (balanceamento < -1 && chave > no.direita.chave) {
            return rotacaoEsquerda(no);
        }
        if (balanceamento > 1 && chave > no.esquerda.chave) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }
        if (balanceamento < -1 && chave < no.direita.chave) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }
        
        return no;
    }
    
    private No rotacaoDireita(No y) {
        No x = y.esquerda;
        No T2 = x.direita;
        
        x.direita = y;
        y.esquerda = T2;
        
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        
        return x;
    }
    
    private No rotacaoEsquerda(No x) {
        No y = x.direita;
        No T2 = y.esquerda;
        
        y.esquerda = x;
        x.direita = T2;
        
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        
        return y;
    }
    
    private int altura(No no) {
        if (no == null) return 0;
        return no.altura;
    }
    
    private int getBalanceamento(No no) {
        if (no == null) return 0;
        return altura(no.esquerda) - altura(no.direita);
    }
    
    // Implementar outros métodos similares à BST
    @Override
    public Integer buscar(int chave) {
        return buscarRecursivo(raiz, chave);
    }
    
    private Integer buscarRecursivo(No no, int chave) {
        contadorComparacoes++;
        if (no == null) {
            return null;
        }
        
        contadorComparacoes++;
        if (chave == no.chave) {
            return no.dados;
        } else if (chave < no.chave) {
            return buscarRecursivo(no.esquerda, chave);
        } else {
            return buscarRecursivo(no.direita, chave);
        }
    }
    
    @Override
    public int altura() {
        return altura(raiz);
    }
    
    @Override
    public int getContadorComparacoes() {
        return contadorComparacoes;
    }
    
    @Override
    public void resetarContadorComparacoes() {
        contadorComparacoes = 0;
    }
    
    @Override
    public void remover(int chave) {
        // Implementação similar à BST com balanceamento AVL
        raiz = removerRecursivo(raiz, chave);
    }
    
    private No removerRecursivo(No no, int chave) {
        // Implementação de remoção AVL
        return no; // Placeholder
    }
}
