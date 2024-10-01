package org.wiremock;

import com.github.jknack.handlebars.Helper;
import com.github.tomakehurst.wiremock.extension.TemplateHelperProviderExtension;
import java.util.Map;

/*
 * Author: Shreya Agarwal
 * Updated by: Gradito Tunggulcahyo
 */
public class RandomExtension implements TemplateHelperProviderExtension {
  @Override
  public Map<String, Helper<?>> provideTemplateHelpers() {
    return Map.of("random", new RandomHelper());
  }

  @Override
  public String getName() {
    return "faker-helper";
  }
}
