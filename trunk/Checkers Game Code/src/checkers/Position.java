/*
 * This class accepts three integer values as input that correspond to
 * the board, row and column location on a 3 dimensional checker board
 */

package checkers;

public class Position
{
    private int board;
    private int row;
    private int column;
    
    public Position (int b, int r, int c)
    {
        board = b;
        row = r;
        column = c;
    }

    public int getBoard ()
    {
        return board;
    }

    public int getColumn ()
    {
        return column;
    }

    public int getRow ()
    {
        return row;
    }
}
