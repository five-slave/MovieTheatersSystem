package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Movie {

    private String name;
    private ShowTime showtime;
    private int price;
    private int seet = 10;

    @Data
    @AllArgsConstructor
    public static class ShowTime{
        private int startTime;
        private int endTime;
    }
}
