setwd("~/Desktop/Peptides_linear_100_1/")
library(plyr)
library(ggplot2)
library(reshape2)

# set number of rows
ROWS <- 100

# create empty list of dataframes and fp names  
frames <- list()
names <- list()

# get bootstrap dirs
dirs <- list.dirs(".", recursive=FALSE, full.names=TRUE)
for (d in 1:length(dirs)) { 
  # read rank files 
  files <- list.files(path=dirs[d], pattern="*.csv", full.names=T, recursive=FALSE)
  for (i in 1:length(files)) {
    # if data frame does not exist, create it 
    if (i > length(frames)) {
      frames[[i]] <- data.frame(rankIdx=c(1:ROWS))
    }
    
    # get name
    if (i > length(names)) {
      names[[i]] <- sub("^([^.]*).*", "\\1", basename(files[i]))
    }
    
    # get data frame 
    frame <- frames[[i]]
    
    data <- read.table(files[i], header=TRUE, sep=",") # load file 
    dist <- count(data, 'Rank') # create rank distribution
    
    # create an empty column
    #   name <- sub("^([^.]*).*", "\\1", basename(files[i])) # get fp name 
    name <- paste("Bootstrap_", d, sep="")
    if (is.null(frame[[name]])) {
      frame[[name]] <- 0;
    }
    
    for (j in 1:nrow(dist)) { 
      count <- dist[j,2]
      frame[[name]][j] <- count # assign value
    }
    
    frames[[i]] <- frame
  }
}

# remove rankIdx column 
for (i in 1:length(frames)) {
  frame <- frames[[i]]
  frame[["rankIdx"]] <- NULL
  frames[[i]] <- frame
}

# create median and stderrs data.frames 
medians <- as.data.frame(matrix(0, ncol=length(names), nrow=ROWS))
colnames(medians) <- names

for (i in 1:length(frames)) {
  # get fingerprinter
  fp <- frames[[i]]
  
  # compute median and SD across bootstraps
  for (j in 1:nrow(fp)) {
    medians[[i]][[j]] <- rowMeans(fp)[[j]]
  }
}

# add rank indices
medians[["rankIdx"]] <- c(1:ROWS)

# melt 
medians.melted <- melt(medians, id="rankIdx")

# plot 
plot <- ggplot(data=medians.melted, aes(x=rankIdx, y=value, color=variable)) + geom_line() + geom_point() + theme_bw() + 
  coord_cartesian(xlim=c(1,5))
plot(plot)
ggsave(file="Ranks_1-5_noerr.pdf", width=11, height=8.5)
