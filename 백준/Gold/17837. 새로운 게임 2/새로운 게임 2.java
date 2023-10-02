import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 소요시간: 39m29s37

public class Main {

    static final int WHITE = 0, RED = 1, BLUE = 2;
    static int N, K;
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};
    static int[][] map;
    static Stack<Horse>[][] horses;
    static Horse[] horseList;

    static class Horse {
        int num, x, y, d;

        public Horse(int num, int x, int y, int d) {
            this.num = num;
            this.x = x;
            this.y = y;
            this.d = d;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        horseList = new Horse[K + 1];
        map = new int[N][N];
        horses = new Stack[N][N];

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                horses[i][j] = new Stack<>();
            }
        }

        for(int i = 0; i < K; i++) {
            st = new StringTokenizer(in.readLine());
            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            horses[x][y].push(new Horse(i + 1, x, y, d));
            horseList[i + 1] = new Horse(i + 1, x, y, d);
        }

        System.out.println(simulate());

    }

    private static void moveRed(int x, int y, int curX, int curY, int i) {
        while (true) {
            Horse cur = horses[x][y].pop();
            horses[curX][curY].push(cur);
            horseList[cur.num].x = curX;
            horseList[cur.num].y = curY;
            if (cur.num == i)
                break;
        }
    }

    private static void moveWhite(int x, int y, int curX, int curY, int i) {
        Stack<Horse> stack = new Stack<>();
        while (true) {
            Horse cur = horses[x][y].pop();
            stack.push(cur);
            horseList[cur.num].x = curX;
            horseList[cur.num].y = curY;
            if (cur.num == i)
                break;
        }
        while (!stack.isEmpty()) {
            Horse cur = stack.pop();
            horses[curX][curY].push(cur);
        }
    }

    private static int simulate() {

        int t = 0;
        while(t++ < 1000) {
            for (int i = 1; i <= K; i++) {
                int x = horseList[i].x, y = horseList[i].y;
                int d = horseList[i].d;
                int curX = x + dx[d];
                int curY = y + dy[d];

                if (!isRange(curX, curY) || map[curX][curY] == BLUE) {
                    // A번 이동 방향 반대로 하고 한 칸 이동
                    // 방향 바꾼 후 이동하려는 칸이 파란색인 경우에는 이동 하지 않고 가만히 있음

                    if (d == 0) {
                        d = 1;
                    } else if (d == 1) {
                        d = 0;
                    } else if (d == 2) {
                        d = 3;
                    } else if (d == 3) {
                        d = 2;
                    }

                    horseList[i].d = d;


                    curX = x + dx[d];
                    curY = y + dy[d];

                    if (isRange(curX, curY) && map[curX][curY] != BLUE) {
                        if(map[curX][curY] == RED) {
                            moveRed(x, y, curX, curY, i);
                        } else if(map[curX][curY] == WHITE) {
                            moveWhite(x, y, curX, curY, i);
                        }
                    }
                } else if (map[curX][curY] == WHITE) {
                    // 말 순서 바꾸기
                    moveWhite(x, y, curX, curY, i);
                } else if (map[curX][curY] == RED) {
                    // 이동
                    moveRed(x, y, curX, curY, i);
                }

                if(isRange(curX, curY) && horses[curX][curY].size() >= 4) return t;

            }
        }

        return -1;

    }


    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N)
            return false;
        return true;
    }

}

// N * N 체스판
// 하나의 말 위에 다른 말 올리기 가능
// 체스판: 흰, 빨, 파
// 말: 1~K번, 이동 방향도 정해져있음 (4방향)

// 0. 1번부터 K번 말까지 순서대로 이동시키는 것 (한 말이 이동할 때 그 위에 있던 말도 함께 이동)
// => 말이 4개 이상 쌓이는 순간 게임 종료
// 1. A번 말이 이동하려는 칸이
// 1-1. 흰색인 경우: 이동
// 1-1-1. 이미 말이 있는 경우: 가장 위에 A번 말 올려놓음
// 2. 빨간색인 경우: 이동 후에 A번 이상 쌓여 있는 순서 반대로 바꿈