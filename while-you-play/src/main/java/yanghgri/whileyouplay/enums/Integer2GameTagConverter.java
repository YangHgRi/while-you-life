package yanghgri.whileyouplay.enums;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;

public class Integer2GameTagConverter implements Converter<Integer, GameTag> {
    @Override
    public GameTag convert(@NotNull Integer source) {
        GameTag gameTag = GameTag.getInstance(source);
        if (gameTag == null) {
            throw new IllegalArgumentException("无法匹配到GameTag的Code!");
        }
        return gameTag;
    }
}