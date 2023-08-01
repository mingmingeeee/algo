import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};
    private static int[][] map;
    private static int[][] visited;
    private static int N, L, R, n, c;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        System.out.println(simulate());

    }

    private static int simulate() {

        int answer = 0;
        while(true) {
            int ans = 0;
            visited = new int[N][N];
            n = 1;
            //  1. 국경선 공유하는 인구 차이 L 이상, R 이하인지 체크
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j] != 0) continue;
                    c = 0;
                    open(i, j);
                    if(c > 0) n++;
                }
            }

            if(n == 1)
                return answer;

            for(int c = 1; c < n; c++) {
                int cnt = 0, sum = 0;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if(visited[i][j] == c) {
                            sum += map[i][j]; cnt++;
                        }
                    }
                }
                int res = sum / cnt;
                for(int i = 0; i < N; i++) {
                    for(int j = 0; j < N; j++) {
                        if(visited[i][j] == c)
                            map[i][j] = res;
                    }
                }
            }

//            for(int i = 0; i < N; i++) {
//
//                for (int j = 0; j < N; j++) {
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println("===================");

            answer++;
        }
    }


    // 국경선 열기
    private static void open(int x, int y) {

        for(int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(!isRange(nx, ny)) continue;
            if(visited[nx][ny] != 0) continue;

            int diff = Math.abs(map[x][y] - map[nx][ny]);
            if(diff >= L && diff <= R) {
                visited[nx][ny] = n;
                c++;
                open(nx, ny);
            }
        }
    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }

}