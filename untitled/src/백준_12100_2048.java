import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 소요시간: 2:02:42
// 조건 하나 잘못 읽었었음 그걸 한시간 반 째에 깨달음..ㅠ
// 조건 잘 읽자...ㅠ^ ㅠ..


public class 백준_12100_2048 {

    // 좌, 우, 상, 하
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};

    private static int N;
    private static int answer;

    public static void main(String[] args) throws Exception{

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        go(map);

        System.out.println(answer);

    }

    private static void go(int[][] map) {

        direction(0, map);

    }

    private static void direction(int cnt, int[][] map) {

        if(cnt > 5)
            return;


        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                answer = Math.max(answer, map[i][j]);
            }
        }


        // 좌우상하 네 방향 중 하나로 이동
        for(int d = 0; d < 4; d++) {
            int[][] next_map = new int[N][N];
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    next_map[i][j] = map[i][j];
                }
            }



            move(d, new boolean[N][N], next_map);



            direction(cnt + 1, next_map);
        }

    }

    private static void move(int d, boolean[][] visited, int[][] map) {

        switch (d) {
            case 0: // 좌
                for(int x = 0; x < N; x++) {
                    for(int y = 0; y < N; y++) {
                        if(map[x][y] > 0)
                            real_move(x, y, d, visited, map);
                    }
                }
                break;
            case 1: // 우
                for(int x = 0; x < N; x++) {
                    for(int y = N - 1; y >= 0; y--) {
                        if(map[x][y] > 0)
                            real_move(x, y, d, visited, map);
                    }
                }
                break;
            case 2: // 상
                for(int y = 0; y < N ; y++) {
                    for(int x = 0; x < N; x++) {
                        if(map[x][y] > 0)
                            real_move(x, y, d, visited, map);
                    }
                }
                break;
            case 3: // 하
                for(int y = 0; y < N; y++) {
                    for(int x =  N - 1; x >= 0; x--) {
                        if(map[x][y] > 0)
                            real_move(x, y, d, visited, map);
                    }
                }
                break;
        }

    }

    private static void real_move(int x, int y, int d, boolean[][] visited, int[][] map) {

        int nx = x; int ny = y;
        while(true) {
            x = nx; y = ny;
            nx += dx[d]; ny += dy[d];

            // 1. 격자에서 벗어났을 때
            if(!isRange(nx, ny)) break;
            // 2. 합체된 블록일 때
            if(visited[nx][ny]) break;

            // 3. 합체할 블록일 때
            if(map[nx][ny] == map[x][y]) {
//                System.out.println("dd");
                map[nx][ny] *= 2;
                map[x][y] = 0;
                visited[nx][ny] = true;
                break;
            } else if(map[nx][ny] != 0) {
                break;
            }

            map[nx][ny] = map[x][y];
            map[x][y] = 0;

        }

    }

    private static boolean isRange(int x, int y) {
        return (x < 0 || y < 0 || x >= N || y >= N) ? false : true;
    }

}

// 전체 블록 상하좌우 네 방향 중 하나로 이동
// 같은 값을 갖는 두 블록이 충돌하면? => 두 블록은 합쳐짐
// 한 번의 이동에서 합쳐진 블록은 또 다른 블록과 함쳐질 수 없음
// 최대 5번 이동해서 만들 수 있는 가장 큰 블록의 값