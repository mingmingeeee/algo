import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 소요시간: 1h03m30s86

public class Main {

    static int N, M, T;
    static int[][] map;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < T; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            simulate(x, d, k);
        }

        int answer = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                answer += map[i][j];
            }
        }

        System.out.println(answer);

    }

    static void simulate(int x, int d, int k) {

        for(int i = 0; i < N; i++) {
            if((i + 1) % x == 0) {

                for(int j = 0; j < k; j++) {
                    rotate(i, d);
                }

            }
        }


        caclulate();
    }

    static void print() {

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

    }

    static void caclulate() {
        int[][] copy = new int[N][M];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                copy[i][j] = map[i][j];
            }
        }

        boolean isValid = false;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                boolean isTrue = false;
                int y = j - 1;
                if(y == -1) y = M - 1;
                if(copy[i][j] == copy[i][y]) {
                    map[i][y] = 0; isTrue = true;
                }
                y = j + 1;
                if(y == M) y = 0;
                if(copy[i][j] == copy[i][y]) {
                    map[i][y] = 0; isTrue = true;
                }
                int x = i - 1;
                if(x >= 0 && copy[i][j] == copy[x][j]) {
                    map[x][j] = 0; isTrue = true;
                }
                x = i + 1;
                if(x < N && copy[i][j] == copy[x][j]) {
                    map[x][j] = 0; isTrue = true;
                }
                if(isTrue) {
                    if(map[i][j] > 0) isValid = true;
                    map[i][j] = 0;
                }
            }
        }

        if(!isValid) {
            int sum = 0;
            int cnt = 0;
            for(int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(map[i][j] > 0) {
                        sum += map[i][j];
                        cnt++;
                    }
                }
            }

            float avg = (float) sum / (float) cnt;

            for(int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if(map[i][j] == 0) continue;
                    if(map[i][j] > avg) {
                        map[i][j] -= 1;
                    } else if(map[i][j] < avg) {
                        map[i][j] += 1;
                    }
                }
            }
        }

    }

    static void rotate(int idx, int d) {

        if(d == 0) {
            // 시계 방향 회전
            int tmp = map[idx][M - 1];
            for(int i = M - 1; i > 0; i--) {
                map[idx][i] = map[idx][i - 1];
            }
            map[idx][0] = tmp;
        } else if(d == 1) {
            // 반시계 방향 회전
            int tmp = map[idx][0];
            for(int i = 0; i < M - 1; i++) {
                map[idx][i] = map[idx][i + 1];
            }
            map[idx][M - 1] = tmp;
        }
    }

}
