import java.util.*;

public class Djikstra {

    public static void djikstra(int verticeInicial, int numVertices, Map<Integer, List<Integer>> listaAdjacencia, Map<String, Double> mapaArestas) {
        double[] distancias = new double[numVertices + 1];
        int[] predecessores = new int[numVertices + 1];
        Arrays.fill(distancias, Double.MAX_VALUE);
        Arrays.fill(predecessores, -1);

        distancias[verticeInicial] = 0;
        boolean[] visitado = new boolean[numVertices + 1];

        for (int i = 0; i < numVertices; i++) {
            int u = -1;

            // Encontrar o vértice não visitado com a menor distância
            for (int j = 1; j <= numVertices; j++) {
                if (!visitado[j] && (u == -1 || distancias[j] < distancias[u])) {
                    u = j;
                }
            }

            // Marca o vértice como visitado
            visitado[u] = true;

            // Atualizar distâncias dos vértices adjacentes
            List<Integer> adjacentes = listaAdjacencia.get(u);
            for (int vizinho : adjacentes) {
                String aresta = u < vizinho ? u + "-" + vizinho : vizinho + "-" + u;

                if (mapaArestas.containsKey(aresta)) {
                    double novaDistancia = distancias[u] + mapaArestas.get(aresta);
                    if (novaDistancia < distancias[vizinho]) {
                        distancias[vizinho] = novaDistancia;
                        predecessores[vizinho] = u;
                    }
                }
            }
        }

        // Visualização do array de distâncias e caminhos
        for (int i = 1; i < distancias.length; i++) {
            System.out.println("Distância do vértice " + verticeInicial + " até o vértice " + i + " é " + distancias[i]);
            System.out.print("Caminho: ");
            printCaminho(predecessores, i);
            System.out.println();
        }
    }

    // Função para imprimir o caminho a partir do vértice inicial até o vértice atual
    private static void printCaminho(int[] predecessores, int vertice) {
        if (vertice == -1) {
            return;
        }
        printCaminho(predecessores, predecessores[vertice]);
        System.out.print(vertice + " ");
    }
}
