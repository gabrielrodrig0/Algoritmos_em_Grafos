import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;

// Gabriel Rodrigo Bezerra Araujo - Trabalho de Grafos 2024.1

public class Main {

    private static int[][] matrizAdjacencia;
    public static Map<Integer, List<Integer>> listaAdjacencia;
    public static HashMap<String, Double> mapaArestas = new HashMap<>();
    public static Percurso percurso = new Percurso();
    public static Djikstra djikstra = new Djikstra();

    public static void main(String[] args) {
        // Pega o diretório da pasta atual e concatena com o diretório do arquivo a ser lido
        String dirTrabalho = System.getProperty("user.dir");
        String arquivo = dirTrabalho + "\\src\\Arquivo_de_Entrada.txt";

        try {
            // Leitura do arquivo .txt
            List<String> linhas = Files.readAllLines(Paths.get(arquivo));

            // Número de vértices.
            int numVertices = getVertices(linhas);

            // Inicializa a matriz de adjacência
            matrizAdjacencia = new int[numVertices + 1][numVertices + 1]; // +1 para índice começar de 1

            // Inicializa a lista de adjacência
            listaAdjacencia = new HashMap<>();
            for (int i = 1; i <= numVertices; i++) {
                listaAdjacencia.put(i, new ArrayList<>());
            }

            // Conjunto para armazenar arestas únicas
            Set<String> arestasUnicas = new HashSet<>();

            // Número de arestas.
            int arestas = 0;
            
            // Verificação de pesos
            boolean temPeso = false;
            
            if (linhas.size() > 1 && linhas.get(1).split(" ").length == 3) {
                temPeso = true;
            }
            
            if (temPeso) {
                for (int i = 1; i < linhas.size(); i++) {
                    String[] partes = linhas.get(i).split(" ");
                    
                    // Verifica se a linha tem o número esperado de partes
                    if (partes.length == 3) {
                        int vertice1 = Integer.parseInt(partes[0]);
                        int vertice2 = Integer.parseInt(partes[1]);
                        double peso = Double.parseDouble(partes[2]);
                        if(peso<0)
                        {
                        	System.out.println("Não é permitido pesos negativos");
                        	return;
                        }
                        String aresta = vertice1 < vertice2 ? vertice1 + "-" + vertice2 : vertice2 + "-" + vertice1;
                        mapaArestas.put(aresta, peso);
                        
                        // Se não tiver a aresta com peso adicione, se tiver ignore.
                        if (!arestasUnicas.contains(aresta)) {
                            arestasUnicas.add(aresta);

                            // Adiciona a aresta na lista de adjacência
                            listaAdjacencia.get(vertice1).add(vertice2);
                            listaAdjacencia.get(vertice2).add(vertice1);

                            // Adiciona a aresta na matriz de adjacência (1 ou 0), se for 1 existe, se for 0 não existe.
                            matrizAdjacencia[vertice1][vertice2] = 1;
                            matrizAdjacencia[vertice2][vertice1] = 1;

                            // Contador de arestas, conta sempre que uma aresta é adicionada na lista de arestas existentes
                            arestas++;
                        }
                    }
                }
                System.out.println(mapaArestas);
            } else {
                for (int i = 1; i < linhas.size(); i++) {
                    String[] partes = linhas.get(i).split(" ");

                    // Verifica se a linha tem o número esperado de partes
                    if (partes.length == 2) {
                        int vertice1 = Integer.parseInt(partes[0]);
                        int vertice2 = Integer.parseInt(partes[1]);
                        
                        String aresta = vertice1 < vertice2 ? vertice1 + "-" + vertice2 : vertice2 + "-" + vertice1;
                        
                        // Se não tiver a aresta com peso adicione, se tiver ignore.
                        if (!arestasUnicas.contains(aresta)) {
                            arestasUnicas.add(aresta);

                            // Adiciona a aresta na lista de adjacência
                            listaAdjacencia.get(vertice1).add(vertice2);
                            listaAdjacencia.get(vertice2).add(vertice1);

                            // Adiciona a aresta na matriz de adjacência (1 ou 0), se for 1 existe, se for 0 não existe.
                            matrizAdjacencia[vertice1][vertice2] = 1;
                            matrizAdjacencia[vertice2][vertice1] = 1;

                            // Contador de arestas, conta sempre que uma aresta é adicionada na lista de arestas existentes
                            arestas++;
                        }
                    }
                }
            }
      
            System.out.println(arestasUnicas);

            // Declarando Scanner para receber comandos.
            Scanner input = new Scanner(System.in);
            int opcao;
            do {
                System.out.println("Escolha a opção que deseja: \n");
                System.out.println(" 1 - Mostrar matriz adjacência\n 2 - Mostrar lista adjacência\n 3 - Mostrar grau do grafo\n 4 - Mostrar grau médio do grafo\n 5 - Mostrar distribuição empírica\n 6 - Mostrar número de arestas do grafo\n 7 - Mostrar número de vértices\n 8 - Busca no Grafo\n 9 - Djikstra \n <0 ou >9 - Finalizar programa\n");
                opcao = input.nextInt();

                switch (opcao) {
                    case 1: {
                        imprimirMatrizAdjacencia(numVertices);
                        System.out.println();
                        break;
                    }
                    case 2: {
                        imprimirListaAdjacencia();
                        System.out.println();
                        break;
                    }
                    case 3: {
                        mostrarGrauGrafo();
                        System.out.println();
                        break;
                    }
                    case 4: {
                        mostrarGrauMedio(numVertices);
                        System.out.println();
                        break;
                    }
                    case 5: {
                        mostrarDistribuicaoEmpirica(arestas, numVertices);
                        System.out.println();
                        break;
                    }
                    case 6: {
                        System.out.println("\nO grafo tem " + arestas + " arestas.\n");
                        break;
                    }
                    case 7: {
                        System.out.println("O grafo tem " + numVertices + " vértices.\n");
                        break;
                    }
                    case 8: {
                        System.out.println("Digite o tipo de busca: \n 1 - Percurso em Profundidade\n 2 - Percurso em largura\n");
                        int tipoBusca = input.nextInt();
                        
                        
                        if(tipoBusca==1)
                        {
                        	percurso.ordemDePercurso="";
                            System.out.println("Digite o vértice pelo qual deseja iniciar");
                            int vertice = input.nextInt();
                            int pai = -1;
                            int nivel = 0;
                            int[] visitados = new int[numVertices + 1];
                            percurso.profundidade(pai, vertice, nivel, listaAdjacencia, visitados);
                            System.out.println(percurso.ordemDePercurso);
                        }
                        
                        else if(tipoBusca==2)
                        {   
                        	percurso.ordemDePercurso="";
                            System.out.println("Digite o vértice pelo qual deseja iniciar");
                            int vertice = input.nextInt();
                            percurso.largura(vertice, listaAdjacencia);
                            System.out.println(percurso.ordemDePercurso);
                        }
                            
                        break;
                    }
                    case 9: {
                        if(!temPeso)
                        {
                        	System.out.println("Não é possível efetuar o algoritmo de Djikstra, pois as arestas não tem peso, deseja executar busca por largura?");
                        	System.out.println(" 1 - Sim\n 2 - Não");
                        	int escolha = input.nextInt();
                        	if(escolha==1)  
                        	{
                        		percurso.ordemDePercurso="";
                                System.out.println("Digite o vértice pelo qual deseja iniciar");
                                int vertice = input.nextInt();
                                percurso.largura(vertice, listaAdjacencia);
                                System.out.println(percurso.ordemDePercurso);
                        	}
                        	else 
                        	{
                        		
                        		break;
                        	}
                        }
                        else {
                        	System.out.println("Digite o vértice que você deseja começar:");
                        	Scanner sc = new Scanner(System.in);
                        	int verticeInicial = sc.nextInt();
                        	Djikstra.djikstra(verticeInicial,numVertices, listaAdjacencia, mapaArestas);
                        }
                        break;
                    }
                    default: {
                        System.out.println("Fim do programa.");
                        break;
                    }
                }
            } while (opcao > 0 && opcao < 10);
            System.out.println("\n");
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter string para número: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ------------------------- MÉTODOS -------------------------------------

    // Método para imprimir a lista de adjacência
    public static void imprimirListaAdjacencia() {
        System.out.println("Lista de Adjacência:");

        for (int vertice : listaAdjacencia.keySet()) {
            List<Integer> adjacentes = listaAdjacencia.get(vertice);
            Collections.sort(adjacentes);
            System.out.println(vertice + " => " + adjacentes);
        }
    }

    // Método para imprimir a matriz de adjacência
    public static void imprimirMatrizAdjacencia(int numVertices) {
        System.out.println("Matriz de Adjacência:");
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Método para mostrar o grau de cada vértice
    public static void mostrarGrauGrafo() {
        System.out.println("Grau de cada vértice:");

        for (int vertice : listaAdjacencia.keySet()) {
            int grau = listaAdjacencia.get(vertice).size();
            System.out.println("Vértice " + vertice + " tem grau " + grau);
        }
    }

    // Método para mostrar o grau médio do grafo
    public static void mostrarGrauMedio(int numVertices) {
        double somaGraus = 0;

        for (int vertice : listaAdjacencia.keySet()) {
            somaGraus += listaAdjacencia.get(vertice).size();
        }

        double grauMedio = somaGraus / numVertices;
        System.out.println("Grau médio do grafo: " + grauMedio);
    }

    // Método para mostrar a distribuição empírica dos graus dos vértices
    public static void mostrarDistribuicaoEmpirica(int numArestas, int numVertices) {
        int[] arestas = new int[numVertices]; 
        
        Arrays.fill(arestas, 0);
       
        
        Iterator<Integer> iterator = listaAdjacencia.keySet().iterator();
        while (iterator.hasNext()) {
            Integer chave = iterator.next();
            int grau = listaAdjacencia.get(chave).size();
            
            // Atualiza o array arestas com o grau encontrado
            if (grau < arestas.length) {
                arestas[grau-1]++;
            } else {
                System.out.println("Grau " + grau + " fora do intervalo esperado.");
            }
        }
        
        // Imprime a distribuição dos graus
        for (int j = 0; j < arestas.length; j++) {
        	double prop = ((double)arestas[j])/numArestas;
            System.out.println("Distribuição Empírica com grau " + (j+1) + ": " + prop);
        }
    }

    
    //Método para retornar o número de vértices do grafo
    public static int getVertices(List<String> linhas) {
        // Verifica se a primeira linha contém um número válido
        if (!linhas.isEmpty()) {
            try {
                return Integer.parseInt(linhas.get(0).trim());
            } catch (NumberFormatException e) {
                System.out.println("Erro ao converter a primeira linha para número: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return 0;
    }
}
