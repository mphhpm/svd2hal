package com.svd2hal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.svdparser.SvdDevice;
import picocli.CommandLine;
import picocli.CommandLine.Option;

public class Svd2hal implements Runnable {

    @Option(names = {"-f", "--File"}, description = "Path to svd file", required = true)
    private String filename;

    @Option(names = {"-o", "--OutputDirectory"}, description = "Path to place the generated data")
    private String outputDirectory;

    @Option(names = {"-t", "--Task"}, description = "task to be executed: gdbinit ")
    private String task;
	
	@Override
	public void run() {
        try {
        	//
        	// read svd file
            var svdFile = new File(filename);
            var device = SvdDevice.fromFile(svdFile);
            
            if (task.compareTo("gdbinit") == 0) {
            	gdbinintTask(device);
            }
            
        } catch (Exception e) {
            System.err.println("Fehler beim Lesen der SVD-Datei: " + e.getMessage());
            e.printStackTrace();
        }
	}
	
	private void gdbinintTask(SvdDevice device) {

        System.out.println("Microcontroller: " + device.getName());
        System.out.println("CPU: " + device.getCpu().getName());
        System.out.println("------------------------------------");

        try {
	        //
	        // iterate over all peripheral
	        var dirname = String.format("%s\\%s\\gdbinit", outputDirectory, device.getName().toLowerCase());
	        Files.createDirectories(Paths.get(dirname));
	        for (var peripheral : device.getPeripherals()) {
	            System.out.printf("%nPeripheral: %-10s | Base Address: 0x%08X%n", 
	                              peripheral.getName(), 
	                              peripheral.getBaseAddr());
	    		var fwriter = new FileWriter(String.format("%s\\.gdbinit.%s.%s", dirname, device.getName(), peripheral.getName()).toLowerCase());
	      	    var bwriter = new BufferedWriter(fwriter);
	      	    var printer = new GdbPeripheralPrinter(peripheral);
	      	    printer.print(bwriter);
	      	    bwriter.flush();
	      	    bwriter.close();
	        }
        }
        catch (Exception ex) {
			ex.printStackTrace();
        }		
	}

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Svd2hal()).execute(args);
        System.exit(exitCode);
    }
}