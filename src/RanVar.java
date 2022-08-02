import java.util.*;

//create random variable from Geometric distribution
public class RanVar{
    public static void main(String[] args) {
        //System.out.println((3^2) + 4);
        System.out.print(RV_gen()); // should be random number?
    }

    public static int RV_gen() {
        int geoResult = 0;
        int i = 0;
        Random random = new Random();
        // generate random number from 0 to 3
        int int_random = random.nextInt(10);
        if (int_random == 0) int_random = random.nextInt(10);
        //System.out.println(number);
        while (geoResult == 0) {
            double p = 1/int_random;
            double probability = (Math.pow((1 - p),(i - 1))) * p; // how do I differentiate i-1, i, i+1?
            double val_1 = (Math.pow((1 - p),(i - 2))) * p;
            double val_2 = (Math.pow((1 - p),(i))) * p;
            if (val_1 < probability && probability < val_2) {
                if (probability < 0) probability = probability * -1;
                geoResult = (int) probability;
            }
            i++;
        }
        return geoResult;
    }

}
