import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static int N, M, K;
    static Queue<FireBall>[][] map;
    static Queue<Pos> positions = new ArrayDeque<>();
    static Queue<FireBall> fireballs = new ArrayDeque<>();
    static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    static class FireBall {
        int r, c, m, s, d;

        public FireBall(int r, int c, int m, int s, int d) {
            this.r = r;
            this.c = c;
            this.m = m;
            this.s = s;
            this.d = d;
        }
    }

    static class Pos {
        int r, c;
        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new ArrayDeque[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = new ArrayDeque<>();
            }
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            fireballs.offer(new FireBall(r, c, m, s, d));
        }


        simulate();

        int answer = 0;
        while(!fireballs.isEmpty()) {
            answer += fireballs.poll().m;
        }
        System.out.println(answer);

    }

    private static void simulate() {

        while(K-- > 0) {
            while(!fireballs.isEmpty()) {
                FireBall ball = fireballs.poll();
                move(ball);
            }

            while(!positions.isEmpty()) {
                Pos curPos = positions.poll();

                int curX = curPos.r;
                int curY = curPos.c;

                int sumS = 0, sumM = 0;
                int size = map[curX][curY].size();

                if(size == 1) {
                    fireballs.offer(map[curX][curY].poll());
                    continue;
                }

                boolean isEven = true, isOdd = true;
                while(!map[curX][curY].isEmpty()) {
                    FireBall ball = map[curX][curY].poll();
                    sumM += ball.m; sumS += ball.s;
                    if(ball.d % 2 == 0) isOdd = false;
                    else isEven = false;
                }

                sumM /= 5;
                if(sumM == 0)
                    continue;
                sumS /= size;

                for(int i = 0; i <=6; i += 2) {
                    if(isOdd || isEven) fireballs.offer(new FireBall(curX, curY, sumM, sumS, i));
                    else fireballs.offer(new FireBall(curX, curY, sumM, sumS, i + 1));
                }

            }
        }

    }


    private static void move(FireBall ball) {
        int r = (N + ball.r + dx[ball.d] * (ball.s % N)) % N;
        int c = (N + ball.c + dy[ball.d] * (ball.s % N)) % N;


        ball.r = r; ball.c = c;

        positions.offer(new Pos(r, c));
        map[r][c].offer(ball);
    }


}
