# LuceneIndexing# Lucene-Indexing-and-Searching

## Trinity College Dublin - CS7IS3 - Information Retrieval and Web Search
## Assignment 1: Lucene and Cranfield by Sujit Jadhav - 19310363 - jadhavs@tcd.ie

### Project Folder Path 
/LuceneIndexing
### Result Folder Path 
/LuceneIndexing/data/results
### Trec_Eval Folder Path 
/LuceneIndexing/data/trec_eval-9.0.7
### Cranfield Data Path
/LuceneIndexing/data/cran

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
Above command will clean the target folder, download all the dependencies, and start Indexing(indexes are stored in index folder), then searching with default similarity as multiSimilarity(BM25 + Classic) will begin after indexing, which will generate results file in result folder(mentioned above). Various other similarities can be tried as explained further. This result file will be used by trec_eval to evaluate the relevance of the searches. The trec_eval will run automatically after the search results are formed, and trec_eval results are displayed on the terminal. This trec_eval result will also be stored in the results folder and a graph of precision against recall will be plotted and an image of the graph will be stored in the results folder. 
### N.B: Look in the terminal for Cyan Color text to see important output.

## Analyzer
Started with Standard Analyzer first, which gave map score of 0.38
Trying various other analyzers did not improve the map value by much, therefore, Custom Analyzer.

## Custom Analyzer
1. Tried StandardTokenizer with ClassicFilter first, which gave map score of approx. 0.41.
2. Used ClassicTokenizer along with ClassicFilter to get the map score of 0.4200. Have used various filters in the order mentioned as  ASCIIFoldingFilter, LengthFilter(min length = 3, max length = 25), LowerCaseFilter, StopFilter(created stopword list manually), KStemFilter and PorterStemFilter.
3. The map value wasn’t much better when used filters like EnglishPossessiveFilter, ASCIIFoldingFilter, WordDelimiterFilter.
4. I also tried building Analyzer using Builder, but was stucked at some point due to some file access issue, and ended up using TokenStream.

## Indexer
For indexing, I took the folder name as parameter, passed it to be read by a function. This function will read the Cran document file that is to be indexed.
Index File Reading/Parser
Cran document file is read line by line to get the identifier and the inner contents in that identifier. The doc is read so as to find the occurence of ‘.I’. If found, read the identifier number, then find for ‘.T’, ‘.A’ and so on. For multiple lines, read the buffer continuously.
Here, for the CRAN document, have indexed all the fields, but added one more field so as to search whole content. The new field was:
### <centre> ALL : Title + Author + Content </centre>

## Searching
For searching, one would use multipart search. In that case, I had only searched for a single field, the field ALL, which is mentioned above in the indexing part.

## Similarity Function
The following Similarities have been used:
   ### 1. ClassicSimilarity
    mvn clean install -Dsimilarity=classic
    Map: 0.4247
   ### 2. BM25Similarity
    mvn clean install -Dsimilarity=bm25
    Map: 0.4266
   ### 3. LMDirichletSimilarity
    mvn clean install -Dsimilarity=lmd
    Map: 0.3818
   ### 4. LMJelinekMercerSimilarity
    mvn clean install -Dsimilarity=lmj
    Map: 0.4076
  ### 5. MultiSimilarity 
    MultiSimilarity = (BM25Similarity + ClassicSimilarity)
    mvn clean install -Dsimilarity=multi
    Map: 0.4322(BEST SCORE)

## Trec Evaluation
The trec eval runs along the mvn command. To run it separately, use the command from project path given below 
data/trec_eval-9.0.7/trec_eval data/cran/cranqrel data/results/query_results_[SimilarityFunctionNamePassedAsArgument]

## Plotting Graph
To get proper knowledge about how the indexer and searcher is performing, a graph of Recall VS Precision is plotted and in the results folder. Along with my graph, I have also plotted the ideal graph of Recall VS Precision. Below is my best Graph.

![Image of PR Graph](https://github.com/jadhavsujit4/LuceneIndexing/blob/master/data/results/PRGraph_MULTI.jpeg)

## What was and is still Challenging?
- Parsing every content in their uniqueness is sometimes challenging and can be a cumbersome job.
- Indexing is easy! However, building the analyzer for it is not as easy. Choosing the right analyzer is not easy and felt like it is subjective to the content/data that we index.
- Again, creating an analyzer with the help of filters is not as easy and a tedious process as there is the need to try every combination and sequence.
- Building a stop words list is a never ending process. I used stopwords from various sources.

## What can be expected in the next phase?
- NLP being heard as a good library to deal with words and content would be worth a try in future.
- Building a synonym dictionary for similar kinds of words in a dataset.
- Trying different or custom similarity and custom scoring implementation.
- Understanding BM25 similarity parameters k and b and trying to find optimal values for CRAN dataset.
- Boosting with optimal value in query term.

## Results
Analyzer = (ClassicFilter + ASCIIFoldingFilter + LengthFiler + LowerCaseFilter + StopFilter + KStemFilter + PorterStemFilter)

IndexedDocs = Indexer(Analyzer)

Query Results = Searcher(IndexedDocs + Multi-Similarity Function + Analyzer + Maximum Hits = 50)
(Query Ranking file is saved)

Recall VS Precision Graph is created.

### *Map Value  = 0.4322 (BEST)*
