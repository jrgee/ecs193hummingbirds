package beetag.zxingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.google.zxing.aztec.detector.Detector;
import com.google.zxing.common.*;
import com.google.zxing.*;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.detector.MonochromeRectangleDetector;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;

import java.io.File;

/**
 *Controls actions of the main screen class of the Hummingbird App
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Sets up actions to be taken upon entering the main screen including button setup to
     * transition to the camera screen
     * @param savedInstanceState the data of the current screen
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = (Button) findViewById(R.id.scan_button);
        scanButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //open built-in Android camera and save temporary image
                File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
                Uri outputFileUri = FileProvider.getUriForFile(MainActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        file);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    /**
     * Retrieves image data from the camera and decodes bit patterns into a corresponding number and
     * passes the number to the next screen
     * @param requestCode the requestCode passed to the function must be 1 in order to decode image
     * @param resultCode the resultCode passed to the function must be void and successful
     * @param data the image data taken by the camera
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) { //on picture successfully taken
            //get TextViews on main screen (temporary)
            //TextView param = (TextView) findViewById(R.id.param_string);
            //TextView results = (TextView) findViewById(R.id.results_string);

            //use the following line instead of the "file" related lines to use thumbnail instead of full image
            //Bitmap thumbMap = (Bitmap) data.getExtras().get("data");

            String decString = "Error";

            //get image to bitmap
            File imgFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "scan.jpg");
            Bitmap bMap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];
            //copy pixel data from the Bitmap into the 'intArray' array
            bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

            //create LuminanceSource and run through Binarizer to get BinaryBitmap
            LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            BitMatrix reader;
            BitMatrix bits = new BitMatrix(5,5);

            try {
                //find code in picture and return corner locations
                ModDetector detector = new ModDetector(bitmap.getBlackMatrix());
                //WhiteRectangleDetector detector = new WhiteRectangleDetector(bitmap.getBlackMatrix());
                //Detector detector = new Detector(bitmap.getBlackMatrix());
                ResultPoint[] corners = detector.detect();

                //print corner locations to TextView (for debugging)
                //param.setText(corners[0].toString() + " " + corners[2].toString() + " " +
                //corners[3].toString() + " " + corners[1].toString());

                //convert code into its binary representation stored in BitMatrix
                GridSampler sampler = GridSampler.getInstance();
                int res = 7;
                reader = sampler.sampleGrid(bitmap.getBlackMatrix(), res,res,
                        0.5f, 0.5f, res - 0.5f, 0.5f, res - 0.5f, res - 0.5f, 0.5f, res - 0.5f,
                        corners[0].getX(), corners[0].getY(), corners[2].getX(), corners[2].getY(),
                        corners[3].getX(), corners[3].getY(), corners[1].getX(), corners[1].getY());

                //print converted BitMatrix (for debugging)
                //results.setText(bits.toString());
                //Log.d("Full bits", reader.toString());
                for(int j = 1; j < 6; j++){
                    for(int i = 1; i < 6; i++){
                        if(reader.get(i,j)){
                            //Log.d("debug ", i + " " + j);
                            bits.set((i-1),(j-1));
                        }
                    }
                }
                //Log.d("Partial bits", bits.toString());
            } catch (Exception e) {
                //couldn't find code in image
                //param.setText("Error: No valid code found.");
                //results.setText("");
                //decstr.setText("");
                Log.d("no code found", "x");
                e.printStackTrace();
                return;
            }
            //NOTE: "row" and "column" are used below referring to the original orientation of the tag
            //The BEEtag specification uses "column" to refer to rows in the original orientation when mentioning the parity format
            //Also note that BEEtag uses white for 1 and 0 for black while ZXing uses true for black and false for white.
            int dec = 0;
            dec = decode(bits);
            for (int i = 0; i < 4; i++) {
                dec = decode(bits);
                if (dec != -1) {
                    //Pass BeeTag info to next screen
                    Intent saveIntent= new Intent(MainActivity.this, cameraActivity.class);
                    saveIntent.putExtra("decimal", Integer.toString(dec));
                    startActivity(saveIntent);
                } else {
                    bits = rotate(bits, 5);
                    //Log.d("bits", bits.toString());
                }
            }
        }
    }

    BitMatrix rotate(BitMatrix bits, int size){
        BitMatrix x = new BitMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(bits.get(size - j - 1, i))
                    x.set(i, j);
            }
        }
        return x;
    }

    int decode(BitMatrix bits){
        int dec = 0; //stores decimal number of tag
        int[] par = new int[5]; //stores expected parity results

        //convert BitMatrix into decimal representation and set column parity bits
        for(int i=0; i<3; i++){ //for first 3 columns
            par[i] = 0; //initialize to even (0)

            for(int j=0; j<5; j++){ //for each row
                if(!bits.get(i, j)) { //if white square (representing 1)
                    dec += Math.pow(2, 14 - 5 * i - j);
                    par[i] = 1 - par[i]; //flip parity for each white
                }
            }
        }

        //set last two parity bits
        par[3] = 0; //initialize to even (0)
        for(int j=0; j<3; j++){ //for first 3 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[3] = 1 - par[3]; //flip parity for each white
                }
            }
        }

        par[4] = 0; //initialize to even (0)
        for(int j=3; j<5; j++){ //for last 2 rows
            for(int i=0; i<3; i++){ //for first 3 columns
                if(!bits.get(i, j)) { //if white square (representing 1)
                    par[4] = 1 - par[4]; //flip parity for each white
                }
            }
        }
      
        //check parity for 4th column
        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(3, j) && par[j] == 0) || (bits.get(3, j) && par[j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 1 failed.");
                return -1;
            }
        }

        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(4, j) && par[4-j] == 0) || (bits.get(4, j) && par[4-j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 2 failed.");
                return -1;
            }
        }

        return dec;
        }

    /////////////////////////////


    public final class ModDetector {

        private static final int INIT_SIZE = 10;
        private static final int CORR = 1;

        private final BitMatrix image;
        private final int height;
        private final int width;
        private final int leftInit;
        private final int rightInit;
        private final int downInit;
        private final int upInit;

        public ModDetector(BitMatrix image) throws NotFoundException {
            this(image, INIT_SIZE, image.getWidth() / 2, image.getHeight() / 2);
        }

        /**
         * @param image barcode image to find a rectangle in
         * @param initSize initial size of search area around center
         * @param x x position of search center
         * @param y y position of search center
         * @throws NotFoundException if image is too small to accommodate {@code initSize}
         */
        public ModDetector(BitMatrix image, int initSize, int x, int y) throws NotFoundException {
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
        public ResultPoint[] detect() throws NotFoundException {

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

                int maxSize = right - left;

                ResultPoint z = null;
                z = new ResultPoint(left-1, down+1);
                if (z == null) {
                    Log.d("error:", "z");
                    throw NotFoundException.getNotFoundInstance();
                }

                ResultPoint t = null;
                //go down right
                t = new ResultPoint(left-1, up-1);
                if (t == null) {
                    Log.d("error:", "t");
                    throw NotFoundException.getNotFoundInstance();
                }

                ResultPoint x = null;
                //go down left
                x = new ResultPoint(right+1 , up-1);
                if (x == null) {
                    Log.d("error:", "x");
                    throw NotFoundException.getNotFoundInstance();
                }

                ResultPoint y = null;
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

        private ResultPoint getBlackPointOnSegment(float aX, float aY, float bX, float bY) {
            int dist = MathUtils.round(MathUtils.distance(aX, aY, bX, bY));
            float xStep = (bX - aX) / dist;
            float yStep = (bY - aY) / dist;

            for (int i = 0; i < dist; i++) {
                int x = MathUtils.round(aX + i * xStep);
                int y = MathUtils.round(aY + i * yStep);
                if (image.get(x, y)) {
                    return new ResultPoint(x, y);
                }
            }
            return null;
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



    /////////////////////////////


}
