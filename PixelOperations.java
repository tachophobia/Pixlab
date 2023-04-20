
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

   public void sunsetize(Color[][] arr) {
      // decrease the green and blue values by 20%

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            int r = tmp.getRed();
            int g = tmp.getGreen();
            int b = tmp.getBlue();
            g = (int) (g * .8);
            b = (int) (b * .8);
            arr[i][j] = new Color(r, g, b);
         }
      }
   }

   public void redeye(Color[][] arr) {
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color tmp = arr[i][j];
            int r = tmp.getRed();
            int g = tmp.getGreen();
            int b = tmp.getBlue();

            float redIntensity = ((float) r / ((g + b) / 2));
            if (redIntensity > 1.5f && i > 420 && i < 470 && j < 1000 && j > 600) {
               arr[i][j] = new Color((g + b) / 2, g, b);
            }

         }
      }

   }

   public void detect(Color[][] arr) {
      // canny edge detection implementation
      gaussianFilter(arr, 4, 8);
      grayScale(arr);

      double[][][] gradient = getGradient(arr);
      double[][] magnitude = gradient[0];
      double[][] direction = gradient[1];
      // apply hysteresis thresholding
      int[][] edges = hysteresisThresholding(magnitude, direction, 30., 50.);

      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            if (edges[i][j] == 0) {
               arr[i][j] = Color.BLACK;
            } else {
               arr[i][j] = Color.WHITE;
            }
         }
      }
   }

   public int[][] hysteresisThresholding(double[][] magnitude, double[][] direction, double lowThreshold,
         double highThreshold) {
      int height = magnitude.length;
      int width = magnitude[0].length;
      int[][] edgeMap = new int[height][width];

      // apply high threshold to mark out strong edges
      for (int i = 0; i < height; i++) {
         for (int j = 0; j < width; j++) {
            if (magnitude[i][j] >= highThreshold) {
               edgeMap[i][j] = 255;
            }
         }
      }

      // trace edges using low threshold and connectivity test
      for (int i = 1; i < height - 1; i++) {
         for (int j = 1; j < width - 1; j++) {
            if (magnitude[i][j] >= lowThreshold && edgeMap[i][j] == 0) {
               double angle = direction[i][j] * 180 / Math.PI;
               angle = angle < 0 ? angle + 180 : angle;
               int q = 0, r = 0;
               if ((0 <= angle && angle < 22.5) || (157.5 <= angle && angle <= 180)) {
                  q = i;
                  r = j + 1;
               } else if (22.5 <= angle && angle < 67.5) {
                  q = i + 1;
                  r = j + 1;
               } else if (67.5 <= angle && angle < 112.5) {
                  q = i + 1;
                  r = j;
               } else if (112.5 <= angle && angle < 157.5) {
                  q = i + 1;
                  r = j - 1;
               }
               if (edgeMap[q][r] == 255) {
                  edgeMap[i][j] = 255;
               } else {
                  edgeMap[i][j] = 0;
               }
            }
         }
      }
      return edgeMap;
   }

   public double[][][] getGradient(Color[][] arr) {
      int height = arr.length;
      int width = arr[0].length;
      double[][][] gradient = new double[2][height][width];
      double[][] gx = { { -1, 0, 1 }, { -2, 0, 2 }, { -1, 0, 1 } };
      double[][] gy = { { -1, -2, -1 }, { 0, 0, 0 }, { 1, 2, 1 } };

      for (int i = 1; i < height - 1; i++) {
         for (int j = 1; j < width - 1; j++) {
            double sumX = 0;
            double sumY = 0;
            for (int k = -1; k <= 1; k++) {
               for (int l = -1; l <= 1; l++) {
                  Color c = arr[i + k][j + l];
                  double intensity = c.getRed() * 0.299 + c.getGreen() * 0.587 + c.getBlue() * 0.114;
                  sumX += gx[k + 1][l + 1] * intensity;
                  sumY += gy[k + 1][l + 1] * intensity;
               }
            }
            double magnitude = Math.sqrt(sumX * sumX + sumY * sumY);
            double direction = Math.atan2(sumY, sumX);
            gradient[0][i][j] = magnitude;
            gradient[1][i][j] = direction;
         }
      }
      return gradient;
   }

   private void gaussianFilter(Color[][] arr, int k, double sigma) {
      // calculate kernel
      double[][] kernel = new double[k][k];
      double sum = 0;
      for (int i = 0; i < k; i++) {
         for (int j = 0; j < k; j++) {
            kernel[i][j] = Math.exp(-(Math.pow(i - (k + 1), 2) + Math.pow(j - (k + 1), 2)) / (2 * Math.pow(sigma, 2)))
                  / (2 * Math.PI * Math.pow(sigma, 2));
            sum += kernel[i][j];
         }
      }
      // normalize kernel
      for (int i = 0; i < k; i++) {
         for (int j = 0; j < k; j++) {
            kernel[i][j] /= sum;
         }
      }
      // apply kernel to image
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            int r = 0, g = 0, b = 0;
            for (int m = -k / 2; m < k / 2; m++) {
               for (int n = -k / 2; n < k / 2; n++) {
                  if (i + m >= 0 && i + m < arr.length && j + n >= 0 && j + n < arr[i].length) {
                     Color c = arr[i + m][j + n];
                     r += c.getRed() * kernel[m + k / 2][n + k / 2];
                     g += c.getGreen() * kernel[m + k / 2][n + k / 2];
                     b += c.getBlue() * kernel[m + k / 2][n + k / 2];
                  }
               }
            }
            Color c = new Color(Math.min(r, 255), Math.min(g, 255), Math.min(b, 255));
            arr[i][j] = c;
         }
      }
   }

   public void encode(Color[][] arr, Color[][] msg) {
      // change all red values to an even value
      for (int i = 0; i < arr.length; i++) {
         for (int j = 0; j < arr[i].length; j++) {
            Color c = arr[i][j];
            int r = c.getRed();
            if (r % 2 == 0) {
               arr[i][j] = new Color(r, c.getGreen(), c.getBlue());
            } else {
               arr[i][j] = new Color(r + 1 < 255 ? r + 1 : r - 1, c.getGreen(), c.getBlue());
            }
            // add message to image
            if (i < msg.length && j < msg[i].length && msg[i][j].getRed() != 0) {
               arr[i][j] = new Color(msg[i][j].getRed(), msg[i][j].getGreen(), msg[i][j].getBlue());
            }
         }

      }
   }

   public void decode(Color[][] arr) {
      // TODO: implement this method
   }

   public void chromakey(Color[][] arr) {
      // TODO: implement this method
   }
}
//
// end of file
//