import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class 백준_1707_이분그래프 {

    private static int K, V, E;
    private static boolean isEven;
    private static boolean[] visited;
    private static int[] groupColor;
    private static ArrayList<Integer>[] adjList;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        K = Integer.parseInt(st.nextToken());

        for(int t = 0; t < K; t++) {
            st = new StringTokenizer(in.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            visited = new boolean[V + 1];
            groupColor = new int[V + 1];
            adjList = new ArrayList[V + 1];

            for(int i = 0; i <= V; i++) {
                adjList[i] = new ArrayList<>();
            }

            for(int i = 0; i < E; i++) {
                st = new StringTokenizer(in.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                adjList[from].add(to);
                adjList[to].add(from);
            }

            for(int i = 1; i <= V; i++) {
                isEven = true;
                dfs(i);
                if(!isEven)
                    break;
            }

            if(isEven) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }

    }

    private static void dfs(int v) {

        visited[v] = true; // 이분 그래프 위해 모든 정점 방문하였는지 확인하기 위함

        for(int i : adjList[v]) {
            if(!visited[i]) {
                groupColor[i] = (groupColor[v] + 1) % 2;
                dfs(i);
            } else if(groupColor[v] == groupColor[i]) {
                isEven = false;
                break;
            }
        }

    }

}


// 이분 그래프
// 각 집합에 속한 정점끼리는 서로 인접하지 않도록 분할하는 그래프
// 한 노드와 연결되는 다른 노드들은 다른 색으로 칠해져야 함

// a [1] 2
// b [2] 1 3 4
// c [3] 2 4
// d [4] 3 2

// 이분 그래프에 존재하는 모든 사이클은 길이(사이클에 포함된 정점의 수)가 짝수, 그 역도 성립함