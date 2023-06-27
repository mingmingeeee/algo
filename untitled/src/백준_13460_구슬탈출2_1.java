import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


// 소요시간: 42m58s8

public class 백준_13460_구슬탈출2_1 {

    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static final char RED = 'R';
    private static final char BLUE = 'B';
    private static final char WALL = '#';
    private static final char HOLE = 'O';

    private static int N, M;
    private static char[][] map;

    private static class Pair implements Comparable<Pair>{
        int rx, ry, bx, by;
        int cnt;

        public Pair(int rx, int ry, int bx, int by, int cnt) {
            this.rx = rx;
            this.ry = ry;
            this.bx = bx;
            this.by = by;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Pair o) {
            return this.cnt - o.cnt;
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

//        boolean[][][][] visited = new boolean[N][M][N][M];
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        queue.offer(new Pair(rx, ry, bx, by, 0));

        while(!queue.isEmpty()) {

            Pair cur = queue.poll();

//            visited[cur.rx][cur.ry][cur.bx][cur.by] = true;

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
                    // 2-1. 왼
                    if(d == 0) {
                        if (cur.ry < cur.by) { // red가 blue보다 왼쪽
                            by -= dy[d];
                        } else if (cur.ry > cur.by) { // blue가 red보다 왼쪽
                            ry -= dy[d];
                        }
                    }
                    // 2-2. 오
                    if(d == 1) {
                        if(cur.ry < cur.by) { // blue가 red보다 오른쪽
                            ry -= dy[d];
                        } else if(cur.ry > cur.by) { // red가 blue보다 오른쪽
                            by -= dy[d];
                        }
                    }
                    // 2-3. 상
                    if(d == 2) {
                        if(cur.rx < cur.bx) { // red가 blue보다 위쪽
                            bx -= dx[d];
                        } else if(cur.rx > cur.bx) { // blue가 red보다 위쪽
                            rx -= dx[d];
                        }
                    }
                    // 2-4. 하
                    if(d == 3) {
                        if(cur.rx < cur.bx) { // blue가 red보다 아래쪽
                            rx -= dx[d];
                        } else if(cur.rx > cur.bx) { // red가 blue보다 아래쪽
                            bx -= dx[d];
                        }
                    }
                }

                if(map[rx][ry] == HOLE) {
                    return cur.cnt + 1;
                }

//                if(!visited[cur.rx][cur.ry][cur.bx][cur.by])
                queue.offer(new Pair(rx, ry, bx, by, cur.cnt + 1));
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

// 최소 몇 번 만에 빨간 구슬을 구멍을 통해 빼낼 수 있는지 출력