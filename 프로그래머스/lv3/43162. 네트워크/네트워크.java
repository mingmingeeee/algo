import java.io.*;
import java.util.*;

class Solution {
    private static boolean[][] visited;
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        visited = new boolean[computers.length][computers[0].length];
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(visited[i][j]) continue;
                if(computers[i][j] == 1) {
                    visited[i][j] = true;
                    visited[j][i] = true;
                    dfs(computers, j);
                    answer++;
                }
            }
        }
        
        return answer;
    }
    
    
    private static void dfs(int[][] computers, int i) {

        
        for(int x = 0; x < computers[0].length; x++) {
            if(visited[i][x]) continue;
            visited[i][x] = true;
            visited[x][i] = true;
            if(computers[i][x] == 1)
                dfs(computers, x);
        }
        
        
    }
}