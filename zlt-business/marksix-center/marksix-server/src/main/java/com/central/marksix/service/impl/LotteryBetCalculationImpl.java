package com.central.marksix.service.impl;

import com.central.marksix.entity.dto.DuplexLotteryBetDto;
import com.central.marksix.entity.dto.QuizChooseDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 彩票投注计算
 */
public class LotteryBetCalculationImpl {
    void test(DuplexLotteryBetDto duplexLotteryBetDto){
        List<QuizChooseDto> quizChooseDtoList = duplexLotteryBetDto.getQuizChooseDtoList();
        HashSet<String> bettingNumberHashSet = new HashSet<>();
            String[] numberStr = new String[quizChooseDtoList.size()];
        for (int i=0;i<quizChooseDtoList.size();i++){
            QuizChooseDto dto = quizChooseDtoList.get(i);
            numberStr[i] = dto.getIntroduce();
        }
        //所投注的每三个号码为一组合，若三个号码都是开奖号码之正码，视为中奖，其余行情视为不中奖
        //所投注的每三个号码为一组合，若其中2个号码都是开奖号码之正码，视为三中二奖，若3个都是开奖号码中的正码，即为三中二之中三，其余行情视为不中奖
        if("三全中".equals(duplexLotteryBetDto.getQuizTitle())
                ||"三中二".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
            bettingNumberHashSet = this.duplexNumber(numberStr,3);
        }

        //所投注的每二个号码为一组合，若二个号码都是开奖号码之正码，视为中奖，其余行情视为不中奖（含一个正码加一个特码情形）
        //所投注的每二个号码为一组合，若二个号码都是开奖号码之正码，叫二中特之中二，其中一个是正码，一个是特码，视为中奖,其余行情视为不中奖二中特之中二赔率高于二中特之中特的赔率
        //所投注的每两个号码为一组合，其中一个是正码，一个是特码，视为中奖，其余情形视为不中奖（含二个都是正码之情形）
        if("二全中".equals(duplexLotteryBetDto.getQuizTitle())
                ||"二中特".equals(duplexLotteryBetDto.getQuizTitle())
                ||"特串".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //所投注号码每四个为一组，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("四中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //选择2-4个尾数为一投注组合进行投注。该注的2-4个尾数必须在当期开出的7个开奖号码相对应的尾数中，（49亦算输赢，不为和）。每个号码都有自己的赔率，下注组合的总赔率，取该组合码的最低赔率为下单赔率
        if("二尾连中".equals(duplexLotteryBetDto.getQuizTitle())
                ||"二尾连不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("三尾连中".equals(duplexLotteryBetDto.getQuizTitle())
                ||"三尾连不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("四尾连中".equals(duplexLotteryBetDto.getQuizTitle())
                ||"四尾连不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选5个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("五选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选6个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("六选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选7个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("七选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选8个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("八选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选9个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("九选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选10个号码为一组进行下注，如果有一个号码在开奖号码的七个号码（正码和特码）里面，视为中奖，其他情形都视为不中奖
        if("十选中一".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        if("五不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("六不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("七不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("八不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("九不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("十不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        if("十一不中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类
        }
        //挑选1个号码为一投注组合进行下注，当期开出的7个号码有任何1个号码在该注组合中，即视为中奖，其余情形视为不中奖
        if("一粒任中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //挑选2个号码为一投注组合进行下注，当期开出的7个号码有任何1个号码在该注组合中，即视为中奖，其余情形视为不中奖
        if("二粒任中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //挑选3个号码为一投注组合进行下注，当期开出的7个号码有任何1个号码在该注组合中，即视为中奖，其余情形视为不中奖
        if("三粒任中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //挑选4个号码为一投注组合进行下注，当期开出的7个号码有任何1个号码在该注组合中，即视为中奖，其余情形视为不中奖
        if("四粒任中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
        //挑选5个号码为一投注组合进行下注，当期开出的7个号码有任何1个号码在该注组合中，即视为中奖，其余情形视为不中奖
        if("五粒任中".equals(duplexLotteryBetDto.getQuizTitle())) {//分类二类

        }
    }
    /**
     * 复式投注
     * @param str 选择号码
     * @param length 每注彩票组合个数
     * @return
     */
    public HashSet<String> duplexNumber(String[] str , int length){
        List<String> list = new ArrayList<>();
        for(int i=0;i<str.length;i++){
            for(int j = 0; j < str.length; j++){
                if(i != j){
                    if(length==2){
                        Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j])};
                        Arrays.sort(str1);
                        list.add(String.format("%02d", str1[0])+","+String.format("%02d", str1[1]));
                    }else {
                        for (int k = 0; k < str.length; k++) {
                            if (k != i && k != j) {
                                if(length==3){
                                    Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k])};
                                    Arrays.sort(str1);
                                    list.add(String.format("%02d", str1[0])+","+String.format("%02d", str1[1])+","+String.format("%02d", str1[2]));
                                }else {
                                    for (int m = 0; m < str.length; m++) {
                                        if (m != i && m != k && m != j) {
                                            if (length == 4) {
                                                Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m])};
                                                Arrays.sort(str1);
                                                list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]));
                                            } else {
                                                for (int n = 0; n < str.length; n++) {
                                                    if (n != i && n != j && n != k && n != m) {
                                                        if (length == 5) {
                                                            Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n])};
                                                            Arrays.sort(str1);
                                                            list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]));
                                                        } else {
                                                            for (int o = 0; o < str.length; o++) {
                                                                if (o != i && o != j && o != k && o != m && o != n) {
                                                                    if (length == 6) {
                                                                        Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o])};
                                                                        Arrays.sort(str1);
                                                                        list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]));
                                                                    }else {
                                                                        for (int p = 0; p < str.length; p++) {
                                                                            if (p != i && p != j && p != k && p != m && p != n && p != o) {
                                                                                if (length == 7) {
                                                                                    Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o]), Integer.parseInt(str[p])};
                                                                                    Arrays.sort(str1);
                                                                                    list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]));
                                                                                }else {
                                                                                    for (int q = 0; q < str.length; q++) {
                                                                                        if (q != i && q != j && q != k && q != m && q != n && q != o && q != p) {
                                                                                            if (length == 8) {
                                                                                                Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o]), Integer.parseInt(str[p]), Integer.parseInt(str[q])};
                                                                                                Arrays.sort(str1);
                                                                                                list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]));
                                                                                            }else {
                                                                                                for (int r = 0; r < str.length; r++) {
                                                                                                    if (r != i && r != j && r != k && r != m && r != n && r != o && r != p && r != q) {
                                                                                                        if (length == 9) {
                                                                                                            Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o]), Integer.parseInt(str[p]), Integer.parseInt(str[q]), Integer.parseInt(str[r])};
                                                                                                            Arrays.sort(str1);
                                                                                                            list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]));
                                                                                                        }else {
                                                                                                            for (int s = 0; s < str.length; s++) {
                                                                                                                if (s != i && s != j && s != k && s != m && s != n && s != o && s != p && s != q && s != r) {
                                                                                                                    if (length == 10) {
                                                                                                                        Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o]), Integer.parseInt(str[p]), Integer.parseInt(str[q]), Integer.parseInt(str[r]), Integer.parseInt(str[s])};
                                                                                                                        Arrays.sort(str1);
                                                                                                                        list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]) + "," + String.format("%02d", str1[s]));
                                                                                                                    }else {
                                                                                                                        for (int t = 0; t < str.length; t++) {
                                                                                                                            if (t != i && t != j && t != k && t != m && t != n && t != o && t != p && t != q && t != r && t != s) {
                                                                                                                                if (length == 11) {
                                                                                                                                    Integer[] str1 = {Integer.parseInt(str[i]), Integer.parseInt(str[j]), Integer.parseInt(str[k]), Integer.parseInt(str[m]), Integer.parseInt(str[n]), Integer.parseInt(str[o]), Integer.parseInt(str[p]), Integer.parseInt(str[q]), Integer.parseInt(str[r]), Integer.parseInt(str[s]), Integer.parseInt(str[t])};
                                                                                                                                    Arrays.sort(str1);
                                                                                                                                    list.add(String.format("%02d", str1[i]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]) + "," + String.format("%02d", str1[s]) + "," + String.format("%02d", str1[t]));
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //去重集合
        return new HashSet<>(list);
    }

    /**
     * 胆拖投注
     * @param braveryNumber 胆码
     * @param towNumber 拖码
     * @param length
     * @return
     */
    public HashSet<String> braveryTow(String braveryNumber,String[] towNumber,int length){
        List<String> list = new ArrayList<>();
        for(int j = 0; j < towNumber.length; j++){
            if(length==2){
                Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j])};
                Arrays.sort(str1);
                list.add(String.format("%02d", str1[0])+","+String.format("%02d", str1[1]));
            }else {
                for (int k = 0; k < towNumber.length; k++) {
                    if(length==3){
                        Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k])};
                        Arrays.sort(str1);
                        list.add(String.format("%02d", str1[0])+","+String.format("%02d", str1[1])+","+String.format("%02d", str1[2]));
                    }else {
                        for (int m = 0; m < towNumber.length; m++) {
                            if (length == 4) {
                                Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m])};
                                Arrays.sort(str1);
                                list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]));
                            } else {
                                for (int n = 0; n < towNumber.length; n++) {
                                    if (length == 5) {
                                        Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n])};
                                        Arrays.sort(str1);
                                        list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]));
                                    } else {
                                        for (int o = 0; o < towNumber.length; o++) {
                                            if (length == 6) {
                                                Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o])};
                                                Arrays.sort(str1);
                                                list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]));
                                            }else {
                                                for (int p = 0; p < towNumber.length; p++) {
                                                    if (length == 7) {
                                                        Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o]), Integer.parseInt(towNumber[p])};
                                                        Arrays.sort(str1);
                                                        list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]));
                                                    }else {
                                                        for (int q = 0; q < towNumber.length; q++) {
                                                            if (length == 8) {
                                                                Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o]), Integer.parseInt(towNumber[p]), Integer.parseInt(towNumber[q])};
                                                                Arrays.sort(str1);
                                                                list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]));
                                                            }else {
                                                                for (int r = 0; r < towNumber.length; r++) {
                                                                    if (length == 9) {
                                                                        Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o]), Integer.parseInt(towNumber[p]), Integer.parseInt(towNumber[q]), Integer.parseInt(towNumber[r])};
                                                                        Arrays.sort(str1);
                                                                        list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]));
                                                                    }else {
                                                                        for (int s = 0; s < towNumber.length; s++) {
                                                                            if (length == 10) {
                                                                                Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o]), Integer.parseInt(towNumber[p]), Integer.parseInt(towNumber[q]), Integer.parseInt(towNumber[r]), Integer.parseInt(towNumber[s])};
                                                                                Arrays.sort(str1);
                                                                                list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]) + "," + String.format("%02d", str1[s]));
                                                                            }else {
                                                                                for (int t = 0; t < towNumber.length; t++) {
                                                                                    if (length == 11) {
                                                                                        Integer[] str1 = {Integer.parseInt(braveryNumber), Integer.parseInt(towNumber[j]), Integer.parseInt(towNumber[k]), Integer.parseInt(towNumber[m]), Integer.parseInt(towNumber[n]), Integer.parseInt(towNumber[o]), Integer.parseInt(towNumber[p]), Integer.parseInt(towNumber[q]), Integer.parseInt(towNumber[r]), Integer.parseInt(towNumber[s]), Integer.parseInt(towNumber[t])};
                                                                                        Arrays.sort(str1);
                                                                                        list.add(String.format("%02d", str1[0]) + "," + String.format("%02d", str1[j]) + "," + String.format("%02d", str1[k]) + "," + String.format("%02d", str1[m]) + "," + String.format("%02d", str1[n]) + "," + String.format("%02d", str1[o]) + "," + String.format("%02d", str1[p]) + "," + String.format("%02d", str1[q]) + "," + String.format("%02d", str1[r]) + "," + String.format("%02d", str1[s]) + "," + String.format("%02d", str1[t]));
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //去重集合
        return new HashSet<>(list);
    }

    /**
     * 获取数组中的最小值 (得到最小赔率)
     * @param arr
     * @return
     */
    public int getMin(int[] arr) {
        // 假设第一位是最小值
        int min = arr[0];
        for(int index = 0; index < arr.length; index ++) {
            // 判断数组元素的最小值
            if(arr[index] < min) {
                // 把最小值存储Min变量
                min = arr[index];
            }
        }
        return min;
    }
}
