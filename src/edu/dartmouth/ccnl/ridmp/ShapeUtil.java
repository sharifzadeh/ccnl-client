package edu.dartmouth.ccnl.ridmp;

import edu.dartmouth.ccnl.ridmp.conf.Configuration;
import edu.dartmouth.ccnl.ridmp.rules.FeatureReward;
import edu.dartmouth.ccnl.ridmp.rules.ProbabilityReward;
import edu.dartmouth.ccnl.ridmp.rules.Reward;
import javafx.geometry.Point2D;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 *
 * Created by amir on 12/19/2014.
 */


public class ShapeUtil {

    /**
     * Bit offset for the alpha channel.
     */
    public static final int ALPHA_SHIFT = 24;
    /**
     * Bit offset for the red channel.
     */
    public static final int RED_SHIFT = 16;
    /**
     * Bit offset for the green channel.
     */
    public static final int GREEN_SHIFT = 8;

    String env = "gaussian";
    double std = 20;
    double phase = 0;

    double[] color0 = {128, 128, 128};

    public GaborShape generateImage(int size, Contrast contrast, double orient, double freq) {
//        System.out.println(orient + " " + freq + " " + contrast.getColor1() + " " + contrast.getColor2());

        double color1 = contrast.getColor1()[0];
        double color2 = contrast.getColor2()[0];

        GaborShape image = new GaborShape(size, size);
        image.setOrientation(orient);
        image.setSpecialFrequency(freq);

        PixelWriter pixelWriter = image.getPixelWriter();

        orient = Math.toRadians(orient);
        double _size = size;
        size = (int) (size / std);
        double dx, dy, t, x, y, amp, f, r, g, b;
        int color;
        for (int rx = 0; rx < std * size; rx++) {
            for (int ry = 0; ry < std * size; ry++) {
                dx = rx - (0.5 * std * size);

                dy = ry - (0.5 * std * size);

                t = Math.atan2(dy, dx) + orient;
                r = Math.sqrt(dx * dx + dy * dy);
                x = r * Math.cos(t);
                y = r * Math.sin(t);
                amp = 0.5 + 0.5 * Math.cos(2 * Math.PI * (x * freq + phase));

                if (env.equals("gaussian")) {
                    f = Math.exp(-0.5 * Math.pow(x / std, 2) - 0.5 * Math.pow(y / std, 2));
                } else if (env.equals("linear")) {
                    f = Math.max(0, (0.5 * std * size - r) / (0.5 * std * size));
                } else if (env.equals("cos")) {
                    if (r > _size / 2) {
                        f = 0;
                    } else {
                        f = Math.cos((Math.PI * (r + _size / 2)) / (_size - 1) - Math.PI / 2);
                    }
                } else if (env.equals("hann")) {
                    if (r > _size / 2) {
                        f = 0;
                    } else {
                        f = 0.5 * (1 - Math.cos((2 * Math.PI * (r + _size / 2)) / (_size - 1)));
                    }
                } else if (env.equals("hamming")) {
                    if (r > _size / 2) {
                        f = 0;
                    } else {
                        f = 0.54 - 0.46 * Math.cos((2 * Math.PI * (r + _size / 2)) / (_size - 1));
                    }
                } else if (env.equals("circle")) {
                    if (r > 0.5 * std * size) {
                        f = 0;
                    } else {
                        f = 1;
                    }
                } else {
                    f = 1;
                }

                r = color1 * amp + color2 * (1 - amp);

                g = color1 * amp + color2 * (1 - amp);

                b = color1 * amp + color2 * (1 - amp);

                if (color0[0] < 0 || color0[1] < 0 || color0[2] < 0) {
                    color = rgba((int) (127 - 127 * f), (int) r, (int) g, (int) b);
                } else {
                    r = (int) (r * f + color0[0] * (1 - f));
                    g = (int) (g * f + color0[1] * (1 - f));
                    b = (int) (b * f + color0[2] * (1 - f));
                    color = rgba(255, (int) r, (int) g, (int) b);
                }

                pixelWriter.setArgb(rx, ry, color);
            }
        }

        return image;
    }


    public int rgba(int alpha, int red, int green, int blue) {
        return (alpha << ALPHA_SHIFT) | red << RED_SHIFT | green << GREEN_SHIFT | blue;
    }

