/**
 * Given a set of 10 random positive integers (in range 1-100), 
 * write a code to conecate the integers in such a way that the concatenation 
 * of the numbers gives the smallest possible integer. 
 * Show us original list of integers and smallest number. 
 * Example: 
 * List: 78, 52, 5, 98, 42, 21, 9, 67, 18, 6 
 * Smallest number : 18214252566778989
 */
package integersconcat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class IntegersConcat {

    private final List<Integer> rndNumbers;
    private final int size;
    private final List<Integer> oneDigit;
    private final List<Integer> twoDigit;

    public static void main(String[] args) {
        IntegersConcat ic = create(10);
        ic.separate();
        System.out.println(ic.oneDigit + "\n" + ic.twoDigit);
        System.out.printf("List: %s%nSmallest number: %s%n", ic, ic.concat());
        Integer[] array = {71,85,50,62,76,76,25,26,95,82};
        IntegersConcat test = new IntegersConcat(Arrays.asList(array));
//        test.separate();
//        System.out.println(test.oneDigit + "\n" + test.twoDigit);
//        System.out.printf("List: %s%nSmallest number: %s%n", test, 
//                test.concat().equals("25265062717676828595"));
    }
    
    private IntegersConcat(List<Integer> args) {
        this.rndNumbers = args;
        this.size = args.size();
        this.oneDigit = new ArrayList<>();
        this.twoDigit = new ArrayList<>();
    }
    
    public static IntegersConcat create(int size) {
        List<Integer> list = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            list.add(rnd.nextInt(99) + 1);
        }
        return new IntegersConcat(list);
    }
    
    public void separate() {
        rndNumbers.forEach((number) -> {
            if (number / 10 == 0) {
                oneDigit.add(number);
            } else {
                twoDigit.add(number);
            }
        });
        Collections.sort(oneDigit);
        Collections.sort(twoDigit);
    }
    
    public String concat() {
        String result = "";
        int i = 0, j = 0;
        if (this.oneDigit.size() > 0) {
            while (i < this.oneDigit.size() && j < this.twoDigit.size() &&
                    i + j < this.size) {
                String oneD = String.valueOf(this.oneDigit.get(i));
                String twoD = String.valueOf(this.twoDigit.get(j));
                if (oneD.charAt(0) < twoD.charAt(0)) {
                    result += oneD;
                    i++;
                } else if (oneD.charAt(0) == twoD.charAt(0)) {
                    if (oneD.charAt(0) <= twoD.charAt(1)) {
                        result += oneD;
                        i++;
                    } else {
                        result += twoD;
                        j++;
                    }
                } else {
                    result += twoD;
                    j++;
                }
            }
            for (;i < this.oneDigit.size(); i++) {
                result += String.valueOf(this.oneDigit.get(i));
            }
            for (;j < this.twoDigit.size(); j++) {
                result += String.valueOf(this.twoDigit.get(j));
            }
        } else {
            List<Integer> sorted = rndNumbers;
            Collections.sort(sorted);
            result = sorted.stream().map((number) -> 
                    String.valueOf(number)).reduce(result, String::concat);
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "";
        result = rndNumbers.stream().map((number) -> 
                number + ", ").reduce(result, String::concat);
        return result.substring(0, result.length() - 2);
    }
}