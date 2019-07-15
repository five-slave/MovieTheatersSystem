package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Movie {

    public Movie(String name,int startTime,int endTime, int price,int seet){
        this.name = name;
        this.showtime = new ShowTime(startTime,endTime);
        this.price = price;
        this.seet = seet;
    }

    private String name;
    private ShowTime showtime;
    private Integer price;
    private Integer seet;

    @Data
    @AllArgsConstructor
    public class ShowTime{
        private int startTime;
        private int endTime;
    }
}
