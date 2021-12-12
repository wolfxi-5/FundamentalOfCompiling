package FundamentalOfCompiling;

import java.util.*;


class DFA_State {
    public int stateNum = -1; // 状态编号：-1未定义
    public int ifEndNode = -1; // 终态判断：-1未定义 0不是终态 1是终态
    public Map<String, Integer> transfer = new HashMap<>(); //转移图

    public DFA_State() {
        stateNum = -1;
        ifEndNode = -1;
    }
}

public class SimplifiedDFA {

    public List<DFA_State> dfaStateList  = new ArrayList<>();
    public ArrayList<String> finalSymbol = new ArrayList<>(); //终结符集合
    public ArrayList<ArrayList<String>> splitStates = new ArrayList<>(); //分割状态集合

    public SimplifiedDFA(String[] str) {
        ArrayList<String> end = new ArrayList<>();  //终态
        ArrayList<String> notEnd = new ArrayList<>();   //非终态

        for (int i = 0; i < str.length; i++) {
            String[] arr1 = str[i].split(" "); //"X" "X-a->0" "X-b->1"

            DFA_State dfa_state = new DFA_State();

            //判断状态：是否是终态
            if (arr1[0].equals("X")) {
                dfa_state.stateNum = 'X'; //88
                dfa_state.ifEndNode = 0;
                notEnd.add(arr1[0]);
            }else if(arr1[0].equals("Y")) {
                dfa_state.stateNum = 'Y'; //89
                dfa_state.ifEndNode = 1;
                end.add(arr1[0]);
            } else {
                dfa_state.stateNum = Integer.parseInt(arr1[0]);
                dfa_state.ifEndNode = 0;
                notEnd.add(arr1[0]);
            }

            //添加转移状态
            for (int j = 1; j < arr1.length; j++) {
                String[] arr2 = arr1[j].split("-");
                arr2[2] = arr2[2].substring(1); //"X" "a" "0"

                //添加终结符
                if (!finalSymbol.contains(arr2[1])) {
                    finalSymbol.add(arr2[1]);
                }

                //添加转移函数
                if (arr2[2].equals("X")) {
                    dfa_state.transfer.put(arr2[1], 88);
                }else if(arr2[2].equals("Y")) {
                    dfa_state.transfer.put(arr2[1], 89);
                } else {
                    dfa_state.transfer.put(arr2[1],Integer.parseInt(arr2[2]));
                }

            }

            //添加DFA中
            dfaStateList.add(dfa_state);
        }

        splitStates.add(end);
        splitStates.add(notEnd);
    }

    public void GetSimplifiedDFA() {

        //是否产生新的划分
        int len = 0;
        while (len != splitStates.size()) {
            len = splitStates.size();

            // 遍历每个划分集合
            for (int i = 0; i < splitStates.size(); i++) {

                ArrayList<ArrayList<String>> split = new ArrayList<>();
                ArrayList<String> arr = splitStates.get(i);

                if (arr.size() != 1) {

                    // 遍历每一个终结符
                    for (int j = 0; j < finalSymbol.size(); j++) {
                        List<Integer> list = new ArrayList<>();
                        String path = finalSymbol.get(j);

                        // 遍历该划分的每一个元素
                        for (int k = 0; k < arr.size(); k++) {

                            // 遍历DFA的状态集合
                            for (int m = 0; m < dfaStateList.size(); m++) {

                                //该元素的状态值
                                int state;
                                if (arr.get(k).equals("X")) {
                                    state = 'X';
                                } else if (arr.get(k).equals("Y")) {
                                    state = 'Y';
                                } else {
                                    state = Integer.parseInt(arr.get(k));
                                }

                                // 该元素的转移状态集
                                if (state == dfaStateList.get(m).stateNum) {

                                    Integer stateNum = dfaStateList.get(m).transfer.get(path);

                                    if (stateNum != null) {

                                        String nextState;
                                        if (dfaStateList.get(m).transfer.get(path) == 89) {
                                            nextState = "Y";
                                        } else {
                                            nextState = dfaStateList.get(m).transfer.get(path) + "";
                                        }

                                        for (int n = 0; n < splitStates.size(); n++) {
                                            ArrayList<String> arr1 = splitStates.get(n);
                                            if (arr1.contains(nextState)) {
                                                list.add(n);
                                            }
                                        }

                                    }

                                }

                            }

                        }

                        Map<Integer, ArrayList<String>> aimStateList = new HashMap<>();
                        for (int k = 0; k < list.size(); k++) {
                            Integer aimState = list.get(k);
                            if (aimStateList.containsKey(aimState)) {
                                aimStateList.get(aimState).add(arr.get(k));
                            } else {
                                ArrayList<String> temp = new ArrayList<>();
                                temp.add(arr.get(k));
                                aimStateList.put(aimState,temp);
                            }
                        }

                        split.clear();
                        for (ArrayList<String> key : aimStateList.values()) {
                            split.add(key);
                        }

                    }

                    for (int j = 0; j < split.size(); j++) {
                        splitStates.add(split.get(j));
                    }
                    splitStates.remove(i);

                }
            }

        }
    }

    public void PrintSimplifiedDFA(String[] str) {

        Map<Integer, String> aimState = new HashMap<>();
        for (int i = 0; i < splitStates.size(); i++) {
            ArrayList<String> list = splitStates.get(i);
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).equals("X")) {
                    aimState.put(88, list.get(0));
                } else if (list.get(j).equals("Y")) {
                    aimState.put(89, list.get(0));
                } else {
                    aimState.put(Integer.parseInt(list.get(j)),list.get(0));
                }
            }
        }

        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            for (Integer key: aimState.keySet()) {
                if (key.equals(88)) {
                    str[i] = str[i].replace("X", aimState.get(key));
                } else if(key.equals(89)) {
                    str[i] = str[i].replace("Y", aimState.get(key));
                } else {
                    str[i] = str[i].replace(key+"", aimState.get(key));
                }
            }
            if (!resultList.contains(str[i])){
                resultList.add(str[i]);
            }
        }

        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i));
        }

    }

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();

        String input = null;
        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            input = in.nextLine();
            list.add(input);
        }

        String[] str = new String[list.size()];
        for (int i = 0; i < str.length; i++) {
            str[i] = list.get(i);
        }

//        for (int i = 0; i < str.length; i++) {
//            System.out.println(str[i]);
//        }


//        String[] a = {
//                "X X-a->0 X-b->1",
//                "Y Y-a->0 Y-b->1",
//                "0 0-a->0 0-b->2",
//                "1 1-a->0 1-b->1",
//                "2 2-a->0 2-b->Y"};
        SimplifiedDFA dfa = new SimplifiedDFA(str);
        dfa.GetSimplifiedDFA();
        dfa.PrintSimplifiedDFA(str);
    }
}
