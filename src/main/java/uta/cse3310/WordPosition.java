package uta.cse3310;

public class WordPosition{
  String word;
  int startPosition;
  int endPosition;
  public WordPosition(String word, int startPosition, int endPosition){
    this.word = word;
    this.startPosition = startPosition;
    this.endPosition = endPosition
  }

  public String getWord(){
    return word;
  }
  public int getStart(){
    return startPosition;
  }
  public int getEnd(){
    return endPosition;
  }
  
}
