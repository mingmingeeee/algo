import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;


// 상, 좌, 하, 우
// 소요시간: 1h12m58s8
// 조건 잘 읽자!!!!

public class Main {

    static int M, S, N = 4, max = Integer.MIN_VALUE;
    static int[] nums = new int[3];
    static Fish shark;
    static boolean isTrue = false;
    static List<Fish>[][] map;
    static int[][] smell;
    static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
    static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dxS = {-1, 0, 1, 0};
    static int[] dyS = {0, -1, 0, 1};

    static class Fish {
        int x, y, d;

        public Fish(int x, int y, int d) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        map = new List[N][N];

        initialize(map);

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;
            map[x][y].add(new Fish(x, y, d));
        }

        st = new StringTokenizer(in.readLine());
        int x = Integer.parseInt(st.nextToken()) - 1;
        int y = Integer.parseInt(st.nextToken()) - 1;
        shark = new Fish(x, y, -1);

        simulate();

        int answer = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                answer += map[i][j].size();
            }
        }

        System.out.println(answer);
    }


    static void simulate() {

        smell = new int[N][N];

        while(S-- > 0) {

            // 1. 모든 물고기 한 칸 이동
            List<Fish> fishes = copy();
            moveFish();

            // 2. 상어 이동
            moveShark();

            // 3. 냄새 사라짐
            disappearSmell();

            // 4. 물고기 복제
            duplicateFish(fishes);

        }

    }

    static void initialize(List[][] m) {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                m[i][j] = new ArrayList<>();
            }
        }
    }

    static void moveFish() {
        List<Fish>[][] nextMap = new List[N][N];
        initialize(nextMap);

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j].isEmpty()) continue;
                for(Fish fish : map[i][j]) {
                    int fx = fish.x, fy = fish.y, fd = fish.d;
                    boolean isTrue = false;
                    for (int d = 0; d < 8; d++) {
                        int curX = fx + dx[fd];
                        int curY = fy + dy[fd];
                        if (isRange(curX, curY) && !(shark.x == curX && shark.y == curY) && smell[curX][curY] == 0) {
                            nextMap[curX][curY].add(new Fish(curX, curY, fd));
                            isTrue = true;
                            break;
                        }
                        fd--;
                        if(fd == -1)
                            fd = 7;
                    }
                    if(!isTrue) {
                        nextMap[fx][fy].add(new Fish(fx, fy, fish.d));
                    }
                }
            }
        }

        initialize(map);
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                for(Fish fish : nextMap[i][j]) {
                    map[i][j].add(new Fish(fish.x, fish.y, fish.d));
                }
            }
        }
    }

    static void findDir(int[] num, int cnt, int n) {
        if(cnt == n) {
            int curX = shark.x, curY = shark.y, sum = 0;
            boolean[][] visited = new boolean[4][4];
            for(int i = 0; i < 3; i++) {
                curX += dxS[num[i]]; curY += dyS[num[i]];
                if(!isRange(curX, curY)) return;
                if(visited[curX][curY]) continue;
                visited[curX][curY] = true;
                sum += map[curX][curY].size();
            }

            if(max < sum) {
                max = sum;
                for(int i = 0; i < 3; i++) {
                    nums[i] = num[i];
                }
            }
            return;
        }

        for(int d = 0; d < 4; d++) {
            num[cnt] = d;
            findDir(num, cnt + 1, n);
        }
    }

    static void moveShark() {
        max = Integer.MIN_VALUE;
        isTrue = false;
        findDir(new int[3], 0, 3);


        int curX = shark.x, curY = shark.y;
        for(int i = 0; i < 3; i++) {
            curX += dxS[nums[i]]; curY += dyS[nums[i]];

            if(!map[curX][curY].isEmpty()) {
                smell[curX][curY] = 3;
                map[curX][curY].clear();
            }
        }
        shark.x = curX; shark.y = curY;
    }

    static void disappearSmell() {
        // 2번 지나면 물고기 냄새 사라짐
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(smell[i][j] > 0)
                    smell[i][j]--;
            }
        }
    }

    static List<Fish> copy() {
        List<Fish> fishes = new ArrayList<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j].isEmpty()) continue;

                for(Fish fish : map[i][j]) {
                    fishes.add(new Fish(fish.x, fish.y, fish.d));
                }
            }
        }
        return fishes;
    }

    static void duplicateFish(List<Fish> fishes) {
        // 물고기 복제! => 1과 같은 칸에 같은 수, 같은 방향
        for(Fish fish : fishes) {
            map[fish.x][fish.y].add(new Fish(fish.x, fish.y, fish.d));
        }
    }

    static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }

}