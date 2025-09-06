/*
A number is said to be Disarium if the sum of its digits raised to their respective positions is the number itself.
Create a function that determines whether a number is a Disarium or not.

Examples:
isDisarium(75) ➞ false
// 7^1 + 5^2 = 7 + 25 = 32
isDisarium(135) ➞ true
// 1^1 + 3^2 + 5^3 = 1 + 9 + 125 = 135
isDisarium(544) ➞ false
isDisarium(518) ➞ true
isDisarium(8) ➞ true
isDisarium(466) ➞ false

Notes:
    Position of the digit is 1-indexed.
    A recursive version of this challenge can be found via this link.
*/

public class DisariumNumber {

    public static boolean isDisarium(int number) {
		boolean b = false;
        int count = (int) Math.log10(number);
        int disarium = 0; 
        int initialNumber = number;
        int[] digits = new int[(int) (Math.log10(number) + 1)];
        while (number > 0) {
            digits[count] = (number % 10);
            number = number / 10;
            count --;
        }
        for(int i = 0; i < digits.length; i++){
            disarium += Math.pow(digits[i], i+1);
        }
        if(disarium == initialNumber){
            b = true;
        }
        return b;
	}

    public static void main(String[] args) {
        System.out.println(isDisarium(75));
        System.out.println(isDisarium(135));
        System.out.println(isDisarium(544));
        System.out.println(isDisarium(518));
        System.out.println(isDisarium(8));
        System.out.println(isDisarium(466));
    }

}
