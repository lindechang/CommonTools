package common.tools.filteration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by lindec on 2016/3/15.
 */
public class FilteringTools {

    private static final Logger logger = LoggerFactory.getLogger(FilteringTools.class);

    public static FilteringTools Tool() {
        return new FilteringTools();
    }

    /**
     * 相近数据规整数组
     * 如：range = 100 ，则两数相近在100范围内进行规整 ｛500,504,300｝-> ｛502,502,300｝
     *
     * @param array 数值
     * @param range 比较范围
     * @return 返回规整后的数组
     */
    public int[] adjustAarry(int[] array, int range) {
        int[] adjArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            long sum = array[i];
            int count = 1;
            for (int j = 0; j < array.length; j++) {
                if (i != j) {
                    if (abs(array[i] - array[j]) < range) {
                        sum += array[j];
                        count++;
                    }
                }
            }
            adjArray[i] = (int) (sum / count);
        }
        return adjArray;
    }

    /**
     * 获得数组的不同的数据，并从小到大排序
     *
     * @param array
     * @return
     */
    public int[] cyCode(int[] array) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(array[0]);
        for (int i = 0; i < array.length; i++) {
            if (!list.contains(array[i])) list.add(array[i]);
        }
        Integer[] ccArray = list.toArray(new Integer[list.size()]);
        Arrays.sort(ccArray);
        int[] result = new int[ccArray.length];
        for (int i = 0; i < ccArray.length; i++) {
            result[i] = ccArray[i];
        }
        return result;
    }

//    /**
//     * 获得简码（除100，四舍五入）
//     *
//     * @param array
//     * @return
//     */
//    public Integer[] cCode(Integer[] array) {
//        Integer[] cArray = ccCode(array);
//        for (int i = 0; i < array.length; i++) {
//            cArray[i] = (cArray[i] + 50) / 100;
//        }
//        return cArray;
//    }

    /**
     * 获得固定码
     *
     * @param array 规整后的红外数据
     * @return 固定码
     */
    public int[] fixedAarry(int[] array) {
        int[] fixedArray = new int[array.length];
        int[] cy = cyCode(array);
        for (int m = 0; m < array.length; m++) {
            for (int n = 0; n < cy.length; n++) {
                if (array[m] == cy[n]) {
                    fixedArray[m] = n + 1;
                }
            }
        }
        return fixedArray;
    }

//    public int[] decode(int[] cyCode, int[] fixedCode) {
//        int[] Array = new int[fixedCode.length];
//        for (int i = 0; i < fixedCode.length; i++) {
//            Array[i] = cyCode[fixedCode[i] - 1] * 100;
////            System.out.println(Array[i]);
//        }
//        return Array;
//    }

    public static void main(String[] args) {
        int[] param = {3742, 1482, 721, 1064, 704, 1059, 704, 335, 700, 335, 700, 335, 700, 1064, 700, 335, 699, 335, 699, 1063, 704, 1063, 700, 335, 700, 1063, 700, 335, 700
                , 335, 700, 1064, 704, 1059, 704, 335, 700, 1064, 700, 1064, 700, 335, 700, 335, 700, 1063, 704, 330, 704, 330, 704, 1064, 700, 335, 700, 335, 700, 335, 700, 335
                , 700, 335, 700, 335, 700, 335, 700, 335, 700, 334, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1063, 700, 335, 700, 335
                , 699, 1063, 705, 330, 704, 331, 704, 1060, 704, 1064, 700, 335, 700, 335, 700, 339, 700, 335, 700, 334, 700, 335, 700, 1063, 700, 335, 700, 1064, 704, 330, 700
                , 335, 700, 339, 704, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1064, 704, 1059, 704, 335, 700, 1063, 700, 1063, 704, 331, 704, 330, 704, 330, 704, 331
                , 705, 330, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 699, 335, 700, 335, 700, 330
                , 704, 331, 704, 330, 704, 331, 704, 331, 704, 330, 704, 331, 704, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 335, 700, 1063, 700,
                335, 700, 335, 700, 1063, 705, 1059, 704, 331, 704, 331, 704, 331, 704, 330, 704, 99860
        };
        int[] array = FilteringTools.Tool().adjustAarry(param, 100);
        int[] fixedarray = FilteringTools.Tool().fixedAarry(array);

        StringBuilder sb = new StringBuilder("");
        for (int a : fixedarray) {
            System.out.println(a);
            sb.append(a);
        }
        System.out.println(sb.toString());
    }
}
