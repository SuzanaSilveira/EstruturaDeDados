package TrabalhoArvore;

public class ABB {
	private No raiz;
    private int contadorComparacoes;
    
    public ABB() {
        this.raiz = null;
        this.contadorComparacoes = 0;
    }

    // --- MÉTODOS OBRIGATÓRIOS ---

    /**
     * @return O número total de comparações de chaves realizadas.
     */
    public int contador_comparacoes() {
        return contadorComparacoes;
    }
    
    /**
     * @return A altura da árvore.
     */
    public int altura() {
        return calcularAltura(raiz);
    }

    // --- Implementações de Manipulação e Busca ---

    /**
     * @return Os dados do Restaurante, ou null se não for encontrado.
     */
    public Restaurante buscar(String chave) {
        this.contadorComparacoes = 0; // Opcional: zerar para medir UMA busca
        return buscarRecursivo(raiz, chave);
    }

    public void inserir(String chave, Restaurante dados) {
        // Para uma inserção limpa, você pode zerar o contador aqui:
        // this.contadorComparacoes = 0; 
        
        // Cria um novo objeto Restaurante apenas para comparação na busca (o que não é o ideal, 
        // mas simplifica para a comparação de String se você não passar o objeto completo)
        Restaurante novo = new Restaurante(chave, dados.nome, dados.nota);
        this.raiz = inserirRecursivo(this.raiz, novo);
    }

    public void remover(String chave) {
        // Implementação omitida por complexidade, mas deve incrementar o contador!
        // ...
    }
    
    // --- Métodos Privados de Suporte ---

    private int calcularAltura(No no) {
        if (no == null) {
            return -1;
        } else {
            return 1 + Math.max(calcularAltura(no.esquerda), calcularAltura(no.direita));
        }
    }

    private Restaurante buscarRecursivo(No atual, String chave) {
        if (atual == null) {
            return null;
        }

        // Simula o objeto de busca para usar o compareTo
        Restaurante chaveBusca = new Restaurante(chave, "", 0.0);

        // Incremento do contador: CHAVE
        contadorComparacoes++; 
        int comparacao = chaveBusca.compareTo(atual.dados); 

        if (comparacao == 0) {
            return atual.dados; // Encontrado
        } else if (comparacao < 0) {
            return buscarRecursivo(atual.esquerda, chave);
        } else {
            return buscarRecursivo(atual.direita, chave);
        }
    }

    private No inserirRecursivo(No atual, Restaurante dados) {
        if (atual == null) {
            return new No(dados);
        }

        // Incremento do contador: CHAVE
        contadorComparacoes++; 
        int comparacao = dados.compareTo(atual.dados);

        if (comparacao < 0) {
            atual.esquerda = inserirRecursivo(atual.esquerda, dados);
        } else if (comparacao > 0) {
            atual.direita = inserirRecursivo(atual.direita, dados);
        } else {
            // Chave já existe, atualiza os dados (ou ignora, dependendo da sua regra)
            atual.dados = dados; 
        }
        return atual;
    }

}
