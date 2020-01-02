import javafx.util.Pair;
import java.util.*;

public class Maze {
    private int rows;
    private int cols;
    private DisjSets set;
    private ArrayList<Pair<Integer, Integer>> edges = new ArrayList<>();
    private ArrayList<Pair<Integer, Integer>> mazeBuilder = new ArrayList<>();

    public Maze(int r, int c){
        rows = r; cols = c;
        set = new DisjSets(rows*cols);
        populateConnections();
        while(notAllCellsAreConnected()){ //while there isn't a connection between the start and the end
            Pair<Integer, Integer> randomEdge = edges.get((int)(Math.random()*(edges.size()))); //get a random edge
            if(set.find(randomEdge.getKey()) != set.find(randomEdge.getValue())) //if they aren't connected already
                set.union(set.find(randomEdge.getKey()), set.find(randomEdge.getValue())); //connect them
            else { //otherwise, since they are already connected
                if(!mazeBuilder.contains(randomEdge)) //if it isn't already an edge to be considered in the maze
                    mazeBuilder.add(randomEdge); //add it to the mazeBuilder list to prevent cycles
            }
            edges.remove(randomEdge);
        }
        mazeBuilder.addAll(edges); //add any remaining edges to mazeBuilder
    }

    private void populateConnections(){
        //this populates the edges list
        //with all the INTERNAL edges
        for(int y = 0; y<rows; y++){
            for(int x = 0;x<cols;x++){
                int n = (y*cols)+x; //the cell "number"
                if(x!=cols-1)
                    //no trailing left edges
                    edges.add(new Pair<>(n,n+1));
                if(y!=rows-1)
                    //no trailing bottom edges
                    edges.add(new Pair<>(n,n+cols));
            }
        }
    }

    private boolean notAllCellsAreConnected(){
        for(int x = 1; x<rows*cols; x++){
            if(set.find(x)!=set.find(0))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        //this is all formatting for the output
        //this was the hardest part...
        StringBuilder sb = new StringBuilder();
        sb.append("+-".repeat(cols)).append("+");
        sb.append('\n');
        for(int y = 0; y<rows; y++){ //print "internal" rows
            if(y!=0) sb.append("|");
            else sb.append(" s");
            for(int x = 0; x<cols-1; x++){
                int n = (y*cols)+x;
                if(!(x==0 && y==0)) sb.append(" ");
                if(mazeBuilder.contains(new Pair<>(n,n+1)))
                    sb.append("|");
                else
                    sb.append(" ");
            }
            if(y!=rows-1) sb.append(" |\n");
            else sb.append("e\n");
            if(y!=rows-1){
                for(int x = 0; x<=cols; x++){
                    int n = (y*cols)+x;
                    sb.append('+');
                    if(mazeBuilder.contains(new Pair<>(n,n+cols)))
                        sb.append("-");
                    else
                        sb.append(" ");
                }
                sb.append('\n');
            }
        }
        sb.append("+-".repeat(cols)).append("+");
        return sb.toString();
    }
}