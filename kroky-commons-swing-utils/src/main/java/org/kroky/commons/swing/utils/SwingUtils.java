package org.kroky.commons.swing.utils;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.Window;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Kroky
 */
public class SwingUtils {

    private static final Logger LOG = LogManager.getLogger();

    private static final Map<String, ImageIcon> ICONS = new HashMap<>();
    private static final Map<String, Map<Dimension, ImageIcon>> SCALED_ICONS = new HashMap<>();
    private static final Map<String, Font> FONTS = new HashMap<>();

    /**
     * Absolute path starts with "/"
     *
     * @param resourcePath
     * @return
     */
    public static ImageIcon getIcon(String resourcePath) {
        return ICONS.computeIfAbsent(resourcePath, k -> new ImageIcon(SwingUtils.class.getResource(resourcePath)));
    }

    public static ImageIcon getIcon(String resourcePath, Dimension d) {
        return SCALED_ICONS.computeIfAbsent(resourcePath, k -> new HashMap<>()).computeIfAbsent(d, k -> scaleIcon(getIcon(resourcePath), k));
    }

    private static ImageIcon scaleIcon(ImageIcon icon, Dimension d) {
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(d.width, d.height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        return new ImageIcon(newimg);
    }

    public static Font getFont(String resourcePath, int style, float size) throws FontFormatException, IOException {
        if (!FONTS.containsKey(resourcePath)) {
            Font baseFont = Font.createFont(Font.TRUETYPE_FONT, SwingUtils.class.getResourceAsStream(resourcePath));
            FONTS.computeIfAbsent(resourcePath, k -> baseFont);
        }
        return FONTS.get(resourcePath).deriveFont(style, size);
    }

    public static void centerOnParent(Component child) {
        Component parent = child.getParent();
        //p = parent
        //c = child
        int pX = parent.getLocation().x;
        int pY = parent.getLocation().y;

        Dimension pSize = parent.getSize();
        Dimension cSize = child.getSize();

        int cX = pX + (pSize.width - cSize.width) / 2;
        int cY = pY + (pSize.height - cSize.height) / 2;
        child.setLocation(cX, cY);
    }

    public static void centerOnComponent(Component component, Component baseComponent) {
        //b = base component
        //c = component being centered
        int bX = baseComponent.getLocation().x;
        int bY = baseComponent.getLocation().y;

        Dimension bSize = baseComponent.getSize();
        Dimension cSize = component.getSize();

        int cX = bX + (bSize.width - cSize.width) / 2;
        int cY = bY + (bSize.height - cSize.height) / 2;
        component.setLocation(cX, cY);
    }

    public static void centerOnScreen(Component component) {
        if (component instanceof Window) {
            ((Window) component).setLocationRelativeTo(null);
        } else {
            throw new UnsupportedOperationException("Unable to center " + component + " because it's not an instance of java.awt.Window");
        }
    }
}
