import java.util.*;
import java.io.*;

class Solution {
    
    static class Node implements Comparable<Node>{
        int to, weight;
        Node next;
        public Node(int to, int weight, Node next) {
            this.to = to;
            this.weight = weight;
            this.next = next;
        }
        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        
        // n: 지점 갯수
        // s: 출발지점
        // a: a도착지점, b: b도착지점
        // fares[n][0]: 지점c, fares[n][1]: 지점d, fares[n][2]: 요금
        
        Node[] nodes = new Node[n + 1];
        
        for(int i = 0; i < fares.length; i++) {
            int from = fares[i][0];
            int to = fares[i][1];
            
            nodes[from] = new Node(to, fares[i][2], nodes[from]);
            nodes[to] = new Node(from, fares[i][2], nodes[to]);
        }
        
        int[] D1 = dijkstra(n, s, nodes);
        int[] D2 = dijkstra(n, a, nodes);
        int[] D3 = dijkstra(n, b, nodes);
        
        for(int i = 1; i <= n; i++) {
            answer = Math.min(answer, D1[i] + D2[i] + D3[i]);
        }
    
        
        return answer;
    }
    
    static int[] dijkstra(int n, int start, Node[] nodes) {
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] D = new int[n + 1];
        boolean[] visited = new boolean[n + 1];
        
        Arrays.fill(D, Integer.MAX_VALUE);
        D[start] = 0;
        
        pq.offer(new Node(start, D[start], null));
        
        while(!pq.isEmpty()) {
            
            Node cur = pq.poll();
            if(visited[cur.to]) continue;
            visited[cur.to] = true;
            
            for(Node node = nodes[cur.to]; node != null; node = node.next) {
                if(!visited[node.to] && D[node.to] > D[cur.to] + node.weight) {
                    D[node.to] = D[cur.to] + node.weight;
                    pq.offer(new Node(node.to, D[node.to], null));
                }
            }
            
        }
        
        return D;
        
    }
}

// 무지 => 어피치 (택시 합승)