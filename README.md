## svd2hal
```
Creates gdbinit script files out of the processors svd definition files to read the register values 
```
## Tools ##
```
os : windows
ide, java : https://www.eclipse.org
```

## Prerequisite
```
svd parser lib: see pom.xml
piccoli lib: see pom.xml
svd files: search for 'System View Description' on st.comdownload the svd files at
           e.g.
             https://www.st.com/resource/en/svd/stm32f4-svd.zip and place into the svd subfolder           

           alternatively find a collection of svd file at source: https://github.com/stm32duino/stm32_svd/

mvn: optional, download it from https://maven.apache.org/download.cgihttps://www.st.com/resource/en/svd/
```
# Command line #
```
 java -jar target\svd2hal-1.0.jar  --File svd\STM32F411.svd  --Task gdbinit --OutputDirectory ..\stm32-make\stm32f411\gdbinit
```

## usage ##
```
source the scripts files from within the .gdbinit file or from within the gdb debugger console

  source ../stm32-make/stm32f411/gdbinit/.gdbinit.stm32f411.rtc
  print_rtc
RTC.TR      0x4000 2800: 0x00130452     PM[22]=0 HT[21:20]=1 HU[19:16]=3 MNT[14:12]=0 MNU[11:8]=4 ST[6:4]=5 SU[3:0]=2 
RTC.DR      0x4000 2804: 0x0026e506     YT[23:20]=2 YU[19:16]=6 WDU[15:13]=7 MT[12]=0 MU[11:8]=5 DT[5:4]=0 DU[3:0]=6 
RTC.CR      0x4000 2808: 0x00000020     COE[23]=0 OSEL[22:21]=0 POL[20]=0 COSEL[19]=0 BKP[18]=0 SUB1H[17]=0 ADD1H[16]=0 TSIE[15]=0 WUTIE[14]=0 ALRBIE[13]=0 ALRAIE[12]=0 TSE[11]=0 WUTE[10]=0 ALRBE[9]=0 ALRAE[8]=0 DCE[7]=0 FMT[6]=0 BYPSHAD[5]=1 REFCKON[4]=0 TSEDGE[3]=0 WCKSEL[2:0]=0 
RTC.ISR     0x4000 280c: 0x00000017     RECALPF[16]=0 TAMP2F[14]=0 TAMP1F[13]=0 TSOVF[12]=0 TSF[11]=0 WUTF[10]=0 ALRBF[9]=0 ALRAF[8]=0 INIT[7]=0 INITF[6]=0 RSF[5]=0 INITS[4]=1 SHPF[3]=0 WUTWF[2]=1 ALRBWF[1]=1 ALRAWF[0]=1 
RTC.PRER    0x4000 2810: 0x007f00ff     PREDIV_A[22:16]=127 PREDIV_S[14:0]=255 
RTC.WUTR    0x4000 2814: 0x0000ffff
RTC.CALIBR  0x4000 2818: 0x00000000     DCS[7]=0 DC[4:0]=0 
```
  