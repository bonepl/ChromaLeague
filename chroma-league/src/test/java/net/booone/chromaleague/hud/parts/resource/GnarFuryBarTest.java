package net.booone.chromaleague.hud.parts.resource;

import net.booone.chromaleague.hud.AnimationTester;
import org.junit.jupiter.api.Test;

class GnarFuryBarTest extends AbstractResourceTest {
    private static int currentResource;
    private static double currentRange = 400;

    @Test
    void testGnarFuryBar() {
        currentResource = 0;
        final GnarFuryBar gnarFuryBar = new GnarFuryBar();

        new AnimationTester()
                .withBeforeIterationAction(integer -> {
                    if (integer < 120) {
                        if (currentResource < 100) {
                            currentResource++;
                        }
                        currentRange = 400;
                    } else if (integer > 150) {
                        if (currentResource > 0) {
                            currentResource -= 2;
                        }
                        currentRange = 170;
                    }
                    mockResource(currentResource, 100);
                    mockRange(currentRange);
                })
                .testAnimation(gnarFuryBar, 220);
    }


}