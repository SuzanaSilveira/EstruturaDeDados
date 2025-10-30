package src;

import java.io.*;
import java.util.*;

class CarregadorDataset {
    private static final String CAMINHO_DATASET = "data/dados_ifood.csv";
    
    public static List<Restaurante> carregarRestaurantes() {
        List<Restaurante> restaurantes = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_DATASET))) {
            String linha;
            boolean cabecalho = true;
            List<String> cabecalhos = new ArrayList<>();
            
            while ((linha = br.readLine()) != null) {
                // Usar separador ponto-e-vírgula comum em datasets brasileiros
                String[] valores = linha.split(";");
                
                if (cabecalho) {
                    cabecalhos = Arrays.asList(valores);
                    cabecalho = false;
                    continue;
                }
                
                if (valores.length >= 10) {
                    try {
                        int idRestaurante = parseIntSeguro(valores[0]);
                        String nomeRestaurante = getStringSeguro(valores, 1);
                        String cidade = getStringSeguro(valores, 2);
                        double avaliacaoMedia = parseDoubleSeguro(valores, 5);
                        int totalAvaliacoes = parseIntSeguro(valores[6]);
                        String tipoComida = getStringSeguro(valores, 7);
                        int tempoEntregaMedio = parseIntSeguro(valores[8]);
                        double precoMedio = parseDoubleSeguro(valores, 9);
                        
                        restaurantes.add(new Restaurante(
                            idRestaurante, nomeRestaurante, cidade, avaliacaoMedia,
                            totalAvaliacoes, tipoComida, tempoEntregaMedio, precoMedio
                        ));
                    } catch (Exception e) {
                        System.out.println("Erro ao processar linha: " + linha);
                    }
                }
            }
            
            System.out.println("Cabeçalhos encontrados: " + cabecalhos);
            
        } catch (IOException e) {
            System.out.println("Erro ao carregar dataset: " + e.getMessage());
            System.out.println("Gerando dados de exemplo...");
            restaurantes = gerarRestaurantesExemplo(10000);
        }
        
        return restaurantes;
    }
    
    // Métodos auxiliares para parsing seguro
    private static int parseIntSeguro(String valor) {
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    
    private static double parseDoubleSeguro(String[] valores, int indice) {
        if (indice >= valores.length) return 0.0;
        try {
            return Double.parseDouble(valores[indice].trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    private static String getStringSeguro(String[] valores, int indice) {
        if (indice >= valores.length) return "";
        return valores[indice].trim();
    }
    
    private static List<Restaurante> gerarRestaurantesExemplo(int quantidade) {
        List<Restaurante> restaurantes = new ArrayList<>();
        Random random = new Random();
        
        String[] cidades = {"São Paulo", "Rio de Janeiro", "Belo Horizonte", "Brasília", 
                          "Porto Alegre", "Salvador", "Fortaleza", "Recife"};
        String[] tiposComida = {"Pizza", "Hambúrguer", "Japonesa", "Brasileira", 
                               "Italiana", "Árabe", "Chinesa", "Mexicana"};
        
        for (int i = 1; i <= quantidade; i++) {
            int idRestaurante = i;
            String nomeRestaurante = "Restaurante " + i;
            String cidade = cidades[random.nextInt(cidades.length)];
            double avaliacaoMedia = 3.0 + random.nextDouble() * 2.0; // 3.0-5.0
            int totalAvaliacoes = random.nextInt(1000);
            String tipoComida = tiposComida[random.nextInt(tiposComida.length)];
            int tempoEntregaMedio = 20 + random.nextInt(41); // 20-60 minutos
            double precoMedio = 15 + random.nextDouble() * 85; // R$15-100
            
            restaurantes.add(new Restaurante(
                idRestaurante, nomeRestaurante, cidade, avaliacaoMedia,
                totalAvaliacoes, tipoComida, tempoEntregaMedio, precoMedio
            ));
        }
        
        return restaurantes;
    }
}
