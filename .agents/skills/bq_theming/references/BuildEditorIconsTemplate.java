package references;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Procedural Atlas Generator for Better Questing Editor Icons (editor_icons.png).
 *
 * Implements the Safe-Zone Inset System (10px Inner Face Rule):
 * Button height is 16px. Top bevel = rows 0..2 (3px), bottom bevel = rows 13..15 (3px).
 * Flat clickable face = strictly rows y = 3..12 (10px).
 * All functional icon glyphs are sized 8-10px in height and centered within [x=3..12, y=3..12]
 * to prevent overlapping 3D bevels and eliminate the "floating sticker" optical illusion.
 */
public class BuildEditorIconsTemplate {

    // Common palette constants (JAPPA UI 3D)
    private static final int T = 0x00000000;
    private static final int SH = 0x80000000;  // 50% drop shadow
    private static final int DSH = 0xB0000000; // 70% deep shadow

    // Gold
    private static final int G_HI = 0xFFFFF070;
    private static final int G_MID = 0xFFF0B810;
    private static final int G_DARK = 0xFFB87800;
    private static final int G_OUT = 0xFF503000;

    // Iron / Silver
    private static final int I_HI = 0xFFFFFFFF;
    private static final int I_MID = 0xFFD8D8D8;
    private static final int I_DARK = 0xFFA0A0A0;
    private static final int I_DEEP = 0xFF606060;
    private static final int I_OUT = 0xFF303030;

    // Parchment
    private static final int P_HI = 0xFFFBF4E4;
    private static final int P_MID = 0xFFE6D8BA;
    private static final int P_DARK = 0xFFC2B08C;
    private static final int P_OUT = 0xFF5C4E3A;
    private static final int P_TXT = 0xFF382E20;

    // Cyan / Diamond
    private static final int C_HI = 0xFF80FFFF;
    private static final int C_MID = 0xFF28C0E0;
    private static final int C_DARK = 0xFF1478A0;
    private static final int C_OUT = 0xFF083848;

    // Ruby / Crimson
    private static final int R_HI = 0xFFFF7070;
    private static final int R_MID = 0xFFE02020;
    private static final int R_DARK = 0xFF901010;
    private static final int R_OUT = 0xFF4A0808;

    // Wood / Oak / Birch
    private static final int W_HI = 0xFFE6CE9E;
    private static final int W_MID = 0xFFC8A870;
    private static final int W_DARK = 0xFF94723E;
    private static final int W_OUT = 0xFF4A3416;

    public static int[][] makeInfo() {
        int[][] ic = new int[16][16];
        ic[3][6] = G_HI;  ic[3][7] = G_HI;  ic[3][8] = G_MID; ic[3][9] = SH;
        ic[4][6] = G_HI;  ic[4][7] = G_MID; ic[4][8] = G_DARK; ic[4][9] = SH;
        ic[5][6] = G_MID; ic[5][7] = G_MID; ic[5][8] = G_DARK; ic[5][9] = SH;
        ic[6][6] = G_MID; ic[6][7] = G_DARK; ic[6][8] = G_OUT; ic[6][9] = SH;
        ic[7][7] = G_MID; ic[7][8] = G_DARK; ic[7][9] = SH;
        ic[8][7] = G_DARK; ic[8][8] = G_OUT; ic[8][9] = SH;
        ic[11][7] = G_HI;  ic[11][8] = G_MID; ic[11][9] = SH;
        ic[12][7] = G_MID; ic[12][8] = G_DARK; ic[12][9] = SH;
        ic[13][7] = SH;    ic[13][8] = SH;
        return ic;
    }

