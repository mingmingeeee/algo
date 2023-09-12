import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        int[] answer = new int[2];
        
        // I: 큐에 주어진 숫자 삽입
        // D 1: 큐에서 최댓값 삭제
        // D -1: 큐에서 최솟값 삭제
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        PriorityQueue<Integer> tpq = new PriorityQueue<>(Collections.reverseOrder());
        
        for(String operation : operations) {
            String[] op = operation.split(" ");
            switch(op[0]) {
                case "I":
                    pq.offer(Integer.parseInt(op[1]));
                    tpq.offer(Integer.parseInt(op[1]));
                break;
                case "D":
                    if(op[1].equals("1")) {
                        Integer max = tpq.poll();
                        pq.remove(max);
                    } else {
                        Integer min = pq.poll();
                        tpq.remove(min);
                    }
                break;
                
            }
        }
        
        
        int a = 0, b = 0;
        if(!tpq.isEmpty() && !pq.isEmpty()) {
            a = tpq.peek(); b = pq.peek();
        }
            
        
        answer[0] = a;
        answer[1] = b;
        
        
        return answer;
    }
}