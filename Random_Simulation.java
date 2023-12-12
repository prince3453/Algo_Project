import java.util.List;

public class Random_Simulation {

    Random_Simulation(){}

    private int calculateTotalEdges(Graph graph) {
        return graph.vertices.values().stream().mapToInt(v -> v.neighbors.size()).sum();
    }

    private List<Vertex> findShortestAugmentingPath(Graph graph, Vertex source, Vertex sink) {
        return RandomSimulation.randomDijkstra(graph, source, sink);
    }

    private void printStatistics(int maxFlow, int paths, double meanLength, double meanProportionalLength, int totalEdges) {
        System.out.println("Maximum Flow: " + maxFlow);
        System.out.println("Paths: " + paths);
        System.out.println("Mean Length: " + meanLength);
        System.out.println("Mean Proportional Length: " + meanProportionalLength);
        System.out.println("Total Edges: " + totalEdges);
    }
    public Result Random_Simulation_run(Graph graph, Vertex source, Vertex sink, String type) {
        int total_Edges = calculateTotalEdges(graph);

        // Find the shortest augmenting path
        List<Vertex> augmentingPath = findShortestAugmentingPath(graph, source, sink);


        // Statistics
        int paths = 0;
        int totalLength = 0;
        int maxLength = 0;

        // Run Ford-Fulkerson
        int maxFlow = 0;
        Ford_Fulkerson f = new Ford_Fulkerson();
        while (augmentingPath.size() > 1) {
            paths++;
            totalLength += augmentingPath.size();
            maxLength = Math.max(maxLength, augmentingPath.size());

            int minCapacity = f.findMinCap(augmentingPath);
            f.Residual_Graph(augmentingPath, minCapacity);
            maxFlow += minCapacity;

            augmentingPath = findShortestAugmentingPath(graph, source, sink);
        }

        double ML = (double) totalLength / paths;
        double MPL = ML / maxLength;

        printStatistics(maxFlow, paths, ML, MPL, total_Edges);

        return new Result(type, maxFlow, paths, ML, MPL, total_Edges);
    }
}

