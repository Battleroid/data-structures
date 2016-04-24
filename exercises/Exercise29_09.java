import Exercise28_01Extra.WeightedEdge;
import Exercise28_01Extra.WeightedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by casey on 2016-04-24.
 */
public class Exercise29_09 {
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
        Scanner in = new Scanner("https://liveexample.pearsoncmg.com/test/WeightedGraphSample.txt");
        System.out.println("Enter URL: ");
        URL url = null;
        try {
            url = new URL(in.nextLine());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // init graphs
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        ArrayList<Integer> verts = new ArrayList<>();
        ArrayList<String> lines = new ArrayList<>();

        // read contents of URL
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

        // parse
        int size = Integer.parseInt(lines.get(0));
        for (int l = 1; l < size; l++) {
            // first split by pipe
            String[] portions = lines.get(l).split("\\|");

            for (String portion : portions) {
                String[] e = portion.split("\\s*,\\s*");

                int u = Integer.parseInt(e[0].trim());
                int v = Integer.parseInt(e[1].trim());
                double weight = Double.parseDouble(e[2].trim());
                WeightedEdge we = new WeightedEdge(u, v, weight);
                WeightedEdge wei = new WeightedEdge(v, u, weight);

                if (!verts.contains(u)) verts.add(u);
                if (!verts.contains(v)) verts.add(v);
                edges.add(we);
                edges.add(wei);
            }
        }

        // create graph
        WeightedGraph<Integer> graph = new WeightedGraph<>(verts, edges);
        System.out.println("The number of vertices is " + graph.getVertices().size());
        graph.printWeightedEdges();
        WeightedGraph.MST mst = graph.getMinimumSpanningTree();
        System.out.println("Total weight in MST is " + mst.getTotalWeight());
        mst.printTree();
    }
}
