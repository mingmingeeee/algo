import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 1h54m01s46
public class Main {

    private static int N, M, max, ans, rainbow;
    private static boolean[][] visited;
    private static int[][] map;
    private static int[] dx = {0, 0, -1, 1};
    private static int[] dy = {-1, 1, 0, 0};

    private static class Pair implements Comparable<Pair> {
        int x, y, rain, m;

        public Pair(int x, int y, int rain, int m) {
            this.x = x;
            this.y = y;
            this.rain = rain;
            this.m = m;
        }

        public int compareTo(Pair o) {
            if (o.m == this.m) {
                if (o.rain == this.rain) {
                    if (o.x == this.x) {
                        return o.y - this.y;
                    }
                    return o.x - this.x;
                }
                return o.rain - this.rain;
            }
            return o.m - this.m;
        }
    }


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

        simulate();

        System.out.println(ans);

    }

    private static void simulate() {

        while(true) {
            visited = new boolean[N][N];

            // 1. 크기가 가장 큰 블록 그룹 찾기
            PriorityQueue<Pair> queue = new PriorityQueue<>();
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (!visited[i][j] && map[i][j] > 0) {
                        max = 0;
                        rainbow = 0;
                        visited[i][j] = true;

                        find_block(i, j, 1, map[i][j]);

                        queue.offer(new Pair(i, j, rainbow, max));
                    }
                    for(int x = 0; x < N; x++) {
                        for(int y = 0; y < N; y++) {
                            if(map[x][y] == 0 && visited[x][y] == true)
                                visited[x][y] = false;
                        }
                    }
                }
            }


            if(queue.isEmpty())
                break;
            Pair pair = queue.poll();
            if(pair.m == 1)
                break;


            ans += (pair.m * pair.m);

            // 2. 해당하는 블록 그룹 모든 블록 제거
            boolean[][] visited = new boolean[N][N];
            remove_block(pair.x, pair.y, visited, map[pair.x][pair.y]);

            // 3. 중력 작용
            gravity_block();


            // 4. 90도 반시계 방향으로 회전
            rotate_block();


            // 5. 중력 작용
            gravity_block();

        }

    }

    private static void find_block(int x, int y, int cnt, int color) {

        max++;
        if(map[x][y] == 0) rainbow++;

        for(int d = 0; d < 4; d++) {
            int curX = x + dx[d];
            int curY = y + dy[d];

            if(curX < 0 || curY < 0 || curX >= N || curY >= N) continue;
            if(visited[curX][curY]) continue;
            if(map[curX][curY] < 0) continue;
            if(map[curX][curY] > 0 && map[curX][curY] != color) continue;

            visited[curX][curY] = true;
            find_block(curX, curY, cnt + 1, color);
        }

    }

    private static void remove_block(int x, int y, boolean[][] visited, int color) {
        for(int d = 0; d < 4; d++) {
            int curX = x + dx[d];
            int curY = y + dy[d];

            if(curX < 0 || curY < 0 || curX >= N || curY >= N) continue;
            if(visited[curX][curY]) continue;
            if(!(map[curX][curY] == color || map[curX][curY] == 0)) continue;

            map[curX][curY] = -5; // 삭제의 의미
            visited[curX][curY] = true;
            remove_block(curX, curY, visited, color);
        }

    }

    private static void rotate_block() {
        int[][] next_map = new int[N][N];
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                int ox = i - 0; int oy = j - 0;
                int rx = N - 1 - oy; int ry = ox;
                next_map[rx + 0][ry + 0] = map[i][j];
            }
        }

        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = next_map[i][j];
            }
        }
    }

    private static void gravity_block() {
        for(int y = 0; y < N; y++) {
            int x = N - 1;
            while(x >= 0) {
                if(map[x][y] > -1) { // 이동
                    int idx = x;
                    while(++idx < N) {
                        if(map[idx][y] == -5) {
                            map[idx][y] = map[idx - 1][y];
                            map[idx - 1][y] = -5;
                        } else break;
                    }
                }
                x--;
            }
        }
    }
    
}