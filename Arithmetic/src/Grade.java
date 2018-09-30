import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;

public class Grade {
    int n;
    public Grade(int _n){
        n=_n;
    }
    void Count()throws Exception {
        BufferedReader br_a = new BufferedReader(new FileReader(".\\src\\Answer.txt"));
        BufferedReader br_a1 = new BufferedReader(new FileReader(".\\src\\Answer1.txt"));
        PrintStream grade = new PrintStream(".\\src\\" + "Grade.txt");
        int k = 0, count_c = 0, count_w = 0;
        int[] corrt_wrong = new int[n];
        String s_a, s_a1;
        while ((s_a = br_a.readLine()) != null && (s_a1 = br_a1.readLine()) != null && k < n) {
            if (s_a.equals(s_a1)) {
                corrt_wrong[k] = 0;
                count_c++;
            } else {
                corrt_wrong[k] = 1;
                count_w++;
            }
            k++;
        }
        grade.print("Correct:"+" "+count_c+" ( ");
        for(int i=0;i<n;i++){
            if(corrt_wrong[i]==0) grade.print(i+1+" ");
        }
        grade.println(")");
        grade.print("Wrong:"+" "+count_w+" ( ");
        for(int i=0;i<n;i++){
            if(corrt_wrong[i]==1) grade.print(i+1+" ");
        }
        grade.println(")");

        br_a.close();
        br_a1.close();
        grade.close();
    }
}
