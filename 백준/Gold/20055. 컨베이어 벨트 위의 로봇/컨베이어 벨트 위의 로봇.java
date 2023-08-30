import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 24m31s64

public class Main {

    private static int N, K, ans, start, end;
    private static int[] A;
    private static boolean[] robot;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(in.readLine());

        A = new int[2 * N];
        robot = new boolean[2 * N];
        for(int i = 0; i < 2 * N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        simulate();

        System.out.println(ans);

    }

    private static void simulate() {

        start = 0;
        end = N - 1;

        while(true) {
            // 1. 회전

            rotate();
            
            if(A[start] > 0) {
                robot[start] = true;
                A[start]--;
            }

            ans++;

            if(check()) break;

        }

    }

    private static void print() {

        for(int i = 0; i < 2 * N; i++) {
            System.out.print(A[i] + " ");
        }
        for(int i = 0; i < 2 * N; i++) {
            System.out.print(robot[i] + " ");
        }
        System.out.println();
    }

    private static void rotate() {

        int tmp = A[2 * N - 1];
        for(int i = 2 * N - 1; i > 0; i--)  {
            A[i] = A[i - 1];
        }
        A[0] = tmp;
        for(int i = end; i > 0; i--) {
            robot[i] = robot[i - 1];
        }
        robot[0] = false;

        robot[end] = false;
        for(int i = end; i > 0; i--) {
            if(robot[i] || A[i] < 1) continue;
            if(robot[i - 1]) {
                robot[i] = robot[i - 1];
                robot[i - 1] = false;
                if(A[i] > 0)
                    A[i]--;
            }
        }

    }

    private static boolean check() {
        int cnt = 0;
        for(int i = 0; i < 2 * N; i++) {
            if(A[i] == 0) {
                cnt++;
            }
        }
        if(cnt >= K)
            return true;

        return false;
    }

}

// 1번부터 2N-1번까지의 칸은 다음 번호의 칸이 있는 위치로 이동
// i번 칸의 내구도: Ai
// 올리는 위치: 1번 칸, 내리는 위치: N번 칸
// 로봇이 올리는 위치에만 올릴 수 있음 => 언제든지 로봇이 내리는 위치에 도달하면 즉시 내림

// 1. 벨트가 각 칸에 있는 로봇과 함께 회전
// 2. 가장 먼저 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동
// (이동: 로봇 없고 내구도 1 이상)
// 3. 올리는 위치에 있는 칸으 ㅣ내구도가 0이 아니면 올리는 위치에 로봇 올림
// 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정 종료