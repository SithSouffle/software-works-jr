package solution;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * A maze game.
 * 
 * @author Lucas Thompson
 * @version 1.0
 *
 */
public class MazeGame
{
    /**
     * The size of each side of the game map.
     */
    private final static int HEIGHT = 19;
    private final static int WIDTH = 39;

    /**
     * The game map, as a 2D array of ints.
     */
    private boolean[][] blocked;

    /**
     * Data structure that tracks where the player has been.
     */
    private boolean[][] breadCrumb = new boolean[HEIGHT][WIDTH];
    /**
     * The current location of the player vertically.
     */
    // TODO: add field here.
    private int userCol;

    /**
     * The current location of the player horizontally.
     */
    // TODO: add field here.
    private int userRow;

    /**
     * The scanner from which each move is read.
     */
    // TODO: add field here.
    private Scanner moveScanner;

    /**
     * The row and column of the goal.
     */
    // TODO: add fields here.
    private int goalRow;
    private int goalColumn;

    /**
     * The row and column of the start.
     */
    // TODO: add fields here.
    private int startRow;
    private int startColumn;

    /**
     * Constructor initializes the maze with the data in 'mazeFile'.
     * 
     * @param mazeFile
     *            the input file for the maze
     */
    public MazeGame(String mazeFile)
    {
        // TODO
        loadMaze(mazeFile);
    }

    /**
     * Constructor initializes the maze with the 'mazeFile' and the move scanner
     * with 'moveScanner'.
     * 
     * @param mazeFile
     *            the input file for the maze
     * @param moveScanner
     *            the scanner object from which to read user moves
     */
    public MazeGame(String mazeFile, Scanner moveScanner)
    {
        // TODO
        this.moveScanner = moveScanner;
        loadMaze(mazeFile);

    }
    /**
     * 
     * @return Scanner 
     */
    public Scanner getMoveScanner()
    {
        return moveScanner;
    }
    /**
     * 
     * @param moveScanner n
     */
    public void setMoveScanner(Scanner moveScanner) 
    {
        this.moveScanner = moveScanner;
    }

    /**
     * 
     * @return int
     */
    public int getGoalRow()
    {
        return goalRow;
    }
    
    /**
     * 
     * @param goalRow n
     */
    public void setGoalRow(int goalRow)
    {
        this.goalRow = goalRow;
    }

    /**
     * 
     * @return int
     */
    public int getGoalColumn()
    {
        return goalColumn;
    }

    /**
     * 
     * @param goalColumn n
     */
    public void setGoalColumn(int goalColumn)
    {
        this.goalColumn = goalColumn;
    }

    /**
     * 
     * @return int
     */
    public int getStartRow()
    {
        return startRow;
    }

