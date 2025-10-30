package src;

import java.util.*;

class TestadorArvores {
    public static void testarArvores(List<Restaurante> restaurantes) {
        ArvoreBinariaBusca abb = new ArvoreBinariaBusca();
        ArvoreAVL avl = new ArvoreAVL();
        ArvoreRubroNegra arn = new ArvoreRubroNegra();
        
        Metricas metricas = new Metricas();
        
        System.out.println("=== TESTE DE INSERÇÃO ===");
        testarInsercao(abb, restaurantes, metricas, "Árvore Binária Busca");
        testarInsercao(avl, restaurantes, metricas, "Árvore AVL");
        testarInsercao(arn, restaurantes, metricas, "Árvore Rubro-Negra");
        
        System.out.println("\n=== TESTE DE BUSCA ===");
        testarBusca(abb, restaurantes, metricas, "Árvore Binária Busca");
        testarBusca(avl, restaurantes, metricas, "Árvore AVL");
        testarBusca(arn, restaurantes, metricas, "Árvore Rubro-Negra");
        
        System.out.println("\n=== TESTE DE BUSCA POR INTERVALO ===");
        testarBuscaIntervalo(abb, restaurantes, "Árvore Binária Busca");
        testarBuscaIntervalo(avl, restaurantes, "Árvore AVL");
        testarBuscaIntervalo(arn, restaurantes, "Árvore Rubro-Negra");
        
        System.out.println("\n=== MÉTRICAS DE ESTRUTURA ===");
        imprimirMetricasEstrutura(abb, avl, arn, restaurantes.size());
    }
    
    private static void testarInsercao(Arvore arvore, List<Restaurante> restaurantes, Metricas metricas, String nomeArvore) {
        metricas.iniciarTemporizador();
        arvore.resetarContadorComparacoes();
        
        for (Restaurante restaurante : restaurantes) {
            // Usar idRestaurante como chave e tempoEntregaMedio como dados
            arvore.inserir(restaurante.idRestaurante, restaurante.tempoEntregaMedio);
        }
        
        metricas.pararTemporizador();
        
        System.out.printf("%s - Inserção: %.3f ms, Comparações: %d, Altura: %d\n",
                nomeArvore, metricas.getTempoDecorridoMillis(), 
                arvore.getContadorComparacoes(), arvore.altura());
    }
    
    private static void testarBusca(Arvore arvore, List<Restaurante> restaurantes, Metricas metricas, String nomeArvore) {
        Random random = new Random();
        int quantidadeBuscas = 1000;
        long tempoTotal = 0;
        int comparacoesTotais = 0;
        
        for (int i = 0; i < quantidadeBuscas; i++) {
            int indiceAleatorio = random.nextInt(restaurantes.size());
            int chaveBusca = restaurantes.get(indiceAleatorio).idRestaurante;
            
            arvore.resetarContadorComparacoes();
            metricas.iniciarTemporizador();
            
            Integer resultado = arvore.buscar(chaveBusca);
            
            metricas.pararTemporizador();
            
            tempoTotal += metricas.getTempoDecorrido();
            comparacoesTotais += arvore.getContadorComparacoes();
        }
        
        double tempoMedio = (tempoTotal / (double)quantidadeBuscas) / 1_000_000.0;
        double comparacoesMedias = comparacoesTotais / (double)quantidadeBuscas;
        
        System.out.printf("%s - Busca: %.6f ms (média), %.2f comparações (média)\n",
                nomeArvore, tempoMedio, comparacoesMedias);
    }
    
    private static void testarBuscaIntervalo(Arvore arvore, List<Restaurante> restaurantes, String nomeArvore) {
        Random random = new Random();
        int quantidadeBuscas = 100;
        long tempoTotal = 0;
        int encontrados = 0;
        
        for (int i = 0; i < quantidadeBuscas; i++) {
            int avaliacaoMinima = 4; // Buscar restaurantes com avaliação >= 4
            Metricas metricas = new Metricas();
            metricas.iniciarTemporizador();
            
            // Simular busca por alta avaliação
            for (Restaurante r : restaurantes) {
                if (r.avaliacaoMedia >= avaliacaoMinima) {
                    arvore.buscar(r.idRestaurante);
                    encontrados++;
                }
            }
            
            metricas.pararTemporizador();
            tempoTotal += metricas.getTempoDecorrido();
        }
        
        double tempoMedio = (tempoTotal / (double)quantidadeBuscas) / 1_000_000.0;
        System.out.printf("%s - Busca intervalo: %.3f ms (média), %d encontrados\n",
                nomeArvore, tempoMedio, encontrados / quantidadeBuscas);
    }
    
    private static void imprimirMetricasEstrutura(ArvoreBinariaBusca abb, ArvoreAVL avl, ArvoreRubroNegra arn, int totalElementos) {
        System.out.printf("ABB - Altura: %d, Balanceamento: %.2f%%\n", 
                abb.altura(), calcularFatorBalanceamento(abb.altura(), totalElementos));
        System.out.printf("AVL - Altura: %d, Balanceamento: %.2f%%\n", 
                avl.altura(), calcularFatorBalanceamento(avl.altura(), totalElementos));
        System.out.printf("Rubro-Negra - Altura: %d, Balanceamento: %.2f%%\n", 
                arn.altura(), calcularFatorBalanceamento(arn.altura(), totalElementos));
    }
    
    private static double calcularFatorBalanceamento(int altura, int elementos) {
        double alturaMinima = Math.ceil(Math.log(elementos + 1) / Math.log(2));
        return (alturaMinima / altura) * 100;
    }
}
