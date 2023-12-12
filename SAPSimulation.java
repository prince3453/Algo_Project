import java.util.*;

public class SAPSimulation {


    private static void initialize_Distances_And_Predecessors(Collection<Vertex> vertices,
                                                              Map<Vertex, Integer> d,
                                                              Map<Vertex, Vertex> pi) {
        int maxDistance = Integer.MAX_VALUE / 2;
        vertices.forEach(vertex -> {
            d.put(vertex, maxDistance);
            pi.put(vertex, null);
        });
    }

    private static void update_Distances_And_Predecessors(Vertex currentVertex,
                                                          Map<Vertex, Integer> d,
                                                          Map<Vertex, Vertex> pi,
                                                          PriorityQueue<Vertex> queue) {
        for (Map.Entry<Vertex, Integer> entry : currentVertex.neighbors.entrySet()) {
            Vertex neighbor = entry.getKey();
            int Cap = entry.getValue();

            if (Cap > 0) {
                int alternative_Distance = 1;
                Integer cDistance = d.get(neighbor);

                if (cDistance == null || alternative_Distance < cDistance) {
                    d.put(neighbor, alternative_Distance);
                    pi.put(neighbor, currentVertex);
                    queue.add(neighbor);
                }
            }
        }
    }

    private static boolean isSinkReachable(Map<Vertex, Integer> distances, Vertex sink) {
        Integer sinkDistance = distances.get(sink);
        return sinkDistance != null && sinkDistance != Integer.MAX_VALUE / 2;
    }

    private static List<Vertex> constructPath(Map<Vertex, Vertex> pi, Vertex sink) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex currentV = sink; currentV != null; currentV = pi.get(currentV)) {
            path.add(currentV);
        }
        Collections.reverse(path);
        return path;
    }
    static List<Vertex> dijkstra(Graph graph, Vertex source, Vertex sink) {
        if (graph == null || source == null || sink == null) {
            throw new IllegalArgumentException("Input arguments cannot be null.");
        }

        Map<Vertex, Vertex> pi = new HashMap<>();
        Map<Vertex, Integer> d = new HashMap<>();
        PriorityQueue<Vertex> Que = new PriorityQueue<>(Comparator.comparingInt(d::get));

        initialize_Distances_And_Predecessors(graph.vertices.values(), d, pi);

        d.put(source, 0);
        Que.add(source);

        while (!Que.isEmpty()) {
            Vertex currentVertex = Que.poll();
            if (currentVertex.id == sink.id) {
                sink = currentVertex;
                break;
            }
            update_Distances_And_Predecessors(currentVertex, d, pi, Que);
        }

        if (!isSinkReachable(d, sink)) {
            return Collections.emptyList();
        }

        return constructPath(pi, sink);
    }

}
