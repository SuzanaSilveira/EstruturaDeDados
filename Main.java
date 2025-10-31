package src;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Restaurante> restaurantes;
    private static ArvoreBinariaBusca abb;
    private static ArvoreAVL avl;
    private static ArvoreRubroNegra arn;
    
    public static void main(String[] args) {
        System.out.println("================================================");
        System.out.println("    SISTEMA DE ANÁLISE DE ÁRVORES - iFOOD");
        System.out.println("==============================================\n");
        
        inicializarSistema();
        
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            
            switch (opcao) {
                case 1:
                    carregarDados();
                    break;
                case 2:
                    testarPerformanceArvores();
                    break;
                case 3:
                    buscarRestauranteEspecifico(scanner);
                    break;
                case 4:
                    exibirEstatisticasRestaurantes();
                    break;
                case 5:
                    compararEstruturas();
                    break;
                case 6:
                    demonstracaoInsercaoBusca();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
            
            if (opcao != 0) {
                System.out.println("\nPressione Enter para continuar...");
                scanner.nextLine();
            }
            
        } while (opcao != 0);
        
        scanner.close();
    }
    
    private static void inicializarSistema() {
        System.out.println("Inicializando estruturas de dados...");
        abb = new ArvoreBinariaBusca();
        avl = new ArvoreAVL();
        arn = new ArvoreRubroNegra();
        System.out.println("Estruturas inicializadas com sucesso!\n");
    }
    
    private static void exibirMenu() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Carregar Dados dos Restaurantes");
        System.out.println("2. Testar Performance das Árvores");
        System.out.println("3. Buscar Restaurante Específico");
        System.out.println("4. Exibir Estatísticas dos Restaurantes");
        System.out.println("5. Comparar Estruturas de Dados");
        System.out.println("6. Demonstração de Inserção e Busca");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }
    
    private static void carregarDados() {
        System.out.println("\n--- CARREGAMENTO DE DADOS ---");
        System.out.println("Carregando dataset de restaurantes iFood...");
        
        restaurantes = CarregadorDataset.carregarRestaurantes();
        
        if (restaurantes.isEmpty()) {
            System.out.println("ERRO: Nenhum restaurante foi carregado!");
            return;
        }
        
        System.out.println("Dataset carregado: " + restaurantes.size() + " restaurantes");
        
        // Inserir dados nas árvores
        System.out.println("Inserindo dados nas árvores...");
        inserirDadosNasArvores();
        System.out.println("Dados inseridos com sucesso!");
        
        // Mostrar amostra
        System.out.println("\n--- AMOSTRA DE DADOS ---");
        for (int i = 0; i < Math.min(3, restaurantes.size()); i++) {
            System.out.println(restaurantes.get(i));
        }
    }
    
    private static void inserirDadosNasArvores() {
        Metricas metricas = new Metricas();
        
        // Inserir na ABB
        metricas.iniciarTemporizador();
        for (Restaurante r : restaurantes) {
            abb.inserir(r.idRestaurante, r.tempoEntregaMedio);
        }
        metricas.pararTemporizador();
        System.out.printf("ABB: %d inserções em %.3f ms\n", 
                         restaurantes.size(), metricas.getTempoDecorridoMillis());
        
        // Inserir na AVL
        metricas.iniciarTemporizador();
        for (Restaurante r : restaurantes) {
            avl.inserir(r.idRestaurante, r.tempoEntregaMedio);
        }
        metricas.pararTemporizador();
        System.out.printf("AVL: %d inserções em %.3f ms\n", 
                         restaurantes.size(), metricas.getTempoDecorridoMillis());
        
        // Inserir na Rubro-Negra
        metricas.iniciarTemporizador();
        for (Restaurante r : restaurantes) {
            arn.inserir(r.idRestaurante, r.tempoEntregaMedio);
        }
        metricas.pararTemporizador();
        System.out.printf("Rubro-Negra: %d inserções em %.3f ms\n", 
                         restaurantes.size(), metricas.getTempoDecorridoMillis());
    }
    
    private static void testarPerformanceArvores() {
        if (restaurantes == null || restaurantes.isEmpty()) {
            System.out.println("ERRO: Carregue os dados primeiro (Opção 1)!");
            return;
        }
        
        System.out.println("\n--- TESTE DE PERFORMANCE DAS ÁRVORES ---");
        TestadorArvores.testarArvores(restaurantes);
    }
    
    private static void buscarRestauranteEspecifico(Scanner scanner) {
        if (restaurantes == null || restaurantes.isEmpty()) {
            System.out.println("ERRO: Carregue os dados primeiro (Opção 1)!");
            return;
        }
        
        System.out.println("\n--- BUSCA DE RESTAURANTE ---");
        System.out.print("Digite o ID do restaurante: ");
        int idBusca = scanner.nextInt();
        
        // Buscar nas três árvores
        System.out.println("\nResultados da busca:");
        
        abb.resetarContadorComparacoes();
        Integer resultadoABB = abb.buscar(idBusca);
        System.out.printf("ABB: %s (%d comparações)\n", 
                         resultadoABB != null ? "Encontrado" : "Não encontrado", 
                         abb.getContadorComparacoes());
        
        avl.resetarContadorComparacoes();
        Integer resultadoAVL = avl.buscar(idBusca);
        System.out.printf("AVL: %s (%d comparações)\n", 
                         resultadoAVL != null ? "Encontrado" : "Não encontrado", 
                         avl.getContadorComparacoes());
        
        arn.resetarContadorComparacoes();
        Integer resultadoRN = arn.buscar(idBusca);
        System.out.printf("Rubro-Negra: %s (%d comparações)\n", 
                         resultadoRN != null ? "Encontrado" : "Não encontrado", 
                         arn.getContadorComparacoes());
        
        // Mostrar informações do restaurante se encontrado
        if (resultadoABB != null) {
            restaurantes.stream()
                .filter(r -> r.idRestaurante == idBusca)
                .findFirst()
                .ifPresent(restaurante -> {
                    System.out.println("\nInformações do Restaurante:");
                    System.out.println(restaurante);
                });
        }
    }
    
    private static void exibirEstatisticasRestaurantes() {
        if (restaurantes == null || restaurantes.isEmpty()) {
            System.out.println("ERRO: Carregue os dados primeiro (Opção 1)!");
            return;
        }
        
        System.out.println("\n--- ESTATÍSTICAS DOS RESTAURANTES ---");
        
        // Análise básica
        long totalAvaliacoes = restaurantes.stream()
            .mapToInt(r -> r.totalAvaliacoes)
            .sum();
        double mediaAvaliacao = restaurantes.stream()
            .mapToDouble(r -> r.avaliacaoMedia)
            .average()
            .orElse(0.0);
        double mediaPreco = restaurantes.stream()
            .mapToDouble(r -> r.precoMedio)
            .average()
            .orElse(0.0);
        
        System.out.printf("Total de restaurantes: %d\n", restaurantes.size());
        System.out.printf("Total de avaliações: %d\n", totalAvaliacoes);
        System.out.printf("Avaliação média: %.2f\n", mediaAvaliacao);
        System.out.printf("Preço médio: R$ %.2f\n", mediaPreco);
        
        // Restaurante com melhor avaliação
        restaurantes.stream()
            .max((r1, r2) -> Double.compare(r1.avaliacaoMedia, r2.avaliacaoMedia))
            .ifPresent(melhor -> {
                System.out.printf("\nMelhor avaliado: %s (Nota: %.1f)\n", 
                                 melhor.nomeRestaurante, melhor.avaliacaoMedia);
            });
    }
    
    private static void compararEstruturas() {
        if (restaurantes == null || restaurantes.isEmpty()) {
            System.out.println("ERRO: Carregue os dados primeiro (Opção 1)!");
            return;
        }
        
        System.out.println("\n--- COMPARAÇÃO DAS ESTRUTURAS ---");
        
        System.out.println("\nAlturas das árvores:");
        System.out.printf("ABB: %d\n", abb.altura());
        System.out.printf("AVL: %d\n", avl.altura());
        System.out.printf("Rubro-Negra: %d\n", arn.altura());
        
        System.out.println("\nFator de balanceamento ideal:");
        double alturaMinima = Math.ceil(Math.log(restaurantes.size() + 1) / Math.log(2));
        System.out.printf("Altura mínima teórica: %.1f\n", alturaMinima);
        System.out.printf("Eficiência ABB: %.1f%%\n", (alturaMinima / abb.altura()) * 100);
        System.out.printf("Eficiência AVL: %.1f%%\n", (alturaMinima / avl.altura()) * 100);
        System.out.printf("Eficiência RN: %.1f%%\n", (alturaMinima / arn.altura()) * 100);
    }
    
    private static void demonstracaoInsercaoBusca() {
        System.out.println("\n--- DEMONSTRAÇÃO DE INSERÇÃO E BUSCA ---");
        
        // Criar uma pequena árvore de exemplo
        ArvoreBinariaBusca arvoreDemo = new ArvoreBinariaBusca();
        
        System.out.println("Inserindo 10 restaurantes de exemplo...");
        for (int i = 1; i <= 10; i++) {
            arvoreDemo.inserir(i * 100, i * 5); // ID e tempo de entrega
        }
        
        System.out.printf("Altura da árvore demo: %d\n", arvoreDemo.altura());
        
        // Testar algumas buscas
        System.out.println("\nTestando buscas:");
        int[] idsTeste = {100, 500, 900, 1500};
        for (int id : idsTeste) {
            arvoreDemo.resetarContadorComparacoes();
            Integer resultado = arvoreDemo.buscar(id);
            System.out.printf("ID %d: %s (%d comparações)\n", 
                             id, resultado != null ? "Encontrado" : "Não encontrado",
                             arvoreDemo.getContadorComparacoes());
        }
    }
}