    public static void circlePoints(Point2D center, int x, int y, Color color, PixelWriter pw) {
        pw.setColor(x, y, color);
        pw.setColor(y, x, color);
        pw.setColor((int) (center.getX() - x), y, color);
        pw.setColor(y, (int) (center.getX() - x), color);
        pw.setColor(x, (int) (center.getY() - y), color);
        pw.setColor((int) (center.getY() - y), x, color);
        pw.setColor((int) (center.getX() - x), (int) (center.getY() - y), color);
        pw.setColor((int) (center.getY() - y), (int) (center.getX() - x), color);
    }

    /**
     *
     * @param center
     * @param r
     * @param color
     * @param pw
     */
    public static void drawCircle(Point2D center, int r, Color color, PixelWriter pw) {
            int x = 0, y = r;
            double d = 1.25 - r;
            circlePoints(center, x, y, color, pw);
            while (x <= y) {
                if (d < 0) {
                    d += 2 * x + 3;
                } else {
                    d += 2 * (x - y) + 5;
                    y--;
                }
                x++;
                circlePoints(center, x, y, color, pw);
            }
    }

    private int[] shuffle(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
        return array;
    }

    private Permutation[] shufflePermutation(Contrast[] contrasts, double[] specialFrequencies, double[] orientations) {
        int u = contrasts.length * specialFrequencies.length * orientations.length;
        Permutation[] permutations = new Permutation[u];

        int count = 0;
        for (double specialFrequency : specialFrequencies)
            for (double orientation : orientations)
                for (Contrast contrast : contrasts)
                    permutations[count] = new Permutation(count++, contrast, specialFrequency, orientation);

        int[] uIndex = new int[u];
        for (int i = 0; i < u; i++)
            uIndex[i] = i;

        uIndex = shuffle(uIndex);

        Permutation perm;
        for (int i = 0; i < u; i++) {
            perm = permutations[i];
            permutations[i] = permutations[uIndex[i]];
            permutations[uIndex[i]] = perm;
        }

        return permutations;
    }

    public Permutation[][] matrixShuffle(int row, int col,
                                         Contrast[] contrasts,
                                         double[] specialFrequencies, double[] orientations) {
        int n = 3;
        int m = (row * col) / n;
        int l = (row * col) / (int) Math.pow(2, n);
        Permutation[][] permutations = new Permutation[row][col];

        for (int i = 0; i < l; i++)
            permutations[i] = shufflePermutation(contrasts, specialFrequencies, orientations);

        ArrayList<Permutation> list = new ArrayList<Permutation>();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                list.add(permutations[i][j]);

        int u = row * col;
        Permutation[] perms = new Permutation[u];
        list.toArray(perms);

//        int[] uIndex = new int[u];
//        for (int i = 0; i < u; i++)
//            uIndex[i] = i;
//
//        uIndex = shuffle(uIndex);
//
//        Permutation perm;
//        for (int i = 0; i < u; i++) {
//            perm = perms[i];
//            perms[i] = perms[uIndex[i]];
//            perms[uIndex[i]] = perm;
//        }

        Permutation[][] shuffleMatrix = new Permutation[row][col];
        u = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                shuffleMatrix[i][j] = perms[u++];

        return permutations;
    }


    public GaborShape[][] probabilityReward(int size, int row, int col,
                                            double[] color1, double[] color2, double constantRate,
                                            double[] specialFrequencies, double[] orientations, double currentProbability, String name) {
        GaborShape[][] shapes = new GaborShape[row][col];
//        TODO: changes to 2
        Contrast[] contrasts = new Contrast[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            contrasts[i] = new Contrast(color1[i] - (i * constantRate), color2[i] + (i * constantRate));

        Permutation[][] permutations = matrixShuffle(row, col, contrasts,
                specialFrequencies, orientations);

        boolean hasReward;
        double specialFrequency;
        double orientation;

        //        TODO: changes to 2
        int[] featureCount = {0, 0};
        int count = 0;

        Permutation[] perms = new Permutation[row * col];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                perms[count++] = permutations[i][j];

        for (Permutation permutation : perms) {
//                System.out.println(c++);
            if (name.equals("ornt")) {
                if (specialFrequencies[0] == permutation.getSpecialFrequency())
                    featureCount[0]++;
                else if (specialFrequencies[1] == permutation.getSpecialFrequency())
                    featureCount[1]++;
//                    else if (specialFrequencies[2] == permutation.getSpecialFrequency())
//                        featureCount[2]++;
            } else if (name.equals("sf")) {
                if (orientations[0] == permutation.getOrientation())
                    featureCount[0]++;
                else if (orientations[1] == permutation.getOrientation())
                    featureCount[1]++;
//                    else if (orientations[2] == permutation.getOrientation())
//                        featureCount[2]++;
            }
        }

        List<List<FeatureRewardIndex>> rewardList = new ArrayList<List<FeatureRewardIndex>>();

        for (int i = 0; i < specialFrequencies.length; i++)
            rewardList.add(i, new ArrayList<FeatureRewardIndex>());

        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                specialFrequency = permutations[i][j].getSpecialFrequency();
                orientation = permutations[i][j].getOrientation();
                FeatureRewardIndex featureRewardIndex = null;
                if (name.equals("ornt")) {
                    index = ArrayUtils.indexOf(orientations, orientation);
                    featureRewardIndex = new FeatureRewardIndex(name, i, j, orientation);
                } else if (name.equals("sf")) {
                    index = ArrayUtils.indexOf(specialFrequencies, specialFrequency);
                    featureRewardIndex = new FeatureRewardIndex(name, i, j, specialFrequency);
                }
                rewardList.get(index).add(featureRewardIndex);
            }
        }

