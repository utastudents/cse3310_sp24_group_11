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
  public int getstartRow(){
    return startRow;
  }
  public int getstartCol(){
    return startCol;
  }

  public int getendRow(){
    return endRow;
  }
  public int getendCol(){
    return endCol;
  }
  
}
