
?read_csv

dat = read.csv("/mnt/work/lvecsey/src/javapso/pso_generations.csv")

errvec = dat[,2]

plot(1:length(errvec), errvec, col = "blue", xlab = "Generation #", ylab = "Total Error", main = "Error Plot")