        int[] featureRewards;
        featureRewards = new int[2];

        for (int i = 0; i < 2; i++)
            featureRewards[i] = (int) Math.round(featureCount[i] * currentProbability);

        Integer[] selections;
        int m;
        count = 0;
        for (List<FeatureRewardIndex> featureRewardIndexList : rewardList) {
            FeatureRewardIndex[] featureRewardIndexes = new FeatureRewardIndex[featureCount[count]];
            featureRewardIndexes = featureRewardIndexList.toArray(featureRewardIndexes);
            m = featureRewards[count++];

            selections = new Integer[m];
            for (int i = 0; i < m; i++)
                selections[i] = i;
            List<Integer> list = Arrays.asList(selections);
            Collections.shuffle(list);
            list.toArray(selections);

            FeatureRewardIndex featureRewardIndex;
            for (int i = 0; i < m; i++) {
                featureRewardIndex = featureRewardIndexes[selections[i]];
                int rowIndex = featureRewardIndex.getRowIndex();
                int columnIndex = featureRewardIndex.getColumnIndex();
                permutations[rowIndex][columnIndex].setReward(true);
            }
        }
        m = row * col;
        perms = new Permutation[m];
        count = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                perms[count++] = permutations[i][j];

        List<Permutation> list = Arrays.asList(perms);
        Collections.shuffle(list);
        list.toArray(perms);
        count = 0;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                permutations[i][j] = perms[count++];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                specialFrequency = permutations[i][j].getSpecialFrequency();
                orientation = permutations[i][j].getOrientation();
                hasReward = permutations[i][j].isReward();
                //                System.out.println(i + " , " + j + " : " + orientation + " : " + hasReward);
                shapes[i][j] = new ShapeUtil().generateImage(size, permutations[i][j].getContrast(), orientation, specialFrequency);

                shapes[i][j].setReward(hasReward);

            }
