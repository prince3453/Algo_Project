import java.util.List;

public class Ford_Fulkerson {
     public int findMinCap(List<Vertex> path) {
        int minCap = Integer.MAX_VALUE;

        for (int i = 0; i < path.size() - 1; i++) {
            Vertex u = path.get(i);
            Vertex v = path.get(i + 1);

            int capacity = u.neighbors.get(v);
            minCap = Math.min(minCap, capacity);
        }
        return minCap;
    }

    public void Residual_Graph(List<Vertex> path, int minCap) {
        for (int i = 0; i < path.size() - 1; i++) {
            Vertex u = path.get(i);
            Vertex v = path.get(i + 1);

            int originalCap = u.neighbors.get(v);
            int forward = Math.min(originalCap, minCap);

            u.neighbors.put(v, Math.max(0, originalCap - forward));
            if (v.neighbors.containsKey(u)) {
                v.neighbors.put(u, v.neighbors.get(u) - forward);
            } else {
                v.neighbors.put(u, forward);
            }
        }
    }
}
