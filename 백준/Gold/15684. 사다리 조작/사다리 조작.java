import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class Main {

    private static int N, M, H, ans;
    private static boolean isFinished = false;
    private static int[][] map;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        map = new int[H + 1][N + 1];

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            map[a][b] = 1; // 해당칸 기준 오른쪽
            map[a][b + 1] = 2; // 해당칸 기준 왼쪽
        }

        for(int i = 0; i <= 3; i++) {
            ans = i;
            makeLines(1, 0);
            if(isFinished) break;
        }


        if(!isFinished) ans = -1;
        System.out.println(ans);

    }

    private static void makeLines(int start, int cnt) {

        if(isFinished) return;
        if(cnt == ans) {
            if(check()) isFinished = true;
            return;
        }


        // 가로선 만들기
        for(int i = start; i <= H; i++) {
            for(int j = 1; j < N; j++) {
                if(map[i][j] == 0 && map[i][j + 1] == 0) {
                    map[i][j] = 1;
                    map[i][j + 1] = 2;
                    makeLines(i, cnt + 1);
                    map[i][j] = 0;
                    map[i][j + 1] = 0;
                }
            }
        }

    }

    private static boolean check() {
        for(int i = 1; i <= N; i++) {
            int x = 1; int y = i;
            while(x <= H) {
                if(map[x][y] == 1) y++;
                else if(map[x][y] == 2) y--;
                x++;
            }

            if(y != i) return false;
        }

        return true;
    }

}

// N개의 세로선, M개의 가로선
// 인접한 세로선 사이 가로선 놓을 수 있음
// 각각 세로선마다 가로선 놓을 수 있는 위치: H
// i번 세로선의 결과가 i번 나와야 함 => 추가해야 하는 가로선 개수의 최솟값 구하기