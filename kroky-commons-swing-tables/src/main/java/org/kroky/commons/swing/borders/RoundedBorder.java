/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.commons.swing.borders;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

/**
 *
 * @author Krokavec Peter
 */
public class RoundedBorder extends AbstractBorder {

    private Color color = Color.BLACK;
    private final Insets insets;
    private boolean opaque = false;

    public RoundedBorder(Insets insets) {
        this.insets = insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setColor(color);
        g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, insets.left, insets.top));
        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return (getBorderInsets(c, insets));
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.left / 2;
        insets.top = insets.top / 2;
        insets.right = insets.right / 2;
        insets.bottom = insets.bottom / 2;
        return insets;
    }

    @Override
    public boolean isBorderOpaque() {
        return opaque;
    }

    public RoundedBorder setOpaque(boolean opaque) {
        this.opaque = opaque;
        return this;
    }

    public RoundedBorder setColor(Color color) {
        this.color = color;
        return this;
    }

}
