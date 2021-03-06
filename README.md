# LEMONS

LEMONS (Library for the Enumeration of MOdular Natural Structures) is a library for the enumeration of hypothetical modular natural product structures. A second library of modified structures can be generated based on the first library, and the first and second libraries can be compared using chemical fingerprints. 

# Usage

The most simple use case for LEMONS involves specifying parameters for the generation of a single library of scaffolds:

``` 
--initial_monomers 	monomers used to construct the original scaffolds
--initial_reactions	tailoring reactions used to construct the original scaffolds
--max_size	    	the maximum size, in monomers, of a scaffold
--min_size	     	the minimum size, in monomers, of a scaffold
--library_size		the size of the library to generate
``` 

A modified library of scaffolds can also be generated by varying parameters:

```
--add_reactions      add tailoring reactions to the modified scaffolds
--remove_reactions   reverse the chemistry catalyzed by these reactions from the modified scaffolds
--swap_reactions     change the site of these tailoring reactions on the modified scaffolds
--swap_monomers      monomers to substitute into the modified scaffolds
--swaps              the number of monomer substitutions to execute
``` 

The `-w` flag writes all generated libraries, while the `-f` flag generates chemical fingerprints and ranks Tanimoto coefficients between original and modified structures. 

To see a full list of options, do:

```
$ java -jar lemons.jar --help
```

# Extending LEMONS

LEMONS is designed to be easily extensible. Templates for the addition of new natural product monomers (MonomerSetTemplate.java) and tailoring reactions (ReactionTemplate.java) are provided to facilitate the addition of new functionality to LEMONS. 
