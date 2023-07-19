import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(in.readLine());
        int [] A = new int[N];

        StringTokenizer st = new StringTokenizer(in.readLine());
        for(int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(in.readLine());
        int B = Integer.parseInt(st.nextToken());
        int C = Integer.parseInt(st.nextToken());

        long answer = 0;
        for(int i = 0; i < N; i++) {
            answer++;
            int a = A[i] - B;
            if(a > 0) {
                answer += a / C;
                if(a % C > 0)
                    answer++;
            }

        }

        System.out.println(answer);

    }

}

// i번 시험장에 있는 음시자 수: Ai명
// 총 감독관, 부감독관
// 총 감독관이 한 시험장에서 감시할 수 있는 응시자 수: B명
// 부 감동관이 한 시험장에서 감시할 수 있는 응시자 수: C명
// 각 시험장마다 응시생 모두 감시해야 함 => 필요한 감독관 수 최소 값 찾기