//            System.out.println("====================================================================");
        }

        return shapes;
    }

    public GaborShape[][] featureReward(int size, int row, int col,
                                        double[] color1, double[] color2, double constantRate,
                                        double[] specialFrequencies, double[] orientations, double[] rewardProbabilities, String name, boolean state) {
        GaborShape[][] shapes = new GaborShape[row][col];
//        TODO: changes to 2
        Contrast[] contrasts = new Contrast[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            contrasts[i] = new Contrast(color1[i] - (i * constantRate), color2[i] + (i * constantRate));

        Permutation[][] permutations = matrixShuffle(row, col, contrasts,
                specialFrequencies, orientations);

        boolean hasReward;
        double specialFrequency;
        double orientation;

        //        TODO: changes to 2
        int[] featureCount = {0, 0};
        int count = 0;

        Permutation[] perms = new Permutation[row * col];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                perms[count++] = permutations[i][j];

            for (Permutation permutation : perms) {
                if (name.equals("sf")) {
                    if (specialFrequencies[0] == permutation.getSpecialFrequency())
                        featureCount[0]++;
                    else if (specialFrequencies[1] == permutation.getSpecialFrequency())
                        featureCount[1]++;
                }

                else if (name.equals("ornt")) {
                    if (orientations[0] == permutation.getOrientation())
                        featureCount[0]++;
                    else if (orientations[1] == permutation.getOrientation())
                        featureCount[1]++;
                }
            }

        int[] featureRewards;
        featureRewards = new int[rewardProbabilities.length];

        featureRewards[0] = (int) Math.round(featureCount[0] * rewardProbabilities[0]);
        featureRewards[1] = (int) (featureCount[1] * rewardProbabilities[1]);

        int ind0,ind1;
        ind0 = ind1 = 0;

        for (Permutation permutation : perms) {
            if (name.equals("sf")) {
                if (specialFrequencies[0] == permutation.getSpecialFrequency()) {
                    if (ind0 < featureRewards[0]) {
                        permutation.setReward(true);
                        ind0++;
                    }
                    else
                        permutation.setReward(false);
                }
                else if (specialFrequencies[1] == permutation.getSpecialFrequency()) {
                    if (ind1 < featureRewards[1]) {
                        permutation.setReward(true);
                        ind1++;
                    }
                    else
                        permutation.setReward(false);
                }
            }

            else if (name.equals("ornt")) {
                if (orientations[0] == permutation.getOrientation()) {
                    if (ind0 < featureRewards[0]) {
                        permutation.setReward(true);
                        ind0++;
                    }
                    else
                        permutation.setReward(false);
                }
                else if (orientations[1] == permutation.getOrientation()) {
                    if (ind1 < featureRewards[1]) {
                        permutation.setReward(true);
                        ind1++;
                    }
                    else
                        permutation.setReward(false);
                }
            }
        }

            permutations = new Permutation[row][col];
            List<Permutation> list = Arrays.asList(perms);
            Collections.shuffle(list);
            list.toArray(perms);
            count = 0;
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    permutations[i][j] = perms[count++];

//            System.out.println("===========================================");
//            double val;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    specialFrequency = permutations[i][j].getSpecialFrequency();
                    orientation = permutations[i][j].getOrientation();
                    hasReward = permutations[i][j].isReward();

//                    if (name.equals("ornt"))
//                        val = permutations[i][j].getOrientation();
//                    else
//                        val = permutations[i][j].getSpecialFrequency();

//                    System.out.print(val + ":" + permutations[i][j].isReward() + " ");

                    shapes[i][j] = new ShapeUtil().generateImage(size, permutations[i][j].getContrast(), orientation, specialFrequency);
                    shapes[i][j].setOrientation(orientation);
                    shapes[i][j].setSpecialFrequency(specialFrequency);
                    shapes[i][j].setReward(hasReward);
            }
//            System.out.println();
        }

//            List<List<FeatureRewardIndex>> rewardList = new ArrayList<List<FeatureRewardIndex>>();
//
//            for (int i = 0; i < specialFrequencies.length; i++)
//                rewardList.add(i, new ArrayList<FeatureRewardIndex>());
//
//            int index = 0;
//            for (int i = 0; i < row; i++) {
//                for (int j = 0; j < col; j++) {
//                    specialFrequency = permutations[i][j].getSpecialFrequency();
//                    orientation = permutations[i][j].getOrientation();
//                    FeatureRewardIndex featureRewardIndex = null;
//                    if (name.equals("ornt")) {
//                        index = ArrayUtils.indexOf(orientations, orientation);
//                        featureRewardIndex = new FeatureRewardIndex(name, i, j, orientation);
//                    } else if (name.equals("sf")) {
//                        index = ArrayUtils.indexOf(specialFrequencies, specialFrequency);
//                        featureRewardIndex = new FeatureRewardIndex(name, i, j, specialFrequency);
//                    }
//                    rewardList.get(index).add(featureRewardIndex);
//                }
//            }
//
//
//            Integer[] selections;
//            int m;
//            count = 0;
//            for (List<FeatureRewardIndex> featureRewardIndexList : rewardList) {
//                FeatureRewardIndex[] featureRewardIndexes = new FeatureRewardIndex[featureCount[count]];
//                featureRewardIndexes = featureRewardIndexList.toArray(featureRewardIndexes);
//                m = featureRewards[count++];
//
//                selections = new Integer[m];
//                for (int i = 0; i < m; i++)
//                    selections[i] = i;
//                List<Integer> list = Arrays.asList(selections);
//                Collections.shuffle(list);
//                list.toArray(selections);
//
//                FeatureRewardIndex featureRewardIndex;
//                for (int i = 0; i < m; i++) {
//                    featureRewardIndex = featureRewardIndexes[selections[i]];
//                    int rowIndex = featureRewardIndex.getRowIndex();
//                    int columnIndex = featureRewardIndex.getColumnIndex();
//                    permutations[rowIndex][columnIndex].setReward(true);
//                }
//            }
//
//        double val;
//        System.out.println(name);
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//                if (name.equals("ornt"))
//                    val = permutations[i][j].getSpecialFrequency();
//                else
//                    val = permutations[i][j].getOrientation();
//
//                System.out.print(val + ":" + permutations[i][j].isReward() + " ");
//                specialFrequency = permutations[i][j].getSpecialFrequency();
//                orientation = permutations[i][j].getOrientation();
//                hasReward = permutations[i][j].isReward();
//                shapes[i][j] = new ShapeUtil().generateImage(size, permutations[i][j].getContrast(), orientation, specialFrequency);
//
//                shapes[i][j].setReward(hasReward);
//            }
//            System.out.println();
//            }

