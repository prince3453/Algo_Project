import java.util.*;

public class DFS_Like_Dijkstra {

    private static void initialize_Distances_And_Predecessors(Collection<Vertex> vertices,
                                                              Map<Vertex, Integer> d,
                                                              Map<Vertex, Vertex> pi) {
        int maxDistance = Integer.MAX_VALUE / 2;
        vertices.forEach(vertex -> {
            d.put(vertex, maxDistance);
            pi.put(vertex, null);
        });
    }

    private static void updateSinkIfReached(Vertex sink, Vertex currentV) {
        if (currentV.id == sink.id) {
            sink = currentV;
        }
    }

    private static boolean isSinkReachable(Map<Vertex, Integer> d, Vertex sink) {
        Integer sinkDistance = d.get(sink);
        return sinkDistance != null && sinkDistance != Integer.MAX_VALUE / 2;
    }

    private static List<Vertex> constructPath(Map<Vertex, Vertex> pi, Vertex sink) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex cVertex = sink; cVertex != null; cVertex = pi.get(cVertex)) {
            path.add(cVertex);
        }
        Collections.reverse(path);
        return path;
    }
    static List<Vertex> dfs_Like_Dijkstra(Graph graph, Vertex source, Vertex sink) {
        if (graph == null || source == null || sink == null) {
            throw new IllegalArgumentException("Input arguments cannot be null.");
        }

        Map<Vertex, Vertex> pi = new HashMap<>();
        Map<Vertex, Integer> d = new HashMap<>();
        PriorityQueue<Vertex> Que = new PriorityQueue<>(Comparator.comparingInt(d::get));

        initialize_Distances_And_Predecessors(graph.vertices.values(), d, pi);

        int Counter = 0;
        d.put(source, 0);
        Que.add(source);

        while (!Que.isEmpty()) {
            Vertex currentV = Que.poll();
            updateSinkIfReached(sink, currentV);

            for (Map.Entry<Vertex, Integer> entry : currentV.neighbors.entrySet()) {
                Vertex neighbor = entry.getKey();
                int cap = entry.getValue();

                if (cap > 0) {
                    Integer cDistance = d.get(neighbor);

                    if (cDistance == null || cDistance == Integer.MAX_VALUE / 2) {
                        d.put(neighbor, Counter--);
                        pi.put(neighbor, currentV);
                        Que.add(neighbor);
                        updateSinkIfReached(sink, neighbor);
                    }
                }
            }
        }

        if (!isSinkReachable(d, sink)) {
            return Collections.emptyList();
        }

        return constructPath(pi, sink);
    }
}
