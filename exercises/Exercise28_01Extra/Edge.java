package Exercise28_01Extra;

/**
 * Created by casey on 2016-04-10.
 */
public class Edge {
    int u;
    int v;

    public Edge(int u, int v) {
        this.u = u;
        this.v = v;
    }

    public boolean equals(Object o) {
        return u == ((Edge) o).u && v == ((Edge) o).v;
    }
}