    public static int[][] makeDoc() {
        int[][] ic = new int[16][16];
        ic[3][4] = P_OUT; ic[3][5] = P_HI;  ic[3][6] = P_HI;  ic[3][7] = P_HI;  ic[3][8] = P_HI;  ic[3][9] = P_OUT;
        ic[4][4] = P_OUT; ic[4][5] = P_HI;  ic[4][6] = P_HI;  ic[4][7] = P_HI;  ic[4][8] = P_DARK; ic[4][9] = P_HI; ic[4][10] = P_OUT;
        ic[5][4] = P_OUT; ic[5][5] = P_HI;  ic[5][6] = P_TXT; ic[5][7] = P_TXT; ic[5][8] = P_MID; ic[5][9] = P_OUT; ic[5][10] = P_OUT;
        ic[6][4] = P_OUT; ic[6][5] = P_HI;  ic[6][6] = P_MID; ic[6][7] = P_MID; ic[6][8] = P_MID; ic[6][9] = P_MID; ic[6][10] = P_OUT;
        ic[7][4] = P_OUT; ic[7][5] = P_HI;  ic[7][6] = P_TXT; ic[7][7] = P_TXT; ic[7][8] = P_TXT; ic[7][9] = P_MID; ic[7][10] = P_OUT;
        ic[8][4] = P_OUT; ic[8][5] = P_HI;  ic[8][6] = P_MID; ic[8][7] = P_MID; ic[8][8] = P_MID; ic[8][9] = P_MID; ic[8][10] = P_OUT;
        ic[9][4] = P_OUT; ic[9][5] = P_HI;  ic[9][6] = P_TXT; ic[9][7] = P_TXT; ic[9][8] = P_MID; ic[9][9] = P_MID; ic[9][10] = P_OUT;
        ic[10][4] = P_OUT; ic[10][5] = P_MID; ic[10][6] = P_MID; ic[10][7] = P_MID; ic[10][8] = P_MID; ic[10][9] = P_DARK; ic[10][10] = P_OUT;
        for (int x = 4; x <= 10; x++) ic[11][x] = P_OUT;
        for (int x = 5; x <= 11; x++) ic[12][x] = SH;
        return ic;
    }

    public static int[][] makeQuestTree() {
        int[][] ic = new int[16][16];
        // Center trunk & nodes
        for (int y = 5; y <= 9; y++) ic[y][7] = G_DARK;
        for (int x = 5; x <= 10; x++) ic[7][x] = G_DARK;
        // Root node
        ic[3][6] = G_HI; ic[3][7] = G_MID; ic[3][8] = SH;
        ic[4][6] = G_MID; ic[4][7] = G_DARK; ic[4][8] = SH;
        // Left child
        ic[9][4] = C_HI; ic[9][5] = C_MID; ic[9][6] = SH;
        ic[10][4] = C_MID; ic[10][5] = C_DARK; ic[10][6] = SH;
        // Right child
        ic[9][9] = C_HI; ic[9][10] = C_MID; ic[9][11] = SH;
        ic[10][9] = C_MID; ic[10][10] = C_DARK; ic[10][11] = SH;
        // Shadows
        ic[11][4] = SH; ic[11][5] = SH; ic[11][9] = SH; ic[11][10] = SH;
        return ic;
    }

    public static int[][] makeBoxFit() {
        int[][] ic = new int[16][16];
        // 4 corner brackets
        ic[3][4] = I_HI; ic[3][5] = I_HI; ic[3][6] = I_MID;
        ic[4][4] = I_HI; ic[5][4] = I_MID;
        ic[3][11] = I_HI; ic[3][10] = I_HI; ic[3][9] = I_MID;
        ic[4][11] = I_MID; ic[5][11] = I_DARK;
        ic[11][4] = I_DARK; ic[11][5] = I_DARK; ic[11][6] = I_OUT;
        ic[10][4] = I_DARK; ic[9][4] = I_MID;
        ic[11][11] = I_OUT; ic[11][10] = I_DARK; ic[11][9] = I_OUT;
        ic[10][11] = I_DARK; ic[9][11] = I_MID;
        return ic;
    }

    public static int[][] makeChecklist() {
        int[][] ic = new int[16][16];
        for (int y = 3; y <= 11; y++) {
            ic[y][4] = P_OUT; ic[y][11] = P_OUT;
        }
        for (int x = 4; x <= 11; x++) {
            ic[3][x] = P_OUT; ic[11][x] = P_OUT; ic[12][x] = SH;
        }
        for (int y = 4; y <= 10; y++) {
            for (int x = 5; x <= 10; x++) ic[y][x] = P_MID;
        }
        ic[5][6] = 0xFF208020; ic[5][7] = 0xFF40C040;
        ic[7][6] = 0xFF208020; ic[7][7] = 0xFF40C040;
        ic[9][6] = 0xFFC04040; ic[9][7] = 0xFF802020;
        return ic;
    }

