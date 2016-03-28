package common.tools.conversion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lindec on 2016/3/16.
 */
public class ComboTools {

    /**
     * 组合
     * @param inputList
     * @return
     */
    public List<String> doCombo(List<String> inputList) {
        List<String> resList = new ArrayList<>();
        reCombine(inputList, resList, 0,
                new char[inputList.size()]);
        return resList;
    }

    private void reCombine(List<String> inputList, List<String> resList,
                           int ind, char[] arr) {
        if (ind == inputList.size()) {
            resList.add(new String(arr));
            return;
        }

        for (char c : inputList.get(ind).toCharArray()) {
            arr[ind] = c;
            reCombine(inputList, resList, ind + 1, arr);
        }
    }

    public List<String> doStringCombo(List<String[]> inputList) {
        List<String> resList = new ArrayList<>();
        reStringCombine(inputList, resList, 0,
                new String[inputList.size()]);
        return resList;
    }

    private void reStringCombine(List<String[]> inputList, List<String> resList,
                                 int ind, String[] arr) {
        if (ind == inputList.size()) {
            resList.add(String.join("", arr));
            return;
        }

        for (String c : inputList.get(ind)) {
            arr[ind] = c;
            reStringCombine(inputList, resList, ind + 1, arr);
        }
    }

//    public static void main(String[] arg0) {
//        List<String> list = new ArrayList<>(4);
//        list.add("abcd");
//        list.add("123");
//        list.add("e");
//        list.add("56");
//        ComboTest c = new ComboTest();
//        c.doCombo(list).stream().forEach(s -> System.out.println(s));

//        List<String[]> list = new ArrayList<>(5);
//        list.add(new String[]{"0", "1"});
//        list.add(new String[]{"0", "1", "2", "3", "4"});
//        list.add(new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "0A", "0B", "0C", "0D", "0E", "0F", "10"});
//        list.add(new String[]{"0", "1", "2", "3"});
//        list.add(new String[]{"0", "1", "2", "3", "4"});
//        ComboTools c = new ComboTools();
//        c.doStringCombo(list).stream().forEach(s -> System.out.println(s));
//
//
//    }

}
