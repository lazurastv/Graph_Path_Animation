package file_managment;

public class FileReadingException extends Exception {

    private final int nline;

    public FileReadingException(int nline, String message) {
        super(message);
        this.nline = nline;
    }

    public FileReadingException(String message) {
        super(message);
        nline = -1;
    }

    @Override
    public String getMessage() {
        if (nline != -1) {
            if (super.getMessage().length() < 128) {
                return "Niepoprawne dane  w linii nr " + nline + " : " + super.getMessage();
            } else {
                return "Niepoprawne dane  w linii nr " + nline;
            }
        } else {
            return super.getMessage();
        }
    }
}
