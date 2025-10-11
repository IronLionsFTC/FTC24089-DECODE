package org.firstinspires.ftc.teamcode.cache;

import org.firstinspires.ftc.teamcode.systems.ColourChamber;

public class Ordering {

    public enum AirsortPattern {
        InOrder,
        FirstToSecond,
        SecondToThird,
        FirstToThird,
        ThirdToFirst,
        Impossible
    }

    private ColourChamber.Ball first;
    private ColourChamber.Ball second;
    private ColourChamber.Ball third;

    public Ordering() {
        this.first = ColourChamber.Ball.None;
        this.second = ColourChamber.Ball.None;
        this.third = ColourChamber.Ball.None;
    }

    public void set(ColourChamber.Ball first, ColourChamber.Ball second, ColourChamber.Ball third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public ColourChamber.Ball getFirst() {
        return first;
    }

    public ColourChamber.Ball getSecond() {
        return second;
    }

    public ColourChamber.Ball getThird() {
        return third;
    }

    public AirsortPattern calculateAirSort(Ordering motif) {
        ColourChamber.Ball m1 = motif.getFirst();
        ColourChamber.Ball m2 = motif.getSecond();
        ColourChamber.Ball m3 = motif.getThird();

        ColourChamber.Ball s1 = this.getFirst();
        ColourChamber.Ball s2 = this.getSecond();
        ColourChamber.Ball s3 = this.getThird();

        // Most obvious edgecase
        if (s1 == m1 && s2 == m2 && s3 == m3) {
            return AirsortPattern.InOrder;
        }

        if (m1 == ColourChamber.Ball.Green) {
            // If we need the second ball to land first, arrange for the first ball to land second
            if (s2 == ColourChamber.Ball.Green) return AirsortPattern.FirstToSecond;
            // If the green ball is third and we need it to land first, perform a third to first
            if (s3 == ColourChamber.Ball.Green) return AirsortPattern.ThirdToFirst;
        }

        if (m2 == ColourChamber.Ball.Green) {
            // If the first is green and we need it to land second, arrange for the first ball to land second
            if (s1 == ColourChamber.Ball.Green) return AirsortPattern.FirstToSecond;
            // If the third is green and we need it second, perform a second to third sort
            if (s3 == ColourChamber.Ball.Green) return AirsortPattern.SecondToThird;
        }

        if (m3 == ColourChamber.Ball.Green) {
            // If the third is green and we need it to land first, perform a third to first swap
            if (s1 == ColourChamber.Ball.Green) return AirsortPattern.ThirdToFirst;
            // If the third is green and we need it to land second, arrange for the second ball to land third
            if (s2 == ColourChamber.Ball.Green) return AirsortPattern.SecondToThird;
        }

        // Equivalent to in order, just for debugging / edge cases
        return AirsortPattern.Impossible;
    }
}
