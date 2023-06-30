package VladMaltsev.weatherapp.util;

public class ChartImageErrorResponse {
    private String message;

    public ChartImageErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
