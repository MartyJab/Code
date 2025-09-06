/*
You will be given the location of a knight, and an end location. The knight can move in a "L" shape. 
"L" shape movement means that the knight can change it's x coordinate by 2 and it's y coordinate by 1 or it can change it's y coordinate by 2 and it's x coordinate by 1 (you can add and subtract from the x/y).

For example, if the knight is at the position (0, 0), it can move to:
(1,2), (1,-2), (2,1), (2,-1), (-1,2), (-1,-2), (-2,1), (-2, -1)

Your job is to return the least amount of steps needed to go from the position K (knight's start position) to E (end). You will only be given the knight starter coordinates (x1, y1) and the end coordinates (x2, y2).

Constrains: 1 <= x1,y1,x2,y2 <= 8
Examples:
knightBFS(1, 1, 8, 8) ➞ 6
knightBFS(1, 1, 3, 2) ➞ 1
knightBFS(8, 8, 3, 3) ➞ 4

Notes:

    This is a simplified version of this problem.
    This travel will always be possible.
    The chess board is 8x8.
*/

public class BFS_Chess {

    public static int knightBFS(int x1, int y1, int x2, int y2) {

        int random = 0;
        int count = 0;
        int xx1 = x1;
        int yy1 = y1;
        int length = 10000;
        int[] counts = new int[length];
        boolean b = false;

        for(int i = 0; i < length; i++){

            y1 = yy1;
            x1 = xx1;
            count = 0;
            b = false;

            while(b == false){

                random = (int) (Math.random() * 8);
                count++;

                if(random == 0){
                    y1 = y1 + 1;
                    x1 = x1 + 2;
                }
                if(random == 1){
                    y1 = y1 - 1;
                    x1 = x1 + 2;
                }
                if(random == 2){
                    y1 = y1 + 1;
                    x1 = x1 - 2;
                }
                if(random == 3){
                    y1 = y1 - 1;
                    x1 = x1 - 2;
                }
                if(random == 4){
                    y1 = y1 + 2;
                    x1 = x1 + 1;
                }
                if(random == 5){
                    y1 = y1 + 2;
                    x1 = x1 - 1;
                }
                if(random == 6){
                    y1 = y1 - 2;
                    x1 = x1 + 1;
                }
                if(random == 7){
                    y1 = y1 - 2;
                    x1 = x1 - 1;
                }
                if(x1 <= 0 || x1 > 8 || y1 <= 0 || y1 > 8){
                    x1 = x2;
                    y1 = y2;
                    count = 100;
                }
                if(x1 == x2 && y1 == y2){
                    b = true;
                }
            }
            counts[i] = count;
        }
        for(int i = 0; i < counts.length; i++){
            for(int n = 0; n < counts.length; n++)
            while(counts[i] > counts[n]){
                int temp = counts[i];
                counts[i] = counts[n];
                counts[n] = temp;
            }
        }
        count = counts[counts.length - 1];

        return count;
  }

    public static void main(String[] args) {
        System.out.println(knightBFS(1, 1, 8, 8));
        System.out.println(knightBFS(1, 1, 3, 2));
        System.out.println(knightBFS(8,8, 3, 3));
    }

}
