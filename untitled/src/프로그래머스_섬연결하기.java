//
//// 크루스칼 알고리즘 => "MST"
//public class 프로그래머스_섬연결하기 {
//    private static int[] parents;
//    private static Node[] edgeList;
//    private static int V;
//
//    private static class Node implements Comparable<Node>{
//        int from;
//        int to;
//        int weight;
//
//        public Node(int from, int to, int weight) {
//            this.from = from;
//            this.to = to;
//            this.weight = weight;
//        }
//
//        @Override
//        public int compareTo(Node o) {
//            return this.weight - o.weight;
//        }
//    }
//
//    private static void make() {
//        parents = new int[V + 1];
//        for(int i = 0; i < V + 1; i++) {
//            parents[i] = i;
//        }
//    }
//
//    private static int find(int a) {
//        if(parents[a] == a)
//            return a;
//
//        return parents[a] = find(parents[a]);
//    }
//
//    private static boolean union(int a, int b) {
//        int aRoot = find(a);
//        int bRoot = find(b);
//
//        if(aRoot != bRoot) {
//            parents[bRoot] = aRoot;
//            return true;
//        }
//
//
//        return false;
//
//    }
//
//    public int solution(int n, int[][] costs) {
//        int answer = 0;
//
//        V = n;
//
//        edgeList = new Node[costs.length];
//
//
//        for(int i = 0; i < costs.length; i++) {
//            int v1 = costs[i][0];
//            int v2 = costs[i][1];
//            int weight = costs[i][2];
//            edgeList[i] = new Node(v1, v2, weight);
//        }
//
//        make();
//
//        Arrays.sort(edgeList);
//
//        int cnt = 0;
//        int ans = 0;
//        for(Node node : edgeList) {
//
//            if(union(node.from, node.to)) {
//                ans += node.weight;
//                if(++cnt == V - 1) {
//                    break;
//                }
//            }
//
//        }
//
//
//        return ans;
//    }
//}