    public static int[][] makeSapphireEye() {
        int[][] ic = new int[16][16];
        // Steel eyelid outline (10x6)
        int[] topY = {6, 5, 5, 5, 5, 5, 5, 6};
        int[] botY = {8, 9, 9, 9, 9, 9, 9, 8};
        for (int i = 0; i < 8; i++) {
            int x = 4 + i;
            ic[topY[i]][x] = I_DARK;
            ic[botY[i]][x] = I_DEEP;
        }
        ic[7][3] = I_DEEP; ic[7][12] = I_DEEP;
        // Sclera
        for (int x = 5; x <= 10; x++) {
            for (int y = 6; y <= 8; y++) ic[y][x] = 0xFFF0F4F0;
        }
        // Sapphire iris & pupil
        ic[6][7] = C_HI;  ic[6][8] = C_MID;
        ic[7][7] = 0xFF0E2440; ic[7][8] = 0xFF144880;
        ic[8][7] = C_DARK; ic[8][8] = C_DARK;
        // White specular highlight
        ic[6][7] = 0xFFFFFFFF;
        // Drop shadow
        for (int x = 4; x <= 12; x++) ic[10][x] = SH;
        return ic;
    }

    public static int[][] makeEyeSlashed() {
        int[][] ic = makeSapphireEye();
        // Red slash across diagonal
        for (int i = 0; i < 8; i++) {
            ic[4 + i][11 - i] = R_MID;
            if (11 - i + 1 < 16) ic[4 + i][11 - i + 1] = SH;
        }
        return ic;
    }

    public static int[][] makeDependencyLinks() {
        int[][] ic = new int[16][16];
        // Cyan diamond node (Source)
        ic[5][4] = C_HI;
        ic[6][3] = C_HI; ic[6][4] = C_MID; ic[6][5] = C_DARK;
        ic[7][4] = C_DARK;
        // Arrow shaft --->
        ic[6][6] = I_MID; ic[6][7] = I_MID; ic[6][8] = I_HI;
        // Arrowhead tip
        ic[5][8] = I_HI; ic[7][8] = I_MID;
        // Golden square node (Target)
        for (int y = 5; y <= 7; y++) {
            for (int x = 10; x <= 12; x++) {
                ic[y][x] = (y == 5 || x == 10) ? G_HI : G_MID;
            }
        }
        // Drop shadows
        ic[8][4] = SH; ic[7][7] = SH; ic[8][10] = SH; ic[8][11] = SH; ic[8][12] = SH;
        return ic;
    }

    public static int[][] makeSort() {
        int[][] ic = new int[16][16];
        // Silver Up Arrow (x=4..6, y=3..10)
        ic[3][5] = I_HI;
        ic[4][4] = I_HI; ic[4][5] = I_MID; ic[4][6] = I_DARK;
        ic[5][5] = I_MID; ic[6][5] = I_MID; ic[7][5] = I_DARK; ic[8][5] = I_DARK;
        // Gold Down Arrow (x=9..11, y=4..11)
        ic[6][10] = G_HI; ic[7][10] = G_MID; ic[8][10] = G_MID; ic[9][10] = G_DARK;
        ic[10][9] = G_HI; ic[10][10] = G_MID; ic[10][11] = G_DARK;
        ic[11][10] = G_DARK;
        // Drop shadows
        ic[9][5] = SH; ic[12][10] = SH;
        return ic;
    }

