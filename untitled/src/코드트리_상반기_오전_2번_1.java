import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

// 소요시간 => 2:04:07
// ㅎr..이걸 어케 1:30 안에 푸는 거지??

public class 코드트리_상반기_오전_2번_1 {

    private static int n, num;
    private static int[][] map, group, next_map;
    private static int[] number;
    private static boolean[][] visited;
    private static int[] dx = {0, 0, -1, 1};
    private static int[] dy = {1, -1, 0, 0};

    private static final int FINALE = 30 * 30;

    private static ArrayList<Pair>[] list = new ArrayList[FINALE];

    private static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        StringBuilder sb = new StringBuilder();

        n = Integer.parseInt(st.nextToken());
        map = new int[n][n];

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = go();

        sb.append(answer);
        System.out.println(sb.toString());
    }

    private static int go() {

        int cnt = 4;
        int answer = 0;
        while(cnt-- > 0) {
            group = new int[n][n];
            visited = new boolean[n][n];
            for(int i = 0; i < FINALE; i++) {
                list[i] = new ArrayList<>();
            }

            // 1. 그룹 나누기
            num = 1;
            divide();

            // 2. 초기 예술 점수 구하기
            answer += score();

            next_map = new int[n][n];
            // 3. 십자 모양 회전 (반시계 90도)
            rotate_counter();

            // 4. 정사각형 회전 (시계 90도)
            rotate_clock(0, 0, n / 2);


            // 5. map으로 옮기기
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    map[i][j] = next_map[i][j];
                }
            }

            
        }

        return answer;

    }

    private static void rotate_clock(int start_x, int start_y, int size) {

        if(!isRange(start_x, start_y)) return;

        for(int i = start_x; i < start_x + size; i++) {
            for(int j = start_y; j < start_y + size; j++) {
                int nx = i - start_x, ny = j - start_y;
                int rx = ny;
                int ry = size - 1 - nx;
                next_map[rx + start_x][ry + start_y] = map[i][j];
            }
        }

        rotate_clock(start_x, start_y + size + 1, size);
        rotate_clock(start_x + size + 1, start_y, size);
        rotate_clock(start_x + size + 1, start_y + size + 1, size);

    }

    private static void rotate_counter() {

        // 1. 세로
        int y = n/2;
        for(int i = 0; i < n; i++) {
            int rx = n - y - 1;
            int ry = i;
            next_map[rx][ry] = map[i][y];
        }

        // 2. 가로
        int x = n/2;
        for(int j = 0; j < n; j++) {
            int rx = n - 1 - j;
            int ry = x;
            next_map[rx][ry] = map[x][j];
        }

    }

    private static boolean isRange(int x, int y) {
        return (x < 0 || y < 0 || x >= n || y >= n) ? false : true;
    }

    private static int score() {
        int[] sum = new int[num];
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                int idx = group[x][y];
                sum[idx]++;
            }
        }
        // "서로 맞닿아 있는 변의 수" == "서로 인접한 그룹"
        int init = 0;
        for(int i = 1; i < num; i++) {
            for(int j = i + 1; j < num; j++) {
                int cnt = 0;
                for(Pair cur : list[i]) {
                    for(int d = 0; d < 4; d++) {
                        int curX = cur.x + dx[d];
                        int curY = cur.y + dy[d];
                        if(!isRange(curX, curY)) continue;
                        if(group[curX][curY] == i || group[curX][curY] != j) continue;
                        cnt++;
                    }
                }
                int calc = (sum[i] + sum[j]) * number[i] * number[j] * cnt;
                init += calc;
            }
        }

        return init;

    }

    private static void divide() {
        number = new int[FINALE];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(visited[i][j]) continue;
                dfs(i, j);
                number[num] = map[i][j];
                num++;
            }
        }

    }

    private static void dfs(int x, int y) {

        visited[x][y] = true;
        group[x][y] = num;

        boolean isTrue = true;
        for(int d = 0; d < 4; d++) {
            int curX = x + dx[d];
            int curY = y + dy[d];

            if(!isRange(curX, curY)) continue;
            if(map[curX][curY] != map[x][y]) {
                isTrue = false;
                continue;
            }
            if(visited[curX][curY]) continue;

            dfs(curX, curY);
        }

        // 경계에 있던 좌표들 저장
        if(!isTrue) {
            list[num].add(new Pair(x, y));
        }

    }

}
