import java.util.*;

public class Driver {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("How many rows should the Maze have?");
        int r = sc.nextInt();
        System.out.println("How many columns should the Maze have?");
        int c = sc.nextInt();
        Maze m = new Maze(r,c);
        System.out.println(m);
        System.out.println("\nWhere s is start and e is end");
        System.out.println("Note: '-' and '|' indicate a Wall");
    }
}