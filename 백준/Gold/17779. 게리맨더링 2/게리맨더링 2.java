import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 소요시간: 1h22m58s8

public class Main {

    static int N, answer = Integer.MAX_VALUE;
    static int[][] map;
    static int[][] zone;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        map = new int[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        simulate();

        System.out.println(answer);

    }

    private static void simulate() {

        // d1, d2 >= 1
        // 1 <= x < x + d1 + d2 <= N
        // 1 <= y - d1 < y < y + d2 <= N
        int n = N / 2;

        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for(int d1 = 1; d1 < N; d1++) {
                    for(int d2 = 1; d2 < N; d2++) {
                        makeLine(i, j, d1, d2);
                    }
                }
                makeFiveZone(i, j);
            }
        }


    }

    private static void makeFiveZone(int x, int y) {

        makeLine(x, y, 1, 1);

    }

    private static void makeLine(int x, int y, int d1, int d2) {
        zone = new int[N][N];

        // 경계선 1
        int curX = x, curY = y;
        while(curX <= x + d1 && curY >= y - d1) {
            if(!isRange(curX, curY)) return;
            zone[curX][curY] = 5;
            curX++; curY--;
        }

        // 경계선 2
        curX = x; curY = y;
        while(curX <= x + d1 && curY <= y + d2) {
            if(!isRange(curX, curY)) return;
            zone[curX][curY] = 5;
            curX++; curY++;
        }

        // 경계선 3
        curX = x + d1; curY = y - d1;
        while(curX <= x + d1 + d2 && curY <= y - d1 + d2) {
            if(!isRange(curX, curY)) return;
            zone[curX][curY] = 5;
            curX++; curY++;
        }

        // 경계선 4
        curX = x + d2; curY = y + d2;
        while(curX <= x + d2 + d1 && curY >= y + d2 - d1) {
            if(!isRange(curX, curY)) return;
            zone[curX][curY] = 5;
            curX++; curY--;
        }

        for(int i = x + 1; i < x + d2 + d1; i++) {
            for(int j = y - d1; j <= y + d2; j++) {
                if(zone[i][j] == 5) {
                    j += 1;
                    while(j <= y + d2 && zone[i][j] == 0) {
                        zone[i][j] = 5;
                        j++;
                    }
                    break;
                }
            }
        }

        if(makeZone(x, y, d1, d2)) {
            int[] score = new int[5];
            for(int i = 0; i < N; i++) {
                for(int j = 0; j < N; j++) {
                    if(zone[i][j] == 1)
                        score[0] += map[i][j];
                    else if(zone[i][j] == 2)
                        score[1] += map[i][j];
                    else if(zone[i][j] == 3)
                        score[2] += map[i][j];
                    else if(zone[i][j] == 4)
                        score[3] += map[i][j];
                    else if(zone[i][j] == 5)
                        score[4] += map[i][j];
                }
            }

            Arrays.sort(score);

            answer = Math.min(answer, score[4] - score[0]);
        }


    }

    private static boolean makeZone(int x, int y, int d1, int d2) {

        int cnt = 0;
        // 1번
        for(int i = 0; i < x + d1; i++) {
            for(int j = 0; j <= y; j++) {
                if(zone[i][j] == 5) break;
                zone[i][j] = 1;
                cnt++;
            }
        }
        if(cnt == 0) return false;

        // 2번
        cnt = 0;
        for(int i = 0; i <= x + d2; i++) {
            for(int j = y + 1; j < N; j++) {
                if(zone[i][j] == 5) continue;
                zone[i][j] = 2;
                cnt++;
            }
        }
        if(cnt == 0) return false;

        // 3번
        cnt = 0;
        for(int i = x + d1; i < N; i++) {
            for(int j = 0; j < y - d1 + d2; j++) {
                if(zone[i][j] == 5) break;
                zone[i][j] = 3;
                cnt++;
            }
        }
        if(cnt == 0) return false;

        // 4번
        cnt = 0;
        for(int i = x + d2 + 1; i < N; i++) {
            for(int j = y - d1 + d2; j < N; j++) {
                if(zone[i][j] == 5) continue;
                zone[i][j] = 4;
                cnt++;
            }
        }
        if(cnt == 0) return false;


        return true;

    }

    private static void print() {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.print(zone[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }

}

// N * N 격자
// 격자의 각 칸은 구역을 의미함
// 구역을 다섯 개의 선거구로 나눠야 함
// => 선거구는 구역을 적어도 하나 포함해야 하고, 한 선거구에 포함되어있는 구역은 모두 연결되어 있어야 함
// [ 선거구 나누는 방법 ]
// 1. 기준점 (x, y)와 경계의 길이 d1, d2 정함
// 2. 다음 칸은 경계선
// 2-1. (x, y), (x + 1, y - 1) ... , (x + d1, y - d1)
// 3. 경계선과 경계선의 안에 포함되어 있는 곳은 5번 선거구
// 4. 5번 선거구에 포함되지 않은 구역 (r, c)의 선거구 번호는 기준 따름

// 인구가 가장 많은 선거구와 가장 적은 선거구의 인구 차이 최소값 구하기

