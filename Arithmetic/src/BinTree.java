import java.util.ArrayList;
import java.util.Arrays;

public class BinTree {
    public BinTree rchild;
    public BinTree lchild;
    public String sdata;
    public int idata;
    public int[] After_op;

    public BinTree(){
        super();
    }

    public BinTree CreateBinTree(BinTree t,ArrayList<String> slist,int[] iarray,int[][] num){
        if(t!=null&&slist.size()>0&&iarray.length>0) {
            t.idata = iarray[slist.size()-1];
            if(t.idata%2==0) t.After_op=Arrays.copyOf(num[t.idata / 2],num[t.idata/2].length);
            t.sdata = slist.remove(slist.size() - 1);
            BinTree L = new BinTree();
            BinTree R = new BinTree();
            String sr=slist.get(slist.size() - 1);
            if (sr.equals("+") || sr.equals("-") || sr.equals("×") || sr.equals("÷")) {
                CreateBinTree(R, slist,iarray,num);
            }else {
                R.idata = iarray[slist.size()-1];
                R.sdata = slist.remove(slist.size() - 1);
            }
            t.rchild = R;
            if(slist.size()>0){
                String sl = slist.get(slist.size() - 1);
                if (sl.equals("+") || sl.equals("-") || sl.equals("×") || sl.equals("÷")) {
                    CreateBinTree(L, slist, iarray,num);
                } else {
                    L.idata = iarray[slist.size() - 1];
                    L.sdata = slist.remove(slist.size() - 1);
                }
                t.lchild = L;
            }
        }
        return t;
    }

    public void Operation(BinTree t,int[][] num){
        BinTree p;
        if(t!=null) {
            if(t.rchild==null&&t.lchild==null)
                t.After_op=Arrays.copyOf(num[t.idata / 2],num[t.idata/2].length);
            if (t.lchild!= null&&t.rchild!=null) {
                Operation(t.lchild,num);
                Operation(t.rchild,num);
                switch (t.sdata) {
                    case "-":
                        if (Compare(t.lchild.After_op, t.rchild.After_op)) {
                            p = t.lchild;
                            t.lchild = t.rchild;
                            t.rchild = p;
                        }
                        t.After_op = Arrays.copyOf(new Exer_Answ().sub(t.lchild.After_op, t.rchild.After_op), 3);
                    break;
                    case "+":
                        t.After_op = Arrays.copyOf(new Exer_Answ().add(t.lchild.After_op, t.rchild.After_op), 3);
                        break;
                    case "×":
                        t.After_op = Arrays.copyOf(new Exer_Answ().mul(t.lchild.After_op, t.rchild.After_op), 3);
                        break;
                    case "÷":
                        t.After_op = Arrays.copyOf(new Exer_Answ().div(t.lchild.After_op, t.rchild.After_op), 3);
                        break;
                }
            }
        }
    }

    public boolean Compare(int[] num1,int[] num2){
        int com1=(num1[0]*num1[2]+num1[1])*num2[2];
        int com2=(num2[0]*num2[2]+num2[1])*num1[2];
        if(com1<com2) return true;
        else return  false;
    }
}

