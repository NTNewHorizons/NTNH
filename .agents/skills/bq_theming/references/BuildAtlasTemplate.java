package references;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Базовый шаблон процедурного генератора графического атласа 256x256
 * для тем Better Questing (Minecraft 1.7.10).
 * 
 * Преимущества перед растровыми редакторами:
 * - 100% точность пиксельной сетки без риска случайных сдвигов на 1px
 * - Быстрая смена палитры всего интерфейса через константы Color
 * - Автоматический экспорт сразу во все директории и инстанции
 */
public class BuildAtlasTemplate {
    // -------------------------------------------------------------
    // Базовая палитра (настраивается под дизайн конкретной темы)
    // -------------------------------------------------------------
    static final Color COLOR_BLACK        = new Color(0x00, 0x00, 0x00, 0xFF);
    static final Color COLOR_BASE_CHASSIS = new Color(0x13, 0x16, 0x14, 0xFF);
    static final Color COLOR_FRAME_METAL  = new Color(0x24, 0x2E, 0x27, 0xFF);
    static final Color COLOR_CARD_FILL    = new Color(0x0E, 0x11, 0x0F, 0xFF);
    static final Color COLOR_PHOSPHOR     = new Color(0x2F, 0xFF, 0x21, 0xFF);

    public static void main(String[] args) throws Exception {
        int width = 256;
        int height = 256;
        BufferedImage atlas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 1. Основное окно (48x48 при bounds [0, 0, 48, 48], padding 16)
        drawChassisPanel(atlas, 0, 0, 48, 48);

        // 2. Карточка квеста с монтажными скобами [128, 128, 64, 64]
        drawInnerCard(atlas, 128, 128, 64, 64, new Color(0x1E, 0x5E, 0x2E));

        // 3. Утопленный сокет наградного слота 18x18 [0, 48, 18, 18]
        drawItemSocket(atlas, 0, 48, 18, 18);

        // 4. Системные иконки чекбоксов 16x16
        drawCheckmark(atlas, 208, 64);
        drawCross(atlas, 224, 64);

        // Сохранение готового атласа
        File outFile = new File("textures/gui/atlas.png");
        outFile.getParentFile().mkdirs();
        ImageIO.write(atlas, "png", outFile);
        System.out.println("Atlas generated: " + outFile.getAbsolutePath());
    }

