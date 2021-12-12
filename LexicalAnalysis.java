package FundamentalOfCompiling;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LexicalAnalysis {
    private String keyWord[] = {"const","int","char","void","main","if","else","do","while","for","scanf","printf","return"};
    private char ch;
    public List<String> resultStr = new ArrayList<>();

    //判断是否是关键字
    boolean isKey(String str)
    {
        for(int i = 0;i < keyWord.length;i++)
        {
            if(keyWord[i].equals(str))
                return true;
        }
        return false;
    }

    //判断是否是字母
    boolean isLetter(char letter)
    {
        if((letter >= 'a' && letter <= 'z')||(letter >= 'A' && letter <= 'Z'))
            return true;
        else
            return false;
    }

    //判断是否是数字
    boolean isDigit(char digit)
    {
        if(digit >= '0' && digit <= '9')
            return true;
        else
            return false;
    }

    //词法分析
    void analyze(char[] chars)
    {
        String arr;

        for(int i = 0;i< chars.length;i++) {

            ch = chars[i];
            arr = "";

            if(ch == ' '||ch == '\t'||ch == '\n'||ch == '\r'){

            } else if(isLetter(ch)) {

                while(isLetter(ch)||isDigit(ch)){
                    arr += ch;
                    ch = chars[++i];
                }

                //回退一个字符
                i--;

                if(isKey(arr)){ //关键字
                    resultStr.add(arr.toUpperCase() + "TK" + " " + arr);
//                    System.out.println(arr.toUpperCase() + "TK" + " " + arr);
                } else{ //标识符
                    resultStr.add("IDENFR" + " " + arr);
//                    System.out.println("IDENFR" + " " + arr);
                }

            } else if(isDigit(ch) || (ch == '.')) { //属于无符号常数

                while(isDigit(ch) || (ch == '.' && isDigit(chars[++i]))) {
                    if(ch == '.') i--;
                    arr = arr + ch;
                    ch = chars[++i];
                }

                //回退一个字符
                i--;

                if (!arr.contains(".")) {
                    resultStr.add("INTCON" + " " + arr);
//                    System.out.println("INTCON" + " " + arr);
                } else {
                    System.out.println("0000");
                }

            } else switch(ch) {

                //运算符
                case '+':resultStr.add("PLUS +");/*System.out.println("PLUS +")*/;break;
                case '-':resultStr.add("MINU -");/*System.out.println("MINU -")*/;break;
                case '*':resultStr.add("MULT *");/*System.out.println("MULT *")*/;break;
                case '/':resultStr.add("DIV /");/*System.out.println("DIV /");*/break;

                //分界符
                case '(':resultStr.add("LPARENT (");/*System.out.println("LPARENT (");*/break;
                case ')':resultStr.add("RPARENT )");/*System.out.println("RPARENT )");*/break;
                case '[':resultStr.add("LBRACK [");/*System.out.println("LBRACK [");*/break;
                case ']':resultStr.add("RBRACK ]");/*System.out.println("RBRACK ]");*/break;
                case ';':resultStr.add("SEMICN ;");/*System.out.println("SEMICN ;");*/break;
                case ',':resultStr.add("COMMA ,");/*System.out.println("COMMA ,");*/break;
                case '{':resultStr.add("LBRACE {");/*System.out.println("LBRACE {");*/break;
                case '}':resultStr.add("RBRACE }");/*System.out.println("RBRACE }");*/break;

                //字符串常量
                case '"':{
                    while (chars[++i] != '"')
                        arr += chars[i];
                    resultStr.add("STRCON" + " " + arr);
//                    System.out.println("STRCON" + " " + arr);
                }break;

                //字符常量
                case '\'':{
                    ch = chars[++i];
                    resultStr.add("CHARCON" + " " + ch);
//                    System.out.println("CHARCON" + " " + ch);
                    i++;
                }break;

                //运算符
                case '=':{
                    ch = chars[++i];
                    if(ch == '=')
                        resultStr.add("EQL ==");
//                        System.out.println("EQL ==");
                    else {
                        resultStr.add("ASSIGN =");
//                        System.out.println("ASSIGN =");
                        i--;
                    }
                }break;
                case '!':{
                    ch = chars[++i];
                    if(ch == '=')
                        resultStr.add("NEQ !=");
//                        System.out.println("NEQ !=");
                    else {
                        i--;
                    }
                }break;
                case '>':{
                    ch = chars[++i];
                    if(ch == '=')
                        resultStr.add("GEQ >=");
//                        System.out.println("GEQ >=");
                    else {
                        resultStr.add("GRE >");
//                        System.out.println("GRE >");
                        i--;
                    }
                }break;
                case '<':{
                    ch = chars[++i];
                    if(ch == '=')
                        resultStr.add("LEQ <=");
//                        System.out.println("LEQ <=");
                    else {
                        resultStr.add("LSS <");
//                        System.out.println("LSS <");
                        i--;
                    }
                }break;

                //无识别
                default: break;
//                    System.out.println("无识别符");
            }
        }
    }

    public static void main(String[] args) throws Exception {
//        File file = new File("D:\\download\\testfile.txt");//定义一个file对象，用来初始化FileReader
        File file = new File("testfile.txt");//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        int length = (int) file.length();

        //这里定义字符数组的时候需要多定义一个,因为词法分析器会遇到超前读取一个字符的时候，如果是最后一个
        //字符被读取，如果在读取下一个字符就会出现越界的异常
        char buf[] = new char[length+1];
        reader.read(buf);
        reader.close();
        LexicalAnalysis result = new LexicalAnalysis();
        result.analyze(buf);

//        String fileName = "D:\\download\\output.txt";
        String fileName = "output.txt";

        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            for (int i = 0; i < result.resultStr.size(); i++) {
                writer.println(result.resultStr.get(i));
            }
        }

    }
}
