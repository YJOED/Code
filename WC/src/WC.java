import java.io.*;
import java.util.*;
import java.lang.String;

public class WC {
    public static void main(String[] args)throws Exception {
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("please input '[order] [filename]':");
            System.out.print("wc.exe ");
            String ip[] = input.nextLine().split(" ");
            if (ip.length==2) {
                Operation(ip);
            }else if(ip.length==3 && "-s".equals(ip[0])){
                HandleFile(ip);
            }else System.out.println("Error");
        }
    }

    public static void Operation(String[] sip)throws Exception{
        String s;
        int linecount = 0, wordcount = 0, charcount = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sip[1])));
        if ("-l".equals(sip[0])) {            // Line count
            while ((s = br.readLine()) != null) {
                linecount++;
            }
            System.out.println("Line count:" + linecount);
        } else if ("-c".equals(sip[0])) {        // Char count
            while ((s = br.readLine()) != null) {
                charcount = charcount + s.length();
            }
            System.out.println("Char count:" + charcount);
        } else if ("-w".equals(sip[0])) {      // Word count
            while ((s = br.readLine()) != null) {
                String[] sa = s.split("\\s+|\\(|\\)|,|\\.|\\:|\\{|\\}|\\-|\\+|;|\\?|\\/|\\\\|/");
                wordcount = wordcount + sa.length;
            }
            System.out.println("Word count:" + wordcount);
        } else if("-a".equals(sip[0])){
            SpecialLine(br);
        }
        br.close();
    }

    public static void SpecialLine(BufferedReader br)throws Exception {
        String s;
        String REGEX_1 = "\\s*(\\{|\\})?//.*";
        String REGEX_2 = "(\\{|\\})?\\s*";
        String REGEX_3 = "\\s*/\\*.*";
        String REGEX_4 = ".*\\*/";
        boolean targer = false;
        int blankline = 0, commentline = 0, codeline = 0;
        while ((s = br.readLine()) != null) {
            if (s.matches(REGEX_1)) {
                commentline++;
            }else if (s.matches(REGEX_2)) {
                blankline++;
            }else if(s.matches(REGEX_3)&&!s.matches(REGEX_4)){
                commentline++;
                targer = true;
            }else if(targer==true){
                commentline++;
                if(s.matches(REGEX_4)){
                    targer=false;
                }
            }else codeline++;
        }
        System.out.println("Blank Line count:"+blankline);
        System.out.println("Comment Line count:"+ commentline);
        System.out.println("Code Line count:"+ codeline);
    }

    public static void HandleFile(String[] file)throws Exception{
        String path=null,filetype=null;
        String[] sip={file[1],null};
        if(file.length>2){
                filetype = file[2].substring(file[2].lastIndexOf("."));
                path = file[2].substring(0,file[2].lastIndexOf("\\"));
        }
        File f = new File(path);
        MyFileFilter mff = new MyFileFilter(filetype);
        File[] files = f.listFiles(mff);
        for(int i=0;i<files.length;i++){
            sip[1]=files[i].getPath();
            System.out.println("File Name: "+files[i].getName());
            Operation(sip);
        }
    }
}

class MyFileFilter implements FileFilter{
    String str;
    public MyFileFilter(String _str){
        str = _str;
    }
    public boolean accept(File pathname){
        //if(pathname.isDirectory()) return true;
        String f = pathname.getName();
        if(f.endsWith(str)) return true;
        return false;
    }
}

