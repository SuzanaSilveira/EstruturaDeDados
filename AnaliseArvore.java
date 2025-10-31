package src;

import java.util.*;

public class AnaliseArvores {
    public static void main(String[] args) {
        System.out.println("=== ANALISE DE ARVORES COM DADOS iFood RESTAURANTS ===\n");
        
        // Carregar os dados dos restaurantes
        
        System.out.println("Carregando dataset de restaurantes iFood...");
        List<Restaurante> restaurantes = CarregadorDataset.carregarRestaurantes();
        System.out.println("Dataset carregado: " + restaurantes.size() + " restaurantes\n");
        
        // Mostrar alguns exemplos
        System.out.println("=== AMOSTRA DE DADOS ===");
        for (int i = 0; i < Math.min(5, restaurantes.size()); i++) {
            System.out.println(restaurantes.get(i));
        }
        System.out.println();
        
        // Executa os testes comparativos das árvores
        TestadorArvores.testarArvores(restaurantes);
        
        // Análises específicas para restaurantes
        realizarAnaliseRestaurantes(restaurantes);
    }
    
    private static void realizarAnaliseRestaurantes(List<Restaurante> restaurantes) {
        System.out.println("\n=== ANALISE ESPECIFICA - RESTAURANTES ===");
        
        // Analise por tipo de comida
        Map<String, Integer> contagemTipoComida = new HashMap<>();
        Map<String, Double> avaliacaoMediaTipoComida = new HashMap<>();
        Map<String, Integer> tempoEntregaTipoComida = new HashMap<>();
        
        for (Restaurante r : restaurantes) {
            contagemTipoComida.merge(r.tipoComida, 1, Integer::sum);
            avaliacaoMediaTipoComida.merge(r.tipoComida, r.avaliacaoMedia, Double::sum);
            tempoEntregaTipoComida.merge(r.tipoComida, r.tempoEntregaMedio, Integer::sum);
        }
        
        System.out.println("\nDistribuição por Tipo de Comida:");
        for (String tipoComida : contagemTipoComida.keySet()) {
            int quantidade = contagemTipoComida.get(tipoComida);
            double avaliacaoMedia = avaliacaoMediaTipoComida.get(tipoComida) / quantidade;
            int tempoEntregaMedio = tempoEntregaTipoComida.get(tipoComida) / quantidade;
            
            System.out.printf("%s: %d restaurantes (Avaliação: %.1f, Entrega: %d min)\n",
                    tipoComida, quantidade, avaliacaoMedia, tempoEntregaMedio);
        }
        
        // Análise de avaliações
        System.out.println("\nDistribuição de Avaliações:");
        Map<String, Integer> faixasAvaliacao = new LinkedHashMap<>();
        faixasAvaliacao.put("0.0-2.9", 0);
        faixasAvaliacao.put("3.0-3.4", 0);
        faixasAvaliacao.put("3.5-3.9", 0);
        faixasAvaliacao.put("4.0-4.4", 0);
        faixasAvaliacao.put("4.5-5.0", 0);
        
        for (Restaurante r : restaurantes) {
            if (r.avaliacaoMedia <= 2.9) {
                faixasAvaliacao.put("0.0-2.9", faixasAvaliacao.get("0.0-2.9") + 1);
            } else if (r.avaliacaoMedia <= 3.4) {
                faixasAvaliacao.put("3.0-3.4", faixasAvaliacao.get("3.0-3.4") + 1);
            } else if (r.avaliacaoMedia <= 3.9) {
                faixasAvaliacao.put("3.5-3.9", faixasAvaliacao.get("3.5-3.9") + 1);
            } else if (r.avaliacaoMedia <= 4.4) {
                faixasAvaliacao.put("4.0-4.4", faixasAvaliacao.get("4.0-4.4") + 1);
            } else {
                faixasAvaliacao.put("4.5-5.0", faixasAvaliacao.get("4.5-5.0") + 1);
            }
        }
        
        for (Map.Entry<String, Integer> entry : faixasAvaliacao.entrySet()) {
            double percentual = (entry.getValue() / (double)restaurantes.size()) * 100;
            System.out.printf("%s: %d restaurantes (%.1f%%)\n", 
                    entry.getKey(), entry.getValue(), percentual);
        }
        
        // Análise por cidade
        System.out.println("\nDistribuição por Cidade:");
        Map<String, Integer> restaurantesPorCidade = new HashMap<>();
        for (Restaurante r : restaurantes) {
            restaurantesPorCidade.merge(r.cidade, 1, Integer::sum);
        }
        
        restaurantesPorCidade.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(8)
            .forEach(entry -> {
                System.out.printf("%s: %d restaurantes\n", entry.getKey(), entry.getValue());
            });
    }
}
