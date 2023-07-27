import java.util.*;

class Solution {
    private static char[] words = {'A', 'E', 'I', 'O', 'U'};
    private static List<String> list = new ArrayList<>();
    
    public int solution(String word) {
        
        for(int i = 0; i < words.length; i++) {
            simulate(0, words[i] + "");
        }
        
        // System.out.println(list.indexOf(word) + 1);
        // System.out.println(list.get(10));
        
        return list.indexOf(word) + 1;
    }
    
    private static void simulate(int cnt, String word) {
        if(cnt == words.length) {
            return;   
        }
        // System.out.println(word);
        list.add(word);
        
        for(int i = 0; i < words.length; i++) {
            simulate(cnt + 1, word + words[i]);
        }
        
    }
}

// 첫 번쨰 단어: "A"