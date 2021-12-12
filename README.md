# FundamentalOfCompiling

# **编译原理实验**

# DFA的化简：SimplifiedDFA.java


## 【问题描述】

DFA化简问题的一种描述是：编写一个程序，输入一个确定的有穷自动机（DFA），输出与DFA等价的最简的确定有穷自动机（DFA）。


## 【基本要求】

设置DFA初始状态X，终态Y，过程态用数字表示：0 1 2 3………


## 【测试用例】


### 测试数据：  


X X-a->0 X-b->1


Y Y-a->0 Y-b->1


0 0-a->0 0-b->2


1 1-a->0 1-b->1


2 2-a->0 2-b->Y


### 输出结果


X X-a->0 X-b->X 


Y Y-a->0 Y-b->X


0 0-a->0 0-b->2


2 2-a->0 2-b->Y



## 存在问题：


1.初态和终态只能有一个状态，且只能是X和Y



---



# 词法分析：LexicalAnalysis.java


单词类别码 单词的字符/字符串形式(中间仅用一个空格间隔)


单词的类别码请统一按如下形式定义：


单词名称	类别


标识符	IDENFR	


if	IFTK	


\-	MINU	


= 	ASSIGN


整型常量	INTCON	


else	ELSETK


\*	MULT

;	SEMICN


字符常量	CHARCON


do	DOTK	


/	DIV


,	COMMA


字符串	STRCON


while	WHILETK


< 	LSS


(	LPARENT


const	CONSTTK


for	FORTK


<=	LEQ


)	RPARENT


int	INTTK


scanf	SCANFTK


> 	GRE 	

\[	LBRACK


char	CHARTK


printf	PRINTFTK


>=	GEQ


]	RBRACK


void	VOIDTK


return	RETURNTK


== 	EQL


{	LBRACE


main	MAINTK


\+	PLUS  
	
!= 	NEQ


}	RBRACE


## 【输入形式】

testfile.txt中的符合文法要求的测试程序。


## 【输出形式】

要求将词法分析结果输出至output.txt中。

## 存在问题


1.只能识别常量，其他变量没有识别实现
