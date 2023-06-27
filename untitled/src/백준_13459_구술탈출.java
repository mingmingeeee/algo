import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 단순 구현 + DFS
// 소요시간 => 00:35:42

public class 백준_13459_구술탈출 {

    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static final char RED = 'R';
    private static final char BLUE = 'B';
    private static final char WALL = '#';
    private static final char HOLE = 'O';

    private static int N, M;
    private static char[][] map;

    private static boolean isAnswer = false;

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
        M = Integer.parseInt(st.nextToken());

        map = new char[N][M];

        int rx = 0, ry = 0, bx = 0, by = 0;
        for(int i = 0; i < N; i++) {
            map[i] = in.readLine().toCharArray();
            for(int j = 0; j < M; j++) {
                if(map[i][j] == RED) {
                    rx = i; ry = j;
                } else if(map[i][j] == BLUE) {
                    bx = i; by = j;
                }
            }
        }

        simulate(rx, ry, bx, by);

        if(isAnswer)
            System.out.println("1");
        else
            System.out.println("0");

    }

    private static void simulate(int rx, int ry, int bx, int by) {
        // 1. 이동하기
        go(rx, ry, bx, by, 0);
    }

    private static void go(int rx, int ry, int bx, int by, int cnt) {

        if(cnt >= 10)
            return;

        for(int d = 0; d < 4; d++) {

            Pair r = move(rx, ry, d);

            Pair b = move(bx, by, d);

            // 1. blue가 홀인 경우
            if(map[b.x][b.y] == HOLE)
                continue;

            // 2. blue가 홀이 아니고 red, blue 좌표가 같은 경우
            if(r.x == b.x && r.y == b.y) {
                // 2-1. 왼
                if(d == 0) {
                    if (ry < by) { // red가 blue보다 왼쪽
                        b.y -= dy[d];
                    } else if (ry > by) { // blue가 red보다 왼쪽
                        r.y -= dy[d];
                    }
                }
                // 2-2. 오
                if(d == 1) {
                    if(ry < by) { // blue가 red보다 오른쪽
                        r.y -= dy[d];
                    } else if(ry > by) { // red가 blue보다 오른쪽
                        b.y -= dy[d];
                    }
                }
                // 2-3. 상
                if(d == 2) {
                    if(rx < bx) { // red가 blue보다 위쪽
                        b.x -= dx[d];
                    } else if(rx > bx) { // blue가 red보다 위쪽
                        r.x -= dx[d];
                    }
                }
                // 2-4. 하
                if(d == 3) {
                    if(rx < bx) { // blue가 red보다 아래쪽
                        r.x -= dx[d];
                    } else if(rx > bx) { // red가 blue보다 아래쪽
                        b.x -= dx[d];
                    }
                }
            }

            // 3. 좌표가 같지 않고 blue가 홀이 아니고 red가 홀인 경우
            if(map[r.x][r.y] == HOLE) {
                isAnswer = true;
                return;
            }

            go(r.x, r.y, b.x, b.y, cnt + 1);

        }

    }

    private static Pair move(int x, int y, int d) {

        int nx = x + dx[d];
        int ny = y + dy[d];
        while(true) {
            // 1. 빠져나간 경우
            if(map[nx][ny] == HOLE) {
                return new Pair(nx, ny);
            }
            // 2. 벽을 만난 경우
            if(map[nx][ny] == WALL) {
                return new Pair(nx - dx[d], ny - dy[d]);
            }
            nx += dx[d];
            ny += dy[d];
        }

    }

}

// 직사각형 보드에 빨간 구슬, 파란 구슬 하나씩 넣은 다음
// 빨간 구슬을 구멍을 통해 빼내는 게임
// 가장 바깥 행, 열은 모두 막혀져 있음
// 파란 구슬이 구멍에 들어가면 안 됨 => 실패
// 왼, 오른, 위, 아래
// 더이상 구슬이 움직이지 않을 때 기울이는 동작 멈춤
// 10번 이하로 빨간 구슬 빼낼 수 있으면 1, 없으면 0 출력