    /**
     * 
     * @param startRow n
     */
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }

    /**
     * 
     * @return int
     */
    public int getStartColumn()
    {
        return startColumn;
    }

    /**
     * 
     * @param startColumn n
     */
    public void setStartColumn(int startColumn)
    {
        this.startColumn = startColumn;
    }

    /**
     * 
     * @return int
     */
    public int getUserCol()
    {
        return userCol;
    }

    /**
     * 
     * @param userCol n
     */
    public void setUserCol(int userCol)
    {
        this.userCol = userCol;
    }

    /**
     * 
     * @return int
     */
    public int getUserRow()
    {
        return userRow;
    }

    /** 
     * 
     * @param userRow int
     */
    public void setUserRow(int userRow)
    {
        this.userRow = userRow;
    }

    /**
     * getMaze returns a copy of the current maze for testing purposes.
     * 
     * @return the grid
     */
    public boolean[][] getMaze()
    {
        if (blocked == null)
        {
            return null;
        }
        boolean[][] copy = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                copy[i][j] = blocked[i][j];
            }
        }
        return copy;
    }

    /**
     * setMaze sets the current map for testing purposes.
     * 
     * @param maze
     *            another maze.
     */
    public void setMaze(boolean[][] maze)
    {
        this.blocked = maze;
    }

    /**
     * Function loads the data from the maze file and creates the 'blocked' 2D
     * array.
     * 
     * @param mazeFile
     *            the input maze file.
     */
    public void loadMaze(String mazeFile)
    {
        this.blocked = new boolean[HEIGHT][WIDTH];

        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(mazeFile);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File Not Found");
            System.exit(0);
        }
        Scanner scanner = new Scanner(fileReader);

        while (scanner.hasNext())
        {
            for (int i = 0; i < HEIGHT; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    String nextString = scanner.next();
                    if (nextString.equalsIgnoreCase("1")) 
                    {
                        this.blocked[i][j] = true; 
                        // boolean true means blocked
                    } 
                    else if (nextString.equalsIgnoreCase("0"))
                    {
                        this.blocked[i][j] = false; 
                        // boolean false means open
                    } 
                    else if (nextString.equalsIgnoreCase("s"))
                    {
                        this.blocked[i][j] = false;
                        // instantiate the fields with the position
                        setStartRow(i);
                        setStartColumn(j);
                    } 
                    else if (nextString.equalsIgnoreCase("g"))
                    {
                        this.blocked[i][j] = false;
                        // instantiate the fields with the position
                        setGoalRow(i);
                        setGoalColumn(j);
                    }
                }
            }
        }
        scanner.close();
    }

    /**
     * Actually plays the game.
     */
    public void playGame()
    {
        moveScanner = new Scanner(System.in);
        while (((getUserRow() != getGoalRow()) && (getUserCol() 
            != getGoalColumn())) || (moveScanner.next() != "quit")) 
        {
            int numMoves = 0;
            printMaze();
            System.out.println("Enter 'quit' to end the game or 'up', 'down', "
                + "'left', and 'right' to traverse the maze");
            String move = moveScanner.next();
            if (move.equalsIgnoreCase("quit")) 
            {
                System.exit(0);
            }
            makeMove(move);
            numMoves++;
            if (playerAtGoal()) 
            {
                System.out.println("It took you " + numMoves 
                    + " moves to escape the maze");
                System.exit(0);
            }
        }
        
        System.exit(0);
        
    }

    /**
     * Checks to see if the player has won the game.
     * 
     * @return true if the player has won.
     */
    public boolean playerAtGoal()
    {
        return getUserRow() == getGoalRow() && getUserCol() == getGoalColumn();
    }

    /**
     * Makes a move based on the String.
     * 
     * @param move
     *            the direction to make a move in.
     * @return whether the move was valid.
     */
    public boolean makeMove(String move)
    {   
//        for (int i = 0; i < HEIGHT; i++) 
//        {
//            for (int j = 0; j < WIDTH; j++)
//            {            
//                this.breadCrumb[i][j] = false;  
//            }
//        }
        
        if (move.equalsIgnoreCase("up"))
        {
            try
            {
                if (this.blocked[getUserRow() - 1][getUserCol()]) 
                {
                    return false;
                }
                setUserRow(getUserRow() - 1);
                this.breadCrumb[getUserRow()][getUserCol()] = true;
                
            }
            catch (Exception e)
            {
                System.out.println("Invalid Move");
            }
            
        } 
        else if (move.equalsIgnoreCase("down"))
        {
            try
            {
                if (this.blocked[getUserRow() + 1][getUserCol()])
                {
                    return false;
                }
                setUserRow(getUserRow() + 1);
                this.breadCrumb[getUserRow()][getUserCol()] = true;
            }
            catch (Exception e)
            {
                System.out.println("Invalid Move");
            }
        } 
        else if (move.equalsIgnoreCase("left"))
        {
            try
            {
                if (this.blocked[getUserRow()][getUserCol() - 1])
                {
                    return false;
                }
                setUserCol(getUserCol() - 1); 
                this.breadCrumb[getUserRow()][getUserCol()] = true;
            }
            catch (Exception e)
            {
                System.out.println("Invalid Move");
            }
        } 
        else if (move.equalsIgnoreCase("right"))
        {
            try
            {
                if (this.blocked[getUserRow()][getUserCol() + 1])
                {
                    return false;
                }
                setUserCol(getUserCol() + 1);
                this.breadCrumb[getUserRow()][getUserCol()] = true;
            }
            catch (Exception e)
            {
                System.out.println("Invalid Move");
            }
        } 
        else if (move.equalsIgnoreCase("quit"))
        {
            return true;
        }
        else 
        {
            System.out.println("invalid move");
            return false;
        }
        return false;
    }

    /**
     * Prints the map of the maze.
     */
    public void printMaze()
    {
        for (int i = 0; i <= WIDTH + 1; i++)
        {
            if (i == WIDTH + 1 || i == 0)
            {
                System.out.print("*");
            }
            else
            {
                System.out.print("-"); 
            }
        }
        System.out.print("\n");
        for (int i = 0; i < HEIGHT; i++)
        {
            System.out.print("|");
            for (int j = 0; j < WIDTH; j++)
            {
                
                if (getUserRow() == i && getUserCol() == j)
                {
                    System.out.print("@");
                }
                else if (getStartRow() == i && getStartColumn() == j)
                {
                    System.out.print("S");
                }
                else if (getGoalRow() == i && getGoalColumn() == j)
                {
                    System.out.print("G");                    
                }
                else if (this.blocked[i][j] == false)
                {
                    if (this.breadCrumb[i][j] == true)
                    {
                        System.out.print('.');
                    } 
                    else 
                    {
                        System.out.print(" ");
                    }
                }
                else if (this.blocked[i][j] == true)
                {
                    System.out.print("X");
                }
            }
            System.out.print("|");
            System.out.print("\n");
        }
        for (int i = 0; i <= WIDTH + 1; i++)
        {
            if (i == WIDTH + 1 || i == 0)
            {
                System.out.print("*");
            }
            else
            {
                System.out.print("-"); 
            }
        }
        System.out.print("\n");
    }

    /**
     * Creates a new game, using a command line argument file name, if one is
     * provided.
     * 
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args)
    {
        String mapFile = "data/easy.txt";
        Scanner scan = new Scanner(System.in);
        MazeGame game = new MazeGame(mapFile, scan);
        game.playGame();
    }
}
