import java.io.*;
import java.util.*;

// 소요시간 => 00:55:52

public class 코드트리_2020_오전_2번_1 {

    private static int n;
    private static int m;
    private static int k;
    private static List<Atom>[][] map;
    private static List<Atom> list = new ArrayList<>();

    // 상, 대각, 우, 대각, 하, 대각, 좌, 대각
    private static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    private static final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    private static class Atom {
        int x;
        int y;
        int mass;
        int speed;
        int dir;

        public Atom(int x, int y, int mass, int speed, int dir) {
            this.x = x;
            this.y = y;
            this.mass = mass;
            this.speed = speed;
            this.dir = dir;
        }
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new List[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                map[i][j] = new ArrayList<Atom>();
            }
        }

        for(int i = 0; i < m; i++) {
            st = new StringTokenizer(in.readLine());

            int x = Integer.parseInt(st.nextToken()) - 1;
            int y = Integer.parseInt(st.nextToken()) - 1;
            int m = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());

            Atom at = new Atom(x, y, m, s, d);
            map[x][y].add(at);
            list.add(at);
        }

        go();

        // 여기에 코드를 작성해주세요.
        int answer = ans();
        System.out.println(answer);
    }

    private static int ans() {
        int answer = 0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < map[i][j].size(); k++) {
                    answer += map[i][j].get(k).mass;
                }
            }
        }
        return answer;
    }

    private static void go() {

        while(k-- > 0) {

            // 1. 1초 지날 때마다 d로 speed만큼 이동
            move();

            // 2. 이동 후 하나의 칸에 2개 이상 원자 처리
            check();

        }

    }

    private static void check() {
        list.clear();
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                // 1. 하나의 칸에 2개 이상 원자가 있다면
                if(map[i][j].size() >= 2) {
                    // 2. 같은 칸 원자들 질량, 속력 합함
                    int sum_mass = 0;
                    int sum_speed = 0;

                    boolean dir_even = true; // 상하좌우
                    boolean dir_odd = true; // 대각선

                    int cnt = 0;
                    for(int idx = 0; idx < map[i][j].size(); idx++) {
                        sum_mass += map[i][j].get(idx).mass;
                        sum_speed += map[i][j].get(idx).speed;


                        int dir = map[i][j].get(idx).dir;
                        if(dir % 2 == 0) {
                            dir_odd = false;
                        } else if(dir % 2 == 1) {
                            dir_even = false;
                        }
                    }


                    // 3. 합쳐진 원자는 4개로 나눔
                    // 3-1. 질량 = sum_mass / 5
                    int mass = sum_mass / 5;
                    // 3-2. 속력 = sum_speed / list.size()
                    int speed = sum_speed / map[i][j].size();

//                    System.out.println(mass + " " + speed);
                    map[i][j].clear();

                    if(mass <= 0)
                        continue;

                    // 3-3. if(전체 dir == 상하좌우 || 전체 dir == 대각선)
                    if(dir_even || dir_odd) {
                        // 나눈 네개 상하좌우
                        for(int d = 0; d < 4; d++) {
                            Atom at = new Atom(i, j, mass, speed, d * 2);
                            map[i][j].add(at);
                            list.add(at);
                        }
                    }
                    // 3-4. else
                    else {
                        // 나눈 네개 대각선
                        for(int d = 0; d < 4; d++) {
                            Atom at = new Atom(i, j, mass, speed, d * 2 + 1);
                            map[i][j].add(at);
                            list.add(at);
                        }
                    }


                }
                else {
                    for(int idx = 0; idx < map[i][j].size(); idx++) {
                        list.add(map[i][j].get(idx));
                    }
                }
            }
        }
    }

    private static void move() {
        for(int i = 0; i < list.size(); i++) {
            Atom atom = list.get(i);

            // map 처리
            map[atom.x][atom.y].remove(atom);

            for(int s = 0; s < atom.speed; s++) {
                atom.x = atom.x + dx[atom.dir];
                atom.y = atom.y + dy[atom.dir];

                if(atom.x == -1)
                    atom.x = n - 1;
                if(atom.y == -1)
                    atom.y = n - 1;
                if(atom.x == n)
                    atom.x = 0;
                if(atom.y == n)
                    atom.y = 0;
            }

            // 새로 연결
            map[atom.x][atom.y].add(atom);
        }
    }
}

// 8방향
// x: 행, y: 열
// 행, 열: 연결되어 있음

// (1) 1초 지날 때마다 d로 speed만큼 이동
// (2) 이동 후 하나의 칸에 2개 이상 원자
//     (2-1) 같은 칸 원자들 질량, 속력 합함
//     (2-2) 합쳐진 원자는 4개로 나눠짐
//     (2-3) 원자들 모두 해당 칸에 위치
//       (2-3-1) 질량: 합쳐진 질량 / 5
//       (2-3-2) 속력: 합쳐진 속력 / 합쳐진 원자 개수
//       (2-3-3) 방향: 합쳐지는 원자 방향이 전부 상하좌우 | 대각선
//                     상하좌우
//                     else 대각선
//       (2-3-4) 소숫점 아래 수 버림