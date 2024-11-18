package com.micfro.ragdemo;

import org.springframework.ai.chat.client.ChatClient;

import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
public class RestController {
  private final ChatClient chatClient;
  private final PgVectorStore vectorStore;

  public RestController(ChatClient.Builder chatClientBuilder, PgVectorStore vectorStore) {
    this.chatClient = chatClientBuilder
        .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
        .build();
    this.vectorStore = vectorStore;
  }


  @GetMapping("/withRAG")
  public String  chatWithRAG(@RequestParam(defaultValue = "What does BMW expect to see for the EBIT margin in a corrido for Q3 2024?") String query) {
    return chatClient.prompt()
        .user(query)
        .call()
        .content();
  }


}
