import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 백준_14500_테트로미노 {

    private static int N, M;
    private static int answer = Integer.MIN_VALUE;
    private static int[][] map;
    private static boolean[][] visited;
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        simulate();

        System.out.println(answer);

    }

    private static void simulate() {

        visited = new boolean[N][M];
        // 탐색 1
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                visited[i][j] = true;
                dfs(i, j, 0, map[i][j]);
                visited[i][j] = false;

                int sum = map[i][j];
                int min = Integer.MAX_VALUE;
                for(int d = 0; d < 4; d++) {
                    int curX = i + dx[d], curY = j + dy[d];


                    if(!isRange(curX, curY)) {
                        min = 0;
                        continue;
                    }
                    sum += map[curX][curY];
                    min = Math.min(min, map[curX][curY]);
                }

                answer = Math.max(answer, sum - min);
            }
        }


    }

    private static void dfs(int x, int y, int cnt, int sum) {

        if(cnt == 3) {
            answer = Math.max(answer, sum);
            return;
        }

        for(int i = 0; i < 4; i++) {
            int curX = x + dx[i];
            int curY = y + dy[i];

            if(!isRange(curX, curY) || visited[curX][curY])
                continue;

            visited[x][y] = true;
            dfs(curX, curY, cnt + 1, sum + map[curX][curY]);
            visited[x][y] = false;
        }

    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= M) {
            return false;
        }
        return true;
    }

}

// 1. 정사각형 서로 겹치면 안 됨
// 2. 도형은 모두 연결되어 있어야 함
// 3. 정사각형은 변끼리 연결되어 있어야 함
// 테트로미노 하나를 적절히 놓아서 테트로미노가 놓인 칸에 쓰여있는 수들의 합을 최대로 하는 프로그램 작성하기
// 회전, 대칭 가능