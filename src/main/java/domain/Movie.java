package domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Movie {

    private String name;
    private ShowTime showtime;
    private int price;
    private int seet;

    @Data
    public static class ShowTime{

        public ShowTime(int startTime,int endTime){
            if(startTime>=endTime){
                throw new RuntimeException("Start time must be less than end time");
            }
            this.startTime = startTime;
            this.endTime = endTime;
        }

        private int startTime;
        private int endTime;
    }
}
