package org.wiremock;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;
import java.util.Locale;
import net.datafaker.Faker;

public class RandomHelper extends HandlebarsHelper<Object> {

  private static final String DEFAULT_LOCALE = "id-ID"; // Default locale set to id-ID
  private Faker faker;

  public RandomHelper() {
    this.faker = new Faker(new Locale(DEFAULT_LOCALE)); // Initialize with default locale
  }

  @Override
  public Object apply(Object context, Options options) {
    try {
      String contextString = context.toString();

      // Check if the context string contains a locale specification
      if (contextString.contains(".")) {
        String[] parts = contextString.split("\\.", 2);
        String localePart = parts[0];
        String expressionPart = parts[1];

        if (localePart.matches("[a-zA-Z]{2}-[a-zA-Z]{2}")) {
          // If locale is provided (e.g., id-ID, th-TH), use it
          Locale locale = new Locale.Builder().setLanguageTag(localePart.replace('_', '-')).build();
          this.faker = new Faker(locale);
          contextString = expressionPart; // Use only the expression part for Faker
        } else {
          // If the first part is not a valid locale, assume it's part of the expression
          this.faker = new Faker(new Locale(DEFAULT_LOCALE)); // Reset to default locale
        }
      } else {
        // No locale specified, use default locale
        this.faker = new Faker(new Locale(DEFAULT_LOCALE));
      }

      return faker.expression("#{" + contextString + "}");
    } catch (RuntimeException e) {
      return handleError("Unable to evaluate the expression " + context, e);
    }
  }
}