//            m = row * col;
//            perms = new Permutation[m];
//            count = 0;
//            for (int i = 0; i < row; i++)
//                for (int j = 0; j < col; j++)
//                    perms[count++] = permutations[i][j];
//
//            List<Permutation> list = Arrays.asList(perms);
//            Collections.shuffle(list);
//            list.toArray(perms);
//            count = 0;
//            for (int i = 0; i < row; i++)
//                for (int j = 0; j < col; j++)
//                    permutations[i][j] = perms[count++];
//
//            System.out.println(name + " : " + state);
//            for (int i = 0; i < row; i++) {
//                for (int j = 0; j < col; j++) {
//                    specialFrequency = permutations[i][j].getSpecialFrequency();
//                    orientation = permutations[i][j].getOrientation();
//                    hasReward = permutations[i][j].isReward();
//
////                    if (state)
////                        hasReward = !hasReward;
//
////                    System.out.println(i + " , " + j + " : " + orientation + " : " + hasReward);
//                    shapes[i][j] = new ShapeUtil().generateImage(size, permutations[i][j].getContrast(), orientation, specialFrequency);
//
//                    shapes[i][j].setReward(hasReward);
//
//            }
////            System.out.println("====================================================================");
//        }

        return shapes;
    }


    public GaborShape[][] displayImageMatrix(int size, int row, int col,
                                             double[] color1, double[] color2, double constantRate,
                                             double[] specialFrequencies, double[] orientations, Reward reward) {
        GaborShape[][] shapes = new GaborShape[row][col];
//        TODO: changes to 2
        Contrast[] contrasts = new Contrast[Configuration.length];
        for (int i = 0; i < Configuration.length; i++)
            contrasts[i] = new Contrast(color1[i] - (i * constantRate), color2[i] + (i * constantRate));

        Permutation[][] permutations = matrixShuffle(row, col, contrasts,
                specialFrequencies, orientations);

        String name;
        boolean hasReward;
        double specialFrequency;
        double orientation;

//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//                orientation = permutations[i][j].getOrientation();
//                System.out.print(i + "," + j + " = " + orientation + " | ");
//            }
//            System.out.println();
//        }

        //        TODO: changes to 2
        int[] featureCount = {0, 0};
        int count = 0;

        Permutation[] perms = new Permutation[row * col];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                perms[count++] = permutations[i][j];

        int c = 0;
        if (reward instanceof FeatureReward) {
            name = ((FeatureReward) reward).getFeature();
            for (Permutation permutation : perms) {
//                System.out.println(c++);
                if (name.equals("ornt")) {
                    if (specialFrequencies[0] == permutation.getSpecialFrequency())
                        featureCount[0]++;
                    else if (specialFrequencies[1] == permutation.getSpecialFrequency())
                        featureCount[1]++;
//                    else if (specialFrequencies[2] == permutation.getSpecialFrequency())
//                        featureCount[2]++;
                } else if (name.equals("sf")) {
                    if (orientations[0] == permutation.getOrientation())
                        featureCount[0]++;
                    else if (orientations[1] == permutation.getOrientation())
                        featureCount[1]++;
//                    else if (orientations[2] == permutation.getOrientation())
//                        featureCount[2]++;
                }
            }

            List<List<FeatureRewardIndex>> rewardList = new ArrayList<List<FeatureRewardIndex>>();

            for (int i = 0; i < specialFrequencies.length; i++)
                rewardList.add(i, new ArrayList<FeatureRewardIndex>());

            int index = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    specialFrequency = permutations[i][j].getSpecialFrequency();
                    orientation = permutations[i][j].getOrientation();
                    FeatureRewardIndex featureRewardIndex = null;
                    if (name.equals("ornt")) {
                        index = ArrayUtils.indexOf(orientations, orientation);
                        featureRewardIndex = new FeatureRewardIndex(name, i, j, orientation);
                    } else if (name.equals("sf")) {
                        index = ArrayUtils.indexOf(specialFrequencies, specialFrequency);
                        featureRewardIndex = new FeatureRewardIndex(name, i, j, specialFrequency);
                    }
                    rewardList.get(index).add(featureRewardIndex);
                }
            }

            int[] featureRewards;
            double[] rewardProbabilities = ((FeatureReward) reward).getRewardProbabilities();
            featureRewards = new int[rewardProbabilities.length];

            for (int i = 0; i < rewardProbabilities.length; i++)
                featureRewards[i] = (int) Math.round(featureCount[i] * rewardProbabilities[i]);

            Integer[] selections;
            int m;
            count = 0;
            for (List<FeatureRewardIndex> featureRewardIndexList : rewardList) {
                FeatureRewardIndex[] featureRewardIndexes = new FeatureRewardIndex[featureCount[count]];
                featureRewardIndexes = featureRewardIndexList.toArray(featureRewardIndexes);
                m = featureRewards[count++];

                selections = new Integer[m];
                for (int i = 0; i < m; i++)
                    selections[i] = i;
                List<Integer> list = Arrays.asList(selections);
                Collections.shuffle(list);
                list.toArray(selections);

                FeatureRewardIndex featureRewardIndex;
                for (int i = 0; i < m; i++) {
                    featureRewardIndex = featureRewardIndexes[selections[i]];
                    int rowIndex = featureRewardIndex.getRowIndex();
                    int columnIndex = featureRewardIndex.getColumnIndex();
                    permutations[rowIndex][columnIndex].setReward(true);
                }
            }
            m = row * col;
            perms = new Permutation[m];
            count = 0;
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    perms[count++] = permutations[i][j];

            List<Permutation> list = Arrays.asList(perms);
            Collections.shuffle(list);
            list.toArray(perms);
            count = 0;
            for (int i = 0; i < row; i++)
                for (int j = 0; j < col; j++)
                    permutations[i][j] = perms[count++];
        }

        else if (reward instanceof ProbabilityReward) {
            name = ((ProbabilityReward) reward).getProbability();
//            TODO
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                specialFrequency = permutations[i][j].getSpecialFrequency();
                orientation = permutations[i][j].getOrientation();
                hasReward = permutations[i][j].isReward();
//                System.out.println(i + " , " + j + " : " + orientation + " : " + hasReward);
                shapes[i][j] = new ShapeUtil().generateImage(size, permutations[i][j].getContrast(), orientation, specialFrequency);

                if (reward instanceof FeatureReward) {
                    shapes[i][j].setReward(hasReward);
                }
            }
