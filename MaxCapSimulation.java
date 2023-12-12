import java.util.*;

public class MaxCapSimulation {

    static List<Vertex> getPathToSource(Vertex sink, Map<Vertex, Vertex> pi) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex currentVertex = sink; currentVertex != null; currentVertex = pi.get(currentVertex)) {
            path.add(currentVertex);
        }
        Collections.reverse(path);
        return path;
    }
    static List<Vertex> maxCap_Dijkstra(Graph graph, Vertex source, Vertex sink) {
        if (graph == null || source == null || sink == null) {
            throw new IllegalArgumentException("Input arguments cannot be null.");
        }

        Map<Vertex, Vertex> pi = new HashMap<>();
        Map<Vertex, Integer> maxCapacities = new HashMap<>();
        PriorityQueue<Vertex> queue = new PriorityQueue<>(Comparator.comparingInt(maxCapacities::get).reversed());

        for (Vertex vertex : graph.vertices.values()) {
            maxCapacities.put(vertex, Integer.MIN_VALUE);
            pi.put(vertex, null);
        }

        int maxCapacity = Integer.MIN_VALUE;
        maxCapacities.put(source, Integer.MAX_VALUE);
        queue.add(source);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            if (currentVertex.id == sink.id) {
                sink = currentVertex;
                break;
            }
            for (Map.Entry<Vertex, Integer> entry : currentVertex.neighbors.entrySet()) {
                Vertex neighbor = entry.getKey();
                int capacity = entry.getValue();

                if (capacity > 0) {
                    int maxCap = Math.min(maxCapacities.get(currentVertex), capacity);

                    Integer cap = maxCapacities.get(neighbor);
                    if (cap == null || maxCap > cap) {
                        maxCapacities.put(neighbor, maxCap);
                        pi.put(neighbor, currentVertex);
                        queue.add(neighbor);
                    }
                }
            }
        }

        Integer sinkCap = maxCapacities.get(sink);

        if (sinkCap == null || sinkCap == Integer.MIN_VALUE) {
            // The sink is not reachable from the source
            return Collections.emptyList();
        }

        maxCapacity = sinkCap;

        List<Vertex> path = getPathToSource(sink, pi);

        return path;
    }

}
