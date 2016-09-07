# Xfers Java Bindings [![Build Status](https://travis-ci.org/xfers/xfers-java.svg?branch=master)](https://travis-ci.org/xfers/xfers-java)

You can sign up for a Xfers account at https://xfers.com.

## Requirements

Java 1.6 and later.

## Installation

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.xfers</groupId>
  <artifactId>xfers-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "com.xfers:xfers-java:1.1.0"
```

### Others

You'll need to manually install the following JARs:

* The Xfers JAR from https://github.com/Xfers/xfers-java/releases/latest
* [Google Gson](http://code.google.com/p/google-gson/) from <http://google-gson.googlecode.com/files/google-gson-2.2.4-release.zip>.


## Documentation

Please see the [Java API docs](https://xfers.com/docs/api/java) for the most up-to-date documentation.

## Usage

XfersExample.java

```java
import java.util.HashMap;
import java.util.Map;

import com.xfers.Xfers;
import com.xfers.exception.XfersException;
import com.xfers.model.Charge;
import com.xfers.net.RequestOptions;

public class XfersExample {

    public static void main(String[] args) {
        RequestOptions requestOptions = (new RequestOptionsBuilder()).setApiKey("YOUR-SECRET-KEY").build();
        Map<String, Object> chargeMap = new HashMap<String, Object>();
        chargeMap.put("amount", 100);
        chargeMap.put("currency", "usd");
        Map<String, Object> cardMap = new HashMap<String, Object>();
        cardMap.put("number", "4242424242424242");
        cardMap.put("exp_month", 12);
        cardMap.put("exp_year", 2020);
        chargeMap.put("card", cardMap);
        try {
            Charge charge = Charge.create(chargeMap, requestOptions);
            System.out.println(charge);
        } catch (XfersException e) {
            e.printStackTrace();
        }
    }
}
```
