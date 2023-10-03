import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// 소요시간: 1h43m45s38

public class Main {

    static final int T = 10;
    static int answer = 0;
    static Node start;
    static Node[] horse = new Node[4];
    static int[] nums = new int[T], dice = new int[T];

    static class Node {
        int score;
        Node next, anotherNext;
        boolean isEmpty;

        public Node(int score) {
            this.score = score;
            this.next = null;
            this.anotherNext = null;
            this.isEmpty = true;
        }
        Node addNode(int score) {
            Node temp = new Node(score);
            this.next = temp;
            return temp;
        }
    }

    static Node getNode(int end) {
        Node temp = start;
        while(temp.next != null) {
            if(temp.score == end) return temp;
            temp = temp.next;
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());

        for(int i = 0; i < T; i++) {
            dice[i] = Integer.parseInt(st.nextToken());
        }

        simulate();

        System.out.println(answer);

    }

    static void simulate() {

        init();
        perm(0);

    }

    static void perm(int cnt) {

        if(cnt == 10) {
            int sum = 0;
            for(int i = 0; i < 4; i++) {
                horse[i] = start;
            }

            for(int i = 0; i < 10; i++) {
                int idx = nums[i];
                // 이동할 말은 empty
                horse[idx].isEmpty = true;

                // 주사위 수 만큼 이동
                for (int j = 0; j < dice[i]; j++) {
                    if(j == 0 && horse[idx].anotherNext != null) {
                        horse[idx] = horse[idx].anotherNext;
                    } else {
                        horse[idx] = horse[idx].next;
                    }
                }

                // 이동 마치고
                if (horse[idx].isEmpty || horse[idx].score == -1) {
                    horse[idx].isEmpty = false;
                    sum += horse[idx].score;
                    if(horse[idx].score == -1)
                        sum++;
                } else {
                    // 현재 이동되어있는 말들 다 empty로 돌리기
                    for (int k = 0; k < 4; k++) {
                        horse[k].isEmpty = true;
                    }
                    return;
                }

            }

            // 현재 이동되어있는 말들 다 empty로 돌리기
            for (int k = 0; k < 4; k++) {
                horse[k].isEmpty = true;
            }


            answer = Math.max(answer, sum);
            return;
        }

        for(int i = 0; i < 4; i++) {
            nums[cnt] = i;
            perm(cnt + 1);
        }

    }

    static void init() {

        start = new Node(0);

        Node temp = start;
        for(int i = 2; i <= 40; i+=2) {
            temp = temp.addNode(i);
        }

        Node end = temp.addNode(-1);
        temp.next = end;
        end.next = end;

        Node middle = new Node(25);

        // 10 -> 13 -> 16 -> 19 -> 25
        temp = getNode(10);
        temp = temp.anotherNext = new Node(13);
        temp = temp.addNode(16);
        temp = temp.addNode(19);
        temp.next = middle;

        // 20 => 22 => 24 => 25
        temp = getNode(20);
        temp = temp.anotherNext = new Node(22);
        temp = temp.addNode(24);
        temp.next = middle;

        // 30 => 28 => 27 => 26 => 25
        temp = getNode(30);
        temp = temp.anotherNext = new Node(28);
        temp = temp.addNode(27);
        temp = temp.addNode(26);
        temp.next = middle;

        // 25 => 30 => 35 => 40
        temp = middle.addNode(30);
        temp = temp.addNode(35);
        temp.next = getNode(40);

    }

}

// 1. 시작 칸에 말 4개
// 2-1. 말이 파란색 칸에서 이동 => 파란색 화살표 타야 함
// 2-2. 이동하는 도중이거나 파란색이 아닌 칸에서 이동 시작 => 빨간색 화살표
// 3. 도착 칸으로 이동하면 이동 마침

// 게임: 10개 턴
// 매 턴마다 1 ~ 5 => 5면체 주사위 굴림
// 도착 칸에 있지 않은 말 하나 골라 주사위에 나온 수만큼 이동시킴
// 말이 이동을 마치는 칸에 다른 말이 있으면 그 말 고를 수 없음 (이동 마치는 칸이 도착 칸이면 가능)
// 말이 이동을 마칠 때마다 칸에 적혀있는 수가 점수에 추가 됨

// 주사위에서 나올 수 10개를 미리 알고 있을 때, 얻을 수 있는 점수 최댓값 구하기