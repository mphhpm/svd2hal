# svd2hal
Creates gdbinit script files to read the register values out of the processors svd definition files

 java -jar target\svd2hal-1.0.jar  --File svd\STM32F411.svd  --Task gdbinit --OutputDirectory ..\stm32-make\

find the svd files at https://www.st.com/resource/en/svd/stm32f4-svd.zip
