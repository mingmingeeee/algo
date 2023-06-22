import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 풀이
public class 코드트리_상반기_오전_2번_2 {

    public static final int DIR_NUM = 4;
    public static final int MAX_N = 29;

    // 변수 선언
    public static int n;
    public static int[][] arr = new int[MAX_N][MAX_N];
    public static int[][] nextArr = new int[MAX_N][MAX_N];

    // 그룹 개수 관리
    public static int groupN;

    // 각 칸에 그룹 번호 적어줌
    public static int[][] group = new int[MAX_N][MAX_N];
    public static int[] groupCnt = new int[MAX_N * MAX_N + 1];

    public static boolean[][] visited = new boolean[MAX_N][MAX_N];

    public static int[] dx = new int[]{1, -1, 0, 0};
    public static int[] dy = new int[]{0, 0, 1, -1};

    public static boolean inRange(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    // (x, y) 위치에서 DFS 진행
    public static void dfs(int x, int y) {
        for(int i = 0; i < DIR_NUM; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(inRange(nx, ny) && !visited[nx][ny] && arr[nx][ny] == arr[x][y]) {
                visited[nx][ny] = true;
                group[nx][ny] = groupN;
                groupCnt[groupN]++;
                dfs(nx, ny);
            }
        }
    }

    // 그룹 만들기
    public static void makeGroup() {
        groupN = 0;

        // visited 값 초기화
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                visited[i][j] = false;
            }
        }

        // DFS 이용하여 그룹 묶는 작업 진행
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(!visited[i][j]) {
                    groupN++;
                    visited[i][j] = true;
                    group[i][j] = groupN;
                    groupCnt[groupN] = 1;
                    dfs(i, j);
                }
            }
        }
    }

    // 예술 점수 계산
    public static int getArtScore() {
        int artScore = 0;

        // 서로 맞닿아있는 개수 먼저 구할 필요 없이
        // 더하는 것이 곧 (* n)이다...
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < DIR_NUM; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];
                    if(inRange(nx, ny) && arr[i][j] != arr[nx][ny]) {
                        int g1 = group[i][j];
                        int g2 = group[nx][ny];
                        int num1 = arr[i][j];
                        int num2 = arr[nx][ny];
                        int cnt1 = groupCnt[g1];
                        int cnt2 = groupCnt[g2];

                        artScore += (cnt1 + cnt2) * num1 * num2;
                    }
                }
            }
        }

        // 중복 계산 제외 => 맞닿은 서로의 값 즉 (답 * 2)가 구해질 테니까
        return artScore / 2;
    }

    public static int getScore() {
        // 1. 그룹 형성
        makeGroup();

        // 2. 예술 점수 계산
        return getArtScore();
    }

    // 정사각형 시계 방향으로 90도 회전
    public static void rotateSquare(int sx, int sy, int squareN) {
        for(int x = sx; x < sx + squareN; x++) {
            for(int y = sy; y < sy + squareN; y++) {
                int nx = x - sx;
                int ny = y - sy;
                int rx = ny;
                int ry = squareN - 1 - nx;
                nextArr[rx + sx][ry + sy] = arr[x][y];
            }
        }
    }

    public static void rotate() {
        // 1. next arr 초기화
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                nextArr[i][j] = 0;
            }
        }

        // 2. 회전 진행
        // 2 - 1. 십자 모양 반시계 회전 진행
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                // 2 - 1 - 1. 세로줄에 대해서는 (i, j) -> (j, i)가 됨
                if(j == n / 2)
                    nextArr[j][i] = arr[i][j];

                // 2 - 1 - 2. 가로줄에 대해서는 (i, j) -> (n - j - 1, i)가 됨
                else if(i == n / 2)
                    nextArr[n - j - 1][i] = arr[i][j];
            }
        }

        // 2 - 2. 4개의 정사각형에 대해 시계 방향 회전 진행
        int squareN = n / 2;
        rotateSquare(0, 0, squareN);
        rotateSquare(0, squareN + 1, squareN);
        rotateSquare(squareN + 1, 0, squareN);
        rotateSquare(squareN + 1, squareN + 1, squareN);

        // 3. next arr 값 다시 옮겨줌
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                arr[i][j] = nextArr[i][j];
            }
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        n = Integer.parseInt(st.nextToken());

        for(int i = 0; i < n; i++) {
            st = new StringTokenizer(in.readLine());
            for(int j = 0; j < n; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int ans = 0;

        // 0 ~ 3회전까지의 예술 점수 더해줌
        for(int i = 0; i < 4; i++) {
            ans += getScore();

            rotate();
        }

        System.out.println(ans);
    }

}
