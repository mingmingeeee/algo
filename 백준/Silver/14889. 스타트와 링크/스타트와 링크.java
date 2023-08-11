import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class Main {

    private static int N, ans = Integer.MAX_VALUE;
    private static int[][] S;
    private static boolean[] visited;


    private static int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        visited = new boolean[N];

        S = new int[N][N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                S[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        make_start(0, 0);

        System.out.println(ans);
    }

    private static void make_start(int start, int cnt) {

        if(cnt == N / 2) {
            int starts_sum = 0, links_sum = 0;
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(visited[i] && visited[j]) { // 스타트
                        starts_sum += S[i][j];
                    } else if(!visited[i] && !visited[j]) { // 링크
                        links_sum += S[i][j];
                    }
                }
            }

            int res = Math.abs(starts_sum - links_sum);

            ans = Math.min(ans, res);
            return;
        }

        for(int i = start; i < N; i++) {
            visited[i] = true;
            make_start(i + 1, cnt + 1);
            visited[i] = false;
        }

    }



}

// [2 1 1 2]
// [1 1 2 1 3 1]
// [3 3 0 0]

// [1 2 2 2]
// [2 1 3 1 1 4]
// [3 3 0 0]
