import java.util.*;

class Solution {
    boolean[] visited;
    ArrayList<String> path;
    public String[] solution(String[][] tickets) {
        
        String[] answer = {};
        visited = new boolean[tickets.length];
        path = new ArrayList<>();
        
        dfs("ICN", "ICN", tickets, 0);
        
        Collections.sort(path);
        answer = path.get(0).split(" ");
    
     
        return answer;
    }
    
    
    private void dfs(String start, String route, String[][] tickets, int cnt) {

        if(cnt == tickets.length) {
            path.add(route);
            return;
        }
        
        for(int i = 0; i < tickets.length; i++) {
            if(visited[i]) continue;
            if(start.equals(tickets[i][0])) {
                visited[i] = true;
                dfs(tickets[i][1], route+" "+tickets[i][1], tickets, cnt + 1);
                visited[i] = false;
            }
        }
        
        
        
    }
    
}

// 항상 "ICN" 공항에서 출발