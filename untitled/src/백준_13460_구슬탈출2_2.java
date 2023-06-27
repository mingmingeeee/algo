

// 4차원 배열 visited 풀이 방식 봄... => 왜 굳이 visited 사용하지?
// => 시간 복잡도 줄이기 위해

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class 백준_13460_구슬탈출2_2 {


    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static final char RED = 'R';
    private static final char BLUE = 'B';
    private static final char WALL = '#';
    private static final char HOLE = 'O';

    private static int N, M;
    private static char[][] map;

    private static class Pair {
        int rx, ry, bx, by;
        int cnt;

        public Pair(int rx, int ry, int bx, int by, int cnt) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int rx = 0, ry = 0;
        int bx = 0, by = 0;

        map = new char[N][M];

        for(int i = 0; i < N; i++) {
            map[i] = in.readLine().toCharArray();
            for(int j = 0; j < M; j++) {
                if(map[i][j] == RED) {
                    rx = i;
                    ry = j;
                } else if(map[i][j] == BLUE) {
                    bx = i;
                    by = j;
                }
            }
        }

        int answer = simulate(rx, ry, bx, by);

        System.out.println(answer);
    }

    private static int simulate(int rx, int ry, int bx, int by) {

        boolean[][][][] visited = new boolean[N][M][N][M];
        Queue<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair(rx, ry, bx, by, 0));

        visited[rx][ry][bx][by] = true;

        while(!queue.isEmpty()) {

            Pair cur = queue.poll();

            if(cur.cnt >= 10)
                continue;

            for(int d = 0; d < 4; d++) {
                int[][] red = move(cur.rx, cur.ry, d);
                int[][] blue = move(cur.bx, cur.by, d);

                rx = red[0][0]; ry = red[0][1];
                bx = blue[0][0]; by = blue[0][1];

//                System.out.println("red" + " " + map[rx][ry]);
//                System.out.println("blue" + " " + map[bx][by]);

                if(map[bx][by] == HOLE) {
                    continue;
                }

                if(rx == bx && ry == by) {
                    int distRed = Math.abs(cur.rx - rx) + Math.abs(cur.ry - ry);
                    int distBlue = Math.abs(cur.bx - bx) + Math.abs(cur.by - by);
                    if(distRed < distBlue) { // blue가 더 뒤에 있었다는 의미
                        bx -= dx[d];
                        by -= dy[d];
                    } else { // red가 더 뒤에 있었다는 의미
                        rx -= dx[d];
                        ry -= dy[d];
                    }
                }

                if(map[rx][ry] == HOLE) {
                    return cur.cnt + 1;
                }

                if(!visited[rx][ry][bx][by]) {
                    visited[rx][ry][bx][by] = true;
                    queue.offer(new Pair(rx, ry, bx, by, cur.cnt + 1));
                }
            }

        }

        return -1;
    }

    private static int[][] move(int x, int y, int d) {
        int[][] pair = new int[1][2];
        int nx = x; int ny = y;
        while(true) {
            if(map[nx][ny] == HOLE) {
//                System.out.println("hole");
                pair[0][0] = nx;
                pair[0][1] = ny;
                return pair;
            } else if(map[nx][ny] == WALL) {
                pair[0][0] = nx - dx[d];
                pair[0][1] = ny - dy[d];
                return pair;
            }



            nx += dx[d];
            ny += dy[d];
        }
    }

}
