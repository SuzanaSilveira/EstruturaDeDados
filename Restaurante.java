package src;

class Restaurante {
    int idRestaurante;
    String nomeRestaurante;
    String cidade;
    double avaliacaoMedia;
    int totalAvaliacoes;
    String tipoComida;
    int tempoEntregaMedio;
    double precoMedio;
    
    public Restaurante(int idRestaurante, String nomeRestaurante, String cidade, 
                     double avaliacaoMedia, int totalAvaliacoes, String tipoComida, 
                     int tempoEntregaMedio, double precoMedio) {
        this.idRestaurante = idRestaurante;
        this.nomeRestaurante = nomeRestaurante;
        this.cidade = cidade;
        this.avaliacaoMedia = avaliacaoMedia;
        this.totalAvaliacoes = totalAvaliacoes;
        this.tipoComida = tipoComida;
        this.tempoEntregaMedio = tempoEntregaMedio;
        this.precoMedio = precoMedio;
    }
    
    @Override
    public String toString() {
        return String.format("Restaurante [ID: %d, Nome: %s, Cidade: %s, Avaliação: %.1f, Entrega: %d min]",
                idRestaurante, nomeRestaurante, cidade, avaliacaoMedia, tempoEntregaMedio);
    }
}
