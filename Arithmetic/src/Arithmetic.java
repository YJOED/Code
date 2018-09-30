import java.util.Scanner;
public class Arithmetic {
    public static void main(String[] args)throws Exception {
        Scanner input = new Scanner(System.in);
        int range_n=0,range_r=0;
        while (true) {
            if(range_n!=0&&range_r!=0) {
                new Exer_Answ().Exercises(range_n,range_r);
                range_r=0;
                System.out.println("Exercise.txt and Answer.txt created successful");
            }
            System.out.println("please input [order] [range]:");
            String sip = input.nextLine();
            String[] s = sip.split(" ");
            if (s.length == 2) {
                switch (s[0]) {
                    case "-n":
                        range_n = Integer.parseInt(s[1]);
                        if (range_r == 0) System.out.println("input \"-r\" order");
                        break;
                    case "-r":
                        range_r = Integer.parseInt(s[1]);
                        if (range_n == 0) System.out.println("input \"-n\" order");
                        break;
                    default:
                        System.out.println("Error,input again");
                        break;
                }
            }else if(s.length==4){
                if(sip.equals("-a Answer.txt -e Exercises.txt")||sip.equals("-e Exercises.txt -a Answer.txt")){
                    new Grade(range_n).Count();
                    System.out.println("Grade.txt created successful");
                }else System.out.println("Error,input again!");
            }else System.out.println("Error");
        }
    }
}
