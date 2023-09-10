import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 2h24m31s64

public class 대충_복기 {

    private static int N;
    private static char[][] map;
    private static int[][] score;
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};
    private static int[] scores = {1, 2, 4, 8};
    private static char[] alphabets;
    private static Map<Character, Pair> keys = new HashMap<>();
    private static boolean[] visited;
    private static boolean isFound = false, isDone = false;
    private static List<Character> list = new ArrayList<>();

    private static class Pair {
        int x, y, d;
        Pair prev;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
            this.d = -1;
        }

    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());

        map = new char[N][N];
        score = new int[N][N];
        for(int i = 0; i < N; i++) {
            String[] line = in.readLine().split("");
            for(int j = 0; j < N; j++) {
                map[i][j] = line[j].charAt(0);
                if(map[i][j] != '#' && map[i][j] != '.')
                    keys.put(map[i][j], new Pair(i, j));
            }
        }

        for(Character key : keys.keySet()) {
            list.add(key);
        }
        alphabets = new char[list.size()];
        visited = new boolean[list.size()];
        simulate();


        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                System.out.printf("%3d", score[i][j]);
            }
            System.out.println();
        }

    }

    private static void simulate() {

        perm(0);

    }

    private static void perm(int cnt) {

        if(isDone) return;

        if(cnt == list.size()) {

            boolean isTrue = true;

            boolean[][] v = new boolean[N][N];

            int[][] tscore = new int[N][N];

            for(int i = 0; i < list.size(); i++) {
                isFound = false;
                int x = keys.get(alphabets[i]).x;
                int y = keys.get(alphabets[i]).y;
                connect(x, y, x, y, alphabets[i], v, tscore);


                if(!isFound) {
                    isTrue = false;
                    break;
                }
            }

            if(isTrue) {
                for(int i = 0; i < N; i++) {
                    for(int j = 0; j < N; j++) {
                        score[i][j] = tscore[i][j];
                    }
                }
                isDone = true;
            }

            return;
        }

        for(int i = 0; i < list.size(); i++) {
            if(visited[i]) continue;
            alphabets[cnt] = list.get(i);
            visited[i] = true;
            perm(cnt + 1);
            visited[i] = false;
        }

    }
    private static void connect(int sx, int sy, int x, int y, char target, boolean[][] v, int[][] tscore) {
        Queue<Pair> queue = new ArrayDeque<>();
        boolean[][] tvisited = new boolean[N][N];


        queue.offer(new Pair(x, y));
        tvisited[x][y] = true;

        while(!queue.isEmpty()) {

            Pair cur = queue.poll();

            if(map[cur.x][cur.y] == target && !(sx == cur.x && sy == cur.y)) {

                ///////////// 경로 저장 /////////////////
                while(cur != null) {
                    v[cur.x][cur.y] = true;
                    if(cur.d > -1) {
                        tscore[cur.x][cur.y] += scores[cur.d];
                        tscore[cur.x + dx[cur.d]][cur.y + dy[cur.d]] += scores[cur.d];
                    }
                    cur = cur.prev;
                    isFound = true;
                }

                return;
            }

            for(int d = 0; d < 4; d++) {
                int curX = cur.x + dx[d];
                int curY = cur.y + dy[d];

                if(!isRange(curX, curY)) continue;
                if(!(map[curX][curY] == target || map[curX][curY] == '.')) continue;
                if(tvisited[curX][curY] || v[curX][curY]) continue;

                Pair new_pair = new Pair(curX, curY);
                queue.offer(new_pair);
                cur.d = d;
                new_pair.prev = cur;

            }

        }
    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= N || y >= N) {
            return false;
        }
        return true;
    }


}
