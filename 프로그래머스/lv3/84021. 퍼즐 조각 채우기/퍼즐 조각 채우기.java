
import java.util.*;

class Solution {

    static int N, M, num = 1, answer;
    static boolean[][] visited;
    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    static class Pair implements Comparable<Pair>{
        int x, y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair o) {
            if(this.x == o.x)
                return this.y - o.y;
            return this.x - o.x;
        }
    }

    public int solution(int[][] game_board, int[][] table) {

        N = table.length;
        M = table[0].length;
        visited = new boolean[N][M];


        // 퍼즐 조각
        Map<Integer, List<Pair>> list = new HashMap<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(visited[i][j]) continue;
                if(table[i][j] == 0) continue;

                if(list.get(num) == null)
                    list.put(num, new ArrayList<Pair>());
                search(table, i, j, i, j, list, 0);
                Collections.sort(list.get(num));
                num++;
            }
        }

        // 빈 칸 조각
        visited = new boolean[N][M];
        num = 1;
        Map<Integer, List<Pair>> emptyList = new HashMap<>();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(visited[i][j]) continue;
                if(game_board[i][j] == 1) continue;

                if(emptyList.get(num) == null)
                    emptyList.put(num, new ArrayList<Pair>());
                search(game_board, i, j, i, j, emptyList, 1);
                Collections.sort(emptyList.get(num));
                num++;
            }
        }

        check(list, emptyList);

        return answer;
    }

    static boolean rotate(List<Pair> l, List<Pair> el) {
        for(int d = 0; d < 4; d++) {
            boolean isTrue = true;


            int x = l.get(0).x, y = l.get(0).y;
            for(int j = 0; j < l.size(); j++) {
                l.get(j).x -= x;
                l.get(j).y -= y;
            }


            for(int j = 0; j < l.size(); j++) {
                int lx = l.get(j).x, ly = l.get(j).y;
                int elx = el.get(j).x, ely = el.get(j).y;

                if(lx != elx || ly != ely) {
                    isTrue = false;
                    break;
                }
            }

            if(isTrue) return true;

            for (int j = 0; j < l.size(); j++) {
                int tmp = l.get(j).x;
                l.get(j).x = l.get(j).y;
                l.get(j).y = -tmp;
            }
            Collections.sort(l);
        }

        return false;
    }


    static void check(Map<Integer, List<Pair>> list, Map<Integer, List<Pair>> emptyList) {
        boolean[] list_visited = new boolean[list.size() + 1];
        for(int i = 1; i <= emptyList.size(); i++) {
            List<Pair> el = emptyList.get(i);
            for(int j = 1; j <= list.size(); j++) {
                List<Pair> l = list.get(j);
                if(list_visited[j]) continue;
                if(el.size() != l.size()) continue;

                if(rotate(l, el)) {
                    list_visited[j] = true;
                    answer += l.size();
                    break;
                }

            }
        }

    }

    static void search(int[][] table, int x, int y, int sx, int sy, Map<Integer, List<Pair>> list, int target) {

        if(x < 0 || y < 0 || x >= N || y >= M) return;
        if(visited[x][y]) return;
        if(table[x][y] == target) return;

        visited[x][y] = true;
        list.get(num).add(new Pair(x - sx, y - sy));

        for(int d = 0; d < 4; d++) {
            int curX = x + dx[d];
            int curY = y + dy[d];

            search(table, curX, curY, sx, sy, list, target);
        }

    }
}