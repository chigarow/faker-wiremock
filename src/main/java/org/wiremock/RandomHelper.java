package org.wiremock;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;
import java.util.Locale;
import net.datafaker.Faker;

public class RandomHelper extends HandlebarsHelper<Object> {

  private static final Locale DEFAULT_LOCALE = Locale.US; // Default to en-US
  private Faker faker;

  public RandomHelper() {
    this.faker = new Faker(DEFAULT_LOCALE); // Initialize with default locale (en-US)
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
      } else {
        // Use default locale if no locale specification is found
        this.faker = new Faker(DEFAULT_LOCALE);
      }

      return faker.expression("#{" + contextString + "}");
    } catch (RuntimeException e) {
      return handleError("Unable to evaluate the expression " + context, e);
    }
  }
}
