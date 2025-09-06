/* 
Let's define a non-repeating integer as one whose digits are all distinct. 97653 is non-repeating while 97252 is not (it has two 2's). 
Among the binary numbers, there are only two positive non-repeating integers: 1 and 10. Ternary (base 3) has ten: 1, 2, 10, 20, 12, 21, 102, 201, 120, 210.

Write a function that has as it's argument the base or radix and returns the number of non-repeating positive integers in that base.
Examples:
nonRepeats(2) ➞ 2
nonRepeats(4) ➞ 48
nonRepeats(5) ➞ 260
nonRepeats(6) ➞ 1630

Notes:
Assume a radix of 1 is not legitimate.
*/

public class Non_Repeating_Integers {
	public static int nonRepeats(int radix) {
		int count = 1;
        int count2 = 0;

        for(int i = radix - 1; i > 0; i--){

            count = 1;

            for(int n = radix - 1; n >= i; n--){
                count = count * n;
            }
            count = count * (radix - 1);
            count2 = count2 + count;
        }
        return count2 + radix - 1;
  }

    public static void main(String[] args) {
        System.out.println(nonRepeats(2));
        System.out.println(nonRepeats(4));
        System.out.println(nonRepeats(5));
        System.out.println(nonRepeats(6));  
    }

}
