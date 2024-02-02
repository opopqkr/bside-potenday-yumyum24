package best.bside.potenday.yumyum24.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "RECOMMEND_MENT")
public class RecommendMent {

    @Id
    @GeneratedValue
    private Long recommendId;

    private LocalTime startTime;

    private LocalTime endTime;

    private String ment;

}
