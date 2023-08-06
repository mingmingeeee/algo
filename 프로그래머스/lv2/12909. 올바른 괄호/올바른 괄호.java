import java.util.*;

class Solution {
    boolean solution(String s) {
        boolean answer = true;

        Stack<Character> stack = new Stack<>();

        stack.push(s.charAt(0));
        for(int i = 1; i < s.length(); i++) {
            
            if(s.charAt(i) == '(') {
                stack.push(s.charAt(i));
            }
            else {
                if(!stack.isEmpty()) {
                    if(s.charAt(i) == ')')
                        stack.pop();
                }
                else {
                    return false;
                }
            }
            
        }

        if(!stack.isEmpty())
            answer = false;

        return answer;
    }
}