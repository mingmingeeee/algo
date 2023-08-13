import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class Main {

    private static int N, L;
    private static int[][] map;

    private static int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = 0;
        for (int i = 0; i < N; i++) {
            if(row(i)) answer++;
            if(col(i)) answer++;
        }

        System.out.println(answer);

    }

    private static boolean row(int row) {

        boolean[] visited = new boolean[N];

        for(int i = 0; i < N - 1; i++) {

            int diff = map[row][i] - map[row][i + 1];
            if(diff > 1 || diff < -1) return false;
            else if(diff == -1) { // 다음 계단이 높음
                for(int j = 0; j < L; j++) {
                    if(i - j < 0 || visited[i - j]) return false;
                    if(map[row][i] != map[row][i - j]) return false;
                    visited[i - j] = true;
                }
            } else if (diff == 1) { // 다음 계단이 한 단계 낮음
                for(int j = 1; j <= L; j++) {
                    if(i + j >= N || visited[i + j]) return false;
                    if(map[row][i + 1] != map[row][i + j]) return false;
                    visited[i + j] = true;
                }
            }
        }
        return true;

    }

    private static boolean col(int col) {

        boolean[] visited = new boolean[N];

        for(int i = 0; i < N - 1; i++) {
            int diff = map[i][col] - map[i + 1][col];


            if(diff > 1 || diff < -1) return false;
            else if(diff == -1) { // 다음 계단이 높음
                for(int j = 0; j < L; j++) {
                    if(i - j < 0 || visited[i - j]) return false;
                    if(map[i][col] != map[i - j][col]) return false;
                    visited[i - j] = true;
                }
            } else if (diff == 1) { // 다음 계단이 한 단계 낮음
                for(int j = 1; j <= L; j++) {
                    if(i + j >= N || visited[i + j]) return false;
                    if(map[i + 1][col] != map[i + j][col]) return false;
                    visited[i + j] = true;
                }
            }
        }
        return true;

    }



}

// [ 길 지나갈 수 있음 ]
// 1. 길에 속한 모든 칸의 높이가 모두 같아야 함
//    => 경사로 놓아서 지나갈 수 있는 길 만들 수 있음 => 높이: 항상 1, 길이 L

// [ 경 사 로 ]
// 경사로는 낮은 칸에 놓음 => L개의 연속된 칸에 경사로 바닥이 모두 접해야 함
// 낮은 칸, 높은 칸 차이 1
// 경사로 놓을 낮은 칸의 높이 모두 같아야 함, L개 칸 연속되어야 함
// 높이 차이: 1
