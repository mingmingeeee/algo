import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// 소요 시간 38m07s46

public class 백준_14499_주사위굴리기 {

    private static int N, M;
    private static int x, y;
    private static int K;
    private static int[][] map;
    private static int[] commands;

    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {1, -1, 0, 0};

    private static class Dice {
        int top, bottom, left, right, back, front;
        public Dice(int top, int bottom, int left, int right, int back, int front) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
            this.back = back;
            this.front = front;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        commands = new int[K];
        st = new StringTokenizer(in.readLine());
        for(int i = 0; i < K; i++) {
            commands[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        simulate();

    }

    // 1, 5, 6, 2
    // 1, 2, 6, 5

    // 1, 3, 6, 4
    // 1, 4, 6, 3
    private static void simulate() {

        int nx = x, ny = y;
        Dice dice = new Dice(0, 0, 0, 0, 0, 0);
        for(int i = 0; i < K; i++) {

            int d = commands[i];
            nx += dx[d];
            ny += dy[d];

            if(!isRange(nx, ny)) {
                nx -= dx[d];
                ny -= dy[d];
                continue;
            }

            // 오른, 왼, 위, 아래
            int tmp = dice.bottom;
            switch (d) {
                case 0:
                    dice.bottom = dice.right;
                    dice.right = dice.top;
                    dice.top = dice.left;
                    dice.left = tmp;
                    break;
                case 1:
                    dice.bottom = dice.left;
                    dice.left = dice.top;
                    dice.top = dice.right;
                    dice.right = tmp;
                    break;
                case 2:
                    dice.bottom = dice.front;
                    dice.front = dice.top;
                    dice.top = dice.back;
                    dice.back = tmp;
                    break;
                case 3:
                    dice.bottom = dice.back;
                    dice.back = dice.top;
                    dice.top = dice.front;
                    dice.front = tmp;
                    break;
            }
            if(map[nx][ny] == 0) {
                map[nx][ny] = dice.bottom;
            } else {
                dice.bottom = map[nx][ny];
                map[nx][ny] = 0;
            }

            System.out.println(dice.top);

        }

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= M) {
            return false;
        }
        return true;
    }

}

// 크기 N * M
// 오른, 위
// r: 위쪽으로부터 떨어진, c: 왼쪽으로부터 떨어진
// 주사위 지도 위에 윗 면: 1, 동쪽 바라보는 방향이 3인 상태로 있음 => (x, y)
// 가장 처음에 주사위에는 모든 면에 0이 적혀져 있음
// 지도의 각 칸에 정수가 하나씩 쓰여 있음
// 주사위 굴렷을 때 이동한 칸에 쓰여있는 수 0
// 주사위의 바닥 면에 쓰여 있는 수가 복사됨
// 0이 아닌 경우
// 칸에 쓰여있는 수가 주사위 바닥면으로 복사되며 칸에 쓰여있는 수는 0이 됨
// 주사위를 놓는 곳의 좌표와 이동시키는 명령이 주어졌을 때 주사위가 이동했을 때마다 상단에 쓰여 있는 값을 구하는 프로그램 작성하기
// 주사위는 지도의 바깥으로 이동시킬 수 없음 => 만약 바깥으로 이동시키려고 하는 경우, 해당 명령 무시해야 함 => 출력도 XXX
