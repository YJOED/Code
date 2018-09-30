import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

class Exer_Answ  {

    Exer_Answ(){ }

    void Exercises(int n,int r)throws Exception{
        String[] str = {"+","-","×","÷"};
        PrintStream f1 = new PrintStream(".\\src\\"+"Exercises.txt");
        PrintStream f2 = new PrintStream(".\\src\\"+"Answer.txt");
        for(int i=0;i<n;i++){  //生成题目数量
            int x=(int)(1+Math.random()*3);//运算符的个数
            String[][] s = new String[2*x+1][2] ;
            int[][] number = new int[x+1][3];
            int[] parenthese=new int[2];
            parenthese[0]=2*x+1;
            parenthese[1]=2*x+1;
            boolean markl=false;
            for(int j=0;j<2*x+1;j++) {
                s[j][1]=j+"";
                if (j % 2 != 0) {
                    int y = (int) (Math.random() * 4);//随机运算符
                    s[j][0] = str[y];
                }
                if (j % 2 == 0) {
                    int randoms =(int)(Math.random()*5);
                    if(randoms>0){
                        int  t;
                        if(j>0&&s[j-1].equals("÷"))
                            t= (int) (Math.random() * r+1);
                        else t= (int) (Math.random() * r);
                        number[j/2][0] = t;
                        number[j/2][2] = 1;
                        s[j][0] = "" + number[j/2][0];
                    }else if (randoms==0){
                        int t1 = (int) (Math.random() * r+1);
                        int t2 = (int) (Math.random() * r);
                        if (t2 == 0) {
                            number[j / 2][0] = t1;
                            number[j / 2][2] = 1;
                            s[j][0] = "" + number[j / 2][0];
                        } else if (t1 % t2 == 0) {
                            number[j / 2][0] = t1 / t2;
                            number[j / 2][2] = 1;
                            s[j][0] = "" + number[j / 2][0];
                        } else if (t1 % t2 != 0) {
                            int[] max_t = com_div(t1, t2);
                            number[j / 2][0] = max_t[0];
                            number[j / 2][1] = max_t[1];
                            number[j / 2][2] = max_t[2];
                            if (number[j / 2][0] == 0) {
                                s[j][0]= "" + number[j / 2][1] + "/" + number[j / 2][2];
                            } else s[j][0] = "" + number[j / 2][0] + "'" + number[j / 2][1] + "/" + number[j / 2][2];
                        }
                    }
                    if(x>2){
                        int rp =(int)(Math.random()*5);
                        if(j<=2*x-2&&rp==0&&!markl) {
                            parenthese[0] = j;
                            parenthese[1] = j + 2;
                            markl = true;
                        }
                    }
                }
            }
            String[][] _s=new String[2*x+1][2];
            String[] _ss=new String[2*x+1];
            int[] index = new int[2*x+1];
            _s[0]=s[0];
            _ss[0]=s[0][0];
            index[0]=0;
            if(parenthese[0] != 2 * x + 1 && parenthese[1] != 2 * x + 1){
                String[] st=s[parenthese[1]];
                s[parenthese[1]]=s[parenthese[1]-1];
                s[parenthese[1]-1]=st;
            }
            for(int p=0;p<x;p++) {
                if (s[2 * p + 1][0].equals("÷") || s[2 * p + 1][0].equals("×")) {
                    if(2*p+2!=parenthese[0]) {
                        String[] stemp = s[2 * p + 1];
                        s[2 * p + 1] = s[2 * p + 2];
                        s[2 * p + 2] = stemp;
                    }else {
                        String[] stemp = s[2 * p + 1];
                        s[2 * p + 1] = s[2 * p + 2];
                        s[2 * p + 2] = s[2 * p + 3];
                        s[2 * p + 3] = s[2 * p + 4];
                        s[2 * p + 4] = stemp;
                        p++;
                    }
                }
            }
            for(int q=1;q<2*x+1;q++){
                if(q<2*x) {
                    if (q + 1 != parenthese[0]) {
                        if ((s[q][0].equals("-") || s[q][0].equals("+")) &&
                                (!s[q + 1][0].equals("-") && !s[q + 1][0].equals("+"))) {
                            _s[q] = s[q + 1];
                            s[q + 1] = s[q];
                            s[q] = _s[q];
                        } else _s[q] = s[q];
                    }else {
                        _s[q]=s[q+1];
                        _s[q+1]=s[q+2];
                        _s[q+2]=s[q+3];
                        _s[q+3]=s[q];
                        q=q+2;
                    }
                } else _s[q] = s[q];
            }
            for(int ii=1;ii<2*x+1;ii++){
                index[ii]=Integer.parseInt(_s[ii][1]);
                _ss[ii]=_s[ii][0];
            }
            ArrayList<String> slist = new ArrayList<> (Arrays.asList(_ss));
            BinTree t=new BinTree();
            t.CreateBinTree(t,slist,index,number);
            t.Operation(t,number);
            System.out.println();
            Answer(f1,f2,t,x,i,parenthese);
        }
    }

