import java.util.*;

class Solution {
    private static String[] answer;
    private static Map<String, boolean[]> visited = new HashMap<>();
    private static int n;
    private static Map<String, List<String>> map = new HashMap<>();
    public String[] solution(String[][] tickets) {
        
        n = tickets.length;
        for(int i = 0; i < tickets.length; i++) {
            if(map.get(tickets[i][0]) == null) {
                map.put(tickets[i][0], new ArrayList<>());
            }
            map.get(tickets[i][0]).add(tickets[i][1]);
       
            
            Collections.sort(map.get(tickets[i][0]));
        }
        
        for(int i = 0; i < tickets.length; i++) {
            int size = map.get(tickets[i][0]).size();
     
            if(visited.get(tickets[i][0]) == null)  
                visited.put(tickets[i][0], new boolean[size]);
        }
        
        String sum = "ICN";
        for(String airport : map.get("ICN")) {
            dfs(airport, sum + " " + airport, 0);
            
        }
        
    
     
        return answer;
    }
    
    private static boolean isChecked = false;
    private static void dfs(String airport, String sum, int cnt) {

        

        if(isChecked)
            return;
        if(cnt == n - 1) {
            answer = sum.split(" ");
            isChecked = true;
            return;
        }
    
        if(map.get(airport) == null)
            return;
        
        for(int i = 0; i < map.get(airport).size(); i++) {
            
            String port = map.get(airport).get(i);
            boolean[] v = visited.get(airport);
        
            if(v[i])
                continue;
            
         
            v[i] = true;
            dfs(port, sum + " " + port, cnt + 1);
            v[i] = false;
        }
        
        
        
    }
    
}

// 항상 "ICN" 공항에서 출발