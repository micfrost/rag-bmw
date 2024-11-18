package com.micfro.ragdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class IngestionService implements CommandLineRunner {

  private final VectorStore vectorStore;
  private final Logger log = LoggerFactory.getLogger(IngestionService.class);



  @Value("classpath:/docs/Statement_Mertl_Q3_2024.pdf")
  private Resource bmwFinancialStatement;

  public IngestionService(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }


  @Override
  public void run(String... args) throws Exception {

    var pdfReader = new PagePdfDocumentReader(bmwFinancialStatement);
    TextSplitter textSplitter = new TokenTextSplitter();
    vectorStore.accept(textSplitter.apply(pdfReader.get()));
    log.info("VectorStore updated with financial statements from BMW-PDF document.");





  }
}
