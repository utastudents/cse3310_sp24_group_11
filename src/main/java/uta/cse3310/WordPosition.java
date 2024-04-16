package uta.cse3310;

public class WordPosition{
  String word;
  int startRow;
  int startCol;
  int endRow;
  int endCol;
  
  public WordPosition(String word, int startRow, int startCol, int endRow, int endCol) {
      this.word = word;
      this.startRow = startRow;
      this.startCol = startCol;
      this.endRow = endRow;
      this.endCol = endCol;
  }
  
    public String getWord(){
      return word;
    }
    public void getStart(){
      System.out.printf("Start: (%d,%d)",startRow,startCol);

    }
    public void getEnd(){
      System.out.printf("End: (%d,%d)",endRow,endCol);
    }
    
  }