package com.svd2hal;

import java.io.IOException;
import java.io.Writer;

import io.svdparser.SvdPeripheral;

public class GdbPeripheralPrinter {

	private SvdPeripheral _peripheral;
	public GdbPeripheralPrinter(SvdPeripheral peripheral) {
		_peripheral = peripheral;
	}
	
	public void print(Writer stream) {
		_peripheral.getRegisters().sort((x,y) -> (x.getOffset().compareTo(y.getOffset())));
		try {
			stream.append(String.format("####\n", _peripheral.getName()));
			stream.append(String.format("#### %s\n", _peripheral.getName()));
			// set $CR1 	 = 0x00
			_peripheral.getRegisters().forEach(register -> {
				try {
					stream.append(String.format("set $%-15s = 0x%x\n", register.getName(), register.getOffset()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			stream.append(String.format("\n"));
			var funcname = String.format("print_%s", _peripheral.getName()).toLowerCase();
			stream.append(String.format("printf \"loading %s\\n\"\n\n", funcname));
			//define print_spi
			stream.append(String.format("define %s\n\n", funcname));
			stream.append(String.format("    set $baseaddr = 0x%x\n", _peripheral.getBaseAddr()));
			_peripheral.getRegisters().forEach(register -> {
				try {
					// 	set $addr = $baseAddr + $CR2
					stream.append(String.format("    ###########################\n", register.getName()));
					stream.append(String.format("    ##        %-7s        ##\n", register.getName()));
					stream.append(String.format("    ###########################\n", register.getName()));
					stream.append(String.format("    set $addr = $baseaddr + $%s\n", register.getName()));
					stream.append(String.format("    printf \"%s.%-7s 0x%%4x %%4x: 0x%%08x\", ($addr >> 16), ($addr & 0xFFFF), *$addr \n", 
							_peripheral.getName(), register.getName()));
					register.getFields().sort((x,y) -> (-1 * x.getBitOffset().compareTo(y.getBitOffset())));
					if (register.getFields().size() > 1) {
						stream.append(String.format("    printf \"     \"\n"));
						register.getFields().forEach(field -> {
							try {
								stream.append(String.format("    printf \"%s:%%d \", (($addr >> %d) & 0x%02x)\n", 
										field.getName(), field.getBitOffset(), (1 << field.getBitWidth())-1));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});
					}
					stream.append(String.format("    printf \"\\n\"\n"));
					stream.append(String.format("\n"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			stream.append(String.format("end\n"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        for (var register: _peripheral.getRegisters()) {
            System.out.printf("      Register: %-10s%n", register.getName());
            register.getFields().sort((x,y) -> (x.getBitOffset().compareTo(y.getBitOffset())));
            for (var field: register.getFields()) {
                System.out.printf("         Field: %-15s Offset %-2d: Width:%-2d%n", 
                        field.getName(), 
                        field.getBitOffset(), 
                        field.getBitWidth());
            }
        	
        }
	}
}