    public static int[][] makePalette() {
        int[][] ic = new int[16][16];
        ic[3][6] = W_HI; ic[3][7] = W_HI; ic[3][8] = W_HI; ic[3][9] = W_MID;
        ic[4][5] = W_HI; ic[4][6] = R_HI;  ic[4][7] = R_MID; ic[4][8] = W_MID; ic[4][9] = G_HI;  ic[4][10] = G_MID; ic[4][11] = W_DARK;
        ic[5][4] = W_HI; ic[5][5] = R_MID; ic[5][6] = W_MID; ic[5][7] = W_MID; ic[5][8] = G_MID; ic[5][9] = W_DARK; ic[5][10] = W_DARK; ic[5][11] = W_OUT;
        ic[6][4] = W_HI; ic[6][5] = C_HI;  ic[6][6] = C_MID; ic[6][7] = W_MID; ic[6][8] = W_MID; ic[6][9] = W_DARK; ic[6][10] = W_OUT;
        ic[7][4] = W_HI; ic[7][5] = C_MID; ic[7][6] = W_MID; ic[7][7] = W_MID; ic[7][8] = W_OUT;  ic[7][9] = W_OUT;  ic[7][10] = W_DARK; ic[7][11] = W_OUT;
        ic[8][4] = W_HI; ic[8][5] = W_MID; ic[8][6] = W_MID; ic[8][7] = 0xFF0E1410; ic[8][8] = 0xFF0E1410; ic[8][9] = W_OUT; ic[8][10] = W_DARK; ic[8][11] = W_OUT;
        ic[9][5] = W_MID; ic[9][6] = W_MID; ic[9][7] = W_MID; ic[9][8] = W_DARK; ic[9][9] = W_OUT; ic[9][10] = W_DARK; ic[9][11] = SH;
        ic[10][5] = W_MID; ic[10][6] = W_DARK; ic[10][7] = W_DARK; ic[10][8] = W_DARK; ic[10][9] = W_OUT; ic[10][10] = SH;
        for (int x = 6; x <= 10; x++) ic[11][x] = SH;
        return ic;
    }

    public static int[][] makeTreasureChest() {
        int[][] ic = new int[16][16];
        int CW_HI = 0xFFD89848;
        int CW_MID = 0xFFB87828;
        int CW_DARK = 0xFF885018;
        int CW_OUT = 0xFF482808;

        // Sparkle above chest
        ic[3][6] = 0xFF55FF55; // Phosphor reward sparkle

        // Lid (y=4..6, x=4..11)
        ic[4][4] = CW_OUT; ic[4][5] = CW_HI;  ic[4][6] = CW_HI;  ic[4][7] = CW_HI;  ic[4][8] = CW_HI;  ic[4][9] = CW_HI;  ic[4][10] = CW_MID; ic[4][11] = CW_OUT;
        ic[5][4] = CW_HI;  ic[5][5] = CW_MID; ic[5][6] = CW_MID; ic[5][7] = CW_MID; ic[5][8] = CW_MID; ic[5][9] = CW_DARK; ic[5][10] = CW_DARK; ic[5][11] = CW_OUT;
        ic[6][4] = CW_OUT; ic[6][5] = CW_DARK; ic[6][6] = CW_DARK; ic[6][7] = G_HI;  ic[6][8] = G_MID; ic[6][9] = CW_DARK; ic[6][10] = CW_DARK; ic[6][11] = CW_OUT;

        // Body (y=7..10, x=4..11)
        ic[7][4] = CW_OUT; ic[7][5] = CW_HI;  ic[7][6] = CW_HI;  ic[7][7] = G_MID; ic[7][8] = G_DARK; ic[7][9] = CW_MID; ic[7][10] = CW_MID; ic[7][11] = CW_OUT;
        ic[8][4] = CW_OUT; ic[8][5] = CW_MID; ic[8][6] = CW_MID; ic[8][7] = CW_MID; ic[8][8] = CW_DARK; ic[8][9] = CW_DARK; ic[8][10] = CW_DARK; ic[8][11] = CW_OUT;
        ic[9][4] = CW_OUT; ic[9][5] = CW_DARK; ic[9][6] = CW_DARK; ic[9][7] = CW_DARK; ic[9][8] = CW_DARK; ic[9][9] = CW_DARK; ic[9][10] = CW_DARK; ic[9][11] = CW_OUT;
        for (int x = 4; x <= 11; x++) ic[10][x] = CW_OUT;
        for (int x = 5; x <= 11; x++) ic[11][x] = SH;
        return ic;
    }

