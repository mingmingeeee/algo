import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

public class Main {

    private static int N;
    private static int[] A;
    private static int[] op = new int[4];
    // { +. -. *, ÷}


    private static int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        N = Integer.parseInt(st.nextToken());
        A = new int[N];

        st = new StringTokenizer(in.readLine());
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(in.readLine());
        int idx = 0;
        for(int i = 0; i < 4; i++) {
            op[i] = Integer.parseInt(st.nextToken());
        }

        dfs(A[0], 1);

        System.out.println(max + "\n" + min);
    }

    private static void dfs(int sum, int idx) {

        if(idx == N) {
            min = Math.min(min, sum);
            max = Math.max(max, sum);
            return;
        }

        for(int i = 0; i < 4; i++) {
            if(op[i] > 0) {
                op[i]--;
                switch (i) {
                    case 0:
                        dfs(sum + A[idx], idx + 1);
                        break;
                    case 1:
                        dfs(sum - A[idx], idx + 1);
                        break;
                    case 2:
                        dfs(sum * A[idx], idx + 1);
                        break;
                    case 3:
                        dfs(sum / A[idx], idx + 1);
                        break;
                }

                op[i]++;
            }
        }


    }

}

// [2 1 1 2]
// [1 1 2 1 3 1]
// [3 3 0 0]

// [1 2 2 2]
// [2 1 3 1 1 4]
// [3 3 0 0]
