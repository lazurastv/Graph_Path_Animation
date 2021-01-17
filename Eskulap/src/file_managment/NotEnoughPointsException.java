package file_managment;

public class NotEnoughPointsException extends FileReadingException {
    
     public NotEnoughPointsException(){
        super("Za mało szpitali i obiektów, aby wyznaczyć granice kraju!");
    }
}
