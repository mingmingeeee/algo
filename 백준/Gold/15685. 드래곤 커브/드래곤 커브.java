import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static int[] dx = {0, -1, 0, 1};
    private static int[] dy = {1, 0, -1, 0};
    private static boolean[][] map = new boolean[101][101];

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        int N = Integer.parseInt(st.nextToken());

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(in.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int g = Integer.parseInt(st.nextToken());

            curvMap(x, y, d, g);
        }

        int ans = 0;
        for(int i = 0; i < 100; i++) {
            for(int j = 0; j < 100; j++) {
                if(map[i][j] && map[i + 1][j] && map[i][j + 1] && map[i + 1][j + 1])
                    ans++;
            }
        }

        System.out.println(ans);

    }

    private static void curvMap(int x, int y, int d, int g) {

        List<Integer> list = new ArrayList<>();
        list.add(d);


        for(int i = 1; i <= g; i++) {
            for(int j = list.size() - 1; j >= 0; j--) {
                list.add((list.get(j) + 1) % 4);
            }
        }

        map[x][y] = true;

        for(Integer direction : list) {
            x += dx[direction];
            y += dy[direction];
            map[x][y] = true;
        }

    }



}