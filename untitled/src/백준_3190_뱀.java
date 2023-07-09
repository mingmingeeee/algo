import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class 백준_3190_뱀 {

    private static final int APPLE = 3;

    private static int N, K, L, time;
    private static int[][] map;
    private static int[][] directions;
    private static Queue<Pair> queue = new ArrayDeque<>();

    // 동, 남, 서, 북 => 오른쪽: +1, 왼쪽: -1
    private static final int[] dx = {0, 1, 0, -1};
    private static final int[] dy = {1, 0, -1, 0};

    private static class Pair {
        int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(in.readLine());
        map = new int[N][N];

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;

            map[x][y] = APPLE;
        }

        L = Integer.parseInt(in.readLine());

        directions = new int[L][2];
        for(int i = 0; i < L; i++) {
            st = new StringTokenizer(in.readLine());
            int time = Integer.parseInt(st.nextToken());
            char dir = st.nextToken().charAt(0);

            directions[i][0] = time;
            directions[i][1] = dir;
        }

        // L: 왼쪽으로 90도 회전
        // D: 오른쪽으로 90도 회전

        simulate();

        System.out.println(time);

    }

    private static void simulate() {

        int nx = 0, ny = 0;
        int d = 0;
        map[nx][ny] = -1;

        queue.offer(new Pair(nx, ny));

        while(true) {

            time++;

//            System.out.println(time);
//            for(int i = 0; i < N; i++) {
//                for(int j = 0; j < N; j++) {
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }

            // 1. 몸길이 늘려 머리 다음칸에 위치시키기
            nx = nx + dx[d];
            ny = ny + dy[d];

            // 2. if(벽이나 자기 자신의 몸과 부딪히면)
            //    return;
            if(!isRange(nx, ny)) {
                return;
            }

            move(nx, ny);


            // 회전
            for(int i = 0; i < L; i++) {
                if(directions[i][0] == time) {
                    if(directions[i][1] == 'D') {
                        d = (d + 1) % 4;
                    } else if(directions[i][1] == 'L') {
                        d = (d - 1 + 4) % 4;
                    }
                    break;
                }
            }

        }

    }

    private static void move(int x, int y) {

        queue.offer(new Pair(x, y));

        //  이동한 칸에 사과 X
        //  몸길이 줄여 꼬리 위치한 칸 비움
        if(map[x][y] != APPLE) {
            Pair cur = queue.poll();
            map[cur.x][cur.y] = 0;
        }
        map[x][y] = -1;

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N || map[x][y] == -1) {
            return false;
        }
        return true;
    }

}

// N * N 정사각
// 몇 몇 칸에는 사과, 보드 끝에는 벽
// (1) 뱀: (0, 0) 위치
// (2) 처음에는 오른쪽
// (3) 매 초 이동
// (3-1) 몸길이를 늘려 머리 다음칸에 위치
// (3-2) 벽, 자기 자신의 몸과 부딪히면 게임 끝남
// (3-3) 이동한 칸에 사과 있다면 사과 없어지고 꼬리 안 움직임
// (3-4) 사과 없다면 몸길이 줄여 꼬리가 위치한 칸 비워줌