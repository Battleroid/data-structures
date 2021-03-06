import Exercise28_01Extra.WeightedEdge;
import Exercise28_01Extra.WeightedGraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by casey on 2016-04-26.
 */
public class Exercise29_11 {
    public static void main(String[] args) {
//        Scanner in = new Scanner("https://liveexample.pearsoncmg.com/test/WeightedGraphSample2.txt");
        Scanner in = new Scanner(System.in);
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
        lines.remove(0);
        for (String line : lines) {
            String[] portions = line.split("\\|");

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

        Collections.sort(verts);
        Collections.sort(edges);

        // create graph
        WeightedGraph<Integer> graph = new WeightedGraph<>(verts, edges);
        System.out.println("Enter two vertices (integer indexes): "); // root for dfs
        int root = in.nextInt();
        int toidx = in.nextInt();
        System.out.println("The number of vertices is " + graph.getVertices().size());
        graph.printWeightedEdges();
        WeightedGraph.SearchTree st = graph.dfs(root);
        st.printPath(toidx);
    }
}
