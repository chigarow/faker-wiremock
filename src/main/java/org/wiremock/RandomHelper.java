package org.wiremock;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;
import java.util.Locale;
import net.datafaker.Faker;

public class RandomHelper extends HandlebarsHelper<Object> {

  private Faker faker; // Field added

  public RandomHelper() {
    this.faker = new Faker(); // Initialize with default locale
  }

  @Override
  public Object apply(Object context, Options options) {
    try {
      String contextString = context.toString();

      // Check if the context string contains a locale specification
      if (contextString.contains(".")) {
        String[] parts = contextString.split("\\.", 2);
        String localePart = parts[0];
        contextString = parts[1];

        // Update the Faker instance with the specified locale
        Locale locale = new Locale.Builder().setLanguageTag(localePart.replace('_', '-')).build();
        this.faker = new Faker(locale);
      }

      return faker.expression("#{" + contextString + "}");
    } catch (RuntimeException e) {
      return handleError("Unable to evaluate the expression " + context, e);
    }
  }
}
