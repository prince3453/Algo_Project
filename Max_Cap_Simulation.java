import java.util.List;

public class Max_Cap_Simulation {
    Max_Cap_Simulation(){}
    private int getTotalEdges(Graph graph) {
        return graph.vertices.values().stream().mapToInt(v -> v.neighbors.size()).sum();
    }

    private double calculateMeanLength(int totalLength, int paths) {
        return paths == 0 ? 0 : (double) totalLength / paths;
    }

    private double calculateMeanProportionalLength(double meanLength, int maxLength) {
        return maxLength == 0 ? 0 : meanLength / maxLength;
    }

    private void printStatistics(int maxFlow, int paths, double meanLength, double meanProportionalLength, int totalEdges) {
        System.out.println("Maximum Flow: " + maxFlow);
        System.out.println("Paths: " + paths);
        System.out.println("Mean Length: " + meanLength);
        System.out.println("Mean Proportional Length: " + meanProportionalLength);
        System.out.println("Total Edges: " + totalEdges);
    }
    public Result Max_Cap_Simulation_run(Graph graph, Vertex source, Vertex sink, String type) {
        int total_Edges = getTotalEdges(graph);
        List<Vertex> augmentedPath = MaxCapSimulation.maxCap_Dijkstra(graph, source, sink);

        int paths = 0;
        int totalLength = 0;
        int maxLength = 0;
        int maxFlow = 0;
        Ford_Fulkerson fordFulkerson = new Ford_Fulkerson();

        while (augmentedPath.size() > 1) {
            paths++;
            totalLength += augmentedPath.size();
            maxLength = Math.max(maxLength, augmentedPath.size());

            int minCapacity = fordFulkerson.findMinCap(augmentedPath);
            fordFulkerson.Residual_Graph(augmentedPath, minCapacity);
            maxFlow += minCapacity;

            augmentedPath = MaxCapSimulation.maxCap_Dijkstra(graph, source, sink);
        }

        double ML = calculateMeanLength(totalLength, paths);
        double MPL = calculateMeanProportionalLength(ML, maxLength);

        printStatistics(maxFlow, paths, ML, MPL, total_Edges);
        return new Result(type, maxFlow, paths, ML, MPL, total_Edges);
    }
}
