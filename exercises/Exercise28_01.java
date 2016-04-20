import Exercise28_01Extra.Edge;
import Exercise28_01Extra.UnweightedGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created by casey on 2016-04-17.
 */
public class Exercise28_01 {
    public static void main(String[] args) {
        // check args
        if (args.length < 1) {
            System.out.printf("Usage:\n" +
                    "\tExercise28_01 <input>");
            System.exit(1);
        }

        // init edges/graph
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Integer> verts = new ArrayList<>();
        UnweightedGraph<Integer> graph;
        ArrayList<String> lines = new ArrayList<>();

        // load file contents
        try {
            Stream<String> stream = Files.lines(Paths.get(args[0]));
            stream.forEach(line -> lines.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // start parsing
        int size = Integer.parseInt(lines.get(0));
        for (int l = 1; l <= size; l++) {
            String line = lines.get(l);

            // to int[]
            String[] nLine = line.split(" ");
            int[] nums = new int[nLine.length];
            for (int i = 0; i < nums.length; i++) {
                nums[i] = Integer.parseInt(nLine[i]);
            }

            // add to edges & vertex
            verts.add(nums[0]);
            for (int i = 1; i < nums.length; i++) {
                edges.add(new Edge(nums[0], nums[i]));
            }
        }

        // create graph
        graph = new UnweightedGraph<>(verts, edges);
        UnweightedGraph<Integer>.SearchTree st = graph.dfs(0);
        System.out.println("The number of vertices is " + graph.getSize());
        graph.printEdges();

        // check
        if (graph.getSize() == st.getNumberOfVerticesFound())
            System.out.println("The graph is connected");
        else
            System.out.println("The graph is not connected");
    }
}