import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 1h46m28s21

public class Main {

    private static int[] beads = new int[4];
    private static int N, M;
    private static int[][] map, pair;
    private static int[] smap;
    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        pair = new int[M][2];
        smap = new int[N * N];
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());

            pair[i][0] = d;
            pair[i][1] = s;
        }


        simulate();

        System.out.println(beads[1] + 2 * beads[2] + 3 * beads[3]);


    }

    private static void simulate() {

        int cnt = 0;
        while(cnt < M) {

            // 1. 구슬 파괴
            destroy(cnt++);

            // 2 - 1. 구슬 이동 전 1차원에 저장
            store();

            // 2 - 2. 구슬 이동 move
            move();

            // 3. 폭발하는 구슬이 없을 때까지 계속
            while(explode()) {
                move();
            }

            // 구슬 개수 & 구슬 번호 저장
            storeCountNum();
            move();


        }

    }

    private static void storeCountNum() {

        int[] tmp = new int[N * N];
        int cnt = 1, idx = 0;
        for(int i = 1; i < N * N; i++) {
            if(idx + 1 >= N * N) break;

            if(smap[i] > 0 && smap[i] == smap[i - 1]) {
                cnt++;
            } else {
                if(smap[i - 1] > 0) {
                    tmp[idx++] = cnt;
                    tmp[idx++] = smap[i - 1];
                }
                cnt = 1;
            }

            if(i == N * N - 1 && smap[i - 1] > 0 && smap[i] == smap[i - 1]) {
                tmp[idx++] = cnt;
                tmp[idx++] = smap[i];
            }
        }

        for(int i = 0; i < N * N; i++) {
            smap[i] = tmp[i];
        }

    }

    private static boolean explode() {

        int cnt = 1, total = 0;
        for(int i = 1; i < N * N; i++) {
            if(smap[i - 1] > 0 && smap[i] == smap[i - 1]) {
                cnt++;
            } else {
                if(cnt >= 4) {
                    beads[smap[i - 1]] += cnt;
                    total++;
                    for(int c = 1; c <= cnt; c++) {
                        smap[i - c] = 0;
                    }
                }
                cnt = 1;
            }

            if(cnt >= 4 && i == N * N - 1) {
                total++;
                beads[smap[i - 1]] += cnt;
                for(int c = 1; c <= cnt; c++) {
                    smap[i - c] = 0;
                }
            }
        }

        if(total == 0)
            return false;

        int idx = 0;
        int[] tmp = new int[N * N];
        for(int i = 0; i < N * N; i++) {
            if(smap[i] > 0) {
                tmp[idx++] = smap[i];
            }
        }
        for(int i = 0; i < N * N; i++) {
            smap[i] = tmp[i];
        }

        return true;

    }

    private static void store() {
        int[] tmp = new int[N * N];

        int cnt = 1, d = -1;
        int x = N / 2, y = N / 2;
        int tx = 0;
        while(cnt < N) {
            for(int i = 0; i < cnt; i++) {
                y += d;
                if(map[x][y] > 0) {
                    tmp[tx++] = map[x][y];
                }
            }

            d *= -1;

            for(int i = 0; i < cnt; i++) {
                x += d;
                if(map[x][y] > 0)
                    tmp[tx++] = map[x][y];
            }

            cnt++;
        }

         for(int i = 0; i < N - 1; i++) {
            y += d;
            if(map[x][y] > 0)
                tmp[tx++] = map[x][y];
        }

        for(int i = 0; i < N * N; i++) {
            smap[i] = tmp[i];
        }
    }

    private static void move() {
        
        //// smap -> tmap 옮기기
        int[][] tmap = new int[N][N];
        int cnt = 1, d = -1;
        int x = N / 2, y = N / 2;
        int tx = 0;
        while(cnt < N) {
            for(int i = 0; i < cnt; i++) {
                y += d;
                tmap[x][y] = smap[tx++];
            }

            d *= -1;

            for(int i = 0; i < cnt; i++) {
                x += d;
                tmap[x][y] = smap[tx++];
            }

            cnt++;
        }

        for(int i = 0; i < N - 1; i++) {
            y += d;
            tmap[x][y] = smap[tx++];
        }
        
        //// tmap -> map 옮기기
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = tmap[i][j];
            }
        }

    }

    private static void destroy(int cnt) {

        int x = N / 2, y = N / 2;
        int k = 0;
        // d 방향으로 거리가 s인 모든 칸에 얼음 파편 던져 그 칸에 있는 구슬 모두 파괴
        while(k++ < pair[cnt][1]) {
            x += dx[pair[cnt][0]];
            y += dy[pair[cnt][0]];

            if(!isRange(x, y)) return;

            map[x][y] = 0;
        }

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }

    private static void print() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}

