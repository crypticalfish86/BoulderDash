import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class is used for the frog pathfinding.
 * @param <T> A generic representing tiles.
 * @author Alex (Tsz Tung Yee)
 * @version 1.2
 */

public class PathFinder<T> {
    


    private final int sizeX;
    private final int sizeY;
    private final T[][] pathList;


    public PathFinder(T[][] pathList, int sizeX, int sizeY) {
        this.pathList = pathList;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }


    /**
     * computes the coordinates of the path.
     * @param start start coordinates of xy.
     * @param end end coordiantes of xy.
     * @param canWalk function that returns if a certain grid can be stepped on.
     * @return 
     */
    public ArrayList<int[]> computeGridFill(int[] start, int[] end, canWalk<T> canWalk) {

        int[][] distances = new int[sizeY][sizeX];
        for (int y = 0; y < sizeY; ++y) {
            for (int x = 0; x < sizeX; ++x) {
                distances[y][x] = Integer.MAX_VALUE;
            }
        }

        VecQueue<int[]> q = new VecQueue<>();
        distances[start[1]][start[0]] = 0;
        q.push(start);

        //flood fill
        int finalCost = 0;
        boolean found = false;
        while (q.hasNext()) {
            int[] pos = q.pop();

            if (pos[0] == end[0] && pos[1] == end[1]) {
                finalCost = distances[pos[1]][pos[0]];
                found = true;

                break;
            }

            int nextCost = distances[pos[1]][pos[0]] + 1;
            // System.out.println(nextCost);

            pushIfLower(distances, q, nextCost, pos[0] - 1, pos[1], canWalk);
            pushIfLower(distances, q, nextCost, pos[0] + 1, pos[1], canWalk);
            pushIfLower(distances, q, nextCost, pos[0], pos[1] - 1, canWalk);
            pushIfLower(distances, q, nextCost, pos[0], pos[1] + 1, canWalk);
        }

        if (!found) {
            return new ArrayList<>();
        }
        //back swipe to obtain the path


        // System.out.println(finalCost);

        ArrayList<int[]> output = new ArrayList<>();
        for (int i = 0; i < finalCost; ++i) {
            output.add(i, new int[]{});
        }
        

        //fill the array
        output.set(finalCost - 1, new int[] {end[0], end[1]});

        int currentX = end[0];
        int currentY = end[1];
        for (int i = finalCost - 1; i > 0; --i) {
            
            if (distances[currentY + 1][currentX] == i) {
                currentY = currentY + 1;
            } else if (distances[currentY - 1][currentX] == i) {
                currentY = currentY - 1;
            } else if (distances[currentY][currentX + 1] == i) {
                currentX = currentX + 1;
            } else if (distances[currentY][currentX - 1] == i) {
                currentX = currentX - 1;
            }

            
            output.set(i - 1, new int[] {currentX, currentY});
        }
        


        return output;
    }


    /**
     * checks if the given position is in range.
     * @param x
     * @param y
     * @return true if in position.
     */
    private boolean isVecInRange(int x, int y) {
        return x >= 0 && y >= 0 && x < sizeX && y < sizeY;
    }
    

    /**
     * adds an element to the queue it is allowed, has lower values than the current value.
     * @param vals old values.
     * @param q queue.
     * @param newVal new value.
     * @param newX new x position.
     * @param newY new y position.
     * @param canWalk boolean whether you can walk.
     */
    private void pushIfLower(int[][] vals, VecQueue<int[]> q, int newVal, int newX, int newY, canWalk<T> canWalk) {
        if (!isVecInRange(newX, newY)) { return; }

        if (!canWalk.e(pathList[newY][newX])) { return; }


        if (vals[newY][newX] > newVal) {
            //push
            vals[newY][newX] = newVal;
            q.push(new int[] {newX, newY});
        }
    }


    /**
     * Used for checking if the type T of element is considered valid.
     */

    public interface canWalk<T> {
        public boolean e(T item);
    }


    /**
     * Class for the vector queue to compute the flood fill.
     * @author Alex (Tsz Tung Yee)
     * @version 1.0
     * @param <U> vector variable.
     */
    private class VecQueue<U> {
        LinkedList<U> queue = new LinkedList<>();

        public VecQueue() { }

        public boolean hasNext() {
            return queue.peekFirst() != null;
        }

        public U pop() {
            return queue.removeFirst();
        }

        public void push(U elem) {
            queue.addLast(elem);
        }
    }

    
}
