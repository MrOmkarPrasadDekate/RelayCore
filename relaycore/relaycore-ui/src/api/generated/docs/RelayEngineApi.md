# OpenApiDefinition.RelayEngineApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**handle**](RelayEngineApi.md#handle) | **POST** /api/v1/relay | Ingest a new webhook



## handle

> String handle(body)

Ingest a new webhook

Accepts a payload and queues it for delivery via Kafka

### Example

```javascript
import OpenApiDefinition from 'open_api_definition';

let apiInstance = new OpenApiDefinition.RelayEngineApi();
let body = "body_example"; // String | 
apiInstance.handle(body, (error, data, response) => {
  if (error) {
    console.error(error);
  } else {
    console.log('API called successfully. Returned data: ' + data);
  }
});
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **body** | **String**|  | 

### Return type

**String**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: */*

