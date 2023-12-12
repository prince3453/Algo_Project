import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Random_Graph_Generate {
    //Creating the Class for the Vertex which represent all the edge
    static class Vertex {
        static int counter = 1;
        int id;
        double x;
        double y;

        Vertex() {
            this.id = counter++;
            this.x = Math.random();
            this.y = Math.random();
        }

        //Writing in the CSV File
        String CSVFileWriter(Map<Vertex, Integer> neighbors) {
            StringBuilder builder = new StringBuilder();
            builder.append(id).append(",");
            for (Vertex neighbor : neighbors.keySet()) {
                builder.append(neighbor.id).append(":").append(neighbors.get(neighbor)).append(",");
            }
            // Remove the trailing comma
            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }
    }

    //Creating stating Class for the Edge which represent all the edge
    static class Edge {
        Vertex u;
        Vertex v;
        int capacity;

        Edge(Vertex u, Vertex v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge edge = (Edge) obj;
            return (u.equals(edge.u) && v.equals(edge.v)) || (u.equals(edge.v) && v.equals(edge.u));
        }

        @Override
        public int hashCode() {
            return u.hashCode() + v.hashCode();
        }
    }

    public void Generate_Source_and_sink_Graph(int n, double r, int upperCap, String OutputFile) {
        Set<Vertex> vertices = new HashSet<>();
        Set<Edge> edges = new HashSet<>();

        for (int i = 0; i < n; i++) {
            Vertex vertex = new Vertex();
            vertices.add(vertex);
        }

        for (Vertex u : vertices) {
            for (Vertex v : vertices) {
                if (!u.equals(v) && Math.pow(u.x - v.x, 2) + Math.pow(u.y - v.y, 2) <= Math.pow(r, 2)) {
                    double randomVariable = Math.random();
                    Edge edge;
                    if (randomVariable < 0.5) {
                        edge = new Edge(u, v);
                    } else {
                        edge = new Edge(v, u);
                    }
                    if (!edges.contains(edge)) {
                        edges.add(edge);
                    }
                }
            }
        }

        for (Edge edge : edges) {
            edge.capacity = new Random().nextInt(upperCap) + 1;
        }

        // Creating the adjacency list to represent the Graph
        Map<Vertex, Map<Vertex, Integer>> adjacencyList = new HashMap<>();
        for (Edge edge : edges) {
            Map<Vertex, Integer> neighbors = adjacencyList.get(edge.u);
            if (neighbors == null) {
                neighbors = new HashMap<>();
                adjacencyList.put(edge.u, neighbors);
            }
            neighbors.put(edge.v, edge.capacity);
        }

        // Write graph into the CSV file using adjecency list
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OutputFile))) {
            for (Vertex vertex : adjacencyList.keySet()) {
                bw.write(vertex.CSVFileWriter(adjacencyList.get(vertex)));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
