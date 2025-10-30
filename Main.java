package TrabalhoArvore;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    // Defina o nome do arquivo CSV (assumindo que está no mesmo diretório do projeto)
	private static final String CSV_FILE = "C:/ifood-restaurants-february-2021.csv";
    // Delimitador padrão do Kaggle para este dataset é a vírgula
    private static final String CSV_DELIMITER = ",";

    public static void main(String[] args) {
        
        // 1. Cria a instância da sua Árvore Binária de Busca
        ABB arvoreBusca = new ABB();
        
        // 2. Carrega e Insere os dados
        System.out.println("Iniciando a leitura do dataset e inserção na ABB...");
        // A lista de testes é usada para escolher chaves válidas para buscar depois.
        List<Restaurante> listaTestes = carregarDadosABB(arvoreBusca);
        System.out.println("Inserção concluída. Total de " + listaTestes.size() + " registros processados.");
        
        // 3. Resultados das Métricas da Inserção
        System.out.println("\n--- Métricas da ABB (Após Inserção de " + listaTestes.size() + " Registros) ---");
        System.out.println("Altura Final da ABB: " + arvoreBusca.altura());
        System.out.println("Total de Comparações na Inserção: " + arvoreBusca.contador_comparacoes());
        
        // 4. Testes de Busca (Métricas de Desempenho)
        realizarTesteBusca(arvoreBusca, listaTestes);
    }

    /**
     * Realiza testes de busca em diferentes posições da árvore.
     */
    private static void realizarTesteBusca(ABB arvore, List<Restaurante> lista) {
        if (lista.isEmpty()) {
            System.out.println("Não é possível realizar testes de busca: a lista de dados está vazia.");
            return;
        }

        System.out.println("\n--- Testes de Desempenho de Busca (5 Amostras) ---");
        
        int[] indices = { 
            0, // Primeiro item (pior ou melhor caso dependendo da ordem)
            lista.size() / 4, // 25%
            lista.size() / 2, // 50% (Meio)
            lista.size() * 3 / 4, // 75%
            lista.size() - 1 // Último item (pior ou melhor caso dependendo da ordem)
        };
        
        long totalComparacoesBusca = 0;
        
        for (int i = 0; i < indices.length; i++) {
            // Garante que o índice não exceda o tamanho da lista
            int index = Math.min(indices[i], lista.size() - 1);
            Restaurante restauranteBusca = lista.get(index);
            
            // Reseta o contador APENAS para esta busca
            arvore.resetContador(); 
            
            arvore.buscar(restauranteBusca.getId());
            
            long comparacoes = arvore.contador_comparacoes();
            totalComparacoesBusca += comparacoes;
            
            System.out.printf("Busca #%d (Reg. #%d): %d comparações.\n", (i + 1), (index + 1), comparacoes);
        }
        
        System.out.println("\nMédia de Comparações de Busca: " + (double) totalComparacoesBusca / indices.length);
        System.out.println("O resultado da ABB não-balanceada deve ser alto.");
    }

    /**
     * Lê o arquivo CSV, cria objetos Restaurante e os insere na ABB.
     */
    private static List<Restaurante> carregarDadosABB(ABB arvore) {
        List<Restaurante> listaRestaurantes = new ArrayList<>();
        int linhasProcessadas = 0;
        
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String linha;
            br.readLine(); // Pula o cabeçalho do CSV
            
            while ((linha = br.readLine()) != null) {
                linhasProcessadas++;
                // Usa uma Regex simples para dividir, mas ignora vírgulas dentro de aspas (se for o caso)
                String[] colunas = linha.split(CSV_DELIMITER);
                
                // Mapeamento esperado das colunas (Verifique no seu CSV):
                // 5: name, 7: rating, 8: url (contém o ID)
                
                try {
                    if (colunas.length < 9) {
                        continue; // Linha incompleta
                    }

                    // Extrai o ID do restaurante da URL (o último segmento após a última barra)
                    String url = colunas[8].trim(); 
                    if (url.isEmpty()) continue;
                    
                    String id = url.substring(url.lastIndexOf("/") + 1).trim();
                    if (id.isEmpty()) continue;
                    
                    String nome = colunas[5].trim();
                    // Converte a nota para double. Usa replace para garantir que o formato decimal seja o correto (ponto)
                    double nota = Double.parseDouble(colunas[7].trim().replace(",", "."));
                    
                    // Cria o objeto Restaurante
                    Restaurante r = new Restaurante(id, nome, nota);
                    
                    // Insere na ABB e armazena na lista para testes
                    arvore.inserir(id, r);
                    listaRestaurantes.add(r);
                    
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    // System.err.println("Erro ao processar linha " + linhasProcessadas + ": " + e.getMessage());
                    // Ignora linhas com erros de formato
                }
            }
        } catch (IOException e) {
            System.err.println("ERRO FATAL: Falha ao ler o arquivo CSV: " + e.getMessage());
            System.err.println("Verifique o nome do arquivo: " + CSV_FILE + " e o delimitador: '" + CSV_DELIMITER + "'.");
        }
        return listaRestaurantes;
    }
}
