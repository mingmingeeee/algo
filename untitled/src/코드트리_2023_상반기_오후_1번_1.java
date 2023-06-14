import java.io.*;
import java.util.*;

// 소요시간 => 02:48:40
// 회전 생각하는 데서 오래 걸림.. ㅠ

public class 코드트리_2023_상반기_오후_1번_1 {

    private static int n, m, k;
    private static int[][] map;
    private static List<Pair> candidates = new ArrayList<>();
    private static Pair exit;
    private static int nemo_x, nemo_y, nemo_size;
    private static int answer;
    private static int escape_count;

    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

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

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[n][n];
        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());
            candidates.add(new Pair(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1));
        }

        st = new StringTokenizer(in.readLine());
        exit = new Pair(Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1);

        go();


        System.out.println(answer);
        System.out.println((exit.x + 1) + " " + (exit.y + 1));
    }

    private static void go() {

        while(k-- > 0) {

            // 1. 참가자 이동
            move();

            if(escape_count == m)
                return;

            // 2. 회전
            rotate();

            // System.out.println(k);
        }

    }

    private static boolean isRange(int x, int y) {
        return (x >= n || y >= n || x < 0 || y < 0 || map[x][y] > 0) ? false : true;
    }

    private static void move() {

        for(int i = candidates.size() - 1; i >= 0; i--) {
            int curX = candidates.get(i).x;
            int curY = candidates.get(i).y;
            int min = Math.abs(exit.x - curX) + Math.abs(exit.y - curY);
            // System.out.println(i + "번째 : " + candidates.get(i).x + " " + candidates.get(i).y);
            for(int d = 0; d < 4; d++) {
                int x = curX + dx[d];
                int y = curY + dy[d];

                if(isRange(x, y)) {
                    if(x == exit.x && y == exit.y) {
                        candidates.remove(i);
                        answer++;
                        escape_count++;
                        break;
                    } else if(min > Math.abs(exit.x - x) + Math.abs(exit.y - y)) {
                        candidates.get(i).x = x;
                        candidates.get(i).y = y;
                        answer++;
                        break;
                    }
                }
            }
        }

    }

    private static void rotate() {

        // 한 명 이상의 참가자, 출구 포함한 가장 작은 정사각형 구하기
        find();

        // 2-3. 시계 방향으로 90도 회전, 내구도 깎임
        real_rotate();

    }

    // map 회전
    private static void real_rotate() {

        int[][] next_map = new int[n][n];

        // 내구도 감소
        for(int i = nemo_x; i < nemo_x + nemo_size; i++) {
            for(int j = nemo_y; j < nemo_y + nemo_size; j++) {
                if(map[i][j] > 0)
                    map[i][j]--;
            }
        }

        // for(int i = nemo_x; i < nemo_x + nemo_size; i++) {
        //     for(int j = nemo_y; j < nemo_y + nemo_size; j++) {
        //         System.out.print(map[i][j] + " ");
        //     }
        //     System.out.println();
        // }
        // System.out.println("=====");
        // 회전
        for(int i = nemo_x; i < nemo_x + nemo_size; i++) {
            for(int j = nemo_y; j < nemo_y + nemo_size; j++) {
                int nx = i - nemo_x, ny = j - nemo_y;
                int rx = ny, ry = nemo_size - nx - 1;
                next_map[rx + nemo_x][ry + nemo_y] = map[i][j];
            }
            // (x, y) -> (y, square_n - x - 1)
        }

        for(int idx = 0; idx < candidates.size(); idx++) {
            if(nemo_x <= candidates.get(idx).x && candidates.get(idx).x < nemo_x + nemo_size
                    && nemo_y <= candidates.get(idx).y && candidates.get(idx).y < nemo_y + nemo_size) {
                int nx = candidates.get(idx).x - nemo_x, ny = candidates.get(idx).y - nemo_y;
                int rx = ny, ry = nemo_size - nx - 1;
                candidates.get(idx).x = rx + nemo_x;
                candidates.get(idx).y = ry + nemo_y;
            }
        }

        if(nemo_x <= exit.x && exit.x < nemo_x + nemo_size && nemo_y <= exit.y && exit.y < nemo_y + nemo_size) {
            int nx = exit.x - nemo_x, ny = exit.y - nemo_y;
            int rx = ny, ry = nemo_size - nx - 1;
            exit.x = rx + nemo_x;
            exit.y = ry + nemo_y;
        }

        // System.out.println(exit.x + " " + exit.y + " 출구");


        for(int i = nemo_x; i < nemo_x + nemo_size; i++) {
            for(int j = nemo_y; j < nemo_y + nemo_size; j++) {
                map[i][j] = next_map[i][j];
            }
        }


    }

    private static void find() {

        // 사이즈가 작은 것 부터
        for(int size = 2; size <= n; size++) {
            // 좌상단 r 좌표 작은 것 부터
            for(int r = 0; r < n - size + 1; r++) {
                // 좌상단에서 c좌표 작은 것 부터
                for(int c = 0; c < n - size + 1; c++) {
                    int r1 = r + size - 1;
                    int c1 = c + size - 1;

                    // 출구 존재 확인
                    if(!(exit.x <= r1 && r <= exit.x && exit.y <= c1 && c <= exit.y)) {
                        // System.out.println("출구!!");
                        continue;
                    }

                    boolean isTrue = false;
                    // 한 명 이상의 참가자 존재 확인
                    for(Pair candidate : candidates) {
                        if(candidate.x <= r1 && r <= candidate.x && c <= candidate.y && candidate.y <= c1) {
                            isTrue = true;
                            break;
                        }
                    }

                    // 한 명 이상의 참가자, 출구 있다면 종료
                    if(isTrue) {
                        nemo_x = r;
                        nemo_y = c;
                        nemo_size = size;
                        return;
                    }
                }
            }
        }

    }

}
