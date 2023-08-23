import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int N, M, K;
    private static int[][] map, smell, num_smell;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};
    private static Shark[] sharks;

    private static class Shark {
        int d;
        int[][] prior;

        public Shark(int d, int[][] prior) {
            this.d = d;
            this.prior = prior;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        map = new int[N][N];
        smell =  new int[N][N];
        num_smell = new int[N][N];
        sharks = new Shark[M + 1];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                int n = Integer.parseInt(st.nextToken());
                map[i][j] = n;
                if(n == 0) continue;
                sharks[n] = new Shark(0, null);
            }
        }

        st = new StringTokenizer(in.readLine());
        for(int i = 1; i <= M; i++) {
            sharks[i].d = Integer.parseInt(st.nextToken()) - 1;
        }

        for(int i = 1; i <= M; i++) {
            int[][] prior = new int[4][4];
            for(int n = 0; n < 4; n++) {
                st = new StringTokenizer(in.readLine());
                for(int d = 0; d < 4; d++) {
                    prior[n][d] = Integer.parseInt(st.nextToken()) - 1;
                }
            }
            sharks[i].prior = prior;
        }

        int ans = simulate();

        System.out.println(ans);

    }

    private static int simulate() {

        int t = 0;
        while(t < 1000) {
            // 1. 냄새 사라짐
            decrease();

            // 2. 냄새 뿌림
            spread();

            // 3. 상어 이동
            move();

            t++;

            // 4. 격자칸에 1번 상어만 남는지 확인
            if(check())
                return t;
        }

        return -1;
    }

    private static boolean check() {
        boolean isTrue = false;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++){
                if(map[i][j] > 1)
                    return false;
                if(map[i][j] == 1)
                    isTrue = true;
            }
        }
        return isTrue;
    }

    private static void decrease() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(smell[i][j] > 0) {
                    if(--smell[i][j] == 0)
                        num_smell[i][j] = 0;
                }
            }
        }
    }

    private static void spread() {
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if(map[i][j] == 0) continue;
                smell[i][j] = K;
                num_smell[i][j] = map[i][j];
            }
        }
    }

    private static void move() {
        int[][] new_map = new int[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j] == 0) continue;

                int n = map[i][j];
                int x = i, y = j;
                int[][] prior = sharks[n].prior;
                int dir = sharks[n].d;


                // 우선순위
                int[] list = prior_empty(new_map, prior, x, y, dir, n);
                if(list != null) {
                    if(isMove(new_map, list[0], list[1], n)) {
                        new_map[list[0]][list[1]] = n;
                        sharks[n].d = prior[dir][list[2]];
                    }
                } else {
                    list = prior_smell(new_map, prior, x, y, dir, n);
                    if(list != null) {
                        if(isMove(new_map, list[0], list[1], n)) {
                            new_map[list[0]][list[1]] = n;
                            sharks[n].d = prior[dir][list[2]];
                        }
                    }
                }

            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = new_map[i][j];
            }
        }
    }

    private static int[] prior_smell(int[][] newMap, int[][] prior, int x, int y, int dir, int n) {
        for(int d = 0; d < 4; d++) {
            int curX = x + dx[prior[dir][d]];
            int curY = y + dy[prior[dir][d]];


            if(curX < 0 || curY < 0 || curX >= N || curY >= N) continue;

            if(num_smell[curX][curY] == n) {
                int[] list = {curX, curY, d};
                return list;
            }

        }

        return null;
    }

    private static int[] prior_empty(int[][] new_map, int[][] prior, int x, int y, int dir, int n) {
        for(int d = 0; d < 4; d++) {
            int curX = x + dx[prior[dir][d]];
            int curY = y + dy[prior[dir][d]];


            if(curX < 0 || curY < 0 || curX >= N || curY >= N) continue;

            if (smell[curX][curY] == 0) {
                int[] list = {curX, curY, d};
                return list;
            }
        }

        return null;
    }

    private static boolean isMove(int[][] new_map, int x, int y, int n) {
        if(new_map[x][y] == 0 || new_map[x][y] > n) {
            return true;
        }
        return false;
    }

}