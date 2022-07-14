package yanghgri.whileyouplay.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import yanghgri.common.entity.BaseDO;
import yanghgri.whileyouplay.enums.GameTag;
import yanghgri.whileyouplay.enums.Review;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GameDO extends BaseDO<Long> {
    private String name;
    private Integer price;
    private List<GameTag> tags;
    private Long developerId;
    private Long publisherId;
    private Review allReviews;
    private Review recentReviews;
    private LocalDate releaseDate;
}