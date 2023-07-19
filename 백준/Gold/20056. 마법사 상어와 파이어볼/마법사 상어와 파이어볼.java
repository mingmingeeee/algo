import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;




public class Main {

    static int N, M, K;
    static List<FireBall>[][] map;
    static List<FireBall> list = new ArrayList<>();
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

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new List[N][N];

        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                map[i][j] = new ArrayList<>();
            }
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(in.readLine());
            int r = Integer.parseInt(st.nextToken()) - 1;
            int c = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            FireBall ball = new FireBall(r, c, m, s, d);
            list.add(ball);
            map[r][c].add(ball);
        }



        simulate();

        int answer = count();
        System.out.println(answer);

    }

    private static int count() {
        int sum = 0;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (FireBall ball : map[i][j]) {
                    sum += ball.m;
                }
            }
        }
        return sum;
    }

    private static void simulate() {

        while(K-- > 0) {

            balls();

        }

    }

    private static void balls() {


        for (int i = list.size() - 1; i >= 0; i--) {

            FireBall ball = list.get(i);

            move(ball);

        }



        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(map[i][j].size() <= 1) {
                    for(FireBall ball : map[i][j]) {
                        list.add(ball);
                    }
                    continue;
                }
                int m = 0, s = 0;
                boolean isEven = true;
                boolean isOdd = true;

                for(FireBall ball : map[i][j]) {
                    m += ball.m; s += ball.s;
                    if(ball.d % 2 == 0) {
                        isOdd = false;
                    } else if(ball.d % 2 == 1) {
                        isEven = false;
                    }
                }

                int size = map[i][j].size();
                int sumM = m / 5;
                map[i][j].clear();
                if(sumM == 0)
                    continue;
                int sumS = s / size;
                if(isEven || isOdd) {
                    for(int d = 0; d <= 6; d+= 2) {
                        FireBall ball = new FireBall(i, j, sumM, sumS, d);
                        map[i][j].add(ball);
                        list.add(ball);
                    }
                } else {
                    for(int d = 1; d <= 7; d+= 2) {
                        FireBall ball = new FireBall(i, j, sumM, sumS, d);
                        map[i][j].add(ball);
                        list.add(ball);
                    }
                }



            }

        }

    }

    private static void move(FireBall ball) {

        int r = (N + ball.r + dx[ball.d] * (ball.s % N)) % N;
        int c = (N + ball.c + dy[ball.d] * (ball.s % N)) % N;

        list.remove(ball);
        map[ball.r][ball.c].remove(ball);

        ball.r = r;
        ball.c = c;

        map[ball.r][ball.c].add(ball);

    }

//    private static int isRange(int pos, int d, int s, int type) {
//        for(int cnt = 0; cnt < s; cnt++) {
//            if(type == 0)
//                pos += dx[d];
//            else
//                pos += dy[d];
//
//            if(pos == -1)
//                pos = N - 1;
//            if(pos == N)
//                pos = 0;
//        }
//        return pos;
//    }

}

// 파이어볼
// 위치 (r, c) | 질량 m | 방향 d | 속력 s
// 파이어볼 방향: 8 방향
// 1. d로 속력 s칸 만큼 이동
// 2. 이동이 끝난 뒤 2개 이상의 파이어 볼 있다면
//    2-1. 같은 칸에 있는 파이어볼 합치기
//    2-2. 파이어볼은 4개로 나누어짐
//    2-3. 나누어진 파이어볼 질량, 속력, 방향
//         2-3-1. 질량: 합친 질량/5
//         2-3-2. 속력: 합친 속력/합친 개수
//         2-3-3. 방향이 모두 홀수 OR 짝수: 방향 - 0, 2, 4, 6 | 아님: 1, 3, 5, 7
//         2-3-4. 질량 0이면 소멸되어 없어짐
// 이동 K번, 남아있는 파이어볼 질량 합 구하기