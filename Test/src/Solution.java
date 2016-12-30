import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

enum Type {
    Queen('Q') {
        @Override
        public List<Position> getPositions(Position position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean canCaptureQueen(Piece opponentQueen, Position currentPosition) {
            int opponentRow = opponentQueen.position.row;
            int opponentColumn = opponentQueen.position.column;

            int currentRow = currentPosition.row;
            int currentColumn = currentPosition.column;

            if (opponentRow < currentRow) {
                if (opponentColumn == currentColumn) {
                    for (int i = currentRow - 1; i >= 0; i--) {
                        if (opponentRow == i) {
                            return true;
                        } else if (!Solution.chessBoard[i][currentColumn].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn < currentColumn) {
                    for (int i = currentRow - 1, j = currentColumn - 1; i >= 0 && j >= 0; i--, j--) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                } else {
                    for (int i = currentRow - 1, j = currentColumn + 1; i >= 0 && j < 4; i--, j++) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                }
            } else if (opponentRow > currentRow) {
                if (opponentColumn == currentColumn) {
                    for (int i = currentRow + 1; i < 4; i++) {
                        if (opponentRow == i) {
                            return true;
                        } else if (!Solution.chessBoard[i][currentColumn].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn < currentColumn) {
                    for (int i = currentRow + 1, j = currentColumn - 1; i < 4 && j >= 0; i++, j--) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                } else {
                    for (int i = currentRow + 1, j = currentColumn + 1; i < 4 && j < 4; i++, j++) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                }
            }
            if (opponentRow == currentRow) {
                if (opponentColumn > currentColumn) {
                    for (int i = currentColumn + 1; i < 4; i++) {
                        if (opponentColumn == i) {
                            return true;
                        } else if (!Solution.chessBoard[currentRow][i].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn < currentColumn) {
                    for (int i = currentColumn - 1; i >= 0; i--) {
                        if (opponentColumn == i) {
                            return true;
                        } else if (!Solution.chessBoard[currentRow][i].isEmpty()) {
                            break;
                        }
                    }
                }
            }
            return false;
        }
    },
    Knight('N') {
        @Override
        public List<Position> getPositions(Position position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean canCaptureQueen(Piece opponentQueen, Position currentPosition) {
            int opponentRow = opponentQueen.position.row;
            int opponentColumn = opponentQueen.position.column;

            int currentRow = currentPosition.row;
            int currentColumn = currentPosition.column;

            if (opponentRow < currentRow) {
                if (opponentColumn < currentColumn) {
                    if (currentColumn - 2 >= opponentColumn && currentRow - 1 >= opponentRow) {
                        return true;
                    } else if (currentColumn - 1 >= opponentColumn && currentRow - 2 >= opponentRow) {
                        return true;
                    }
                } else if (opponentColumn > currentColumn) {
                    if (currentColumn + 2 >= opponentColumn && currentRow - 1 >= opponentRow) {
                        return true;
                    } else if (currentColumn + 1 >= opponentColumn && currentRow - 2 >= opponentRow) {
                        return true;
                    }
                }
            } else if (opponentRow > currentRow) {
                if (opponentColumn < currentColumn) {
                    if (currentColumn - 2 >= opponentColumn && currentRow + 1 >= opponentRow) {
                        return true;
                    } else if (currentColumn - 1 >= opponentColumn && currentRow + 2 >= opponentRow) {
                        return true;
                    }
                } else if (opponentColumn > currentColumn) {
                    if (currentColumn + 2 >= opponentColumn && currentRow + 1 >= opponentRow) {
                        return true;
                    } else if (currentColumn + 1 >= opponentColumn && currentRow + 2 >= opponentRow) {
                        return true;
                    }
                }
            }

            return false;
        }
    },
    Bishop('B') {
        @Override
        public List<Position> getPositions(Position position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean canCaptureQueen(Piece opponentQueen, Position currentPosition) {
            int opponentRow = opponentQueen.position.row;
            int opponentColumn = opponentQueen.position.column;

            int currentRow = currentPosition.row;
            int currentColumn = currentPosition.column;

            if (opponentRow < currentRow) {
                if (opponentColumn < currentColumn) {
                    for (int i = currentRow - 1, j = currentColumn - 1; i >= 0 && j >= 0; i--, j--) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn > currentColumn) {
                    for (int i = currentRow - 1, j = currentColumn + 1; i >= 0 && j < 4; i--, j++) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                }
            } else if (opponentRow > currentRow) {
                if (opponentColumn < currentColumn) {
                    for (int i = currentRow + 1, j = currentColumn - 1; i < 4 && j >= 0; i++, j--) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn > currentColumn) {
                    for (int i = currentRow + 1, j = currentColumn + 1; i < 4 && j < 4; i++, j++) {
                        if (opponentRow == i && opponentColumn == j) {
                            return true;
                        } else if (!Solution.chessBoard[i][j].isEmpty()) {
                            break;
                        }
                    }
                }
            }

            return false;
        }
    },
    Rook('R') {
        @Override
        public List<Position> getPositions(Position position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean canCaptureQueen(Piece opponentQueen, Position currentPosition) {
            int opponentRow = opponentQueen.position.row;
            int opponentColumn = opponentQueen.position.column;

            int currentRow = currentPosition.row;
            int currentColumn = currentPosition.column;

            if (opponentColumn == currentColumn) {
                if (opponentRow < currentRow) {
                    for (int i = currentRow - 1; i >= 0; i--) {
                        if (opponentRow == i) {
                            return true;
                        } else if (!Solution.chessBoard[i][currentColumn].isEmpty()) {
                            break;
                        }
                    }
                }
            } else if (opponentRow > currentRow) {
                if (opponentColumn == currentColumn) {
                    for (int i = currentRow + 1; i < 4; i++) {
                        if (opponentRow == i) {
                            return true;
                        } else if (!Solution.chessBoard[i][currentColumn].isEmpty()) {
                            break;
                        }
                    }
                }
            }
            if (opponentRow == currentRow) {
                if (opponentColumn > currentColumn) {
                    for (int i = currentColumn + 1; i < 4; i++) {
                        if (opponentColumn == i) {
                            return true;
                        } else if (!Solution.chessBoard[currentRow][i].isEmpty()) {
                            break;
                        }
                    }
                } else if (opponentColumn < currentColumn) {
                    for (int i = currentColumn - 1; i >= 0; i--) {
                        if (opponentColumn == i) {
                            return true;
                        } else if (!Solution.chessBoard[currentRow][i].isEmpty()) {
                            break;
                        }
                    }
                }
            }
            return false;
        }
    };

    Character value;

    Type(Character value) {
        this.value = value;
    }

    public Character getValue() {
        return value;
    }

    public static Type getPiece(Character piece) {
        switch (piece) {
            case 'Q':
                return Type.Queen;
            case 'N':
                return Type.Knight;
            case 'B':
                return Type.Bishop;
            case 'R':
                return Type.Rook;
        }
        return null;
    }

    public abstract List<Position> getPositions(Position position);

    public abstract boolean canCaptureQueen(Piece opponentQueen, Position currentPosition);
}


enum Color {
    White, Black
}


class Position {
    int row;
    int column;

    public Position() {

    }

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }
}


class Piece {
    Type     type;
    Position position;
    Color    color;

    public List<Position> getPositions() {
        return type.getPositions(position);
    }

    public boolean canCaptureQueen(Piece opponentQueen) {
        return type.canCaptureQueen(opponentQueen, position);
    }
}


class Box {
    Piece piece;

    public boolean isEmpty() {
        return piece == null;
    }
}


public class Solution {

    public static boolean     isWhiteQueenPresent = true;

    public static boolean     isBlackQueenPresent = true;

    public static List<Piece> whitePieces         = new ArrayList<>();
    public static List<Piece> blackPieces         = new ArrayList<>();

    public static Piece       blackQueen;
    public static Piece       whiteQueen;

    public static Color       turn                = Color.White;

    public static Box[][]     chessBoard;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalGames = sc.nextInt();
        for (int i = 0; i < totalGames; i++) {
            int whiteCoins = sc.nextInt();
            int blackCoins = sc.nextInt();
            int noOfMoves = sc.nextInt();
            sc.nextLine(); // skip new line.

            chessBoard = new Box[4][4];
            isWhiteQueenPresent = false;
            isBlackQueenPresent = false;
            turn = Color.White;

            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    chessBoard[j][k] = new Box();
                }
            }

            whitePieces = new ArrayList<>();
            blackPieces = new ArrayList<>();

            for (int j = 0; j < whiteCoins; j++) {
                String[] move = sc.nextLine().split(" ");
                Piece piece = insertPiece(move, chessBoard, Color.White);
                whitePieces.add(piece);
            }

            for (int j = 0; j < blackCoins; j++) {
                String[] move = sc.nextLine().split(" ");
                Piece piece = insertPiece(move, chessBoard, Color.Black);
                blackPieces.add(piece);
            }

            //displayChessBoard(chessBoard);

            playGame(chessBoard, noOfMoves);

            if (isBlackQueenPresent && isWhiteQueenPresent) {
                if (whitePieces.size() > blackPieces.size())
                    System.out.println("YES");
                else if (whitePieces.size() < blackPieces.size())
                    System.out.println("NO");
                else {
                    if(Math.random() % 2 == 0)
                        System.out.println("YES");
                    else
                        System.out.println("NO");
                }
            } else {
                if (isBlackQueenPresent)
                    System.out.println("NO");
                else
                    System.out.println("YES");
            }
        }
    }

    private static void playGame(Box[][] chessBoard, int noOfMoves) {
        for (int i = 0; i < 2 && isWhiteQueenPresent && isBlackQueenPresent; i++) {
            movePiece(chessBoard);
            turn = (turn == Color.White) ? Color.Black : Color.White;
        }
    }

    private static void movePiece(Box[][] chessBoard) {
        if (turn == Color.White) {
            for (Piece piece : whitePieces) {
                boolean canCaptureQueen = piece.canCaptureQueen(blackQueen);
                if (canCaptureQueen) {
                    isBlackQueenPresent = false;
                    return;
                }
            }
        } else {
            for (Piece piece : blackPieces) {
                boolean canCaptureQueen = piece.canCaptureQueen(whiteQueen);
                if (canCaptureQueen) {
                    isWhiteQueenPresent = false;
                    return;
                }
            }
        }

    }

    private static void canCaptureQueen(Piece opponentQueen, Piece piece) {
        piece.getPositions();

    }

    private static void displayChessBoard(Box[][] chessBoard) {
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
                String columnDelimiter = "|";
                if (chessBoard[j][k].isEmpty())
                    System.out.print("  " + columnDelimiter);
                else {
                    if (chessBoard[j][k].piece.color == Color.White) {
                        System.out.print("W" + chessBoard[j][k].piece.type.getValue() + columnDelimiter);
                    } else {
                        System.out.print("B" + chessBoard[j][k].piece.type.getValue() + columnDelimiter);
                    }
                }
            }
            System.out.println("");
        }
    }

    private static Piece insertPiece(String[] move, Box[][] chessBoard, Color color) {
        Type type = Type.getPiece(move[0].charAt(0));
        int row = 4 - Integer.parseInt(move[2]);
        int column;
        if (move[1].equals("A")) {
            column = 0;
        } else if (move[1].equals("B")) {
            column = 1;
        } else if (move[1].equals("C")) {
            column = 2;
        } else {
            column = 3;
        }
        Position position = new Position(row, column);
        Piece piece = new Piece();
        piece.color = color;
        piece.position = position;
        piece.type = type;

        if (type == Type.Queen && color == Color.White) {
            whiteQueen = piece;
            isWhiteQueenPresent = true;
        } else if (type == Type.Queen && color == Color.Black) {
            blackQueen = piece;
            isBlackQueenPresent = true;
        }

        chessBoard[row][column].piece = piece;
        return piece;
    }

}
