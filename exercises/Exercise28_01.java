import Exercise28_01Extra.Edge;
import Exercise28_01Extra.UnweightedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by casey on 2016-04-17.
 */
public class Exercise28_01 {
    public static void main(String[] args) {
        // get url
        Scanner in = new Scanner(System.in);
        System.out.print("Enter URL: ");
        URL url = null;
        try {
            url = new URL(in.nextLine());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // init edges/graph
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Integer> verts = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();

        // read contents
        try {
            URLConnection uc = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            br.close();
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
        UnweightedGraph<Integer> graph = new UnweightedGraph<>(verts, edges);
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