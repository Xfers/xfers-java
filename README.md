# Xfers Java Bindings

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
  <version>1.3.0</version>
</dependency>
```

### Non-Maven users

You'll need to manually install the following JARs:

* [The Xfers JAR](https://github.com/Xfers/xfers-java/releases/latest) from https://github.com/Xfers/xfers-java/releases/latest.
* [Google Gson](http://code.google.com/p/google-gson/) from <http://google-gson.googlecode.com/>.
* [Google Guava](https://github.com/google/guava) from <https://github.com/google/guava>.
* [Unirest for Java](http://unirest.io/java.html) from http://unirest.io/java.html.

## Documentation

Please see the [API docs](http://docs.xfers.io/) for the most up-to-date documentation.

## Usage

XfersExample.java

```java
import com.xfers.Xfers;
import com.xfers.model.Charge;
import com.xfers.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XfersExample {

    public static void main(String[] args) {
        Xfers.apiKey = "pXcfdAKNorDe_o1eou1NSp4mwssiEzem_6sg8fwnZWs";
        Xfers.setSGSandbox();

        try {
            System.out.println("Listing charges without filter");
            List<Charge> charges = Charge.listAll();
            for (Charge charge : charges) {
                System.out.println(charge.toString());
                List<Item> items = charge.getItems();
                for (Item item : items) {
                    System.out.println(item.toString());
                }
            }

            System.out.println("Listing charges with filter");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("limit", "1");

            charges = Charge.listAll(params);
            for (Charge charge : charges) {
                System.out.println(charge.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

}
```
