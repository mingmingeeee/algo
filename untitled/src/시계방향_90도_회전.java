import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 시계방향_90도_회전 {

    public static void main(String[] args) throws Exception {

        int[][] map = new int[4][4];

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for(int i = 0; i < 4; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < 4; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int[][] next_map = new int[4][4];
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                int ox = i - 0, oy = j - 0;
                int rx = oy, ry = 4 - ox - 1;
                next_map[rx + 0][ry + 0] = map[i][j];
            }
        }

        /*
        * i = 시작x, j = 시작y
        * int ox = i - 시작x, oy = j - 시작y
        * int rx = oy (y 좌표), ry = 4 - ox (x 좌표) - 1
        * => x, y 좌표 바꿈
        * next_map[rx + x시작][ry + y시작] = map[i][j];
        * */

        // 반시계
//        int[][] next_map = new int[4][4];
//        for(int i = 0; i < 4; i++) {
//            for(int j = 0; j < 4; j++) {
//                int ox = i - 0, oy = j - 0;
//                int rx = 4 - oy - 1, ry = ox;
//                next_map[rx + 0][ry + 0] = map[i][j];
//            }
//        }

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                map[i][j] = next_map[i][j];
            }
        }


        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

}
