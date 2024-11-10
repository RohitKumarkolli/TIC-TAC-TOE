import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayDeque;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("TIC-TAC-TOE");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton [][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    ArrayDeque<JButton> deque = new ArrayDeque<>();

    boolean gameOver = false;


    TicTacToe(){
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.red);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("TIC-TAC-TOE");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        for (int r = 0; r < 3; r++){
            for (int c = 0; c < 3; c++){
                JButton tile = new JButton();
                board[r][c] = tile;

                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                //title.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return ;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == ""){
                            tile.setText(currentPlayer);
                            if(currentPlayer == "X"){
                                tile.setForeground(Color.red);
                            }
                            else{
                                tile.setForeground(Color.yellow);
                            }
                            deque.add(tile);        //adding the current player into deque
                            checkWinner();
                            if(!gameOver){
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");    
                            }
                        }
                        
                        // my logic to make infinity game
                        if(deque.size()>=5 && !gameOver){
                            JButton olderMove=deque.removeFirst();
                            olderMove.setText("");
                        }

                    }
                });
                
            }
        }

        // restarting the setup
        JPanel buttonPanel = new JPanel();
        JButton resetButton = new JButton("RESTART");
        buttonPanel.add(resetButton);
        frame.add(buttonPanel,BorderLayout.SOUTH);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int r = 0; r < 3; r++){
                    for (int c =0; c <3; c++){
                        board[r][c].setText("");
                        board[r][c].setBackground(Color.darkGray);
                        board[r][c].setForeground(Color.white);
                    }
                }
                currentPlayer = playerX;
                textLabel.setText("TIC-TAC-TOE");
                deque.clear();
                gameOver= false;
            }
        });

    }
    void checkWinner(){
        //horizontal
        for(int r = 0; r < 3 ; r++){
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText()) {
                for (int i =0; i<3; i++){
                    setWinnter(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }
        //vertical
        for (int c =0; c <3; c++){
            if (board[0][c].getText() == "") continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()){
                for (int i =0; i < 3; i++){
                    setWinnter(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }
        //diagonal
        if(board[0][0].getText()==board[1][1].getText() &&
           board[1][1].getText()==board[2][2].getText() &&
           board[0][0].getText() != ""){
                for(int i = 0; i < 3 ; i++){
                    setWinnter(board[i][i]);
                }
            gameOver = true;
            return;
        }

        //anti diagonally
        if(board[0][2].getText()==board[1][1].getText() &&
           board[1][1].getText()==board[2][0].getText() &&
           board[0][2].getText() != ""){
                setWinnter(board[0][2]);
                setWinnter(board[1][1]);
                setWinnter(board[2][0]);
                gameOver = true;
                return;
           }
    }

    void setWinnter(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    }

}
