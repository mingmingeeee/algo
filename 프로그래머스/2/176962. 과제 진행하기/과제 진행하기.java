import java.util.*;

class Solution {
    static class Plan implements Comparable<Plan> {
        String name;
        int time, playtime;
        public Plan(String name, int time, int playtime) {
            this.name = name;
            this.time = time;
            this.playtime = playtime;
        }
        public int compareTo(Plan o) {
            return this.time - o.time;
        }
    }
    public String[] solution(String[][] plans) {
        String[] answer = {};
        
        List<Plan> planList = new ArrayList<>();
        
        for(String[] plan : plans) {
            String name = plan[0];
            String[] start = plan[1].split(":");
            int time = Integer.parseInt(start[0]) * 60 + Integer.parseInt(start[1]);
            int playtime = Integer.parseInt(plan[2]);
            
            planList.add(new Plan(name, time, playtime));
        }
        
        Collections.sort(planList);
        
        Stack<Plan> stack = new Stack<>();
    
        List<Plan> answers = new ArrayList<>();
        
        for(int i = 0; i < planList.size(); i++) {
            Plan curPlan = planList.get(i);
            // 1. stack 과제 끝낼 수 있나 확인
            if(!stack.isEmpty()) {
                Plan cur = stack.peek();
                int time = curPlan.time - cur.playtime;
                // 1-1. 끝낼 수 있으면 꺼내기
                while(time >= planList.get(i - 1).time) {
                    stack.pop();
                    answers.add(cur);
            
                    System.out.println(cur.time + " " + cur.name + " " + time + " " + curPlan.time);
                    if(stack.isEmpty())
                        break;
                    cur = stack.peek();
                    time -= cur.playtime;
                }
                if(!stack.isEmpty() && planList.get(i - 1).time > time) { 
                    // 1-2. 끝낼 수 없다면 과제 진행
                    cur.playtime -= time + cur.playtime - planList.get(i - 1).time;
                }
            }
            // 2. 과제 시작
            stack.push(curPlan);
        }
        while(!stack.isEmpty()) {
            answers.add(stack.pop());
        }
        
        answer = new String[answers.size()];
        int idx = 0;
        for(Plan plan : answers) {
            answer[idx++] = plan.name;
        }
        
        return answer;
    }
}

// 과제: 시작하기로 한 시각이 되면 시작함
// 새로운 과제 시작할 시각이 되었을 때, 기존 진행 중이던 과제 있다면 멈추고 새로운 과제 시작
// 진행중이던 과제 끝냈을 때, 잠시 멈춘 과제 있다면 멈춰둔 과제 이어서 진행
// 끝낸 시각에 동시에! 잠시 멈춰둔 과제가 있다면? 새로 시작해야 하는 과제부터 진행
// 멈춰둔 과제 여러개: 가장 최근에 멈춘 과제부터 시작