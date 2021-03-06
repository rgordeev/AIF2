package io.aif.language.semantic;

import io.aif.associations.builder.AssociationGraph;
import io.aif.graph.normal.IGraph;
import io.aif.common.FileHelper;
import io.aif.language.common.IDict;
import io.aif.language.common.IDictBuilder;
import io.aif.language.common.IMapper;
import io.aif.language.common.ISearchable;
import io.aif.language.sentence.SimpleSentenceSplitterCharactersExtractorQualityTest;
import io.aif.language.sentence.splitters.AbstractSentenceSplitter;
import io.aif.language.token.TokenSplitter;
import io.aif.language.word.IWord;
import io.aif.language.word.dict.DictBuilder;
import io.aif.language.word.dict.WordPlaceHolderMapper;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class SemanticGraphBuilderTest {

    @Test
    public void testMap() throws Exception {
        String text;
        try(InputStream modelResource = SimpleSentenceSplitterCharactersExtractorQualityTest.class.getResourceAsStream("aif_article.txt")) {
            text = FileHelper.readAllText(modelResource);
        }

        final TokenSplitter tokenSplitter = new TokenSplitter();
        final AbstractSentenceSplitter sentenceSplitter = AbstractSentenceSplitter.Type.HEURISTIC.getInstance();
        final List<String> tokens = tokenSplitter.split(text);
        final List<List<String>> sentences = sentenceSplitter.split(tokens);
        final List<String> filteredTokens = sentences.stream().flatMap(List::stream).collect(Collectors.toList());

        final IDictBuilder dictBuilder = new DictBuilder();
        final IDict<IWord> dict = dictBuilder.build(filteredTokens);
        final IMapper<Collection<String>, List<IWord.IWordPlaceholder>> toWordPlaceHolderMapper = new WordPlaceHolderMapper((ISearchable<String, IWord>)dict);
        final List<IWord.IWordPlaceholder> placeholders = toWordPlaceHolderMapper.map(filteredTokens);

//        final ISeparatorExtractor testInstance = ISeparatorExtractor.Type.PROBABILITY.getInstance();
//        final ISeparatorsGrouper separatorsGrouper = ISeparatorsGrouper.Type.PROBABILITY.getInstance();
//        final ISeparatorGroupsClassifier sentenceSeparatorGroupsClassificatory = ISeparatorGroupsClassifier.Type.PROBABILITY.getInstance();
//        final List<Character> separators = testInstance.extract(tokens).get();
//        final Map<ISeparatorGroupsClassifier.Group, Set<Character>> grouppedSeparators = sentenceSeparatorGroupsClassificatory.classify(tokens, separatorsGrouper.group(tokens, separators));


        final SemanticGraphBuilder semanticGraphBuilder = new SemanticGraphBuilder();
        final AssociationGraph<IWord> graph = semanticGraphBuilder.build(placeholders);
        final List<IWord> sortedNodes = graph.getVertices().stream().sorted((w2, w1) -> (graph.getVertexWeight(w1)).compareTo(graph.getVertexWeight(w2))).collect(Collectors.toList());
        final List<IWord> invertedSortedNodes = graph.getVertices().stream().sorted((w1, w2) -> (graph.getVertexWeight(w1)).compareTo(graph.getVertexWeight(w2))).collect(Collectors.toList());
        System.out.println(sortedNodes);
    }
}