//            System.out.println("====================================================================");
        }

        return shapes;
    }

    class FeatureRewardIndex {
        private String name;
        private double value;
        private int rowIndex;
        private int columnIndex;

        public FeatureRewardIndex(String name, int rowIndex, int columnIndex, double value) {
            this.name = name;
            this.rowIndex = rowIndex;
            this.columnIndex = columnIndex;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public void setColumnIndex(int columnIndex) {
            this.columnIndex = columnIndex;
        }
    }

    class Permutation {
        private Contrast contrast;
        double specialFrequency;
        double orientation;
        int index;
        boolean reward;

        private Permutation(int index, Contrast contrast, double specialFrequency, double orientation) {
            this.index = index;
            this.contrast = contrast;
            this.specialFrequency = specialFrequency;
            this.orientation = orientation;
        }

        public Contrast getContrast() {
            return contrast;
        }

        public void setContrast(Contrast contrast) {
            this.contrast = contrast;
        }

        public double getSpecialFrequency() {
            return specialFrequency;
        }

        public void setSpecialFrequency(double specialFrequency) {
            this.specialFrequency = specialFrequency;
        }

        public double getOrientation() {
            return orientation;
        }

        public void setOrientation(double orientation) {
            this.orientation = orientation;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public boolean isReward() {
            return reward;
        }

        public void setReward(boolean reward) {
            this.reward = reward;
        }
    }
}
