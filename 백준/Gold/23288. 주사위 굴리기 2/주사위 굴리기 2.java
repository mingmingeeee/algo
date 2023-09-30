import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 소요시간: 32m13s52

public class Main {

    static int N, M, K, C;
    static int[][] map;
    static Dice dice = new Dice(1, 6, 4, 3, 5, 2);
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    private static class Dice {
        int top, bottom, left, right, back, front;
        public Dice(int top, int bottom, int left, int right, int back, int front) {
            this.top = top;
            this.bottom = bottom;
            this.left = left;
            this.right = right;
            this.back = back;
            this.front = front;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken()); // 세로 크기
        M = Integer.parseInt(st.nextToken()); // 가로 크기
        K = Integer.parseInt(st.nextToken()); // 이동 횟수

        map = new int[N][M];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }



        System.out.println(simulate());
    }

    static int simulate() {

        // 처음 이동방향: 동
        int d = 0, answer = 0;
        int x = 0, y = 0;

        while(K-- > 0) {

            int curX = x + dx[d];
            int curY = y + dy[d];

            if (!isRange(curX, curY)) {
                // 이동 방향 반대로
                // 0 2 || 1 3
                d = (d + 2) % 4;
            }
                // 한 칸 구르기
            roll(d);
            // 좌표 이동
            x += dx[d];
            y += dy[d];

            int A = dice.bottom, B = map[x][y];

            if (A > B) {
                // 90도 시계 방향 회전
                d = (d + 1) % 4;
            } else if (A < B) {
                // 90도 반시계 방향 회전
                d--;
                if (d == -1)
                    d = 3;
            }

            C = 0;
            dfs(x, y,  new boolean[N][M], map[x][y]);
            answer += map[x][y] * C;

        }
        return answer;

    }

    static void dfs(int x, int y, boolean[][] visited, int target) {
        if(map[x][y] != target)
            return;

        C++;
        visited[x][y] = true;

        for(int d = 0; d < 4; d++) {
            int curX = x + dx[d];
            int curY = y + dy[d];
            if(!isRange(curX, curY) || visited[curX][curY]) {
                continue;
            }
            dfs(curX, curY, visited, target);
        }

    }

    static void roll(int d) {

        int tmp = dice.bottom;
        switch (d) {
            case 0: // 오른
                dice.bottom = dice.right;
                dice.right = dice.top;
                dice.top = dice.left;
                dice.left = tmp;
                break;
            case 1: // 위
                dice.bottom = dice.back;
                dice.back = dice.top;
                dice.top = dice.front;
                dice.front = tmp;
                break;
            case 2: // 왼
                dice.bottom = dice.left;
                dice.left = dice.top;
                dice.top = dice.right;
                dice.right = tmp;
                break;
            case 3: // 아래
                dice.bottom = dice.front;
                dice.front = dice.top;
                dice.top = dice.back;
                dice.back = tmp;
                break;
        }

    }

    static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= M)
            return false;
        return true;
    }

}


