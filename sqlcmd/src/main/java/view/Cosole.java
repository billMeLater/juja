package view;

import java.util.Scanner;

/**
 * Created by v.kalitsev on 4/12/2016.
 */
public class Cosole implements View {
    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner userinput = new Scanner(System.in);
        return userinput.nextLine();
    }
}
