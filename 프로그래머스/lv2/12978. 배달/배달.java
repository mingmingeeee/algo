import java.util.*;

class Solution {
    static class Node implements Comparable<Node>{
        int v, weight;
        Node next;
        
        public Node(int v, int weight, Node next) {
            this.v = v;
            this.weight = weight;
            this.next = next;
        }
        
        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }
    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        
        Node[] node = new Node[N + 1];
        for(int i = 0; i < road.length; i++) {
            int from = road[i][0];
            int to = road[i][1];
            int weight = road[i][2];
            
            // 양방향
            node[from] = new Node(to, weight, node[from]);
            node[to] = new Node(from, weight, node[to]);
        }
        
        answer = daijkstra(node, N, K, 1);

        return answer;
    }
    
    static int daijkstra(Node[] node, int N, int K, int start) {
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] D = new int[N + 1];
        boolean[] visited = new boolean[N + 1];
        
        Arrays.fill(D, Integer.MAX_VALUE);
        
        D[start] = 0;
        pq.offer(new Node(start, D[start], null));
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            
            if(visited[cur.v]) continue;
            visited[cur.v] = true;
            
            for(Node temp = node[cur.v]; temp != null; temp = temp.next) {
                if(!visited[temp.v] && D[temp.v] > D[cur.v] + temp.weight) {
                    D[temp.v] = D[cur.v] + temp.weight;
                    pq.offer(new Node(temp.v, D[temp.v], null));
                }
            }
        }
        
        int answer = 0;
        for(int i = 1; i < N + 1; i++) {
            if(D[i] <= K) {
                answer++;
            }
        }
        
        return answer;
        
    }
}

// 각 마을에 1부터 N까지 번호 하나씩 부여되어 있음
// 양방향으로 통행할 수 있는 도로로 연결되어 있음
// 음식 주문을 받을 수 있는 마을의 개수 return 