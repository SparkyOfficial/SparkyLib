package com.sparky.ui;

/**
 * Реалізація менеджера компонування з плаваючим розташуванням.
 *
 * @author Андрій Будильников
 */
public class FlowLayout implements LayoutManager {
    private int hgap = 5;  // Horizontal gap between components
    private int vgap = 5;  // Vertical gap between components
    private boolean horizontal = true; // Horizontal flow by default
    
    public FlowLayout() {}
    
    public FlowLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    public FlowLayout(int hgap, int vgap, boolean horizontal) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.horizontal = horizontal;
    }
    
    @Override
    public void layout(Container container) {
        if (horizontal) {
            layoutHorizontal(container);
        } else {
            layoutVertical(container);
        }
    }
    
    /**
     * Компонує елементи горизонтально.
     */
    private void layoutHorizontal(Container container) {
        int x = container.getX() + hgap;
        int y = container.getY() + vgap;
        int maxHeight = 0;
        
        for (UIElement element : container.getChildren()) {
            if (element.isVisible()) {
                // Check if element fits in current row
                if (x + element.getWidth() > container.getX() + container.getWidth() - hgap) {
                    // Move to next row
                    x = container.getX() + hgap;
                    y += maxHeight + vgap;
                    maxHeight = 0;
                }
                
                // Position element
                element.setPosition(x, y);
                x += element.getWidth() + hgap;
                maxHeight = Math.max(maxHeight, element.getHeight());
            }
        }
    }
    
    /**
     * Компонує елементи вертикально.
     */
    private void layoutVertical(Container container) {
        int x = container.getX() + hgap;
        int y = container.getY() + vgap;
        int maxWidth = 0;
        
        for (UIElement element : container.getChildren()) {
            if (element.isVisible()) {
                // Check if element fits in current column
                if (y + element.getHeight() > container.getY() + container.getHeight() - vgap) {
                    // Move to next column
                    y = container.getY() + vgap;
                    x += maxWidth + hgap;
                    maxWidth = 0;
                }
                
                // Position element
                element.setPosition(x, y);
                y += element.getHeight() + vgap;
                maxWidth = Math.max(maxWidth, element.getWidth());
            }
        }
    }
    
    /**
     * Отримує горизонтальний інтервал.
     */
    public int getHgap() {
        return hgap;
    }
    
    /**
     * Встановлює горизонтальний інтервал.
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }
    
    /**
     * Отримує вертикальний інтервал.
     */
    public int getVgap() {
        return vgap;
    }
    
    /**
     * Встановлює вертикальний інтервал.
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }
    
    /**
     * Перевіряє, чи компонування горизонтальне.
     */
    public boolean isHorizontal() {
        return horizontal;
    }
    
    /**
     * Встановлює орієнтацію компонування.
     */
    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }
}