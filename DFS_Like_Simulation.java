import java.util.List;

public class DFS_Like_Simulation {

    private int calculateTotalEdges(Graph graph) {
        return graph.vertices.values().stream().mapToInt(v -> v.neighbors.size()).sum();
    }
    private void printSimulationResults(String title, int maxFlow, int paths, double ML,
                                        double MPL, int total_Edges) {
        System.out.println(title);
        System.out.println("Maximum Flow: " + maxFlow);
        System.out.println("Paths: " + paths);
        System.out.println("Mean Length: " + ML);
        System.out.println("Mean Proportional Length: " + MPL);
        System.out.println("Total Edges: " + total_Edges);
    }
    public Result DFS_Like_Simulation_run(Graph graph, Vertex source, Vertex sink, String type) {
        int totalEdges = calculateTotalEdges(graph);

        List<Vertex> augmentingPath = DFS_Like_Dijkstra.dfs_Like_Dijkstra(graph, source, sink);

        int paths = 0;
        int totalLength = 0;
        int maxLength = 0;
        int maxFlow = 0;

        Ford_Fulkerson f = new Ford_Fulkerson();

        while (augmentingPath.size() > 1) {
            paths++;
            totalLength += augmentingPath.size();
            maxLength = Math.max(maxLength, augmentingPath.size());

            int minCapacity = f.findMinCap(augmentingPath);
            f.Residual_Graph(augmentingPath, minCapacity);
            maxFlow += minCapacity;

            augmentingPath = DFS_Like_Dijkstra.dfs_Like_Dijkstra(graph, source, sink);
        }

        double ML = (double) totalLength / paths;
        double MPL = ML / maxLength;

        printSimulationResults("DFS Like Results", maxFlow, paths, ML, MPL, totalEdges);

        return new Result(type, maxFlow, paths, ML, MPL, totalEdges);
    }
}
