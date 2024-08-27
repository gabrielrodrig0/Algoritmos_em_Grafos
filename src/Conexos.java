	import java.util.*;

	public class Conexos {

	    public static String ordemDePercurso = ""; 

	    public static void profundidade(int vertice, Map<Integer, List<Integer>> listaAdjacencia, int[] visitados, List<Integer> componente) {
	        ordemDePercurso += vertice + ", ";
	        componente.add(vertice);
	        visitados[vertice] = 1;

	        List<Integer> lista = listaAdjacencia.get(vertice);
	        if (lista != null) {
	            for (int vizinho : lista) {
	                if (visitados[vizinho] == 0) {
	                    profundidade(vizinho, listaAdjacencia, visitados, componente);
	                }
	            }
	        }
	    }

	    public static List<List<Integer>> encontrarComponentesConexos(Map<Integer, List<Integer>> listaAdjacencia, int numVertices) {
	        int[] visitados = new int[numVertices];
	        List<List<Integer>> componentes = new ArrayList<>();

	        for (int i = 0; i < numVertices; i++) {
	            if (visitados[i] == 0) {
	                List<Integer> componente = new ArrayList<>();
	                profundidade(i, listaAdjacencia, visitados, componente);
	                componentes.add(componente);
	            }
	        }

	        componentes.sort((a, b) -> b.size() - a.size()); // Ordena em ordem decrescente de tamanho
	        return componentes;
	    }

	    public static void main(String[] args) {
	        Map<Integer, List<Integer>> listaAdjacencia = new HashMap<>();
	        // Exemplo de grafo
	        listaAdjacencia.put(1, Arrays.asList(1, 2));
	        listaAdjacencia.put(2, Arrays.asList(3, 1));
	        listaAdjacencia.put(4, Arrays.asList(5, 3));
	        listaAdjacencia.put(5, Arrays.asList(4, 1));

	        int numVertices = 5;

	        List<List<Integer>> componentes = encontrarComponentesConexos(listaAdjacencia, numVertices);

	        System.out.println("Número de componentes conexos: " + componentes.size());
	        for (List<Integer> componente : componentes) {
	            System.out.println("Componente: " + componente + " (Tamanho: " + componente.size() + ")");
	        }
	    }
	}


