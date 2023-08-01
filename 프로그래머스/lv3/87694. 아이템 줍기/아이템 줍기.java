import java.util.*;

class Solution {
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static int[][] map = new int[101][101];
    
    private static class Pair {
        int x, y, cnt;
        
        public Pair(int x, int y, int cnt) {
            this.x = x;
            this.y = y;
            this.cnt = cnt;
        }
    }
    public int solution(int[][] rectangle, int characterX, int characterY, int itemX, int itemY) {
        int answer = 0;
        
        rectangle_make(rectangle);
        answer = bfs(characterX * 2, characterY * 2, itemX * 2, itemY * 2);
        
        return answer / 2;
    }
    
    private static int bfs(int startX, int startY, int endX, int endY) {
        Queue<Pair> queue = new ArrayDeque<>();
        queue.offer(new Pair(startX, startY, 0));
        
        boolean[][] visited = new boolean[101][101];
        visited[startX][startY] = true;
        
        while(!queue.isEmpty()) {
            Pair cur = queue.poll();
            
            if(cur.x == endX && cur.y == endY)
                return cur.cnt;
            
            for(int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                
                if(nx < 0 || ny < 0 || ny >= 101 || nx >= 101) continue;
                if(map[nx][ny] != 1 || visited[nx][ny]) continue;
                visited[nx][ny] = true;
                queue.offer(new Pair(nx, ny, cur.cnt + 1));
            }
        }
        
        return 0;
    }
    
    private static void rectangle_make(int[][] rectangle) {
        for(int i = 0; i < rectangle.length; i++) {
            int x1 = rectangle[i][0] * 2;
            int y1 = rectangle[i][1] * 2;
            int x2 = rectangle[i][2] * 2;
            int y2 = rectangle[i][3] * 2;
            
            for(int x = x1; x <= x2; x++) {
                for(int y = y1; y <= y2; y++) {
                    if(map[x][y] == 2) continue;
                    map[x][y] = 2;
                    if(x == x1 || x == x2 || y == y1 || y == y2)
                        map[x][y] = 1;
                }
            }
        }
    }
    

}