import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.*;

// 단순 구현

public class Main {

    private static int r, c, k;
    private static int n = 3, m = 3;
    private static int[][] A = new int[100][100];

    private static class Pair implements Comparable<Pair> {
        int num, cnt;

        public Pair(int num, int cnt) {
            this.num = num;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Pair o) {
            if(this.cnt == o.cnt) {
                return this.num - o.num;
            }
            return this.cnt - o.cnt;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < n; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = simulate();

        System.out.println(ans);
    }

    private static int simulate() {

        int answer = 0;
        while(answer < 101) {
            if (check()) return answer;

            if (n >= m)
                R_sort();
            else
                C_sort();

            answer++;
        }

        return -1;

    }

    private static void C_sort() {
        List<Pair>[] list = new ArrayList[m];
        for(int i = 0; i < m; i++) {
            list[i] = new ArrayList<Pair>();
        }

        int maxR = 0;
        for(int i = 0; i < m; i++) {
            int[] nums = new int[101];
            for(int j = 0; j < n; j++) {
                nums[A[j][i]]++;
            }
            for(int c = 1; c < 101; c++) {
                if(nums[c] > 0)
                    list[i].add(new Pair(c, nums[c]));
            }

            Collections.sort(list[i]);

            maxR = Math.max(list[i].size(), maxR);
        }

        for(int i = 0; i < m; i++) {
            int k = 0;
            for (int j = 0; j < list[i].size(); j++) {
                A[k++][i] = list[i].get(j).num;
                A[k++][i] = list[i].get(j).cnt;
            }
            for(int j = k; j < 100; j++) {
                A[j][i] = 0;
            }
        }

        n = maxR * 2;
    }

    private static void R_sort() {
        List<Pair>[] list = new ArrayList[n];
        for(int i = 0; i < n; i++) {
            list[i] = new ArrayList<Pair>();
        }

        int maxR = 0;
        for(int i = 0; i < n; i++) {
            int[] nums = new int[101];
            for(int j = 0; j < m; j++) {
                nums[A[i][j]]++;
            }
           for(int c = 1; c < 101; c++) {
               if(nums[c] > 0)
                   list[i].add(new Pair(c, nums[c]));
           }

           Collections.sort(list[i]);

           maxR = Math.max(list[i].size(), maxR);
        }


        for(int i = 0; i < n; i++) {
            int k = 0;

            for (int j = 0; j < list[i].size(); j++) {
                A[i][k++] = list[i].get(j).num;
                A[i][k++] = list[i].get(j).cnt;
            }
            for(int j = k; j < 100; j++) {
                A[i][j] = 0;
            }
        }

        m = maxR * 2;

//        for(int i = 0; i < n; i++) {
//            for(int j = 0; j < m; j++) {
//                System.out.print(A[i][j] + " ");
//            }
//            System.out.println();
//        }

    }

    private static boolean check() {
        return A[r - 1][c - 1] == k ? true : false;
    }

    private static boolean isRange(int x, int y) {
        if(x < 0 || y < 0 || x >= 100 || y >= 100)
            return false;
        return true;
    }

}

// [2 1 1 2]
// [1 1 2 1 3 1]
// [3 3 0 0]

// [1 2 2 2]
// [2 1 3 1 1 4]
// [3 3 0 0]

// [