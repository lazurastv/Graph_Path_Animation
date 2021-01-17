package file_managment;

public class DuplicatedPointException extends FileReadingException {
    
     public DuplicatedPointException(){
        super("Współrzędne obiektów i szpitali nie mogą się powtarzać!");
    }
     
}
