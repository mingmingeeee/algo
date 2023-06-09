import java.util.*;

// 해설 참고
// (1) 상하좌우 이어진 거
// (2) 나눠진 원자 dir 값 구현

// 구조
// class: Pair (위치), Atom (원자 속성)
// (1) nextPos: 위치 구하기 (상하좌우 이어짐 존재)
// (2) moveAll: 초마다 이동 (nextGrid) [nextPos 사용]
// (3) split: 한 칸에 원자 2개 이상일 때 나눔
// (4) compound: nextGrid -> grid로 옮기기
// (5) simulate: nextGrid 초기화 -> moveAll -> split

public class 코드트리_2020_오전_2번_2 {

    static class Pair {
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Atom {
        int mass, velocity, dir;

        public Atom(int mass, int velocity, int dir) {
            this.mass = mass;
            this.velocity = velocity;
            this.dir = dir;
        }
    }

    static final int DIR_NUM = 8;
    static final int MAX_N = 50;

    static int n, m, k;
    static ArrayList<Atom>[][] grid = new ArrayList[MAX_N][MAX_N];
    static ArrayList<Atom>[][] nextGrid = new ArrayList[MAX_N][MAX_N];

    static boolean isRange(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < n;
    }

    static Pair nextPos(int x, int y, int v, int moveDir) {
        int[] dx = new int[]{-1, -1, 0, 1, 1,  1,  0, -1};
        int[] dy = new int[]{ 0,  1, 1, 1, 0, -1, -1, -1};

        // 움직인 이후 값이 음수가 되는 경우, 이를 양수로 쉽게 만들기 위해서는
        // n의 배수이며 더했을 때 값을 항상 양수로 만들어 주는 수인 nv를 더해주면 됩니다.
        int nx = (x + dx[moveDir] * v + n * v) % n;
        int ny = (y + dy[moveDir] * v + n * v) % n;

        return new Pair(nx, ny);
    }

    static void moveAll() {
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                for(int i = 0; i < grid[x][y].size(); i++) {
                    int w = grid[x][y].get(i).mass;
                    int v = grid[x][y].get(i).velocity;
                    int moveDir = grid[x][y].get(i).dir;

                    Pair nPos = nextPos(x, y, v, moveDir);
                    int nextX = nPos.x, nextY = nPos.y;

                    nextGrid[nextX][nextY].add(new Atom(w, v, moveDir));
                }
            }
        }
    }

    static void split(int x, int y) {
        int sumOfMass = 0;
        int sumOfVelocity = 0;
        int[] numOfDirType = new int[2];

        for(int i = 0; i < nextGrid[x][y].size(); i++) {
            int w = nextGrid[x][y].get(i).mass;
            int v = nextGrid[x][y].get(i).velocity;
            int moveDir = nextGrid[x][y].get(i).dir;

            sumOfMass += w;
            sumOfVelocity += v;
            numOfDirType[moveDir % 2]++;
        }

        int startDir;

        if(numOfDirType[0] == 0 || numOfDirType[1] == 0) {
            startDir = 0;
        }
        else
            startDir = 1;

        int atomCnt = nextGrid[x][y].size();

        for(int moveDir = startDir; moveDir < DIR_NUM; moveDir += 2) {
            if(sumOfMass / 5 > 0) {
                grid[x][y].add(
                        new Atom(sumOfMass / 5,
                                sumOfVelocity / atomCnt,
                                moveDir)
                );
            }
        }
    }

    static void compound() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = new ArrayList<>();
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                int atomCnt = nextGrid[i][j].size();
                if(atomCnt == 1)
                    grid[i][j].add(nextGrid[i][j].get(nextGrid[i][j].size() - 1));
                else if(atomCnt > 1)
                    split(i, j);
            }
        }
    }

    static void simulate() {
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                nextGrid[i][j] = new ArrayList<>();
            }
        }

        moveAll();

        compound();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        k = sc.nextInt();

        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                grid[i][j] = new ArrayList<>();

        for(int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int m = sc.nextInt();
            int s = sc.nextInt();
            int d = sc.nextInt();

            grid[x - 1][y - 1].add(new Atom(m, s, d));
        }

        // k초에 걸쳐 시뮬레이션을 반복합니다.
        while(k-- > 0)
            simulate();

        int ans = 0;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                for(int k = 0; k < grid[i][j].size(); k++)
                    ans += grid[i][j].get(k).mass;

        System.out.print(ans);
    }

}