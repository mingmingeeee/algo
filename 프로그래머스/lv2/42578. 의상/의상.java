import java.util.*;

class Solution {
    static int answer = 1;
    public int solution(String[][] clothes) {
        
        Map<String, Integer> map = new HashMap<>();
        // 얼굴, 상의, 하의, 겉옷
        for(int i = 0; i < clothes.length; i++) {
            String type = clothes[i][1];
            
            if(map.get(type) == null) {
                map.put(type, 1);
            }
            
            map.put(type, map.get(type) + 1);
        }
        
        for(String key : map.keySet()) {
            
            answer *= map.get(key);
            
        }
        
        return answer - 1;
    }
    
}

// 코니: 매일 다른 옷 조합
// 각 종류별로 최대 1가지 의상 착용 가능
// 하루 최소 1개 이상 입음 
// 서로 다른 옷의 조합의 수