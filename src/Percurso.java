import java.util.*;

public class Percurso {
	
    public static String ordemDePercurso=""; 
    
    public static void profundidade(int pai, int vertice, int nivel, Map<Integer, List<Integer>> listaAdjacencia, int[] visitados) {
        System.out.println("Vértice: " + vertice);
        System.out.println("Nível do vértice " + vertice + ": " + nivel);
        System.out.println("Pai do vértice " + vertice + ": " + pai+"\n");

        ordemDePercurso+=vertice+", ";
        List<Integer> lista = listaAdjacencia.get(vertice);
        

        if (lista == null || lista.size() == 0) {
            System.out.println("Vértice não tem vizinhos\n");
            return;
        }
        
        visitados[vertice] = 1;

        for (int i = 0; i < lista.size(); i++) {
            int element = lista.get(i);
            if (visitados[element] == 1) {
                continue;
            } else if (visitados[element] == 0) {
                profundidade(vertice, element, nivel + 1, listaAdjacencia, visitados);
            }
        }
        
    }

    public static void largura(int verticeInicial, Map<Integer, List<Integer>> listaAdjacencia) {
        // Verifica se o vértice inicial existe
        if (!listaAdjacencia.containsKey(verticeInicial)) {
            System.out.println("O vértice inicial " + verticeInicial + " não existe no grafo.");
            return;
        }

        // Estruturas de dados
        Queue<Integer> fila = new LinkedList<>();
        Set<Integer> visitados = new HashSet<>();
        Map<Integer, Integer> niveis = new HashMap<>();
        Map<Integer, Integer> pais = new HashMap<>();

        // Inicializa o vértice inicial
        fila.add(verticeInicial);
        visitados.add(verticeInicial);
        niveis.put(verticeInicial, 0);
        pais.put(verticeInicial, null); 

        // Executa a busca em largura
        while (!fila.isEmpty()) {
            int verticeAtual = fila.poll();
            int nivelAtual = niveis.get(verticeAtual);
            Integer pai = pais.get(verticeAtual);

            System.out.println("Vértice: " + verticeAtual);
            System.out.println("Nível do vértice " + verticeAtual + ": " + nivelAtual);
            System.out.println("Pai do vértice " + verticeAtual + ": " + (pai != null ? pai : "Nenhum") + "\n");
            ordemDePercurso+=verticeAtual+", ";
            // Adiciona os vértices adjacentes à fila
            for (int adjacente : listaAdjacencia.get(verticeAtual)) {
                
                if (!visitados.contains(adjacente)) {
                	
                    fila.add(adjacente);
                    visitados.add(adjacente);
                    niveis.put(adjacente, nivelAtual + 1);
                    pais.put(adjacente, verticeAtual);
                }
            }
        }
    }
    
    
}
