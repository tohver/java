import edu.duke.*;
import java.io.*;

/**
 * @author tohver
 * @version .9 19.01.2022
 */
public class Converter {

    public  ImageResource makeGrey (ImageResource inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        ImageResource greyImage = new ImageResource(width, height);
        for (Pixel pixel : greyImage.pixels()) {
            Pixel inputPixel = inputImage.getPixel(pixel.getX(), pixel.getY());
            int average = (inputPixel.getRed() + inputPixel.getGreen() + inputPixel.getBlue()) / 3;
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        return greyImage;
    }

    public ImageResource makeNegative(ImageResource inputImage) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        ImageResource negativeImage = new ImageResource(width, height);
        for (Pixel pixel : negativeImage.pixels()) {
            Pixel inputPixel = inputImage.getPixel(pixel.getX(), pixel.getY());
            pixel.setRed(255 - inputPixel.getRed());
            pixel.setGreen(255 - inputPixel.getGreen());
            pixel.setBlue(255 - inputPixel.getBlue());
        }
        return negativeImage;
    }


    public void convertNegative() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            ImageResource image = new ImageResource(f);
            String fileName = image.getFileName();
            String negativeName = "negative-" + fileName;
            ImageResource negativeImage = makeNegative(image);
            negativeImage.setFileName(negativeName);
            negativeImage.save();
        }
    }

    public void convertGrey() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
            ImageResource inputFile = new ImageResource(f);
            String fileName = inputFile.getFileName();
            String newName = "grey-" + fileName;
            ImageResource greyImage = makeGrey(inputFile);
            greyImage.setFileName(newName);
            greyImage.save();
        }
    }
    public void test() {
        ImageResource ir = new ImageResource();
        ImageResource greyImage = makeGrey(ir);
        greyImage.draw();
    }


}
