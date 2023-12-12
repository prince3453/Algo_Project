import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class Graph {
    Map<Integer, Vertex> vertices = new HashMap<>();
    Graph(){};
    Graph(Graph other) {
        for (Map.Entry<Integer, Vertex> entry : other.vertices.entrySet()) {
            int id = entry.getKey();
            Vertex original_v = entry.getValue();
            Vertex new_v = new Vertex(id);
            this.vertices.put(id, new_v);

            for (Map.Entry<Vertex, Integer> main_neighbour : original_v.neighbors.entrySet()) {
                Vertex originalNeighbor = main_neighbour.getKey();
                int capacity = main_neighbour.getValue();
                Vertex Neighbor = this.vertices.computeIfAbsent(originalNeighbor.id, Vertex::new);
                new_v.neighbors.put(Neighbor, capacity);
            }
        }
    }

    void addEdge(int uid, int vid, int capacity) {
        Vertex u = vertices.computeIfAbsent(uid, Vertex::new);
        Vertex v = vertices.computeIfAbsent(vid, Vertex::new);
        u.neighbors.put(v, capacity);
    }

    private List<Vertex> get_Vertices_With_Outgoing_Edges(Map<?, Vertex> v) {
        return v.values().stream()
                .filter(vertex -> Outgoing_Edges(vertex))
                .collect(Collectors.toList());
    }

    private boolean Outgoing_Edges(Vertex v) {
        return !v.neighbors.isEmpty();
    }
    Vertex Get_random_vertex() {
        List<Vertex> eligibleVertices = get_Vertices_With_Outgoing_Edges(vertices);
        if (eligibleVertices.isEmpty()) {
            return null;
        }
        return eligibleVertices.get(new Random().nextInt(eligibleVertices.size()));
    }
}

class Vertex {
    int id;
    Map<Vertex, Integer> neighbors = new HashMap<>();

    Vertex(int id) {
        this.id = id;
    }
}

public class Generate_result {
    static Graph read_CSV_Graph_File(String fileName) throws IOException {
        Graph g = new Graph();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int uVerticeId = Integer.parseInt(parts[0]);
                for (int i = 1; i < parts.length; i++) {
                    String[] edge = parts[i].split(":");
                    int vVerticeId = Integer.parseInt(edge[0]);
                    int Cap = Integer.parseInt(edge[1]);
                    g.addEdge(uVerticeId, vVerticeId, Cap);
                }
            }
        }
        return g;
    }

    static Vertex Find_Longest_BFS_Path(Vertex source) {
        Map<Vertex, Integer> d = new HashMap<>();
        Queue<Vertex> Que = new LinkedList<>();
        Que.offer(source);
        d.put(source, 0);

        Vertex fNode = source;
        int maxDistance = 0;
        while (!Que.isEmpty()) {
            Vertex current = Que.poll();
            for (Vertex neighbor : current.neighbors.keySet()) {
                if (!d.containsKey(neighbor)) {
                    d.put(neighbor, d.get(current) + 1);
                    Que.offer(neighbor);

                    if (d.get(neighbor) > maxDistance) {
                        maxDistance = d.get(neighbor);
                        fNode = neighbor;

                    }
                }
            }
        }
        System.out.println("Longest path length using BFS : "+d.get(fNode));
        return fNode;
    }

    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        double r = Double.parseDouble(args[1]);
        int upperCap = Integer.parseInt(args[2]);
        String fileName = "graph_adjacency_list_"+n+"_"+r+"_"+upperCap+".csv";
        Graph main_graph = read_CSV_Graph_File(fileName);

        Vertex source = main_graph.Get_random_vertex();
        Vertex sink = Find_Longest_BFS_Path(source);

        List<Result> results = new ArrayList<>();

        Graph graph_SAP =  read_CSV_Graph_File(fileName);

        Vertex source_SAP = graph_SAP.vertices.get(source.id);
        Vertex sink_SAP = graph_SAP.vertices.get(sink.id);

        SAP_Simulation rss = new SAP_Simulation();
        Result SAP_Result = rss.SAP_Simulation(graph_SAP, source_SAP, sink_SAP, "SAP");
        results.add(SAP_Result);
        System.out.println();

        Graph graph_DFS =  read_CSV_Graph_File(fileName);

        Vertex source_DFS = graph_DFS.vertices.get(source.id);
        Vertex sink_DFS = graph_DFS.vertices.get(sink.id);

        Result DFS_Like = new DFS_Like_Simulation().DFS_Like_Simulation_run(graph_DFS, source_DFS, sink_DFS, "DFS-Like");
        results.add(DFS_Like);
        System.out.println();

        Graph graph_Max_Cap =  read_CSV_Graph_File(fileName);

        Vertex Source_MaxCap = graph_Max_Cap.vertices.get(source.id);
        Vertex Sink_MaxCap = graph_Max_Cap.vertices.get(sink.id);
        Result Max_Cap = new Max_Cap_Simulation().Max_Cap_Simulation_run(graph_Max_Cap, Source_MaxCap, Sink_MaxCap, "Max-Cap");
        results.add(Max_Cap);
        System.out.println();

        Graph graph_random =  read_CSV_Graph_File(fileName);

        Vertex source_random = graph_random.vertices.get(source.id);
        Vertex sink_random = graph_random.vertices.get(sink.id);
        Result Random_result = new Random_Simulation().Random_Simulation_run(graph_random, source_random, sink_random, "Random");
        results.add(Random_result);
        System.out.println();

        // Display
        System.out.println();
        results.forEach(result -> {
            String s = result.toFormattedString(200, 0.3, 50);
            System.out.println(s);
        });
    }

}
