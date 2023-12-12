public class Result {
    private String Simulation_algotrithm_name;  // Identifier for the simulation
    private int maxFlow;
    private int paths;
    private double ML;
    private double MPL;
    private int total_Edges;

    Result(){}

    // Constructor
    public Result(String Simulation_algorithm_name, int maxFlow, int paths, double ML, double MPL, int total_Edges) {
        this.Simulation_algotrithm_name = Simulation_algorithm_name;
        this.maxFlow = maxFlow;
        this.paths = paths;
        this.ML = ML;
        this.MPL = MPL;
        this.total_Edges = total_Edges;
    }

    public String toFormattedString(int n, double r, int upperCap) {
        return String.format("Algorithm: %s | N: %d | R: %.2f | Cap: %d | Paths: %s | ML: %.2f | MPL: %.2f | Edges: %s | Flow: %s",
                Simulation_algotrithm_name, n, r, upperCap, paths, ML, MPL, total_Edges, maxFlow);
    }

}
