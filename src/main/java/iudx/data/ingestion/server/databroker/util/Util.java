package iudx.data.ingestion.server.databroker.util;

import static iudx.data.ingestion.server.databroker.util.Constants.*;

import io.vertx.core.json.JsonObject;

public class Util {

  public static JsonObject getMetadata(JsonObject request) {
    JsonObject result = new JsonObject();
    String id = request.getString("id");
    String[] arr = id.split("/");
    String exchangeName = getExchangeName(arr);
    result
        .put(EXCHANGE_NAME, exchangeName)
        .put(EXCHANGE_URL, getExchangeUrl(arr));
    if (arr.length == 5) {
      result.put(ROUTING_KEY, getRoutingKey(exchangeName, getResourceName(arr)));
    } else {
      result.put(ROUTING_KEY, getRoutingKey(exchangeName));
    }
    return result;
  }

  public static String convertExchangeIntoUrl(String exchange) {
    return exchange.replaceAll("/", "%2F");
  }

  private static String getResourceName(String[] arr) {
    return arr[4];
  }

  private static String getResourceGroupName(String[] arr) {
    return arr[3];
  }

  private static String getResourceServerName(String[] arr) {
    return arr[2];
  }

  private static String getProviderName(String[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 2; i++) {
      sb.append(arr[i]);
      sb.append('/');
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  private static String getExchangeName(String[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      sb.append(arr[i]);
      sb.append('/');
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  private static String getRoutingKey(String exchangeName) {
    StringBuilder sb = new StringBuilder(exchangeName);
    return sb.append('/').append(ROUTING_KEY_ALL).toString();
  }

  private static String getRoutingKey(String exchangeName, String resourceName) {
    StringBuilder sb = new StringBuilder(exchangeName);
    return sb.append('/').append('.').append(resourceName).toString();
  }

  private static String getExchangeUrl(String[] arr) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      sb.append(arr[i]);
      sb.append("%2F");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }
}