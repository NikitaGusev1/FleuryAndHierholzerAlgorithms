package GraphTheoryTask3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hierholzer {

    public static <V> List<V> getPath(Graph<V> graph) {
        List<V> odd = new ArrayList<>();
        for(V v : graph.vertexes) {
            int grad = graph.getNeighbors(v).size();
            if (grad % 2 == 1) {
                odd.add(v);
            }
        }
        List<V> way = new ArrayList<V>();
        V from = odd.size() == 2 ? odd.get(0) : graph.vertexes.iterator().next();
        V to = odd.size() == 2 ? odd.get(1) : from;
        int insertPosition = 0;
        while(graph.edges.size() > 0) {
            List w = findWay(graph, from, to);
            way.addAll(insertPosition, w);
            for(int i = 0; i < way.size(); i++) {
                V v = way.get(i);
                if (graph.vertexes.contains(v)) {
                    from = v;
                    to = v;
                    insertPosition = i;
                    break;
                }
            }
        }
        return way;
    }

    private static <V> List<V> findWay(Graph<V> graph, V from, V to) {
        List<V> way = new ArrayList<>();
        V current = from;
        do {
            way.add(current);
            Set<V> neighbors = graph.getNeighbors(current);
            V next = neighbors.iterator().next();
            graph.removeEdge(current, next);
            current = next;
        } while(current != to);
        if(!from.equals(to)) {
            way.add(to);
        }
        return way;
    }
    public static class Graph<V> {
   Set<V> vertexes = new HashSet<>();
   Map<V, Set<V>> edges = new HashMap<>();

   public void addEdge(V v1, V v2) {
       vertexes.add(v1);
       vertexes.add(v2);
       Set<V> e1 = edges.get(v1);
       if(e1 == null) {
           e1 = new HashSet<V>();
           edges.put(v1,e1);
       }
       e1.add(v2);
       Set<V> e2 = edges.get(v2);
       if(e2 == null) {
           e2 = new HashSet<V>();
           edges.put(v2,e2);
       }
       e2.add(v1);
   }

   public void removeEdge(V v1, V v2) {
       Set<V> e1 = edges.get(v1);
       e1.remove(v2);
       if(e1.isEmpty()) {
           edges.remove(v1);
           vertexes.remove(v1);
       }
       Set<V> e2 = edges.get(v2);
       e2.remove(v1);
       if(e2.isEmpty()) {
           edges.remove(v2);
           vertexes.remove(v2);
       }
   }

   public Set<V> getNeighbors(V v) {
       return edges.get(v);
   }
}
}