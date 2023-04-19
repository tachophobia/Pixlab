
//
// Torbert, 24 July 2013
//
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

//
public class PixelOperations {
   public Color[][] getArray(BufferedImage img) {
      Color[][] arr;
      //
      int numcols = img.getWidth();
      int numrows = img.getHeight();
      //
      arr = new Color[numrows][numcols];
      //
      for (int j = 0; j < arr.length; j++) {
         for (int k = 0; k < arr[0].length; k++) {
            int rgb = img.getRGB(k, j);
            //
            arr[j][k] = new Color(rgb);
         }
      }
      //
      return arr;
   }

   public void setImage(BufferedImage img, Color[][] arr) {
      for (int j = 0; j < arr.length; j++) {
         for (int k = 0; k < arr[0].length; k++) {
            Color tmp = arr[j][k];
            //
            int rgb = tmp.getRGB();
            //
            img.setRGB(k, j, rgb);
         }
      }
   }

   //
   /**********************************************************************/
   //
   // pixel operations
   //
   public void zeroBlue(Color[][] arr) {
      for (int j = 0; j < arr.length; j++) {
         for (int k = 0; k < arr[0].length; k++) {
            Color tmp = arr[j][k];
            arr[j][k] = new Color(tmp.getRed(), tmp.getGreen(), 0);
         }
      }
   }

   // --------> your new methods go here <--------------
   public void negate(Color[][] arr) {
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            arr[i][j] = new Color(255 - tmp.getRed(), 255 - tmp.getGreen(), 255 - tmp.getBlue());
         }
      }
   }

   public void grayScale(Color[][] arr) {
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            int avg = (tmp.getRed() + tmp.getGreen() + tmp.getBlue()) / 3;
            arr[i][j] = new Color(avg, avg, avg);
         }
      }
   }

   public void sepiaTone(Color[][] arr) {
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];

            int r = (int) (.393 * tmp.getRed() + .769 * tmp.getGreen() + .189 * tmp.getBlue());
            int g = (int) (.349 * tmp.getRed() + .686 * tmp.getGreen() + .168 * tmp.getBlue());
            int b = (int) (.272 * tmp.getRed() + .534 * tmp.getGreen() + .131 * tmp.getBlue());

            r = Math.min(255, r);
            g = Math.min(255, g);
            b = Math.min(255, b);

            arr[i][j] = new Color(r, g, b);
         }
      }
   }

   public void blur(Color[][] arr) {
      // get the colors of the four pixels to the left, right, bottom, and top of the
      // current pixel.
      // set the current pixel's color to the average of the colors of the current
      // pixel and its four neighboring pixels.
      // ignore screen edges

      int n = 10; // amount of blur
      while (n > 0) {
         n--;
         for (int i = 1; i < arr.length - 1; i++) {
            for (int j = 1; j < arr[i].length - 1; j++) {
               Color top = arr[i - 1][j];
               Color bottom = arr[i + 1][j];
               Color left = arr[i][j - 1];
               Color right = arr[i][j + 1];

               int r = (int) ((top.getRed() + bottom.getRed() + left.getRed() + right.getRed()) / 4);
               int g = (int) ((top.getGreen() + bottom.getGreen() + left.getGreen() + right.getGreen()) / 4);
               int b = (int) ((top.getBlue() + bottom.getBlue() + left.getBlue() + right.getBlue()) / 4);

               arr[i][j] = new Color(r, g, b);
            }
         }
      }
   }

   public void posterize(Color[][] arr) {
      // for a range of colors, map them to a single color.

      // for each color if it is
      // between 0 and 62; set to 31
      // between 63 and 128; set to 95
      // between 129 and 191; set to 159
      // between 192 and 255; set to 223

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            int rgb[] = { tmp.getRed(), tmp.getGreen(), tmp.getBlue() }; // you could initialize this array with
                                                                         // int rgb[] = new int[] {...}
                                                                         // but i think that isn't necessary anymore in
                                                                         // newer versions of java
            for (int k = 0; k < rgb.length; k++) {
               if (rgb[k] < 63) {
                  rgb[k] = 31;
               } else if (rgb[k] < 128) {
                  rgb[k] = 95;
               } else if (rgb[k] < 192) {
                  rgb[k] = 159;
               } else {
                  rgb[k] = 223;
               }
            }
            arr[i][j] = new Color(rgb[0], rgb[1], rgb[2]);
         }
      }
   }

   public void colorSplash(Color[][] arr) {
      // turn picture into a grayscale except for red colors
      // a color is red if red component is greater than the sum of its green and blue

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            int r = tmp.getRed();
            int g = tmp.getGreen();
            int b = tmp.getBlue();
            int sum = r + g + b;
            if (r > (sum - r)) {
               arr[i][j] = new Color(255, sum / 3, sum / 3);
            } else {
               arr[i][j] = new Color(sum / 3, sum / 3, sum / 3);
            }
         }
      }
   }

   public void mirrorLR(Color[][] arr) {
      // mirror image horizontally, split in the center

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length / 2; j++) {
            Color tmp = arr[i][j];
            arr[i][arr[i].length - 1 - j] = tmp;
         }
      }
   }

   public void mirrorUD(Color[][] arr) {
      // mirror image vertically, split in the middle

      for (int i = 0; i < arr.length / 2; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            arr[arr.length - 1 - i][j] = tmp;
         }
      }
   }

   public void flipLR(Color[][] arr) {
      // flip across y-axis

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length / 2; j++) {
            Color tmp = arr[i][j];
            arr[i][j] = arr[i][arr[i].length - 1 - j];
            arr[i][arr[i].length - 1 - j] = tmp;
         }
      }
   }

   public void flipUD(Color[][] arr) {
      // flip across x-axis

      for (int i = 0; i < arr.length / 2; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            arr[i][j] = arr[arr.length - 1 - i][j];
            arr[arr.length - 1 - i][j] = tmp;
         }
      }
   }

   public void pixelate(Color[][] arr) {
      // prompt the user for the size of the pixelation

      int size;
      try {
         size = Integer.parseInt(JOptionPane.showInputDialog("Enter the size of the pixelation"));
      } catch (Exception e) {
         JOptionPane.showMessageDialog(null, "Invalid input. Please enter an integer");
         return;
      }
      // pixelate the image by variable size.

      for (int i = 0; i < arr.length; i += size) {
         for (int j = 0; j < arr[i].length; j += size) {
            Color tmp = arr[i][j];
            int r = tmp.getRed();
            int g = tmp.getGreen();
            int b = tmp.getBlue();
            try {
               for (int k = i; k < i + size; k++) {
                  for (int l = j; l < j + size; l++) {
                     arr[k][l] = new Color(r, g, b);
                  }
               }
            } catch (Exception e) {

            }

         }
      }
   }

}

//
// end of file
//