    private void Answer(PrintStream _f1,PrintStream _f2,BinTree t,int len,int _i,int[] parenthese) {
        String[] sarray = new String[2*len+1];
        int[] indexarray = new int[2*len+1];
        inorder(t,sarray,indexarray,2*len+1);
        _f1.print(_i+1+".");
        for(int x=2*len+1;x>0;x--){
            if(indexarray[x-1]==parenthese[0]) _f1.print("("+" ");
            _f1.print(sarray[x-1]+" ");
            if(indexarray[x-1]==parenthese[1]) _f1.print(")"+" ");
        }
        _f1.println();
        _f2.print(_i+1+".");
        if(t.After_op[1]==0&&t.After_op[2]==1) _f2.print(""+t.After_op[0]);
        else if(t.After_op[0]!=0)_f2.print(""+t.After_op[0]+"'"+t.After_op[1]+"/"+t.After_op[2]);
        else _f2.print(""+t.After_op[1]+"/"+t.After_op[2]);
        _f2.println();
    }

    public int[] com_div(int numerator,int denominator){//求最大公约数
        int[] result = new int[3];
        int m, x=numerator,y=denominator;
        while(y!=0){
            m = x%y;
            x=y;
            y=m;
        }
        numerator=numerator/x;
        denominator=denominator/x;
        result[0]=numerator/denominator;
        result[1]=numerator%denominator;
        result[2]=denominator;
        return result;
    }

    public int[] mul(int[] _num1, int[]_num2){//乘
        int numer = (_num1[0] * _num1[2] + _num1[1]) * (_num2[0] * _num2[2] + _num2[1]);
        int denom = _num1[2] * _num2[2];
        return com_div(numer,denom);
    }

    public int[] add(int[] _num1, int[] _num2){
        int numer = _num2[2] * (_num1[1] + _num1[0] * _num1[2]) + _num1[2] * (_num2[1] + _num2[0] * _num2[2]);
        int denom = _num1[2] * _num2[2];
        return com_div(numer,denom);
    }//加

    public int[] sub(int[] _num1, int[] _num2){
        int numer = _num2[2] * (_num1[1] + _num1[0] * _num1[2]) - _num1[2] * (_num2[1] + _num2[0] * _num2[2]);
        int denom = _num1[2] * _num2[2];
        return com_div(numer,denom);
    }//减

    public int[] div(int[] _num1, int[] _num2){
        int temp;
        temp=_num2[0]*_num2[2]+_num2[1];
        _num2[0]=0;
        _num2[1]=_num2[2];
        _num2[2]=temp;
        return  mul(_num1,_num2);
    }//除

    public int inorder(BinTree root,String[] s,int[] in,int x){
        String[] _s=s;
        int[] _in=in;
        if(root!=null) {
            if (root.lchild == null && root.rchild == null) {
                _s[x - 1] = root.sdata;
                _in[x - 1] = root.idata;
                return x - 1;
            } else {
                x = inorder(root.lchild, _s, _in, x);
                _s[x - 1] = root.sdata;
                _in[x - 1] = root.idata;
                x = inorder(root.rchild, _s, _in, x - 1);
                return x;
            }
        }return x;
    }  //遍历生成表达式
}
