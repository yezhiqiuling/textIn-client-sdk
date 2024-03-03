# Getting Started

### Introduce dependency

We started by introducing our SDK-related dependencies into our project's `pom.xml` file

```xml
<dependency>
	<groupId>com.zhou</groupId>
  	<artifactId>textIn-client-sdk</artifactId>
  	<version>0.0.2</version>
</dependency>
```

### Identity authentication

Next, we need to provide the free `x-ti-app-id` and `x-ti-secret-code` we gave when registering with the TextIn product in the project's application.yml profile

```yml
textin:
  client:
    appId: xxx
    secretCode: xxx
```

### Creating our client

Use java's resource annotation to inject into our client

```java
@Resource
private TextInApiClient textInApiClient;
```

### Call method testing

First test the connectivity of the client by calling the testConnect method on the client, and then you can perform a simple test using the provided image recognition method

```java
/**
 * image recognition MyTextIn SDK test
 * @param multipartFile
 * @return
*/
@PostMapping("/testImage/mySDK")
public List<String> testImageMyTextInSDK(@RequestPart("file") MultipartFile multipartFile) {
	String testConnect = textInApiClient.testConnect();
	System.out.println(testConnect);
	List<String> imageData = textInApiClient.judgeImage(multipartFile);

	return imageData;
}
```

Similarly, we also provide the corresponding form picture recognition and picture cutting enhancement functions, more functions look forward to your discovery and supplement.
