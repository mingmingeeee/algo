import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 36m38s

public class Main {

    private static int N, M;
    private static int[][] water;
    private static boolean[][] cloud, copy, cloud_copy;
    private static int[][] dir;
    private static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    private static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        water = new int[N][N];
        copy = new boolean[N][N];
        cloud = new boolean[N][N];
        cloud_copy = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                water[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dir = new int[M][2];
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());
            dir[i][0] = d;
            dir[i][1] = s;
        }

        simulate();

        System.out.println(cal());

    }

    private static int cal() {
        int sum = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                sum += water[i][j];
            }
        }
        return sum;
    }

    private static void simulate() {

        // 비바라기
        cloud[N - 1][0] = true; cloud[N - 1][1] = true;
        cloud[N - 2][0] = true; cloud[N - 2][1] = true;

        // M번 이동
        for(int i = 0; i < M; i++) {
            
            // 구름 이동
            move_cloud(i);

            // 구름 있는 칸: 물 양 1 증가
            increase();

            // 구름 다 사라짐
            disappear();

            // 물복사버그 마법
            copy_water();

            // 2 이상인 모든 칸: 구름, 물의 양 2 줄어듬
            decrease();

        }

    }

    private static void decrease() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(water[i][j] >= 2 && !cloud_copy[i][j]) {
                    water[i][j]-=2;
                    cloud[i][j] = true;
                }
            }
        }
    }
    

    private static void copy_water() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(copy[i][j]) {
                    // 1, 3, 5, 7
                    for(int d = 1; d < 8; d += 2) {
                        int curX = i + dx[d];
                        int curY = j + dy[d];

                        if(curX < 0 || curY < 0 || curX >= N || curY >= N) continue;
                        if(water[curX][curY] == 0) continue;

                        water[i][j]++;
                    }
                }
            }
        }

    }

    private static void disappear() {
        boolean[][] tmp = new boolean[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(!cloud[i][j]) continue;
                cloud[i][j] = false;
                tmp[i][j] = true;
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                cloud_copy[i][j] = tmp[i][j];
            }
        }
    }

    private static void increase() {

        boolean[][] tmp = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(cloud[i][j]) {
                    water[i][j]++;
                    tmp[i][j] = true;
                }
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                copy[i][j] = tmp[i][j];
            }
        }

    }

    private static void move_cloud(int idx) {

        int d = dir[idx][0];
        int s = dir[idx][1];

        boolean[][] tmp = new boolean[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int curX = (N + i + (dx[d] * s) % N) % N;
                int curY = (N + j + (dy[d] * s) % N) % N;
                if(cloud[i][j]) {
                    tmp[curX][curY] = true;
                }
            }
        }

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                cloud[i][j] = tmp[i][j];
            }
        }

    }


}

// A[][]: 물의 양
// 비바라기 시전: (N, 1) (N, 2) (N-1, 1) (N-1, 2)에 비구름
// 구름에 M번 이동 명령
// i번째 이동 명령은 방향 d, 거리 s
// 8방향

// 1. 모든 구름이 d 방향으로 s칸 이동
// 2. 각 구름에서 비가 내려 구름이 있는 칸의 바구니에 저장된 물 양 1 증가
// 3. 구름 모두 사라짐
// 4. 2에서 물이 증가한 칸 (r, c)에 물복사버그 마법 시전 => 대각선 방향으로 거리가 1인 칸에 물이 있는 바구니 수만큼 (r, c)에 있는 바구니의 물 양 증가
// => 경계 넘어가는 칸: 이동 X
// 5. 바구니에 저장된 물의 양이 2 이상인 모든 칸에 구름 생기고 물 양 2 줄어든다. 이 때 구름이 생기는 칸은 3에서 구름이 사라진 칸이 아니어야 함