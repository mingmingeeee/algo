//public class 프로그래머스_부대복귀 {
//
//    private static class Node implements Comparable<Node> {
//        int n, cnt;
//        Node next;
//
//        public Node(int n, int cnt, Node next) {
//            this.n = n;
//            this.cnt = cnt;
//            this.next = next;
//        }
//
//        @Override
//        public int compareTo(Node o) {
//            return this.cnt - o.cnt;
//        }
//    }
//
//    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
//        int[] answer = new int[sources.length];
//
//        Node[] nodes = new Node[n + 1];
//
//        for(int i = 0; i < roads.length; i++) {
//            int from = roads[i][0];
//            int to = roads[i][1];
//
//            nodes[from] = new Node(to, 0, nodes[from]);
//            nodes[to] = new Node(from, 0, nodes[to]);
//        }
//
//
//        int[] D = daijkstra(n, nodes, destination, 0);
//
//        for(int i = 0; i < sources.length; i++) {
//            int num = sources[i];
//            answer[i] = D[num];
//        }
//
//
//        return answer;
//    }
//
//    private static int[] daijkstra(int n, Node[] nodes, int destination, int depth) {
//
//
//        PriorityQueue<Integer> queue = new PriorityQueue<>();
//        int[] D = new int[n + 1];
//        Arrays.fill(D, Integer.MAX_VALUE);
//
//        D[destination] = 0;
//        queue.offer(destination);
//
//
//        while(!queue.isEmpty()) {
//
//            int cur = queue.poll();
//
//
//            for(Node temp = nodes[cur]; temp != null; temp = temp.next) {
//                if(D[temp.n] > D[cur] + 1) {
//                    D[temp.n] = D[cur] + 1;
//                    queue.offer(temp.n);
//                }
//
//            }
//        }
//
//        for(int i = 1; i < n + 1; i++) {
//            if(D[i] == Integer.MAX_VALUE)
//                D[i] = -1;
//        }
//
//
//        return D;
//
//    }
//
//}
