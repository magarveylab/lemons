setwd("~/Desktop/Peptides_linear_100_3/")
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
medians <- as.data.frame(matrix(0, ncol=length(names), nrow=1))
colnames(medians) <- names
sds <- as.data.frame(matrix(0, ncol=length(names), nrow=1))
colnames(sds) <- names

for (i in 1:length(frames)) {
  # get fingerprinter
  fp <- frames[[i]]
  
  # compute median and SD for #1 rank 
  medians[[i]][[1]] <- rowMeans(fp[1,])
  sds[[i]][[1]] <- sd(fp[1,])
}

# create firsts
firsts <- data.frame(Fingerprinter=character(length(names)), Mean=double(length(names)), SD=double(length(names)), stringsAsFactors=FALSE)
for (i in 1:length(names))
  firsts$Fingerprinter[[i]] <- names[[i]]
firsts$Fingerprinter[[1]] <- "CDK_DEFAULT"
firsts$Fingerprinter[[7]] <- "CDK_EXTENDED"
firsts$Fingerprinter[[12]] <- "CDK_GRAPH_ONLY"
firsts$Fingerprinter[[13]] <- "CDK_HYBRIDIZATION"
for (i in 1:length(medians)) 
  firsts$Mean[[i]] <-  medians[[i]][1]
for (i in 1:length(sds))
    firsts$SD[[i]] <- sds[[i]][1]
str(firsts)

plot2 <- ggplot(data=firsts, aes(x=Fingerprinter, y=Mean, fill=Fingerprinter)) + 
  geom_bar(stat="identity") + geom_errorbar(aes(x=Fingerprinter, ymin=Mean-SD, ymax=Mean+SD), width=0.2) + theme_bw() + 
  coord_cartesian(ylim=c(0,100)) + theme(axis.ticks = element_blank(), axis.text.x = element_text(angle = 90, hjust = 1)) 
#   scale_x_discrete(limits=c("MACCS", "PUBCHEM", "KLEKOTA_ROTH", "ESTATE",
#                             "CDK_DEFAULT", "CDK_EXTENDED", "CDK_GRAPH_ONLY", "CDK_HYBRIDIZATION", 
#                             "ECFP0", "ECFP2", "ECFP4", "ECFP6", 
#                             "FCFP0", "FCFP2", "FCFP4", "FCFP6", 
#                             "LINGO"))
plot(plot2)
ggsave(file="Firsts.pdf", width=11, height=8.5)
