package file_managment;

public class AllPointsColinearException extends FileReadingException {
    
    public AllPointsColinearException(){
        super("Wszystkie szpitale i obiekty są współliniowe!");
    }
 
}
