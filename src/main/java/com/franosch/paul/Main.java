package com.franosch.paul;

public class Main {

    public static void main(String[] args) {
        Solver solver = new Solver(Integer.parseInt(args[0]), false);
        solver.solve();
    }
}
