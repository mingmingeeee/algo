import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 도로포장 하지 않을 때부터 포장했을 때의 거리 비용 구하기
// => 2차원 D[] 배열
// D[도시 번호][도로포장 개수]
// 다익스트라 + DP

public class 백준_1162_도로포장 {

    private static int N, M, K;
    private static long[][] D;
    private static Node[] adjList;

    private static class Node implements Comparable<Node> {
        int v, cnt;
        long weight;
        Node next;

        public Node(int v, int cnt, long weight, Node next) {
            this.v = v;
            this.cnt = cnt;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            if(this.weight > o.weight) return 1;
            else if(this.weight < o.weight) return -1;
            return 0;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        adjList = new Node[N + 1];

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            adjList[v1] = new Node(v2, 0, weight, adjList[v1]);
            adjList[v2] = new Node(v1, 0, weight, adjList[v2]);
        }

        dijkstra();

        long answer = Long.MAX_VALUE;
        for(int i = 0; i <= K; i++) {
            if(answer > D[N][i])
                answer = D[N][i];
        }

        System.out.println(answer);

    }

    private static void dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        D = new long[N + 1][K + 1];

        for(int i = 0; i <= N; i++) {
            Arrays.fill(D[i], Long.MAX_VALUE);
        }

        // 초기화
        D[1][0] = 0;
        pq.offer(new Node(1, 0, 0, null));

        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            if(cur.weight > D[cur.v][cur.cnt]) continue;

            for(Node temp = adjList[cur.v]; temp != null; temp = temp.next) {


                // 포장 X
                if(D[temp.v][cur.cnt] > D[cur.v][cur.cnt] + temp.weight) {
                    D[temp.v][cur.cnt] = D[cur.v][cur.cnt] + temp.weight;
                    pq.offer(new Node(temp.v, cur.cnt, D[temp.v][cur.cnt], null));
                }

                // 포장 O
                if(cur.cnt < K && D[temp.v][cur.cnt + 1] > D[cur.v][cur.cnt]) {
                    D[temp.v][cur.cnt + 1] = D[cur.v][cur.cnt];
                    pq.offer(new Node(temp.v, cur.cnt + 1, D[temp.v][cur.cnt + 1], null));
                }
            }

        }
    }

}

// K개의 도로 포장
// 서울 => 포천 시간 단축

// 최소 시간이 걸리도록 하는 K개 이하의 도로 포장
// => 이미 있는 도로만 포장 가능, 포장하게 되면 도로 지나는데 걸리는 시간 0 됨