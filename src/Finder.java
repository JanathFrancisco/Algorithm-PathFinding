/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author win 8
 */
import java.awt.*;
import java.util.*;

public class Finder {

    Node start;
    Node end;
    Node[][] gridArea;// gridArea array 

    // Horizontal and VerticalDistance
    double hVDistance = 1.0;

    // Diagonal Distance
    //Manhattan values.
    public static double Manhattan(){

        double DiagonalDistance = 2;
        return DiagonalDistance;
    }

    //Euclidean values.
    public static double Euclidean() {
        double DiagonalDistance = 1.4;
        return DiagonalDistance;
    }

    //Chebyshev values

    public static double Chebyshev() {
        double DiagonalDistance = 1;
        return DiagonalDistance;
    }

    ///double dDistance = 1.4;

   /* for Manhattan Distances,
    double horizontalVerticalDistance = 1.0;
    double diagonalDistance = 2.0;

    for Chebyshev Distances,
    double horizontalVerticalDistance = 1.0;
    double diagonalDistance = 1.0; */

    /**
     *
     * @param matrix The boolean matrix from the framework given
     * @param sx start x value
     * @param sy start y value
     * @param ex end x value
     * @param ey end x value
     * @return The path nodes
     */
    ArrayList<Node> distance(boolean[][] matrix, int sx, int sy, int ex, int ey,double DiagonalDistance,String name) {

         int size = matrix.length;
        //initialising the point of  starting node
        start = new Node(sx, sy);
        //initialising the point of ending node
        end = new Node(ex, ey);
        // The grid that is used to store nodes
        gridArea = new Node[size][size];
        // Creating nodes and finding blocked cells in matrix and mapping accordingly to our grid
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                gridArea[i][j] = new Node(i, j);//creating new node
                if (matrix[i][j] == false) {// checking if that node is blocked or not
                    
                    gridArea[i][j].blocked = true;

                }
            }
        }

        // setting start distance to 0.
        // All other nodes will have infinity distance at the beginning
        start.distance =0;

        // a comparator object to deal with Priority Queue
        Comparator <Node> adjacencyComparator = (left, right) -> {
            if (left.distance > (right.distance)) {
                return 1; // swapping values

            }
            return -1;
        };

        // Queue to store visiting nodes
        Queue<Node> queue = new PriorityQueue(size, adjacencyComparator);

        queue.add(start);

        while (queue.size() > 0) {
            Node current = queue.remove();
            //saving the next selected node
            Node newNode;
            //breaks the loop when then end node becomes the current node
            if(current.x==end.x && current.y==end.y){
            }
              // Top
            // checks whether there is a node in the top
            
            if (current.x - 1 >= 0) {

                // Top Top
                newNode = gridArea[current.x - 1][current.y];
               //checking whether newNode is visited ,blocked or the distance equals to
                if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + hVDistance) {
                    newNode.distance = current.distance + hVDistance;
                    newNode.parent = current;
                    queue.add(newNode);
                }

                // Top Left
               if (current.y - 1 > 0) {
                    newNode = gridArea[current.x - 1][current.y - 1];
                    if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + DiagonalDistance) {
                        newNode.distance = current.distance + DiagonalDistance;
                        newNode.parent = current;
                        queue.add(newNode);
                    }
                }

                // Top Right
               if (current.y + 1 < size) {
                    newNode = gridArea[current.x - 1][current.y + 1];
                    if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + DiagonalDistance) {
                        newNode.distance = current.distance + DiagonalDistance;
                        newNode.parent = current;
                        queue.add(newNode);
                    }
                }
            }

            // Left
            if (current.y - 1 > 0) {
                newNode = gridArea[current.x][current.y - 1];
                if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + hVDistance) {
                    newNode.distance = current.distance + hVDistance;
                    newNode.parent = current;
                    queue.add(newNode);
                }
            }

            // Right
            if (current.y + 1 < size) {
                newNode = gridArea[current.x][current.y + 1];
                if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + hVDistance) {
                    newNode.distance = current.distance + hVDistance;
                    newNode.parent = current;
                    queue.add(newNode);
                }
            }
            // Down
            if (current.x + 1 < size) {

                // Down Down
                newNode = gridArea[current.x + 1][current.y];
                if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + hVDistance) {
                    newNode.distance = current.distance + hVDistance;
                    newNode.parent = current;
                    queue.add(newNode);
                }

                // Down Left
               if (current.y - 1 >= 0) {
                    newNode = gridArea[current.x + 1][current.y - 1];
                    if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + DiagonalDistance) {
                        newNode.distance = current.distance + DiagonalDistance;
                        newNode.parent = current;
                        queue.add(newNode);
                    }
                }

                // Down Right
               if (current.y + 1 < size) {
                    newNode = gridArea[current.x + 1][current.y + 1];
                    if (!newNode.visited && !newNode.blocked && newNode.distance > current.distance + DiagonalDistance) {
                        newNode.distance = current.distance + DiagonalDistance;
                        newNode.parent = current;
                        queue.add(newNode);
                    }
                }
            }
            current.visited = true;
        }
        
        ArrayList<Node> path = new ArrayList<>();

        // Checking if a path exists
        if (!(gridArea[end.x][end.y].distance == Integer.MAX_VALUE||gridArea[start.x][start.y].distance== Integer.MAX_VALUE)) {
            //Trace back the path
            Node current = gridArea[end.x][end.y];
            System.out.println(name+":"+current.distance);
            
            while (current.parent != null) {
                path.add(current.parent);

                current = current.parent;
            }
        } else System.out.println("No possible path");


        return path;
    }


    class Node {
        int x;
        int y;
        double distance = Integer.MAX_VALUE;
        Node parent = null;
        boolean visited;
        boolean blocked;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public static void show(boolean [][]a , boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);//show all cells without black cells
                else StdDraw.filledSquare(j, N - i - 1, .5);//show black cells
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2,ArrayList<Node> path) {
        int N = a.length;
        int s=path.size();
        int count=0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.setPenColor(Color.GREEN);
                        StdDraw.filledCircle(j, N - i - 1, .5);

                    }


        for (Finder.Node node : path) {
            if(s-count==1){
                return;
            }
            count++;

                StdDraw.setPenColor(Color.RED);

            StdDraw.circle(node.y,  N- node.x - 1, .5);
            //path.remove(node.y);

        }
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

    public static void main(String[] args){
        
        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(10, 0.6);

        StdArrayIO.print(randomlyGenMatrix);
        show(randomlyGenMatrix, true);

        // Reading the coordinates for points A and B on the input squared grid.

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
        

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();
        Stopwatch timerFlow = new Stopwatch();
        ArrayList<Finder.Node> path1 = new Finder().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Manhattan(),"Manhattan");
       // System.out.println((timerFlow.elapsedTime()));
        ArrayList<Finder.Node> path2 = new Finder().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Euclidean(),"Euclidean");
        System.out.println((timerFlow.elapsedTime()));
        ArrayList<Finder.Node> path3= new Finder().distance(randomlyGenMatrix, Ai, Aj, Bi, Bj,Chebyshev(),"Chebyshev");

       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path2);
       // show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path1);
        show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj, path3);

    }
}

