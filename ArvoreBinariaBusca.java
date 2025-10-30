package src;

class ArvoreBinariaBusca implements Arvore {
    private No raiz;
    private int contadorComparacoes;
    
    public ArvoreBinariaBusca() {
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
            // Chave já existe, atualiza dados
            no.dados = dados;
        }
        
        return no;
    }
    
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
    public void remover(int chave) {
        raiz = removerRecursivo(raiz, chave);
    }
    
    private No removerRecursivo(No no, int chave) {
        contadorComparacoes++;
        if (no == null) {
            return null;
        }
        
        contadorComparacoes++;
        if (chave < no.chave) {
            no.esquerda = removerRecursivo(no.esquerda, chave);
        } else if (chave > no.chave) {
            no.direita = removerRecursivo(no.direita, chave);
        } else {
            // Nó encontrado para remoção
            if (no.esquerda == null) {
                return no.direita;
            } else if (no.direita == null) {
                return no.esquerda;
            }
            
            // Nó com dois filhos
            no.chave = valorMinimo(no.direita);
            no.direita = removerRecursivo(no.direita, no.chave);
        }
        
        return no;
    }
    
    private int valorMinimo(No no) {
        int valorMinimo = no.chave;
        while (no.esquerda != null) {
            no = no.esquerda;
            valorMinimo = no.chave;
        }
        return valorMinimo;
    }
    
    @Override
    public int altura() {
        return alturaRecursivo(raiz);
    }
    
    private int alturaRecursivo(No no) {
        if (no == null) {
            return 0;
        }
        return 1 + Math.max(alturaRecursivo(no.esquerda), alturaRecursivo(no.direita));
    }
    
    @Override
    public int getContadorComparacoes() {
        return contadorComparacoes;
    }
    
    @Override
    public void resetarContadorComparacoes() {
        contadorComparacoes = 0;
    }
}
