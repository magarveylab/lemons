setwd("/Users/michaelskinnider/Desktop/lemons/2015-11-24-1154-07/Bootstrap_1/")
library(plyr)
library(ggplot2)
library(reshape2)

# create empty rank distribution
rankIdx <- c(1:1000)
dists <- data.frame(rankIdx)

# read rank files 
files <- list.files(path=getwd(), pattern="*.csv", full.names=T, recursive=FALSE)
for (i in 1:length(files)) {
  name <- sub("^([^.]*).*", "\\1", basename(files[i]))
  data <- read.table(files[i], header=TRUE, sep=",") # load file 
  dist <- count(data, 'Rank') # create rank distribution
  cat(name, ":", dist[1,2], "\n")
  dists[[name]] <- 0 # create empty column 
  for (j in 1:nrow(dist)) { 
    count <- dist[j,2]
    dists[[name]][j] <- count # assign value
  }
}

#plot 
dists.melted <- melt(dists, id="rankIdx")
plot <- ggplot(data=dists.melted, aes(x=rankIdx, y=value, color=variable)) + geom_point() + geom_line() + theme_bw() + coord_cartesian(xlim = c(1, 5)) 
plot(plot)
ggsave(file="Ranks_1-5.pdf", width=11, height=8.5)
