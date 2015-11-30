setwd("~/Desktop/lemons/2015-11-19-1320/")
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
sds <- as.data.frame(matrix(0, ncol=length(names), nrow=ROWS))
colnames(sds) <- names

for (i in 1:length(frames)) {
  # get fingerprinter
  fp <- frames[[i]]
  
  # compute median and SD across bootstraps
  for (j in 1:nrow(fp)) {
    medians[[i]][[j]] <- rowMeans(fp)[[j]]
    sds[[i]][[j]] <- apply(fp,1,sd)[[j]]
  }
}

# add rank indices
medians[["rankIdx"]] <- c(1:ROWS)
sds[["rankIdx"]] <- c(1:ROWS)

# melt 
medians.melted <- melt(medians, id="rankIdx")
sds.melted <- melt(sds, id="rankIdx")

str(medians.melted)
str(sds.melted)

# plot 
levels(medians.melted$variable) <- levels(sds.melted$variable)
data.melt <- merge(medians.melted, sds.melted, by="rankIdx")
str(data.melt)
str(data.melt$value.x)

plot <- ggplot(data=data.melt, aes(x=rankIdx, y=value.x, color=variable.x)) + geom_line() + geom_point() + theme_bw() + 
 coord_cartesian(xlim=c(1,5))  + geom_errorbar(aes(ymax=value.x+value.y, ymin=value.x-value.y))
plot(plot)


# plot <- ggplot(data=medians.melted, aes(x=rankIdx, y=value, color=variable)) + geom_line() + geom_point() + theme_bw() + 
#  coord_cartesian(xlim=c(1,5))  + geom_errorbar(aes(ymin=medians.melted-sds.melted, ymax=medians.melted-sds.melted))
# plot(plot)

# plot <- ggplot(data=medians, aes(x=rankIdx)) + theme_bw() + coord_cartesian(xlim=c(1,5)) 
# for (i in 1:(length(medians)-1)) {
#   plot <- plot + geom_point(aes(y=medians[[i]])) + geom_line(aes(y=medians[[i]])) + 
#     geom_errorbar(aes(ymin=medians[[i]] + sds[[i]], ymax=medians[[i]] - sds[[i]]))
# }
# plot(plot)



# plot <- ggplot(data=frames[0], aes(x=c(1:1000)))
# for (i in 1:length(frames)) {
#   plot <- plot + geom_point(aes(y=medians[[i]])) + geom_line((aes(y=medians[[i]]))) # + geom_error()
# }
# plot(plot)

# plot <- ggplot(data=frames.melted, aes(x=rankIndices, y=value, color=variable)) + geom_line() + theme_bw() + coord_cartesian(xlim = c(1, 5)) 
# plot(plot)
