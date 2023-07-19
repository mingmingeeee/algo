import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// 소요 시간 : 1h22m45s67

public class Main {

    static int N, answer;
    static int[][] map;
    static final int[] dx = {0, 1, 0, -1};
    static final int[] dy = {-1, 0, 1, 0};
    static final int[] dc = {1, 1, 2, 2};
    // 2, 10, 7, 1, 5, 10, 7, 1, 2
    static final double[] rate = {0.02, 0.1, 0.07, 0.01, 0.05, 0.1, 0.07, 0.01, 0.02};
    static final int[][] sandX = {
            {-2, -1, -1, -1, 0, 1, 1, 1, 2},
            {0, 1, 0, -1, 2, 1, 0, -1, 0},
            {2, 1, 1, 1, 0, -1, -1, -1, -2},
            {0, -1, 0, 1, -2, -1, 0, 1, 0}
    };
    static final int[][] sandY = {
            {0, -1, 0, 1, -2, -1, 0, 1, 0},
            {-2, -1, -1, -1, 0, 1, 1, 1, 2},
            {0, 1, 0, -1, 2, 1, 0, -1, 0},
            {2, 1, 1, 1, 0, -1, -1, -1, -2}
    };

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(in.readLine());
        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        simulate();

        System.out.println(answer);

    }

    private static void simulate() {

        int nx = N / 2;
        int ny = N / 2;
        while(true) {
            for(int d = 0; d < 4; d++) {

                for(int c = 0; c < dc[d]; c++) {

                    nx += dx[d];
                    ny += dy[d];

                    if(!isRange(nx, ny)) {
                        nx -= dx[d];
                        ny -= dy[d];
                        break;
                    }
                    spread(nx, ny, d);

                    map[nx][ny] = 0;
                }

                if(nx == 0 && ny == 0) {
                    return;
                }

            }

            for(int d = 0; d < 4; d++) {
                dc[d] += 2;
            }
        }

    }


    private static void spread(int x, int y, int d) {
        int sum = 0;
        for(int i = 0; i < 9; i++) {
            int curX = x + sandX[d][i];
            int curY = y + sandY[d][i];
            int sand = (int) (map[x][y] * rate[i]);
            sum += sand;
            if(!isRange(curX, curY)) {
                answer += sand;
                continue;
            }
            map[curX][curY] += sand;
        }

        int alphaX = x + dx[d];
        int alphaY = y + dy[d];
        int sand = map[x][y] - sum;
        if(isRange(alphaX, alphaY))
            map[alphaX][alphaY] += sand;
        else
            answer += sand;

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }



}

// 토네이도 이동
// 1. 가운데칸부터 토네이도 이동 시작
// 2. 이동할 때마다 모래 일정한 비율로 흩날림
// 3. 알파: 비율이 적혀있는 카능로 이동하지 않은 남은 모래의 양
// 4. 모래가 이미 있는 칸으로 이동하면 모래의 양은 더해짐
// 5. 토네이도가 (1, 1)까지 이동한 뒤 소멸함