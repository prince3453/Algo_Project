import java.util.List;

public class SAP_Simulation {
    SAP_Simulation() {}

    private int calculateTotalEdges(Graph graph) {
        return graph.vertices.values().stream().mapToInt(v -> v.neighbors.size()).sum();
    }

    private void printStatistics(int maxFlow, int paths, double ML, double MPL, int total_Edges) {
        System.out.println("Maximum Flow: " + maxFlow);
        System.out.println("Paths: " + paths);
        System.out.println("Mean Length: " + ML);
        System.out.println("Mean Proportional Length: " + MPL);
        System.out.println("Total Edges: " + total_Edges);
    }
    public Result SAP_Simulation(Graph graph, Vertex source, Vertex sink, String type) {
        int total_Edges = calculateTotalEdges(graph);

        List<Vertex> augmentingPath = SAPSimulation.dijkstra(graph, source, sink);

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

            augmentingPath = SAPSimulation.dijkstra(graph, source, sink);
        }

        double ML = (double) totalLength / paths;
        double MPL = ML / maxLength;

        printStatistics(maxFlow, paths, ML, MPL, total_Edges);

        return new Result(type, maxFlow, paths, ML, MPL, total_Edges);
    }

}
