import java.util.*;

public class RandomSimulation {

    private static void initializeDistancesAndPredecessors(Collection<Vertex> vertices, Map<Vertex, Integer> distances, Map<Vertex, Vertex> pi) {
        int maxDistance = Integer.MAX_VALUE / 2;
        for (Vertex vertex : vertices) {
            distances.put(vertex, maxDistance);
            pi.put(vertex, null);
        }
    }

    private static void exploreNeighbors(Graph graph, Vertex currentVertex, Map<Vertex, Integer> distances, Map<Vertex, Vertex> pi, PriorityQueue<Vertex> queue, Vertex sink) {
        for (Map.Entry<Vertex, Integer> entry : currentVertex.neighbors.entrySet()) {
            Vertex neighbor = entry.getKey();
            int capacity = entry.getValue();

            if (capacity > 0) {
                Integer cDistance = distances.get(neighbor);

                if (cDistance == null || cDistance == Integer.MAX_VALUE / 2) {
                    distances.put(neighbor, new Random().nextInt(Integer.MAX_VALUE / 2));
                    pi.put(neighbor, currentVertex);
                    queue.add(neighbor);

                    if (neighbor.id == sink.id) {
                        sink = neighbor;
                    }
                }
            }
        }
    }

    private static List<Vertex> constructPath(Vertex sink, Map<Vertex, Vertex> predecessors) {
        List<Vertex> path = new ArrayList<>();
        for (Vertex currentVertex = sink; currentVertex != null; currentVertex = predecessors.get(currentVertex)) {
            path.add(currentVertex);
        }
        Collections.reverse(path);
        return path;
    }
    static List<Vertex> randomDijkstra(Graph graph, Vertex source, Vertex sink) {

        if (graph == null || source == null || sink == null) {
            throw new IllegalArgumentException("Input arguments cannot be null.");
        }

        Map<Vertex, Vertex> pi = new HashMap<>();
        Map<Vertex, Integer> distances = new HashMap<>();
        PriorityQueue<Vertex> Que = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        initializeDistancesAndPredecessors(graph.vertices.values(), distances, pi);
        distances.put(source, 0);
        Que.add(source);

        while (!Que.isEmpty()) {
            Vertex currentV = Que.poll();
            if (currentV.id == sink.id) {
                sink = currentV;
            }

            exploreNeighbors(graph, currentV, distances, pi, Que, sink);
        }

        Integer sinkDistance = distances.get(sink);
        if (sinkDistance == null || sinkDistance == Integer.MAX_VALUE / 2) {
            return Collections.emptyList();
        }
        return constructPath(sink, pi);
    }
}