    public static int[][] makeHeraldicShield() {
        int[][] ic = new int[16][16];
        // Gold Shield with exclamation mark
        for (int x = 4; x <= 11; x++) ic[3][x] = G_OUT;
        for (int y = 4; y <= 8; y++) {
            ic[y][3] = G_OUT; ic[y][4] = G_HI; ic[y][11] = G_DARK; ic[y][12] = G_OUT;
            for (int x = 5; x <= 10; x++) ic[y][x] = G_MID;
        }
        // Pointed keel
        ic[9][4] = G_OUT; ic[9][5] = G_HI;  ic[9][10] = G_DARK; ic[9][11] = G_OUT;
        for (int x = 6; x <= 9; x++) ic[9][x] = G_MID;
        ic[10][5] = G_OUT; ic[10][6] = G_HI; ic[10][9] = G_DARK; ic[10][10] = G_OUT;
        ic[10][7] = G_MID; ic[10][8] = G_MID;
        ic[11][6] = G_OUT; ic[11][7] = G_HI; ic[11][8] = G_DARK; ic[11][9] = G_OUT;
        ic[12][7] = G_OUT; ic[12][8] = G_OUT;

        // Exclamation mark in black graphite
        int EX = 0xFF141816;
        ic[4][7] = EX; ic[4][8] = EX;
        ic[5][7] = EX; ic[5][8] = EX;
        ic[6][7] = EX; ic[6][8] = EX;
        ic[8][7] = EX; ic[8][8] = EX;
        // Drop shadow
        for (int x = 5; x <= 12; x++) ic[13][x] = SH;
        return ic;
    }

    public static int[][] makeBookmark() {
        int[][] ic = new int[16][16];
        // Gold top clip
        for (int x = 5; x <= 10; x++) ic[3][x] = G_MID;
        // Silk ribbon body (y=4..11)
        for (int y = 4; y <= 9; y++) {
            ic[y][5] = R_HI; ic[y][6] = R_MID; ic[y][7] = R_MID;
            ic[y][8] = R_MID; ic[y][9] = R_DARK; ic[y][10] = R_OUT;
        }
        // Fishtail notched bottom
        ic[10][5] = R_HI; ic[10][6] = R_MID; ic[10][9] = R_DARK; ic[10][10] = R_OUT;
        ic[11][5] = R_MID; ic[11][10] = R_OUT;
        // Shadow
        ic[12][5] = SH; ic[12][10] = SH;
        return ic;
    }

    public static int[][] makeLoupe() {
        int[][] ic = new int[16][16];
        // Brass lens rim (y=3..8, x=3..8)
        ic[3][4] = G_HI; ic[3][5] = G_HI; ic[3][6] = G_MID;
        ic[4][3] = G_HI; ic[4][7] = G_DARK;
        ic[5][3] = G_HI; ic[5][7] = G_DARK;
        ic[6][3] = G_MID; ic[6][7] = G_DARK;
        ic[7][4] = G_MID; ic[7][5] = G_DARK; ic[7][6] = G_OUT;
        // Glass fill & glare
        ic[4][4] = 0xFFFFFFFF; ic[4][5] = C_HI;
        ic[5][4] = C_HI; ic[5][5] = C_MID; ic[5][6] = C_MID;
        ic[6][5] = C_MID; ic[6][6] = C_DARK;
        // Diagonal wooden handle at 45 deg
        ic[7][7] = G_OUT;
        ic[8][8] = W_HI; ic[8][9] = W_MID;
        ic[9][9] = W_HI; ic[9][10] = W_DARK;
        ic[10][10] = W_MID; ic[10][11] = W_OUT;
        ic[11][11] = W_DARK; ic[11][12] = W_OUT;
        // Shadow
        ic[12][11] = SH; ic[12][12] = SH;
        return ic;
    }

