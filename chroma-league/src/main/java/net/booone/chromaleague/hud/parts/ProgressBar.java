package net.booone.chromaleague.hud.parts;

import net.booone.razersdk.animation.SimpleFrame;
import net.booone.razersdk.color.Color;
import net.booone.razersdk.sdk.RzKey;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgressBar extends SimpleFrame {

    public ProgressBar(final List<RzKey> progressBar, final Integer percent, final Color color) {
        super(getBarPercent(progressBar, percent), color);
    }

    public ProgressBar(final LinkedHashMap<RzKey, Color> progressBar, final Integer percent) {
        super(progressBar.entrySet().stream()
                .limit(indexToFill(progressBar.size(), percent))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private static List<RzKey> getBarPercent(final List<RzKey> progressBar, final Integer percent) {
        return progressBar.subList(0, indexToFill(progressBar.size(), percent));
    }

    public static int indexToFill(int progressBarSize, Integer percent) {
        if (percent >= 100) {
            return progressBarSize;
        }
        return Double.valueOf(progressBarSize * (percent * 0.01)).intValue();
    }
}
