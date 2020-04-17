# LuceneIndexing# Lucene-Indexing-and-Searching

## Trinity College Dublin - CS7IS3 - Information Retrieval and Web Search
## Assignment 2: Lucene by Team 6 : BOOSTERS
## Team Members:
Name - TCD Id
Sujit Jadhav - 19310363
Rohan Bagwe - 19314431
Chetan Prasad - 19308180
Jiawen Lin - 19309750

### Project Folder Path 
/LuceneIndexing
### Result Folder Path 
/LuceneIndexing/data/results
### Trec_Eval Folder Path 
/LuceneIndexing/data/trec_eval-9.0.7
### Cranfield Data Path
/LuceneIndexing/rawdata

## File Naming Convention
### Query result file
query_results_[SimilarityFunctionNamePassedAsArgument]
### Trec Eval result file
TrecEval_Result_[SimilarityFunctionNamePassedAsArgument].txt
### Precision VS Recall Graph Image file
PRGraph_[SimilarityFunctionNamePassedAsArgument].jpeg

## Getting Started
### How to Run: 
cd to above project path and type <b> mvn clean install </b>

### What will happen from above command: 
Above command will clean the target folder, download all the dependencies, and give interactive options. 
### N.B: Look in the terminal for Cyan Color text to see important output.

## Data Set
1. Financial Times Limited (1991, 1992, 1993, 1994)
2. Federal Register (1994)
3. Foreign Broadcast Information Service (1996)
4. Los Angeles Times (1989, 1990)

## Analyzer
Started with Standard Analyzer first, which gave map score of 0.38
Trying various other analyzers did not improve the map value by much, therefore, Custom Analyzer.

## Custom Analyzer
1. Tried StandardTokenizer with ClassicFilter first, which gave map score of approx. 0.20.
2. Used ClassicTokenizer along with ClassicFilter to get the map score of 0.30. Have used various filters in the order mentioned as  ASCIIFoldingFilter, LengthFilter(min length = 3, max length = 25), LowerCaseFilter, SynonymFilter, StopFilter(created stopword list manually), KStemFilter and PorterStemFilter.
3. The map value wasn’t much better when used filters like EnglishPossessiveFilter, ASCIIFoldingFilter, WordDelimiterFilter.

## Indexer
For indexing various news publication documents, I took the folder name as parameter, passed it to be read by a function. This function will read the nrews document file that is to be indexed.

The most inportant fields that were indexed are : HEADLINE/TITLE/DOCTITLE, TEXT, SUMMARY, GRAPHICS, SUPPLEMENTARY
### <centre> ALL : HEADLINE/TITLE/DOCTITLE + TEXT + SUMMARY + GRAPHICS + SUPPLEMENTARY </centre>

## Searching
The queries were topics containing topic number, title, text and narrative. Narrative contained information that can be either relevant or irrelevant.
For searching, one would use multipart search. In that case, we had only searched for a single field, the field ALL, which is mentioned above in the indexing part.
Boosting was provided to the search query parameters. Relevant and irrelevant narrative have to be dealt carefully with boosting values.
## Similarity Function
The following Similarities have been used:
   ### 1. ClassicSimilarity
   ### 2. BM25Similarity
   ### 3. LMDirichletSimilarity
   ### 4. LMJelinekMercerSimilarity
  ### 5. MultiSimilarity 
    MultiSimilarity = (BM25Similarity + LMJSimilarity)

## Trec Evaluation
The trec eval runs along the mvn command. To run it separately, use the command from project path given below 
data/trec_eval-9.0.7/trec_eval data/cran/cranqrel data/results/query_results_[SimilarityFunctionNamePassedAsArgument]

## Plotting Graph
To get proper knowledge about how the indexer and searcher is performing, a graph of Recall VS Precision is plotted and in the results folder. Along with my graph, I have also plotted the ideal graph of Recall VS Precision. Below is my best Graph.

![Image of PR Graph](https://github.com/jadhavsujit4/LuceneIndexing/blob/master/data/results/PRGraph_MULTI.jpeg)

## Results
Analyzer = (ClassicFilter + ASCIIFoldingFilter + LengthFiler + LowerCaseFilter + SynonymFilter + StopFilter + KStemFilter + PorterStemFilter)

IndexedDocs = Indexer(Analyzer)

Query Results = Searcher(IndexedDocs + BM25 Function + Analyzer + Maximum Hits = 1000)
(Query Ranking file is saved)

Recall VS Precision Graph is created.

### *Map Value  = 0.3404 (BEST)*