    public static int[][] makeSymmetricReturnArrow() {
        int[][] ic = new int[16][16];
        // Arrowhead pointing left, horizontal centerline between y=7 and y=8
        ic[5][7] = I_HI;  ic[5][8] = SH;
        ic[6][6] = I_HI;  ic[6][7] = I_MID; ic[6][8] = SH;
        ic[7][4] = I_HI;  ic[7][5] = I_HI;  ic[7][6] = I_HI; ic[7][7] = I_HI;
        ic[7][8] = I_HI;  ic[7][9] = I_HI;  ic[7][10] = I_HI; ic[7][11] = I_MID; ic[7][12] = SH;
        ic[8][4] = I_MID; ic[8][5] = I_MID; ic[8][6] = I_MID; ic[8][7] = I_MID;
        ic[8][8] = I_MID; ic[8][9] = I_MID; ic[8][10] = I_DARK; ic[8][11] = I_DARK; ic[8][12] = SH;
        ic[9][6] = I_DARK; ic[9][7] = I_DEEP; ic[9][8] = SH;
        ic[10][7] = I_DEEP; ic[10][8] = SH;

        // Vertical stem on right (x=10..11, y=4..6)
        ic[4][10] = I_HI;  ic[4][11] = I_MID; ic[4][12] = SH;
        ic[5][10] = I_HI;  ic[5][11] = I_MID; ic[5][12] = SH;
        ic[6][10] = I_HI;  ic[6][11] = I_MID; ic[6][12] = SH;
        ic[11][7] = SH;
        return ic;
    }

    public static void splice(BufferedImage target, int[][] glyph, int ox, int oy) {
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                int col = glyph[y][x];
                target.setRGB(ox + x, oy + y, col);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int w = 256, h = 256;
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        // Map icons to exact Better Questing atlas coordinates:
        // 1. INFO (!) -> {64, 80}, {80, 80}
        int[][] info = makeInfo();
        splice(out, info, 64, 80);
        splice(out, info, 80, 80);

        // 2. DESC ([=]) -> {208, 64}
        splice(out, makeDoc(), 208, 64);

        // 3. QUEST TREE -> {80, 0}
        splice(out, makeQuestTree(), 80, 0);

        // 4. BOX FIT ([ ]) -> {80, 64}
        splice(out, makeBoxFit(), 80, 64);

        // 5. CHECKLIST (Hide locked) -> {0, 96}
        splice(out, makeChecklist(), 0, 96);

        // 6. VIEW MODE ON (Sapphire Eye) -> {96, 0}
        splice(out, makeSapphireEye(), 96, 0);

        // 7. VIEW MODE OFF (Slashed Eye) -> {240, 80}
        splice(out, makeEyeSlashed(), 240, 80);

        // 8. DEPENDENCY LINKS (Graph Nodes + Arrow) -> {192, 80}, {208, 80}, {224, 80}
        int[][] depLinks = makeDependencyLinks();
        splice(out, depLinks, 192, 80);
        splice(out, depLinks, 208, 80);
        splice(out, depLinks, 224, 80);

        // 9. SORT (Up/Down) -> {64, 16}, {80, 16}
        int[][] sort = makeSort();
        splice(out, sort, 64, 16);
        splice(out, sort, 80, 16);

        // 10. PALETTE (Themes) -> {48, 48}, {208, 0}
        int[][] palette = makePalette();
        splice(out, palette, 48, 48);
        splice(out, palette, 208, 0);

        // 11. CLAIM ALL REWARDS (Treasure Chest!) -> {144, 64}, {160, 64}
        int[][] chest = makeTreasureChest();
        splice(out, chest, 144, 64);
        splice(out, chest, 160, 64);

        // 12. NOTIFICATION SETTINGS (Heraldic Shield Badge) -> {176, 0}
        splice(out, makeHeraldicShield(), 176, 0);

        // 13. BOOKMARK (Ribbon) -> {176, 64}
        splice(out, makeBookmark(), 176, 64);

        // 14. SEARCH / ZOOM (Loupe) -> {96, 64}
        splice(out, makeLoupe(), 96, 64);

        // 15. RETURN / EXIT ARROW (Symmetric ↰) -> {224, 48}, {160, 0}
        int[][] symmArrow = makeSymmetricReturnArrow();
        splice(out, symmArrow, 224, 48);
        splice(out, symmArrow, 160, 0);

        // Save local output
        File localOut = new File("scratch/editor_icons_template_out.png");
        localOut.getParentFile().mkdirs();
        ImageIO.write(out, "png", localOut);
        System.out.println("Atlas successfully generated to: " + localOut.getAbsolutePath());
    }
}
