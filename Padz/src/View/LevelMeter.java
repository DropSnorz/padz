package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

import resources.AppResources;

public class LevelMeter extends JComponent {
    private int meterWidth = 10;

    private float amp = 0f;
    private float peak = 0f;

    public void setAmplitude(float amp) {
        this.amp = Math.abs(amp);
        repaint();
    }

    public void setPeak(float peak) {
        this.peak = Math.abs(peak);
        repaint();
    }

    public void setMeterWidth(int meterWidth) {
        this.meterWidth = meterWidth;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int w = Math.min(meterWidth, getWidth());
        int h = getHeight();
        int x = getWidth() / 2 - w / 2;
        int y = 0;

        g.setColor(AppResources.UI_Background_Color);
        g.fillRect(x, y, w, h);

        g.setColor(AppResources.UI_PlainBorder_Color);
        g.drawRect(x, y, w - 1, h - 1);

        int a = Math.round(amp * (h - 2));
        g.setColor(Color.GREEN);
        g.fillRect(x + 1, y + h - 1 - a, w - 2, a);

        int p = Math.round(peak * (h - 2));
        g.setColor(Color.RED);
        g.drawLine(x + 1, y + h - 1 - p, x + w - 1, y + h - 1 - p);
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension min = super.getMinimumSize();
        if(min.width < meterWidth)
            min.width = meterWidth;
        if(min.height < meterWidth)
            min.height = meterWidth;
        return min;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension pref = super.getPreferredSize();
        pref.width = meterWidth;
        return pref;
    }

    @Override
    public void setPreferredSize(Dimension pref) {
        super.setPreferredSize(pref);
        setMeterWidth(pref.width);
    }
}