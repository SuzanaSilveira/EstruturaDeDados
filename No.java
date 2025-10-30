package TrabalhoArvore;

public class No {
	
	Restaurante dados;
    No esquerda;
    No direita;
    
    // Construtor básico
    public No(Restaurante dados) {
        this.dados = dados;
        this.esquerda = null;
        this.direita = null;
    }
}
