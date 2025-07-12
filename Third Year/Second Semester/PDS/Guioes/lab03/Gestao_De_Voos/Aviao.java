public class Aviao {


  private int[][] seats_T; // turistas
  private int[][] seats_E;// executiva

  public Aviao(int[][] seats_E, int[][] seats_T){ 
    this.seats_E = seats_E;
    this.seats_T = seats_T;
  }

  public int[][] getSeatsT(){
    return seats_T;
  }

  public void setSeats_T(int[][] seats_T) {
    this.seats_T = seats_T;
  }

  public int[][] getSeatsE(){
    return seats_E;
  }

  public void setSeats_E(int[][] seats_E){
    this.seats_E = seats_E;
  }


  // capacidade para as duas classes

  public int getTCap(){
    if(seats_T.length == 0) return 0;
    return seats_T[0].length * seats_T.length;
  }

  public int getECap(){
    if(seats_E.length == 0) return 0;
    return seats_E[0].length * seats_E.length;
  }

  // lugares ocupados para as 2 classes

  public int getOccupiedT(){
    if(seats_T.length == 0) return 0;

    int count = 0;
    for(int i = 0; i< seats_T.length;i++){
      for(int j = 0;j<seats_T[0].length;j++){
        if(seats_T[i][j] != 0){
          count++;
        }
      }
    }

    return count;
  }

  public int getOccupiedE(){

    if(seats_E.length == 0) return 0;

    int count = 0;
    for(int i = 0; i< seats_E.length;i++){
      for(int j = 0;j<seats_E[0].length;j++){
        if(seats_E[i][j] != 0){
          count++;
        }
      }
    }

    return count;
  }






  
}
