package beetag.zxingtest;

import android.util.Log;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

final class ModDetector {

    private static final int INIT_SIZE = 10;
    private static final int CORR = 1;

    private final BitMatrix image;
    private final int height;
    private final int width;
    private final int leftInit;
    private final int rightInit;
    private final int downInit;
    private final int upInit;

    ModDetector(BitMatrix image) throws NotFoundException {
        this(image, INIT_SIZE, image.getWidth() / 2, image.getHeight() / 2);
    }

    /**
     * @param image barcode image to find a rectangle in
     * @param initSize initial size of search area around center
     * @param x x position of search center
     * @param y y position of search center
     * @throws NotFoundException if image is too small to accommodate {@code initSize}
     */
    ModDetector(BitMatrix image, int initSize, int x, int y) throws NotFoundException {
        this.image = image;
        height = image.getHeight();
        width = image.getWidth();
        int halfsize = initSize / 2;
        leftInit = x - halfsize;
        rightInit = x + halfsize;
        upInit = y - halfsize;
        downInit = y + halfsize;
        if (upInit < 0 || leftInit < 0 || downInit >= height || rightInit >= width) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    /**
     * <p>
     * Detects a candidate barcode-like rectangular region within an image. It
     * starts around the center of the image, increases the size of the candidate
     * region until it finds a white rectangular region.
     * </p>
     *
     * @return {@link ResultPoint}[] describing the corners of the rectangular
     *         region. The first and last points are opposed on the diagonal, as
     *         are the second and third. The first point will be the topmost
     *         point and the last, the bottommost. The second point will be
     *         leftmost and the third, the rightmost
     * @throws NotFoundException if no Data Matrix Code can be found
     */
    ResultPoint[] detect() throws NotFoundException {

        int left = leftInit;
        int right = rightInit;
        int up = upInit;
        int down = downInit;
        boolean sizeExceeded = false;
        boolean aBlackPointFoundOnBorder = true;
        boolean atLeastOneBlackPointFoundOnBorder = false;

        boolean atLeastOneBlackPointFoundOnRight = false;
        boolean atLeastOneBlackPointFoundOnBottom = false;
        boolean atLeastOneBlackPointFoundOnLeft = false;
        boolean atLeastOneBlackPointFoundOnTop = false;

        while (aBlackPointFoundOnBorder) {

            aBlackPointFoundOnBorder = false;

            // .....
            // .   |
            // .....
            boolean rightBorderNotWhite = true;
            while ((rightBorderNotWhite || !atLeastOneBlackPointFoundOnRight) && right < width) {
                rightBorderNotWhite = containsBlackPoint(up, down, right, false);
                if (rightBorderNotWhite) {
                    right++;
                    aBlackPointFoundOnBorder = true;
                    atLeastOneBlackPointFoundOnRight = true;
                } else if (!atLeastOneBlackPointFoundOnRight) {
                    right++;
                }
            }

            if (right >= width) {
                sizeExceeded = true;
                break;
            }

            // .....
            // .   .
            // .___.
            boolean bottomBorderNotWhite = true;
            while ((bottomBorderNotWhite || !atLeastOneBlackPointFoundOnBottom) && down < height) {
                bottomBorderNotWhite = containsBlackPoint(left, right, down, true);
                if (bottomBorderNotWhite) {
                    down++;
                    aBlackPointFoundOnBorder = true;
                    atLeastOneBlackPointFoundOnBottom = true;
                } else if (!atLeastOneBlackPointFoundOnBottom) {
                    down++;
                }
            }

            if (down >= height) {
                sizeExceeded = true;
                break;
            }

            // .....
            // |   .
            // .....
            boolean leftBorderNotWhite = true;
            while ((leftBorderNotWhite || !atLeastOneBlackPointFoundOnLeft) && left >= 0) {
                leftBorderNotWhite = containsBlackPoint(up, down, left, false);
                if (leftBorderNotWhite) {
                    left--;
                    aBlackPointFoundOnBorder = true;
                    atLeastOneBlackPointFoundOnLeft = true;
                } else if (!atLeastOneBlackPointFoundOnLeft) {
                    left--;
                }
            }

            if (left < 0) {
                sizeExceeded = true;
                break;
            }

            // .___.
            // .   .
            // .....
            boolean topBorderNotWhite = true;
            while ((topBorderNotWhite  || !atLeastOneBlackPointFoundOnTop) && up >= 0) {
                topBorderNotWhite = containsBlackPoint(left, right, up, true);
                if (topBorderNotWhite) {
                    up--;
                    aBlackPointFoundOnBorder = true;
                    atLeastOneBlackPointFoundOnTop = true;
                } else if (!atLeastOneBlackPointFoundOnTop) {
                    up--;
                }
            }

            if (up < 0) {
                sizeExceeded = true;
                break;
            }

            if (aBlackPointFoundOnBorder) {
                atLeastOneBlackPointFoundOnBorder = true;
            }

        }//while

        if (!sizeExceeded && atLeastOneBlackPointFoundOnBorder) {
            ResultPoint z;
            z = new ResultPoint(left-1, down+1);
            if (z == null) {
                Log.d("error:", "z");
                throw NotFoundException.getNotFoundInstance();
            }

            ResultPoint t;
            //go down right
            t = new ResultPoint(left-1, up-1);
            if (t == null) {
                Log.d("error:", "t");
                throw NotFoundException.getNotFoundInstance();
            }

            ResultPoint x;
            //go down left
            x = new ResultPoint(right+1 , up-1);
            if (x == null) {
                Log.d("error:", "x");
                throw NotFoundException.getNotFoundInstance();
            }

            ResultPoint y;
            //go up left
            y = new ResultPoint(right+1 , down-1);
            if (y == null) {
                Log.d("error:", "y");
                throw NotFoundException.getNotFoundInstance();
            }

            return centerEdges(y, z, x, t);

        } else {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    /**
     * recenters the points of a constant distance towards the center
     *
     * @param y bottom most point
     * @param z left most point
     * @param x right most point
     * @param t top most point
     * @return {@link ResultPoint}[] describing the corners of the rectangular
     *         region. The first and last points are opposed on the diagonal, as
     *         are the second and third. The first point will be the topmost
     *         point and the last, the bottommost. The second point will be
     *         leftmost and the third, the rightmost
     */
    private ResultPoint[] centerEdges(ResultPoint y, ResultPoint z,
                                      ResultPoint x, ResultPoint t) {

        //
        //       t            t
        //  z                      x
        //        x    OR    z
        //   y                    y
        //

        float yi = y.getX();
        float yj = y.getY();
        float zi = z.getX();
        float zj = z.getY();
        float xi = x.getX();
        float xj = x.getY();
        float ti = t.getX();
        float tj = t.getY();

        if (yi < width / 2.0f) {
            return new ResultPoint[]{
                    new ResultPoint(ti - CORR, tj + CORR),
                    new ResultPoint(zi + CORR, zj + CORR),
                    new ResultPoint(xi - CORR, xj - CORR),
                    new ResultPoint(yi + CORR, yj - CORR)};
        } else {
            return new ResultPoint[]{
                    new ResultPoint(ti + CORR, tj + CORR),
                    new ResultPoint(zi + CORR, zj - CORR),
                    new ResultPoint(xi - CORR, xj + CORR),
                    new ResultPoint(yi - CORR, yj - CORR)};
        }
    }

    /**
     * Determines whether a segment contains a black point
     *
     * @param a          min value of the scanned coordinate
     * @param b          max value of the scanned coordinate
     * @param fixed      value of fixed coordinate
     * @param horizontal set to true if scan must be horizontal, false if vertical
     * @return true if a black point has been found, else false.
     */
    private boolean containsBlackPoint(int a, int b, int fixed, boolean horizontal) {

        if (horizontal) {
            for (int x = a; x <= b; x++) {
                if (image.get(x, fixed)) {
                    return true;
                }
            }
        } else {
            for (int y = a; y <= b; y++) {
                if (image.get(fixed, y)) {
                    return true;
                }
            }
        }

        return false;
    }

}

