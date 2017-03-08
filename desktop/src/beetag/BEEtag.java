package beetag;

import com.google.zxing.common.*;
import com.google.zxing.*;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.GridSampler;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
//import javax.swing.JFileChooser;


public class BEEtag {
    public static void main(String[] args) {
        
        if(args.length < 2){
            System.err.println("Please specify a directory to process and a file to output to.");
            return;
        }
        
        File dir = new File(args[0]);
        if(!dir.exists() || !dir.isDirectory()) {
           System.err.println("Invalid directory " + dir.getAbsolutePath() + ".");
           return;
        }
        
        FileWriter out;
        
        try{
            out = new FileWriter(args[1]);
        }
        catch(IOException e){
            System.err.println("Invalid output file.");
            return;
        }
        
        /*JFileChooser fc = new JFileChooser();
        File dir;
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            dir = fc.getSelectedFile();
        else
            return;*/
        
        //write header to CSV
        writeCSV(out, new ArrayList<>(Arrays.asList("ID","Time")));
        
        for(File imgFile : dir.listFiles()){
            BufferedImage img;
            BasicFileAttributes attr;
            try{
                //get image to buffered image
                img = ImageIO.read(imgFile);
                attr = Files.readAttributes(imgFile.toPath(), BasicFileAttributes.class);
            }
            catch(IOException e){
                System.err.println("Invalid image file " + imgFile.getPath() + ".");
                continue;
            }

            int[] intArray = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth());

            //create LuminanceSource and run through Binarizer to get BinaryBitmap
            LuminanceSource source = new RGBLuminanceSource(img.getWidth(), img.getHeight(), intArray);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            BitMatrix bits;

            try{
                //find code in picture and return corner locations
                WhiteRectangleDetector detector = new WhiteRectangleDetector(bitmap.getBlackMatrix());
                ResultPoint[] corners = detector.detect();

                //convert code into its binary representation stored in BitMatrix
                GridSampler sampler = GridSampler.getInstance();
                bits = sampler.sampleGrid(bitmap.getBlackMatrix(), 5, 5,
                        0.5f, 0.5f, 5-0.5f, 0.5f, 5-0.5f, 5-0.5f, 0.5f, 5-0.5f,
                        corners[0].getX(), corners[0].getY(), corners[2].getX(), corners[2].getY(),
                        corners[3].getX(), corners[3].getY(), corners[1].getX(), corners[1].getY());

                //print converted BitMatrix (for debugging)
                //System.out.println(bits.toString());
            }
            catch(NotFoundException e){
                //couldn't find code in image
                System.err.println("Error: No valid code found.");
                continue;
            }
            
            //NOTE: "row" and "column" are used below referring to the original orientation of the tag
            //The BEEtag specification uses "column" to refer to rows in the original orientation when mentioning the parity format
            //Also note that BEEtag uses white for 1 and 0 for black while ZXing uses true for black and false for white.
            int dec = -1;

            for(int i = 0; i < 4; i++){
                dec = decode(bits);
                if(dec != -1) { //found valid tag orientation
                    ArrayList<String> values = new ArrayList<>(); //list of strings to write to csv
                    values.add(Integer.toString(dec)); //tag ID
                    values.add(attr.creationTime().toString()); //file creation time (temp)
                    writeCSV(out, values); //write record to CSV
                    break;
                }else{
                    bits = rotate(bits, 5);
                }
            }
            
            if(dec == -1){
                //all parity checks failed, display error message
                System.err.println("Error: All orientations failed.");
            }
        } //for all files in directory
        
        try{
            out.flush();
            out.close();
            System.out.println("Wrote to output file " + args[1] + ".");
        }
        catch(IOException e){
            System.err.println("Failed to close CSV file.");
        }
    } //main

    public static BitMatrix rotate(BitMatrix bits, int size){
        BitMatrix x = new BitMatrix(size, size);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if(bits.get(size - j - 1, i))
                    x.set(i, j);
            }
        }
        return x;
    } //rotate

    public static int decode(BitMatrix bits){
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

        //check parity for 5th column (reverse of 4th column)
        for(int j=0; j<5; j++){ //for each row
            if((!bits.get(4, j) && par[4-j] == 0) || (bits.get(4, j) && par[4-j] == 1)){ //if failed parity check
                //decstr.setText("Error: Parity check 2 failed.");
                return -1;
            }
        }
        return dec;
    } //decode
    
    public static void writeCSV(FileWriter writer, ArrayList<String> record)
    {
        try{
            writer.append(record.get(0));
            for(int i=1; i<record.size(); i++){
                writer.append("," + record.get(i));
            }
            writer.append("\n");
        }
        catch(IOException e){
            System.err.println("Failed to write to CSV.");
        }
    } //writeCSV
} //BEEtag class
