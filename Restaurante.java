package TrabalhoArvore;

public class Restaurante implements Comparable<Restaurante> {
	
	//(O Objeto de Dados)
	//Esta classe representa os dados do seu dataset. O método compareTo é crucial para as árvores

    // Chave: vamos usar o ID único como String
    private String id;
    // Dados para análise
    private String nome;
    private double nota;
    
    public Restaurante(String id, String nome, double nota) {
        this.id = id;
        this.nome = nome;
        this.nota = nota;
    }

    public String getId() {
        return id;
    }

    // Método obrigatório para que a árvore saiba como ordenar
    @Override
    public int compareTo(Restaurante outro) {
        // Compara os IDs como strings
        return this.id.compareTo(outro.id);
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Nota: " + nota;
    }
}