    /** Отрисовка базового окна с металлической рамкой */
    public static void drawChassisPanel(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        // Заливка фона
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, COLOR_BASE_CHASSIS.getRGB());
        }
        // Черный контур
        for (int x = x0; x <= x1; x++) { img.setRGB(x, y0, COLOR_BLACK.getRGB()); img.setRGB(x, y1, COLOR_BLACK.getRGB()); }
        for (int y = y0; y <= y1; y++) { img.setRGB(x0, y, COLOR_BLACK.getRGB()); img.setRGB(x1, y, COLOR_BLACK.getRGB()); }
        // Металлическая рамка
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0 + 1, COLOR_FRAME_METAL.getRGB()); img.setRGB(x, y1 - 1, COLOR_FRAME_METAL.getRGB()); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0 + 1, y, COLOR_FRAME_METAL.getRGB()); img.setRGB(x1 - 1, y, COLOR_FRAME_METAL.getRGB()); }
    }

    /** Отрисовка карточки с монтажными скобами внутри углов 8x8 */
    public static void drawInnerCard(BufferedImage img, int x0, int y0, int w, int h, Color bracketColor) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, COLOR_CARD_FILL.getRGB());
        }
        // Черный контур и металлическая фаска
        for (int x = x0; x <= x1; x++) { img.setRGB(x, y0, COLOR_BLACK.getRGB()); img.setRGB(x, y1, COLOR_BLACK.getRGB()); }
        for (int y = y0; y <= y1; y++) { img.setRGB(x0, y, COLOR_BLACK.getRGB()); img.setRGB(x1, y, COLOR_BLACK.getRGB()); }
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0 + 1, COLOR_FRAME_METAL.getRGB()); img.setRGB(x, y1 - 1, COLOR_FRAME_METAL.getRGB()); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0 + 1, y, COLOR_FRAME_METAL.getRGB()); img.setRGB(x1 - 1, y, COLOR_FRAME_METAL.getRGB()); }

        // Угловые скобы 4px (строго x ∈ [3..6], y ∈ [3..6] внутри 8x8 углов)
        int cBracket = bracketColor.getRGB();
        int len = 4;
        for (int i = 0; i < len; i++) {
            img.setRGB(x0 + 3 + i, y0 + 3, cBracket); img.setRGB(x0 + 3, y0 + 3 + i, cBracket); // Top-Left
            img.setRGB(x1 - 3 - i, y0 + 3, cBracket); img.setRGB(x1 - 3, y0 + 3 + i, cBracket); // Top-Right
            img.setRGB(x0 + 3 + i, y1 - 3, cBracket); img.setRGB(x0 + 3, y1 - 3 - i, cBracket); // Bottom-Left
            img.setRGB(x1 - 3 - i, y1 - 3, cBracket); img.setRGB(x1 - 3, y1 - 3 - i, cBracket); // Bottom-Right
        }
    }

    /** Отрисовка утопленного сокета слота предмета 18x18 */
    public static void drawItemSocket(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        int cWell    = new Color(0x0A, 0x10, 0x0C).getRGB();
        int cBorder  = new Color(0x14, 0x1A, 0x16).getRGB();
        int cShadow  = new Color(0x04, 0x06, 0x05).getRGB();
        int cBevelHi = new Color(0x26, 0x36, 0x2C).getRGB();
        int cPin     = new Color(0x1E, 0x5E, 0x2E).getRGB();

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, cWell);
        }
        for (int x = x0; x <= x1; x++) { img.setRGB(x, y0, cBorder); img.setRGB(x, y1, cBorder); }
        for (int y = y0; y <= y1; y++) { img.setRGB(x0, y, cBorder); img.setRGB(x1, y, cBorder); }
        // Тень заглубления (верх и лево)
        for (int x = x0 + 1; x <= x1 - 1; x++) img.setRGB(x, y0 + 1, cShadow);
        for (int y = y0 + 1; y <= y1 - 1; y++) img.setRGB(x0 + 1, y, cShadow);
        // Световой скос фаски (низ и право)
        for (int x = x0 + 1; x <= x1 - 1; x++) img.setRGB(x, y1 - 1, cBevelHi);
        for (int y = y0 + 1; y <= y1 - 1; y++) img.setRGB(x1 - 1, y, cBevelHi);
        // Угловые аппаратные пины
        img.setRGB(x0 + 2, y0 + 2, cPin); img.setRGB(x1 - 2, y0 + 2, cPin);
        img.setRGB(x0 + 2, y1 - 2, cPin); img.setRGB(x1 - 2, y1 - 2, cPin);
    }

    /** Белая галочка для цветового умножения Better Questing (16x16) */
    public static void drawCheckmark(BufferedImage img, int x0, int y0) {
        int shadow = new Color(0, 0, 0, 128).getRGB();
        int white  = Color.WHITE.getRGB();
        int[][] pts = {
            {3, 8}, {4, 8}, {4, 9}, {5, 9}, {5, 10}, {6, 10}, {6, 11}, {7, 11},
            {7, 10}, {8, 10}, {8, 9}, {9, 9}, {9, 8}, {10, 8}, {10, 7}, {11, 7},
            {11, 6}, {12, 6}, {12, 5}, {13, 5}
        };
        for (int[] p : pts) img.setRGB(x0 + p[0] + 1, y0 + p[1] + 1, shadow);
        for (int[] p : pts) img.setRGB(x0 + p[0], y0 + p[1], white);
    }

    /** Белый крестик для цветового умножения Better Questing (16x16) */
    public static void drawCross(BufferedImage img, int x0, int y0) {
        int shadow = new Color(0, 0, 0, 128).getRGB();
        int white  = Color.WHITE.getRGB();
        int[][] pts = {
            {3, 3}, {4, 3}, {11, 3}, {12, 3},
            {4, 4}, {5, 4}, {10, 4}, {11, 4},
            {5, 5}, {6, 5}, {9, 5}, {10, 5},
            {6, 6}, {7, 6}, {8, 6}, {9, 6},
            {7, 7}, {8, 7}, {7, 8}, {8, 8},
            {6, 9}, {7, 9}, {8, 9}, {9, 9},
            {5, 10}, {6, 10}, {9, 10}, {10, 10},
            {4, 11}, {5, 11}, {10, 11}, {11, 11},
            {3, 12}, {4, 12}, {11, 12}, {12, 12}
        };
        for (int[] p : pts) img.setRGB(x0 + p[0] + 1, y0 + p[1] + 1, shadow);
        for (int[] p : pts) img.setRGB(x0 + p[0], y0 + p[1], white);
    }
}
