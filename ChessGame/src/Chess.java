/* 
 * This class is the main class.
 * The program implements at here.
 */

import java.util.Scanner;
import java.io.IOException;

public class Chess {
	
	private GameBoard board;
    private int rank1,file1,rank2,file2;
    private int special;
    private char promote;
    private boolean isDraw;
    private Scanner sc;
    private boolean gameOver;
    private String player;
    private int count=0;
    
	
    public Chess(){
        this.board = new GameBoard();
        sc = new Scanner(System.in);
        init();
    }
    
    /**
    *
    */
   private void init(){
       board.initialize();
       this.player= "white";
       this.isDraw = false;
       this.gameOver = false;
   }
   
   private void playOneMove(){
       System.out.println(board);
       special = -1;
       while(true) {
           System.out.println(player + " make a move: ");
           String input = sc.nextLine();
           //String input = inputs[count++];
           if(dealInput(input)){
               if(special != -1){//resign,draw
                   if(special == 1){//resign
                       System.out.println(player + " resigns");
                       System.out.println(playerChange(player) + " wins the game!");
                       gameOver = true;
                       return;
                   }else if(special ==3){//draw?
                       isDraw = true;
                       break;
                   }else if(special == 2){//draw
                       System.out.println("The game is a draw.");
                       gameOver = true;
                       return;
                   }
               }else{//normal move
                   if(isDraw){
                       isDraw = false;
                   }
                   if(board.validInput(player,rank1,file1,rank2,file2)){
                       break;
                   }
               }
           }
           System.out.println("Illegal move, try again");
       }

       board.makeMove(rank1,file1,rank2,file2);

       if(!board.kingAlive()){
           System.out.println(player + " wins");
           gameOver = true;
           return;
       }
       if(board.isUnderCheck(player) || board.isUnderCheck(playerChange(player))){
           System.out.println("Check!");
           if(board.checkMate(player)){
               System.out.println("Checkmate");
               System.out.println(playerChange(player) + " wins");
               gameOver = true;
               return;
           }
           if(board.checkMate(playerChange(player))){
               System.out.println("Checkmate");
               System.out.println(player + " wins");
               gameOver = true;
               return;
           }
       }else if(board.staleMate(playerChange(player))){
           System.out.println("Stalemate!");
           gameOver = true;
           return;
       }
       player = playerChange(player);
   }

   /**
    *
    */
   private void playOneGame(){
       init();
       while(!gameOver){
           playOneMove();
       }
   }

	/**
     * @param args none
     */
    public static void main(String[] args) {

        Chess game = new Chess();
        game.playOneGame();
    }

	/**
    *
    * @param input input from keyboard
    * @return is a valid input or not
    */
   private boolean dealInput(String input){
       if(input==null || input.length()==0)
           return false;
       String[] strs = input.trim().replaceAll(" {2,}", " ").split(" ");
       int len = strs.length;
       if(len == 1){
           if(strs[0].equals("resign")){
               special = 1;
               return true;
           }
           if(strs[0].equals("draw") && isDraw){
               special = 2;
               return true;
           }
       }else if(len==2 || len==3){
           if(strs[0].length()==2 && strs[1].length()==2 &&
              strs[0].charAt(0)<='h' && strs[0].charAt(0)>='a' &&
              strs[1].charAt(0)<='h' && strs[1].charAt(0)>='a' &&
              strs[0].charAt(1)<='8' && strs[0].charAt(1)>='1' &&
              strs[1].charAt(1)<='8' && strs[1].charAt(1)>='1'
           ){
               if(len==2) {
                   toAxis(strs[0], 0);
                   toAxis(strs[1], 1);
                   if(rank1!=rank2 || file1!=file2) {
                       return true;
                   }
               }else{
                   if(strs[2].equals("draw?")){
                       special = 3;
                       toAxis(strs[0], 0);
                       toAxis(strs[1], 1);
                       if(rank1!=rank2 || file1!=file2) {
                           return true;
                       }
                   }else if(strs[2].length()==1){
                       char ch = strs[2].charAt(0);
                       if(ch=='R'||ch=='N'||ch=='B'||ch=='Q'){
                           promote = ch;
                           board.promote = promote;
                           toAxis(strs[0], 0);
                           toAxis(strs[1], 1);
                           if(rank1!=rank2 || file1!=file2) {
                               return true;
                           }
                       }
                   }
               }
           }
       }
       return false;

   }
   
   /**
   *
   * @param player current player
   * @return other player
   */
  private String playerChange(String player){
      if(player.equals("white")){
          return "black";
      }else {
          return "white";
      }
  }
   
   /**
    * transfer input axis value to the corresponding board array
    * range: 0-7
    */
   private void toAxis(String str, int index){
       char ch1 = str.charAt(0);
       char ch2 = str.charAt(1);
       int[] res = new int[2];
       switch(ch1){
           case 'a': res[1] = 0;break;
           case 'b': res[1] = 1;break;
           case 'c': res[1] = 2;break;
           case 'd': res[1] = 3;break;
           case 'e': res[1] = 4;break;
           case 'f': res[1] = 5;break;
           case 'g': res[1] = 6;break;
           case 'h': res[1] = 7;break;
       }
       res[0] = 8- (ch2-'0');
       if(index==0){
           rank1 = res[0];
           file1 = res[1];
       }else{
           rank2 = res[0];
           file2 = res[1];
       }
   }
}
