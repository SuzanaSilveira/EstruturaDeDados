package src;

class Metricas {
    private long tempoInicio;
    private long tempoFim;
    
    public void iniciarTemporizador() {
        tempoInicio = System.nanoTime();
    }
    
    public void pararTemporizador() {
        tempoFim = System.nanoTime();
    }
    
    public long getTempoDecorrido() {
        return tempoFim - tempoInicio;
    }
    
    public double getTempoDecorridoMillis() {
        return (tempoFim - tempoInicio) / 1_000_000.0;
    }
}
