import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        int n = Integer.parseInt(args[0]);
        double r = Double.parseDouble(args[1]);
        int upperCap = Integer.parseInt(args[2]);
        String OutputFile = "graph_adjacency_list_"+n+"_"+r+"_"+upperCap+".csv";
        Random_Graph_Generate rgg = new Random_Graph_Generate();
        rgg.Generate_Source_and_sink_Graph(n, r, upperCap, OutputFile);
        Generate_result.main(new String[]{String.valueOf(n), String.valueOf(r), String.valueOf(upperCap)});
    }
}