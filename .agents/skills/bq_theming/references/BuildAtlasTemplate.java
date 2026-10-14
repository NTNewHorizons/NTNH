package references;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Базовый шаблон процедурного генератора графического атласа 256x256
 * для тем Better Questing (Minecraft 1.7.10).
 * 
 * Включает:
 * - Окна и карточки с угловыми монтажными скобами
 * - Кнопки с каноническими 2px фасками Minecraft GUI (Top-Left Highlight / Bottom-Right Shadow)
 * - Процедурные сокеты квестов (обычный 24x24 и 8-гранный главный) с изоляцией колодца
 * - Дискретные светодиодные шкалы прогресса (LED bar graph)
 * - Системные иконки чекбоксов (белый цвет + полупрозрачная тень под OpenGL tinting)
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

    // Кнопки (2px канонические фаски Minecraft GUI)
    static final Color COLOR_BTN_DIS_FILL  = new Color(0x0E, 0x14, 0x10, 0xFF);
    static final Color COLOR_BTN_DIS_HI    = new Color(0x24, 0x38, 0x2B, 0xFF);
    static final Color COLOR_BTN_DIS_SH    = new Color(0x06, 0x0A, 0x08, 0xFF);

    static final Color COLOR_BTN_IDLE_FILL = new Color(0x14, 0x3C, 0x22, 0xFF); // Vibrant terminal emerald
    static final Color COLOR_BTN_IDLE_HI   = new Color(0x38, 0x8A, 0x52, 0xFF);
    static final Color COLOR_BTN_IDLE_SH   = new Color(0x0A, 0x1E, 0x11, 0xFF);

    static final Color COLOR_BTN_HOV_FILL  = new Color(0x1A, 0x54, 0x28, 0xFF); // Canonical Minecraft 3D with phosphor glow
    static final Color COLOR_BTN_HOV_HI    = new Color(0x55, 0xFF, 0x48, 0xFF);
    static final Color COLOR_BTN_HOV_SH    = new Color(0x12, 0x3E, 0x1D, 0xFF);

    // Сокеты предметов (item_frame 18x18 - калиброванный терминальный графит для читаемости темных предметов)
    static final Color COLOR_SOCKET_WELL     = new Color(0x38, 0x46, 0x3D, 0xFF);
    static final Color COLOR_SOCKET_BORDER   = new Color(0x14, 0x1A, 0x16, 0xFF);
    static final Color COLOR_SOCKET_SHADOW   = new Color(0x1C, 0x24, 0x1F, 0xFF);
    static final Color COLOR_SOCKET_BEVEL_HI = new Color(0x4E, 0x60, 0x54, 0xFF);
    static final Color COLOR_SOCKET_PIN      = new Color(0x1E, 0x5E, 0x2E, 0xFF);

    public static void main(String[] args) throws Exception {
        int width = 256;
        int height = 256;
        BufferedImage atlas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // 1. Основное окно (bounds [0, 0, 48, 48], padding [16, 16, 16, 16], sliceMode 1)
        drawChassisPanel(atlas, 0, 0, 48, 48);

        // 2. Кнопки с 2px Minecraft фасками (bounds [48, 0..40, 200, 20], padding [3, 3, 3, 3], sliceMode 2 - ОБЯЗАТЕЛЬНО Stretched для устранения швов!)
        drawPixelButton(atlas, 48, 0,  200, 20, COLOR_BTN_DIS_FILL,  COLOR_BTN_DIS_HI,  COLOR_BTN_DIS_SH);  // btn_normal_0 (Disabled)
        drawPixelButton(atlas, 48, 20, 200, 20, COLOR_BTN_IDLE_FILL, COLOR_BTN_IDLE_HI, COLOR_BTN_IDLE_SH); // btn_normal_1 (Idle)
        drawPixelButton(atlas, 48, 40, 200, 20, COLOR_BTN_HOV_FILL,  COLOR_BTN_HOV_HI,  COLOR_BTN_HOV_SH);  // btn_normal_2 (Hover)

        // 3. Утопленный сокет наградного слота 18x18 (bounds [0, 48, 18, 18], padding [2, 2, 2, 2], sliceMode 2)
        drawItemSocket(atlas, 0, 48, 18, 18);

        // 4. Шкалы прогресса (LED bar graph)
        drawMeterTrack(atlas, 48, 84, 24, 12);   // meter_h_0: groove track (padding [3, 3, 3, 3], sliceMode 2)
        drawMeterFillLED(atlas, 76, 84, 16, 12); // meter_h_1: discrete LEDs (padding [0, 0, 0, 0], sliceMode 1)

        // 5. Процедурные сокеты узлов квестов 24x24 (padding [8, 8, 8, 8], sliceMode 1)
        drawQuestNormSocket(atlas, 0, 104, 24, 24);  // quest_norm_*: аппаратный микрочип-сокет с латунными заклёпками
        drawQuestMainSocket(atlas, 24, 104, 24, 24); // quest_main_*: 8-гранный бронированный сокет майлстоуна

        // 6. Карточка квеста с монтажными скобами (bounds [128, 128, 64, 64], padding [8, 8, 8, 8], sliceMode 1)
        drawInnerCard(atlas, 128, 128, 64, 64, new Color(0x1E, 0x5E, 0x2E));

        // 7. Системные иконки чекбоксов 16x16 (texture_simple)
        drawCheckmark(atlas, 208, 64); // icon_tick
        drawCross(atlas, 224, 64);     // icon_cross

        // 8. Скроллбары (нативные 8x16 вертикальный, padding [2, 3, 2, 3], sliceMode 2)
        drawScrollTrackV(atlas, 248, 0, 8, 60);
        drawScrollThumbV(atlas, 248, 60, 8, 16, COLOR_BTN_DIS_FILL, COLOR_BLACK, COLOR_BTN_DIS_HI, COLOR_BTN_DIS_SH, COLOR_BTN_DIS_HI, COLOR_BLACK);
        drawScrollThumbV(atlas, 248, 76, 8, 16, COLOR_BASE_CHASSIS, COLOR_BLACK, COLOR_FRAME_METAL, COLOR_BLACK, COLOR_PHOSPHOR, COLOR_BLACK);
        drawScrollThumbV(atlas, 248, 92, 8, 16, COLOR_BTN_HOV_FILL, COLOR_BLACK, COLOR_BTN_HOV_HI, COLOR_BTN_HOV_SH, COLOR_BTN_HOV_HI, COLOR_BLACK);

        // Сохранение готового атласа
        File outFile = new File("textures/gui/atlas.png");
        if (outFile.getParentFile() != null) outFile.getParentFile().mkdirs();
        ImageIO.write(atlas, "png", outFile);
        System.out.println("Atlas generated: " + outFile.getAbsolutePath());
    }

    /** Отрисовка базового окна с металлической рамкой */
    public static void drawChassisPanel(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, COLOR_BASE_CHASSIS.getRGB());
        }
        for (int x = x0; x <= x1; x++) { img.setRGB(x, y0, COLOR_BLACK.getRGB()); img.setRGB(x, y1, COLOR_BLACK.getRGB()); }
        for (int y = y0; y <= y1; y++) { img.setRGB(x0, y, COLOR_BLACK.getRGB()); img.setRGB(x1, y, COLOR_BLACK.getRGB()); }
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0 + 1, COLOR_FRAME_METAL.getRGB()); img.setRGB(x, y1 - 1, COLOR_FRAME_METAL.getRGB()); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0 + 1, y, COLOR_FRAME_METAL.getRGB()); img.setRGB(x1 - 1, y, COLOR_FRAME_METAL.getRGB()); }
    }

    /**
     * Отрисовка кнопки в стиле Minecraft GUI:
     * - 1px внешний чёрный контур со срезанными на 1px углами
     * - 2px направленная световая фаска (Top-Left Highlight / Bottom-Right Shadow)
     * - Срез 9-slice: padding [3, 3, 3, 3]
     */
    public static void drawPixelButton(BufferedImage img, int x0, int y0, int w, int h, Color fill, Color highlight, Color shadow) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        int cFill  = fill.getRGB();
        int cHi    = highlight.getRGB();
        int cSh    = shadow.getRGB();
        int cOuter = COLOR_BLACK.getRGB();

        // 1. Внутренняя заливка
        for (int y = y0 + 1; y <= y1 - 1; y++) {
            for (int x = x0 + 1; x <= x1 - 1; x++) img.setRGB(x, y, cFill);
        }

        // 2. Внешний 1px чёрный контур со срезанными на 1px углами
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0, cOuter); img.setRGB(x, y1, cOuter); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0, y, cOuter); img.setRGB(x1, y, cOuter); }

        // 3. Top-Left Highlight (2px в отступе 1..2)
        for (int x = x0 + 1; x <= x1 - 2; x++) { img.setRGB(x, y0 + 1, cHi); img.setRGB(x, y0 + 2, cHi); }
        for (int y = y0 + 1; y <= y1 - 2; y++) { img.setRGB(x0 + 1, y, cHi); img.setRGB(x0 + 2, y, cHi); }

        // 4. Bottom-Right Shadow (2px в отступе 1..2)
        for (int x = x0 + 2; x <= x1 - 1; x++) { img.setRGB(x, y1 - 1, cSh); img.setRGB(x, y1 - 2, cSh); }
        for (int y = y0 + 2; y <= y1 - 1; y++) { img.setRGB(x1 - 1, y, cSh); img.setRGB(x1 - 2, y, cSh); }
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
        int cWell    = COLOR_SOCKET_WELL.getRGB();
        int cBorder  = COLOR_SOCKET_BORDER.getRGB();
        int cShadow  = COLOR_SOCKET_SHADOW.getRGB();
        int cBevelHi = COLOR_SOCKET_BEVEL_HI.getRGB();
        int cPin     = COLOR_SOCKET_PIN.getRGB();

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

    /**
     * Обычный узел квеста 24x24 (микрочип-сокет):
     * - Тёмный колодец (изоляция от цветового умножения OpenGL)
     * - Серая градиентная рамка для чистого тонирования статуса
     * - 4 латунные угловые заклёпки
     */
    public static void drawQuestNormSocket(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        int cOuter   = COLOR_BLACK.getRGB();
        int cRimHi1  = new Color(0xFF, 0xFF, 0xFF, 0xFF).getRGB();
        int cRimSh1  = new Color(0x75, 0x75, 0x75, 0xFF).getRGB();
        int cRimHi2  = new Color(0xD8, 0xD8, 0xD8, 0xFF).getRGB();
        int cRimSh2  = new Color(0x50, 0x50, 0x50, 0xFF).getRGB();
        int cWell    = new Color(0x34, 0x40, 0x38, 0xFF).getRGB();
        int cWellSh  = new Color(0x18, 0x20, 0x1B, 0xFF).getRGB();
        int cWellHi  = new Color(0x48, 0x5A, 0x4E, 0xFF).getRGB();
        int cRivetHi = new Color(0xEB, 0xC4, 0x75, 0xFF).getRGB();
        int cRivetSh = new Color(0x6E, 0x50, 0x20, 0xFF).getRGB();

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, cWell);
        }
        img.setRGB(x0, y0, 0); img.setRGB(x1, y0, 0);
        img.setRGB(x0, y1, 0); img.setRGB(x1, y1, 0);
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0, cOuter); img.setRGB(x, y1, cOuter); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0, y, cOuter); img.setRGB(x1, y, cOuter); }

        for (int x = x0 + 1; x <= x1 - 2; x++) img.setRGB(x, y0 + 1, cRimHi1);
        for (int y = y0 + 1; y <= y1 - 2; y++) img.setRGB(x0 + 1, y, cRimHi1);
        for (int x = x0 + 2; x <= x1 - 1; x++) img.setRGB(x, y1 - 1, cRimSh1);
        for (int y = y0 + 2; y <= y1 - 1; y++) img.setRGB(x1 - 1, y, cRimSh1);

        for (int x = x0 + 2; x <= x1 - 3; x++) img.setRGB(x, y0 + 2, cRimHi2);
        for (int y = y0 + 2; y <= y1 - 3; y++) img.setRGB(x0 + 2, y, cRimHi2);
        for (int x = x0 + 3; x <= x1 - 2; x++) img.setRGB(x, y1 - 2, cRimSh2);
        for (int y = y0 + 3; y <= y1 - 2; y++) img.setRGB(x1 - 2, y, cRimSh2);

        for (int x = x0 + 3; x <= x1 - 3; x++) { img.setRGB(x, y0 + 3, cWellSh); img.setRGB(x, y1 - 3, cWellHi); }
        for (int y = y0 + 3; y <= y1 - 3; y++) { img.setRGB(x0 + 3, y, cWellSh); img.setRGB(x1 - 3, y, cWellHi); }

        img.setRGB(x0 + 2, y0 + 2, cRivetHi); img.setRGB(x1 - 2, y0 + 2, cRivetHi);
        img.setRGB(x0 + 2, y1 - 2, cRivetSh); img.setRGB(x1 - 2, y1 - 2, cRivetSh);
    }

    /**
     * Главный узел квеста 24x24 (8-гранный бронированный сокет):
     * - Фаски 45 градусов по углам
     * - Серая двухуровневая фасетная фаска
     * - Изолированный колодец
     */
    public static void drawQuestMainSocket(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        int cOuter      = COLOR_BLACK.getRGB();
        int cRimHi1     = new Color(0xFF, 0xFF, 0xFF, 0xFF).getRGB();
        int cRimSh1     = new Color(0x70, 0x70, 0x70, 0xFF).getRGB();
        int cRimHi2     = new Color(0xD0, 0xD0, 0xD0, 0xFF).getRGB();
        int cRimSh2     = new Color(0x48, 0x48, 0x48, 0xFF).getRGB();
        int cWell       = new Color(0x30, 0x3C, 0x34, 0xFF).getRGB();
        int cWellSh     = new Color(0x16, 0x1E, 0x19, 0xFF).getRGB();
        int cWellHi     = new Color(0x44, 0x54, 0x48, 0xFF).getRGB();
        int cCornerTick = new Color(0xFF, 0xFF, 0xFF, 0xFF).getRGB();

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int dTL = x + y;
                int dTR = (w - 1 - x) + y;
                int dBL = x + (h - 1 - y);
                int dBR = (w - 1 - x) + (h - 1 - y);
                int edgeDist = Math.min(Math.min(x, w - 1 - x), Math.min(y, h - 1 - y));
                int diagDist = Math.min(Math.min(dTL - 4, dTR - 4), Math.min(dBL - 4, dBR - 4));
                int layer = Math.min(edgeDist, diagDist);

                if (layer < 0) {
                    img.setRGB(x0 + x, y0 + y, 0);
                } else if (layer == 0) {
                    img.setRGB(x0 + x, y0 + y, cOuter);
                } else if (layer == 1) {
                    boolean isTL = (x + y <= w - 1);
                    img.setRGB(x0 + x, y0 + y, isTL ? cRimHi1 : cRimSh1);
                } else if (layer == 2) {
                    boolean isTL = (x + y <= w - 1);
                    img.setRGB(x0 + x, y0 + y, isTL ? cRimHi2 : cRimSh2);
                } else if (layer == 3) {
                    boolean isTL = (x + y <= w - 1);
                    img.setRGB(x0 + x, y0 + y, isTL ? cWellSh : cWellHi);
                } else {
                    img.setRGB(x0 + x, y0 + y, cWell);
                }
            }
        }
        img.setRGB(x0 + 3, y0 + 3, cCornerTick); img.setRGB(x1 - 3, y0 + 3, cCornerTick);
        img.setRGB(x0 + 3, y1 - 3, cCornerTick); img.setRGB(x1 - 3, y1 - 3, cCornerTick);
    }

    /** Отрисовка направляющей канавки шкалы прогресса (meter_h_0, padding [3, 3, 3, 3], sliceMode 2) */
    public static void drawMeterTrack(BufferedImage img, int x0, int y0, int w, int h) {
        int x1 = x0 + w - 1, y1 = y0 + h - 1;
        int cWell    = new Color(0x06, 0x09, 0x07).getRGB();
        int cOuter   = COLOR_BLACK.getRGB();
        int cBorder  = new Color(0x18, 0x22, 0x1A).getRGB();
        int cShadow  = new Color(0x03, 0x05, 0x04).getRGB();
        int cBevelHi = new Color(0x22, 0x30, 0x26).getRGB();

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) img.setRGB(x, y, cWell);
        }
        for (int x = x0; x <= x1; x++) { img.setRGB(x, y0, cOuter); img.setRGB(x, y1, cOuter); }
        for (int y = y0; y <= y1; y++) { img.setRGB(x0, y, cOuter); img.setRGB(x1, y, cOuter); }
        for (int x = x0 + 1; x <= x1 - 1; x++) { img.setRGB(x, y0 + 1, cBorder); img.setRGB(x, y1 - 1, cBorder); }
        for (int y = y0 + 1; y <= y1 - 1; y++) { img.setRGB(x0 + 1, y, cBorder); img.setRGB(x1 - 1, y, cBorder); }

        for (int x = x0 + 2; x <= x1 - 2; x++) img.setRGB(x, y0 + 2, cShadow);
        for (int y = y0 + 2; y <= y1 - 2; y++) img.setRGB(x0 + 2, y, cShadow);
        for (int x = x0 + 2; x <= x1 - 2; x++) img.setRGB(x, y1 - 2, cBevelHi);
        for (int y = y0 + 2; y <= y1 - 2; y++) img.setRGB(x1 - 2, y, cBevelHi);
    }

    /** Отрисовка дискретных светодиодных сегментов (meter_h_1, padding [0, 0, 0, 0], sliceMode 1 Tiled) */
    public static void drawMeterFillLED(BufferedImage img, int x0, int y0, int w, int h) {
        int cLED   = new Color(0x2F, 0xFF, 0x21).getRGB();
        int cLEDHi = new Color(0xBB, 0xFF, 0xB5).getRGB();
        int cLEDSh = new Color(0x16, 0x58, 0x24).getRGB();
        int cGap   = COLOR_BLACK.getRGB();

        // 16x12: 4 сегмента по 4px (3px светодиод + 1px разделительный чёрный зазор)
        for (int x = 0; x < w; x++) {
            boolean isGap = (x % 4 == 3);
            for (int y = y0; y < y0 + h; y++) {
                if (isGap) {
                    img.setRGB(x0 + x, y, cGap);
                } else {
                    int relY = y - y0;
                    if (relY <= 1) img.setRGB(x0 + x, y, cLEDHi);
                    else if (relY >= h - 2) img.setRGB(x0 + x, y, cLEDSh);
                    else img.setRGB(x0 + x, y, cLED);
                }
            }
        }
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

    /** Вертикальный направляющий трек скроллбара */
    public static void drawScrollTrackV(BufferedImage img, int x0, int y0, int w, int h) {
        int cBorder = new Color(0x18, 0x1E, 0x1A).getRGB();
        int cSlot   = new Color(0x09, 0x0B, 0x0A).getRGB();
        int cCap    = new Color(0x22, 0x2A, 0x24).getRGB();

        for (int y = y0; y < y0 + h; y++) {
            for (int x = x0 + 2; x <= x0 + 5; x++) img.setRGB(x, y, cSlot);
            img.setRGB(x0 + 1, y, cBorder);
            img.setRGB(x0 + 6, y, cBorder);
        }
        for (int x = x0 + 1; x <= x0 + 6; x++) {
            img.setRGB(x, y0, cCap);
            img.setRGB(x, y0 + h - 1, cCap);
        }
    }

    /** Нативный вертикальный ползунок скроллбара 8x16 с фосфорными метками */
    public static void drawScrollThumbV(BufferedImage img, int x0, int y0, int w, int h, Color fill, Color outer, Color hi, Color sh, Color ridge, Color groove) {
        int cFill   = fill.getRGB();
        int cOuter  = outer.getRGB();
        int cHi     = hi.getRGB();
        int cSh     = sh.getRGB();
        int cRidge  = ridge.getRGB();
        int cGroove = groove.getRGB();

        for (int y = y0; y < y0 + h; y++) {
            for (int x = x0; x < x0 + w; x++) img.setRGB(x, y, cFill);
        }
        img.setRGB(x0, y0, 0); img.setRGB(x0 + w - 1, y0, 0);
        img.setRGB(x0, y0 + h - 1, 0); img.setRGB(x0 + w - 1, y0 + h - 1, 0);

        for (int x = x0 + 1; x < x0 + w - 1; x++) {
            img.setRGB(x, y0, cOuter);
            img.setRGB(x, y0 + h - 1, cOuter);
        }
        for (int y = y0 + 1; y < y0 + h - 1; y++) {
            img.setRGB(x0, y, cOuter);
            img.setRGB(x0 + w - 1, y, cOuter);
        }

        for (int x = x0 + 1; x < x0 + w - 2; x++) img.setRGB(x, y0 + 1, cHi);
        for (int y = y0 + 1; y < y0 + h - 2; y++) img.setRGB(x0 + 1, y, cHi);

        for (int x = x0 + 2; x < x0 + w - 1; x++) img.setRGB(x, y0 + h - 2, cSh);
        for (int y = y0 + 2; y < y0 + h - 1; y++) img.setRGB(x0 + w - 2, y, cSh);

        for (int x = x0 + 3; x <= x0 + 4; x++) {
            img.setRGB(x, y0 + 6, cGroove);
            img.setRGB(x, y0 + 7, cRidge);
            img.setRGB(x, y0 + 8, cGroove);
            img.setRGB(x, y0 + 9, cRidge);
        }
    }
}
