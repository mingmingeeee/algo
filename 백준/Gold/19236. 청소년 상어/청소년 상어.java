import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 2h24m31s64

public class Main {
    
    private static int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static int[] dy = {0, -1, -1, -1, 0, 1, 1, 1};
    private static int max = 0;


    private static class Fish implements Comparable<Fish> {
        int x, y, num, d;

        public Fish(int x, int y, int num, int d) {
            this.x = x;
            this.y = y;
            this.num = num;
            this.d = d;
        }

        public int compareTo(Fish o) {
            return this.num - o.num;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Fish[][] map = new Fish[4][4];
        Fish shark;
        for(int i = 0; i < 4; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < 4; j++) {
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken()) - 1;
                map[i][j] = new Fish(i, j, a, b);
            }
        }

        // 상어 (0, 0)에서 시작
        int start = map[0][0].num;
        shark = new Fish(0, 0, -1, map[0][0].d);
        map[0][0] = shark;
        simulate(map, shark, start);

        System.out.println(max);

    }

    private static void simulate(Fish[][] map, Fish shark, int eat) {

        if(max < eat) {
            max = Math.max(max, eat);
        }

        // 물고기 이동
        move_fish(map);


        List<Fish> fishList = new ArrayList<>();
        int x = shark.x, y = shark.y;
        while(true) {
            x += dx[shark.d];
            y += dy[shark.d];

            if(!isRange(x, y)) break;

            if(map[x][y].num > 0)
                fishList.add(new Fish(x, y, map[x][y].num, map[x][y].d));
        }


        // 청소년 상어 이동
        for(Fish fish : fishList) {
            // 물고기 먹고 물고기 방향 가짐
            Fish tmp_shark = new Fish(fish.x, fish.y, -1, fish.d);

            Fish[][] tmp_map = new Fish[4][4];
            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    tmp_map[i][j] = new Fish(map[i][j].x, map[i][j].y, map[i][j].num, map[i][j].d);
                }
            }

            tmp_map[shark.x][shark.y].num = 0;
            tmp_map[shark.x][shark.y].d = -1;
            tmp_map[fish.x][fish.y] = tmp_shark;

            simulate(tmp_map, tmp_shark, eat + fish.num);
        }


    }

    private static void move_fish(Fish[][] map) {
        // PriorityQueue fishes => 작은 물고기 앞으로
        PriorityQueue<Fish> fishes = new PriorityQueue<>();
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(map[i][j].num > 0) {
                    fishes.offer(new Fish(map[i][j].x, map[i][j].y, map[i][j].num, map[i][j].d));
                }
            }
        }

        // 하나씩 꺼내서 이동
        while(!fishes.isEmpty()) {

            Fish cur = fishes.poll();

            for(int i = 0; i < 4; i++) {
                for(int j = 0; j < 4; j++) {
                    if(map[i][j].num == cur.num) {
                        cur.x = i; cur.y = j; cur.d = map[i][j].d;
                    }
                }
            }

            int n = cur.num;
            int curX = cur.x, curY = cur.y, dir = cur.d;
            for(int d = 0; d < 8; d++) {
                dir = (cur.d + d) % 8;
                curX = cur.x + dx[dir];
                curY = cur.y + dy[dir];

                if (!(!isRange(curX, curY) || map[curX][curY].num < 0)) break;
            }

            if(!isRange(curX, curY) || map[curX][curY].num < 0) return;

            map[cur.x][cur.y].d = dir;

            // swap
            // 1. num
            int tmp = map[cur.x][cur.y].num;
            map[cur.x][cur.y].num = map[curX][curY].num;
            map[curX][curY].num = tmp;

            // 2. d
            tmp = map[cur.x][cur.y].d;
            map[cur.x][cur.y].d = map[curX][curY].d;
            map[curX][curY].d = tmp;

        }

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || x >= 4 || y < 0 || y >= 4) return false;
        return true;
    }

}
