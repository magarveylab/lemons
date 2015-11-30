setwd("~/Desktop/lemons/2015-11-19-1320/")
library(plyr)
library(ggplot2)
library(reshape2)



# create empty bootstrap rank distribution
rankIndices <- c(1:1000)
bootstrapDists <- data.frame(rankIndices)

# get bootstrap dirs
dirs <- list.dirs(".", recursive=FALSE, full.names=TRUE)
for (d in 1:length(dirs)) { 
  # create empty rank distribution
  rankIdx <- c(1:1000)
  dists <- data.frame(rankIdx)
  
  # read rank files 
  files <- list.files(path=dirs[d], pattern="*.csv", full.names=T, recursive=FALSE)
  for (i in 1:length(files)) {
    name <- sub("^([^.]*).*", "\\1", basename(files[i]))
    data <- read.table(files[i], header=TRUE, sep=",") # load file 
    dist <- count(data, 'Rank') # create rank distribution
    dists[[name]] <- 0 # create empty column 
    for (j in 1:nrow(dist)) { 
      count <- dist[j,2]
      dists[[name]][j] <- count # assign value
    }
  }
  
  # now, add to bootstrap
  for (k in colnames(dists)) { # for each fingerprinter 
    if (k != "rankIdx" && k %in% names(bootstrapDists)) { # if there is already a column 
      for (j in 1:nrow(dists)) { # for each row in that column 
        l <- bootstrapDists[[k]][[j]]
        l[[d]] <- dists[[k]][[j]]
      }
    } else if (k != "rankIdx") {
      cbind(bootstrapDists[[k]], vector("list", length(dirs)))
      for (j in 1:nrow(dists)) {
        l <- vector("list", length(dirs))
        l[[d]] <- dists[[k]][[j]]
        message(l)
        bootstrapDists[[k]][[j]] <- I(l)
      }
    }
  }
}

message(bootstrapDists)

#plot 
dists.melted <- melt(bootstrapDists, id="rankIndices")
plot <- ggplot(data=dists.melted, aes(x=rankIndices, y=value, color=variable)) + geom_line() + theme_bw() + coord_cartesian(xlim = c(1, 5)) 
plot(plot)