# Jaxrs Security

**Jaxrs Security** provides an authorization filter that uses Authorization HTTP header to set JAX-RS API security context. It allows you to use [Javax security annotations](https://docs.oracle.com/javaee/7/api/javax/annotation/security/package-summary.html) in your resources.

## Get it

Add the following to your Maven configuration:

```xml
<dependency>
	<groupId>com.bekioui.jaxrs</groupId>
	<artifactId>jaxrs-security</artifactId>
	<version>1.0.0</version>
</dependency>
```

## Use it

Add the following to your Spring configuration file:

```java
@Configuration
@Import({ JaxrsSecurityConfig.class })
public class SpringConfig {
  
}
```

By default Authorization HTTP header has to have the below JSON format:

```json
{
  "identifier":"solid",
  "roles":[
    "ADMIN"
  ]
}
```

You can custom your authorization JSON by inheriting Token class.

## License
	
	Copyright (C) 2016 Mehdi Bekioui (consulting@bekioui.com)
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.		